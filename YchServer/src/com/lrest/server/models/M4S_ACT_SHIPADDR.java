package com.lrest.server.models;

import java.util.List;

/**
 * Created by angrycans on 16/8/1.
 */
public class M4S_ACT_SHIPADDR {

    //获得4S点的收获地址
    public static class ShippingAddress{
        public String id;
        public String user_id;
        public String receiver;
        public String telphone;
        public String receipt_province;
        public String province_id;
        public String receipt_city;
        public String city_id;
        public String receipt_quarter;
        public String quarter_id;
        public String receipt_address;
        public String status;
        public String createtime;
    }

    public List<ShippingAddress> shippingAddress;

}
