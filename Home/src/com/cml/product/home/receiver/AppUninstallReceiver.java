package com.cml.product.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cml.product.home.constant.IntentAction;
import com.cml.product.home.db.helper.AppHelper;
import com.cml.product.home.util.ToastUtil;

public class AppUninstallReceiver extends BroadcastReceiver {

	private static final String TAG = "AppUninstallReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		ToastUtil.show(context, "app卸载：");
		Log.d(TAG, "接收到卸载app==》");

		// 接收卸载广播
		if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {

			String packageName = intent.getDataString();
			// 删除本地app信息
			long delCount = new AppHelper(context).delApp(packageName);

			// 删除成功，通知ui变化
			if (delCount > 0) {
				context.sendBroadcast(new Intent(
						IntentAction.Broadcast.APP_UNINSTALLED));
				Log.d(TAG, packageName + "卸载，db更新成功");
				ToastUtil.show(context, "app卸载：卸载，db更新成功");
			} else {
				Log.d(TAG, packageName + "卸载，db更新失败");
				ToastUtil.show(context, "app卸载：卸载，卸载，db更新失败");
			}
		}
	}

}
