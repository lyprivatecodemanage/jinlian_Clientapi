package com.xiangshangban.transit_service.bean;

public class Theme {

	private String templateId;
	
	private String templateType;
	
	private String templateLevel;
	
	private String imageName;
	
	private String imagePath;
	
	private String alertButtonColor;
	
	private String alertImgName;
	
	private String alertImgPath;
	
	private String broadStartDate;
	
	private String broadEndDate;
	
	private String broadStartTime;
	
	private String broadEndTime;
	
	private String roastingTime;
	
	private String items;

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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getAlertButtonColor() {
		return alertButtonColor;
	}

	public void setAlertButtonColor(String alertButtonColor) {
		this.alertButtonColor = alertButtonColor;
	}

	public String getAlertImgName() {
		return alertImgName;
	}

	public void setAlertImgName(String alertImgName) {
		this.alertImgName = alertImgName;
	}

	public String getAlertImgPath() {
		return alertImgPath;
	}

	public void setAlertImgPath(String alertImgPath) {
		this.alertImgPath = alertImgPath;
	}

	public String getBroadStartDate() {
		return broadStartDate;
	}

	public void setBroadStartDate(String broadStartDate) {
		this.broadStartDate = broadStartDate;
	}

	public String getBroadEndDate() {
		return broadEndDate;
	}

	public void setBroadEndDate(String broadEndDate) {
		this.broadEndDate = broadEndDate;
	}

	public String getBroadStartTime() {
		return broadStartTime;
	}

	public void setBroadStartTime(String broadStartTime) {
		this.broadStartTime = broadStartTime;
	}

	public String getBroadEndTime() {
		return broadEndTime;
	}

	public void setBroadEndTime(String broadEndTime) {
		this.broadEndTime = broadEndTime;
	}

	public String getRoastingTime() {
		return roastingTime;
	}

	public void setRoastingTime(String roastingTime) {
		this.roastingTime = roastingTime;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Theme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Theme(String templateId, String templateType, String templateLevel, String imageName, String imagePath,
			String alertButtonColor, String alertImgName, String alertImgPath, String broadStartDate,
			String broadEndDate, String broadStartTime, String broadEndTime, String roastingTime, String items) {
		super();
		this.templateId = templateId;
		this.templateType = templateType;
		this.templateLevel = templateLevel;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.alertButtonColor = alertButtonColor;
		this.alertImgName = alertImgName;
		this.alertImgPath = alertImgPath;
		this.broadStartDate = broadStartDate;
		this.broadEndDate = broadEndDate;
		this.broadStartTime = broadStartTime;
		this.broadEndTime = broadEndTime;
		this.roastingTime = roastingTime;
		this.items = items;
	}

}