package com.service.extra.mall.model;

/**
 * 
 * desc：用户提现记录标识
 * @author L
 * time:2018年4月23日
 */
public class UserToCashRecord {

	private String utcrId; // 用户提现记录表主键标识
	private String sId; // 店铺主键标识
	private String uId; // 用户主键标识
	private int utcrStatus; // 提现状态 0--申请提现，1--已转账
	private double utcrMoney; // 提现金额
	private String psId; // 提现方式
	private String utcrAccount; // 提现账户
	private String utcrCreateTime; // 操作时间
	private Store store;
	private User user;
	private PayStyle payStyle;
	
	
	public PayStyle getPayStyle() {
		return payStyle;
	}
	public void setPayStyle(PayStyle payStyle) {
		this.payStyle = payStyle;
	}
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	
	public String getUtcrAccount() {
		return utcrAccount;
	}
	public void setUtcrAccount(String utcrAccount) {
		this.utcrAccount = utcrAccount;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUtcrId() {
		return utcrId;
	}
	public void setUtcrId(String utcrId) {
		this.utcrId = utcrId;
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
	public int getUtcrStatus() {
		return utcrStatus;
	}
	public void setUtcrStatus(int utcrStatus) {
		this.utcrStatus = utcrStatus;
	}
	public double getUtcrMoney() {
		return utcrMoney;
	}
	public void setUtcrMoney(double utcrMoney) {
		this.utcrMoney = utcrMoney;
	}
	public String getUtcrCreateTime() {
		return utcrCreateTime;
	}
	public void setUtcrCreateTime(String utcrCreateTime) {
		this.utcrCreateTime = utcrCreateTime;
	}
	
}
