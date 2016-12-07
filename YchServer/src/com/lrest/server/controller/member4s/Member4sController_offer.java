package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.find
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchCarPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchSalesAreaRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Base_Idname;
import com.lrest.server.models.Car_offer;
import com.lrest.server.models.Car_special_price_car;
import com.lrest.server.services.DB;
import com.lrest.server.utils.DateUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.GCH_CAR_PRICE;
import static com.lrest.server.jooqmodel.Tables.GCH_SALES_AREA;
import static com.lrest.server.utils.UtilBase.*;

/**
 * @version V2.0
 * @DESCRIPTION:
 * @Return: 报价管理的类
 * @Author: 韩武洽 @Wyshown
 * @Date: 2016/9/14-13:47
 **/
@Path("/4s/offer")
public class Member4sController_offer extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/offer");
    }

    /**
     * @param query 格式如下:
    {
    "query":{
    "pagenum":1,"page":10,
    "carId":2071,"userId":"adf","brandId":"111",
    "carModelId":"111","interiorColorId":"111","exteriorColorId":"222"
    }
    }
     * @DESCRIPTION:
     * @Return: 本店的我的报价的车款的内饰 外饰
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/12-17:42
     * @version V2.0
     **/
    @POST
    @Path("findMyOfferlist")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findMyOfferlist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            // 车款的ID
            String carId = getJsonAsString(json, "carId");
            String userId = getJsonAsString(json, "userId");
            String brandId = getJsonAsString(json,"brandId");
            String carModelId = getJsonAsString(json,"carModelId");
            String interiorColorId = getJsonAsString(json,"interiorColorId");
            String exteriorColorId = getJsonAsString(json,"exteriorColorId");

            // 查询出所有我的报价的车款的内饰 外饰(带分页)
            List<Car_offer> rets = create.fetch(
                    priceSql(getJsonAsInt(json, "pagenum"), getJsonAsInt(json, "page"), userId, carId,
                            brandId,carModelId,interiorColorId,exteriorColorId)
            ).into(Car_offer.class);

            for (Car_offer list : rets) {
                Result<Record1<String>> result_area = create.select(GCH_SALES_AREA.SALES_AREA_NAME)
                        .from(GCH_SALES_AREA)
                        .where(GCH_SALES_AREA.ISDELETE.eq(0).and(GCH_SALES_AREA.CAR_PRICE_ID.eq(list.carPriceId)))
                        .fetch();
                List params = new ArrayList();
                for (Record re : result_area) {
                    params.add(re.get(GCH_SALES_AREA.SALES_AREA_NAME));
                }
                list.saleArea = String.valueOf(params);
            }
            // 查询出所有我的报价的车款的内饰 外饰的总数
            int count = create.fetch(priceSql(0, 0, userId, carId,brandId,carModelId,interiorColorId,exteriorColorId)).size();
            // 封装为一个带count的对象
            BaseCount<Car_offer> resultData = new BaseCount<>();
            resultData.count = count;
            resultData.rows = rets;
            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
     * @Description:
     * @Return: 本店活动通用Sql
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016-09-21 13:18
     * @Version 2.0
     */
    public String priceSql(Integer pagenum, Integer page, String userId, String carId,
                           String brandId,String carModelId,String interiorColorId,String exteriorColorId) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \n");
        sql.append(" car.brand_id AS brandId, \n");
        sql.append(" car.brand_name AS brandName, \n");
        sql.append(" car.car_model_id AS carModelId, \n");
        sql.append(" car.car_model_name AS carModelName, \n");
        sql.append(" car.id AS carId, \n");
        sql.append(" car.car_name AS carName, \n");
        sql.append(" carPrice.interior_color_id AS interiorColorId, \n");
        sql.append(" interiorColor.color_name AS interiorColorName, \n");
        sql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
        sql.append(" exteriorColor.color_name AS exteriorColorName, \n");
        sql.append(" carPrice.price, \n");
        sql.append(" carPrice.discount, \n");
        sql.append(" carPrice.low_price AS lowPrice, \n");
        sql.append(" carPrice.stock, \n");
        sql.append(" carPrice.onWay, \n");
        sql.append(" carPrice.id AS carPriceId  \n");

        sql.append(" FROM gch_car_price AS carPrice \n");
        sql.append(" LEFT JOIN gch_view_interior_color AS interiorColor ON carPrice.interior_color_id = interiorColor.id \n");
        sql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON carPrice.exterior_color_id = exteriorColor.id \n");
        sql.append(" LEFT JOIN gch_view_car AS car ON carPrice.car_id = car.id \n");

        sql.append(" WHERE carPrice.user_id = '" + userId + "' \n");
        if (!"null".equals(carId) && !"".equals(carId) && !"-1".equals(carId)  && null != carId) {
            sql.append(" AND carPrice.car_id = '" + carId + "' \n");
        }
        if (!"null".equals(brandId) && !"".equals(brandId) && !"-1".equals(brandId)  && null != brandId) {
            sql.append(" AND car.brand_id = '" + brandId + "' \n");
        }
        if (!"null".equals(carModelId) && !"".equals(carModelId) && !"-1".equals(carModelId)  && null != carModelId) {
            sql.append(" AND car.car_model_id = '" + carModelId + "' \n");
        }
        if (!"null".equals(interiorColorId) && !"".equals(interiorColorId) && !"-1".equals(interiorColorId)  && null != interiorColorId) {
            sql.append(" AND carPrice.interior_color_id in (" + interiorColorId + ") \n");
        }
        if (!"null".equals(exteriorColorId) && !"".equals(exteriorColorId) && !"-1".equals(exteriorColorId) && null != exteriorColorId) {
            sql.append(" AND carPrice.exterior_color_id IN (" + exteriorColorId + ") \n");
        }
        sql.append(" AND carPrice.isdelete = 0 \n");
        sql.append(" AND interiorColor.isdelete = 0 \n");
        sql.append(" AND exteriorColor.isdelete = 0 \n");
        sql.append(" ORDER BY carPrice.updatetime DESC \n");

        if (0 != pagenum && 0 != page) {
            sql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
        }
        return sql.toString();
    }


    /**
     * @Description:
     * @param query
    {
    "query":[
    {
    "carPriceId":"00027e6e68c2ca875c2d728f710b2fda",
    "stock":"110",
    "onWay":"11",
    "discount":"111111",
    "lowPrice":"222222",
    "createUser":"342",
    "areas":[
    {"salesAreaName":"江苏","salesAreaLevel":2}]
    }
    ]
    }
     * @Return:  修改或者批量修改我的报价
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016-09-20 15:45
     * @Version 2.0
     */
    @POST
    @Path("/batchUpdateCarPrice")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String batchUpdateCarPrice(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                this.jsonArray = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonArray();

                String carPriceId = "";
                // 汽车ID
                String carId = "";
                // 在途
                String onWay = "";
                // 库存
                String stock = "";
                // 优惠
                int discount = 0;
                // 报价
                int lowPrice = 0;
                String createUser = "";
                // 销售区域的名称
                String salesAreaName = "";
                // 销售区域的权重
                int salesAreaLevel = -1;

                JsonObject salseAreaObj = new JsonObject();
                // 用于计算的值
                int num = 0;


                // 用来存放GCH_CAR_PREFER的数据集合
                List<GchCarPriceRecord> carPriceList = new ArrayList<>();
                GchCarPriceRecord carPriceRecord = create.newRecord(GCH_CAR_PRICE);

                for (int i = 0, j = this.jsonArray.size(); i < j; i++) {
                    carPriceId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "carPriceId");
                    carId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "carId");
                    onWay = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "onWay");
                    stock = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "stock");
                    createUser = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "createUser");
                    discount = getJsonAsInt(this.jsonArray.get(i).getAsJsonObject(), "discount");
                    lowPrice = getJsonAsInt(this.jsonArray.get(i).getAsJsonObject(), "lowPrice");

                    carPriceRecord = create.newRecord(GCH_CAR_PRICE);
                    carPriceRecord.setId(carPriceId);
                    carPriceRecord.setOnway(onWay);
                    carPriceRecord.setStock(stock);
                    carPriceRecord.setDiscount(discount);
                    carPriceRecord.setLowPrice(lowPrice);
                    carPriceRecord.setUpdateuser(createUser);
                    carPriceRecord.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                    /*
                    *  无论是对车款批量修改还是单次修改 lowPrice都是一样的 所以判断第一条数据是否为底价就可以了
                    *  思路如下:
                    *   0. 查询出未修改之前这辆车的价格
                        1. 如果和之前未修改的车未价格不相等
                        2. 查看最底价价格 则再进行比较,
                        3. 如是最底价 当前车款所有的month_status为0
                        4. 当前new.month_status的值是1
                    * */
                    if (0 == i) {
                        // 查看最底价价格 则再进行比较,
                        int minimumPrice =
                                Integer.valueOf(create.fetchOne(
                                        "SELECT id, MIN(low_price) AS name FROM gch_car_price WHERE car_id ='" + carId + "'"
                                ).into(Base_Idname.class).name);

                        // 如是最底价 当前车款所有的 month_status,quarter_status 为0
                        if (lowPrice < minimumPrice) {
                            // 把car_price_id的地区的值置为删除
                            StringBuilder updateMonthAndQuarterStatus = new StringBuilder();
                            updateMonthAndQuarterStatus.append(" UPDATE gch_car_price \n");
                            updateMonthAndQuarterStatus.append(" SET month_status = 0, \n");
                            updateMonthAndQuarterStatus.append(" quarter_status = 0\n");
                            updateMonthAndQuarterStatus.append(" WHERE car_id ='" + carId + "'\n");
                            create.execute(updateMonthAndQuarterStatus.toString());

                            // 如果是底价车 则修改每月和每季度为低价车和成为底价车的时间
                            carPriceRecord.setMonthStatus(1);
                            carPriceRecord.setQuarterStatus(1);
                            carPriceRecord.setStatusTime(DateUtils.millisecondChangeTimestamp());
                        }
                    }
                    carPriceList.add(carPriceRecord);

                    // 把car_price_id的地区的值置为删除
                    StringBuilder updateCarPriceStatus = new StringBuilder();
                    updateCarPriceStatus.append(" UPDATE gch_sales_area \n");
                    updateCarPriceStatus.append(" SET isdelete = 1 \n");
                    updateCarPriceStatus.append(" WHERE car_price_id ='" + carPriceId + "' \n");
                    updateCarPriceStatus.append(" AND isdelete = 0 \n");
                    create.execute(updateCarPriceStatus.toString());

                    JsonArray asJsonArray = this.jsonArray.get(i).getAsJsonObject().get("areas").getAsJsonArray();
                    List<GchSalesAreaRecord> list = new ArrayList<>();
                    GchSalesAreaRecord insertSalseArea = create.newRecord(GCH_SALES_AREA);

                    // 循环便利 然后把销售区域放到list集合中
                    for (int k = 0; k < asJsonArray.size(); k++) {
                        salseAreaObj = asJsonArray.get(k).getAsJsonObject();
                        insertSalseArea = create.newRecord(GCH_SALES_AREA);
                        insertSalseArea.setId(genUUID());
                        insertSalseArea.setSalesAreaName(getJsonAsString(salseAreaObj, "salesAreaName"));
                        insertSalseArea.setSalesAreaLevel(getJsonAsInt(salseAreaObj, "salesAreaLevel"));
                        insertSalseArea.setCarPriceId(carPriceId);
                        insertSalseArea.setType(1);

                        insertSalseArea.setCreatetime(DateUtils.millisecondChangeTimestamp());
                        insertSalseArea.setCreateuser(createUser);
                        insertSalseArea.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                        insertSalseArea.setIsdelete(0);
                        list.add(insertSalseArea);
                    }
                    create.batchInsert(list).execute();
                }
                create.batchUpdate(carPriceList).execute();
            });
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
     * @Description:
     * @param query
    {
    "query":[
    {"carPriceId":"00027e6e68c2ca875c2d728f710b2fda"}
    ,{"carPriceId":"000793c4001c5c0b72c8312ffbfbb1db"}
    ,{"carPriceId":"000a65ebd2fadae90764940994607e41"}
    ,{"carPriceId":"000a65ebd2fadae90764940994607e42"}

    ]
    }
     * @Return: 删除或者批量删除我的报价
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016-09-21 13:18
     * @Version 2.0
     */
    @POST
    @Path("/batchDeleteCarPrice")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String batchDeleteCarPrice(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                this.jsonArray = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonArray();
                // 底价车的Id
                String carPriceId = "";
                // 用来存放GCH_CAR_PREFER的数据集合
                List<GchCarPriceRecord> carPreferList = new ArrayList<>();
                GchCarPriceRecord carPriceRecord = create.newRecord(GCH_CAR_PRICE);
                for (int i = 0, j = this.jsonArray.size(); i < j; i++) {
                    carPriceId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "carPriceId");
                    carPriceRecord = create.newRecord(GCH_CAR_PRICE);
                    carPriceRecord.setId(carPriceId);
                    carPriceRecord.setIsdelete(1);
                    carPriceRecord.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                    carPreferList.add(carPriceRecord);
                }
                create.batchUpdate(carPreferList).execute();
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
    {"query":{"pagenum":1,"page":10,"carId":2071,"interiorColorId":"1111","exteriorColorId":"12222222"}}
     * @Return: 本店的新增报价的车款的内饰和外饰的列表
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/12-17:42
     * @version V2.0
     **/
    @POST
    @Path("findNewOfferlist")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findNewOfferlist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            // 车款的ID
            String carId = getJsonAsString(json,"carId");
            String interiorColorId = getJsonAsString(json,"interiorColorId");
            String exteriorColorId = getJsonAsString(json,"exteriorColorId");
            String userId = getJsonAsString(json, "userId");

            // 查询出所有本店新增报价的车款的内饰 外饰(带分页)
            List<Car_special_price_car> rets = create.fetch(
                    newAddOfferSql(getJsonAsInt(json,"pagenum"),getJsonAsInt(json,"page"),userId,carId,interiorColorId,exteriorColorId)
            ).into(Car_special_price_car.class);

            // 查询出所有本店新增报价的车款的内饰 外饰的总数
            int count = create.fetch(newAddOfferSql(0,0,userId,carId,interiorColorId,exteriorColorId)).size();
            // 封装为一个带count的对象
            BaseCount<Car_special_price_car> resultData = new BaseCount<>();
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
     * @Return: 此方法为查询新增报价的通用方法
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/13-10:21
     * @version V2.0
     **/
    public String newAddOfferSql(Integer pagenum, Integer page,String userId, String carId,String interiorColorId,String exteriorColorId){

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

        if (!"null".equals(interiorColorId) && !"".equals(interiorColorId) && !"-1".equals(interiorColorId)  && null != interiorColorId) {
            sql.append(" AND interiorColor.id in (" + interiorColorId + ") \n");
        }
        if (!"null".equals(exteriorColorId) && !"".equals(exteriorColorId) && !"-1".equals(exteriorColorId) && null != exteriorColorId) {
            sql.append(" AND exteriorColor.id in (" + exteriorColorId + ") \n");
        }
        sql.append(" ) AS color ON color.carId = car.id \n");
        sql.append(" WHERE car.id = '" + carId + "' \n");
        sql.append(" AND car.isdelete = 0 \n");

        sql.append(" ) AS viewColor \n");
        sql.append(" WHERE viewColor.mergeColor NOT IN ( \n");
        sql.append(" SELECT \n");
        sql.append(" CONCAT(specialCar.interior_color_id,\",\",specialCar.exterior_color_id) \n");
        sql.append(" FROM gch_car_price AS specialCar\n");
        sql.append(" WHERE specialCar.isdelete = 0 \n");
        sql.append(" AND specialCar.price IS NOT NULL \n");
        sql.append(" AND specialCar.user_id = '" + userId + "' \n");

        if (!"null".equals(interiorColorId) && !"".equals(interiorColorId) && !"-1".equals(interiorColorId)  && null != interiorColorId) {
            sql.append(" AND specialCar.interior_color_id in (" + interiorColorId + ") \n");
        }
        if (!"null".equals(exteriorColorId) && !"".equals(exteriorColorId) && !"-1".equals(exteriorColorId) && null != exteriorColorId) {
            sql.append(" AND specialCar.exterior_color_id in (" + exteriorColorId + ") \n");
        }

        sql.append(" AND specialCar.car_id ='" + carId + "')\n");

        if (0 != pagenum && 0 != page) {
            sql.append("LIMIT " + (page - 1) * pagenum + "," + pagenum);
        }
        return  sql.toString();
    }


    /**
     * @param query
    {
    "query":[
    {
    "carId":"han",
    "exteriorColorId":"hanhan",
    "interiorColorId":"hanhanhan",
    "stock":"110",
    "onWay":"22",
    "price":"3333333",
    "discount":"111111",
    "lowPrice":"222222",
    "userId":"342",
    "areas":[
    {"salesAreaName":"江苏","salesAreaLevel":5}]
    }
    ]
    }

     * @Description:
     * @Return: 添加或者批量添加我的报价
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016-09-21 13:05
     * @Version 2.0
     */
    @POST
    @Path("/batchAddCarPrice")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String batchAddCarPrice(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                this.jsonArray = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonArray();

                // 汽车车款
                String carId = "";

                // 汽车外观
                String exteriorColorId = "";
                // 汽车内饰
                String interiorColorId = "";
                // 在途
                String onWay = "";
                // 库存
                String stock = "";
                // 价格
                int price = 0;
                // 优惠
                int discount = 0;
                // 报价
                int lowPrice = 0;
                String userId = "";
                // 销售区域的名称
                String salesAreaName = "";
                // 销售区域的权重
                int salesAreaLevel = -1;
                String carPriceId = "";

                JsonObject salseAreaObj = new JsonObject();


                // 用来存放GCH_CAR_PREFER的数据集合
                List<GchCarPriceRecord> carPriceList = new ArrayList<>();
                GchCarPriceRecord carPriceRecord = create.newRecord(GCH_CAR_PRICE);

                for (int i = 0, j = this.jsonArray.size(); i < j; i++) {
                    carPriceId = genUUID();
                    carId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "carId");
                    interiorColorId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "interiorColorId");
                    exteriorColorId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "exteriorColorId");
                    onWay = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "onWay");
                    stock = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "stock");
                    userId = getJsonAsString(this.jsonArray.get(i).getAsJsonObject(), "userId");
                    price = getJsonAsInt(this.jsonArray.get(i).getAsJsonObject(), "price");
                    discount = getJsonAsInt(this.jsonArray.get(i).getAsJsonObject(), "discount");
                    lowPrice = getJsonAsInt(this.jsonArray.get(i).getAsJsonObject(), "lowPrice");

                    carPriceRecord = create.newRecord(GCH_CAR_PRICE);
                    carPriceRecord.setId(carPriceId);
                    carPriceRecord.setCarId(carId);
                    carPriceRecord.setInteriorColorId(interiorColorId);
                    carPriceRecord.setExteriorColorId(exteriorColorId);
                    carPriceRecord.setOnway(onWay);
                    carPriceRecord.setStock(stock);
                    carPriceRecord.setUserId(userId);
                    carPriceRecord.setPrice(price);
                    carPriceRecord.setDiscount(discount);
                    carPriceRecord.setLowPrice(lowPrice);

                    carPriceRecord.setCreateuser(userId);
                    carPriceRecord.setCreatetime(DateUtils.millisecondChangeTimestamp());
                    carPriceRecord.setIsdelete(0);
                    carPriceRecord.setUpdateuser(userId);
                    carPriceRecord.setUpdatetime(DateUtils.millisecondChangeTimestamp());

                    if (0 == i) {
                        // 查看最底价价格 则再进行比较,

                        String str_minimumPrice =
                                create.fetchOne(
                                        "SELECT id, MIN(low_price) AS name FROM gch_car_price WHERE car_id ='" + carId + "'"
                                ).into(Base_Idname.class).name;

                        // 如是最底价 当前车款所有的 month_status,quarter_status 为0
                        if (null == str_minimumPrice || lowPrice < Integer.valueOf(str_minimumPrice)) {
                            // 把car_price_id的地区的值置为删除
                            StringBuilder updateMonthAndQuarterStatus = new StringBuilder();
                            updateMonthAndQuarterStatus.append(" UPDATE gch_car_price \n");
                            updateMonthAndQuarterStatus.append(" SET month_status = 0, \n");
                            updateMonthAndQuarterStatus.append(" quarter_status = 0\n");
                            updateMonthAndQuarterStatus.append(" WHERE car_id ='" + carId + "'\n");
                            create.execute(updateMonthAndQuarterStatus.toString());
                            // 如果是底价车 则添加每月和每季度为低价车和成为底价车的时间
                            carPriceRecord.setMonthStatus(1);
                            carPriceRecord.setQuarterStatus(1);
                            carPriceRecord.setStatusTime(DateUtils.millisecondChangeTimestamp());
                        }
                    }


                    carPriceList.add(carPriceRecord);

                    JsonArray asJsonArray = this.jsonArray.get(i).getAsJsonObject().get("areas").getAsJsonArray();
                    List<GchSalesAreaRecord> list = new ArrayList<>();
                    GchSalesAreaRecord insertSalseArea = create.newRecord(GCH_SALES_AREA);

                    // 循环便利 然后把销售区域放到list集合中
                    for (int k = 0; k < asJsonArray.size(); k++) {
                        salseAreaObj = asJsonArray.get(k).getAsJsonObject();
                        insertSalseArea = create.newRecord(GCH_SALES_AREA);
                        insertSalseArea.setId(genUUID());
                        insertSalseArea.setSalesAreaName(getJsonAsString(salseAreaObj, "salesAreaName"));
                        insertSalseArea.setSalesAreaLevel(getJsonAsInt(salseAreaObj, "salesAreaLevel"));
                        insertSalseArea.setCarPriceId(carPriceId);
                        insertSalseArea.setType(1);

                        insertSalseArea.setCreatetime(DateUtils.millisecondChangeTimestamp());
                        insertSalseArea.setCreateuser(userId);
                        insertSalseArea.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                        insertSalseArea.setIsdelete(0);
                        list.add(insertSalseArea);
                    }
                    create.batchInsert(list).execute();
                }
                create.batchInsert(carPriceList).execute();
            });
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }



}
