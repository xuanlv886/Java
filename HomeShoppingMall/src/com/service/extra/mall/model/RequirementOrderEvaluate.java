package com.service.extra.mall.model;

/**
 * 
 * desc：t_requirement_order_evaluate表实体类
 * @author L
 * time:2018年4月24日
 */
public class RequirementOrderEvaluate {

	private String roeId; // 主键标识
	private String roId; // 需求订单主键标识
	private String uId; // 发表评价的用户的主键标识
	private int roeLevel; // 评价等级 0--好评，1--中评，2--差评
	private String roeContent; // 评价内容
	private String roeCreateTime; // 发表评价的时间
	private String sId; // 评价的店铺的主键标识
	public String getRoeId() {
		return roeId;
	}
	public void setRoeId(String roeId) {
		this.roeId = roeId;
	}
	public String getRoId() {
		return roId;
	}
	public void setRoId(String roId) {
		this.roId = roId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public int getRoeLevel() {
		return roeLevel;
	}
	public void setRoeLevel(int roeLevel) {
		this.roeLevel = roeLevel;
	}
	public String getRoeContent() {
		return roeContent;
	}
	public void setRoeContent(String roeContent) {
		this.roeContent = roeContent;
	}
	public String getRoeCreateTime() {
		return roeCreateTime;
	}
	public void setRoeCreateTime(String roeCreateTime) {
		this.roeCreateTime = roeCreateTime;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	
}
