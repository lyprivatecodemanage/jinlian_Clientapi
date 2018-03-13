package com.xiangshangban.transit_service.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.exception.MyException;
import com.xiangshangban.transit_service.service.DoorService;
import com.xiangshangban.transit_service.utils.HttpClientUtil;
import com.xiangshangban.transit_service.utils.PropertiesUtils;

@Service
@Transactional
public class DoorServiceImpl implements DoorService{

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> addDoor(Object data,Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")+PropertiesUtils.pathUrl("door")+PropertiesUtils.pathUrl("addDoor");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> handOutEmployeePermission(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")+PropertiesUtils.pathUrl("door")+PropertiesUtils.pathUrl("handOutEmployeePermission");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));
		return result;
	}
	
}
