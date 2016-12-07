package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * DESCRIPTION: 新增活动的列表
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 12 16:03
 **/
public class Car_special_price_car {

    public String id;
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
    public String interiorColorValue;

    // 外观颜色ID
    public String exteriorColorId;
    public String exteriorColorName;
    public String exteriorColorValue;

    //特价车图片
    public String carImage;

    // 官方价格
    public String price;
    public String mergeColor;
    public int authPrice;
    public int specialPrice;
    //活动数量
    public int number;
    //特价日期
    public String startDate;
    public String endDate;
    //销售地
    public String salesAreaName;
    public int isAttention;
    public int attentionCount;




}
