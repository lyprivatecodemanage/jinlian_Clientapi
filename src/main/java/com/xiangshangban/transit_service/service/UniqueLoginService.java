package com.xiangshangban.transit_service.service;

import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.UniqueLogin;

public interface UniqueLoginService {
	
	UniqueLogin selectByPhone(String phone);

	UniqueLogin selectBySessionId(String sessionId);

	UniqueLogin selectByToken(String token);
	
	UniqueLogin selectByTokenAndClientId(String token, String clientId);

	int insert(UniqueLogin uniqueLogin);

	int deleteByPhone(String phone);
	
	int deleteBySessinId(String sessionId);
	
	int deleteByTokenAndClientId(String token, String clientId);
}