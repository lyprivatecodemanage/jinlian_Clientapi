package com.xiangshangban.transit_service.bean;

/**
 * 模板和背景图的关联关系表
 * @author mian
 *
 */
public class BackgroundImageTemplate {
	//编号
	private String id;
	//背景图的id
	private String imgId;
	//模板的id
	private String templateId;
	//背景图显示的开始日期
	private String broadStartDate;
	//背景图展示的结束日期
	private String broadEndDate;
	//背景图展示的开始时间
	private String broadStartTime;
	//背景图展示结束时间
	private String broadEndTime;
	//状态   0 可用   1 不可用
	private String status;
	//公司编号
	private String companyId;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public BackgroundImageTemplate(String id, String imgId, String templateId, String broadStartDate,
			String broadEndDate, String broadStartTime, String broadEndTime, String status, String companyId) {
		super();
		this.id = id;
		this.imgId = imgId;
		this.templateId = templateId;
		this.broadStartDate = broadStartDate;
		this.broadEndDate = broadEndDate;
		this.broadStartTime = broadStartTime;
		this.broadEndTime = broadEndTime;
		this.status = status;
		this.companyId = companyId;
	}

	public BackgroundImageTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

}
