package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_relation_property表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductRelationProperty {

	private String prpId; // 商品关联属性主键标识
	private String pId; // 商品主键标识
	private String ppId; // 商品属性主键标识
	public String getPrpId() {
		return prpId;
	}
	public void setPrpId(String prpId) {
		this.prpId = prpId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	
}
