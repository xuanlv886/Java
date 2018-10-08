package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_attention表实体类
 * @author L
 * time:2018年4月23日
 */
public class UserAttention {

	private String uaId; // 主键标识
	private String uId; // 用户主键标识
	private String sId; // 店铺主键标识
	private String uaCreateTime; // 添加关注时间
	private Store store;//店铺实体类
	public String getUaId() {
		return uaId;
	}
	public void setUaId(String uaId) {
		this.uaId = uaId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getUaCreateTime() {
		return uaCreateTime;
	}
	public void setUaCreateTime(String uaCreateTime) {
		this.uaCreateTime = uaCreateTime;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	
	
}
