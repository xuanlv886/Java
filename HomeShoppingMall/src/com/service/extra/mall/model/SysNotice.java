package com.service.extra.mall.model;

/**
 * 
 * desc：t_sys_notice表实体类
 * @author L
 * time:2018年4月24日
 */
public class SysNotice {

	private String snId; // 系统公告主键标识
	private String snTitle; // 系统公告标题
	private String snContent; // 系统公告内容
	private String uId; // 系统公告发布人主键标识
	private String snCreateTime; // 系统公告发布时间
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSnId() {
		return snId;
	}
	public void setSnId(String snId) {
		this.snId = snId;
	}
	public String getSnTitle() {
		return snTitle;
	}
	public void setSnTitle(String snTitle) {
		this.snTitle = snTitle;
	}
	public String getSnContent() {
		return snContent;
	}
	public void setSnContent(String snContent) {
		this.snContent = snContent;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getSnCreateTime() {
		return snCreateTime;
	}
	public void setSnCreateTime(String snCreateTime) {
		this.snCreateTime = snCreateTime;
	}
	
}
