package com.lrest.server.models;

import java.util.List;
import java.util.Map;

/**
 * Created by angrycans on 16/8/1.
 */
public  class Car_details {
    /*
    *   汽车详情页获得该报价车具体信息
    * */
    /**
        * @Description:
        * @Return:
        * @Author: 孙磊
        * @Date:2016/9/19 11:09
        * @Version 2.0
        */
    public static class  car_details{
        String id;
        public String car_id;
        public String car_name;
        String brand_id;
        String brand_name;
        String car_model_id;
        String car_model_name;
        String car_model_imageurl;
/*        String price;
        String discount;
        String low_price;
        String auth_price;
        String car_status;
        String is_xunjia;
        String exterior_color_id;
        String interior_color_id;*/

    }

    public static class car_price{
        public String car_id;
        public String exterior_color_id;
        public String interior_color_id;
        public String auth_price;
        public String low_price;
        public String price;
        public String discount;
        public String is_xunjia;
        public String id;




        /**
            * @Description:
            * @Return:
            * @Author: 孙磊
            * @Date:2016/9/19 11:10
            * @Version 2.0
            */
        public car_price(String  a,String b,String c,String d,String e,String f,String g,String h,String j){
            //car_type_in_color r=new car_type_in_color();
            this.car_id=a;
            this.exterior_color_id=b;
            this.interior_color_id=c;
            this.auth_price=d;
            this.low_price=e;
            this.price=f;
            this.discount=g;
            this.is_xunjia=h;
            this.id=j;


        }
    }

    public  static  class img{

        public String imgurl;

        public img(String c){

            imgurl=c;

        }
    }






    //获得该报价车所有外观
    public static class car_ex_color{
        String exterior_color_id;
        String exterior_color_name;
        String exterior_color_value;


        public car_ex_color(String  a,String b,String c){
            //car_type_in_color r=new car_type_in_color();
            this.exterior_color_id=a;
            this.exterior_color_name=b;
            this.exterior_color_value=c;

        }
    }
    //获得该报价车所有内饰颜色
    public static class car_in_color{
        public String interior_color_id;
        public String interior_color_name;
        public String interior_color_value;



        public car_in_color(String  a,String b,String c){
            //car_type_in_color r=new car_type_in_color();
            this.interior_color_id=a;
            this.interior_color_name=b;
            this.interior_color_value=c;

        }
    }

    public List<car_details> car_details;//=new ArrayList<car_type>();

    public Map<String,car_price> car_prices;
    //
    // public Map<String,Car_specialcar.car_price> car_prices;
    public Map<String,List<car_ex_color>> car_ex_color;
    public Map<String,List<car_in_color>> car_in_color;
    public Map<String,List<img>> car_imgs;


}
