package com.service.extra.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.extra.mall.service.AliPayService;

import util.Utils;




@Controller
public class AliPayController {
	Logger logger = Logger.getLogger(AliPayController.class);//获取日志记录器，这个记录器将负责控制日志信息
	@Resource private AliPayService aliPayService;
	/**
	 * 
	 * desc：支付宝支付统一下单接口
	 *	param:uId--用户主键标识
	 * 			payCode--支付订单号
	 * 			totalMoney--支付的总金额
	 * 			orderType--订单类型0--商品订单，1--需求订单，2--缴纳保障金
	 * 			appType--平台类型0--用户端，1--商户端，2--WEB管理端
	 * 			orderData--订单详情数据
	 * 			①商品订单详情数据格式：
	 * 				{
	 *					"poDeliverName": "收货人姓名",
	 *					"poDeliverTel": "收货人联系电话",
	 *					"poDeliverAddress": "收货人地址",
	 *					"stores": [{
	 *						"sId": "商品所属的店铺的主键标识",
	 *						"products": [{
	 *								"pId": "商品主键标识",
	 *								"podNum": "购买的数量",
	 *								"podProperty": "要购买的商品的属性",
	 *								"podPrice": "购买的商品的单价"
	 *							},
	 *							{
	 *								"pId": "商品主键标识",
	 *								"podNum": "购买的数量",
	 *								"podProperty": "要购买的商品的属性",
	 *								"podPrice": "购买的商品的单价"
	 *							}
     *							]
	 *					},
	 *					{
	 *						"sId": "商品所属的店铺的主键标识",
	 *						"products": [{
	 *								"pId": "商品主键标识",
	 *								"podNum": "购买的数量",
	 *								"podProperty": "要购买的商品的属性",
	 *								"podPrice": "购买的商品的单价"
	 *							},
	 *							{
	 *								"pId": "商品主键标识",
	 *								"podNum": "购买的数量",
	 *								"podProperty": "要购买的商品的属性",
	 *								"podPrice": "购买的商品的单价"
	 *							}
	 *						]
	 *					}
	 *				]
	 *			}
	 *			②需求订单详情格式
	 *				{}
	 *			③缴纳保障金详情格式
	 *				{}
	 * return:Map<String,Object>
	 * time:2018年7月24日
	 * author:L
	 */
	@RequestMapping("/ailPayUnifiedOrder")
	@ResponseBody
	public Map<String,Object> doAilPayUnifiedOrder (String uId,String payCode,
			String totalMoney, String orderType, String appType,
			String orderData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(payCode)
				||Utils.isEmpty(totalMoney)||Utils.isEmpty(orderType)
				||Utils.isEmpty(appType)) {
					return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString="";
			Object data =aliPayService.doAilPayUnifiedOrder(uId, payCode,
					totalMoney, orderType, appType, orderData);
			map=Utils.packageResponseDate(flag, errorString, data);
			logger.info(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc： 待付款商品订单统一下单接口
	 * param: uId--用户主键标识
	 * 			poId--商品订单主键标识
	 * 			payCode--支付订单号（重新调用“获取订单号接口”获得）
	 * 			totalMoney--订单总金额
	 * 			orderData--订单详情数据
	 * 			商品订单详情数据格式：
	 * 			{
	 *					"poDeliverName": "收货人姓名",
	 *					"poDeliverTel": "收货人联系电话",
	 *					"poDeliverAddress": "收货人地址",
	 *					"stores": []
	 *			}
	 * return: Map<String,Object>
	 * time:2 018年7月24日
	 * author: L
	 */
	@RequestMapping(value ="/ailProductUnPayUnifiedOrder",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAilProductUnPayUnifiedOrder (String uId, String poId,
			String payCode, String totalMoney, String orderData){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(poId)
				||Utils.isEmpty(payCode)||Utils.isEmpty(totalMoney)
				||Utils.isEmpty(orderData)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString="";
			Object data =aliPayService.doAilProductUnPayUnifiedOrder(uId, poId,
					payCode, totalMoney, orderData);
			map=Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
		
	}
	
	/**
	 * 
	 * desc：查询支付宝订单支付结果接口
	 * param: uId--用户主键标识
	 * 			payCode--支付订单号
	 * 			tradeNo--支付宝交易号
	 * 			totalMoney--支付的总金额
	 * 			orderType--订单类型0--商品订单，1--需求订单，2--缴纳保障金
	 * 			appType--平台类型0--用户端，1--商户端，2--WEB管理端
	 * 			orderData--订单详情数据
	 * 			①商品订单详情数据格式：
	 * 			{}
	 * 			②需求订单详情格式
	 *				{
	 *					"roId": "需求订单的主键标识",
	 *					"rtId": "需求类别的主键标识",
	 *					"sId": "执行订单的店铺主键标识"
	 *				}
	 *			③缴纳保障金详情格式
	 *				{
	 *					"sId": "缴纳保障金的店铺的主键标识"
	 *				}
	 * return:Map<String,Object>
	 * time:2018年7月20日
	 * author:L
	 */
	@RequestMapping(value ="/aliPayOrderQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAliPayOrderQuery (String uId,String payCode, String tradeNo,
			String totalMoney, String orderType, String appType,
			String orderData) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(payCode)||Utils.isEmpty(tradeNo)
				||Utils.isEmpty(totalMoney)||Utils.isEmpty(orderType)
				||Utils.isEmpty(appType)||Utils.isEmpty(orderData)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString = "";
			Object data =aliPayService.doAliPayOrderQuery(uId, payCode,tradeNo,
					totalMoney, orderType, appType, orderData);
			map = Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
		
	}
	
	/**
	 * desc: 支付宝商品交易退款接口
	 * param: uId--用户主键标识
	 * 			poId--商品订单主键标识
	 * 			payCode--支付订单号
	 * 			totalMoney--订单总金额
	 * return: Map<String ,Object>
	 * time: 2018年7月26日
	 * author: L
	 */
	@RequestMapping(value ="/ailTradeRefund",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doAliTradeRefund(String uId, String poId,
			String payCode, String totalMoney ){
		Map<String,Object> map = new HashMap<String,Object>();
		// 先对进行非空判断
		if (Utils.isEmpty(uId)||Utils.isEmpty(poId)
				||Utils.isEmpty(payCode)||Utils.isEmpty(totalMoney)) {
			return Utils.commonErrorMap();
		}
		try {
			boolean flag = true;
			String errorString="";
			Object data =aliPayService.doAliTradeRefund(uId, poId,
					payCode, totalMoney);
			map=Utils.packageResponseDate(flag, errorString, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return Utils.commonErrorMap();
		}
		return map;
	}
}
