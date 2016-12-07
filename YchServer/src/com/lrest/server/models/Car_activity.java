package com.lrest.server.models;

import com.lrest.server.services.weixin.base.AccessTokenManager;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public  class Car_activity {
    /*
    *   汽车活动专场
    * */
    //获得该报价车所有内饰颜色
    public static class Activityinfo{
        //select id,activity_name,activity_number from gch_activities where id=4
        public String activity_name;
        public String id;
        public String activity_number;
        public String starttime;
        public String endtime;
    }

    public static class activitylist{
        public String brand_name;
        public String car_model_id;
        public String car_model_name;
        public String car_id;
        public String car_name;
        public String exterior_color_id;
        public String interior_color_id;
        public String discount;
        public String price;
        public String low_price;
        public String bid;
        public int buyer_count;
        public int type;
        public String exterior_color_name;
        public String interior_color_name;
        public int sort;
        public int status;
        public int activity_car_id;





//        public activitylist(String  a,String b,String c,String  d,String e,String f,String g,String h,String i,String j,String k){
//            //car_type_in_color r=new car_type_in_color();
//            this.brand_name=a;
//            this.car_model_id=b;
//            this.car_model_name=c;
//            this.car_name=d;
//            this.discount=e;
//            this.price=f;
//            this.low_price=g;
//            this.car_id=h;
//            this.exterior_color_id=i;
//            this.interior_color_id=j;
//            this.bid=k;
//
//        }
    }
    public Map<String,List<activitylist>> activitylist;
    public Activityinfo activityinfo;
    public List<activitylist> list;


}
