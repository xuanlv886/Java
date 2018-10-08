package com.service.extra.mall.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.persistence.criteria.From;
import javax.ws.rs.PUT;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.service.extra.mall.controller.CAPTCHA;
import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.StoreMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.model.AreaCode;
import com.service.extra.mall.model.FeedBack;
import com.service.extra.mall.model.OpenCity;
import com.service.extra.mall.model.PayStyle;
import com.service.extra.mall.model.Pic;
import com.service.extra.mall.model.Product;
import com.service.extra.mall.model.ProductDetailTypeRelationProductProperty;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.ProductProperty;
import com.service.extra.mall.model.ProductType;
import com.service.extra.mall.model.ProductTypeDetail;
import com.service.extra.mall.model.RecommendStore;
import com.service.extra.mall.model.RequirementOrder;
import com.service.extra.mall.model.RequirementType;
import com.service.extra.mall.model.SafetyQuestion;
import com.service.extra.mall.model.Store;
import com.service.extra.mall.model.StoreArrange;
import com.service.extra.mall.model.SysNotice;
import com.service.extra.mall.model.SysOperateLog;
import com.service.extra.mall.model.User;
import com.service.extra.mall.model.UserRole;
import com.service.extra.mall.model.UserToCashRecord;
import com.service.extra.mall.model.UserWallet;
import com.service.extra.mall.model.vo.PicVo;
import com.service.extra.mall.model.vo.RequirementTypeOfMoney;
import com.service.extra.mall.model.vo.SafetyQuestionVo;
import com.service.extra.mall.service.ManageSystemService;

import util.OSSUtils;
import util.ProductOrderStatus;
import util.RequirementOrderStatus;
import util.SysOperateType;
import util.Utils;

/**
 * 
 * desc：平台管理系统相关接口服务实现类
 * @author L
 * time:2018年4月23日
 */
@Service
public class ManageSystemServiceImpl implements ManageSystemService{

	@Resource private ManageSystemMapper manageSystemMapper;
	@Resource private UserMapper userMapper;
	@Resource private StoreMapper storeMapper;

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public int doMsAddSysOperateLog(String uId, 
			String solType, String solObject, String solTable)
			throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 添加系统日志
		 */
		int result = manageSystemMapper
				.doMsAddSysOperateLog(uId, 
						Integer.valueOf(solType).intValue(), 
						solObject, solTable);
		if (1 != result) { // 添加失败
			throw new RuntimeException();
		}
		return result;
	}
	
	@Override
	public Map<String, Object> doMsLogin(String account, String password)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 登录
		 * t_user:U_ID、S_ID、U_TRUE_NAME
		 * t_user_role：UR_NAME
		 * t_store：S_NAME、S_CHECKED
		 */
		User user = manageSystemMapper.doMsLogin(account, password);
		if (null != user) { // 有该用户
			if ("平台用户".equals(user.getUserRole().getUrName())) {
				map.put("status", "false"); // 登录失败
				map.put("errorString", "该账户无权限登录管理平台！");
			} else if ("个人商户".equals(user.getUserRole().getUrName())
					|| "企业商户".equals(user.getUserRole().getUrName())) {
				if (1 == user.getStore().getsChecked()) { // 通过审核
					map.put("status", "true"); // 登录成功
					map.put("errorString", "");
					map.put("checked", 1); // 已通过审核
					map.put("uId", user.getuId()); // 用户主键标识
					map.put("uTrueName", user
							.getuTrueName()); // 用户真实姓名
					map.put("urName", user.getUserRole()
							.getUrName()); // 用户角色名称
					map.put("sId", user.getsId()); // 店铺主键标识
					map.put("sName", user.getStore()
							.getsName()); // 店铺名称
					map.put("account", account); // 账号
				} else {
					map.put("status", "false"); // 登录失败
					map.put("checked", 0); // 未通过审核
					map.put("errorString", "该账户尚未通过审核！");
					map.put("uId", user.getuId()); // 用户主键标识
					map.put("uTrueName", user
							.getuTrueName()); // 用户真实姓名
					map.put("urName", user.getUserRole()
							.getUrName()); // 用户角色名称
					map.put("sId", user.getsId()); // 店铺主键标识
					map.put("sName", user.getStore()
							.getsName()); // 店铺名称
					map.put("account", account); // 账号
				}
			} else { // 系统管理员
				map.put("status", "true"); // 登录成功
				map.put("errorString", "");
				map.put("checked", 1); // 已通过审核
				map.put("uId", user.getuId()); // 用户主键标识
				map.put("uTrueName", user.getuTrueName()); // 用户真实姓名
				map.put("urName", user.getUserRole()
						.getUrName()); // 用户角色名称
				map.put("sId", ""); // 店铺主键标识
				map.put("sName", ""); // 店铺名称
				map.put("account", account); // 账号
			}
		} else { // 没有该用户
			map.put("status", "false"); // 登录失败
			map.put("errorString", "账号或密码错误！");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsGetSysNoticeList(String uId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取系统公告列表
		 * t_sys_notice：SN_ID、SN_TITLE、SN_CONTENT、SN_CREATE_TIME
		 * t_user：U_TRUE_NAME
		 */
		List<SysNotice> notices = manageSystemMapper
				.doMsGetSysNoticeList();
		for (int i = 0; i < notices.size(); i++) {
			Map<String,Object> notice = new HashMap<String,Object>();
			notice.put("snId", notices.get(i)
					.getSnId()); // 系统公告主键标识
			notice.put("snTitle", notices.get(i)
					.getSnTitle()); // 系统公告标题
			notice.put("snContent", notices.get(i)
					.getSnContent()); // 系统公告内容
			notice.put("uTrueName", notices.get(i)
					.getUser().getuTrueName()); // 系统公告发布人
			notice.put("snCreateTime", notices.get(i)
					.getSnCreateTime()); // 系统公告发布时间
			map.add(notice);
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetAdminIndexInfo(String uId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户角色名称
		 * t_user_role：UR_NAME
		 */
		UserRole userRole = manageSystemMapper
				.msGetUserRoleByUid(uId);
		if (null != userRole) {
			// 先行判断用户角色是否为系统管理员
			if ("系统管理员".equals(userRole.getUrName())
					|| "系统子管理员".equals(userRole.getUrName())) {
				map.put("status", "true"); // 获取数据成功
				map.put("errorString", "");
				/**
				 * 获取平台总用户数量
				 */
				int totalUser = manageSystemMapper
						.msGetTotalUserNum();
				map.put("totalUser", totalUser); // 平台总用户数量
				/**
				 * 获取平台已入驻商户数量
				 */
				int storeUser = manageSystemMapper.msGetStoreUserNum();
				map.put("storeUser", storeUser); // 平台已入驻商户数量
				/**
				 * 获取平台用户数量
				 */
				int normalUser = manageSystemMapper.msGetNormalUserNum();
				map.put("normalUser", normalUser); // 平台用户数量
				/**
				 * 获取平台需求总数
				 */
				int totalRequirement = manageSystemMapper
						.msGetTotalRequirementNum();
				map.put("totalRequirement", 
						totalRequirement); // 平台需求总数
				/**
				 * 获取平台今日发布的需求数量
				 */
				int todayRequirement = manageSystemMapper
						.msGetTodayRequirementNum();
				map.put("todayRequirement", 
						todayRequirement); // 平台今日发布的需求数量
				/**
				 * 获取平台昨日发布的需求数量
				 */
				int yestodayRequirement = manageSystemMapper
						.msGetYestodayRequirementNum();
				map.put("yestodayRequirement", 
						yestodayRequirement); // 平台昨日发布的需求数量
				/**
				 * 获取平台今日已完成的需求数量
				 */
				int todayOverRequirement = manageSystemMapper
						.msGetTodayOverRequirementNum();
				map.put("todayOverRequirement", 
						todayOverRequirement); // 平台今日已完成的需求数量
				/**
				 * 获取平台商品总数量
				 */
				int totalProduct = manageSystemMapper
						.msGetTotalProductNum();
				map.put("totalProduct", 
						totalProduct); // 获取平台商品总数量
				/**
				 * 获取平台今日发布的商品数量
				 */
				int todayProduct = manageSystemMapper
						.msGetTodayProductNum();
				map.put("todayProduct", 
						todayProduct); // 平台今日发布的商品数量
				/**
				 * 获取平台昨日发布的商品数量
				 */
				int yestodayProduct = manageSystemMapper
						.msGetYestodayProductNum();
				map.put("yestodayProduct", 
						yestodayProduct); // 平台昨日发布的商品数量
				/**
				 * 获取平台今日已完成的订单总数量
				 */
				int todayOverOrder = manageSystemMapper
						.msGetTodayOverOrderNum();
				map.put("todayOverOrder", 
						todayOverOrder); // 平台今日已完成的订单总数量
				/**
				 * 获取平台今日已退款的订单总数量
				 */
				int todayBackOrder = manageSystemMapper
						.msGetTodayBackOrderNum();
				map.put("todayBackOrder", 
						todayBackOrder); // 平台今日已退款的订单总数量
				/**
				 * 获取平台待审核商户的数量
				 */
				int uncheckStore = manageSystemMapper
						.msGetUncheckStoreNum();
				map.put("uncheckStore", 
						uncheckStore); // 平台待审核商户的数量
				/**
				 * 获取平台待审核商品的数量
				 */
				int uncheckProduct = manageSystemMapper
						.msGetUncheckProductNum();
				map.put("uncheckProduct", 
						uncheckProduct); // 平台待审核商品的数量
				/**
				 * 获取平台待审核提现请求的数量
				 */
				int uncheckApplyToCash = manageSystemMapper
						.msGetUncheckApplyToCashNum();
				map.put("uncheckApplyToCash", 
						uncheckApplyToCash); // 平台待审核提现请求的数量
			} else {
				map.put("status", "false"); // 获取数据失败
				map.put("errorString", "该账户角色不是系统管理员！");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetStoreList(String sName, 
			String sType, String sLeader, String sChecked, String isRecommend,
			String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的店铺总数接口
		 */
		int totalCount = manageSystemMapper
				.msGetStoreListSatisfyConditionCount(sName, sType,
						sLeader, sChecked, isRecommend);
		map.put("totalCount", totalCount); // 满足条件的店铺总数
		/**
		 * 获取满足条件的店铺信息列表接口
		 * t_store：S_ID、S_TYPE、S_NAME、P_TAG、S_DESCRIBE、S_LEADER、
		 * 		S_LEADER_IDCARD、S_LEGAL、S_LEGAL_IDCARD、S_LEADER_PIC、
		 * 		S_LEGAL_PIC、S_BUSINESS_LICENSE_PIC、S_TEL、S_CREATE_TIME、
		 * 		S_ADDRESS、S_LON、S_LAT、S_WEIGHT、S_BOOTH_NUM、
		 * 		S_LEFT_BOOTH_NUM、S_REQUIREMENT_SERVICE_CHARGE、
		 * 		S_ORDER_LIMINT、S_PRODUCT_SERVICE_CHARGE、
		 * 		S_CHECKED、S_LEVEL、S_CHECKED_TIME、S_CHECKED_OPINION
		 * t_areacode：AC_CITY
		 * t_user：U_TRUE_NAME
		 */
		List<Store> stores = manageSystemMapper
				.doMsGetStoreList(sName, sType, sLeader, sChecked, isRecommend,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> storeList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < stores.size(); i++) {
			Map<String,Object> storeMap = new HashMap<String,Object>();
			storeMap.put("sId", stores.get(i).getsId()); // 店铺主键标识
			storeMap.put("sType", stores.get(i).getsType()); // 店铺类型
			storeMap.put("sName", stores.get(i).getsName()); // 店铺名称
			/**
			 * 获取店铺封面图片
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> storePics = manageSystemMapper
					.msGetPicByPTag(stores.get(i).getpTag());
			List<Map<String,Object>> storePicList 
				= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < storePics.size(); j++) {
				Map<String,Object> storePicMap = new HashMap<String,Object>();
				storePicMap.put("url", Utils.PIC_BASE_URL 
						+ storePics.get(j).getpFileName()
						+ storePics.get(j).getpName()); // 图片地址
				storePicMap.put("pNo", storePics.get(j)
						.getpNo()); // 图片编号
				storePicList.add(storePicMap);
			}
			storeMap.put("storePics", storePicList); // 店铺封面图片
			storeMap.put("acCity", stores.get(i).getAreaCode()
					.getAcCity()); // 店铺所在城市
			storeMap.put("sDescribe", stores.get(i)
					.getsDescribe()); // 店铺描述
			storeMap.put("sLeader", stores.get(i)
					.getsLeader()); // 店铺负责人
			storeMap.put("sLeaderIdCard", stores.get(i)
					.getsLeaderIdCard()); // 店铺负责人身份证号
			storeMap.put("sLegal", stores.get(i).getsLegal()); // 店铺法人
			storeMap.put("sLegalIdCard", stores.get(i)
					.getsLegalIdCard()); // 店铺法人身份证号
			/**
			 * 获取店铺负责人身份证图片
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> sLeaderPics = manageSystemMapper
					.msGetPicByPTag(stores.get(i).getsLeaderPic());
			List<Map<String,Object>> sLeaderPicList 
				= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < sLeaderPics.size(); j++) {
				Map<String,Object> sLeaderPicMap = new HashMap<String,Object>();
				sLeaderPicMap.put("url", Utils.PIC_BASE_URL 
						+ sLeaderPics.get(j).getpFileName()
						+ sLeaderPics.get(j).getpName()); // 图片地址
				sLeaderPicMap.put("pNo", sLeaderPics.get(j)
						.getpNo()); // 图片编号
				sLeaderPicList.add(sLeaderPicMap);
			}
			storeMap.put("sLeaderPics", sLeaderPicList); // 店铺负责人身份证图片
			/**
			 * 获取店铺法人身份证图片
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> sLegalPics = manageSystemMapper
					.msGetPicByPTag(stores.get(i).getsLegalPic());
			List<Map<String,Object>> sLegalPicList 
				= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < sLegalPics.size(); j++) {
				Map<String,Object> sLegalPicMap = new HashMap<String,Object>();
				sLegalPicMap.put("url", Utils.PIC_BASE_URL 
						+ sLegalPics.get(j).getpFileName()
						+ sLegalPics.get(j).getpName()); // 图片地址
				sLegalPicMap.put("pNo", sLegalPics.get(j)
						.getpNo()); // 图片编号
				sLegalPicList.add(sLegalPicMap);
			}
			storeMap.put("sLegalPics", sLegalPicList); // 店铺法人身份证图片
			/**
			 * 获取店铺营业执照图片
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> sBuinessLicensePics = manageSystemMapper
					.msGetPicByPTag(stores.get(i)
							.getsBuinessLicensePic());
			List<Map<String,Object>> sBuinessLicensePicList 
				= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < sBuinessLicensePics.size(); j++) {
				Map<String,Object> sBuinessLicensePicMap = new HashMap<String,Object>();
				sBuinessLicensePicMap.put("url", Utils.PIC_BASE_URL 
						+ sBuinessLicensePics.get(j).getpFileName()
						+ sBuinessLicensePics.get(j)
						.getpName()); // 图片地址
				sBuinessLicensePicMap.put("pNo", sBuinessLicensePics
						.get(j).getpNo()); // 图片编号
				sBuinessLicensePicList.add(sBuinessLicensePicMap);
			}
			storeMap.put("sBuinessLicensePics",
					sBuinessLicensePicList); // 店铺营业执照图片
			storeMap.put("sTel", stores.get(i).getsTel()); // 店铺联系电话
			storeMap.put("sCreateTime", stores.get(i)
					.getsCreateTime()); // 店铺创建时间
			storeMap.put("sAddress", stores.get(i)
					.getsAddress()); // 店铺地址
			storeMap.put("sLon", stores.get(i).getsLon()); // 店铺经度
			storeMap.put("sLat", stores.get(i).getsLat()); // 店铺纬度
			storeMap.put("sWeight", stores.get(i).getsWeight()); // 店铺权重
			storeMap.put("sBoothNum", stores.get(i)
					.getsBoothNum()); // 店铺推荐展位总个数
			storeMap.put("sLeftBoothNum", stores.get(i)
					.getsLeftBoothNum()); // 店铺剩余推荐展位个数
			storeMap.put("sRequirementServiceCharge", stores.get(i)
					.getsRequirementServiceCharge()); // 店铺需求交易服务费
			storeMap.put("sOrderLimint", stores.get(i)
					.getsOrderLimint()); // 店铺订单交易限额
			storeMap.put("sProductServiceCharge", stores.get(i)
					.getsProductServiceCharge()); // 店铺商品交易服务费
			storeMap.put("sChecked", stores.get(i)
					.getsChecked()); // 店铺审核状态
			storeMap.put("sLevel", stores.get(i).getsLevel()); // 店铺评级
			try {
				storeMap.put("uTrueName", stores.get(i).getUser()
						.getuTrueName()); // 审核人姓名
			} catch (Exception e) {
				// TODO: handle exception
				storeMap.put("uTrueName", ""); // 审核人姓名
			}
			storeMap.put("sCheckedTime", stores.get(i)
					.getsCheckedTime()); // 店铺审核时间
			storeMap.put("sCheckedOpinion", stores.get(i)
					.getsCheckedOpinion()); // 店铺审核意见
			storeList.add(storeMap);
		}
		map.put("stores", storeList);
		return map;
	}

	@Override
	public Map<String, Object> doMsGetStoreOtherInfoBySId(String sId, String sType) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取特定店铺钱包信息
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT
		 */
		UserWallet userWallet = manageSystemMapper
				.msGetStoreWalletListBySId(sId);
		if (null != userWallet) {
			map.put("uwLeftMoney", userWallet.getUwLeftMoney()); // 可用余额
			map.put("uwDeposit", userWallet
					.getUwDeposit()); // 已缴纳（冻结）的保障金
		} else {
			map.put("uwLeftMoney", 0); // 可用余额
			map.put("uwDeposit", 0); // 已缴纳（冻结）的保障金
		}
		if ("1".equals(sType)) { // 企业商户 只有企业商户可以发布商品
			/**
			 * 获取特定店铺已发布的商品的总数
			 */
			int productNum = manageSystemMapper.msGetStoreProductNum(sId);
			map.put("productNum", productNum); // 已发布的商品的总数
			/**
			 * 商户是否为推荐商户
			 * t_recommend_store：RS_TITLE、RS_CONTENT、P_TAG
			 */
			RecommendStore recommendStore = manageSystemMapper
					.msGetStoreIsRecommendInfo(sId);
			if (null != recommendStore) {
				map.put("isRecommend", "1"); // 推荐店铺
				map.put("rsTitle", recommendStore.getRsTitle());
				map.put("rsContent", recommendStore.getRsContent());
				map.put("pTag", recommendStore.getpTag());
				/**
				 * 获取推荐图片
				 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
				 */
				List<Pic> pics = manageSystemMapper
						.msGetPicByPTag(recommendStore.getpTag());
				List<Map<String,Object>> picList 
					= new ArrayList<Map<String,Object>>();
				for (int j = 0; j < pics.size(); j++) {
					Map<String,Object> picMap = new HashMap<String,Object>();
					picMap.put("url", Utils.PIC_BASE_URL 
							+ Utils.STORE_PIC
							+ pics.get(j)
							.getpName()); // 图片地址
					picMap.put("pNo", pics
							.get(j).getpNo()); // 图片编号
					picList.add(picMap);
				}
				map.put("pics",
						picList); // 推荐图片
			} else {
				map.put("isRecommend", "0"); // 非推荐店铺
				map.put("pTag", "");
			}
		} else {
			map.put("productNum", 0); // 已发布的商品的总数
		}
		
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeStoreCheckStatus(String uId,
			String sId, String sChecked, String sCheckedOpinion)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改店铺审核状态结果
		 */
		int result = manageSystemMapper
				.doMsChangeStoreCheckStatus(uId, sId, 
						Integer.valueOf(sChecked).intValue(), 
						sCheckedOpinion);
		/**
		 * 获取用户相关信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (1 == result) { // 修改成功
			map.put("status", "true");
			map.put("errorString", "");
			CAPTCHA captcha = new CAPTCHA();
			/**
			 * 获取店铺的审核状态
			 * t_store: S_CHECKED, S_NAME
			 */
			Store store = manageSystemMapper
					.msGetStoreCheckedStatus(sId);
			// 插入系统操作日志表
			if (0 == Integer.valueOf(sChecked).intValue()) { // 驳回、下架
				if (0 == store.getsChecked()) { // 驳回
					manageSystemMapper
					.doMsAddSysOperateLog(uId, 
							SysOperateType.STORE_CHECK_FAIL.getId(),
							sId, "t_store");
				} else if (1 == store.getsChecked()) { // 下架
					manageSystemMapper
					.doMsAddSysOperateLog(uId, 
							SysOperateType.STORE_CHECK_DOWN.getId(),
							sId, "t_store");
				}
				// 发送审核短信通知
				captcha.sendStoreCheckedNotice(user.getuTel(),
						store.getsName(), "未通过", sCheckedOpinion);
			} else if (1 == Integer.valueOf(sChecked).intValue()) { // 通过
				manageSystemMapper
						.doMsAddSysOperateLog(uId, 
								SysOperateType.STORE_CHECK_PASS.getId(),
								sId, "t_store");
				// 发送审核短信通知
				captcha.sendStoreCheckedNotice(user.getuTel(),
						store.getsName(), "已通过", sCheckedOpinion);
			}
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddOrChangeStoreConfigInfo(
			String uId, String sId, String sOrderLimint,
			String requirementServiceCharge, String productServiceCharge, String sLevel, String sWeight,
			String sBoothNum, String isRecommend, String rsTitle, String rsContent,
			String picData) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改商品推荐展位状态 取消该店铺内所有商品的推荐展位
		 */
		int updateResult = manageSystemMapper
				.msChangeProductHaveBoothStatus(sId);
		/**
		 * 添加或修改店铺配置信息
		 */
		int result = manageSystemMapper
				.doMsAddOrChangeStoreConfigInfo(
						Double.valueOf(sOrderLimint).doubleValue(),
						requirementServiceCharge,
						productServiceCharge,
						Integer.valueOf(sLevel).intValue(),
						Integer.valueOf(sWeight).intValue(),
						Integer.valueOf(sBoothNum).intValue(), 
						Integer.valueOf(sBoothNum).intValue(),
						sId);
		if (1 == result) { // 保存成功
			// 判断是否设置为推荐店铺
			/**
			 * 先判断之前是否设置过该店铺为推荐店铺
			 */
			RecommendStore recommendStore = manageSystemMapper
					.msGetStoreIsRecommendInfo(sId);
			if (null != recommendStore) {
				// 先删除之前设置的数据
				manageSystemMapper.msDelRecommendStoreBySid(sId);
				if (!Utils.isEmpty(isRecommend) 
						&& "1".equals(isRecommend)) { // 推荐店铺
					/**
					 * 更改店铺关联的推荐图片
					 * 1、删除关联图片
					 * 2、新增关联图片
					 */
					// 解析图片数据
					PicVo picVo = new PicVo();
					picVo = Utils.parserJsonResult(picData, PicVo.class);
					if (null != picVo) {
						/**
						 * 根据pTag删除数据库中的图片
						 */
						manageSystemMapper
								.msDelPicsByPtag(picVo.getPTag());
						/**
						 * 添加图片
						 */
						for (int i = 0; i < picVo.getPics().size(); i++) {
							manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
									picVo.getPics().get(i).getPName(),
									picVo.getPics().get(i).getPNo(),
									picVo.getPTag());
						}
						/**
						 * 添加推荐商户
						 */
						manageSystemMapper.msAddRecommendStore(sId, rsTitle, 
								rsContent, picVo.getPTag(), uId);
					}
				}
			} else {
				if (!Utils.isEmpty(isRecommend) 
						&& "1".equals(isRecommend)) { // 推荐店铺
					String pTag = Utils.randomUUID();
					// 解析图片数据
					PicVo picVo = new PicVo();
					picVo = Utils.parserJsonResult(picData, PicVo.class);
					if (null != picVo) {
						/**
						 * 添加图片
						 */
						for (int i = 0; i < picVo.getPics().size(); i++) {
							manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
									picVo.getPics().get(i).getPName(),
									picVo.getPics().get(i).getPNo(),
									pTag);
						}
						/**
						 * 添加推荐商户
						 */
						manageSystemMapper.msAddRecommendStore(sId, rsTitle, 
								rsContent, pTag, uId);
					}
				}
				
			}
			
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
				.doMsAddSysOperateLog(uId, 
						SysOperateType.STORE_CONFIG_CHANGE.getId(),
						sId, "t_store");
		} else { // 保存失败
			map.put("status", "false");
			map.put("errorString", "保存失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsGetProductTypeList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取商品大类列表
		 * t_product_type:PT_ID、PT_NAME
		 */
		List<ProductType> productTypes = manageSystemMapper
				.doMsGetProductTypeList();
		for (int i = 0; i < productTypes.size(); i++) {
			Map<String,Object> productTypeMap = new HashMap<String,Object>();
			productTypeMap.put("ptId", productTypes.get(i)
					.getPtId()); // 商品大类主键标识
			productTypeMap.put("ptName", productTypes.get(i)
					.getPtName()); // 商品大类名称
			map.add(productTypeMap);
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetProductList(String userType, String pName, String pHaveBooth, String ptId, String pChecked,
			String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的商品的总数
		 */
		int totalCount = manageSystemMapper
				.msGetProductListSatisfyConditionCount(pName, pHaveBooth, 
						ptId, pChecked, userType);
		map.put("totalCount", totalCount); // 满足条件的商品总数
		/**
		 * 根据条件获取商品列表
		 * t_product：P_ID、P_NAME、P_BRAND、P_DESCRIBE、P_TAG、
		 * 		P_ORIGINAL_PRICE、P_NOW_PRICE、P_CREATE_TIME、PTD_ID
		 * 		P_DEL、P_CHECKED、P_WEIGHT、P_HAVE_BOOTH、
		 * 		P_CHECKED_TIME、P_CHECKED_OPINION
		 * t_store：S_NAME
		 * t_product_type_detail：PTD_NAME
		 * t_user：U_TRUE_NAME
		 */
		List<Product> products = manageSystemMapper
				.doMsGetProductList(pName, pHaveBooth, ptId, pChecked, 
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue(),
						userType);
		List<Map<String,Object>> productList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < products.size(); i++) {
			Map<String,Object> productMap = new HashMap<String,Object>();
			productMap.put("pId", products.get(i).getpId()); // 商品主键标识
			productMap.put("pName", products.get(i).getpName()); // 商品名称
			productMap.put("pBrand", products.get(i).getpBrand()); // 商品品牌
			productMap.put("pDescribe", products.get(i)
					.getpDescribe()); // 商品描述
			/**
			 * 获取商品图片
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> productPics = manageSystemMapper
					.msGetPicByPTag(products.get(i)
							.getpTag());
			List<Map<String,Object>> productPicList 
				= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < productPics.size(); j++) {
				Map<String,Object> productPicMap = new HashMap<String,Object>();
				productPicMap.put("url", Utils.PIC_BASE_URL 
						+ productPics.get(j).getpFileName()
						+ productPics.get(j)
						.getpName()); // 图片地址
				productPicMap.put("pNo", productPics
						.get(j).getpNo()); // 图片编号
				productPicList.add(productPicMap);
			}
			productMap.put("productPics",
					productPicList); // 商品图片
			productMap.put("pOriginalPrice", products.get(i)
					.getpOriginalPrice()); // 商品原价
			productMap.put("pNowPrice", products.get(i)
					.getpNowPrice()); // 商品现价
			productMap.put("pCreateTime", products.get(i)
					.getpCreateTime()); // 商品添加时间
			productMap.put("pDel", products.get(i).getpDel()); // 商品是否被删除
			productMap.put("pChecked", products.get(i)
					.getpChecked()); // 商品审核状态
			productMap.put("pWeight", products.get(i)
					.getpWeight()); // 商品权重
			productMap.put("pHaveBooth", products.get(i)
					.getpHaveBooth()); // 是否为推荐商品
			productMap.put("pCheckedTime", products.get(i)
					.getpCheckedTime()); // 商品审核时间
			productMap.put("pCheckedOpinion", products.get(i)
					.getpCheckedOpinion()); // 商品审核意见
			productMap.put("sName", products.get(i).getStore()
					.getsName()); // 商品所属店铺名称
			productMap.put("ptdId", products.get(i).getPtdId()); // 商品小类主键标识
			productMap.put("ptdName", products.get(i)
					.getProductTypeDetail().getPtdName()); // 商品所属小类名称
			try {
				productMap.put("uTrueName", products.get(i).getUser()
						.getuTrueName()); // 商品审核人姓名
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("uTrueName", ""); // 商品审核人姓名
			}
			
			productList.add(productMap);
		}
		map.put("products", productList);
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeProductCheckStatus(String uId, String pId, String pChecked,
			String pCheckedOpinion) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改商品审核状态
		 */
		int result = manageSystemMapper
				.doMsChangeProductCheckStatus(
						Integer.valueOf(pChecked).intValue(),
						uId, pCheckedOpinion, pId);
		/**
		 * 获取用户相关信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		/**
		 * 通过uId获取商户名称
		 */
		Store store = manageSystemMapper.msGetSnameBypId(pId);
		CAPTCHA captcha = new CAPTCHA();
		if (1 == result) { // 修改成功
			map.put("status", "true");
			map.put("errorString", "");
			/**
			 * 获取商品的审核状态
			 * t_product: P_CHECKED, P_NAME
			 */
			Product product = manageSystemMapper
					.msGetProductCheckedStatus(pId);
			// 插入系统操作日志表
			if (0 == Integer.valueOf(pChecked).intValue()) { // 驳回、下架
				
				if (0 == product.getpChecked()) { // 驳回
					manageSystemMapper
					.doMsAddSysOperateLog(uId, 
							SysOperateType.PRODUCT_CHECK_FAIL.getId(),
							pId, "t_product");
				} else if (1 == product.getpChecked()) { // 下架
					manageSystemMapper
					.doMsAddSysOperateLog(uId, 
							SysOperateType.PRODUCT_CHECK_DOWN.getId(),
							pId, "t_product");
				}
				// 发送审核短信通知
				captcha.sendProductCheckedNotice(user.getuTel(),
						store.getsName(), product.getpName() + "未", 
						pCheckedOpinion);
			} else if (1 == Integer.valueOf(pChecked).intValue()) { // 通过
				manageSystemMapper
						.doMsAddSysOperateLog(uId, 
								SysOperateType.PRODUCT_CHECK_PASS.getId(),
								pId, "t_product");
				// 发送审核短信通知
				captcha.sendProductCheckedNotice(user.getuTel(),
						store.getsName(), product.getpName() + "已", 
						pCheckedOpinion);
			}						
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddOrChangeProductConfigInfo(
			String uId, String pId, String pWeight) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 添加或修改商品配置信息
		 */
		int result = manageSystemMapper
				.msAddOrChangeProductConfigInfo(
						Integer.valueOf(pWeight).intValue(),
						pId);
		if (1 == result) { // 保存成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
					SysOperateType.PRODUCT_CONFIG_CHANGE.getId(),
					pId, "t_product");			
		} else { // 保存失败
			map.put("status", "false");
			map.put("errorString", "保存失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsSysSettingGetSysNoticeList(String snTitle, String uTrueName) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取系统公告列表
		 * t_sys_notice：SN_ID、SN_TITLE、SN_CONTENT、SN_CREATE_TIME
		 * t_user：U_TRUE_NAME
		 */
		List<SysNotice> notices = manageSystemMapper
				.doMsSysSettingGetSysNoticeList(snTitle, uTrueName);
		for (int i = 0; i < notices.size(); i++) {
			Map<String,Object> noticeMap = new HashMap<String,Object>();
			noticeMap.put("snId", notices.get(i)
					.getSnId()); // 系统公告主键标识
			noticeMap.put("snTitle", notices.get(i)
					.getSnTitle()); // 系统公告标题
			noticeMap.put("snContent", notices.get(i)
					.getSnContent()); // 系统公告内容
			noticeMap.put("uTrueName", notices.get(i)
					.getUser().getuTrueName()); // 系统公告发布人
			noticeMap.put("snCreateTime", notices.get(i)
					.getSnCreateTime()); // 系统公告发布时间
			map.add(noticeMap);
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSysSettingAddSysNotice(String uId, String snTitle, String snContent)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String uuid = UUID.randomUUID().toString();
		/**
		 * 发布系统公告
		 */
		int result = manageSystemMapper
				.doMsSysSettingAddSysNotice(uuid, snTitle, snContent, uId);
		if (1 == result) { // 发布成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
					SysOperateType.ADD_SYS_NOTICE.getId(),
					uuid, "t_sys_notice");				
		} else { // 发布失败
			map.put("status", "false");
			map.put("errorString", "发布失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSysSettingChangeSysNotice(String uId, String snId, String snTitle, String snContent)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改系统公告
		 */
		int result = manageSystemMapper
				.doMsSysSettingChangeSysNotice(snTitle, snContent, 
						uId, snId);
		if (1 == result) { // 修改成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
					SysOperateType.CHANGE_SYS_NOTICE.getId(),
					snId, "t_sys_notice");				
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败！");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsSysSettingGetSysOperateLogTypeList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (SysOperateType info : SysOperateType.values()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", info.getId()); // 状态标识
			map.put("name", info.getName()); // 状态名称
			list.add(map);
		}
		return list;
	}

	@Override
	public Map<String, Object> doMsSysSettingGetSysOperateLogList(String solType,
			String start, String end)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的数据总数
		 */
		int totalCount = manageSystemMapper
				.msSysSettingGetSysOperateLogCount(solType);
		map.put("totalCount", totalCount); // 总数
		/**
		 * 根据条件获取系统操作日志列表
		 * t_sys_operate_log：SOL_ID、SOL_CREATE_TIME、SOL_TYPE、
		 * 		SOL_OBJECT、SOL_TABLE
		 * t_user：U_TRUE_NAME
		 */
		List<SysOperateLog> logs = manageSystemMapper
				.doMsSysSettingGetSysOperateLogList(solType,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> logList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < logs.size(); i++) {
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put("solId", logs.get(i)
					.getSolId()); // 操作日志主键标识
			logMap.put("solCreateTime", logs.get(i)
					.getSolCreateTime()); // 创建时间
			for (SysOperateType info : SysOperateType.values()) {
				if (info.getId() == logs.get(i).getSolType()) {
					logMap.put("solType", info.getName()); // 操作类型
				}
			}
			logMap.put("solObject", logs.get(i)
					.getSolObject()); // 操作对象
			logMap.put("solTable", logs.get(i)
					.getSolTable()); // 操作的表
			logMap.put("uTrueName", logs.get(i).getUser()
					.getuTrueName()); // 操作人
			logList.add(logMap);
		}
		map.put("logs", logList); //操作日志列表
		return map;
	}

	@Override
	public Map<String, Object> doMsSysSettingGetFeedbackList(String fContent, String fAppType, String fType,
			String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据条件获取反馈意见列表总数
		 */
		int totalCount = manageSystemMapper
				.msSysSettingGetFeedbackCount(fContent, fAppType, fType);
		map.put("totalCount", totalCount); // 反馈意见列表总数
		/**
		 * 根据条件获取反馈意见列表
		 * t_feedback:F_ID、F_TYPE、F_CONTENT、F_CREATE_TIME、F_APP_TYPE
		 * t_user：U_ACCOUNT、U_NICKNAME、U_TRUE_NAME
		 */
		List<FeedBack> feedBacks = manageSystemMapper
				.doMsSysSettingGetFeedbackList(fContent, fAppType, fType,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> feedBackList 
			= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < feedBacks.size(); i++) {
			Map<String,Object> feedBackMap = new HashMap<String,Object>();
			feedBackMap.put("fId", feedBacks.get(i).getfId()); // 主键标识
			feedBackMap.put("fType", feedBacks.get(i).getfType()); // 问题类型
			feedBackMap.put("fContent", feedBacks.get(i)
					.getfContent()); // 反馈内容
			feedBackMap.put("fCreateTime", feedBacks.get(i)
					.getfCreateTime()); // 反馈创建时间
			feedBackMap.put("fAppType", feedBacks.get(i)
					.getfAppType()); // 客户端类型
			feedBackMap.put("uAccount", feedBacks.get(i).getUser()
					.getuAccount()); // 反馈人账号
			feedBackMap.put("uNickName", feedBacks.get(i).getUser()
					.getuNickName()); // 反馈人昵称
			feedBackList.add(feedBackMap);
		}
		map.put("feedbacks", feedBackList); // 反馈列表 
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsSysSettingGetUserRoleList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map 
				= new ArrayList<Map<String,Object>>();
		/**
		 * 获取用户角色列表
		 * t_user_role:UR_ID、UR_NAME
		 */
		List<UserRole> userRoles = manageSystemMapper
				.doMsSysSettingGetUserRoleList();
		for (int i = 0; i < userRoles.size(); i++) {
			Map<String,Object> userRoleMap = new HashMap<String,Object>();
			userRoleMap.put("urId", userRoles.get(i)
					.getUrId()); // 用户角色主键标识
			userRoleMap.put("urName", userRoles.get(i)
					.getUrName()); // 用户角色名称
			map.add(userRoleMap);
		}		
		return map;
	}

	@Override
	public Map<String, Object> doMsSysSettingGetUserList(String uAccount, String uTrueName, String uTel, String urId,
			String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的用户总数
		 */
		int totalCount = manageSystemMapper
				.msSysSettingGetUserCount(uAccount, uTrueName, 
						uTel, urId);
		map.put("totalCount", totalCount);
		/**
		 * 根据条件获取用户列表
		 * t_user:U_ID、U_ACCOUNT、U_PASSWORD、U_NICKNAME、U_SEX、U_EMAIL、U_BIRTHDAY、
		 * 		U_TEL、U_TRUE_NAME、U_CREATE_TIME
		 * t_user_role：UR_NAME
		 * t_store：S_NAME
		 */
		List<User> users = manageSystemMapper
				.doMsSysSettingGetUserList(uAccount, uTrueName, uTel, urId,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> userList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < users.size(); i++) {
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("uId", users.get(i).getuId()); // 用户主键标识
			userMap.put("uAccount", users.get(i).getuAccount()); // 用户账号
			userMap.put("uPassword", users.get(i).getuPassword()); // 用户密码
			userMap.put("uNickName", users.get(i).getuNickName()); // 用户昵称
			userMap.put("uSex", users.get(i).getuSex()); // 用户性别
			userMap.put("uEmail", users.get(i).getuEmail()); // 用户邮箱
			userMap.put("uBirthday", users.get(i)
					.getuBirthday()); // 用户出生日期
			userMap.put("uTel", users.get(i).getuTel()); // 用户电话
			userMap.put("uTrueName", users.get(i)
					.getuTrueName()); // 用户真实姓名
			userMap.put("uCreateTime", users.get(i)
					.getuCreateTime()); // 用户创建时间
			userMap.put("urName", users.get(i).getUserRole()
					.getUrName()); // 用户角色名称
			try {
				userMap.put("sName", users.get(i).getStore()
						.getsName()); // 店铺名称
			} catch (Exception e) {
				// TODO: handle exception
				userMap.put("sName", ""); // 店铺名称
			}
			userList.add(userMap);
		}
		map.put("users", userList); // 用户列表
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSysSettingAddUserInfo(String uId, 
			String uAccount, String uPassword, String uNickName, 
			String uTrueName, String uSex, String uEmail, String uTel) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String uuid = UUID.randomUUID().toString();
		/**
		 * 添加用户信息
		 */
		int result = manageSystemMapper
				.doMsSysSettingAddUserInfo(uuid, uAccount, uPassword, 
						uNickName, Integer.valueOf(uSex).intValue(),
						uEmail, uTel, uTrueName);
		if (1 == result) { // 添加成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
					SysOperateType.ADD_ADMIN_ACCOUNT.getId(),
					uuid, "t_user");				
		} else { // 添加失败
			map.put("status", "false");
			map.put("errorString", "添加失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSysSettingChangeUserInfo(String uId,
			String targetId, String uAccount, String uPassword,
			String uNickName, String uTrueName, String uSex, String uEmail,
			String uTel) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改用户信息
		 */
		int result = manageSystemMapper
				.doMsSysSettingChangeUserInfo(targetId, uAccount, uPassword,
						uNickName, Integer.valueOf(uSex).intValue(),
						uEmail, uTel, uTrueName);
		if (1 == result) { // 修改成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
					SysOperateType.CHANGE_USER_INFO.getId(),
					targetId, "t_user");				
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsSysSettingGetAreaList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map 
				= new ArrayList<Map<String,Object>>();
		/**
		 * 获取全国省份列表
		 * t_areacode:AC_ID、AC_PROVINCE
		 */
		List<AreaCode> provinces = manageSystemMapper
				.msGetProvinceListOfArea();
		for (int i = 0; i < provinces.size(); i++) {
			Map<String,Object> proviceMap = new HashMap<String,Object>();
			proviceMap.put("acId", provinces.get(i).getAcId()); // 主键标识
			proviceMap.put("acProvince", provinces.get(i)
					.getAcProvince()); // 省份名称
			
			/**
			 * 获取某省份下所有的城市
			 * t_areacode:AC_ID、AC_CITY
			 */
			List<AreaCode> citys = manageSystemMapper
					.msGetCityListOfProvince(provinces.get(i).getAcId());
			List<Map<String,Object>> cityList 
					= new ArrayList<Map<String,Object>>();
			for (int j = 0; j < citys.size(); j++) {
				Map<String,Object> cityMap = new HashMap<String,Object>();
				if (!Utils.isEmpty(citys.get(j).getAcCity())) {
					cityMap.put("acId", citys.get(j).getAcId()); // 主键标识
					cityMap.put("acCity", citys.get(j).getAcCity()); // 城市名称
					cityList.add(cityMap);
				}
			}
			proviceMap.put("citys", cityList); // 城市列表
			map.add(proviceMap); // 添加省份列表
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsSysSettingGetCityInfoByCityName(String acCity) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取城市相关数据
		 * t_areacode:AC_ID、AC_CODE、AC_PARENT、AC_CITY
		 */
		AreaCode cityInfo = manageSystemMapper
				.doMsSysSettingGetCityInfoByCityName(acCity);
		if (null != cityInfo) {
			map.put("status", "true");
			map.put("acId", cityInfo.getAcId()); // 主键标识
			map.put("acCode", cityInfo.getAcCode()); // 行政区划代码
			map.put("acCity", cityInfo.getAcCity()); // 城市名称
			/**
			 * 获取城市所属的省份
			 * t_areacode:AC_PROVINCE
			 */
			AreaCode province = manageSystemMapper
					.msGetProvinceByCityName(cityInfo.getAcParent());
			map.put("acProvince", province.getAcProvince()); // 所属省份
			/**
			 * 获取已开通的城市数据
			 * t_open_city:OC_ID、OC_IS_HOT
			 */
			OpenCity openCity = manageSystemMapper
					.msGetOpenCityInfoByAcId(cityInfo.getAcId());
			if (null != openCity) { // 已开通
				map.put("isOpen", "true");
				map.put("ocId", openCity.getAcId()); // 主键标识
				map.put("ocIsHot", openCity.getOcIsHot()); // 是否热门城市
				/**
				 * 获取已开通的城市内已入驻的商家的个数
				 */
				int num = manageSystemMapper
						.msGetStoreNumFromOpenCityByAcId(cityInfo.getAcId());
				map.put("storeNum", num); // 已入驻的商家的个数
			} else { // 未开通
				map.put("isOpen", "false");
			}
		} else {
			map.put("status", "false");
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSysSettingAddOrChangeOpenCityInfo(
			String uId, String acId, String isOpen, String ocIsHot)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取已开通的城市数据
		 * t_open_city:OC_ID、OC_IS_HOT
		 */
		OpenCity openCity = manageSystemMapper.msGetOpenCityInfoByAcId(acId);
		if (null != openCity) { // 已开通
			if ("1".equals(isOpen)) { // open
				/**
				 * 设置已开通城市是否为热门城市
				 */
				int updateResult = manageSystemMapper
						.msChangeOpenCityInfoByOcId(
								Integer.valueOf(ocIsHot).intValue(),
								openCity.getOcId());
				if (1 == updateResult) { // 保存成功
					map.put("status", "true");
					map.put("errorString", "");
					// 插入系统操作日志
					manageSystemMapper
					.doMsAddSysOperateLog(uId, 
							SysOperateType.CHANGE_OPEN_CITY.getId(),
							openCity.getOcId() + ";" + acId,
							"t_open_city;t_areacode");				
				} else { // 保存失败
					map.put("status", "false");
					map.put("errorString", "保存失败！");
					throw new RuntimeException();
				}
			} else if ("0".equals(isOpen)) { // close
				/**
				 * 删除某已开通的城市
				 */
				int delResult = manageSystemMapper
						.msDelOpenCityByOcId(openCity.getOcId());
				if (1 == delResult) { // 保存成功
					map.put("status", "true");
					map.put("errorString", "");
					// 插入系统操作日志
					manageSystemMapper
						.doMsAddSysOperateLog(uId, 
							SysOperateType.DEL_OPEN_CITY.getId(),
							openCity.getOcId() + ";" + acId,
							"t_open_city;t_areacode");				
				} else { // 保存失败
					map.put("status", "false");
					map.put("errorString", "保存失败！");
					throw new RuntimeException();
				}
			}
		} else { // 未开通
			if ("1".equals(isOpen)) { // open 
				String uuid = UUID.randomUUID().toString();
				/**
				 * 新增已开通服务的城市
				 */
				int addResult = manageSystemMapper.msAddOpenCityInfo(uuid,
						acId, Integer.valueOf(ocIsHot).intValue());
				if (1 == addResult) { // 保存成功
					map.put("status", "true");
					map.put("errorString", "");
					// 插入系统操作日志
					manageSystemMapper
						.doMsAddSysOperateLog(uId, 
							SysOperateType.ADD_OPEN_CITY.getId(),
							uuid + ";" + acId,
							"t_open_city;t_areacode");				
				} else { // 保存失败
					map.put("status", "false");
					map.put("errorString", "保存失败！");
					throw new RuntimeException();
				}
			} else if ("0".equals(isOpen)) { // close
				map.put("status", "true");
				map.put("errorString", "");
			}
		}
		
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsSysSettingGetPTDByPtId(String ptId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map 
				= new ArrayList<Map<String,Object>>();
		/**
		 * 获取某商品类别下第一级商品小类列表
		 * t_product_type_detail：PTD_ID、PT_ID、PTD_NAME、PTD_FATHER_ID、
		 * 		P_TAG
		 * t_product_type：PT_NAME
		 * t_pic：P_NAME
		 */
		List<ProductTypeDetail> productTypeDetails = manageSystemMapper
				.msGetFirstPTDByPtId(ptId);
		for (int i = 0; i < productTypeDetails.size(); i++) {
			Map<String,Object> productTypeDetailMap 
					= new HashMap<String,Object>();
			productTypeDetailMap.put("ptdId", productTypeDetails.get(i)
					.getPtdId()); // 商品小类主键标识
			productTypeDetailMap.put("ptdName", productTypeDetails.get(i)
					.getPtdName()); // 商品小类名称
			productTypeDetailMap.put("ptId", productTypeDetails.get(i)
					.getPtId()); // 商品大类主键标识
			productTypeDetailMap.put("ptName", productTypeDetails.get(i)
					.getProductType().getPtName()); // 商品大类名称
			productTypeDetailMap.put("ptdFatherId", productTypeDetails.get(i)
					.getPtdFatherId()); // 父类主键标识
			if (productTypeDetails.get(i)
					.getPtdFatherId().equals(productTypeDetails.get(i)
					.getPtId())) { // 当前小类所属父类与商品大类相同
				productTypeDetailMap.put("ptdFatherName", productTypeDetails.get(i)
						.getProductType().getPtName()); // 父类名称
			} else {
				/**
				 * 根据ptdId获取商品小类名称
				 * t_product_type_detail:PTD_NAME
				 */
				ProductTypeDetail typeDetail = manageSystemMapper
						.msGetProductTypeDetailNameByPtdId(productTypeDetails.get(i)
								.getPtdFatherId());
				if (null != typeDetail) {
					productTypeDetailMap.put("ptdFatherName",
							typeDetail.getPtdName()); // 父类名称
				}
			}
			productTypeDetailMap.put("pTag", productTypeDetails.get(i)
					.getpTag()); // 图片二级标识
			try {
				productTypeDetailMap.put("pName", productTypeDetails.get(i).getPic()
						.getpName()); // 图片名称
			} catch (Exception e) {
				// TODO: handle exception
				productTypeDetailMap.put("pName", ""); // 图片名称
			}
			map.add(productTypeDetailMap);
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddProductTypeInfo(String uId, String ptId, String ptName) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 添加商品大类
		 */
		int result = manageSystemMapper
				.doMsAddProductTypeInfo(ptId, ptName);
		if (1 == result) { // 添加成功
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
				.doMsAddSysOperateLog(uId, 
					SysOperateType.ADD_PRODUCT_TYPE.getId(),
					ptId, "t_product_type");				
		} else { // 添加失败
			map.put("status", "false");
			map.put("errorString", "添加失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeProductTypeDetailInfo(String uId,
			String ptdId, String ptdName, String pTag, String pName)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改商品小类接口
		 */
		int result = manageSystemMapper
				.doMsChangeProductTypeDetailInfo(ptdId, ptdName);
		if (1 == result) { // 修改成功
			/**
			 * 根据pTag修改图片信息
			 */
			int updatePic = manageSystemMapper
					.msChangePNameByPTag(pName, pTag);
			if (1 == updatePic) { // 修改成功
				map.put("status", "true");
				map.put("errorString", "");
				// 插入系统操作日志
				manageSystemMapper
					.doMsAddSysOperateLog(uId, 
						SysOperateType.CHANGE_PRODUCT_TYPE_DETAIL.getId(),
						ptdId + ";" + pTag, 
						"t_product_type_detail;t_pic");		
			} else {
				map.put("status", "false");
				map.put("errorString", "修改失败！");
				throw new RuntimeException();
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "修改失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddProductTypeDetailInfo(String uId, String ptdId, String ptId, String ptdName,
			String ptdFatherId, String pName) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String pTag = UUID.randomUUID().toString();
		/**
		 * 添加商品小类
		 */
		int result = manageSystemMapper
				.doMsAddProductTypeDetailInfo(ptdId, ptId, ptdName,
						ptdFatherId, pTag);
		if (1 == result) { // 添加成功
			/**
			 * 添加商品类别图片
			 */
			int insertPic = manageSystemMapper.msAddPicInfo(
					Utils.PRODUCT_TYPE_PIC, pName, 0, pTag);
			if (1 == insertPic) { // 添加成功
				map.put("status", "true");
				map.put("errorString", "");
				// 插入系统操作日志
				manageSystemMapper
					.doMsAddSysOperateLog(uId, 
						SysOperateType.ADD_PRODUCT_TYPE_DETAIL.getId(),
						ptdId + ";" + pTag, 
						"t_product_type_detail;t_pic");
			} else { // 添加失败
				map.put("status", "false");
				map.put("errorString", "添加失败！");
				throw new RuntimeException();
			}
		} else { // 添加失败
			map.put("status", "false");
			map.put("errorString", "添加失败！");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetProductPropertyList(String ppName,
			String ppValue, String ppTag, String start,
			String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据条件查询商品属性列表
		 * t_product_property:PP_ID、PP_NAME、PP_VALUE、PP_TAG、PP_CHOSE_TYPE、
		 * 		PP_REQUIRED
		 */
		List<ProductProperty> properties = manageSystemMapper
				.doMsGetProductPropertyList(ppName, ppValue, ppTag,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> propertyList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < properties.size(); i++) {
			Map<String,Object> propertyMap = new HashMap<String,Object>();
			propertyMap.put("ppId", properties.get(i).getPpId()); // 主键标识
			propertyMap.put("ppName", properties.get(i)
					.getPpName()); // 属性名称
			propertyMap.put("ppValue", properties.get(i)
					.getPpValue()); // 属性值
			propertyMap.put("ppTag", properties.get(i)
					.getPpTag()); // 属性二级标识
			propertyMap.put("ppChoseType", properties.get(i)
					.getPpChoseType()); // 单选或多选
			propertyMap.put("ppRequired", properties.get(i)
					.getPpRequired()); // 是否必填
			propertyList.add(propertyMap);
		}
		map.put("properties", propertyList); // 属性列表
		/**
		 *  获取满足条件的商品属性的总数
		 */
		int totalCount = manageSystemMapper
				.msGetProductPropertyCount(ppName, ppValue, ppTag);
		map.put("totalCount", totalCount); // 总数
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddProductProperty(String uId, String ppName, String ppValues, String ppTag,
			String ppChoseType, String ppRequired) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String[] ppValue = ppValues.split(";");
		int num = 0;
		for (int i = 0; i < ppValue.length; i++) {
			/**
			 * 添加商品属性
			 */
			int result = manageSystemMapper.doMsAddProductProperty(
					ppName, ppValue[i], ppTag,
					Integer.valueOf(ppChoseType).intValue(),
					Integer.valueOf(ppRequired).intValue());
			if (1 != result) { // 添加失败
				map.put("status", "false");
				map.put("errorString", "添加失败!");
				throw new RuntimeException();
			} else {
				num += 1;
			}
		}
		if (num == ppValue.length) {
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
				SysOperateType.ADD_PRODUCT_PROPERTY.getId(),
				ppTag, "t_product_property");				
		} 
			
		return map;
	}

	@Override
	public Map<String, Object> doMsGetProductTypeRelationPropertiesByPtdId(String ptdId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据商品小类标识搜索商品小类信息
		 * t_product_type_detail: PTD_NAME
		 */
		ProductTypeDetail productTypeDetail = manageSystemMapper
				.msGetProductTypeDetailInfoByPtdId(ptdId);
		if (null != productTypeDetail) { // 是最小的商品类别
			/**
			 * 根据商品小类标识搜索其关联的商品属性信息
			 * t_type_relation_property: PP_TAG
			 */
			List<ProductDetailTypeRelationProductProperty> relationProperties =
					manageSystemMapper.msGetProductPropertiesByPtdId(ptdId);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < relationProperties.size(); i++) {
				if (i == relationProperties.size() - 1) {
					sb.append(relationProperties.get(i)
							.getPpTag());
				} else {
					sb.append(relationProperties.get(i)
						.getPpTag() + ";");
				}
			}
			map.put("status", "true");
			map.put("errorString", "");
			map.put("ppTags", sb);
			map.put("ptdName", productTypeDetail.getPtdName()); // 商品类别名称
		} else { // 不是最小的商品类别
			map.put("status", "false");
			map.put("errorString", "不是最小的商品类别!");
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeProductTypeRelationProperties(String uId, String ptdId, String ppTags)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 先删除该商品类别关联的所有商品属性
		 */
		manageSystemMapper.msDelProductTypeRelationProperties(ptdId);
		/**
		 * 重新插入该商品类别关联的商品属性
		 */
		String[] strings = ppTags.split(";");
		int num = 0;
		for (int i = 0; i < strings.length; i++) {
			int result = manageSystemMapper
					.msAddProductTypeRelationProperty(strings[i], ptdId);
			if (1 != result) {
				map.put("status", "false");
				map.put("errorString", "修改失败!");
				throw new RuntimeException();
			} else {
				num += 1;
			}
		}
		if (num == strings.length) {
			map.put("status", "true");
			map.put("errorString", "");
			// 插入系统操作日志
			manageSystemMapper
			.doMsAddSysOperateLog(uId, 
				SysOperateType.CHANGE_PP_RELATION_PTD.getId(),
				ppTags, 
				"t_product_detail_type_relation_product_property");			
		}
		
		return map;
	}

	@Override
	public Map<String, Object> doMsGetApplyToCashList(String sName, 
			String utcrStatus, String start,
			String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的商户提现列表
		 * t_user_to_cash_record：UTCR_ID、UTCR_STATUS、UTCR_MONEY、UTCR_ACCOUNT、
		 * 		UTCR_CREATE_TIME
		 * t_store：S_NAME
		 * t_user：U_TRUE_NAME
		 */
		List<UserToCashRecord> records = manageSystemMapper
				.doMsGetApplyToCashList(sName, utcrStatus,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> recordList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < records.size(); i++) {
			Map<String,Object> recordMap = new HashMap<String,Object>();
			recordMap.put("utcrId", records.get(i)
					.getUtcrId()); // 主键标识
			recordMap.put("sName", records.get(i).getStore()
					.getsName()); // 商户名称
			recordMap.put("uTrueName", records.get(i).getUser()
					.getuTrueName()); // 申请人姓名
			recordMap.put("utcrStatus", records.get(i)
					.getUtcrStatus()); // 提现状态
			recordMap.put("utcrMoney", records.get(i)
					.getUtcrMoney()); // 提现金额
			recordMap.put("utcrAccount", records.get(i)
					.getUtcrAccount()); // 提现账号
			recordMap.put("utcrCreateTime", records.get(i)
					.getUtcrCreateTime()); // 申请时间
			recordList.add(recordMap);
		}
		map.put("records", recordList); // 提现记录
		/**
		 * 获取满足条件的商户提现记录总数
		 */
		int totalCount = manageSystemMapper
				.msGetApplyToCashCount(sName, utcrStatus);
		map.put("totalCount", totalCount); // 满足条件的商户提现记录总数
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeApplyToCashStatus(String uId, String utcrId, String utcrStatus)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改商户提现状态
		 */
		int result = manageSystemMapper
				.doMsChangeApplyToCashStatus(
						Integer.valueOf(utcrStatus).intValue(), 
						utcrId);
		if (1 == result) {
			/**
			 * 获取商户申请提现信息
			 * t_user_to_cash_record：S_ID、UTCR_STATUS、UTCR_MONEY
			 */
			UserToCashRecord record = manageSystemMapper
					.msGetApplyToCashInfoByUtcrId(utcrId);
			if (1 == record.getUtcrStatus()) { // 已转账
				/**
				 * 修改商户钱包余额
				 */
				int updateResult = manageSystemMapper.msUpdateCashOfUserWallet(
						record.getUtcrMoney(), record.getUtcrMoney(),
						record.getsId());
				if (1 == updateResult) {
					map.put("status", "true");
					map.put("errorString", "");
				} else {
					map.put("status", "false");
					map.put("errorString", "修改失败!");
					throw new RuntimeException();
				}
			}
			// 插入系统操作日志
			manageSystemMapper
				.doMsAddSysOperateLog(uId, 
						SysOperateType.CHANGE_MERCHANT_APPLY_TO_CASH_STATUS
						.getId(), utcrId, 
						"t_user_to_cash_record");				
		} else {
			map.put("status", "false");
			map.put("errorString", "修改失败!");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetPersonalIndexInfo(String uId,
			String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
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
		Store store = manageSystemMapper.msGetStoreLevel(sId);
		if (null != store) {
			map.put("sLevel", store.getsLevel()); // 商户评级
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
		return map;
	}

	@Override
	public List<Map<String, Object>> doMsGetRequirementOrderStatusList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list 
				= new ArrayList<Map<String,Object>>();
		for (RequirementOrderStatus info : RequirementOrderStatus.values()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", info.getId()); // 状态标识
			map.put("name", info.getName()); // 状态名称
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> doMsGetRequirementTypeList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list 
				= new ArrayList<Map<String,Object>>();
		/**
		 * 获取需求类别列表
		 * t_requirement_type:RT_ID、RT_NAME
		 */
		List<RequirementType> types = manageSystemMapper
				.msGetRequirementTypeList();
		for (int i = 0; i < types.size(); i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("rtId", types.get(i).getRtId()); // 主键标识
			map.put("rtName", types.get(i).getRtName()); // 类别名称
			list.add(map);
		}
		return list;
	}

	@Override
	public Map<String, Object> doMsGetRequirementOrderListWithCondition(
			String sId, String roStatus, String rtId,
			String urTitle, String urTrueName, String urTel,
			String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据条件获取某商户的需求订单总数
		 */
		int totalCount = manageSystemMapper
				.msGetRequirementOrderCountWithCondition(sId, roStatus,
						rtId, urTitle, urTrueName, urTel);
		map.put("totalCount", totalCount);	// 某商户的需求订单总数
		/**
		 * 某商户的需求订单列表
		 * t_requirement_order:RO_ID、RO_CREATE_TIME、RO_STATUS、
		 * 		P_TAG、RO_TOTAL_PRICE、RO_ORDER_ID、RO_CONFIRM_TIME、
		 * 		RO_OVER_TIME、RO_GET_TIME、RO_VERIFICATION_TIME
		 * t_user_requirement：UR_TITLE、UR_CONTENT、UR_OFFER_TYPE、
		 * 		UR_TRUE_NAME、UR_TEL、UR_ADDRESS、UR_CREATE_TIME、
		 * 		UR_GET_ADDRESS
		 * t_requirement_type：RT_NAME
		 */
		List<RequirementOrder> orders = manageSystemMapper
				.doMsGetRequirementOrderListWithCondition(sId, roStatus,
						rtId, urTitle, urTrueName, urTel, 
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> orderList 
				= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < orders.size(); i++) {
			Map<String,Object> orderMap = new HashMap<String,Object>();
			orderMap.put("roId", orders.get(i).getRoId()); // 主键标识
			orderMap.put("roCreateTime", orders.get(i)
					.getRoCreateTime()); // 订单创建时间
			orderMap.put("roStatus", orders.get(i).getRoStatus()); // 订单状态
			// 判断是否有货物验证图片
			if (!Utils.isEmpty(orders.get(i).getpTag())) {
				/**
				 * 获取货物验证图片
				 * t_pic:P_FILE_NAME、P_NAME、P_NO、P_JUMP
				 */
				List<Pic> pics = manageSystemMapper
						.msGetPicByPTag(orders.get(i).getpTag());
				List<Map<String,Object>> picList 
					= new ArrayList<Map<String,Object>>();
				for (int j = 0; j < pics.size(); j++) {
					Map<String,Object> picMap = new HashMap<String,Object>();
					picMap.put("url", Utils.PIC_BASE_URL 
							+ pics.get(j).getpFileName()
							+ pics.get(j).getpName()); // 图片地址
					picMap.put("pNo", pics.get(j)
							.getpNo()); // 图片编号
					picList.add(picMap);
				}
				orderMap.put("pics", picList); // 图片列表
			} else {
				orderMap.put("pics", null); // 图片列表
			}
			orderMap.put("roTotalPrice", orders.get(i)
					.getRoTotalPrice()); // 需求总价格
			orderMap.put("roOrderId", orders.get(i)
					.getRoOrderId()); // 订单号
			orderMap.put("roConfirmTime", orders.get(i)
					.getRoConfirmTime()); // 订单确认时间
			orderMap.put("roOverTime", orders.get(i)
					.getRoOverTime()); // 订单完成时间
			orderMap.put("roGetTime", orders.get(i)
					.getRoGetTime()); // 取货时间
			orderMap.put("roVerificationTime", orders.get(i)
					.getRoVerificationTime()); // 用户验货时间
			orderMap.put("urTitle", orders.get(i).getUserRequirement()
					.getUrTitle()); // 需求标题
			orderMap.put("urContent", orders.get(i).getUserRequirement()
					.getUrContent()); // 需求内容
			orderMap.put("urOfferType", orders.get(i).getUserRequirement()
					.getUrOfferType()); // 需求报价类型
			orderMap.put("urTrueName", orders.get(i).getUserRequirement()
					.getUrTrueName()); // 需求发布人姓名
			orderMap.put("urTel", orders.get(i).getUserRequirement()
					.getUrTel()); // 需求发布人联系电话
			orderMap.put("urAddress", orders.get(i).getUserRequirement()
					.getUrAddress()); // 需求发布人联系地址
			orderMap.put("urCreateTime", orders.get(i).getUserRequirement()
					.getUrCreateTime()); // 需求发布时间
			orderMap.put("urGetAddress", orders.get(i).getUserRequirement()
					.getUrGetAddress()); // 取货地址
			orderMap.put("rtName", orders.get(i).getRequirementType()
					.getRtName()); // 需求类别名称
			orderList.add(orderMap);
		}
		map.put("orders", orderList); // 需求列表
		return map;
	}

	@Override
	public Map<String, Object> doMsGetPersonalStoreInfoBySid(String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取某商户的店铺信息接口
		 * t_store：S_ID、S_TYPE、S_NAME、P_TAG、S_DESCRIBE、S_LEADER、
		 * 		S_LEADER_IDCARD、S_LEGAL、S_LEGAL_IDCARD、S_LEADER_PIC、
		 * 		S_LEGAL_PIC、S_BUSINESS_LICENSE_PIC、S_TEL、S_CREATE_TIME、
		 * 		S_ADDRESS、S_LON、S_LAT、S_WEIGHT、S_BOOTH_NUM、
		 * 		S_LEFT_BOOTH_NUM、S_REQUIREMENT_SERVICE_CHARGE、
		 * 		S_ORDER_LIMINT、S_PRODUCT_SERVICE_CHARGE、
		 * 		S_CHECKED、S_LEVEL、S_CHECKED_TIME、S_CHECKED_OPINION
		 * t_areacode：AC_CITY
		 * t_user：U_TRUE_NAME
		 */
		Store store = manageSystemMapper.doMsGetStoreInfoBySid(sId);
		map.put("sId", store.getsId()); // 店铺主键标识
		map.put("sType", store.getsType()); // 店铺类型
		map.put("sName", store.getsName()); // 店铺名称
		/**
		 * 获取店铺封面图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> storePics = manageSystemMapper
				.msGetPicByPTag(store.getpTag());
		List<Map<String,Object>> storePicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < storePics.size(); j++) {
			Map<String,Object> storePicMap = new HashMap<String,Object>();
			storePicMap.put("url", Utils.PIC_BASE_URL 
					+ storePics.get(j).getpFileName()
					+ storePics.get(j).getpName()); // 图片地址
			storePicMap.put("pNo", storePics.get(j)
					.getpNo()); // 图片编号
			storePicList.add(storePicMap);
		}
		map.put("pTag", store.getpTag()); // 店铺封面图片标识
		map.put("storePics", storePicList); // 店铺封面图片
		map.put("acCity", store.getAreaCode()
				.getAcCity()); // 店铺所在城市
		map.put("sDescribe", store.getsDescribe()); // 店铺描述
		map.put("sLeader", store.getsLeader()); // 店铺负责人
		map.put("sLeaderIdCard", store
				.getsLeaderIdCard()); // 店铺负责人身份证号
		map.put("sLegal", store.getsLegal()); // 店铺法人
		map.put("sLegalIdCard", store
				.getsLegalIdCard()); // 店铺法人身份证号
		/**
		 * 获取店铺负责人身份证图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sLeaderPics = manageSystemMapper
				.msGetPicByPTag(store.getsLeaderPic());
		List<Map<String,Object>> sLeaderPicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sLeaderPics.size(); j++) {
			Map<String,Object> sLeaderPicMap = new HashMap<String,Object>();
			sLeaderPicMap.put("url", Utils.PIC_BASE_URL 
					+ sLeaderPics.get(j).getpFileName()
					+ sLeaderPics.get(j).getpName()); // 图片地址
			sLeaderPicMap.put("pNo", sLeaderPics.get(j)
					.getpNo()); // 图片编号
			sLeaderPicList.add(sLeaderPicMap);
		}
		map.put("sLeaderPics", sLeaderPicList); // 店铺负责人身份证图片
		/**
		 * 获取店铺法人身份证图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sLegalPics = manageSystemMapper
				.msGetPicByPTag(store.getsLegalPic());
		List<Map<String,Object>> sLegalPicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sLegalPics.size(); j++) {
			Map<String,Object> sLegalPicMap = new HashMap<String,Object>();
			sLegalPicMap.put("url", Utils.PIC_BASE_URL 
					+ sLegalPics.get(j).getpFileName()
					+ sLegalPics.get(j).getpName()); // 图片地址
			sLegalPicMap.put("pNo", sLegalPics.get(j)
					.getpNo()); // 图片编号
			sLegalPicList.add(sLegalPicMap);
		}
		map.put("sLegalPics", sLegalPicList); // 店铺法人身份证图片
		/**
		 * 获取店铺营业执照图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sBuinessLicensePics = manageSystemMapper
				.msGetPicByPTag(store
						.getsBuinessLicensePic());
		List<Map<String,Object>> sBuinessLicensePicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sBuinessLicensePics.size(); j++) {
			Map<String,Object> sBuinessLicensePicMap = new HashMap<String,Object>();
			sBuinessLicensePicMap.put("url", Utils.PIC_BASE_URL 
					+ sBuinessLicensePics.get(j).getpFileName()
					+ sBuinessLicensePics.get(j)
					.getpName()); // 图片地址
			sBuinessLicensePicMap.put("pNo", sBuinessLicensePics
					.get(j).getpNo()); // 图片编号
			sBuinessLicensePicList.add(sBuinessLicensePicMap);
		}
		map.put("sBuinessLicensePics",
				sBuinessLicensePicList); // 店铺营业执照图片
		map.put("sTel", store.getsTel()); // 店铺联系电话
		map.put("sCreateTime", store.getsCreateTime()); // 店铺创建时间
		map.put("sAddress", store.getsAddress()); // 店铺地址
		map.put("sLon", store.getsLon()); // 店铺经度
		map.put("sLat", store.getsLat()); // 店铺纬度
		map.put("sWeight", store.getsWeight()); // 店铺权重
		map.put("sBoothNum", store.getsBoothNum()); // 店铺推荐展位总个数
		map.put("sLeftBoothNum", store
				.getsLeftBoothNum()); // 店铺剩余推荐展位个数
		map.put("sRequirementServiceCharge", store
				.getsRequirementServiceCharge()); // 店铺需求交易服务费
		map.put("sOrderLimint", store
				.getsOrderLimint()); // 店铺订单交易限额
		map.put("sProductServiceCharge", store
				.getsProductServiceCharge()); // 店铺商品交易服务费
		map.put("sChecked", store
				.getsChecked()); // 店铺审核状态
		map.put("sLevel", store.getsLevel()); // 店铺评级

		try {
			map.put("uTrueName", store.getUser()
					.getuTrueName()); // 审核人姓名
			map.put("sCheckedTime", store
					.getsCheckedTime()); // 店铺审核时间
			map.put("sCheckedOpinion", store
					.getsCheckedOpinion()); // 店铺审核意见
		} catch (Exception e) {
			// TODO: handle exception
			map.put("uTrueName", ""); // 审核人姓名
			map.put("sCheckedTime", ""); // 店铺审核时间
			map.put("sCheckedOpinion", ""); // 店铺审核意见
		}
		/**
		 * 获取特定店铺钱包信息
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT
		 */
		UserWallet userWallet = manageSystemMapper
				.msGetStoreWalletListBySId(sId);
		if (null != userWallet) {
			map.put("uwLeftMoney", userWallet.getUwLeftMoney()); // 可用余额
			map.put("uwDeposit", userWallet
					.getUwDeposit()); // 已缴纳（冻结）的保障金
		} else {
			map.put("uwLeftMoney", 0); // 可用余额
			map.put("uwDeposit", 0); // 已缴纳（冻结）的保障金
		}
		// 只有企业商户可以发布商品
		map.put("productNum", 0); // 已发布的商品的总数
//		if ("1".equals(sType)) { // 企业商户 只有企业商户可以发布商品
//			/**
//			 * 获取特定店铺已发布的商品的总数
//			 */
//			int productNum = manageSystemMapper.msGetStoreProductNum(sId);
//			map.put("productNum", productNum); // 已发布的商品的总数
//		} else {
//			map.put("productNum", 0); // 已发布的商品的总数
//		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangePersonalStoreInfoBySid(String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改店铺基本信息	
		 */
		int result = manageSystemMapper
				.doMsChangeStoreInfoBySid(sId, sDescribe, sTel,
						sAddress, sLon, sLat);
		if (1 == result) { // 修改成功
			/**
			 * 更改店铺关联的宣传图片
			 * 1、删除关联图片
			 * 2、新增关联图片
			 */
			// 解析图片数据
			PicVo picVo = new PicVo();
			picVo = Utils.parserJsonResult(picData, PicVo.class);
			if (null != picVo) {
				/**
				 * 根据pTag删除数据库中的图片
				 */
				manageSystemMapper
						.msDelPicsByPtag(picVo.getPTag());
				/**
				 * 添加图片
				 */
				for (int i = 0; i < picVo.getPics().size(); i++) {
					manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
							picVo.getPics().get(i).getPName(),
							picVo.getPics().get(i).getPNo(),
							picVo.getPTag());
				}
			}
			map.put("status", "true");
			map.put("errorString", "");
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败!");
			throw new RuntimeException();
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetStatusOfSafetyQuestions(String uId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 判断是否设置过密保问题
		 */
		int num = manageSystemMapper.doMsGetStatusOfSafetyQuestions(uId);
		if (1 == num) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "");
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetSafetyQuestionsList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取所有安全问题列表
		 * t_safety_question:SQ_ID、SQ_NAME、SQ_POSITION
		 */
		List<SafetyQuestion> questions = manageSystemMapper
				.doMsGetSafetyQuestionsList();
		for (int i = 0; i < questions.size(); i++) {
			Map<String,Object> questionMap = new HashMap<String,Object>();
			questionMap.put("sqId", questions.get(i).getSqId()); // 安全问题主键标识
			questionMap.put("sqName", questions.get(i).getSqName()); // 问题名称
			questionMap.put("sqPosition", questions.get(i)
					.getSqPosition()); // 问题位置
			map.add(questionMap);
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsSetSafetyQuestionForUser(String uId, 
			String firstSqId, String firstSqAnswer,
			String secondSqId, String secondSqAnswer, String thirdSqId, 
			String thirdSqAnswer) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 设置安全问题
		 */
		int result = manageSystemMapper
				.doMsSetSafetyQuestionForUser(uId, firstSqId, firstSqAnswer,
						secondSqId, secondSqAnswer, thirdSqId, thirdSqAnswer);
		if (1 == result) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "设置失败!");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetSafetyQuestionOfUser(String uId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取某用户已设置的安全问题
		 * t_user:U_FIRST_SQ_ID、U_SECOND_SQ_ID、U_THIRD_SQ_ID
		 */
		User user = manageSystemMapper.msGetSafetyQuestionIdOfUser(uId);
		if (null != user) {
			try {
				Map<String,Object> questionMap1 = new HashMap<String,Object>();
				/**
				 * 根据安全问题标识获取安全问题相关信息接口
				 * t_safety_question:SQ_ID、SQ_NAME、SQ_POSITION
				 */
				SafetyQuestion question1 = manageSystemMapper
						.msGetSafetyQuestionInfoBySqId(user.getuFirstSqId());
				if (null != question1) {
					questionMap1.put("sqId", question1.getSqId()); // 安全问题主键标识
					questionMap1.put("sqName", question1.getSqName()); // 问题名称
					int sqPosition = question1.getSqPosition();
					String sqPosition1 = String.valueOf(sqPosition);
					questionMap1.put("sqPosition",sqPosition1); // 问题位置
					map.add(questionMap1);
				}
				Map<String,Object> questionMap2 = new HashMap<String,Object>();
				/**
				 * 根据安全问题标识获取安全问题相关信息接口
				 * t_safety_question:SQ_ID、SQ_NAME、SQ_POSITION
				 */
				SafetyQuestion question2 = manageSystemMapper
						.msGetSafetyQuestionInfoBySqId(user.getuSecondSqId());
				if (null != question2) {
					questionMap2.put("sqId", question2.getSqId()); // 安全问题主键标识
					questionMap2.put("sqName", question2.getSqName()); // 问题名称
					int sqPosition = question2.getSqPosition();
					String sqPosition1 = String.valueOf(sqPosition);
					questionMap2.put("sqPosition", sqPosition1); // 问题位置
					map.add(questionMap2);
				}
				
				
				Map<String,Object> questionMap3 = new HashMap<String,Object>();
				/**
				 * 根据安全问题标识获取安全问题相关信息接口
				 * t_safety_question:SQ_ID、SQ_NAME、SQ_POSITION
				 */
				SafetyQuestion question3 = manageSystemMapper
						.msGetSafetyQuestionInfoBySqId(user.getuThirdSqId());
				if (null != question3) {
					questionMap3.put("sqId", question3.getSqId()); // 安全问题主键标识
					questionMap3.put("sqName", question3.getSqName()); // 问题名称
					int sqPosition = question3.getSqPosition();
					String sqPosition1 = String.valueOf(sqPosition);
					questionMap3.put("sqPosition", sqPosition1); // 问题位置
					map.add(questionMap3);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doMsCheckSafetyQuestionOfUser(String uId, String questionData) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		SafetyQuestionVo safetyQuestionVos = new SafetyQuestionVo();
		safetyQuestionVos = Utils.parserJsonResult(questionData, 
				SafetyQuestionVo.class);
		/**
		 * 获取某用户已设置的安全问题答案
		 * t_user:U_FIRST_SQ_ANSWER、U_SECOND_SQ_ANSWER、U_THIRD_SQ_ANSWER
		 */
		User user = manageSystemMapper.msGetSafetyQuestionAnswerOfUser(uId);
		int num = 0; // num为3时，标识提交的安全问题答案全部正确
		if (null != user) {
			try {
				for (int i = 0; i < safetyQuestionVos.getData().size(); i++) {
					if (1 == safetyQuestionVos.getData().get(i).getSqPosition()) {
						if (user.getuFirstSqAnswer().equals(
								safetyQuestionVos.getData().get(i)
								.getSqAnswer())) {
							num += 1;
						}
					}
					if (2 == safetyQuestionVos.getData().get(i).getSqPosition()) {
						if (user.getuSecondSqAnswer().equals(
								safetyQuestionVos.getData().get(i)
								.getSqAnswer())) {
							num += 1;
						}
					}
					if (3 == safetyQuestionVos.getData().get(i).getSqPosition()) {
						if (user.getuThirdSqAnswer().equals(
								safetyQuestionVos.getData().get(i)
								.getSqAnswer())) {
							num += 1;
						}
					}
				}
				if (3 == num) {
					map.put("status", "true");
					map.put("errorString", "");
				} else {
					map.put("status", "false");
					map.put("errorString", "您提交的安全问题答案有误！");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doMsRetrievePassword(String uId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 找回密码
		 * t_user：U_PASSWORD
		 */
		User user = manageSystemMapper.doMsRetrievePassword(uId);
		if (null != user) {
			map.put("status", "true");
			map.put("errorString", "");
			map.put("uPassword", user.getuPassword()); // 用户密码
		} else {
			map.put("status", "false");
			map.put("errorString", "不存在该用户！");
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangePassword(String uId, String oldPassword, String newPassword) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 查找用户密码
		 * t_user：U_PASSWORD
		 */
		User user = manageSystemMapper.doMsRetrievePassword(uId);
		if (null != user) {
			if (oldPassword.equals(user.getuPassword())) {
				/**
				 * 修改用户密码
				 */
				int result = manageSystemMapper.doMsChangePassword(uId,
						newPassword);
				if (1 == result) {
					map.put("status", "true");
					map.put("errorString", "");
				} else {
					map.put("status", "false");
					map.put("errorString", "修改失败，请稍后重试！");
					throw new RuntimeException();
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "当前密码错误！");
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "不存在该用户！");
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doMsGetMyWallet(String uId, String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商户钱包相关数据
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
		 * 		UW_ALREADY_TO_CASH
		 */
		UserWallet userWallet = manageSystemMapper.doMsGetMyWallet(sId);
		if (null != userWallet) {
			map.put("status", "true");
			map.put("errorString", "");
			map.put("uwLeftMoney", userWallet.getUwLeftMoney()); // 可用余额
			map.put("uwDeposit", userWallet.getUwDeposit()); // 冻结（已缴纳）的保障金
			map.put("uwApplyToCash", userWallet
					.getUwApplyToCash()); // 申请提现的金额
			map.put("uwAlreadyToCash", userWallet
					.getUwAlreadyToCash()); // 已经提现的金额
			/**
			 * 获取商户申请解冻的保障金金额
			 */
			double applyToFreeDepositMoney = manageSystemMapper
					.msGetApplyToFreeDepositMoney(sId);
			map.put("uwApplyToFreeDepositMoney", applyToFreeDepositMoney);
			/**
			 * 获取店铺信息(接单限额、需求交易手续费率、商品交易手续费率)
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			map.put("sRequirementServiceCharge", store.getsRequirementServiceCharge()); // 需求手续费率
			map.put("sProductServiceCharge", store.getsProductServiceCharge()); // 商品手续费率
			map.put("sOrderLimint", store.getsOrderLimint()); // 需求接单限额
		} else {
			map.put("status", "false");
			map.put("errorString", "该商户不存在！");
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetPayStyleList(String uId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取全部支付方式列表
		 * t_pay_style：PS_ID、PS_NAME
		 * t_pic：P_NAME
		 */
		List<PayStyle> payStyles = manageSystemMapper.doMsGetPayStyleList();
		for (int i = 0; i < payStyles.size(); i++) {
			Map<String,Object> styleMap = new HashMap<String,Object>();
			styleMap.put("psId", payStyles.get(i).getPsId()); // 支付方式主键标识
			styleMap.put("psName", payStyles.get(i).getPsName()); // 支付方式名称
			styleMap.put("url", Utils.PIC_BASE_URL + Utils.COMMON_PIC + payStyles.get(i).getPic()
					.getpName()); // 图片路径
			map.add(styleMap);
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsApplyToCash(String uId, String sId,
			String psId, String utcrMoney, 
			String utcrAccount) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商户钱包相关数据
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
		 * 		UW_ALREADY_TO_CASH
		 */
		UserWallet userWallet = manageSystemMapper.doMsGetMyWallet(sId);
		if (null != userWallet) {
			// 申请提现的金额小于或等于账户内的可用余额
			if (Double.valueOf(utcrMoney).doubleValue() <= userWallet
					.getUwLeftMoney()) {
				/**
				 * 添加提现记录
				 */
				int result = manageSystemMapper
						.doMsApplyToCash(uId, sId, psId, 
								Double.valueOf(utcrMoney).doubleValue(),
								utcrAccount);
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
					int changeResult = manageSystemMapper
							.msChangeUserWalletOfLeftMoney(uwLeftMoney,
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
	
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
//	@Override
//	public Map<String, Object> doMsAddDepositRecord(String uId, String sId,
//			String psId, String udrMoney)
//			throws Exception {
//		// TODO Auto-generated method stub
//		Map<String,Object> map = new HashMap<String,Object>();
//		/**
//		 * 获取商户钱包相关数据
//		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
//		 * 		UW_ALREADY_TO_CASH
//		 */
//		UserWallet userWallet = manageSystemMapper.doMsGetMyWallet(sId);
//		if (null != userWallet) {
//			/**
//			 * 添加保障金缴纳记录
//			 */
//			int result = manageSystemMapper
//					.doMsAddDepositRecord(uId, sId, psId,
//					Double.valueOf(udrMoney).doubleValue(), "");
//			if (1 == result) {
//				double uwDeposit = userWallet.getUwDeposit() + Double
//						.valueOf(udrMoney).doubleValue();
//				/**
//				 * 修改商户钱包冻结（已缴纳）的保障金金额
//				 */
//				int changeResult = manageSystemMapper
//						.msChangeUserWalletOfDeposit(uwDeposit,
//								sId);
//				if (1 == changeResult) {
//					map.put("status", "true");
//					map.put("errorString", "");
//				} else {
//					map.put("status", "false");
//					map.put("errorString", "系统繁忙，请稍后重试！");
//					throw new RuntimeException();
//				}
//			} else {
//				map.put("status", "false");
//				map.put("errorString", "系统繁忙，请稍后重试！");
//				throw new RuntimeException();
//			}
//		} else {
//			map.put("status", "false");
//			map.put("errorString", "该商户不存在！");
//		}
//		return map;
//	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsApplyToFreeForDepositRecord(String uId, String sId, String udrMoney)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取商户钱包相关数据
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
		 * 		UW_ALREADY_TO_CASH
		 */
		UserWallet userWallet = manageSystemMapper.doMsGetMyWallet(sId);
		if (null != userWallet) {
			// 申请解冻的保障金小于或等于冻结（已缴纳）的保障金
			if (Double.valueOf(udrMoney).doubleValue() <= userWallet
					.getUwDeposit()) {
				int result = manageSystemMapper
						.doMsApplyToFreeForDepositRecord(uId, sId,
								Double.valueOf(udrMoney).doubleValue());
				if (1 == result) {
					double uwDeposit = userWallet.getUwDeposit() - Double
							.valueOf(udrMoney).doubleValue();
					double uwLeftMoney = userWallet.getUwLeftMoney() + Double
							.valueOf(udrMoney).doubleValue();
					/**
					 * 修改商户钱包冻结（已缴纳）的保障金金额
					 */
					int changeResult = manageSystemMapper
							.msChangeUserWalletByDeposit(uwLeftMoney,uwDeposit,
									sId);
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
				map.put("errorString", "可申请解冻的保障金不足！");
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "该商户不存在！");
		}
		return map;
	}

	@Override
	public Map<String, Object> doMsGetApplyToCashListBySId(String uId, String sId,
			String utcrStatus, String psId, String start, String end)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的商户提现记录总数
		 */
		int totalCount = manageSystemMapper
				.msGetApplyToCashListCount(sId, utcrStatus, psId);
		map.put("totalCount", totalCount); // 记录总数
		/**
		 * 获取商户提现记录列表
		 * t_user_to_cash_record：UTCR_ID、UTCR_STATUS、UTCR_MONEY、UTCR_ACCOUNT、
		 * 		UTCR_CREATE_TIME
		 * t_store：S_NAME
		 * t_user：U_TRUE_NAME
		 * t_pay_style：PS_NAME
		 */
		List<UserToCashRecord> records = manageSystemMapper
				.doMsGetApplyToCashListBySId(sId, utcrStatus, psId,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> recordList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < records.size(); i++) {
			Map<String,Object> recordMap = new HashMap<String,Object>();
			recordMap.put("utcrId", records.get(i).getUtcrId()); // 主键标识
			recordMap.put("utcrStatus", records.get(i)
					.getUtcrStatus()); // 提现状态 0--申请提现，1--已转账
			recordMap.put("utcrMoney", records.get(i)
					.getUtcrMoney()); // 金额
			recordMap.put("utcrAccount", records.get(i)
					.getUtcrAccount()); // 提现账号
			recordMap.put("utcrCreateTime", records.get(i)
					.getUtcrCreateTime()); // 时间
			recordMap.put("sName", records.get(i).getStore()
					.getsName()); // 商户名称
			recordMap.put("uTrueName", records.get(i).getUser()
					.getuTrueName()); // 申请提现人
			recordMap.put("psName", records.get(i).getPayStyle()
					.getPsName()); // 提现方式名称
			recordList.add(recordMap);
		}
		map.put("records", recordList); // 提现记录
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetMyArrangeListBySId(String uId, String sId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 	获取商户我的安排列表
		 * t_store_arrange：SA_ID、SA_CONTENT、SA_CREATE_TIME
		 * t_store：S_NAME
		 * t_user：U_TRUE_NAME
		 */
		List<StoreArrange> arranges = manageSystemMapper
				.doMsGetMyArrangeListBySId(sId);
		for (int i = 0; i < arranges.size(); i++) {
			Map<String,Object> arrangeMap = new HashMap<String,Object>();
			arrangeMap.put("saId", arranges.get(i).getSaId()); // 主键标识
			arrangeMap.put("saContent", arranges.get(i)
					.getSaContent()); // 安排内容
			arrangeMap.put("saCreateTime", arranges.get(i)
					.getSaCreateTime()); // 安排创建时间
			arrangeMap.put("sName", arranges.get(i).getStore()
					.getsName()); // 商户名称
			arrangeMap.put("uTrueName", arranges.get(i).getUser()
					.getuTrueName()); // 安排发布人姓名
			map.add(arrangeMap);
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddMyArrange(String uId, String sId, String saContent) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 商户添加我的安排
		 */
		int result = manageSystemMapper.doMsAddMyArrange(saContent, uId, sId);
		if (1 == result) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "系统繁忙，请稍后重试！");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMSUpdateMyArrange(String saId, String saContent) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 商户添加我的安排
		 */
		int result = manageSystemMapper.doMSUpdateMyArrange(saId, saContent);
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
	public Map<String, Object> doMsGetCompanyIndexInfo(String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
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
		 * 获取企业商户我的评级
		 * t_store:S_LEVEL
		 */
		Store store = manageSystemMapper.msGetStoreLevel(sId);
		if (null != store) {
			map.put("sLevel", store.getsLevel()); // 商户评级
		} else {
			map.put("sLevel", ""); // 商户评级
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
		return map;
	}
	
	@Override
	public Map<String, Object> doMsGetProductListBySId(String sId, String pName,
			String ptId, String start, String end)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取满足条件的商品的总数
		 */
		int totalCount = manageSystemMapper
				.msGetSatisfactionConditionProductCount(sId, pName, ptId);
		map.put("totalCount", totalCount); // 满足条件的商品的总数
		/**
		 * 获取满足条件的商品列表
		 * t_product：P_ID、P_NAME、P_BRAND、P_DESCRIBE、P_TAG、P_ORIGINAL_PRICE、
		 * 		P_NOW_PRICE、P_CREATE_TIME、P_DEL、P_CHECKED、P_HTML、P_BROWSE_NUM、
		 * 		P_HAVE_BOOTH、P_TOTAL_NUM、P_STOCK_NUM、P_CHECKED_TIME、
		 * 		P_CHECKED_OPINION
		 * t_store：S_NAME
		 * t_user：U_TRUE_NAME
		 * t_product_type_detail：PTD_NAME
		 */
		List<Product> products = manageSystemMapper
				.doMsGetProductListBySId(sId, pName, ptId,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < products.size(); i++) {
			Map<String,Object> productMap = new HashMap<String,Object>();
			productMap.put("pId", products.get(i).getpId()); // 商品主键标识
			productMap.put("pName", products.get(i).getpName()); // 商品名称
			productMap.put("pBrand", products.get(i).getpBrand()); // 商品品牌
			productMap.put("pDescribe", products.get(i)
					.getpDescribe()); // 商品描述
			productMap.put("pTag", products.get(i).getpTag()); // 商品图片二级标识
			/**
			 * 根据pTag获取商品图片列表
			 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
			 */
			List<Pic> pics = manageSystemMapper
					.msGetPicByPTag(products.get(i).getpTag());
			List<Map<String,Object>> picList = new ArrayList<Map<String,Object>>();
			for (int j = 0; j < pics.size(); j++) {
				Map<String,Object> picMap = new HashMap<String,Object>();
				picMap.put("url", Utils.PIC_BASE_URL 
						+ Utils.PRODUCT_PIC + pics.get(j)
						.getpName()); // 图片地址
				picMap.put("no", pics.get(j).getpNo()); // 图片编号
				picList.add(picMap);
			}
			productMap.put("pics", picList); // 商品图片列表
			productMap.put("pOriginalPrice", products.get(i)
					.getpOriginalPrice()); // 商品原价
			productMap.put("pNowPrice", products.get(i)
					.getpNowPrice()); // 商品现价
			productMap.put("pCreateTime", products.get(i)
					.getpCreateTime()); // 商品添加时间
			productMap.put("pDel", products.get(i)
					.getpDel()); // 商品是否被删除 0--否，1--是
			productMap.put("pChecked", products.get(i)
					.getpChecked()); // 商品审核状态  0--否，1--是
			try {
				productMap.put("uTrueName", products.get(i).getUser()
						.getuTrueName()); // 商品审核人
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("uTrueName", ""); // 商品审核人
			}
			try {
				productMap.put("pCheckedTime", products.get(i)
						.getpCheckedTime()); // 商品审核时间
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("pCheckedTime", ""); // 商品审核时间
			}
			try {
				productMap.put("pCheckedOpinion", products.get(i)
						.getpCheckedOpinion()); // 商品审核意见
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("pCheckedOpinion", ""); // 商品审核意见
			}
			try {
				productMap.put("pHtml", new String(products.get(i)
						.getpHtml())); // 商品图文详情
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("pHtml", ""); // 商品图文详情
			}
			productMap.put("pBrowseNum", products.get(i)
					.getpBrowseNum()); // 商品被浏览次数
			productMap.put("pHaveBooth", products.get(i)
					.getpHaveBooth()); // 是否推荐商品 0--否，1--是
			productMap.put("pTotalNum", products.get(i)
					.getpTotalNum()); // 商品总数
			productMap.put("pStockNum", products.get(i)
					.getpStockNum()); // 商品库存数
			productMap.put("ptdId", products.get(i).getPtdId()); // 商品小类主键标识
			productMap.put("ptdName", products.get(i).getProductTypeDetail()
					.getPtdName()); // 商品小类名称
			productMap.put("sName", products.get(i).getStore()
					.getsName()); // 店铺名称
			productList.add(productMap);
		}
		map.put("products", productList); // 商品列表
		return map;
	}
	
	@Override
	public Map<String, Object> msGetBoothBySId(String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		Store store = manageSystemMapper.msGetBoothBySId(sId);
		if (null != store) {
			map.put("status", "true");
			map.put("errorString", "");
			map.put("sBoothNum", store.getsBoothNum()); // 推荐橱窗总数量
			map.put("sLeftBoothNum", store.getsLeftBoothNum()); // 剩余数量
		} else {
			map.put("status", "false");
			map.put("errorString", "该商户不存在!");
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetAllProductPropertyListOfProduct(String pId, String ptdId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 根据商品小类标识搜索其关联的商品属性信息
		 */
		List<ProductDetailTypeRelationProductProperty> properties 
			= manageSystemMapper.msGetProductPropertiesByPtdId(ptdId);
		for (int i = 0; i < properties.size(); i++) {
			Map<String,Object> propertyMap = new HashMap<String,Object>();
			List<Map<String,Object>> propertyMapList 
				= new ArrayList<Map<String,Object>>();
			/**
			 * 获取某商品没有选择的商品属性列表
			 * t_product_property：PP_ID、PP_NAME、PP_VALUE、PP_TAG、PP_CHOSE_TYPE、
			 * 		PP_REQUIRED
			 */
			List<ProductProperty> productProperties = manageSystemMapper
					.msGetProductPropertyListWhichNotSelect(properties.get(i)
							.getPpTag(), pId);
			for (int j = 0; j < productProperties.size(); j++) {
				Map<String,Object> productPropertiesMap = new HashMap<String,Object>();
				productPropertiesMap.put("ppId", productProperties.get(j)
						.getPpId()); // 主键标识
				productPropertiesMap.put("ppValue", productProperties.get(j)
						.getPpValue()); // 属性值
				productPropertiesMap.put("ppTag", productProperties.get(j)
						.getPpTag()); // 属性二级标识
				productPropertiesMap.put("ppChoseType", productProperties.get(j)
						.getPpChoseType()); // 商品属性单选多选 0--单选，1--多选
				productPropertiesMap.put("ppRequired", productProperties.get(j)
						.getPpRequired()); // 商品属性是否必填 0--非必填，1--必填
				productPropertiesMap.put("isSelect", "false"); // 未选中
				propertyMapList.add(productPropertiesMap);
			}
			
			/**
			 * 获取某商品已选择的商品属性列表
			 * t_product_property：PP_ID、PP_NAME、PP_VALUE、PP_TAG、PP_CHOSE_TYPE、
			 * 		PP_REQUIRED
			 */
			List<ProductProperty> productProperties2 = manageSystemMapper
					.msGetProductPropertyListWhichSelect(properties.get(i)
							.getPpTag(), pId);
			for (int j = 0; j < productProperties2.size(); j++) {
				Map<String,Object> productPropertiesMap = new HashMap<String,Object>();
				productPropertiesMap.put("ppId", productProperties2.get(j)
						.getPpId()); // 主键标识
				productPropertiesMap.put("ppValue", productProperties2.get(j)
						.getPpValue()); // 属性值
				productPropertiesMap.put("ppTag", productProperties2.get(j)
						.getPpTag()); // 属性二级标识
				productPropertiesMap.put("ppChoseType", productProperties2.get(j)
						.getPpChoseType()); // 商品属性单选多选 0--单选，1--多选
				productPropertiesMap.put("ppRequired", productProperties2.get(j)
						.getPpRequired()); // 商品属性是否必填 0--非必填，1--必填
				productPropertiesMap.put("isSelect", "true"); // 选中
				propertyMapList.add(productPropertiesMap);
			}
			propertyMap.put("ppName", productProperties.get(0).getPpName()); // 属性名称
			propertyMap.put("details", propertyMapList);
			map.add(propertyMap);
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeProductInfo(String sId, String pId, String pName,
			String pBrand, String pDescribe, String picData, 
			String pOriginalPrice, String pNowPrice, String pWeight, 
			String pHtml, String pHaveBooth, String pStockNum, String ppIds) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取某商品的库存数量
		 * t_product：P_TOTAL_NUM、P_STOCK_NUM、P_HAVE_BOOTH
		 */
		Product product = manageSystemMapper.msGetProductStockNum(pId);
		if (null != product) {
			int pTotalNum = product.getpTotalNum();
			pTotalNum += Integer.valueOf(pStockNum).intValue() - product
					.getpStockNum();
			/**
			 * 修改商品基本信息
			 */
			int result = manageSystemMapper.msChangeProductBaseInfo(pId, pName, 
					pBrand, pDescribe, 
					Double.valueOf(pOriginalPrice).doubleValue(),
					Double.valueOf(pNowPrice).doubleValue(),
					Integer.valueOf(pWeight).intValue(), pHtml,
					Integer.valueOf(pHaveBooth).intValue(),
					pTotalNum, Integer.valueOf(pStockNum).intValue());
			if (1 == result) { // 商品基本信息修改成功
				// 是否为推荐商品 是--减少店铺剩余展位的数量
				if (1 == product.getpHaveBooth()) {// 商品原来为推荐商品
					if (1 != Integer.valueOf(pHaveBooth).intValue()) { // 现在为非推荐商品
						/**
						 * 修改某店铺剩余展位的数量 0--减 1--加
						 */
						manageSystemMapper.msUpdateStoreLeftBoothNum(sId, 1);
					}
				} else {// 商品原来为非推荐商品
					if (1 == Integer.valueOf(pHaveBooth).intValue()) { // 现在为推荐商品
						/**
						 * 修改某店铺剩余展位的数量 0--减 1--加
						 */
						manageSystemMapper.msUpdateStoreLeftBoothNum(sId, 0);
					}
				}
				
				// 修改商品图片 商品图片如无修改，则picData为空字符串
				if (!Utils.isEmpty(picData)) { // 商品图片有修改
					PicVo picVo = new PicVo();
					picVo = Utils.parserJsonResult(picData, PicVo.class);
					if (null != picVo) {
						/**
						 * 根据pTag删除数据库中的图片
						 */
						manageSystemMapper
								.msDelPicsByPtag(picVo.getPTag());
						/**
						 * 添加图片
						 */
						for (int i = 0; i < picVo.getPics().size(); i++) {
							manageSystemMapper.msAddPicInfo(Utils.PRODUCT_PIC,
									picVo.getPics().get(i).getPName(),
									picVo.getPics().get(i).getPNo(),
									picVo.getPTag());
						}
					}
				}
				// 修改商品关联的属性 商品属性如无修改，则ppIds为空字符串
				if (!Utils.isEmpty(ppIds)) { // 商品属性有修改
					/**
					 * 删除该商品所有的关联属性
					 */
					manageSystemMapper
							.msDelProductPropertiesByPId(pId);
					String[] ppIdStrings = ppIds.split(";");
					for (int i = 0; i < ppIdStrings.length; i++) {
						/**
						 * 添加商品关联的商品属性
						 */
						manageSystemMapper
							.msAddProductPropertiesByPId(pId, ppIdStrings[i]);
					}
				}
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("status", "false");
				map.put("errorString", "系统繁忙，请稍后重试！");
				throw new RuntimeException();
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "该商品不存在！");
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsAddProductInfo(String uId, String sId, String pName, String pBrand, String pDescribe,
			String picData, String pOriginalPrice, String pNowPrice, String ptdId, String pWeight, String pHtml,
			String pHaveBooth, String pStockNum, String ppIds) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String pId = UUID.randomUUID().toString();
		String pTag = UUID.randomUUID().toString();
		/**
		 * 插入商品基本信息
		 */
		int result = manageSystemMapper.msAddProductBaseInfo(pId, sId, pName,
				pBrand, pDescribe, pTag, 
				Double.valueOf(pOriginalPrice).doubleValue(),
				Double.valueOf(pNowPrice).doubleValue(), ptdId,
				Integer.valueOf(pWeight).intValue(),
				pHtml, Integer.valueOf(pHaveBooth).intValue(),
				Integer.valueOf(pStockNum).intValue(), uId);
		if (1 == result) { // 商品基本信息插入成功
			if (!Utils.isEmpty(picData)) {
				PicVo picVo = new PicVo();
				picVo = Utils.parserJsonResult(picData, PicVo.class);
				if (null != picVo) {
					/**
					 * 添加图片
					 */
					for (int i = 0; i < picVo.getPics().size(); i++) {
						manageSystemMapper.msAddPicInfo(Utils.PRODUCT_PIC,
								picVo.getPics().get(i).getPName(),
								picVo.getPics().get(i).getPNo(),
								pTag);
					}
				}
			}
			if (!Utils.isEmpty(ppIds)) {
				String[] ppIdStrings = ppIds.split(";");
				for (int i = 0; i < ppIdStrings.length; i++) {
					/**
					 * 添加商品关联的商品属性
					 */
					manageSystemMapper
						.msAddProductPropertiesByPId(pId, ppIdStrings[i]);
				}
			}
			// 是否为推荐商品 是--减少店铺剩余展位的数量
			if (1 == Integer.valueOf(pHaveBooth).intValue()) { // 推荐商品
				/**
				 * 修改某店铺剩余展位的数量 0--减 1--加
				 */
				manageSystemMapper.msUpdateStoreLeftBoothNum(sId, 0);
			}
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "系统繁忙，请稍后重试！");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsDelProductInfoByPId(String pId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据pId删除商品（软删除）
		 */
		int result = manageSystemMapper.doMsDelProductInfoByPId(pId);
		if (1 == result) {
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
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "系统繁忙，请稍后重试！");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> doMsGetProductOrderStatusList() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (ProductOrderStatus info : ProductOrderStatus.values()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", info.getId()); // 状态标识
			map.put("name", info.getName()); // 状态名称
			list.add(map);
		}
		return list;
	}
	
	@Override
	public Map<String, Object> doMsGetProductOrderListBySId(String sId, String poOrderId, String poStatus,
			String poDeliverName, String poDeliverTel, String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取某商户满足条件的商品订单总数
		 */
		int totalCount = manageSystemMapper
				.msGetSatifitionConditionProductOrderCountBySId(sId, poOrderId,
						poStatus, poDeliverName, poDeliverTel);
		map.put("totalCount", totalCount); // 满足条件的商品订单总数
		/**
		 * 获取某商户满足条件的商品订单列表
		 * t_product_order：PO_ID、PO_CREATE_TIME、PO_TOTAL_PRICE、PO_STATUS、
		 * 		PO_PAY_TIME、PO_SEND_TIME、PO_DELIVER_TIME、PO_OVER_TIME、PO_PAY_CODE
		 * 		PO_ORDER_ID、PO_DELIVER_NAME、PO_DELIVER_TEL、PO_DELIVER_ADDRESS、
		 * 		PO_DELIVER_COMPANY、PO_DELIVER_CODE
		 * t_pay_style：PS_NAME
		 * t_user：U_ACCOUNT
		 */
		List<ProductOrder> productOrders = manageSystemMapper
				.doMsGetProductOrderListBySId(sId, poOrderId, poStatus,
						poDeliverName, poDeliverTel,
						Integer.valueOf(start).intValue(),
						Integer.valueOf(end).intValue());
		List<Map<String,Object>> productOrderList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < productOrders.size(); i++) {
			Map<String,Object> productOrderMap = new HashMap<String,Object>();
			productOrderMap.put("poId", productOrders.get(i)
					.getPoId()); // 订单主键标识
			productOrderMap.put("poCreateTime", productOrders.get(i)
					.getPoCreateTime()); // 订单创建时间
			productOrderMap.put("poTotalPrice", productOrders.get(i)
					.getPoTotalPrice()); // 订单总金额
			productOrderMap.put("psNmae", productOrders.get(i).getPayStyle()
					.getPsName()); // 订单支付方式
			productOrderMap.put("uAccount", productOrders.get(i).getUser()
					.getuAccount()); // 支付账号
			productOrderMap.put("poStatus", productOrders.get(i)
					.getPoStatus()); // 订单状态
			productOrderMap.put("poPayTime", productOrders.get(i)
					.getPoPayTime()); // 订单支付时间
			productOrderMap.put("poSendTime", productOrders.get(i)
					.getPoSendTime()); // 订单发货时间
			productOrderMap.put("poDeliverTime", productOrders.get(i)
					.getPoDeliverTime()); // 订单收货时间
			productOrderMap.put("poOverTime", productOrders.get(i)
					.getPoOverTime()); // 订单完成时间
			productOrderMap.put("poPayCode", productOrders.get(i)
					.getPoPayCode()); // 支付订单号
			productOrderMap.put("poOrderId", productOrders.get(i)
					.getPoOrderId()); // 订单号
			productOrderMap.put("poDeliverName", productOrders.get(i)
					.getPoDeliverName()); // 收货人
			productOrderMap.put("poDeliverTel", productOrders.get(i)
					.getPoDeliverTel()); // 收货人电话
			productOrderMap.put("poDeliverAddress", productOrders.get(i)
					.getPoDeliverAddress()); // 收货地址
			productOrderMap.put("poDeliverCompany", productOrders.get(i)
					.getPoDeliverCompany()); // 发货的快递公司
			productOrderMap.put("poDeliverCode", productOrders.get(i)
					.getPoDeliverCode()); // 发货的快递单号
			/**
			 * 根据poId获取订单详情
			 * t_product_order_detail:POD_ID、POD_NUM、POD_PROPERTY、POD_PRICE
			 * t_product：P_NAME
			 */
			List<ProductOrderDetail> orderDetails = manageSystemMapper
					.msGetProductOrderDetailByPoId(productOrders.get(i)
							.getPoId());
			List<Map<String,Object>> productOrderDetailList = new ArrayList<Map<String,Object>>();
			for (int j = 0; j < orderDetails.size(); j++) {
				Map<String,Object> productOrderDetailMap = new HashMap<String,Object>();
				productOrderDetailMap.put("podId", orderDetails.get(j)
						.getPodId()); // 订单详情主键标识
				productOrderDetailMap.put("pName", orderDetails.get(j).getProduct()
						.getpName()); // 商品名称
				productOrderDetailMap.put("podNum", orderDetails.get(j)
						.getPodNum()); // 商品数量
				productOrderDetailMap.put("podProperty", orderDetails.get(j)
						.getPodProperty()); // 商品属性
				productOrderDetailMap.put("podPrice", orderDetails.get(j)
						.getPodPrice()); // 商品单价
				productOrderDetailList.add(productOrderDetailMap);
			}
			productOrderMap.put("orderDetails", 
					productOrderDetailList); // 商品详情
			productOrderList.add(productOrderMap);
		}
		map.put("orders", productOrderList); // 订单列表
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeProductOrderStatus(String poId, String poStatus, String poDeliverCompany,
			String poDeliverCode) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改商品订单状态
		 */
		int result = manageSystemMapper.doMsChangeProductOrderStatus(poStatus,
				poDeliverCompany, poDeliverCode, poId);
		if (1 == result) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "系统繁忙，请稍后重试！");
			throw new RuntimeException();
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doMsGetCompanyStoreInfoBySid(String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取某商户的店铺信息接口
		 * t_store：S_ID、S_TYPE、S_NAME、P_TAG、S_DESCRIBE、S_LEADER、
		 * 		S_LEADER_IDCARD、S_LEGAL、S_LEGAL_IDCARD、S_LEADER_PIC、
		 * 		S_LEGAL_PIC、S_BUSINESS_LICENSE_PIC、S_TEL、S_CREATE_TIME、
		 * 		S_ADDRESS、S_LON、S_LAT、S_WEIGHT、S_BOOTH_NUM、
		 * 		S_LEFT_BOOTH_NUM、S_REQUIREMENT_SERVICE_CHARGE、
		 * 		S_ORDER_LIMINT、S_PRODUCT_SERVICE_CHARGE、
		 * 		S_CHECKED、S_LEVEL、S_CHECKED_TIME、S_CHECKED_OPINION
		 * t_areacode：AC_CITY
		 * t_user：U_TRUE_NAME
		 */
		Store store = manageSystemMapper.doMsGetStoreInfoBySid(sId);
		map.put("sId", store.getsId()); // 店铺主键标识
		map.put("sType", store.getsType()); // 店铺类型
		map.put("sName", store.getsName()); // 店铺名称
		/**
		 * 获取店铺封面图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> storePics = manageSystemMapper
				.msGetPicByPTag(store.getpTag());
		List<Map<String,Object>> storePicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < storePics.size(); j++) {
			Map<String,Object> storePicMap = new HashMap<String,Object>();
			storePicMap.put("url", Utils.PIC_BASE_URL 
					+ storePics.get(j).getpFileName()
					+ storePics.get(j).getpName()); // 图片地址
			storePicMap.put("pNo", storePics.get(j)
					.getpNo()); // 图片编号
			storePicList.add(storePicMap);
		}
		map.put("pTag", store.getpTag()); // 店铺封面图片标识
		map.put("storePics", storePicList); // 店铺封面图片
		map.put("acCity", store.getAreaCode()
				.getAcCity()); // 店铺所在城市
		map.put("sDescribe", store.getsDescribe()); // 店铺描述
		map.put("sLeader", store.getsLeader()); // 店铺负责人
		map.put("sLeaderIdCard", store
				.getsLeaderIdCard()); // 店铺负责人身份证号
		map.put("sLegal", store.getsLegal()); // 店铺法人
		map.put("sLegalIdCard", store
				.getsLegalIdCard()); // 店铺法人身份证号
		/**
		 * 获取店铺负责人身份证图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sLeaderPics = manageSystemMapper
				.msGetPicByPTag(store.getsLeaderPic());
		List<Map<String,Object>> sLeaderPicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sLeaderPics.size(); j++) {
			Map<String,Object> sLeaderPicMap = new HashMap<String,Object>();
			sLeaderPicMap.put("url", Utils.PIC_BASE_URL 
					+ sLeaderPics.get(j).getpFileName()
					+ sLeaderPics.get(j).getpName()); // 图片地址
			sLeaderPicMap.put("pNo", sLeaderPics.get(j)
					.getpNo()); // 图片编号
			sLeaderPicList.add(sLeaderPicMap);
		}
		map.put("sLeaderPics", sLeaderPicList); // 店铺负责人身份证图片
		/**
		 * 获取店铺法人身份证图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sLegalPics = manageSystemMapper
				.msGetPicByPTag(store.getsLegalPic());
		List<Map<String,Object>> sLegalPicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sLegalPics.size(); j++) {
			Map<String,Object> sLegalPicMap = new HashMap<String,Object>();
			sLegalPicMap.put("url", Utils.PIC_BASE_URL 
					+ sLegalPics.get(j).getpFileName()
					+ sLegalPics.get(j).getpName()); // 图片地址
			sLegalPicMap.put("pNo", sLegalPics.get(j)
					.getpNo()); // 图片编号
			sLegalPicList.add(sLegalPicMap);
		}
		map.put("sLegalPics", sLegalPicList); // 店铺法人身份证图片
		/**
		 * 获取店铺营业执照图片
		 * t_pic：P_FILE_NAME、P_NAME、P_NO、P_JUMP
		 */
		List<Pic> sBuinessLicensePics = manageSystemMapper
				.msGetPicByPTag(store
						.getsBuinessLicensePic());
		List<Map<String,Object>> sBuinessLicensePicList 
			= new ArrayList<Map<String,Object>>();
		for (int j = 0; j < sBuinessLicensePics.size(); j++) {
			Map<String,Object> sBuinessLicensePicMap = new HashMap<String,Object>();
			sBuinessLicensePicMap.put("url", Utils.PIC_BASE_URL 
					+ sBuinessLicensePics.get(j).getpFileName()
					+ sBuinessLicensePics.get(j)
					.getpName()); // 图片地址
			sBuinessLicensePicMap.put("pNo", sBuinessLicensePics
					.get(j).getpNo()); // 图片编号
			sBuinessLicensePicList.add(sBuinessLicensePicMap);
		}
		map.put("sBuinessLicensePics",
				sBuinessLicensePicList); // 店铺营业执照图片
		map.put("sTel", store.getsTel()); // 店铺联系电话
		map.put("sCreateTime", store.getsCreateTime()); // 店铺创建时间
		map.put("sAddress", store.getsAddress()); // 店铺地址
		map.put("sLon", store.getsLon()); // 店铺经度
		map.put("sLat", store.getsLat()); // 店铺纬度
		map.put("sWeight", store.getsWeight()); // 店铺权重
		map.put("sBoothNum", store.getsBoothNum()); // 店铺推荐展位总个数
		map.put("sLeftBoothNum", store
				.getsLeftBoothNum()); // 店铺剩余推荐展位个数
		map.put("sRequirementServiceCharge", store
				.getsRequirementServiceCharge()); // 店铺需求交易服务费
		map.put("sOrderLimint", store
				.getsOrderLimint()); // 店铺订单交易限额
		map.put("sProductServiceCharge", store
				.getsProductServiceCharge()); // 店铺商品交易服务费
		map.put("sChecked", store
				.getsChecked()); // 店铺审核状态
		map.put("sLevel", store.getsLevel()); // 店铺评级
		try {
			map.put("uTrueName", store.getUser()
					.getuTrueName()); // 审核人姓名
			map.put("sCheckedTime", store
					.getsCheckedTime()); // 店铺审核时间
			map.put("sCheckedOpinion", store
					.getsCheckedOpinion()); // 店铺审核意见
		} catch (Exception e) {
			// TODO: handle exception
			map.put("uTrueName", ""); // 审核人姓名
			map.put("sCheckedTime", ""); // 店铺审核时间
			map.put("sCheckedOpinion", ""); // 店铺审核意见
		}
		
		/**
		 * 获取特定店铺钱包信息
		 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT
		 */
		UserWallet userWallet = manageSystemMapper
				.msGetStoreWalletListBySId(sId);
		if (null != userWallet) {
			map.put("uwLeftMoney", userWallet.getUwLeftMoney()); // 可用余额
			map.put("uwDeposit", userWallet
					.getUwDeposit()); // 已缴纳（冻结）的保障金
		} else {
			map.put("uwLeftMoney", 0); // 可用余额
			map.put("uwDeposit", 0); // 已缴纳（冻结）的保障金
		}
		// 只有企业商户可以发布商品
		/**
		 * 获取特定店铺已发布的商品的总数
		 */
		int productNum = manageSystemMapper.msGetStoreProductNum(sId);
		map.put("productNum", productNum); // 已发布的商品的总数
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsChangeCompanyStoreInfoBySid(String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改店铺基本信息	
		 */
		int result = manageSystemMapper
				.doMsChangeStoreInfoBySid(sId, sDescribe, sTel,
						sAddress, sLon, sLat);
		if (1 == result) { // 修改成功
			/**
			 * 更改店铺关联的宣传图片
			 * 1、删除关联图片
			 * 2、新增关联图片
			 */
			// 解析图片数据
			PicVo picVo = new PicVo();
			picVo = Utils.parserJsonResult(picData, PicVo.class);
			if (null != picVo) {
				/**
				 * 根据pTag删除数据库中的图片
				 */
				manageSystemMapper
						.msDelPicsByPtag(picVo.getPTag());
				/**
				 * 添加图片
				 */
				for (int i = 0; i < picVo.getPics().size(); i++) {
					manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
							picVo.getPics().get(i).getPName(),
							picVo.getPics().get(i).getPNo(),
							picVo.getPTag());
				}
			}
			map.put("status", "true");
			map.put("errorString", "");
		} else { // 修改失败
			map.put("status", "false");
			map.put("errorString", "修改失败!");
			throw new RuntimeException();
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doMsReSubmitStoreAuditInfo(String sId, String sDescribe, String sTel, String sAddress,
			String sLon, String sLat, String picData, String sLeader, String sLeaderIdCard, String sLeaderPicName,
			String sLegal, String sLegalIdCard, String sLegalPicName, String sBusinessLicensePicName) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		//----------修改商户审核图片------------------
		/**
		 * 更改店铺关联的宣传图片
		 * 1、删除关联图片
		 * 2、新增关联图片
		 */
		// 解析图片数据
		PicVo picVo = new PicVo();
		picVo = Utils.parserJsonResult(picData, PicVo.class);
		if (null != picVo) {
			/**
			 * 根据pTag删除数据库中的图片
			 */
			manageSystemMapper
					.msDelPicsByPtag(picVo.getPTag());
			/**
			 * 添加图片
			 */
			for (int i = 0; i < picVo.getPics().size(); i++) {
				manageSystemMapper.msAddPicInfo(
						Utils.STORE_PIC,
						picVo.getPics().get(i).getPName(),
						picVo.getPics().get(i).getPNo(),
						picVo.getPTag());
			}
		}
		/**
		 * 获取某商户相关证件照审核资料
		 * t_store：S_LEADER_PIC、S_LEGAL_PIC、S_BUSINESS_LICENSE_PIC
		 */
		Store store = manageSystemMapper
				.msGetStoreAuditPicsInfoBySid(sId);
		// 商户负责人手持证件照pTag
		String sLeaderPic = "";
		// 商户法人手持证件照pTag
		String sLegalPic = "";
		// 营业执照pTag
		String sBusinessLicense = "";
		if (null != store) {
			// 商户负责人手持证件照
			if (!Utils.isEmpty(sLeaderPicName)) {
				/**
				 * 更改商户负责人手持证件照片
				 * 1、判断商户是否上传过，如上传过则返回pTag
				 * 2、根据pTag去修改图片
				 * 3、没有上传过则添加
				 */
				if (!Utils.isEmpty(store.getsLeaderPic())) {
					sLeaderPic = store.getsLeaderPic();
					int result = manageSystemMapper.msChangePNameByPTag(sLeaderPicName,
							sLeaderPic);
					if (1 == result) {
//						System.out.println("sLeaderPic:" + sLeaderPic);
					}
				} else {
					sLeaderPic = Utils.randomUUID();
					manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
							sLeaderPicName, 0,
							sLeaderPic);
				}
			}
			// 商户法人手持证件照
			if (!Utils.isEmpty(sLegalPicName)) {
				/**
				 * 更改商户法人手持证件照片
				 * 1、判断商户是否上传过，如上传过则返回pTag
				 * 2、根据pTag去修改图片
				 * 3、没有上传过则添加
				 */
				if (!Utils.isEmpty(store.getsLegalPic())) {
					sLegalPic = store.getsLegalPic();
					int result = manageSystemMapper.msChangePNameByPTag(sLegalPicName,
							sLegalPic);
					if (1 == result) {
//						System.out.println("sLegalPic:" + sLegalPic);
					}
				} else {
					sLegalPic = Utils.randomUUID();
					manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
							sLegalPicName, 0, 
							sLegalPic);
				}
			}			
			// 营业执照
			if (!Utils.isEmpty(sBusinessLicensePicName)) {
				/**
				 * 更改营业执照照片
				 * 1、判断商户是否上传过，如上传过则返回pTag
				 * 2、根据pTag去修改图片
				 * 3、没有上传过则添加
				 */
				if (!Utils.isEmpty(store.getsBuinessLicensePic())) {
					sBusinessLicense = store.getsBuinessLicensePic();
					int result = manageSystemMapper.msChangePNameByPTag(
							sBusinessLicensePicName,
							sBusinessLicense);
					if (1 == result) {
//						System.out.println("sBusinessLicense:" + sBusinessLicense);
					}
				} else {
					sBusinessLicense = Utils.randomUUID();
					manageSystemMapper.msAddPicInfo(Utils.STORE_PIC,
							sBusinessLicensePicName, 0,
							sBusinessLicense);
				}
			}				
		}
		/**
		 * 修改店铺基本信息	
		 */
		int result = manageSystemMapper
				.doMsChangeStoreAuditInfoBySid(sId, sDescribe, sTel,
						sAddress, sLon, sLat, sLeader, sLeaderIdCard,
						sLegal, sLegalIdCard, sLeaderPic, sLegalPic,
						sBusinessLicense);
		if (1 == result) {
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "提交失败，请稍后重试!");
			throw new RuntimeException();
		}
		return map;
	}
	
}
