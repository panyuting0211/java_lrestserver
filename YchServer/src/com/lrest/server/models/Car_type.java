package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public class Car_type {

    public String id;
    public String name;
    public String img;

    public Car_type(String _id,String _name,String _img){
        id=_id;
        name=_name;
        img= Config.OSS+_img.replace("type","small");
    }

}
