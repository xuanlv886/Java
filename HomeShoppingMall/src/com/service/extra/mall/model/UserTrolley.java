package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_trolley表实体类
 * @author L
 * time:2018年4月24日
 */
public class UserTrolley {

	private String utId; // 购物车主键标识
	private String uId; // 用户主键标识
	private String pId; // 商品主键标识
	private String sId; // 店铺主键标识
	private int utProductNum; // 商品数量
	private String utProductProperty; // 商品属性
	private String utCreateTime; // 添加购物车时间
	private Product product;//商品实体类
	private Store store;//店铺实体类
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public int getUtProductNum() {
		return utProductNum;
	}
	public void setUtProductNum(int utProductNum) {
		this.utProductNum = utProductNum;
	}
	public String getUtProductProperty() {
		return utProductProperty;
	}
	public void setUtProductProperty(String utProductProperty) {
		this.utProductProperty = utProductProperty;
	}
	public String getUtCreateTime() {
		return utCreateTime;
	}
	public void setUtCreateTime(String utCreateTime) {
		this.utCreateTime = utCreateTime;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
}
