package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lrest.server.services.weixin.WXAPIConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Iterator;

@Path("/wx")
public class WxController extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    @GET

    public String get() {

     return "wx";
    }

    @GET
    @Path("auth2")
    public String auth2(@QueryParam("state") String state,@QueryParam("code") String code, @Context HttpServletResponse res) {


        log.debug(String.format("code  %s state %s",code,state));

        String openid="";
        if (code!=null&&!code.isEmpty()){
            openid=getOpenidBycode(code);
            if (openid==null){
                return error(-1,"get openid error");
            }
        }

        try{
            if (state!=null&&!state.isEmpty()){
                res.sendRedirect(state+"?openid="+openid);
                //log.debug(String.format("2code  %s state %s",code,state));
            }
            //log.debug(String.format("3code  %s state %s",code,state));

        }catch (Exception e){
            e.printStackTrace();
            return error(-1,"state params error");
        }


        return error(-1,"state params error");

    }


    public String getOpenidBycode(String code){

        try {
//sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
            OkHttpClient client=new OkHttpClient();
            Request request = new Request.Builder().url(WXAPIConfig.WX_API+"sns/oauth2/access_token?appid="
                    +WXAPIConfig.AppID+"&secret="+WXAPIConfig.AppSecret
                    +"&code="+code+"&grant_type=authorization_code").build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                JsonElement json=new JsonParser().parse(response.body().string());
                log.debug("URL="+request.url()+"\n"+json.toString());

                return json.getAsJsonObject().get("openid").toString();
            } else {
                log.error("Unexpected code " + response);
                //throw new IOException("Unexpected code " + response);
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }


    @GET
    @Path("auth")
    public String auth(@Context UriInfo urlparams, @Context HttpServletResponse res) {

        MultivaluedMap<String, String> queryParams = urlparams.getQueryParameters();

        if (queryParams != null) {

            Iterator it = queryParams.keySet().iterator();
            while (it.hasNext()) {
                String key = (String)it.next();

                log.debug("key="+key+" val="+queryParams.get(key));
            }

        }


        return success();

    }




}
