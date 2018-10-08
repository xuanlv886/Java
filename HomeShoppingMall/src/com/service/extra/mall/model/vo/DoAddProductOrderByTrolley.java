package com.service.extra.mall.model.vo;

import java.util.List;

/**
 * desc：解析商品从购物车下订单
 * @author J
 * time:2018年5月26日
 */
public class DoAddProductOrderByTrolley {
	 private List<ParameterData> parameterData;

	    public List<ParameterData> getParameterData() {
	        return parameterData;
	    }

	    public void setParameterData(List<ParameterData> parameterData) {
	        this.parameterData = parameterData;
	    }

	    	public class ParameterData {
	    	    private int utProductNum;
	    	    private String utId;
	    	    private String pId;
	    	    private String utProductProperty;
				public int getUtProductNum() {
					return utProductNum;
				}
				public void setUtProductNum(int utProductNum) {
					this.utProductNum = utProductNum;
				}
				public String getUtId() {
					return utId;
				}
				public void setUtId(String utId) {
					this.utId = utId;
				}
				public String getpId() {
					return pId;
				}
				public void setpId(String pId) {
					this.pId = pId;
				}
				public String getUtProductProperty() {
					return utProductProperty;
				}
				public void setUtProductProperty(String utProductProperty) {
					this.utProductProperty = utProductProperty;
				}
	    	   

	    	}
	
	
	
	
}
