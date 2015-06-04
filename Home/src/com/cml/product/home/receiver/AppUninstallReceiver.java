package com.cml.product.home.receiver;

import com.cml.product.home.util.ToastUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppUninstallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 接收卸载广播
		if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
			String packageName = intent.getDataString();
			ToastUtil.show(context, "新卸载app："+packageName);
		}
	}

}
