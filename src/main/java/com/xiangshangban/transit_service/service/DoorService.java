package com.xiangshangban.transit_service.service;

import java.util.Map;

public interface DoorService {
	
	Map<String ,Object> addDoor(Object data,Map<String, String> headers);
	
	Map<String ,Object> handOutEmployeePermission(Object data,Map<String, String> headers);
}
