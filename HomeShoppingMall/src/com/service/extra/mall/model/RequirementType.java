package com.service.extra.mall.model;

/**
 * 
 * desc：t_requirement_type表实体类
 * @author L
 * time:2018年4月24日
 */
public class RequirementType {

	private String rtId; // 需求类别主键标识
	private String rtName; // 需求类别名称
	public String getRtId() {
		return rtId;
	}
	public void setRtId(String rtId) {
		this.rtId = rtId;
	}
	public String getRtName() {
		return rtName;
	}
	public void setRtName(String rtName) {
		this.rtName = rtName;
	}
	
}
