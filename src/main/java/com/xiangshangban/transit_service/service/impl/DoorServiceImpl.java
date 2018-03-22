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
import com.xiangshangban.transit_service.bean.ReturnData;
import com.xiangshangban.transit_service.exception.MyException;
import com.xiangshangban.transit_service.service.DoorService;
import com.xiangshangban.transit_service.utils.HttpClientUtil;
import com.xiangshangban.transit_service.utils.PropertiesUtils;

@Service
@Transactional
public class DoorServiceImpl implements DoorService{

	/**
	 * 添加门和绑定设备
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> addDoor(Object data,Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController").trim()
					+PropertiesUtils.pathUrl("addDoor");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));*/
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 门禁权限下发
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData handOutEmployeePermission(Object data, Map<String, String> headers) {
		ReturnData rd = new ReturnData();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")+PropertiesUtils.pathUrl("EmployeeController")+PropertiesUtils.pathUrl("handOutEmployeePermission");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		rd.setReturnCode(jobj.getString("returnCode"));
		rd.setMessage(jobj.getString("message"));*/
		rd = JSON.parseObject(json, ReturnData.class);
		return rd;
	}
	/**
	 * 设备已有权限查询
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public String getRelateEmpPermissionInfo(Object data, Map<String, String> headers) {
		ReturnData rd = new ReturnData();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
			+PropertiesUtils.pathUrl("getRelateEmpPermissionInfo");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		return json;
	}
	/**
	 * 门已有权限删除
	 */
	@Override
	public Map<String, Object> deleteEmployeeInformationDev(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")+PropertiesUtils.pathUrl("EmployeeController")+PropertiesUtils.pathUrl("deleteEmployeeInformationDev");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));*/
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 所有设备信息查询
	 */
	@Override
	public Map<String, Object> findDeviceInformation(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("DeviceController")
					+PropertiesUtils.pathUrl("findDeviceInformation");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));*/
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 更改门与设备的绑定关系
	 */
	@Override
	public String updateDoor(Object data, Map<String, String> headers) {
		/*Map<String, Object> result = new HashMap<String ,Object>();*/
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
					+PropertiesUtils.pathUrl("updateDoor");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));*/
		return json;
	}
	/**
	 * 门禁记录查询
	 */
	@Override
	public Map<String, Object> getInOutRecord(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
					+PropertiesUtils.pathUrl("getInOutRecord");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 门禁参数设置
	 */
	@Override
	public Map<String, Object> handOutDoorFeaturesSetup(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
					+PropertiesUtils.pathUrl("handOutDoorFeaturesSetup");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		/*JSONObject jobj = JSON.parseObject(json);
		result.put("returnCode", jobj.getString("returnCode"));
		result.put("message", jobj.getString("message"));*/
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 门禁参数查询
	 */
	@Override
	public Map<String, Object> getHighSettingForFunction(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
					+PropertiesUtils.pathUrl("getHighSettingForFunction");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	/**
	 * 删除门
	 */
	@Override
	public Map<String, Object> delDoor(Object data, Map<String, String> headers) {
		Map<String, Object> result = new HashMap<String ,Object>();
		String url;
		try {
			url = PropertiesUtils.pathUrl("device")
					+PropertiesUtils.pathUrl("EntranceGuardController")
					+PropertiesUtils.pathUrl("delDoor");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("接口地址获取出错");
		}
		String json = HttpClientUtil.sendRequet(url, data, ContentType.APPLICATION_JSON, headers);
		result = JSON.parseObject(json, Map.class);
		return result;
	}
	
}
