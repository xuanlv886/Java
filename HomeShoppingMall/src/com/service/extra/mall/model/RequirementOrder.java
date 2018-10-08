package com.service.extra.mall.model;

/**
 * 
 * desc：t_requirement_order表实体类
 * @author L
 * time:2018年4月24日
 */
public class RequirementOrder {
	private String roId; // 需求订单主键标识
	private String urId; // 用户需求主键标识
	private String rtId;//需求类型主键标识
	private String roCreateTime; // 订单创建时间
	private int roStatus; // 订单状态 7--待接单, 0--待确认，1--进行中，2--已完成,(3--取货中，4--待验货，5--送货中)，6--已评价
	private String pTag; // 货物验证图片
	private double roTotalPrice; // 订单金额
	private String roOrderId; // 订单号
	private String sId; // 执行订单的店铺主键标识
	private String roConfirmTime; // 用户确认时间
	private String roOverTime; // 订单完成时间
	private String roGetTime; // 店铺取货时间
	private String roVerificationTime; // 用户验货时间
	private UserRequirement userRequirement;
	private RequirementType requirementType;
	
	public UserRequirement getUserRequirement() {
		return userRequirement;
	}
	public void setUserRequirement(UserRequirement userRequirement) {
		this.userRequirement = userRequirement;
	}
	public RequirementType getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(RequirementType requirementType) {
		this.requirementType = requirementType;
	}
	public String getRoId() {
		return roId;
	}
	public void setRoId(String roId) {
		this.roId = roId;
	}
	public String getUrId() {
		return urId;
	}
	public void setUrId(String urId) {
		this.urId = urId;
	}
	public String getRoCreateTime() {
		return roCreateTime;
	}
	public void setRoCreateTime(String roCreateTime) {
		this.roCreateTime = roCreateTime;
	}
	public int getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(int roStatus) {
		this.roStatus = roStatus;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public double getRoTotalPrice() {
		return roTotalPrice;
	}
	public void setRoTotalPrice(double roTotalPrice) {
		this.roTotalPrice = roTotalPrice;
	}
	public String getRoOrderId() {
		return roOrderId;
	}
	public void setRoOrderId(String roOrderId) {
		this.roOrderId = roOrderId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getRoConfirmTime() {
		return roConfirmTime;
	}
	public void setRoConfirmTime(String roConfirmTime) {
		this.roConfirmTime = roConfirmTime;
	}
	public String getRoOverTime() {
		return roOverTime;
	}
	public void setRoOverTime(String roOverTime) {
		this.roOverTime = roOverTime;
	}
	public String getRoGetTime() {
		return roGetTime;
	}
	public void setRoGetTime(String roGetTime) {
		this.roGetTime = roGetTime;
	}
	public String getRtId() {
		return rtId;
	}
	public void setRtId(String rtId) {
		this.rtId = rtId;
	}
	public String getRoVerificationTime() {
		return roVerificationTime;
	}
	public void setRoVerificationTime(String roVerificationTime) {
		this.roVerificationTime = roVerificationTime;
	}
	

}
