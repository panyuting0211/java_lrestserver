package com.lrest.server.controller.Mobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lrest.server.controller.BaseController;
import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.models.BaseCount;
import com.lrest.server.models.Mobile.Mobile_BaseCarPrice;
import com.lrest.server.models.Mobile.Mobile_BasePay;
import com.lrest.server.services.Config;
import com.lrest.server.services.DB;
import com.lrest.server.utils.Constants;
import com.lrest.server.utils.DateUtils;
import com.tencent.common.MD5;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.lrest.server.jooqmodel.Tables.*;
import static com.lrest.server.utils.UtilBase.*;

/**
 * DESCRIPTION:
 *
 * @Author 韩武洽
 * @Date 2016-11
 * @Time 11 15:39
 **/
@Path("mobile/order")
public class Mobile_OrderController extends BaseController {

    public static final String PAY_FROM_ORDER = "Mobile端";
    /** 上牌地点: 不限城市*/
    public static final String PAY_CARD_PLACE_NINI = "不限城市";
    /**支付对象: 一键询价*/
    public static final Byte PAY_CARD_PLACE_KEY_INQUIRY = 3;

    /**车辆类型: 底价车*/
    public static final Integer PAY_TYPE_CARPRICE = 1;
    /**车辆类型: 特价车 */
    public static final Integer PAY_TYPE_CARSPECIAL = 2;


    /**我的订单: 购车订单*/
    public static final Integer ISPAYCAR_INQUIRY = 2;
    /**我的订单: 询价订单*/
    public static final Integer ISPAYCAR_PAYCAR = 1;


    /** 支付状态: 1已支付（未完善） */
    public static final Integer PAY_STATUS_PAID = 1;

    /**
     * @param query  userId: 如果是免登录 userId不可传
      {
        "query":{
            "interiorColorId":"000a9d1430458c3bfadf04e730040ffb",
            "exteriorColorId":"33370",
            "cellphone":"187518526911",
            "signInNo":"123456",
            "carPriceId":"a3507382be2dd729b94d1b41097c67c2",
            "carId":"7391",
            "userId":"0e7d07c240e24eb18b58c205679638e0",
            "userRemark":"备注...."
        }
    }
     * @Description: Mobile端 一键查询底价
     * @Return:
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/11/14 10:48
     * @Version 2.0
     */
    @POST
    @Path("/freeFindCarPrice")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String freeFindCarPrice(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {

            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();

            String userId = getJsonAsString(json, "userId");
            String carId = getJsonAsString(json, "carId");
            String carPriceId = getJsonAsString(json, "carPriceId");
            String interiorColorId = getJsonAsString(json, "interiorColorId");
            String exteriorColorId = getJsonAsString(json, "exteriorColorId");
            String cellphone = getJsonAsString(json, "cellphone");

            String signInNo = getJsonAsString(json, "signInNo");
            String userRemark = getJsonAsString(json, "userRemark");
            String password = MD5.MD5Encode(Constants.GENERAL_PASSWD);

            // 如果已经登录
            if (userId == null) {
                // 查询出手机号对应的用户是否存在
                Record rr = new Mobile_LoginController().findGeneralUserByCell(create,cellphone);

                if (rr != null ) {
                    return error(-2,"该手机号已存在,请直接登录!");
                } else {
                    // 查询 免费查询底价车的验证码
                    Record r = new AuthCodeController().checkSignInNoIsValid(
                            create,cellphone,signInNo,AuthCodeController.AUTHCODE_TYPE_FREESELECTCARPRICE);
                    if (r == null) {
                        return  error(-1,"验证码不正确");
                    } else {
                        // 添加个人用户
                        GchUserGeneralRecord userGeneral = create.newRecord(GCH_USER_GENERAL);
                        String uuId = genUUID();
                        userGeneral.setId(uuId);
                        userGeneral.setUserName(cellphone);
                        userGeneral.setPassword(password);
                        userGeneral.setTel(cellphone);
                        userGeneral.setRole(Constants.GENERAL_ROLE_GENERALUSER);
                        userGeneral.setCreatetime(DateUtils.millisecondChangeTimestamp());
                        userGeneral.setIsdelete(0);
                        userGeneral.setUpdatetime(DateUtils.millisecondChangeTimestamp());
                        userGeneral.insert();
                        userId = uuId;
                    }
                }
            }

            // 查询出出售车的CarPrice信息,方便添加Pay信息
            StringBuffer carPriceSql = new StringBuffer();
            carPriceSql.append(" SELECT * \n");
            carPriceSql.append(" FROM gch_view_car_price AS carPrice \n");
            carPriceSql.append(" WHERE 1 = 1 \n");
            carPriceSql.append(" AND carPrice.car_id = '" + carId + "' \n");
            carPriceSql.append(" AND carPrice.interior_color_id = '" + interiorColorId + "' \n");
            carPriceSql.append(" AND carPrice.exterior_color_id = '" + exteriorColorId + "' \n");
            carPriceSql.append(" AND carPrice.isdelete = 0 \n");
            carPriceSql.append(" LIMIT 1 \n");
            Record carPriceRecord = create.fetchOne(carPriceSql.toString());

            // 查询出当前内饰,外观颜色的车款的最底价
            StringBuffer lowPriceSql = new StringBuffer();
            lowPriceSql.append(" SELECT * \n");
            lowPriceSql.append(" FROM \n");
            lowPriceSql.append(" gch_view_car_price AS carPrice \n");
            lowPriceSql.append(" WHERE 1 = 1 \n");
            lowPriceSql.append(" AND carPrice.isdelete = 0 \n");
            lowPriceSql.append(" AND carPrice.interior_color_id = '" + interiorColorId + "' \n");
            lowPriceSql.append(" AND carPrice.exterior_color_id = '" + exteriorColorId + "' \n");
            lowPriceSql.append(" ORDER BY carPrice.low_price \n");
            lowPriceSql.append(" LIMIT 1 \n");
            Mobile_BaseCarPrice lowPriceRecord = create.fetchOne(lowPriceSql.toString()).into(Mobile_BaseCarPrice.class);

                // carPrice的视图
                GchViewCarPrice viewCarPrice = new GchViewCarPrice();

                GchPayRecord addPayRecord = create.newRecord(GCH_PAY);
                addPayRecord.setId(genUUID());
                // 订单号
                addPayRecord.setOutTradeNo(createOrderNo());
                addPayRecord.setUserId(userId);
                addPayRecord.setCarstyle(carPriceRecord.getValue(viewCarPrice.BRAND_NAME) + carPriceRecord.getValue(viewCarPrice.CAR_MODEL_NAME) + carPriceRecord.getValue(viewCarPrice.CAR_NAME));
                addPayRecord.setCarId(carPriceRecord.getValue(viewCarPrice.CAR_ID));
                addPayRecord.setCarName(carPriceRecord.getValue(viewCarPrice.CAR_NAME));
                addPayRecord.setExteriorColorId(carPriceRecord.getValue(viewCarPrice.EXTERIOR_COLOR_ID));
                addPayRecord.setExteriorColorName(carPriceRecord.getValue(viewCarPrice.EXTERIOR_COLOR_NAME));
                addPayRecord.setExteriorColorValue(carPriceRecord.getValue(viewCarPrice.EXTERIOR_COLOR_VALUE));
                addPayRecord.setExteriorImg(carPriceRecord.getValue(viewCarPrice.EXTERIOR_IMG));
                addPayRecord.setInteriorColorId(carPriceRecord.getValue(viewCarPrice.INTERIOR_COLOR_ID));
                addPayRecord.setInteriorColorName(carPriceRecord.getValue(viewCarPrice.INTERIOR_COLOR_NAME));
                addPayRecord.setInteriorColorValue(carPriceRecord.getValue(viewCarPrice.INTERIOR_COLOR_VALUE));
                addPayRecord.setMoney(BigDecimal.valueOf(0));
                addPayRecord.setPayIp(getIpAddr(req));
                addPayRecord.setCallback("/index.php/pay/payback");
                addPayRecord.setUrl("/index.php/car/car_price_pay/pid/" + addPayRecord.getId());
                addPayRecord.setPayObj(PAY_CARD_PLACE_KEY_INQUIRY);
                addPayRecord.setUpdatetime(String.valueOf(System.currentTimeMillis() / 1000));
                addPayRecord.setType(PAY_TYPE_CARPRICE);
                addPayRecord.setFromOrder(PAY_FROM_ORDER);
                addPayRecord.setUserRemark(userRemark);
                // 底价车详情Id
                addPayRecord.setCarPriceId(carPriceId);
                addPayRecord.setCardPlace(PAY_CARD_PLACE_NINI);
                addPayRecord.setPayWay("Mobile端免费询价");
                addPayRecord.setStatus(PAY_STATUS_PAID);
                addPayRecord.insert();//执行insert sql


                // 查询出当前内饰,外观颜色的车款的最底价
                StringBuffer seleLowPriceSql = new StringBuffer();
                seleLowPriceSql.append(" SELECT * \n");
                seleLowPriceSql.append(" FROM \n");
                seleLowPriceSql.append(" gch_view_car_price AS carPrice \n");
                seleLowPriceSql.append(" WHERE 1 = 1 \n");
                seleLowPriceSql.append(" AND carPrice.isdelete = 0 \n");
                seleLowPriceSql.append(" AND carPrice.interior_color_id = '" + interiorColorId + "' \n");
                seleLowPriceSql.append(" AND carPrice.exterior_color_id = '" + exteriorColorId + "' \n");
                seleLowPriceSql.append(" ORDER BY carPrice.low_price \n");
                seleLowPriceSql.append(" LIMIT 1 \n");
                Record seleLowPriceRecord = create.fetchOne(seleLowPriceSql.toString());

                GchPayAreaLowPriceRecord recordarea = create.newRecord(GCH_PAY_AREA_LOW_PRICE);
                recordarea.setId(genUUID());
                recordarea.setPayId(addPayRecord.getId());
                recordarea.setCarPriceId(seleLowPriceRecord.getValue(viewCarPrice.ID));
                recordarea.setId_4s(seleLowPriceRecord.getValue(viewCarPrice.USER_ID));
                recordarea.setPayLowPrice(seleLowPriceRecord.getValue(viewCarPrice.LOW_PRICE));
                recordarea.setAreaName("不限地区");
                recordarea.setSalesAreaLevel(1);
                recordarea.setPayObj((byte) 3);
                recordarea.insert();//执行insert sql
               return  success();
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }


    @POST
    @Path("findMyOrder")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON)
    public String findMyOrder(String query) {
        try (Connection conn = DB.getConnection();
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            JsonObject json = new JsonParser().parse(query).getAsJsonObject().get("query").getAsJsonObject();
            Integer pagenum = getJsonAsInt(json, "pagenum");
            Integer page = getJsonAsInt(json, "page");
            String isPayCar = getJsonAsString(json, "isPayCar");
            String userId = getJsonAsString(json, "userId");

            // 查询出车型的SQL语句
            StringBuilder carPriceSql = new StringBuilder();
            carPriceSql.append(" SELECT \n");
            carPriceSql.append(" brand.id AS brandId, \n");
            carPriceSql.append(" brand.brand_name AS brandName, \n");

            carPriceSql.append(" carModel.id AS carModelId, \n");
            carPriceSql.append(" carModel.car_model_name AS carModelName, \n");

            carPriceSql.append(" pay.car_id AS carId, \n");
            carPriceSql.append(" pay.car_name AS carName, \n");
            carPriceSql.append(" CASE \n");
            carPriceSql.append(" WHEN pay.exterior_img IS NULL THEN NULL \n");
            carPriceSql.append(" ELSE CONCAT('" + Config.OSS + "',REPLACE(pay.exterior_img,\"type\",\"small\")) \n");
            carPriceSql.append(" END AS carImage, \n");
            carPriceSql.append(" pay.interior_color_id AS interiorColorId, \n");
            carPriceSql.append(" pay.interior_color_name AS interiorColorName, \n");
            carPriceSql.append(" pay.interior_color_value AS interiorColorValue, \n");

            carPriceSql.append(" pay.exterior_color_id AS exteriorColorId, \n");
            carPriceSql.append(" pay.exterior_color_name AS exteriorColorName, \n");
            carPriceSql.append(" pay.exterior_color_value AS exteriorColorValue, \n");

            carPriceSql.append(" pay.out_trade_no AS outTradeNo, \n");
            carPriceSql.append(" pay.carstyle AS carStyle, \n");
            carPriceSql.append(" pay.money, \n");
            carPriceSql.append(" pay.order_money AS orderMoney, \n");
            carPriceSql.append(" pay.ticket_money AS ticketMoney, \n");
            carPriceSql.append(" pay.status, \n");

            carPriceSql.append(" pay.status_track AS statusTrack, \n");
            carPriceSql.append(" pay.pay_obj AS payObj, \n");
            carPriceSql.append(" pay.buy_time AS buyTime, \n");
            carPriceSql.append(" pay.user_remark AS userRemark, \n");
            carPriceSql.append(" pay.createtime, \n");
            carPriceSql.append(" pay.user_id AS userId, \n");

            carPriceSql.append(" carPrice.is_xunjia AS isXunjia, \n");
            carPriceSql.append(" carPrice.price AS carPricePrice, \n");
            carPriceSql.append(" carPrice.discount AS discount, \n");
            carPriceSql.append(" carPrice.low_price AS lowPrice, \n");

            carPriceSql.append(" specialCar.special_price AS specialPrice, \n");
            carPriceSql.append(" specialCar.price AS specialCarPrice, \n");
            carPriceSql.append(" specialCar.start_date AS startDate, \n");
            carPriceSql.append(" specialCar.end_date AS endDate \n");

            carPriceSql.append(" FROM \n");
            carPriceSql.append(" gch_pay AS pay \n");
            carPriceSql.append(" LEFT JOIN gch_car AS car ON pay.car_id = car.id \n");
            carPriceSql.append(" LEFT JOIN gch_car_model AS carModel ON carModel.id = car.car_model_id \n");
            carPriceSql.append(" LEFT JOIN gch_brand AS brand ON carModel.brand_id = brand.id \n");
            carPriceSql.append(" LEFT JOIN gch_car_price AS carPrice ON carPrice.id = pay.car_price_id \n");
            carPriceSql.append(" LEFT JOIN gch_special_price_car AS specialCar ON specialCar.id = pay.car_special_id \n");

            carPriceSql.append(" WHERE 1 = 1 \n");
            carPriceSql.append(" AND pay.isdelete = 0 \n");
            if (ISPAYCAR_PAYCAR.equals(isPayCar)) {
                carPriceSql.append(" AND pay.pay_obj in (2) \n");
            } else if (ISPAYCAR_INQUIRY.equals(isPayCar)) {
                carPriceSql.append(" AND pay.pay_obj in (1,3,4) \n");
            } else {
                // 我的订单只有两种结果 1和2 错误的值 查询结果为null
                carPriceSql.append(" AND 1 = 2 \n");
            }
            carPriceSql.append(" AND pay.user_id = '" + userId + "' \n");
            carPriceSql.append(" AND carModel.id IS NOT NULL \n");

            List<Mobile_BasePay> rets = create.fetch(carPriceSql.toString()).into(Mobile_BasePay.class);
            BaseCount<Mobile_BasePay> resultData = new BaseCount<>();
            resultData.count = (int) rets.stream().count();
            resultData.rows = rets.stream().skip((page - 1) * pagenum)
                    .limit(pagenum).collect(Collectors.toList());
            return success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return error(-1, e.getMessage());
        }
    }
}
