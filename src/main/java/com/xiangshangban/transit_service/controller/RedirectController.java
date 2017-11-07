package com.xiangshangban.transit_service.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.ReturnData;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.HttpClientUtil;

/**
 * 请求转发用
 */
@RestController
@RequestMapping("/redirectApi")
public class RedirectController{
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	UusersService userService;
	@Autowired
	LoginService loginService;
	/**
	 * 请求接口
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/sendRequest")
    public String register(HttpServletRequest request) {
		
		//根据token获得当前用户id,公司id
		String token = request.getHeader("ACCESS_TOKEN");
		Uusers user = new Uusers();
		if(StringUtils.isEmpty(token)){
			String sessionId = request.getSession().getId();
			user = userService.selectCompanyBySessionId(sessionId);
		}else{
			user = userService.selectCompanyByToken(token);
		}
		
		if(StringUtils.isEmpty(user.getCompanyId()) || StringUtils.isEmpty(user.getUserid())){
			ReturnData returnData = new ReturnData();
			returnData.setReturnCode("3003");
			returnData.setMessage("用户身份获取失败");
			return JSON.toJSONString(returnData);
		}
		
		String uri = request.getParameter("redirectUrl");//转发路径
		String modeCode = request.getParameter("redirectMode");//模块
		String sendurl = HttpClientUtil.getModeUrl(modeCode)+uri;
		//头信息
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("companyId", user.getCompanyId());
		headers.put("accessUserId", user.getUserid());
		//请求参数
		Map<String,String[]> paramMap =  (Map<String,String[]>)request.getParameterMap();
		JSONObject newParamMap = new JSONObject();
		for (Map.Entry<String,String[]> entry : paramMap.entrySet()) {
			logger.info(entry.getKey()+":"+entry.getValue()[0]);
			if(!entry.getKey().equals("redirectUrl") && !entry.getKey().equals("redirectMode")){
				newParamMap.put(entry.getKey(), entry.getValue().length>1?entry.getValue():entry.getValue()[0]);
			}
		}
		//ContentType contentType = ContentType.create(req.getHeader("content-type").split(";")[0], "UTF-8");
		String result = HttpClientUtil.sendRequet(sendurl, newParamMap, ContentType.APPLICATION_JSON, headers);//ContentType.APPLICATION_JSON);
        return result;
    }
}