package com.lrest.server.models.Mobile;

import java.util.List;

/**
 * Created by angrycans on 16/8/1.
 */
public class Mobile_UserGeneral {
    public static class  Session{
        public String sessionid;
        public String token;

        public Session() {
        }

        public Session(String sessionId, String token) {
            this.sessionid = sessionId;
            this.token = token;
        }
    }

    public String id;
    public String userName;
    public String role;
    public String tel;
    public String email;
    public String headUrl;
    public String name;
    public String nick;
    public String sex;
    public String birthday;
    public String provinceId;
    public String cityId;
    public String districtId;
    public String address;
    public String postcode;
    public String remark;
    public Session session;

}
