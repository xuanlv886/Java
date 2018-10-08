package com.service.extra.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.extra.mall.service.WangYiYunService;

import util.Utils;

@Controller
public class WangYiYunController {
	Logger logger = Logger.getLogger(WangYiYunController.class);//获取日志记录器，这个记录器将负责控制日志信息
	@Resource private WangYiYunService wangYiYunService;
	/**
	 * 
	 * desc：创建网易云通信ID接口
	 * @author J
	 * time:2018年6月8日
	 * param:accid用户账号,name用户名称
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Map<String,Object> create (String accid,String name){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.create(accid,name);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：更新网易云通信ID接口
	 * @author J
	 * time:2018年6月12日
	 * param:accid用户账号
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update (String accid){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.update(accid);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：更新并获取新token接口
	 * @author J
	 * time:2018年6月12日
	 * param:accid用户账号
	 */
	@RequestMapping("/refreshToken")
	@ResponseBody
	public Map<String,Object> refreshToken (String accid){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.refreshToken(accid);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：更新用户名接口
	 * @author J
	 * time:2018年6月8日
	 * param:accid用户账号,name用户名称,ex用户额外字段
	 */
	@RequestMapping("/updateUinfo")
	@ResponseBody
	public Map<String,Object> updateUinfo (String accid,String name, String ex){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.updateUinfo(accid,name, ex);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	/**
	 * 
	 * desc：发送普通消息
	 * @author J
	 * time:2018年6月9日
	 * param:body消息内容,from发送者accid,to接受者accid,ope,type
	 */
	@RequestMapping("/sendMsg")
	@ResponseBody
	public Map<String,Object> sendMsg (String from,String to,String body,String ope,String type){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.sendMsg(from,to,body,ope,type);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	/**
	 * 
	 * desc：消息撤回接口
	 * @author J
	 * time:2018年6月12日
	 * param:deleteMsgid要撤回消息的msgid,timetag要撤回消息的创建时间,type 7:表示点对点消息撤回，8:表示群消息撤回，其它为参数错误,
	 * from发消息的accid,to如果点对点消息，为接收消息的accid,如果群消息，为对应群的tid
	 */
	@RequestMapping("/recall")
	@ResponseBody
	public Map<String,Object> recall (String deleteMsgid,String timetag,String type,String from,String to){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.recall(deleteMsgid,timetag,type,from,to);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：单聊历史记录接口
	 * @author J
	 * time:2018年6月12日
	 * param:from发消息的accid,to接受者accid，begintime开始时间，ms,endtime截止时间，ms,limit本次查询的消息条数上限(最多100条),小于等于0，或者大于100，会提示参数错误
	 */
	@RequestMapping("/querySessionMsg")
	@ResponseBody
	public Map<String,Object> querySessionMsg (String from,String to,String begintime,String endtime,String limit){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.querySessionMsg(from,to,begintime,endtime,limit);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：获取用户名片接口
	 * @author J
	 * time:2018年6月12日
	 * param:accids用户帐号（例如：JSONArray对应的accid串，如：["zhangsan"]，如果解析出错，会报414）（一次查询最多为200）
	 */
	@RequestMapping("/getUinfos")
	@ResponseBody
	public Map<String,Object> getUinfos (String accids){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString="";
			Object data =wangYiYunService.getUinfos(accids);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			boolean flag = false;
			String errorString="网络连接异常";
			Object data = null;
			map = Utils.packageResponseDate(flag, errorString, data);
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return map;
		
	}
}
