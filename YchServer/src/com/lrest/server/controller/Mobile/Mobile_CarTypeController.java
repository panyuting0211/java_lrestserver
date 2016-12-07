package com.lrest.server.controller.Mobile;

import com.lrest.server.controller.BaseController;
import com.lrest.server.models.Mobile.Mobile_BaseCarPrice;
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

/**
 * DESCRIPTION: 车款的类型
 * @Author 韩武洽
 * @Date 2016-11
 * @Time 09 10:43
 **/
@Path("mobile/carType")
public class Mobile_CarTypeController extends BaseController {

    /**
     * @Description: 查询出所有车款的类型
     * Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/9 10:58
     * @Version 2.0
     */
    @POST
    @Path("findAllCarType")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findAllCarType() {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            // 查询出所有车款的类型
            StringBuilder indexPriceSql = new StringBuilder();
            indexPriceSql.append(" SELECT \n");
            indexPriceSql.append(" carType.id AS carTypeId, \n");
            indexPriceSql.append(" carType.car_type_name AS carTypeName, \n");
            indexPriceSql.append(" CASE \n");
            indexPriceSql.append(" WHEN carType.logo IS NULL THEN NULL \n");
            indexPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(carType.logo,\"type\",\"small\")) \n");
            indexPriceSql.append(" END AS carTypeImage \n");
            indexPriceSql.append(" FROM gch_car_type AS carType \n");
            indexPriceSql.append(" WHERE carType.isdelete = 0 \n");

            return success(create.fetch(indexPriceSql.toString()).into(Mobile_BaseCarPrice.class));
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

}
