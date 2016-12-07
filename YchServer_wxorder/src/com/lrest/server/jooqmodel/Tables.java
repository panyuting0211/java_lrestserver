/**
 * This class is generated by jOOQ
 */
package com.lrest.server.jooqmodel;


import com.lrest.server.jooqmodel.tables.GchCar;
import com.lrest.server.jooqmodel.tables.GchPay;
import com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice;
import com.lrest.server.jooqmodel.tables.GchViewCarPrice;
import com.lrest.server.jooqmodel.tables.GchWxpayPackage;

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
     * 车款表
     */
    public static final GchCar GCH_CAR = com.lrest.server.jooqmodel.tables.GchCar.GCH_CAR;

    /**
     * 询价定车支付表
     */
    public static final GchPay GCH_PAY = com.lrest.server.jooqmodel.tables.GchPay.GCH_PAY;

    /**
     * 订单中区域最低价表
     */
    public static final GchPayAreaLowPrice GCH_PAY_AREA_LOW_PRICE = com.lrest.server.jooqmodel.tables.GchPayAreaLowPrice.GCH_PAY_AREA_LOW_PRICE;

    /**
     * VIEW
     */
    public static final GchViewCarPrice GCH_VIEW_CAR_PRICE = com.lrest.server.jooqmodel.tables.GchViewCarPrice.GCH_VIEW_CAR_PRICE;

    /**
     * The table <code>gouchehui2.0.gch_wxpay_package</code>.
     */
    public static final GchWxpayPackage GCH_WXPAY_PACKAGE = com.lrest.server.jooqmodel.tables.GchWxpayPackage.GCH_WXPAY_PACKAGE;
}