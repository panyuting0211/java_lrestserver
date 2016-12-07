package com.lrest.server.models;

/**
 * DESCRIPTION: 报价活动的历史
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 12 16:03
 **/
public class Member4s_offerTrend {

    // 汽车品牌
    public String brandId;
    public String brandName;

    // 汽车车型
    public String carModelId;
    public String carModelName;

    // 汽车车款
    public String carId;
    public String carName;

    // 内饰颜色ID
    public String interiorColorId;
    public String interiorColorName;

    // 外观颜色ID
    public String exteriorColorId;
    public String exteriorColorName;

    // 官方价格
    public String price;
    // 优惠
    public String discount;
    // 报价
    public String lowPrice;
    public String createTime;

}
