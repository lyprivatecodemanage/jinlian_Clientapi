package com.xiangshangban.transit_service.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface BackgroundImageTemplateService {

	
	public Map<String, Object> addTask(String templateLevel,String imageName,String broadStartDate,String broadEndDate,String broadStartTime,String broadEndTime,String roastingTime,
			String deviceId,String sendUrl,String companyId);
	
	public Map<String, Object> updateTask(String templateId,String templateLevel,String imageName,String broadStartDate,String broadEndDate,String broadStartTime,String broadEndTime,String roastingTime);

	public Map<String, Object> deleteTask(String templateId);
	
	public Map<String, Object> getTaskById(String templateId);
	
	public Map<String, Object> getTaskAll(String companyId);
}
