package com.xiangshangban.transit_service.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math.util.MathUtils;
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
import com.xiangshangban.transit_service.utils.FormatUtil;
import com.xiangshangban.transit_service.utils.RedisUtil;
import com.xiangshangban.transit_service.utils.RedisUtil.Hash;


@RestController
@RequestMapping("/TokenController")
public class TokenController {
	public static final String Token = "a13598e8696644444dfe";
	
	@Autowired
	TokenCompanyService tokenCompanyService;
	
	/**
	 * 获得接口调用凭证token
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get/token",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getToken(@RequestBody String jsonString,HttpServletRequest request){
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
		
		TokenCompany tokenCompany = tokenCompanyService.getToken(appId, secretKey);
		
		if(tokenCompany!=null)
		{
			result.put("message", "数据请求成功");
			result.put("returnCode", "3000");
			result.put("token", tokenCompany.getToken());
			return result;
		}else{
			result.put("message", "账号密码错误");
			result.put("returnCode", "9999");
			return result;
		}
		
	}
		
}
