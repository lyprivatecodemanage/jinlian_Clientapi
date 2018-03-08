package com.xiangshangban.transit_service.bean;

public class TokenCompany {

	private String companyId;
	private String appId;
	private String secretKey;
	private String token;
	private String expirationTime;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public TokenCompany(String companyId, String appId, String secretKey, String token, String expirationTime) {
		super();
		this.companyId = companyId;
		this.appId = appId;
		this.secretKey = secretKey;
		this.token = token;
		this.expirationTime = expirationTime;
	}
	public TokenCompany() {
		super();
		// TODO Auto-generated constructor stub
	}
}
