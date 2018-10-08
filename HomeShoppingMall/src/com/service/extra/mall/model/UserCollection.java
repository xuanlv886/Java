package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_collection表实体类
 * @author L
 * time:2018年4月23日
 */
public class UserCollection {

	private String ucId; // 主键标识
	private String uId; // 用户主键标识
	private String pId; // 商品主键标识
	private String ucCreateTime; // 添加时间
	private Product product;//商品实体类
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getUcId() {
		return ucId;
	}
	public void setUcId(String ucId) {
		this.ucId = ucId;
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
	public String getUcCreateTime() {
		return ucCreateTime;
	}
	public void setUcCreateTime(String ucCreateTime) {
		this.ucCreateTime = ucCreateTime;
	}
	
}
