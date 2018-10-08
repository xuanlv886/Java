package com.service.extra.mall.model.vo;

import java.util.List;

/**
 * desc：APP端提交的商品订单贫评价数据
 * @author L
 * time:2018年8月22日
 */

public class EvaluateDataVo {

   private List<EvaluateData> evaluateData;
   public void setEvaluateData(List<EvaluateData> evaluateData) {
        this.evaluateData = evaluateData;
    }
    public List<EvaluateData> getEvaluateData() {
        return evaluateData;
    }

    public static class EvaluateData {

    	/**
    	 * pId:商品主键标识
    	 * podId:商品订单详情主键标识
    	 * peLevel:评价等级
    	 * peContent:评价内容
    	 * picList:[{"pNo":"图片顺序","pName":"图片名"},{"pNo":"图片等级","pName":"图片名"}]
    	 */
    	
    	private String pId;
    	private String podId;
    	private String peLevel;
    	private String peContent;
    	private List<PicList> picList;
    	
    	public String getpId() {
    		return pId;
    	}

    	public void setpId(String pId) {
    		this.pId = pId;
    	}

    	public String getPodId() {
    		return podId;
    	}

    	public void setPodId(String podId) {
    		this.podId = podId;
    	}

    	public String getPeLevel() {
    		return peLevel;
    	}

    	public void setPeLevel(String peLevel) {
    		this.peLevel = peLevel;
    	}

    	public String getPeContent() {
    		return peContent;
    	}

    	public void setPeContent(String peContent) {
    		this.peContent = peContent;
    	}

    	public List<PicList> getPicList() {
    		return picList;
    	}

    	public void setPicList(List<PicList> picList) {
    		this.picList = picList;
    	}

    	public static class PicList {
    		/**
    		 * pNo:图片顺序
    		 * pName:图片名
    		 */
    		private int pNo;
    		private String pName;
    		
    		public int getpNo() {
    			return pNo;
    		}
    		
    		public void setpNo(int pNo) {
    			this.pNo = pNo;
    		}
    		
    		public String getpName() {
    			return pName;
    		}
    		
    		public void setpName(String pName) {
    			this.pName = pName;
    		}
    	}
    }
}

