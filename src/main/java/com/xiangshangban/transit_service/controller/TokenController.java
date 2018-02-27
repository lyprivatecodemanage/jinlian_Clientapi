package com.xiangshangban.transit_service.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@RestController
@RequestMapping("/TokenController")
public class TokenController {
	public static final String AppId = "5746467465akfkd";
	public static final String SecretKey = "dkfjdklfjdkljfisldf36867";
	public static final String Token = "a13598e8696644444dfe";
	/**
	 * 获得接口调用凭证token
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get/token",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> registerUsers(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		if(StringUtils.isEmpty(jsonString)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		JSONObject jobj = JSON.parseObject(jsonString);
		String appId = jobj.getString("appId");
		String secretKey = jobj.getString("secretKey");
		if(StringUtils.isEmpty(appId)||StringUtils.isEmpty(secretKey)){
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			return result;
		}
		if(AppId.equals(appId)&&SecretKey.equals(secretKey))
		{
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			result.put("token", Token);
			return result;
		}else{
			result.put("message", "账号密码错误");
			result.put("returnCode", "9999");
			return result;
		}
		
	}
		
}
