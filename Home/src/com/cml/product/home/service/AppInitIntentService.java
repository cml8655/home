package com.cml.product.home.service;

import java.util.ArrayList;
import java.util.List;

import com.cml.product.home.constant.Constant;
import com.cml.product.home.db.helper.AppHelper;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppInitIntentService extends IntentService {

	private static final String TAG = AppInitIntentService.class
			.getSimpleName();

	private static final String PACKAGE_GAME = "game";

	public AppInitIntentService() {
		super("AppInitIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// 加载所有app
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> apps = pm.getInstalledApplications(0);

		for (ApplicationInfo app : apps) {

			String appName = app.loadLabel(pm).toString();
			String packageName = app.packageName;

			Integer categoryId = Constant.AppType.TYPE_ETC;
			
			// 系统
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				categoryId = Constant.AppType.TYPE_SYSTEM;
			} else if (packageName.contains(PACKAGE_GAME)) {
				categoryId = Constant.AppType.TYPE_SYSTEM;
			}

			Log.e(TAG, "pn:" + app.packageName + "," + app.loadLabel(pm) + ","
					+ app.flags + "," + app.enabled);
		}

	}

}
