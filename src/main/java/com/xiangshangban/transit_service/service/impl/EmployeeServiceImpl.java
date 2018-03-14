package com.xiangshangban.transit_service.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Employee;
import com.xiangshangban.transit_service.dao.EmployeeDao;
import com.xiangshangban.transit_service.service.EmployeeService;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	public List<Employee> selectAllEmployeeByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return employeeDao.selectAllEmployeeByCompanyId(companyId);
	}
	
}
