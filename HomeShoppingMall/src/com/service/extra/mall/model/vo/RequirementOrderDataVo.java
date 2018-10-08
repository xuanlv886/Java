package com.service.extra.mall.model.vo;

/**
 * 
 * desc：APP端提交的需求订单数据
 * @author L
 * time:2018年7月20日
 */
public class RequirementOrderDataVo {

	/**
     * roId : 需求订单的主键标识
     * rtId : 需求类别的主键标识
     */

    private String roId;
    private String rtId;
    private String sId;
    
    

    public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getRtId() {
		return rtId;
	}

	public void setRtId(String rtId) {
		this.rtId = rtId;
	}

	public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId;
    }
}
