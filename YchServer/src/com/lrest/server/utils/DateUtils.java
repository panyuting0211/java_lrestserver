package com.lrest.server.utils;

import java.sql.Timestamp;

/**
 * DESCRIPTION: 用来管理时间格式转换
 *
 * @Author 韩武洽
 * @Date 2016-09
 * @Time 12 14:03
 **/
public class DateUtils {


    /**
    * @DESCRIPTION:
    * @Return: 返回Timestamp的当前时间
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/9/12-14:04
    * @version V2.0
    **/
    public static Timestamp millisecondChangeTimestamp(){
        return millisecondChangeTimestamp(System.currentTimeMillis());
    }

    /**
    * @DESCRIPTION:
    * @Return: 把long转化为Timestamp的时间
    * @param time Long类型的毫秒数
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/9/12-14:06
    * @version V2.0
    **/
    public static Timestamp millisecondChangeTimestamp(Long time){
        return new Timestamp(time);
    }


    /**
     * @DESCRIPTION:
     * @Return: 把Date转化为Timestamp的时间
     * @param tsStr Date类型的毫秒数
     * @Author: 韩武洽 @Wyshown
     * @Date: 2016/9/12-14:06
     * @version V2.0
     **/
    public static Timestamp dateChangeTimestamp(String tsStr){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(tsStr);
            System.out.println(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }




    public static void main(String arg[]){

        System.out.print(dateChangeTimestamp("2011-05-09 11:49:45"));
        System.out.print(dateChangeTimestamp("2011-05-09 11:49:45"));
    }

}
