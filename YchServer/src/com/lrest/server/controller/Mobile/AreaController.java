package com.lrest.server.controller.Mobile;

import com.lrest.server.controller.BaseController;
import com.lrest.server.models.Mobile.Mobile_Area;
import com.lrest.server.services.DB;
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
import java.util.List;

/**
 * DESCRIPTION: 区域
 *
 * @Author 韩武洽
 * @Date 2016-11
 * @Time 02 17:06
 **/
@Path("mobile/area")
public class AreaController extends BaseController{

    /**
     * @Description:
     * @Return:  找到销售地区
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/10/28 10:05
     * @Version 2.0
     */
    @POST
    @Path("findOnSellArea")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findOnSellArea() {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            StringBuilder mobileAreaSql = new StringBuilder();

            mobileAreaSql.append(" SELECT \n");
            mobileAreaSql.append(" city.id, \n");
            mobileAreaSql.append(" city.city_name AS name, \n");
            mobileAreaSql.append(" \"3\" AS level \n");
            mobileAreaSql.append(" FROM gch_city AS city \n");
            mobileAreaSql.append(" LEFT JOIN gch_province AS province ON city.province_id = province.id \n");
            mobileAreaSql.append(" GROUP BY city.id \n");
            mobileAreaSql.append(" UNION \n");
            mobileAreaSql.append(" SELECT \n");
            mobileAreaSql.append(" province.id, \n");
            mobileAreaSql.append(" province.province_name AS name, \n");
            mobileAreaSql.append(" \"2\" AS level \n");
            mobileAreaSql.append(" FROM gch_city AS city \n");
            mobileAreaSql.append(" LEFT JOIN gch_province AS province ON city.province_id = province.id \n");
            mobileAreaSql.append(" GROUP BY province.id \n");

            mobileAreaSql.append(" UNION \n");

            mobileAreaSql.append(" SELECT \n");
            mobileAreaSql.append(" \"001\" AS id, \n");
            mobileAreaSql.append(" \"国家\" AS name, \n");
            mobileAreaSql.append(" \"1\" AS level  \n");
            mobileAreaSql.append(" FROM ( \n");
            mobileAreaSql.append(" SELECT id \n");
            mobileAreaSql.append(" FROM gch_city AS city \n");
            mobileAreaSql.append(" LIMIT 0,1 \n");
            mobileAreaSql.append(" ) AS city \n");
            List<Mobile_Area> rets = create.fetch(mobileAreaSql.toString()).into(Mobile_Area.class);
            return success(rets);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
