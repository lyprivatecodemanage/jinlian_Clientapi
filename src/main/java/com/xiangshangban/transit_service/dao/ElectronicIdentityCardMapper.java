package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.ElectronicIdentityCard;

@Mapper
public interface ElectronicIdentityCardMapper {
	ElectronicIdentityCard selectElectronicIdentityCard(@Param("companyId")String companyId,@Param("employeeId")String employeeId);
	
	Integer insertSelective(ElectronicIdentityCard electronicIdentityCard);
	
	Integer updateElectronicIdentityCard(ElectronicIdentityCard electronicIdentityCard);
}
