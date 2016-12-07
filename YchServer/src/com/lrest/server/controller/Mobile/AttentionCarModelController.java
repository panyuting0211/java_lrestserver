package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Mobile.Mobile_AttentionBrand;
import com.lrest.server.models.Mobile.Mobile_AttentionCarModel;
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
import static com.lrest.server.jooqmodel.Tables.*;

import static com.lrest.server.utils.UtilBase.getJsonAsInt;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION: Mobile 热门推荐
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 28 10:00
 **/
@Path("/mobile/attentionCarModel")
public class AttentionCarModelController extends BaseController{


    /**
     * @param query  参数如下: {"query":{"pagenum":10,"page":1}}
     * @Description:
     * @Return:  找到热门推荐的车款 根据询价量倒序排列
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 10:05
     * @Version 2.0
     */
    @POST
    @Path("findAttentionCarModelList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findAttentionCarModelList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
//            Integer pagenum = getJsonAsInt(json, "pagenum");
//            Integer page = getJsonAsInt(json, "page");

            StringBuilder attentionCarModelSql = new StringBuilder();

            attentionCarModelSql.append(" SELECT \n");
            attentionCarModelSql.append(" brand.id AS brandId, \n");
            attentionCarModelSql.append(" brand.brand_name AS brandName, \n");
            attentionCarModelSql.append(" carModel.id AS carModelId, \n");
            attentionCarModelSql.append(" carModel.car_model_name AS carModelName, \n");
            attentionCarModelSql.append(" CASE \n");
            attentionCarModelSql.append(" WHEN carModel.imgurl IS NULL THEN NULL \n");
            attentionCarModelSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(carModel.imgurl ,\"type\",\"small\")) \n");
            attentionCarModelSql.append(" END AS carModelImageUrl, \n");
            attentionCarModelSql.append(" attentionCarModel.inquiryAmount, \n");
            attentionCarModelSql.append(" carPrice.interior_color_id AS interiorColorId, \n");
            attentionCarModelSql.append(" carPrice.exterior_color_id AS exteriorColorId, \n");
            attentionCarModelSql.append(" carPrice.car_id AS carId \n");

            attentionCarModelSql.append(" FROM( \n");
            attentionCarModelSql.append(" SELECT \n");
            attentionCarModelSql.append(" attentionCarModel.car_model_id, \n");
            attentionCarModelSql.append(" COUNT(0) AS inquiryAmount \n");
            attentionCarModelSql.append(" FROM gch_user_attention_car_model AS attentionCarModel \n");
            attentionCarModelSql.append(" WHERE attentionCarModel.isdelete = 0 \n");
            attentionCarModelSql.append(" AND attentionCarModel.car_model_id IS NOT NULL \n");
            attentionCarModelSql.append(" GROUP BY attentionCarModel.car_model_id) AS attentionCarModel \n");
            attentionCarModelSql.append(" LEFT JOIN gch_car_model AS carModel ON attentionCarModel.car_model_id = carModel.id \n");
            attentionCarModelSql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id \n");
            attentionCarModelSql.append(" LEFT JOIN ( \n");
            attentionCarModelSql.append(" SELECT \n");
            attentionCarModelSql.append(" carPrice.interior_color_id, \n");
            attentionCarModelSql.append(" carPrice.exterior_color_id, \n");
            attentionCarModelSql.append(" carPrice.car_id, \n");
            attentionCarModelSql.append(" carPrice.car_model_id \n");
            attentionCarModelSql.append(" FROM gch_view_car_price AS carPrice \n");
            attentionCarModelSql.append(" WHERE 1 = 1 \n");
            attentionCarModelSql.append(" GROUP BY carPrice.car_model_id \n");
            attentionCarModelSql.append(" ) AS carPrice ON carPrice.car_model_id = attentionCarModel.car_model_id \n");

            attentionCarModelSql.append(" WHERE 1 = 1 \n");
            attentionCarModelSql.append(" AND carModel.id IS NOT NULL \n");
            attentionCarModelSql.append(" ORDER BY inquiryAmount DESC \n");
            attentionCarModelSql.append(" LIMIT 6 \n");


            List<Mobile_AttentionCarModel> rets = create.fetch(attentionCarModelSql.toString()).into(Mobile_AttentionCarModel.class);
//            BaseCount<Mobile_AttentionCarModel> resultData =  new BaseCount<>();
//            resultData.count = (int)rets.stream().count();
//            resultData.rows = rets.stream().skip((page - 1) * pagenum)
//                    .limit(pagenum).collect(Collectors.toList());
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    /**
     * @param query  参数如下: {"query":{"pagenum":10,"page":1}}
     * @Description:
     * @Return:  找到热门品牌 根据询价量倒序排列
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 10:05
     * @Version 2.0
     */
    @POST
    @Path("findAttentionBrandList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findAttentionBrandList(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");

            StringBuilder attentionBrandSql = new StringBuilder();

            attentionBrandSql.append(" SELECT \n");
            attentionBrandSql.append(" brand.id AS brandId, \n");
            attentionBrandSql.append(" brand.brand_Name AS brandName, \n");
            attentionBrandSql.append(" COUNT(0) AS inquiryAmount, \n");
            attentionBrandSql.append(" CASE \n");
            attentionBrandSql.append(" WHEN brand.logo IS NULL THEN NULL \n");
            attentionBrandSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(brand.logo,\"type\",\"small\")) \n");
            attentionBrandSql.append(" END AS image \n");
            attentionBrandSql.append(" FROM gch_user_attention_car_model AS attentionCarModel \n");
            attentionBrandSql.append(" LEFT JOIN gch_car_model AS carModel ON attentionCarModel.car_model_id = carModel.id \n");
            attentionBrandSql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id \n");
            attentionBrandSql.append(" WHERE attentionCarModel.isdelete = 0 \n");
            attentionBrandSql.append(" AND carModel.id IS NOT NULL \n");
            attentionBrandSql.append(" AND brand.id IS NOT NULL \n");
            attentionBrandSql.append(" GROUP BY brand.id \n");
            attentionBrandSql.append(" ORDER BY inquiryAmount DESC \n");

            create.fetch(attentionBrandSql.toString());

            List<Mobile_AttentionBrand> rets = create.fetch(attentionBrandSql.toString()).into(Mobile_AttentionBrand.class);
            BaseCount<Mobile_AttentionBrand> resultData =  new BaseCount<>();
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
     * @Description:个人中心--关注的车型
     * @Params:userId;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/9 14:03
     * @Version 2.0
     */
    @POST
    @Path("userAttentionCarModelList")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String userAttentionCarModelList(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String Sql="SELECT\n" +
                    "\tguacm.id,gvc.brand_id,gvc.brand_name,gvc.car_model_id,gvc.car_model_name,MAX(gvc.auth_price) AS maxAuthPrice,MIN(gvc.auth_price) AS minAuthPrice,CONCAT('" + Config.OSS + "',REPLACE(gvc.car_model_imageurl ,\"type\",\"small\")) AS carModelImageUrl\n" +
                    "FROM\n" +
                    "\tgch_user_attention_car_model AS guacm\n" +
                    "RIGHT JOIN gch_view_car AS gvc ON gvc.car_model_id = guacm.car_model_id\n" +
                    "AND guacm.isdelete = 0\n" +
                    "AND gvc.isdelete = 0\n" +
                    "WHERE\n" +
                    "\tguacm.user_id = '"+getJsonAsString(json,"userId")+"'\n" +
                    "GROUP BY gvc.car_model_id";
            List<Mobile_AttentionCarModel> result=create.fetch(Sql).into(Mobile_AttentionCarModel.class);
            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    /**
     * @Description:个人中心--删除关注的车型
     * @Params:id;
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/11/9 14:37
     * @Version 2.0
     */
    @POST
    @Path("userAttentionCarModelDel")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String userAttentionCarModelDel(String query){
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

        /*    String Sql="delete from gch_user_attention_car_model where id='"+getJsonAsString(json,"id")+"'";
            List<Integer> delresult = create.fetch(Sql).into(int.class);*/
            int delresult = create.delete(GCH_USER_ATTENTION_CAR_MODEL)
                    .where(GCH_USER_ATTENTION_CAR_MODEL.ID.eq(getJsonAsString(json, "id")))
                    .execute();
            log.debug("delResult=============================================="+delresult);

            return success(delresult);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }


}
