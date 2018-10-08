package com.service.extra.mall.alipay;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.service.extra.mall.weixinpay.util.ConfigUtil;

import util.Utils;



public class AliPayHttpClientUtils {
	
	
	public static final String  SIGN_ALGORITHMS = "SHA256WithRSA";
	private static final  String app_id = ConfigUtil.getProperty("ali.appid");
	private static final  String url = "https://openapi.alipay.com/gateway.do";
	private static final String privateKey = ConfigUtil.getProperty("ali.private_key");
	private static final String format = "json";
	private static final String charset = "utf-8";
	private static final String publicKey = ConfigUtil.getProperty("ali.public_key");
	private static final String sign_type="RSA2";
	private static final String version = "1.0";
	
	public static AlipayClient createClient(){
		AlipayClient alipayClient = new DefaultAlipayClient(url,app_id,privateKey,format,charset,publicKey,sign_type);
		return alipayClient;
	}
	
	//支付结果查询
	public static AlipayTradeQueryResponse execute(String tradeNo,String appType) throws AlipayApiException{
		//String timestamp = Util.createTime0();
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//		request.setApiVersion(version);
		//request.putOtherTextParam("method", method);
		//request.putOtherTextParam("sign_type", sign_type);
		//request.putOtherTextParam("sign", sign);
		//request.putOtherTextParam("timestamp", timestamp);
		if ("2".equals(appType)) {
			request.setBizContent("{" +
					"\"out_trade_no\":"+ "\""+tradeNo +"\""+" }");
		} else {
			request.setBizContent("{" +
					"\"trade_no\":"+ "\""+tradeNo +"\""+" }");
		}
		AlipayTradeQueryResponse response = createClient().execute(request);
		return response;
	}
	
	//支付宝转账
	public static boolean  aliTransfer (Map<String, Object> paramMap) throws AlipayApiException{
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		request.setBizContent("{" +
		"\"out_biz_no\": " +"\" "+ paramMap.get("out_biz_no")+" \" ," +
		"\"payee_type\":\"ALIPAY_LOGONID\"," +
		"\"payee_account\":"+ "\" "+paramMap.get("payee_account")+" \" ," +
		"\"amount\":" + "\""+paramMap.get("amount")+"\" ," +
		"\"payer_show_name\":\"家装商城平台\"," +
		"\"remark\":"+"\" "+paramMap.get("remark")+" \" "+
		"  }");
		AlipayFundTransToaccountTransferResponse response = createClient().execute(request);
		
		if(response.isSuccess()){
			String result = response.getBody();
			JSONObject jsonObject = JSONObject.parseObject(result);
	        String out_biz_no = jsonObject.getJSONObject("alipay_fund_trans_toaccount_transfer_response").getString("out_biz_no");
	        String order_id = jsonObject.getJSONObject("alipay_fund_trans_toaccount_transfer_response").getString("order_id");
			boolean result1 =  aliPayTranQuery(out_biz_no,order_id);
	        
			return result1;
		}
		
		return false;
	}
	//转账结果查询
	public static boolean aliPayTranQuery(String out_biz_no,String order_id ) throws AlipayApiException{
		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
		request.setBizContent("{" +
		"\"out_biz_no\":"+ "\""+ out_biz_no +"\"," +
		"\"order_id\":"+"\""+ order_id+"\" "+
		"  }");
		AlipayFundTransOrderQueryResponse response = createClient().execute(request);
		if(response.isSuccess()){
			return true;
		} 
		
		return false;
	}
	
	//统一收单交易预创建接口(WEB端)
	public static AlipayTradePrecreateResponse aliPayTranPrecreate(Map<String, Object> paramMap) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(url,app_id,privateKey,format,charset,publicKey,sign_type);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent("{" +
				"\"out_trade_no\":\""
				+ paramMap.get("out_trade_no")
				+ "\"," +
				"\"total_amount\":"
				+ paramMap.get("total_amount")
				+ "," +
				"\"subject\":\""
				+ paramMap.get("subject")
				+ "\"" +
				"  }");
		AlipayTradePrecreateResponse response = alipayClient.execute(request);
		return response;
	}
	
	
	//统一收单交易创建接口
	public static AlipayTradeAppPayResponse aliPayTranCreate(Map<String, Object> paramMap) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(url,app_id,privateKey,format,charset,publicKey,sign_type);
		AlipayTradeAppPayRequest  request = new AlipayTradeAppPayRequest ();
		request.setBizContent("{" +
		"\"out_trade_no\":\""+ paramMap.get("out_trade_no")+" \"," +
		"\"total_amount\":"+paramMap.get("total_amount")+"," +
		"\"subject\":\""+paramMap.get("subject")+"\"" +
		"  }");
		AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		//response.setOutTradeNo(outTradeNo);
		//response.setTotalAmount(totalAmount);
		//response.setTradeNo(tradeNo);
		return response;
	}
	
	//统一收单交易退款接口
	public static AlipayTradeRefundResponse aliTradeRefund(Map<String, Object> paramMap) throws AlipayApiException{
		AlipayClient alipayClient = new DefaultAlipayClient(url,app_id,privateKey,format,charset,publicKey,sign_type);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" +
		"\"out_trade_no\":\""+ paramMap.get("out_trade_no")+" \"," +
//		"\"trade_no\":\""+ paramMap.get("trade_no")+" \"," +
		"\"refund_amount\":"+paramMap.get("refund_amount")+"," +
//		"\"refund_amount\":0.01" +

//		"\"refund_currency\":\"USD\"," +
//		"\"refund_reason\":\"正常退款\"," +
		"\"out_request_no\":\""+ paramMap.get("out_request_no")+"\"" +
//		"\"operator_id\":\"OP001\"," +
//		"\"store_id\":\"NJ_S_001\"," +
//		"\"terminal_id\":\"NJ_T_001\"," +
//		"      \"goods_detail\":[{" +
//		"        \"goods_id\":\"apple-01\"," +
//		"\"alipay_goods_id\":\"20010001\"," +
//		"\"goods_name\":\"ipad\"," +
//		"\"quantity\":1," +
//		"\"price\":2000," +
//		"\"goods_category\":\"34543238\"," +
//		"\"body\":\"特价手机\"," +
//		"\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
//		"        }]," +
//		"      \"refund_royalty_parameters\":[{" +
//		"        \"royalty_type\":\"transfer\"," +
//		"\"trans_out\":\"2088101126765726\"," +
//		"\"trans_out_type\":\"userId\"," +
//		"\"trans_in_type\":\"userId\"," +
//		"\"trans_in\":\"2088101126708402\"," +
//		"\"amount\":0.1," +
//		"\"amount_percentage\":100," +
//		"\"desc\":\"分账给2088101126708402\"" +
//		"        }]" +
		"  }");
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		
		return response;
	}

	/**
	 * 支付宝签名
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String signAllString(String [] array){
		   StringBuffer sb = new StringBuffer("");
		   for (int i = 0; i < array.length; i++) {
		      if(i==(array.length-1)){
		         sb.append(array[i]);
		      }else{
		         sb.append(array[i]+"&");
		      }
		   }
//		   System.out.println(sb.toString());
		   String sign = "";
		   try {
		      sign = URLEncoder.encode(RSA.sign(sb.toString(), privateKey, charset), charset);//private_key私钥
		   } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		   }
		   sb.append("&sign=\""+sign+"\"&");
		   sb.append("sign_type=\"RSA\"");
		   return sb.toString();
		}
}
