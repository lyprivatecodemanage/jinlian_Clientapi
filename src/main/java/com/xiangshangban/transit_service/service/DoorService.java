package com.xiangshangban.transit_service.service;

import java.util.Map;

import com.xiangshangban.transit_service.bean.ReturnData;

public interface DoorService {
	
	Map<String ,Object> addDoor(Object data,Map<String, String> headers);
	
	ReturnData handOutEmployeePermission(Object data,Map<String, String> headers);
}
