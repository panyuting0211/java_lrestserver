package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: Mobile端的底价车类
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:24
 **/
public class Mobile_BaseCarPrice {

    // 汽车品牌
    public String carPriceId;
    public String brandId;
    public String brandName;
    public String brandImage;

    // 汽车车型
    public String carModelId;
    public String carModelName;
    // 车型图片
    public String carModelImage;

    // 车型类型
    public String carTypeId;
    public String carTypeName;
    public String carTypeImage;

    // 车款
    public String carId;
    public String carName;
    public String carImage;

    // 内饰颜色ID
    public String interiorColorId;
    public String interiorColorName;
    public String interiorColorValue;

    // 外观颜色ID
    public String exteriorColorId;
    public String exteriorColorName;
    public String exteriorColorValue;


    // 官方价格
    public String price;
    public String lowPrice;
    public String discount;
    public String isXunjia;

    public String authPrice; // 官方指导价
    public String maxPrice; // 最高价
    public String minPrice; // 最低价

    public String transactionsCount; // 成交量
    public String isAttention; // 是否关注

}
