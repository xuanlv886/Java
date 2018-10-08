package com.service.extra.mall.model;

/**
 * 
 * desc：客户端引导页表实体类
 * @author L
 * time:2018年6月12日
 */
public class Guide {

	private String gId; // 主键标识
	private int gAppType; // 客户端类型 0--用户端，1--商户端
	private int gEdition; // 引导页版本
	private String pTag; // 图片标识
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public int getgAppType() {
		return gAppType;
	}
	public void setgAppType(int gAppType) {
		this.gAppType = gAppType;
	}
	public int getgEdition() {
		return gEdition;
	}
	public void setgEdition(int gEdition) {
		this.gEdition = gEdition;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	
}
