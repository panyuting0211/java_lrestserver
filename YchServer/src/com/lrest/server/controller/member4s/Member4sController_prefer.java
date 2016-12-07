package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.GchCarModelPreferRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarPreferRecord;
import com.lrest.server.models.Base_Idname;
import com.lrest.server.models.Car_BrandModelCar;
import com.lrest.server.models.Car_InteriorExteriorColor;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.GCH_CAR_MODEL_PREFER;
import static com.lrest.server.jooqmodel.Tables.GCH_CAR_PREFER;
import static com.lrest.server.utils.UtilBase.getJsonAsString;
import static java.util.stream.Collectors.*;

/**
* @DESCRIPTION: 
* @Return: 喜好的类
* @Author: 韩武洽 @Wyshown
* @Date: 2016/9/14-13:47
* @version V2.0  
**/
@Path("/4s/prefer")
public class Member4sController_prefer extends BaseController{
    private   final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s/offer");
    }

    /**
     * * @param query  入参方式如下:
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
    @Path("preferBrandModelCar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String preferBrandModelCar(String query) {
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
            sqlBrand.append(" AND user4s.isdelete = 0 \n");

            sqlBrand.append(" UNION \n\n");

            sqlBrand.append(" SELECT \n");
            sqlBrand.append(" brand.id AS brandId, \n");
            sqlBrand.append(" brand.brand_name AS brandName \n");
            sqlBrand.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            sqlBrand.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id \n");
            sqlBrand.append(" WHERE user4sBrank.user_4s_id ='" + user4sId + "' \n");
            sqlBrand.append(" AND user4sBrank.isdelete = 0 \n");

            // 定义 品牌的Result结果集
            brandModelCar.brand = create.fetch(sqlBrand.toString()).into(Car_BrandModelCar.Brand.class);

            // 查询出喜好车型的SQL语句
            StringBuilder carModelSql = new StringBuilder();

            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.brandId AS brandId, \n");
            carModelSql.append(" brand.brandName AS brandName, \n");
            carModelSql.append(" carModelPrefer.car_model_id AS carModelId, \n");
            carModelSql.append(" carModelPrefer.car_model_name AS carModelName \n\n");

            carModelSql.append(" FROM gch_car_model_prefer AS carModelPrefer  \n");
            carModelSql.append(" LEFT JOIN  ( \n");
            // 子查询
            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM \n");
            carModelSql.append(" gch_user_4s AS user4s \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id AND brand.isdelete = 0 \n");
            carModelSql.append(" WHERE user4s.isdelete = 0 \n");

            carModelSql.append(" UNION \n\n");

            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0\n");
            carModelSql.append(" WHERE user4sBrank.isdelete = 0 \n");

            carModelSql.append(" ) AS brand ON carModelPrefer.brand_id = brand.brandId\n");
            carModelSql.append(" WHERE carModelPrefer.user_id = '" + user4sId + "' \n");
            carModelSql.append(" AND  brand.brandId IS NOT NULL \n");
            carModelSql.append(" AND carModelPrefer.isdelete = 0 \n");
            carModelSql.append(" GROUP BY brand.brandId,carModelPrefer.car_model_id \n");
            carModelSql.append("  ORDER BY  carModelPrefer.car_model_name \n");

            log.debug(carModelSql.toString());

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

            // 查询出喜好车款的SQL语句
            StringBuilder carSql = new StringBuilder();

            carSql.append(" SELECT \n");
            carSql.append(" brand.brandId AS brandId, \n");
            carSql.append(" brand.brandName AS brandName, \n");
            carSql.append(" carModelPrefer.car_model_id AS carModelId, \n");
            carSql.append(" carModelPrefer.car_model_name AS carModelName, \n");
            carSql.append(" carPrefer.car_id AS carId, \n");
            carSql.append(" carPrefer.car_name AS carName \n\n");

            carSql.append(" FROM gch_car_prefer AS carPrefer \n");
            carSql.append(" LEFT JOIN gch_car_model_prefer AS carModelPrefer ON carPrefer.car_model_id = carModelPrefer.car_model_id AND carPrefer.user_id = carModelPrefer.user_id \n");
            carSql.append(" LEFT JOIN(  \n");
            // 子查询
            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM \n");
            carSql.append(" gch_user_4s AS user4s \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id  AND brand.isdelete = 0\n");
            carSql.append(" WHERE user4s.isdelete = 0 \n");

            carSql.append(" UNION \n\n");

            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0 \n");
            carSql.append(" WHERE user4sBrank.isdelete = 0 \n");
            carSql.append(" ) AS brand  ON carModelPrefer.brand_id = brand.brandId \n");
            carSql.append(" WHERE carPrefer.user_id = '" + user4sId + "' \n");
            carSql.append(" AND carPrefer.car_model_id IS NOT NULL  \n");
            carSql.append(" AND carPrefer.isdelete = 0    \n");
            carSql.append(" GROUP BY brand.brandId,carModelPrefer.car_model_id,carPrefer.car_id  \n ");
            carSql.append(" ORDER BY carPrefer.car_name desc\n");
            log.debug(carSql.toString());

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
                                            r -> new Car_BrandModelCar.Car(                                                  r.getValue("carId", String.class)
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


    @POST
    @Path("findMyBrandModelCar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findMyBrandModelCar(String query) {
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
            sqlBrand.append(" AND user4s.isdelete = 0 \n");

            sqlBrand.append(" UNION \n\n");

            sqlBrand.append(" SELECT \n");
            sqlBrand.append(" brand.id AS brandId, \n");
            sqlBrand.append(" brand.brand_name AS brandName \n");
            sqlBrand.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            sqlBrand.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id \n");
            sqlBrand.append(" WHERE user4sBrank.user_4s_id ='" + user4sId + "' \n");
            sqlBrand.append(" AND user4sBrank.isdelete = 0 \n");

            // 定义 品牌的Result结果集
            brandModelCar.brand = create.fetch(sqlBrand.toString()).into(Car_BrandModelCar.Brand.class);

            // 查询出喜好车型的SQL语句
            StringBuilder carModelSql = new StringBuilder();

            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.brandId AS brandId, \n");
            carModelSql.append(" brand.brandName AS brandName, \n");
            carModelSql.append(" carModel.id AS carModelId, \n");
            carModelSql.append(" carModel.car_model_name AS carModelName \n\n");


            carModelSql.append(" FROM gch_car_price AS price \n");
            carModelSql.append(" LEFT JOIN gch_car AS car ON price.car_id = car.id \n");
            carModelSql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
            carModelSql.append(" LEFT JOIN ( \n");
            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM \n");
            carModelSql.append(" gch_user_4s AS user4s \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id AND brand.isdelete = 0 \n");
            carModelSql.append(" WHERE user4s.isdelete = 0 \n");
            carModelSql.append(" And user4s.id ='" + user4sId + "'   \n");

            carModelSql.append(" UNION \n\n");

            carModelSql.append(" SELECT \n");
            carModelSql.append(" brand.id AS brandId, \n");
            carModelSql.append(" brand.brand_name AS brandName \n");
            carModelSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carModelSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0\n");
            carModelSql.append(" WHERE user4sBrank.isdelete = 0 \n");
            carModelSql.append(" AND user4sBrank.user_4s_id ='" +  user4sId + "'  \n");
            carModelSql.append(" ) AS brand ON carModel.brand_id = brand.brandId \n");

            carModelSql.append(" WHERE price.user_id = '" + user4sId + "' \n");
            carModelSql.append(" AND  brand.brandId IS NOT NULL \n");
            carModelSql.append(" AND price.isdelete = 0 \n");
            carModelSql.append(" GROUP BY brand.brandId,carModel.id \n");
            carModelSql.append(" ORDER BY  carModel.car_model_name \n");

            log.debug(carModelSql.toString());

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

            // 查询出喜好车款的SQL语句
            StringBuilder carSql = new StringBuilder();


            carSql.append(" SELECT \n");
            carSql.append(" brand.brandId AS brandId, \n");
            carSql.append(" brand.brandName AS brandName, \n");
            carSql.append(" carModel.id AS carModelId, \n");
            carSql.append(" carModel.car_model_name AS carModelName, \n");
            carSql.append(" car.id AS carId, \n");
            carSql.append(" car.car_name AS carName \n");

            carSql.append(" FROM gch_car_price AS price \n");
            carSql.append(" LEFT JOIN gch_car AS car ON price.car_id = car.id \n");
            carSql.append(" LEFT JOIN gch_car_model AS carModel ON car.car_model_id = carModel.id \n");
            carSql.append(" LEFT JOIN ( \n");
            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM \n");
            carSql.append(" gch_user_4s AS user4s \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4s.brand_4s = brand.id AND brand.isdelete = 0 \n");
            carSql.append(" WHERE user4s.isdelete = 0 \n");
            carSql.append(" And user4s.id ='" + user4sId + "'   \n");

            carSql.append(" UNION \n\n");

            carSql.append(" SELECT \n");
            carSql.append(" brand.id AS brandId, \n");
            carSql.append(" brand.brand_name AS brandName \n");
            carSql.append(" FROM gch_user_4s_brand AS user4sBrank \n");
            carSql.append(" LEFT JOIN gch_brand AS brand ON user4sBrank.brand_id = brand.id AND brand.isdelete = 0\n");
            carSql.append(" WHERE user4sBrank.isdelete = 0 \n");
            carSql.append(" AND user4sBrank.user_4s_id ='" +  user4sId + "'  \n");
            carSql.append(" ) AS brand ON carModel.brand_id = brand.brandId \n");

            carSql.append(" WHERE price.user_id = '" + user4sId + "' \n");
            carSql.append(" AND car.id IS NOT NULL \n");
            carSql.append(" AND price.isdelete = 0 \n");
            carSql.append(" GROUP BY brand.brandId,carModel.id,car.id \n");
            carSql.append(" ORDER BY car.car_name desc \n");

            log.debug(carSql.toString());

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
                                            r -> new Car_BrandModelCar.Car(                                                  r.getValue("carId", String.class)
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
     * @param query  {"query":{"brandId":"258ce5ebe63161f7f265fbb508696d7b"}}
    * @Description:
     * @Return: 通过品牌的Id 找到品牌下所有的车型
     * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-18 17:08
    * @Version 2.0
    */
    @POST
    @Path("findCarModelByBrand")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarModelByBrand(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String brandId = getJsonAsString(this.jsonObject,"brandId");

            StringBuilder modelCarSql = new StringBuilder();
            modelCarSql.append(" SELECT \n");
            modelCarSql.append(" carModel.id AS carModelId, \n");
            modelCarSql.append(" carModel.car_model_name AS carModelName \n\n");

            modelCarSql.append(" FROM \n");
            modelCarSql.append(" gch_car_model AS carModel \n");
            modelCarSql.append(" WHERE carModel.brand_id = '" + brandId + "' \n");
            modelCarSql.append(" AND carModel.isdelete = 0 ");
            modelCarSql.append(" ORDER BY carModel.car_model_name\n");
            List<Car_BrandModelCar.CarModel> rets = create.fetch(modelCarSql.toString()).into(Car_BrandModelCar.CarModel.class);
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param query
      {"query":{"carModelId":"258ce5ebe63161f7f265fbb508696d7b"}}
    * @Description:
     * @Return: 通过车型的ID 找到车型下所有的车款
     * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-18 17:09
    * @Version 2.0
    */
    @POST
    @Path("findCarByCarModel")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarByCarModel(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carModelId = getJsonAsString(this.jsonObject,"carModelId");

            StringBuilder carSql = new StringBuilder();
            carSql.append(" SELECT \n");
            carSql.append(" car.id AS carId, \n");
            carSql.append(" car.car_name AS carName \n");
            carSql.append(" FROM gch_car AS car \n");
            carSql.append(" WHERE car.car_model_id = '" + carModelId + "' \n");
            carSql.append(" AND car.isdelete = 0 \n");
            carSql.append(" ORDER BY car.car_name DESC");

            List<Car_BrandModelCar.Car> rets = create.fetch(carSql.toString()).into(Car_BrandModelCar.Car.class);
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }



    /**
    * @Description:
    * @Return:  执行批量修改或者修改喜好车型的操作
    * @param query  入参方式如下:
    {"query": {
            "brandId":"121"
            ,"userId":"190"
            ,"carModels":[
                    {"carModelId":"281","carModelName":"281车lg"}
                    ,{"carModelId":"283","carModelName":"283lg"}
                    ,{"carModelId":"285","carModelName":"2835glg车 "}
                    ,{"carModelId":"han","carModelName":"hanlg车  "}
                    ,{"carModelId":"hanhan","carModelName":"hanhanlg车  "}

                    ]

            }
    }
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-19 11:06
    * @Version 2.0
    */
    @POST
    @Path("updateCarModelPrefer")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCarModelPrefer(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
             create.transaction(configuration -> {
                JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

                // 判断 有三种情况
                // 第一种: 入参有值A 数据库有值B 则对比 然后删除差集A\B 添加差集B\A 并集A⌒B保持不变
                // 第二种: 入参有值A 数据库无值B 则把入参的值 添加到数据库中
                // 第三种: 入参为空A 数据库有值B 则把数据库中喜好车型 车款删除

                // 品牌的ID
                String brandId = getJsonAsString(json, "brandId");
                // 用户的Id
                String userId = getJsonAsString(json, "userId");
                JsonObject jsonObject = new JsonObject();
                // 入参传来的carModel集合
                JsonArray enter_carModels = json.getAsJsonArray("carModels");
                // 定义用来接收入参中CarMoel中传来的carModelId的值
                String enter_carModelId = "";
                // 定义查询出来的carModelId的值
                String db_carModelId = "";
                // 用来存放GCH_CAR_MODEL_PREFER的数据集合
                List<GchCarModelPreferRecord> carModelPreferList = new ArrayList<>();
                GchCarModelPreferRecord carModelPreferRecord = create.newRecord(GCH_CAR_MODEL_PREFER);

                // 通过品牌,用户ID获得未删除的喜好
                StringBuilder carModelSql = new StringBuilder();
                carModelSql.append(" SELECT \n");
                carModelSql.append(" carModelPrefer.id, \n");
                carModelSql.append(" carModelPrefer.car_model_id AS name \n");
                carModelSql.append(" FROM gch_car_model_prefer AS carModelPrefer \n");
                carModelSql.append(" WHERE carModelPrefer.brand_id = '" + brandId + "' \n");
                carModelSql.append(" AND carModelPrefer.user_id = '" + userId + "' \n");
                carModelSql.append(" AND carModelPrefer.isdelete = 0 \n");
                // 通过SQL查询出来的未删除的爱好
                List<Base_Idname> db_carModels = create.fetch(carModelSql.toString()).into(Base_Idname.class);

                /***第一种: 入参有值A 数据库有值B 则对比 然后删除差集A\B 添加差集B\A 并集A⌒B保持不变        start********/
                if (enter_carModels.size() != 0 && db_carModels.size() != 0) {
                    for (int i = 0; i < enter_carModels.size(); i++) {
                        enter_carModelId = getJsonAsString(enter_carModels.get(i).getAsJsonObject(), "carModelId");
                        for (int k = 0, l = db_carModels.size(); k < l; k++) {
                            // 把数据库的carModel和入参的carModel进行比较 如果相等,则移除数据库和入参的这一条的值
                            db_carModelId = db_carModels.get(k).name;
                            if (enter_carModelId.equals(db_carModelId)) {
                                db_carModels.remove(k);
                                enter_carModels.remove(i);
                                i = 0;
                                break;
                            }
                        }
                    }
                    // 删除差集A\B: 删除喜好车型和喜好车型下的车款
                    for (int i = 0, j = db_carModels.size(); i < j; i++) {
                        db_carModelId = db_carModels.get(i).name;

                        // 把喜好的车款的状态置为删除
                        StringBuilder updateCarPreferSql = new StringBuilder();
                        updateCarPreferSql.append(" UPDATE  gch_car_prefer \n");
                        updateCarPreferSql.append(" SET isdelete = 1 \n");
                        updateCarPreferSql.append(" WHERE car_model_id ='" + db_carModelId + "' \n");
                        updateCarPreferSql.append(" AND isdelete = 0 \n");
                        create.execute(updateCarPreferSql.toString());

                        // 把喜好的车型的状态置为删除
                        StringBuilder updateCarModelPreferSql = new StringBuilder();
                        updateCarModelPreferSql.append(" UPDATE  gch_car_model_prefer \n");
                        updateCarModelPreferSql.append(" SET isdelete = 1, \n");
                        updateCarModelPreferSql.append(" updatetime = '" + DateUtils.millisecondChangeTimestamp() + "', \n");
                        updateCarModelPreferSql.append(" updateuser = '" + userId + "' \n");
                        updateCarModelPreferSql.append(" WHERE car_model_id ='" + db_carModelId +  "' \n");
                        updateCarModelPreferSql.append(" AND isdelete = 0 \n");
                        create.execute(updateCarModelPreferSql.toString());
                    }

                    // 添加差集B\A
                    for (int i = 0, j = enter_carModels.size(); i < j; i++) {
                        carModelPreferRecord = create.newRecord(GCH_CAR_MODEL_PREFER);
                        carModelPreferRecord.setId(MD5.MD5Encode(String.valueOf(System.currentTimeMillis() + i)));
                        carModelPreferRecord.setBrandId(brandId);
                        carModelPreferRecord.setCarModelId(getJsonAsString(enter_carModels.get(i).getAsJsonObject(), "carModelId"));
                        carModelPreferRecord.setUserId(userId);
                        carModelPreferRecord.setCarModelName(getJsonAsString(enter_carModels.get(i).getAsJsonObject(), "carModelName"));
                        carModelPreferRecord.setCreatetime(DateUtils.millisecondChangeTimestamp());
                        carModelPreferRecord.setCreateuser(userId);
                        carModelPreferRecord.setIsdelete(0);
                        carModelPreferRecord.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                        carModelPreferRecord.setUpdateuser(userId);
                        carModelPreferList.add(carModelPreferRecord);
                    }

                    // 执行批量添加操作
                    create.batchInsert(carModelPreferList).execute();
                }
                /***第一种: 入参有值A 数据库有值B 则对比 然后删除差集A\B 添加差集B\A 并集A⌒B保持不变        end********/
                /* 第二种: 入参有值A 数据库无值B 则把入参的值 添加到数据库中      start *******************/
                else if (enter_carModels.size() != 0 && db_carModels.size() == 0) {
                    for (int i = 0, j = enter_carModels.size(); i < j; i++) {
                        carModelPreferRecord = create.newRecord(GCH_CAR_MODEL_PREFER);
                        carModelPreferRecord.setId(MD5.MD5Encode(String.valueOf(System.currentTimeMillis() + i)));
                        carModelPreferRecord.setBrandId(brandId);
                        carModelPreferRecord.setCarModelId(getJsonAsString(enter_carModels.get(i).getAsJsonObject(), "carModelId"));
                        carModelPreferRecord.setUserId(userId);
                        carModelPreferRecord.setCarModelName(getJsonAsString(enter_carModels.get(i).getAsJsonObject(), "carModelName"));
                        carModelPreferRecord.setCreatetime(DateUtils.millisecondChangeTimestamp());
                        carModelPreferRecord.setCreateuser(userId);
                        carModelPreferRecord.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                        carModelPreferRecord.setUpdateuser(userId);
                        carModelPreferRecord.setIsdelete(0);
                        carModelPreferList.add(carModelPreferRecord);
                    }
                    // 执行carModelPreferList的批量添加操作
                    create.batchInsert(carModelPreferList).execute();
                }
                else if (enter_carModels.size() == 0 && db_carModels.size() != 0) {
                    // 删除差集A\B: 删除喜好车型和喜好车型下的车款
                    for (int i = 0, j = db_carModels.size(); i < j; i++) {
                        db_carModelId = db_carModels.get(i).name;

                        // 把喜好的车款的状态置为删除
                        StringBuilder updateCarPreferSql = new StringBuilder();
                        updateCarPreferSql.append(" UPDATE  gch_car_prefer \n");
                        updateCarPreferSql.append(" SET isdelete = 1 \n");
                        updateCarPreferSql.append(" WHERE car_model_id ='" + db_carModelId + "' \n");
                        updateCarPreferSql.append(" AND isdelete = 0 \n");
                        create.execute(updateCarPreferSql.toString());

                        // 把喜好的车型的状态置为删除
                        StringBuilder updateCarModelPreferSql = new StringBuilder();
                        updateCarModelPreferSql.append(" UPDATE  gch_car_model_prefer \n");
                        updateCarModelPreferSql.append(" SET isdelete = 1, \n");
                        updateCarModelPreferSql.append(" updatetime = '" + DateUtils.millisecondChangeTimestamp() + "', \n");
                        updateCarModelPreferSql.append(" updateuser = '" + userId + "' \n");
                        updateCarModelPreferSql.append(" WHERE car_model_id ='" + db_carModelId +  "' \n");
                        updateCarModelPreferSql.append(" AND isdelete = 0 \n");
                        create.execute(updateCarModelPreferSql.toString());
                    }

                }
             });
             return  success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }



    /**
    * @Description:  执行批量添加或者修改喜好车款的操作
     * @param query :
         {"query": {
                    "carModelId":"279"
                    ,"userId":"190"
                    ,"cars":[
                       {"carId":"281","carName":"281车lg"}
                      ,{"carId":"283","carName":"283lg"}
                      ,{"carId":"285","carName":"2835glg车 "}
                      ,{"carId":"han","carName":"hanlg车  "}
                      ,{"carId":"hanhan","carName":"hanhanlg车  "}
                    ]
                 }
        }
    * @Return:
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016-09-19 16:04
    * @Version 2.0
    */
    @POST
    @Path("updateCarPrefer")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCarPrefer(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
             create.transaction(configuration -> {
                 this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

                 // 车型的ID
                 String carModelId = getJsonAsString(this.jsonObject, "carModelId");
                 // 用户的Id
                 String userId = getJsonAsString(this.jsonObject, "userId");

                 // 入参传来的car集合
                 JsonArray enter_cars = this.jsonObject.getAsJsonArray("cars");

                 // 用来存放GCH_CAR_PREFER的数据集合
                 List<GchCarPreferRecord> carPreferList = new ArrayList<>();
                 GchCarPreferRecord carPreferRecord = create.newRecord(GCH_CAR_PREFER);

                 // 把喜好的车款的状态置为删除
                 StringBuilder updateCarPreferSql = new StringBuilder();
                 updateCarPreferSql.append(" UPDATE  gch_car_prefer \n");
                 updateCarPreferSql.append(" SET isdelete = 1 \n");
                 updateCarPreferSql.append(" WHERE car_model_id ='" + carModelId + "' \n");
                 updateCarPreferSql.append(" AND isdelete = 0 \n");
                 create.execute(updateCarPreferSql.toString());

                 for (int i = 0, j = enter_cars.size(); i < j; i++) {
                     carPreferRecord = create.newRecord(GCH_CAR_PREFER);
                     carPreferRecord.setId(MD5.MD5Encode(String.valueOf(System.currentTimeMillis() + i)));
                     carPreferRecord.setCarId(getJsonAsString(enter_cars.get(i).getAsJsonObject(), "carId"));
                     carPreferRecord.setCarModelId(carModelId);
                     carPreferRecord.setCarName(getJsonAsString(enter_cars.get(i).getAsJsonObject(), "carName"));
                     carPreferRecord.setUserId(userId);
                     carPreferRecord.setCreatetime(DateUtils.millisecondChangeTimestamp());
                     carPreferRecord.setCreateuser(userId);
                     carPreferRecord.setIsdelete(0);
                     carPreferList.add(carPreferRecord);
                 }
                 // 执行carModelPreferList的批量添加操作
                 create.batchInsert(carPreferList).execute();
            });
            return  success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
        * @Description:
     {
        "query":{
            "carId":"aaa"
        }
    }
        * @Return: 根据车款 查询出外观 和内饰颜色
        * @Author: 韩武洽 @Wyshown
        * @Date: 2016/9/29 11:40
        * @Version 2.0
        */
    @POST
    @Path("findCarColorByCarId")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarColorByCarId(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carId = getJsonAsString(this.jsonObject,"carId");

            Car_InteriorExteriorColor interiorExteriorColor = new Car_InteriorExteriorColor();

            // 外观接口
            StringBuilder exteriorColorSql = new StringBuilder();
            exteriorColorSql.append(" SELECT id,color_name AS colorName,color_value AS  colorValue \n");
            exteriorColorSql.append(" FROM gch_view_exterior_color \n");
            exteriorColorSql.append(" WHERE car_id ='" + carId + "' \n");
            exteriorColorSql.append(" AND isdelete = 0 \n");
            exteriorColorSql.append(" ORDER BY color_name \n");
            interiorExteriorColor.exteriorColorList = create.fetch(exteriorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);

            // 内饰接口
            StringBuilder interiorColorSql = new StringBuilder();
            interiorColorSql.append(" SELECT id,color_name AS colorName,color_value AS  colorValue \n");
            interiorColorSql.append(" FROM gch_view_interior_color \n");
            interiorColorSql.append(" WHERE car_id ='" + carId + "' \n");
            interiorColorSql.append(" AND isdelete = 0 \n");
            interiorColorSql.append(" ORDER BY color_name \n");
            interiorExteriorColor.interiorColorList = create.fetch(interiorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);

            return success(interiorExteriorColor);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("findMyCarColorByCarId")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findMyCarColorByCarId(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            this.jsonObject = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String userId = getJsonAsString(this.jsonObject,"userId");
            String carId = getJsonAsString(this.jsonObject,"carId");

            Car_InteriorExteriorColor interiorExteriorColor = new Car_InteriorExteriorColor();

            // 外观接口
            StringBuilder exteriorColorSql = new StringBuilder();
            exteriorColorSql.append(" SELECT \n");
            exteriorColorSql.append(" exteriorColor.id, \n");
            exteriorColorSql.append(" exteriorColor.color_name AS colorName, \n");
            exteriorColorSql.append(" exteriorColor.color_value AS  colorValue \n");
            exteriorColorSql.append(" FROM  gch_car_price AS price \n");
            exteriorColorSql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON price.exterior_color_id = exteriorColor.id \n");
            exteriorColorSql.append(" WHERE price.user_id = '" + userId + "' \n");
            exteriorColorSql.append(" AND price.car_id = '" + carId + "' \n");
            exteriorColorSql.append(" AND price.isdelete = 0 \n");
            exteriorColorSql.append(" AND exteriorColor.isdelete = 0 \n");
            exteriorColorSql.append(" GROUP BY price.user_id,exteriorColor.id \n");
            exteriorColorSql.append(" ORDER BY exteriorColor.color_name \n");

            interiorExteriorColor.exteriorColorList = create.fetch(exteriorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);
            // 内饰接口
            StringBuilder interiorColorSql = new StringBuilder();
            interiorColorSql.append(" SELECT interiorColor.id, \n");
            interiorColorSql.append(" interiorColor.color_name AS colorName, \n");
            interiorColorSql.append(" interiorColor.color_value AS  colorValue \n");
            interiorColorSql.append(" FROM gch_car_price AS price \n");
            interiorColorSql.append(" LEFT JOIN gch_view_interior_color AS interiorColor ON price.interior_color_id = interiorColor.id \n");
            interiorColorSql.append(" WHERE price.user_id = '" + userId + "' \n");
            interiorColorSql.append(" AND price.car_id = '" + carId + "' \n");
            interiorColorSql.append(" AND price.isdelete = 0 \n");
            interiorColorSql.append(" AND interiorColor.isdelete = 0 \n");
            interiorColorSql.append(" GROUP BY price.user_id,price.interior_color_id \n");
            interiorColorSql.append(" ORDER BY interiorColor.color_name \n");

            interiorExteriorColor.interiorColorList = create.fetch(interiorColorSql.toString()).into(Car_InteriorExteriorColor.CarColor.class);

            return success(interiorExteriorColor);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
