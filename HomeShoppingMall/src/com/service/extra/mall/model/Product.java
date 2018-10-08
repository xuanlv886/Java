package com.service.extra.mall.model;

/**
 * 
 * desc：t_product表实体类
 * @author L
 * time:2018年4月23日
 */
public class Product {

	private String pId; // 商品主键标识
	private String sId; // 商品所属店铺主键标识
	private String pName; // 商品名称
	private String pBrand; // 商品品牌
	private String pDescribe; // 商品描述
	private String pTag; // 商品图片
	private double pOriginalPrice; // 商品原价
	private double pNowPrice; // 商品现价
	private String pCreateTime; // 商品添加时间
	private String ptdId; // 商品所属小类主键标识
	private int pDel; // 商品是否被删除 0--否，1--是
	private int pChecked; // 商品是否通过审核 0--否，1--是
	private int pWeight; // 商品权重 1~9
	private byte[] pHtml; // 商品图文详情
	private int pBrowseNum; // 商品被浏览次数
	private int pHaveBooth; // 是否推荐商品 0--否，1--是
	private int pTotalNum; // 商品总数
	private int pStockNum; // 商品库存数量
	private String uId; // 商品审核人主键标识
	private String pCheckedTime; // 商品审核时间
	private String pCheckedOpinion; // 商品审核意见
	private Store store;
	private ProductTypeDetail productTypeDetail;
	private User user;
	private Pic pic;
	
	
	public Pic getPic() {
		return pic;
	}
	public void setPic(Pic pic) {
		this.pic = pic;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public ProductTypeDetail getProductTypeDetail() {
		return productTypeDetail;
	}
	public void setProductTypeDetail(ProductTypeDetail productTypeDetail) {
		this.productTypeDetail = productTypeDetail;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpBrand() {
		return pBrand;
	}
	public void setpBrand(String pBrand) {
		this.pBrand = pBrand;
	}
	public String getpDescribe() {
		return pDescribe;
	}
	public void setpDescribe(String pDescribe) {
		this.pDescribe = pDescribe;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public double getpOriginalPrice() {
		return pOriginalPrice;
	}
	public void setpOriginalPrice(double pOriginalPrice) {
		this.pOriginalPrice = pOriginalPrice;
	}
	public double getpNowPrice() {
		return pNowPrice;
	}
	public void setpNowPrice(double pNowPrice) {
		this.pNowPrice = pNowPrice;
	}
	
	public String getpCreateTime() {
		return pCreateTime;
	}
	public void setpCreateTime(String pCreateTime) {
		this.pCreateTime = pCreateTime;
	}
	public String getPtdId() {
		return ptdId;
	}
	public void setPtdId(String ptdId) {
		this.ptdId = ptdId;
	}
	public int getpDel() {
		return pDel;
	}
	public void setpDel(int pDel) {
		this.pDel = pDel;
	}
	public int getpChecked() {
		return pChecked;
	}
	public void setpChecked(int pChecked) {
		this.pChecked = pChecked;
	}
	public int getpWeight() {
		return pWeight;
	}
	public void setpWeight(int pWeight) {
		this.pWeight = pWeight;
	}
	public byte[] getpHtml() {
		return pHtml;
	}
	public void setpHtml(byte[] pHtml) {
		this.pHtml = pHtml;
	}
	public int getpBrowseNum() {
		return pBrowseNum;
	}
	public void setpBrowseNum(int pBrowseNum) {
		this.pBrowseNum = pBrowseNum;
	}
	public int getpHaveBooth() {
		return pHaveBooth;
	}
	public void setpHaveBooth(int pHaveBooth) {
		this.pHaveBooth = pHaveBooth;
	}
	public int getpTotalNum() {
		return pTotalNum;
	}
	public void setpTotalNum(int pTotalNum) {
		this.pTotalNum = pTotalNum;
	}
	public int getpStockNum() {
		return pStockNum;
	}
	public void setpStockNum(int pStockNum) {
		this.pStockNum = pStockNum;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getpCheckedTime() {
		return pCheckedTime;
	}
	public void setpCheckedTime(String pCheckedTime) {
		this.pCheckedTime = pCheckedTime;
	}
	public String getpCheckedOpinion() {
		return pCheckedOpinion;
	}
	public void setpCheckedOpinion(String pCheckedOpinion) {
		this.pCheckedOpinion = pCheckedOpinion;
	}
	
}
