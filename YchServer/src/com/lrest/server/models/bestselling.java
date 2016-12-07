package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public class bestselling {

    public String id;
    public String brand_id;
    public String brand_name;
    public String car_model_id;
    public String car_model_name;
    public String car_id;
    public String car_name;
    public String exterior_color_name;
    public String interior_color_name;
    public String car_model_imageurl;
    public String discount;
    public String low_price;
    public String auth_price;
    public String url;


    public bestselling(String _id,String _brand_id,String _brand_name,String _car_model_id,String _car_model_name,String _car_id,String _car_name,String _exterior_color_name,String _interior_color_name,String _car_model_imageurl,String _discount,String _low_price,String _auth_price){
        id=_id;
        brand_id=_brand_id;
        brand_name=_brand_name;
        car_model_id=_car_model_id;
        car_model_name=_car_model_name;
        car_id=_car_id;
        car_name=_car_name;
        exterior_color_name=_exterior_color_name;
        interior_color_name=_interior_color_name;
        car_model_imageurl= Config.OSS+_car_model_imageurl.replace("type","small");
        discount=_discount;
        low_price=_low_price;
        auth_price=_auth_price;
        url="/index.php/car/product_details?id="+id;
    }

}
