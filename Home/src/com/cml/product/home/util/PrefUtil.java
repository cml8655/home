package com.cml.product.home.util;

import com.cml.product.home.constant.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

	public static final String KEY_LAUNCH = "PrefUtil.KEYLAUNCH";
	public static final String KEY_ROW_COUNT = "PrefUtil.ROWCOUNT";
	public static final String KEY_COLUMN_COUNT = "PrefUtil.COLUMNCOUNT";

	public static void setAppRowCount(Context context, int count) {
		setPref(context, KEY_ROW_COUNT, count);
	}

	/**
	 * 获取APP行数
	 * @param context
	 * @return
	 */
	public static Integer getAppRowCount(Context context) {

		Integer count = getIntPref(KEY_ROW_COUNT, context);

		if (count == -1) {
			setAppRowCount(context, Constant.Common.COUNT_APP_ROW);
			return Constant.Common.COUNT_APP_ROW;
		}

		return count;
	}

	/**
	 * 获取APP列数
	 * 
	 * @param context
	 * @return
	 */
	public static Integer getAppColumnCount(Context context) {

		Integer count = getIntPref(KEY_COLUMN_COUNT, context);

		if (count == -1) {
			setPref(context, KEY_COLUMN_COUNT, Constant.Common.COUNT_APP_COLUMN);
			return Constant.Common.COUNT_APP_COLUMN;
		}

		return count;
	}

	public static boolean isFirstLaunch(Context context) {
		return getPref(KEY_LAUNCH, context) == null;
	}

	public static String getPref(String key, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		return pref.getString(key, null);
	}

	public static Integer getIntPref(String key, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		return pref.getInt(key, -1);
	}

	public static void setPref(Context context, String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		pref.edit().putString(key, value).apply();
	}

	public static void setPref(Context context, String key, Integer value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		pref.edit().putInt(key, value).apply();
	}
}
