package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lrest.server.services.DB;
import com.lrest.server.services.weixin.WXAPIConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

@Path("/static/{file}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaticController extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
//    public static class test{
//         public String  id;
//         public String id2;
//          public String price;
//         public String carid;
//
//        public test(String _id,String _id2,String _carid,String _price){
//            this.id=_id;
//            this.id2=_id2;
//            this.carid=_carid;
//            this.price=_price;
//        }
//
//        public String toString(){
//            return id+" "+id2+" "+price+" "+carid;
//        }
//    }

    @GET
    //@Path("carlist/{car_id}")

    public String get(@PathParam("file") String filename) {
         log.debug("static file="+filename+" req.url="+req.getRequestURL().toString());


       // String response = "statc";

        try {
//sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
            OkHttpClient client=new OkHttpClient();

            String url=req.getRequestURL().toString().replaceAll("/api/","/");
            log.debug("new url="+url);

            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {


                return response.body().string();
            } else {
                log.error("Unexpected code " + response);
                //throw new IOException("Unexpected code " + response);
            }

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }









}
