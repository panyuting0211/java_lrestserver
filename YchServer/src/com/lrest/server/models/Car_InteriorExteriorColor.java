package com.lrest.server.models;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION: 关于汽车外观和内饰的Model类
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 29 11:16
 **/
public class Car_InteriorExteriorColor {


    public static class CarColor {
        String id;
        String colorName;
        String colorValue;

    }

    // 内饰列表
    public List<CarColor> interiorColorList = new ArrayList<>();
    // 外观列表
    public List<CarColor> exteriorColorList = new ArrayList<>();
}
