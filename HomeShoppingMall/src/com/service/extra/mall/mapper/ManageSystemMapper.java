package com.service.extra.mall.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
import com.service.extra.mall.model.UserDepositRecord;
import com.service.extra.mall.model.UserRole;
import com.service.extra.mall.model.UserToCashRecord;
import com.service.extra.mall.model.UserWallet;


/**
 * 
 * desc：平台管理系统相关接口Mapper
 * @author L
 * time:2018年4月23日
 */
public interface ManageSystemMapper {

	public int doMsAddSysOperateLog(
			@Param("uId")String uId,
			@Param("solType")int solType,
			@Param("solObject")String solObject,
			@Param("solTable")String solTable)
			throws SQLException;
	
	public User doMsLogin(@Param("account")String account,
			@Param("password")String password) throws SQLException;
	
	public List<SysNotice> doMsGetSysNoticeList() throws SQLException;
	
	public UserRole msGetUserRoleByUid(@Param("uId")String uId) 
			throws SQLException;
	
	public int msGetTotalUserNum() throws SQLException;
	
	public int msGetStoreUserNum() throws SQLException;
	
	public int msGetNormalUserNum() throws SQLException;
	
	public int msGetTotalRequirementNum() throws SQLException;
	
	public int msGetTodayRequirementNum() throws SQLException;
	
	public int msGetYestodayRequirementNum() throws SQLException;
	
	public int msGetTodayOverRequirementNum() throws SQLException;
	
	public int msGetTotalProductNum() throws SQLException;

	public int msGetTodayProductNum() throws SQLException;

	public int msGetYestodayProductNum() throws SQLException;

	public int msGetTodayOverOrderNum() throws SQLException;

	public int msGetTodayBackOrderNum() throws SQLException;
	
	public int msGetUncheckStoreNum() throws SQLException;

	public int msGetUncheckProductNum() throws SQLException;

	public int msGetUncheckApplyToCashNum() throws SQLException;
	
	public List<Store> doMsGetStoreList(
			@Param("sName")String sName,
			@Param("sType")String sType,
			@Param("sLeader")String sLeader,
			@Param("sChecked")String sChecked,
			@Param("isRecommend")String isRecommend,
			@Param("start")int start,
			@Param("end")int end) throws SQLException;
	
	public int msGetStoreListSatisfyConditionCount(
			@Param("sName")String sName,
			@Param("sType")String sType,
			@Param("sLeader")String sLeader,
			@Param("sChecked")String sChecked,
			@Param("isRecommend")String isRecommend) throws SQLException;
	
	public List<Pic> msGetPicByPTag(@Param("pTag")String pTag)
			throws SQLException;
	
	public UserWallet msGetStoreWalletListBySId
			(@Param("sId")String sId)
			throws SQLException;
	
	public int msGetStoreProductNum
			(@Param("sId")String sId)
			throws SQLException;
	
	public int doMsChangeStoreCheckStatus
			(@Param("uId")String uId,
					@Param("sId")String sId,
					@Param("sChecked")int sChecked,
					@Param("sCheckedOpinion")String sCheckedOpinion)
					throws SQLException;
	
	public int msChangeProductHaveBoothStatus(@Param("sId")String sId)
			throws SQLException;
	
	public int doMsAddOrChangeStoreConfigInfo(
			@Param("sOrderLimint")double sOrderLimint,
			@Param("requirementServiceCharge")String requirementServiceCharge,
			@Param("productServiceCharge")String productServiceCharge,
			@Param("sLevel")int sLevel,
			@Param("sWeight")int sWeight,
			@Param("sBoothNum")int sBoothNum,
			@Param("sLeftBoothNum")int sLeftBoothNum,
			@Param("sId")String sId)
			throws SQLException;
	
	public Store msGetStoreCheckedStatus(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<ProductType> doMsGetProductTypeList() throws SQLException;

	public List<Product> doMsGetProductList
			(@Param("pName")String pName,
			@Param("pHaveBooth")String pHaveBooth,
			@Param("ptId")String ptId,
			@Param("pChecked")String pChecked,
			@Param("start")int start,
			@Param("end")int end,
			@Param("userType")String userType)
			throws SQLException;
	
	public int msGetProductListSatisfyConditionCount
			(@Param("pName")String pName,
			@Param("pHaveBooth")String pHaveBooth,
			@Param("ptId")String ptId,
			@Param("pChecked")String pChecked,
			@Param("userType")String userType)
			throws SQLException;
	
	public int doMsChangeProductCheckStatus(
			@Param("pChecked")int pChecked,
			@Param("uId")String uId,
			@Param("pCheckedOpinion")String pCheckedOpinion,
			@Param("pId")String pId)
			throws SQLException;
	
	public int msAddOrChangeProductConfigInfo(
			@Param("pWeight")int pWeight,
			@Param("pId")String pId)
			throws SQLException;
	
	public Product msGetProductCheckedStatus(
			@Param("pId")String pId)
			throws SQLException;
	
	public List<SysNotice> doMsSysSettingGetSysNoticeList(
			@Param("snTitle")String snTitle,
			@Param("uTrueName")String uTrueName)
			throws SQLException;
	
	public int doMsSysSettingAddSysNotice(
			@Param("uuid")String uuid,
			@Param("snTitle")String snTitle,
			@Param("snContent")String snContent,
			@Param("uId")String uId)
			throws SQLException;
	
	public int doMsSysSettingChangeSysNotice(
			@Param("snTitle")String snTitle,
			@Param("snContent")String snContent,
			@Param("uId")String uId,
			@Param("snId")String snId)
			throws SQLException;
	
	public List<SysOperateLog> doMsSysSettingGetSysOperateLogList(
			@Param("solType")String solType,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msSysSettingGetSysOperateLogCount(
			@Param("solType")String solType)
			throws SQLException;

	public List<FeedBack> doMsSysSettingGetFeedbackList(
			@Param("fContent")String fContent,
			@Param("fAppType")String fAppType,
			@Param("fType")String fType,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msSysSettingGetFeedbackCount(
			@Param("fContent")String fContent,
			@Param("fAppType")String fAppType,
			@Param("fType")String fType)
			throws SQLException;
	
	public List<UserRole> doMsSysSettingGetUserRoleList()
			throws SQLException;
	
	public List<User> doMsSysSettingGetUserList(
			@Param("uAccount")String uAccount,
			@Param("uTrueName")String uTrueName,
			@Param("uTel")String uTel,
			@Param("urId")String urId,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msSysSettingGetUserCount(
			@Param("uAccount")String uAccount,
			@Param("uTrueName")String uTrueName,
			@Param("uTel")String uTel,
			@Param("urId")String urId)
			throws SQLException;
	
	public int doMsSysSettingAddUserInfo(
			@Param("uuId")String uuId,
			@Param("uAccount")String uAccount,
			@Param("uPassword")String uPassword,
			@Param("uNickName")String uNickName,
			@Param("uSex")int uSex,
			@Param("uEmail")String uEmail,
			@Param("uTel")String uTel,
			@Param("uTrueName")String uTrueName)
			throws SQLException;
	
	public int doMsSysSettingChangeUserInfo(
			@Param("uId")String uId,
			@Param("uAccount")String uAccount,
			@Param("uPassword")String uPassword,
			@Param("uNickName")String uNickName,
			@Param("uSex")int uSex,
			@Param("uEmail")String uEmail,
			@Param("uTel")String uTel,
			@Param("uTrueName")String uTrueName)
			throws SQLException;
	
	public List<AreaCode> msGetProvinceListOfArea() throws SQLException;
	
	public List<AreaCode> msGetCityListOfProvince(
			@Param("acId")String acId)
			throws SQLException;
	
	public AreaCode doMsSysSettingGetCityInfoByCityName(
			@Param("acCity")String acCity)
			throws SQLException;
	
	public AreaCode msGetProvinceByCityName(
			@Param("acId")String acId)
			throws SQLException;
	
	public OpenCity msGetOpenCityInfoByAcId(
			@Param("acId")String acId)
			throws SQLException;
	
	public int msGetStoreNumFromOpenCityByAcId(
			@Param("acId")String acId)
			throws SQLException;
	
	public int msChangeOpenCityInfoByOcId(
			@Param("ocIsHot")int ocIsHot,
			@Param("ocId")String ocId)
			throws SQLException;
	
	public int msDelOpenCityByOcId(
			@Param("ocId")String ocId)
			throws SQLException;
	
	public int msAddOpenCityInfo(
			@Param("uuId")String uuId,
			@Param("acId")String acId, 
			@Param("ocIsHot")int ocIsHot)
			throws SQLException;
	
	public List<ProductTypeDetail> msGetFirstPTDByPtId(
			@Param("typeId")String typeId)
			throws SQLException;
	
	public ProductTypeDetail msGetProductTypeDetailNameByPtdId(
			@Param("ptdId")String ptdId)
			throws SQLException;
	
	public int doMsAddProductTypeInfo(
			@Param("ptId")String ptId,
			@Param("ptName")String ptName)
			throws SQLException;
	
	public int doMsChangeProductTypeDetailInfo(
			@Param("ptdId")String ptdId,
			@Param("ptdName")String ptdName)
			throws SQLException;
	
	public int msChangePNameByPTag(
			@Param("pName")String pName,
			@Param("pTag")String pTag)
			throws SQLException;
	
	public int msAddPicInfo(
			@Param("pFileName")String pFileName,
			@Param("pName")String pName,
			@Param("pNo")int pNo,
			@Param("pTag")String pTag)
			throws SQLException;
	
	public int doMsAddProductTypeDetailInfo(
			@Param("ptdId")String ptdId,
			@Param("ptId")String ptId,
			@Param("ptdName")String ptdName,
			@Param("ptdFatherId")String ptdFatherId,
			@Param("pTag")String pTag)
			throws SQLException;
	
	public List<ProductProperty> doMsGetProductPropertyList(
			@Param("ppName")String ppName,
			@Param("ppValue")String ppValue,
			@Param("ppTag")String ppTag,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msGetProductPropertyCount(
			@Param("ppName")String ppName,
			@Param("ppValue")String ppValue,
			@Param("ppTag")String ppTag)
			throws SQLException;
	
	public int doMsAddProductProperty(
			@Param("ppName")String ppName,
			@Param("ppValue")String ppValue,
			@Param("ppTag")String ppTag,
			@Param("ppChoseType")int ppChoseType,
			@Param("ppRequired")int ppRequired)
			throws SQLException;
	
	public ProductTypeDetail msGetProductTypeDetailInfoByPtdId(
			@Param("ptdId")String ptdId)
			throws SQLException;
	
	public List<ProductDetailTypeRelationProductProperty> msGetProductPropertiesByPtdId(
					@Param("ptdId")String ptdId)
							throws SQLException;
	
	public int msDelProductTypeRelationProperties(
			@Param("ptdId")String ptdId)
					throws SQLException;
	
	public int msAddProductTypeRelationProperty(
			@Param("ppTag")String ppTag,
			@Param("ptdId")String ptdId)
			throws SQLException;
	
	public List<UserToCashRecord> doMsGetApplyToCashList(
			@Param("sName")String sName,
			@Param("utcrStatus")String utcrStatus,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msGetApplyToCashCount(
			@Param("sName")String sName,
			@Param("utcrStatus")String utcrStatus)
			throws SQLException;
	
	public int doMsChangeApplyToCashStatus(
			@Param("utcrStatus")int utcrStatus,
			@Param("utcrId")String utcrId)
			throws SQLException;
	
	public List<RequirementOrder> doMsGetPersonalIndexInfo(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<RequirementOrder> msGetYestodyRequirementOrderMoney(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<RequirementOrder> msGetTodayRequirementOrderMoney(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<RequirementOrder> msGetRequirementOrderTotalMoney(
			@Param("sId")String sId)
			throws SQLException;
	
	public int msGetStoreArrangeNum(
			@Param("sId")String sId)
			throws SQLException;
	
	public Store msGetStoreLevel(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<RequirementType> msGetRequirementTypeList()
			throws SQLException;
	
	public double msGetTotalMoneyByRtIdAndSId(
			@Param("rtId")String rtId,
			@Param("sId")String sId)
			throws SQLException;
	
	public int msGetTotalNumByRtIdAndSId(
			@Param("rtId")String rtId,
			@Param("sId")String sId)
			throws SQLException;
	
	public RequirementOrder msGetFirstRoInfoBySid(
			@Param("sId")String sId)
			throws SQLException;

	public List<RequirementOrder> doMsGetRequirementOrderListWithCondition(
			@Param("sId")String sId,
			@Param("roStatus")String roStatus,
			@Param("rtId")String rtId,
			@Param("urTitle")String urTitle,
			@Param("urTrueName")String urTrueName,
			@Param("urTel")String urTel,
			@Param("start")int start,
			@Param("end")int end)
			throws SQLException;
	
	public int msGetRequirementOrderCountWithCondition(
			@Param("sId")String sId,
			@Param("roStatus")String roStatus,
			@Param("rtId")String rtId,
			@Param("urTitle")String urTitle,
			@Param("urTrueName")String urTrueName,
			@Param("urTel")String urTel)
			throws SQLException;

	public Store doMsGetStoreInfoBySid(
			@Param("sId")String sId)
			throws SQLException;
	
	public int doMsChangeStoreInfoBySid(
			@Param("sId")String sId,
			@Param("sDescribe")String sDescribe,
			@Param("sTel")String sTel,
			@Param("sAddress")String sAddress,
			@Param("sLon")String sLon,
			@Param("sLat")String sLat)
			throws SQLException;
	
	public int msDelPicsByPtag(
			@Param("pTag")String pTag)
			throws SQLException;

	public int doMsGetStatusOfSafetyQuestions(
			@Param("uId")String uId)
			throws SQLException;
	
	public List<SafetyQuestion> doMsGetSafetyQuestionsList()
			throws SQLException;
	
	public int doMsSetSafetyQuestionForUser(
			@Param("uId")String uId,
			@Param("firstSqId")String firstSqId,
			@Param("firstSqAnswer")String firstSqAnswer,
			@Param("secondSqId")String secondSqId,
			@Param("secondSqAnswer")String secondSqAnswer,
			@Param("thirdSqId")String thirdSqId,
			@Param("thirdSqAnswer")String thirdSqAnswer)
			throws SQLException;
	
	public User msGetSafetyQuestionIdOfUser(
			@Param("uId")String uId)
			throws SQLException;
	
	public SafetyQuestion msGetSafetyQuestionInfoBySqId(
			@Param("sqId")String sqId)
			throws SQLException;
	
	public User msGetSafetyQuestionAnswerOfUser(
			@Param("uId")String uId)
			throws SQLException;
	
	public User doMsRetrievePassword(
			@Param("uId")String uId)
			throws SQLException;
	
	public int doMsChangePassword(
			@Param("uId")String uId,
			@Param("newPassword")String newPassword)
			throws SQLException;
	
	public UserWallet doMsGetMyWallet(
			@Param("sId")String sId)
			throws SQLException;
	
	public double msGetApplyToFreeDepositMoney(
			@Param("sId")String sId)
			throws SQLException;
	
	public List<PayStyle> doMsGetPayStyleList()
			throws SQLException;
	
	public int doMsApplyToCash(
			@Param("uId")String uId,
			@Param("sId")String sId,
			@Param("psId")String psId,
			@Param("utcrMoney")double utcrMoney,
			@Param("utcrAccount")String utcrAccount)
			throws SQLException;
	
	public int msChangeUserWalletOfLeftMoney(
			@Param("uwLeftMoney")double uwLeftMoney,
			@Param("uwApplyToCash")double uwApplyToCash,
			@Param("sId")String sId)
			throws SQLException;
	
	public int doMsAddDepositRecord(
			@Param("uId")String uId,
			@Param("sId")String sId,
			@Param("psId")String psId,
			@Param("udrMoney")double udrMoney,
			@Param("udrOrderId")String udrOrderId)
			throws SQLException;
	
	public int msChangeUserWalletOfDeposit(
			@Param("uwDeposit")double uwDeposit,
			@Param("sId")String sId)
			throws SQLException;
	
	public int msChangeUserWalletByDeposit(
			@Param("uwLeftMoney")double uwLeftMoney,
			@Param("uwDeposit")double uwDeposit,
			@Param("sId")String sId)
					throws SQLException;
	
	public int doMsApplyToFreeForDepositRecord(
			@Param("uId")String uId,
			@Param("sId")String sId,
			@Param("udrMoney")double udrMoney)
			throws SQLException;
	
	public List<UserToCashRecord> doMsGetApplyToCashListBySId(
			@Param("sId")String sId,
			@Param("utcrStatus")String utcrStatus,
			@Param("psId")String psId,
			@Param("start")int start,
			@Param("end")int end)throws SQLException;
	
	public int msGetApplyToCashListCount(
			@Param("sId")String sId,
			@Param("utcrStatus")String utcrStatus,
			@Param("psId")String psId)throws SQLException;
	
	public List<StoreArrange> doMsGetMyArrangeListBySId(
			@Param("sId")String sId)throws SQLException;
	
	public int doMsAddMyArrange(
			@Param("saContent")String saContent,
			@Param("uId")String uId,
			@Param("sId")String sId)throws SQLException;
	
	public int doMSUpdateMyArrange(
			@Param("saId")String saId,
			@Param("saContent")String saContent)throws SQLException;
	
	public int msGetTodayProductOrderNumBySId(
			@Param("sId")String sId)throws SQLException;
	
	public List<ProductOrder> msGetProductOrderStatusListBySId(
			@Param("sId")String sId)throws SQLException;
	
	public double msGetYestodayProductOrderTotalMoney(
			@Param("sId")String sId)throws SQLException;
	
	public double msGetTodayProductOrderTotalMoney(
			@Param("sId")String sId)throws SQLException;
	
	public double msGetProductOrderTotalMoney(
			@Param("sId")String sId)throws SQLException;
	
	public List<Product> doMsGetProductListBySId(
			@Param("sId")String sId,
			@Param("pName")String pName,
			@Param("ptId")String ptId,
			@Param("start")int start,
			@Param("end")int end)throws SQLException;
	
	public int msGetSatisfactionConditionProductCount(
			@Param("sId")String sId,
			@Param("pName")String pName,
			@Param("ptId")String ptId)throws SQLException;
	
	public Store msGetBoothBySId(
			@Param("sId")String sId)throws SQLException;
	
	public List<ProductProperty> msGetProductPropertyListWhichNotSelect(
			@Param("ppTag")String ppTag,
			@Param("pId")String pId)throws SQLException;
	
	public List<ProductProperty> msGetProductPropertyListWhichSelect(
			@Param("ppTag")String ppTag,
			@Param("pId")String pId)throws SQLException;
	
	public Product msGetProductStockNum(
			@Param("pId")String pId)throws SQLException;
	
	public int msChangeProductBaseInfo(
			@Param("pId")String pId,
			@Param("pName")String pName,
			@Param("pBrand")String pBrand,
			@Param("pDescribe")String pDescribe,
			@Param("pOriginalPrice")double pOriginalPrice,
			@Param("pNowPrice")double pNowPrice,
			@Param("pWeight")int pWeight,
			@Param("pHtml")String pHtml,
			@Param("pHaveBooth")int pHaveBooth,
			@Param("pTotalNum")int pTotalNum,
			@Param("pStockNum")int pStockNum)throws SQLException;

	public int msDelProductPropertiesByPId(
			@Param("pId")String pId)throws SQLException;
	
	public int msAddProductPropertiesByPId(
			@Param("pId")String pId,
			@Param("ppId")String ppId)throws SQLException;
	
	public int msAddProductBaseInfo(
			@Param("pId")String pId,
			@Param("sId")String sId,
			@Param("pName")String pName,
			@Param("pBrand")String pBrand,
			@Param("pDescribe")String pDescribe,
			@Param("pTag")String pTag,
			@Param("pOriginalPrice")double pOriginalPrice,
			@Param("pNowPrice")double pNowPrice,
			@Param("ptdId")String ptdId,
			@Param("pWeight")int pWeight,
			@Param("pHtml")String pHtml,
			@Param("pHaveBooth")int pHaveBooth,
			@Param("pStockNum")int pStockNum,
			@Param("uId")String uId)throws SQLException;
	
	public int doMsDelProductInfoByPId(
			@Param("pId")String pId)throws SQLException;
	
	public List<ProductOrder> doMsGetProductOrderListBySId(
			@Param("sId")String sId,
			@Param("poOrderId")String poOrderId,
			@Param("poStatus")String poStatus,
			@Param("poDeliverName")String poDeliverName,
			@Param("poDeliverTel")String poDeliverTel,
			@Param("start")int start,
			@Param("end")int end)throws SQLException;
	
	public int msGetSatifitionConditionProductOrderCountBySId(
			@Param("sId")String sId,
			@Param("poOrderId")String poOrderId,
			@Param("poStatus")String poStatus,
			@Param("poDeliverName")String poDeliverName,
			@Param("poDeliverTel")String poDeliverTel)throws SQLException;
	
	public List<ProductOrderDetail> msGetProductOrderDetailByPoId(
			@Param("poId")String poId)throws SQLException;
	
	public int doMsChangeProductOrderStatus(
			@Param("poStatus")String poStatus,
			@Param("poDeliverCompany")String poDeliverCompany,
			@Param("poDeliverCode")String poDeliverCode,
			@Param("poId")String poId)throws SQLException;
	
	public int doMsChangeStoreAuditInfoBySid(
			@Param("sId")String sId,
			@Param("sDescribe")String sDescribe,
			@Param("sTel")String sTel,
			@Param("sAddress")String sAddress,
			@Param("sLon")String sLon,
			@Param("sLat")String sLat,
			@Param("sLeader")String sLeader,
			@Param("sLeaderIdCard")String sLeaderIdCard,
			@Param("sLegal")String sLegal,
			@Param("sLegalIdCard")String sLegalIdCard,
			@Param("sLeaderPic")String sLeaderPic,
			@Param("sLegalPic")String sLegalPic,
			@Param("sBuinessLicensePic")String sBuinessLicensePic)throws SQLException;

	public Store msGetStoreAuditPicsInfoBySid(
			@Param("sId")String sId)throws SQLException;
	
	public Store msGetSnameBypId(
			@Param("pId")String pId)throws SQLException;
	
	public UserToCashRecord msGetApplyToCashInfoByUtcrId(
			@Param("utcrId")String utcrId)throws SQLException;
	
	public int msUpdateCashOfUserWallet(
			@Param("uwApplyToCash")double uwApplyToCash,
			@Param("uwAlreadyToCash")double uwAlreadyToCash,
			@Param("sId")String sId)throws SQLException;
	
	public int msUpdateStoreLeftBoothNum(
			@Param("sId")String sId,
			@Param("status")int status)throws SQLException;
	
	public RecommendStore msGetStoreIsRecommendInfo(
			@Param("sId")String sId)throws SQLException;
	
	public int msDelRecommendStoreBySid(
			@Param("sId")String sId)throws SQLException;
	
	public int msAddRecommendStore(
			@Param("sId")String sId,
			@Param("rsTitle")String rsTitle,
			@Param("rsContent")String rsContent,
			@Param("pTag")String pTag,
			@Param("uId")String uId)throws SQLException;
}
