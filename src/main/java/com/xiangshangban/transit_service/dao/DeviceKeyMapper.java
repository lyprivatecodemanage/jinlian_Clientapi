package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.DeviceKey;

@Mapper
public interface DeviceKeyMapper {
	
	DeviceKey selectDeviceKey();
}
