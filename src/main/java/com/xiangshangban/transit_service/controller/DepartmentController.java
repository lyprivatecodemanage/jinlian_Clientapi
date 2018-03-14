package com.xiangshangban.transit_service.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.util.EmptyUtil;
import com.xiangshangban.transit_service.utils.HttpClientUtil;
import com.xiangshangban.transit_service.utils.PropertiesUtils;
@RestController
@RequestMapping("/DepartmentController")
public class DepartmentController {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	TokenCompanyService tokenCompanyService;
	
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
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		
		boolean b = CompareTime(token);
		
		if(!b){
			result.put("returnCode", "3014");
			result.put("message", "token验证失败");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("departmentName",departmentName);
			if("".equals(departmentParentId)||departmentParentId==null){
				dateMap.put("departmentParentId",tc.getCompanyId());
			}else{
				dateMap.put("departmentParentId",departmentParentId);
			}
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/DepartmentController/insertDepartment",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json ;
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		} catch (Exception e){
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
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
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		
		boolean b = CompareTime(token);
		
		if(!b){
			result.put("returnCode", "3014");
			result.put("message", "token验证失败");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("departmentName",departmentName);
			dateMap.put("departmentDescribe", departmentParentId);
			dateMap.put("employeeId", departmentParentId);
			dateMap.put("departmentId", departmentParentId);
			dateMap.put("departmentParentId", departmentParentId);
			dateMap.put("departmentDescribe", departmentDescribe);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/DepartmentController/updateByDepartment",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json ;
			
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
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

		boolean b = CompareTime(token);
		
		if(!b){
			result.put("returnCode", "3014");
			result.put("message", "token验证失败");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("departmentId",departmentId);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/DepartmentController/deleteByDepartment",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json ;
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
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
		boolean b = CompareTime(token);
		
		if(!b){
			result.put("returnCode", "3014");
			result.put("message", "token验证失败");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("departmentId",departmentId);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/DepartmentController/findDepartmentById",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json ;
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}
	
	public boolean CompareTime(String token){
		//验证token是否存在
		TokenCompany tokenCompany = tokenCompanyService.selectByToken(token);
				
		if(tokenCompany!=null){
					
			try {
				//获取token过期时间 转成date类型
				Date date = sdf.parse(tokenCompany.getExpirationTime());
				//当前时间
				Date newdate = sdf.parse(sdf.format(new Date()));
						
				if(date.getTime()>=newdate.getTime()){
					return true;
				}
						
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
