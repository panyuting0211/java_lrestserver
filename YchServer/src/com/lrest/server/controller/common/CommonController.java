package com.lrest.server.controller.common;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchBrand;
import com.lrest.server.jooqmodel.tables.GchUser_4s;
import com.lrest.server.models.*;
import com.lrest.server.services.DB;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.Connection;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Path("/nl/common")
public class CommonController extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    @GET

    public String get() {
         log.debug("nl");
         return "nl";
    }

    @POST
    @Path("provincecity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String provincecity(@QueryParam("model") int model) {

        log.debug("model="+model);
        try(Connection conn= DB.getConnection();
            DSLContext create = DSL.using(conn , SQLDialect.MYSQL))  {

            String sql="select a.ProvinceID provinceid,a.ProvinceName provincename,b.CityID cityid,b.CityName cityname from gch_data_province a,gch_data_city b where a.ProvinceID=b.ProvinceID ";//-- order by a.ProvinceName
//
//            java.util.List<Base_ProvinceCity> list=create.fetch(sql).into(Base_ProvinceCity.class);
//            return  success(list);


            Result<Record> result=create.fetch(sql);

            Base_ProvinceCity m_base_provincecity=new Base_ProvinceCity();
            m_base_provincecity.provincecity=result
                    .stream()
                    .collect(
                            groupingBy(
                                    r ->

                                       r.getValue("provincename", String.class)
                                    ,
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Base_ProvinceCity.ProvinceCity(

                                                    r.getValue("provincename", String.class)

                                                    ,r.getValue("cityname", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );


            sql="select ProvinceName from gch_data_province";

            m_base_provincecity.provinces=create.fetch(sql).into(String.class);

            return success(m_base_provincecity);


        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }



    }









}
