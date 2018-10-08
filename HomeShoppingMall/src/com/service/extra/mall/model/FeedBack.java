package com.service.extra.mall.model;

/**
 * 
 * desc：t_feedback表实体类
 * @author L
 * time:2018年4月23日
 */
public class FeedBack {

	private String fId; // 意见反馈主键标识
	private int fType; // 反馈问题类型 0--功能异常，1--产品建议，2--其它问题
	private String fContent; // 反馈内容
	private String uId; // 用户主键标识
	private String fCreateTime; // 反馈时间
	private int fAppType; // 客户端类型 0--用户端，1--商户端
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public int getfType() {
		return fType;
	}
	public void setfType(int fType) {
		this.fType = fType;
	}
	public String getfContent() {
		return fContent;
	}
	public void setfContent(String fContent) {
		this.fContent = fContent;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getfCreateTime() {
		return fCreateTime;
	}
	public void setfCreateTime(String fCreateTime) {
		this.fCreateTime = fCreateTime;
	}
	public int getfAppType() {
		return fAppType;
	}
	public void setfAppType(int fAppType) {
		this.fAppType = fAppType;
	}
	
}
