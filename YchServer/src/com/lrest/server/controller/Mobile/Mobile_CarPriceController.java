package com.lrest.server.controller.Mobile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice;
import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Base_Idname;
import com.lrest.server.models.Car_InteriorExteriorColor;
import com.lrest.server.models.Mobile.*;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import org.jooq.*;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.GCH_PAY;
import static com.lrest.server.jooqmodel.Tables.GCH_PAY_AREA_LOW_PRICE;
import static com.lrest.server.utils.UtilBase.*;
import static com.lrest.server.utils.UtilBase.genUUID;
import static com.lrest.server.utils.UtilBase.getIpAddr;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * DESCRIPTION: Mobile端 底价车
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:14
 **/

@Path("mobile/carPrice")
public class Mobile_CarPriceController extends BaseController{

    /**
     * @Description: 查询出数字对应的 目前在售的品牌
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 14:14
     * @Version 2.0
     */
    @POST
    @Path("findOnSellBrand/letter")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findOnSellBrandLetter() {

        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT \n");
            sql.append(" brand.brandId AS id, \n");
            sql.append(" brand.brandName AS name, \n");
            sql.append(" brand.logo, \n");
            sql.append(" brand.alif \n");
            sql.append(" FROM gch_car_price AS carPrice \n");
            sql.append(" LEFT JOIN gch_car AS car ON car.id = carPrice.car_id \n");
            sql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
            sql.append(" LEFT JOIN  ( \n");
            sql.append(" SELECT \n");
            sql.append(" brand.id AS brandId, \n");
            sql.append(" brand.brand_name AS brandName, \n");
            sql.append(" CASE \n");
            sql.append(" WHEN brand.logo IS NULL THEN NULL \n");
            sql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(brand.logo,\"type\",\"small\")) \n");
            sql.append(" END AS logo, \n");
            sql.append(" brand.alif \n");
            sql.append(" FROM \n");
            sql.append(" gch_user_4s AS user4s \n");
            sql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id AND brand.isdelete = 0 \n");
            sql.append(" WHERE user4s.isdelete = 0 \n");
            sql.append(" UNION \n");
            sql.append(" SELECT \n");
            sql.append(" brand.id AS brandId, \n");
            sql.append(" brand.brand_name AS brandName, \n");
            sql.append(" CASE \n");
            sql.append(" WHEN brand.logo IS NULL THEN NULL \n");
            sql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(brand.logo,\"type\",\"small\")) \n");
            sql.append(" END AS logo, \n");
            sql.append(" brand.alif \n");
            sql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            sql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0 \n");
            sql.append(" WHERE user4sBrank.isdelete = 0  \n");
            sql.append("  ) AS brand ON carModel.brand_id = brand.brandId \n");
            sql.append(" WHERE brand.brandId IS NOT NULL \n");
            sql.append(" GROUP BY carModel.brand_id \n");
            sql.append(" ORDER BY alif,name \n");

            Result<Record> result = create.fetch(sql.toString());

            Map<String, List<String[]>> rets = result.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("alif", String.class).toUpperCase(),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> {
                                                String[] s = {
                                                        r.getValue("id", String.class),
                                                        r.getValue("name", String.class),
                                                        r.getValue("logo", String.class)
                                                };
                                                return s;
                                            },
                                            toList()
                                    )
                            )
                    );
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }



    /**
     * @param query {"query":{"brandId":"74"}}
     * @Description: 查询出所有在售品牌下的车型
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 15:59
     * @Version 2.0
     */
    @POST
    @Path("findOnSellCarModelByBracnd")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findOnSellCarModelByBracnd(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String brandId = getJsonAsString(json, "brandId");
            // 查询出在售车型的SQL语句
            StringBuilder onSellCarModelSql = new StringBuilder();
            onSellCarModelSql.append(" SELECT \n");
            onSellCarModelSql.append(" DISTINCT (carModel.id) AS carModelId, \n");
            onSellCarModelSql.append(" carModel.car_model_name AS carModelName, \n");
            onSellCarModelSql.append(" brand.brandId, \n");
            onSellCarModelSql.append(" brand.brandName, \n");
            onSellCarModelSql.append(" CASE \n");
            onSellCarModelSql.append(" WHEN carModel.imgurl IS NULL THEN NULL \n");
            onSellCarModelSql.append(" ELSE CONCAT('" + Config.OSS +"',REPLACE(carModel.imgurl ,\"type\",\"small\")) \n");
            onSellCarModelSql.append("  END AS carModelImageUrl, \n");
            onSellCarModelSql.append(" minMaxCarPrice.minCarPrice, \n");
            onSellCarModelSql.append(" minMaxCarPrice.maxCarPrice \n");

            onSellCarModelSql.append(" FROM gch_car_price AS carPrice \n");
            onSellCarModelSql.append(" LEFT JOIN gch_car AS car ON car.id = carPrice.car_id \n");
            onSellCarModelSql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
            onSellCarModelSql.append(" LEFT JOIN  ( \n");
            onSellCarModelSql.append(" SELECT \n");
            onSellCarModelSql.append(" brand.id AS brandId, \n");
            onSellCarModelSql.append(" brand.brand_name AS brandName \n");
            onSellCarModelSql.append(" FROM \n");
            onSellCarModelSql.append(" gch_user_4s AS user4s \n");
            onSellCarModelSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id AND brand.isdelete = 0 \n");
            onSellCarModelSql.append(" WHERE user4s.isdelete = 0 \n");
            onSellCarModelSql.append(" UNION \n");
            onSellCarModelSql.append(" SELECT \n");
            onSellCarModelSql.append(" brand.id AS brandId, \n");
            onSellCarModelSql.append(" brand.brand_name AS brandName \n");
            onSellCarModelSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            onSellCarModelSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0 \n");
            onSellCarModelSql.append(" WHERE user4sBrank.isdelete = 0  \n");
            onSellCarModelSql.append("  ) AS brand ON carModel.brand_id = brand.brandId \n");
            onSellCarModelSql.append(" LEFT JOIN ( \n");
            onSellCarModelSql.append(" SELECT \n");
            onSellCarModelSql.append(" car.car_model_id, \n");
            onSellCarModelSql.append(" MIN(carPrice.low_price) AS minCarPrice, \n");
            onSellCarModelSql.append(" Max(carPrice.low_price) AS maxCarPrice \n");
            onSellCarModelSql.append(" FROM  gch_car_price AS carPrice \n");
            onSellCarModelSql.append(" LEFT JOIN gch_car AS car ON car.id = carPrice.car_id \n");
            onSellCarModelSql.append(" WHERE carPrice.isdelete = 0 \n");
            onSellCarModelSql.append(" AND car.car_model_id  IS NOT NULL \n");
            onSellCarModelSql.append(" GROUP BY car.car_model_id  \n");
            onSellCarModelSql.append(" ) AS minMaxCarPrice ON minMaxCarPrice.car_model_id = carModel.id \n");
            onSellCarModelSql.append(" WHERE carPrice.isdelete = 0 \n");
            onSellCarModelSql.append(" AND carModel.brand_id = '" + brandId + "' \n");

            List<Mobile_OnSellCarModel> rets = create.fetch(onSellCarModelSql.toString()).into(Mobile_OnSellCarModel.class);

            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }




    /**
     * @param query {"query":{"brandId":""74}}
     * @Description: 查询出所有在售品牌下的车款
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 15:59
     * @Version 2.0
     */
    @POST
    @Path("findOnSellCarByCarModelId")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findOnSellCarByCarModelId(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carModelId = getJsonAsString(json, "carModelId");


            // 查询出在售车型的SQL语句
            StringBuilder indexCarsSql = new StringBuilder();
            indexCarsSql.append(" SELECT \n");
            indexCarsSql.append(" CONCAT(\"'\",REPLACE(GROUP_CONCAT(carId),\",\",\"','\"),\"'\")  AS id \n");
            indexCarsSql.append(" FROM ( \n");
            indexCarsSql.append(" SELECT \n");
            indexCarsSql.append(" indexCarPrice.car_id AS carId \n");
            indexCarsSql.append(" FROM gch_view_index_car_price  AS indexCarPrice \n");
            indexCarsSql.append(" WHERE indexCarPrice.car_model_id = '" + carModelId + "' \n");
            indexCarsSql.append(" GROUP BY indexCarPrice.car_id \n");
            indexCarsSql.append(" ) AS indexCarPrice \n");

            String indexCars = (String) create.fetchOne(indexCarsSql.toString()).get("id");

            // 查询出在售车款的SQL语句
            StringBuilder onSellCarSql1 = new StringBuilder();
            // 查询出不需要询价的车款
            onSellCarSql1.append(" SELECT \n");
            onSellCarSql1.append(" car.id AS carId, \n");
            onSellCarSql1.append(" \"1\" AS isXunjia, \n");
            onSellCarSql1.append(" car.auth_price AS authPrice, \n");
            onSellCarSql1.append(" car.car_name AS carName, \n");
            onSellCarSql1.append(" CASE \n");
            onSellCarSql1.append(" WHEN exteriorColorImage.imgurl IS NULL THEN NULL \n");
            onSellCarSql1.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(exteriorColorImage.imgurl ,\"type\",\"small\")) \n");
            onSellCarSql1.append(" END AS carImage, \n");
            onSellCarSql1.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            onSellCarSql1.append(" carPrice.interior_color_id AS interiorColorId \n");

            onSellCarSql1.append(" FROM \n");
            onSellCarSql1.append(" gch_car_price AS carPrice \n");
            onSellCarSql1.append(" LEFT JOIN gch_car AS car ON carPrice.car_id = car.id \n");
            onSellCarSql1.append(" LEFT JOIN gch_car_exterior_color_image AS exteriorColorImage ON car.id = exteriorColorImage.car_id \n");
            onSellCarSql1.append(" WHERE 1 = 1 \n");
            onSellCarSql1.append(" AND car.car_model_id = '" + carModelId + "' \n");
            if (null == indexCars) {
                onSellCarSql1.append(" AND carPrice.car_id NOT IN ('') \n");
            } else {
                onSellCarSql1.append(" AND carPrice.car_id NOT IN ("+  indexCars +") \n");
            }
            onSellCarSql1.append("  GROUP BY carPrice.car_id \n");

            onSellCarSql1.append(" UNION \n");

            // 查询出需要询价的车款
            onSellCarSql1.append(" SELECT \n");
            onSellCarSql1.append(" car.id AS carId, \n");
            onSellCarSql1.append(" \"2\" AS isXunjia, \n");
            onSellCarSql1.append(" car.auth_price AS authPrice, \n");
            onSellCarSql1.append(" car.car_name AS carName, \n");
            onSellCarSql1.append(" CASE \n");
            onSellCarSql1.append(" WHEN exteriorColorImage.imgurl IS NULL THEN NULL \n");
            onSellCarSql1.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(exteriorColorImage.imgurl ,\"type\",\"small\")) \n");
            onSellCarSql1.append(" END AS carImage, \n");
            onSellCarSql1.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            onSellCarSql1.append(" carPrice.interior_color_id AS interiorColorId \n");

            onSellCarSql1.append(" FROM \n");
            onSellCarSql1.append(" gch_car_price AS carPrice \n");
            onSellCarSql1.append(" LEFT JOIN gch_car AS car ON carPrice.car_id = car.id \n");
            onSellCarSql1.append(" LEFT JOIN gch_car_exterior_color_image AS exteriorColorImage ON car.id = exteriorColorImage.car_id \n");
            onSellCarSql1.append(" WHERE 1 = 1 \n");
            onSellCarSql1.append(" AND car.car_model_id = '" + carModelId + "' \n");
            if (null == indexCars) {
                onSellCarSql1.append(" AND carPrice.car_id IN ('') \n");
            } else {
                onSellCarSql1.append(" AND carPrice.car_id IN ("+  indexCars +") \n");
            }
            onSellCarSql1.append("  GROUP BY carPrice.car_id \n");
            List<Mobile_OnSellCar> rets = create.fetch(onSellCarSql1.toString()).into(Mobile_OnSellCar.class);
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
    /**
     * @param query  {"query":{"pagenum":10,"page":1}}
     * @Description:
     * @Return:  找到底价车
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/31 14:05
     * @Version 2.0
     */
    @POST
    @Path("findCarPriceList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarPriceList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");

            // 查询出车型的SQL语句
            StringBuilder carPriceSql = new StringBuilder();
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" car.brand_id AS brandId, \n");
            carPriceSql.append(" car.brand_name AS brandName, \n");
            carPriceSql.append(" car.car_model_id AS carModelId, \n");
            carPriceSql.append(" car.car_model_name AS carModelName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN car.car_model_imageurl IS NULL THEN NULL \n");
            carPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(car.car_model_imageurl,\"type\",\"small\")) \n");
            carPriceSql.append(" END AS carModelImageUrl, \n");

            carPriceSql.append(" carPrice.price, \n");
            carPriceSql.append(" carPrice.low_price AS lowPrice, \n");
            carPriceSql.append(" carPrice.discount \n");

            carPriceSql.append(" FROM gch_car_price AS carPrice \n");
            carPriceSql.append(" LEFT JOIN gch_view_car AS car ON carPrice.car_id = car.id \n");
            carPriceSql.append(" WHERE car.car_model_id IS NOT NULL \n");
            carPriceSql.append(" AND carPrice.isdelete = 0 \n");
            carPriceSql.append(" GROUP BY car.car_model_id \n");
            carPriceSql.append(" ORDER BY carPrice.updatetime desc \n");

            List<Mobile_CarPrice> rets = create.fetch(carPriceSql.toString()).into(Mobile_CarPrice.class);
            BaseCount<Mobile_CarPrice> resultData =  new BaseCount<>();
            resultData.count = (int)rets.stream().count();
            resultData.rows = rets.stream().skip((page - 1) * pagenum)
                    .limit(pagenum).collect(Collectors.toList());
            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param query
    {
        "query":
            {
                "carId":"6247",
                "interiorColorId":"4e16e330781e69843cec0ca9b6450b61",
                "exteriorColorId":"14839"
            }
    }
     * @Description: 查询出低价车详情
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/3 14:16
     * @Version 2.0
     */
    @POST
    @Path("findCarPriceDetails")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarPriceDetails(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String userId = getJsonAsString(json, "userId");
            String carId = getJsonAsString(json, "carId");
            String interiorColorId = getJsonAsString(json, "interiorColorId");
            String exteriorColorId = getJsonAsString(json, "exteriorColorId");
            // 查询出车型的SQL语句
            StringBuilder carPriceSql = new StringBuilder();
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" carPrice.id, \n");
            carPriceSql.append(" carPrice.car_model_id AS carModelId, \n");
            carPriceSql.append(" carPrice.car_model_name AS carModelName, \n");
            carPriceSql.append(" carPrice.car_id AS carId, \n");
            carPriceSql.append(" carPrice.car_name AS carName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN carPrice.exterior_img IS NULL THEN NULL \n");
            carPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(carPrice.exterior_img,\"type\",\"small\")) \n");
            carPriceSql.append(" END AS carImage, \n");
            carPriceSql.append(" carPrice.auth_price AS authPrice, \n");
            carPriceSql.append(" carPrice.asking_price_count AS askingPriceCount, \n");
            carPriceSql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            carPriceSql.append(" carPrice.interior_color_id AS interiorColorId, \n");
            carPriceSql.append(" carPrice.is_xunjia AS isXunjia, \n");

            carPriceSql.append(" carPrice.city_id AS cityId, \n");
            carPriceSql.append(" carPrice.city_name AS cityName, \n");
            carPriceSql.append(" carPrice.province_id AS provinceId, \n");
            carPriceSql.append(" carPrice.province_name AS provinceName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN attentionCarModel.user_id IS NULL THEN 0 \n");
            carPriceSql.append(" ELSE 1 \n");
            carPriceSql.append(" END AS isAttention \n");

            carPriceSql.append(" FROM gch_view_car_price AS carPrice \n");
            carPriceSql.append(" LEFT JOIN ( \n");
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" carModel.user_id,\n");
            carPriceSql.append(" carModel.car_model_id \n");
            carPriceSql.append(" FROM  \n");
            carPriceSql.append(" gch_user_attention_car_model AS carModel \n");
            carPriceSql.append(" GROUP BY carModel.user_id,car_model_id  \n");
            carPriceSql.append(" ) AS attentionCarModel ON carPrice.car_model_id = attentionCarModel.car_model_id  \n");
            carPriceSql.append(" AND attentionCarModel.user_id = '" + userId + "'  \n");

            carPriceSql.append(" WHERE 1 = 1 \n");
            carPriceSql.append(" AND carPrice.car_id = '" + carId + "' \n");

            if (null != interiorColorId) {
                carPriceSql.append(" AND carPrice.interior_color_id = '" + interiorColorId + "' \n");
            }

            if (null != exteriorColorId) {
                carPriceSql.append(" AND carPrice.exterior_color_id = '" +  exteriorColorId + "' \n");
            }

            carPriceSql.append(" ORDER BY authPrice ASC \n");
            carPriceSql.append(" LIMIT 1 \n");
            return success(create.fetch(carPriceSql.toString()).into(Mobile_CarPriceDetail.class));
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param query
     orderRule有两种规则 降序:"DESC" 升序:"ASC" orderField有两个字段: xjCount(询价量)和authPrice(官方指导价) 默认以官方指导价升序排列
       {
            "query":{
                "pagenum":"10",
                "page":"1",
                "brandId":"52",
                "carModelId":"223",
                "carTypeId":"0ff0c498bf3d11e5ba4114dda95160ab",
                "maxPrice":"50000000",
                "minPrice":"0",
                "orderField":"xjCount",
                "orderRule":"DESC"
            }
       }
     * @Description:  找车-条件选车
     * @Return: 查询出找车-条件选车需要的结果集
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
            String carModelId = getJsonAsString(json, "carModelId");
            String carTypeId = getJsonAsString(json, "carTypeId");
            String maxPrice = getJsonAsString(json, "maxPrice");
            String minPrice = getJsonAsString(json, "minPrice");
            String orderField = getJsonAsString(json, "orderField");
            String orderRule = getJsonAsString(json, "orderRule");

            // 查询出车型的SQL语句
            StringBuilder carPriceSql = new StringBuilder();
            carPriceSql.append(" SELECT \n");
            // 品牌
            carPriceSql.append(" carPrice.brand_id AS brandId, \n");
            carPriceSql.append(" carPrice.brand_name AS brandName, \n");
            // 车型
            carPriceSql.append(" carPrice.car_model_id AS carModelId, \n");
            carPriceSql.append(" carPrice.car_model_name AS carModelName, \n");
            carPriceSql.append(" carPrice.car_model_xj_count AS xjCount, \n");

            carPriceSql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            carPriceSql.append(" carPrice.interior_color_id AS interiorColorId, \n");
            // 车款
            carPriceSql.append(" carPrice.car_id as carId, \n");
            carPriceSql.append(" carPrice.car_name AS carName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN carPrice.exterior_img IS NULL THEN NULL \n");
            carPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(carPrice.exterior_img ,\"type\",\"small\")) \n");
            carPriceSql.append(" END AS carImage, \n");
            // 指导价格
            carPriceSql.append(" carPrice.auth_price AS authPrice, \n");
            // 汽车类型
            carPriceSql.append(" carPrice.car_type_id AS carTypeId, \n");
            carPriceSql.append(" carPrice.car_type_name AS carTypeName, \n");
            // 价格和价格区间
            carPriceSql.append(" carPrice.price, \n");

            carPriceSql.append(" minCarPrice.minCarPrice AS minPrice, \n");
            carPriceSql.append(" minCarPrice.maxCarPrice AS maxPrice \n");
            carPriceSql.append(" FROM gch_view_car_price AS carPrice \n");
            carPriceSql.append(" LEFT JOIN ( \n");
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" car.car_model_id, \n");
            carPriceSql.append(" MIN(carPrice.low_price) AS minCarPrice, \n");
            carPriceSql.append(" Max(carPrice.low_price) AS maxCarPrice \n");
            carPriceSql.append(" FROM  gch_car_price AS carPrice \n");
            carPriceSql.append(" LEFT JOIN gch_car AS car ON car.id = carPrice.car_id \n");
            carPriceSql.append(" WHERE carPrice.isdelete = 0 \n");
            carPriceSql.append(" AND car.car_model_id  IS NOT NULL \n");
            carPriceSql.append(" GROUP BY car.car_model_id \n");
            carPriceSql.append(" ) AS minCarPrice ON carPrice.car_model_id = minCarPrice.car_model_id \n");
            carPriceSql.append(" WHERE 1 = 1 \n");

            if (null != brandId) {
                carPriceSql.append(" AND carPrice.brand_id = '" + brandId + "' \n");
            }
            if (null != carModelId) {
                carPriceSql.append(" AND carPrice.car_model_id = '" + carModelId + "' \n");
            }
            if (null != carTypeId) {
                carPriceSql.append(" AND carPrice.car_type_id = '" + carTypeId + "' \n");
            }

            if (null != maxPrice && null != minPrice) {
                carPriceSql.append(" AND ( \n");
                carPriceSql.append(" (minCarPrice.minCarPrice <= " + minPrice + " AND minCarPrice.maxCarPrice >=" + maxPrice + ") \n");
                carPriceSql.append(" OR (minCarPrice.minCarPrice >= " + minPrice + " AND minCarPrice.maxCarPrice >=" + maxPrice + ") \n");
                carPriceSql.append(" OR (minCarPrice.minCarPrice <=" + minPrice + " AND minCarPrice.maxCarPrice <=" + maxPrice + ") \n");
                carPriceSql.append(" ) \n");
            }
            carPriceSql.append(" GROUP BY carPrice.car_id \n");

            if (orderField != null) {
                carPriceSql.append(" ORDER BY " + orderField + " " + orderRule + " \n");
            } else {
                carPriceSql.append(" ORDER BY authPrice ASC \n");
            }
            List<Mobile_BaseCarPrice> rets = create.fetch(carPriceSql.toString()).into(Mobile_BaseCarPrice.class);
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
     * @param query 查询条件如下:
               {"query":{"carId":"6247"}}
     * @Description: 查询出车款在售的内饰和外观颜色
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/9 14:38
     * @Version 2.0
     */
    @POST
    @Path("findMyCarColorByCarId")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findMyCarColorByCarId(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carId = getJsonAsString(this.jsonObject,"carId");

            Car_InteriorExteriorColor interiorExteriorColor = new Car_InteriorExteriorColor();
            // 外观接口
            StringBuilder exteriorColorSql = new StringBuilder();
            exteriorColorSql.append(" SELECT \n");
            exteriorColorSql.append(" carPrice.exterior_color_id AS id, \n");
            exteriorColorSql.append(" carPrice.exterior_color_name AS colorName, \n");
            exteriorColorSql.append(" carPrice.exterior_color_value AS colorValue \n");

            exteriorColorSql.append(" FROM gch_view_car_price AS carPrice \n");
            exteriorColorSql.append(" WHERE carPrice.isdelete = 0 \n");
            exteriorColorSql.append(" AND carPrice.car_id = '" + carId + "' \n");
            exteriorColorSql.append(" GROUP BY carPrice.exterior_color_id \n");
            exteriorColorSql.append(" ORDER BY carPrice.exterior_color_name DESC \n");

            interiorExteriorColor.exteriorColorList = create.fetch(exteriorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);
            // 内饰接口
            StringBuilder interiorColorSql = new StringBuilder();
            interiorColorSql.append(" SELECT \n");
            interiorColorSql.append(" carPrice.interior_color_id AS id, \n");
            interiorColorSql.append(" carPrice.interior_color_name AS colorName, \n");
            interiorColorSql.append(" carPrice.interior_color_value AS colorValue \n");
            interiorColorSql.append(" FROM gch_view_car_price AS carPrice \n");
            interiorColorSql.append(" WHERE carPrice.isdelete = 0 \n");
            interiorColorSql.append(" AND carPrice.car_id = '" + carId + "' \n");
            interiorColorSql.append(" GROUP BY carPrice.interior_color_id \n");
            interiorColorSql.append(" ORDER BY carPrice.interior_color_name DESC \n");

            interiorExteriorColor.interiorColorList = create.fetch(interiorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);

            return success(interiorExteriorColor);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }



    /**
     * @param query 查询条件如下:
     {
    "query":{
    "name":"6247",
    "carTypeId":"6247",
    "brandAlif":"6247",
    "minAuthPrice":"6247",
    "maxAuthPrice":"11111111"

    }
    }
     * @Description: 首页搜索: 根据搜索品牌名,车型,车款,价格,汽车类型,首页字母搜索
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/9 14:38
     * @Version 2.0
     */
    @POST
    @Path("searchBrandCarModelCar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String searchBrandCarModelCar(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String name = getJsonAsString(this.jsonObject,"name");
            String carTypeId = getJsonAsString(this.jsonObject,"carTypeId");
            String brandAlif = getJsonAsString(this.jsonObject,"brandAlif");
            String minAuthPrice = getJsonAsString(this.jsonObject,"minAuthPrice");
            String maxAuthPrice = getJsonAsString(this.jsonObject,"maxAuthPrice");



            Map<String,Object> map = new HashMap<>();


            StringBuffer carPriceSql = new StringBuffer();
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" carPrice.id AS carPriceId, \n");
            carPriceSql.append(" carPrice.brand_id AS brandId, \n");
            carPriceSql.append(" carPrice.brand_name AS brandName, \n");
            carPriceSql.append(" carPrice.car_model_id AS carModelId, \n");
            carPriceSql.append(" carPrice.car_model_name AS carModelName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN carPrice.car_model_imageurl IS NULL THEN NULL \n");
            carPriceSql.append(" ELSE CONCAT('\" + Config.OSS + \"',REPLACE(carPrice.car_model_imageurl,\"type\",\"small\")) \n");
            carPriceSql.append(" END AS carModelImage, \n");

            carPriceSql.append(" carPrice.car_id AS carId, \n");
            carPriceSql.append(" carPrice.car_name AS carName, \n");
            carPriceSql.append(" carPrice.interior_color_id AS interiorColorId, \n");
            carPriceSql.append(" carPrice.interior_color_name AS interiorColorName, \n");
            carPriceSql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            carPriceSql.append(" carPrice.exterior_color_name AS exteriorColorName, \n");
            carPriceSql.append(" carPrice.price AS price, \n");
            carPriceSql.append(" carPrice.discount AS discount, \n");
            carPriceSql.append(" carPrice.low_price AS lowPrice, \n");
            carPriceSql.append(" carPrice.auth_price AS authPrice \n");

            carPriceSql.append(" FROM gch_view_car_price AS carPrice \n");
            carPriceSql.append(" WHERE 1 = 1 \n");

            if (null != name) {
                carPriceSql.append(" AND (carPrice.brand_name LIKE '%" + name + "%' \n");
                carPriceSql.append(" OR carPrice.car_model_name LIKE '%" + name + "%' \n");
                carPriceSql.append(" OR carPrice.car_name LIKE '%" + name + "%') \n");
            }

            if (null != carTypeId) {
                carPriceSql.append(" AND carPrice.car_type_id = '' \n");
            }

            if (null != brandAlif) {
                carPriceSql.append(" AND carPrice.brand_alif ='" + brandAlif + "' \n");
            }

            if (null != minAuthPrice && null != maxAuthPrice) {
                carPriceSql.append(" AND carPrice.auth_price >=" + minAuthPrice + " \n");
                carPriceSql.append(" AND carPrice.auth_price <=" + maxAuthPrice + " \n");
            }

            carPriceSql.append(" AND carPrice.isdelete = 0 \n");
            carPriceSql.append(" GROUP BY carPrice.car_model_id \n");

            map.put("carPrice",create.fetch(carPriceSql.toString()).into(Mobile_BaseCarPrice.class));



            StringBuffer indexCarPriceSql = new StringBuffer();
            indexCarPriceSql.append(" SELECT \n");
            indexCarPriceSql.append(" indexCarPrice.id AS indexCarPriceId, \n");
            indexCarPriceSql.append(" indexCarPrice.brand_id AS brandId, \n");
            indexCarPriceSql.append(" indexCarPrice.brand_name AS brandName, \n");
            indexCarPriceSql.append(" indexCarPrice.car_model_id AS carModelId, \n");
            indexCarPriceSql.append(" indexCarPrice.car_model_name AS carModelName, \n");
            indexCarPriceSql.append(" indexCarPrice.car_id AS carId, \n");
            indexCarPriceSql.append(" indexCarPrice.car_name AS carName, \n");
            indexCarPriceSql.append(" indexCarPrice.interior_color_id AS interiorColorId, \n");
            indexCarPriceSql.append(" indexCarPrice.interior_color_name AS interiorColorName, \n");
            indexCarPriceSql.append(" indexCarPrice.exterior_color_id AS exteriorColorId, \n");
            indexCarPriceSql.append(" indexCarPrice.exterior_color_name AS exteriorColorName, \n");
            indexCarPriceSql.append(" carModel.car_type_id, \n");
            indexCarPriceSql.append(" carPrice.price AS price, \n");
            indexCarPriceSql.append(" carPrice.discount AS discount, \n");
            indexCarPriceSql.append(" carPrice.low_price AS lowPrice, \n");
            indexCarPriceSql.append(" car.auth_price AS authPrice \n");

            indexCarPriceSql.append(" FROM gch_view_index_car_price AS indexCarPrice \n");
            indexCarPriceSql.append(" LEFT JOIN gch_car_model AS carModel ON indexCarPrice.car_model_id = carModel.id \n");
            indexCarPriceSql.append(" LEFT JOIN gch_car_price AS carPrice ON carPrice.id = indexCarPrice.id \n");
            indexCarPriceSql.append(" LEFT JOIN gch_car AS car ON indexCarPrice.car_id = car.id \n");
            indexCarPriceSql.append(" WHERE 1 = 1 \n");

            if (null != name) {
                indexCarPriceSql.append(" AND (indexCarPrice.brand_name LIKE '%" + name + "%' \n");
                indexCarPriceSql.append(" OR indexCarPrice.car_model_name LIKE '%" + name + "%' \n");
                indexCarPriceSql.append(" OR indexCarPrice.car_name LIKE '%" + name + "%') \n");
            }

            if (null != carTypeId) {
                indexCarPriceSql.append(" AND carModel.car_type_id = '" + carTypeId + "' \n");
            }

            if (null != brandAlif) {
                indexCarPriceSql.append(" AND indexCarPrice.brand_alif ='" + brandAlif + "' \n");
            }

            if (null != minAuthPrice && null != maxAuthPrice) {
                indexCarPriceSql.append(" AND carPrice.auth_price >=" + minAuthPrice + " \n");
                indexCarPriceSql.append(" AND carPrice.auth_price <=" +  maxAuthPrice + " \n");
            }
            indexCarPriceSql.append(" AND indexCarPrice.isdelete = 0 \n");
            indexCarPriceSql.append(" GROUP BY indexCarPrice.car_model_id \n");

            map.put("indexCarPrice",create.fetch(carPriceSql.toString()).into(Mobile_BaseCarPrice.class));
            return success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param query
        {
            "query":{
                "carId":"6247",
                "interiorColorId":"6247",
                "exteriorColorId":"6247"
            }
        }
    * @Description:
    * @Return:
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/12/2 13:40
    * @Version 2.0
    */
    @POST
    @Path("findCarPriceEnter")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarPriceEnter(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carId = getJsonAsString(json, "carId");
            String interiorColorId = getJsonAsString(json, "interiorColorId");
            String exteriorColorId = getJsonAsString(json, "exteriorColorId");
            // 查询出车型的SQL语句
            StringBuilder carPriceSql = new StringBuilder();
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" carPrice.id AS carPriceId, \n");
            carPriceSql.append(" carPrice.car_id AS carId, \n");
            carPriceSql.append(" carPrice.interior_color_id AS interiorColorId, \n");
            carPriceSql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            carPriceSql.append(" carPrice.is_xunjia AS isXunjia \n");
            carPriceSql.append(" FROM gch_car_price AS carPrice \n");
            carPriceSql.append(" WHERE 1 = 1 \n");
            carPriceSql.append(" AND carPrice.isdelete = 0 \n");
            carPriceSql.append(" AND carPrice.car_id = '" + carId + "' \n");

            if (null != interiorColorId) {
                carPriceSql.append(" AND carPrice.interior_color_id = '" + interiorColorId + "' \n");
            }

            if (null != exteriorColorId) {
                carPriceSql.append(" AND carPrice.exterior_color_id = '" +  exteriorColorId + "' \n");
            }
            carPriceSql.append(" GROUP BY carPrice.car_id \n");
            carPriceSql.append(" ORDER BY carPrice.createtime DESC \n");
            carPriceSql.append(" LIMIT 1 \n");
            return success(create.fetch(carPriceSql.toString()).into(Mobile_CarPriceDetail.class));
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
