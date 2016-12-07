package com.lrest.server.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by angrycans on 16/8/1.
 */
public class Member4s_scorelist {

    public int score;
    public String info;
    public String createtime;
    public String out_trade_no;
    public String telphone;




    public Member4s_scorelist(int a, String b, Timestamp c,String d,String e)
    {
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        score=a;
        info=b;
        createtime=format.format(c.getTime());
        out_trade_no=d;
        telphone=e;


    }


}
