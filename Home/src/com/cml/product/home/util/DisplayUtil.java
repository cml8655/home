package com.cml.product.home.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
	/**
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();

		return metrics.widthPixels;

	}
}
