package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.Base_Idname;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lrest.server.utils.UtilBase.getJsonAsString;

/**
 * DESCRIPTION:
 * @Author 韩武洽
 * @Date 2016-11
 * @Time 03 10:11
 **/
@Path("mobile/carColorImage")
public class Mobile_CarColorImageController extends BaseController{

    // 汽车图片分类: 外观
    public static final Integer CARCOLORIMAGE_EXTERIORCOLOR = 0;
    // 汽车图片分类: 内饰
    public static final Integer CARCOLORIMAGE_INTERIORCOLORID = 1;
    // 汽车图片分类: 空间
    public static final Integer CARCOLORIMAGE_SPACE = 2;
    // 汽车图片分类: 细节
    public static final Integer CARCOLORIMAGE_DETAIL = 3;


    /**
     * @param query
    {
        "query":
        {
            "carId":"1777",
            "interiorColorId":"3604a3ffcba314a003292f6c938e48b4",
            "exteriorColorId":"26172"
        }
    }
    0: 外观 1: 内饰 2:空间 3:细节
     * @Description:
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/3 10:29
     * @Version 2.0
     */
    @POST
    @Path("findCarImageByQuery")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findCarImageByQuery(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String carId = getJsonAsString(json, "carId");
            String interiorColorId = getJsonAsString(json, "interiorColorId");
            String exteriorColorId = getJsonAsString(json, "exteriorColorId");

            Map<Integer,List<Base_Idname>> map = new HashMap<>();
            // 汽车图片分类: 外观
            map.put(CARCOLORIMAGE_EXTERIORCOLOR,
                    findCarImageByQuery(create,carId,
                            interiorColorId,exteriorColorId,CARCOLORIMAGE_EXTERIORCOLOR));
            // 汽车图片分类: 内饰
            map.put(CARCOLORIMAGE_INTERIORCOLORID,
                    findCarImageByQuery(create,carId,
                            interiorColorId,exteriorColorId,CARCOLORIMAGE_INTERIORCOLORID));
            // 汽车图片分类: 空间
            map.put(CARCOLORIMAGE_SPACE,
                    findCarImageByQuery(create,carId,
                            interiorColorId,exteriorColorId,CARCOLORIMAGE_SPACE));
            // 汽车图片分类: 细节
            map.put(CARCOLORIMAGE_DETAIL,
                    findCarImageByQuery(create,carId,
                            interiorColorId,exteriorColorId,CARCOLORIMAGE_DETAIL));
            return success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    /**
     * @param create DSLContext
     * @param carId 车款的Id
     * @param interiorColorId 内饰
     * @param exteriorColorId 外观
     * @param type 类型 0: 外观 1: 内饰 2:空间 3:细节
     * @Description:
     * @Return:  根据查询条件 查询出车分类的图片
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/3 10:32
     * @Version 2.0
     */
    public List<Base_Idname> findCarImageByQuery(
            DSLContext create, String carId,
            String interiorColorId, String exteriorColorId, Integer type){

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append(" carColorImage.id, \n");
        sql.append(" CASE \n");
        sql.append(" WHEN carColorImage.imgurl IS NULL THEN NULL \n");
        sql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(carColorImage.imgurl,\"type\",\"small\")) \n");
        sql.append(" END AS name \n");
        sql.append(" FROM gch_car_color_image AS carColorImage \n");
        sql.append(" WHERE carColorImage.car_id = '" + carId + "' \n");
        sql.append(" AND carColorImage.exterior_color_id = '" + exteriorColorId + "' \n");
        sql.append(" AND carColorImage.interior_color_id = '" + interiorColorId + "' \n");
        sql.append(" AND carColorImage.isdelete = 0 \n");
        sql.append(" AND carColorImage.type ='" + type + "' \n");
        return create.fetch(sql.toString()).into(Base_Idname.class);
    }
}
