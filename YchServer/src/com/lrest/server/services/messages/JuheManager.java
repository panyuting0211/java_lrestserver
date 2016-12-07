package com.lrest.server.services.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
/**
    * @Description:
         短信API服务调用示例代码 － 聚合数据
        在线接口文档：http://www.juhe.cn/docs/54
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/26 14:08
    * @Version 2.0
    */
public class JuheManager {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY = "a69a5764f670ccd1f473f21e1f4dad9c";

    // 短信模板Id(4852): 【南京易橙汇】欢迎您注册#app#，您的验证码是#code#
    public static final String TPL_ID = "4852";
    public static final String TPL_APP = "南京易橙汇会员";



    //1.屏蔽词检查测
    public static void getRequest1() {
        String result = null;
        String url = "http://v.juhe.cn/sms/black";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("word", "");//需要检测的短信内容，需要UTF8 URLENCODE
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)

        try {
            result = net(url, params, "GET");
//                    JSONObject object = JSONObject.fromObject(result);
//                    if(object.getInt("error_code")==0){
//                        System.out.println(object.get("result"));
//                    }else{
//                        System.out.println(object.get("error_code")+":"+object.get("reason"));
//                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * @param mobile 注册的手机号
    * @param codeNum 验证码
    * @Description:  发送短信验证码
    * @Return:
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/26 13:59
    * @Version 2.0
    */
    public static void sendSignInMassage(String mobile,String codeNum) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map<String, Object> params = new HashMap();//请求参数
        // 接收短信的手机号码
        params.put("mobile", mobile);
        // 短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_id", TPL_ID);
         /*
         变量名和变量值对。
         如果你的变量名或者变量值中带有#&=中的任意一个特殊符号
         请先分别进行urlencode编码后再传递
         如连接: http://www.juhe.cn/news/index/id/50" target="_blank
         */
        params.put("tpl_value", "#code#=" + codeNum + "&#app#=" + TPL_APP + "");
        // 应用APPKEY(应用详细页查询)
        params.put("key", APPKEY);
        // 返回数据的格式,xml或json，默认json
        params.put("dtype", "json");
        try {
            result = net(url, params, "GET");
            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();

            // 获取错误状态码!
            int error_code = jsonObject.get("error_code").getAsInt();
            // 判断发送验证码是否正确
            if (error_code  == 0) {
                System.out.println(result);
            } else {
                // 在错误的情况下.打印错误码和错误原因
                System.out.println(
                        jsonObject.get("error_code").getAsInt() + ":"
                                + jsonObject.get("reason").getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}