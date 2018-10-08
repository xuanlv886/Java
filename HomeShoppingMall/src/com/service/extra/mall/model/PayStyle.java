package com.service.extra.mall.model;

/**
 * 
 * desc：t_pay_style表实体类
 * @author L
 * time:2018年4月23日
 */
public class PayStyle {

	private String psId; // 主键标识
	private String psName; // 支付方式名称
	private String pTag; // 图片二级标识
	private Pic pic;
	
	public Pic getPic() {
		return pic;
	}
	public void setPic(Pic pic) {
		this.pic = pic;
	}
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	
}
