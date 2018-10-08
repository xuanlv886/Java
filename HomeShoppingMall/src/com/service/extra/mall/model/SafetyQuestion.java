package com.service.extra.mall.model;

/**
 * 
 * desc：t_safety_question表实体类
 * @author L
 * time:2018年4月23日
 */
public class SafetyQuestion {

	private String sqId; // 安全问题主键标识
	private String sqName; // 安全问题名称
	private int sqPosition; // 安全问题位置
	public String getSqId() {
		return sqId;
	}
	public void setSqId(String sqId) {
		this.sqId = sqId;
	}
	public String getSqName() {
		return sqName;
	}
	public void setSqName(String sqName) {
		this.sqName = sqName;
	}
	public int getSqPosition() {
		return sqPosition;
	}
	public void setSqPosition(int sqPosition) {
		this.sqPosition = sqPosition;
	}
	
}
