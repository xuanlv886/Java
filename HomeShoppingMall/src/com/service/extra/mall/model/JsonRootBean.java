package com.service.extra.mall.model;

/**
 *desc：JsonRootBean实体类
 * @author L
 * time:2018年7月27日
*/
public class JsonRootBean {

   private AlipayTradePrecreateResponse alipay_trade_precreate_response;
   private String sign;
   public void setAlipayTradePrecreateResponse(AlipayTradePrecreateResponse alipay_trade_precreate_response) {
        this.alipay_trade_precreate_response = alipay_trade_precreate_response;
    }
    public AlipayTradePrecreateResponse getAlipayTradePrecreateResponse() {
        return alipay_trade_precreate_response;
    }

   public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

}
