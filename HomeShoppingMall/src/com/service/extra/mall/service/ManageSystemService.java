package com.service.extra.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * desc：平台管理系统相关接口服务类
 * @author L
 * time:2018年4月23日
 */
public interface ManageSystemService {

	/**
	 * 
	 * desc:添加系统日志
	 * param:
	 * return:int
	 * time:2018年5月4日
	 * author:L
	 */
	public int doMsAddSysOperateLog(String uId, 
			String solType, String solObject, String solTable)
					throws Exception;
	
	public Map<String, Object> doMsLogin(String account, 
			String password) throws Exception;
	
	public List<Map<String, Object>> doMsGetSysNoticeList(String uId)
			throws Exception;
	
	public Map<String, Object> doMsGetAdminIndexInfo(String uId)
			throws Exception;
	
	public Map<String, Object> doMsGetStoreList(String sName,
			String sType, String sLeader, String sChecked, String isRecommend,
			String start, String end)
			throws Exception;
	
	public Map<String, Object> doMsGetStoreOtherInfoBySId(String sId,
			String sType)
			throws Exception;
	
	public Map<String, Object> doMsChangeStoreCheckStatus(String uId, String sId,
			String sChecked, String sCheckedOpinion)
			throws Exception;
	
	public Map<String, Object> doMsAddOrChangeStoreConfigInfo (
			String uId, String sId,
			String sOrderLimint, String requirementServiceCharge,
			String productServiceCharge, String sLevel, String sWeight,
			String sBoothNum, String isRecommend, String rsTitle, String rsContent,
			String picData)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetProductTypeList()
			throws Exception;
	
	public Map<String, Object> doMsGetProductList (String userType, String pName,
			String pHaveBooth, String ptId, String pChecked, 
			String start, String end)
			throws Exception;
	
	public Map<String, Object> doMsChangeProductCheckStatus (String uId, String pId,
			String pChecked, String pCheckedOpinion)
			throws Exception;
	
	public Map<String, Object> doMsAddOrChangeProductConfigInfo (
			String uId, String pId, String pWeight)
			throws Exception;
	
	public List<Map<String, Object>> doMsSysSettingGetSysNoticeList (
			String snTitle, String uTrueName)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingAddSysNotice (
			String uId, String snTitle, String snContent)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingChangeSysNotice (
			String uId, String snId, String snTitle, String snContent)
			throws Exception;
	
	public List<Map<String, Object>> doMsSysSettingGetSysOperateLogTypeList ()
			throws Exception;
	
	public Map<String, Object> doMsSysSettingGetSysOperateLogList (
			String solType, String start, String end)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingGetFeedbackList (
			String fContent, String fAppType, 
			String fType, String start, String end)
			throws Exception;
	
	public List<Map<String, Object>> doMsSysSettingGetUserRoleList ()
			throws Exception;
	
	public Map<String, Object> doMsSysSettingGetUserList (
			String uAccount, String uTrueName, String uTel,
			String urId, String start, String end)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingAddUserInfo (
			String uId, String uAccount, String uPassword, String uNickName,
			String uTrueName, String uSex, String uEmail, String uTel)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingChangeUserInfo (
			String uId, String targetId, String uAccount, String uPassword, String uNickName,
			String uTrueName, String uSex, String uEmail, String uTel)
			throws Exception;
	
	public List<Map<String, Object>> doMsSysSettingGetAreaList ()
			throws Exception;
	
	public Map<String, Object> doMsSysSettingGetCityInfoByCityName (
			String acCity)
			throws Exception;
	
	public Map<String, Object> doMsSysSettingAddOrChangeOpenCityInfo (
			String uId, String acId, String isOpen, String ocIsHot)
			throws Exception;
	
	public List<Map<String, Object>> doMsSysSettingGetPTDByPtId (
			String ptId)
			throws Exception;
	
	public Map<String, Object> doMsAddProductTypeInfo (String uId,
			String ptId, String ptName)
			throws Exception;
	
	public Map<String, Object> doMsChangeProductTypeDetailInfo (String uId,
			String ptdId, String ptdName, String pTag, String pName)
			throws Exception;
	
	public Map<String, Object> doMsAddProductTypeDetailInfo (String uId,
			String ptdId, String ptId, String ptdName, String ptdFatherId,
			String pName)
			throws Exception;
	
	public Map<String, Object> doMsGetProductPropertyList (
			String ppName, String ppValue, String ppTag, String start,
			String end)
			throws Exception;
	
	public Map<String, Object> doMsAddProductProperty (String uId,
			String ppName, String ppValues, String ppTag, String ppChoseType,
			String ppRequired)
			throws Exception;
	
	public Map<String, Object> doMsGetProductTypeRelationPropertiesByPtdId
			(String ptdId) throws Exception;
	
	public Map<String, Object> doMsChangeProductTypeRelationProperties 
			(String uId, String ptdId, String ppTags) 
					throws Exception;
	
	public Map<String, Object> doMsGetApplyToCashList 
			(String sName, String utcrStatus, String start,
					String end)
			throws Exception;
	
	public Map<String, Object> doMsChangeApplyToCashStatus 
			(String uId, String utcrId, String utcrStatus)
						throws Exception;

	public Map<String, Object> doMsGetPersonalIndexInfo(String uId,
			String sId)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetRequirementOrderStatusList ()
			throws Exception;
	
	public List<Map<String, Object>> doMsGetRequirementTypeList ()
			throws Exception;
	
	public Map<String, Object> doMsGetRequirementOrderListWithCondition (
			String sId, String roStatus, String rtId,
			String urTitle, String urTrueName, String urTel,
			String start, String end)
			throws Exception;
	
	public Map<String, Object> doMsGetPersonalStoreInfoBySid (
			String sId)
			throws Exception;
	
	public Map<String, Object> doMsGetCompanyStoreInfoBySid (
			String sId)
			throws Exception;
	
	public Map<String, Object> doMsChangePersonalStoreInfoBySid (
			String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData)
			throws Exception;
	
	public Map<String, Object> doMsChangeCompanyStoreInfoBySid (
			String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData)
			throws Exception;
	
	public Map<String, Object> doMsGetStatusOfSafetyQuestions (
			String uId)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetSafetyQuestionsList ()
			throws Exception;
	
	public Map<String, Object> doMsSetSafetyQuestionForUser (
			String uId, String firstSqId, String firstSqAnswer, 
			String secondSqId, String secondSqAnswer,
			String thirdSqId, String thirdSqAnswer)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetSafetyQuestionOfUser (String uId)
			throws Exception;
	
	public Map<String, Object> doMsCheckSafetyQuestionOfUser (
			String uId, String questionData)
			throws Exception;
	
	public Map<String, Object> doMsRetrievePassword (
			String uId)
			throws Exception;
	
	public Map<String, Object> doMsChangePassword (
			String uId, String oldPassword, String newPassword)
			throws Exception;
	
	public Map<String, Object> doMsGetMyWallet (
			String uId, String sId)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetPayStyleList (
			String uId)
			throws Exception;
	
	public Map<String, Object> doMsApplyToCash (
			String uId, String sId, String psId, String utcrMoney, 
			String utcrAccount)
			throws Exception;
	
//	public Map<String, Object> doMsAddDepositRecord (
//			String uId, String sId, String psId, String udrMoney)
//			throws Exception;
	
	public Map<String, Object> doMsApplyToFreeForDepositRecord (
			String uId, String sId, String udrMoney)
			throws Exception;
	
	public Map<String, Object> doMsGetApplyToCashListBySId (
			String uId, String sId, String utcrStatus, String psId,
			String start, String end)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetMyArrangeListBySId (
			String uId, String sId)
			throws Exception;
	
	public Map<String, Object> doMsAddMyArrange (
			String uId, String sId, String saContent)
			throws Exception;
	
	public Map<String, Object> doMSUpdateMyArrange (
			String saId, String saContent)
			throws Exception;
	
	public Map<String, Object> doMsGetCompanyIndexInfo (
			String sId)
			throws Exception;
	
	public Map<String, Object> doMsGetProductListBySId (
			String sId, String pName, String ptId,
			String start, String end)
			throws Exception;
	
	public Map<String, Object> msGetBoothBySId (
			String sId)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetAllProductPropertyListOfProduct (
			String pId, String ptdId)
			throws Exception;
	
	public Map<String, Object> doMsChangeProductInfo (
			String sId, String pId, String pName, String pBrand, String pDescribe,
			String picData, String pOriginalPrice, String pNowPrice, 
			String pWeight, String pHtml, String pHaveBooth, String pStockNum,
			String ppIds)
			throws Exception;
	
	public Map<String, Object> doMsAddProductInfo (
			String uId, String sId, String pName, String pBrand, String pDescribe,
			String picData, String pOriginalPrice, String pNowPrice, String ptdId,
			String pWeight, String pHtml, String pHaveBooth, String pStockNum,
			String ppIds)
			throws Exception;
	
	public Map<String, Object> doMsDelProductInfoByPId (
			String pId)
			throws Exception;
	
	public List<Map<String, Object>> doMsGetProductOrderStatusList ()
			throws Exception;
	
	public Map<String, Object> doMsGetProductOrderListBySId (
			String sId, String poOrderId, String poStatus,
			String poDeliverName, String poDeliverTel, String start,
			String end)
			throws Exception;
	
	public Map<String, Object> doMsChangeProductOrderStatus (
			String poId, String poStatus, String poDeliverCompany,
			String poDeliverCode)
			throws Exception;
	
	public Map<String, Object> doMsReSubmitStoreAuditInfo (
			String sId, String sDescribe, String sTel,
			String sAddress, String sLon, String sLat, String picData,
			String sLeader, String sLeaderIdCard, String sLeaderPicName,
			String sLegal, String sLegalIdCard, String sLegalPicName,
			String sBusinessLicensePicName)
			throws Exception;
}
