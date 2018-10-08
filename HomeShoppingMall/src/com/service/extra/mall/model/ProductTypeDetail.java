package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_type_detail表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductTypeDetail {

	private String ptdId; // 商品小类主键标识
	private String ptId; // 商品大类主键标识
	private String ptdName; // 商品小类名称
	private String ptdFatherId; // 商品小类父类主键标识
	private String pTag; // 商品小类图片标识
	private ProductType productType;
	private Pic pic;
	private String picName;
	
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public Pic getPic() {
		return pic;
	}
	public void setPic(Pic pic) {
		this.pic = pic;
	}
	public String getPtdId() {
		return ptdId;
	}
	public void setPtdId(String ptdId) {
		this.ptdId = ptdId;
	}
	public String getPtId() {
		return ptId;
	}
	public void setPtId(String ptId) {
		this.ptId = ptId;
	}
	public String getPtdName() {
		return ptdName;
	}
	public void setPtdName(String ptdName) {
		this.ptdName = ptdName;
	}
	public String getPtdFatherId() {
		return ptdFatherId;
	}
	public void setPtdFatherId(String ptdFatherId) {
		this.ptdFatherId = ptdFatherId;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	
}
