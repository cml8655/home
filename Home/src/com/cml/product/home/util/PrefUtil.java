package com.cml.product.home.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

	public static final String KEY_LAUNCH = "PrefUtil.KEYLAUNCH";

	public static boolean isFirstLaunch(Context context) {
		return getPref(KEY_LAUNCH, context) == null;
	}

	public static String getPref(String key, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		return pref.getString(key, null);
	}

	public static void setPref(Context context, String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		pref.edit().putString(key, value).apply();
	}
}
