package com.lrest.server.utils;

import com.google.gson.JsonObject;
import org.omg.CORBA.INTERNAL;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

import java.util.UUID;

/**
 * Created by angrycans on 16/8/3.
 */
public class UtilBase {

    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("\\-", "");
    }


    public static String getJsonAsString(JsonObject json, String name) {
//        System.out.println(String.format("--- {%b}",json.get(name)==null));
        String ret = json.get(name) == null ? null : json.get(name).getAsString();
        return ret;
    }

    public static int getJsonAsInt(JsonObject json, String name) {
        return json.get(name) == null ? -1 : json.get(name).getAsInt();
    }

    public static String createOrderNo() {
        Calendar ca = Calendar.getInstance();
        long seconds = System.currentTimeMillis() / 1000;
        String second_str = String.valueOf(seconds);
        String year_code[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        return year_code[(int) Math.floor(ca.get(Calendar.YEAR)) - 2010] +
                Integer.toHexString(ca.get(Calendar.MONTH)).toUpperCase() + ca.get(Calendar.DATE) +
                second_str.substring(second_str.length() - 5, second_str.length()) + String.valueOf(System.currentTimeMillis()).substring(2, 5) + String.format("%02d", (int) (Math.random() * 100));

    }

    public static String getIpAddr(HttpServletRequest req) {

        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        return ip;

    }


}
