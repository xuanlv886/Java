package com.service.extra.mall.model;

/**
 * 
 * desc：t_store表实体类
 * @author L
 * time:2018年4月23日
 */
public class Store {

	private String sId; // 店铺主键标识
	private int sType; // 店铺类型 0--个人店铺，1--公司店铺
	private String sName; // 店铺名称
	private String pTag; // 店铺图片
	private String sDescribe; // 店铺描述
	private String acId; // 行政区划主键标识
	private String sLeader; // 负责人姓名
	private String sLeaderIdCard; // 店铺负责人身份证号
	private String sLegal; // 法人姓名
	private String sLegalIdCard; // 法人身份证号
	private String sLeaderPic; // 负责人手持证件照
	private String sLegalPic; // 法人手持证件照
	private String sBuinessLicensePic; // 营业执照照片
	private String sTel; // 店铺联系电话
	private String sCreateTime; // 店铺创建时间
	private String sAddress; // 店铺地址
	private String sLon; // 店铺经度
	private String sLat; // 店铺纬度
	private int sWeight; // 店铺权重
	private int sBoothNum; // 商品推荐展位总个数
	private int sLeftBoothNum; // 商品推荐展位剩余个数
	private String sRequirementServiceCharge; // 需求交易手续费
	private String sOrderLimint; // 店铺接单额度
	private String sProductServiceCharge; // 商品交易手续费
	private int sChecked; // 店铺是否通过审核 0--否，1--是
	private int sLevel; // 店铺评级
	private String uId; // 审核人主键标识
	private String sCheckedTime; // 审核时间
	private String sCheckedOpinion; // 审核意见
	private AreaCode areaCode;
	private User user;
	private Pic pic;//图片实体类
	
	public AreaCode getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(AreaCode areaCode) {
		this.areaCode = areaCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public int getsType() {
		return sType;
	}
	public void setsType(int sType) {
		this.sType = sType;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getpTag() {
		return pTag;
	}
	public void setpTag(String pTag) {
		this.pTag = pTag;
	}
	public String getsDescribe() {
		return sDescribe;
	}
	public void setsDescribe(String sDescribe) {
		this.sDescribe = sDescribe;
	}
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public String getsLeader() {
		return sLeader;
	}
	public void setsLeader(String sLeader) {
		this.sLeader = sLeader;
	}
	public String getsLeaderIdCard() {
		return sLeaderIdCard;
	}
	public void setsLeaderIdCard(String sLeaderIdCard) {
		this.sLeaderIdCard = sLeaderIdCard;
	}
	public String getsLegal() {
		return sLegal;
	}
	public void setsLegal(String sLegal) {
		this.sLegal = sLegal;
	}
	public String getsLegalIdCard() {
		return sLegalIdCard;
	}
	public void setsLegalIdCard(String sLegalIdCard) {
		this.sLegalIdCard = sLegalIdCard;
	}
	public String getsLeaderPic() {
		return sLeaderPic;
	}
	public void setsLeaderPic(String sLeaderPic) {
		this.sLeaderPic = sLeaderPic;
	}
	public String getsLegalPic() {
		return sLegalPic;
	}
	public void setsLegalPic(String sLegalPic) {
		this.sLegalPic = sLegalPic;
	}
	public String getsBuinessLicensePic() {
		return sBuinessLicensePic;
	}
	public void setsBuinessLicensePic(String sBuinessLicensePic) {
		this.sBuinessLicensePic = sBuinessLicensePic;
	}
	public String getsTel() {
		return sTel;
	}
	public void setsTel(String sTel) {
		this.sTel = sTel;
	}
	public String getsCreateTime() {
		return sCreateTime;
	}
	public void setsCreateTime(String sCreateTime) {
		this.sCreateTime = sCreateTime;
	}
	public String getsAddress() {
		return sAddress;
	}
	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}
	public String getsLon() {
		return sLon;
	}
	public void setsLon(String sLon) {
		this.sLon = sLon;
	}
	public String getsLat() {
		return sLat;
	}
	public void setsLat(String sLat) {
		this.sLat = sLat;
	}
	public int getsWeight() {
		return sWeight;
	}
	public void setsWeight(int sWeight) {
		this.sWeight = sWeight;
	}
	public int getsBoothNum() {
		return sBoothNum;
	}
	public void setsBoothNum(int sBoothNum) {
		this.sBoothNum = sBoothNum;
	}
	public int getsLeftBoothNum() {
		return sLeftBoothNum;
	}
	public void setsLeftBoothNum(int sLeftBoothNum) {
		this.sLeftBoothNum = sLeftBoothNum;
	}
	public String getsRequirementServiceCharge() {
		return sRequirementServiceCharge;
	}
	public void setsRequirementServiceCharge(String sRequirementServiceCharge) {
		this.sRequirementServiceCharge = sRequirementServiceCharge;
	}
	public String getsOrderLimint() {
		return sOrderLimint;
	}
	public void setsOrderLimint(String sOrderLimint) {
		this.sOrderLimint = sOrderLimint;
	}
	public String getsProductServiceCharge() {
		return sProductServiceCharge;
	}
	public void setsProductServiceCharge(String sProductServiceCharge) {
		this.sProductServiceCharge = sProductServiceCharge;
	}
	public int getsChecked() {
		return sChecked;
	}
	public void setsChecked(int sChecked) {
		this.sChecked = sChecked;
	}
	public int getsLevel() {
		return sLevel;
	}
	public void setsLevel(int sLevel) {
		this.sLevel = sLevel;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getsCheckedTime() {
		return sCheckedTime;
	}
	public void setsCheckedTime(String sCheckedTime) {
		this.sCheckedTime = sCheckedTime;
	}
	public String getsCheckedOpinion() {
		return sCheckedOpinion;
	}
	public void setsCheckedOpinion(String sCheckedOpinion) {
		this.sCheckedOpinion = sCheckedOpinion;
	}
	public Pic getPic() {
		return pic;
	}
	public void setPic(Pic pic) {
		this.pic = pic;
	}
	
}
