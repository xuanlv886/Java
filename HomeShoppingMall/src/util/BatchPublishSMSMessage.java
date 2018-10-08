package util;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;





public class BatchPublishSMSMessage {
	static final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	static final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	
	
	
	public static void Push(String phone,Map<String,String>paramMap,String templateCode) throws ClientException {
		System.out.println("短信功能启用---------------");
		
		IClientProfile profile = DefaultProfile.getProfile("cn-beijing", Utils.accessKeyId,
				Utils.accessKeySecret);
		DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		 request.setPhoneNumbers(phone);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("石家庄爱团科技有限公司");
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode(templateCode);
		 
		 Gson gson = new Gson();
		 String result = gson.toJson(paramMap); 
		 request.setTemplateParam(result);
		 
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		//请求成功 
			System.out.println("短信发送成功");
		}else{
			System.out.println("发送返回状态码+++"+sendSmsResponse.getCode());
		}
		
		
        
    }
}
