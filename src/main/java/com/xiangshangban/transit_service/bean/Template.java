package com.xiangshangban.transit_service.bean;

/**
 * 模板表
 * @author mian
 *
 */
public class Template {
	//主键：模板的ID
	private String templateId;
	//区分是主题还是活动     0：主题  1：活动
	private String templateType;
	//template_level：（0<1<2，对于主题来说0-默认轮播主题，1-节气节日主题，2-自定义主题，）
	private String templateLevel;
	//操作时间（<更改/修改>模板）
	private String operateTime;
	//操作人
	private String operateEmp;
	//设备的ID
	private String deviceId;
	//轮播时间
	private String roastingTime;
	//自定义logo标志
	private String logoFlag;
	//solutation_flag  0:表示使用默认的  1：表示自定义
	private String salutationFlag;
	//对于节假日模板而言：该字段表示节假日名称
	private String festivalFlag;
	//模板的样式
	private String templateStyle;
	//标志位：标志当前的模板是否正在使用
	private String isUse;
	//标志位：表示当前的模板是不是标准模板
	private String isStandard;
	//状态   0 可用   1 不可用
	private String status;
		
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateLevel() {
		return templateLevel;
	}

	public void setTemplateLevel(String templateLevel) {
		this.templateLevel = templateLevel;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateEmp() {
		return operateEmp;
	}

	public void setOperateEmp(String operateEmp) {
		this.operateEmp = operateEmp;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRoastingTime() {
		return roastingTime;
	}

	public void setRoastingTime(String roastingTime) {
		this.roastingTime = roastingTime;
	}

	public String getLogoFlag() {
		return logoFlag;
	}

	public void setLogoFlag(String logoFlag) {
		this.logoFlag = logoFlag;
	}

	public String getSalutationFlag() {
		return salutationFlag;
	}

	public void setSalutationFlag(String salutationFlag) {
		this.salutationFlag = salutationFlag;
	}

	public String getFestivalFlag() {
		return festivalFlag;
	}

	public void setFestivalFlag(String festivalFlag) {
		this.festivalFlag = festivalFlag;
	}

	public String getTemplateStyle() {
		return templateStyle;
	}

	public void setTemplateStyle(String templateStyle) {
		this.templateStyle = templateStyle;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(String isStandard) {
		this.isStandard = isStandard;
	}

	public Template() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Template(String templateId, String templateType, String templateLevel, String operateTime, String operateEmp,
			String deviceId, String roastingTime, String logoFlag, String salutationFlag, String festivalFlag,
			String templateStyle, String isUse, String isStandard, String status) {
		super();
		this.templateId = templateId;
		this.templateType = templateType;
		this.templateLevel = templateLevel;
		this.operateTime = operateTime;
		this.operateEmp = operateEmp;
		this.deviceId = deviceId;
		this.roastingTime = roastingTime;
		this.logoFlag = logoFlag;
		this.salutationFlag = salutationFlag;
		this.festivalFlag = festivalFlag;
		this.templateStyle = templateStyle;
		this.isUse = isUse;
		this.isStandard = isStandard;
		this.status = status;
	}

}
