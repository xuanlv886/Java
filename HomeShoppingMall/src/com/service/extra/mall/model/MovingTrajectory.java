package com.service.extra.mall.model;

/**
 * 
 * desc：移动轨迹表实体类
 * @author L
 * time:2018年6月12日
 */
public class MovingTrajectory {

	private String mtId; // 主键标识
	private String mtLon; // 轨迹点的经度
	private String mtLat; // 轨迹点的纬度
	private String mtCreateTime; // 轨迹点的添加时间
	private String roId; // 轨迹点对应的需求订单的主键标识
	public String getMtId() {
		return mtId;
	}
	public void setMtId(String mtId) {
		this.mtId = mtId;
	}
	public String getMtLon() {
		return mtLon;
	}
	public void setMtLon(String mtLon) {
		this.mtLon = mtLon;
	}
	public String getMtLat() {
		return mtLat;
	}
	public void setMtLat(String mtLat) {
		this.mtLat = mtLat;
	}
	public String getMtCreateTime() {
		return mtCreateTime;
	}
	public void setMtCreateTime(String mtCreateTime) {
		this.mtCreateTime = mtCreateTime;
	}
	public String getRoId() {
		return roId;
	}
	public void setRoId(String roId) {
		this.roId = roId;
	}
	
	
}
