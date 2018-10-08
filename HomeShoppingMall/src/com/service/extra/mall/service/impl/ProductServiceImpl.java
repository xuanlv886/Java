package com.service.extra.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.ProductMapper;
import com.service.extra.mall.mapper.StoreMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.model.Pic;
import com.service.extra.mall.model.Product;
import com.service.extra.mall.model.ProductEvaluate;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.ProductProperty;
import com.service.extra.mall.model.ProductSearch;
import com.service.extra.mall.model.ProductType;
import com.service.extra.mall.model.ProductTypeDetail;
import com.service.extra.mall.model.RequirementOrder;
import com.service.extra.mall.model.RequirementOrderEvaluate;
import com.service.extra.mall.model.RequirementType;
import com.service.extra.mall.model.Store;
import com.service.extra.mall.model.StoreApplyRequirement;
import com.service.extra.mall.model.User;
import com.service.extra.mall.model.UserAttention;
import com.service.extra.mall.model.UserCollection;
import com.service.extra.mall.model.UserDeliverAddress;
import com.service.extra.mall.model.UserFootprint;
import com.service.extra.mall.model.UserMemoName;
import com.service.extra.mall.model.UserRequirement;
import com.service.extra.mall.model.vo.DoAddProductOrderByTrolley;
import com.service.extra.mall.model.vo.DoAddProductOrderByTrolley.ParameterData;
import com.service.extra.mall.model.vo.EvaluateDataVo;
import com.service.extra.mall.model.vo.EvaluateDataVo.EvaluateData.PicList;
import com.service.extra.mall.search.SearchPage;
import com.service.extra.mall.service.ProductService;

import util.Utils;

/**
 * 
 * desc：平台管理系统相关接口服务实现类
 * @author L
 * time:2018年5月17日
 */
@Service
public class ProductServiceImpl implements ProductService{
	@Resource private ProductMapper productMapper;
	@Resource private UserMapper userMapper;
	@Resource private StoreMapper storeMapper;
	@Resource private ManageSystemMapper manageSystemMapper;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doGetProductInfo(String pId,String uId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 判断商品是否收藏
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (user == null) {
			map.put("isCollection", "false");
		}else {
			UserCollection userCollection = userMapper.doGetUserIfCollection(uId,pId);
			if (userCollection == null) {
				map.put("isCollection", "false");
			}else {
				map.put("isCollection", "true");
			}
		}
		/**
		 * 根据pId获取商品信息
		 */
		Product product = productMapper.doGetProductByPId(pId);
		map.put("pName", product.getpName());
		map.put("pBrowseNum", product.getpBrowseNum());
		map.put("ptdId", product.getPtdId());
		map.put("pOriginalPrice", product.getpOriginalPrice());
		map.put("pNowPrice", product.getpNowPrice());
		map.put("pDescribe", product.getpDescribe());
		map.put("pStockNum", product.getpStockNum());
		String sId = product.getsId();
		map.put("sId", sId);
		/**
		 * 获取用户信息
		 */
		User user1 = userMapper.doGetUserInfoBySId(sId);
		String uId1 = user1.getuId();
		/**
		 * 获取该用户给店铺的备注名
		 */
		UserMemoName userMemoName = userMapper.doGetUserMemoNameByUid(uId, uId1);
		if (userMemoName != null) {
			map.put("umnName", userMemoName.getUmnName());
		}else {
			map.put("umnName", "");
		}
		/**
		 * 获取店铺店主信息
		 */
		User user2 = userMapper.doGetUserInfoBySId(sId);
		map.put("accid", user2.getuAccid());
		String html = "";
		if (product.getpHtml() != null) {
			html = new String(product.getpHtml());
		}
		map.put("html", html);
		String pTag = product.getpTag();
		/**
		 * 根据pTag查询图片信息
		 */
		List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
		List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
		for (Pic pic : pics) {
			Map<String,Object> picMap = new HashMap<String,Object>();
			picMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName());
			picMap.put("picNo", pic.getpNo());
			picMap.put("picId", pic.getpId());
			picList.add(picMap);
		}
		map.put("picList", picList);
		//String ptdId = product.getPtdId();//获取商品ptdId
		/**
		 * 获取商品关联属性
		 */
		List<ProductProperty> productProperties = productMapper.doGetProductPropertyByPId(pId);
		Set<String> set = new HashSet<String>();
		Set<String> ppropertyNames = new HashSet<String>();
		for (ProductProperty productProperty : productProperties) {
			String name = productProperty.getPpName();
			if (set.contains(name)) {
				ppropertyNames.add(name);
			}
			set.add(name);
		}
		Iterator<String> it = set.iterator();
		List<Object> propertysList1 = new ArrayList<Object>();
		List<Object> propertysList2 = new ArrayList<Object>();
		while (it.hasNext()) {
			Map<String, Object> nameMap = new HashMap<String, Object>();
			String str = it.next();
			List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();//属性值集合
			for (int i = 0; i < productProperties.size(); i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				if (str.equals(productProperties.get(i).getPpName())) {
					valueMap.put("id", productProperties.get(i).getPpId());
					valueMap.put("value", productProperties.get(i).getPpValue());
					valueList.add(valueMap);
				}
			}
			nameMap.put("name", str);
			nameMap.put("values", valueList);
			if (1==valueList.size()) {
				propertysList1.add(nameMap);
				map.put("propertysList1", propertysList1);
			}else {
				propertysList2.add(nameMap);
				map.put("propertysList2", propertysList2);
			}
		}
		map.put("ppropertyNames", ppropertyNames);
		if (!Utils.isEmpty(uId)) {
			//如果用户不为空
			/**
			 * 判断商品是否存在用户足迹
			 */
			UserFootprint userFootprint = userMapper.selectUserFootPrint(uId,pId);
			if (userFootprint == null) {
				//第一次访问该商品,添加用户足迹
				/**
				 * 添加用户足迹
				 */
				int row = userMapper.doAddUserFootPrint(uId,pId);
				if (1 != row) {
					map.put("errorString", "添加用户商品足迹失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}
		}
		/**
		 * 修改商品被浏览次数
		 */
		int row = productMapper.doUpdateProductBrowseNum(pId);
		if (1 != row) {
			map.put("errorString", "修改商品访问次数失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public Map<String, Object> doGetProductType() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品大类表
		 */
		List<ProductType> productTypes = productMapper.doGetProductType();
		List<Map<String, Object>> productTypeList = new ArrayList<Map<String,Object>>();
		for (ProductType productType : productTypes) {
			Map<String,Object> productTypeMap = new HashMap<String,Object>();
			productTypeMap.put("ptId", productType.getPtId());
			productTypeMap.put("ptName", productType.getPtName());
			productTypeList.add(productTypeMap);
		}
		map.put("productTypeList", productTypeList);
		return map;
	}

	@Override
	public Map<String, Object> doGetProductList(String uId, String currentPage, String size, String ptdId,
			String priceSort, String saleSort, String parameterData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		// 创建商品服务集合
//		List<String> serviceList = new ArrayList<String>();
		// 创建商品属性集合
		List<String> propertieList = new ArrayList<String>();
		int s = Integer.parseInt(size);
		int c = Integer.parseInt(currentPage);
		String highPrice = null;
		String lowPrice = null;
		if (parameterData != null && parameterData.length() > 0) {
			Gson gson = new Gson();
			Map<String, Object> retMap = gson.fromJson(parameterData, new TypeToken<Map<String, Object>>() {
			}.getType());
			// 获取所有的服务条件
//			List<String> services = (List<String>) retMap.get("service");
//			for (String string : services) {
//				/**
//				 * 根据服务名称查询服务属性获取id
//				 */
//				ProductServices productServices = productMapper.getProductServiceByName(string);
//				serviceList.add(productServices.getPsId());
//			}
			lowPrice = (String) retMap.get("lowPrice");
			highPrice = (String) retMap.get("highPrice");
			propertieList = (List<String>) retMap.get("properties");
		}
		/**
		 * 筛选搜索商品信息
		 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ptdId", ptdId);
		paramMap.put("i", (c-1)*s);
		paramMap.put("j", s);
		paramMap.put("properties", propertieList);
		paramMap.put("lowPrice", lowPrice);
		paramMap.put("highPrice", highPrice);
		paramMap.put("priceSort", priceSort);
		paramMap.put("saleSort", saleSort);
		/**
		 * 判断用户是商户还是平台用户
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if(null != user) {
			if (null != user.getsId() && Utils.isEmpty(user.getsId())) {
				paramMap.put("userType", "1"); // 商户
			} else {
				paramMap.put("userType", "0"); // 平台用户
			}
		}
		/**
		 * 根据筛选条件获取满足的所有商品
		 */
		List<Product> products = productMapper.doGetProductList(paramMap);
		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
		for (Product product : products) {
			Map<String, Object> productMap = new HashMap<String, Object>();
			productMap.put("pName", product.getpName());
			productMap.put("pId", product.getpId());
			productMap.put("pOriginalPrice", product.getpOriginalPrice());
			productMap.put("pNowPrice", product.getpNowPrice());
			productMap.put("pDescribe", product.getpDescribe());
			productMap.put("serviceList", "");
			productMap.put("pSaleNum", product.getpTotalNum() - product.getpStockNum());
//			productMap.put("sId", product.getsId());
			String pTag = product.getpTag();
//			String sId = product.getsId();
			/**
			 * 获取商铺信息
			 */
//			Store store = storeMapper.doGetStoreInfo(sId);
//			productMap.put("sAddress", store.getsAddress());
//			productMap.put("sName", store.getsName());
//			productMap.put("sId", store.getsId());
			/**
			 * 获取第一张图片信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			if (pic == null) {
				productMap.put("picName", "");
			}else {
				productMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName());
			}
			productList.add(productMap);
		}
		map.put("productList", productList);
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserRequirement(String urId, String uId, String rtId, String urTitle,
			String urContent, String urOfferType, String urOfferPrice, String urTrueName, String urTel,
			String urAddress) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改用户需求
		 */
		Map<String,Object> requirementMap = new HashMap<String,Object>();
		requirementMap.put("urId", urId);
		requirementMap.put("uId", uId);
		requirementMap.put("rtId", rtId);
		requirementMap.put("urTitle", urTitle);
		requirementMap.put("urContent", urContent);
		requirementMap.put("urOfferType", urOfferType);
		requirementMap.put("urOfferPrice", urOfferPrice);
		requirementMap.put("urTrueName", urTrueName);
		requirementMap.put("urTel", urTel);
		requirementMap.put("urAddress", urAddress);
		int row = productMapper.doUpdateUserRequirement(requirementMap);
		if (1 != row) {
			map.put("errorString", "修改用户需求失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");		
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddUserRequirement(String uId, String rtId, String urTitle, String urContent,
			String urOfferType, String urOfferPrice, String urTrueName, String urTel,
			String urAddress,String sId, String urGetAddress)
					throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 发布我的需求
		 */
		int uot = Integer.parseInt(urOfferType);
		if (0 != uot) {
			urOfferPrice = "0";
		} 
		int row = productMapper.doAddUserRequirement(uId,rtId,urTitle,urContent,
				urOfferType,urOfferPrice,urTrueName,urTel,urAddress,sId, urGetAddress);
		if (1 != row) {
			map.put("errorString", "添加用户需求失败");
			map.put("status", "false");
			throw new RuntimeException();
		} 
		UserRequirement userRequirement = productMapper.doSelectUserRequirement(uId);
		String urId = userRequirement.getUrId();
		/**
		 * 添加需求订单
		 */
		String orOrderId = Utils.formatNowTimeLimitMillisecond() + Utils.getFourRandom();
		String roTotalPrice = urOfferPrice;
		int row1 = productMapper.doAddUserRequirementOrder(urId,rtId,roTotalPrice,orOrderId,sId);
		if (1 != row1) {
			map.put("errorString", "添加需求订单失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("status", "true");
		map.put("errorString", "");
		return map;
	}

	@Override
	public Map<String, Object> doGetUserRequirement(String uId, String status,String currentPage,String size) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		int start = (c-1)*s;
		int end = s;
		/**
		 * 获取我的需求
		 */
		List<UserRequirement> userRequirements = productMapper.doGetUserRequirement(uId,status,start,end);
		List<Map<String, Object>> userRequirementList = new ArrayList<Map<String,Object>>();
		for (UserRequirement userRequirement : userRequirements) {
			Map<String,Object> userRequirementMap = new HashMap<String,Object>();
			userRequirementMap.put("urId", userRequirement.getUrId());
			userRequirementMap.put("uId", userRequirement.getuId());
			userRequirementMap.put("rtId", userRequirement.getRtId());
			userRequirementMap.put("urTitle", userRequirement.getUrTitle());
			userRequirementMap.put("urContent", userRequirement.getUrContent());
			userRequirementMap.put("urOfferType", userRequirement.getUrOfferType());
			userRequirementMap.put("urOfferPrice", userRequirement.getUrOfferPrice());
			userRequirementMap.put("urTrueName", userRequirement.getUrTrueName());
			userRequirementMap.put("urTel", userRequirement.getUrTel());
			userRequirementMap.put("urAddress", userRequirement.getUrAddress());
			userRequirementMap.put("urCreateTime", userRequirement.getUrCreateTime());
			userRequirementMap.put("sId", userRequirement.getsId());
			userRequirementMap.put("urBrowserNum", userRequirement.getUrBrowserNum());
			userRequirementMap.put("urGetAddress", userRequirement.getUrGetAddress());
			String sId = userRequirement.getsId();
			/**
			 * 根据sId获取店铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			if (store != null) {
				userRequirementMap.put("sName", store.getsName());
			}
			String rtId = userRequirement.getRtId();
			/**
			 * 根据rtId获取需求类别信息
			 */
			RequirementType requirementType = productMapper.doGetRequirementType(rtId);
			userRequirementMap.put("rtName", requirementType.getRtName());
			/**
			 * 获取需求订单信息根据urId
			 */
			RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(userRequirement.getUrId());
			/**
			 * 查询该需求申请接单数量
			 */
			int applyNum = storeMapper.doGetStoreApplyRequirementNum(requirementOrder.getRoId());
			userRequirementMap.put("applyNum", applyNum);
			userRequirementMap.put("roConfirmTime", requirementOrder.getRoConfirmTime());
			userRequirementMap.put("roOverTime", requirementOrder.getRoOverTime());
			userRequirementMap.put("roGetTime", requirementOrder.getRoGetTime());
			userRequirementMap.put("roVerificationTime", requirementOrder.getRoVerificationTime());
			userRequirementMap.put("status", status);
			userRequirementList.add(userRequirementMap);
		}
		map.put("userRequirementList", userRequirementList);
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddRequirementEvaluate(String uId, String roId, String roeContent, String roeLevel,
			String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 添加需求订单评价
		 */
		int row = productMapper.doAddRequirementEvaluate(uId,roId,roeContent,roeLevel,sId);
		if (1 != row) {
			map.put("errorString", "添加用户需求评价失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		int status = 6;
		/**
		 * 修改需求订单状态
		 */
		int row1 = productMapper.doUpdateRequirementOrderStatus(status,roId, "");
		if (1 != row1) {
			map.put("errorString", "添加用户需求订单状态失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}

	@Override
	public Map<String, Object> doGetUserRequirementDetail(String uId, String urId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户需求详情
		 */
		UserRequirement userRequirement = productMapper.doGetUserRequirementDetail(urId);
		map.put("urId", userRequirement.getUrId());
		map.put("uId", userRequirement.getuId());
		map.put("rtId", userRequirement.getRtId());
		String rtId = userRequirement.getRtId();
		/**
		 * 根据rtId获取需求类别信息
		 */
		RequirementType requirementType = productMapper.doGetRequirementType(rtId);
		map.put("rtName", requirementType.getRtName());
		map.put("urTitle", userRequirement.getUrTitle());
		map.put("urContent", userRequirement.getUrContent());
		map.put("urOfferType", userRequirement.getUrOfferType());
		String urop = String.valueOf(userRequirement.getUrOfferPrice());
		map.put("urOfferPrice", urop);
		map.put("urTrueName", userRequirement.getUrTrueName());
		map.put("urTel", userRequirement.getUrTel());
		map.put("urAddress", userRequirement.getUrAddress());
		map.put("urCreateTime", userRequirement.getUrCreateTime());
		map.put("sId", userRequirement.getsId());
		String sId = userRequirement.getsId();
		/**
		 * 根据sId获取店铺信息
		 */
		if (sId != null && !"".equals(sId)) {
			Store store = storeMapper.doGetStoreInfo(sId);
			map.put("sName", store.getsName());
			map.put("sDescribe", store.getsDescribe());
			map.put("sTel", store.getsTel());
			map.put("sLevel", store.getsLevel());
		}
		map.put("urBrowserNum", userRequirement.getUrBrowserNum());
		map.put("urGetAddress", userRequirement.getUrGetAddress());
		/**
		 * 根据urId查询用户需求订单
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(urId);
		map.put("roId", requirementOrder.getRoId());
		map.put("roCreateTime", requirementOrder.getRoCreateTime());
		map.put("roStatus", requirementOrder.getRoStatus());
		map.put("pTag", requirementOrder.getpTag());
		String rotp = String.valueOf(requirementOrder.getRoTotalPrice());
		map.put("roTotalPrice", rotp);
		map.put("roOrderId", requirementOrder.getRoOrderId());
		map.put("roConfirmTime", requirementOrder.getRoConfirmTime());
		map.put("roOverTime", requirementOrder.getRoOverTime());
		map.put("roGetTime", requirementOrder.getRoGetTime());
		map.put("roVerificationTime", requirementOrder.getRoVerificationTime());
		String roId = requirementOrder.getRoId();
		/**
		 * 获取所有接单店铺信息
		 */
		List<StoreApplyRequirement> storeApplyRequirements = storeMapper.doGetAllStoreApplyRequirement(roId);
		List<Map<String, Object>> storeApplyRequirementList = new ArrayList<Map<String,Object>>();
		for (StoreApplyRequirement storeApplyRequirement : storeApplyRequirements) {
			Map<String,Object> storeApplyRequirementMap = new HashMap<String,Object>();
			String sId1 = storeApplyRequirement.getsId();
			//已完成单数
			int num = productMapper.doGetUserRequirementOrderNum(sId1);
			storeApplyRequirementMap.put("sId", sId1);
			/**
			 * 获取网易云accId
			 */
			User user = userMapper.doGetUserInfoBySId(sId1);
			storeApplyRequirementMap.put("accId", user.getuAccid());
			String pTag = user.getpTag();
			/**
			 * 获取店铺头像图片
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			String picName = "";
			if (pic != null) {
				picName = Utils.PIC_BASE_URL + Utils.PROFILE_PIC + pic.getpName();
			} 
			/**
			 * 根据sId1获取店铺信息
			 */
			Store store1 = storeMapper.doGetStoreInfo(sId1);
			
			storeApplyRequirementMap.put("sDescribe", store1.getsDescribe());
			storeApplyRequirementMap.put("sName", store1.getsName());
			storeApplyRequirementMap.put("sTel", store1.getsTel());
			storeApplyRequirementMap.put("sLevel", store1.getsLevel());
			storeApplyRequirementMap.put("sarPrice", storeApplyRequirement.getSarPrice());
			storeApplyRequirementMap.put("sarCreateTime", storeApplyRequirement.getSarCreateTime());
			storeApplyRequirementMap.put("picName", picName);
			storeApplyRequirementMap.put("finishedNum", num);
			storeApplyRequirementList.add(storeApplyRequirementMap);
			//接单店铺的数量
			int num1 = productMapper.doGetStoreApplyRequirementNum(sId1);
			map.put("applyNum", num1);
			map.put("storeApplyRequirementList", storeApplyRequirementList);
		}
		/**
		 * 获取需求评价内容
		 */
		RequirementOrderEvaluate evaluate = storeMapper
				.getRequirementOrderEvaluate(requirementOrder.getRoId());
		if (null != evaluate) {
			map.put("roeLevel", evaluate.getRoeLevel());
			map.put("roeContent", evaluate.getRoeContent());
			map.put("roeCreateTime", evaluate.getRoeCreateTime());
		} else {
			map.put("roeLevel", 0);
			map.put("roeContent", "");
			map.put("roeCreateTime", "");
		}
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddProductEvaluate(String uId, String poId, String evaluateData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		//1 向商品评价表中插入评价信息
		//2 修改商品订单状态
		//3 修改商品订单详情状态
		if (!Utils.isEmpty(evaluateData)) {
			EvaluateDataVo evaluateDataVo = new EvaluateDataVo();
			evaluateDataVo = Utils.parserJsonResult(evaluateData, EvaluateDataVo.class);
			
			/**
			 * 向t_product_evaluate表中插入数据
			 * 一条订单有一种或多种商品(即在评价表中生成一条或多条记录)
			 */
			/**
			 * 获取订单详情信息
			 */
			ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
			int poStatus = productOrder.getPoStatus();
			if (3 == poStatus) {
				for (int i = 0; i < evaluateDataVo.getEvaluateData().size(); i++) {
					String pId = evaluateDataVo.getEvaluateData().get(i).getpId();
					String podId = evaluateDataVo.getEvaluateData().get(i).getPodId();
					String peLevel = evaluateDataVo.getEvaluateData().get(i).getPeLevel();
					String peContent = evaluateDataVo.getEvaluateData().get(i).getPeContent();
					List<PicList> picLists = evaluateDataVo.getEvaluateData().get(i).getPicList();
					//如果图片不为空,插入图片
					String pTag = null;
					if (picLists != null) {
						pTag = Utils.randomUUID();
						for (PicList picList : picLists) {
							int pNo = Integer.valueOf(picList.getpNo());
							String pName = picList.getpName();
							int row = productMapper.doAddPic(Utils.EVALUATE_PIC, pName, pNo, pTag);
							if (row != 1) {
								map.put("errorString", "插入图片失败");
								map.put("status", "false");
								throw new RuntimeException();
							}
						}
					}
					/**
					 * 添加商品订单评价
					 */
					int row = productMapper.doAddProductEvaluate(uId,pId,podId,peLevel,peContent,pTag);
					if (1 != row) {
						map.put("errorString", "添加商品订单评价失败");
						map.put("status", "false");
						throw new RuntimeException();
					}
					
					int podEvaluate = 1;
					/**
					 * 修改商品订单详情
					 */
					int row1 = productMapper.doUpdateProductOrderDetail(podId,podEvaluate);
					if (1 != row1) {
						map.put("errorString", "修改商品订单详情评价状态失败");
						map.put("status", "false");
						throw new RuntimeException();
					}
				}
				/**
				 * 修改订单状态为已评价
				 */
				int status = 4;
				int row2 = productMapper.doUpdateProductOrderStatus(status,poId);
				if (1 != row2) {
					map.put("errorString", "修改商品订单状态失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public Map<String, Object> doGetUserProductEvaluate(String uId, String peLevel) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		/**
		 * 获取我的所有商品订单评价
		 */
		List<ProductEvaluate> productEvaluates = productMapper.doGetUserProductEvaluate(uId,peLevel);
		List<Map<String, Object>> productEvaluateList = new ArrayList<Map<String,Object>>();
		for (ProductEvaluate productEvaluate : productEvaluates) {
			Map<String,Object> productEvaluateMap = new HashMap<String,Object>();
			productEvaluateMap.put("peId", productEvaluate.getPeId());
			productEvaluateMap.put("pId", productEvaluate.getProduct().getpId());
			productEvaluateMap.put("peLevel", productEvaluate.getPeLevel());
			productEvaluateMap.put("podId", productEvaluate.getPodId());
			productEvaluateMap.put("peContent", productEvaluate.getPeContent());
			productEvaluateMap.put("peCreateTime", productEvaluate.getPeCreateTime());
			productEvaluateMap.put("pDescribe", productEvaluate.getProduct().getpDescribe());
			productEvaluateMap.put("pName", productEvaluate.getProduct().getpName());
			productEvaluateMap.put("picName",Utils.PIC_BASE_URL + Utils.EVALUATE_PIC + 
					productEvaluate.getProduct().getPic().getpName());
			/**
			 * 获取用户头像
			 */
			Pic ProfilePhoto = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
			if (ProfilePhoto == null) {
				productEvaluateMap.put("picFileName", "");//使用默认头像
				productEvaluateMap.put("picName", "");//使用默认头像
			}else {
				productEvaluateMap.put("picFileName", ProfilePhoto.getpFileName());
				productEvaluateMap.put("picName",Utils.PIC_BASE_URL + Utils.PROFILE_PIC + ProfilePhoto.getpName());
			}
			String picId = productEvaluate.getpTag();
			List<Map<String,Object>> picList = new ArrayList<>();
			if (picId != null) {
				String[] picIds = picId.split(",");
				for (String picId1 : picIds) {
					/**
					 * 根据picId获取图片实体
					 */
					Pic pic = productMapper.getPicByPid(picId1);
					Map<String,Object> picMap = new HashMap<>();
					picMap.put("url",Utils.PIC_BASE_URL+Utils.EVALUATE_PIC+pic.getpName());
					picMap.put("picNo", pic.getpNo());
					picList.add(picMap);
				}
			}
			productEvaluateMap.put("picList", picList);
			productEvaluateMap.put("uNickName", user.getuNickName());
			productEvaluateList.add(productEvaluateMap);
		}
		map.put("productEvaluateList", productEvaluateList);
		return map;
	}

	@Override
	public Map<String, Object> doGetProductOrderDetail(String uId, String poId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		map.put("poId", productOrder.getPoId());
		map.put("psId", productOrder.getPsId());
		map.put("uId", productOrder.getuId());
		map.put("poCreateTime", productOrder.getPoCreateTime());
		map.put("poTotalPrice", productOrder.getPoTotalPrice());
		map.put("poStatus", productOrder.getPoStatus());
		map.put("poPayTime", productOrder.getPoPayTime());
		map.put("poSendTime", productOrder.getPoSendTime());
		map.put("poDeliverTime", productOrder.getPoDeliverTime());
		map.put("poOverTime", productOrder.getPoOverTime());
		map.put("poPayCode", productOrder.getPoPayCode());
		map.put("poOrderId", productOrder.getPoOrderId());
		map.put("poDel", productOrder.getPoDel());
		map.put("poDeliverName", productOrder.getPoDeliverName());
		map.put("poDeliverTel", productOrder.getPoDeliverTel());
		map.put("poDeliverAddress", productOrder.getPoDeliverAddress());
		map.put("sId", productOrder.getsId());
		map.put("poDeliverCompany", productOrder.getPoDeliverCompany());
		map.put("poDeliverCode", productOrder.getPoDeliverCode());
		/**
		 * 根据uId获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		map.put("uTel", user.getuTel());
		/**
		 * 根据poId获取订单详情
		 */
		List<ProductOrderDetail> productOrderDetail = productMapper.doGetProductOrderDetailByPoId(poId);
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < productOrderDetail.size(); i++) {
			Map<String,Object> detailMap = new HashMap<>();
			detailMap.put("podId", productOrderDetail.get(i).getPodId());
			detailMap.put("podPrice", productOrderDetail.get(i).getPodPrice());
			detailMap.put("podProperty", productOrderDetail.get(i).getPodProperty());
			detailMap.put("podNum", productOrderDetail.get(i).getPodNum());
			detailMap.put("podEvaluate", productOrderDetail.get(i).getPodEvaluate());
			detailMap.put("pId", productOrderDetail.get(i).getpId());
			String pId = productOrderDetail.get(i).getpId();
			/**
			 * 根据pId获取商品信息
			 */
			Product product = productMapper.doGetProductByPId(pId);
			detailMap.put("pName", product.getpName());
			detailMap.put("pDescribe", product.getpDescribe());
			String pTag = product.getpTag();
			/**
			 * 根据pTag获取图片信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			detailMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName());
			detailMap.put("picId", pic.getpId());
			detailList.add(detailMap);
		}
		map.put("orderDetails", detailList);
		/**
		 * 根据sId获取商铺信息
		 */
		String sId = productOrder.getsId();
		Store store = storeMapper.doGetStoreInfo(sId);
		map.put("sName", store.getsName());
		/**
		 * 获取用户信息
		 */
		User user1 = userMapper.doGetUserInfoBySId(sId);
		String uId1 = user1.getuId();
		/**
		 * 获取该用户给店铺的备注名
		 */
		UserMemoName userMemoName = userMapper.doGetUserMemoNameByUid(uId, uId1);
		if (userMemoName != null) {
			map.put("umnName", userMemoName.getUmnName());
		}else {
			map.put("umnName", "");
		}
		/**
		 * 根据sId获取店铺资料
		 */
		User user2 = userMapper.doGetUserInfoBySId(sId);
		map.put("accid", user2.getuAccid());
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddProductOrder(String uId, String udaId, String pNum, String pId, String pProperty)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据pId获取商品信息
		 * 
		 */
		Product product = productMapper.doGetProductByPId(pId);
		if (product==null) {
			map.put("errorString", "商品不存在");
			map.put("status", "false");
			return map;
		}
		int stockNum = product.getpStockNum();
		int pnum = Integer.valueOf(pNum);
		// 判断库存数是否大于用户所需数量
		if (stockNum < pnum) {
			map.put("errorString", "库存不足提交订单失败");
			return map;
		}
		String sId = product.getsId();
		ProductOrder productOrder = new ProductOrder();
		/**
		 * 根据udaId获取收货地址信息
		 */
		UserDeliverAddress userDeliverAddress = userMapper.doGetUserDeliverAddressByUdaId(udaId);
		String poId = Utils.randomUUID(); // 订单主键标识
		productOrder.setPoId(poId);
		productOrder.setPoDeliverAddress(userDeliverAddress.getUdaAddress());
		productOrder.setPoDeliverTel(userDeliverAddress.getUdaTel());
		productOrder.setPoDeliverName(userDeliverAddress.getUdaTrueName());
		String psId = "";// 支付方式假数据
		productOrder.setPsId(psId);
		// 生成支付订单号
		String poPayCode = Utils.formatNowTimeLimitMillisecond() + Utils.getFourRandom();
		productOrder.setuId(uId);
		productOrder.setPoPayCode(poPayCode);
		productOrder.setPoStatus(0);//待付款
		productOrder.setsId(sId);
		productOrder.setPoPayTime("");
		productOrder.setPoSendTime("");
		productOrder.setPoDeliverTime("");
		productOrder.setPoOverTime("");
		productOrder.setPoOrderId(poPayCode);
		productOrder.setPoDeliverCompany("");
		productOrder.setPoDeliverCode("");
		int num = Integer.parseInt(pNum);
		productOrder.setPoTotalPrice(product.getpNowPrice()*num);
		/**
		 * 立即购买插入商品订单
		 */
		int row = productMapper.doAddProductOrder(productOrder);
		if (1 != row) {
			map.put("errorString", "添加商品订单失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		ProductOrderDetail productOrderDetail = new ProductOrderDetail();
		productOrderDetail.setPodEvaluate(0);
		productOrderDetail.setpId(pId);
		productOrderDetail.setPoId(productOrder.getPoId());
		productOrderDetail.setPodPrice(product.getpNowPrice());
		productOrderDetail.setPodProperty(pProperty);
		//int num = Integer.parseInt(pNum);
		productOrderDetail.setPodNum(num);
		/**
		 * 插入商品订单详情表
		 */
		int row1 = productMapper.doAddProductOrderDetail(productOrderDetail);
		if (1 != row1) {
			map.put("errorString", "添加商品订单详情失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("poPayCode", poPayCode);
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public Map<String, Object> doGetProductProperty(String ptdId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据商品底层类id获取商品服务属性
		 */
		List<ProductProperty> productProperties = productMapper.doGetProductProperty(ptdId);
		
		
		Set<Object> set = new HashSet<Object>();
		for (int i = 0; i < productProperties.size(); i++) {
			String name = productProperties.get(i).getPpName();
			set.add(name);
		}
		Iterator<Object> it = set.iterator();
		List<Object> propertysList1 = new ArrayList<Object>();//单条属性
		List<Object> propertysList2 = new ArrayList<Object>();//多条属性
		while (it.hasNext()) {
			Map<String, Object> nameMap = new HashMap<String, Object>();
			String str = (String) it.next();
			nameMap.put("name", str);
			List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < productProperties.size(); i++) {
				Map<String, String> valueMap = new HashMap<String, String>();
				if (productProperties.get(i).getPpName().equals(str)) {
					valueMap.put("ppId", productProperties.get(i).getPpId());
					valueMap.put("value", productProperties.get(i).getPpValue());
					valueList.add(valueMap);
					nameMap.put("chooseState", productProperties.get(i).getPpChoseType());
					nameMap.put("required", productProperties.get(i).getPpRequired());
					nameMap.put("pp_tag", productProperties.get(i).getPpTag());
				}
			}
			nameMap.put("values", valueList);
			if (1==valueList.size()) {
				propertysList1.add(nameMap);
				map.put("propertysList1", propertysList1);
			}else {
				propertysList2.add(nameMap);
				map.put("propertysList2", propertysList2);
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doGetProductTypeDetailByPtId(String ptId) throws Exception {
		List<Map<String,Object>> map 
			= new ArrayList<Map<String,Object>>();
		/**
		 * 根据ptId获取父类别为ptId的所有二级类
		 */
		List<ProductTypeDetail> productTypeDetails = productMapper.getProductTypeDetailList(ptId);
		for (ProductTypeDetail productTypeDetail : productTypeDetails) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("ptdName", productTypeDetail.getPtdName());
			/**
			 * 获取二级类别下的所有底层的类别
			 */
			List<ProductTypeDetail> bottomProductTypeDetails = productMapper
					.getBottomProductTypeDetail(productTypeDetail.getPtdId());
			List<Map<String, Object>> typelist2 = new ArrayList<Map<String, Object>>();
			for (ProductTypeDetail productTypeDetail2 : bottomProductTypeDetails) {
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("ptdId", productTypeDetail2.getPtdId());
				map3.put("ptdName", productTypeDetail2.getPtdName());
				map3.put("url",Utils.PIC_BASE_URL + Utils.PRODUCT_TYPE_PIC 
						+ productTypeDetail2.getPicName());
				typelist2.add(map3);
			}
			map2.put("typelist2", typelist2);
			map.add(map2);
		}
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelProductOrder(String uId, String poId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 删除商品订单
		 */
		int row = productMapper.doDelProductOrder(uId,poId);
		if (1 != row) {
			map.put("errorString", "删除商品订单失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelProductEvaluate(String peId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 删除商品订单评价
		 */
		int row = productMapper.doDelProductEvaluate(peId);
		if (1 != row) {
			map.put("errorString", "删除商品订单评价失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}

	@Override
	public Map<String, Object> doGetProductOrder(String uId, String status, String currentPage, String size)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		/**
		 * 获取我的订单
		 */
		List<ProductOrder> productOrders = productMapper.doGetProductOrder(uId,status,(c-1)*s,s);
		List<Map<String, Object>> productOrderList = new ArrayList<Map<String, Object>>();
		for (ProductOrder productOrder : productOrders) {
			Map<String,Object> productOrderMap = new HashMap<String,Object>();
			productOrderMap.put("poId", productOrder.getPoId());
			productOrderMap.put("psId", productOrder.getPsId());
			productOrderMap.put("uId", productOrder.getuId());
			productOrderMap.put("poCreateTime", productOrder.getPoCreateTime());
			productOrderMap.put("poTotalPrice", productOrder.getPoTotalPrice());
			productOrderMap.put("poStatus", productOrder.getPoStatus());
			//productOrderMap.put("poPayTime", productOrder.getPoPayTime());
			//productOrderMap.put("poSendTime", productOrder.getPoSendTime());
			//productOrderMap.put("poDeliverTime", productOrder.getPoDeliverTime());
			//productOrderMap.put("poOverTime", productOrder.getPoOverTime());
			productOrderMap.put("poPayCode", productOrder.getPoPayCode());
			productOrderMap.put("poOrderId", productOrder.getPoOrderId());
			//productOrderMap.put("poDel", productOrder.getPoDel());
			//productOrderMap.put("poDeliverName", productOrder.getPoDeliverName());
			//productOrderMap.put("poDeliverAddress", productOrder.getPoDeliverAddress());
			productOrderMap.put("sId", productOrder.getsId());
			productOrderMap.put("poDeliverCompany", productOrder.getPoDeliverCompany());
			productOrderMap.put("poDeliverCode", productOrder.getPoDeliverCode());
			String poId = productOrder.getPoId();
			/**
			 * 根据poId获取订单详情
			 */
			List<ProductOrderDetail> productOrderDetail = productMapper
					.doGetProductOrderDetailByPoId(poId);
			// 商品的总件数
			int sum = 0;
			for (int i = 0; i < productOrderDetail.size(); i++) {
				sum += productOrderDetail.get(i).getPodNum();
			}
			productOrderMap.put("podId", productOrderDetail.get(0).getPodId());
			productOrderMap.put("podPrice", productOrderDetail.get(0).getPodPrice());
			productOrderMap.put("podProperty", productOrderDetail.get(0).getPodProperty());
			productOrderMap.put("podNum", sum);
			productOrderMap.put("podEvaluate", productOrderDetail.get(0).getPodEvaluate());
			productOrderMap.put("pId", productOrderDetail.get(0).getpId());
			String pId = productOrderDetail.get(0).getpId();
			/**
			 * 根据pId获取商品信息
			 */
			Product product = productMapper.doGetProductByPId(pId);
			productOrderMap.put("pName", product.getpName());
			productOrderMap.put("pDescribe", product.getpDescribe());
			String pTag = product.getpTag();
			/**
			 * 根据pTag获取图片信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			productOrderMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName());
			productOrderMap.put("picId", pic.getpId());
			/**
			 * 根据sId获取商铺信息
			 */
			String sId = productOrder.getsId();
			Store store = storeMapper.doGetStoreInfo(sId);
			productOrderMap.put("sName", store.getsName());
			productOrderList.add(productOrderMap);
		}
		map.put("productOrderList", productOrderList);
		return map;
	}

	@Override
	public Map<String, Object> doGetRecommendProductList(String uId, String currentPage, String size, String ptdId)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		/**
		 * 根据ptdId查询推荐商品
		 */
		List<Product> products = productMapper.doGetRecommendProductList(ptdId,(c-1)*s,s);
		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
		for (Product product : products) {
			Map<String,Object> productMap = new HashMap<String,Object>();
			productMap.put("pId", product.getpId());
			productMap.put("pName", product.getpName());
			productMap.put("pDescribe", product.getpDescribe());
			productMap.put("pOriginalPrice", product.getpOriginalPrice());
			productMap.put("pNowPrice", product.getpNowPrice());
			productMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + product.getPic().getpName());
			productList.add(productMap);
		}
		map.put("productList", productList);
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddProductOrderByTrolley(String uId, String udaId, String parameterData)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		// 解析json数据
		Gson gson = new Gson();
		DoAddProductOrderByTrolley doAddProductOrderByTrolley = gson.fromJson(parameterData,
				DoAddProductOrderByTrolley.class);
		List<ParameterData> list = doAddProductOrderByTrolley.getParameterData();
		// 支付订单号 购物车里所有商品的支付订单号相同
		String poPayCode = Utils.formatNowTimeLimitMillisecond() + Utils.getFourRandom();
		for (ParameterData parameterData2 : list) {
			double totalPrice = 0;
			int pNum = parameterData2.getUtProductNum();
			String pProperty = parameterData2.getUtProductProperty();
			String utId = parameterData2.getUtId();
			/**
			 * 获取商品信息
			 */
			String pId = parameterData2.getpId();
			Product product = productMapper.doGetProductByPId(pId);
			String sId = product.getsId();
			int stockNum = product.getpStockNum();
			// 判断库存数是否大于用户所需数量
			if (stockNum < pNum) {
				map.put("errorString", "库存不足提交订单失败");
				return map;
			}
			totalPrice += product.getpNowPrice() * pNum;
			/**
			 * 插入商品订单
			 */
			ProductOrder productOrder = new ProductOrder();
			/**
			 * 根据rdaId获取收货地址信息
			 */
			UserDeliverAddress userDeliverAddress = userMapper.doGetUserDeliverAddressByUdaId(udaId);
			String poId = Utils.randomUUID(); // 订单主键标识
			productOrder.setPoId(poId);
			productOrder.setPoDeliverAddress(userDeliverAddress.getUdaAddress());
			productOrder.setPoDeliverTel(userDeliverAddress.getUdaTel());
			productOrder.setPoDeliverName(userDeliverAddress.getUdaTrueName());
			String psId = "";// 支付方式假数据
			productOrder.setPsId(psId);
			productOrder.setuId(uId);
			productOrder.setPoTotalPrice(totalPrice);
			productOrder.setPoPayTime("");
			productOrder.setPoSendTime("");
			productOrder.setPoDeliverTime("");
			productOrder.setPoOverTime("");
			// 商品订单订单号
			String poOrderId = Utils.formatNowTimeLimitMillisecond() + Utils.getFourRandom();
			productOrder.setPoStatus(0);//待付款
			productOrder.setPoPayCode(poPayCode);
			productOrder.setPoOrderId(poOrderId);
			productOrder.setsId(sId);
			productOrder.setPoDeliverCompany("");
			productOrder.setPoDeliverCode("");
			int row1 = productMapper.doAddProductOrder(productOrder);
			if (1 != row1) {
				map.put("errorString", "插入商品订单失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
			/**
			 * 插入商品订单详情
			 */
			ProductOrderDetail productOrderDetail = new ProductOrderDetail();
			productOrderDetail.setPodEvaluate(0);
			productOrderDetail.setpId(pId);
			productOrderDetail.setPoId(productOrder.getPoId());
			productOrderDetail.setPodPrice(product.getpNowPrice());
			productOrderDetail.setPodProperty(pProperty);
			productOrderDetail.setPodNum(pNum);
			int row2 = productMapper.doAddProductOrderDetail(productOrderDetail);
			if (1 != row2) {
				map.put("errorString", "插入商品订单详情失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
			/**
			 * 删除购物车信息
			 */
			int row = userMapper.doDelUserTrolley(utId, uId);
			if (1 != row) {
				map.put("errorString", "删除购物车信息失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}
		map.put("poPayCode", poPayCode);
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelUserRequirement(String uId, String urId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据需求主键标识获取需求订单
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(urId);
		if (requirementOrder != null) {
			//需求订单已存在
			/**
			 * 删除用户需求订单
			 */
			String roId = requirementOrder.getRoId();
			int row = productMapper.doDelUserRequirementOrder(roId);
			if (1 != row) {
				map.put("errorString", "删除用户需求订单失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
			/**
			 * 删除所有的店铺申请接单
			 */
			int row1 = storeMapper.doDelStoreApplyRequirement(roId);
			
		}
		/**
		 * 删除用户需求
		 */
		int row1 = productMapper.doDelUserRequirement(urId);
		if (1 != row1) {
			map.put("errorString", "删除用户需求失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddUserProductRequirement(String uId, String roId, String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据roId和sId获取商铺申请接单信息
		 */
		StoreApplyRequirement storeApplyRequirement = storeMapper.doGetStoreApplyRequirement(roId,sId);
		double sarPrice = storeApplyRequirement.getSarPrice();
		int status = 1;
		/**
		 * 获取需求订单信息
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
		requirementOrder.setRoTotalPrice(sarPrice);
		requirementOrder.setsId(sId);
		requirementOrder.setRoStatus(status);
		String confirmTime = Utils.formatNowTimeLimitMillisecond();
		requirementOrder.setRoConfirmTime(confirmTime);
		String urId = requirementOrder.getUrId();
		/**
		 * 修改用户需求订单信息
		 */
		int row = productMapper.doUpdateRequirementOrder(requirementOrder);
		if (1 != row) {
			map.put("errorString", "修改用户需求订单信息失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		/**
		 * 修改用户需求信息
		 */
		int row1 = productMapper.doUpdateUserRequirementSId(sId,urId, 0);
		if (1 != row1) {
			map.put("errorString", "修改用户需求信息失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		/**
		 * 根据roId获取申请店铺信息
		 */
		List<StoreApplyRequirement> storeApplyRequirements = storeMapper.doGetAllStoreApplyRequirement(roId);
		int num = storeApplyRequirements.size();
		if (num > 1) {
			/**
			 * 删除其他商户申请接单信息
			 */
			int row2 =storeMapper.doDeleteOtherStoreApplyRequirement(roId,sId);
			if (1 != row2) {
				map.put("errorString", "删除其他商户申请接单信息失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}
		/**
		 * 修改商户申请接单信息
		 */
		int row3 = storeMapper.doUpdateStoreApplyRequirement(status,sId,roId);
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public Map<String, Object> doGetUserRequirementEvaluate(String uId, String roeLevel) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		/**
		 * 根据评价等级和用户id获取所有需求评价
		 */
		List<RequirementOrderEvaluate> requirementOrderEvaluates = productMapper.doGetUserRequirementEvaluate(uId,roeLevel);
		List<Map<String, Object>> requirementOrderEvaluateList = new ArrayList<Map<String,Object>>();
		for (RequirementOrderEvaluate requirementOrderEvaluate : requirementOrderEvaluates) {
			Map<String,Object> requirementOrderEvaluateMap = new HashMap<String,Object>();
			requirementOrderEvaluateMap.put("roeId", requirementOrderEvaluate.getRoeId());
			requirementOrderEvaluateMap.put("roeContent", requirementOrderEvaluate.getRoeContent());
			requirementOrderEvaluateMap.put("roeLevel", requirementOrderEvaluate.getRoeLevel());
			requirementOrderEvaluateMap.put("roId", requirementOrderEvaluate.getRoId());
			requirementOrderEvaluateMap.put("roeCreateTime", requirementOrderEvaluate.getRoeCreateTime());
			requirementOrderEvaluateMap.put("sId", requirementOrderEvaluate.getsId());
			requirementOrderEvaluateMap.put("uId", requirementOrderEvaluate.getuId());
			/**
			 * 获取用户头像
			 */
			Pic ProfilePhoto = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
			if (ProfilePhoto == null) {
				requirementOrderEvaluateMap.put("picFileName", "");//使用默认头像
				requirementOrderEvaluateMap.put("picName", "");//使用默认头像
			}else {
				requirementOrderEvaluateMap.put("picFileName", ProfilePhoto.getpFileName());
				requirementOrderEvaluateMap.put("picName",Utils.PIC_BASE_URL + Utils.PROFILE_PIC + 
						ProfilePhoto.getpName());
			}
			requirementOrderEvaluateMap.put("uNickName", user.getuNickName());
			requirementOrderEvaluateList.add(requirementOrderEvaluateMap);
		}
		map.put("requirementOrderEvaluateList",requirementOrderEvaluateList);
		return map;
	}

	@Override
	public Map<String, Object> doGetProductEvaluate(String pId,String peLevel,String size,String currentPage) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> paramertMap = new HashMap<String,Object>();
		int cP = Integer.parseInt(currentPage);
		int si = Integer.parseInt(size);
		int index = (cP-1)*si;
//		paramertMap.put("pId", pId);
//		paramertMap.put("index", index);
//		paramertMap.put("peLevel", peLevel);
//		paramertMap.put("size", size);
		/**
		 * 获取商品评价数量
		 */
		int levelNum = productMapper.getEvaluesNum(pId,peLevel);
		/**
		 * 获取该商品的所有评价
		 */
		List<ProductEvaluate> productEvaluates = productMapper.doGetProductEvaluate(pId,index,peLevel,si);
		List<Map<String, Object>> productEvaluateList = new ArrayList<Map<String,Object>>();
		for (ProductEvaluate productEvaluate : productEvaluates) {
			Map<String,Object> productEvaluateMap = new HashMap<String,Object>();
			productEvaluateMap.put("peId", productEvaluate.getPeId());
			productEvaluateMap.put("peContent", productEvaluate.getPeContent());
			productEvaluateMap.put("peCreateTime", productEvaluate.getPeCreateTime());
			productEvaluateMap.put("podId", productEvaluate.getPodId());
			productEvaluateMap.put("pId", productEvaluate.getpId());
			productEvaluateMap.put("uId", productEvaluate.getuId());
			String pTag = productEvaluate.getpTag();
			List<Pic> pics = productMapper.getPicByPtag(pTag);
			List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
			for(Pic pic : pics){
				Map<String,Object> picMap = new HashMap<String,Object>();
				picMap.put("url", Utils.PIC_BASE_URL + Utils.EVALUATE_PIC + pic.getpName());
				picMap.put("picNo", pic.getpNo());
				picList.add(picMap);
				productEvaluateMap.put("picList",picList);
			}
			/**
			 * 获取用户信息
			 */
			User user = userMapper.doGetUserInfoByUId(productEvaluate.getuId());
			if (user == null) {
				productEvaluateMap.put("uNickName", "");
			} else {
				productEvaluateMap.put("uNickName", user.getuNickName());
				/**
				 * 获取用户头像
				 */
				Pic ProfilePhoto = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
				if (ProfilePhoto == null) {
					productEvaluateMap.put("picFileName", "");//使用默认头像
					productEvaluateMap.put("picName", "");//使用默认头像
				}else {
					productEvaluateMap.put("picFileName", ProfilePhoto.getpFileName());
					productEvaluateMap.put("picName",Utils.PIC_BASE_URL + Utils.PROFILE_PIC + ProfilePhoto.getpName());
				}
			}
			productEvaluateList.add(productEvaluateMap);
		}
		map.put("levelNum",levelNum);
		map.put("productEvaluateList", productEvaluateList);
		return map;
	}

	@Override
	public Map<String, Object> doSelectBylooseName(String looseName, String ptdId, String priceSort, String saleSort,
			String currentPage, String size) throws Exception {
		int type = 0;//商品
		if (looseName != null && looseName.length() > 0) {
			/**
			 * 根据搜索的名称获取热搜的id
			 */
			String psId = productMapper.doGetProductSearchId(looseName,type);
			if (psId == null) {
				/**
				 * 添加新的热搜信息
				 */
				productMapper.doAddProductSearch(looseName,type);
			} else {
				/**
				 * 修改热搜访问次数
				 */
				productMapper.doUpdateProductSearchNum(psId);
			}
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		int page = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		int index = (page - 1) * s;
		parameterMap.put("index", index);
		parameterMap.put("size", size);
		parameterMap.put("looseName", looseName);
		parameterMap.put("productTypeId", ptdId);
		parameterMap.put("salesSortType", saleSort);
		parameterMap.put("priceSortType", priceSort);
		Map<String, Object> map = SearchPage.doSearch(parameterMap);
		List<Map<String, Object>> messageList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("productMessage");
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map2 = list.get(i);
			
			String productId = (String) map2.get("productId");
			/**
			 * 获取商品是否被删除
			 * t_product:P_DEL
			 */
			Product pDel = productMapper.getProductIsDel(productId);
			if (0 == pDel.getpDel()) { // 未删除
				String proName = (String) map2.get("productName");
				String proPrice = (String) map2.get("price");
				String payNum = (String) map2.get("payNum");
				/**
				 * 根据商品id获取商品信息
				 */
				Product product = productMapper.doGetProductByPId(productId);
				Map<String,Object> map3 = new HashMap<String, Object>();
				/**
				 * 获取商品图片信息
				 */
				Pic pic = userMapper.doSelectOnePicInfoByPTag(product.getpTag());
				String picName = pic.getpName();
				//String url =  Utils.PIC_PRODUCT_ROUTE+picName;
				map3.put("productId", productId);
				map3.put("proName", proName);
				map3.put("proPrice", proPrice);
				map3.put("payNum", payNum);
				map3.put("url",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + picName);
				messageList.add(map3);
			}
		}
		List<Map<String, Object>> list2 = (List<Map<String, Object>>) map.get("productType");
		List<Map<String, Object>> typeList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list2.size(); i++) {
			Map<String,Object> map4 = new HashMap<String, Object>();
			Map<String,Object> map2 = list2.get(i);
			String typeName = (String) map2.get("name");
			String typeId = (String) map2.get("id");
			map4.put("name", typeName);
			map4.put("id", typeId);
			typeList.add(map4);
		}
		map.put("productMessage", messageList);
		map.put("productType", typeList);
		return map;
	}

	@Override
	public Map<String, Object> doGetHotSearch(String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int t = Integer.parseInt(type);
		/**
		 * 获取热搜
		 */
		List<ProductSearch> productSearchs = productMapper.doGetHotSearch(t);
		List<Map<String, Object>> productSearchList = new ArrayList<Map<String,Object>>();
		for (ProductSearch productSearch : productSearchs) {
			Map<String, Object> productSearchMap = new HashMap<String, Object>();
			if (productSearch.getPsNum() > 100) {
				productSearchMap.put("psId", productSearch.getPsId());
				productSearchMap.put("psName", productSearch.getPsName());
				productSearchMap.put("psNum", productSearch.getPsNum());
				productSearchMap.put("psType", productSearch.getPsType());
				productSearchList.add(productSearchMap);
			}
		}
		map.put("hotSearchList", productSearchList);
		return map;
	}

	

	@Override
	public Map<String, Object> doGetStoreDetail(String sId, String currentPage, String size, String ptdId,
			String saleSort, String priceSort,String uId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 获取店铺信息
		 */
		Store store = storeMapper.doGetStoreInfo(sId);
		map.put("sName", store.getsName());
		/**
		 * 获取用户信息
		 */
		User user = userMapper.doGetUserInfoBySId(sId);
		String uId1 = user.getuId();
		map.put("accid",user.getuAccid());
		/**
		 * 判断该店铺是否被用户收藏
		 */
		if (uId != null) {
			/**
			 * 查询该店铺是否被关注
			 */
			UserAttention userAttention =  userMapper.doGetStoreIfAttention(uId,sId);
			if (userAttention == null) {
				map.put("isAttention", "false");
			}else {
				map.put("isAttention", "true");
			}
		}else {
			map.put("isAttention", "false");//没有被关注
		}
		/**
		 * 获取店铺轮播图
		 */
		String pTag = store.getpTag();
		if (pTag != null) {
			List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
			List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
			for (Pic pic : pics) {
				Map<String, Object> picMap = new HashMap<String, Object>();
				picMap.put("picId", pic.getpId());
				picMap.put("picName",Utils.PIC_BASE_URL + Utils.STORE_PIC + pic.getpName());
				picMap.put("picNO", pic.getpNo());
				picList.add(picMap);
			}
			map.put("picList", picList);
		}
		/**
		 * 获取店铺头像信息
		 */
		Pic pic = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
		if (pic != null) {
			map.put("picName",Utils.PIC_BASE_URL + Utils.PROFILE_PIC + pic.getpName());
		} else {
			map.put("picName","");
		}
		
		/**
		 * 获取店铺商品信息
		 */
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		int start = (c-1)*s;
		int end = s;
		List<Product> products = productMapper.doGetStoreProduct(sId,start,end,ptdId,saleSort,priceSort);
		List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
		for (Product product : products) {
			Map<String, Object> productMap = new HashMap<String, Object>();
			productMap.put("pName", product.getpName());
			productMap.put("pId", product.getpId());
			productMap.put("pDescribe", product.getpDescribe());
			productMap.put("pBrowseNum", product.getpBrowseNum());
			productMap.put("pOriginalPrice", product.getpOriginalPrice());
			productMap.put("pNowPrice", product.getpNowPrice());
			String ptdId1 = product.getPtdId();
			productMap.put("ptdId", ptdId1);
			ProductTypeDetail productTypeDetail = productMapper.getProductTypeDetail(ptdId1);
			productMap.put("ptdName", productTypeDetail.getPtdName());
			/**
			 * 获取商品图片信息
			 */
			Pic productPic = userMapper.doSelectOnePicInfoByPTag(product.getpTag());
			if (productPic == null) {
				productMap.put("picName", "");
			}else {
				productMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + productPic.getpName());
			}
			
			productList.add(productMap);
		}
		/**
		 * 获取店铺内商品分类信息
		 */
		List<Product> productPtds = productMapper.doGetStoreProductBySId(sId);
		List<Map<String, Object>> productPtdList = new ArrayList<Map<String,Object>>();
		for (Product productPtd : productPtds) {
			Map<String, Object> productPtdMap = new HashMap<String, Object>();
			String ptdId1 = productPtd.getPtdId();
			/**
			 * 获取商品类别信息
			 */
			ProductTypeDetail productTypeDetail = productMapper.getProductTypeDetail(ptdId1);
			productPtdMap.put("ptdName", productTypeDetail.getPtdName());
			productPtdMap.put("ptdId", ptdId1);
			productPtdList.add(productPtdMap);
		}
		/**
		 * 获取该用户给店铺的备注名
		 */
		UserMemoName userMemoName = userMapper.doGetUserMemoNameByUid(uId, uId1);
		if (userMemoName != null) {
			map.put("umnName", userMemoName.getUmnName());
		}else {
			map.put("umnName", "");
		}
		map.put("productPtdNameList", Utils.removeDuplicate(productPtdList));
		map.put("productList", productList);
		return map;
	}

	@Override
	public Map<String, Object> doSelectStoreBylooseName(String looseName, String currentPage, String size)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int type = 1;
		if (looseName != null && looseName.length() > 0) {
			/**
			 * 根据搜索的名称获取热搜的id
			 */
			String psId = productMapper.doGetProductSearchId(looseName,type);
			if (psId == null) {
				/**
				 * 添加新的热搜信息
				 */
				productMapper.doAddProductSearch(looseName,type);
			} else {
				/**
				 * 修改热搜访问次数
				 */
				productMapper.doUpdateProductSearchNum(psId);
			}
		}
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		int start = (c-1)*s;
		int end = s;
		/**
		 * 获取店铺信息
		 */
		List<Store> stores = storeMapper.doSelectStoreBylooseName(looseName,start,end);
		List<Map<String, Object>> storeList = new ArrayList<Map<String,Object>>();
		for (Store store : stores) {
			Map<String, Object> storeMap = new HashMap<String, Object>();
			storeMap.put("sName", store.getsName());
			storeMap.put("sId", store.getsId());
			storeMap.put("sAddress", store.getsAddress());
			storeMap.put("sDescribe", store.getsDescribe());
			storeMap.put("sTel", store.getsTel());
			String pTag = store.getpTag();
			/**
			 * 获取店铺图片
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			if(pic != null){
				storeMap.put("pName",pic.getpName());
			}
			storeList.add(storeMap);
		}
		map.put("storeList", storeList);
		return map;
	}

	@Override
	public Map<String, Object> doSelectRequirementType() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RequirementType> rt = productMapper.doGetAllRequirementType();
		List<Map<String, Object>> rtList = new ArrayList<Map<String,Object>>();
		for (int i= 0; i< rt.size(); i++){
			Map<String, Object> rtMap = new HashMap<String, Object>();
			rtMap.put("rtId", rt.get(i).getRtId());
			rtMap.put("rtName", rt.get(i).getRtName());
			rtList.add(rtMap);
		}
		map.put("requirementTypeList", rtList);
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateRequirementOrderStatus(String roId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int status = 2;
		/**
		 * 修改需求订单状态
		 */
		int row1 = productMapper.doUpdateRequirementOrderStatus(status,roId, "");
		if (1 != row1) {
			map.put("errorString", "修改需求订单状态失败!");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			// 将需求订单的金额扣除手续费后转至商户账户余额中
			/**
			 * 根据需求订单主键标识获取商户店铺主键标识
			 */
			RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
			/**
			 * 获取商户需求订单的手续费率
			 */
			Store store = storeMapper.doGetStoreInfo(requirementOrder.getsId());
			double money = 0;
			try {
				money = (100 - Float.valueOf(store.getsRequirementServiceCharge())
						.floatValue()) / 100 * requirementOrder.getRoTotalPrice();
			} catch (Exception e) {
				// TODO: handle exception
				money = requirementOrder.getRoTotalPrice();
			}
			/**
			 * 修改账户余额
			 */
			int result = storeMapper.doUpdateUserWalletLeftMoney(requirementOrder.getsId(), money,
					0);
			if (1 != result) {
				map.put("errorString", "修改需求订单状态失败!");
				map.put("status", "false");
				throw new RuntimeException();
			}
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDeleteStoreApplyRequirement(String roId,String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 移除申请接单店铺
		 */
		int row1 = storeMapper.doDeleteStoreApplyRequirement(roId,sId);
		if (1 != row1) {
			map.put("errorString", "移除申请接单店铺失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
			return map;
		}
	

	@Override
	public Map<String, Object> doGetOrderPayCode() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 生成支付订单号
		 * 20位：16位时间戳+4位随机数
		 */
		String payCode = Utils.formatNowTimeLimitMillisecond()
				+ Utils.getFourRandom();
		if (Utils.isEmpty(payCode)) {
			map.put("status", "false");
			map.put("errorString", "获取支付订单号失败！");
		} else {
			map.put("status", "true");
			map.put("errorString", "");
			map.put("payCode", payCode); // 支付订单号
		}
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doGetConfirmReceipt(String poId, String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 修改订单状态为待评价
		 */
		int row = productMapper.doGetConfirmReceipt(poId,uId);
		if(1 != row){
			map.put("errorString", "修改订单状态失败!");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			// 将商品的金额扣除手续费后转至商户账户余额中
			/**
			 * 根据商品订单获取商户店铺主键标识
			 */
			ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
			/**
			 * 获取商户商品订单的手续费率
			 */
			Store store = storeMapper.doGetStoreInfo(productOrder.getsId());
			double money = 0;
			try {
				money = (100 - Float.valueOf(store.getsProductServiceCharge())
						.floatValue()) / 100 * productOrder.getPoTotalPrice();
			} catch (Exception e) {
				// TODO: handle exception
				money = productOrder.getPoTotalPrice();
			}
			/**
			 * 修改账户余额
			 */
			int result = storeMapper.doUpdateUserWalletLeftMoney(productOrder.getsId(), money,
					0);
			if (1 != result) {
				map.put("errorString", "修改订单状态失败!");
				map.put("status", "false");
				throw new RuntimeException();
			}
			map.put("status", "true");
			map.put("errorString", "");
		}
		return map;
	}
	
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doGetProductRequestRefund(String poId, String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		int status = productOrder.getPoStatus();
		if ( 1==status || 2 ==status) {
			//可以退款
			/**
			 * 修改商品订单状态
			 */
			map.put("status", "true");
			map.put("errorString", "");
			int status1 = 5;
			int row = productMapper.doUpdateProductOrderStatus(status1,poId);
			if(1 != row){
				map.put("errorString", "修改订单状态失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}else {
			//不可以退款
			map.put("errorString", "退款失败");
			map.put("status", "false");
		}
		return map;
	}
	

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doCancelProductRequestRefund(String poId, String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		String poDeliverCompany = productOrder.getPoDeliverCompany();
		String poDeliverCode = productOrder.getPoDeliverCode();
		int status = productOrder.getPoStatus();
		if (5 == status) {
			//取消退款
			if (poDeliverCompany != null &&  !"".equals(poDeliverCompany) && 
					poDeliverCode != null && !"".equals(poDeliverCode)) {
				/**
				 * 修改商品订单状态
				 */
				map.put("status", "true");
				map.put("errorString", "");
				int status1 = 2;
				int row = productMapper.doUpdateProductOrderStatus(status1,poId);
				if(1 != row){
					map.put("errorString", "取消退款失败");
					map.put("status", "false");
					throw new RuntimeException();
				} 
			}else {
				int status2 = 1;
				int row1 = productMapper.doUpdateProductOrderStatus(status2,poId);
				if(1 != row1){
					map.put("errorString", "取消退款失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}
		}else {
			//不可取消退款
			map.put("errorString", "取消退款失败");
			map.put("status", "false");
		}
		return map;
	}
	
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doStayInspectionProduct(String uId, String roId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取需求订单信息
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
		String pTag = requirementOrder.getpTag();
		String roConfirmTime = requirementOrder.getRoConfirmTime();
		/**
		 * 需求订单待验货图片
		 */
		List<Pic> pics = productMapper.getPicByPtag(pTag);
		List<Map<String, Object>> picList = new  ArrayList<Map<String,Object>>();
		for (Pic pic : pics) {
			Map<String, Object> picMaps = new HashMap<String, Object>();
			picMaps.put("url", Utils.PIC_BASE_URL + Utils.REQUIREMENT_PIC + pic.getpName());
			picList.add(picMaps);
		}
		map.put("picList", picList);
		map.put("roConfirmTime", roConfirmTime);
		return map;
	}
	
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doRorderVerification(String roId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取需求订单信息
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
		int status = requirementOrder.getRoStatus();
		if (4 == status) {
			/**
			 * 修改需求订单状态
			 */
			int status1 = 5;
			int row = productMapper.doUpdateRequirementOrderStatus(status1,roId, "");
			if(1 == row){
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("errorString", "修改订单状态失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}else {
			map.put("errorString", "确认验货失败");
			map.put("status", "false");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doGetStoreProductListFromUserFootprint(String sId, String uId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map 
			= new ArrayList<Map<String,Object>>();
		/**
		 * 获取某用户在某店铺内浏览过的商品主键标识列表
		 * t_user_footprint:P_ID
		 */
		List<UserFootprint> pIds = productMapper.doGetStoreProductListFromUserFootprint(uId, sId);
		for (int i = 0; i < pIds.size(); i++) {
			Map<String,Object> productMap = new HashMap<>();
			/**
			 * 根据pId获取商品信息
			 */
			Product product = productMapper.doGetProductByPId(pIds.get(i).getpId());
			productMap.put("pId", product.getpId()); // 商品主键标识
			productMap.put("pName", product.getpName()); // 商品名称
			productMap.put("pOriginalPrice", product.getpOriginalPrice()); // 商品原价
			productMap.put("pNowPrice", product.getpNowPrice()); // 商品现价
			productMap.put("pStockNum", product.getpStockNum()); // 商品库存
			/**
			 * 根据pTag查询图片信息
			 */
			List<Pic> pics = userMapper.doGetPicInfoByPTag(product.getpTag());
			List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
			for (Pic pic : pics) {
				if (0 == pic.getpNo()) {
					productMap.put("url",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName()); // 商品图片链接
				}
			}
			map.add(productMap);
		}
		return map;
	}
}
