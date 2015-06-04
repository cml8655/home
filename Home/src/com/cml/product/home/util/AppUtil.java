package com.cml.product.home.util;

import com.cml.product.home.constant.Constant;
import com.cml.product.home.model.AppModel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class AppUtil {
	private static final String TAG = "AppUtil";

	/**
	 * 判断app是否为系统应用
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApp(Context context, String packageName) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return false;
	}

	public static AppModel getAppModel(ApplicationInfo info, Context context) {

		PackageManager pm = context.getPackageManager();

		String pkgName = info.packageName; // 获得应用程序的包名
		String appName = info.loadLabel(pm).toString(); // 获得应用程序的Label

		Integer categoryId = Constant.AppType.TYPE_ETC;
		Integer appFlg = Constant.AppFlg.FLAG_ETC;

		// 系统应用
		if (isSystemApp(context, pkgName)) {
			categoryId = Constant.AppType.TYPE_SYSTEM;
			appFlg = Constant.AppFlg.FLAG_SYSTEM;
		}

		return new AppModel(pkgName, info.icon, appName, categoryId, appFlg);

	}

}
