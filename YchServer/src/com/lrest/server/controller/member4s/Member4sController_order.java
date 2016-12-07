package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Member4s_orderlist;
import com.lrest.server.services.DB;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

@Path("/4s/order")
public class Member4sController_order extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private SessionManager sessionManager;
/**
    * @Description:4S店会员获取定车订单列表
    * @Return:
    * @Author: 潘玉婷 @panyuting
    * @Date: 2016/9/19 11:18
    * @Version 2.0
    */
    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String orderlist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            JsonArray status = json.get("status").getAsJsonArray();
            List inparams = new ArrayList();
            for (JsonElement x : status) {
                inparams.add(x);

            }
            List<Member4s_orderlist> result_pay = create.select(GCH_PAY.ID,GCH_PAY.OUT_TRADE_NO,GCH_PAY.CARSTYLE,GCH_PAY.EXTERIOR_COLOR_NAME,GCH_PAY.INTERIOR_COLOR_NAME,GCH_PAY.STATUS,GCH_PAY.EXTERIOR_IMG,GCH_PAY.BUYER_NAME,GCH_PAY.BUYER_TEL,GCH_PAY.BUY_TIME,GCH_PAY.LOW_PRICE,GCH_PAY.MONEY,GCH_PAY.CREATETIME)
                    .from(GCH_PAY)
                    .where(GCH_PAY.PAY_OBJ.eq((byte)2).and(GCH_PAY.ISDELETE.eq(0)).and(GCH_PAY.ID_4S.eq(getJsonAsString(json, "id_4s"))).and(GCH_PAY.STATUS.in(inparams)).and(GCH_PAY.CARSTYLE.concat(GCH_PAY.OUT_TRADE_NO,GCH_PAY.BUYER_NAME,GCH_PAY.BUYER_TEL).like("%"+getJsonAsString(json,"keyword")+"%")))
                    .orderBy(GCH_PAY.CREATETIME.desc())
                    .limit(Integer.parseInt(getJsonAsString(json, "pagenum")))
                    .offset((Integer.parseInt(getJsonAsString(json, "page"))-1)*Integer.parseInt(getJsonAsString(json, "pagenum")))
                    .fetch()
                    .into(Member4s_orderlist.class);

            // 添加订单的总数
            int count =create.selectCount()
                    .from(GCH_PAY)
                    .where(GCH_PAY.PAY_OBJ.eq((byte)2).and(GCH_PAY.ISDELETE.eq(0)).and(GCH_PAY.ID_4S.eq(getJsonAsString(json, "id_4s"))).and(GCH_PAY.STATUS.in(inparams)).and(GCH_PAY.CARSTYLE.concat(GCH_PAY.OUT_TRADE_NO,GCH_PAY.BUYER_NAME,GCH_PAY.BUYER_TEL).like("%"+getJsonAsString(json,"keyword")+"%")))
                    .fetchOne(0, int.class);

            BaseCount<Member4s_orderlist> resultData =  new BaseCount<>();
            resultData.count = count;
            resultData.rows = result_pay;

            return success(resultData);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


}
