package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchSalesArea;
import com.lrest.server.jooqmodel.tables.records.GchReceiptAddressRecord;
import com.lrest.server.jooqmodel.tables.records.GchSalesAreaRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarTrendRecord;
import com.lrest.server.models.*;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.utils.*;
import com.tencent.common.MD5;
import org.jooq.*;
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
import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
* @DESCRIPTION: 
* @Return: 特价车的类
* @Author: 韩武洽 @Wyshown
* @Date: 2016/9/14-13:47
* @version V2.0  
**/
@Path("/4s/activity")
public class Member4sController_activity extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/activity");
    }

    /**
     * @param query  入参方式如下:
        {
            "query":{
            "userId":"258ce5ebe63161f7f265fbb508696d7b"
            }
        }
    * @DESCRIPTION:
    * @Return: 品牌 车型 车款 三级联动方法
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/9/14-13:47
    * @version V2.0
    **/
    @POST
    @Path("carActivity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carActivity(String query) {

        try(Connection conn = DB.getConnection();
            DSLContext create = DSL.using(conn,SQLDialect.MYSQL)){
            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String user4sId = getJsonAsString(this.jsonObject,"userId");

            // 定义: 品牌车型车款
            Car_BrandModelCar brandModelCar = new Car_BrandModelCar();
            // 查询出车标致的Sql
            StringBuilder sqlBrand = new StringBuilder();
            sqlBrand.append(" SELECT \n");
            sqlBrand.append(" brand.id AS brandId, \n");
            sqlBrand.append(" brand.brand_name AS brandName \n");
            sqlBrand.append(" FROM \n");
            sqlBrand.append(" gch_user_4s AS user4s \n");
            sqlBrand.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id \n");
            sqlBrand.append(" WHERE user4s.id ='" + user4sId + "' \n");
            sqlBrand.append(" AND brand.isdelete = 0 \n");


            sqlBrand.append(" UNION \n\n");

            sqlBrand.append(" SELECT \n");
            sqlBrand.append(" brand.id AS brandId, \n");
            sqlBrand.append(" brand.brand_name AS brandName \n");
            sqlBrand.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            sqlBrand.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id \n");
            sqlBrand.append(" WHERE user4sBrank.user_4s_id ='" + user4sId + "'\n");
            sqlBrand.append(" AND user4sBrank.isdelete = 0 \n");

            // 品牌列表转Map
            brandModelCar.brand = create.fetch(sqlBrand.toString()).into(Car_BrandModelCar.Brand.class);

            // 查询出车型的SQL语句
            StringBuilder carModelSql = new StringBuilder();
            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.brandId AS brandId, \n");
            carModelSql.append(" brand.brandName AS brandName, \n");
            carModelSql.append(" carModel.id AS carModelId, \n");
            carModelSql.append(" carModel.car_model_name AS carModelName \n\n");

            carModelSql.append(" FROM gch_car_model AS carModel \n");
            carModelSql.append(" LEFT JOIN ( \n");
            // 子查询
            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM \n");
            carModelSql.append(" gch_user_4s AS user4s \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id \n");
            carModelSql.append(" WHERE user4s.id ='" + user4sId + "' \n");
            carModelSql.append(" AND brand.isdelete = 0 \n");

            carModelSql.append(" UNION \n\n");

            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id \n");
            carModelSql.append(" WHERE user4sBrank.user_4s_id ='" + user4sId + "' \n");
            carModelSql.append(" AND user4sBrank.isdelete = 0 \n");

            carModelSql.append(" ) AS brand  ON carModel.brand_id = brand.brandId \n");
            carModelSql.append(" WHERE 1 = 1 \n");
            carModelSql.append(" AND brand.brandId IS NOT NULL \n");
            carModelSql.append(" AND carModel.isdelete = 0 \n");

            // 定义 品牌的Result结果集
            Result<Record> resultCarModel = create.fetch(carModelSql.toString());
            // 品牌列表转Map
                brandModelCar.carModel= resultCarModel.
                    stream().
                    collect(
                            groupingBy(
                                    r -> r.getValue("brandId", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_BrandModelCar.CarModel(
                                                    r.getValue("carModelId", String.class)
                                                    ,r.getValue("carModelName", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );

            // 查询出车款的SQL语句
            StringBuilder carSql = new StringBuilder();
            carSql.append(" SELECT \n");
            carSql.append(" brand.brandId AS brandId, \n");
            carSql.append(" brand.brandName AS brandName, \n");
            carSql.append(" carModel.id AS carModelId, \n");
            carSql.append(" carModel.car_model_name AS carModelName, \n");
            carSql.append(" car.id AS carId, \n");
            carSql.append(" car.car_name AS carName \n\n");

            carSql.append(" FROM gch_car AS car \n");
            carSql.append(" LEFT JOIN  gch_car_model AS carModel ON car.car_model_id = carModel.id AND carModel.isdelete = 0 \n");
            carSql.append(" LEFT JOIN ( \n");
            // 子查询
            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM \n");
            carSql.append(" gch_user_4s AS user4s \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id \n");
            carSql.append(" WHERE user4s.id ='" + user4sId + "' \n\n");
            carSql.append(" AND brand.isdelete = 0 \n");
            carSql.append(" UNION \n\n");

            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id \n");
            carSql.append(" WHERE user4sBrank.user_4s_id ='" + user4sId + "'\n");
            carSql.append("  AND user4sBrank.isdelete = 0 \n");
            carSql.append(" ) AS brand ON carModel.brand_id = brand.brandId\n");

            carSql.append(" WHERE 1 = 1 \n");
            carSql.append(" AND carModel.id IS NOT NULL \n");
            carSql.append(" AND car.isdelete = 0 \n");
            carSql.append(" ORDER BY car.car_name DESC \n");

            // 定义 品牌的Result结果集
            Result<Record> resultCar = create.fetch(carSql.toString());
            // 品牌列表转Map
            brandModelCar.car= resultCar.
                    stream().
                    collect(
                            groupingBy(
                                    r ->  r.getValue("carModelId", String.class),
                                    LinkedHashMap::new,
                                    mapping(
                                            r -> new Car_BrandModelCar.Car(
                                                    r.getValue("carId", String.class)
                                                    ,r.getValue("carName", String.class)
                                            ),
                                            toList()
                                    )
                            )
                    );
            return success(brandModelCar);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1,e.getMessage());
        }
    }


   /**
   * @DESCRIPTION: 添加 "新增活动"
   * @param query 传参方式如下:
             {"query":
                  {
                       "user_id":1,"interior_color_id":1,"exterior_color_id":2,
                       "car_image":"/Upload/type/2016-08-08/type_57a8233f2a954.jpg",
                       "car_id":"1","price":"1000","special_price":200,"start_date":"2016-01-01 01:01:10",
                       "end_date":"2017-01-01 01:01:10","number":10,"status":1,"remark":"备注信息 备注(审核不通过）",
                       "description":"活动说明","attention_count":22,"createuser":"12",
                       "areas":[{"sales_area_name":"江苏","sales_area_level":2}]
                  }
             }
   * @Author: 韩武洽 @Wyshown
   * @Date: 2016/9/12-13:43
   * @version V2.0
   **/
    @POST
    @Path("/addCarActivity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCarActivity(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
             create.transaction(configuration -> {
                JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

                GchSpecialPriceCarRecord res = create.newRecord(GCH_SPECIAL_PRICE_CAR);

                String carActivityId = genUUID();
                String userId = getJsonAsString(json,"user_id");
                String interiorColorId =  getJsonAsString(json,"interior_color_id");
                String exteriorColorId =  getJsonAsString(json,"exterior_color_id");
                String carImage = getJsonAsString(json,"car_image");
                String carId = getJsonAsString(json,"car_id");
                int price = getJsonAsInt(json,"price");
                int specialPrice = getJsonAsInt(json,"special_price");
                Timestamp startDate = DateUtils.dateChangeTimestamp(getJsonAsString(json,"start_date"));
                Timestamp endDate = DateUtils.dateChangeTimestamp(getJsonAsString(json,"end_date"));
                int number = getJsonAsInt(json,"number");
                String description = getJsonAsString(json,"description");
                Timestamp currentTimestamp = DateUtils.millisecondChangeTimestamp();

                // 主键
                res.setId(carActivityId);
                // 关联用户ID
                res.setUserId(userId);
                // 内饰颜色ID
                res.setInteriorColorId(interiorColorId);
                // 外观颜色ID
                res.setExteriorColorId(exteriorColorId);
                // 车款图片
                res.setCarImage(carImage);
                // 汽车ID
                res.setCarId(carId);
                // 价格
                res.setPrice(price);

                // 特价
                res.setSpecialPrice(specialPrice);
                // 开始时间
                res.setStartDate(startDate);
                // 结束时间
                res.setEndDate(endDate);

                // 活动数量
                res.setNumber(number);
                // 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
                res.setStatus((byte)1);

                // 备注(审核不通过）
                res.setRemark(getJsonAsString(json,"remark"));
                // 活动说明
                res.setDescription(description);
                // 关注度
                res.setAttentionCount(getJsonAsInt(json,"attention_count"));
                // 创建时间
                res.setCreatetime(currentTimestamp);
                // 创建人
                res.setCreateuser(userId);
                // 修改时间
                res.setUpdatetime(currentTimestamp);

                res.insert();

                 // 添加销售区域
                com.google.gson.JsonObject jsonObject = new JsonObject();
                com.google.gson.JsonArray jsonArray = json.get("areas").getAsJsonArray();
                List<GchSalesAreaRecord> list = new ArrayList<>();
                GchSalesAreaRecord userRe1cord = create.newRecord(GCH_SALES_AREA);

                 for (int i = 0,j = jsonArray.size(); i < j; i++) {
                     jsonObject = jsonArray.get(i).getAsJsonObject();
                     userRe1cord = create.newRecord(GCH_SALES_AREA);
                     userRe1cord.setId(genUUID());
                     userRe1cord.setSalesAreaName(getJsonAsString(jsonObject,"sales_area_name"));
                     userRe1cord.setSalesAreaLevel(getJsonAsInt(jsonObject,"sales_area_level"));

                     userRe1cord.setCarSpecialId(carActivityId);
                     userRe1cord.setType(2);

                     userRe1cord.setCreatetime(currentTimestamp);
                     userRe1cord.setCreateuser(userId);
                     userRe1cord.setUpdatetime(currentTimestamp);
                     userRe1cord.setUpdateuser(userId);
                     userRe1cord.setIsdelete(0);

                     list.add(userRe1cord);
                 }

                 create.batchInsert(list).execute();

                 // 添加历史记录
                 GchSpecialPriceCarTrendRecord trend = create.newRecord(GCH_SPECIAL_PRICE_CAR_TREND);
                 trend.setId(genUUID());
                 trend.setSpecialPriceCar(carActivityId);
                 trend.setUserId(userId);
                 trend.setInteriorColorId(interiorColorId);
                 trend.setExteriorColorId(exteriorColorId);
                 trend.setCarImage(carImage);
                 trend.setCarId(carId);
                 trend.setPrice(price);
                 trend.setSpecialPrice(specialPrice);
                 trend.setStartDate(startDate);
                 trend.setEndDate(endDate);
                 trend.setNumber(number);
                 trend.setStatus((byte)1);
                 trend.setDescription(description);
                 trend.setCreateuser(userId);
                 trend.setCreatetime(currentTimestamp);
                 trend.insert();

             });

            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @DESCRIPTION: 修改 "新增活动"
     * @param query 传参方式如下:
    {"query":
            {
            "id":"111","user_id":1,"interior_color_id":1,"exterior_color_id":2,
            "car_image":"/Upload/type/2016-08-08/type_57a8233f2a954.jpg",
            "car_id":"1","price":"1000","special_price":200,"start_date":"2016-01-01 01:01:10",
            "end_date":"2017-01-01 01:01:10","number":10,
            "description":"活动说明","createuser":"12",
            "areas":[{"sales_area_name":"江苏","sales_area_level":2}]
            }
    }
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/12-13:43
     * @version V2.0
     **/
    @POST
    @Path("/updateCarActivity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCarActivity(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

                // 修改 特价车
                GchSpecialPriceCarRecord res = create.newRecord(GCH_SPECIAL_PRICE_CAR);
                String carActivityId = getJsonAsString(json,"id");
                String userId = getJsonAsString(json,"user_id");
                String interiorColorId =  getJsonAsString(json,"interior_color_id");
                String exteriorColorId =  getJsonAsString(json,"exterior_color_id");

                String carImage = getJsonAsString(json,"car_image");
                if (carImage.startsWith(Config.OSS)) {
                    carImage = carImage.substring(Config.OSS.length());
                }

                String carId = getJsonAsString(json,"car_id");

                int price = getJsonAsInt(json,"price");
                int specialPrice = getJsonAsInt(json,"special_price");
                Timestamp startDate = DateUtils.dateChangeTimestamp(getJsonAsString(json,"start_date"));
                Timestamp endDate = DateUtils.dateChangeTimestamp(getJsonAsString(json,"end_date"));
                int number = getJsonAsInt(json,"number");
                String description = getJsonAsString(json,"description");
                Timestamp currentTimestamp = DateUtils.millisecondChangeTimestamp();

                // 主键
                res.setId(carActivityId);
                // 关联用户ID
                res.setUserId(userId);
                // 内饰颜色ID
                res.setInteriorColorId(interiorColorId);
                // 外观颜色ID
                res.setExteriorColorId(exteriorColorId);
                // 车款图片
                res.setCarImage(carImage);
                // 汽车ID
                res.setCarId(carId);
                // 价格
                res.setPrice(price);

                // 特价
                res.setSpecialPrice(specialPrice);
                // 开始时间
                res.setStartDate(startDate);
                // 结束时间
                res.setEndDate(endDate);

                // 活动数量
                res.setNumber(number);
                // 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
                res.setStatus((byte)1);
                // 活动说明
                res.setDescription(description);
                // 修改时间
                res.setUpdatetime(currentTimestamp);
                // 修改人
                res.setUpdateuser(userId);
                res.update();

                // 删除销售区域
                StringBuilder deleteSaleAreaSql = new StringBuilder();
                deleteSaleAreaSql.append(" UPDATE \n");
                deleteSaleAreaSql.append(" gch_sales_area AS sale \n");
                deleteSaleAreaSql.append(" SET sale.isdelete = 1 \n");
                deleteSaleAreaSql.append(" WHERE sale.car_special_id ='" + carActivityId + "'\n");
                create.execute(deleteSaleAreaSql.toString());

                // 添加销售区域
                com.google.gson.JsonObject jsonObject = new JsonObject();
                List<GchSalesAreaRecord> list = new ArrayList<>();
                GchSalesAreaRecord userRe1cord = create.newRecord(GCH_SALES_AREA);

                com.google.gson.JsonArray jsonArray = json.get("areas").getAsJsonArray();
                // 循环便利 然后把销售区域放到list集合中
                for (int i = 0,j = jsonArray.size(); i < j; i++) {
                    jsonObject = jsonArray.get(i).getAsJsonObject();
                    userRe1cord = create.newRecord(GCH_SALES_AREA);
                    userRe1cord.setId(genUUID());
                    userRe1cord.setSalesAreaName(getJsonAsString(jsonObject,"sales_area_name"));
                    userRe1cord.setSalesAreaLevel(getJsonAsInt(jsonObject,"sales_area_level"));

                    userRe1cord.setCarSpecialId(carActivityId);
                    userRe1cord.setType(2);

                    // 设置时间等 基本格式
                    userRe1cord.setCreatetime(currentTimestamp);
                    userRe1cord.setCreateuser(userId);
                    userRe1cord.setUpdatetime(currentTimestamp);
                    userRe1cord.setIsdelete(0);

                    list.add(userRe1cord);
                }
                create.batchInsert(list).execute();

                // 添加历史记录
                GchSpecialPriceCarTrendRecord trend = create.newRecord(GCH_SPECIAL_PRICE_CAR_TREND);
                trend.setId(genUUID());
                trend.setSpecialPriceCar(carActivityId);
                trend.setUserId(userId);
                trend.setInteriorColorId(interiorColorId);
                trend.setExteriorColorId(exteriorColorId);
                trend.setCarImage(carImage);
                trend.setCarId(carId);
                trend.setPrice(price);
                trend.setSpecialPrice(specialPrice);
                trend.setStartDate(startDate);
                trend.setEndDate(endDate);
                trend.setNumber(number);
                trend.setStatus((byte)1);
                trend.setDescription(description);
                trend.setCreateuser(userId);
                trend.setCreatetime(currentTimestamp);
                trend.insert();

            });

            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
    * @DESCRIPTION:
    * @param query 格式如下:
           {"query":{"pagenum":1,"page":10,"car_id":2071}}
    * @Return: 本店的新增活动的车款的内饰 外饰
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/9/12-17:42
    * @version V2.0
    **/
    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String list(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            // 车款的ID
            String carId = getJsonAsString(json,"car_id");
            String userId = getJsonAsString(json,"user_id");

            // 查询出所有本店新增活动的车款的内饰 外饰(带分页)
            List<Car_special_price_car> rets = create.fetch(
                    activitySql(getJsonAsInt(json,"pagenum"),getJsonAsInt(json,"page"),userId,carId)
            ).into(Car_special_price_car.class);

            // 查询出所有本店新增活动的车款的内饰 外饰的总数
            int count = create.fetch( activitySql(0,0,userId,carId)).size();
            // 封装为一个带count的对象
            BaseCount<Car_special_price_car> resultData =  new BaseCount<>();
            resultData.count = count;
            resultData.rows = rets;

            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
    * @DESCRIPTION:
     * @param page 页数
     * @param pagenum 一页多少条数据
    * @param carId 汽车人的ID
    * @Return: 此方法为查询活动列表的通用方法
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/9/13-10:21
    * @version V2.0
    **/
    public String activitySql(Integer pagenum,Integer page,String userId,String carId){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * \n");
        sql.append(" FROM ( \n");

        sql.append(" SELECT \n");
        sql.append(" brand.id AS brandId, \n");
        sql.append(" brand.brand_name AS brandName, \n");
        sql.append(" carModel.id AS carModelId, \n");
        sql.append(" carModel.car_model_name AS carModelName, \n");
        sql.append(" color.*, \n");
        sql.append(" car.car_name AS carName, \n");
        sql.append(" car.auth_price AS price, \n");
        sql.append(" CONCAT(color.interiorColorId,\",\",color.exteriorColorId) AS mergeColor \n");
        sql.append(" FROM \n");
        sql.append(" gch_car AS car \n");
        sql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
        sql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id  \n");
        sql.append(" LEFT JOIN ( \n");
        sql.append(" SELECT \n");
        sql.append(" interiorColor.id AS interiorColorId, \n");
        sql.append(" interiorColor.color_name AS interiorColorName, \n");
        sql.append(" exteriorColor.id AS exteriorColorId, \n");
        sql.append(" exteriorColor.color_name AS exteriorColorName, \n");
        sql.append(" exteriorColor.car_id AS carId \n");
        sql.append(" FROM gch_view_interior_color AS interiorColor \n");
        sql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON interiorColor.car_id = exteriorColor.car_id \n");
        sql.append(" WHERE interiorColor.car_id = '" + carId + "' \n");
        sql.append(" AND exteriorColor.car_id = '" + carId + "' \n");
        sql.append(" AND interiorColor.isdelete = 0 \n");
        sql.append(" AND exteriorColor.isdelete = 0 \n");

        sql.append(" ) AS color ON color.carId = car.id \n");
        sql.append(" WHERE car.id = '" + carId + "' \n");
        sql.append(" AND car.isdelete = 0 \n");

        sql.append(" ) AS viewColor \n");
        sql.append(" WHERE viewColor.mergeColor NOT IN ( \n");
        sql.append(" SELECT \n");
        sql.append(" CONCAT(specialCar.interior_color_id,\",\",specialCar.exterior_color_id) \n");
        sql.append(" FROM gch_special_price_car AS specialCar\n");
        sql.append(" WHERE specialCar.isdelete = 0 \n");
        sql.append(" AND specialCar.price IS NOT NULL \n");
        sql.append(" AND specialCar.start_date IS NOT NULL \n");
        sql.append("  AND specialCar.user_id ='" + userId + "' \n");
        sql.append(" AND specialCar.car_id ='" + carId + "')\n");

        if (0 != pagenum && 0 != page) {
            sql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
        }
        return  sql.toString();
    }

    /**
     * @DESCRIPTION:
     * @param query 格式如下:
            {"query":{"id":1,"user_id":10,"status":1}}
     * @Return: 修改本店的新增活动车款的状态 如: 停售 在售
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/12-17:42
     * @version V2.0
     **/
    @POST
    @Path("/updateCarActivityStatus")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCarActivityStatus(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

                GchSpecialPriceCarRecord res = create.newRecord(GCH_SPECIAL_PRICE_CAR);

                // 特价车的Id
                String carActivityId = getJsonAsString(json,"id");
                String userId = getJsonAsString(json,"user_id");
                Byte status = getJsonAsByte(json,"status");
                Timestamp currentTimestamp = DateUtils.millisecondChangeTimestamp();

                // 主键
                res.setId(carActivityId);
                // 特价车状态（1：审核中，2：未开始，3：在售，4：停售，5：过期，6：审核失败）
                res.setStatus(status);
                // 修改时间
                res.setUpdatetime(currentTimestamp);
                // 修改人
                res.setUpdateuser(userId);
                res.update();
            });

            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

}
