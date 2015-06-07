package com.cml.product.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cml.product.home.constant.IntentAction;
import com.cml.product.home.db.helper.AppHelper;

public class AppUninstallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// app已经卸载了，删除db数据
		if (IntentAction.Broadcast.APP_REMOVED.equals(intent.getAction())) {
			new AppHelper(context).delApp(intent.getDataString());
		} else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {

			String packageName = intent.getDataString().replace("package:", "");
			// 删除本地app信息
			new AppHelper(context).delApp(packageName);

			// 删除成功，通知ui变化
			context.sendBroadcast(new Intent(
					IntentAction.Broadcast.APP_UNINSTALLED));
		}
	}
}
