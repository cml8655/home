package com.cml.product.home.receiver;

import com.cml.product.home.util.ToastUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppInstallReceiver extends BroadcastReceiver {
	private static final String TAG = AppInstallReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(TAG, "AppInstallReceiver===>" + intent.getAction());

		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
			String packageName = intent.getDataString();
			Log.d(TAG, "AppInstallReceiver===>" + packageName);
			ToastUtil.show(context, "安装app：" + packageName);
		}
	}

}
