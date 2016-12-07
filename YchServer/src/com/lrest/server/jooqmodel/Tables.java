/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel;


import com.lrest.server.jooqmodel.tables.GchArea;
import com.lrest.server.jooqmodel.tables.GchAuthCode;
import com.lrest.server.jooqmodel.tables.GchBrand;
import com.lrest.server.jooqmodel.tables.GchCar;
import com.lrest.server.jooqmodel.tables.GchCarActivities;
import com.lrest.server.jooqmodel.tables.GchCarLifeBbs;
import com.lrest.server.jooqmodel.tables.GchCarLifeThumbs;
import com.lrest.server.jooqmodel.tables.GchCarModel;
import com.lrest.server.jooqmodel.tables.GchCarModelPrefer;
import com.lrest.server.jooqmodel.tables.GchCarPrefer;
import com.lrest.server.jooqmodel.tables.GchCarPrice;
import com.lrest.server.jooqmodel.tables.GchCity;
import com.lrest.server.jooqmodel.tables.GchCoin;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice;
import com.lrest.server.jooqmodel.tables.GchProvince;
import com.lrest.server.jooqmodel.tables.GchReceiptAddress;
import com.lrest.server.jooqmodel.tables.GchSalesArea;
import com.lrest.server.jooqmodel.tables.GchScore;
import com.lrest.server.jooqmodel.tables.GchScoreExchange;
import com.lrest.server.jooqmodel.tables.GchSpecialPriceCar;
import com.lrest.server.jooqmodel.tables.GchSpecialPriceCarTrend;
import com.lrest.server.jooqmodel.tables.GchUserActivity;
import com.lrest.server.jooqmodel.tables.GchUserAttentionCarModel;
import com.lrest.server.jooqmodel.tables.GchUserGeneral;
import com.lrest.server.jooqmodel.tables.GchUser_4s;
import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.jooqmodel.tables.GchViewSpecialPriceCar;
import com.lrest.server.jooqmodel.tables.V3UserDevice;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in gouchehui2.0
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 4S所在区域
     */
    public static final GchArea GCH_AREA = com.lrest.server.jooqmodel.tables.GchArea.GCH_AREA;

    /**
     * The table <code>gouchehui2.0.gch_auth_code</code>.
     */
    public static final GchAuthCode GCH_AUTH_CODE = com.lrest.server.jooqmodel.tables.GchAuthCode.GCH_AUTH_CODE;

    /**
     * 品牌表
     */
    public static final GchBrand GCH_BRAND = com.lrest.server.jooqmodel.tables.GchBrand.GCH_BRAND;

    /**
     * 车款表
     */
    public static final GchCar GCH_CAR = com.lrest.server.jooqmodel.tables.GchCar.GCH_CAR;

    /**
     * The table <code>gouchehui2.0.gch_car_activities</code>.
     */
    public static final GchCarActivities GCH_CAR_ACTIVITIES = com.lrest.server.jooqmodel.tables.GchCarActivities.GCH_CAR_ACTIVITIES;

    /**
     * The table <code>gouchehui2.0.gch_car_life_bbs</code>.
     */
    public static final GchCarLifeBbs GCH_CAR_LIFE_BBS = com.lrest.server.jooqmodel.tables.GchCarLifeBbs.GCH_CAR_LIFE_BBS;

    /**
     * The table <code>gouchehui2.0.gch_car_life_thumbs</code>.
     */
    public static final GchCarLifeThumbs GCH_CAR_LIFE_THUMBS = com.lrest.server.jooqmodel.tables.GchCarLifeThumbs.GCH_CAR_LIFE_THUMBS;

    /**
     * 车型表
     */
    public static final GchCarModel GCH_CAR_MODEL = com.lrest.server.jooqmodel.tables.GchCarModel.GCH_CAR_MODEL;

    /**
     * 4s用户车型喜好表
     */
    public static final GchCarModelPrefer GCH_CAR_MODEL_PREFER = com.lrest.server.jooqmodel.tables.GchCarModelPrefer.GCH_CAR_MODEL_PREFER;

    /**
     * 车款表
     */
    public static final GchCarPrefer GCH_CAR_PREFER = com.lrest.server.jooqmodel.tables.GchCarPrefer.GCH_CAR_PREFER;

    /**
     * 报价表
     */
    public static final GchCarPrice GCH_CAR_PRICE = com.lrest.server.jooqmodel.tables.GchCarPrice.GCH_CAR_PRICE;

    /**
     * 4S店所在城市
     */
    public static final GchCity GCH_CITY = com.lrest.server.jooqmodel.tables.GchCity.GCH_CITY;

    /**
     * 积分详情表
     */
    public static final GchCoin GCH_COIN = com.lrest.server.jooqmodel.tables.GchCoin.GCH_COIN;

    /**
     * 询价定车支付表
     */
    public static final GchPay GCH_PAY = com.lrest.server.jooqmodel.tables.GchPay.GCH_PAY;

    /**
     * 订单中区域最低价表
     */
    public static final GchPayAreaLowPrice GCH_PAY_AREA_LOW_PRICE = com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE;

    /**
     * 4S店所在省份
     */
    public static final GchProvince GCH_PROVINCE = com.lrest.server.jooqmodel.tables.GchProvince.GCH_PROVINCE;

    /**
     * The table <code>gouchehui2.0.gch_receipt_address</code>.
     */
    public static final GchReceiptAddress GCH_RECEIPT_ADDRESS = com.lrest.server.jooqmodel.tables.GchReceiptAddress.GCH_RECEIPT_ADDRESS;

    /**
     * 销售区域表（底价车、特价车）
     */
    public static final GchSalesArea GCH_SALES_AREA = com.lrest.server.jooqmodel.tables.GchSalesArea.GCH_SALES_AREA;

    /**
     * 积分详情表
     */
    public static final GchScore GCH_SCORE = com.lrest.server.jooqmodel.tables.GchScore.GCH_SCORE;

    /**
     * The table <code>gouchehui2.0.gch_score_exchange</code>.
     */
    public static final GchScoreExchange GCH_SCORE_EXCHANGE = com.lrest.server.jooqmodel.tables.GchScoreExchange.GCH_SCORE_EXCHANGE;

    /**
     * 特价车
     */
    public static final GchSpecialPriceCar GCH_SPECIAL_PRICE_CAR = com.lrest.server.jooqmodel.tables.GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR;

    /**
     * 特价车
     */
    public static final GchSpecialPriceCarTrend GCH_SPECIAL_PRICE_CAR_TREND = com.lrest.server.jooqmodel.tables.GchSpecialPriceCarTrend.GCH_SPECIAL_PRICE_CAR_TREND;

    /**
     * 用户表
     */
    public static final GchUser_4s GCH_USER_4S = com.lrest.server.jooqmodel.tables.GchUser_4s.GCH_USER_4S;

    /**
     * The table <code>gouchehui2.0.gch_user_activity</code>.
     */
    public static final GchUserActivity GCH_USER_ACTIVITY = com.lrest.server.jooqmodel.tables.GchUserActivity.GCH_USER_ACTIVITY;

    /**
     * 用户关注车型表
     */
    public static final GchUserAttentionCarModel GCH_USER_ATTENTION_CAR_MODEL = com.lrest.server.jooqmodel.tables.GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL;

    /**
     * 用户表
     */
    public static final GchUserGeneral GCH_USER_GENERAL = com.lrest.server.jooqmodel.tables.GchUserGeneral.GCH_USER_GENERAL;

    /**
     * VIEW
     */
    public static final GchViewCarPrice GCH_VIEW_CAR_PRICE = com.lrest.server.jooqmodel.tables.GchViewCarPrice.GCH_VIEW_CAR_PRICE;

    /**
     * VIEW
     */
    public static final GchViewSpecialPriceCar GCH_VIEW_SPECIAL_PRICE_CAR = com.lrest.server.jooqmodel.tables.GchViewSpecialPriceCar.GCH_VIEW_SPECIAL_PRICE_CAR;

    /**
     * ios android web
     */
    public static final V3UserDevice V3_USER_DEVICE = com.lrest.server.jooqmodel.tables.V3UserDevice.V3_USER_DEVICE;
}