package com.lrest.server.models.Mobile;

import com.lrest.server.models.User_general;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * DESCRIPTION: Mobile端的底价车类
 *
 * @Author 韩武洽
 * @Date 2016-10
 * @Time 27 15:24
 **/
public class Mobile_CarLifeBBS {
    public String bbsId;
    public String title; //标题
    public String pid; // 车生活大类
    public String typeId; // 车生活小类
    public String typeName; // 车生活小类名称
    public String image;
    public String summary; //摘要



    public String carId;
    public String carName;

    public String clickAmount; // 点击数
    public String commentAmount;// 评论数
    public String thumbsAmount; // 点赞数

    /**
     * @Description:车生活详情
     * @param：
     * @Return:
     * @Author: 孙磊
     * @Date:2016/11/9 10:06
     * @Version 2.0
     */
    public static class BBSDetail {
        public String bbsid;
        public String title; //标题
        public String ftypeName; // 车生活大类名称
        public String typeName; // 车生活小类名称
        public String contents; //内容
        public String image;
        public String createtime;
        public int isthumbs;


        public String clickAmount; // 点击数
        public String commentAmount;// 评论数
        public String thumbsAmount; // 点赞数



    }
    /**
        * @Description:车生活详情页里的评论
        * @param：
        * @Return:
        * @Author: 孙磊
        * @Date:2016/11/10 15:15
        * @Version 2.0
        */
    public static class BBSComments{

        public String id;
        public String user_name;
        public String head_url;
        public String comment;
        public String createtime;
    }



    public Mobile_CarLifeBBS.BBSDetail Detail;
    public List<Mobile_CarLifeBBS.BBSComments> Comments;
}
