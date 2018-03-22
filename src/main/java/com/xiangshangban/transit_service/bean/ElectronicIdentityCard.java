package com.xiangshangban.transit_service.bean;

public class ElectronicIdentityCard {
	private String companyId;//公司id
	private String employeeId;//员工id
	private String validBeginTime;//有效期开始时间 
	private String ValidEndTime;//有效期结束时间
	private String visitKey;//二维码
	private String visitFormat;//二维码码制
	
	public ElectronicIdentityCard(){}
	
	public ElectronicIdentityCard(String companyId, String employeeId, String validBeginTime, String validEndTime,
			String visitKey, String visitFormat) {
		super();
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.validBeginTime = validBeginTime;
		ValidEndTime = validEndTime;
		this.visitKey = visitKey;
		this.visitFormat = visitFormat;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getValidBeginTime() {
		return validBeginTime;
	}
	public void setValidBeginTime(String validBeginTime) {
		this.validBeginTime = validBeginTime;
	}
	public String getValidEndTime() {
		return ValidEndTime;
	}
	public void setValidEndTime(String validEndTime) {
		ValidEndTime = validEndTime;
	}
	public String getVisitKey() {
		return visitKey;
	}
	public void setVisitKey(String visitKey) {
		this.visitKey = visitKey;
	}
	public String getVisitFormat() {
		return visitFormat;
	}
	public void setVisitFormat(String visitFormat) {
		this.visitFormat = visitFormat;
	}
	
	
}
