package com.xiangshangban.transit_service.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.dao.TokenCompanyMapper;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.utils.FormatUtil;
import com.xiangshangban.transit_service.utils.RedisUtil;

@Service("tokenCompanyService")
public class TokenCompanyServiceImpl implements TokenCompanyService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	TokenCompanyMapper tokenCompanyMapper;
	
	@Override
	public TokenCompany getToken(String appId, String secretKey) {
		// TODO Auto-generated method stub
		TokenCompany tokenCompany = tokenCompanyMapper.SelectByAppIdAndSecretKey(appId, secretKey);
		
		if(tokenCompany!=null){
			//token凭证
			String token = FormatUtil.createUuid();
			
			//获取当前时间 加7200秒 得到token 过期时间
			Calendar calendar = Calendar.getInstance ();
		    calendar.add (Calendar.SECOND, 7200);
			
		    tokenCompany.setToken(token);
		    tokenCompany.setExpirationTime(sdf.format(calendar.getTime()));
			
			int i = tokenCompanyMapper.update(tokenCompany);
			
			if(i>0){
				//初始化redis
				RedisUtil redis = RedisUtil.getInstance();
				// 设值
				redis.new Hash().hset("appId_" + appId, "token", tokenCompany.getToken());
				// 设置redis保存时间
				redis.expire("appId_" + appId, 7200);
			}
		}
		
		return tokenCompany;
	}

	@Override
	public TokenCompany selectByToken(String token) {
		// TODO Auto-generated method stub
		return tokenCompanyMapper.selectByToken(token);
	}

}
