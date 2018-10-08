package com.service.extra.mall.service;

public interface WangYiYunService {

	Object updateUinfo(String accid, String name, String ex) throws Exception;

	Object create(String accid, String name) throws Exception;

	Object sendMsg(String from, String to, String body, String ope, String type) throws Exception;

	Object recall(String deleteMsgid, String timetag, String type, String from, String to) throws Exception;

	Object querySessionMsg(String from, String to, String begintime, String endtime, String limit) throws Exception;

	Object getUinfos(String accids) throws Exception;

	Object update(String accid) throws Exception;

	Object refreshToken(String accid) throws Exception;

}
