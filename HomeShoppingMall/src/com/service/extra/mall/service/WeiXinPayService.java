package com.service.extra.mall.service;

import java.util.Map;

public interface WeiXinPayService {

	
	public Map<String, Object> doWxPayUnifiedOrder( String uId,String payCode,
			String totalMoney, String orderType, String appType,
			String orderData) throws Exception;
	
	public Map<String, Object> doWxProductUnPayUnifiedOrder(String uId, String poId,
			String payCode, String totalMoney, String orderData) throws Exception;
	
	public Map<String, Object> doWxPayOrderQuery( String uId,String payCode,
			String totalMoney, String orderType, String appType,
			String orderData) throws Exception;
	
	public Map<String, Object> doWXTradeRefund(String uId, String poId,
			String payCode, String totalMoney) throws Exception;
}
