package com.lrest.server.controller;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lrest.server.jooqmodel.Tables;
import com.lrest.server.jooqmodel.tables.GchArea;
import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.models.Car_specialcar;
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

import static com.lrest.server.jooqmodel.Tables.GCH_AREA;
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




    @GET
    @Path("specialcar1")
    public String specialcar1(@QueryParam("model") int model) {

        log.debug("model="+model);
        try(Connection conn= DB.getConnection();
            DSLContext create = DSL.using(conn , SQLDialect.MYSQL))  {

//            List<GchViewCarPrice> list=create
//                    .select(GchViewCarPrice.GCH_VIEW_CAR_PRICE.CAR_ID)
//                    .from(GchViewCarPrice.GCH_VIEW_CAR_PRICE)
//                    .where("car_model_id=" +
//                    model).fetchInto(GchViewCarPrice.class);

//            String sql="select  car_id,car_name ,interior_color_id,interior_color_name from gch_view_car_price  where car_model_id=1263";
//
//
//            Result<Record> result =create.fetch(sql);


//
//            create.select(GCH_VIEW_CAR_PRICE.CAR_ID,GCH_VIEW_CAR_PRICE.INTERIOR_COLOR_ID,GCH_VIEW_CAR_PRICE.INTERIOR_COLOR_NAME)
//                       .from(GCH_VIEW_CAR_PRICE)
//                      .where(GCH_VIEW_CAR_PRICE.CAR_MODEL_ID.eq("1263"))
//                      .fetch()
//                      .stream()
//                      .collect(groupingBy(
//                              r -> r.getValue(GCH_VIEW_CAR_PRICE.CAR_ID),
//
//                              mapping(
//                                      r -> r.getValue(GCH_VIEW_CAR_PRICE.INTERIOR_COLOR_ID),
//                                      //r -> r.getValue(GCH_VIEW_CAR_PRICE.INTERIOR_COLOR_ID),
//                                      toList()
//                              )
//                      ))
//                      .forEach(
//                              (table, columns) ->
//                                      System.out.println(table + ": " + columns)
//                      )
//            ;


//log.info("-----");
//
//            sql="select id,id2,price,carid from test_min_price  where   id2=\"c1\"";
//            result =create.fetch(sql);
//
//
//            Map<String,test> t=result.
//                    stream().
//                    collect(
//                            Collectors.toMap
//                                    (       k->k.getValue("id2",String.class)+","+k.getValue("carid",String.class),
//                                            v -> new test(
//                                                    v.getValue("id",int.class).toString(),
//                                                    v.getValue("id2",String.class),
//                                                    v.getValue("carid",String.class),
//                                                    v.getValue("price",int.class).toString()
//
//                                            ),
//                                            (v1,v2)->{
//
//                                                //log.info(v1.toString()+"  "+v2.toString());
//                                                if (Integer.parseInt(v1.price)>Integer.parseInt(v2.price)){
//                                                    return v2;
//                                                }else{
//                                                    return v1;
//                                                }
//
//
//                                            }
//                                    )
//                    );

            //log.info(new Gson().toJson(result));

            String sql="select distinct car_id,car_name from gch_view_car_price  where car_model_id="+model;

            Result<Record> rets =create.fetch(sql);

            for (Record r:rets){

                JsonElement json = new JsonParser().parse(r.getValue("car_id").toString());

               // String str=

            }

            return successRespond("");

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }



    }









}
