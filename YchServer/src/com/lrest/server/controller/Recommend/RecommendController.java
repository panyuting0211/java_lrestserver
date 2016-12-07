package com.lrest.server.controller.Recommend;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.Tables;


import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.jooqmodel.tables.records.GchViewCarPriceRecord;
import com.lrest.server.models.bestselling;

import com.lrest.server.services.DB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.GCH_USER_ACTIVITY;
import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.*;

@Path("/nl/recommend")
public class RecommendController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    public String get() {
        log.debug("/nl/car");
        return "/nl/car";
    }

    @POST
    @Path("bestselling/list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String bestselling() {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            GchViewCarPrice GchViewCarPrice=new GchViewCarPrice();
//            String result= create.select()
//            Map<String,Record2<String,String>> result=create.select(GchViewCarPrice.ID,GchViewCarPrice.BRAND_NAME)
            List<bestselling> result=create.select(GchViewCarPrice.ID,GchViewCarPrice.BRAND_ID,GchViewCarPrice.BRAND_NAME,GchViewCarPrice.CAR_MODEL_ID,GchViewCarPrice.CAR_MODEL_NAME,GchViewCarPrice.CAR_ID,GchViewCarPrice.CAR_NAME,GchViewCarPrice.EXTERIOR_COLOR_NAME,GchViewCarPrice.INTERIOR_COLOR_NAME,GchViewCarPrice.CAR_MODEL_IMAGEURL,GchViewCarPrice.DISCOUNT,GchViewCarPrice.LOW_PRICE,GchViewCarPrice.AUTH_PRICE)
                    .from(GchViewCarPrice)
                    .where(GchViewCarPrice.ISDELETE.eq(0).and(GchViewCarPrice.IS_XUNJIA.eq((short) 1)))
                    .groupBy(GchViewCarPrice.CAR_MODEL_ID)
                    .orderBy(GchViewCarPrice.MODEL_ACCESS_QUANTITY.desc())
                    .limit(50)
                    .fetch().into(bestselling.class);


            List<bestselling> rst=new ArrayList<bestselling>();



            if (result.size()>4){
                HashSet<Integer> hashSet = new HashSet<Integer>();

                // 生成随机数字并存入HashSet
                while(hashSet.size() < 4){
                    Random random = new Random();
                    int v=random.nextInt(result.size());
                    log.debug("random "+v);
                    hashSet.add(v);
                }


                for (Integer r_num : hashSet) {

                    rst.add(result.get(r_num));
                }
            }else{
                for (int i=0;i<result.size();i++) {

                    rst.add(result.get(i));
                }
            }



            return success(rst);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


}
