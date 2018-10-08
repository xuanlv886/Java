package com.service.extra.mall.model;

/**
 * 
 * desc：t_sys_operate_log表实体类
 * @author L
 * time:2018年4月24日
 */
public class SysOperateLog {

	private String solId; // 系统操作日志主键标识
	private String uId; // 操作人主键标识
	private String solCreateTime; // 操作时间
	private int solType; // 0--审核店铺，1--审核商品，2--发布商品，3--添加用户，4--删除用户，5--申请提现，6--处理提现申请
	private String solObject; // 操作对象
	private String solTable; // 被操作对象所在的表
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSolId() {
		return solId;
	}
	public void setSolId(String solId) {
		this.solId = solId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getSolCreateTime() {
		return solCreateTime;
	}
	public void setSolCreateTime(String solCreateTime) {
		this.solCreateTime = solCreateTime;
	}
	public int getSolType() {
		return solType;
	}
	public void setSolType(int solType) {
		this.solType = solType;
	}
	public String getSolObject() {
		return solObject;
	}
	public void setSolObject(String solObject) {
		this.solObject = solObject;
	}
	public String getSolTable() {
		return solTable;
	}
	public void setSolTable(String solTable) {
		this.solTable = solTable;
	}
	
}
