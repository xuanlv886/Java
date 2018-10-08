package com.service.extra.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * desc：平台管理系统相关接口服务类
 * @author J
 * time:2018年5月17日
 */
public interface UserService {

	public Map<String, Object> doUserLogin(String uAccount, String uPassword) throws Exception;

	public Map<String, Object> doUserRegistered(String uAccount, String uPassword) throws Exception;

	public Map<String, Object> doGetUserInfo(String uId) throws Exception;

	public Map<String, Object> doUpdateUserPassword(String uAccount, String uPassword, String uNewPassword,String uId) throws Exception;

	public Map<String, Object> doUpdateUserSecretQuestion(String uId, String uFirstSqId, String uFirstSqAnswer, String uSecondSqId,
			String uSecondSqAnswer, String uThirdSqId, String uThirdSqAnswer) throws Exception;

	public Map<String, Object> doGetSafetyQuestion(String sqPosition) throws Exception;

	public Map<String, Object> doUpdateUserInfo(String uId, String uNickName, String uSex, String uEmail, String uBirthday) throws Exception;

	public Map<String, Object> doUpdateUserAvatar(String uId, String pName) throws Exception;

	public Map<String, Object> doGetUserDeliverAddress(String uId) throws Exception;

	public Map<String, Object> doAddUserDeliverAddress(String uId, String udaTrueName, String udaTel, String udaAddress) throws Exception;

	public Map<String, Object> doUpdateUserDeliverAddress(String udaId,String uId, String udaTrueName, String udaTel, String udaAddress
			) throws Exception;

	public Map<String, Object> doDelUserDeliverAddress(String udaId, String uId) throws Exception;

	public Map<String, Object> doSetDefaultUserDeliverAddress(String udaId, String uId) throws Exception;

	public Map<String, Object> doGetUserAttention(String uId,String page,String size) throws Exception;

	public Map<String, Object> doAddUserAttention(String uId, String sId) throws Exception;

	public Map<String, Object> doDelUserAttention(String uId, String sId) throws Exception;

	public Map<String, Object> doAddUserTrolley(String uId, String pId, String sId, String utProductNum, String utProductProperty) throws Exception;

	public Map<String, Object> doDelUserTrolley(String uId, String utIdList) throws Exception;

	public Map<String, Object> doGetUserTrolley(String uId) throws Exception;

	public Map<String, Object> doAddUserCollection(String uId, String pId) throws Exception;

	public Map<String, Object> doDelUserCollection(String uId, String pId) throws Exception;

	public Map<String, Object> doGetUserCollection(String uId,String page,String size) throws Exception;

	public Map<String, Object> doGetUserFootPrint(String uId,String page,String size) throws Exception;

	public Map<String, Object> doDelUserFootPrint(String uId, String ufIdList) throws Exception;

	public Map<String, Object> doAddUserFeedBack(String uId, String fType, String fAppType, String fContent) throws Exception;

	public Map<String, Object> doGetPayStyle() throws Exception;

	public Map<String, Object> doForgetSecretQuestion(String uId, String uPassword) throws Exception;

	public Map<String, Object> doGetCityList() throws Exception;

	public Map<String, Object> doGetPersonCenter(String uId) throws Exception;

	public Map<String, Object> doGetversion(String vType, String Code, String phoneType) throws Exception;

	public Map<String, Object> doResetUserPassword(String uId,String uTel, String uNewPassword) throws Exception;

	public Map<String, Object> doUpdateSecretQuestion(String uId, String uOldFirstSqId, String uOldFirstSqAnswer,
			String uOldSecondSqId, String uOldSecondSqAnswer, String uOldThirdSqId, String uOldThirdSqAnswer,
			String uNewFirstSqId, String uNewFirstSqAnswer, String uNewSecondSqId, String uNewSecondSqAnswer,
			String uNewThirdSqId, String uNewThirdSqAnswer) throws Exception;

	public Map<String, Object> doGetUserAnswerSecretQuestion(String uId, String firstAnswer, String secondAnswer,
			String thirdAnswer) throws Exception;

	public Map<String, Object> doAddMessageCopy(String eventType, String convType, String to, String fromAccount, String msgTimestamp,
			String msgType, String msgidClient, String msgidServer, String body1) throws Exception;

	public Map<String, Object> doGetGuidePic(String gAppType, String gEdition)
			throws Exception;

	public int doSelectMessage(String to, String fromAccount, String msgTimestamp, String body1) throws Exception;

	public Map<String, Object> doGetMainInterface(String acId, String uId,
			String page, String pageSize) throws Exception;

	public Map<String, Object> doGetAreaCode(String acCode) throws Exception;

	public Map<String, Object> doUpdateUserPhoneIdAndReturnUserInfo(String uId) throws Exception;

	public Map<String, Object> doRetrievePsw(String uAccount) throws Exception;
	
	public Map<String, Object> doGetSearchCityList(String keyWords) throws Exception;
	
	public Map<String, Object> doGetStoreUserInfo(String uId, String sId) throws Exception;

	public Map<String, Object> dochangeStoreUserInfo(String uId, String sId,
			String which, String uNickName, String pTag, String pName, String uSex, String uEmail,
			String uBirthday, String uTel, String sName, String sDescribe,
			String sTel) throws Exception;
	
	public Map<String, Object> doUpdateStoreUserPsw (String uId, String sId,
			String uAccount, String uPassword,String uNewPassword)
					throws Exception;
	
	public Map<String, Object> doCheckUpdate (String vType,
			String vBuildCode, String vSystemType)
					throws Exception;
	
	public List<Map<String, Object>> doGetMerchantLonLatList(String uId, String roId)
			throws Exception;
	
	public Map<String, Object> doSetUserMemoName (String fromUid,String toUid, String memoName)
					throws Exception;
	
	public List<Map<String, Object>> doGetUserMemoName(String uId)
			throws Exception;
}
