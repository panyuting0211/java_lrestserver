package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Car_Question;
import com.lrest.server.models.Car_special_price_car;
import com.lrest.server.models.Mobile.Mobile_SpecialPriceCar;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.utils.DBUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lrest.server.utils.UtilBase.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * DESCRIPTION: Mobile端 特价车
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:14
 **/

@Path("mobile/specialPriceCar")
public class Mobile_SpecialPriceCarController extends BaseController {

    /**
     * 特价车状态: 在售
     */
    public static final Integer SPECIAL_STATUS_ONSELL = 3;

    /**
     * @param query orderRule有两种规则 降序:"DESC" 升序:"ASC" orderField有两个字段: discount和price 参数如下: {"query":{"pagenum":10,"page":1,"orderField":"discount","orderRule":"desc"}}
     * @Description:
     * @Return: 查询出所有的特价车
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/27 16:08
     * @Version 2.0
     */
    @POST
    @Path("findSpecialPriceCarList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findSpecialPriceCarList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");
            String orderField = getJsonAsString(json, "orderField");
            String orderRule = getJsonAsString(json, "orderRule");

            StringBuilder specialCarPriceSql = new StringBuilder();
            specialCarPriceSql.append(" SELECT \n");
            specialCarPriceSql.append(" special.id AS specialPriceId, \n");
            specialCarPriceSql.append(" car.brand_id AS brandId, \n");
            specialCarPriceSql.append(" car.brand_name AS brandName, \n");
            specialCarPriceSql.append(" car.car_model_id AS carModelId, \n");
            specialCarPriceSql.append(" car.car_model_name AS carModelName, \n");
            specialCarPriceSql.append(" car.id as carId, \n");
            specialCarPriceSql.append(" car.car_name AS carName, \n");
            specialCarPriceSql.append(" CASE \n");
            specialCarPriceSql.append(" WHEN special.car_image IS NULL THEN NULL \n");
            specialCarPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(special.car_image ,\"type\",\"small\")) \n");
            specialCarPriceSql.append(" END AS carImage, \n");
            specialCarPriceSql.append(" special.start_date AS startDate, \n");
            specialCarPriceSql.append(" special.end_date AS endDate, \n");
            specialCarPriceSql.append(" special.number, \n");
            specialCarPriceSql.append(" special.price, \n");
            specialCarPriceSql.append(" special.special_price AS specialPrice, \n");
            specialCarPriceSql.append(" (special.price - special.special_price) AS discount \n");
            specialCarPriceSql.append(" FROM gch_special_price_car AS special\n");
            specialCarPriceSql.append(" LEFT JOIN gch_view_exterior_color AS exteriorColor ON special.exterior_color_id = exteriorColor.id \n");
            specialCarPriceSql.append(" LEFT JOIN gch_view_car AS car ON special.car_id = car.id \n");
            specialCarPriceSql.append(" WHERE car.id IS NOT NULL \n");
            specialCarPriceSql.append(" AND special.isdelete = 0 \n");
            specialCarPriceSql.append(" AND special.status ='" + SPECIAL_STATUS_ONSELL + "' \n");

            if (orderField != null) {
                specialCarPriceSql.append(" ORDER BY " + orderField + " " + orderRule + " \n");
            } else {
                specialCarPriceSql.append(" ORDER BY price \n");
            }
            List<Mobile_SpecialPriceCar> rets = create.fetch(specialCarPriceSql.toString()).into(Mobile_SpecialPriceCar.class);
            BaseCount<Mobile_SpecialPriceCar> resultData = new BaseCount<>();
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
     * @Description:特价车详情页
     * @Params:carSpecialId(特价车id)
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/4 13:46
     * @Version 2.0
     */
    @POST
    @Path("specialPriceCarDetails")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String specialPriceCarDetails(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            StringBuilder specialCarPriceSql = new StringBuilder();
            specialCarPriceSql.append(" SELECT \n");
            specialCarPriceSql.append("specialPriceCar.id,\n");
            specialCarPriceSql.append("specialPriceCar.brand_name,\n");
            specialCarPriceSql.append("specialPriceCar.car_model_name,\n");
            specialCarPriceSql.append("specialPriceCar.car_name,\n");
            specialCarPriceSql.append("specialPriceCar.exterior_color_name,\n");
            specialCarPriceSql.append("specialPriceCar.exterior_color_value,\n");
            specialCarPriceSql.append("specialPriceCar.interior_color_name,\n");
            specialCarPriceSql.append("specialPriceCar.interior_color_value,\n");
            specialCarPriceSql.append(" CONCAT('" + Config.OSS + "',REPLACE(specialPriceCar.car_image ,\"type\",\"small\"))  AS carImage ,\n");
            specialCarPriceSql.append("specialPriceCar.auth_price,\n");
            specialCarPriceSql.append("specialPriceCar.special_price,\n");
            specialCarPriceSql.append("specialPriceCar.number,\n");
            specialCarPriceSql.append(" DATE_FORMAT(specialPriceCar.start_date,\"%Y-%m-%d\") AS startDate, \n");
            specialCarPriceSql.append("  DATE_FORMAT(specialPriceCar.end_date,\"%Y-%m-%d\") AS endDate, \n");
            specialCarPriceSql.append("specialPriceCar.attention_count,\n");
            specialCarPriceSql.append(" CONCAT(\"['\",REPLACE(GROUP_CONCAT(salesArea.sales_area_name),\",\",\"','\"),\"']\") AS salesAreaName \n");
            specialCarPriceSql.append("FROM\n");
            specialCarPriceSql.append("gch_view_special_price_car AS specialPriceCar\n");
            specialCarPriceSql.append(" LEFT JOIN gch_sales_area AS salesArea ON specialPriceCar.id = salesArea.car_special_id and specialPriceCar.isdelete=0 and salesArea.isdelete=0\n");
            specialCarPriceSql.append("WHERE\n");
            specialCarPriceSql.append("specialPriceCar.id = '" + getJsonAsString(json, "carSpecialId") + "'\n");
            List<Car_special_price_car> result = create.fetch(specialCarPriceSql.toString()).into(Car_special_price_car.class);

            String specialSql = "select car_model_id from gch_view_special_price_car where id='" + getJsonAsString(json, "carSpecialId") + "'";
            Result<Record> specialResult = create.fetch(specialSql);
            log.debug("specialResult=========================" + specialResult);

            String attSql = "select id from gch_user_attention_car_model where user_id='" + getJsonAsString(json, "userId") + "' and car_model_id='" + specialResult.get(0).getValue("car_model_id") + "' and isdelete=0";
            Result<Record> isAttResult = create.fetch(attSql);

            if (isAttResult.isEmpty()) {
                result.get(0).isAttention = 0;
            } else {
                result.get(0).isAttention = 1;
            }

            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    /**
     * @Description:关注车型
     * @Params:userId;carModelId
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/4 13:47
     * @Version 2.0
     */
    @POST
    @Path("attentionCarModel")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String attentionCarModel(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String userId = getJsonAsString(json, "userId");
            String carModelId = getJsonAsString(json, "carModelId");
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT \n");
            sql.append("id\n");
            sql.append("FROM\n");
            sql.append("gch_user_attention_car_model\n");
            sql.append("WHERE\n");
            sql.append("user_id = '" + userId + "'\n");
            sql.append("AND car_model_id = '" + carModelId + "'\n");
            sql.append(" AND isdelete = 0\n");
            sql.append(" LIMIT 1\n");
            Record record = create.fetchOne(sql.toString());

            log.debug("record==========================" + String.valueOf(record));
            String result;
            if (record != null) {
                // 取消关注
                String id = (String) record.getValue(0);
                StringBuilder qxSql = new StringBuilder();
                qxSql.append("UPDATE gch_user_attention_car_model\n");
                qxSql.append("SET isdelete = 1\n");
                qxSql.append("WHERE id = '" + id + "'\n");
                List<Integer> into = create.fetch(qxSql.toString()).into(int.class);

                if (into.isEmpty()) {
                    return success(2, "取消关注成功");
                } else {
                    return error(-2, "取消关注失败");
                }
            } else {
                // 关注
                StringBuilder gzSql = new StringBuilder();
                gzSql.append("INSERT INTO gch_user_attention_car_model (id, user_id, car_model_id)\n");
                gzSql.append("VALUES\n");
                gzSql.append("('" + genUUID() + "','" + userId + "','" + carModelId + "')\n");
                List<Integer> into = create.fetch(gzSql.toString()).into(int.class);

                if (into.isEmpty()) {
                    return success(1, "关注成功");
                } else {
                    return error(-1, "关注失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:车款详情页问题咨询
     * @Params:carBrandId;carModelId;question;questionType;from;userId;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/7 10:13
     * @Version 2.0
     */
    @POST
    @Path("carQuestion")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carQuestion(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n");
            sql.append("id \n");
            sql.append("FROM \n");
            sql.append("gch_car_question \n");
            sql.append(" WHERE \n");
            sql.append("car_brand_id = '" + getJsonAsString(json, "carBrandId") + "' \n");
            sql.append("AND car_model_id = '" + getJsonAsString(json, "carModelId") + "' \n");
            sql.append("AND createuser = '" + getJsonAsString(json, "userId") + "' \n");
            sql.append("AND question = '" + getJsonAsString(json, "question") + "' \n");
            sql.append("AND question_type = '" + getJsonAsString(json, "questionType") + "'\n");
            sql.append("AND isdelete = 0 \n");
            sql.append("LIMIT 1\n");
            Record record = create.fetchOne(sql.toString());
            if (record == null) {
                StringBuilder insertSql = new StringBuilder();
                insertSql.append("INSERT INTO gch_car_question ( \n");
                insertSql.append("id,\n");
                insertSql.append("car_brand_id,\n");
                insertSql.append("car_model_id,\n");
                insertSql.append("question,\n");
                insertSql.append("question_type,\n");
                insertSql.append("`from`,\n");
                insertSql.append("createuser\n");
                insertSql.append(")\n");
                insertSql.append("VALUES\n");
                insertSql.append("('" + genUUID() + "', '" + getJsonAsString(json, "carBrandId") + "', '" + getJsonAsString(json, "carModelId") + "', '" + getJsonAsString(json, "question") + "', '" + getJsonAsString(json, "questionType") + "', '" + getJsonAsString(json, "from") + "', '" + getJsonAsString(json, "userId") + "')\n");
                List<Integer> into = create.fetch(insertSql.toString()).into(int.class);
                if (into.isEmpty()) {
                    return success(1, "问题咨询成功");
                } else {
                    return error(-1, "问题咨询失败");
                }
            } else {
                return error(-1, "请勿重复提交数据");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:意见反馈
     * @Params:username;type;tel;email;view;userId
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/28 14:29
     * @Version 2.0
     */
    @POST
    @Path("carUserQuestion")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carUserQuestion(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String email = getJsonAsString(json, "email");
            String view = getJsonAsString(json, "view");
            String type = getJsonAsString(json, "type");
            if (email.length() == 0 || view.length() == 0 || type.length() == 0) {
                return error(-1, "数据不能为空！");
            } else {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT \n");
                sql.append("id \n");
                sql.append("FROM \n");
                sql.append("gch_online_feedback \n");
                sql.append(" WHERE \n");
                sql.append("type = '" + getJsonAsString(json, "type") + "' \n");
                sql.append("AND email = '" + getJsonAsString(json, "email") + "' \n");
                sql.append("AND view = '" + getJsonAsString(json, "view") + "' \n");
                sql.append("AND isdelete = 0 \n");
                sql.append("LIMIT 1\n");
                Record record = create.fetchOne(sql.toString());
                if (record == null) {
                    StringBuilder insertSql = new StringBuilder();
                    insertSql.append("INSERT INTO gch_online_feedback ( \n");
                    insertSql.append("id,\n");
                    insertSql.append("username,\n");
                    insertSql.append("type,\n");
                    insertSql.append("tel,\n");
                    insertSql.append("email,\n");
                    insertSql.append("`view`,\n");
                    insertSql.append("createuser\n");
                    insertSql.append(")\n");
                    insertSql.append("VALUES\n");
                    insertSql.append("('" + genUUID() + "', '" + getJsonAsString(json, "username") + "', '" + getJsonAsString(json, "type") + "', '" + getJsonAsString(json, "tel") + "', '" + getJsonAsString(json, "email") + "', '" + getJsonAsString(json, "view") + "', '" + getJsonAsString(json, "userId") + "')\n");
                    List<Integer> into = create.fetch(insertSql.toString()).into(int.class);
                    if (into.isEmpty()) {
                        return success(1, "意见反馈成功");
                    } else {
                        return error(-1, "意见反馈失败");
                    }
                } else {
                    return error(-1, "请勿重复提交数据");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @Description:问题咨询列表页
     * @Params:carModelId;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/8 13:42
     * @Version 2.0
     */
    @POST
    @Path("carQuestionList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String carQuestionList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            String Sql = "SELECT INSERT (gug.user_name, 4, 4, '****') AS userName,gcq.question,DATE_FORMAT(gcq.createtime,\"%Y-%m-%d %H:%i\") AS createTime,gcq.reply,DATE_FORMAT(gcq.updatetime,\"%Y-%m-%d %H:%i\") AS replyTime\n" +
                    "FROM gch_car_question AS gcq\n" +
                    "LEFT JOIN gch_user_general AS gug ON gug.id = gcq.createuser\n" +
                    "AND gcq.isdelete = 0\n" +
                    "AND gug.isdelete = 0\n" +
                    "WHERE gcq.car_model_id='" + getJsonAsString(json, "carModelId") + "' AND gcq.isdelete=0\n" +
                    "ORDER BY createTime DESC";
            List<Car_Question> result = create.fetch(Sql).into(Car_Question.class);

            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}

