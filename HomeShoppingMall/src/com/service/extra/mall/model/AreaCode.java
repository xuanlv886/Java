package com.service.extra.mall.model;

/**
 * 
 * desc：t_areacode表实体类
 * @author L
 * time:2018年4月23日
 */
public class AreaCode {

	private String acId; // 行政区划主键标识
	private String acCode; // 行政区划代码
	private String acProvince; // 省份/直辖市
	private String acCity; // 城市
	private String acName; // 区县
	private String acParent; // 父级行政区划主键标识
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public String getAcCode() {
		return acCode;
	}
	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}
	public String getAcProvince() {
		return acProvince;
	}
	public void setAcProvince(String acProvince) {
		this.acProvince = acProvince;
	}
	public String getAcCity() {
		return acCity;
	}
	public void setAcCity(String acCity) {
		this.acCity = acCity;
	}
	public String getAcName() {
		return acName;
	}
	public void setAcName(String acName) {
		this.acName = acName;
	}
	public String getAcParent() {
		return acParent;
	}
	public void setAcParent(String acParent) {
		this.acParent = acParent;
	}
	
}
