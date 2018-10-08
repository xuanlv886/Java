package com.service.extra.mall.model;

/**
 * 
 * desc：t_open_city表实体类
 * @author L
 * time:2018年4月23日
 */
public class OpenCity {

	private String ocId; // 主键标识
	private String acId; // 行政区划主键标识
	private int ocIsHot; // 是否热门城市 0--否，1--是
	private String ocCreateTime; // 开通时间
	private AreaCode areaCode;//行政区域表
	public String getOcId() {
		return ocId;
	}
	public void setOcId(String ocId) {
		this.ocId = ocId;
	}
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public int getOcIsHot() {
		return ocIsHot;
	}
	public void setOcIsHot(int ocIsHot) {
		this.ocIsHot = ocIsHot;
	}
	public String getOcCreateTime() {
		return ocCreateTime;
	}
	public void setOcCreateTime(String ocCreateTime) {
		this.ocCreateTime = ocCreateTime;
	}
	public AreaCode getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(AreaCode areaCode) {
		this.areaCode = areaCode;
	}
	
}
