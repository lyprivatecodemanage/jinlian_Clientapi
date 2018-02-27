package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.Employee;
import com.xiangshangban.transit_service.bean.Post;
import com.xiangshangban.transit_service.util.EmptyUtil;

@RestController
@RequestMapping("/EmployeeController")
public class EmployeeController {
	/**
	 * 添加人员
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertEmployee",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> insertEmployee(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
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
		if(TokenController.Token.equals(token)){
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			result.put("employeeId", "788aef438888");//添加成功时返回员工ID
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
		String companyName = jobj.getString("companyName");//当前组织机构（公司名称）
		String departmentName = jobj.getString("departmentName");//部门名称（模糊查询）
		String departmentPrincipal = jobj.getString("departmentId");//部门负责人（模糊查询）
		String pageNum = jobj.getString("pageNum");//整数，页码，默认为1
		String pageRecordNum = jobj.getString("pageRecordNum");//整数，每页记录数，默认为10
		String departmentId = jobj.getString("departmentId");//部门ID （该字段为确定查询，非模糊查询）
		params.add(token);
		params.add(companyName);
		params.add(departmentName);
		params.add(departmentPrincipal);
		params.add(pageNum);
		params.add(pageRecordNum);
		params.add(departmentId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		if(TokenController.Token.equals(token)){
			List<Employee> data = new ArrayList<Employee>();
			List<Post> postList = new ArrayList<Post>();
			Employee emp = new Employee();
			Post post = new Post();
			postList.add(post);
			emp.setPostList(postList);
			emp.setEmployeeId("5A8B2CC5374043C18AB69A14B9DD0040");
			emp.setEmployeeName("呵呵哒");
			emp.setLoginName("13655646532");
			emp.setEmployeeSex("");
			emp.setDepartmentId("F74725FA3A85494784069D2E007FD280");
			emp.setEmployeePhone("");
			emp.setEntryTime("");
			emp.setEmployeeStatus("在职");
			emp.setDepartmentName("我就是要看看到底能有多长");
			emp.setIsActive("1");
			data.add(emp);
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			result.put("totalPages", "19");
			result.put("pagecountNum", "2");
			result.put("data", data);
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
		String postId = jobj.getString("postId");//（必传）	主岗位id
		String workAddress = jobj.getString("workAddress");//（必传）	工作地
		String marriageStatus = jobj.getString("marriageStatus");//婚姻状况
		String seniorit = jobj.getString("seniorit");//（必传）	工龄
		String departmentId = jobj.getString("departmentId");//（必传）	部门id
		String employeeNo = jobj.getString("employeeNo");//员工编号
		String directPersonId = jobj.getString("directPersonId");//直接汇报人id
		String entryTime = jobj.getString("entryTime");//（必传）	入职时间
		String probationaryExpired = jobj.getString("probationaryExpired");//（必传）	试用到期日
		String transferJobCause = jobj.getString("transferJobCause");//调动原因
		List<Post> postList = JSON.parseArray(jobj.getString("postList"), Post.class);//副岗位;数据类型:数组
		params.add(token);
		params.add(employeeId);
		params.add(employeeName);
		params.add(employeeSex);
		params.add(loginName);
		params.add(postId);
		params.add(workAddress);
		params.add(seniorit);
		params.add(departmentId);
		params.add(entryTime);
		params.add(probationaryExpired);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		if(TokenController.Token.equals(token)){
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
		if(TokenController.Token.equals(token)){
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
		if(TokenController.Token.equals(token)){
			Employee employee = new Employee();
			List<Post> postList = new ArrayList<Post>();
			Post post1 = new Post();
			post1.setPostName("");// 副岗位名称
			Post post2 = new Post();
			post2.setPostName("");// 副岗位名称
			postList.add(post1);
			postList.add(post1);
			employee.setPostList(postList);
			employee.setEmployeeId("");//员工id
			employee.setEmployeeName("");//员工姓名
			employee.setEmployeeSex("");//员工性别
			employee.setLoginName("");//登录名
			employee.setEmployeePhone("");//联系方式1
			employee.setEmployeeTwophone("");//联系方式2
			employee.setPostName("");//主岗位名称
			employee.setDepartmentName("");//部门名称
			employee.setEmployeeNo("");//员工编号
			employee.setDirectPersonName("");//直接汇报人姓名
			employee.setEntryTime("");//入职时间
			employee.setProbationaryExpired("");//转正时间
			employee.setWorkAddress("");//工作地
			employee.setMarriageStatus("");//婚姻状况(0:未婚,1:已婚)
			employee.setSeniority("");//工龄
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			result.put("data", employee);
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
	}
}
