package com.cml.product.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.cml.product.home.constant.IntentAction;
import com.cml.product.home.db.helper.AppHelper;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.util.AppUtil;

public class AppInstallReceiver extends BroadcastReceiver {

	private static final String TAG = AppInstallReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(TAG, "AppInstallReceiver===>" + intent.getAction());

		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {

			try {
				String packageName = intent.getDataString().replace("package:",
						"");

				// 获取app信息
				PackageManager pm = context.getPackageManager();

				PackageInfo info = pm.getPackageInfo(packageName,
						PackageManager.GET_ACTIVITIES);

				// 插入到DB
				AppModel model = AppUtil.getAppModel(info.applicationInfo,
						context);
				long result = new AppHelper(context).insertApp(model);
				// 插入成功,发送广播通知ui变化
				if (result != -1) {
					context.sendBroadcast(new Intent(
							IntentAction.Broadcast.APP_INSTALLED));
				}
			} catch (NameNotFoundException e) {
				Log.e(TAG, "app没有安装==>" + e.getMessage());
			}
		}
	}
}
