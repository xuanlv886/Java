package util;

/**
 * 
 * desc：系统操作日志中操作类型的枚举。
 * @author L
 * time:2018年5月4日
 */
public enum SysOperateType {

	STORE_CHECK_PASS("商户审核--通过", 0), 
	STORE_CHECK_FAIL("商户审核--驳回", 1), 
	STORE_CHECK_DOWN("商户审核--下架", 2), 
	PRODUCT_CHECK_PASS("商品审核--通过", 3),
	PRODUCT_CHECK_FAIL("商品审核--驳回", 4), 
	PRODUCT_CHECK_DOWN("商品审核--下架", 5), 
	STORE_CONFIG_CHANGE("修改商户配置信息", 6), 
	PRODUCT_CONFIG_CHANGE("修改商品配置信息", 7),
	ADD_SYS_NOTICE("发布系统公告", 8),
	CHANGE_SYS_NOTICE("修改系统公告", 9),
	ADD_ADMIN_ACCOUNT("添加管理者账号", 10),
	CHANGE_USER_INFO("修改用户账号信息", 11),
	ADD_OPEN_CITY("新增服务城市", 12),
	CHANGE_OPEN_CITY("修改服务城市信息", 13),
	DEL_OPEN_CITY("删除已开通服务的城市", 14),
	ADD_PRODUCT_TYPE("添加商品大类", 15),
	CHANGE_PRODUCT_TYPE_DETAIL("修改商品小类", 16),
	ADD_PRODUCT_TYPE_DETAIL("添加商品小类", 17),
	ADD_PRODUCT_PROPERTY("添加商品属性", 18),
	CHANGE_PP_RELATION_PTD("修改商品属性关联类别", 19),
	CHANGE_MERCHANT_APPLY_TO_CASH_STATUS("修改商户提现状态", 20);

	private String name ;
	private int id ;
	 
	private SysOperateType( String name , int id ){
	    this.name = name ;
	    this.id = id ;
	}
	 
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id = id;
	}
}
