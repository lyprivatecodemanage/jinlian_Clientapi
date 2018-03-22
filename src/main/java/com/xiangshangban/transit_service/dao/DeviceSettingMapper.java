package com.xiangshangban.transit_service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.DeviceSetting;

@Mapper
public interface DeviceSettingMapper {
    int deleteByPrimaryKey(String deviceId);

    int insert(DeviceSetting record);

    int insertSelective(DeviceSetting record);

    DeviceSetting selectByPrimaryKey(String deviceId);

    int updateByPrimaryKeySelective(DeviceSetting record);

    int updateByPrimaryKey(DeviceSetting record);

    /**
     * 非自动生成
     */
    List<Map<String, Object>> selectDeviceSettingByCondition(@Param("deviceId") String deviceId,
                                                             @Param("activeStatus") String activeStatus,
                                                             @Param("companyName") String companyName);
}