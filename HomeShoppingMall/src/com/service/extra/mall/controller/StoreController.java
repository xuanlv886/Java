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
public class StoreController {
	@Resource private ProductService productService;
	@Resource private StoreService storeService;
	@Resource private UserService userService;
	//获取日志记录器，这个记录器将负责控制日志信息
		Logger logger = Logger.getLogger(ManageSystemController.class);
		
	/**
	 * 
  	 * desc:商户登录接口
	 * param:uAccount--账号
	 * 		uAccount--密码,
	 * 		uPhoneId--用户账号绑定设备标识
	 * 		acId--用户当前所在城市的标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/storeLogin")
	@ResponseBody
	public Map<String,Object> doStoreLogin (String uAccount,
			String uPassword,String uPhoneId, String acId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对账号和密码进行非空判断
		if (Utils.isEmpty(uAccount) 
				|| Utils.isEmpty(uPassword)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doStoreLogin(uAccount, 
					uPassword,uPhoneId, acId);
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
  	 * desc:获取店铺个人资料接口
	 * param:sId店铺主键标识,uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getStoreInfo")
	@ResponseBody
	public Map<String,Object> doGetStoreInfo (String sId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(uId)){
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreInfo(sId,uId);
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
  	 * desc:店铺轮播图接口
	 * param:sId店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getStoreSlideShow")
	@ResponseBody
	public Map<String,Object> doGetStoreSlideShow (String sId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreSlideShow(sId);
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
  	 * desc:修改商品接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/updateProductInfo")
	@ResponseBody
	public Map<String,Object> doUpdateProductInfo (String pId,String pName,String pDesc,String pOriginalPrice,String pNowPrice,String pStockNum,String pProperty){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(pId)||Utils.isEmpty(pName)||Utils.isEmpty(pDesc)||Utils.isEmpty(pOriginalPrice) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doUpdateProductInfo(pId,pName,pDesc,pOriginalPrice,pNowPrice,pStockNum,pProperty);
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
  	 * desc:删除店铺商品接口
	 * param:pId:商品主键标识
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/deleteStoreProduct")
	@ResponseBody
	public Map<String,Object> doDeleteStoreProduct (String pId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对pId进行非空判断
		if (Utils.isEmpty(pId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doDeleteStoreProduct(pId);
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
  	 * desc:商户意见反馈接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/addStoreFeedBack")
	@ResponseBody
	public Map<String,Object> doAddStoreFeedBack (String fType,String fContent,String uId,String fAppType){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(fType)||Utils.isEmpty(fContent)||Utils.isEmpty(uId)||Utils.isEmpty(fAppType) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doAddStoreFeedBack(fType,fContent,uId,fAppType);
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
  	 * desc:申请提现接口
	 * param:uId--用户主键标识
	 * 			sId--商户主键标识
	 * 			psId--提现方式
	 * 			utcrMoney--提现金额
	 * 			utcrAccount--提现账号
	 * return:Map<String,Object>
	 * time:2018年8月03日
	 * author:L
	 */
	@RequestMapping(value="/getAlreadyCash")
	@ResponseBody
	public Map<String,Object> doGetAlreadyCash (String sId,String psId,String uId,String utcrAccount,String utcrMoney){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(psId)||Utils.isEmpty(uId)
				||Utils.isEmpty(utcrAccount)||Utils.isEmpty(utcrMoney)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetAlreadyCash(sId,psId,uId,utcrAccount,utcrMoney);
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
  	 * desc:商户我的钱包接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/getMyUserWallet")
	@ResponseBody
	public Map<String,Object> doGetMyUserWallet (String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetMyUserWallet(sId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
//	/**
//	 * 
//  	 * desc:用户缴纳保障金接口
//	 * param:sId商户主键标识,uId用户主键标识,udrMoney缴纳保障金金额
//	 * return:Map<String,Object>
//	 * time:2018年8月04日
//	 * author:L
//	 */
//	@RequestMapping(value="/getPayDepositMoney")
//	@ResponseBody
//	public Map<String,Object> doGetPayDepositMoney (String sId,String uId,String udrMoney,String psId){
//		Map<String,Object> map = new HashMap<String,Object>();
//		// 先对sId,uId进行非空判断
//		if (Utils.isEmpty(sId)||Utils.isEmpty(uId)||Utils.isEmpty(udrMoney)||Utils.isEmpty(psId) 
//				) {
//			return Utils.commonErrorMap();
//		}
//		try {
//			boolean flag = true;
//			String errorString = "";
//			Object data = storeService.doGetPayDepositMoney(sId,uId,udrMoney,psId);
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
  	 * desc:解冻保障金接口
	 * param:sId:商户主键标识,depositMoney:解冻金额,uId:用户主键标识
	 * return:Map<String,Object>
	 * time:2018年8月04日
	 * author:L
	 */
	@RequestMapping(value="/getThawDepositMoney")
	@ResponseBody
	public Map<String,Object> doGetThawDepositMoney (String sId,String depositMoney,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(depositMoney)||Utils.isEmpty(uId) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetThawDepositMoney(sId,depositMoney,uId);
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
  	 * desc:个人身份商户注册接口
	 * param:sTel--用户账号
	 * 			sName--商户名称
	 * 			acId--商户所在城市标识
	 * 			sAddress--商户详细地址
	 * 			sLon--地址所在经度
	 * 			sLat--地址所在纬度
	 * 			sLeader--商户负责人姓名
	 * 			uSex--用户性别 0--女，1--男
	 * 			sLeaderIdCard--商户负责人身份证号
	 * 			sLeaderPic--商户负责人手持证件照片名称
	 * 			sPassword--用户密码
	 * return:Map<String,Object>
	 * time:2018年5月23日
	 * author:J
	 */
	@RequestMapping(value="/storePersonRegistered")
	@ResponseBody
	public Map<String,Object> doStorePersonRegistered (String sTel,
			String sName,String acId, String sAddress,
			String sLon, String sLat, String sLeader, String uSex,
			String sLeaderIdCard,String sLeaderPic,String sPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId进行非空判断
		if (Utils.isEmpty(sTel)||Utils.isEmpty(sLeader)
				||Utils.isEmpty(sName)||Utils.isEmpty(acId)
				||Utils.isEmpty(sPassword) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doStorePersonRegistered(sTel,sName,acId,
					sAddress,sLon, sLat, sLeader,uSex, sLeaderIdCard, sLeaderPic, 
					sPassword);
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
  	 * desc:商户企业注册
	 * param:sTel--用户账号
	 * 			sName--商户名称
	 * 			acId--商户所在城市标识
	 * 			sAddress--商户详细地址
	 * 			sLon--地址所在经度
	 * 			sLat--地址所在纬度
	 * 			sLeader--商户负责人姓名
	 * 			uSex--用户性别 0--女，1--男
	 * 			sLeaderIdCard--商户负责人身份证号
	 * 			sLeaderPic--商户负责人手持证件照片名称
	 * 			sLegal--商户法人姓名
	 * 			sLegalIdCard--商户法人身份证号
	 * 			sLegalPic--商户法人手持证件照片名称
	 * 			sBussinessLicensePic--营业执照照片名称
	 * 			sPassword--用户密码
	 * return:Map<String,Object>
	 * time:2018年5月23日
	 * author:J
	 */
	@RequestMapping(value="/storeCompanyRegistered")
	@ResponseBody
	public Map<String,Object> doStoreCompanyRegistered (String sTel,
			String sName,String acId, String sAddress,
			String sLon, String sLat, String sLeader, String uSex,
			String sLeaderIdCard,String sLeaderPic, 
			String sLegal, String sLegalIdCard, String sLegalPic,
			String sBussinessLicensePic, String sPassword){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId进行非空判断
		if (Utils.isEmpty(sTel)||Utils.isEmpty(sLeader)
				||Utils.isEmpty(sName)||Utils.isEmpty(acId)
				||Utils.isEmpty(sPassword) 
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doStoreCompanyRegistered(sTel,sName,
					acId,sAddress,sLon, sLat, sLeader, uSex, sLeaderIdCard,
					sLeaderPic, sLegal, sLegalIdCard, sLegalPic, 
					sBussinessLicensePic, sPassword);
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
  	 * desc:添加商户安排详情
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月23日
	 * author:J
	 */
	@RequestMapping(value="/addStoreArrange")
	@ResponseBody
	public Map<String,Object> doAddStoreArrange (String sId,String saContent,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,saContent,uId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(saContent)||Utils.isEmpty(uId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doAddStoreArrange(sId,saContent,uId);
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
  	 * desc:商户申请接单
	 * param:sId:店铺主键标识,quotedPrice:报价,urId:用户需求主键标识
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/getStoreApplicationRequirement")
	@ResponseBody
	public Map<String,Object> doGetStoreApplicationRequirement (String sId,String quotedPrice,String urId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,quotedPrice,urId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(quotedPrice)||Utils.isEmpty(urId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreApplicationRequirement(sId,quotedPrice,urId);
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
  	 * desc:删除商户安排
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/delStoreArrange")
	@ResponseBody
	public Map<String,Object> doDelStoreArrange (String saId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对saId进行非空判断
		if (Utils.isEmpty(saId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doDelStoreArrange(saId);
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
  	 * desc:获取我的店铺安排
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/getMyArranges")
	@ResponseBody
	public Map<String,Object> doGetMyArranges (String sId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(uId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetMyArranges(sId,uId);
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
	@RequestMapping(value="/updateMyArrange")
	@ResponseBody
	public Map<String,Object> doUpdateMyArrange (String saId, String saContent){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对商户安排主键标识进行非空判断
		if (Utils.isEmpty(saId) || Utils.isEmpty(saContent)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService
					.doUpdateMyArrange(saId,saContent);
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
  	 * desc:修改商户信息接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/updateStoreInfo")
	@ResponseBody
	public Map<String,Object> doUpdateStoreInfo (String uId,String sId,String sName,String sAnnouncement,String sTel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)||Utils.isEmpty(sName)||Utils.isEmpty(sAnnouncement)||Utils.isEmpty(sTel)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doUpdateStoreInfo(uId,sId,sName,sAnnouncement,sTel);
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
  	 * desc:需求中心接口（分页获取满足条件的需求列表接口）
	 * param: uId--用户主键标识
	 * 			sId--商户店铺主键标识
	 * 			hotSort--热门排序
	 * 			timeSort--发布时间排序
	 * 			priceSort--价格排序
	 * 			currentPage--当前页
	 * 			size--页大小
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/getAllRequirement")
	@ResponseBody
	public Map<String,Object> doGetAllRequirement (String uId,String sId,
			String hotSort,String timeSort,String priceSort,String currentPage,
			String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)||Utils.isEmpty(currentPage)||Utils.isEmpty(size)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetAllRequirement(uId,sId,hotSort,
					timeSort,priceSort,currentPage,size);
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
  	 * desc:商户取消需求订单接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/cancelStoreRequirement")
	@ResponseBody
	public Map<String,Object> doCancelStoreRequirement (String sId,String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,roId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(roId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doCancelStoreRequirement(sId,roId);
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
  	 * desc:获取所有提现记录接口
	 * param:sId:店铺主键标识,uId:用户主键标识,size:每页记录数,pages:页数
	 * return:Map<String,Object>
	 * time:2018年8月03日
	 * author:L
	 */
	@RequestMapping(value="/getAllAlreadyCashRecord")
	@ResponseBody
	public Map<String,Object> doGetAllAlreadyCashRecord (String sId,String uId, String size, String pages){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(uId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetAllAlreadyCashRecord(sId,uId,size,pages);
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
  	 * desc:商户订单详情接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/getStoreProductOrderDetail")
	@ResponseBody
	public Map<String,Object> doGetStoreProductOrderDetail (String poId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对poId进行非空判断
		if (Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreProductOrderDetail(poId);
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
  	 * desc:商户商品订单管理接口
	 * param:sId:店铺主键标识,currentPage:页数,size:每页条数,status:订单状态
	 * return:Map<String,Object>
	 * time:2018年8月7日
	 * author:L
	 */
	@RequestMapping(value="/getStoreProductOrder")
	@ResponseBody
	public Map<String,Object> doGetStoreProductOrder (String sId,String currentPage,String size,String status){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId进行非空判断
		if (Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreProductOrder(sId,currentPage,size,status);
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
  	 * desc:商户需求订单管理接口
	 * param:sId:店铺主键标识,currentPage:页数,size:每页条数,roStatus:订单状态
	 * return:Map<String,Object>
	 * time:2018年8月7日
	 * author:J
	 */
	@RequestMapping(value="/getStoreRequirement")
	@ResponseBody
	public Map<String,Object> doGetStoreRequirement (String sId,String roStatus,String currentPage,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对sId,uId进行非空判断
		if (Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreRequirement(sId,roStatus,currentPage,size);
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
  	 * desc:商户需求详情接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/getStoreRequirementDetail")
	@ResponseBody
	public Map<String,Object> doGetStoreRequirementDetail (String urId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对urId进行非空判断
		if (Utils.isEmpty(urId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doGetStoreRequirementDetail(urId);
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
  	 * desc:获取个人商户主界面相关数据接口
	 * param:uId--用户主键标识
	 * 		sId--商户店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:J
	 */
	@RequestMapping(value="/personStoreMainInterface")
	@ResponseBody
	public Map<String,Object> doPersonStoreMainInterface (String uId,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doPersonStoreMainInterface(uId,sId);
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
  	 * desc:获取企业商户主界面相关数据接口
	 * param:uId用户主键标识,sId店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:J
	 */
	@RequestMapping(value="/companyStoreMainInterface")
	@ResponseBody
	public Map<String,Object> doCompanyStoreMainInterface (String uId,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)
				) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doCompanyStoreMainInterface(uId,sId);
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
  	 * desc:提交商户所在位置经纬度接口（一分钟提交一次）
  	 * 			当需求订单处于已完成或已评价时，可删除对应的移动轨迹点
	 * param:uId--用户主键标识
	 * 			sId--店铺主键标识
	 * 			mtLon--经度
	 * 			mtLat--纬度
	 * return:Map<String,Object>
	 * time:2018年5月25日
	 * author:L
	 */
	@RequestMapping(value="/submitMerchantLonlat")
	@ResponseBody
	public Map<String,Object> doSubmitMerchantLonlat (String uId,String sId,
			String mtLon, String mtLat){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doSubmitMerchantLonlat(uId, sId, mtLon, mtLat);
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
  	 * desc:商户确认商品退款
	 * param:poId--商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年8月02日
	 * author:L
	 */
	@RequestMapping(value="/affirmProductRefund")
	@ResponseBody
	public Map<String,Object> doAffirmProductRefund (String poId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对poId进行非空判断
		if (Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doAffirmProductRefund(poId);
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
  	 * desc:商户通知用户验货
	 * param:roId--需求订单主键标识
	 * 			picData--待验货的商品图片 json格式字符串 
	 * 				例:{"pTag": "传空字符串即可","pics": [{"pName": "图片名称","pNo": 0}, {"pName": "图片名称","pNo": 1
	 *					}, {"pName": "图片名称","pNo": 2}]}
	 * return:Map<String,Object>
	 * time:2018年8月02日
	 * author:L
	 */
	@RequestMapping(value="/informInspection")
	@ResponseBody
	public Map<String,Object> doInformInspection (String roId, String picData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对roId进行非空判断
		if (Utils.isEmpty(roId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = storeService.doInformInspection(roId, picData);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc:商户发货接口
	 * param:poId--商品订单主键标识,poDeliverCompany:发货的快递公司,poDeliverCode:发货的快递单号
	 * return:Map<String,Object>
	 * time:2018年8月04日
	 * author:L
	 */
	@RequestMapping(value="/merchantSend")
	@ResponseBody
	public Map<String,Object>  merchantSend (String poId,String poDeliverCompany,String poDeliverCode){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对poId进行非空判断
		if (Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data  = storeService.merchantSend (poId,poDeliverCompany,poDeliverCode);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
	}
	
	/**
	 * desc:商户删除订单接口
	 * param:poId--商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年8月04日
	 * author:L
	 */
	@RequestMapping(value="/delStoreProductOrder")
	@ResponseBody
	public Map<String,Object>  doDelStoreProductOrder (String poId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对poId进行非空判断
		if (Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}			
		try {
			boolean flag = true;
			String errorString = "";
			Object data  = storeService.doDelStoreProductOrder (poId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
	}
	
}
