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
			//TODO step1 删除本地app信息
			//TODO step2 发出通知，提示刷新界面
			ToastUtil.show(context, "新卸载app："+packageName);
		}
	}

}
