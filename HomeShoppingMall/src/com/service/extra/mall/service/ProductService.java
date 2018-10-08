package com.service.extra.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * desc：平台管理系统相关接口服务类
 * @author J
 * time:2018年5月17日
 */
public interface ProductService {

	public Map<String, Object> doGetProductInfo(String pId,String uId) throws Exception;

	public Map<String, Object> doGetProductType() throws Exception;

	public Map<String, Object> doGetProductList(String uId, String currentPage, String size, String ptdId, String priceSort,
			String saleSort, String parameterData) throws Exception;

	public Map<String, Object> doUpdateUserRequirement(String urId, String uId, String rtId, String urTitle, String urContent,
			String urOfferType, String urOfferPrice, String urTrueName, String urTel, String urAddress) throws Exception;

	public Map<String, Object> doAddUserRequirement(String uId, String rtId, String urTitle, String urContent, String urOfferType,
			String urOfferPrice, String urTrueName, String urTel, String urAddress,
			String sId, String urGetAddress) throws Exception;

	public Map<String, Object> doGetUserRequirement(String uId, String status , String currentPage, String size) throws Exception;

	public Map<String, Object> doAddRequirementEvaluate(String uId, String roId, String roeContent, String roeLevel, String sId) throws Exception;

	public Map<String, Object> doGetUserRequirementDetail(String uId, String urId) throws Exception;

	public Map<String, Object> doAddProductEvaluate(String uId, String poId, String evaluateData) throws Exception;

	public Map<String, Object> doGetUserProductEvaluate(String uId, String peLevel) throws Exception;

	public Map<String, Object> doGetProductOrderDetail(String uId, String poId) throws Exception;

	public Map<String, Object> doAddProductOrder(String uId, String udaId, String pNum, String pId, String pProperty) throws Exception;

	public Map<String, Object> doGetProductProperty(String ptdId) throws Exception;

	public List<Map<String, Object>> doGetProductTypeDetailByPtId(String ptId) throws Exception;

	public Map<String, Object> doDelProductOrder(String uId, String poId) throws Exception;

	public Map<String, Object> doDelProductEvaluate(String peId) throws Exception;

	public Map<String, Object> doGetProductOrder(String uId, String status, String currentPage, String size) throws Exception;

	public Map<String, Object> doGetRecommendProductList(String uId, String currentPage, String size, String ptdId) throws Exception;

	public Map<String, Object> doAddProductOrderByTrolley(String uId, String udaId, String parameterData) throws Exception;

	public Map<String, Object> doDelUserRequirement(String uId, String urId) throws Exception;

	public Map<String, Object> doAddUserProductRequirement(String uId, String roId, String sId) throws Exception;

	public Map<String, Object> doGetUserRequirementEvaluate(String uId, String roeLevel) throws Exception;

	public Map<String, Object> doGetProductEvaluate(String pId, String peLevel,String size,String currentPage ) throws Exception;

	public Map<String, Object> doSelectBylooseName(String looseName, String ptdId, String priceSort, String saleSort,
			String currentPage, String size) throws Exception;

	public Map<String, Object> doGetHotSearch(String type) throws Exception;

	public Map<String, Object> doGetStoreDetail(String sId, String currentPage, String size, String ptdId, String saleSort,
			String priceSort,String uId) throws Exception;

	public Map<String, Object> doSelectStoreBylooseName(String looseName, String currentPage, String size) throws Exception;

	public Map<String, Object> doSelectRequirementType() throws Exception;
	
	public Map<String, Object> doGetProductRequestRefund(String poId, String uId) throws Exception;

	public Map<String, Object> doUpdateRequirementOrderStatus(String roId) throws Exception;
	
	public Map<String, Object> doDeleteStoreApplyRequirement(String roId,String sId) throws Exception;
	
	public Map<String, Object> doGetOrderPayCode() throws Exception;
	
	public Map<String, Object> doGetConfirmReceipt(String poId, String uId) throws Exception;
	
	public Map<String, Object> doCancelProductRequestRefund(String poId, String uId) throws Exception;
	
	public Map<String, Object> doRorderVerification(String roId) throws Exception;
	
	public Map<String, Object> doStayInspectionProduct(String uId, String roId) throws Exception;
	
	public List<Map<String, Object>> doGetStoreProductListFromUserFootprint(String sId, String uId) throws Exception;
}
