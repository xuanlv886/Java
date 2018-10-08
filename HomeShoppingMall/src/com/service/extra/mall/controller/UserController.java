package com.service.extra.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.extra.mall.service.ProductService;
import com.service.extra.mall.service.StoreService;
import com.service.extra.mall.service.UserService;

import util.Utils;

/**
 * 
 * desc：管理后台相关接口控制器
 * @author J
 * time:2018年5月17日
 */
@Controller
public class UserController {
	@Resource private ProductService productService;
	@Resource private StoreService storeService;
	@Resource private UserService userService;
	
	//获取日志记录器，这个记录器将负责控制日志信息
		Logger logger = Logger.getLogger(ManageSystemController.class);
	/**
	 * 
  	 * desc:管理后台用户登录接口
	 * param:account--账号
	 * 		password--密码
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/userLogin")
	@ResponseBody
	public Map<String,Object> doUserLogin (String uAccount, String uPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uAccount) 
				|| Utils.isEmpty(uPassword)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";	
			Object data = userService.doUserLogin(uAccount, uPassword);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:用户注册接口
	 * param:uAccount--用户账号
	 * 		uPassword--密码
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/userRegistered")
	@ResponseBody
	public Map<String,Object> doUserRegistered (String uAccount, String uPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uAccount) 
				|| Utils.isEmpty(uPassword)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUserRegistered(uAccount, uPassword);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户信息接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/getUserInfo")
	@ResponseBody
	public Map<String,Object> doGetUserInfo (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserInfo(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}

	/**
	 * 
  	 * desc:获取商户个人、店铺相关信息接口
	 * param:uId--商户主键标识
	 * 			sId--商户店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/getStoreUserInfo")
	@ResponseBody
	public Map<String,Object> doGetStoreUserInfo (String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetStoreUserInfo(uId, sId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改商户个人、店铺相关信息接口
	 * param:uId--商户主键标识
	 * 			sId--商户店铺主键标识
	 * 			which--标识修改的内容 0--商户账户信息，1--商户店铺信息
	 * 			uNickName--商户账号昵称 
	 * 			pTag--商户账号头像图片二级标识
	 * 			pName--商户账号头像图片名称
	 * 			uSex--商户性别
	 * 			uEmail--商户联系邮箱
	 * 			uBirthday--商户出生日期
	 * 			uTel--商户账号联系方式
	 * 			sName--商户店铺名称
	 * 			sDescribe--商户描述
	 * 			sTel--商户联系电话
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/changeStoreUserInfo")
	@ResponseBody
	public Map<String,Object> dochangeStoreUserInfo (String uId, String sId,
			String which, String uNickName, String pTag, String pName, String uSex, String uEmail,
			String uBirthday, String uTel, String sName, String sDescribe,
			String sTel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.dochangeStoreUserInfo(uId, sId,
					which, uNickName, pTag, pName, uSex, uEmail, uBirthday,
					uTel, sName, sDescribe, sTel);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改密码接口
	 * param:uAccount用户账号,uPassword用户原先密码,uNewPassword用户新密码,uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateUserPassword")
	@ResponseBody
	public Map<String,Object> doUpdateUserPassword (String uAccount,String uPassword,String uNewPassword,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uAccount)||Utils.isEmpty(uPassword)||Utils.isEmpty(uNewPassword)||Utils.isEmpty(uId) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserPassword(uAccount,uPassword,uNewPassword,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:设置账户密保接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateUserSecretQuestion")
	@ResponseBody
	public Map<String,Object> doUpdateUserSecretQuestion (String uId,
			String uFirstSqId,String uFirstSqAnswer,String uSecondSqId,
			String uSecondSqAnswer,String uThirdSqId,String uThirdSqAnswer){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(uFirstSqId)||Utils.isEmpty(uFirstSqAnswer)||Utils.isEmpty(uSecondSqId)||Utils.isEmpty(uSecondSqAnswer) ||Utils.isEmpty(uThirdSqId) ||Utils.isEmpty(uThirdSqAnswer)   
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserSecretQuestion(uId,uFirstSqId,uFirstSqAnswer,uSecondSqId,uSecondSqAnswer,uThirdSqId,uThirdSqAnswer);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户安全问题接口
	 * param:sqPosition安全问题位置
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/getSafetyQuestion")
	@ResponseBody
	public Map<String,Object> doGetSafetyQuestion (String sqPosition){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(sqPosition)   
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetSafetyQuestion(sqPosition);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改个人信息接口
	 * param:uId用户主键标识,uNickName用户昵称,uSex用户性别,uEmail用户邮箱,uBirthday用户生日
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateUserInfo")
	@ResponseBody
	public Map<String,Object> doUpdateUserInfo (String uId,String uNickName,String uSex,String uEmail,String uBirthday){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(uNickName)||Utils.isEmpty(uSex)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserInfo(uId,uNickName,uSex,uEmail,uBirthday);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改用户头像接口
	 * param:uId用户主键标识,pName头像图片名称
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateUserAvatar")
	@ResponseBody
	public Map<String,Object> doUpdateUserAvatar (String uId,String pName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(pName))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserAvatar(uId,pName);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户收货地址接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/getUserDeliverAddress")
	@ResponseBody
	public Map<String,Object> doGetUserDeliverAddress (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserDeliverAddress(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:添加用户收货地址接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/addUserDeliverAddress")
	@ResponseBody
	public Map<String,Object> doAddUserDeliverAddress (String uId,String udaTrueName,String udaTel,String udaAddress){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(udaTrueName)||Utils.isEmpty(udaTel)||Utils.isEmpty(udaAddress))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doAddUserDeliverAddress(uId,udaTrueName,udaTel,udaAddress);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改用户收货地址接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateUserDeliverAddress")
	@ResponseBody
	public Map<String,Object> doUpdateUserDeliverAddress (String udaId,String uId,String udaTrueName,String udaTel,String udaAddress){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(udaId)||Utils.isEmpty(uId)||Utils.isEmpty(udaTrueName)||Utils.isEmpty(udaTel)||Utils.isEmpty(udaAddress))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserDeliverAddress(udaId,uId,udaTrueName,udaTel,udaAddress);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:删除用户收货地址接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/delUserDeliverAddress")
	@ResponseBody
	public Map<String,Object> doDelUserDeliverAddress (String udaId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(udaId)||Utils.isEmpty(uId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doDelUserDeliverAddress(udaId,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:设置默认收货地址接口
	 * param:uId用户主键标识,udaId收货地址主键标识
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/setDefaultUserDeliverAddress")
	@ResponseBody
	public Map<String,Object> doSetDefaultUserDeliverAddress (String udaId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和udaId进行非空判断
		if (Utils.isEmpty(udaId)||Utils.isEmpty(uId))  {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doSetDefaultUserDeliverAddress(udaId,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户关注的店铺接口
	 * param:uId用户主键标识,page页数,size每页条数
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getUserAttention")
	@ResponseBody
	public Map<String,Object> doGetUserAttention (String uId,String page,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId) || Utils.isEmpty(page) || Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserAttention(uId,page,size);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:用户关注的店铺接口
	 * param:uId用户主键标识,sId店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/addUserAttention")
	@ResponseBody
	public Map<String,Object> doAddUserAttention (String uId,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId))  {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doAddUserAttention(uId,sId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:取消关注的店铺接口
	 * param:uId用户主键标识,uaId用户关注主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/delUserAttention")
	@ResponseBody
	public Map<String,Object> doDelUserAttention (String uId,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doDelUserAttention(uId,sId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:商品添加购物车接口
	 * param:uId用户主键标识,pId商品主键标识,sId店铺主键标识,utProductNum商品数量,utProductProperty商品属性
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/addUserTrolley")
	@ResponseBody
	public Map<String,Object> doAddUserTrolley (String uId,String pId,String sId,String utProductNum,String utProductProperty){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(pId)||Utils.isEmpty(sId))  {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doAddUserTrolley(uId,pId,sId,utProductNum,utProductProperty);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:删除购物车商品接口
	 * param:uId用户主键标识,uaIdList用户关注主键标识字符串
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/delUserTrolley")
	@ResponseBody
	public Map<String,Object> doDelUserTrolley (String uId,String utIdList){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,utIdList进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(utIdList))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doDelUserTrolley(uId,utIdList);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户购物车商品接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getUserTrolley")
	@ResponseBody
	public Map<String,Object> doGetUserTrolley (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserTrolley(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:添加商品收藏接口
	 * param:uId用户主键标识,pId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/addUserCollection")
	@ResponseBody
	public Map<String,Object> doAddUserCollection (String uId,String pId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(pId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doAddUserCollection(uId,pId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:删除商品收藏接口
	 * param:uId用户主键标识,pId商品主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/delUserCollection")
	@ResponseBody
	public Map<String,Object> doDelUserCollection (String uId,String pId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(pId))  {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doDelUserCollection(uId,pId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取商品收藏接口
	 * param:uId用户主键标识,page页数,size每页条数
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getUserCollection")
	@ResponseBody
	public Map<String,Object> doGetUserCollection (String uId,String page,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId) || Utils.isEmpty(page) || Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserCollection(uId,page,size);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取用户足迹接口
	 * param:uId用户主键标识,page页数,size每页条数
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getUserFootPrint")
	@ResponseBody
	public Map<String,Object> doGetUserFootPrint (String uId,String page,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId) || Utils.isEmpty(page) || Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserFootPrint(uId,page,size);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:删除用户足迹接口
	 * param:uId用户主键标识,ufIdList用户足迹主键标识字符串
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/delUserFootPrint")
	@ResponseBody
	public Map<String,Object> doDelUserFootPrint (String uId,String ufIdList){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(ufIdList)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doDelUserFootPrint(uId,ufIdList);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:添加用户反馈意见接口
	 * param:uId用户主键标识,fType反馈问题类型,fAppType反馈客户端类型,fContent反馈内容
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/addUserFeedBack")
	@ResponseBody
	public Map<String,Object> doAddUserFeedBack (String uId,String fType,
			String fAppType,String fContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(fType)||Utils.isEmpty(fAppType)||Utils.isEmpty(fContent))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doAddUserFeedBack(uId,fType,fAppType,fContent);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取支付方式接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getPayStyle")
	@ResponseBody
	public Map<String,Object> doGetPayStyle (){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetPayStyle();
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:忘记密码接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/forgetSecretQuestion")
	@ResponseBody
	public Map<String,Object> doForgetSecretQuestion (String uId,String uPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(uPassword))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doForgetSecretQuestion(uId,uPassword);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取城市列表接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/getCityList")
	@ResponseBody
	public Map<String,Object> doGetCityList (){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetCityList();
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取个人中心接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/getPersonCenter")
	@ResponseBody
	public Map<String,Object> doGetPersonCenter (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetPersonCenter(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:检查更新接口
	 * param:vType 用户端类型 （0-客户端，1-商户端）Code 用户端版本号,phoneType 手机类型 （0-Android/1-Apple）
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/version")
	@ResponseBody
	public Map<String,Object> doGetversion (String vType,String Code,String phoneType){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对vType,vCode进行非空判断
		if (Utils.isEmpty(vType)||Utils.isEmpty(Code)||Utils.isEmpty(phoneType)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetversion(vType,Code,phoneType);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:重置用户密码
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/resetUserPassword")
	@ResponseBody
	public Map<String,Object> doResetUserPassword (String uId,String uTel,String uNewPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,uTel,uNewPassword进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(uTel)||Utils.isEmpty(uNewPassword))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doResetUserPassword(uId,uTel,uNewPassword);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:修改密保接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月23日
	 * author:J
	 */
	@RequestMapping(value="/updateSecretQuestion")
	@ResponseBody
	public Map<String,Object> doUpdateSecretQuestion (String uId,String uOldFirstSqId,String uOldFirstSqAnswer,String uOldSecondSqId,String uOldSecondSqAnswer,
			String uOldThirdSqId,String uOldThirdSqAnswer,String uNewFirstSqId,String uNewFirstSqAnswer,
			String uNewSecondSqId,String uNewSecondSqAnswer,String uNewThirdSqId,String uNewThirdSqAnswer){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,uTel,uNewPassword进行非空判断
		if (Utils.isEmpty(uId))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateSecretQuestion(uId,uOldFirstSqId,uOldFirstSqAnswer,uOldSecondSqId,uOldSecondSqAnswer,uOldThirdSqId,uOldThirdSqAnswer,
					uNewFirstSqId,uNewFirstSqAnswer,uNewSecondSqId,uNewSecondSqAnswer,uNewThirdSqId,uNewThirdSqAnswer);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:用户验证密保问题
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:J
	 */
	@RequestMapping(value="/getUserAnswerSecretQuestion")
	@ResponseBody
	public Map<String,Object> doGetUserAnswerSecretQuestion (String uId,String firstAnswer,String secondAnswer,String thirdAnswer){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserAnswerSecretQuestion(uId,firstAnswer,secondAnswer,thirdAnswer);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取引导页图片接口
	 * param:gAppType--客户端类型 0--用户端，1--商户端
	 * 			gEdition--引导页版本
	 * return:Map<String,Object>
	 * time:2018年6月4日
	 * author:J
	 */
	@RequestMapping(value="/getGuidePic")
	@ResponseBody
	public Map<String,Object> doGetGuidePic (String gAppType, String gEdition){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,oldPayPassword,newPayPassword进行非空判断
		if (Utils.isEmpty(gAppType))  
		{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetGuidePic(gAppType, gEdition);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:主页面接口
	 * param:acId--行政区划主键标识
	 * 			uId--用户主键标识
	 * 			page--当前页
	 * 			pageSize--页大小
	 * return:Map<String,Object>
	 * time:2018年6月14日
	 * author:J
	 */
	@RequestMapping(value="/getMainInterface")
	@ResponseBody
	public Map<String,Object> doGetMainInterface (String acId, String uId,
			String page, String pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetMainInterface(acId,uId, page,pageSize);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:获取行政区信息接口
	 * param:acCode--行政区划代码
	 * return:Map<String,Object>
	 * time:2018年6月14日
	 * author:J
	 */
	@RequestMapping(value="/getAreaCode")
	@ResponseBody
	public Map<String,Object> doGetAreaCode (String acCode){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetAreaCode(acCode);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}


	/**
	 * 
  	 * desc:更新用户设备ID并返回用户信息接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:J
	 */
	@RequestMapping(value="/updateUserPhoneIdAndReturnUserInfo")
	@ResponseBody
	public Map<String,Object> doUpdateUserPhoneIdAndReturnUserInfo (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateUserPhoneIdAndReturnUserInfo(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc:找回密码接口
	 * param:uAccount--用户账号（手机号）
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:J
	 */
	@RequestMapping(value="/retrievePsw")
	@ResponseBody
	public Map<String,Object> doRetrievePsw (String uAccount){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uAccount)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doRetrievePsw(uAccount);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}

	
	/**
	 * 
  	 * desc:获取行政区信息接口
	 * param:acCode--行政区划代码
	 * return:Map<String,Object>
	 * time:2018年6月14日
	 * author:J
	 */
	@RequestMapping(value="/getSearchCityList")
	@ResponseBody
	public Map<String,Object> getSearchCityList (String keyWords){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetSearchCityList(keyWords);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}

	/**
	 * 
  	 * desc:修改商户身份用户密码接口
	 * param:uId--商户主键标识
	 * 			sId--商户店铺主键标识
	 * 			uAccount--商户账号
	 * 			uPassword--商户原先密码,
	 * 			uNewPassword--商户新密码
	 * return:Map<String,Object>
	 * time:2018年5月17日
	 * author:J
	 */
	@RequestMapping(value="/updateStoreUserPsw")
	@ResponseBody
	public Map<String,Object> doUpdateStoreUserPsw (String uId, String sId,
			String uAccount, String uPassword,String uNewPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doUpdateStoreUserPsw(uId, sId, uAccount,
					uPassword, uNewPassword);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}

	/**
	 * 
  	 * desc:检查更新接口
	 * param: uId--用户主键标识
	 * 			vType--应用类型0--用户端，1--商户端，2--后台端
	 * 			vBuildCode--应用版本
	 * 			vSystemType--系统类型0--安卓端，1--苹果端，2--后台端
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/checkUpdate")
	@ResponseBody
	public Map<String,Object> doCheckUpdate (String uId,String vType,
			String vBuildCode, String vSystemType){
		Map<String,Object> map = new HashMap<String,Object>();
		if (Utils.isEmpty(vType)
				|| Utils.isEmpty(vBuildCode) 
				|| Utils.isEmpty(vSystemType)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doCheckUpdate(vType,vBuildCode,
					vSystemType);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc: 获取某商户针对于特定需求订单的移动轨迹列表
	 * param:uId--用户主键标识
	 * 			roId--需求订单主键标识
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:L
	 */
	@RequestMapping(value="/getMerchantLonLatList")
	@ResponseBody
	public Map<String,Object> doGetMerchantLonLatList (String uId,String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(roId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetMerchantLonLatList(uId, roId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc: 为用户设置备注名
	 * param:fromUid--命名备注名的用户主键标识
	 * 			toUid--被命名的用户的主键标识
	 * 			memoName--备注名
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:L
	 */
	@RequestMapping(value="/setUserMemoName")
	@ResponseBody
	public Map<String,Object> doSetUserMemoName (String fromUid,String toUid, String memoName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(fromUid)||Utils.isEmpty(toUid)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doSetUserMemoName(fromUid, toUid, memoName);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * 
  	 * desc: 获取某用户为别人设置的备注名
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:L
	 */
	@RequestMapping(value="/getUserMemoName")
	@ResponseBody
	public Map<String,Object> doGetUserMemoName (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = userService.doGetUserMemoName(uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
}
