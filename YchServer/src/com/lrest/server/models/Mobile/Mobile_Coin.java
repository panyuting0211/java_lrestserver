package com.lrest.server.models.Mobile;

import java.util.List;

/**
    * @Description:车币
    * @param：
    * @Return:
    * @Author: 孙磊
    * @Date:2016/11/25 10:16
    * @Version 2.0
    */
public class Mobile_Coin {

    //获取车币首页
    public int coin;
    public int issign;
    public int isfirstlogin;
    public int iscomplete;
    public int isheadimg;

    //车币列表字段
    public static class myCoin {
        public String id;
        public String coin; // 车币
        public String info; // 车币详情
        public String createtime;  // 创建时间
    }



    public List<Mobile_Coin.myCoin> coinlist;
}
