package com.xiangshangban.transit_service.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.dao.DoorCmdMapper;
import com.xiangshangban.transit_service.bean.BackgroundImageTemplate;
import com.xiangshangban.transit_service.bean.DoorCmd;
import com.xiangshangban.transit_service.bean.Image;
import com.xiangshangban.transit_service.bean.Template;
import com.xiangshangban.transit_service.bean.Theme;
import com.xiangshangban.transit_service.dao.BackgroundImageTemplateMapper;
import com.xiangshangban.transit_service.service.BackgroundImageTemplateService;
import com.xiangshangban.transit_service.utils.CmdUtil;
import com.xiangshangban.transit_service.utils.FormatUtil;
import com.xiangshangban.transit_service.utils.RabbitMQSender;

@Service("backgroundImageTemplateService")
public class BackgroundImageTemplateServiceImpl implements BackgroundImageTemplateService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	BackgroundImageTemplateMapper backgroundImageTemplateMapper;
	
	@Autowired
    DoorCmdMapper doorCmdMapper;
	
	@Value("${serverId}")
    String serverId;
	
	@Override
	public Map<String, Object> addTask(String templateLevel,String imageName,String broadStartDate,String broadEndDate,String broadStartTime,String broadEndTime,String roastingTime,
			String deviceId,String sendUrl,String companyId) {
		Map<String, Object> result = new HashMap<>();
		String resultCode;
        String resultMessage;
        
		Image image = new Image();
		image.setId(FormatUtil.createUuid());
		image.setImgName(imageName);
		image.setImgUrl(sendUrl);
		image.setImgType("preview");
		image.setStatus("0");
		
		int imageNum = backgroundImageTemplateMapper.insertImage(image);
		
		if(imageNum>0){
			Template template = new Template();
			template.setTemplateId(FormatUtil.createUuid());
			template.setTemplateType("0");
			template.setTemplateLevel(templateLevel);
			template.setOperateTime(sdf.format(new Date()));
			template.setDeviceId(deviceId);
			template.setRoastingTime(roastingTime);
			template.setStatus("0");
			
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
				backgroundImageTemplate.setStatus("0");
				
				int bgitNum = backgroundImageTemplateMapper.insertBackgroundImageTemplate(backgroundImageTemplate);
				
				if(bgitNum > 0){
					Theme t = new Theme(template.getTemplateId(), template.getTemplateType(), templateLevel, imageName, sendUrl, "", "", "", broadStartDate, broadEndDate, broadStartTime, broadEndTime, roastingTime, "");
					
					Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
			        Map<String, Object> resultData = new LinkedHashMap<String, Object>();
			        resultCode = "0";
			        resultMessage = "执行成功";
			        resultData.put("resultCode", resultCode);
			        resultData.put("resultMessage", resultMessage);
			        resultData.put("returnObj",t);
			        resultMap.put("result", resultData);
					
					//构造命令格式
			        DoorCmd doorCmdRecord = new DoorCmd();
			        doorCmdRecord.setServerId(serverId);
			        doorCmdRecord.setDeviceId(deviceId);
			        doorCmdRecord.setFileEdition("v1.3");
			        doorCmdRecord.setCommandMode("C");
			        doorCmdRecord.setCommandType("S");
			        doorCmdRecord.setCommandTotal("1");
			        doorCmdRecord.setCommandIndex("1");
			        doorCmdRecord.setSubCmdId("");
			        doorCmdRecord.setAction("UPLOAD_DEVICE_REBOOT_RECORD");
			        doorCmdRecord.setActionCode("1004");
			        doorCmdRecord.setSendTime(sdf.format(new Date()));
			        Calendar c = Calendar.getInstance();
			        c.add(Calendar.SECOND, 300);
			        doorCmdRecord.setOutOfTime(sdf.format(c.getTime()));
			        doorCmdRecord.setSuperCmdId(FormatUtil.createUuid());
			        doorCmdRecord.setData(JSON.toJSONString(resultMap));

			        //获取完整的数据加协议封装格式
			        RabbitMQSender rabbitMQSender = new RabbitMQSender();
			        Map<String, Object> doorRecordAll =  CmdUtil.messagePackaging(doorCmdRecord, "", resultData, "C");
			        //命令状态设置为: 已回复
			        doorCmdRecord.setStatus("5");
			        //设置md5校验值
			        doorCmdRecord.setMd5Check((String) doorRecordAll.get("MD5Check"));
			        //设置数据库的data字段
			        doorCmdRecord.setData(JSON.toJSONString(doorRecordAll.get("result")));
			        doorCmdRecord.setResultCode("3000");
			        doorCmdRecord.setResultMessage("执行成功");
			        //命令数据存入数据库
			        doorCmdMapper.insert(doorCmdRecord);
			        //立即下发回复数据到MQ
			        rabbitMQSender.sendMessage(deviceId, doorRecordAll);
					
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
		
		BackgroundImageTemplate bt = backgroundImageTemplateMapper.SelectByTemplateId(templateId);
		if(bt==null){
			result.put("returnCode","3015");
			result.put("message","主题不存在");
			return result;
		}
		
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
		
		BackgroundImageTemplate bit = backgroundImageTemplateMapper.SelectByTemplateId(templateId);
		
		int dtNum = backgroundImageTemplateMapper.deleteTemplate(templateId);
		
		if(dtNum > 0){
			int dbNum = backgroundImageTemplateMapper.deleteBackgroundImageTemplate(templateId);
			
			if(dbNum > 0){
				
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
			
			if(tp==null){
				result.put("returnCode","3015");
				result.put("message","主题不存在");
				return result;
			}
			
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
