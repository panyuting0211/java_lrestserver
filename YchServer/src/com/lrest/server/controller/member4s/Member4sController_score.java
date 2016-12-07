package com.lrest.server.controller.member4s;

/**
 * Created by acans on 16/6/21.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchScore;
import com.lrest.server.jooqmodel.tables.GchScoreExchange;
import com.lrest.server.jooqmodel.tables.records.GchScoreExchangeRecord;
import com.lrest.server.jooqmodel.tables.records.GchScoreRecord;
import com.lrest.server.jooqmodel.tables.records.GchUser_4sRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Member4s_orderlist;
import com.lrest.server.models.Member4s_scorelist;
import com.lrest.server.services.DB;
import com.tencent.common.MD5;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.createOrderNo;
import static com.lrest.server.utils.UtilBase.genUUID;
import static com.lrest.server.utils.UtilBase.getJsonAsString;

@Path("/4s/score")
public class Member4sController_score extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Inject
//    private SessionManager sessionManager;

    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String orderlist(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            List<Member4s_scorelist> result_score = create.select(GCH_SCORE.SCORE, GCH_SCORE.INFO, GCH_SCORE.CREATETIME,GCH_SCORE_EXCHANGE.OUT_TRADE_NO,GCH_SCORE_EXCHANGE.TELPHONE)
                    .from(GCH_SCORE)
                    .leftJoin(GCH_SCORE_EXCHANGE)
                    .on(GCH_SCORE.FROM_ID.eq(GCH_SCORE_EXCHANGE.ID).and(GCH_SCORE_EXCHANGE.ISDELETE.eq(0)).and(GCH_SCORE.FLAG.eq(1)))
                    .where(GCH_SCORE.USER_ID.eq(getJsonAsString(json, "user_id")).and(GCH_SCORE.ISDELETE.eq(0)))
                    .orderBy(GCH_SCORE.CREATETIME.desc())
                    .limit(Integer.parseInt(getJsonAsString(json, "pagenum")))
                    .offset((Integer.parseInt(getJsonAsString(json, "page")) - 1) * Integer.parseInt(getJsonAsString(json, "pagenum")))
                    .fetch()
                    .into(Member4s_scorelist.class);
            int count = create.selectCount()
                    .from(GCH_SCORE)
                    .where(GCH_SCORE.USER_ID.eq(getJsonAsString(json, "user_id")).and(GCH_SCORE.ISDELETE.eq(0)))
                    .fetchOne(0, int.class);
            BaseCount<Member4s_scorelist> resultData = new BaseCount<>();
            resultData.count = count;
            resultData.rows = result_score;

            return success(resultData);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("times/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String times(@PathParam("user_id") String user_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            Record result_alltimes = create.select(GCH_USER_4S.EXCHANGE_ALLTIMES)
                    .from(GCH_USER_4S)
                    .where(GCH_USER_4S.ID.eq(user_id).and(GCH_USER_4S.ISDELETE.eq(0)))
                    .fetchOne();
            Record result_usedtimes = create.select(GCH_USER_4S.EXCHANGE_USEDTIMES)
                    .from(GCH_USER_4S)
                    .where(GCH_USER_4S.ID.eq(user_id).and(GCH_USER_4S.ISDELETE.eq(0)))
                    .fetchOne();
            int times = result_alltimes.getValue(GCH_USER_4S.EXCHANGE_ALLTIMES) - result_usedtimes.getValue(GCH_USER_4S.EXCHANGE_USEDTIMES);
            Map<String, String> map = new HashMap<String, String>();
            map.put("grade", String.valueOf(result_alltimes.getValue(GCH_USER_4S.EXCHANGE_ALLTIMES)));
            map.put("times", String.valueOf(times));

            return success(map);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("values/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String values(@PathParam("user_id") String user_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            String result = create.select(GCH_USER_4S.TOTAL_JIFEN)
                    .from(GCH_USER_4S)
                    .where(GCH_USER_4S.ID.eq(user_id).and(GCH_USER_4S.ISDELETE.eq(0)))
                    .fetch()
                    .formatJSON();

            return successJson(result);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String add(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            String tel=getJsonAsString(json,"tel");
            String code=getJsonAsString(json,"code");
            String md5code= MD5.MD5Encode(code+tel);
            log.debug("组合的md5="+md5code);
            log.debug("原始的md5="+getJsonAsString(json,"md5code"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String[] aa = {"", ""};
            create.transaction(configuration -> {
                if (md5code.equals(getJsonAsString(json,"md5code"))) {
                    Record result_user = create.select(GCH_USER_4S.USER_NAME, GCH_USER_4S.EXCHANGE_ALLTIMES, GCH_USER_4S.EXCHANGE_USEDTIMES, GCH_USER_4S.TOTAL_JIFEN)
                            .from(GCH_USER_4S)
                            .where(GCH_USER_4S.ID.eq(getJsonAsString(json, "user_id")).and(GCH_USER_4S.ISDELETE.eq(0)))
                            .fetchOne();
                    int total_jifen = result_user.getValue(GCH_USER_4S.TOTAL_JIFEN);
                    int alltimes = result_user.getValue(GCH_USER_4S.EXCHANGE_ALLTIMES);
                    int usedtimes = result_user.getValue(GCH_USER_4S.EXCHANGE_USEDTIMES);
                    int times = alltimes - usedtimes;
                    if (times > 0) {
                        GchScoreExchangeRecord record = create.newRecord(GCH_SCORE_EXCHANGE);
                        record.setId(genUUID());
                        record.setOutTradeNo(createOrderNo());
                        record.setUserId(getJsonAsString(json, "user_id"));
                        record.setRole(Byte.valueOf(getJsonAsString(json, "role")));
                        record.setTelphone(getJsonAsString(json, "tel"));
                        record.setGoodsId(getJsonAsString(json, "goods_id"));
                        record.setGoodsName(getJsonAsString(json, "goods_name"));
                        record.setScoreValue(Integer.valueOf(getJsonAsString(json, "score_value")));
                        record.setAddressId(getJsonAsString(json, "address_id"));
                        int inst = record.insert();
                        if (inst == 1) {
                            GchUser_4sRecord recorduser = create.newRecord(GCH_USER_4S);
                            recorduser.setId(getJsonAsString(json, "user_id"));
                            recorduser.setTotalJifen(total_jifen - Integer.valueOf(getJsonAsString(json, "score_value")));
                            recorduser.setExchangeUsedtimes(usedtimes + 1);
                            recorduser.setUpdatetime(Timestamp.valueOf(format.format(System.currentTimeMillis())));
                            recorduser.update();

                            GchScoreRecord recordscore = create.newRecord(GCH_SCORE);
                            recordscore.setId(genUUID());
                            recordscore.setUserId(getJsonAsString(json, "user_id"));
                            recordscore.setRole(Byte.valueOf(getJsonAsString(json, "role")));
                            recordscore.setScore(-Integer.valueOf(getJsonAsString(json, "score_value")));
                            recordscore.setInfo("兑换" + getJsonAsString(json, "goods_name") + ",消耗" + getJsonAsString(json, "score_value") + "积分");
                            recordscore.setCreateuser(result_user.getValue(GCH_USER_4S.USER_NAME));
                            recordscore.setFlag(1);
                            recordscore.setFromId(record.getId());
                            recordscore.insert();

                            //成功
                            aa[0] = "1";
                            aa[1] = "兑换成功";
                        } else {
                            //失败
                            aa[0] = "2";
                            aa[1] = "兑换失败";
                        }

                    } else {
                        //次数不够
                        aa[0] = "3";
                        aa[1] = "本月兑换次数已使用完";
                    }
                }else
                {
                    //验证码错误
                    aa[0] = "4";
                    aa[1] = "验证码错误";
                }

            });
            return success(aa);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }

}
