package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by angrycans on 16/8/1.
 */
public class Member4s_speciallist {

    public String id;
    public String brand_name;
    public String car_model_name;
    public String car_name;
    public String exterior_color_id;
    public String exterior_color_name;
    public String interior_color_id;
    public String interior_color_name;
    public String auth_price;
    public String special_price;
    public String start_date;
    public String end_date;
    public int number;
    public int status;
    public String sales_area;
    public String car_image;
    public String car_id;
    public String description;


    public Member4s_speciallist(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j,Timestamp k,Timestamp l,int m,int n,String o,String p,String q)
    {
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        id=a;
        brand_name=b;
        car_model_name=c;
        car_name=d;
        exterior_color_id=e;
        exterior_color_name=f;
        interior_color_id=g;
        interior_color_name=h;
        auth_price=i;
        special_price=j;
        start_date=format.format(k.getTime());
        end_date=format.format(l.getTime());
        number=m;
        status=n;
        car_image = Config.OSS+o.replace("type","small");
        car_id = p;
        description = q;
       /* sales_area=o;*/
    }


}
