package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by angrycans孙磊 on 16/9/14.
 */
public class Member4s_home {

    //基本信息
    public static  class homeBase{

        public int deposit; //已付定金
        public int mentioncars; //待提车
        public int library; //出库中

    }

    //最新订单
    public static class newOrder{
        public String id;
        public String out_trade_no;
        public String carstyle;
        public String exterior_color_name;
        public String interior_color_name;
        public String status;
        public String exterior_img;
        public String buyer_name;
        public String buyer_tel;
        public String buy_time;
        public String low_price;
        public BigDecimal money;
        public String createtime;

            public newOrder(String a, String b, String c, String d, String e, String f, String g, String h, String i, String j, String k, BigDecimal l, Timestamp m)
            {
                SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                id=a;
                out_trade_no=b;
                carstyle=c;
                exterior_color_name=d;
                interior_color_name=e;
                status=f;
                exterior_img= Config.OSS+g.replace("type","small");
                buyer_name=h;
                buyer_tel=i;
                buy_time=j;
                low_price=k;
                money=l;
                createtime=format.format(m.getTime());
            }

    }

    public static class newPrice{
        public String id;
        public String car_model_name;
        public String car_name;
        public String car_id;
        public String exterior_color_name;
        public String interior_color_name;
        public String exterior_color_id;
        public String interior_color_id;
        public String price;
        public String discount;
        public String low_price;
        public String stock;
        public String onway;
        public String salearea;
    }
    public static class priceArea{
        public String id;
        public String sales_area_name;
    }
    public static class newActivity{
        public String id;
        public String car_model_name;
        public String car_name;
        public String car_id;
        public String exterior_color_name;
        public String interior_color_name;
        public String exterior_color_id;
        public String interior_color_id;
        public String auth_price;
        public String price;
        public String special_price;
        public String start_date;
        public String end_date;
        public String number;
        public String salearea;
        public String status;
    }
    public static class specialPriceArea{
        public String id;
        public String sales_area_name;
    }
    public homeBase baseinfo = new homeBase();
    public newOrder neworder;
    public newPrice newprice;
    public List<Member4s_home.priceArea> pricearea;
    public newActivity newactivity;
    public List<Member4s_home.specialPriceArea> specialpricearea;


}
