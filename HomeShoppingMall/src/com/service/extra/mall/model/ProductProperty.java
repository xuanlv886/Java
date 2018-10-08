package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_property表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductProperty {

	private String ppId; // 商品属性主键标识
	private String ppName; // 商品属性名称
	private String ppValue; // 商品属性值
	private String ppTag; // 商品属性二级标识
	private int ppChoseType; // 商品属性单选多选 0--单选，1--多选
	private int ppRequired; // 商品属性是否必填 0--非必填，1--必填
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getPpName() {
		return ppName;
	}
	public void setPpName(String ppName) {
		this.ppName = ppName;
	}
	public String getPpValue() {
		return ppValue;
	}
	public void setPpValue(String ppValue) {
		this.ppValue = ppValue;
	}
	public String getPpTag() {
		return ppTag;
	}
	public void setPpTag(String ppTag) {
		this.ppTag = ppTag;
	}
	public int getPpChoseType() {
		return ppChoseType;
	}
	public void setPpChoseType(int ppChoseType) {
		this.ppChoseType = ppChoseType;
	}
	public int getPpRequired() {
		return ppRequired;
	}
	public void setPpRequired(int ppRequired) {
		this.ppRequired = ppRequired;
	}
	
}
