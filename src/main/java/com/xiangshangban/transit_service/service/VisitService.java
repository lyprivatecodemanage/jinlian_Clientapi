package com.xiangshangban.transit_service.service;

import java.util.List;
import java.util.Map;


public interface VisitService {
	Map<String,Object> addVisitCardEmp(String employeeId,String companyId,String validBeginTime,String validEndTime);
	
	Map<String,Object> addVisitCard(String companyId,String employeeName,String employeeCompany,String validBeginTime,String validEndTime,List<String> doorList);
}
