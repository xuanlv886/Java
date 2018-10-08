package com.service.extra.mall.weixinpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class UserMessageUtil {
	private static final String API_SECRET = ConfigUtil.getProperty("wx.api.secret0");
	private static final String APP_ID = ConfigUtil.getProperty("wx.appid0");
	
	public static String byteToStr(byte[] bytearray){
		String strDigest="";
		for(int i=0;i<bytearray.length;i++){
			strDigest += byteToHexStr(bytearray[i]);
		}
		return strDigest;
	} 
	
	private static String byteToHexStr(byte ib){
		char[] Digit ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4)& 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s =new String(ob);
		return s;
	}
	
	public static String getAccessToken() throws Exception{
		Map<String,String> map =new HashMap<>();
		String requestUrl ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+API_SECRET;
System.out.println(requestUrl);
		map= httpsRequest(requestUrl,"GET");
		String accessToken = map.get("access_token");
		return accessToken;
	}
	
	public static Map<String,String> httpsRequest(String requestUrl , String requestType) throws Exception{
		Map<String,String> map =new HashMap<>();
		URL url = new URL(requestUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		//使用自定义的信任管理
		TrustManager [] tm = {new MyX509TrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		SSLSocketFactory ssf  = sslContext.getSocketFactory();
		conn.setSSLSocketFactory(ssf);
		conn.setDoInput(true);
		//设置请求方式
		conn.setRequestMethod(requestType);
		//取得输入流
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader =new InputStreamReader(inputStream,"utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		//读取相应内容
		StringBuffer buffer = new StringBuffer();
		String str="";
		while ((str = bufferedReader.readLine())!=null){
			buffer.append(str);
		}
System.out.println("buffer : "+buffer.toString());
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		conn.disconnect();
			
		Gson gson = new Gson();
		map=gson.fromJson(buffer.toString(),
				new TypeToken<Map<String, String>>() {
				}.getType());
		return map;
		}
	
	
	
}
