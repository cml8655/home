package com.cml.product.home.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppInitIntentService extends IntentService {

	private static final String TAG = AppInitIntentService.class
			.getSimpleName();

	public AppInitIntentService() {
		super("AppInitIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// º”‘ÿÀ˘”–app
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> apps = pm.getInstalledApplications(0);

		for (ApplicationInfo app : apps) {
			Log.e(TAG, "pn:" + app.packageName + "," + app.loadLabel(pm) + ","
					+ app.flags + "," + app.enabled);
		}

	}

}
