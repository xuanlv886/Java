package com.service.extra.mall.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.service.extra.mall.model.PayStyle;
import com.service.extra.mall.model.RecommendStore;
import com.service.extra.mall.model.RequirementOrder;
import com.service.extra.mall.model.RequirementOrderEvaluate;
import com.service.extra.mall.model.Store;
import com.service.extra.mall.model.StoreApplyRequirement;
import com.service.extra.mall.model.StoreArrange;
import com.service.extra.mall.model.UserToCashRecord;
import com.service.extra.mall.model.UserWallet;

/**
 * 
 * desc：平台管理系统相关接口Mapper
 * @author J
 * time:2018年5月17日
 */
public interface StoreMapper {

	public Store doGetStoreInfo(@Param("sId")String sId) throws SQLException;

	public int doApplyToCash(@Param("sId")String sId,@Param("uId")String uId, @Param("utcrMoney")double utcrMoney, @Param("psId")String psId, @Param("utcrAccount")String utcrAccount) throws SQLException;

	public UserWallet doGetMyWallet(@Param("sId")String sId)throws SQLException;
	
	public int getApplyToCashListCount(@Param("sId")String sId)throws SQLException;

	public int doGetPayDepositMoney(@Param("sId")String sId, @Param("uId")String uId, @Param("udrMoney")String udrMoney,@Param("psId")String psId,@Param("status")int status) throws SQLException;

	public int doGetThawDepositMoney(@Param("sId")String sId, @Param("uId")String uId, @Param("udrMoney")String udrMoney,@Param("status")int status) throws SQLException;

	public int doAddStoreInfo(Store store) throws SQLException;

	public int doAddStoreArrange(@Param("sId")String sId, @Param("saContent")String saContent, @Param("uId")String uId) throws SQLException;

	public int doAddStoreApplyRequirement(@Param("roId")String roId, @Param("sId")String sId, @Param("quotedPrice")String quotedPrice) throws SQLException;

	public int doDelStoreArrange(@Param("saId")String saId) throws SQLException;

	public List<StoreArrange> doGetMyArranges(@Param("sId")String sId, @Param("uId")String uId) throws SQLException;

	public int doUpdateMyArrange(@Param("saId")String saId,@Param("saContent")String saContent)throws SQLException;
	
	public int doCancelStoreRequirement(@Param("sId")String sId, @Param("roId")String roId) throws SQLException;

	public List<UserToCashRecord> doGetAllAlreadyCashRecord(@Param("sId")String sId, @Param("uId")String uId,
			@Param("i")int i,@Param("j")int j) throws SQLException;

	public int doDelStoreApplyRequirement(@Param("roId")String roId) throws SQLException;
	
	public int doDeleteStoreApplyRequirement(@Param("roId")String roId,@Param("sId")String sId) throws SQLException;
	
	public int doDeleteOtherStoreApplyRequirement(@Param("roId")String roId,@Param("sId")String sId) throws SQLException;
	
	public int doUpdateStoreApplyRequirement(@Param("status")int status,@Param("sId")String sId,@Param("roId")String roId) throws SQLException;

	public StoreApplyRequirement doGetStoreApplyRequirement(@Param("roId")String roId, @Param("sId")String sId) throws SQLException;

	public int doGetMyArrangesNum(@Param("sId")String sId, @Param("uId")String uId) throws SQLException;

	public int doGetStoreApplyRequirementNum(@Param("roId")String roId) throws SQLException;

	public List<StoreApplyRequirement> doGetAllStoreApplyRequirement(@Param("roId")String roId) throws SQLException;

	public int doUpdateStoreInfo(Store store) throws SQLException;

	public int doUpdateUserWalletDeposit(@Param("sId")String sId, @Param("udrMoney")String udrMoney,@Param("status")int status) throws SQLException;

	public int doUpdateUserWalletLeftMoney(@Param("sId")String sId, @Param("receivingMoney")double receivingMoney, @Param("status")int status) throws SQLException;

	public List<Store> doSelectStoreBylooseName(@Param("looseName")String looseName, @Param("start")int start, @Param("end")int end) throws SQLException;

	public int doGetRecommendStoreCount(@Param("acId")String acId) throws SQLException;

	public List<RecommendStore> doGetRecommendStore(@Param("acId")String acId,@Param("min")int min,
			@Param("max")int max) throws SQLException;

	public List<RequirementOrder> getRequirementOrderListWhichSatisfyCondition(
			@Param("sId")String sId) throws SQLException;
	
	public int addMovingTrajectoryPoint(@Param("mtLon")String mtLon,@Param("mtLat")String mtLat,
			@Param("roId")String roId) throws SQLException;
	
	public int changeUserWalletOfLeftMoney(@Param("uwLeftMoney")double uwLeftMoney,@Param("uwApplyToCash")double uwApplyToCash,
			@Param("sId")String sId)throws SQLException;
	
	public int merchantSend(@Param("poId")String poId,@Param("poDeliverCompany")String poDeliverCompany,
			@Param("poDeliverCode") String poDeliverCode)throws SQLException;
	
	public PayStyle getPayStyleByPsId(@Param("psId")String psId)throws SQLException;
	
	public RequirementOrderEvaluate getRequirementOrderEvaluate(@Param("roId")String roId)throws SQLException;
}
