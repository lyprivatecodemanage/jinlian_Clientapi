package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.service.VisitService;

@RestController
@RequestMapping("/VisitController")
public class VisitController {
	@Autowired
	private TokenCompanyService tokenCompanyService;
	@Autowired
	private VisitService visitService;
	/**
	 * 申请正式人员的电子身份证
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addVisitCardEmp",produces="Application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addVisitCardEmp(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(jsonString);
		String employeeId = obj.getString("employeeId");
		String validBeginTime = obj.getString("validBeginTime");
		String validEndTime = obj.getString("validEndTime");
		
		if(StringUtils.isEmpty(token)
				||StringUtils.isEmpty(employeeId)
				||StringUtils.isEmpty(validBeginTime)
				||StringUtils.isEmpty(validEndTime)
				){
			result.put("returnCode","3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		//判断token是否有有效
		boolean compareTime = tokenCompanyService.CompareTime(token);
		if(!compareTime){
			result.put("returnCode", 3014);
			result.put("message", "token验证失败");
			return result;
		}
		//获取公司id
		TokenCompany tokenCompany = tokenCompanyService.selectByToken(token);
		String companyId = tokenCompany.getCompanyId();
		result = visitService.addVisitCardEmp(employeeId,companyId,validBeginTime,validEndTime);
		return result;
	}
	/**
	 * 申请临时人员的电子通行证
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addVisitCard",produces="Application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addVisitCard(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(jsonString);
		String employeeName = obj.getString("employeeName");//临时人员姓名 （默认“访客”）
		String employeeCompany = obj.getString("employeeCompany");//临时人员所在公司
		String validBeginTime = obj.getString("validBeginTime");//有效期：所申请的电子通行证的有效开始时间。（必传）
		String validEndTime = obj.getString("validEndTime");//有效期：所申请的电子通行证的有效结束时间 。（必传）
		JSONArray doorListArray = obj.getJSONArray("doorList");
		List<String> doorList = new ArrayList<String>();
		if(StringUtils.isEmpty(token)
				||StringUtils.isEmpty(validBeginTime)
				||StringUtils.isEmpty(validEndTime)){
			result.put("returnCode","3006");
			result.put("message","必传参数为空");
			return result;
		}
		if(StringUtils.isEmpty(employeeName)){
			employeeName="访客";
		}else{
			if(StringUtils.isEmpty(employeeCompany)){
				result.put("returnCode","3006");
				result.put("message","必传参数为空");
				return result;
			}
		}
		if(doorListArray==null||doorListArray.size()<1){
			result.put("returnCode","3006");
			result.put("message","必传参数为空");
			return result;
		}else{
			
			for(int i=0;i<doorListArray.size();i++){
				if(StringUtils.isEmpty(doorListArray.getString(i))){
					result.put("returnCode","3006");
					result.put("message","必传参数为空");
					return result;
				}
				String doorId = JSON.parseObject(doorListArray.getString(i)).getString("doorId");
				doorList.add(doorId);
			}
		}
		//判断token是否有有效
		boolean compareTime = tokenCompanyService.CompareTime(token);
		if(!compareTime){
			result.put("returnCode", 3014);
			result.put("message", "token验证失败");
			return result;
		}
		//获取公司id
		TokenCompany tokenCompany = tokenCompanyService.selectByToken(token);
		String companyId = tokenCompany.getCompanyId();
		result = visitService.addVisitCard(companyId,employeeName,employeeCompany,validBeginTime,validEndTime,doorList);
		return result;
	}
	
}
