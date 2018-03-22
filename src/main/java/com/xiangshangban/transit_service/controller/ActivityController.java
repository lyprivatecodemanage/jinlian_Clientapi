package com.xiangshangban.transit_service.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.service.BackgroundImageTemplateService;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.util.EmptyUtil;

@RestController
@RequestMapping("/ActivityController")
public class ActivityController {
	private SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat hourSdf = new SimpleDateFormat("mm:ss");
	
	@Autowired
	BackgroundImageTemplateService backgroundImageTemplateService;
	
	@Autowired
	TokenCompanyService tokenCompanyService;
	
	@Autowired
	UusersRolesService uusersRolesService;
	
	@Autowired
	CompanyService companyService;
	
	@Value("${ossUrl}")
	String url;
	/**
	 * 4.1新增主题
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addTask",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addTask(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token"); 		
		String templateLevel = jobj.getString("templateLevel");//优先级  0：低， 1：高
		String imageKey = jobj.getString("imageKey");//图片唯一名称，通过图片上传接口获得;可传多个图片key,以逗号分隔
		String broadStartDate = jobj.getString("broadStartDate");//开始日期
		String broadEndDate = jobj.getString("broadEndDate");//结束日期
		String broadStartTime = jobj.getString("broadStartTime");//开始时间
		String broadEndTime = jobj.getString("broadEndTime");//结束时间
		String roastingTime = jobj.getString("roastingTime");//轮播时间间隔 ，单位为秒
		String deviceId = jobj.getString("deviceId");//设备ID
		params.add(token);
		params.add(templateLevel);
		params.add(imageKey);
		params.add(broadStartDate);
		params.add(broadEndDate);
		params.add(broadStartTime);
		params.add(broadEndTime);
		params.add(roastingTime);
		params.add(deviceId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		try {
			broadStartDate = dateSdf.format(dateSdf.parse(broadStartDate));
			broadEndDate = dateSdf.format(dateSdf.parse(broadEndDate));
			broadStartTime = hourSdf.format(hourSdf.parse(broadStartTime));
			broadEndTime = hourSdf.format(hourSdf.parse(broadEndTime));
		} catch (ParseException e) {
			e.printStackTrace();
			result.put("message", "日期时间格式错误");
			result.put("returnCode", "3014");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "3014");
			return result;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			String companyNo = companyService.selectByPrimaryKey(tc.getCompanyId()).getCompany_no();//公司编号，此编号实际应用时，应根据token去查询
			
			//该公司完整oss路径
			String sendUrl = url+companyNo+"/deviceItme";
			
			result = backgroundImageTemplateService.addTask(templateLevel, imageKey, broadStartDate, broadEndDate, broadStartTime, broadEndTime, roastingTime, deviceId, sendUrl,tc.getCompanyId());
			
			return result;
		} catch (Exception e){
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}
	/**
	 * 4.2修改主题
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateTask",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> updateTask(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token"); 		
		String templateId = jobj.getString("templateId");//主题ID
		String templateLevel = jobj.getString("templateLevel");//优先级  0：低， 1：高
		String imageKey = jobj.getString("imageKey");//图片唯一名称，通过图片上传接口获得;可传多个图片key,以逗号分隔
		String broadStartDate = jobj.getString("broadStartDate");//开始日期
		String broadEndDate = jobj.getString("broadEndDate");//结束日期
		String broadStartTime = jobj.getString("broadStartTime");//开始时间
		String broadEndTime = jobj.getString("broadEndTime");//结束时间
		String roastingTime = jobj.getString("roastingTime");//轮播时间间隔 ，单位为秒
		params.add(token);
		params.add(templateId);
		params.add(templateLevel);
		params.add(imageKey);
		params.add(broadStartDate);
		params.add(broadEndDate);
		params.add(broadStartTime);
		params.add(broadEndTime);
		params.add(roastingTime);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		try {
			broadStartDate = dateSdf.format(dateSdf.parse(broadStartDate));
			broadEndDate = dateSdf.format(dateSdf.parse(broadEndDate));
			broadStartTime = hourSdf.format(hourSdf.parse(broadStartTime));
			broadEndTime = hourSdf.format(hourSdf.parse(broadEndTime));
		} catch (ParseException e) {
			e.printStackTrace();
			result.put("message", "日期时间格式错误");
			result.put("returnCode", "3014");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "3014");
			return result;
		}
		
		try {
			result = backgroundImageTemplateService.updateTask(templateId,templateLevel, imageKey, broadStartDate, broadEndDate, broadStartTime, broadEndTime, roastingTime);
			
			return result;
		} catch (Exception e){
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}
	/**
	 * 4.3 删除主题
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteTask",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> deleteTask(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<String> params = new ArrayList<String>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String token = request.getHeader("token"); 		
		String templateId = jobj.getString("templateId");//主题ID
		params.add(token);
		params.add(templateId);
		boolean isEmpty = EmptyUtil.isEmpty(params);
		if(!isEmpty){
			result.put("message", "必传参数为空");
			result.put("returnCode", "3006");
			return result;
		}
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "3014");
			return result;
		}
		
		try {
			result = backgroundImageTemplateService.deleteTask(templateId);
			
			return result;
		} catch (Exception e){
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}
	
	/**
	 * 根据ID查询任务
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getTaskById",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getTaskById(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String templateId = obj.getString("templateId");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(templateId)){
			result.put("returnCode","3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			result.put("message", "token验证失败");
			result.put("returnCode", "3014");
			return result;
		}
		
		try {
			result = backgroundImageTemplateService.getTaskById(templateId);
			
			return result;
		} catch (Exception e){
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}
	
	/**
	 * 查询所有任务
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getTaskAll",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getTaskAll(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		
		if(StringUtils.isEmpty(token)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		
		
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			map.put("message", "token验证失败");
			map.put("returnCode", "3014");
			return map;
		}
		
		try {
			TokenCompany tc = tokenCompanyService.selectByToken(token);
			
			map = backgroundImageTemplateService.getTaskAll(tc.getCompanyId());
			
			return map;
		} catch (Exception e){
			e.printStackTrace();
			map.put("returnCode", "3001");
			map.put("message", "服务器错误");
			return map;
		}
	}
	
	/*************************************************重复部分**************************************************************/
	
	@RequestMapping(value="/addVisitCardEmp",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addVisitCardEmp(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String employeeId = obj.getString("employeeId");
		String validBeginDate = obj.getString("validBeginTime");
		String validEndDate = obj.getString("validEndTime");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(employeeId)||StringUtils.isEmpty(validBeginDate)||StringUtils.isEmpty(validEndDate)){
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
		String validBeginDate = obj.getString("validBeginTime");
		String validEndDate = obj.getString("validEndTime");
		String doorList = obj.getString("doorList");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(validBeginDate)
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
