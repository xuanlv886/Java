package util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;

/**
 * 
 * desc：通用工具类
 * @author L
 * time:2018年1月9日
 */
public class Utils {
	
	/**
	 * 项目名称
	 */
	public static final String PROJECT_NAME ="家装商城";

	/**
	 * 图片根链接
	 */
	public static final String PIC_BASE_URL ="https://hsmcommon.oss-cn-beijing.aliyuncs.com/";
	/**
	 * 店铺图片路径
	 */
	public static final String STORE_PIC = "store/";
	/**
	 * 商品图片路径
	 */
	public static final String PRODUCT_PIC = "product/";
	/**
	 * 商品图片详情图片路径
	 */
	public static final String PRODUCT_HTML_PIC = "product/detail/";
	/**
	 * 商品类别图片路径
	 */
	public static final String PRODUCT_TYPE_PIC = "product/type/";
	/**
	 * 需求相关图片路径
	 */
	public static final String REQUIREMENT_PIC = "requirement/";
	/**
	 * 通用图片路径（支付方式图片）
	 */
	public static final String COMMON_PIC = "common/";
	/**
	 * 用户头像路径
	 */
	public static final String PROFILE_PIC = "profilephoto/";
	/**
	 * 主页面轮播图片路径
	 */
	public static final String SLIDE_PIC = "slide/";
	/**
	 * 引导页图片路径
	 */
	public static final String GUIDE_PIC = "guide/";
	/**
	 * 评价图片路径
	 */
	public static final String EVALUATE_PIC = "evaluate/";
	/**
	 * 阿里云OSS相关参数
	 */
	public static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
	public static String accessKeyId = "LTAIFYiCJtna4hE0";
	public static String accessKeySecret = "Tq24v1zG0SAg2l09sumIa5LVQUzr4K";
	public static String bucketName = "hsmcommon";
	
	/**
	 * 测试数据
	 */
//	public static final int testMoney = 1;
	public static final String PRODUCT_DES = "家装商城商品消费";
	public static final String REQUIREMENT_DES = "家装商城发布需求消费";
	public static final String DEPOSIT_DES = "缴纳保障金";
	/**
	 * 微信支付方式主键标识
	 */
	public static final String WX_PAY_ID = "f1f2215c-43a9-11e8-8390-4ccc6a70ac67";
	/**
	 * 支付宝支付方式主键标识
	 */
	public static final String ALI_PAY_ID = "f1f2231e-43a9-11e8-8390-4ccc6a70ac67";
	/**
	 * 需求类型--取货送货主键标识
	 */
	public static final String REQUIREMENT_TYPE_SEND = "8a7caa94-43aa-11e8-8390-4ccc6a70ac67";
	/**
	 * 网易云--app
	 */
	public static final String APPKEY = "a554f6d632ae9e0e05c6139a3439ebf1";
	public static final String APPSECRET = "3a63d8066d4a";
	
	/**
	 * 
	 * desc:通用错误响应
	 * param:
	 * return:Map<String,Object>
	 * time:2018年1月10日
	 * author:L
	 */
	public static Map<String,Object> commonErrorMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = false;
		String errorString=null;
		Object data = null;
		map = packageResponseDate(flag, errorString, data);
		return map;
	}
	
	/**
	 * 
	 * desc: 组装通用的响应数据格式
	 * param: flag--标志位，用于判断响应数据是否有异常
	 * 			errorString--错误信息
	 * 			data--正常响应数据
	 * return:Map<String,Object>
	 * time:2018年1月9日
	 * author:L
	 */
	public static Map<String,Object> packageResponseDate(boolean flag, String errorString,
			Object data){
		if (null == errorString) {
			errorString = "服务异常，请稍后重试！";
		}
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("flag", flag + "");
    	map.put("data", data);
    	map.put("errorString", errorString);
    	return map;
    }
	
	/**
	 * 
	 * desc:格式化当前时间，精确到天
	 * param:
	 * return:String
	 * time:2018年1月10日
	 * author:L
	 */
	public static String formatNowTimeLimitDay () {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}
	/**
	 * desc:格式化当前时间，精确到毫秒两位
	 * param:
	 * return:String
	 * time:2018年2月1日
	 * author:J
	 */
	public static String formatNowTimeLimitMillisecond () {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String datetime = formatter.format(currentTime);
		return datetime.substring(0,datetime.length()-1);
	}
	/**
	 * desc:格式化当前时间，精确到秒
	 * param:
	 * return:String
	 * time:2018年3月17日
	 * author:J
	 */
	public static String formatNowTimeLimitSecond() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = formatter.format(currentTime);
		return datetime.substring(0,datetime.length()-1);
	}
	/**
	 * desc: 随机生成36位的UUID
	 * param:
	 * return:String
	 * time:2018年1月29日
	 * author:J
 */
	public static String randomUUID() {  
//	        return UUID.randomUUID().toString().replace("-", "");  
	        return UUID.randomUUID().toString();  
	    }  
	
	/**
	 * desc: 随机生成36位的UUID
	 * param:
	 * return:String
	 * time:2018年1月29日
	 * author:J
 */
	public static String randomUUIDWithoutLine() {  
	        return UUID.randomUUID().toString().replace("-", "");
	    } 
	 
	/**
	 * desc:生成四位随机数
	 * param:
	 * return:String
	 * time:2018年2月1日
	 * author:J
	 */ 
	public static String getFourRandom(){
	        Random random = new Random();
	        String fourRandom = random.nextInt(10000) + "";
	        int randLength = fourRandom.length();
	        if(randLength<4){
	          for(int i=1; i<=4-randLength; i++)
	              fourRandom = "0" + fourRandom  ;
	      }
	        return fourRandom;
	}
	
	/**
	 * desc:生成8位随机数
	 * param:
	 * return:String
	 * time:2018年2月1日
	 * author:J
	 */ 
	public static String getEightRandom(){
	        Random random = new Random();
	        String eightRandom = random.nextInt(100000000) + "";
	        int randLength = eightRandom.length();
	        if(randLength<8){
	          for(int i=1; i<=8-randLength; i++)
	        	  eightRandom = "0" + eightRandom  ;
	      }
	        return eightRandom;
	}

	/*
     * 解析响应的json数据到实体类
     */
    public static <T> T parserJsonResult(String in, Class<T> cls) {
        // TODO Auto-generated method stub
        T t = null;
        if (in == null) {
            return null;
        }
        try {
            Gson gson = new Gson();
            t = gson.fromJson(in, cls);
        } catch (Exception e) {
            // TODO: handle exception
        	System.out.println("解析响应的json数据异常:" + e.getMessage());
        }
        return t;
    }
	 /** 
     * 得到昨天的日期 
     * @return 
     */  
    public static String getYestoryDate() {  
            Calendar calendar = Calendar.getInstance();    
            calendar.add(Calendar.DATE,-1);  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            String yestoday = sdf.format(calendar.getTime());  
            return yestoday;  
    } 
	
    /**
     * 
     * desc:获取min~max范围内的随机数
     * param: 
     * return:int
     * time:2018年6月16日
     * author:L
     */
    public static int getLimitRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * 
     * 
    	 * desc: 将实体类拼装成json字符串
    	 * param:
    	 * return:
    	 * time:
    	 * author:J	 
     */
    public static String ObjectToJson (Object obj) {
    	String result = null;
        if (null == obj) {
            return null;
        }
        try {
            Gson gson = new Gson();
            result = gson.toJson(obj);
        } catch (Exception e) {
        	System.out.println("拼装json格式字符串异常:" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 
     * desc:List去重
     * param:
     * return:List
     * time:2018年9月3日
     * author:L
     */
    public static List removeDuplicate(List list) {   
        HashSet h = new HashSet(list);   
        list.clear();   
        list.addAll(h);   
        return list;   
    }
    
    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
    	if(str == null || str.length() == 0){
    		return true;
    	}
    	return false;
    } 
}
