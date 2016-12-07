/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel;


import com.lrest.server.jooqmodel.tables.GchArea;
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
import com.lrest.server.jooqmodel.tables.V3UserDevice;
import com.lrest.server.jooqmodel.tables.records.GchAreaRecord;
import com.lrest.server.jooqmodel.tables.records.GchBrandRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarActivitiesRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarLifeBbsRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarLifeThumbsRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarModelPreferRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarModelRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarPreferRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchCityRecord;
import com.lrest.server.jooqmodel.tables.records.GchCoinRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchProvinceRecord;
import com.lrest.server.jooqmodel.tables.records.GchReceiptAddressRecord;
import com.lrest.server.jooqmodel.tables.records.GchSalesAreaRecord;
import com.lrest.server.jooqmodel.tables.records.GchScoreExchangeRecord;
import com.lrest.server.jooqmodel.tables.records.GchScoreRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchSpecialPriceCarTrendRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserActivityRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserAttentionCarModelRecord;
import com.lrest.server.jooqmodel.tables.records.GchUserGeneralRecord;
import com.lrest.server.jooqmodel.tables.records.GchUser_4sRecord;
import com.lrest.server.jooqmodel.tables.records.V3UserDeviceRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>gouchehui2.0</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<GchCarActivitiesRecord, Integer> IDENTITY_GCH_CAR_ACTIVITIES = Identities0.IDENTITY_GCH_CAR_ACTIVITIES;
    public static final Identity<GchCarLifeThumbsRecord, Integer> IDENTITY_GCH_CAR_LIFE_THUMBS = Identities0.IDENTITY_GCH_CAR_LIFE_THUMBS;
    public static final Identity<GchCarPriceRecord, Integer> IDENTITY_GCH_CAR_PRICE = Identities0.IDENTITY_GCH_CAR_PRICE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<GchAreaRecord> KEY_GCH_AREA_PRIMARY = UniqueKeys0.KEY_GCH_AREA_PRIMARY;
    public static final UniqueKey<GchBrandRecord> KEY_GCH_BRAND_PRIMARY = UniqueKeys0.KEY_GCH_BRAND_PRIMARY;
    public static final UniqueKey<GchCarRecord> KEY_GCH_CAR_PRIMARY = UniqueKeys0.KEY_GCH_CAR_PRIMARY;
    public static final UniqueKey<GchCarActivitiesRecord> KEY_GCH_CAR_ACTIVITIES_PRIMARY = UniqueKeys0.KEY_GCH_CAR_ACTIVITIES_PRIMARY;
    public static final UniqueKey<GchCarLifeBbsRecord> KEY_GCH_CAR_LIFE_BBS_PRIMARY = UniqueKeys0.KEY_GCH_CAR_LIFE_BBS_PRIMARY;
    public static final UniqueKey<GchCarLifeThumbsRecord> KEY_GCH_CAR_LIFE_THUMBS_PRIMARY = UniqueKeys0.KEY_GCH_CAR_LIFE_THUMBS_PRIMARY;
    public static final UniqueKey<GchCarModelRecord> KEY_GCH_CAR_MODEL_PRIMARY = UniqueKeys0.KEY_GCH_CAR_MODEL_PRIMARY;
    public static final UniqueKey<GchCarModelPreferRecord> KEY_GCH_CAR_MODEL_PREFER_PRIMARY = UniqueKeys0.KEY_GCH_CAR_MODEL_PREFER_PRIMARY;
    public static final UniqueKey<GchCarPreferRecord> KEY_GCH_CAR_PREFER_PRIMARY = UniqueKeys0.KEY_GCH_CAR_PREFER_PRIMARY;
    public static final UniqueKey<GchCarPriceRecord> KEY_GCH_CAR_PRICE_PRIMARY = UniqueKeys0.KEY_GCH_CAR_PRICE_PRIMARY;
    public static final UniqueKey<GchCityRecord> KEY_GCH_CITY_PRIMARY = UniqueKeys0.KEY_GCH_CITY_PRIMARY;
    public static final UniqueKey<GchCoinRecord> KEY_GCH_COIN_PRIMARY = UniqueKeys0.KEY_GCH_COIN_PRIMARY;
    public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_PRIMARY = UniqueKeys0.KEY_GCH_PAY_PRIMARY;
    public static final UniqueKey<GchPayAreaLowPriceRecord> KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY = UniqueKeys0.KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY;
    public static final UniqueKey<GchProvinceRecord> KEY_GCH_PROVINCE_PRIMARY = UniqueKeys0.KEY_GCH_PROVINCE_PRIMARY;
    public static final UniqueKey<GchReceiptAddressRecord> KEY_GCH_RECEIPT_ADDRESS_PRIMARY = UniqueKeys0.KEY_GCH_RECEIPT_ADDRESS_PRIMARY;
    public static final UniqueKey<GchSalesAreaRecord> KEY_GCH_SALES_AREA_PRIMARY = UniqueKeys0.KEY_GCH_SALES_AREA_PRIMARY;
    public static final UniqueKey<GchSalesAreaRecord> KEY_GCH_SALES_AREA_ID = UniqueKeys0.KEY_GCH_SALES_AREA_ID;
    public static final UniqueKey<GchScoreRecord> KEY_GCH_SCORE_PRIMARY = UniqueKeys0.KEY_GCH_SCORE_PRIMARY;
    public static final UniqueKey<GchScoreExchangeRecord> KEY_GCH_SCORE_EXCHANGE_PRIMARY = UniqueKeys0.KEY_GCH_SCORE_EXCHANGE_PRIMARY;
    public static final UniqueKey<GchSpecialPriceCarRecord> KEY_GCH_SPECIAL_PRICE_CAR_PRIMARY = UniqueKeys0.KEY_GCH_SPECIAL_PRICE_CAR_PRIMARY;
    public static final UniqueKey<GchSpecialPriceCarTrendRecord> KEY_GCH_SPECIAL_PRICE_CAR_TREND_PRIMARY = UniqueKeys0.KEY_GCH_SPECIAL_PRICE_CAR_TREND_PRIMARY;
    public static final UniqueKey<GchUser_4sRecord> KEY_GCH_USER_4S_PRIMARY = UniqueKeys0.KEY_GCH_USER_4S_PRIMARY;
    public static final UniqueKey<GchUserActivityRecord> KEY_GCH_USER_ACTIVITY_PRIMARY = UniqueKeys0.KEY_GCH_USER_ACTIVITY_PRIMARY;
    public static final UniqueKey<GchUserAttentionCarModelRecord> KEY_GCH_USER_ATTENTION_CAR_MODEL_PRIMARY = UniqueKeys0.KEY_GCH_USER_ATTENTION_CAR_MODEL_PRIMARY;
    public static final UniqueKey<GchUserGeneralRecord> KEY_GCH_USER_GENERAL_PRIMARY = UniqueKeys0.KEY_GCH_USER_GENERAL_PRIMARY;
    public static final UniqueKey<GchUserGeneralRecord> KEY_GCH_USER_GENERAL_WX_OPEN_ID = UniqueKeys0.KEY_GCH_USER_GENERAL_WX_OPEN_ID;
    public static final UniqueKey<V3UserDeviceRecord> KEY_V3_USER_DEVICE_PRIMARY = UniqueKeys0.KEY_V3_USER_DEVICE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<GchCarActivitiesRecord, Integer> IDENTITY_GCH_CAR_ACTIVITIES = createIdentity(GchCarActivities.GCH_CAR_ACTIVITIES, GchCarActivities.GCH_CAR_ACTIVITIES.ID);
        public static Identity<GchCarLifeThumbsRecord, Integer> IDENTITY_GCH_CAR_LIFE_THUMBS = createIdentity(GchCarLifeThumbs.GCH_CAR_LIFE_THUMBS, GchCarLifeThumbs.GCH_CAR_LIFE_THUMBS.ID);
        public static Identity<GchCarPriceRecord, Integer> IDENTITY_GCH_CAR_PRICE = createIdentity(GchCarPrice.GCH_CAR_PRICE, GchCarPrice.GCH_CAR_PRICE.SHOW_INDEX);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<GchAreaRecord> KEY_GCH_AREA_PRIMARY = createUniqueKey(GchArea.GCH_AREA, "KEY_gch_area_PRIMARY", GchArea.GCH_AREA.ID);
        public static final UniqueKey<GchBrandRecord> KEY_GCH_BRAND_PRIMARY = createUniqueKey(GchBrand.GCH_BRAND, "KEY_gch_brand_PRIMARY", GchBrand.GCH_BRAND.ID);
        public static final UniqueKey<GchCarRecord> KEY_GCH_CAR_PRIMARY = createUniqueKey(GchCar.GCH_CAR, "KEY_gch_car_PRIMARY", GchCar.GCH_CAR.ID);
        public static final UniqueKey<GchCarActivitiesRecord> KEY_GCH_CAR_ACTIVITIES_PRIMARY = createUniqueKey(GchCarActivities.GCH_CAR_ACTIVITIES, "KEY_gch_car_activities_PRIMARY", GchCarActivities.GCH_CAR_ACTIVITIES.ID);
        public static final UniqueKey<GchCarLifeBbsRecord> KEY_GCH_CAR_LIFE_BBS_PRIMARY = createUniqueKey(GchCarLifeBbs.GCH_CAR_LIFE_BBS, "KEY_gch_car_life_bbs_PRIMARY", GchCarLifeBbs.GCH_CAR_LIFE_BBS.ID);
        public static final UniqueKey<GchCarLifeThumbsRecord> KEY_GCH_CAR_LIFE_THUMBS_PRIMARY = createUniqueKey(GchCarLifeThumbs.GCH_CAR_LIFE_THUMBS, "KEY_gch_car_life_thumbs_PRIMARY", GchCarLifeThumbs.GCH_CAR_LIFE_THUMBS.ID);
        public static final UniqueKey<GchCarModelRecord> KEY_GCH_CAR_MODEL_PRIMARY = createUniqueKey(GchCarModel.GCH_CAR_MODEL, "KEY_gch_car_model_PRIMARY", GchCarModel.GCH_CAR_MODEL.ID);
        public static final UniqueKey<GchCarModelPreferRecord> KEY_GCH_CAR_MODEL_PREFER_PRIMARY = createUniqueKey(GchCarModelPrefer.GCH_CAR_MODEL_PREFER, "KEY_gch_car_model_prefer_PRIMARY", GchCarModelPrefer.GCH_CAR_MODEL_PREFER.ID);
        public static final UniqueKey<GchCarPreferRecord> KEY_GCH_CAR_PREFER_PRIMARY = createUniqueKey(GchCarPrefer.GCH_CAR_PREFER, "KEY_gch_car_prefer_PRIMARY", GchCarPrefer.GCH_CAR_PREFER.ID);
        public static final UniqueKey<GchCarPriceRecord> KEY_GCH_CAR_PRICE_PRIMARY = createUniqueKey(GchCarPrice.GCH_CAR_PRICE, "KEY_gch_car_price_PRIMARY", GchCarPrice.GCH_CAR_PRICE.ID);
        public static final UniqueKey<GchCityRecord> KEY_GCH_CITY_PRIMARY = createUniqueKey(GchCity.GCH_CITY, "KEY_gch_city_PRIMARY", GchCity.GCH_CITY.ID);
        public static final UniqueKey<GchCoinRecord> KEY_GCH_COIN_PRIMARY = createUniqueKey(GchCoin.GCH_COIN, "KEY_gch_coin_PRIMARY", GchCoin.GCH_COIN.ID);
        public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_PRIMARY = createUniqueKey(GchPay.GCH_PAY, "KEY_gch_pay_PRIMARY", GchPay.GCH_PAY.ID);
        public static final UniqueKey<GchPayAreaLowPriceRecord> KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY = createUniqueKey(GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE, "KEY_gch_pay_area_low_price_PRIMARY", GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE.ID);
        public static final UniqueKey<GchProvinceRecord> KEY_GCH_PROVINCE_PRIMARY = createUniqueKey(GchProvince.GCH_PROVINCE, "KEY_gch_province_PRIMARY", GchProvince.GCH_PROVINCE.ID);
        public static final UniqueKey<GchReceiptAddressRecord> KEY_GCH_RECEIPT_ADDRESS_PRIMARY = createUniqueKey(GchReceiptAddress.GCH_RECEIPT_ADDRESS, "KEY_gch_receipt_address_PRIMARY", GchReceiptAddress.GCH_RECEIPT_ADDRESS.ID);
        public static final UniqueKey<GchSalesAreaRecord> KEY_GCH_SALES_AREA_PRIMARY = createUniqueKey(GchSalesArea.GCH_SALES_AREA, "KEY_gch_sales_area_PRIMARY", GchSalesArea.GCH_SALES_AREA.ID);
        public static final UniqueKey<GchSalesAreaRecord> KEY_GCH_SALES_AREA_ID = createUniqueKey(GchSalesArea.GCH_SALES_AREA, "KEY_gch_sales_area_id", GchSalesArea.GCH_SALES_AREA.ID);
        public static final UniqueKey<GchScoreRecord> KEY_GCH_SCORE_PRIMARY = createUniqueKey(GchScore.GCH_SCORE, "KEY_gch_score_PRIMARY", GchScore.GCH_SCORE.ID);
        public static final UniqueKey<GchScoreExchangeRecord> KEY_GCH_SCORE_EXCHANGE_PRIMARY = createUniqueKey(GchScoreExchange.GCH_SCORE_EXCHANGE, "KEY_gch_score_exchange_PRIMARY", GchScoreExchange.GCH_SCORE_EXCHANGE.ID);
        public static final UniqueKey<GchSpecialPriceCarRecord> KEY_GCH_SPECIAL_PRICE_CAR_PRIMARY = createUniqueKey(GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR, "KEY_gch_special_price_car_PRIMARY", GchSpecialPriceCar.GCH_SPECIAL_PRICE_CAR.ID);
        public static final UniqueKey<GchSpecialPriceCarTrendRecord> KEY_GCH_SPECIAL_PRICE_CAR_TREND_PRIMARY = createUniqueKey(GchSpecialPriceCarTrend.GCH_SPECIAL_PRICE_CAR_TREND, "KEY_gch_special_price_car_trend_PRIMARY", GchSpecialPriceCarTrend.GCH_SPECIAL_PRICE_CAR_TREND.ID);
        public static final UniqueKey<GchUser_4sRecord> KEY_GCH_USER_4S_PRIMARY = createUniqueKey(GchUser_4s.GCH_USER_4S, "KEY_gch_user_4s_PRIMARY", GchUser_4s.GCH_USER_4S.ID);
        public static final UniqueKey<GchUserActivityRecord> KEY_GCH_USER_ACTIVITY_PRIMARY = createUniqueKey(GchUserActivity.GCH_USER_ACTIVITY, "KEY_gch_user_activity_PRIMARY", GchUserActivity.GCH_USER_ACTIVITY.ID);
        public static final UniqueKey<GchUserAttentionCarModelRecord> KEY_GCH_USER_ATTENTION_CAR_MODEL_PRIMARY = createUniqueKey(GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL, "KEY_gch_user_attention_car_model_PRIMARY", GchUserAttentionCarModel.GCH_USER_ATTENTION_CAR_MODEL.ID);
        public static final UniqueKey<GchUserGeneralRecord> KEY_GCH_USER_GENERAL_PRIMARY = createUniqueKey(GchUserGeneral.GCH_USER_GENERAL, "KEY_gch_user_general_PRIMARY", GchUserGeneral.GCH_USER_GENERAL.ID);
        public static final UniqueKey<GchUserGeneralRecord> KEY_GCH_USER_GENERAL_WX_OPEN_ID = createUniqueKey(GchUserGeneral.GCH_USER_GENERAL, "KEY_gch_user_general_wx_open_id", GchUserGeneral.GCH_USER_GENERAL.WX_OPEN_ID);
        public static final UniqueKey<V3UserDeviceRecord> KEY_V3_USER_DEVICE_PRIMARY = createUniqueKey(V3UserDevice.V3_USER_DEVICE, "KEY_v3_user_device_PRIMARY", V3UserDevice.V3_USER_DEVICE.USERID);
    }
}