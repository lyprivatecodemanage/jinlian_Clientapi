package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("ActivityController")
public class ActivityController {

	@RequestMapping(value="/getTaskById",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getTaskById(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String templateId = obj.getString("templateId");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(templateId)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("templateId", "655786545");
		resultmap.put("templateLevel ", "0");
		resultmap.put("imagePath", "http://www.oa.smy.com/123.png");
		resultmap.put("imageKey", "kfjkasjkasj.png");
		resultmap.put("broadStartDate", "2017-10-27");
		resultmap.put("broadEndDate", "2017-10-27");
		resultmap.put("broadStartTime", "09:00");
		resultmap.put("broadEndTime", "18:00");
		resultmap.put("roastingTime", "3");
		resultmap.put("doorId", "123");
		
		map.put("data",JSON.toJSON(resultmap));
		map.put("returnCode","3000");
		map.put("message","操作成功");
		return map;
	}
	
	@RequestMapping(value="/getTaskAll",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getTaskAll(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String templateId = obj.getString("templateId");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(templateId)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("templateId", "655786545");
		resultmap.put("templateLevel ", "0");
		resultmap.put("imagePath", "http://www.oa.smy.com/123.png");
		resultmap.put("imageKey", "kfjkasjkasj.png");
		resultmap.put("broadStartDate", "2017-10-27");
		resultmap.put("broadEndDate", "2017-10-27");
		resultmap.put("broadStartTime", "09:00");
		resultmap.put("broadEndTime", "18:00");
		resultmap.put("roastingTime", "3");
		resultmap.put("doorId", "123");
		
		List<Map<String,String>> list = new ArrayList<>();
		list.add( resultmap);
		
		map.put("data",JSON.toJSON(list));
		map.put("returnCode","3000");
		map.put("message","操作成功");
		return map;
	}
	
	@RequestMapping(value="/addVisitCardEmp",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addVisitCardEmp(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String employeeId = obj.getString("employeeId");
		String doorId  = obj.getString("doorId");
		String validBeginDate = obj.getString("validBeginDate");
		String validEndDate = obj.getString("validEndDate");
		String doorList = obj.getString("doorList");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(employeeId)||StringUtils.isEmpty(doorId)||StringUtils.isEmpty(validBeginDate)||StringUtils.isEmpty(validEndDate)
				||StringUtils.isEmpty(doorList)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		map.put("visitId","8793493");
		map.put("visitKey","87934fdkfjkdfjdlojglskgldfkgl;dkgdkfg;fkdg;lskgkgfkdjgkdjfgdlgjdjgkdlfjgdkg93");
		map.put("visitFormat","QR Code");
		map.put("returnCode","3000");
		map.put("message","操作成功");
		return map;
	}
	
	@RequestMapping(value="/addVisitCard",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addVisitCard(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String employeeName = obj.getString("employeeName");
		String employeeCompany = obj.getString("employeeCompany");
		String doorId = obj.getString("doorId");
		String validBeginDate = obj.getString("validBeginDate");
		String validEndDate = obj.getString("validEndDate");
		String doorList = obj.getString("doorList");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(doorId)||StringUtils.isEmpty(validBeginDate)
				||StringUtils.isEmpty(validEndDate)||StringUtils.isEmpty(doorList)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		map.put("visitId","8793493");
		map.put("visitKey","87934fdkfjkdfjdlojglskgldfkgl;dkgdkfg;fkdg;lskgkgfkdjgkdjfgdlgjdjgkdlfjgdkg93");
		map.put("visitFormat","QR Code");
		map.put("returnCode","3000");
		map.put("message","操作成功");
		return map;
	}
}
