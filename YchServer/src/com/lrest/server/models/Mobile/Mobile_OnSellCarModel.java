package com.lrest.server.models.Mobile;

/**
 * DESCRIPTION: Mobile端 在售的车款
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:24
 **/
public class Mobile_OnSellCarModel {

    // 汽车品牌
    public String brandId;
    public String brandName;

    // 汽车车型
    public String carModelId;
    public String carModelName;
    public String carModelImageUrl;


    // 在售车型的最低价和最高价
    public String minCarPrice;
    public String maxCarPrice;

}
