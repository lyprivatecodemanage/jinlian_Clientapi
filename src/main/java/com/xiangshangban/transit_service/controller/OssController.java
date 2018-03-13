package com.xiangshangban.transit_service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.OSSFile;
import com.xiangshangban.transit_service.bean.ReturnData;
import com.xiangshangban.transit_service.bean.TokenCompany;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.OSSFileService;
import com.xiangshangban.transit_service.service.TokenCompanyService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.utils.HttpClientUtil;
import com.xiangshangban.transit_service.utils.RedisUtil;

@RestController
@RequestMapping("/OssController")
public class OssController {

	@Autowired
	UusersService uusersService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	OSSFileService oSSFileService;
	
	@Autowired
	TokenCompanyService tokenCompanyService;
	
	/**
	 * 上传文件到OSS
	 * @param file
	 * @param token 
	 * @param funcDirectory 存储模块名称
	 * @return
	 */
	@RequestMapping(value = "/upload",method=RequestMethod.POST)
	public ReturnData appUpload(@RequestParam("file")MultipartFile file,HttpServletRequest request){ 
		ReturnData returnData = new ReturnData();
		
		String funcDirectory = "deviceItme";
		
		String token = request.getHeader("token");
		
		//根据token获得当前用户id,公司id
		boolean b = tokenCompanyService.CompareTime(token);
		
		if(!b){
			returnData.setReturnCode("3014");
			returnData.setMessage("token验证失败");
			return returnData;
		}
		
		TokenCompany tc = tokenCompanyService.selectByToken(token);
		
		String companyNo = companyService.selectByPrimaryKey(tc.getCompanyId()).getCompany_no();//公司编号，此编号实际应用时，应根据token去查询
		//funcDirectory = "portrait";//portrait目录存储员工头像
		//此处判断上传的funcDirectory是否是固定的几个文件名
		boolean funcDirectoryFlag = false;
		String [] funcDirectoryArray = HttpClientUtil.getFuncDirectory();
		for(String directory:funcDirectoryArray){
			if(funcDirectory.equals(directory)){
				funcDirectoryFlag=true;
			}
		}
		if(!funcDirectoryFlag){
			returnData.setReturnCode("3011");
			returnData.setMessage("funcDirectory目录不可用");
			return returnData;
		}
		if(!file.isEmpty()){		
        	OSSFile ossFile = oSSFileService.addOSSFile(companyNo, funcDirectory, file);
        	returnData.setData(ossFile);
        	returnData.setReturnCode("3000");
			returnData.setMessage("上传成功");
			return returnData;
        }
    	returnData.setReturnCode("3010");
		returnData.setMessage("上传文件失败：文件内容为空");
		return returnData;
    }
	
}
