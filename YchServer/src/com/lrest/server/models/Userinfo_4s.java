package com.lrest.server.models;

import java.util.List;

/**
 * Created by angrycans on 16/8/1.
 */
public class Userinfo_4s {
    public static class  Session{
        public String sessionid;
        public String token;

    }

    public String id;
    public String user_name;
    public String role;
    public String tel;
    public String email;
    public String head_url;
    public String nick;
    public String name_4s;
    public String brand_4s;
    public String total_jifen;
    public String brand_name;
    public String contacts;
    public String addr;
    public Session session;

    public static class brandList {
        String brand_id;
        String brand_name;

    }

    public List<brandList> brandlist;


}
