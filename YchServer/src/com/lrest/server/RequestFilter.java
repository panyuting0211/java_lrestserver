package com.lrest.server;

/**
 * Created by angrycans on 15/7/7.
 */

import com.lrest.server.services.*;
import com.lrest.server.services.session.SessionManager;


import com.mysql.jdbc.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


@PreMatching
public class RequestFilter implements ContainerRequestFilter
{

    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    //@Inject
    //private SessionManager sessionManager;

    @Inject
    private SystemManager systemManager;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {



        MultivaluedMap<String, String> requestheaders = requestContext.getHeaders();
        log.debug("Executing REST RequestFilter getAbsolutePath >"+requestContext.getUriInfo().getAbsolutePath());
        log.debug("Executing REST RequestFilter header >"+requestContext.getUriInfo().getPath());
        log.debug("Executing REST RequestFilter getMethod >"+requestContext.getMethod());

        if (systemManager.SYSCODE!=1){
            log.error("System error "+systemManager.dump());
            requestContext.abortWith(Response.status(Response.Status.EXPECTATION_FAILED).entity(systemManager.LASTERR).build());
            return;
        }

        if (requestContext.getMethod().equalsIgnoreCase("options")) {
            MultivaluedMap<String, String> headers = requestContext.getHeaders();

/*            System.out.println("Executing REST response header>");

            Iterator it2 = headers.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pairs = (Map.Entry) it2.next();
                System.out.println("  " + pairs.getKey() + " = " + pairs.getValue());
            }*/

//            responseheaders.add("Access-Control-Allow-Origin", "*");
//            //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org
//            responseheaders.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
//            responseheaders.add("Access-Control-Allow-Headers", " Content-Type, sessionid");
            requestContext.abortWith(Response.ok().build());
            return;
        }


        ArrayList<String> ignorelist=new ArrayList<String>();
        ignorelist.add("/");
        ignorelist.add("login/");
        ignorelist.add("wx/");
        ignorelist.add("demo/");
        ignorelist.add("nl/");
        ignorelist.add("static/");
//        ignorelist.add("mobile/login/");


        String urlpath=requestContext.getUriInfo().getPath()+"/";
        for (int i = 0; i < ignorelist.size(); i++) {
            //log.debug(" "+urlpath.substring(0,urlpath.indexOf("/")+1)+" "+ignorelist.get(i));
            if (urlpath.substring(0,urlpath.indexOf("/")+1).equalsIgnoreCase(ignorelist.get(i))){

                log.info("no login enter");
                return;
            }
        }



        String sid = requestContext.getHeaderString("sessionid");
        if (StringUtils.isNullOrEmpty(sid)){

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(systemManager.LASTERR).build());
            return;

        }else{
            //auth sid expired
            log.debug("sid ="+sid);
            String authsid= SessionManager.getInstance().getSID(sid);
            log.debug("sid ="+sid+" authsid="+authsid);
//            if (Config.use_redis==1){
//                authsid=redisSessionManager.getSID(sid);
//            }else{
//                MemorySessionManager memorySessionManager=new MemorySessionManager();
//                authsid=memorySessionManager.getSID(sid);
//            }

            if (StringUtils.isNullOrEmpty(authsid)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

        }

        //RedisSessionManager rsm=new RedisSessionManager();
        //log.info(rsm.createSID("1",2));
//
//        Iterator it1 = requestheaders.entrySet().iterator();
//        while (it1.hasNext()) {
//            Map.Entry pairs = (Map.Entry) it1.next();
//            System.out.println("  " + pairs.getKey() + " = " + pairs.getValue());
//        }



        /*

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        System.out.println("Executing REST response header>");

        Iterator it2 = headers.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pairs = (Map.Entry) it2.next();
            System.out.println("  " + pairs.getKey() + " = " + pairs.getValue());
        }


        System.out.println("Inject sessionService 0"+sessionService);
        System.out.println("Inject sessionService 1"+sessionService.get("randmonsid"));
*/
//        if (requestheaders.get("session-id")==null){
//            sessionService.set("randmonsid", new SessionInfo("aaa"));
//        }
    }



}


