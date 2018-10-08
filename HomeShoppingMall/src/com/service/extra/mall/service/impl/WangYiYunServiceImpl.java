package com.service.extra.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.service.WangYiYunService;
import com.service.extra.mall.wangyiyun.CheckSumBuilder;

import util.Utils;

@Service
public class WangYiYunServiceImpl implements WangYiYunService{
	
	@Resource private UserMapper userMapper;

	private static final String UPDATEUINFO = "https://api.netease.im/nimserver/user/updateUinfo.action"; //更新名片
	
	private static final String GETUINFOS = "https://api.netease.im/nimserver/user/getUinfos.action"; //获取用户名片
	
	private static final String CREATE = "https://api.netease.im/nimserver/user/create.action"; //创建网易云通信ID
	
	private static final String UPDATE = "https://api.netease.im/nimserver/user/update.action"; //更新网易云通信ID
	
	private static final String REFRESH_TOKEN = "https://api.netease.im/nimserver/user/refreshToken.action"; //更新并获取token
	
	private static final String QUERY_SESSION_MSG = "https://api.netease.im/nimserver/history/querySessionMsg.action"; //单聊云端历史消息查询
	
	private static final String SEND_MSG = "https://api.netease.im/nimserver/msg/sendMsg.action"; //发送普通消息
	
	private static final String RECALL = "https://api.netease.im/nimserver/msg/recall.action"; //消息撤回
	
	
	@Override
	public Object updateUinfo(String accid, String name, String ex) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = UPDATEUINFO;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", accid));
	        nvps.add(new BasicNameValuePair("name", name));
	        nvps.add(new BasicNameValuePair("ex", ex));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

	@Override
	public Object create(String accid, String name) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = CREATE;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", accid));
	        nvps.add(new BasicNameValuePair("name", name));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        
	        // 执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		return result;
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Object sendMsg(String from, String to, String body, String ope, String type) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = SEND_MSG;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("from", from));
	        nvps.add(new BasicNameValuePair("to", to));
	        nvps.add(new BasicNameValuePair("ope", ope));
	        nvps.add(new BasicNameValuePair("type", type));
	        nvps.add(new BasicNameValuePair("body", body));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        // 打印执行结果
//	        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
	        /**
	         * 插入消息抄送表
	         */
	        String mcEventType = null;
	        String mcConvType = null;
	        String mcTo = null;
	        String mcFrom = null;
	        String mcTimeStamp = null;
	        String mcMsgType = null;
	        String mcMsgIdClient = null;
	        String mcMsgIdServer = null;
	        String mcBody = null;
	       int row = userMapper.doAddMessageCopy(mcEventType,mcConvType,mcTo,mcFrom,mcTimeStamp,mcMsgType,mcMsgIdClient,mcMsgIdServer,mcBody);
	       if (1 != row) {
				map.put("status", "false");
				map.put("errorString", "插入消息抄送失败");
				throw new RuntimeException();
			}
		return map;
	}

	@Override
	public Object recall(String deleteMsgid, String timetag, String type, String from, String to) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = RECALL;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", "helloworld"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

	@Override
	public Object querySessionMsg(String from, String to, String begintime, String endtime, String limit)
			throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = QUERY_SESSION_MSG;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", "helloworld"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

	@Override
	public Object getUinfos(String accids) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = GETUINFOS;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", accids));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

	@Override
	public Object update(String accid) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = UPDATE;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", accid));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

	@Override
	public Object refreshToken(String accid) throws Exception {
		  DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = REFRESH_TOKEN;
	        HttpPost httpPost = new HttpPost(url);

	        String appKey = Utils.APPKEY;
	        String appSecret = Utils.APPSECRET;
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("accid", accid));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        HttpResponse response = httpClient.execute(httpPost);
	        
	        // 打印执行结果
	        String result = "";
	        result = EntityUtils.toString(response.getEntity(), "utf-8");
		
		return result;
	}

}
