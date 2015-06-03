package com.cml.product.home.activity;

import java.util.List;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.constant.Constant;
import com.cml.product.home.db.helper.AppHelper;
import com.cml.product.home.fragment.CategoryIndicatorFragment;
import com.cml.product.home.fragment.dialog.LoadingFragment;
import com.cml.product.home.util.PrefUtil;

public class HomeActivity extends BaseActivity {

	private TextView titleView;
	private AsyncTask<Void, Void, Boolean> appInitTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		titleView = (TextView) findViewById(R.id.home_title);

		boolean isFistLaunch = PrefUtil.isFirstLaunch(this);

		// 第一次运行，加载app数据
		if (isFistLaunch) {
			DialogFragment loadingDialog = new LoadingFragment();
			loadingDialog.setCancelable(false);
			loadingDialog.show(getFragmentManager(), "ss");
			appInitTask = new AppInitTask(loadingDialog);
			appInitTask.execute();
		} else {
			showMenuContent();
		}
	}

	public void setCategoryTitle(String text) {
		titleView.setText(text);
	}

	private class AppInitTask extends AsyncTask<Void, Void, Boolean> {

		private static final String PACKAGE_GAME = "game";
		private static final String TAG = "AppInitTask";

		private DialogFragment loadingDialog;

		public AppInitTask(DialogFragment loadingDialog) {
			this.loadingDialog = loadingDialog;
		}

		// 获得所有启动Activity的信息，类似于Launch界面
		public void queryAppInfo() {
			AppHelper helper = new AppHelper(getApplicationContext());
			PackageManager pm = getPackageManager(); // 获得PackageManager对象
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			// 通过查询，获得所有ResolveInfo对象.
			List<ResolveInfo> resolveInfos = pm.queryIntentActivities(
					mainIntent, PackageManager.GET_ACTIVITIES);

			// 调用系统排序 ， 根据name排序
			// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
			// Collections.sort(resolveInfos,
			// new ResolveInfo.DisplayNameComparator(pm));

			for (ResolveInfo reInfo : resolveInfos) {
				String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
				String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label

				Integer categoryId = Constant.AppType.TYPE_ETC;
				Integer appFlg = Constant.AppFlg.FLAG_ETC;

				// 系统应用
				if (isSystemApp(pkgName)) {
					categoryId = Constant.AppType.TYPE_SYSTEM;
					appFlg = Constant.AppFlg.FLAG_SYSTEM;
				}

				// // 为应用程序的启动Activity 准备Intent
				// Intent launchIntent = new Intent();
				// launchIntent.setComponent(new ComponentName(pkgName,
				// activityName));

				long id = helper.insertApp(appLabel, pkgName, activityName,
						reInfo.icon, categoryId, appFlg);

				Log.e(TAG, "activityName :" + activityName + ",flags:"
						+ reInfo.activityInfo.flags + ",pkgName:" + pkgName
						+ ",appLabel:" + appLabel + ",,插入ID:" + id);
			}
		}

		private boolean isSystemApp(String packageName) {
			try {
				PackageInfo info = getPackageManager().getPackageInfo(
						packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
				return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
			} catch (NameNotFoundException e) {
				Log.e(TAG, e.getMessage());
			}
			return false;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			this.queryAppInfo();

			// // 获取所有已安装的程序
			// PackageManager pm = getPackageManager();
			// List<ApplicationInfo> apps = pm.getInstalledApplications(0);
			//
			// AppHelper helper = new AppHelper(getApplicationContext());
			//
			// for (ApplicationInfo app : apps) {
			//
			// String appName = app.loadLabel(pm).toString();
			// String packageName = app.packageName;
			//
			// Integer categoryId = Constant.AppType.TYPE_ETC;
			// Integer appFlg = Constant.AppFlg.FLAG_ETC;
			//
			// // 系统app
			// if ((app.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
			// categoryId = Constant.AppType.TYPE_SYSTEM;
			// appFlg = Constant.AppFlg.FLAG_SYSTEM;
			// } else if (packageName.contains(PACKAGE_GAME)) {
			// categoryId = Constant.AppType.TYPE_GAME;
			// }
			//
			// long id = helper.insertApp(appName, packageName, app.icon,
			// categoryId, appFlg);
			//
			// Log.e(TAG, "insert app values :" + id + ",flags:" + app.flags
			// + ",name:" + appName);
			// }

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			Log.d(TAG, "onPostExecute:" + result);

			// 设置初始化完毕
			PrefUtil.setPref(getApplicationContext(), PrefUtil.KEY_LAUNCH,
					"success");

			showMenuContent();

			if (null != loadingDialog) {
				loadingDialog.dismiss();
			}

		}
	}

	private void showMenuContent() {
		// 设置显示面板
		getFragmentManager().beginTransaction()
				.replace(R.id.container, new CategoryIndicatorFragment())
				.commit();

	}

}
