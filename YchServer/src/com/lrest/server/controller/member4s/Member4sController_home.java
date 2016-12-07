package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.records.V3UserDeviceRecord;
import com.lrest.server.models.Car_activity;
import com.lrest.server.models.Car_details;
import com.lrest.server.models.Member4s_home;
import com.lrest.server.models.Userinfo_4s;
import com.lrest.server.services.DB;
import com.lrest.server.services.session.SessionManager;
import com.lrest.server.utils.UtilBase;
import com.tencent.common.MD5;
import eu.bitwalker.useragentutils.UserAgent;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static com.lrest.server.jooqmodel.Tables.GCH_CAR;
import static com.lrest.server.jooqmodel.Tables.GCH_VIEW_CAR_PRICE;
import static com.lrest.server.jooqmodel.Tables.V3_USER_DEVICE;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

@Path("/4s/home")
public class Member4sController_home extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private SessionManager sessionManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get() {
        return success("4s");
    }
    /**
        * @Description:4S店会员中心首页
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 13:28
        * @Version 2.0
        */
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String index(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String user_id = getJsonAsString(json, "user_id");


            Member4s_home homelist = new Member4s_home();
            //String sql = "select id,activity_name,activity_number,DATE_FORMAT(from_unixtime(starttime),\"%Y-%m-%d\" ) starttime,DATE_FORMAT(from_unixtime(endtime),\"%Y-%m-%d\" ) endtime  from gch_activities where id=" + activity_id;
            String sql = "select count(id) as deposit from gch_pay where id_4s='"+user_id+"' and status in(1,3,4,5) and pay_obj=2";
            int deposit =  create.fetchOne(sql).into(int.class);//已付定金

            String sql1 = "select count(id) as mentioncars from gch_pay where id_4s='"+user_id+"' and status=7 and pay_obj=2";
            int mentioncars =  create.fetchOne(sql1).into(int.class);//提车中

            String  sql2 = "select count(id) as library from gch_pay where id_4s='"+user_id+"' and status=6 and pay_obj=2";
            int library =  create.fetchOne(sql2).into(int.class);//出库中

            homelist.baseinfo.deposit = deposit;
            homelist.baseinfo.mentioncars = mentioncars;
            homelist.baseinfo.library = library;

            //最新订车订单
            sql = "select id,out_trade_no,carstyle,exterior_color_name,interior_color_name,status,exterior_img,buyer_name,buyer_tel,buy_time,low_price,money,createtime from gch_pay where id_4s='"+user_id+"' and pay_obj=2 AND status != 0 AND status != 2 AND status != 10 order by createtime desc limit 1";
            if (create.fetch(sql).equals(null)|| create.fetch(sql).isEmpty()) {
                homelist.neworder  = null;
            } else {
                homelist.neworder  = create.fetchOne(sql).into(Member4s_home.newOrder.class);
            }

            //最新报价
            sql = "select id,car_model_name,car_name,car_id,exterior_color_name,exterior_color_id,interior_color_name,interior_color_id,price,discount,low_price,stock,onway from gch_view_car_price where user_id='"+user_id+"' and  isdelete=0 order by createtime desc limit 1";
            if (create.fetch(sql).equals(null)|| create.fetch(sql).isEmpty()) {
                homelist.newprice = null;
            } else {
                homelist.newprice = create.fetchOne(sql).into(Member4s_home.newPrice.class);
            }

            //销售区域
            List<Member4s_home.priceArea> areas = null;
            if (homelist.newprice == null) {
                areas = null;
            } else {
                sql ="select id,sales_area_name from gch_sales_area where car_price_id='"+ homelist.newprice.id+"'and isdelete=0";
                if (create.fetch(sql).equals(null)|| create.fetch(sql).isEmpty()) {
                    areas = null;
                } else {
                    areas = create.fetch(sql).into(Member4s_home.priceArea.class);
                }
            }
            homelist.pricearea = areas;

            //最新活动车款特价车
            sql ="select id,car_model_name,car_name,car_id,exterior_color_name,exterior_color_id,interior_color_name,interior_color_id,auth_price,price,special_price,start_date,end_date,number,status from gch_view_special_price_car where user_id='"+ user_id+"'and isdelete=0 order by start_date desc limit 1";
            if (create.fetch(sql).equals(null)|| create.fetch(sql).isEmpty()) {
                homelist.newactivity = null;
            } else {
                homelist.newactivity = create.fetchOne(sql).into(Member4s_home.newActivity.class);
            }

            //销售区域
            if (null == homelist.newactivity) {
                homelist.specialpricearea = null;
            } else {
                sql ="select id,sales_area_name from gch_sales_area where car_special_id='"+ homelist.newactivity.id+"'and isdelete=0";
                List<Member4s_home.specialPriceArea> specialareas = null;
                if (create.fetch(sql).equals(null)|| create.fetch(sql).isEmpty()) {
                    specialareas = null;
                } else {
                    specialareas = create.fetch(sql).into(Member4s_home.specialPriceArea.class);
                }
                homelist.specialpricearea = specialareas;
            }
            return success(homelist);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
