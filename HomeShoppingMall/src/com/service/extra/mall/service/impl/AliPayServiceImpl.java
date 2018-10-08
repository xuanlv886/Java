package com.service.extra.mall.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.service.extra.mall.alipay.AliPayHttpClientUtils;
import com.service.extra.mall.mapper.ManageSystemMapper;
import com.service.extra.mall.mapper.ProductMapper;
import com.service.extra.mall.mapper.StoreMapper;
import com.service.extra.mall.mapper.UserMapper;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.RequirementOrder;
import com.service.extra.mall.model.StoreApplyRequirement;
import com.service.extra.mall.model.UserTrolley;
import com.service.extra.mall.model.UserWallet;
import com.service.extra.mall.model.vo.ProductOrderDataVo;
import com.service.extra.mall.model.vo.RequirementOrderDataVo;
import com.service.extra.mall.model.vo.UserDepositDataVo;
import com.service.extra.mall.service.AliPayService;

import net.sf.json.JSONObject;
//import net.sf.json.JSONObject;
import util.Utils;

@Service
public class AliPayServiceImpl implements AliPayService {
	@Resource ProductMapper productMapper;
	@Resource UserMapper userMapper;
	@Resource StoreMapper storeMapper;
	@Resource ManageSystemMapper manageSystemMapper;
	
	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, propagation=Propagation.REQUIRED)
	public Map<String, Object> doAilPayUnifiedOrder(String uId,String payCode,
			String totalMoney, String orderType, String appType,
			String orderData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		if ("0".equals(orderType)) { // 商品订单
			// 1、向商品订单主表中插入订单信息
			// 2、向商品订单详情表中插入订单信息
			if (!Utils.isEmpty(orderData)) {
				ProductOrderDataVo pVo = new ProductOrderDataVo();
				pVo = Utils.parserJsonResult(orderData, 
						ProductOrderDataVo.class);
				/**
				 * 向t_product_order表中插入数据
				 * 情况1：单一店铺内一种或多种商品（即订单主表内生成1条记录，
				 * 						订单详情表内根据商品种类生成记录）
				 * 情况2：多个店铺内多种商品 （即订单主表内根据店铺数量生成对应条数的记录，
				 * 						订单详情表内根据商品种类生成记录）
				 */
				ProductOrder productOrder = null;
				/**
				 * 判断订单主表内生成的记录的条数
				 */
				if (null != pVo.getStores() && 0 < pVo.getStores().size()) {
					if (1 == pVo.getStores().size()) { // 生成1条订单
						productOrder = new ProductOrder();
						String poId = Utils.randomUUID(); // 订单主键标识
						productOrder.setPoId(poId); // 订单主键标识
						productOrder.setPsId(Utils.ALI_PAY_ID); // 支付宝支付主键标识
						productOrder.setuId(uId); // 付款用户主键标识
						productOrder.setPoTotalPrice(Double.valueOf(totalMoney)
								.doubleValue()); // 订单总金额
						productOrder.setPoStatus(0); // 订单状态 0--待付款
						productOrder.setPoPayTime(""); // 订单支付时间
						productOrder.setPoSendTime(""); // 订单发货时间
						productOrder.setPoDeliverTime(""); // 用户收货时间
						productOrder.setPoOverTime(""); // 订单完成时间
						productOrder.setPoPayCode(payCode); // 支付订单号
						productOrder.setPoOrderId(payCode); // 展示的订单号
						productOrder.setPoDel(0); // 订单是否被用户删除 0--否
						productOrder.setPoDeliverName(
								pVo.getPoDeliverName()); // 订单收货人姓名
						productOrder.setPoDeliverTel(
								pVo.getPoDeliverTel()); // 订单收货人联系电话
						productOrder.setPoDeliverAddress(
								pVo.getPoDeliverAddress()); // 收货人地址
						productOrder.setsId(pVo.getStores().get(0)
								.getSId()); // 订单关联的店铺主键标识
						productOrder.setPoDeliverCompany(""); // 发货的快递公司
						productOrder.setPoDeliverCode(""); // 发货的快递单号
						/**
						 * 插入订单表
						 */
						int result = productMapper.doAddProductOrder(
								productOrder);
						if (1 == result) { // 插入成功
							// 向订单详情表中插入数据
							ProductOrderDetail productOrderDetail = null;
							for (int i = 0; i < pVo.getStores().get(0)
									.getProducts().size(); i++) {
								productOrderDetail = new ProductOrderDetail();
								productOrderDetail.setpId(pVo.getStores()
										.get(0).getProducts().get(i)
										.getPId()); // 商品主键标识
								productOrderDetail.setPoId(
										poId); // 商品订单表主键标识
								productOrderDetail.setPodNum(Integer.valueOf(
										pVo.getStores().get(0).getProducts()
										.get(i).getPodNum())
										.intValue()); // 商品数量
								productOrderDetail.setPodProperty(pVo
										.getStores().get(0).getProducts()
										.get(i).getPodProperty()); // 商品属性
								productOrderDetail.setPodPrice(Double.valueOf(
										pVo.getStores().get(0).getProducts()
										.get(i).getPodPrice())
										.doubleValue()); // 商品单价
								// 是否已评价 0--否
								productOrderDetail.setPodEvaluate(0); 
								int row = productMapper
										.doAddProductOrderDetail(
										productOrderDetail);
								if (1 != row) { // 插入失败
									map.put("status", "false");
									map.put("errorString", "下单失败！");
									throw new RuntimeException();
								}
								String sId = pVo.getStores().get(0).getSId();
								String pId = pVo.getStores().get(0).getProducts().get(i).getPId();
								String podProperty = pVo.getStores().get(0).getProducts().get(i).getPodProperty();
								/**
								 * 获取购物车信息
								 */
								List<UserTrolley> userTrolleys = userMapper.doGetUserTrolleyByUid(uId);
								for (UserTrolley userTrolley : userTrolleys) {
									String sId1 = userTrolley.getsId();
									String pId1 = userTrolley.getpId();
									String podProperty1 = userTrolley.getUtProductProperty();
									if (sId1.equals(sId) && pId1.equals(pId) && podProperty1.equals(podProperty)){
										/**
										 * 删除购物车信息
										 */
										int row1 = userMapper.doDeleteUserTrolley(uId, sId, pId, podProperty);
										if (1 != row1) {
											map.put("errorString", "删除购物车信息失败");
											map.put("status", "false");
											throw new RuntimeException();
										}
									}
								}
							}
						} else {
							map.put("status", "false");
							map.put("errorString", "下单失败！");
							throw new RuntimeException();
						}
					} else { // 生成多条订单
						for (int i = 0; i < pVo.getStores().size(); i++) {
							productOrder = new ProductOrder();
							String poId = Utils.randomUUID(); // 订单主键标识
							productOrder.setPoId(poId); // 订单主键标识
							productOrder.setPsId(Utils.ALI_PAY_ID); // 支付宝支付主键标识
							productOrder.setuId(uId); // 付款用户主键标识
							// 计算单一店铺内订单总金额
							double sum = 0;
							for (int j = 0; j < pVo.getStores().get(i)
										.getProducts().size(); j++) {
								// 商品单价
								double price = Double.valueOf(pVo.getStores()
										.get(i).getProducts().get(j)
										.getPodPrice()).doubleValue();
								// 商品数量
								int num = Integer.valueOf(pVo.getStores()
										.get(i).getProducts().get(j)
										.getPodNum()).intValue();
								sum += price* num;
							}
							productOrder.setPoTotalPrice(sum); // 订单总金额
							productOrder.setPoStatus(0); // 订单状态 0--待付款
							productOrder.setPoPayTime(""); // 订单支付时间
							productOrder.setPoSendTime(""); // 订单发货时间
							productOrder.setPoDeliverTime(""); // 用户收货时间
							productOrder.setPoOverTime(""); // 订单完成时间
							productOrder.setPoPayCode(payCode); // 支付订单号
							String poOrderId = Utils
									.formatNowTimeLimitMillisecond()
									+ Utils.getFourRandom();
							productOrder.setPoOrderId(poOrderId); // 展示的订单号
							productOrder.setPoDel(0); // 订单是否被用户删除 0--否
							productOrder.setPoDeliverName(
									pVo.getPoDeliverName()); // 订单收货人姓名
							productOrder.setPoDeliverTel(
									pVo.getPoDeliverTel()); // 订单收货人联系电话
							productOrder.setPoDeliverAddress(
									pVo.getPoDeliverAddress()); // 收货人地址
							productOrder.setsId(pVo.getStores().get(i)
									.getSId()); // 订单关联的店铺主键标识
							productOrder.setPoDeliverCompany(""); // 发货的快递公司
							productOrder.setPoDeliverCode(""); // 发货的快递单号
							/**
							 * 插入订单表
							 */
							int result = productMapper.doAddProductOrder(
									productOrder);
							if (1 == result) { // 插入成功
								// 向订单详情表中插入数据
								ProductOrderDetail productOrderDetail = null;
								for (int k = 0; k < pVo.getStores().get(i)
										.getProducts().size(); k++) {
									productOrderDetail = new ProductOrderDetail();
									productOrderDetail.setpId(pVo.getStores()
											.get(i).getProducts().get(k)
											.getPId()); // 商品主键标识
									productOrderDetail.setPoId(
											poId); // 商品订单表主键标识
									productOrderDetail.setPodNum(Integer.valueOf(
											pVo.getStores().get(i).getProducts()
											.get(k).getPodNum())
											.intValue()); // 商品数量
									productOrderDetail.setPodProperty(pVo
											.getStores().get(i).getProducts()
											.get(k).getPodProperty()); // 商品属性
									productOrderDetail.setPodPrice(Double.valueOf(
											pVo.getStores().get(i).getProducts()
											.get(k).getPodPrice())
											.doubleValue()); // 商品单价
									// 是否已评价 0--否
									productOrderDetail.setPodEvaluate(0); 
									int row = productMapper
											.doAddProductOrderDetail(
											productOrderDetail);
									if (1 != row) { // 插入失败
										map.put("status", "false");
										map.put("errorString", "下单失败！");
										throw new RuntimeException();
									}
									String sId = pVo.getStores().get(i).getSId();
									String pId = pVo.getStores().get(i).getProducts().get(k).getPId();
									String podProperty = pVo.getStores().get(i).getProducts().get(k).getPodProperty();
									/**
									 * 删除购物车信息
									 */
									int row1 = userMapper.doDeleteUserTrolley(uId, sId, pId, podProperty);
									if (1 != row1) {
										map.put("errorString", "删除购物车信息失败");
										map.put("status", "false");
										throw new RuntimeException();
									}
								}
							} else {
								map.put("status", "false");
								map.put("errorString", "下单失败！");
								throw new RuntimeException();
							}
						}
					}
				} else {
					map.put("status", "false");
					map.put("errorString", "订单信息为空！");
					return map;
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "订单信息为空！");
				return map;
			}
		} else if ("1".equals(orderType)) { // 需求订单
			// doNothing
		} else if ("2".equals(orderType)) { // 缴纳保障金
			// doNothing
		}
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> parm = new HashMap<>();
		parm.put("out_trade_no", payCode);//支付订单号
		parm.put("total_amount", df.format(Double.valueOf(totalMoney)
				.doubleValue()));//总金额
		System.out.println(df.format(Double.valueOf(totalMoney)
				.doubleValue()));
//		parm.put("total_amount", Utils.testMoney*0.01 + "");//总金额
		if ("0".equals(orderType)) { // 商品订单
			parm.put("subject", Utils.PRODUCT_DES); // 描述
		} else if ("1".equals(orderType)) { // 需求订单
			parm.put("subject", Utils.REQUIREMENT_DES); // 描述
		} else if ("2".equals(orderType)) { // 缴纳保障金
			parm.put("subject", Utils.DEPOSIT_DES); // 描述
		}
		if ("2".equals(appType)) { // WEB端
			/**
			 * 统一收单交易预创建接口(WEB端)
			 */
			AlipayTradePrecreateResponse response = AliPayHttpClientUtils
					.aliPayTranPrecreate(parm);
			String qr_code = response.getQrCode();
			//判断response是否成功
			if (response.isSuccess()) {
				// 统一下单成功 执行业务逻辑
				map.put("status", "true");
				map.put("errorString", "");
				map.put("qr_code",qr_code); // 二维码字符串
			} else {
				// 统一下单失败
				map.put("status", "false");
				String msg = response.getMsg();
				try {
					map.put("errorString", msg);
				} catch (Exception e) {
					map.put("errorString", "下单失败！");
				}
				TransactionAspectSupport.currentTransactionStatus()
				.setRollbackOnly();
			}
		} else { // 用户端或商户端
			/**
			 * 调用支付宝统一下单API并获取数据
			 */
			AlipayTradeAppPayResponse response = AliPayHttpClientUtils
					.aliPayTranCreate(parm);
			if (response.isSuccess()) {
				// 统一下单成功 执行业务逻辑
				map.put("status", "true");
				map.put("errorString", "");
				map.put("orderInfo",response.getBody()); // 订单信息
			} else {
				// 统一下单失败
				map.put("status", "false");
				String msg = response.getMsg();
				try {
					map.put("errorString", msg);
				} catch (Exception e) {
					map.put("errorString", "下单失败！");
				}
				TransactionAspectSupport.currentTransactionStatus()
				.setRollbackOnly();
			}
		}
		return map;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doAilProductUnPayUnifiedOrder(String uId, 
			String poId, String payCode, String totalMoney, String orderData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 针对支付宝支付OUT_TRADE_NO_USED（商户订单号重复	同一笔交易不能多次提交）错误		
		 * 使用新的支付订单号payCode，并更新至数据库中
		 */
		ProductOrderDataVo pVo = new ProductOrderDataVo();
		pVo = Utils.parserJsonResult(orderData, ProductOrderDataVo.class);
		/**
		 * 更新订单信息
		 */
		int result = userMapper.updateProductOrderInfoByPoId(Utils.ALI_PAY_ID,
				Double.valueOf(totalMoney).doubleValue(), payCode,
				pVo.getPoDeliverName(), pVo.getPoDeliverTel(),
				pVo.getPoDeliverAddress(), poId);
		if (1 == result) { // 更新订单信息成功
			
//				String [] parms = {
//					"out_trade_no=\""+payCode+"\"",//商户内部订单号
//					"subject=\""+Utils.PRODUCT_DES+"\"",
//					"total_amount=\""+Utils.testMoney*0.01+"\"",
//					"trade_type=\""+"APP"+"\""
//				};
//				 String signOrderUrl = AliPayHttpClientUtils.signAllString(parms);
			DecimalFormat df = new DecimalFormat("0.00");
			Map<String, Object> parm = new HashMap<>();
			parm.put("out_trade_no", payCode);//支付订单号
			parm.put("total_amount", df.format(Double.valueOf(totalMoney)
					.doubleValue()));//总金额
			System.out.println(df.format(Double.valueOf(totalMoney)
					.doubleValue()));
//			parm.put("total_amount", Utils.testMoney*0.01 + "");//总金额
			parm.put("subject", Utils.PRODUCT_DES); // 描述
			/**
			* 调用支付宝统一下单API并获取数据
			*/
			AlipayTradeAppPayResponse response = AliPayHttpClientUtils
						.aliPayTranCreate(parm);
			if (response.isSuccess()) {
				// 统一下单成功 执行业务逻辑
				map.put("status", "true");
				map.put("errorString", "");
				map.put("orderInfo",response.getBody()); // 订单信息
			} else {
				// 统一下单失败
				map.put("status", "false");
				String msg = response.getMsg();
				try {
					map.put("errorString", msg);
				} catch (Exception e) {
					map.put("errorString", "下单失败！");
				}
				TransactionAspectSupport.currentTransactionStatus()
				.setRollbackOnly();
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "更新订单失败！");
			
		}	
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doAliPayOrderQuery(String uId, String payCode, String tradeNo, String totalMoney, String orderType,
			String appType, String orderData) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		// 先操作APP业务数据库，再执行支付宝支付相关操作
		if ("0".equals(orderType)) { // 商品订单
			// 修改商品订单状态
			/**
			 * 获取商品订单信息
			 * t_product_order:PO_ID
			 */
			List<ProductOrder> productOrder = userMapper.getProductOrderInfo(uId,
					payCode);
			for (int i = 0; i < productOrder.size(); i++) {
				/**
				 * 修改商品订单状态
				 */
				int result = userMapper.updateProductOrderStatus(
						1,  // 0--待付款  1--待发货
						productOrder.get(i).getPoId());
				if (1 == result) { // 修改成功
					// 修改商品的库存
					// 1、查询订单详情 获取购买的商品的数量
					// 2、修改商品表中商品的库存数量
					/**
					 * 根据poId获取订单详情信息列表
					 */
					List<ProductOrderDetail> details = userMapper
							.getProductOrderDetailListByPoId(productOrder.get(i)
									.getPoId());
					for (int j = 0; j < details.size(); j++) {
						String pId = details.get(j).getpId();
						int num = details.get(j).getPodNum();
						/**
						 * 修改商品库存数量
						 */
						int row = productMapper.doUpdateProductStockNum(pId,num);
						if(row!=1){
							map.put("status", "false");
							map.put("errorString", "查询失败!");
							throw new RuntimeException();
						}
					}
				} else {
					map.put("status", "false");
					map.put("errorString", "查询失败!");
					throw new RuntimeException();
				}
			}
		} else if ("1".equals(orderType)) { // 需求订单
			// 修改需求订单状态
			RequirementOrderDataVo rVo = new RequirementOrderDataVo();
			rVo = Utils.parserJsonResult(orderData,
					RequirementOrderDataVo.class);
			int roStatus = 0;
			if (Utils.REQUIREMENT_TYPE_SEND.equals(
					rVo.getRtId())) { // 需求类型为取货送货
				roStatus = 3; // 取货中
			} else {
				roStatus = 1; // 进行中
			}
			int result = userMapper.updateRequirementOrderInfo(roStatus,
					Double.valueOf(totalMoney).doubleValue(),
					rVo.getsId(), payCode, rVo.getRoId());
			if (1 != result) {
				map.put("status", "false");
				map.put("errorString", "修改需求订单状态失败!");
				throw new RuntimeException();
			}
			/**
			 * 获取需求订单信息
			 */
			RequirementOrder requirementOrder = productMapper
					.doGetUserRequirementOrderByRoId(rVo.getRoId());
			String urId = requirementOrder.getUrId();
			/**
			 * 修改用户需求信息
			 */
			int row1 = productMapper.doUpdateUserRequirementSId(rVo.getsId(),
					urId, Double.valueOf(totalMoney).doubleValue());
			if (1 != row1) {
				map.put("errorString", "修改用户需求信息失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
			/**
			 * 根据roId获取申请店铺信息
			 */
			List<StoreApplyRequirement> storeApplyRequirements = storeMapper.doGetAllStoreApplyRequirement(rVo.getRoId());
			int num = storeApplyRequirements.size();
			if (num > 1) {
				/**
				 * 删除其他商户申请接单信息
				 */
				int row2 =storeMapper.doDeleteOtherStoreApplyRequirement(rVo.getRoId(),rVo.getsId());
				if (1 != row2) {
					map.put("errorString", "删除其他商户申请接单信息失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}
			/**
			 * 修改商户申请接单信息
			 */
			int status = 1;
			int row3 = storeMapper.doUpdateStoreApplyRequirement(status,rVo.getsId(),rVo.getRoId());
			if (1 != row3) {
				map.put("errorString", "修改商户申请接单信息失败");
				map.put("status", "false");
				throw new RuntimeException();
			}
		} else if ("2".equals(orderType)) { // 缴纳保障金
			// 插入缴纳保障金记录
			UserDepositDataVo uVo = new UserDepositDataVo();
			uVo = Utils.parserJsonResult(orderData, UserDepositDataVo.class);
			/**
			 * 获取商户钱包相关数据
			 * t_user_wallet：UW_LEFT_MONEY、UW_DEPOSIT、UW_APPLY_TO_CASH、
			 * 		UW_ALREADY_TO_CASH
			 */
			UserWallet userWallet = manageSystemMapper
					.doMsGetMyWallet(uVo.getsId());
			if (null != userWallet) {
				/**
				 * 添加保障金缴纳记录
				 */
				int result = manageSystemMapper
						.doMsAddDepositRecord(uId, uVo.getsId(), 
								Utils.ALI_PAY_ID,
						Double.valueOf(totalMoney).doubleValue(),
						payCode);
				if (1 == result) {
					double uwDeposit = userWallet.getUwDeposit() + Double
							.valueOf(totalMoney).doubleValue();
					/**
					 * 修改商户钱包冻结（已缴纳）的保障金金额
					 */
					int changeResult = manageSystemMapper
							.msChangeUserWalletOfDeposit(uwDeposit,
									uVo.getsId());
					if (1 != changeResult) {
						map.put("status", "false");
						map.put("errorString", "修改商户保障金失败！");
						throw new RuntimeException();
					}
				} else {
					map.put("status", "false");
					map.put("errorString", "添加保障金缴纳记录失败！");
					throw new RuntimeException();
				}
			} else {
				map.put("status", "false");
				map.put("errorString", "该商户不存在！");
				throw new RuntimeException();
			}
		}
		AlipayTradeQueryResponse result = AliPayHttpClientUtils.execute(tradeNo,appType);
//		System.out.println(payCode);
		if (result.isSuccess()) {
			String tradeStatus = result.getTradeStatus();
			// 判断交易状态
			if (tradeStatus.equals("TRADE_SUCCESS")) {
				// 支付成功
				map.put("status", "true");
				map.put("errorString", "");
			} else if (tradeStatus.equals("WAIT_BUYER_PAY")){
				// 其他交易状态
				//交易状态未成功,但处于支付中,继续监听
				map.put("status", "false");
				map.put("errorString", "0");
				TransactionAspectSupport.currentTransactionStatus()
				.setRollbackOnly();
			}else if (tradeStatus.equals("TRADE_CLOSED")) {
				// 交易状态未成功处于支付超时，即交易失败，回滚数据库操作
				map.put("status", "false");
				map.put("errorString", "1");
				TransactionAspectSupport.currentTransactionStatus()
				.setRollbackOnly();	
			}else {
				map.put("status", "true");
				map.put("errorString", "");
			}
		} else {
			// 查询失败
			map.put("status", "false");
			map.put("errorString", "查询失败！");
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
		}	
		return map;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public Map<String, Object> doAliTradeRefund(String uId, String poId, String payCode, String totalMoney)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 用户在购买商品过程中处于待发货和待收货状态时可以申请退款,商家同意退款后钱返还给用户
		 */
		//根据poId获取订单信息
		ProductOrder productOrder = productMapper.doGetProductOrderByPoId(poId);
		int poStatus = productOrder.getPoStatus();
		if (5 == poStatus) { //用户申请退款
			//调用支付宝交易退款接口
			DecimalFormat df = new DecimalFormat("0.00");
			Map<String, Object> parm = new HashMap<>();
			parm.put("out_trade_no", payCode); //支付订单号
			parm.put("refund_amount", df.format(Double.valueOf(totalMoney)
					.doubleValue())); //退款总金额
			parm.put("out_request_no", Utils.randomUUIDWithoutLine());
			System.out.println(df.format(Double.valueOf(totalMoney)
					.doubleValue()));
			/**
			 * 调支付宝交易退款API并获取数据
			 */
			AlipayTradeRefundResponse response = AliPayHttpClientUtils.aliTradeRefund(parm);
			String result = response.getBody();
			JSONObject jsonObject = JSONObject.fromObject(result);
			String msg = jsonObject.getJSONObject("alipay_trade_refund_response")
					.getString("msg");
			String code = jsonObject.getJSONObject("alipay_trade_refund_response")
					.getString("code");
			if("10000".equals(code)&&"Success".equals(msg)){
				// 退款成功 
				map.put("status", "true");
				map.put("errorString", "");
				int status = 6;
				int row = productMapper.doUpdateProductOrderStatus(status,poId);
				if(row!=1){
					map.put("errorString", "修改订单状态失败");
					map.put("status", "false");
					throw new RuntimeException();
				}
			}else{
				// 退款失败
				map.put("status", "false");
				try {
					map.put("errorString", msg);
				} catch (Exception e) {
					map.put("errorString", "退款失败！");
				}
			}
		} else {
			map.put("status", "false");
			map.put("errorString", "该订单非待退款状态!");
		}	
		return map;
	}

}
