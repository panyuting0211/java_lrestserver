package com.lrest.server.models;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public class Car_info {
    public static class  car_type{
        String car_id;
        String car_name;
        String brand_id;
        String brand_name;
        String car_model_id;
        String car_model_name;
        String car_model_imageurl;

    }


    public static class car_type_ex_color{
        String exterior_color_id;
        String exterior_color_name;
        String exterior_color_value;


        public car_type_ex_color(String  a,String b,String c){
            //car_type_in_color r=new car_type_in_color();
            this.exterior_color_id=a;
            this.exterior_color_name=b;
            this.exterior_color_value=c;

        }
    }

    public static class car_type_in_color{
        public String interior_color_id;
        public String interior_color_name;
        public String interior_color_value;



        public car_type_in_color(String  a,String b,String c){
            //car_type_in_color r=new car_type_in_color();
            this.interior_color_id=a;
            this.interior_color_name=b;
            this.interior_color_value=c;

        }
    }

    public static class car_price{
        public String interior_color_id;
        public String exterior_color_id;
        public String price;
        public String low_price;
        public String discount;
        public String car_id;
        public String is_xunjia;



        public car_price(String  a,String b,String c,String d,String e,String f){
            //car_type_in_color r=new car_type_in_color();
            this.car_id=a;
            this.exterior_color_id=b;
            this.interior_color_id=c;

            this.price=d;
            this.low_price=e;
            this.discount=f;


        }
    }

    public List<car_type> car_types;//=new ArrayList<car_type>();

    public Map<String,List<car_type_ex_color>> car_type_ex_colors;
    public Map<String,List<car_type_in_color>> car_type_in_colors;
    public Map<String,car_price> car_prices;
    //Map<String >


}
