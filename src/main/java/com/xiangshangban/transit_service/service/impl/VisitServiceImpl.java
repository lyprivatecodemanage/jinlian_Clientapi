package com.xiangshangban.transit_service.service.impl;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.DeviceKey;
import com.xiangshangban.transit_service.bean.ElectronicIdentityCard;
import com.xiangshangban.transit_service.bean.Employee;
import com.xiangshangban.transit_service.bean.ProvisionalPass;
import com.xiangshangban.transit_service.dao.DeviceKeyMapper;
import com.xiangshangban.transit_service.dao.DeviceSettingMapper;
import com.xiangshangban.transit_service.dao.ElectronicIdentityCardMapper;
import com.xiangshangban.transit_service.dao.ProvisionalPassMapper;
import com.xiangshangban.transit_service.dao.UusersMapper;
import com.xiangshangban.transit_service.service.VisitService;
import com.xiangshangban.transit_service.utils.DESEncode;
import com.xiangshangban.transit_service.utils.FileMD5Util;
import com.xiangshangban.transit_service.utils.FormatUtil;
import com.xiangshangban.transit_service.utils.RSAUtils;
@Service
public class VisitServiceImpl implements VisitService {
	@Autowired
	private UusersMapper uusersMapper;
	@Autowired
	private DeviceSettingMapper deviceSettingMapper;
	@Autowired
	private DeviceKeyMapper deviceKeyMapper;
	@Autowired
	private ProvisionalPassMapper provisionalPassMapper;
	@Autowired
	private ElectronicIdentityCardMapper electronicIdentityCardMapper;
	
	/**
	 * 申请正式人员的电子身份证
	 */
	@Override
	public Map<String, Object> addVisitCardEmp(String employeeId,String companyId,String validBeginTime,String validEndTime) {
		Map<String, Object> result = new HashMap<String,Object>();
		Employee emp = uusersMapper.SeletctEmployeeByUserId(employeeId, companyId);
		if(emp==null){
			result.put("returnCode","9999");
			result.put("message","员工不存在!");
			return result;
		}
		DeviceKey deviceKey = deviceKeyMapper.selectDeviceKey();
		RSAPublicKey publicKey;
		try {
			publicKey = RSAUtils.getPublicKey(deviceKey.getPublicKey());//公钥
			StringBuffer data = new StringBuffer("0;"+employeeId+";"+validBeginTime+";"+validEndTime);//密文
			String md5Str = FileMD5Util.getMD5String(data.toString());
			String rsaStr = RSAUtils.publicEncrypt(md5Str, publicKey);//rsa密文
			String desStr = DESEncode.encrypt(data.toString());//des密文
			
			ElectronicIdentityCard electronicIdentityCard = new ElectronicIdentityCard(companyId,
					employeeId,validBeginTime,validEndTime,rsaStr+","+desStr,"QR Code");
			ElectronicIdentityCard aleradyExistedElectronicIdentityCard = electronicIdentityCardMapper.selectElectronicIdentityCard(companyId, employeeId);
			if(aleradyExistedElectronicIdentityCard==null){
				electronicIdentityCardMapper.insertSelective(electronicIdentityCard);
			}else{
				electronicIdentityCardMapper.updateElectronicIdentityCard(electronicIdentityCard);
			}
			result.put("visitKey", rsaStr+","+desStr);
			result.put("visitFormat", "QR Code");
			result.put("returnCode", "3000");
			result.put("message", "操作成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器异常");
			return result;
		}
	}
	/**
	 * 申请临时人员的电子通行证
	 */
	@Override
	public Map<String, Object> addVisitCard(String companyId,String employeeName, String employeeCompany, String validBeginTime,
			String validEndTime, List<String> doorList) {
		Map<String, Object> result = new HashMap<String,Object>();
		StringBuffer doorListSbf = new StringBuffer("");
		for(String item:doorList){
			doorListSbf.append(item+",");
		}
		String doorListStr = doorListSbf.substring(0, doorListSbf.length()-1);
		DeviceKey deviceKey = deviceKeyMapper.selectDeviceKey();
		RSAPublicKey publicKey;
		try {
			publicKey = RSAUtils.getPublicKey(deviceKey.getPublicKey());//公钥
			StringBuffer data = new StringBuffer("1;"+employeeName+";"+employeeCompany+";"+validBeginTime+";"+validEndTime+";"+doorListStr);//密文
			String md5Str = FileMD5Util.getMD5String(data.toString());
			String rsaStr = RSAUtils.publicEncrypt(md5Str, publicKey);//rsa密文
			String desStr = DESEncode.encrypt(data.toString());//des密文
			String visitId = FormatUtil.createUuid();
			
			ProvisionalPass provisionalPass = new ProvisionalPass(visitId, 
					companyId, employeeName, employeeCompany, validBeginTime, validEndTime, doorListStr, rsaStr+","+desStr, "QR Code");
			provisionalPassMapper.insertSelective(provisionalPass);
			result.put("visitId", visitId);
			result.put("visitKey", rsaStr+","+desStr);
			result.put("visitFormat", "QR Code");
			result.put("returnCode", "3000");
			result.put("message", "操作成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器异常");
			return result;
		}
	}

}
