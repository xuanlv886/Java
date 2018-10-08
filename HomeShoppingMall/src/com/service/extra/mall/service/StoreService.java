package com.service.extra.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * desc：平台管理系统相关接口服务类
 * @author J
 * time:2018年5月17日
 */
public interface StoreService {

	public Map<String, Object> doStoreLogin(String uAccount, String uPassword,
			String uPhoneId, String acId) throws Exception;

	public Map<String, Object> doGetStoreInfo(String sId,String uId) throws Exception;

	public Map<String, Object> doGetStoreSlideShow(String sId) throws Exception;

	public Map<String, Object> doUpdateProductInfo(String pId, String pName, String pDesc, String pOriginalPrice, String pNowPrice,
			String pStockNum, String pProperty) throws Exception;

	public Map<String, Object> doDeleteStoreProduct(String pId) throws Exception;
	
	public Map<String, Object> doAddStoreFeedBack(String fType, String fContent, String uId, String fAppType) throws Exception;

	public Map<String, Object> doGetAlreadyCash(String sId, String psId, String uId,String utcrAccount,String utcrMoney) throws Exception;

	public Map<String, Object> doGetMyUserWallet(String sId) throws Exception;

//	public Map<String, Object> doGetPayDepositMoney(String sId, String uId, String udrMoney,String psId) throws Exception;

	public Map<String, Object> doGetThawDepositMoney(String sId,String depositMoney,String uId) throws Exception;

	public Map<String, Object> doStorePersonRegistered(String sTel,
			String sName,String acId, String sAddress, String sLon,
			String sLat,String sLeader, String uSex, String sLeaderIdCard,
			String sLeaderPic,String sPassword) throws Exception;

	public Map<String, Object> doStoreCompanyRegistered(String sTel,
			String sName,String acId, String sAddress,
			String sLon, String sLat, String sLeader, String uSex,
			String sLeaderIdCard,String sLeaderPic, 
			String sLegal, String sLegalIdCard, String sLegalPic,
			String sBussinessLicensePic, String sPassword) throws Exception;

	public Map<String, Object> doAddStoreArrange(String sId, String saContent, String uId) throws Exception;

	public Map<String, Object> doGetStoreApplicationRequirement(String sId, String quotedPrice, String urId) throws Exception;

	public Map<String, Object> doDelStoreArrange(String saId) throws Exception;

	public Map<String, Object> doGetMyArranges(String sId, String uId) throws Exception;
	
	public Map<String, Object> doUpdateMyArrange(String saId, String saContent) throws Exception;

	public Map<String, Object> doUpdateStoreInfo(String uId, String sId, String sName, String sAnnouncement, String sTel) throws Exception;

	public List<Map<String, Object>> doGetAllRequirement(String uId, String sId, String hotSort,String timeSort,String priceSort,String currentPage,String size) throws Exception;

	public Map<String, Object> doCancelStoreRequirement(String sId, String roId) throws Exception;

	public Map<String, Object> doGetAllAlreadyCashRecord(String sId, String uId, String size, String pages) throws Exception;

	public Map<String, Object> doGetStoreProductOrderDetail(String poId) throws Exception;

	public Map<String, Object> doGetStoreProductOrder(String sId,String currentPage,String size,String status) throws Exception;

	public Map<String, Object> doGetStoreRequirement(String sId,String roStatus,String currentPage,String size) throws Exception;

	public Map<String, Object> doGetStoreRequirementDetail(String urId) throws Exception;

	public Map<String, Object> doPersonStoreMainInterface(String uId,String sId) throws Exception;

	public Map<String, Object>  doCompanyStoreMainInterface(String uId,String sId) throws Exception;

	public Map<String, Object>  doSubmitMerchantLonlat(String uId,String sId,
			String mtLon, String mtLat) throws Exception;

	public Map<String, Object>  doAffirmProductRefund(String poId) throws Exception;
	
	public Map<String, Object>  doInformInspection(String roId, String picData) throws Exception;
	
	public Map<String, Object> merchantSend(String poId,String poDeliverCompany,
			String poDeliverCode) throws Exception;
	
	public Map<String, Object> doDelStoreProductOrder(String poId) throws Exception;
	
}
