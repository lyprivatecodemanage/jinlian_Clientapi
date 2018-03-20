package com.xiangshangban.transit_service.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiangshangban.transit_service.bean.BackgroundImageTemplate;
import com.xiangshangban.transit_service.bean.Image;
import com.xiangshangban.transit_service.bean.Template;
import com.xiangshangban.transit_service.dao.BackgroundImageTemplateMapper;
import com.xiangshangban.transit_service.service.BackgroundImageTemplateService;
import com.xiangshangban.transit_service.utils.FormatUtil;

@Service("backgroundImageTemplateService")
public class BackgroundImageTemplateServiceImpl implements BackgroundImageTemplateService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	BackgroundImageTemplateMapper backgroundImageTemplateMapper;
	
	@Override
	public Map<String, Object> addTask(String templateLevel,String imageName,String broadStartDate,String broadEndDate,String broadStartTime,String broadEndTime,String roastingTime,
			String deviceId,String sendUrl,String companyId) {
		Map<String, Object> result = new HashMap<>();
		
		Image image = new Image();
		image.setId(FormatUtil.createUuid());
		image.setImgName(imageName);
		image.setImgUrl(sendUrl);
		image.setImgType("preview");
		
		int imageNum = backgroundImageTemplateMapper.insertImage(image);
		
		if(imageNum>0){
			Template template = new Template();
			template.setTemplateId(FormatUtil.createUuid());
			template.setTemplateType("0");
			template.setTemplateLevel(templateLevel);
			template.setOperateTime(sdf.format(new Date()));
			template.setDeviceId(deviceId);
			template.setRoastingTime(roastingTime);
			
			int bitNum  = backgroundImageTemplateMapper.insertTemplate(template);
			
			if(bitNum > 0){
				BackgroundImageTemplate backgroundImageTemplate = new BackgroundImageTemplate();
				backgroundImageTemplate.setId(FormatUtil.createUuid());
				backgroundImageTemplate.setImgId(image.getId());
				backgroundImageTemplate.setTemplateId(template.getTemplateId());
				backgroundImageTemplate.setBroadStartDate(broadStartDate);
				backgroundImageTemplate.setBroadStartTime(broadStartTime);
				backgroundImageTemplate.setBroadEndDate(broadEndDate);
				backgroundImageTemplate.setBroadEndTime(broadEndTime);
				backgroundImageTemplate.setCompanyId(companyId);
				
				int bgitNum = backgroundImageTemplateMapper.insertBackgroundImageTemplate(backgroundImageTemplate);
				
				if(bgitNum > 0){
					result.put("templateId",template.getTemplateId());
					result.put("returnCode","3000");
					result.put("message","数据请求成功");
					return result;
				}
			}
		}
		
		result.put("returnCode","3001");
		result.put("message","服务器错误");
		return result;
	}

	@Override
	public Map<String, Object> updateTask(String templateId,String templateLevel, String imageName, String broadStartDate,
			String broadEndDate, String broadStartTime, String broadEndTime, String roastingTime) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		
		Template template = new Template();
		template.setTemplateId(templateId);
		template.setOperateTime(sdf.format(new Date()));
		template.setTemplateLevel(templateLevel);
		template.setRoastingTime(roastingTime);
		
		int utNum = backgroundImageTemplateMapper.updateTemplate(template);
		
		if(utNum > 0){
			BackgroundImageTemplate backgroundImageTemplate = new BackgroundImageTemplate();
			backgroundImageTemplate.setTemplateId(templateId);
			backgroundImageTemplate.setBroadEndDate(broadEndDate);
			backgroundImageTemplate.setBroadEndTime(broadEndTime);
			backgroundImageTemplate.setBroadStartDate(broadStartDate);
			backgroundImageTemplate.setBroadStartTime(broadStartTime);
			
			int ubt = backgroundImageTemplateMapper.updateBackgroundImageTemplate(backgroundImageTemplate);
			
			if(ubt > 0){
				BackgroundImageTemplate bgt = backgroundImageTemplateMapper.SelectByTemplateId(templateId);
				
				Image image = new Image();
				image.setId(bgt.getImgId());
				image.setImgName(imageName);
				
				int uiNum = backgroundImageTemplateMapper.updateImage(image);
				
				if(uiNum > 0){
					result.put("returnCode","3000");
					result.put("message","数据请求成功");
					return result;
				}
			}
		}
		
		result.put("returnCode","3001");
		result.put("message","服务器错误");
		return result;
	}

	@Override
	public Map<String, Object> deleteTask(String templateId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		
		int dtNum = backgroundImageTemplateMapper.deleteTemplate(templateId);
		
		if(dtNum > 0){
			int dbNum = backgroundImageTemplateMapper.deleteBackgroundImageTemplate(templateId);
			
			if(dbNum > 0){
				BackgroundImageTemplate bit = backgroundImageTemplateMapper.SelectByTemplateId(templateId);
				
				int diNum = backgroundImageTemplateMapper.deleteImage(bit.getImgId());
				
				if(diNum > 0){
					result.put("returnCode","3000");
					result.put("message","数据请求成功");
					return result;
				}
			}
		}
		result.put("returnCode","3001");
		result.put("message","服务器错误");
		return result;
	}
	
	@Override
	public Map<String, Object> getTaskById(String templateId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		
		Map<String,String> resultmap = new HashMap<String,String>();
		
		try {
			Template tp = backgroundImageTemplateMapper.SelectTemplateById(templateId);
			
			BackgroundImageTemplate bd = backgroundImageTemplateMapper.SelectByTemplateId(templateId);
			
			Image image = backgroundImageTemplateMapper.SelectImageById(bd.getImgId());
			
			resultmap.put("templateId",tp.getTemplateId());
			resultmap.put("templateLevel ",tp.getTemplateLevel());
			resultmap.put("imagePath",image.getImgUrl());
			resultmap.put("imageKey",image.getImgName());
			resultmap.put("broadStartDate",bd.getBroadStartDate());
			resultmap.put("broadEndDate", bd.getBroadEndDate());
			resultmap.put("broadStartTime",bd.getBroadStartTime());
			resultmap.put("broadEndTime", bd.getBroadEndTime());
			resultmap.put("roastingTime",tp.getRoastingTime());
			resultmap.put("deviceId",tp.getDeviceId());
			
			result.put("returnCode","3000");
			result.put("message","数据请求成功");
			result.put("data",resultmap);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("returnCode","3001");
			result.put("message","服务器错误");
			return result;
		}
	}
	
	@Override
	public Map<String, Object> getTaskAll(String companyId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		
		List<Map<String,String>> listMap = new ArrayList<>();
		
		try{
			List<BackgroundImageTemplate> list = backgroundImageTemplateMapper.FindAllByCompanyId(companyId);
			
			if(list.size()>0){
				for (BackgroundImageTemplate bd : list) {
					Map<String,String> resultmap = new HashMap<String,String>();
					
					Template tp = backgroundImageTemplateMapper.SelectTemplateById(bd.getTemplateId());
					
					Image image = backgroundImageTemplateMapper.SelectImageById(bd.getImgId());
					
					resultmap.put("templateId",tp.getTemplateId());
					resultmap.put("templateLevel ",tp.getTemplateLevel());
					resultmap.put("imagePath",image.getImgUrl());
					resultmap.put("imageKey",image.getImgName());
					resultmap.put("broadStartDate",bd.getBroadStartDate());
					resultmap.put("broadEndDate", bd.getBroadEndDate());
					resultmap.put("broadStartTime",bd.getBroadStartTime());
					resultmap.put("broadEndTime", bd.getBroadEndTime());
					resultmap.put("roastingTime",tp.getRoastingTime());
					resultmap.put("deviceId",tp.getDeviceId());
					
					listMap.add(resultmap);
				}
			}
			
			result.put("returnCode","3000");
			result.put("message","数据请求成功");
			result.put("data",listMap);
			return result;
		} catch(Exception e){
			e.printStackTrace();
			result.put("returnCode","3001");
			result.put("message","服务器错误");
			return result;
		}
	}
}
