package com.service.extra.mall.model;

import java.util.List;


/**
 * 
 * desc：商品订单评价标识
 * @author L
 * time:2018年4月23日
 */
public class ProductEvaluate {

	private String peId; // 商品订单评价主键标识
	private String pId; // 商品主键标识
	private String podId; // 商品订单详情主键标识
	private String uId; // 发表评价的用户主键标识
	private int peLevel; // 评价等级 0--好评，1--中评，2--差评
	private String peContent; // 商品评价内容
	private String peCreateTime; // 添加评价的时间
	private String pTag;//评价图片
	private List<Pic> pic;//图片实体类
	private String picId;
	private Product product;//商品实体类
	private User user;//用户实体类
	
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPeId() {
		return peId;
	}
	public void setPeId(String peId) {
		this.peId = peId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getPodId() {
		return podId;
	}
	public void setPodId(String podId) {
		this.podId = podId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public int getPeLevel() {
		return peLevel;
	}
	public void setPeLevel(int peLevel) {
		this.peLevel = peLevel;
	}
	public String getPeContent() {
		return peContent;
	}
	public void setPeContent(String peContent) {
		this.peContent = peContent;
	}
	public String getPeCreateTime() {
		return peCreateTime;
	}
	public void setPeCreateTime(String peCreateTime) {
		this.peCreateTime = peCreateTime;
	}
	
}
