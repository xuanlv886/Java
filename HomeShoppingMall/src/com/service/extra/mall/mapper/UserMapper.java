package com.service.extra.mall.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.service.extra.mall.model.AreaCode;
import com.service.extra.mall.model.Guide;
import com.service.extra.mall.model.MovingTrajectory;
import com.service.extra.mall.model.OpenCity;
import com.service.extra.mall.model.PayStyle;
import com.service.extra.mall.model.Pic;
import com.service.extra.mall.model.Product;
import com.service.extra.mall.model.ProductOrder;
import com.service.extra.mall.model.ProductOrderDetail;
import com.service.extra.mall.model.SafetyQuestion;
import com.service.extra.mall.model.User;
import com.service.extra.mall.model.UserAttention;
import com.service.extra.mall.model.UserCollection;
import com.service.extra.mall.model.UserDeliverAddress;
import com.service.extra.mall.model.UserFootprint;
import com.service.extra.mall.model.UserMemoName;
import com.service.extra.mall.model.UserRole;
import com.service.extra.mall.model.UserTrolley;
import com.service.extra.mall.model.Version;

/**
 * 
 * desc：平台管理系统相关接口Mapper
 * @author J
 * time:2018年5月17日
 */
public interface UserMapper {

	public User doUserLogin(@Param("uAccount")String uAccount, @Param("uPassword")String uPassword) throws SQLException;

	public UserRole doGetUserRoleByUrId(@Param("urId")String urId) throws SQLException;

	public int doAddCustomer(User user) throws SQLException;

	public List<UserRole> doGetUserRole() throws SQLException;

	public User doGetUserInfoByUId(@Param("uId")String uId) throws SQLException;
	
	public User doGetUserInfoBySId(@Param("sId")String sId) throws SQLException;

	public int doUpdateUserPassword(@Param("uId")String uId, @Param("uNewPassword")String uNewPassword) throws SQLException;

	public int doUpdateUserSecretQuestion(@Param("uId")String uId, @Param("uFirstSqId")String uFirstSqId, @Param("uFirstSqAnswer")String uFirstSqAnswer, @Param("uSecondSqId")String uSecondSqId,
			@Param("uSecondSqAnswer")String uSecondSqAnswer, @Param("uThirdSqId")String uThirdSqId, @Param("uThirdSqAnswer")String uThirdSqAnswer) throws SQLException;

	public List<SafetyQuestion> doGetSafetyQuestion(@Param("sqPosition")String sqPosition) throws SQLException;

	public int doUpdateUserInfo(@Param("uId")String uId, @Param("uNickName")String uNickName, @Param("uSex")String uSex, @Param("uEmail")String uEmail, @Param("uBirthday")String uBirthday) throws SQLException;

	public List<Pic> doGetPicInfoByPTag(@Param("pTag")String pTag) throws SQLException;

	public int doUpdateUserAvatar(@Param("pId")String pId,@Param("pName")String pName) throws SQLException;

	public List<UserDeliverAddress> doGetUserDeliverAddress(@Param("uId")String uId) throws SQLException;

	public int doAddUserDeliverAddress(@Param("uId")String uId, @Param("udaTrueName")String udaTrueName,@Param("udaTel")String udaTel, @Param("udaAddress")String udaAddress, @Param("def")int def) throws SQLException;

	public int doUpdateUserDeliverAddress(@Param("udaId")String udaId, @Param("uId")String uId, @Param("udaTrueName")String udaTrueName, @Param("udaTel")String udaTel,@Param("udaAddress")String udaAddress
			 ) throws SQLException;

	public int doDelUserDeliverAddress(@Param("udaId")String udaId, @Param("uId")String uId) throws SQLException;

	public UserDeliverAddress doGetDefaultUserDeliverAddress(@Param("uId")String uId) throws SQLException;

	public int doSetDefaultUserDeliverAddress(@Param("udaId")String udaId,@Param("def")int def) throws SQLException;

	public List<UserAttention> doGetUserAttention(@Param("uId")String uId,@Param("status")int status,@Param("end")int end) throws SQLException;

	public int doAddUserAttention(@Param("uId")String uId, @Param("sId")String sId) throws SQLException;

	public int doDelUserAttention(@Param("uId")String uId, @Param("sId")String sId) throws SQLException;

	public UserTrolley doSelectUserTrolley(@Param("uId")String uId, @Param("utProductProperty")String utProductProperty, @Param("pId")String pId, @Param("sId")String sId) throws SQLException;

	public int doAddUserTrolley(@Param("uId")String uId, @Param("pId")String pId, @Param("utProductNum")int pNum, @Param("utProductProperty")String utProductProperty, @Param("sId")String sId) throws SQLException;

	public int doUpdateUserTrolleyProductNum(@Param("utProductNum")String utProductNum, @Param("utId")String utId) throws SQLException;

	public int doDelUserTrolley(@Param("utId")String utId, @Param("uId")String uId) throws SQLException;
	
	public int doDeleteUserTrolley(@Param("uId")String uId, @Param("sId")String sId, @Param("pId")String pId,
			@Param("podProperty")String podProperty) throws SQLException;

	public List<UserTrolley> doGetUserTrolley(@Param("uId")String uId) throws SQLException;
	
	public List<UserTrolley> doGetUserTrolleyByUid(@Param("uId")String uId) throws SQLException;

	public int doAddUserCollection(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public int doDelUserCollection(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public List<UserCollection> doGetUserCollection(@Param("uId")String uId,@Param("status")int status,@Param("end")int end) throws SQLException;

	public List<UserFootprint> doGetUserFootPrint(@Param("uId")String uId,@Param("status")int status,@Param("end")int end) throws SQLException;

	public int doDelUserFootPrint(@Param("uId")String uId, @Param("ufId")String ufId) throws SQLException;

	public int doAddUserFeedBack(@Param("uId")String uId, @Param("fType")String fType, @Param("fAppType")String fAppType, @Param("fContent")String fContent) throws SQLException;

	public List<PayStyle> doGetPayStyle() throws SQLException;

	public User doForgetSecretQuestion(@Param("uId")String uId, @Param("uPassword")String uPassword) throws SQLException;

	public int doAddPic(@Param("pName")String pName, @Param("pFileName")String pFileName,@Param("pTag")String pTag, @Param("pNo")int pNo,@Param("pJump")String pJump) throws SQLException;

	public UserDeliverAddress doGetUserDeliverAddressByUdaId(@Param("udaId")String udaId) throws SQLException;

	public int doGetUserCollectionNum(@Param("uId")String uId) throws SQLException;

	public int doGetUserFootPrintNum(@Param("uId")String uId) throws SQLException;

	public int doGetUserAttentionNum(@Param("uId")String uId) throws SQLException;

	public UserCollection selectUserCollection(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public UserAttention selectUserAttention(@Param("uId")String uId,  @Param("sId")String sId) throws SQLException;

	public int doGetProductOrderEvaluateNum(@Param("uId")String uId) throws SQLException;

	public int doGetRequirementOrderEvaluateNum(@Param("uId")String uId) throws SQLException;

	public UserFootprint selectUserFootPrint(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public int doAddUserFootPrint(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public User selectUserSecretQuestion(@Param("uId")String uId, @Param("uOldFirstSqId")String uOldFirstSqId, @Param("uOldFirstSqAnswer")String uOldFirstSqAnswer,
			@Param("uOldSecondSqId")String uOldSecondSqId, @Param("uOldSecondSqAnswer")String uOldSecondSqAnswer, @Param("uOldThirdSqId")String uOldThirdSqId, @Param("uOldThirdSqAnswer")String uOldThirdSqAnswer) throws SQLException;

	public int doUpdateSecretQuestion(@Param("uId")String uId, @Param("uNewFirstSqId")String uNewFirstSqId, @Param("uNewFirstSqAnswer")String uNewFirstSqAnswer, @Param("uNewSecondSqId")String uNewSecondSqId,
			@Param("uNewSecondSqAnswer")String uNewSecondSqAnswer, @Param("uNewThirdSqId")String uNewThirdSqId, @Param("uNewThirdSqAnswer")String uNewThirdSqAnswer) throws SQLException;

	public User selectUserInfoByUPhoneId(@Param("uId")String uId, @Param("uPhoneId")String uPhoneId) throws SQLException;

	public User doGetUserAnswerSecretQuestion(@Param("uId")String uId, @Param("firstAnswer")String firstAnswer, @Param("secondAnswer")String secondAnswer, @Param("thirdAnswer")String thirdAnswer) throws SQLException;

	public int doupdateUserUPhoneId(@Param("uId")String uId, @Param("uPhoneId")String uPhoneId) throws SQLException;

	public Pic doSelectOnePicInfoByPTag(@Param("pTag")String pTag) throws SQLException;

	public List<OpenCity> doGetCityList() throws SQLException;

	public int doAddUserWallet(@Param("sId")String sId) throws SQLException;

	public UserAttention doGetStoreIfAttention(@Param("uId")String uId, @Param("sId")String sId) throws SQLException;

	public UserCollection doGetUserIfCollection(@Param("uId")String uId, @Param("pId")String pId) throws SQLException;

	public int doAddMessageCopy(@Param("mcEventType")String mcEventType, @Param("mcConvType")String mcConvType, @Param("mcTo")String mcTo, @Param("mcFrom")String mcFrom, @Param("mcTimeStamp")String mcTimeStamp,
			@Param("mcMsgType")String mcMsgType, @Param("mcMsgIdClient")String mcMsgIdClient, @Param("mcMsgIdServer")String mcMsgIdServer, @Param("mcBody")String mcBody) throws SQLException;

	public Guide doGetGuideInfo (@Param("gAppType")int gAppType)
			throws SQLException;

	public int doSelectMessage(@Param("to")String to, @Param("fromAccount")String fromAccount, @Param("msgTimestamp")String msgTimestamp, @Param("body1")String body1) throws SQLException;

	public AreaCode doGetAreaCode(@Param("acCode")String acCode) throws SQLException;

	public List<Pic> doGetSlidePic() throws SQLException;
	public int msUpdateUserPhoneId(
			@Param("uPhoneId")String uPhoneId,
			@Param("uId")String uId) throws SQLException;

	public User doRetrievePsw(
			@Param("uAccount")String uAccount) throws SQLException;
	public AreaCode doGetSearchCityList(@Param("keyWords")String keyWords) throws SQLException;

	public Version versionController(@Param("vType")int vtype, @Param("phoneType")int phoneType) throws SQLException;

	public int doUpdateStoreUserInfo(
			@Param("uId")String uId,
			@Param("uNickName")String uNickName,
			@Param("uSex")int uSex,
			@Param("uEmail")String uEmail,
			@Param("uBirthday")String uBirthday,
			@Param("uTel")String uTel) throws SQLException;
	
	public int updateStoreInfo(
			@Param("sId")String sId,
			@Param("sName")String sName,
			@Param("sDescribe")String sDescribe,
			@Param("sTel")String sTel) throws SQLException;
	
	public int checkStoreUserExist(
			@Param("uAccount")String uAccount,
			@Param("uPassword")String uPassword,
			@Param("uId")String uId) throws SQLException;
	
	public Version getVersionInfo(
			@Param("vType")int vType,
			@Param("vSystemType")int vSystemType) throws SQLException;

	public int getRequirementOrderInfoByRoIdAndRoStatus(
			@Param("roId")String roId,
			@Param("roStatus")int roStatus) throws SQLException;
	
	public ProductOrder getPoOrderIdByPoId(
			@Param("poId")String poId) throws SQLException;
	
	public int updateProductOrderInfoByPoId(
			@Param("psId")String psId,
			@Param("poTotalMoney")double poTotalMoney,
			@Param("poOrderId")String poOrderId,
			@Param("poDeliverName")String poDeliverName,
			@Param("poDeliverTel")String poDeliverTel,
			@Param("poDeliverAddress")String poDeliverAddress,
			@Param("poId")String poId) throws SQLException;
	
	public int updateRequirementOrderInfo(
			@Param("roStatus")int roStatus,
			@Param("roTotalPrice")double roTotalPrice,
			@Param("sId")String sId,
			@Param("roOrderId")String roOrderId,
			@Param("roId")String roId) throws SQLException;
	
	public int doUpdateRequirementOrderStatus(@Param("status")int status,
			@Param("roId")String roId) throws SQLException;
	
	public List<ProductOrder> getProductOrderInfo(
			@Param("uId")String uId,
			@Param("poPayCode")String poPayCode) throws SQLException;
	
	public int updateProductOrderStatus(
			@Param("poStatus")int poStatus,
			@Param("poId")String poId) throws SQLException;
	
	public List<ProductOrderDetail> getProductOrderDetailListByPoId(
			@Param("poId")String poId) throws SQLException;

	public List<MovingTrajectory> doGetMerchantLonLatList(
			@Param("roId")String roId) throws SQLException;
	
	public int doSetUserMemoName(
			@Param("umnFromId")String umnFromId,
			@Param("umnToId")String umnToId,
			@Param("umnName")String umnName) throws SQLException;
	
	public List<UserMemoName> doGetUserMemoName(
			@Param("uId")String uId) throws SQLException;
	
	public UserMemoName doGetUserMemoNameByUid(@Param("umnFromId")String umnFromId,
			@Param("umnToId")String umnToId) throws SQLException;
	
	public int doUpdataUserMemoName(@Param("umnFromId")String umnFromId,
			@Param("umnToId")String umnToId,@Param("umnName")String umnName) throws SQLException;

	public int delUserFootprintByPid(@Param("pId")String pId) throws SQLException;
	
	public int delUserTrolleyByPid(@Param("pId")String pId) throws SQLException;
	
	public int delUserCollectionByPid(@Param("pId")String pId) throws SQLException;
}
