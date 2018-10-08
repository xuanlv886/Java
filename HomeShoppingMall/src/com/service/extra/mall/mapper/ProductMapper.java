package com.service.extra.mall.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
import com.service.extra.mall.model.StoreApplyRequirement;
import com.service.extra.mall.model.UserFootprint;
import com.service.extra.mall.model.UserMemoName;
import com.service.extra.mall.model.UserRequirement;
import com.service.extra.mall.model.vo.ProductVo;

/**
 * 
 * desc：平台管理系统相关接口Mapper
 * @author J
 * time:2018年5月17日
 */

public interface ProductMapper {

	public Product doGetProductByPId(@Param("pId")String pId) throws SQLException;

	public List<ProductType> doGetProductType() throws SQLException;

	public List<Product> doGetStoreNewProduct(@Param("sId")String sId) throws SQLException;

	public List<Product> doGetStoreHistoryProduct(@Param("sId")String sId, @Param("uId")String uId) throws SQLException;

	public int doUpdateUserRequirement(Map<String, Object> requirementMap) throws SQLException;
	
	public List<RequirementType> doGetAllRequirementType() throws SQLException;

	public int doAddUserRequirement(@Param("uId")String uId, @Param("rtId")String rtId, @Param("urTitle")String urTitle, @Param("urContent")String urContent, @Param("urOfferType")String urOfferType,
			@Param("urOfferPrice")String urOfferPrice, 
			@Param("urTrueName")String urTrueName, 
			@Param("urTel")String urTel, @Param("urAddress")String urAddress,
			@Param("sId")String sId,
			@Param("urGetAddress")String urGetAddress) throws SQLException;

	public List<UserRequirement> doGetUserRequirement(@Param("uId")String uId, @Param("status")String status, @Param("start")int start, @Param("end")int end) throws SQLException;

	public int doAddRequirementEvaluate(@Param("uId")String uId, @Param("roId")String roId, @Param("roeContent")String roeContent, @Param("roeLevel")String roeLevel, @Param("sId")String sId) throws SQLException;

	public UserRequirement doGetUserRequirementDetail(@Param("urId")String urId) throws SQLException;

	public RequirementOrder doGetUserRequirementOrder(@Param("urId")String urId) throws SQLException;

	public int doAddProductEvaluate(@Param("uId")String uId, @Param("pId")String pId, @Param("podId")String podId,
			@Param("peLevel")String peLevel, @Param("peContent")String peContent, @Param("pTag")String pTag) throws SQLException;

	public List<ProductEvaluate> doGetUserProductEvaluate(@Param("uId")String uId, @Param("peLevel")String peLevel) throws SQLException;

	public ProductOrder doGetProductOrderByPoId(@Param("poId")String poId) throws SQLException;

	public List<ProductOrderDetail> doGetProductOrderDetailByPoId(@Param("poId")String poId) throws SQLException;

	public int doAddProductOrder(ProductOrder productOrder) throws SQLException;

	public int doAddProductOrderDetail(ProductOrderDetail productOrderDetail) throws SQLException;

	public List<ProductProperty> doGetProductProperty(@Param("ptdId")String ptdId) throws SQLException;

	public int doDelProductOrder(@Param("uId")String uId, @Param("poId")String poId) throws SQLException;
	
	public int doDeleteProductOrder(@Param("poId")String poId) throws SQLException;

	public int doUpdateProductInfo(@Param("pId")String pId, @Param("pName")String pName, @Param("pDesc")String pDesc, @Param("pOriginalPrice")String pOriginalPrice, @Param("pNowPrice")String pNowPrice,
			@Param("pStockNum")String pStockNum, @Param("pTotalNum")int pTotalNum,@Param("pProperty")String pProperty) throws SQLException;

	public int doDelProductEvaluate(@Param("peId")String peId) throws SQLException;
	
	public int doDelStoreProduct(@Param("pId")String pId) throws SQLException;

	public List<ProductOrder> doGetProductOrder(@Param("uId")String uId, @Param("status")String status, @Param("i")int i, @Param("j")int j) throws SQLException;

	public List<Product> doGetRecommendProductList(@Param("ptdId")String ptdId, @Param("i")int i, @Param("j")int j) throws SQLException;

	public RequirementOrder doGetUserRequirementOrderByRoId(@Param("roId")String roId) throws SQLException;

	public int doDelUserRequirementOrder(@Param("roId")String roId) throws SQLException;

	public int doDelUserRequirement(@Param("urId")String urId) throws SQLException;

	public int doAddUserRequirementOrder(@Param("urId")String urId,@Param("rtId")String rtId,
			@Param("roTotalPrice")String roTotalPrice,@Param("orOrderId")String orOrderId,@Param("sId")String sId) throws SQLException;

	public List<UserRequirement> doGetAllRequirement(@Param("hotSort")String hotSort,@Param("timeSort")String timeSort,@Param("priceSort")String priceSort,@Param("start")int start,@Param("end")int end,@Param("sId")String sId) throws SQLException;

	public List<ProductOrder> doGetStoreProductOrder(@Param("sId")String sId, @Param("status")String status, @Param("i")int i, @Param("j")int j) throws SQLException;

	public List<UserRequirement> doGetStoreRequirement(@Param("sId")String sId,@Param("roStatus")String roStatus,
			@Param("i")int i,@Param("j")int j) throws SQLException;
	
	public List<StoreApplyRequirement> doGetStoreApplyRequirement(@Param("sId")String sId,@Param("i")int i,@Param("j")int j) throws SQLException;
	
	public List<StoreApplyRequirement> doGetStoreApplyRequirementByRoId(@Param("roId")String roId) throws SQLException;
	
	public List<RequirementOrder> doGetRequirementOrderByRoStatus(@Param("roStatus")String roStatus,@Param("i")int i,
			@Param("j")int j) throws SQLException;
	
	public List<Product> doGetStoreProductBySId(@Param("sId")String sId) throws SQLException;
	
	public int doUpdateProductBrowseNum(@Param("pId")String pId) throws SQLException;

	public int doUpdateRequirementBrowseNum(@Param("urId")String urId) throws SQLException;
	
	public int doUpdateUserRequirementSId(@Param("sId")String sId,
			@Param("urId")String urId,
			@Param("urOfferPrice")double urOfferPrice) throws SQLException;

	public int doUpdateRequirementOrderStatus(@Param("status")int status,
			@Param("roId")String roId,
			@Param("pTag")String pTag) throws SQLException;

	public int doUpdateProductOrderDetail(@Param("podId")String podId, @Param("podEvaluate")int podEvaluate) throws SQLException;

	public int doUpdateProductOrderStatus(@Param("status")int status, @Param("poId")String poId) throws SQLException;

	public ProductOrderDetail doGetProductOrderDetailByPodId(@Param("podId")String podId) throws SQLException;

	public int doUpdateRequirementOrder(RequirementOrder requirementOrder) throws SQLException;

	public List<Product> doGetProductList(Map<String, Object> paramMap) throws SQLException;

	public double doGetMaxUserRequirementOrder(@Param("sId")String sId) throws SQLException;

	public int doGetUserRequirementOrderNum(@Param("sId")String sId) throws SQLException;
	
	public int doGetStoreApplyRequirementNum(@Param("sId")String sId) throws SQLException;
	
	public int getStoreApplyRequirementNum(@Param("roId")String roId) throws SQLException;

	public double doGetUserRequirementOrderMoneyByDate(@Param("sId")String sId, @Param("date")String date) throws SQLException;

	public double doGetUserRequirementOrderTotalMoney(@Param("sId")String sId) throws SQLException;

	public RequirementType doGetRequirementTypeMaxPrice(@Param("sId")String sId) throws SQLException;

	public RequirementType doGetRequirementTypeMaxOrderNum(@Param("sId")String sId) throws SQLException;

	public RequirementType doGetRequirementTypeMaxTotalMoney(@Param("sId")String sId) throws SQLException;

	public int doGetTodayProductOrderNum(@Param("sId")String sId,@Param("date")String date) throws SQLException;

	public int doProductOrderNumByStatus(@Param("sId")String sId, @Param("i")int i) throws SQLException;

	public double doGetProductOrderTotalMoney(@Param("sId")String sId) throws SQLException;

	public double doGetProductOrderTotalMoneyByDate(@Param("sId")String sId, @Param("date")String date) throws SQLException;

	public List<RequirementOrderEvaluate> doGetUserRequirementEvaluate(@Param("uId")String uId, @Param("roeLevel")String roeLevel) throws SQLException;

	public List<ProductEvaluate> doGetProductEvaluate(@Param("pId")String pId,@Param("index")int index,
			@Param("peLevel")String peLevel,@Param("size")int size) throws SQLException;

	public List<ProductVo> getProduct() throws SQLException;

	public void doAddProductSearch(@Param("looseName")String looseName, @Param("type")int type) throws SQLException;

	public void doUpdateProductSearchNum(@Param("psId")String psId) throws SQLException;

	public String doGetProductSearchId(@Param("looseName")String looseName, @Param("type")int type)throws SQLException;

	public List<ProductSearch> doGetHotSearch(@Param("type")int type) throws SQLException;

	public List<Product> doGetStoreProduct(@Param("sId")String sId, @Param("start")int start, @Param("end")int end, @Param("ptdId")String ptdId, @Param("saleSort")String saleSort,
			@Param("priceSort")String priceSort) throws SQLException;

	public int doUpdateProductStockNum(@Param("pId")String pId, @Param("num")int num) throws SQLException;

	public List<ProductOrder> doGetProductOrderByPayCode(@Param("payCode")String out_trade_no) throws SQLException;

	public int doUpdateProductOrder(ProductOrder productOrder) throws SQLException;

	public int doUpdateProductOrderDetailPrice(@Param("m")Double m, @Param("poId")String poId) throws SQLException;

	public List<ProductProperty> doGetProductPropertyByPId(@Param("pId")String pId) throws SQLException;

	public List<ProductType> doGetProductTypeByRecommend() throws SQLException;

	public List<Product> getDefaultRecommendProductList(
			@Param("acId")String acId,
			@Param("record")int record,
			@Param("pageSize")int pageSize) throws SQLException;

	public List<Product> getRecommendProductListOfUser(
			@Param("uId")String uId,
			@Param("acId")String acId,
			@Param("record")int record,
			@Param("pageSize")int pageSize) throws SQLException;

	public List<ProductTypeDetail> getProductTypeDetailList(@Param("ptId") String ptId) throws SQLException;
	
	public List<ProductTypeDetail> getBottomProductTypeDetail(@Param("fatherId") String fatherId) throws SQLException;

	public RequirementType doGetRequirementType(@Param("rtId")String rtId) throws SQLException;
	

	//获取商品评价数量
	public int getEvaluesNum(@Param("pId")String pId, @Param("peLevel")String peLevel) throws SQLException;
	
	public UserRequirement doSelectUserRequirement(@Param("uId")String uId) throws SQLException;

	//获取图片信息根据图片id
	public Pic getPicByPid(@Param("picId")String picId) throws SQLException;
	
	//获取图片信息根据图片pTag
	public List<Pic> getPicByPtag(@Param("pTag")String pTag) throws SQLException;
	
	//获取图片信息根据图片picFileName,picName
	public Pic getPicByPicFileNameAndPicName(@Param("picName")String picname, @Param("picFileName")String picFileName) throws SQLException;
	
	public ProductTypeDetail getProductTypeDetail(@Param("ptdId") String ptdId) throws SQLException;
	
	//修改订单状态为待评价
	public int doGetConfirmReceipt(@Param("poId")String poId, @Param("uId")String uId) throws SQLException;
	
	public int doAddPic(@Param("pFileName")String pFileName,@Param("pName")String pName,
			@Param("pNo")int pNo,@Param("pTag")String pTag) throws SQLException;
	
	public List<UserFootprint> doGetStoreProductListFromUserFootprint(
			@Param("uId") String uId,
			@Param("sId") String sId) throws SQLException;
	
	public Product getProductIsDel(
			@Param("pId") String pId) throws SQLException;

}
