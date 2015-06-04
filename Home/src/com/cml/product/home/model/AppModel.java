package com.cml.product.home.model;

public class AppModel {
	private String packageName;
	private int iconRes;
	private String appName;
	private Integer categoryId;
	private Integer appFlg;

	public AppModel(String packageName, int iconRes, String appName,
			Integer categoryId, Integer appFlg) {
		this.packageName = packageName;
		this.iconRes = iconRes;
		this.appName = appName;
		this.categoryId = categoryId;
		this.appFlg = appFlg;
	}

	public AppModel(String packageName, int iconRes, String appName) {
		this.packageName = packageName;
		this.iconRes = iconRes;
		this.appName = appName;
	}

	public AppModel() {
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getAppFlg() {
		return appFlg;
	}

	public void setAppFlg(Integer appFlg) {
		this.appFlg = appFlg;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
