package com.lrest.server.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * DESCRIPTION: 泛型类 用来存放带Count的数据
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 12 11:45
 **/
public class BaseCount<T> {

    public int count;
    public List<T> rows;
}
