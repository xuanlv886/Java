package com.service.extra.mall.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.extra.mall.service.UserService;
import com.service.extra.mall.wangyiyun.CheckSumBuilder;

import util.Utils;

/**
 * 
 * desc：网易云消息抄送接口控制器
 * @author J
 * time:2018年6月11日
 */

@Controller
@RequestMapping(value = {"/route"})
public class RouteController {
    public static final Logger logger = LoggerFactory
            .getLogger(RouteController.class);
    @Resource private UserService userService;
    
    @RequestMapping(value = {"/mockClient.action"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject mockClient(HttpServletRequest request)
            throws Exception {
        JSONObject result = new JSONObject();
        try {
            // 获取请求体
            byte[] body = readBody(request);
            if (body == null) {
                logger.warn("request wrong, empty body!");
                result.put("code", 414);
                return result;
            }
            // 获取部分request header，并打印
            String ContentType = request.getContentType();
            String AppKey = request.getHeader("AppKey");
            String CurTime = request.getHeader("CurTime");
            String MD5 = request.getHeader("MD5");
            String CheckSum = request.getHeader("CheckSum");
           /* logger.info("request headers: ContentType = {}, AppKey = {}, CurTime = {}, " +
                  "MD5 = {}, CheckSum = {}", ContentType, AppKey, CurTime, MD5, CheckSum);*/
            // 将请求体转成String格式，并打印
            String requestBody = new String(body, "utf-8");
            logger.error("request body = {}", requestBody);
//            System.out.println("request body = {}" + requestBody);
            // 获取计算过的md5及checkSum
            String verifyMD5 = CheckSumBuilder.getMD5(requestBody);
            String verifyChecksum = CheckSumBuilder.getCheckSum(Utils.APPSECRET, verifyMD5, CurTime);
            logger.error("verifyMD5 = {}, verifyChecksum = {}", verifyMD5, verifyChecksum);
//            System.out.println("verifyMD5 = {}, verifyChecksum = {}" + verifyMD5 + verifyChecksum);
            // TODO: 比较md5、checkSum是否一致，以及后续业务处理
           Gson gson = new Gson();
            Map<String, Object> retMap = gson.fromJson(requestBody, new TypeToken<Map<String, Object>>() {
			}.getType());
            String body1 = (String) retMap.get("body");
            String attach = (String) retMap.get("attach");
            String convType = (String) retMap.get("convType");
            String eventType = (String) retMap.get("eventType");
            String fromAccount = (String) retMap.get("fromAccount");
            String fromClientType = (String) retMap.get("fromClientType");
            String fromDeviceId = (String) retMap.get("fromDeviceId");
            String fromNick = (String) retMap.get("fromNick");
            String msgTimestamp = (String) retMap.get("msgTimestamp");
            String msgType = (String) retMap.get("msgType");
            String msgidClient = (String) retMap.get("msgidClient");
            String msgidServer = (String) retMap.get("msgidServer");
            String resendFlag = (String) retMap.get("resendFlag");
            String to = (String) retMap.get("to");
            /**
             * 判断消息是否存在
             */
            int row = userService.doSelectMessage(to,fromAccount,msgTimestamp,body1);
            if (row == 0) {
            	/**
                 * 插入消息抄送表
                 */
                userService.doAddMessageCopy(eventType,convType,to,fromAccount,msgTimestamp,msgType,msgidClient,msgidServer,body1);
			}
            //获取请求体
            result.put("code", 200);
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code", 414);
            return result;
        }
    }
    private byte[] readBody(HttpServletRequest request) throws IOException {
        if (request.getContentLength() > 0) {
            byte[] body = new byte[request.getContentLength()];
            IOUtils.readFully(request.getInputStream(), body);
            return body;
        } else
            return null;
    }
}
