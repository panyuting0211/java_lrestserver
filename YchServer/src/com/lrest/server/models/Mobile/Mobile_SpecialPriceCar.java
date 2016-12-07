package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: Mobile端的特价车
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:24
 **/
public class Mobile_SpecialPriceCar {

    // 特价车Id
    public String specialPriceId;

    // 汽车品牌
    public String brandId;
    public String brandName;

    // 汽车车型
    public String carModelId;
    public String carModelName;

    // 汽车车款
    public String carId;
    public String carName;
    public String carImage;

    // 特价车的属性
    public String startDate;
    public String endDate;
    public String number;  // 销售数量
    public String price;  // 价格
    public String specialPrice; // 特价
}
