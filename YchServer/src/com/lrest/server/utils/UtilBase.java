package com.lrest.server.utils;

import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import org.omg.CORBA.INTERNAL;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import java.util.UUID;

/**
 * Created by angrycans on 16/8/3.
 */
public class UtilBase {
    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

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
    public static byte getJsonAsByte(JsonObject json, String name) {
        return json.get(name) == null ? -1 : json.get(name).getAsByte();
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


    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //builder.sslSocketFactory(sslSocketFactory);
            builder.sslSocketFactory(sslSocketFactory,new TrustAllManager());
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
