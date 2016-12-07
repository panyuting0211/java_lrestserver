package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Mobile.Mobile_Ad;
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

/**
 * DESCRIPTION: 轮播图
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 13:36
 **/
@Path("mobile/ad")
public class Mobile_AdControlller extends BaseController{

    /**
     * @param query {"query":{"pagenum":10,"page":1}}
     * @Description:
     * @Return:  找到所有可视的轮播图
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/27 14:19
     * @Version 2.0
     */
    @POST
    @Path("findAllShowAdByMobile")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findAllShowAdByMobile(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();;
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");

            // 查询出车型的SQL语句
            StringBuilder carModelSql = new StringBuilder();
            carModelSql.append(" SELECT id, \n");
            carModelSql.append(" ad_name AS adName, \n");
            carModelSql.append(" CASE \n");
            carModelSql.append(" WHEN imgUrl IS NULL THEN NULL \n");
            carModelSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(imgurl,\"type\",\"small\")) \n");
            carModelSql.append(" END AS imgUrl, \n");
            carModelSql.append(" adurl AS adUrl \n");
            carModelSql.append(" FROM gch_ad \n");
            carModelSql.append(" WHERE type = 1\n");
            carModelSql.append(" AND status = 1 \n");
            carModelSql.append(" AND isdelete = 0 \n");
            carModelSql.append(" ORDER BY alif \n");

            List<Mobile_Ad> rets = create.fetch(carModelSql.toString()).into(Mobile_Ad.class);

            BaseCount<Mobile_Ad> resultData =  new BaseCount<>();
            resultData.count = (int)rets.stream().count();
            resultData.rows = rets.stream().skip((page - 1) * pagenum)
                    .limit(pagenum).collect(Collectors.toList());

            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
