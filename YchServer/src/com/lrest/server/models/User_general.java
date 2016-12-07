package com.lrest.server.models;

import java.sql.Timestamp;
import com.lrest.server.services.Config;
import java.text.SimpleDateFormat;

import java.util.List;

/**
 * Created by angrycans on 16/8/1.
 */
public class User_general {

    public static class generals {
        String id;
        String wx_open_id;
    }

    public static class myscore{
        String score;
        String info;
        String createtime ;
    }

    public static class mysticket{
        String from_ticket;
        String money;
        String createtime;
        String end_time;
        String ticket_number;
    }

    public static class myactivityorder{
        String id;
        String out_trade_no;
        String createtime;
        String carstyle;
        String status;
        String money;
        String activity_name;
        String endtime;
        String exterior_img;
        String img_url;
        String brand_id;
        String brand_name;
        String car_model_id;
        String car_model_name;

        public myactivityorder(String a,String b,Timestamp c,String d,String e,String f,String g,String h,String i,String j,String k,String l,String m,String n)
            {
                SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                id=a;
                out_trade_no=b;
                createtime=format.format(c.getTime());
                carstyle=d;
                status= e;
                money=f;
                activity_name=g;
                endtime =h;
                if(i!=null){
                    exterior_img= Config.OSS+i.replace("type","small");
                }else{
                    exterior_img= i;
                }
                img_url = Config.OSS+j.replace("type","small");
                brand_id = k;
                brand_name = l;
                car_model_id = m;
                car_model_name = n;
            }
    }
    public static class orderdetail{
        String id;
        String buyer_tel;
        String brand_name;
        String car_model_name;
        String car_name;
        String name_4s;
        String low_price;
        String credit_fee;
        String licensing_fees;
        String exterior_img;
        String user_remark;
        String carstyle;
    }


    public List<generals> list;
    public List<myscore> myscorelist;
    public List<mysticket> mysticketlist;
    public List<myactivityorder> myactivityorderlist;
    public List<orderdetail> myorderdetail;
}
