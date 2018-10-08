package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_requirement表实体类
 * @author L
 * time:2018年4月24日
 */
public class UserRequirement {

	private String urId; // 用户需求主键标识
	private String uId; // 用户主键标识
	private String rtId; // 需求类别主键标识
	private String urTitle; // 需求标题
	private String urContent; // 需求描述
	private int urOfferType; // 需求报价类型 0--用户自己报价，1--商家报价
	private double urOfferPrice;//需求报价金额
	private String urTrueName; // 需求联系人
	private String urTel; // 需求联系人电话
	private String urAddress; // 需求联系人地址
	private String urCreateTime; // 需求发布时间
	private String sId; // 指定接单店铺的主键标识
	private int urBrowserNum; // 需求被浏览次数
	private String urGetAddress; // 取货地址
	private RequirementType requirementType;
	private RequirementOrder requirementOrder;
	
	
	public RequirementType getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(RequirementType requirementType) {
		this.requirementType = requirementType;
	}
	public RequirementOrder getRequirementOrder() {
		return requirementOrder;
	}
	public void setRequirementOrder(RequirementOrder requirementOrder) {
		this.requirementOrder = requirementOrder;
	}
	public String getUrId() {
		return urId;
	}
	public void setUrId(String urId) {
		this.urId = urId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getRtId() {
		return rtId;
	}
	public void setRtId(String rtId) {
		this.rtId = rtId;
	}
	public String getUrTitle() {
		return urTitle;
	}
	public void setUrTitle(String urTitle) {
		this.urTitle = urTitle;
	}
	public String getUrContent() {
		return urContent;
	}
	public void setUrContent(String urContent) {
		this.urContent = urContent;
	}
	public int getUrOfferType() {
		return urOfferType;
	}
	public void setUrOfferType(int urOfferType) {
		this.urOfferType = urOfferType;
	}
	public String getUrTrueName() {
		return urTrueName;
	}
	public void setUrTrueName(String urTrueName) {
		this.urTrueName = urTrueName;
	}
	public String getUrTel() {
		return urTel;
	}
	public void setUrTel(String urTel) {
		this.urTel = urTel;
	}
	public String getUrAddress() {
		return urAddress;
	}
	public void setUrAddress(String urAddress) {
		this.urAddress = urAddress;
	}
	public String getUrCreateTime() {
		return urCreateTime;
	}
	public void setUrCreateTime(String urCreateTime) {
		this.urCreateTime = urCreateTime;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public int getUrBrowserNum() {
		return urBrowserNum;
	}
	public void setUrBrowserNum(int urBrowserNum) {
		this.urBrowserNum = urBrowserNum;
	}
	public String getUrGetAddress() {
		return urGetAddress;
	}
	public void setUrGetAddress(String urGetAddress) {
		this.urGetAddress = urGetAddress;
	}
	public double getUrOfferPrice() {
		return urOfferPrice;
	}
	public void setUrOfferPrice(double urOfferPrice) {
		this.urOfferPrice = urOfferPrice;
	}
	
	
}
