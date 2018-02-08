package com.xiangshangban.transit_service.bean;
/**
 * 请求返回数据模型
 * @author 韦友弟
 *
 */
public class ReturnData {
	private Object data;//数据
	private String message;//请求状态描述
	private String returnCode;//请求状态编码
	private String pagecountNum;
	private String totalPages;
	private String bluetoothId;
	
	public String getPagecountNum() {
		return pagecountNum;
	}
	public void setPagecountNum(String pagecountNum) {
		this.pagecountNum = pagecountNum;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getBluetoothId() {
		return bluetoothId;
	}
	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
}
