/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel;


import com.lrest.server.jooqmodel.tables.GchCar;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice;
import com.lrest.server.jooqmodel.tables.GchWxpayPackage;
import com.lrest.server.jooqmodel.tables.records.GchCarRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayAreaLowPriceRecord;
import com.lrest.server.jooqmodel.tables.records.GchPayRecord;
import com.lrest.server.jooqmodel.tables.records.GchWxpayPackageRecord;

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

    public static final Identity<GchWxpayPackageRecord, Integer> IDENTITY_GCH_WXPAY_PACKAGE = Identities0.IDENTITY_GCH_WXPAY_PACKAGE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<GchCarRecord> KEY_GCH_CAR_PRIMARY = UniqueKeys0.KEY_GCH_CAR_PRIMARY;
    public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_PRIMARY = UniqueKeys0.KEY_GCH_PAY_PRIMARY;
    public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_OUT_TRADE_NO = UniqueKeys0.KEY_GCH_PAY_OUT_TRADE_NO;
    public static final UniqueKey<GchPayAreaLowPriceRecord> KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY = UniqueKeys0.KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY;
    public static final UniqueKey<GchWxpayPackageRecord> KEY_GCH_WXPAY_PACKAGE_PRIMARY = UniqueKeys0.KEY_GCH_WXPAY_PACKAGE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<GchWxpayPackageRecord, Integer> IDENTITY_GCH_WXPAY_PACKAGE = createIdentity(GchWxpayPackage.GCH_WXPAY_PACKAGE, GchWxpayPackage.GCH_WXPAY_PACKAGE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<GchCarRecord> KEY_GCH_CAR_PRIMARY = createUniqueKey(GchCar.GCH_CAR, "KEY_gch_car_PRIMARY", GchCar.GCH_CAR.ID);
        public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_PRIMARY = createUniqueKey(GchPay.GCH_PAY, "KEY_gch_pay_PRIMARY", GchPay.GCH_PAY.ID);
        public static final UniqueKey<GchPayRecord> KEY_GCH_PAY_OUT_TRADE_NO = createUniqueKey(GchPay.GCH_PAY, "KEY_gch_pay_out_trade_no", GchPay.GCH_PAY.OUT_TRADE_NO);
        public static final UniqueKey<GchPayAreaLowPriceRecord> KEY_GCH_PAY_AREA_LOW_PRICE_PRIMARY = createUniqueKey(GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE, "KEY_gch_pay_area_low_price_PRIMARY", GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE.ID);
        public static final UniqueKey<GchWxpayPackageRecord> KEY_GCH_WXPAY_PACKAGE_PRIMARY = createUniqueKey(GchWxpayPackage.GCH_WXPAY_PACKAGE, "KEY_gch_wxpay_package_PRIMARY", GchWxpayPackage.GCH_WXPAY_PACKAGE.ID);
    }
}
