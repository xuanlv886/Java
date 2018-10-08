package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_search表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductSearch {

	private String psId; // 逐渐标识
	private String psName; // 搜索的关键字
	private int psNum; // 搜索次数
	private int psType; // 搜索类型 0--商品，1--店铺
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public int getPsNum() {
		return psNum;
	}
	public void setPsNum(int psNum) {
		this.psNum = psNum;
	}
	public int getPsType() {
		return psType;
	}
	public void setPsType(int psType) {
		this.psType = psType;
	}
	
}
