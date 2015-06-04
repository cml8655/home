package com.cml.product.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppInstallReceiver extends BroadcastReceiver {
	private static final String TAG = AppInstallReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(TAG, "AppInstallReceiver===>" + intent.getAction());

		if (Intent.ACTION_INSTALL_PACKAGE.equals(intent.getAction())) {
			//TODO 进行新app的操作
		}
	}

}
