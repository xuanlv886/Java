package com.service.extra.mall.model;

/**
 * 
 * desc：t_user_deliver_address表实体类
 * @author L
 * time:2018年4月23日
 */
public class UserDeliverAddress {

	private String udaId; // 主键标识
	private String uId; // 用户主键标识
	private String udaTrueName; // 收货人姓名
	private String udaTel; // 收货人联系电话
	private String udaAddress; // 收货人地址
	private int udaDefault; // 是否默认收货地址 0--非，1--是
	public String getUdaId() {
		return udaId;
	}
	public void setUdaId(String udaId) {
		this.udaId = udaId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getUdaTrueName() {
		return udaTrueName;
	}
	public void setUdaTrueName(String udaTrueName) {
		this.udaTrueName = udaTrueName;
	}
	public String getUdaTel() {
		return udaTel;
	}
	public void setUdaTel(String udaTel) {
		this.udaTel = udaTel;
	}
	public String getUdaAddress() {
		return udaAddress;
	}
	public void setUdaAddress(String udaAddress) {
		this.udaAddress = udaAddress;
	}
	public int getUdaDefault() {
		return udaDefault;
	}
	public void setUdaDefault(int udaDefault) {
		this.udaDefault = udaDefault;
	}
	
}
