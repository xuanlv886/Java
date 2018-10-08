package com.service.extra.mall.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.ProductMapper;
import com.service.extra.mall.mapper.StoreMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.model.PayStyle;
import com.service.extra.mall.model.Pic;
import com.service.extra.mall.model.Product;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.RequirementOrder;
import com.service.extra.mall.model.RequirementOrderEvaluate;
import com.service.extra.mall.model.RequirementType;
import com.service.extra.mall.model.Store;
import com.service.extra.mall.model.StoreApplyRequirement;
import com.service.extra.mall.model.StoreArrange;
import com.service.extra.mall.model.User;
import com.service.extra.mall.model.UserFootprint;
import com.service.extra.mall.model.UserRequirement;
import com.service.extra.mall.model.UserRole;
import com.service.extra.mall.model.UserToCashRecord;
import com.service.extra.mall.model.UserWallet;
import com.service.extra.mall.model.vo.PicVo;
import com.service.extra.mall.model.vo.RequirementTypeOfMoney;
import com.service.extra.mall.model.vo.UserVo;
import com.service.extra.mall.service.StoreService;
import com.service.extra.mall.service.WangYiYunService;

import util.Utils;

/**
 * 
 * desc：平台管理系统相关接口服务实现类
 * @author L
 * time:2018年5月17日
 */
@Service
public class StoreServiceImpl implements StoreService{
	@Resource private StoreMapper storeMapper;
	@Resource private UserMapper userMapper;
	@Resource private ProductMapper productMapper;
	@Resource private ManageSystemMapper manageSystemMapper;
	@Resource private WangYiYunService wangYiYunService;

	@Override
	public Map<String, Object> doStoreLogin(String uAccount, 
			String uPassword,String uPhoneId, String acId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户账号密码查询用户是否存在
		 */
		User user = userMapper.doUserLogin(uAccount,uPassword);
		if (null != user) {
			String urId = user.getUrId();
			String token = user.getuToken();
			String accid = user.getuAccid();
			/**
			 * 根据urId查询用户角色
			 */
			UserRole userRole = userMapper.doGetUserRoleByUrId(urId);
			if (userRole.getUrName().contains("商户")) {
				map.put("uId", user.getuId());
				map.put("sId", user.getsId());
				map.put("token", token);
				map.put("accid", accid);
			}else {
				map.put("status","false");
				map.put("errorString","该账号非商户账号！");
				return map;
			}
			/**
			 * 获取商户店铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(user.getsId());
			if (null != store) {
				String sChecked = String.valueOf(store.getsChecked());
				map.put("sChecked", sChecked); // 审核状态 0--未通过，1--通过
				if (1 != store.getsChecked()) {
					map.put("status","false");
					if (Utils.isEmpty(store.getuId())) { // 未完成审核
						map.put("errorString","请等待管理员完成审核！");
					} else { // 未通过审核
						map.put("errorString","未通过审核！审核意见："
									+ store.getsCheckedOpinion());
					}
					return map;
				}
			} else {
				map.put("status","false");
				map.put("errorString","账号异常，请联系管理员处理！");
				return map;
			}
			
			/**
			 * 判断是否需要对商户进行身份验证
			 * 1、设备id是否一致（换机登录）
			 * 2、所在城市是否一致（异地登录）
			 */
			if (uPhoneId.equals(user.getuPhoneId())) {
				if (acId.equals(store.getAcId())) {
					map.put("needVerification", "false"); // 是否需要身份验证
				} else {
					map.put("status", "false");
					map.put("errorString", "账号异地登录，请进行身份验证！");
					map.put("needVerification", "true"); // 是否需要身份验证
					return map;
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "账号在其它设备登录，请进行身份验证！");
				map.put("needVerification", "true"); // 是否需要身份验证
				return map;
			}
			map.put("acId", store.getAcId()); // 商户店铺所在地址的acId
			map.put("sType", store
					.getsType()); // 商户类型 0--个人商户，1--企业商户 
		}else {
			map.put("status","false");
			map.put("errorString","用户账号或密码错误！");
			return map;
		}
		map.put("errorString","");
		map.put("status","true");
		return map;
	}

	@Override
	public Map<String, Object> doGetStoreInfo(String sId,String uId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据sId获取商铺信息
		 */
		Store store = storeMapper.doGetStoreInfo(sId);
		map.put("sName", store.getsName());
		map.put("sId", store.getsId());
		map.put("sCreateTime", store.getsCreateTime());
		map.put("sLevel", store.getsLevel());
		map.put("sAddress", store.getsAddress());
		String pTag = store.getpTag();
		/**
		 * 根据pTag获取图片信息
		 */
		Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
		map.put("pId", pic.getpId());
		map.put("pName", pic.getpName());
		/**
		 * 根据sId获取店铺最新商品
		 */
		List<Product> newProducts = productMapper.doGetStoreNewProduct(sId);
		List<Map<String, Object>> newProductList = new ArrayList<Map<String,Object>>();
		for (Product newProduct : newProducts) {
			Map<String,Object> newProductMap = new HashMap<String,Object>();
			newProductMap.put("pId", newProduct.getpId());
			newProductMap.put("pName", newProduct.getpName());
			newProductMap.put("pOriginalPrice", newProduct.getpOriginalPrice());
			newProductMap.put("pNowPrice", newProduct.getpNowPrice());
			newProductMap.put("pDescribe", newProduct.getpDescribe());
			newProductMap.put("newProductPicId", newProduct.getPic().getpId());
			newProductMap.put("newProductPicName", Utils.PIC_BASE_URL + Utils.PRODUCT_PIC+newProduct.getPic().getpName());
			newProductList.add(newProductMap);
		}
		/**
		 * 获取用户该商店的历史访问记录
		 */
		List<Product> historyProducts  = productMapper.doGetStoreHistoryProduct(sId,uId);
		List<Map<String, Object>> historyProductList = new ArrayList<Map<String,Object>>();
		for (Product historyProduct : historyProducts) {
			Map<String,Object> historyProductMap = new HashMap<String,Object>();
			historyProductMap.put("pId", historyProduct.getpId());
			historyProductMap.put("pName", historyProduct.getpName());
			historyProductMap.put("pOriginalPrice", historyProduct.getpOriginalPrice());
			historyProductMap.put("pNowPrice", historyProduct.getpNowPrice());
			historyProductMap.put("pDescribe", historyProduct.getpDescribe());
			historyProductMap.put("historyProductPicId", historyProduct.getPic().getpId());
			historyProductMap.put("historyProductPicName", Utils.PRODUCT_PIC+historyProduct.getPic().getpName());
			historyProductList.add(historyProductMap);
		}
		map.put("historyProductList", historyProductList);
		map.put("newProductList", newProductList);
		return map;
	}

	@Override
	public Map<String, Object> doGetStoreSlideShow(String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据sId商铺信息
		 */
		Store store = storeMapper.doGetStoreInfo(sId);
		String pTag = store.getpTag();
		/**
		 * 根据pTag获取商铺图片
		 */
		List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
		List<Map<String, Object>> picList = new ArrayList<Map<String,Object>>();
		for (Pic pic : pics) {
			Map<String,Object> picMap = new HashMap<String,Object>();
			picMap.put("pId", pic.getpId());
			picMap.put("pName", Utils.PIC_BASE_URL + Utils.STORE_PIC + pic.getpName());
			picMap.put("pNo", pic.getpNo());
			picMap.put("pJump", pic.getpJump());
			picList.add(picMap);
		}
		map.put("picList", picList);
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doUpdateProductInfo(String pId, String pName, String pDesc, String pOriginalPrice,
			String pNowPrice, String pStockNum, String pProperty) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品信息
		 */
		Product product = productMapper.doGetProductByPId(pId);
		int stockNum = Integer.parseInt(pStockNum);
		int num = stockNum - product.getpStockNum();
		int pTotalNum = product.getpTotalNum() +num;
		/**
		 * 修改商品信息
		 */
		int row = productMapper.doUpdateProductInfo(pId,pName,pDesc,pOriginalPrice,pNowPrice,pStockNum,pTotalNum,pProperty);
		if (1 != row) {
			map.put("errorString", "修改商品信息失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doDeleteStoreProduct(String pId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 删除店铺商品
		 */
		int row = productMapper.doDelStoreProduct(pId);
		if (1 != row) {
			map.put("errorString", "删除店铺商品失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		/**
		 * 删除跟pId相关的用户足迹
		 */
		userMapper.delUserFootprintByPid(pId);
		/**
		 * 删除跟pId相关的购物车记录
		 */
		userMapper.delUserTrolleyByPid(pId);
		/**
		 * 删除跟pId相关的用户收藏
		 */
		userMapper.delUserCollectionByPid(pId);
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	
	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doAddStoreFeedBack(String fType, String fContent, String uId, String fAppType)
			throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 添加商户意见反馈
		 */
		int row = userMapper.doAddUserFeedBack(uId,fType,fAppType,fContent);
		if (1 != row) {
			map.put("errorString", "添加商户意见反馈失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> doGetAlreadyCash(String sId, String psId, String uId,String utcrAccount,String utcrMoney)
			throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取商户钱包相关数据
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
		 * 		UW_ALREADY_TO_CASH
		 */
		UserWallet userWallet = storeMapper.doGetMyWallet(sId);
		if (null != userWallet) {
			// 申请提现的金额小于或等于账户内的可用余额
			if (Double.valueOf(utcrMoney).doubleValue() <= userWallet
					.getUwLeftMoney()) {
				/**
				 * 添加提现记录
				 */
				int result = storeMapper
						.doApplyToCash( sId, uId,Double.valueOf(utcrMoney).doubleValue()
								,psId,utcrAccount);
				if (1 == result) {
					// 当前剩余的可用余额
					double uwLeftMoney = userWallet.getUwLeftMoney() 
							- Double.valueOf(utcrMoney).doubleValue();
					// 当前的申请提现的金额
					double uwApplyToCash = userWallet.getUwApplyToCash() 
							+ Double.valueOf(utcrMoney).doubleValue();
					/**
					 * 修改用户钱包可用余额、申请提现的金额
					 */
					int changeResult = storeMapper
							.changeUserWalletOfLeftMoney(uwLeftMoney,
									uwApplyToCash, sId);
					if (1 == changeResult) {
						map.put("status", "true");
						map.put("errorString", "");
					} else {
						map.put("status", "false");
						map.put("errorString", "系统繁忙，请稍后重试！");
						throw new RuntimeException();
					}
				} else {
					map.put("status", "false");
					map.put("errorString", "系统繁忙，请稍后重试！");
					throw new RuntimeException();
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "可用余额不足！");
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "该商户不存在！");
		}
		return map;
	}

	@Override
	public Map<String, Object> doGetMyUserWallet(String sId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取我的钱包
		 */
		UserWallet userWallet = storeMapper.doGetMyWallet(sId);
		map.put("uwId", userWallet.getUwId());
		map.put("uwLeftMoney", userWallet.getUwLeftMoney());//可用余额
		map.put("uwDeposit", userWallet.getUwDeposit());//冻结冻结的（已缴纳）保障金
		map.put("uwAlreadyToCash", userWallet.getUwAlreadyToCash());//已提现的金额
		map.put("sId", userWallet.getsId());
		map.put("uwApplyToCash", userWallet.getUwApplyToCash());//申请提现金额
		/**
		 * 根据sId获取店铺需求订单
		 */
		double maxOrderMoney =  productMapper.doGetMaxUserRequirementOrder(sId);
		map.put("maxOrderMoney", maxOrderMoney);//最大接单金额
		return map;
	}

//	@Override
//	public Map<String, Object> doGetPayDepositMoney(String sId, String uId, String udrMoney,String psId) throws Exception {
//		Map<String,Object> map = new HashMap<>();
//		int status = 0;
//		/**
//		 * 用户缴纳保障金
//		 */
//		int row = storeMapper.doGetPayDepositMoney(sId,uId,udrMoney,psId,status);
//		if (1 != row) {
//			map.put("errorString", "商户缴纳保障金失败");
//			map.put("status", "false");
//			throw new RuntimeException();
//		}
//		/**
//		 * 修改用户钱包保障金金额
//		 */
//		int row1 = storeMapper.doUpdateUserWalletDeposit(sId,udrMoney,status);
//		if (1 != row1) {
//			map.put("errorString", "修改用户钱包信息失败");
//			map.put("status", "false");
//			throw new RuntimeException();
//		}
//		map.put("errorString", "");
//		map.put("status", "true");
//		return map;
//	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doGetThawDepositMoney(String sId,String depositMoney,String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取商户钱包相关数据
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
		 * 		UW_ALREADY_TO_CASH
		 */
		UserWallet userWallet = storeMapper.doGetMyWallet(sId);
		int status = 2;
		if (null != userWallet) {
			// 申请解冻的保障金小于或等于冻结（已缴纳）的保障金
			if (Double.valueOf(depositMoney).doubleValue() <= userWallet
					.getUwDeposit()) {
				/**
				 * 解冻保障金
				 */
				String psId = null;
				int row = storeMapper.doGetThawDepositMoney(sId,uId,depositMoney,status);
				/**
				 * 修改用户钱包信息
				 */
				int row1 = storeMapper.doUpdateUserWalletDeposit(sId,depositMoney,status);
				if (1 == row1) {
					map.put("errorString", "");
					map.put("status", "true");
				} else {
					map.put("errorString", "系统繁忙,请稍后重试!");
					map.put("status", "false");
					throw new RuntimeException();
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "可申请解冻的保障金不足！");
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "该商户不存在！");
		}
		return map;
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	@Override
	public Map<String, Object> doStorePersonRegistered(String sTel,
			String sName,String acId, String sAddress,String sLon,
			String sLat, String sLeader, String uSex, String sLeaderIdCard,
			String sLeaderPic,String sPassword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * first:向商户表中添加商户
		 */
		String sId = Utils.randomUUID();
		Store store = new Store();
		store.setsId(sId); // 商户主键标识
		store.setsType(0); // 商户类型 0--个人商户，1--企业商户
		store.setsName(sName); // 商户名称
		store.setAcId(acId); // 商户所在城市
		store.setsLeader(sLeader); // 商户负责人
		store.setsLeaderIdCard(sLeaderIdCard); // 商户负责人身份证号
		// 商户上传了负责人手持证件照
		if (!Utils.isEmpty(sLeaderPic)) {
			String sLeaderPicTag = Utils.randomUUID();
			store.setsLeaderPic(sLeaderPicTag); // 商户负责人手持身份证照片标识
			/**
			 * 添加个人商户负责人手持身份证照片
			 */
			userMapper.doAddPic(sLeaderPic, Utils.STORE_PIC,
					sLeaderPicTag, 0, "");
		}		
		store.setsTel(sTel); // 商户联系电话
		store.setsAddress(sAddress); // 商户地址
		store.setsLon(sLon); // 商户地址所在经度
		store.setsLat(sLat); // 商户地址所在纬度
		store.setsChecked(0); // 商户是否通过审核 0--否，1--是
		int row = storeMapper.doAddStoreInfo(store);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "添加商户信息失败！");
			throw new RuntimeException();
		}
		/**
		 * second:向用户表中添加个人商户身份用户
		 */
		/**
		 * 获取用户角色
		 */
		String urId = null;
		List<UserRole> userRoles = userMapper.doGetUserRole();
		for (UserRole userRole : userRoles) {
			if ("个人商户".equals(userRole.getUrName())) {
				urId = userRole.getUrId();
			}
		}
		if (Utils.isEmpty(urId)) {
			map.put("status", "false");
			map.put("errorString", "用户角色错误！");
			throw new RuntimeException();
		}
		User user1 = userMapper.doRetrievePsw(sTel);
		if (user1 != null) {
			map.put("status", "false");
			map.put("errorString", "该手机号已注册！");
			return map;
		}
		User user = new User();
		String uId = Utils.randomUUID();
		user.setuId(uId); // 用户主键标识
		user.setUrId(urId); // 用户角色
		user.setuAccount(sTel); // 用户账号
		user.setuPassword(sPassword); // 用户密码
		user.setuNickName(sName); // 用户昵称
		user.setuSex(Integer.valueOf(uSex)); // 用户性别 0--女，1--男
		user.setuTel(sTel); // 用户电话
		user.setuTrueName(sLeader); // 用户真实姓名
		String uPhoneId = Utils.randomUUID();
		user.setuPhoneId(uPhoneId); // 用户设备标识
		user.setsId(sId); // 商户店铺主键标识
		/**
		 * 个人商户注册网易云
		 */
		try {
			String accid = "";
			String token = "";
			String name = "";
			String desc = "";
			String uAccid = uId.replace("-", "");
			String yunXinResult = (String) wangYiYunService.create(uAccid, sName);
			JSONObject jsonObject = JSONObject.parseObject(yunXinResult);
			int code = jsonObject.getInteger("code");
			if (code == 200) {
				JSONObject info = jsonObject.getJSONObject("info");
				accid = info.getString("accid");
				token = info.getString("token");
				name = info.getString("name");
				user.setuAccid(accid);
				user.setuToken(token);

				/**
				 * 插入用户表
				 */
				int row1 = userMapper.doAddCustomer(user);
				if (1 == row1) {
					map.put("status", "true");
					map.put("errorString", "");
					map.put("accid", accid);
					map.put("token", token);
					map.put("uId", uId);
				} else {
					map.put("status", "false");
					map.put("errorString", "添加用户信息失败！");
					throw new RuntimeException();
				}
				/**
				 * third:向用户钱包表中添加记录
				 */
				int row2 = userMapper.doAddUserWallet(sId);
				if (1 != row2) {
					map.put("status", "false");
					map.put("errorString", "配置商户钱包失败！");
					throw new RuntimeException();
				}
				/**
				 * 更新网易云用户名片
				 */
				UserVo userVo = new UserVo();
				userVo.setuId(uId);
				userVo.setsId(sId);
				userVo.setsType("0");
				String updateResult = (String) wangYiYunService.updateUinfo(uAccid, sName, Utils.ObjectToJson(userVo));
				JSONObject jsonObject1 = JSONObject.parseObject(updateResult);
			    int code1 = jsonObject1.getInteger("code");
			    if (code1 != 200) {
			    	map.put("status", "false");
		   			map.put("errorString", "用户注册失败");
		   			throw new RuntimeException();
				}
			} else {
				desc = jsonObject.getString("desc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("uPhoneId", uPhoneId); // 商户设备标识	
		return map;
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	@Override
	public Map<String, Object> doStoreCompanyRegistered(String sTel,
			String sName,String acId, String sAddress,
			String sLon, String sLat, String sLeader, String uSex,
			String sLeaderIdCard,String sLeaderPic, 
			String sLegal, String sLegalIdCard, String sLegalPic,
			String sBussinessLicensePic, String sPassword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * first:向商户表中添加商户
		 */
		String sId = Utils.randomUUID();
		Store store = new Store();
		store.setsId(sId); // 商户主键标识
		store.setsType(1); // 商户类型 0--个人商户，1--企业商户
		store.setsName(sName); // 商户名称
		store.setAcId(acId); // 商户所在城市
		store.setsLeader(sLeader); // 商户负责人
		store.setsLeaderIdCard(sLeaderIdCard); // 商户负责人身份证号
		// 商户上传了负责人手持证件照
		if (!Utils.isEmpty(sLeaderPic)) {
			String sLeaderPicTag = Utils.randomUUID();
			store.setsLeaderPic(sLeaderPicTag); // 商户负责人手持身份证照片标识
			/**
			 * 添加企业商户负责人手持身份证照片
			 */
			userMapper.doAddPic(sLeaderPic, Utils.STORE_PIC,
					sLeaderPicTag, 0, "");
		}
		store.setsLegal(sLegal); // 商户法人
		store.setsLegalIdCard(sLegalIdCard); // 商户法人身份证号
		// 商户上传了法人手持证件照
		if (!Utils.isEmpty(sLegalPic)) {
			String sLegalPicTag = Utils.randomUUID();
			store.setsLegalPic(sLegalPicTag); // 商户法人手持身份证照片标识
			/**
			 * 添加企业商户法人手持身份证照片
			 */
			userMapper.doAddPic(sLegalPic, Utils.STORE_PIC,
					sLegalPicTag, 0, "");
		}		
		// 商户上传了营业执照
		if (!Utils.isEmpty(sBussinessLicensePic)) {
			String sBussinessLicensePicTag = Utils.randomUUID();
			store.setsBuinessLicensePic(sBussinessLicensePicTag); // 商户营业执照照片标识
			/**
			 * 添加企业商户营业执照照片
			 */
			userMapper.doAddPic(sBussinessLicensePic, Utils.STORE_PIC,
					sBussinessLicensePicTag, 0, "");
		}		
		store.setsTel(sTel); // 商户联系电话
		store.setsAddress(sAddress); // 商户地址
		store.setsLon(sLon); // 商户地址所在经度
		store.setsLat(sLat); // 商户地址所在纬度
		store.setsChecked(0); // 商户是否通过审核 0--否，1--是
		int row = storeMapper.doAddStoreInfo(store);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "添加商户信息失败！");
			throw new RuntimeException();
		}
		/**
		 * second:向用户表中添加企业商户身份用户
		 */
		/**
		 * 获取用户角色
		 */
		String urId = null;
		List<UserRole> userRoles = userMapper.doGetUserRole();
		for (UserRole userRole : userRoles) {
			if ("企业商户".equals(userRole.getUrName())) {
				urId = userRole.getUrId();
			}
		}
		if (Utils.isEmpty(urId)) {
			map.put("status", "false");
			map.put("errorString", "用户角色错误！");
			throw new RuntimeException();
		}
		User user1 = userMapper.doRetrievePsw(sTel);
		if (user1 != null) {
			map.put("status", "false");
			map.put("errorString", "该手机号已注册!");
			return map;
		}
		User user = new User();
		String uId = Utils.randomUUID();
		user.setuId(uId); // 用户主键标识
		user.setUrId(urId); // 用户角色
		user.setuAccount(sTel); // 用户账号
		user.setuPassword(sPassword); // 用户密码
		user.setuNickName(sName); // 用户昵称
		user.setuSex(Integer.valueOf(uSex)); // 用户性别 0--女，1--男
		user.setuTel(sTel); // 用户电话
		user.setuTrueName(sLeader); // 用户真实姓名
		String uPhoneId = Utils.randomUUID();
		user.setuPhoneId(uPhoneId); // 用户设备标识
		user.setsId(sId); // 商户店铺主键标识
		/**
		 * 企业用户注册网易云
		 */
		try {
			String accid = "";
			String token = "";
			String name = "";
			String desc = "";
			String uAccid = uId.replace("-", "");
			String yunXinResult = (String) wangYiYunService.create(uAccid, sName);
			JSONObject jsonObject = JSONObject.parseObject(yunXinResult);
			int code = jsonObject.getInteger("code");
			if (code == 200) {
				JSONObject info = jsonObject.getJSONObject("info");
				accid = info.getString("accid");
				token = info.getString("token");
				name = info.getString("name");
				user.setuAccid(accid);
				user.setuToken(token);

				/**
				 * 插入用户表
				 */
				int row1 = userMapper.doAddCustomer(user);
				if (1 == row1) {
					map.put("accid", accid);
					map.put("token", token);
					map.put("uId", uId);
					map.put("status", "true");
					map.put("errorString", "");
				} else {
					map.put("status", "false");
					map.put("errorString", "添加用户信息失败！");
					throw new RuntimeException();
				}
				/**
				 * third:向用户钱包表中添加记录
				 */
				int row2 = userMapper.doAddUserWallet(sId);
				if (1 != row2) {
					map.put("status", "false");
					map.put("errorString", "配置商户钱包失败！");
					throw new RuntimeException();
				}
				/**
				 * 更新网易云用户名片
				 */
				UserVo userVo = new UserVo();
				userVo.setuId(uId);
				userVo.setsId(sId);
				userVo.setsType("1");
				String updateResult = (String) wangYiYunService.updateUinfo(uAccid, sName, Utils.ObjectToJson(userVo));
				JSONObject jsonObject1 = JSONObject.parseObject(updateResult);
			    int code1 = jsonObject1.getInteger("code");
			    if (code1 != 200) {
			    	map.put("status", "false");
		   			map.put("errorString", "商户注册失败");
		   			throw new RuntimeException();
				}
			} else {
				desc = jsonObject.getString("desc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("uPhoneId", uPhoneId); // 商户设备标识
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doAddStoreArrange(String sId, String saContent, String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 添加商户安排
		 */
		int row = storeMapper.doAddStoreArrange(sId,saContent,uId);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "添加商户安排失败");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doGetStoreApplicationRequirement(String sId, String quotedPrice, String urId)
			throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取需求订单信息
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(urId);
		String roId = requirementOrder.getRoId();
		// 判断要申请接单的需求的金额是否超过限制接单金额
		Store store = storeMapper.doGetStoreInfo(sId);
		if (null != store) {
			if (!Utils.isEmpty(quotedPrice)) {
				if (Double.valueOf(quotedPrice).doubleValue() <= Double
						.valueOf(store.getsOrderLimint()).doubleValue()) {
					/**
					 * 插入商户申请接单表
					 */
					int row = storeMapper.doAddStoreApplyRequirement(roId,sId,quotedPrice);
					if (1 == row) {
						/**
						 * 修改用户需求订单状态
						 */
						int status = 0;
						int row1 = userMapper.doUpdateRequirementOrderStatus(status, roId);
						if (1 != row1) {
							map.put("status", "false");
							map.put("errorString", "修改用户需求订单状态失败!");
							throw new RuntimeException();
						}
					} else {
						map.put("status", "false");
						map.put("errorString", "添加商户申请接单失败!");
						throw new RuntimeException();
					}
				} else {
					map.put("status", "false");
					map.put("errorString", "需求报价超过您的接单限额!");
					return map;
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "需求报价不能为空!");
				throw new RuntimeException();
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "商户不存在!");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doDelStoreArrange(String saId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 删除店铺安排
		 */
		int row = storeMapper.doDelStoreArrange(saId);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "删除店铺安排失败！");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public Map<String, Object> doGetMyArranges(String sId, String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取我的店铺安排
		 */
		List<StoreArrange> storeArranges = storeMapper.doGetMyArranges(sId,uId);
		List<Map<String, Object>> storeArrangeList = new ArrayList<Map<String,Object>>();
		for (StoreArrange storeArrange : storeArranges) {
			Map<String,Object> storeArrangeMap = new HashMap<>();
			storeArrangeMap.put("saId", storeArrange.getSaId());
			storeArrangeMap.put("saContent", storeArrange.getSaContent());
			storeArrangeMap.put("sId", storeArrange.getsId());
			storeArrangeMap.put("uId", storeArrange.getuId());
			storeArrangeMap.put("saCreateTime", storeArrange.getSaCreateTime());
			storeArrangeList.add(storeArrangeMap);
		}
		map.put("storeArrangeList", storeArrangeList);
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doUpdateMyArrange(String saId, String saContent) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 商户修改我的安排
		 */
		int result = storeMapper.doUpdateMyArrange(saId, saContent);
		if (1 == result) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "修改安排失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doUpdateStoreInfo(String uId, String sId, String sName, String sAnnouncement,
			String sTel) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取商户信息
		 */
		Store store = storeMapper.doGetStoreInfo(sId);
		store.setsName(sName);
		store.setsTel(sTel);
		store.setsDescribe(sAnnouncement);
		/**
		 * 修改商户信息
		 */
		int row = storeMapper.doUpdateStoreInfo(store);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "修改店铺信息失败");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}

	@Override
	public List<Map<String, Object>> doGetAllRequirement(String uId, String sId, String hotSort,
			String timeSort,String priceSort,String currentPage,String size) throws Exception {
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		int start = (c-1)*s;
		int end = s;
		/**
		 * 获取没有指定店铺接单的所有的用户需求
		 * t_user_requirement:UR_ID、RT_ID、UR_TITLE、UR_CONTENT、UR_OFFER_PRICE、
		 * 		UR_CREATE_TIME、UR_BROWSER_NUM
		 * t_requirement_order：RO_ID、RO_STATUS
		 * t_requirement_type：RT_NAME
		 */
		List<UserRequirement> userRequirements = productMapper
				.doGetAllRequirement(hotSort,timeSort,priceSort,start,end,sId);
		for (UserRequirement userRequirement : userRequirements) {
			Map<String,Object> userRequirementMap = new HashMap<String,Object>();
			userRequirementMap.put("urId", 
					userRequirement.getUrId()); // 用户需求主键标识
			userRequirementMap.put("rtId", 
					userRequirement.getRtId()); // 需求类型主键标识
			userRequirementMap.put("rtName", userRequirement.getRequirementType()
					.getRtName()); // 需求类型名称
			userRequirementMap.put("urTitle", 
					userRequirement.getUrTitle()); // 需求标题
			userRequirementMap.put("urContent", 
					userRequirement.getUrContent()); // 需求描述内容
			userRequirementMap.put("urOfferType", 
					userRequirement.getUrOfferType()); // 需求报价类型
			userRequirementMap.put("urOfferPrice",
					userRequirement.getUrOfferPrice()); // 需求价格
			userRequirementMap.put("urCreateTime",
					userRequirement.getUrCreateTime()); // 需求发布时间
			userRequirementMap.put("urBrowserNum",
					userRequirement.getUrBrowserNum()); // 被浏览次数
			userRequirementMap.put("urGetAddress",
					userRequirement.getUrGetAddress()); // 收货地址
			userRequirementMap.put("roId",
					userRequirement.getRequirementOrder()
					.getRoId()); // 需求订单主键标识
			userRequirementMap.put("roStatus",
					userRequirement.getRequirementOrder()
					.getRoStatus()); // 需求订单状态
			
			/**
			 * 申请接单的店铺数量
			 */
			String roId = userRequirement.getRequirementOrder()
					.getRoId();
			int num = productMapper.getStoreApplyRequirementNum(roId);
			userRequirementMap.put("num",num); //申请接此单的商铺数量
			/**
			 * 获取申请接单店铺信息
			 */
			List<StoreApplyRequirement> storeApplyRequirements = productMapper
					.doGetStoreApplyRequirementByRoId(roId);
			int applyStatus = 0;
			if (1 > storeApplyRequirements.size()) {
				userRequirementMap.put("applyStatus",applyStatus);
			} else {
				for (StoreApplyRequirement storeApplyRequirement : storeApplyRequirements) {
					if (sId.equals(storeApplyRequirement.getsId())) {
						userRequirementMap.put("applyStatus",1);
					} else {
						userRequirementMap.put("applyStatus",applyStatus);
					}
				}
			}
			map.add(userRequirementMap);

		}
		return map;
	}

	@Override
	public Map<String, Object> doCancelStoreRequirement(String sId, String roId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 删除商户申请接单表
		 */
		int row = storeMapper.doCancelStoreRequirement(sId,roId);
		if (1 != row) {
			map.put("status", "false");
			map.put("errorString", "删除店铺安排失败");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}


	@Override
	public Map<String, Object> doGetAllAlreadyCashRecord(String sId, String uId, String size, String pages) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取商户提现记录总数
		 */
		int totalCount = storeMapper.getApplyToCashListCount(sId);
		map.put("totalCount", totalCount); // 记录总数
		int s = Integer.valueOf(size);//每页记录数
		int e = Integer.valueOf(pages);//页数
		int i = (e-1)*s;
		int j = s;
		/**
		 * 获取所有的提现记录
		 */
		List<UserToCashRecord> userToCashRecords = storeMapper
				.doGetAllAlreadyCashRecord(sId,uId,i,j);
		List<Map<String, Object>> userToCashRecordList = new ArrayList<Map<String,Object>>();
		for (UserToCashRecord userToCashRecord : userToCashRecords) {
			Map<String,Object> userToCashRecordMap = new HashMap<>();
			userToCashRecordMap.put("utcrId", userToCashRecord.getUtcrId());// 主键标识
			String psId = userToCashRecord.getPsId();
			PayStyle payStyle = storeMapper.getPayStyleByPsId(psId);
			userToCashRecordMap.put("psName", payStyle.getPsName());// 提现方式名称
			userToCashRecordMap.put("utcrMoney", userToCashRecord.getUtcrMoney());// 金额
			userToCashRecordMap.put("utcrAccount", userToCashRecord.getUtcrAccount());// 提现账号
			userToCashRecordMap.put("utcrStatus", userToCashRecord.getUtcrStatus());// 提现状态 0--申请提现，1--已转账
			userToCashRecordMap.put("utcrCreateTime", userToCashRecord.getUtcrCreateTime());// 时间
			userToCashRecordList.add(userToCashRecordMap);
		}
		map.put("userToCashRecordList", userToCashRecordList);
		return map;
	}

	@Override
	public Map<String, Object> doGetStoreProductOrderDetail(String poId)
			throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取商品订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		map.put("poId", productOrder.getPoId());
		map.put("psId", productOrder.getPsId());
		map.put("uId", productOrder.getuId());
		/**
		 * 获取网易云accid
		 */
		User user = userMapper.doGetUserInfoByUId(productOrder.getuId());
		map.put("accId", user.getuAccid());
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
		map.put("poDeliverCompany", productOrder.getPoDeliverCompany());
		map.put("poDeliverCode", productOrder.getPoDeliverCode());
		map.put("poDeliverAddress", productOrder.getPoDeliverAddress());
		String sId =  productOrder.getsId();
		map.put("sId", sId);
		/**
		 * 获取店铺信息
		 */
		Store store = storeMapper.doGetStoreInfo(sId);
		map.put("sName", store.getsName());
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
			List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
			for (Pic pic : pics) {
				if (0 == pic.getpNo()) {
					detailMap.put("picName", Utils.PIC_BASE_URL + Utils.PRODUCT_PIC+pic.getpName());
					detailMap.put("picId", pic.getpId());
				}
			}
			detailList.add(detailMap);
		}
		map.put("orderDetails", detailList);
		
		return map;
	}

	@Override
	public Map<String, Object> doGetStoreProductOrder(String sId, String currentPage,String size,String status) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		/**
		 * 获取我的订单
		 */
		List<ProductOrder> productOrders = productMapper.doGetStoreProductOrder(sId,status,(c-1)*s,s);
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
			List<ProductOrderDetail> productOrderDetail = productMapper.doGetProductOrderDetailByPoId(poId);
			productOrderMap.put("podId", productOrderDetail.get(0).getPodId());
			productOrderMap.put("podPrice", productOrderDetail.get(0).getPodPrice());
			productOrderMap.put("podProperty", productOrderDetail.get(0).getPodProperty());
			productOrderMap.put("podNum", productOrderDetail.get(0).getPodNum());
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
			List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
			for (Pic pic : pics) {
				if (0 == pic.getpNo()) {
					productOrderMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + pic.getpName());
					productOrderMap.put("picId", pic.getpId());
				}
			}
			/**
			 * 根据sId获取商铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			productOrderMap.put("sName", store.getsName());
			productOrderList.add(productOrderMap);
		}
		map.put("productOrderList", productOrderList);
		return map;
	}

	@Override
	public Map<String, Object> doGetStoreRequirement(String sId,String roStatus,String currentPage,String size) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int c = Integer.parseInt(currentPage);
		int s = Integer.parseInt(size);
		List<UserRequirement> userRequirements = productMapper.doGetStoreRequirement(sId,roStatus,(c-1)*s,s);
		List<Map<String, Object>> userRequirementList = new ArrayList<Map<String,Object>>();
		for (UserRequirement userRequirement : userRequirements) {
			Map<String,Object> userRequirementMap = new HashMap<String,Object>();
			String urId = userRequirement.getUrId();
			userRequirementMap.put("urId", urId);
			userRequirementMap.put("uId", userRequirement.getuId());
			userRequirementMap.put("rtId", userRequirement.getRtId());
			RequirementType type = productMapper.doGetRequirementType(
					userRequirement.getRtId());
			userRequirementMap.put("rtName", type.getRtName());
			userRequirementMap.put("urTitle", userRequirement.getUrTitle());
			userRequirementMap.put("urContent", userRequirement.getUrContent());
			userRequirementMap.put("urOfferType", userRequirement.getUrOfferType());
			userRequirementMap.put("urOfferPrice", userRequirement.getUrOfferPrice());
			userRequirementMap.put("urTrueName", userRequirement.getUrTrueName());
			userRequirementMap.put("urAddress", userRequirement.getUrAddress());
			userRequirementMap.put("urCreateTime", userRequirement.getUrCreateTime());
			userRequirementMap.put("sId", userRequirement.getsId());
			userRequirementMap.put("urBrowserNum", userRequirement.getUrBrowserNum());
			userRequirementMap.put("urGetAddress", userRequirement.getUrGetAddress());
			/**
			 * 获取需求订单信息
			 */
			RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(urId);
			String status = String.valueOf(requirementOrder.getRoStatus());
			userRequirementMap.put("roStatus", status);
			userRequirementMap.put("roConfirmTime", requirementOrder.getRoConfirmTime());
			userRequirementMap.put("roOverTime", requirementOrder.getRoOverTime());
			userRequirementMap.put("roGetTime", requirementOrder.getRoGetTime());
			userRequirementMap.put("roVerificationTime", requirementOrder.getRoVerificationTime());
			userRequirementList.add(userRequirementMap);
		}
		/**
		 * 获取用户未确认的店铺申请信息
		 */
		List<StoreApplyRequirement> storeApplyRequirements = productMapper.
				doGetStoreApplyRequirement(sId,(c-1)*s,c*s);
		List<Map<String, Object>> storeApplyRequirementList = new ArrayList<Map<String,Object>>();
		for (StoreApplyRequirement storeApplyRequirement : storeApplyRequirements) {
			Map<String,Object> storeApplyRequirementMap = new HashMap<String,Object>();
			String roId = storeApplyRequirement.getRoId();
			/**
			 * 获取用户需求订单表信息
			 */
			RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
			String status = String.valueOf(requirementOrder.getRoStatus());
			
			String urId = requirementOrder.getUrId();
			/**
			 * 申请接单的店铺数量
			 */
			int num = productMapper.getStoreApplyRequirementNum(roId);
			/**
			 * 获取用户需求信息
			 */
			UserRequirement userRequirement = productMapper.doGetUserRequirementDetail(urId);
			storeApplyRequirementMap.put("num", num);
			storeApplyRequirementMap.put("urId", userRequirement.getUrId());
			storeApplyRequirementMap.put("uId", userRequirement.getuId());
			storeApplyRequirementMap.put("rtId", userRequirement.getRtId());
			RequirementType type = productMapper.doGetRequirementType(
					userRequirement.getRtId());
			storeApplyRequirementMap.put("rtName", type.getRtName());
			storeApplyRequirementMap.put("urTitle", userRequirement.getUrTitle());
			storeApplyRequirementMap.put("urContent", userRequirement.getUrContent());
			storeApplyRequirementMap.put("urOfferType", userRequirement.getUrOfferType());
			storeApplyRequirementMap.put("urOfferPrice", userRequirement.getUrOfferPrice());
			storeApplyRequirementMap.put("urTrueName", userRequirement.getUrTrueName());
			storeApplyRequirementMap.put("urAddress", userRequirement.getUrAddress());
			storeApplyRequirementMap.put("urCreateTime", userRequirement.getUrCreateTime());
			storeApplyRequirementMap.put("sId", sId);
			storeApplyRequirementMap.put("urBrowserNum", userRequirement.getUrBrowserNum());
			storeApplyRequirementMap.put("urGetAddress", userRequirement.getUrGetAddress());
			storeApplyRequirementMap.put("roStatus", status);
			storeApplyRequirementList.add(storeApplyRequirementMap);
		}
		/**
		 * 获取用户待接单需求订单信息
		 */
		List<RequirementOrder> requirementOrders = productMapper.doGetRequirementOrderByRoStatus(roStatus,(c-1)*s,c*s);
		List<Map<String, Object>> requirementOrderList = new ArrayList<Map<String,Object>>();
		for (RequirementOrder requirementOrder : requirementOrders) {
			Map<String,Object> requirementOrderMap = new HashMap<String,Object>();
			String urId = requirementOrder.getUrId();
			String status = String.valueOf(requirementOrder.getRoStatus());
			
			/**
			 * 获取用户需求信息
			 */
			UserRequirement userRequirement = productMapper.doGetUserRequirementDetail(urId);
			requirementOrderMap.put("urId", userRequirement.getUrId());
			requirementOrderMap.put("uId", userRequirement.getuId());
			requirementOrderMap.put("rtId", userRequirement.getRtId());
			RequirementType type = productMapper.doGetRequirementType(
					userRequirement.getRtId());
			requirementOrderMap.put("rtName", type.getRtName());
			requirementOrderMap.put("urTitle", userRequirement.getUrTitle());
			requirementOrderMap.put("urContent", userRequirement.getUrContent());
			requirementOrderMap.put("urOfferType", userRequirement.getUrOfferType());
			requirementOrderMap.put("urOfferPrice", userRequirement.getUrOfferPrice());
			requirementOrderMap.put("urTrueName", userRequirement.getUrTrueName());
			requirementOrderMap.put("urAddress", userRequirement.getUrAddress());
			requirementOrderMap.put("urCreateTime", userRequirement.getUrCreateTime());
			requirementOrderMap.put("sId", sId);
			requirementOrderMap.put("urBrowserNum", userRequirement.getUrBrowserNum());
			requirementOrderMap.put("urGetAddress", userRequirement.getUrGetAddress());
			requirementOrderMap.put("roStatus", status);
			requirementOrderList.add(requirementOrderMap);
		}
		map.put("storeApplyRequirementList", storeApplyRequirementList);
		map.put("requirementOrderList", requirementOrderList);
		map.put("userRequirementList", userRequirementList);
		return map;
	}

	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doGetStoreRequirementDetail(String urId)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户需求详情
		 */
		UserRequirement userRequirement = productMapper.doGetUserRequirementDetail(urId);
		map.put("urId", userRequirement.getUrId());
		map.put("uId", userRequirement.getuId());
		String rtId = userRequirement.getRtId();
		/**
		 * 获取需求类别名称
		 */
		RequirementType requirementType = productMapper.doGetRequirementType(rtId);
		map.put("rtId", rtId);
		map.put("rtName", requirementType.getRtName());
		map.put("urTitle", userRequirement.getUrTitle());
		map.put("urContent", userRequirement.getUrContent());
		map.put("urOfferType", userRequirement.getUrOfferType());
		map.put("urOfferPrice", userRequirement.getUrOfferPrice());
		map.put("urTrueName", userRequirement.getUrTrueName());
		map.put("urAddress", userRequirement.getUrAddress());
		map.put("urCreateTime", userRequirement.getUrCreateTime());
		map.put("sId", userRequirement.getsId());
		map.put("urTel", userRequirement.getUrTel());
		map.put("urBrowserNum", userRequirement.getUrBrowserNum());
		map.put("urGetAddress", userRequirement.getUrGetAddress());
		/**
		 * 根据urId查询用户需求订单
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrder(urId);
		map.put("roId", requirementOrder.getRoId());
		map.put("urId", requirementOrder.getUrId());
		map.put("roCreateTime", requirementOrder.getRoCreateTime());
		map.put("roStatus", requirementOrder.getRoStatus());
		map.put("pTag", requirementOrder.getpTag());
		map.put("roTotalPrice", requirementOrder.getRoTotalPrice());
		map.put("roOrderId", requirementOrder.getRoOrderId());
		map.put("roConfirmTime", requirementOrder.getRoConfirmTime());
		map.put("roOverTime", requirementOrder.getRoOverTime());
		map.put("roGetTime", requirementOrder.getRoGetTime());
		map.put("roVerificationTime", requirementOrder.getRoVerificationTime());
		/**
		 * 查询用户网易云accid
		 */
		User user = userMapper.doGetUserInfoByUId(userRequirement.getuId());
		map.put("accId", user.getuAccid());
		/**
		 * 修改需求被浏览次数
		 */
		productMapper.doUpdateRequirementBrowseNum(urId);
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
	public Map<String, Object> doPersonStoreMainInterface(String uId,String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (null != user) {
			/**
			 * 根据pTag获取用户头像
			 */
			List<Pic> pics = userMapper.doGetPicInfoByPTag(user.getpTag());
			if (null != pics && 0 < pics.size()) {
				for (Pic pic : pics) {
					if (0 == pic.getpNo()) {
						map.put("url",Utils.PIC_BASE_URL + Utils.PROFILE_PIC 
								+ pic.getpName() ); // 用户头像
					}
				}
			} else {
				map.put("url", "");  // 用户头像
			}
			map.put("uNickName", user.getuNickName());  // 用户昵称
			/**
			 * 获取商铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			map.put("sName", store.getsName()); // 商户店铺名称
			/**
			 * 获取个人商户需求订单状态列表
			 * t_requirement_order:RO_STATUS
			 */
			List<RequirementOrder> orders = manageSystemMapper
					.doMsGetPersonalIndexInfo(sId);
			int completeOrderNum = 0;
			int unConfirmOrderNum = 0;
			int doingOrderNum = 0;
			for (int i = 0; i < orders.size(); i++) {
				// RequirementOrderStatus
				switch (orders.get(i).getRoStatus()) {
				case 0: // 待确认
					unConfirmOrderNum += 1;
					break;
				case 1: // 进行中
					doingOrderNum += 1;
					break;
				case 2: // 已完成
					completeOrderNum += 1;
					break;
				case 3: // 取货中--进行中
					doingOrderNum += 1;
					break;
				case 4: // 待验货--进行中
					doingOrderNum += 1;
					break;
				case 5: // 送货中--进行中
					doingOrderNum += 1;
					break;
				case 6: // 已评价--已完成
					completeOrderNum += 1;
					break;
				default:
					break;
				}
			}
			map.put("completeOrderNum",
					completeOrderNum);// 已完成的需求订单数量
			map.put("unConfirmOrderNum",
					unConfirmOrderNum);// 待确认的需求订单数量
			map.put("doingOrderNum",
					doingOrderNum);// 进行中的需求订单数量
			/**
			 * 获取需求订单昨日成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders2 = manageSystemMapper
					.msGetYestodyRequirementOrderMoney(sId);
			double yestodayCompleteMoney = 0;
			for (int i = 0; i < orders2.size(); i++) {
				yestodayCompleteMoney += orders2.get(i).getRoTotalPrice();
			}
			map.put("yestodayCompleteMoney", 
					yestodayCompleteMoney); // 昨日成交额
			/**
			 * 获取需求订单今日成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders3 = manageSystemMapper
					.msGetTodayRequirementOrderMoney(sId);
			double todayCompleteMoney = 0;
			for (int i = 0; i < orders3.size(); i++) {
				todayCompleteMoney += orders3.get(i).getRoTotalPrice();
			}
			map.put("todayCompleteMoney", 
					todayCompleteMoney); // 今日成交额
			/**
			 * 获取需求订单总成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders4 = manageSystemMapper
					.msGetRequirementOrderTotalMoney(sId);
			double totalCompleteMoney = 0;
			for (int i = 0; i < orders4.size(); i++) {
				totalCompleteMoney += orders4.get(i).getRoTotalPrice();
			}
			map.put("totalCompleteMoney", 
					totalCompleteMoney); // 总成交额
			/**
			 * 获取个人商户我的安排数量
			 */
			int myArrangeNum = manageSystemMapper
					.msGetStoreArrangeNum(sId);
			map.put("myArrangeNum", myArrangeNum); // 我的安排数量
			/**
			 * 获取个人商户我的评级
			 * t_store:S_LEVEL
			 */
			Store store1 = manageSystemMapper.msGetStoreLevel(sId);
			if (null != store1) {
				map.put("sLevel", store1.getsLevel()); // 商户评级
			} else {
				map.put("sLevel", 0); // 商户评级
			}
			/**
			 * 获取需求类别列表
			 * t_requirement_type:RT_ID、RT_NAME
			 */
			List<RequirementType> types = manageSystemMapper
					.msGetRequirementTypeList();
			List<RequirementTypeOfMoney> list 
				= new ArrayList<RequirementTypeOfMoney>(); // 数组序列  
			for (int i = 0; i < types.size(); i++) {
				/**
				 * 获取本商户该需求类别下的总成交额
				 */
				double totalMoney = manageSystemMapper
						.msGetTotalMoneyByRtIdAndSId(
								types.get(i).getRtId(), sId);
				/**
				 * 获取某商户特定需求类别的总接单数量
				 */
				int totalNum = manageSystemMapper
						.msGetTotalNumByRtIdAndSId(
								types.get(i).getRtId(), sId);
				RequirementTypeOfMoney vo 
					= new RequirementTypeOfMoney(types.get(i).getRtId(),
							types.get(i).getRtName(), totalMoney, totalNum);
				list.add(vo);
			}	
			if (0 < list.size()) {
				Collections.sort(list);
				map.put("firstRtNameOfMoney", "需求类别：" + list.get(0)
						.getRtName() + "(合计：" + list.get(0)
						.getTotalMoney() + ")"); // 成交额贡献最多的需求类别和具体金额
			} else {
				map.put("firstRtNameOfMoney", "暂无"); // 成交额贡献最多的需求类别和具体金额
			}
			
			/**
			 * 获取某商户需求单价No.1数据
			 * t_requirement_order：RO_TOTAL_PRICE
			 * t_user_requirement：UR_TITLE
			 * t_requirement_type：RT_NAME
			 */
			RequirementOrder order = manageSystemMapper
					.msGetFirstRoInfoBySid(sId);
			if (null != order) {
				map.put("firstOfRequirementPrice",
						"需求类别：" + order.getRequirementType().getRtName()
						+ "   标题：" + order.getUserRequirement().getUrTitle()
						+ "   单价：" + order.getRoTotalPrice()); // 商户需求单价No.1
			} else {
				map.put("firstOfRequirementPrice", "暂无"); // 商户需求单价No.1
			}
			if (0 < list.size()) {
				Collections.sort(list, new Comparator<RequirementTypeOfMoney>() {

					@Override
					public int compare(RequirementTypeOfMoney o1, RequirementTypeOfMoney o2) {
						// TODO Auto-generated method stub
						return o2.getTotalNum()-o1.getTotalNum(); 
					}
				});
				map.put("firstRoNum", "需求类别：" + list.get(0)
						.getRtName() + "(共" + list.get(0).getTotalNum() 
						+ "单，合计：" + list.get(0)
						.getTotalMoney() + ")"); // 接单数量最多的需求类别
			} else {
				map.put("firstRoNum", "暂无"); // 接单数量最多的需求类别
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "账号不存在！");
			return map;
		}
		map.put("status", "true");
		map.put("errorString", "");
		return map;
	}

	@Override
	public Map<String, Object> doCompanyStoreMainInterface(String uId,String sId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (null != user) {
			/**
			 * 根据pTag获取用户头像
			 */
			List<Pic> pics = userMapper.doGetPicInfoByPTag(user.getpTag());
			if (null != pics && 0 < pics.size()) {
				for (Pic pic : pics) {
					if (0 == pic.getpNo()) {
						map.put("url",Utils.PIC_BASE_URL + Utils.PROFILE_PIC 
								+ pic.getpName() ); // 用户头像
					}
				}
			} else {
				map.put("url", "");  // 用户头像
			}
			map.put("uNickName", user.getuNickName());  // 用户昵称
			/**
			 * 获取商铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			map.put("sName", store.getsName()); // 商户店铺名称
			map.put("sLevel", store.getsLevel()); // 商户评级
			//------需求---------
			/**
			 * 获取企业商户需求订单状态列表
			 * t_requirement_order:RO_STATUS
			 */
			List<RequirementOrder> orders = manageSystemMapper
					.doMsGetPersonalIndexInfo(sId);
			int completeOrderNum = 0;
			int unConfirmOrderNum = 0;
			int doingOrderNum = 0;
			for (int i = 0; i < orders.size(); i++) {
				// RequirementOrderStatus
				switch (orders.get(i).getRoStatus()) {
				case 0: // 待确认
					unConfirmOrderNum += 1;
					break;
				case 1: // 进行中
					doingOrderNum += 1;
					break;
				case 2: // 已完成
					completeOrderNum += 1;
					break;
				case 3: // 取货中--进行中
					doingOrderNum += 1;
					break;
				case 4: // 待验货--进行中
					doingOrderNum += 1;
					break;
				case 5: // 送货中--进行中
					doingOrderNum += 1;
					break;
				case 6: // 已评价--已完成
					completeOrderNum += 1;
					break;
				default:
					break;
				}
			}
			map.put("completeOrderNum",
					completeOrderNum);// 已完成的需求订单数量
			map.put("unConfirmOrderNum",
					unConfirmOrderNum);// 待确认的需求订单数量
			map.put("doingOrderNum",
					doingOrderNum);// 进行中的需求订单数量
			/**
			 * 获取需求订单昨日成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders2 = manageSystemMapper
					.msGetYestodyRequirementOrderMoney(sId);
			double yestodayCompleteMoney = 0;
			for (int i = 0; i < orders2.size(); i++) {
				yestodayCompleteMoney += orders2.get(i).getRoTotalPrice();
			}
			map.put("yestodayCompleteMoney", 
					yestodayCompleteMoney); // 昨日成交额
			/**
			 * 获取需求订单今日成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders3 = manageSystemMapper
					.msGetTodayRequirementOrderMoney(sId);
			double todayCompleteMoney = 0;
			for (int i = 0; i < orders3.size(); i++) {
				todayCompleteMoney += orders3.get(i).getRoTotalPrice();
			}
			map.put("todayCompleteMoney", 
					todayCompleteMoney); // 今日成交额
			/**
			 * 获取需求订单总成交额
			 * t_requirement_order:RO_TOTAL_PRICE
			 */
			List<RequirementOrder> orders4 = manageSystemMapper
					.msGetRequirementOrderTotalMoney(sId);
			double totalCompleteMoney = 0;
			for (int i = 0; i < orders4.size(); i++) {
				totalCompleteMoney += orders4.get(i).getRoTotalPrice();
			}
			map.put("totalCompleteMoney", 
					totalCompleteMoney); // 总成交额
			//------商品---------
			/**
			 * 获取企业商户今日商品订单总数
			 */
			int todayProductOrderNum = manageSystemMapper
					.msGetTodayProductOrderNumBySId(sId);
			map.put("todayProductOrderNum", todayProductOrderNum); // 今日商品订单数
			/**
			 * 获取企业商户全部商品订单订单状态列表
			 * t_product_order:PO_STATUS
			 */
			List<ProductOrder> productOrders = manageSystemMapper
					.msGetProductOrderStatusListBySId(sId);
			int unReundOfProduct = 0; // 待退款订单数
			int unPayOfProduct = 0; // 待付款订单数
			int unDeliverOfProduct = 0; // 待发货订单数
			int completeOfProduct = 0; // 已完成订单数
			for (int i = 0; i < productOrders.size(); i++) {
				// 商品订单状态 0--待付款，1--待发货，2--待收货，3--待评价，4--已完成，5--待退款，6--已退款
				switch (productOrders.get(i).getPoStatus()) {
				case 0: // 待付款
					unPayOfProduct += 1;
					break;
				case 1: // 待发货
					unDeliverOfProduct += 1;
					break;
				case 4: // 已完成
					completeOfProduct += 1;
					break;
				case 5: // 待退款
					unReundOfProduct += 1;
					break;
				default:
					break;
				}
			}
			map.put("unReundProductOrderNum", unReundOfProduct); // 待退款订单数
			map.put("unPayProductOrderNum", unPayOfProduct); // 待付款订单数
			map.put("unDeliverProductOrderNum", unDeliverOfProduct); // 待发货订单数
			map.put("completeProductOrderNum", completeOfProduct); // 已完成订单数
			/**
			 * 昨日商品订单成交额
			 */
			double yestodayProductOrderMoney = manageSystemMapper
					.msGetYestodayProductOrderTotalMoney(sId);
			map.put("yestodayProductOrderMoney", 
					yestodayProductOrderMoney); // 昨日商品订单成交额
			/**
			 * 今日商品订单成交额
			 */
			double todayProductOrderMoney = manageSystemMapper
					.msGetTodayProductOrderTotalMoney(sId);
			map.put("todayProductOrderMoney", 
					todayProductOrderMoney); // 今日商品订单成交额
			/**
			 * 商品订单总成交额
			 */
			double ProductOrderMoney = manageSystemMapper
					.msGetProductOrderTotalMoney(sId);
			map.put("ProductOrderMoney", 
					ProductOrderMoney); // 商品订单总成交额
			/**
			 * 获取企业商户我的安排数量
			 */
			int myArrangeNum = manageSystemMapper
					.msGetStoreArrangeNum(sId);
			map.put("myArrangeNum", myArrangeNum); // 我的安排数量
			
			/**
			 * 获取需求类别列表
			 * t_requirement_type:RT_ID、RT_NAME
			 */
			List<RequirementType> types = manageSystemMapper
					.msGetRequirementTypeList();
			List<RequirementTypeOfMoney> list 
				= new ArrayList<RequirementTypeOfMoney>(); // 数组序列  
			for (int i = 0; i < types.size(); i++) {
				/**
				 * 获取本商户该需求类别下的总成交额
				 */
				double totalMoney = manageSystemMapper
						.msGetTotalMoneyByRtIdAndSId(
								types.get(i).getRtId(), sId);
				/**
				 * 获取某商户特定需求类别的总接单数量
				 */
				int totalNum = manageSystemMapper
						.msGetTotalNumByRtIdAndSId(
								types.get(i).getRtId(), sId);
				RequirementTypeOfMoney vo 
					= new RequirementTypeOfMoney(types.get(i).getRtId(),
							types.get(i).getRtName(), totalMoney, totalNum);
				list.add(vo);
			}	
			if (0 < list.size()) {
				Collections.sort(list);
				map.put("firstRtNameOfMoney", "需求类别：" + list.get(0)
						.getRtName() + "(合计：" + list.get(0)
						.getTotalMoney() + ")"); // 成交额贡献最多的需求类别和具体金额
			} else {
				map.put("firstRtNameOfMoney", "暂无"); // 成交额贡献最多的需求类别和具体金额
			}
			
			/**
			 * 获取某商户需求单价No.1数据
			 * t_requirement_order：RO_TOTAL_PRICE
			 * t_user_requirement：UR_TITLE
			 * t_requirement_type：RT_NAME
			 */
			RequirementOrder order = manageSystemMapper
					.msGetFirstRoInfoBySid(sId);
			if (null != order) {
				map.put("firstOfRequirementPrice",
						"需求类别：" + order.getRequirementType().getRtName()
						+ "   标题：" + order.getUserRequirement().getUrTitle()
						+ "   单价：" + order.getRoTotalPrice()); // 商户需求单价No.1
			} else {
				map.put("firstOfRequirementPrice", "暂无"); // 商户需求单价No.1
			}
			if (0 < list.size()) {
				Collections.sort(list, new Comparator<RequirementTypeOfMoney>() {

					@Override
					public int compare(RequirementTypeOfMoney o1, RequirementTypeOfMoney o2) {
						// TODO Auto-generated method stub
						return o2.getTotalNum()-o1.getTotalNum(); 
					}
				});
				map.put("firstRoNum", "需求类别：" + list.get(0)
						.getRtName() + "(共" + list.get(0).getTotalNum() 
						+ "单，合计：" + list.get(0)
						.getTotalMoney() + ")"); // 接单数量最多的需求类别
			} else {
				map.put("firstRoNum", "暂无"); // 接单数量最多的需求类别
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "账号不存在！");
			return map;
		}
		map.put("status", "true");
		map.put("errorString", "");
		return map;
	}

	@Override
	public Map<String, Object> doSubmitMerchantLonlat(String uId, String sId, String mtLon, String mtLat)
			throws Exception {
		// 1、先行判断商户是否存在处于取货中、待验货、送货中状态的需求订单 如有则获取处于上述状态的需求订单
		// 的主键标识
		// 2、向移动轨迹表中插入记录
		/**
		 * 获取处于取货中、待验货、送货中状态的需求订单列表
		 * t_requirement_order: RO_ID
		 */
		List<RequirementOrder> orders = storeMapper
				.getRequirementOrderListWhichSatisfyCondition(sId);
		for (int i = 0; i < orders.size(); i++) {
			/**
			 * 添加移动轨迹
			 */
			storeMapper.addMovingTrajectoryPoint(mtLon, mtLat,
					orders.get(i).getRoId());
		}
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAffirmProductRefund(String poId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		int status = productOrder.getPoStatus();
		if (5 == status) { //为待退款状态
			int status1 = 6;
			/**
			 * 修改商品订单状态
			 */
			int row1 = productMapper.doUpdateProductOrderStatus(status1, poId);
			if (1 != row1) {
				map.put("errorString", "修改用户商品订单状态失败");
				map.put("status", "false");
				throw new RuntimeException();
			} 
		}else {
			map.put("errorString", "不能退款!");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doInformInspection(String roId, String picData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品订单信息
		 */
		RequirementOrder requirementOrder = productMapper.doGetUserRequirementOrderByRoId(roId);
		int status = requirementOrder.getRoStatus();
		if (3 == status) { //为取货中状态
			PicVo picVo = Utils.parserJsonResult(picData, PicVo.class);
			// 待验货商品图片的二级标识
			String pTag = Utils.randomUUID();
			int count = 0; // 插入图片成功的次数
			for (int i = 0; i < picVo.getPics().size(); i++) {
				int result = manageSystemMapper.msAddPicInfo(Utils.REQUIREMENT_PIC, picVo.getPics().get(i).getPName(),
						picVo.getPics().get(i).getPNo(), pTag);
				if (1 == result) {
					count += 1;
				}
			}
			if (count == picVo.getPics().size()) {
				/**
				 * 修改需求订单状态
				 */
				int row1 = productMapper.doUpdateRequirementOrderStatus(4, roId, pTag);
				if (1 != row1) {
					map.put("errorString", "修改用户需求订单状态失败!");
					map.put("status", "false");
					throw new RuntimeException();
				}
			} else {
				map.put("errorString", "提交验货申请失败!");
				map.put("status", "false");
				return map;
			}
		}else {
			map.put("errorString", "提交验货申请失败!");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> merchantSend(String poId,String poDeliverCompany,String poDeliverCode)
			throws Exception {
		Map<String ,Object> map = new HashMap<String,Object>();
		/**
		 * 商户发货
		 */
		int row=storeMapper.merchantSend(poId,poDeliverCompany,poDeliverCode);
		if (1 == row) {
			//获取商品订单信息
			ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
			int status = productOrder.getPoStatus();
			if (1 == status) {
				int status1 = 2;
				//修改商品订单状态
				int row1 = productMapper.doUpdateProductOrderStatus(status1, poId);
				if (1 != row1) {
					map.put("errorString", "修改商品订单状态失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}
		} else {
			map.put("errorString", "商户发货失败");
			map.put("status", "false");
			throw new RuntimeException();
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> doDelStoreProductOrder(String poId)throws Exception {
		Map<String ,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商品订单信息
		 */
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		int status = productOrder.getPoStatus();
		/**
		 * 商户删除商品订单
		 */
		if (0 == status) {
			int row = productMapper.doDeleteProductOrder(poId);
			if (1 == row) {
				map.put("errorString", "");
				map.put("status", "true");
			} else {
				map.put("errorString", "商户删除订单失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		} else {
			map.put("errorString", "商户不能删除订单");
			map.put("status", "false");
		}
		return map;
	}
	
}