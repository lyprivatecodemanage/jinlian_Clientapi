package com.xiangshangban.transit_service.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.xiangshangban.transit_service.util.EmptyUtil;

@RestController
@RequestMapping("ActivityController")
public class ActivityController {
	private SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat hourSdf = new SimpleDateFormat("mm:ss");
	
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
		String doorId = jobj.getString("roastingTime");//门ID
		params.add(token);
		params.add(templateLevel);
		params.add(imageKey);
		params.add(broadStartDate);
		params.add(broadEndDate);
		params.add(broadStartTime);
		params.add(broadEndTime);
		params.add(roastingTime);
		params.add(doorId);
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
			result.put("returnCode", "9999");
			return result;
		}
		if(RegisterController.Token.equals(token)){
			result.put("message", "操作成功");
			result.put("returnCode", "3000");
			result.put("templateId", "12358555");//添加成功时返回主题ID
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
			result.put("returnCode", "9999");
			return result;
		}
		if(RegisterController.Token.equals(token)){
			result.put("message", "操作成功");
			result.put("returnCode", "3000");
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
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
		if(RegisterController.Token.equals(token)){
			result.put("message", "操作成功");
			result.put("returnCode", "3000");
			return result;
		}else{
			result.put("message", "token验证失败");
			result.put("returnCode", "9999");
			return result;
		}
	}
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
