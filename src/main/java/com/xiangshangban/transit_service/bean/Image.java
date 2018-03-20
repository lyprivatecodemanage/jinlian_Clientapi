package com.xiangshangban.transit_service.bean;

/**
 * 模板相关联的图片表
 * @author mian
 *
 */
public class Image {
	//编号
	private String id;
	//图片名称
	private String imgName;
	//图片的URL访问路径
	private String imgUrl;
	//图片的类型（preview:标准模板预览图、back_show:模板背景图<展示>、back_visit:模板背景图<来访>、back_festival_xx(节日节气名称):模板背景图<节日>、logo：公司logo图片、qr_code:设备二维码、bell_btn：铃铛按钮图、bell_back:铃铛背景图、font_frame：文字边框图）
	private String imgType;
	//relate:当前图片关联的其它图片ID（背景图关联的门铃按钮图）
	private String relate;
	//ripple_color:水波纹颜色
	private String rippleColor;
	//状态   0 可用   1 不可用
	private String status;
	
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

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

	public String getRippleColor() {
		return rippleColor;
	}

	public void setRippleColor(String rippleColor) {
		this.rippleColor = rippleColor;
	}

	public Image(String id, String imgName, String imgUrl, String imgType, String relate, String rippleColor,
			String status) {
		super();
		this.id = id;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
		this.imgType = imgType;
		this.relate = relate;
		this.rippleColor = rippleColor;
		this.status = status;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

}
