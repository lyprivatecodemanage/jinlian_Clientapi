package com.xiangshangban.transit_service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.Employee;
import com.xiangshangban.transit_service.bean.Post;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.UusersRolesKey;
import com.xiangshangban.transit_service.service.EmployeeService;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.util.EmptyUtil;
import com.xiangshangban.transit_service.utils.HttpClientUtil;
import com.xiangshangban.transit_service.utils.PropertiesUtils;

@RestController
@RequestMapping("/EmployeeController")
public class EmployeeController {
	
	@Autowired
	TokenCompanyService tokenCompanyService;
	
	@Autowired
	UusersRolesService uusersRolesService;
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 添加人员
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertEmployee",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> insertEmployee(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String employeeName = jobj.getString("employeeName");//姓名
		String loginName = jobj.getString("loginName");//员工登录手机号
		String departmentId = jobj.getString("departmentId");//所属部门id，没有传参时，默认在全公司下
		params.add(token);
		params.add(employeeName);
		params.add(loginName);
		params.add(departmentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		if(!Pattern.matches("^1[34578]\\d{9}$", loginName)){
			result.put("message", "登录名格式必须为11位手机号");
			result.put("returnCode", "4102");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			// 查看当前管理员及历史管理员
			UusersRolesKey list = uusersRolesService.SelectAdministrator(tc.getCompanyId(),new Uroles().admin_role);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			hmap.put("accessUserId",list.getUserId());
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("employeeName",employeeName);
			dateMap.put("loginName",loginName);
			dateMap.put("departmentId",departmentId);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/EmployeeController/insertEmployee",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json;
			
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
	 * 查询公司所有人员信息
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getEmployeeListAll",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getEmployeeListAll(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String employeeName = jobj.getString("employeeName");//员工姓名
		String employeeSex = jobj.getString("employeeSex");//员工性别
		String departmentName = jobj.getString("departmentName");//部门名称（模糊查询）  
		String postName = jobj.getString("postName");//岗位名称
		String employeeStatus = jobj.getString("employeeStatus");//员工状态
		String pageNum = StringUtils.isEmpty(jobj.getString("pageNum"))?"1":jobj.getString("pageNum");//整数，页码，默认为1
		String pageRecordNum = StringUtils.isEmpty(jobj.getString("pageRecordNum"))?"10":jobj.getString("pageRecordNum");//整数，每页记录数，默认为10
		String departmentId = jobj.getString("departmentId");//部门ID （该字段为确定查询，非模糊查询）
		params.add(token);
		params.add(employeeStatus);
		params.add(pageNum);
		params.add(pageRecordNum);
		params.add(departmentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			UusersRolesKey accessUserId = uusersRolesService.SelectAdministrator(tc.getCompanyId(),new Uroles().admin_role);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			hmap.put("accessUserId",accessUserId.getUserId());
			hmap.put("type","0");
			
			Map<String,String> dateMap = new HashMap<>();
			dateMap.put("employeeName",employeeName);
			dateMap.put("employeeSex",employeeSex);
			dateMap.put("departmentName",departmentName);
			dateMap.put("postName",postName);
			dateMap.put("employeeStatus",employeeStatus);
			dateMap.put("pageNum",pageNum);
			dateMap.put("pageRecordNum",pageRecordNum);
			dateMap.put("departmentId",departmentId);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/EmployeeController/selectByAllFnyeEmployee",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json;
			
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
	 * 人员信息修改
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateEmployeeInformation",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> updateEmployeeInformation(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String employeeId = jobj.getString("employeeId");//（必传）	员工ID
		String employeeName = jobj.getString("employeeName");//（必传）	员工姓名
		String employeeSex = jobj.getString("employeeSex");//（必传）	性别
		String loginName = jobj.getString("loginName");//（必传）	登录名
		String employeePhone = jobj.getString("employeePhone");//联系方式1
		String employeeTwophone = jobj.getString("employeeTwophone");//联系方式2
		String postId = jobj.getString("postId");//	主岗位id
		String workAddress = jobj.getString("workAddress");//工作地
		String marriageStatus = jobj.getString("marriageStatus");//婚姻状况
		String seniorit = jobj.getString("seniorit");//（必传）	工龄
		String departmentId = jobj.getString("departmentId");//（必传）	部门id
		String employeeNo = jobj.getString("employeeNo");//员工编号
		String directPersonId = jobj.getString("directPersonId");//直接汇报人id
		String entryTime = jobj.getString("entryTime");//（必传）	入职时间
		String probationaryExpired = jobj.getString("probationaryExpired");//（必传）	试用到期日
		String transferJobCause = jobj.getString("transferJobCause");//调动原因
		List<Post> postList = new ArrayList<>();
		Post p1 = new Post();
		p1.setDepartmentId(departmentId);
		p1.setPostGrades("1");
		postList.add(p1);
		Post p2 = new Post();
		p2.setPostGrades("0");
		postList.add(p2);
		Post p3 = new Post();
		p3.setPostGrades("0");
		postList.add(p3);
		params.add(token);
		params.add(employeeId);
		params.add(employeeName);
		params.add(loginName);
		params.add(departmentId);
		params.add(seniorit);
		params.add(entryTime);
		params.add(probationaryExpired);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			UusersRolesKey accessUserId = uusersRolesService.SelectAdministrator(tc.getCompanyId(),new Uroles().admin_role);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			hmap.put("accessUserId",accessUserId.getUserId());
			hmap.put("type","0");
			
			Map<String,Object> dateMap = new HashMap<>();
			dateMap.put("employeeId",employeeId);
			dateMap.put("employeeName",employeeName);
			dateMap.put("employeeSex",employeeSex);
			dateMap.put("loginName",loginName);
			dateMap.put("employeePhone",employeePhone);
			dateMap.put("employeeTwophone",employeeTwophone);
			dateMap.put("postId",postId);
			dateMap.put("workAddress",workAddress);
			dateMap.put("marriageStatus",marriageStatus);
			dateMap.put("seniorit",seniorit);
			dateMap.put("departmentId",departmentId);
			dateMap.put("employeeNo",employeeNo);
			dateMap.put("directPersonId",directPersonId);
			dateMap.put("entryTime",entryTime);
			dateMap.put("probationaryExpired",probationaryExpired);
			dateMap.put("transferJobCause",transferJobCause);
			dateMap.put("postList",postList);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/EmployeeController/updateEmployeeInformation",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json;
			
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
	 * 人员信息删除（离职）
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/batchUpdateStatus",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> batchUpdateStatus(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		String token = request.getHeader("token");
		JSONArray jsonArray = JSON.parseObject(jsonString).getJSONArray("deleteData");
		params.add(token);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		if(jsonArray.size()==0){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			UusersRolesKey accessUserId = uusersRolesService.SelectAdministrator(tc.getCompanyId(),new Uroles().admin_role);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			hmap.put("accessUserId",accessUserId.getUserId());
			hmap.put("type","0");
			
			Map<String,Object> dateMap = new HashMap<>();
			dateMap.put("deleteData",jsonArray);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/EmployeeController/deleteActivity",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json;
			
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
	 * 查询某个员工的详细信息
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> search(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token");
		String employeeId = jobj.getString("employeeId");//人员ID（必传）   
		params.add(token);
		params.add(employeeId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			UusersRolesKey accessUserId = uusersRolesService.SelectAdministrator(tc.getCompanyId(),new Uroles().admin_role);
			
			Map<String,String> hmap = new HashMap<>();
			hmap.put("companyId", tc.getCompanyId());
			hmap.put("accessUserId",accessUserId.getUserId());
			hmap.put("type","0");
			
			Map<String,Object> dateMap = new HashMap<>();
			dateMap.put("employeeId",employeeId);
			
			String url = PropertiesUtils.pathUrl("organization");
			
			String code = HttpClientUtil.sendRequet(url+"/EmployeeController/search",dateMap,ContentType.APPLICATION_JSON, hmap);
			
			JSONObject json = JSONObject.parseObject(code);
			
			result = json;
			
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
}
