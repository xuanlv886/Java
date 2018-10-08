package com.service.extra.mall.model;

public class UserMemoName {
	private String umnId; // 主键标识
	private String umnFromId; // 命名备注名的用户主键标识
	private String umnToId; // 被命名的用户的主键标识
	private String umnName; // 备注名
	public String getUmnId() {
		return umnId;
	}
	public void setUmnId(String umnId) {
		this.umnId = umnId;
	}
	public String getUmnFromId() {
		return umnFromId;
	}
	public void setUmnFromId(String umnFromId) {
		this.umnFromId = umnFromId;
	}
	public String getUmnToId() {
		return umnToId;
	}
	public void setUmnToId(String umnToId) {
		this.umnToId = umnToId;
	}
	public String getUmnName() {
		return umnName;
	}
	public void setUmnName(String umnName) {
		this.umnName = umnName;
	}
	
	

}
