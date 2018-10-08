package com.service.extra.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.extra.mall.controller.CAPTCHA;
import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.ProductMapper;
import com.service.extra.mall.mapper.StoreMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.model.AreaCode;
import com.service.extra.mall.model.Guide;
import com.service.extra.mall.model.MovingTrajectory;
import com.service.extra.mall.model.OpenCity;
import com.service.extra.mall.model.PayStyle;
import com.service.extra.mall.model.Pic;
import com.service.extra.mall.model.Product;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.ProductType;
import com.service.extra.mall.model.RecommendStore;
import com.service.extra.mall.model.SafetyQuestion;
import com.service.extra.mall.model.Store;
import com.service.extra.mall.model.User;
import com.service.extra.mall.model.UserAttention;
import com.service.extra.mall.model.UserCollection;
import com.service.extra.mall.model.UserDeliverAddress;
import com.service.extra.mall.model.UserFootprint;
import com.service.extra.mall.model.UserMemoName;
import com.service.extra.mall.model.UserRole;
import com.service.extra.mall.model.UserTrolley;
import com.service.extra.mall.model.Version;
import com.service.extra.mall.model.vo.UserVo;
import com.service.extra.mall.service.UserService;
import com.service.extra.mall.service.WangYiYunService;

import util.Utils;

/**
 * 
 * desc：平台管理系统相关接口服务实现类
 * @author L
 * time:2018年5月17日
 */
@Service
public class UserServiceImpl implements UserService{
	@Resource private UserMapper userMapper;
	@Resource private StoreMapper storeMapper;
	@Resource private ProductMapper productMapper;
	@Resource private ManageSystemMapper manageSystemMapper;
	@Resource private WangYiYunService wangYiYunService;

	
	@Override
	public Map<String, Object> doUserLogin(String uAccount, String uPassword) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户账号密码查询用户是否存在
		 */
		User user = userMapper.doUserLogin(uAccount,uPassword);
		if (user!=null) {
			String urId = user.getUrId();
			String token = user.getuToken();
			String accid = user.getuAccid();
			/**
			 * 根据urId查询用户角色
			 */
			UserRole userRole = userMapper.doGetUserRoleByUrId(urId);
			if (userRole.getUrName().equals("平台用户")) {
				map.put("uId", user.getuId());
				map.put("uTel", uAccount);
				map.put("token", token);
				map.put("accid", accid);
				map.put("status","true");
				map.put("errorString", "");
			}else {
				map.put("status","false");
				map.put("errorString","账号角色错误！");
			}
		}else {
			map.put("status","false");
			map.put("errorString","用户账号或密码错误");
		}
		return map;
	}
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doUserRegistered(String uAccount, String uPassword) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户角色主键标识
		 */
		String urId = null;
		List<UserRole> userRoles = userMapper.doGetUserRole();
		for (UserRole userRole : userRoles) {
			if (userRole.getUrName().equals("平台用户")) {
				 urId = userRole.getUrId();
			}
		}
		User user1 = userMapper.doRetrievePsw(uAccount);
		if (user1 != null) {
			map.put("status", "false");
   			map.put("errorString", "该账号已注册!");
   			return map;
		}
		User user = new User();
		String uId = Utils.randomUUID();
		user.setuId(uId);
		user.setuAccount(uAccount);
		user.setuPassword(uPassword);
		user.setUrId(urId);
		/**
		 * 用户注册网易云
		 */
		try {
			String accid = "";
		    String token = "";
		    String name = "";
		    String desc = "";
		    String uAccid = uId.replace("-", "");
		    String uNickName = "U" + Utils.getEightRandom();
		    String yunXinResult = (String) wangYiYunService.create(uAccid, uNickName);
		    JSONObject jsonObject = JSONObject.parseObject(yunXinResult);
		    int code = jsonObject.getInteger("code");
		    if (code == 200) {
		        JSONObject info = jsonObject.getJSONObject("info");
		        accid = info.getString("accid");
		        token = info.getString("token");
		        name = info.getString("name");
		        user.setuAccid(accid);
		        user.setuToken(token);
		        user.setuNickName(uNickName);
		        /**
		   		 * 用户注册插入用户表
		   		 */
		   		int row = userMapper.doAddCustomer(user);
		   		if (1 == row) {
		   			map.put("errorString", "");
		   			map.put("status", "true");
		   			map.put("accid", accid);
		   			map.put("token", token);
		   			map.put("uId", uId);
		   		} else {
		   			map.put("status", "false");
		   			map.put("errorString", "插入用户信息失败");
		   			throw new RuntimeException();
				}
		   		/**
				 * 更新网易云用户名片
				 */
				UserVo userVo = new UserVo();
				userVo.setuId(uId);
				userVo.setsId("");
				userVo.setsType("");
				String updateResult = (String) wangYiYunService.updateUinfo(uAccid, name, Utils.ObjectToJson(userVo));
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
		return map;
	}
	@Override
	public Map<String, Object> doGetUserInfo(String uId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户主键标识获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		map.put("uId", user.getuId());
		map.put("uNickName", user.getuNickName());
		int uSex = user.getuSex();
		String usex = String.valueOf(uSex);
		map.put("uSex", usex);
		map.put("uEmail", user.getuEmail());
		map.put("uBirthday", user.getuBirthday());
		map.put("pTag", user.getpTag());
		/**
		 * 获取用户头像信息
		 */
		Pic pic = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
		if (pic == null) {
			map.put("picFileName", "");
			map.put("picName", "");
		}else {
			map.put("picFileName", pic.getpFileName());
			map.put("picName", Utils.PIC_BASE_URL + Utils.PROFILE_PIC + pic.getpName());
		}
		map.put("uTel", user.getuTel());
		map.put("uTrueName", user.getuTrueName());
		map.put("sId", user.getsId());
		map.put("uCreateTime", user.getuCreateTime());
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserPassword(String uAccount, String uPassword, String uNewPassword,String uId)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户手机号获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (!user.getuAccount().equals(uAccount)) {
			map.put("status","false");
			map.put("errorString","用户账号输入错误");
			return map;
		}
		if (!user.getuPassword().equals(uPassword)) {
			map.put("status","false");
			map.put("errorString","用户密码输入错误");
			return map;
		}
		/**
		 * 修改用户密码
		 */
		int row = userMapper.doUpdateUserPassword(uId,uNewPassword);
		if (1 != row) {
			map.put("errorString", "修改密码失败");
			map.put("status","false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status","true");
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserSecretQuestion(String uId, String uFirstSqId, String uFirstSqAnswer,
			String uSecondSqId, String uSecondSqAnswer, String uThirdSqId, String uThirdSqAnswer) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 设置用户密保
		 */
		int row = userMapper.doUpdateUserSecretQuestion(uId,uFirstSqId,uFirstSqAnswer,uSecondSqId,uSecondSqAnswer,uThirdSqId,uThirdSqAnswer);
		if (1 != row) {
			map.put("errorString", "设置密保失败");
			map.put("status","false");
			throw new RuntimeException();

		} else {
			map.put("errorString", "");
			map.put("status","true");

		}
		return map;
	}
	@Override
	public Map<String, Object> doGetSafetyQuestion(String sqPosition) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据安全问题位置获取安全问题信息
		 */
		List<SafetyQuestion> safetyQuestions = userMapper.doGetSafetyQuestion(sqPosition);
		List<Map<String, Object>> safetyQuestionList = new ArrayList<Map<String,Object>>();
		for (SafetyQuestion safetyQuestion : safetyQuestions) {
			Map<String,Object> safetyQuestionMap = new HashMap<String,Object>();
			safetyQuestionMap.put("sqId", safetyQuestion.getSqId());
			safetyQuestionMap.put("sqName", safetyQuestion.getSqName());
			safetyQuestionMap.put("sqPosition", safetyQuestion.getSqPosition());
			safetyQuestionList.add(safetyQuestionMap);
		}
		map.put("safetyQuestionList", safetyQuestionList);
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserInfo(String uId, String uNickName, String uSex, String uEmail,
			String uBirthday) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改添加用户信息
		 */
		int row = userMapper.doUpdateUserInfo(uId,uNickName,uSex,uEmail,uBirthday);
		if (1 != row) {
			map.put("errorString", "修改添加用户信息失败");
			map.put("status","false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status","true");

		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserAvatar(String uId, String pName) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据uId查询用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		String pTag = user.getpTag();
		/**
		 * 根据pTag查询图片信息
		 */
		List<Pic> pics = userMapper.doGetPicInfoByPTag(pTag);
		if (0== pics.size()||pics==null) {
			//第一次插入头像信息
			String pFileName = Utils.PROFILE_PIC;
			int pNo = 0;
			String pJump = "";
			int row = userMapper.doAddPic(pName,pFileName,pTag,pNo,pJump);
			if (1 != row) {
				map.put("errorString", "添加图片失败");
				map.put("status","false");
				throw new RuntimeException();
			}
		}else {
			for (Pic pic : pics) {
				String pId = pic.getpId();
				/**
				 * 修改用户头像信息
				 */
				int row = userMapper.doUpdateUserAvatar(pId,pName);
				if (1 != row) {
					map.put("errorString", "修改图片信息失败");
					map.put("status","false");
					throw new RuntimeException();
				}
			}
		}
		map.put("errorString", "");
		map.put("status","true");
		return map;
	}
	@Override
	public Map<String, Object> doGetUserDeliverAddress(String uId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户id获取用户收货地址
		 */
		List<UserDeliverAddress> userDeliverAddresses = userMapper.doGetUserDeliverAddress(uId);
		List<Map<String, Object>> userDeliverAddressList = new ArrayList<Map<String,Object>>();
		for (UserDeliverAddress userDeliverAddress : userDeliverAddresses) {
			Map<String,Object> userDeliverAddressMap = new HashMap<String,Object>();
			userDeliverAddressMap.put("udaId", userDeliverAddress.getUdaId());
			userDeliverAddressMap.put("uId", userDeliverAddress.getuId());
			userDeliverAddressMap.put("udaAddress", userDeliverAddress.getUdaAddress());
			userDeliverAddressMap.put("udaTel", userDeliverAddress.getUdaTel());
			userDeliverAddressMap.put("udaTrueName", userDeliverAddress.getUdaTrueName());
			userDeliverAddressMap.put("udaDefault", userDeliverAddress.getUdaDefault());
			userDeliverAddressList.add(userDeliverAddressMap);
		}
		map.put("userDeliverAddressList", userDeliverAddressList);
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddUserDeliverAddress(String uId, String udaTrueName, String udaTel, String udaAddress)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int def = 1;//默认地址
		/**
		 * 查询该用户的所有收货地址
		 */
		List<UserDeliverAddress> list = userMapper.doGetUserDeliverAddress(uId);
		if (list.size()>0) {
			def = 0;//非默认地址
		}
		/**
		 * 添加收货地址
		 */
		int row = userMapper.doAddUserDeliverAddress(uId,udaTrueName,udaTel,udaAddress,def);
		if(1 != row){
			map.put("errorString", "添加收货地址失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true"); // 添加成功
		} 
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateUserDeliverAddress(String udaId,String uId, String udaTrueName, String udaTel,
			String udaAddress) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 修改收货地址信息
		 */
		int row = userMapper.doUpdateUserDeliverAddress(udaId,uId,udaTrueName,udaTel,udaAddress);
		if(1 != row){
			map.put("errorString", "修改收货地址失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true"); // 修改成功

		} 
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelUserDeliverAddress(String udaId, String uId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 删除收货地址信息
		 */
		int row = userMapper.doDelUserDeliverAddress(udaId,uId);
		if(1 != row){
			map.put("errorString", "删除收货地址失败");
			map.put("status", "false");
			throw new RuntimeException();
		}else {
			map.put("errorString", "");
			map.put("status", "true"); // 删除成功
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doSetDefaultUserDeliverAddress(String udaId, String uId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 将原先的默认地址设置为非默认
		 */
		UserDeliverAddress userDeliverAddress = userMapper.doGetDefaultUserDeliverAddress(uId);
		if (userDeliverAddress != null) {
			String defaultId = userDeliverAddress.getUdaId();
			int def = 0;//非默认
			int row = userMapper.doSetDefaultUserDeliverAddress(defaultId,def);
			if(1 != row){
				map.put("errorString", "修改默认收货地址失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}
		/**
		 * 再将新的地址修改为默认状态
		 */
		int row1 = userMapper.doSetDefaultUserDeliverAddress(udaId, 1);
		if(1 != row1){
			map.put("errorString", "设置新的默认地址失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}
	@Override
	public Map<String, Object> doGetUserAttention(String uId,String page,String size) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int p = Integer.parseInt(page);
		int s = Integer.parseInt(size);
		int status = (p-1)*s;
		int end = s;
		/**
		 * 根据用户id获取用户所有的关注店铺
		 */
		List<UserAttention> userAttentions = userMapper.doGetUserAttention(uId,status,end);
		List<Map<String, Object>> userAttentionList = new ArrayList<Map<String,Object>>();
		for (UserAttention userAttention : userAttentions) {
			Map<String, Object> userAttentionMap = new HashMap<String, Object>();
			userAttentionMap.put("uaId", userAttention.getUaId());
			userAttentionMap.put("uId", userAttention.getuId());
			userAttentionMap.put("sId", userAttention.getStore().getsId());
			userAttentionMap.put("uaCreateTime", userAttention.getUaCreateTime());
			userAttentionMap.put("sName", userAttention.getStore().getsName());
			String sLevel = String.valueOf(userAttention.getStore().getsLevel());
			userAttentionMap.put("sLeve", sLevel );
			userAttentionMap.put("pName", Utils.PIC_BASE_URL + Utils.STORE_PIC + 
					userAttention.getStore().getPic().getpName());
			userAttentionList.add(userAttentionMap);
		}
		map.put("userAttentionList", userAttentionList);
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> doAddUserAttention(String uId, String sId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 判断用户是否已关注该店铺
		 */
		UserAttention userAttention = userMapper.selectUserAttention(uId,sId);
		if (userAttention == null) {
			/**
			 * 添加用户关注店铺
			 */
			int row = userMapper.doAddUserAttention(uId,sId);
			if(1 != row){
				map.put("errorString", "添加用户关注店铺失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}else {
			map.put("errorString", "该店铺已被关注");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> doDelUserAttention(String uId, String sId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 判断用户是否关注该店铺
		 */
		UserAttention userAttention = userMapper.selectUserAttention(uId, sId);
		String uaId = userAttention.getUaId();
		if (uaId != null) {
			/**
		 	* 取消用户关注店铺
		 	*/
			int row = userMapper.doDelUserAttention(uId,sId);
			if(1 != row){
				map.put("errorString", "取消用户关注店铺失败");
				map.put("status", "false");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public Map<String, Object> doAddUserTrolley(String uId, String pId, String sId, String utProductNum,
			String utProductProperty) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int pNum = Integer.parseInt(utProductNum);
		/**
		 * 查询购物车是否存在该商品
		 */
		UserTrolley userTrolley = userMapper.doSelectUserTrolley(uId,utProductProperty,pId,sId);
		//购物车不存在该商品
		if (userTrolley == null) {
			/**
			 * 商品添加到购物车
			 */
			int row = userMapper.doAddUserTrolley(uId, pId, pNum,utProductProperty,sId);
			if (1 != row) {
				map.put("status", "false");
				map.put("errorString", "加入购物车失败");
				throw new RuntimeException();
			}
		}else{
			String utId =  userTrolley.getUtId();
			//购物车存在该商品
			/**
			 * 修改购物车商品数量
			 */
			int row = userMapper.doUpdateUserTrolleyProductNum(utProductNum,utId);
			if (1 != row) {
				map.put("status", "false");
				map.put("errorString", "修改购物车商品数量失败");
				throw new RuntimeException();
			}
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelUserTrolley(String uId, String utIdList) throws Exception {
		Map<String,Object> map = new HashMap<>();
		String[] list = utIdList.split(",");
		for (String utId : list) {
			/**
			 * 根据购物车id删除购物车
			 */
			int row = userMapper.doDelUserTrolley(utId,uId);
			if (1 != row) {
				map.put("status", "false");
				map.put("errorString", "删除购物车商品失败");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
		}
		return map;
	}
	@Override
	public Map<String, Object> doGetUserTrolley(String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 根据uId获取用户所有购物车商品信息
		 */
		List<UserTrolley> userTrolleys = userMapper.doGetUserTrolley(uId);
		List<Map<String, Object>> userTrolleyList = new ArrayList<Map<String,Object>>();
		for (UserTrolley userTrolley : userTrolleys) {
			Map<String,Object> userTrolleyMap = new HashMap<>();
			userTrolleyMap.put("utCreateTime", userTrolley.getUtCreateTime());
			userTrolleyMap.put("utId", userTrolley.getUtId());
			userTrolleyMap.put("utProductNum", userTrolley.getUtProductNum());
			userTrolleyMap.put("utProductProperty", userTrolley.getUtProductProperty());
			userTrolleyMap.put("pName", userTrolley.getProduct().getpName());
			userTrolleyMap.put("pId", userTrolley.getProduct().getpId());
			userTrolleyMap.put("sId", userTrolley.getStore().getsId());
			userTrolleyMap.put("pOriginalPrice", userTrolley.getProduct().getpOriginalPrice());
			userTrolleyMap.put("pNowPrice", userTrolley.getProduct().getpNowPrice());
			userTrolleyMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC +
					userTrolley.getProduct().getPic().getpName());
			userTrolleyMap.put("sName", userTrolley.getStore().getsName());
			userTrolleyList.add(userTrolleyMap);
		}
		map.put("userTrolleyList", userTrolleyList);
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddUserCollection(String uId, String pId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 查询用户收藏是否存在该商品
		 */
		UserCollection userCollection = userMapper.selectUserCollection(uId,pId);
		if (userCollection==null) {
			/**
			 * 添加商品到用户收藏
			 */
			int row = userMapper.doAddUserCollection(uId,pId);
			if (1 != row) {
				map.put("errorString", "添加用户收藏失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		}else{
			map.put("errorString", "商品已存在用户收藏");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelUserCollection(String uId, String pId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		 	/**
			 * 删除商品到用户收藏
			 */
			int row = userMapper.doDelUserCollection(uId,pId);
			if (1 != row) {
				map.put("errorString", "删除用户收藏失败");
				map.put("status", "false");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
		return map;
	}
	@Override
	public Map<String, Object> doGetUserCollection(String uId,String page,String size) throws Exception {
		Map<String,Object> map = new HashMap<>();
		int p = Integer.parseInt(page);
		int s = Integer.parseInt(size);
		int status = (p-1)*s;
		int end = s;
		/**
		 * 获取用户所有的收藏商品
		 */
		List<UserCollection> userCollections = userMapper.doGetUserCollection(uId,status,end);
		List<Map<String, Object>> userCollectionList = new ArrayList<Map<String,Object>>();
		for (UserCollection userCollection : userCollections) {
			Map<String,Object> userCollectionMap = new HashMap<>();
			userCollectionMap.put("ucCreateTime", userCollection.getUcCreateTime());
			userCollectionMap.put("ucId", userCollection.getUcId());
			userCollectionMap.put("uId", userCollection.getuId());
			userCollectionMap.put("pName", userCollection.getProduct().getpName());
			userCollectionMap.put("pId", userCollection.getProduct().getpId());
			userCollectionMap.put("pOriginalPrice", userCollection.getProduct().getpOriginalPrice());
			userCollectionMap.put("pNowPrice", userCollection.getProduct().getpNowPrice());
			userCollectionMap.put("pDescribe", userCollection.getProduct().getpDescribe());
			userCollectionMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + 
					userCollection.getProduct().getPic().getpName());
			userCollectionList.add(userCollectionMap);
		}
		map.put("userCollectionList", userCollectionList);
		return map;
	}
	@Override
	public Map<String, Object> doGetUserFootPrint(String uId,String page,String size) throws Exception {
		Map<String,Object> map = new HashMap<>();
		int p = Integer.parseInt(page);
		int s = Integer.parseInt(size);
		int status = (p-1)*s;
		int end = s;
		/**
		 * 获取用户所有的足迹
		 */
		List<UserFootprint> userFootprints = userMapper.doGetUserFootPrint(uId,status,end);
		List<Map<String, Object>> userFootprintList = new ArrayList<Map<String,Object>>();
		for (UserFootprint userFootprint : userFootprints) {
			Map<String,Object> userFootprintMap = new HashMap<>();
			userFootprintMap.put("ufCreateTime", userFootprint.getUfCreateTime());
			userFootprintMap.put("ufId", userFootprint.getUfId());
			userFootprintMap.put("uId", userFootprint.getuId());
			userFootprintMap.put("pName", userFootprint.getProduct().getpName());
			userFootprintMap.put("pId", userFootprint.getProduct().getpId());
			userFootprintMap.put("pOriginalPrice", userFootprint.getProduct().getpOriginalPrice());
			userFootprintMap.put("pNowPrice", userFootprint.getProduct().getpNowPrice());
			userFootprintMap.put("pDescribe", userFootprint.getProduct().getpDescribe());
			String pTag = userFootprint.getProduct().getpTag();
			/**
			 * 获取图片信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			if (pic != null) {
				userFootprintMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + 
						pic.getpName());
			} else {
				userFootprintMap.put("picName", "");
			}

//			userFootprintMap.put("picName",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC + 
//					userFootprint.getProduct().getPic().getpName());
			userFootprintList.add(userFootprintMap);
		}
		map.put("userFootprintList", userFootprintList);
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doDelUserFootPrint(String uId, String ufIdList) throws Exception {
		Map<String,Object> map = new HashMap<>();
		String[] list = ufIdList.split(",");
		 for (String ufId : list) {
			/**
			 * 删除用户足迹
			 */
			int row = userMapper.doDelUserFootPrint(uId,ufId);
			if (1 != row) {
				map.put("errorString", "删除用户足迹失败");
				map.put("status", "false");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddUserFeedBack(String uId, String fType, String fAppType, String fContent)
			throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 添加用户意见反馈
		 */
		int row = userMapper.doAddUserFeedBack(uId,fType,fAppType,fContent);
		if (1 != row) {
			map.put("errorString", "提交失败！");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}
	@Override
	public Map<String, Object> doGetPayStyle() throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取支付方式
		 */
		List<PayStyle> payStyles = userMapper.doGetPayStyle();
		List<Map<String, Object>> payStyleList = new ArrayList<Map<String,Object>>();
		for (PayStyle payStyle : payStyles) {
			Map<String,Object> payStyleMap = new HashMap<>();
			payStyleMap.put("psId", payStyle.getPsId()); // 主键标识
			payStyleMap.put("psName", payStyle.getPsName()); // 支付方式名称
			payStyleMap.put("url", Utils.PIC_BASE_URL + Utils.COMMON_PIC 
					+ payStyle.getPic().getpName()); // 支付方式图片
			payStyleList.add(payStyleMap);
		}
		map.put("payStyleList", payStyleList);
		return map;
	}
	@Override
	public Map<String, Object> doForgetSecretQuestion(String uId, String uPassword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 判断用户密码是否正确
		 */
		User user = userMapper.doForgetSecretQuestion(uId,uPassword);
		if (user != null) {
			map.put("status", "true");
			map.put("errorString", "");
		}else {
			map.put("errorString", "用户密码错误");
			map.put("status", "false");
		} 
		return map;
	}
	@Override
	public Map<String, Object> doGetCityList() throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取城市列表信息
		 */
		List<OpenCity> openCities = userMapper.doGetCityList();
		List<Map<String, Object>> openCityList = new ArrayList<Map<String,Object>>();
		for (OpenCity openCity : openCities) {
			Map<String,Object> openCityMap = new HashMap<>();
			openCityMap.put("acId", openCity.getAcId());
			openCityMap.put("ocId", openCity.getOcId());
			openCityMap.put("ocCreateTime", openCity.getOcCreateTime());
			openCityMap.put("ocIsHot", openCity.getOcIsHot());
			openCityMap.put("acCity", openCity.getAreaCode().getAcCity());
			openCityMap.put("acName", openCity.getAreaCode().getAcName());
			openCityMap.put("acParent", openCity.getAreaCode().getAcParent());
			openCityMap.put("acProvince", openCity.getAreaCode().getAcProvince());
			openCityMap.put("acCode", openCity.getAreaCode().getAcCode());
			openCityList.add(openCityMap);
		}
		map.put("openCityList", openCityList);
		return map;
	}
	@Override
	public Map<String, Object> doGetPersonCenter(String uId) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 获取我的收藏数量
		 */
		int userCollectionNum = userMapper.doGetUserCollectionNum(uId);
		/**
		 * 获取我的足迹数量
		 */
		int footPrintNum = userMapper.doGetUserFootPrintNum(uId);
		/**
		 * 获取我的关注数量
		 */
		int userAttentionNum = userMapper.doGetUserAttentionNum(uId);
//		/**
//		 * 获取我的商品订单评价数量
//		 */
//		int productOrderEvaluateNum = userMapper.doGetProductOrderEvaluateNum(uId);
//		/**
//		 * 获取我的需求订单评价数量
//		 */
//		int requirementOrderEvaluateNum = userMapper.doGetRequirementOrderEvaluateNum(uId);
		/**
		 * 根据uId获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		System.out.println(uId);
		String pTag = user.getpTag();
		/**
		 * 根据pTag查询图片信息
		 */
		Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
		if (null != pic) {
			map.put("url", Utils.PIC_BASE_URL + Utils.PROFILE_PIC + pic.getpName()); 
		} else {
			map.put("url", ""); 
		}
		map.put("uNickName", user.getuNickName());
		map.put("userCollectionNum",userCollectionNum );
		map.put("footPrintNum",footPrintNum );
		map.put("userAttentionNum", userAttentionNum);
//		map.put("userEvaluate", productOrderEvaluateNum+requirementOrderEvaluateNum);
		return map;
	}
	@Override
	public Map<String, Object> doGetversion(String vType, String Code, String phoneType) throws Exception {
		String status ="false";
		Map<String,Object> map = new HashMap<String,Object>();
		int vtype = Integer.valueOf(vType);
		int sysType = Integer.valueOf(phoneType);
		int buildCode = Integer.valueOf(Code);
		/**
		 * 获取系统版本信息
		 */
		Version version =userMapper.versionController(vtype,sysType);
		if (null != version) {
			if(Integer.valueOf(version.getBuildCode())>buildCode){
				status="true";
			}
			map.put("url", version.getvUrl());
			map.put("status", status);
			map.put("vCode", version.getvCode());
			map.put("createTime", version.getvCreateTime());
			map.put("ifMust", version.getvIfMust());
			map.put("fileSize", version.getvFileSize());
			map.put("content", version.getvContent());
		}else {
			map.put("url", "");
			map.put("status", status);
			map.put("vCode", "");
			map.put("createTime", "");
			map.put("ifMust", "");
			map.put("fileSize", "");
			map.put("content", "");
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doResetUserPassword(String uId,String uTel, String uNewPassword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 根据uId获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (uTel.equals(user.getuTel())) {
			//手机号正确修改密码
			/**
			 * 修改用户密码
			 */
			int row = userMapper.doUpdateUserPassword(uId, uNewPassword);
			if (1 != row) {
				map.put("errorString", "修改用户密码失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
			map.put("status", "true");
			map.put("errorString", "");
		}else{
			map.put("status", "false");
			map.put("errorString", "手机号码不存在");
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doUpdateSecretQuestion(String uId, String uOldFirstSqId, String uOldFirstSqAnswer,
			String uOldSecondSqId, String uOldSecondSqAnswer, String uOldThirdSqId, String uOldThirdSqAnswer,
			String uNewFirstSqId, String uNewFirstSqAnswer, String uNewSecondSqId, String uNewSecondSqAnswer,
			String uNewThirdSqId, String uNewThirdSqAnswer) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 判断用户输入的密保问题是否正确
		 */
		User user = userMapper.selectUserSecretQuestion(uId,uOldFirstSqId,uOldFirstSqAnswer,uOldSecondSqId,uOldSecondSqAnswer,uOldThirdSqId,uOldThirdSqAnswer);
		if (user!= null) {
			//输入密保正确
			/**
			 * 修改用户密保问题
			 */
			int row = userMapper.doUpdateSecretQuestion(uId,uNewFirstSqId,uNewFirstSqAnswer,uNewSecondSqId,uNewSecondSqAnswer,uNewThirdSqId,uNewThirdSqAnswer);
			if (1 != row) {
				map.put("errorString", "修改用户密保失败");
				map.put("status", "false");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
		}else {
			map.put("errorString", "密保问题输入错误");
			map.put("status", "false");
			return map;
		}
		return map;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doGetUserAnswerSecretQuestion(String uId, String firstAnswer, String secondAnswer,
			String thirdAnswer) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 密保验证
		 */
		User user = userMapper.doGetUserAnswerSecretQuestion(uId,firstAnswer,secondAnswer,thirdAnswer);
		if (user == null) {
			map.put("status", "false");
			map.put("errorString", "密保问题输入错误");
			return map;
		}else {
			String uPhoneId = Utils.randomUUID();//生成新的uPhoneId
			map.put("uPhoneId", uPhoneId);
			/**
			 * 修改用户的uPhoneId
			 */
			int row = userMapper.doupdateUserUPhoneId(uId,uPhoneId);
			if (1 != row) {
				map.put("errorString", "修改用户的uPhoneId失败");
				map.put("status", "false");
				throw new RuntimeException();
			} else {
				map.put("errorString", "");
				map.put("status", "true");
			}
			return map;
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doAddMessageCopy(String eventType, String convType, String to, String fromAccount, String msgTimestamp,
			String msgType, String msgidClient, String msgidServer, String body1) throws Exception {
		Map<String,Object> map = new HashMap<>();
		/**
		 * 插入消息抄送表
		 */
		int row = userMapper.doAddMessageCopy(eventType,convType, to, fromAccount, msgTimestamp,
				msgType, msgidClient, msgidServer,body1);
		
		if (1 != row) {
			map.put("errorString", "插入消息抄送表失败");
			map.put("status", "false");
			throw new RuntimeException();
		} else {
			map.put("errorString", "");
			map.put("status", "true");
		}
		return map;
	}
	@Override
	public Map<String, Object> doGetGuidePic(String gAppType, String gEdition) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据客户端类型获取引导页相关信息
		 * t_guide：G_EDITION、P_TAG
		 */
		Guide guide = userMapper.doGetGuideInfo(Integer.valueOf(gAppType)
				.intValue());
		if (null != guide) {
			// 判断是否需要展示引导页
			if(guide.getgEdition() <= Integer.valueOf(gEdition)
					.intValue()) { // 无需展示
				map.put("isShow", 0); // 无需展示
				map.put("pics", null); // 图片列表
			} else { // 需展示
				map.put("isShow", 1); // 需展示
				/**
				 * 根据图片pTag获取图片信息
				 * t_pic:P_NAME、P_NO
				 */
				List<Pic> pics = userMapper
						.doGetPicInfoByPTag(guide.getpTag());
				List<Map<String,Object>> picList 
						= new ArrayList<Map<String,Object>>();
				for (int i = 0; i < pics.size(); i++) {
					Map<String,Object> picMap = new HashMap<String,Object>();
					picMap.put("url",Utils.PIC_BASE_URL + Utils.GUIDE_PIC +
							pics.get(i).getpName()); // 图片链接
					picMap.put("pNo", pics.get(i).getpNo()); // 图片编号
					picList.add(picMap);
				}
				map.put("pics", picList); // 图片列表
			}
			map.put("gEdition", guide.getgEdition()); // 引导页版本
		} else {
			map.put("isShow", 0); // 无需展示
			map.put("pics", null); // 图片列表
			map.put("gEdition", "0"); // 引导页版本
		}
		return map;
	}
	@Override
	public int doSelectMessage(String to, String fromAccount, String msgTimestamp, String body1) throws Exception {
		/**
		 * 判断是否是重发
		 */
		int row = userMapper.doSelectMessage(to,fromAccount,msgTimestamp,body1);
		return row;
	}
	@Override
	public Map<String, Object> doGetMainInterface(String acId, String uId,
			String page, String pageSize) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取主页面轮播图
		 * 
		 */
		List<Pic> pics = userMapper.doGetSlidePic();
		List<Map<String, Object>> slidePicList = new ArrayList<Map<String,Object>>();
		for (Pic pic : pics) {
			Map<String,Object> picMap = new HashMap<String,Object>();
			picMap.put("pId", pic.getpId());
			picMap.put("pName",Utils.PIC_BASE_URL + Utils.SLIDE_PIC + pic.getpName());
			picMap.put("pNo", pic.getpNo());
			slidePicList.add(picMap);
		}
		map.put("slidePicList", slidePicList);
		/**
		 * 获取商品大类推荐
		 */
		List<ProductType> productTypes = productMapper.doGetProductTypeByRecommend();
		List<Map<String, Object>> productTypeList = new ArrayList<Map<String,Object>>();
		for (ProductType productType : productTypes) {
			Map<String,Object> productTypeMap = new HashMap<String,Object>();
			productTypeMap.put("ptId", productType.getPtId());
			productTypeMap.put("ptName", productType.getPtName());
			productTypeList.add(productTypeMap);
		}
		map.put("productTypeList", productTypeList);
		// 如果acId为空，则返回石家庄市的店铺、商品数据
		if (Utils.isEmpty(acId)) {
			/**
			 * 根据城市名称 37--石家庄
			 */
			acId = "37";
		}
		/**
		 * 获取某城市内推荐店铺的数量
		 */
		int count = storeMapper.doGetRecommendStoreCount(acId);
		if (0 == count) {
			map.put("recommendStoreList", null);
		}else {
			// 要查询的店铺的记录的最大个数 最多返回3个推荐店铺
			int queryRows = 3;
			List<RecommendStore> recommendStores = null;
			if (queryRows >= count) {
				/**
				 * 获取某城市推荐店铺信息
				 */
				recommendStores = storeMapper.doGetRecommendStore(acId,
						0, queryRows);
			} else {
				int min = Utils.getLimitRandomNum(0, count - queryRows);
				/**
				 * 获取某城市推荐店铺信息
				 */
				recommendStores = storeMapper.doGetRecommendStore(acId,
						min, queryRows);
			}
			
			List<Map<String, Object>> recommendStoreList = new ArrayList<Map<String,Object>>();
			for (RecommendStore recommendStore : recommendStores) {
				Map<String,Object> recommendStoreMap = new HashMap<String,Object>();
				recommendStoreMap.put("sId", recommendStore.getsId());
				recommendStoreMap.put("rsTitle", recommendStore.getRsTitle());
				recommendStoreMap.put("rsContent", recommendStore.getRsContent());
				recommendStoreMap.put("picName",Utils.PIC_BASE_URL + Utils.STORE_PIC
						+ recommendStore.getPic().getpName());
				recommendStoreList.add(recommendStoreMap);
			}
			map.put("recommendStoreList", recommendStoreList);
		}
		// 根据用户足迹、订单获取某城市内推荐商品  用户未登录，则返回默认的推荐商品
		List<Product> products = null;
		if (Utils.isEmpty(uId)) { // 用户未登录
			/**
			 * 分页获取某城市内默认的推荐商品列表
			 */
			products = productMapper.getDefaultRecommendProductList(acId, 
					(Integer.valueOf(page).intValue() - 1) * Integer
						.valueOf(pageSize).intValue(),
						Integer.valueOf(pageSize).intValue());
		} else { // 用户已登录
			/**
			 * 根据用户足迹和订单分页获取默认推荐的商品列表接口接口
			 */
			products = productMapper
					.getRecommendProductListOfUser(uId, acId,
							(Integer.valueOf(page).intValue() - 1) * Integer
							.valueOf(pageSize).intValue(),
							Integer.valueOf(pageSize).intValue());
			/**
			 * 若该用户无足迹或订单，则返回默认的推荐商品列表
			 */
			if (null == products || 0 == products.size()) {
				products = productMapper.getDefaultRecommendProductList(acId, 
						(Integer.valueOf(page).intValue() - 1) * Integer
							.valueOf(pageSize).intValue(),
							Integer.valueOf(pageSize).intValue());
			}
		}
		List<Map<String, Object>> recommendProductList 
				= new ArrayList<Map<String, Object>>();
		for (int i = 0; i < products.size(); i++) {
			Map<String,Object> productMap = new HashMap<String,Object>();
			productMap.put("pId", products.get(i).getpId()); // 商品主键标识
			productMap.put("pName", products.get(i).getpName()); // 商品名称
			productMap.put("pOriginalPrice", products.get(i)
					.getpOriginalPrice()); // 商品原价
			productMap.put("pNowPrice", products.get(i).getpNowPrice()); // 商品现价
			try {
				productMap.put("url",Utils.PIC_BASE_URL + Utils.PRODUCT_PIC 
						+ products.get(i).getPic().getpName()); // 图片链接
			} catch (Exception e) {
				// TODO: handle exception
				productMap.put("url", ""); // 图片链接
			}
			productMap.put("pBrowseNum", products.get(i)
					.getpBrowseNum()); // 商品被浏览次数
			recommendProductList.add(productMap);
		}
		map.put("recommendProductList", recommendProductList); // 推荐商品列表
		return map;
	}
	@Override
	public Map<String, Object> doGetAreaCode(String acCode) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据行政区划代码获取行政区域信息
		 */
		AreaCode areaCode = userMapper.doGetAreaCode(acCode);
		if (null != areaCode) {
			// 若为区，则返回所在市的acId
			if (Utils.isEmpty(areaCode.getAcName())) {
				map.put("acId", areaCode.getAcId());
			} else {
				map.put("acId", areaCode.getAcParent());
			}
			map.put("acParent", areaCode.getAcParent());
		}else {
			map.put("errorString", "acCode错误");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doUpdateUserPhoneIdAndReturnUserInfo(String uId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 更新用户设备id
		 */
		String uPhoneId = Utils.randomUUID();
		int updateResult = userMapper.msUpdateUserPhoneId(uPhoneId, uId);
		if (1 == updateResult) { // 更新成功
			/**
			 * 获取用户数据
			 */
			User user = userMapper.doGetUserInfoByUId(uId);
			if (null != user) {
				/**
				 * 获取商户数据
				 */
				Store store = storeMapper.doGetStoreInfo(user.getsId());
				if (null != store) {
					map.put("status", "true");
					map.put("errorString", "");
					map.put("sId", user.getsId()); // 商户店铺主键标识
					map.put("acId", store.getAcId()); // 商户店铺所在的城市
					map.put("sType", store.getsType()); // 商户类型 0--个人商户，1--企业商户
					map.put("uPhoneId", uPhoneId); // 设备ID
				} else {
					map.put("status", "false");
					map.put("errorString", "服务异常，请稍后重试！");
					throw new RuntimeException();
				}
			}
		} else { // 更新失败
			map.put("status", "false");
			map.put("errorString", "验证失败，请稍后重试！");
			throw new RuntimeException();
		}
		return map;
	}
	@Override
	public Map<String, Object> doRetrievePsw(String uAccount) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据uAccount获取用户密码
		 */
		User user = userMapper.doRetrievePsw(uAccount);
		if (null != user) {
			map.put("status", "true");
			map.put("errorString", "");
			/**
			 * 以短信方式通知用户其当前密码
			 */
			CAPTCHA captcha = new CAPTCHA();
			captcha.sendUserPasswordNotice(uAccount,
					user.getuPassword());
		} else {
			map.put("status", "false");
			map.put("errorString", "没有该账号！");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> doGetSearchCityList(String keyWords) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据行政区划代码获取行政区域信息
		 */
		AreaCode areaCode = userMapper.doGetSearchCityList(keyWords);
		if (areaCode != null) {
			map.put("acId", areaCode.getAcId());
			map.put("acCode", areaCode.getAcCode());
			map.put("acCity", areaCode.getAcCity());
		}else {
			map.put("errorString", "城市不存在或管理员尚未开通!");
			map.put("status", "false");
			return map;
		}
		map.put("errorString", "");
		map.put("status", "true");
		return map;
	}
	@Override
	public Map<String, Object> doGetStoreUserInfo(String uId, String sId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据用户主键标识获取用户信息
		 */
		User user = userMapper.doGetUserInfoByUId(uId);
		if (null != user) {
			/**
			 * 获取商铺信息
			 */
			Store store = storeMapper.doGetStoreInfo(sId);
			if (null != store) {
				/**
				 * 获取商户店铺宣传图片
				 */
				List<Pic> storePics = manageSystemMapper.msGetPicByPTag(store.getpTag());
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
				map.put("storePics", storePicList);
				map.put("sName", store.getsName()); // 商户店铺名称
				map.put("sDescribe", store.getsDescribe()); // 商户描述
				map.put("sLeader", store.getsLeader()); // 商户负责人
				map.put("sLegal", store.getsLegal()); // 商户法人
				map.put("sTel", store.getsTel()); // 商户联系电话
				map.put("sAddress", store.getsAddress()); // 商户地址
				map.put("sLevel", store.getsLevel()); // 商户评级
			} else {
				map.put("status", "false");
				map.put("errorString", "当前账号非商户账号！");
				return map;
			}
			map.put("uNickName", user.getuNickName()); // 商户昵称
			map.put("uSex", user.getuSex()); // 商户性别
			map.put("uEmail", user.getuEmail()); // 商户邮箱
			map.put("uBirthday", user.getuBirthday()); // 商户出生日期
			map.put("pTag", user.getpTag());
			/**
			 * 获取用户头像信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(user.getpTag());
			if (null == pic) {
				map.put("profile", ""); // 商户头像
			} else {
				map.put("profile", Utils.PIC_BASE_URL + Utils.PROFILE_PIC + 
						pic.getpName()); // 商户头像
			}
			map.put("uTel", user.getuTel()); // 商户联系电话
			map.put("uTrueName", user.getuTrueName()); // 商户真实姓名
			map.put("status", "true");
			map.put("errorString", "");
		} else {
			map.put("status", "false");
			map.put("errorString", "账号不存在！");
			return map;
		}
		return map;
	}
	
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> dochangeStoreUserInfo(String uId, String sId,
			String which, String uNickName, String pTag, String pName, String uSex, String uEmail,
			String uBirthday, String uTel, String sName, String sDescribe,
			String sTel) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		if ("0".equals(which)) {
			/**
			 * 获取用户头像信息
			 */
			Pic pic = userMapper.doSelectOnePicInfoByPTag(pTag);
			if (null == pic) { // 未设置过头像
				if (!Utils.isEmpty(pName)) { // 需要设置头像
					/**
					 * 插入商户头像
					 */
					int result = manageSystemMapper.msAddPicInfo(Utils.PROFILE_PIC,
							pName, 0, pTag);
					if (1 != result) { // 插入失败
						map.put("status", "false");
						map.put("errorString", "修改账号信息失败！");
						return map;
					}
				} 
			} else { // 设置过头像
				// 判断是否需要更新商户账号头像
				if (!pName.equals(pic.getpName()) 
						&& !Utils.isEmpty(pName)) { // 需要更新
					/**
					 * 先删除t_pic表中商户账号头像记录
					 */
					int delResult = manageSystemMapper.msDelPicsByPtag(pTag);
					if (1 == delResult) {
						/**
						 * 插入商户头像
						 */
						int addResult = manageSystemMapper.msAddPicInfo(Utils.PROFILE_PIC,
								pName, 0, pTag);
						if (1 != addResult) { // 插入失败
							map.put("status", "false");
							map.put("errorString", "修改账号信息失败！");
							return map;
						}
					} else {
						map.put("status", "false");
						map.put("errorString", "修改账号信息失败！");
						return map;
					}
				}
			}
			/**
			 * 修改商户个人基本信息
			 */
			int result = userMapper.doUpdateStoreUserInfo(uId, uNickName,
					Integer.valueOf(uSex).intValue(),
					uEmail, uBirthday, uTel);
			if (1 == result) {
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("status", "false");
				map.put("errorString", "修改账号信息失败！");
			}
		} else if ("1".equals(which)) {
			/**
			 * 修改商户店铺基本信息
			 */
			int result = userMapper.updateStoreInfo(sId, 
					sName, sDescribe, sTel);
			if (1 == result) {
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("status", "false");
				map.put("errorString", "修改商户信息失败！");
			}
		}
		return map;
	}
	
	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> doUpdateStoreUserPsw(String uId, String sId,
			String uAccount, String uPassword,
			String uNewPassword) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 判断商户是否存在
		 */
		int result = userMapper.checkStoreUserExist(uAccount, uPassword, uId);
		if (1 == result) {
			/**
			 * 修改商户当前密码
			 */
			int row = userMapper.doUpdateUserPassword(uId, uNewPassword);
			if (1 == row ) {
				map.put("errorString", "");
				map.put("status","true");
			} else {
				map.put("errorString", "修改密码失败！");
				map.put("status","false");
				throw new RuntimeException();
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "账号或密码错误！");
			return map;
		}
		return map;
	}
	
	@Override
	public Map<String, Object> doCheckUpdate(String vType, String vBuildCode,
			String vSystemType) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 根据应用类型、系统类型获取最新的应用版本信息
		 * t_version:V_ID、V_CODE、V_CREATE_TIME、V_IF_MUST、V_FILE_SIZE、
		 * 		V_CONTENT、V_URL
		 */
		Version version = userMapper.getVersionInfo(Integer.valueOf(vType).intValue(),
				Integer.valueOf(vSystemType).intValue());
		if (null != version) {
			// 判断是否需要更新
			if (Integer.valueOf(vBuildCode).intValue() 
					< Integer.valueOf(version.getBuildCode()).intValue()) { // 需要更新
				map.put("isNeedUpdate", "true"); // 需要更新
				map.put("vCode", version.getvCode()); // 应用版本名称
				map.put("vCreateTime", version.getvCreateTime()); // 版本发布时间
				map.put("vIfMust", version.getvIfMust()); // 是否强制更新 0--否，1--是
				map.put("vFileSize", version.getvFileSize()); // 更新文件的大小
				map.put("vContent", version.getvContent()); // 更新内容
				map.put("vUrl", version.getvUrl()); // 更新下载地址
			} else { // 无需更新
				map.put("isNeedUpdate", "false"); // 无需更新
			}
		}
		return map;
	}
	@Override
	public List<Map<String, Object>> doGetMerchantLonLatList(String uId, String roId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取某商户针对于特定需求订单的移动轨迹列表
		 * t_moving_trajectory:MT_LON、MT_LAT
		 */
		List<MovingTrajectory> points = userMapper
				.doGetMerchantLonLatList(roId);
		for (int i = 0; i < points.size(); i++) {
			Map<String,Object> pointMap = new HashMap<String,Object>();
			pointMap.put("mtLon", points.get(i).getMtLon()); // 经度
			pointMap.put("mtLat", points.get(i).getMtLat()); // 纬度
			map.add(pointMap);
		}
		return map;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class,Exception.class})
	public Map<String, Object> doSetUserMemoName(String fromUid, String toUid, String memoName) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 获取用户设置备注名信息
		 */
		UserMemoName userMemoName = userMapper.doGetUserMemoNameByUid(fromUid, toUid);
		if (userMemoName == null) {
			/**
			 * 为用户设置备注名
			 */
			int result = userMapper.doSetUserMemoName(fromUid, toUid, memoName);
			if (1 == result) {
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("status", "false");
				map.put("errorString", "设置失败!");
				throw new RuntimeException();
			}
		} else {
			/**
			 * 修改用户设置的备注名
			 */
			int result = userMapper.doUpdataUserMemoName(fromUid, toUid, memoName);
			if (1 == result) {
				map.put("status", "true");
				map.put("errorString", "");
			} else {
				map.put("status", "false");
				map.put("errorString", "修改失败!");
				throw new RuntimeException();
			}
		}
		return map;
	}
	@Override
	public List<Map<String, Object>> doGetUserMemoName(String uId) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
		/**
		 * 获取某用户为别人设置的备注名
		 * t_user_memo_name:UMN_TO_ID、UMN_NAME
		 */
		List<UserMemoName> memoNames = userMapper.doGetUserMemoName(uId);
		for (int i = 0; i < memoNames.size(); i++) {
			Map<String,Object> userMap = new HashMap<String,Object>();
			userMap.put("umnToId", memoNames.get(i).getUmnToId()); // 被命名的用户的主键标识
			userMap.put("umnName", memoNames.get(i).getUmnName()); // 备注名
			map.add(userMap);
		}
		return map;
	}
}
