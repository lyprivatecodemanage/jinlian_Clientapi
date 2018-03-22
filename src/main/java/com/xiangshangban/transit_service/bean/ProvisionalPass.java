package com.xiangshangban.transit_service.bean;

public class ProvisionalPass {
	private String visitId;//通行证id
	private String companyId;//公司id
	private String employeeName;//临时人员名字
	private String employeeCompany;//临时人员所属公司
	private String validBeginTime;//有效期开始时间
	private String validEndTime;//有效期结束时间
	private String doorList;//开门权限
	private String visitKey;//二维码
	private String visitFormat;//二维码码制
	
	public ProvisionalPass(){}
	
	public ProvisionalPass(String visitId, String companyId, String employeeName, String employeeCompany,
			String validBeginTime, String validEndTime, String doorList, String visitKey, String visitFormat) {
		super();
		this.visitId = visitId;
		this.companyId = companyId;
		this.employeeName = employeeName;
		this.employeeCompany = employeeCompany;
		this.validBeginTime = validBeginTime;
		this.validEndTime = validEndTime;
		this.doorList = doorList;
		this.visitKey = visitKey;
		this.visitFormat = visitFormat;
	}
	public String getVisitId() {
		return visitId;
	}
	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeCompany() {
		return employeeCompany;
	}
	public void setEmployeeCompany(String employeeCompany) {
		this.employeeCompany = employeeCompany;
	}
	public String getValidBeginTime() {
		return validBeginTime;
	}
	public void setValidBeginTime(String validBeginTime) {
		this.validBeginTime = validBeginTime;
	}
	public String getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}
	public String getDoorList() {
		return doorList;
	}
	public void setDoorList(String doorList) {
		this.doorList = doorList;
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
