package com.service.extra.mall.model;

/**
 * 
 * desc：t_store_apply_requirement表实体类
 * @author L
 * time:2018年4月23日
 */
public class StoreApplyRequirement {

	private String sarId; // 主键标识
	private String roId; // 需求订单主键标识
	private String sId; // 申请接单的店铺的主键标识
	private double sarPrice; // 申请接单的店铺的报价
	private String sarCreateTime; // 申请接单的时间
	private int sarStatus; // 申请接单的状态(0-申请中,1-用户已确认)
	
	public int getSarStatus() {
		return sarStatus;
	}
	public void setSarStatus(int sarStatus) {
		this.sarStatus = sarStatus;
	}
	public String getSarId() {
		return sarId;
	}
	public void setSarId(String sarId) {
		this.sarId = sarId;
	}
	public String getRoId() {
		return roId;
	}
	public void setRoId(String roId) {
		this.roId = roId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public double getSarPrice() {
		return sarPrice;
	}
	public void setSarPrice(double sarPrice) {
		this.sarPrice = sarPrice;
	}
	public String getSarCreateTime() {
		return sarCreateTime;
	}
	public void setSarCreateTime(String sarCreateTime) {
		this.sarCreateTime = sarCreateTime;
	}
	
}
