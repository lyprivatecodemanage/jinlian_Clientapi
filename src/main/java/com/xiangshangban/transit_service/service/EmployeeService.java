package com.xiangshangban.transit_service.service;

import java.util.List;
import com.xiangshangban.transit_service.bean.Employee;

public interface EmployeeService {
	
	/**
	 * 查询公司所有员工信息
	 * @param companyId
	 * @return
	 */
	List<Employee> selectAllEmployeeByCompanyId(String companyId);
	
}
