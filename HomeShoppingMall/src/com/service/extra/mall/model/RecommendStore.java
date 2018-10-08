package com.service.extra.mall.model;
/**
 * 
 * desc：t_recommend_store表实体类
 * @author J
 * time:2018年4月23日
 */
public class RecommendStore {
	private String rsId;
	private String sId;
	private String rsTitle;
	private String rsContent;
	private String pTag;
	private String uId;
	private String rsCreateTime;
	private Pic pic;
	public String getRsId() {
		return rsId;
	}
	public void setRsId(String rsId) {
		this.rsId = rsId;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getRsTitle() {
		return rsTitle;
	}
	public void setRsTitle(String rsTitle) {
		this.rsTitle = rsTitle;
	}
	public String getRsContent() {
		return rsContent;
	}
	public void setRsContent(String rsContent) {
		this.rsContent = rsContent;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getRsCreateTime() {
		return rsCreateTime;
	}
	public void setRsCreateTime(String rsCreateTime) {
		this.rsCreateTime = rsCreateTime;
	}
	public Pic getPic() {
		return pic;
	}
	public void setPic(Pic pic) {
		this.pic = pic;
	}
	
	
	
}
