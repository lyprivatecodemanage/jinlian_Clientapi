package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.Company;

@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(String company_id);

    int insert(Company record);

    Company selectByPrimaryKey(String company_id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    
    //根绝公司名称判断公司是否重复
    int selectCompanyName(String companyName);
    
    //根据公司编号判断公司名是否重复(是否有这个公司)
    int selectByCompany(String company_no);

    //根据公司编号获得公司信息
    Company selectByCompanyName(String company_no);

    //创建公司(填写公司名称，存入员工表编号关联)
    int insertSelective(Company record);

    //模糊查询公司编号前缀是否有一致的
    int selectCompanyNo(String companyNo);
    
    //根据手机号查询出公司信息
	Company selectByPhone(String phone);
}