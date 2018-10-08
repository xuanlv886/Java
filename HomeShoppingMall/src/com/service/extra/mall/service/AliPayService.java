package com.service.extra.mall.service;

import java.util.Map;

public interface AliPayService {

	public Map<String, Object> doAilPayUnifiedOrder( String uId,String payCode,
			String totalMoney, String orderType, String appType,
			String orderData) throws Exception;
	
	public Map<String, Object> doAilProductUnPayUnifiedOrder(String uId, String poId,
			String payCode, String totalMoney, String orderData) throws Exception;
	
	public Map<String, Object> doAliPayOrderQuery( String uId,String payCode,String tradeNo,
			String totalMoney, String orderType, String appType,
			String orderData) throws Exception;
	
	public Map<String, Object> doAliTradeRefund(String uId, String poId,
			String payCode, String totalMoney) throws Exception;
}
