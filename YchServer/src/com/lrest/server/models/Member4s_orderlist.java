package com.lrest.server.models;

import com.lrest.server.services.Config;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by angrycans on 16/8/1.
 */
public class Member4s_orderlist {

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
    public int count;

    public Member4s_orderlist(String a,String b,String c,String d,String e,String f,String g,String h,String i,String j,String k,BigDecimal l,Timestamp m)
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
