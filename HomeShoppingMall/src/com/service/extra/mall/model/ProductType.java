package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_type表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductType {

	private String ptId; // 商品大类主键标识
	private String ptName; // 商品大类名称
	public String getPtId() {
		return ptId;
	}
	public void setPtId(String ptId) {
		this.ptId = ptId;
	}
	public String getPtName() {
		return ptName;
	}
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	
}
