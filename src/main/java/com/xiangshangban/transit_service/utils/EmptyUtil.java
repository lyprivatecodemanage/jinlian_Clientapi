package com.xiangshangban.transit_service.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class EmptyUtil {

	public static boolean isEmpty(List<String> params){
		if(params!=null && params.size()>0){
			for(String str:params){
				if(StringUtils.isEmpty(str)){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
}

