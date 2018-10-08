package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_footprint表实体类
 * @author L
 * time:2018年4月23日
 */
public class UserFootprint {

	private String ufId; // 主键标识
	private String pId; // 商品主键标识
	private String uId; // 用户主键标识
	private String ufCreateTime; // 足迹添加时间
	private Product product;//商品实体类
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getUfId() {
		return ufId;
	}
	public void setUfId(String ufId) {
		this.ufId = ufId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getUfCreateTime() {
		return ufCreateTime;
	}
	public void setUfCreateTime(String ufCreateTime) {
		this.ufCreateTime = ufCreateTime;
	}
	
}
