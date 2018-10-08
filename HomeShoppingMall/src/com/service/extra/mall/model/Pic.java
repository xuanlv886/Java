package com.service.extra.mall.model;

/**
 * 
 * desc：t_pic表实体类
 * @author L
 * time:2018年4月23日
 */
public class Pic {

	private String pId; // 图片主键标识
	private String pFileName; // 图片文件路径
	private String picName; // 图片名称
	private int pNo; // 图片编号
	private String pTag; // 图片二级标识
	private String pCreateTime; // 图片创建时间
	private String pJump; // 图片跳转地址
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getpFileName() {
		return pFileName;
	}
	public void setpFileName(String pFileName) {
		this.pFileName = pFileName;
	}
	
	public String getpName() {
		return picName;
	}
	public void setpName(String picName) {
		this.picName = picName;
	}
	public int getpNo() {
		return pNo;
	}
	public void setpNo(int pNo) {
		this.pNo = pNo;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public String getpCreateTime() {
		return pCreateTime;
	}
	public void setpCreateTime(String pCreateTime) {
		this.pCreateTime = pCreateTime;
	}
	public String getpJump() {
		return pJump;
	}
	public void setpJump(String pJump) {
		this.pJump = pJump;
	}
	
}
