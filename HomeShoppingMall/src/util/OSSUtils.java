package util;

import com.aliyun.oss.OSSClient;

public class OSSUtils {
	
	private static OSSClient ossClient;
	
	public static void init(){
		if (null == ossClient) {
			ossClient = new OSSClient(Utils.endpoint,
					Utils.accessKeyId, Utils.accessKeySecret);
		}
	} 

	/**
	 * 
	 * desc:删除OSS内object
	 * param:
	 * 		bucketName--节点名
	 * 		objectName--要删除的文件名
	 * return:void
	 * time:2018年5月23日
	 * author:L
	 */
	public static void delObject(String bucketName, String objectName){
		init();
		ossClient.deleteObject(bucketName, objectName);
		ossClient.shutdown();
	}  
}
