package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.ProvisionalPass;

@Mapper
public interface ProvisionalPassMapper {
	Integer insertSelective(ProvisionalPass provisionalPass);
}
