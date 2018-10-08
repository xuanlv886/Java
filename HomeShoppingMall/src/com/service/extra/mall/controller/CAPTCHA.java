package com.service.extra.mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;

import util.BatchPublishSMSMessage;
import util.TemplateCode;
import util.Utils;
/**
 * 
 * desc：短信接口控制器
 * @author J
 * time:2018年5月17日
 */
@Controller
public class CAPTCHA {
	
//	private String tip = "如有疑问，请联系客服。客服热线：123456789";
	/**
	 * desc: 获取短信验证码接口
	 * param:uTel电话号码
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/getCaptcha")
	@ResponseBody 
	public Map<String,Object>  UR_CAPTCHA (String uTel) throws ClientException{
		Map <String,String>data = new HashMap<String,String>();
		Map <String,Object>map = new HashMap<String,Object>();
		int num=(int) ((Math.random()*9+1)*100000);
		String code = String.valueOf(num);
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("code", code);
//		BatchPublishSMSMessage.Push(uTel, paramMap, TemplateCode.CAPTCHA);
		boolean flag = true;
		String errorString = "";
		data.put("CAPTCHA", code);
		map = Utils.packageResponseDate(flag, errorString, data);
		return map ;
	}
	



	/**
	 * desc: 发送商户审核结果通知接口 
	 * 		尊敬的${name}您好，您的账号${result}审核。审核意见：${content}。
	 * param:uTel电话号码
	 * 		sName--商户名称
	 * 		result--审核结果
	 * 		content--审核意见
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/sendStoreCheckedNotice")
	@ResponseBody 
	public Map<String,Object>  sendStoreCheckedNotice (String uTel, String sName,
			String result, String content) throws ClientException{
		Map <String,Object>map = new HashMap<String,Object>();
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("name", sName);
		paramMap.put("result", uTel + result);
		paramMap.put("content", content);
//		BatchPublishSMSMessage.Push(uTel, paramMap, 
//				TemplateCode.STORE_CHECKED_NOTICE);
		boolean flag = true;
		String errorString = "";
		map = Utils.packageResponseDate(flag, errorString, null);
		return map ;
	}
	
	/**
	 * desc: 发送商品审核结果通知接口 
	 * 		尊敬的${name}您好，您上传的商品${product}通过审核。审核意见：${content}。
	 * param:uTel电话号码
	 * 		sName--商户名称
	 * 		pName--商品名称
	 * 		content--审核意见
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/sendProductCheckedNotice")
	@ResponseBody 
	public Map<String,Object>  sendProductCheckedNotice (String uTel,
			String sName, String pName, String content) throws ClientException{
		Map <String,Object>map = new HashMap<String,Object>();
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("name", sName);
		paramMap.put("product", pName);
		paramMap.put("content", content);
//		BatchPublishSMSMessage.Push(uTel, paramMap, 
//				TemplateCode.PRODUCT_CHECKED_NOTICE);
		boolean flag = true;
		String errorString = "";
		map = Utils.packageResponseDate(flag, errorString, null);
		return map ;
	}

	/**
	 * desc: 发送密码接口
	 * 		当前密码：${code}，您正进行找回密码，若非本人操作，请勿泄露。
	 * param:uTel电话号码
	 * 			code--用户密码
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/sendUserPasswordNotice")
	@ResponseBody 
	public Map<String,Object>  sendUserPasswordNotice (String uTel,
			String code) throws ClientException{
		Map <String,Object>map = new HashMap<String,Object>();
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("code", code);
//		BatchPublishSMSMessage.Push(uTel, paramMap, TemplateCode.RETRIEVE_PASSWORD_NOTICE);
		boolean flag = true;
		String errorString = "";
		map = Utils.packageResponseDate(flag, errorString, null);
		return map;
	}
}

