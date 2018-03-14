package com.xiangshangban.transit_service.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.xiangshangban.transit_service.bean.Employee;

@Mapper
public interface EmployeeDao {

	/**
	 * @author 李业
	 * 通过公司id查询公司所有的在职员工
	 * @param companyId
	 * @return
	 */
	List<Employee> selectAllEmployeeByCompanyId(String companyId);
	
}
