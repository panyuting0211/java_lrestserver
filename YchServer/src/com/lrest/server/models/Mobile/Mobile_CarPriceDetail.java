package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: 新增活动的列表
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 12 16:03
 **/
public class Mobile_CarPriceDetail {


    // 汽车车型
    public String carModelId;
    public String carModelName;

    // 汽车车款
    public String carId;
    public String carName;
    public String carImage;

    // 外观颜色ID
    public String exteriorColorId;

    // 官方价格
    public String authPrice;

    // 询价地区
    public String cityId;
    public String cityName;
    public String provinceId;
    public String provinceName;

    public String isXunjia;

    // 上牌地区
    public String salesAreaLevel;
    public String salesAreaName;
    public String askingPriceCount;
    public String isAttention;

}
