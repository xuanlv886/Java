package com.service.extra.mall.model.vo;

import com.service.extra.mall.model.Product;



public class ProductVo extends Product{
	String typeName="";
	String url="";
	String PPname="";//商品属性名称
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getPPname() {
		return PPname;
	}
	public void setPPname(String pPname) {
		if(pPname !=null){
			PPname = pPname;
		}
		
	}
	
}
