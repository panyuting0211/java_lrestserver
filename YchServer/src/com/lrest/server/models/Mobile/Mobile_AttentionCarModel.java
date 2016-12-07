package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: Mobile 热门推荐
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 28 9:58
 **/
public class Mobile_AttentionCarModel {

    public String id;
    // 汽车品牌
    public String brandId;
    public String brandName;

    // 汽车车型
    public String carModelId;
    public String carModelName;
    public String carModelImageUrl;

    //指导价
    public int maxAuthPrice;
    public int minAuthPrice;

    // 询价量
    public String inquiryAmount;

    // 内饰颜色ID
    public String interiorColorId;
    // 外观颜色ID
    public String exteriorColorId;
    public String carId;
}
