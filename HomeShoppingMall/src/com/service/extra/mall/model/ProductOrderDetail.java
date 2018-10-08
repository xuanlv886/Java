package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_order_detail表主键标识
 * @author L
 * time:2018年4月23日
 */
public class ProductOrderDetail {

	private String podId; // 主键标识 
	private String pId; // 商品主键标识
	private String poId; // 商品订单主键标识
	private int podNum; // 商品数量
	private String podProperty; // 商品属性
	private double podPrice; // 商品单价
	private int podEvaluate; // 商品是否已评价
	private Product product;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getPodId() {
		return podId;
	}
	public void setPodId(String podId) {
		this.podId = podId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	public int getPodNum() {
		return podNum;
	}
	public void setPodNum(int podNum) {
		this.podNum = podNum;
	}
	public String getPodProperty() {
		return podProperty;
	}
	public void setPodProperty(String podProperty) {
		this.podProperty = podProperty;
	}
	public double getPodPrice() {
		return podPrice;
	}
	public void setPodPrice(double podPrice) {
		this.podPrice = podPrice;
	}
	public int getPodEvaluate() {
		return podEvaluate;
	}
	public void setPodEvaluate(int podEvaluate) {
		this.podEvaluate = podEvaluate;
	}
	
}
