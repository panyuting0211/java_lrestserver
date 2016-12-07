package com.lrest.server.controller.member4s;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchSalesArea;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Car_BrandModelCar;
import com.lrest.server.models.Car_special_price_car;
import com.lrest.server.services.DB;
import com.lrest.server.utils.Constants;
import com.lrest.server.utils.DateUtils;
import com.tencent.common.MD5;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.GCH_SPECIAL_PRICE_CAR;
import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.*;

/**
* @DESCRIPTION: 销售区域Contrller接口
* @Author: 韩武洽 @Wyshown
* @Date: 2016/9/13-11:29
* @version V2.0  
**/
@Path("/4s/saleArea")
public class Member4sController_saleArea extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/activity");
    }



}
