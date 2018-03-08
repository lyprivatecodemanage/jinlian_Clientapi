package com.xiangshangban.transit_service.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.transit_service.bean.TokenCompany;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface TokenCompanyService {

	TokenCompany getToken(String appId,String secretKey);
	
	TokenCompany selectByToken(String token);
}
