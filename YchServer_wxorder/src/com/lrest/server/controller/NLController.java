package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lrest.server.jooqmodel.Tables;

import com.lrest.server.jooqmodel.tables.GchViewCarPrice;

import com.lrest.server.services.DB;
import com.lrest.server.services.weixin.WXAPIConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRecordMapper;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.GCH_VIEW_CAR_PRICE;
import static java.util.stream.Collectors.*;

@Path("/nl")
public class NLController extends BaseController{
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

    public String get() {
         log.debug("nl");
         return "nl";
    }











}
