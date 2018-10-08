package com.service.extra.mall.model;


/**
 *desc：AlipayTradePrecreateResponse实体类
 * @author L
 * time:2018年7月27日
*/
public class AlipayTradePrecreateResponse {

   private String code;
   private String msg;
   private String out_trade_no;
   private String qr_code;
   public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

   public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

   public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }

   public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
    public String getQr_code() {
        return qr_code;
    }

}