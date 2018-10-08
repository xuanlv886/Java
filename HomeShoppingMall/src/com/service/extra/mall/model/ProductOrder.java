package com.service.extra.mall.model;

/**
 * 
 * desc：t_product_order表实体类
 * @author L
 * time:2018年4月23日
 */
public class ProductOrder {

	private String poId; // 商品订单主键标识
	private String psId; // 商品订单支付方式主键标识
	private String uId; // 付款用户主键标识
	private String poCreateTime; //  商品订单创建时间
	private double poTotalPrice; // 商品订单总金额
	private int poStatus; // 商品订单状态 0--待付款，1--待发货，2--待收货，3--待评价，4--已完成，5--待退款，6--已退款
	private String poPayTime; // 商品订单付款时间
	private String poSendTime; // 店铺发货时间
	private String poDeliverTime; // 用户收货时间
	private String poOverTime;//订单完成时间
	private String poPayCode; // 订单支付订单号
	private String poOrderId; // 订单订单号
	private int poDel; // 订单是否被用户删除 0--否，1--是
	private String poDeliverName; // 订单收货人姓名
	private String poDeliverTel; // 订单收货人电话
	private String poDeliverAddress; // 订单收货人地址
	private String sId; // 订单关联的店铺主键标识
	private String poDeliverCompany; // 发货的快递公司
	private String poDeliverCode; // 发货的快递单号
	private PayStyle payStyle;
	private User user;
	
	public String getPoDeliverCompany() {
		return poDeliverCompany;
	}
	public void setPoDeliverCompany(String poDeliverCompany) {
		this.poDeliverCompany = poDeliverCompany;
	}
	public String getPoDeliverCode() {
		return poDeliverCode;
	}
	public void setPoDeliverCode(String poDeliverCode) {
		this.poDeliverCode = poDeliverCode;
	}
	public PayStyle getPayStyle() {
		return payStyle;
	}
	public void setPayStyle(PayStyle payStyle) {
		this.payStyle = payStyle;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getPoCreateTime() {
		return poCreateTime;
	}
	public void setPoCreateTime(String poCreateTime) {
		this.poCreateTime = poCreateTime;
	}
	public double getPoTotalPrice() {
		return poTotalPrice;
	}
	public void setPoTotalPrice(double poTotalPrice) {
		this.poTotalPrice = poTotalPrice;
	}
	public int getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(int poStatus) {
		this.poStatus = poStatus;
	}
	public String getPoPayTime() {
		return poPayTime;
	}
	public void setPoPayTime(String poPayTime) {
		this.poPayTime = poPayTime;
	}
	public String getPoSendTime() {
		return poSendTime;
	}
	public void setPoSendTime(String poSendTime) {
		this.poSendTime = poSendTime;
	}
	public String getPoDeliverTime() {
		return poDeliverTime;
	}
	public void setPoDeliverTime(String poDeliverTime) {
		this.poDeliverTime = poDeliverTime;
	}
	public String getPoPayCode() {
		return poPayCode;
	}
	public void setPoPayCode(String poPayCode) {
		this.poPayCode = poPayCode;
	}
	public String getPoOrderId() {
		return poOrderId;
	}
	public void setPoOrderId(String poOrderId) {
		this.poOrderId = poOrderId;
	}
	public int getPoDel() {
		return poDel;
	}
	public void setPoDel(int poDel) {
		this.poDel = poDel;
	}
	public String getPoDeliverName() {
		return poDeliverName;
	}
	public void setPoDeliverName(String poDeliverName) {
		this.poDeliverName = poDeliverName;
	}
	public String getPoDeliverTel() {
		return poDeliverTel;
	}
	public void setPoDeliverTel(String poDeliverTel) {
		this.poDeliverTel = poDeliverTel;
	}
	public String getPoDeliverAddress() {
		return poDeliverAddress;
	}
	public void setPoDeliverAddress(String poDeliverAddress) {
		this.poDeliverAddress = poDeliverAddress;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getPoOverTime() {
		return poOverTime;
	}
	public void setPoOverTime(String poOverTime) {
		this.poOverTime = poOverTime;
	}
		
}
