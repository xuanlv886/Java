package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_deposit_record表实体类
 * @author L
 * time:2018年4月24日
 */
public class UserDepositRecord {

	private String udrId; // 主键标识
	private String sId; // 店铺主键标识
	private String uId; // 用户主键标识
	private int udrStatus; // 保障金状态 0--缴纳，1--解冻保障金
	private double udrMoney; // 保障金金额
	private String psId; // 保障金缴纳方式
	private String udrCreateTime; // 操作时间
	private String udrOrderId; // 缴纳保障金订单号 当保障金状态为0（缴纳）时，该字段有值
	
	
	public String getUdrOrderId() {
		return udrOrderId;
	}
	public void setUdrOrderId(String udrOrderId) {
		this.udrOrderId = udrOrderId;
	}
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getUdrId() {
		return udrId;
	}
	public void setUdrId(String udrId) {
		this.udrId = udrId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public int getUdrStatus() {
		return udrStatus;
	}
	public void setUdrStatus(int udrStatus) {
		this.udrStatus = udrStatus;
	}
	public double getUdrMoney() {
		return udrMoney;
	}
	public void setUdrMoney(double udrMoney) {
		this.udrMoney = udrMoney;
	}
	public String getUdrCreateTime() {
		return udrCreateTime;
	}
	public void setUdrCreateTime(String udrCreateTime) {
		this.udrCreateTime = udrCreateTime;
	}
	
}
