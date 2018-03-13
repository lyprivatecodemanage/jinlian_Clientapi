package com.xiangshangban.transit_service.service;

import java.util.Map;

import com.xiangshangban.transit_service.bean.ReturnData;

public interface DoorService {
	/**
	 * 添加门和绑定设备
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String ,Object> addDoor(Object data,Map<String, String> headers);
	/**
	 * 门禁权限下发
	 * @param data
	 * @param headers
	 * @return
	 */
	ReturnData handOutEmployeePermission(Object data,Map<String, String> headers);
	/**
	 * 设备已有权限查询
	 * @param data
	 * @param headers
	 * @return
	 */
	String getRelateEmpPermissionInfo(Object data,Map<String, String> headers);
	/**
	 * 门已有权限删除
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> deleteEmployeeInformationDev(Object data,Map<String, String> headers);
	/**
	 * 所有设备信息查询
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> findDeviceInformation(Object data,Map<String, String> headers);
	/**
	 * 更改门与设备的绑定关系
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> updateDoor(Object data,Map<String, String> headers);
	/**
	 * 门禁记录查询
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> getInOutRecord(Object data,Map<String, String> headers);
	/**
	 * 门禁参数设置
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> handOutDoorFeaturesSetup(Object data,Map<String, String> headers);
	/**
	 * 门禁参数查询
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> getHighSettingForFunction(Object data,Map<String, String> headers);
	/**
	 * 删除门
	 * @param data
	 * @param headers
	 * @return
	 */
	Map<String,Object> delDoor(Object data,Map<String, String> headers);
}
