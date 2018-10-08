package com.service.extra.mall.model.vo;

import java.util.Comparator;

public class RequirementTypeOfMoney implements Comparable<Object>{

	private String rtId; // 需求类别标识
	private String rtName; // 需求类别名称
	private double totalMoney; // 该类别下总成交额
	private int totalNum; // 该类别下总订单数
	
	

	public RequirementTypeOfMoney() {  
        this("", "", 0.0, 0);  
    }  
  
    public RequirementTypeOfMoney(String rtId, String rtName,
    		double totalMoney, int totalNum) {  
        this.rtId = rtId;  
        this.rtName = rtName;  
        this.totalMoney = totalMoney;  
        this.totalNum = totalNum;
    }  
	
	
	public String getRtId() {
		return rtId;
	}



	public void setRtId(String rtId) {
		this.rtId = rtId;
	}



	public String getRtName() {
		return rtName;
	}



	public void setRtName(String rtName) {
		this.rtName = rtName;
	}



	public double getTotalMoney() {
		return totalMoney;
	}



	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}


	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		RequirementTypeOfMoney r = (RequirementTypeOfMoney) obj; 
        return (int) (r.totalMoney - this.totalMoney); 
	} 

}
