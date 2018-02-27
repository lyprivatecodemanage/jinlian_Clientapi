package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.Department;
import com.xiangshangban.transit_service.util.EmptyUtil;
@RestController
@RequestMapping("/DepartmentController")
public class DepartmentController {
	
	/**
	 * 添加部门
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertDepartment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> insertDepartment(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String departmentName = jobj.getString("departmentName");
		String departmentParentId = jobj.getString("departmentParentId");
		params.add(token);
		params.add(departmentName);
		params.add(departmentParentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		if(TokenController.Token.equals(token)){
			result.put("returnCode", "3000");
			result.put("message", "数据请求成功");
			result.put("departmentId", "123abc478723");
			return result;
		}else{
			result.put("returnCode", "9999");
			result.put("message", "token验证失败");
			return result;
		}
	}
	/**
	 * 修改部门信息
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateByDepartment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> updateByDepartment(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String departmentName = jobj.getString("departmentName");//部门名称
		String departmentParentId = jobj.getString("departmentParentId");//上级部门ID
		String employeeId = jobj.getString("employeeId");//部门负责人ID
		String departmentId = jobj.getString("departmentId");//部门ID
		String departmentDescribe = jobj.getString("departmentDescribe");//部门描述
		params.add(token);
		params.add(departmentName);
		params.add(departmentParentId);
		params.add(employeeId);
		params.add(departmentId);
		params.add(departmentDescribe);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		if(TokenController.Token.equals(token)){
			result.put("returnCode", "3000");
			result.put("message", "数据请求成功");
			return result;
		}else{
			result.put("returnCode", "9999");
			result.put("message", "token验证失败");
			return result;
		}
	}
	/**
	 * 删除部门
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteByDepartment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> deleteByDepartment(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String departmentId = jobj.getString("departmentId");//部门ID
		params.add(token);
		params.add(departmentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		if(TokenController.Token.equals(token)){
			result.put("returnCode", "3000");
			result.put("message", "数据请求成功");
			return result;
		}else{
			result.put("returnCode", "9999");
			result.put("message", "token验证失败");
			return result;
		}
	}
	/**
	 * 查询部门信息
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findDepartmentById",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> findDepartmentById(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String departmentId = jobj.getString("departmentId");//部门ID
		params.add(token);
		params.add(departmentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		if(TokenController.Token.equals(token)){
			List<Department> childrenList = new ArrayList<Department>();
			Department childrenDepartment = new Department("204E097D2CF24BE68D0349148A82B5B7",
					"01", "子部门", "hhahaha", "", null, null, 
					"104E097D2CF24BE68D0349148A82B5B7", "腾讯", "腾讯", "A4F5A833EE674AE6B85F5582CCB3550D", 
					null, null, null);
			childrenList.add(childrenDepartment);
			Department department = new Department("104E097D2CF24BE68D0349148A82B5B7",
					"01", "部门", "hhahaha", "", null, null, 
					"0", "父部门名称", "腾讯", "A4F5A833EE674AE6B85F5582CCB3550D", 
					null, null, childrenList);
			
			result.put("returnCode", "3000");
			result.put("message", "数据请求成功");
			result.put("data", department);
			return result;
		}else{
			result.put("returnCode", "9999");
			result.put("message", "token验证失败");
			return result;
		}
	}
}
