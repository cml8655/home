package com.cml.product.home.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

	private static final String KEY_LAUNCH = "PrefUtil.KEYLAUNCH";

	public boolean isFirstLaunch(String version, Context context) {
		return getPref(KEY_LAUNCH, context) == null;
	}

	public static String getPref(String key, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		return pref.getString(key, null);
	}

}
