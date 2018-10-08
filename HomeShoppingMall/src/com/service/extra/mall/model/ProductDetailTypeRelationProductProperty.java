package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_detail_type_relation_product_property表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductDetailTypeRelationProductProperty {
	private String pdtrppId; // 主键标识
	private String ppTag; // 商品属性二级标识
	private String ptdId; // 商品小类主键标识
	public String getPdtrppId() {
		return pdtrppId;
	}
	public void setPdtrppId(String pdtrppId) {
		this.pdtrppId = pdtrppId;
	}
	public String getPpTag() {
		return ppTag;
	}
	public void setPpTag(String ppTag) {
		this.ppTag = ppTag;
	}
	public String getPtdId() {
		return ptdId;
	}
	public void setPtdId(String ptdId) {
		this.ptdId = ptdId;
	}
	

}
