package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.TokenCompany;

@Mapper
public interface TokenCompanyMapper {

	/**
	 * 新增公司与token关联表信息
	 * @param tokenCompany
	 * @return
	 */
	int insert(TokenCompany tokenCompany);
	
	/**
	 * 根据token查询记录
	 * @param token
	 * @return
	 */
	TokenCompany selectByToken(String token);
	
	/**
	 * 根据appId 和 secretKey 查询记录
	 * @param appId
	 * @param secretKey
	 * @return
	 */
	TokenCompany SelectByAppIdAndSecretKey(@Param("appId")String appId,@Param("secretKey")String secretKey);
	
	/**
	 * 修改记录
	 * @param tokenCompany
	 * @return
	 */
	int update(TokenCompany tokenCompany);
}
