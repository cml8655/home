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
			//TODO step1 获取app信息
			//TODO step2 插入到数据库
			//TODO step3 发出通知，刷新页面
			Log.d(TAG, "AppInstallReceiver===>" + packageName);
			ToastUtil.show(context, "安装app：" + packageName);
		}
	}

}
