package com.service.extra.mall.model;

/**
 * 
 * desc：t_version表实体类
 * @author L
 * time:2018年4月23日
 */
public class Version {

	private String vId; // 应用版本主键标识
	private int vType; // 应用类型 0--用户端，1--商户端，2--后台端
	private String vCode; // 应用版本号
	private String vCreateTime; // 应用版本发布时间
	private int vIfMust; // 是否必须更新 0--否，1--是
	private String vFileSize; // 文件大小
	private String vContent; // 更新说明
	private String BuildCode;
	private int vSystemType; // 系统类型 0--安卓端，1--苹果端，2--后台端
	private String vUrl; // 下载地址
	public String getvId() {
		return vId;
	}
	public void setvId(String vId) {
		this.vId = vId;
	}
	public int getvType() {
		return vType;
	}
	public void setvType(int vType) {
		this.vType = vType;
	}
	public String getvCode() {
		return vCode;
	}
	public void setvCode(String vCode) {
		this.vCode = vCode;
	}
	public String getvCreateTime() {
		return vCreateTime;
	}
	public void setvCreateTime(String vCreateTime) {
		this.vCreateTime = vCreateTime;
	}
	public int getvIfMust() {
		return vIfMust;
	}
	public void setvIfMust(int vIfMust) {
		this.vIfMust = vIfMust;
	}
	public String getvFileSize() {
		return vFileSize;
	}
	public void setvFileSize(String vFileSize) {
		this.vFileSize = vFileSize;
	}
	public String getvContent() {
		return vContent;
	}
	public void setvContent(String vContent) {
		this.vContent = vContent;
	}
	public String getBuildCode() {
		return BuildCode;
	}
	public void setBuildCode(String buildCode) {
		BuildCode = buildCode;
	}
	public int getvSystemType() {
		return vSystemType;
	}
	public void setvSystemType(int vSystemType) {
		this.vSystemType = vSystemType;
	}
	public String getvUrl() {
		return vUrl;
	}
	public void setvUrl(String vUrl) {
		this.vUrl = vUrl;
	}
	
}
