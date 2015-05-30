package com.cml.product.home.model;

public class AppModel {
	private String packageName;
	private int iconRes;
	private String appName;

	public AppModel(String packageName, int iconRes, String appName) {
		super();
		this.packageName = packageName;
		this.iconRes = iconRes;
		this.appName = appName;
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
