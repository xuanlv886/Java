package com.service.extra.mall.model;

/**
 * 
 * desc：t_store_arrange表实体类
 * @author L
 * time:2018年4月23日
 */
public class StoreArrange {

	private String saId; // 主键标识
	private String saContent; // 安排内容
	private String uId; // 用户主键标识
	private String saCreateTime; // 安排添加时间
	private String sId; // 关联的店铺的主键标识
	private Store store;
	private User user;
	
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
	public String getSaId() {
		return saId;
	}
	public void setSaId(String saId) {
		this.saId = saId;
	}
	public String getSaContent() {
		return saContent;
	}
	public void setSaContent(String saContent) {
		this.saContent = saContent;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getSaCreateTime() {
		return saCreateTime;
	}
	public void setSaCreateTime(String saCreateTime) {
		this.saCreateTime = saCreateTime;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	
}
