package com.service.extra.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.extra.mall.service.ManageSystemService;

import util.Utils;

/**
 * 
 * desc：管理后台相关接口控制器
 * @author L
 * time:2018年4月23日
 */
@Controller
public class ManageSystemController {

	//获取日志记录器，这个记录器将负责控制日志信息
	Logger logger = Logger.getLogger(ManageSystemController.class);
		
	@Resource private ManageSystemService manageSystemService;
	
	/**
	 * 
  	 * desc:管理后台用户登录接口
	 * param:account--账号
	 * 		password--密码
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msLogin")
	@ResponseBody
	public Map<String,Object> doMsLogin (String account, String password){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(account) 
				|| Utils.isEmpty(password)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsLogin(account, password);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	// -----------------------管理员身份相关接口----------------
	/**
	 * 
	 * desc:获取系统公告列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetSysNoticeList")
	@ResponseBody
	public Map<String,Object> doMsGetSysNoticeList (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetSysNoticeList(uId);
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
	 * desc:获取管理员首页相关数据信息接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetAdminIndexInfo")
	@ResponseBody
	public Map<String,Object> doMsGetAdminIndexInfo (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetAdminIndexInfo(uId);
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
	 * desc:根据条件获取店铺列表接口
	 * param:uId--用户主键标识
	 * 			sName--店铺名称
	 * 			sType--店铺类型 0--个人店铺，1--公司店铺
	 * 			sLeader--店铺负责人姓名
	 * 			sChecked--店铺审核状态 0--未通过，1--已通过
	 * 			isRecommend--是否推荐店铺
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetStoreList")
	@ResponseBody
	public Map<String,Object> doMsGetStoreList (String uId, String sName,
			String sType, String sLeader, String sChecked, String isRecommend,
			String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetStoreList(sName,
					sType, sLeader, sChecked, isRecommend, start, end);
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
	 * desc:获取特定店铺保障金、可用余额、已发布商品数量接口
	 * param:uId--用户主键标识
	 * 			sId--店铺主键标识
	 * 			sType--店铺类型 0--个人店铺，1--公司店铺
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetStoreOtherInfoBySId")
	@ResponseBody
	public Map<String,Object> doMsGetStoreOtherInfoBySId (String uId, String sId,
			String sType){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetStoreOtherInfoBySId(sId,
					sType);
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
	 * desc:修改店铺审核状态接口
	 * param:uId--用户主键标识
	 * 			sId--店铺主键标识
	 * 			sChecked--店铺是否通过审核 0--否，1--是
	 * 			sCheckedOpinion--审核意见
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msChangeStoreCheckStatus")
	@ResponseBody
	public Map<String,Object> doMsChangeStoreCheckStatus (String uId, String sId,
			String sChecked, String sCheckedOpinion){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsChangeStoreCheckStatus(uId, sId,
					sChecked, sCheckedOpinion);
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
	 * desc:添加或修改店铺配置信息接口
	 * param:uId--用户主键标识
	 * 			sId--店铺主键标识
	 * 			sOrderLimint--店铺接单限额
	 * 			requirementServiceCharge--需求交易手续费
	 * 			productServiceCharge--商品交易手续费
	 * 			sLevel--店铺评级
	 * 			sWeight--店铺权重
	 * 			sBoothNum--推荐展位数量
	 * 			isRecommend--是否为推荐店铺
	 * 			rsTitle--推荐标题
	 * 			rsContent--推荐内容
	 * 			picData--推荐图片
	 * 			例：{"pTag": "16869e8a-46ac-11e8-8390-4ccc6a70ac67","pics": [{"pName": "xx.jpg","pNo": 0},{"pName": "xx.png","pNo": 1},{"pName": "xx.jpg","pNo": 2}]}
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msAddOrChangeStoreConfigInfo")
	@ResponseBody
	public Map<String,Object> doMsAddOrChangeStoreConfigInfo (String uId, String sId,
			String sOrderLimint, String requirementServiceCharge,
			String productServiceCharge, String sLevel, String sWeight,
			String sBoothNum, String isRecommend, String rsTitle, String rsContent,
			String picData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsAddOrChangeStoreConfigInfo(
					uId, sId, sOrderLimint, requirementServiceCharge,
					productServiceCharge, sLevel, sWeight,
				    sBoothNum, isRecommend, rsTitle, rsContent, picData);
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
	 * desc:获取商品大类列表
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductTypeList")
	@ResponseBody
	public Map<String,Object> doMsGetProductTypeList (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetProductTypeList();
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
	 * desc:根据条件获取商品列表接口
	 * param:uId--用户主键标识
	 * 			userType--用户身份 0--企业商户，1--管理员（用来区别返回的商品列表是否含已删除的商品）
	 * 			pName--商品名称
	 * 			pHaveBooth--是否为推荐商品  0--否，1--是
	 * 			ptId--商品大类主键标识
	 * 			pChecked--商品审核状态 0--未通过，1--已通过
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductList")
	@ResponseBody
	public Map<String,Object> doMsGetProductList (String uId, String userType,String pName,
			String pHaveBooth, String ptId, String pChecked, 
			String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetProductList(userType, pName,
					pHaveBooth, ptId, pChecked, start, end);
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
	 * desc:修改商品审核状态接口
	 * param:uId--用户主键标识
	 * 			pId--商品主键标识
	 * 			pChecked--商品是否通过审核 0--否，1--是
	 * 			pCheckedOpinion--审核意见
	 * return:Map<String,Object>
	 * time:2018年5月3日
	 * author:L
	 */
	@RequestMapping(value="/msChangeProductCheckStatus")
	@ResponseBody
	public Map<String,Object> doMsChangeProductCheckStatus (String uId, String pId,
			String pChecked, String pCheckedOpinion){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsChangeProductCheckStatus(uId, pId,
					pChecked, pCheckedOpinion);
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
	 * desc:添加或修改商品配置信息接口
	 * param:uId--用户主键标识
	 * 			pId--商品主键标识
	 * 			pWeight--商品权重
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msAddOrChangeProductConfigInfo")
	@ResponseBody
	public Map<String,Object> doMsAddOrChangeProductConfigInfo (
			String uId, String pId, String pWeight){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsAddOrChangeProductConfigInfo(
					uId, pId, pWeight);
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
	 * desc:(系统设置)根据条件获取系统公告列表接口
	 * param:uId--用户主键标识
	 * 			snTitle--系统公告标题
	 * 			uTrueName--系统公告发布人
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetSysNoticeList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetSysNoticeList (
			String uId, String snTitle, String uTrueName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetSysNoticeList(
					snTitle, uTrueName);
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
	 * desc:(系统设置)发布系统公告接口
	 * param:uId--用户主键标识
	 * 			snTitle--系统公告标题
	 * 			snContent--系统公告内容
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingAddSysNotice")
	@ResponseBody
	public Map<String,Object> doMsSysSettingAddSysNotice (
			String uId, String snTitle, String snContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingAddSysNotice(
					uId, snTitle, snContent);
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
	 * desc:(系统设置)修改系统公告接口
	 * param:uId--用户主键标识
	 * 			snId--系统公告主键标识
	 * 			snTitle--系统公告标题
	 * 			snContent--系统公告内容
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingChangeSysNotice")
	@ResponseBody
	public Map<String,Object> doMsSysSettingChangeSysNotice (
			String uId, String snId, String snTitle, String snContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingChangeSysNotice(
					uId, snId, snTitle, snContent);
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
	 * desc:(系统设置)获取系统操作日志操作类型列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetSysOperateLogTypeList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetSysOperateLogTypeList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsSysSettingGetSysOperateLogTypeList();
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
	 * desc:(系统设置)根据条件获取系统操作日志列表接口
	 * param:uId--用户主键标识
	 * 			solType--系统操作日志类型
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetSysOperateLogList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetSysOperateLogList (
			String uId, String solType, String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetSysOperateLogList(
					solType, start, end);
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
	 * desc:(系统设置)根据条件获取反馈意见列表接口
	 * param:uId--用户主键标识
	 * 			fContent--反馈内容
	 * 			fAppType--客户端类型
	 * 			fType--问题类型
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetFeedbackList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetFeedbackList (
			String uId, String fContent, String fAppType, 
			String fType, String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetFeedbackList(
					fContent, fAppType, fType, start, end);
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
	 * desc:(系统设置)获取用户角色列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetUserRoleList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetUserRoleList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetUserRoleList();
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
	 * desc:(系统设置)根据条件获取用户列表接口
	 * param:uId--用户主键标识
	 * 			uAccount--用户账号
	 * 			uTrueName--用户真实姓名
	 * 			uTel--用户联系电话
	 * 			urId--用户角色主键标识
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetUserList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetUserList (
			String uId, String uAccount, String uTrueName, String uTel,
			String urId, String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetUserList(uAccount,
					uTrueName, uTel, urId, start, end);
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
	 * desc:(系统设置)添加用户信息接口
	 * param:uId--用户主键标识
	 * 			uAccount--用户账号
	 * 			uPassword--用户密码
	 * 			uNickName--用户昵称
	 * 			uTrueName--用户真实姓名
	 * 			uSex--用户性别
	 * 			uEmail--用户邮箱
	 * 			uTel--用户联系电话
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingAddUserInfo")
	@ResponseBody
	public Map<String,Object> doMsSysSettingAddUserInfo (
			String uId, String uAccount, String uPassword, String uNickName,
			String uTrueName, String uSex, String uEmail, String uTel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingAddUserInfo(uId,
					uAccount, uPassword, uNickName, uTrueName, uSex, uEmail,
					uTel);
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
	 * desc:(系统设置)修改用户信息接口
	 * param:uId--用户主键标识
	 * 			targetId--要修改的用户的主键标识
	 * 			uAccount--用户账号
	 * 			uPassword--用户密码
	 * 			uNickName--用户昵称
	 * 			uTrueName--用户真实姓名
	 * 			uSex--用户性别
	 * 			uEmail--用户邮箱
	 * 			uTel--用户联系电话
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingChangeUserInfo")
	@ResponseBody
	public Map<String,Object> doMsSysSettingChangeUserInfo (
			String uId, String targetId, String uAccount, String uPassword, String uNickName,
			String uTrueName, String uSex, String uEmail, String uTel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingChangeUserInfo(uId,
					targetId, uAccount, uPassword, uNickName, uTrueName, uSex, uEmail,
					uTel);
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
	 * desc:(系统设置)获取全国行政区划列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetAreaList")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetAreaList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetAreaList();
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
	 * desc:(系统设置)根据城市名称获取城市相关数据接口
	 * param:uId--用户主键标识
	 * 			acCity--城市名称
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetCityInfoByCityName")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetCityInfoByCityName (
			String uId, String acCity){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetCityInfoByCityName(acCity);
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
	 * desc:(系统设置)根据acId添加或修改已开通城市相关数据接口
	 * param:uId--用户主键标识
	 * 			acId--行政区划主键标识
	 * 			isOpen--是否开通 0--否，1--是
	 * 			ocIsHot--是否热门城市 0--否，1--是
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingAddOrChangeOpenCityInfo")
	@ResponseBody
	public Map<String,Object> doMsSysSettingAddOrChangeOpenCityInfo (
			String uId, String acId, String isOpen, String ocIsHot){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingAddOrChangeOpenCityInfo(
					uId, acId, isOpen, ocIsHot);
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
	 * desc:(系统设置)获取某商品大类下商品小类列表接口
	 * param:uId--用户主键标识
	 * 			ptId--商品大类主键标识
	 * return:Map<String,Object>
	 * time:2018年5月7日
	 * author:L
	 */
	@RequestMapping(value="/msSysSettingGetPTDByPtId")
	@ResponseBody
	public Map<String,Object> doMsSysSettingGetPTDByPtId (
			String uId, String ptId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsSysSettingGetPTDByPtId(
					ptId);
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
	 * desc:(系统设置)获取商品大类列表
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductTypeList2")
	@ResponseBody
	public Map<String,Object> doMsGetProductTypeList2 (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetProductTypeList();
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
	 * desc:(系统设置)添加商品大类接口
	 * param:uId--用户主键标识
	 * 			ptId--商品大类标识
	 * 			ptName--商品大类名称
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msAddProductTypeInfo")
	@ResponseBody
	public Map<String,Object> doMsAddProductTypeInfo (String uId,
			String ptId, String ptName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsAddProductTypeInfo(uId,
					ptId, ptName);
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
	 * desc:(系统设置)修改商品小类接口
	 * param:uId--用户主键标识
	 * 			ptdId--商品小类标识
	 * 			ptdName--商品小类名称
	 * 			pTag--类别图片标识
	 * 			pName--类别图片名称
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msChangeProductTypeDetailInfo")
	@ResponseBody
	public Map<String,Object> doMsChangeProductTypeDetailInfo (String uId,
			String ptdId, String ptdName, String pTag, String pName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsChangeProductTypeDetailInfo(uId,
					ptdId, ptdName, pTag, pName);
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
	 * desc:(系统设置)添加商品小类接口
	 * param:uId--用户主键标识
	 * 			ptdId--商品小类标识
	 * 			ptId--商品大类标识
	 * 			ptdName--商品小类名称
	 * 			ptdFatherId--商品小类父类标识
	 * 			pName--类别图片名称
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msAddProductTypeDetailInfo")
	@ResponseBody
	public Map<String,Object> doMsAddProductTypeDetailInfo (String uId,
			String ptdId, String ptId, String ptdName, String ptdFatherId,
			String pName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsAddProductTypeDetailInfo(uId,
					ptdId, ptId, ptdName, ptdFatherId, pName);
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
	 * desc:(系统设置)根据条件查询商品属性列表接口
	 * param:uId--用户主键标识
	 * 			ppName--商品属性名称
	 * 			ppValue--商品属性值
	 * 			ppTag--商品属性二级标识
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductPropertyList")
	@ResponseBody
	public Map<String,Object> doMsGetProductPropertyList (String uId,
			String ppName, String ppValue, String ppTag, String start,
			String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetProductPropertyList(ppName,
					ppValue, ppTag, start, end);
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
	 * desc:(系统设置)添加商品属性接口
	 * param:uId--用户主键标识
	 * 			ppName--商品属性名称
	 * 			ppValues--商品属性值
	 * 			ppTag--商品属性二级标识
	 * 			ppChoseType--单选或多选
	 * 			ppRequired--是否必填
	 * return:Map<String,Object>
	 * time:2018年5月2日
	 * author:L
	 */
	@RequestMapping(value="/msAddProductProperty")
	@ResponseBody
	public Map<String,Object> doMsAddProductProperty (String uId,
			String ppName, String ppValues, String ppTag, String ppChoseType,
			String ppRequired){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsAddProductProperty(uId, 
					ppName, ppValues, ppTag, ppChoseType, ppRequired);
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
	 * desc: (系统设置)根据商品小类标识搜索商品小类信息接口
	 * param: uId--用户标识
	 * 			ptdId--商品小类标识
	 * return:Map<String,Object>
	 * time:2018年5月12日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductTypeRelationPropertiesByPtdId")
	@ResponseBody
	public Map<String,Object> doMsGetProductTypeRelationPropertiesByPtdId 
		(String uId, String ptdId) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetProductTypeRelationPropertiesByPtdId(ptdId);
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
	 * desc:(系统设置)根据条件查询商品属性列表接口
	 * param:uId--用户主键标识
	 * 			ppName--商品属性名称
	 * 			ppValue--商品属性值
	 * 			ppTag--商品属性二级标识
	 * 			start--开始的记录项
	 * 			end--结束的记录项
	 * return:Map<String,Object>
	 * time:2018年5月12日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductPropertyList2")
	@ResponseBody
	public Map<String,Object> doMsGetProductPropertyList2 (String uId,
			String ppName, String ppValue, String ppTag, String start,
			String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetProductPropertyList(ppName,
					ppValue, ppTag, start, end);
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
	 * desc: (系统设置)修改商品类别关联的属性接口
	 * param: uId--用户标识
	 * 			ptdId--商品类别标识
	 * 			ppTags--商品属性二级标识
	 * return:Map<String,Object>
	 * time:2018年5月12日
	 * author:L
	 */
	@RequestMapping(value="/msChangeProductTypeRelationProperties")
	@ResponseBody
	public Map<String,Object> doMsChangeProductTypeRelationProperties 
		(String uId, String ptdId, String ppTags) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangeProductTypeRelationProperties(uId, 
							ptdId, ppTags);
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
	 * desc: (系统设置)根据条件获取商户提现列表接口
	 * param: uId--用户标识
	 * 			sName--商户名称
	 * 			utcrStatus--提现状态
	 * 			start--开始的记录项
	 * 			end--页大小
	 * return:Map<String,Object>
	 * time:2018年5月12日
	 * author:L
	 */
	@RequestMapping(value="/msGetApplyToCashList")
	@ResponseBody
	public Map<String,Object> doMsGetApplyToCashList 
		(String uId, String sName, String utcrStatus, String start,
				String end) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetApplyToCashList( 
							sName, utcrStatus, start, end);
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
	 * desc: (系统设置)修改商户提现状态接口
	 * param: uId--用户标识
	 * 			utcrId--主键标识
	 * 			utcrStatus--提现状态
	 * return:Map<String,Object>
	 * time:2018年5月12日
	 * author:L
	 */
	@RequestMapping(value="/msChangeApplyToCashStatus")
	@ResponseBody
	public Map<String,Object> doMsChangeApplyToCashStatus 
		(String uId, String utcrId, String utcrStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangeApplyToCashStatus( 
							uId, utcrId, utcrStatus);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	// -----------------------个人商户身份相关接口----------------
	/**
	 * 
	 * desc:获取系统公告列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetSysNoticeList2")
	@ResponseBody
	public Map<String,Object> doMsGetSysNoticeList2 (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService.doMsGetSysNoticeList(uId);
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
	 * desc:获取个人商户首页数据接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年4月23日
	 * author:L
	 */
	@RequestMapping(value="/msGetPersonalIndexInfo")
	@ResponseBody
	public Map<String,Object> doMsGetPersonalIndexInfo (String uId,
			String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetPersonalIndexInfo(uId, sId);
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
	 * desc:获取需求订单状态列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetRequirementOrderStatusList")
	@ResponseBody
	public Map<String,Object> doMsGetRequirementOrderStatusList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetRequirementOrderStatusList();
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
	 * desc:获取需求类型列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetRequirementTypeList")
	@ResponseBody
	public Map<String,Object> doMsGetRequirementTypeList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetRequirementTypeList();
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
	 * desc:根据条件获取某商户的需求订单列表接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			roStatus--订单状态
	 * 			rtId--需求类型主键标识
	 * 			urTitle--需求标题
	 * 			urTrueName--需求发布人
	 * 			urTel--需求发布人电话
	 * 			start--开始的记录项
	 * 			end--页大小
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetRequirementOrderListWithCondition")
	@ResponseBody
	public Map<String,Object> doMsGetRequirementOrderListWithCondition (
			String uId, String sId, String roStatus, String rtId,
			String urTitle, String urTrueName, String urTel,
			String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetRequirementOrderListWithCondition(sId,
							roStatus, rtId, urTitle, urTrueName,
							urTel, start, end);
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
	 * desc:（账号管理）获取某商户的店铺信息接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetPersonalStoreInfoBySid")
	@ResponseBody
	public Map<String,Object> doMsGetPersonalStoreInfoBySid (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetPersonalStoreInfoBySid(sId);
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
	 * desc:（账号管理）修改某商户的店铺信息接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			sDescribe--商户描述
	 * 			sTel--商户联系电话
	 * 			sAddress--商户详细地址
	 * 			sLon--商户店铺经度
	 * 			sLat--商户店铺纬度
	 * 			picData--商户宣传图片 json格式字符串
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msChangePersonalStoreInfoBySid")
	@ResponseBody
	public Map<String,Object> doMsChangePersonalStoreInfoBySid (
			String uId, String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangePersonalStoreInfoBySid(sId, sDescribe, sTel,
							sAddress, sLon, sLat, picData);
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
	 * desc:（账号管理）是否设置过密保问题接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetStatusOfSafetyQuestions")
	@ResponseBody
	public Map<String,Object> doMsGetStatusOfSafetyQuestions (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetStatusOfSafetyQuestions(uId);
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
	 * desc:（账号管理）获取所有安全问题接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetSafetyQuestionsList")
	@ResponseBody
	public Map<String,Object> doMsGetSafetyQuestionsList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetSafetyQuestionsList();
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
	 * desc:（账号管理）设置安全问题接口
	 * param:uId--用户主键标识
	 * 			firstSqId--第一个安全问题的标识
	 * 			firstSqAnswer--第一个安全问题的答案
	 * 			secondSqId--第二个安全问题的标识
	 * 			secondSqAnswer--第二个安全问题的答案
	 * 			thirdSqId--第三个安全问题的标识
	 * 			thirdSqAnswer--第三个安全问题的答案
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msSetSafetyQuestionForUser")
	@ResponseBody
	public Map<String,Object> doMsSetSafetyQuestionForUser (
			String uId, String firstSqId, String firstSqAnswer, 
			String secondSqId, String secondSqAnswer,
			String thirdSqId, String thirdSqAnswer){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsSetSafetyQuestionForUser(uId, firstSqId, firstSqAnswer,
							secondSqId, secondSqAnswer, thirdSqId,
							thirdSqAnswer);
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
	 * desc:（账号管理）获取某用户已设置的安全问题接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetSafetyQuestionOfUser")
	@ResponseBody
	public Map<String,Object> doMsGetSafetyQuestionOfUser (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetSafetyQuestionOfUser(uId);
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
	 * desc:（账号管理）校验某用户已设置的安全问题接口
	 * param:uId--用户主键标识
	 * 			questionData--用户提交的安全问题内容 {"data": [{"sqAnswer": "xxx","sqPosition": 1}, {"sqAnswer": "xxx","sqPosition": 2}, {"sqAnswer": "xxx","sqPosition": 3}]}
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msCheckSafetyQuestionOfUser")
	@ResponseBody
	public Map<String,Object> doMsCheckSafetyQuestionOfUser (
			String uId, String questionData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsCheckSafetyQuestionOfUser(uId, questionData);
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
	 * desc:（账号管理）找回密码接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msRetrievePassword")
	@ResponseBody
	public Map<String,Object> doMsRetrievePassword (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsRetrievePassword(uId);
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
	 * desc:（账号管理）修改密码接口
	 * param:uId--用户主键标识
	 * 			oldPassword--旧密码
	 * 			newPassword--新密码
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msChangePassword")
	@ResponseBody
	public Map<String,Object> doMsChangePassword (
			String uId, String oldPassword, String newPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangePassword(uId, oldPassword, newPassword);
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
	 * desc:（账号管理）获取某商户的钱包相关数据接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetMyWallet")
	@ResponseBody
	public Map<String,Object> doMsGetMyWallet (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetMyWallet(uId, sId);
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
	 * desc:（账号管理）获取全部支付方式接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetPayStyleList")
	@ResponseBody
	public Map<String,Object> doMsGetPayStyleList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetPayStyleList(uId);
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
	 * desc:（账号管理）商户申请提现接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			psId--提现方式
	 * 			utcrMoney--提现金额
	 * 			utcrAccount--提现账号
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msApplyToCash")
	@ResponseBody
	public Map<String,Object> doMsApplyToCash (
			String uId, String sId, String psId, String utcrMoney, 
			String utcrAccount){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsApplyToCash(uId, sId, psId, utcrMoney, utcrAccount);
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
	 * desc:（账号管理）商户缴纳保障金接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			psId--缴纳方式
	 * 			udrMoney--缴纳金额
	 * 			payCode--支付订单号
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
//	@RequestMapping(value="/msAddDepositRecord")
//	@ResponseBody
//	public Map<String,Object> doMsAddDepositRecord (
//			String uId, String sId, String psId, String udrMoney,
//			String payCode){
//		Map<String,Object> map = new HashMap<String,Object>();
//		// 先对用户主键标识进行非空判断
//		if (Utils.isEmpty(uId) 
//				|| Utils.isEmpty(sId)) {
//			return Utils.commonErrorMap();
//		}
//		try {
//			boolean flag = true;
//			String errorString = "";
//			Object data = manageSystemService
//					.doMsAddDepositRecord(uId, sId, psId, udrMoney);
//			map = Utils.packageResponseDate(flag, errorString, data);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(),e);
//			return Utils.commonErrorMap();
//		}
//		return map;	
//	}
	
	/**
	 * 
	 * desc:（账号管理）商户申请解冻保障金接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			udrMoney--申请解冻的保障金金额
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msApplyToFreeForDepositRecord")
	@ResponseBody
	public Map<String,Object> doMsApplyToFreeForDepositRecord (
			String uId, String sId, String udrMoney){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsApplyToFreeForDepositRecord(uId, sId, udrMoney);
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
	 * desc:（账号管理）获取商户提现记录列表接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			utcrStatus--商户提现状态 0--申请提现，1--已转账
	 * 			psId--提现方式
	 * 			start--开始的记录项
	 * 			end--页大小
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetApplyToCashListBySId")
	@ResponseBody
	public Map<String,Object> doMsGetApplyToCashListBySId (
			String uId, String sId, String utcrStatus, String psId,
			String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetApplyToCashListBySId(uId, sId, utcrStatus, psId,
							start, end);
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
	 * desc:（账号管理）获取商户我的安排列表接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetMyArrangeListBySId")
	@ResponseBody
	public Map<String,Object> doMsGetMyArrangeListBySId (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetMyArrangeListBySId(uId, sId);
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
	 * desc:（账号管理）商户添加我的安排接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			saContent--安排内容
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msAddMyArrange")
	@ResponseBody
	public Map<String,Object> doMsAddMyArrange (
			String uId, String sId, String saContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId) 
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsAddMyArrange(uId, sId, saContent);
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
	 * desc:修改商户我的安排接口
	 * param:saId--商户安排主键标识
	 * 			saContent--安排内容
	 * return:Map<String,Object>
	 * time:2018年8月02日
	 * author:L
	 */
	@RequestMapping(value="/msUpdateMyArrange")
	@ResponseBody
	public Map<String,Object> doMSUpdateMyArrange (String saId, String saContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对商户安排主键标识进行非空判断
		if (Utils.isEmpty(saId) || Utils.isEmpty(saContent)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMSUpdateMyArrange(saId,saContent);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	// -----------------------企业商户身份相关接口----------------
	
	/**
	 * 
	 * desc：获取企业商户主界面相关数据接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetCompanyIndexInfo")
	@ResponseBody
	public Map<String,Object> doMsGetCompanyIndexInfo (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetCompanyIndexInfo(sId);
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
	 * desc：根据条件获取商品列表接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			pName--商品名称
	 * 			ptId--商品大类主键标识
	 * 			start--开始的记录项
	 * 			end--页大小
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductListBySId")
	@ResponseBody
	public Map<String,Object> doMsGetProductListBySId (
			String uId, String sId, String pName, String ptId,
			String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetProductListBySId(sId, pName, ptId, start, end);
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
	 * desc：获取某商户商品推荐橱窗接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetBoothBySId")
	@ResponseBody
	public Map<String,Object> doMsGetBoothBySId (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.msGetBoothBySId(sId);
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
	 * desc：获取某商品全部属性列表接口
	 * param:uId--用户主键标识
	 * 			pId--商品主键标识
	 * 			ptdId--商品小类主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetAllProductPropertyListOfProduct")
	@ResponseBody
	public Map<String,Object> doMsGetAllProductPropertyListOfProduct (
			String uId, String pId, String ptdId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetAllProductPropertyListOfProduct(pId, ptdId);
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
	 * desc：修改商品信息接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			pId--商品主键标识
	 * 			pName--商品名称
	 * 			pBrand--商品品牌
	 * 			pDescribe--商品描述
	 * 			picData--商品图片 json格式字符串  商品图片如无修改，则picData为空字符串
	 * 				例：{"pTag": "16869e8a-46ac-11e8-8390-4ccc6a70ac67","pics": [{"pName": "xx.jpg","pNo": 0},{"pName": "xx.png","pNo": 1},{"pName": "xx.jpg","pNo": 2}]}
	 * 			pOriginalPrice--商品原价
	 * 			pNowPrice--商品现价
	 * 			pWeight--商品权重
	 * 			pHtml--商品图文详情
	 * 			pHaveBooth--是否推荐商品
	 * 			pStockNum--商品库存数量
	 * 			ppIds--选择的商品属性主键标识列表，以;隔开  商品属性如无修改，则ppIds为空字符串
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msChangeProductInfo")
	@ResponseBody
	public Map<String,Object> doMsChangeProductInfo (
			String uId, String sId, String pId, String pName, String pBrand, String pDescribe,
			String picData, String pOriginalPrice, String pNowPrice, 
			String pWeight, String pHtml, String pHaveBooth, String pStockNum,
			String ppIds){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangeProductInfo(sId, pId, pName, pBrand, pDescribe,
							picData, pOriginalPrice, pNowPrice, pWeight,
							pHtml, pHaveBooth, pStockNum, ppIds);
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
	 * desc：企业商户发布商品接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			pName--商品名称
	 * 			pBrand--商品品牌
	 * 			pDescribe--商品描述
	 * 			picData--商品图片 json格式字符串  
	 * 				例：{"pTag": "","pics": [{"pName": "xx.jpg","pNo": 0},{"pName": "xx.png","pNo": 1},{"pName": "xx.jpg","pNo": 2}]}
	 * 			pOriginalPrice--商品原价
	 * 			pNowPrice--商品现价
	 * 			ptdId--商品所属小类主键标识
	 * 			pWeight--商品权重
	 * 			pHtml--商品图文详情
	 * 			pHaveBooth--是否推荐商品
	 * 			pStockNum--商品库存数量
	 * 			ppIds--选择的商品属性主键标识列表，以;隔开  
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msAddProductInfo")
	@ResponseBody
	public Map<String,Object> doMsAddProductInfo (
			String uId, String sId, String pName, String pBrand, String pDescribe,
			String picData, String pOriginalPrice, String pNowPrice, String ptdId,
			String pWeight, String pHtml, String pHaveBooth, String pStockNum,
			String ppIds){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsAddProductInfo(uId, sId, pName, pBrand, pDescribe,
							picData, pOriginalPrice, pNowPrice, ptdId, pWeight,
							pHtml, pHaveBooth, pStockNum, ppIds);
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
	 * desc：企业商户删除某商品接口
	 * param:uId--用户主键标识
	 * 			pId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msDelProductInfoByPId")
	@ResponseBody
	public Map<String,Object> doMsDelProductInfoByPId (
			String uId, String pId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsDelProductInfoByPId(pId);
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
	 * desc：获取商品订单状态列表接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductOrderStatusList")
	@ResponseBody
	public Map<String,Object> doMsGetProductOrderStatusList (
			String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetProductOrderStatusList();
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
	 * desc：获取某商户的商品订单列表接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			poOrderId--商品订单号
	 * 			poStatus--订单状态
	 * 			poDeliverName--收货人
	 * 			poDeliverTel--收货人电话
	 * 			start--当前的记录项
	 * 			end--页大小
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetProductOrderListBySId")
	@ResponseBody
	public Map<String,Object> doMsGetProductOrderListBySId (
			String uId, String sId, String poOrderId, String poStatus,
			String poDeliverName, String poDeliverTel, String start,
			String end){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)
				|| Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetProductOrderListBySId(sId, poOrderId, poStatus, 
							poDeliverName, poDeliverTel, start, end);
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
	 * desc：修改商品订单状态接口
	 * param:uId--用户主键标识
	 * 			poId--商品订单主键标识
	 * 			poStatus--要修改的订单状态 
	 * 			poDeliverCompany--发货的快递公司 商户发货时需传该字段  否则传空字符串
	 * 			poDeliverCode--发货的快递单号 商户发货时需传该字段  否则传空字符串
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msChangeProductOrderStatus")
	@ResponseBody
	public Map<String,Object> doMsChangeProductOrderStatus (
			String uId, String poId, String poStatus, String poDeliverCompany,
			String poDeliverCode){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)
				|| Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangeProductOrderStatus(poId, poStatus, poDeliverCompany, 
							poDeliverCode);
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
	 * desc:（账号管理）获取某商户的店铺信息接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msGetCompanyStoreInfoBySid")
	@ResponseBody
	public Map<String,Object> doMsGetCompanyStoreInfoBySid (
			String uId, String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsGetCompanyStoreInfoBySid(sId);
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
	 * desc:（账号管理）修改某商户的店铺信息接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			sDescribe--商户描述
	 * 			sTel--商户联系电话
	 * 			sAddress--商户详细地址
	 * 			sLon--商户店铺经度
	 * 			sLat--商户店铺纬度
	 * 			picData--商户宣传图片 json格式字符串
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msChangeCompanyStoreInfoBySid")
	@ResponseBody
	public Map<String,Object> doMsChangeCompanyStoreInfoBySid (
			String uId, String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsChangeCompanyStoreInfoBySid(sId, sDescribe, sTel,
							sAddress, sLon, sLat, picData);
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
	 * desc: 商户重新提交审核资料接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			sDescribe--商户描述
	 * 			sTel--商户联系电话
	 * 			sAddress--商户详细地址
	 * 			sLon--商户店铺经度
	 * 			sLat--商户店铺纬度
	 * 			picData--商户宣传图片 json格式字符串
	 * 			sLeader--商户负责人姓名
	 * 			sLeaderIdCard--商户负责人身份证号
	 * 			sLeaderPicName--商户负责人手持证件照图片名称
	 * 			sLegal--商户法人姓名
	 * 			sLegalIdCard--商户法人身份证号
	 * 			sLegalPicName--商户法人手持证件照图片名称
	 * 			sBusinessLicensePicName--营业执照图片名称
	 * return:Map<String,Object>
	 * time:2018年5月15日
	 * author:L
	 */
	@RequestMapping(value="/msReSubmitStoreAuditInfo")
	@ResponseBody
	public Map<String,Object> doMsReSubmitStoreAuditInfo (
			String uId, String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData,
			String sLeader, String sLeaderIdCard, String sLeaderPicName,
			String sLegal, String sLegalIdCard, String sLegalPicName,
			String sBusinessLicensePicName){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对用户主键标识进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = manageSystemService
					.doMsReSubmitStoreAuditInfo(sId, sDescribe, sTel,
							sAddress, sLon, sLat, picData,
							sLeader, sLeaderIdCard, sLeaderPicName,
							sLegal, sLegalIdCard, sLegalPicName,
							sBusinessLicensePicName);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
}
