package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_wallet表实体类
 * @author L
 * time:2018年4月24日
 */
public class UserWallet {

	private String uwId; // 主键标识
	private String sId; // 店铺主键标识
	private double uwLeftMoney; // 可用余额
	private double uwDeposit; // 冻结的保障金
	private double uwApplyToCash; // 申请提现的金额
	private double uwAlreadyToCash; // 已提现的金额
	public String getUwId() {
		return uwId;
	}
	public void setUwId(String uwId) {
		this.uwId = uwId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public double getUwLeftMoney() {
		return uwLeftMoney;
	}
	public void setUwLeftMoney(double uwLeftMoney) {
		this.uwLeftMoney = uwLeftMoney;
	}
	public double getUwDeposit() {
		return uwDeposit;
	}
	public void setUwDeposit(double uwDeposit) {
		this.uwDeposit = uwDeposit;
	}
	public double getUwApplyToCash() {
		return uwApplyToCash;
	}
	public void setUwApplyToCash(double uwApplyToCash) {
		this.uwApplyToCash = uwApplyToCash;
	}
	public double getUwAlreadyToCash() {
		return uwAlreadyToCash;
	}
	public void setUwAlreadyToCash(double uwAlreadyToCash) {
		this.uwAlreadyToCash = uwAlreadyToCash;
	}
	
	
}
