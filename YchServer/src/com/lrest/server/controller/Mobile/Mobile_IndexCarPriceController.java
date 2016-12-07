package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Mobile.Mobile_BaseCarPrice;
import com.lrest.server.models.Mobile.Mobile_CarPrice;
import com.lrest.server.models.Mobile.Mobile_CarPriceDetail;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION: Mobile端 免费看底价车
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:14
 **/

@Path("mobile/indexCarPrice")
public class Mobile_IndexCarPriceController extends BaseController{

    /**
     * @param query
     orderRule有两种规则 降序:"DESC" 升序:"ASC" orderField有一个字段: lowPrice(最低价)
     默认以lowPrice了降序排列
       {
            "query":{
                "pagenum":"10",
                "page":"1",
                "brandId":"102",
                "carTypeId":"0ff0c9d4bf3d11e5ba4114dda95160ab",
                "maxPrice":"50000000",
                "minPrice":"0",
                "orderField":"lowPrice",
                "orderRule":"DESC"
            }
       }
     * @Description:  首页-免费看底价-列表
     * @Return: 首页-免费看底价-列表
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/7 17:16
     * @Version 2.0
     */
    @POST
    @Path("findCarPriceByQuery")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarPriceByQuery(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");
            String brandId = getJsonAsString(json, "brandId");
            String carTypeId = getJsonAsString(json, "carTypeId");
            String maxPrice = getJsonAsString(json, "maxPrice");
            String minPrice = getJsonAsString(json, "minPrice");
            String orderField = getJsonAsString(json, "orderField");
            String orderRule = getJsonAsString(json, "orderRule");

            // 查询出车型的SQL语句
            StringBuilder indexCarPriceSql = new StringBuilder();
            indexCarPriceSql.append(" SELECT \n");
            indexCarPriceSql.append(" indexPrice.brand_id AS brandId, \n");
            indexCarPriceSql.append(" indexPrice.brand_name AS brandName, \n");
            indexCarPriceSql.append(" CASE \n");
            indexCarPriceSql.append(" WHEN indexPrice.logo IS NULL THEN NULL \n");
            indexCarPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(indexPrice.logo ,\"type\",\"small\")) \n");
            indexCarPriceSql.append(" END AS brandImage, \n");
            indexCarPriceSql.append(" indexPrice.model_id AS carModelId, \n");
            indexCarPriceSql.append(" indexPrice.model_name AS carModelName, \n");
            indexCarPriceSql.append(" indexPrice.car_id as carId, \n");
            indexCarPriceSql.append(" indexPrice.car_name AS carName, \n");
            indexCarPriceSql.append(" CASE \n");
            indexCarPriceSql.append(" WHEN indexPrice.exterior_img IS NULL THEN NULL \n");
            indexCarPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(indexPrice.exterior_img ,\"type\",\"small\")) \n");
            indexCarPriceSql.append(" END AS carImage, \n");
            indexCarPriceSql.append(" indexPrice.price, \n");
            indexCarPriceSql.append(" indexPrice.discount, \n");
            indexCarPriceSql.append(" indexPrice.low_price AS lowPrice, \n");
            indexCarPriceSql.append(" indexPrice.car_type_id AS carTypeId, \n");
            indexCarPriceSql.append(" indexPrice.car_type_name AS carTypeName, \n");
            indexCarPriceSql.append(" indexPrice.interior_color_id AS interiorColorId, \n");
            indexCarPriceSql.append(" indexPrice.interior_color_name AS interiorColorName, \n");
            indexCarPriceSql.append(" indexPrice.interior_color_value AS interiorColorValue, \n");
            indexCarPriceSql.append(" indexPrice.exterior_color_id AS exteriorColorId, \n");
            indexCarPriceSql.append(" indexPrice.exterior_color_name AS exteriorColorName, \n");
            indexCarPriceSql.append(" indexPrice.exterior_color_value AS exteriorColorValue \n");

            indexCarPriceSql.append(" FROM \n");
            indexCarPriceSql.append(" gch_view_index_price AS indexPrice \n");
            indexCarPriceSql.append(" WHERE 1 = 1 \n");

            indexCarPriceSql.append(" AND indexPrice.client = 1 \n");

            if (null != brandId) {
                indexCarPriceSql.append(" AND indexPrice.brand_id = '" + brandId + "' \n");
            }
            if (null != carTypeId) {
                indexCarPriceSql.append(" AND indexPrice.car_type_id = '" + carTypeId + "' \n");
            }
            if (null != maxPrice) {
                indexCarPriceSql.append(" AND indexPrice.low_price <= '" + maxPrice + "' \n");
            }
            if (null != minPrice) {
                indexCarPriceSql.append(" AND indexPrice.low_price >= '" + minPrice + "' \n");
            }
            indexCarPriceSql.append(" GROUP BY indexPrice.car_id\n");
            if (orderField != null) {
                indexCarPriceSql.append(" ORDER BY " + orderField + " " + orderRule + " \n");
            } else {
                indexCarPriceSql.append(" ORDER BY discount DESC  \n");
            }
            List<Mobile_BaseCarPrice> rets = create.fetch(indexCarPriceSql.toString()).into(Mobile_BaseCarPrice.class);
            BaseCount<Mobile_BaseCarPrice> resultData = new BaseCount<>();
            resultData.count = (int) rets.stream().count();
            resultData.rows = rets.stream().skip((page - 1) * pagenum)
                    .limit(pagenum).collect(Collectors.toList());
            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     @param query
     {
        "query":{
             "carId":"7363",
             "interiorColorId":"3111c4a9bf4311e5ba4114dda95160ab",
             "exteriorColorId":"37257"
         }
     }
     * @Description:
     * @Return: 查询出免费看底价车的详情
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/8 15:15
     * @Version 2.0
     */
    @POST
    @Path("findIndexPriceDetails")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findIndexPriceDetails(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String userId = getJsonAsString(json, "userId");
            String carId = getJsonAsString(json, "carId");
            String interiorColorId = getJsonAsString(json, "interiorColorId");
            String exteriorColorId = getJsonAsString(json, "exteriorColorId");

            // 查询出底价车详情
            StringBuilder indexPriceSql = new StringBuilder();
            indexPriceSql.append(" SELECT \n");
            indexPriceSql.append(" indexPrice.brand_id AS brandId, \n");
            indexPriceSql.append(" indexPrice.brand_name AS brandName, \n");
            indexPriceSql.append(" indexPrice.model_id AS carModelId, \n");
            indexPriceSql.append(" indexPrice.model_name AS carModelName, \n");
            indexPriceSql.append(" indexPrice.car_id AS carId, \n");
            indexPriceSql.append(" indexPrice.car_name AS carName, \n");
            indexPriceSql.append(" CASE \n");
            indexPriceSql.append(" WHEN indexPrice.exterior_img IS NULL THEN NULL \n");
            indexPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(indexPrice.exterior_img,\"type\",\"small\")) \n");
            indexPriceSql.append(" END AS carImage, \n");
            indexPriceSql.append(" indexPrice.exterior_color_id AS exteriorColorId, \n");
            indexPriceSql.append(" indexPrice.interior_color_id AS interiorColorId, \n");
            indexPriceSql.append(" indexPrice.city_id AS cityId, \n");
            indexPriceSql.append(" indexPrice.city_name AS cityName, \n");
            indexPriceSql.append(" indexPrice.province_id AS provinceId, \n");
            indexPriceSql.append(" indexPrice.province_name AS provinceName, \n");
            indexPriceSql.append(" indexPrice.auth_price AS authPrice, \n");
            indexPriceSql.append(" indexPrice.price, \n");
            indexPriceSql.append(" indexPrice.discount, \n");
            indexPriceSql.append(" indexPrice.transactions_count AS transactionsCount, \n");
            indexPriceSql.append(" indexPrice.low_price AS lowPrice, \n");
            indexPriceSql.append(" CASE \n");
            indexPriceSql.append(" WHEN attentionCarModel.user_id IS NULL THEN 0  \n");
            indexPriceSql.append(" ELSE 1 \n");
            indexPriceSql.append(" END AS isAttention \n");

            indexPriceSql.append(" FROM gch_view_index_price AS indexPrice \n");
            indexPriceSql.append(" LEFT JOIN ( \n");
            indexPriceSql.append(" SELECT \n");
            indexPriceSql.append(" carModel.user_id, \n");
            indexPriceSql.append(" carModel.car_model_id \n");
            indexPriceSql.append(" FROM \n");
            indexPriceSql.append(" gch_user_attention_car_model AS carModel \n");
            indexPriceSql.append(" GROUP BY carModel.user_id,car_model_id \n");
            indexPriceSql.append(" ) AS attentionCarModel ON indexPrice.model_id = attentionCarModel.car_model_id \n");
            indexPriceSql.append(" AND attentionCarModel.user_id = '" + userId + "' \n");

            indexPriceSql.append(" WHERE indexPrice.car_id = '" + carId + "' \n");
            if (null != interiorColorId) {
                indexPriceSql.append(" AND indexPrice.interior_color_id = '" + interiorColorId + "' \n");
            }

            if (null != exteriorColorId) {
                indexPriceSql.append(" AND indexPrice.exterior_color_id = '" + exteriorColorId + "' \n");
            }

            indexPriceSql.append(" AND indexPrice.isdelete = 0 \n");
            indexPriceSql.append(" GROUP BY indexPrice.id \n");
            indexPriceSql.append(" ORDER BY lowPrice DESC \n");
            indexPriceSql.append(" LIMIT 1 \n");
            return success(create.fetch(indexPriceSql.toString()).into(Mobile_BaseCarPrice.class));
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
