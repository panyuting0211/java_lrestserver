package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: Mobile端 在售的车款
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:24
 **/
public class Mobile_OnSellCar {

    // 汽车车款
    public String carId;
    public String carName;
    public String carImage;

    public String carPriceId;
    public String authPrice;  // 价格
    public String isXunjia;  // 是否询价

    public String exteriorColorId;
    public String interiorColorId;
}
