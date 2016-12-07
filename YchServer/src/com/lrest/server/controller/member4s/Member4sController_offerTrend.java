package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Member4s_offerTrend;
import com.lrest.server.services.DB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;

import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * @version V2.0
 * @DESCRIPTION:
 * @Return: 报价历史管理的类
 * @Author: 韩武洽 @Wyshown
 * @Date: 2016/9/14-13:47
 **/
@Path("/4s/offerTrend")
public class Member4sController_offerTrend extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/offerTrend");
    }



    /**
    * @Description:
     * @param query 格式参数如下:
        {
            "query": {
                "pagenum": 10,
                "page": 1,
                "carId": "5958",
                "userId": "174",
                "interiorColorId": "310f4fc3bf4311e5ba4114dda95160ab",
                "exteriorColorId": "11509"
            }
        }
    * @Return: 返回4店的报价历史活动
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-21 10:03
    * @Version 2.0
    */

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
            String carId = getJsonAsString(json,"carId");
            // 用户的ID
            String userId = getJsonAsString(json,"userId");
            // 内饰
            String interiorColorId = getJsonAsString(json,"interiorColorId");
            // 外观
            String exteriorColorId = getJsonAsString(json,"exteriorColorId");

            // 查询出所有4s店的报价历史(带分页)
            List<Member4s_offerTrend> rets = create.fetch(
                    offerTrendSql(getJsonAsInt(json,"pagenum"),getJsonAsInt(json,"page")
                            ,userId,carId,interiorColorId,exteriorColorId)
            ).into(Member4s_offerTrend.class);

            // 查询出所有4s店的报价历史车款的内饰 外饰的总数
            int count = create.fetch(offerTrendSql(0,0 ,userId,carId,interiorColorId,exteriorColorId)).size();
            // 封装为一个带count的对象
            BaseCount<Member4s_offerTrend> resultData =  new BaseCount<>();
            resultData.count = count;
            resultData.rows = rets;

            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }
    }



    public String offerTrendSql(Integer pagenum, Integer page,
                                String userId, String carId, String interiorColorId, String exteriorColorId){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \n");
        sql.append(" brand.id AS brandId, \n");
        sql.append(" brand.brand_name AS brandName, \n");
        sql.append(" carModel.id AS carModelId, \n");
        sql.append(" carModel.car_model_name AS carModelName, \n");
        sql.append(" color.*, \n");
        sql.append(" car.car_name AS carName, \n");
        sql.append(" trend.price, \n");
        sql.append(" trend.discount, \n");
        sql.append(" trend.low_price AS lowPrice, \n");
        sql.append(" trend.createtime AS createTime\n");
        sql.append(" FROM gch_price_trend AS trend \n");
        sql.append(" LEFT JOIN gch_car_price AS price ON trend.car_price_id = price.id \n");
        sql.append(" LEFT JOIN gch_car AS car ON trend.car_id = car.id \n");
        sql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
        sql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id \n");
        sql.append(" LEFT JOIN ( \n");
        sql.append(" SELECT \n");
        sql.append(" interiorColor.id AS interiorColorId, \n");
        sql.append(" interiorColor.color_name AS interiorColorName, \n");
        sql.append(" exteriorColor.id AS exteriorColorId, \n");
        sql.append(" exteriorColor.color_name AS exteriorColorName, \n");
        sql.append(" exteriorColor.car_id AS carId \n");
        sql.append(" FROM gch_view_interior_color AS interiorColor \n");
        sql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON interiorColor.car_id = exteriorColor.car_id \n");
        sql.append(" WHERE interiorColor.id = '" +  interiorColorId+ "' \n");
        sql.append(" AND exteriorColor.id = '" + exteriorColorId + "' \n");
        sql.append(" ) AS color ON color.carId = car.id \n");
        sql.append(" WHERE trend.car_id = '" + carId + "' \n");
        sql.append(" AND trend.isdelete = 0 \n");
        sql.append(" AND price.user_id = '" + userId + "' \n");
        sql.append(" AND trend.interior_color_id = '" + interiorColorId + "' \n");
        sql.append(" AND trend.exterior_color_id = '" + exteriorColorId + "' \n");
        sql.append(" ORDER BY trend.createtime DESC \n");

        if (0 != pagenum && 0 != page) {
            sql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
        }
        return  sql.toString();
    }
}
