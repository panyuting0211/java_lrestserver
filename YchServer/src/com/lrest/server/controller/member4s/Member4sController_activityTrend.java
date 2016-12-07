package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchSalesAreaRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarTrendRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Car_BrandModelCar;
import com.lrest.server.models.Car_special_price_car;
import com.lrest.server.models.Car_special_price_car_trend;
import com.lrest.server.services.DB;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;
import static java.util.stream.Collectors.*;

/**
* @DESCRIPTION: 本类用来记录活动历史
* @Author: 韩武洽 @Wyshown
* @Date: 2016/9/13-15:36
* @version V2.0  
**/
@Path("/4s/activityTrend")
public class Member4sController_activityTrend extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/activityTrend");
    }


    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String list(String query) {
        log.debug("han------------------------------runing");

        try(Connection conn = DB.getConnection();
            DSLContext create = DSL.using(conn,SQLDialect.MYSQL)){
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            // 车款的ID
            String carId = getJsonAsString(json,"car_id");
            // 用户的ID
            String userId = getJsonAsString(json,"user_id");
            // 内饰
            String interiorColorId = getJsonAsString(json,"interior_color_id");
            // 外观
            String exteriorColorId = getJsonAsString(json,"exterior_color_id");

            // 查询出所有本店的车款的活动历史(带分页)
            List<Car_special_price_car_trend> rets = create.fetch(
                    activityTrendSql(getJsonAsInt(json,"pagenum"),getJsonAsInt(json,"page")
                            ,userId,carId,interiorColorId,exteriorColorId)
            ).into(Car_special_price_car_trend.class);

            // 查询出所有本店新增活动的车款的内饰 外饰的总数
            int count = create.fetch(activityTrendSql(0,0 ,userId,carId,interiorColorId,exteriorColorId)).size();
            // 封装为一个带count的对象
            BaseCount<Car_special_price_car_trend> resultData =  new BaseCount<>();
            resultData.count = count;
            resultData.rows = rets;

            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }
    }



    public String activityTrendSql(Integer pagenum,Integer page,
                                   String userId,String carId,String interiorColorId,String exteriorColorId){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \n");
        sql.append(" brand.id AS brandId, \n");
        sql.append(" brand.brand_name AS brandName, \n");
        sql.append(" carModel.id AS carModelId, \n");
        sql.append(" carModel.car_model_name AS carModelName, \n");
        sql.append(" car.id AS carId, \n");
        sql.append(" car.car_name AS carName, \n");
        sql.append(" interiorColor.id AS interiorColorId, \n");
        sql.append(" interiorColor.color_name AS interiorColorName, \n");
        sql.append(" exteriorColor.id AS exteriorColorId, \n");
        sql.append(" exteriorColor.color_name AS exteriorColorName, \n");
        sql.append(" carTrend.price, \n");
        sql.append(" carTrend.special_price AS specialPrice, \n");
        sql.append(" carTrend.start_date AS startDate, \n");
        sql.append(" carTrend.end_date AS endDate, \n");
        sql.append(" carTrend.number, \n");
        sql.append(" carTrend.createtime AS createTime \n");

        sql.append(" FROM gch_special_price_car_trend AS carTrend\n");
        sql.append(" LEFT JOIN gch_car AS car ON carTrend.car_id = car.id \n");
        sql.append(" LEFT JOIN gch_view_interior_color AS interiorColor ON carTrend.interior_color_id = interiorColor.id \n");
        sql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON carTrend.exterior_color_id = exteriorColor.id \n");
        sql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
        sql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(" AND carTrend.car_id ='" + carId + "' \n");
        sql.append(" AND carTrend.interior_color_id ='" + interiorColorId + "' \n");
        sql.append(" AND carTrend.exterior_color_id ='" + exteriorColorId + "'\n");
        sql.append(" AND carTrend.user_id ='" + userId + "'\n");
        sql.append(" ORDER BY carTrend.createtime \n");

        if (0 != pagenum && 0 != page) {
            sql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
        }
        return  sql.toString();
    }











    


}
