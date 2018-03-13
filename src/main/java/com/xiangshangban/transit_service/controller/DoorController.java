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
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.ReturnData;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.UusersRolesKey;
import com.xiangshangban.transit_service.service.DoorService;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.utils.EmptyUtil;
import com.xiangshangban.transit_service.utils.PropertiesUtils;

@RestController
@RequestMapping("/DoorController")
public class DoorController {
	@Autowired
	private TokenCompanyService tokenCompanyService;
	@Autowired
	private UusersRolesService uusersRolesService;
	@Autowired
	private DoorService doorService;
	/**
	 * 添加门和绑定设备
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/basic/addDoor",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> addDoor(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,String> headers = new HashMap<String,String>();
		Map<String,String> params = new HashMap<String,String>();
		
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String doorName = obj.getString("doorName");
		String deviceId = obj.getString("deviceId");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(doorName)||StringUtils.isEmpty(deviceId)){
			map.put("returnCode","3006");
			map.put("message","必传参数为空");
			return map;
		}
		//判断token是否有有效
		boolean compareTime = tokenCompanyService.CompareTime(token);
		if(!compareTime){
			map.put("returnCode", 3014);
			map.put("message", "token验证失败");
			return map;
		}
		//获取公司id
		TokenCompany tokenCompany = tokenCompanyService.selectByToken(token);
		// 查看当前管理员及历史管理员
		UusersRolesKey accessUser = uusersRolesService.SelectAdministrator(tokenCompany.getCompanyId(),new Uroles().admin_role);
		//设置请求头参数
		headers.put("companyId", tokenCompany.getCompanyId());
		headers.put("accessUserId", accessUser.getUserId());
		//设置请求参数
		params.put("doorName",doorName);
		params.put("deviceId",deviceId);
		Map<String,Object> result = doorService.addDoor(params, headers);
		if(result==null){
			map.put("returnCode", "3001");
			map.put("message", "操作失败");
			return map;
		}
		/*Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("doorId", "10");
		map.put("data",JSON.toJSON(resultmap));
		map.put("returnCode","3000");
		map.put("message","操作成功");*/
		return result;
	}
	/**
	 * 门禁权限下发
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/employee/handOutEmployeePermission",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public ReturnData handOutEmployeePermission(@RequestBody String objectString,HttpServletRequest request){
		ReturnData rd = new ReturnData();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String immediatelyDownload = obj.getString("immediatelyDownload");
		String employeePermission = obj.getString("employeePermission");
		String doorId = obj.getString("doorId");
		String doorName = obj.getString("doorName");
		String employeeList = obj.getString("employeeList");
		String employeeId = obj.getString("employeeId");
		String employeeName = obj.getString("employeeName");
		String rangeDoorOpenType = obj.getString("rangeDoorOpenType");
		String oneWeekTimeList = obj.getString("oneWeekTimeList");
		String isAllDay = obj.getString("isAllDay");
		String weekType = obj.getString("weekType");
		String startTime = obj.getString("startTime");
		String endTime = obj.getString("endTime");
		String isDitto = obj.getString("isDitto");
		String doorOpenStartTime = obj.getString("doorOpenStartTime");
		String doorOpenEndTime = obj.getString("doorOpenEndTime");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(immediatelyDownload)
				||StringUtils.isEmpty(employeePermission)
				||StringUtils.isEmpty(doorId)||StringUtils.isEmpty(doorName)
				||StringUtils.isEmpty(isDitto)
				||StringUtils.isEmpty(doorOpenStartTime)
				||StringUtils.isEmpty(doorOpenEndTime)){
			rd.setReturnCode("3006");
			rd.setMessage("必传参数为空");
			return rd;
		}

		rd.setReturnCode("3000");
		rd.setMessage("已执行下发人员信息操作，请前往门列表查看当前选中的门，了解具体的下发状态");
		return rd;
	}
	
	/**
	 * 设备已有权限查询
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/autho/getRelateEmpPermissionInfo",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getRelateEmpPermissionInfo(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String doorId = obj.getString("doorId");
		String empName = obj.getString("empName");
		String deptName = obj.getString("deptName");
		String openType = obj.getString("openType");
		String openTime = obj.getString("openTime");
		String page = obj.getString("page");
		String rows = obj.getString("rows");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(page)||StringUtils.isEmpty(rows)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}

		Map<String,String> map = new HashMap<>();
		map.put("day_of_week", "1");
		map.put("deviceName", "无敌的阿凡提设备1");
		map.put("employee_department_name", "大佬一部");
		map.put("employee_id", "0EDE47CCB72C4C1DB1D60A6B7125F2F6");
		map.put("employee_name", "刘广龙-大大佬");
		map.put("isHistoryDevice", "0");
		map.put("lasttime", "2017-11-26 09:00");
		map.put("range_door_open_type", "卡-人脸-手机蓝牙");
		map.put("range_end_time", "23:59");
		map.put("range_start_time", "00:00");
		map.put("status", "下发成功");
		
		
		result.put("data",JSON.toJSON(map));
		result.put("doorId", "24");
		result.put("doorName", "阿凡提门");
		result.put("returnCode", "3000/4203");
		result.put("message","数据请求成功/请求数据不存在");
		return result;
	}
	/**
	 * 门已有权限删除
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/employee/deleteEmployeeInformationDev",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> deleteEmployeeInformationDev(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String doorId = obj.getString("doorId");
		String employeeIdList = obj.getString("employeeIdList");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(employeeIdList)||StringUtils.isEmpty(doorId)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}

		result.put("returnCode", "3000");
		result.put("message","已执行删除设备上人员权限的操作");
		return result;
	}
	/**
	 * 所有设备信息查询
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findDeviceInformation",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> findDeviceInformation(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String deviceName = obj.getString("deviceName");
		String deviceId = obj.getString("deviceId");
		String isOnline = obj.getString("isOnline");
		String activeStatus = obj.getString("activeStatus");
		
		if(StringUtils.isEmpty(token)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		Map<String,String> map = new HashMap<>();
		map.put("is_online", "在线");
		map.put("company_id", "A4F5A833EE674AE6B85F5582CCB3550D");
		map.put("device_place", "无敌的乐山新村");
		map.put("door_name", "无敌的大门");
		map.put("operate_employee", "3");
		map.put("have_used_time", "2个月零20天");
		map.put("device_id", "0f1a21d4e6fd3cb8");
		map.put("mac_address", "001062654135123");
		map.put("active_status", "待激活");
		map.put("operate_time", "2017-10-30");
		map.put("remain_server_time", "3个月零10天");
		map.put("device_name", "无敌的设备");
		map.put("company_name", "无敌的公司");
		map.put("door_id", "003");
		map.put("device_usages", "无敌的考勤");
		map.put("total_server_time", "5个月");
		map.put("is_unbind", "1");
		
		
		result.put("data",JSON.toJSON(map));
		result.put("returnCode", "3000");
		result.put("message","数据请求成功");
		return result;
	}
	/**
	 * 更改门与设备的绑定关系
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/basic/updateDoor",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> updateDoor(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String cdoorName = obj.getString("cdoorName");
		String deviceId = obj.getString("deviceId");
		String doorId = obj.getString("doorId");
		
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(cdoorName)||StringUtils.isEmpty(deviceId)||StringUtils.isEmpty(doorId)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		result.put("returnCode", "3000");
		result.put("message","数据请求成功");
		return result;
	}
	/**
	 * 门禁记录查询
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/record/getInOutRecord",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getInOutRecord(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String empName = obj.getString("empName");
		String dept = obj.getString("dept");
		String recordType = obj.getString("recordType");
		String recordTime = obj.getString("recordTime");
		String page = obj.getString("page");
		String rows = obj.getString("rows");
		String companyId = obj.getString("companyId");
		String deviceName = obj.getString("deviceName");
		
		if(StringUtils.isEmpty(token)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("deviceId","161a21d4e6fd3cb8-1-2-bf9b");
		map.put("deviceName","5e4b哈哈哈");
		map.put("isHistoryDevice","0");
		map.put("record_type", "22");
		map.put("employee_department_name", "研发部");
		map.put("record_type_name", "消防联动撤防关锁");
		map.put("employee_name", "赵武");
		
		result.put("data",JSON.toJSON(map));
		result.put("pagecountNum", "1");
		result.put("totalPages", "5");
		result.put("returnCode", "3000");
		result.put("message","数据请求成功");
		return result;
	}
	/**
	 * 门禁参数设置
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/handOutDoorFeaturesSetup",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> handOutDoorFeaturesSetup(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		List<String> list = new ArrayList<>();
		
		list.add(obj.getString("doorId"));
		list.add(obj.getString("loginEmployeeId"));
		list.add(obj.getString("countLimitAuthenticationFailed"));
		list.add(obj.getString("enableAlarm"));
		String alarmTimeLength = obj.getString("alarmTimeLength");
		list.add(obj.getString("publicPassword1"));
		list.add(obj.getString("publicPassword2"));
		list.add(obj.getString("threatenPassword"));
		list.add(obj.getString("deviceManagePassword"));
		list.add(obj.getString("enableDoorOpenRecord"));
		list.add(obj.getString("enableDoorKeepOpe"));
		list.add(obj.getString("enableFirstCardKeepOpe"));
		list.add(obj.getString("enableDoorCalendar"));
		list.add(obj.getString("employeeIdList"));
		list.add( obj.getString("oneWeekTimeDoorKeepList"));
		String weekType = obj.getString("weekType");
		String startTime = obj.getString("startTime");
		String endTime = obj.getString("endTime");
		list.add(obj.getString("oneWeekTimeFirstCardList"));
		String doorOpenType = obj.getString("doorOpenType");
		list.add(obj.getString("accessCalendarList"));
		String deviceCalendarDate = obj.getString("deviceCalendarDate");
		String enableDoorOpenGlobal = obj.getString("enableDoorOpenGlobal");
		list.add(obj.getString("isAllDa"));
		list.add(obj.getString("isDitto"));
		list.add(obj.getString("startWeekNumber"));
		list.add(obj.getString("endWeekNumber"));
		
		boolean b = new EmptyUtil().isEmpty(list);
		
		if(b){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		result.put("returnCode", "3000");
		result.put("message","已执行下发门禁设置操作");
		return result;
	}
	/**
	 * 门禁参数查询
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/autho/getHighSettingForFunction",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getHighSettingForFunction(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String doorId = obj.getString("doorId");
		
		if(StringUtils.isEmpty(token)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		Map<String,String> dmap =new HashMap<>();
		dmap.put("calendarDate","2017-11-30");
		dmap.put("doorId","17");
		dmap.put("weatherOpenDoor","1");
		
		Map<String,String> emap =new HashMap<>();
		emap.put("employeeId","2A42EC2E17944F6EA8E01D7319843D61");
		emap.put("employeeName","云溪");
		
		Map<String,String> tmap =new HashMap<>();
		tmap.put("endTime","12:00");
		tmap.put("startTime","08:00");
		
		Map<String,Object> fmap =new HashMap<>();
		fmap.put("employeeIdList",JSON.toJSON(emap));
		fmap.put("timeList",JSON.toJSON(tmap));
		fmap.put("endWeekNumber","5");
		fmap.put("startWeekNumber","1");
		
		Map<String,Object> trmap =new HashMap<>();
		trmap.put("startTime","08:00");
		trmap.put("endTime","10:00");
		
		Map<String,Object> timap =new HashMap<>();
		timap.put("timeRange",trmap);
		timap.put("isDitto","1");
		timap.put("isAllDay","0");
		timap.put("week","2");
		
		Map<String,Object> domap =new HashMap<>();
		domap.put("enable_first_card_keep_open","1");
		domap.put("manager_password","18649866");
		domap.put("first_publish_password","110");
		domap.put("second_publish_password","120");
		domap.put("alarm_time_length_trespass","60");
		domap.put("fault_count_authentication","5");
		domap.put("enable_door_keep_open","0");
		domap.put("threaten_publish_passwrod","130");
		domap.put("enable_door_event_record","0");
		domap.put("alarmFlag","1");
		domap.put("enable_door_calendar","1");
		
		Map<String,Object> map =new HashMap<>();
		map.put("doorCalendar",JSON.toJSON(dmap));
		map.put("firstCardKeepOpen",JSON.toJSON(fmap));
		map.put("timingKeepOpen",JSON.toJSON(timap));
		map.put("doorSetting",JSON.toJSON(domap));
		
		result.put("data",JSON.toJSON(map));
		result.put("returnCode", "3000");
		result.put("message","已执行下发门禁设置操作");
		return result;
	}
	/**
	 * 删除门
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/basic/delDoor",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> delDoor(@RequestBody String objectString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String token = request.getHeader("token");
		JSONObject obj = JSON.parseObject(objectString);
		String doorId = obj.getString("doorId");
		
		if(StringUtils.isEmpty(token)){
			result.put("returnCode", "3006");
			result.put("message","必传参数为空");
			return result;
		}
		
		result.put("returnCode", "3000");
		result.put("message","操作成功");
		return result;
	}
	
	
}
