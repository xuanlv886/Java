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
public class ProductController {
	@Resource private ProductService productService;
	@Resource private StoreService storeService;
	@Resource private UserService userService;
	//获取日志记录器，这个记录器将负责控制日志信息
		Logger logger = Logger.getLogger(ManageSystemController.class);
		
	/**
	 * 
  	 * desc:获取商品详情接口
	 * param:pId商品主键标识,uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getProductInfo")
	@ResponseBody
	public Map<String,Object> doGetProductInfo (String pId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(pId))  
				{
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductInfo(pId,uId);
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
  	 * desc:获取商品大类接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getProductType")
	@ResponseBody
	public Map<String,Object> doGetProductType (){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductType();
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
  	 * desc:获取所有商品信息接口
	 * param:uId用户主键标识,currentPage当前页,size每页记录数,ptdId商品小类主键标识,priceSort价格排序,
	 * saleSort销售排序,parameterData参数json条件
	 * return:Map<String,Object>
	 * time:2018年5月18日
	 * author:J
	 */
	@RequestMapping(value="/getProductList")
	@ResponseBody
	public Map<String,Object> doGetProductList (String uId,String currentPage,String size,String ptdId,String priceSort,String saleSort,String parameterData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(currentPage)||Utils.isEmpty(size)||Utils.isEmpty(ptdId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductList(uId,currentPage,size,ptdId,priceSort,saleSort,parameterData);
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
  	 * desc:修改我的需求接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月19日
	 * author:J
	 */
	@RequestMapping(value="/updateUserRequirement")
	@ResponseBody
	public Map<String,Object> doUpdateUserRequirement (String urId,String uId,String rtId,String urTitle,String urContent,String urOfferType,String urOfferPrice,String urTrueName,String urTel,String urAddress){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(urId)||Utils.isEmpty(uId)||Utils.isEmpty(rtId)||Utils.isEmpty(urTitle)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doUpdateUserRequirement(urId,uId,rtId,urTitle,urContent,urOfferType,urOfferPrice,urTrueName,urTel,urAddress);
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
  	 * desc:发布我的需求接口
	 * param:uId用户主键标识,rtId需求类型主键标识,urTitle需求标题,urContent需求内容,urOfferType需求报价类型,
	 * urOfferPrice需求报价金额,urTrueName需求人姓名,urTel需求人电话,urAddress需求人地址,sId指定店铺主键标识
	 * urGetAddress需求取货地址(需求类型为取货送货时需填写)
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/addUserRequirement")
	@ResponseBody
	public Map<String,Object> doAddUserRequirement (String uId,String rtId,
			String urTitle,String urContent,String urOfferType,
			String urOfferPrice,String urTrueName,String urTel,
			String urAddress,String sId, String urGetAddress){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(rtId)||Utils.isEmpty(urTitle)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddUserRequirement(uId,rtId,urTitle,
					urContent,urOfferType,urOfferPrice,urTrueName,
					urTel,urAddress,sId, urGetAddress);
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
  	 * desc:获取我的需求接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getUserRequirement")
	@ResponseBody
	public Map<String,Object> doGetUserRequirement (String uId,String status,String currentPage,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetUserRequirement(uId,status,currentPage,size);
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
  	 * desc:评价需求接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/addRequirementEvaluate")
	@ResponseBody
	public Map<String,Object> doAddRequirementEvaluate (String uId,String roId,String roeContent,String roeLevel,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(roId)||Utils.isEmpty(roeContent)||Utils.isEmpty(roeLevel)||Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddRequirementEvaluate(uId,roId,roeContent,roeLevel,sId);
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
  	 * desc:获取需求详情接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getUserRequirementDetail")
	@ResponseBody
	public Map<String,Object> doGetUserRequirementDetail (String uId,String urId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(urId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetUserRequirementDetail(uId,urId);
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
  	 * desc:商品订单评价接口
	 * param:uId用户主键标识,
	 * 		 poId商品订单主键标识,
	 * 		 evaluateData:{
	 * 				pId商品主键标识,
	 * 				podId商品订单详情主键标识,
	 * 				peLevel评价等级,
	 * 				peContent评价内容,
	 * 				picList商品评价图片列表:[{
	 * 						picName评价图片名
	 * 						pNo图片等级
	 * 					},{
	 * 						picName评价图片名
	 * 						pNo图片等级
	 * 					}]
	 * 				},{
	 * 				pId商品主键标识,
	 * 				podId商品订单详情主键标识,
	 * 				peLevel评价等级,
	 * 				peContent评价内容,
	 * 				picList商品评价图片列表:[{
	 * 						picName评价图片名
	 * 						pNo图片等级
	 * 					},{
	 * 						picName评价图片名
	 * 						pNo图片等级
	 * 					}]
	 * 				}
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/addProductEvaluate")
	@ResponseBody
	public Map<String,Object> doAddProductEvaluate (String uId,String poId,String evaluateData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(poId)||Utils.isEmpty(evaluateData)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddProductEvaluate(uId,poId,evaluateData);
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
  	 * desc:获取我的商品订单评价接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getUserProductEvaluate")
	@ResponseBody
	public Map<String,Object> doGetUserProductEvaluate (String uId,String peLevel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和urId进行非空判断
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetUserProductEvaluate(uId,peLevel);
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
  	 * desc:获取商品订单详情接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getProductOrderDetail")
	@ResponseBody
	public Map<String,Object> doGetProductOrderDetail (String uId,String poId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductOrderDetail(uId,poId);
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
  	 * desc:立即购买生成订单接口
	 * param:uId用户主键标识,udaId用户收货地址主键标识,pNum商品数量,pId商品主键标识,PProperty商品属性
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/addProductOrder")
	@ResponseBody
	public Map<String,Object> doAddProductOrder (String uId,String udaId,String pNum,String pId,String pProperty){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId和urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(udaId)||Utils.isEmpty(pNum)||Utils.isEmpty(pId)||Utils.isEmpty(pProperty)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddProductOrder(uId,udaId,pNum,pId,pProperty);
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
  	 * desc:获取商品筛选接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getProductProperty")
	@ResponseBody
	public Map<String,Object> doGetProductProperty (String ptdId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对ptdId进行非空判断
		if (Utils.isEmpty(ptdId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductProperty(ptdId);
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
  	 * desc:根据商品一级类别获取所有小类信息
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getProductTypeDetailByPtId")
	@ResponseBody
	public Map<String,Object> doGetProductTypeDetailByPtId (String ptId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对ptId进行非空判断
		if (Utils.isEmpty(ptId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductTypeDetailByPtId(ptId);
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
  	 * desc:删除商品订单接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/delProductOrder")
	@ResponseBody
	public Map<String,Object> doDelProductOrder (String uId,String poId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,poId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(poId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doDelProductOrder(uId,poId);
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
  	 * desc:删除商品评价接口
	 * param:
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/delProductEvaluate")
	@ResponseBody
	public Map<String,Object> doDelProductEvaluate (String peId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对peId进行非空判断
		if (Utils.isEmpty(peId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doDelProductEvaluate(peId);
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
  	 * desc:获取用户所有订单接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月21日
	 * author:J
	 */
	@RequestMapping(value="/getProductOrder")
	@ResponseBody
	public Map<String,Object> doGetProductOrder (String uId,String status,String currentPage,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(currentPage)||Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductOrder(uId,status,currentPage,size);
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
  	 * desc:获取推荐商品接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/getRecommendProductList")
	@ResponseBody
	public Map<String,Object> doGetRecommendProductList (String uId,String currentPage,String size,String ptdId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId进行非空判断
		if (Utils.isEmpty(currentPage)||Utils.isEmpty(size)||Utils.isEmpty(ptdId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetRecommendProductList(uId,currentPage,size,ptdId);
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
  	 * desc:从购物车生成订单接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/addProductOrderByTrolley")
	@ResponseBody
	public Map<String,Object> doAddProductOrderByTrolley (String uId,String udaId,String parameterData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,rdaId和parameterData进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(udaId)||Utils.isEmpty(parameterData)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddProductOrderByTrolley(uId,udaId,parameterData);
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
  	 * desc:删除我的需求接口
	 * param:uId用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月22日
	 * author:J
	 */
	@RequestMapping(value="/delUserRequirement")
	@ResponseBody
	public Map<String,Object> doDelUserRequirement (String urId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(urId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doDelUserRequirement(uId,urId);
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
  	 * desc:用户确认需求订单接口
	 * param:uId用户主键标识,sId商铺主键标识,roId用户需求订单主键标识
	 * return:Map<String,Object>
	 * time:2018年5月24日
	 * author:J
	 */
	@RequestMapping(value="/addUserProductRequirement")
	@ResponseBody
	public Map<String,Object> doAddUserProductRequirement (String uId,String sId,String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,sId,urId进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(roId)||Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doAddUserProductRequirement(uId,roId,sId);
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
  	 * desc:获取我的需求评价接口
	 * param:uId用户主键标识,roeLevel需求评价等级
	 * return:Map<String,Object>
	 * time:2018年5月29日
	 * author:J
	 */
	@RequestMapping(value="/getUserRequirementEvaluate")
	@ResponseBody
	public Map<String,Object> doGetUserRequirementEvaluate (String uId,String roeLevel){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对uId,roeLevel进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(roeLevel)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetUserRequirementEvaluate(uId,roeLevel);
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
  	 * desc:获取商品评价接口
	 * param:pId商品主键标识,peLevel商品评价等级,size每页数量,currentPage页数
	 * return:Map<String,Object>
	 * time:2018年5月29日
	 * author:J
	 */
	@RequestMapping(value="/getProductEvaluate")
	@ResponseBody
	public Map<String,Object> doGetProductEvaluate (String pId,String peLevel,String size,String currentPage ){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对pId,peLevel进行非空判断
		if (Utils.isEmpty(pId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductEvaluate(pId,peLevel,size,currentPage);
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
  	 * desc:获取商品根据关键字接口
	 * param:looseName关键字,ptdId商品类型,priceSort价格排序,saleSort销量排序,currentPage当前页,size每页记录数
	 * return:Map<String,Object>
	 * time:2018年5月30日
	 * author:J
	 */
	@RequestMapping(value="/selectBylooseName")
	@ResponseBody
	public Map<String,Object> doSelectBylooseName (String looseName,String ptdId,String priceSort,String saleSort,String currentPage,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对looseName,ptdId,currentPage,size进行非空判断
		if (Utils.isEmpty(looseName)||Utils.isEmpty(currentPage)||Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doSelectBylooseName(looseName,ptdId,priceSort,saleSort,currentPage,size);
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
  	 * desc:获取热搜
	 * param:type热搜类型
	 * return:Map<String,Object>
	 * time:2018年5月30日
	 * author:J
	 */
	@RequestMapping(value="/getHotSearch")
	@ResponseBody
	public Map<String,Object> doGetHotSearch (String type){
		Map<String,Object> map = new HashMap<String,Object>();
		//对type进行非空判断
		if (Utils.isEmpty(type)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetHotSearch(type);
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
  	 * desc:获取店铺详情接口
	 * param:sId店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年5月30日
	 * author:J
	 */
	@RequestMapping(value="/getStoreDetail")
	@ResponseBody
	public Map<String,Object> doGetStoreDetail (String sId,String currentPage,String size,String ptdId,String saleSort,String priceSort,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对sId进行非空判断
		if (Utils.isEmpty(sId)||Utils.isEmpty(currentPage)||Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetStoreDetail(sId,currentPage,size,ptdId,saleSort,priceSort,uId);
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
  	 * desc:获取店铺根据关键字接口
	 * param:looseName关键字,currentPage当前页,size每页记录数
	 * return:Map<String,Object>
	 * time:2018年5月31日
	 * author:J
	 */
	@RequestMapping(value="/selectStoreBylooseName")
	@ResponseBody
	public Map<String,Object> doSelectStoreBylooseName (String looseName,String currentPage,String size){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对looseName,currentPage,size进行非空判断
		if (Utils.isEmpty(looseName)||Utils.isEmpty(currentPage)||Utils.isEmpty(size)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doSelectStoreBylooseName(looseName,currentPage,size);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	/**
  	 * desc:获取需求类别接口
	 * return:Map<String,Object>
	 * time:2018年7月18日
	 * author:L
	 */
	@RequestMapping(value="/selectRequirementType")
	@ResponseBody
	public Map<String,Object> doSelectRequirementType (){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doSelectRequirementType();
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc:确认需求订单完成验货接口
	 * param:roId用户需求订单
	 * return:Map<String,Object>
	 * time:2018年7月18日
	 * author:L
	 */
	@RequestMapping(value="/updateRequirementOrderStatus")
	@ResponseBody
	public Map<String,Object> doUpdateRequirementOrderStatus (String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对roId进行非空判断
		if (Utils.isEmpty(roId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doUpdateRequirementOrderStatus(roId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc:移除申请接单店铺接口
	 * param:roId用户需求订单,sId店铺主键标识
	 * return:Map<String,Object>
	 * time:2018年7月18日
	 * author:L
	 */
	@RequestMapping(value="/deleteStoreApplyRequirement")
	@ResponseBody
	public Map<String,Object> doDeleteStoreApplyRequirement (String roId,String sId){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对roId进行非空判断
		if (Utils.isEmpty(roId) || Utils.isEmpty(sId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doDeleteStoreApplyRequirement(roId,sId);
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
  	 * desc:获取支付订单号接口
	 * param:uId--用户主键标识
	 * return:Map<String,Object>
	 * time:2018年5月31日
	 * author:J
	 */
	@RequestMapping(value="/getOrderPayCode")
	@ResponseBody
	public Map<String,Object> doGetOrderPayCode (String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		if (Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetOrderPayCode();
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc: 商品订单确认收货接口
	 * param:uId用户主键标识,poId商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年7月25日
	 * author:L
	 */
	@RequestMapping(value="/getConfirmReceipt")
	@ResponseBody
	public Map<String,Object> doGetConfirmReceipt (String poId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(poId)||Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetConfirmReceipt(poId,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc: 商品订单退款
	 * param:uId用户主键标识,poId商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年7月25日
	 * author:L
	 * spl：
	 */
	@RequestMapping(value="/getProductRequestRefund")
	@ResponseBody
	public Map<String,Object> doGetProductRequestRefund (String poId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(poId)||Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetProductRequestRefund(poId,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc: 取消商品订单退款
	 * param:uId用户主键标识,poId商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年7月25日
	 * author:L
	 * spl：
	 */
	@RequestMapping(value="/cancelProductRequestRefund")
	@ResponseBody
	public Map<String,Object> doCancelProductRequestRefund (String poId,String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(poId)||Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doCancelProductRequestRefund(poId,uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * desc: 获取待验货商品信息
	 * param:uId用户主键标识,roId商品订单主键标识
	 * return:Map<String,Object>
	 * time:2018年7月28日
	 * author:L
	 * spl：
	 */
	@RequestMapping(value="/stayInspectionProduct")
	@ResponseBody
	public Map<String,Object> doStayInspectionProduct (String uId, String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(uId) || Utils.isEmpty(roId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doStayInspectionProduct(uId, roId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * * desc: 需求订单确认验货
	 * param:roId:需求订单主键标识
	 * return:Map<String,Object>
	 * time:2018年7月28日
	 * author:L
	 * spl：
	 */
	@RequestMapping(value="/rorderVerification")
	@ResponseBody
	public Map<String,Object> doRorderVerification (String roId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(roId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doRorderVerification(roId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
	
	/**
	 * * desc: 获取某用户在某店铺内浏览过的商品列表接口
	 * param: sId--商户主键标识
	 * 			uId--用户端的用户主键标识
	 * return:Map<String,Object>
	 * time:2018年7月28日
	 * author:L
	 * spl：
	 */
	@RequestMapping(value="/getStoreProductListFromUserFootprint")
	@ResponseBody
	public Map<String,Object> doGetStoreProductListFromUserFootprint (String sId, String uId){
		Map<String,Object> map = new HashMap<String,Object>();
		//对poId,uId进行非空校验
		if (Utils.isEmpty(sId) || Utils.isEmpty(uId)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data = productService.doGetStoreProductListFromUserFootprint(sId, uId);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;	
	}
}
