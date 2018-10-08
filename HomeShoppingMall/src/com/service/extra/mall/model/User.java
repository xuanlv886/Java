package com.service.extra.mall.model;

/**
 * 
 * desc：t_user表实体类
 * @author L
 * time:2018年4月23日
 */
public class User {

	private String uId; // 用户主键标识
	private String urId; // 用户角色主键标识
	private String uAccount; // 用户账号
	private String uPassword; // 用户密码
	private String uNickName; // 用户昵称
	private int uSex; // 用户性别 0--女,1--男
	private String uEmail; // 用户邮箱
	private String uBirthday; // 用户出生日期
	private String pTag; // 用户头像
	private String uTel; // 用户电话
	private String uPayCode; // 用户支付密码
	private String uTrueName; // 用户真实姓名
	private String uPhoneId; // 用户设备标识
	private String uFirstSqId; // 第一个安全问题主键标识
	private String uFirstSqAnswer; // 第一个安全问题答案
	private String uSecondSqId; // 第二个安全问题主键标识
	private String uSecondSqAnswer; // 第二个安全问题答案
	private String uThirdSqId; // 第三个安全问题主键标识
	private String uThirdSqAnswer; // 第三个安全问题答案
	private String sId; // 店铺主键标识
	private String uCreateTime; // 用户创建时间
	private String uAccid; // 网易云accid
	private String uToken; // 网易云token
	private UserRole userRole;
	private Store store;
	
	
	public String getuAccid() {
		return uAccid;
	}
	public void setuAccid(String uAccid) {
		this.uAccid = uAccid;
	}
	public String getuToken() {
		return uToken;
	}
	public void setuToken(String uToken) {
		this.uToken = uToken;
	}
	public String getuCreateTime() {
		return uCreateTime;
	}
	public void setuCreateTime(String uCreateTime) {
		this.uCreateTime = uCreateTime;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getUrId() {
		return urId;
	}
	public void setUrId(String urId) {
		this.urId = urId;
	}
	public String getuAccount() {
		return uAccount;
	}
	public void setuAccount(String uAccount) {
		this.uAccount = uAccount;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public String getuNickName() {
		return uNickName;
	}
	public void setuNickName(String uNickName) {
		this.uNickName = uNickName;
	}
	public int getuSex() {
		return uSex;
	}
	public void setuSex(int uSex) {
		this.uSex = uSex;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getuBirthday() {
		return uBirthday;
	}
	public void setuBirthday(String uBirthday) {
		this.uBirthday = uBirthday;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public String getuTel() {
		return uTel;
	}
	public void setuTel(String uTel) {
		this.uTel = uTel;
	}
	public String getuPayCode() {
		return uPayCode;
	}
	public void setuPayCode(String uPayCode) {
		this.uPayCode = uPayCode;
	}
	public String getuTrueName() {
		return uTrueName;
	}
	public void setuTrueName(String uTrueName) {
		this.uTrueName = uTrueName;
	}
	public String getuPhoneId() {
		return uPhoneId;
	}
	public void setuPhoneId(String uPhoneId) {
		this.uPhoneId = uPhoneId;
	}
	public String getuFirstSqId() {
		return uFirstSqId;
	}
	public void setuFirstSqId(String uFirstSqId) {
		this.uFirstSqId = uFirstSqId;
	}
	public String getuFirstSqAnswer() {
		return uFirstSqAnswer;
	}
	public void setuFirstSqAnswer(String uFirstSqAnswer) {
		this.uFirstSqAnswer = uFirstSqAnswer;
	}
	public String getuSecondSqId() {
		return uSecondSqId;
	}
	public void setuSecondSqId(String uSecondSqId) {
		this.uSecondSqId = uSecondSqId;
	}
	public String getuSecondSqAnswer() {
		return uSecondSqAnswer;
	}
	public void setuSecondSqAnswer(String uSecondSqAnswer) {
		this.uSecondSqAnswer = uSecondSqAnswer;
	}
	public String getuThirdSqId() {
		return uThirdSqId;
	}
	public void setuThirdSqId(String uThirdSqId) {
		this.uThirdSqId = uThirdSqId;
	}
	public String getuThirdSqAnswer() {
		return uThirdSqAnswer;
	}
	public void setuThirdSqAnswer(String uThirdSqAnswer) {
		this.uThirdSqAnswer = uThirdSqAnswer;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	
	
}
