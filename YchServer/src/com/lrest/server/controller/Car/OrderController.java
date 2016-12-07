package com.lrest.server.controller.Car;

/**
 * Created by panyuting on 16/6/21.
 */

import com.google.gson.*;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.Tables;

import com.lrest.server.jooqmodel.tables.*;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;

import com.lrest.server.jooqmodel.tables.records.GchUserActivityRecord;
import com.lrest.server.models.*;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;


import com.lrest.server.services.weixin.WXAPIConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.jooq.tools.json.JSONObject;
import org.jooq.types.UByte;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.*;
import static com.tencent.common.RandomStringGenerator.getRandomStringByLength;
import static com.tencent.common.Signature.getSign;


import static com.tencent.common.XMLParser.getMapFromXML;
import static java.util.stream.Collectors.*;

@Path("/nl/order")
public class OrderController extends BaseController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    @POST
    @Path("inquiry/list/{user_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String inquirylist(@PathParam("user_id") String user_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            GchPay pay = new GchPay();
            GchPayAreaLowPrice paylowprice = new GchPayAreaLowPrice();
            String result = create.select(pay.ID, pay.OUT_TRADE_NO, pay.EXTERIOR_IMG.replace("type", "small"), pay.CARSTYLE, GCH_CAR.CAR_MODEL_ID, pay.CAR_ID, pay.EXTERIOR_COLOR_ID, pay.EXTERIOR_COLOR_NAME, pay.EXTERIOR_COLOR_VALUE, pay.INTERIOR_COLOR_ID, pay.INTERIOR_COLOR_NAME, pay.INTERIOR_COLOR_VALUE, pay.CREATETIME, pay.MONEY, pay.STATUS, pay.FROM_ACTIVITYID, paylowprice.CAR_PRICE_ID, paylowprice.AREA_NAME, paylowprice.PAY_LOW_PRICE, GCH_CAR.AUTH_PRICE)
                    .from(pay)
                    .join(paylowprice)
                    .on(pay.ID.eq(paylowprice.PAY_ID).and(pay.ISDELETE.eq(0)).and(paylowprice.ISDELETE.eq(0)))
                    .join(GCH_CAR)
                    .on(GCH_CAR.ID.eq(pay.CAR_ID).and(GCH_CAR.ISDELETE.eq(0)))
                    .where(pay.PAY_OBJ.in((byte) 1, (byte) 3).and(pay.USER_ID.eq(user_id)))
                    .orderBy(pay.CREATETIME.desc())
                    .fetch()
                    .formatJSON();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    /**
     * @Description:移动端询价订单（第一次免费询价，后续缴费）
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/9/28 9:42
     * @Version 2.0
     */
    @POST
    @Path("/inquiry/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String inquiry_add(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            JsonArray bpcities = json.get("bpcities").getAsJsonArray();

//判断数据内有没有这样的询价订单
            GchPay pay = new GchPay();
            GchPayAreaLowPrice paylowprice = new GchPayAreaLowPrice();
            GchViewCarPrice viewcarprice = new GchViewCarPrice();
            Record result_carinfo = create.select()
                    .from(viewcarprice)
                    .where(viewcarprice.ID.eq(getJsonAsString(json, "car_price_id")).and(viewcarprice.ISDELETE.eq(0)))
                    .limit(1)
                    .fetchOne();
            Result<Record1<String>> result_payinfo = create.select(pay.ID)
                    .from(pay)
                    .where(pay.USER_ID.eq(getJsonAsString(json, "user_id")).and(pay.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID))).and(pay.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(pay.PAY_OBJ.in((byte) 1, (byte) 3)).and(pay.STATUS.eq(1)))
                    .fetch();
            for (Record r : result_payinfo) {
                Result<Record1<String>> result_paylowprice = create.select(paylowprice.AREA_NAME)
                        .from(paylowprice)
                        .where(paylowprice.PAY_ID.eq(r.getValue(pay.ID)).and(paylowprice.PAY_OBJ.in(Byte.valueOf("1"), Byte.valueOf("3"))))
                        .fetch();


                if (bpcities.size() == result_paylowprice.size()) {
                    int count = 0;
                    for (Record re : result_paylowprice) {
                        for (int i = 0; i < bpcities.size(); i++) {
                            if (re.getValue(paylowprice.AREA_NAME).equals(bpcities.get(i).getAsJsonArray().get(1).getAsString())) {
                                count++;
                            }
                        }
                    }
                    if (bpcities.size() == count) {
                        return error(-1, "data  exists");
                    }
                }

            }

            //查找该用户之前是否询价过
            Record1<String> result_inquiry = create.select(pay.ID)
                    .from(pay)
                    .where(pay.USER_ID.eq(getJsonAsString(json, "user_id")).and(pay.PAY_OBJ.eq((byte) 1)).and(pay.ISDELETE.eq(0)).and(pay.STATUS.between(1, 2)))
                    .limit(1)
                    .fetchOne();
            //数据库内没有这样的数据时，写进数据库
            double money = 0;
            if (bpcities.size() == 1) {
                money = 99;
            } else if (bpcities.size() == 2) {
                money = 119;
            } else if (bpcities.size() == 3) {
                money = 139;
            }
            int isfirst = 0;
            GchPayRecord record = create.newRecord(GCH_PAY);
            record.setId(genUUID());
            record.setOutTradeNo(createOrderNo());
            record.setUserId(getJsonAsString(json, "user_id"));
            record.setCarstyle(result_carinfo.getValue(viewcarprice.BRAND_NAME) + result_carinfo.getValue(viewcarprice.CAR_MODEL_NAME) + result_carinfo.getValue(viewcarprice.CAR_NAME));
            record.setCarId(result_carinfo.getValue(viewcarprice.CAR_ID));
            record.setCarName(result_carinfo.getValue(viewcarprice.CAR_NAME));
            record.setExteriorColorId(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID));
            record.setExteriorColorName(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_NAME));
            record.setExteriorColorValue(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_VALUE));
            record.setExteriorImg(result_carinfo.getValue(viewcarprice.EXTERIOR_IMG));
            record.setInteriorColorId(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID));
            record.setInteriorColorName(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_NAME));
            record.setInteriorColorValue(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_VALUE));
            record.setMoney(BigDecimal.valueOf(money));
            record.setPayIp(getIpAddr(req));
            record.setCallback("/index.php/pay/payback");
            record.setUrl("/index.php/car/car_price_pay/pid/" + record.getId());
            record.setPayObj((byte) 1);
            record.setStatus(0);
            record.setUpdatetime(String.valueOf(System.currentTimeMillis() / 1000));
            record.setType(1);
            record.setFromOrder("移动端");
            record.setCardPlace(getJsonAsString(json, "upCardCityName"));
            if (result_inquiry == null) {
                record.setPayWay("用户第一次免费询价");
                record.setStatus(1);
                isfirst = 1;
            }
            int insert = record.insert();//执行insert sql

            if (insert == 1) {
                GchPayAreaLowPriceRecord recordarea = create.newRecord(GCH_PAY_AREA_LOW_PRICE);
           /*     GchProvince province = new GchProvince();
                GchCity city = new GchCity();*/
                for (int i = 0; i < bpcities.size(); i++) {
                    if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 1) {
                        //查找全国最底价
                        Record result_lowprice = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();
                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowprice.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowprice.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowprice.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName("全国");
                        recordarea.setSalesAreaLevel(1);
                        recordarea.setPayObj((byte) 1);
//                        recordarea.setCardPlace(getJsonAsString(json, "upCardCityName"));
                    } else if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 2) {
                        Record result_lowpricepro = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)).and(viewcarprice.PROVINCE_NAME.eq(bpcities.get(i).getAsJsonArray().get(1).getAsString())))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();

                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowpricepro.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowpricepro.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowpricepro.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName(bpcities.get(i).getAsJsonArray().get(1).getAsString());
                        recordarea.setSalesAreaLevel(2);
                        recordarea.setPayObj((byte) 1);
//                        recordarea.setCardPlace(getJsonAsString(json, "upCardCityName"));

                    } else if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 3) {
                        Record result_lowpricecity = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)).and(viewcarprice.CITY_NAME.eq(bpcities.get(i).getAsJsonArray().get(1).getAsString())))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();
                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowpricecity.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowpricecity.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowpricecity.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName(bpcities.get(i).getAsJsonArray().get(1).getAsString());
                        recordarea.setSalesAreaLevel(3);
                        recordarea.setPayObj((byte) 1);
//                        recordarea.setCardPlace(getJsonAsString(json, "upCardCityName"));
                    }

                    int inseta = recordarea.insert();//执行insert sql


                }

            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("Id", record.getId());
            map.put("isfirst", String.valueOf(isfirst));
            return success(map);

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    /**
     * @Description:微信免费询价订单
     * @Return:
     * @Author: 潘玉婷 @panyuting
     * @Date: 2016/9/28 16:35
     * @Version 2.0
     */
    @POST
    @Path("/inquiry/addfree")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String inquiry_addfree(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            JsonArray bpcities = json.get("bpcities").getAsJsonArray();

            //判断数据内有没有这样的询价订单
            GchPay pay = new GchPay();
            GchPayAreaLowPrice paylowprice = new GchPayAreaLowPrice();
            GchViewCarPrice viewcarprice = new GchViewCarPrice();
            Record result_carinfo = create.select()
                    .from(viewcarprice)
                    .where(viewcarprice.ID.eq(getJsonAsString(json, "car_price_id")).and(viewcarprice.ISDELETE.eq(0)))
                    .limit(1)
                    .fetchOne();
            Result<Record1<String>> result_payinfo = create.select(pay.ID)
                    .from(pay)
                    .where(pay.USER_ID.eq(getJsonAsString(json, "user_id")).and(pay.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID))).and(pay.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(pay.PAY_OBJ.in((byte) 1, (byte) 3)).and(pay.STATUS.eq(1)))
                    .fetch();
            for (Record r : result_payinfo) {
                Result<Record1<String>> result_paylowprice = create.select(paylowprice.AREA_NAME)
                        .from(paylowprice)
                        .where(paylowprice.PAY_ID.eq(r.getValue(pay.ID)).and(paylowprice.PAY_OBJ.in(Byte.valueOf("1"), Byte.valueOf("3"))))
                        .fetch();
                if (bpcities.size() == result_paylowprice.size()) {
                    int count = 0;
                    for (Record re : result_paylowprice) {
                        for (int i = 0; i < bpcities.size(); i++) {
                            if (re.getValue(paylowprice.AREA_NAME).equals(bpcities.get(i).getAsJsonArray().get(1).getAsString())) {
                                count++;
                            }
                        }
                    }
                    if (bpcities.size() == count) {
                        return error(-1, "data  exists");
                    }
                }

            }


            GchPayRecord record = create.newRecord(GCH_PAY);
            record.setId(genUUID());
            record.setOutTradeNo(createOrderNo());
            record.setUserId(getJsonAsString(json, "user_id"));
            record.setCarstyle(result_carinfo.getValue(viewcarprice.BRAND_NAME) + result_carinfo.getValue(viewcarprice.CAR_MODEL_NAME) + result_carinfo.getValue(viewcarprice.CAR_NAME));
            record.setCarId(result_carinfo.getValue(viewcarprice.CAR_ID));
            record.setCarName(result_carinfo.getValue(viewcarprice.CAR_NAME));
            record.setExteriorColorId(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID));
            record.setExteriorColorName(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_NAME));
            record.setExteriorColorValue(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_VALUE));
            record.setExteriorImg(result_carinfo.getValue(viewcarprice.EXTERIOR_IMG));
            record.setInteriorColorId(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID));
            record.setInteriorColorName(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_NAME));
            record.setInteriorColorValue(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_VALUE));
            record.setMoney(BigDecimal.valueOf(0));
            record.setPayIp(getIpAddr(req));
            record.setCallback("/index.php/pay/payback");
            record.setUrl("/index.php/car/car_price_pay/pid/" + record.getId());
            record.setPayObj((byte) 3);
            record.setUpdatetime(String.valueOf(System.currentTimeMillis() / 1000));
            record.setType(1);
            record.setFromOrder(getJsonAsString(json, "fromorder"));
            record.setFromActivityid(Integer.valueOf(getJsonAsString(json, "fromacticityid")));
            record.setCardPlace(getJsonAsString(json, "upCardCityName"));
            record.setPayWay("微信端免费询价");
            record.setStatus(1);

            int insert = record.insert();//执行insert sql

            if (insert == 1) {
                GchPayAreaLowPriceRecord recordarea = create.newRecord(GCH_PAY_AREA_LOW_PRICE);
                for (int i = 0; i < bpcities.size(); i++) {
                    if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 1) {
                        //查找全国最底价
                        Record result_lowprice = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();
                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowprice.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowprice.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowprice.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName("全国");
                        recordarea.setSalesAreaLevel(1);
                        recordarea.setPayObj((byte) 3);
                    } else if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 2) {
                        Record result_lowpricepro = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)).and(viewcarprice.PROVINCE_NAME.eq(bpcities.get(i).getAsJsonArray().get(1).getAsString())))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();

                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowpricepro.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowpricepro.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowpricepro.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName(bpcities.get(i).getAsJsonArray().get(1).getAsString());
                        recordarea.setSalesAreaLevel(2);
                        recordarea.setPayObj((byte) 3);

                    } else if (bpcities.get(i).getAsJsonArray().get(0).getAsInt() == 3) {
                        Record result_lowpricecity = create.select()
                                .from(viewcarprice)
                                .where(viewcarprice.EXTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.EXTERIOR_COLOR_ID)).and(viewcarprice.INTERIOR_COLOR_ID.eq(result_carinfo.getValue(viewcarprice.INTERIOR_COLOR_ID))).and(viewcarprice.ISDELETE.eq(0)).and(viewcarprice.CITY_NAME.eq(bpcities.get(i).getAsJsonArray().get(1).getAsString())))
                                .orderBy(viewcarprice.LOW_PRICE.asc())
                                .limit(1)
                                .fetchOne();
                        recordarea.setId(genUUID());
                        recordarea.setPayId(record.getId());
                        recordarea.setCarPriceId(result_lowpricecity.getValue(viewcarprice.ID));
                        recordarea.setId_4s(result_lowpricecity.getValue(viewcarprice.USER_ID));
                        recordarea.setPayLowPrice(result_lowpricecity.getValue(viewcarprice.LOW_PRICE));
                        recordarea.setAreaName(bpcities.get(i).getAsJsonArray().get(1).getAsString());
                        recordarea.setSalesAreaLevel(3);
                        recordarea.setPayObj((byte) 3);
                    }
                    recordarea.insert();//执行insert sql
                }
            }

            return success(record.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    @POST
    @Path("inquiry/details/{pay_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String inquiry_details(@PathParam("pay_id") String pay_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            GchPay pay = new GchPay();
            GchPayAreaLowPrice paylowprice = new GchPayAreaLowPrice();
            String Result = create.select()
                    .from(pay)
                    .join(paylowprice)
                    .on(pay.ID.eq(paylowprice.PAY_ID).and(pay.ISDELETE.eq(0)).and(paylowprice.ISDELETE.eq(0)))
                    .where(pay.ID.eq(pay_id))
                    .fetch()
                    .formatJSON();

            return successJson(Result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }

    @POST
    @Path("weixin/jsapi/{pay_id}/{open_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String jsapi(@PathParam("pay_id") String pay_id, @PathParam("open_id") String open_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            GchPay pay = new GchPay();
            Record7<String, String, BigDecimal, Byte, String, String, Timestamp> result = create.select(pay.ID, pay.OUT_TRADE_NO, pay.MONEY, pay.PAY_OBJ, pay.PAY_IP, pay.CALLBACK, pay.CREATETIME)
                    .from(pay)
                    .where(pay.ID.eq(pay_id).and(pay.ISDELETE.eq(0)))
                    .limit(1)
                    .fetchOne();

            //JSon params = new JsonObject();  // 创建一个json对象
            weixin_unifiedorder params = new weixin_unifiedorder();
            params.appid = WXAPIConfig.AppID;
            params.mch_id = WXAPIConfig.MCH_ID;
            params.device_info = "WEB";
            params.nonce_str = getRandomStringByLength(32);

            if (result.getValue(pay.PAY_OBJ) == 1) {
                params.body = "咨询底价支付";
            } else if (result.getValue(pay.PAY_OBJ) == 2) {
                params.body = "订金支付";
            } else if (result.getValue(pay.PAY_OBJ) == 4) {
                params.body = "一元抢（双十一）";
            }
            params.out_trade_no = result.getValue(pay.OUT_TRADE_NO);
            params.total_fee = result.getValue(pay.MONEY).multiply(BigDecimal.valueOf(100)).intValue();
            params.spbill_create_ip = result.getValue(pay.PAY_IP);
            params.notify_url = "https://www.woodche.com/YchLrestServerWxorder/server/nl/order/weixin/notify";
            params.trade_type = "JSAPI";
            params.openid = open_id;
            String sign = getSign(params);
            params.sign = sign;
            XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
            xStreamForRequestPostData.alias("xml", weixin_unifiedorder.class);
            String postDataXML = xStreamForRequestPostData.toXML(params);

            try {
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/xml;charset=UTF-8"), postDataXML);

                String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String ret = response.body().string();
                    Map<String, Object> info = getMapFromXML(ret);
                    long seconds = System.currentTimeMillis() / 1000;
                    Map<String, String> mapjs = new HashMap<String, String>();
                    mapjs.put("appId", WXAPIConfig.AppID);
                    mapjs.put("timeStamp", String.valueOf(seconds));
                    mapjs.put("nonceStr", getRandomStringByLength(32));
                    mapjs.put("package", "prepay_id=" + info.get("prepay_id"));
                    mapjs.put("signType", "MD5");

                    info.put("appId", mapjs.get("appId"));
                    info.put("timeStamp", mapjs.get("timeStamp"));
                    info.put("nonceStr", mapjs.get("nonceStr"));
                    info.put("package", mapjs.get("package"));
                    info.put("signType", mapjs.get("signType"));
                    info.put("paySign", getSign(mapjs));
                    info.put("gch_out_trade_no", result.getValue(pay.OUT_TRADE_NO));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    info.put("gch_createtime", format.format(result.getValue(pay.CREATETIME).getTime()));
                    GchPayRecord record = create.newRecord(GCH_PAY);
                    record.setId(pay_id);
                    record.setStatus(9);
                    record.update();

                    return success(info);
                } else {
                    // String ret=response.body().string();
                    return error(-1, response.toString());
                    ///log.error("Unexpected code " + response);
                    //throw new IOException("Unexpected code " + response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }


    }

    @POST
    @Path("inquiry/cancel/{pay_id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String inquiry_cancel(@PathParam("pay_id") String pay_id) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            long seconds = System.currentTimeMillis() / 1000;
            Record result = create.select(GCH_PAY.ID, GCH_PAY.STATUS)
                    .from(GCH_PAY)
                    .where(GCH_PAY.ID.eq(pay_id))
                    .limit(1)
                    .fetchOne();
            if (result != null && result.getValue(1).equals(9)) {
                GchPayRecord record = create.newRecord(GCH_PAY);
                record.setId(pay_id);
                record.setStatus(11);
                record.setUpdatetime(String.valueOf(seconds));
                int upd = record.update();
                return success(upd);
            } else {
                return error(-1, "更新前的状态必须是支付中");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }

    }


}
