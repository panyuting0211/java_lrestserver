/**
 * 包打听全知道-微信H5版本
 * weixin.base
 * AccessToken.java
 * Ver0.0.1
 * 2016年6月20日-下午3:06:02
 *  2016全智道(北京)科技有限公司-版权所有
 * 
 */
package com.lrest.server.services.services.weixin.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lrest.server.services.services.weixin.WXAPIConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;


public class AccessTokenManager extends   TimerTask {

	private static  final Logger log = LoggerFactory.getLogger(AccessTokenManager.class);


	//AccessToken 与微信服务器通讯的重要票据
	//public static String accesstoken = "";
	//public static int expires_in = 7200;			//默认是7200秒过期

	/**
	 * 
	 * 创建一个新的实例 AccessTokenManager.
	 */
	public AccessTokenManager(){

		run();
	}
	
	@Override
	public void run() {
		reqData();
	}


    public void reqData(){
		try {

			OkHttpClient client=new OkHttpClient();
			Request request = new Request.Builder().url(WXAPIConfig.ACCESSTOKEN_GET_URL+ WXAPIConfig.AppID+"&secret="+ WXAPIConfig.AppSecret).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				//Gson json= (new Gson()).t
				//log.info("URL="+request.url()+"\n"+response.body().string());
				JsonElement json=new JsonParser().parse(response.body().string());
				log.debug("URL="+request.url()+"\n"+json.toString());
				log.debug(json.getAsJsonObject().get("access_token").toString());
				WXAPIConfig.AccessToken=json.getAsJsonObject().get("access_token").toString();
				WXAPIConfig.AccessTokenExpires=Integer.parseInt(json.getAsJsonObject().get("expires_in").toString());

				log.info("wxapiconfig token update.");
			} else {
				log.error("Unexpected code " + response);
				//throw new IOException("Unexpected code " + response);
			}
		}catch (Exception e){
				e.printStackTrace();
		}


    }
    
    

    public  void main11(String args[]){
    	

    		AccessTokenManager accessTokenManager  = new AccessTokenManager();
    		Timer timer  = new Timer();
    		timer.schedule(accessTokenManager, 0, WXAPIConfig.AccessTokenExpires-1000);
  
    	
    		
    }
}
