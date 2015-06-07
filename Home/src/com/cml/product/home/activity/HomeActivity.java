package com.cml.product.home.activity;

import java.util.ArrayList;
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
import com.cml.product.home.model.AppModel;
import com.cml.product.home.util.AppUtil;
import com.cml.product.home.util.PrefUtil;

public class HomeActivity extends BaseActivity {

	private TextView titleView;
	private AsyncTask<Void, Void, Boolean> appInitTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		titleView = (TextView) findViewById(R.id.home_title);

		// 加载app数据
		DialogFragment loadingDialog = new LoadingFragment();
		loadingDialog.setCancelable(false);
		loadingDialog.show(getFragmentManager(), "ss");
		appInitTask = new AppInitTask(loadingDialog);
		appInitTask.execute();
	}

	public void setCategoryTitle(String text) {
		titleView.setText(text);
	}

	private class AppInitTask extends AsyncTask<Void, Void, Boolean> {

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

			List<AppModel> apps = new ArrayList<AppModel>(resolveInfos.size());

			if (resolveInfos.size() > 0) {

				for (ResolveInfo reInfo : resolveInfos) {

					String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
					String appName = reInfo.loadLabel(pm).toString(); // 获得应用程序的Label

					Integer categoryId = Constant.AppType.TYPE_ETC;
					Integer appFlg = Constant.AppFlg.FLAG_ETC;

					// 系统应用
					if (AppUtil.isSystemApp(getApplicationContext(), pkgName)) {
						categoryId = Constant.AppType.TYPE_SYSTEM;
						appFlg = Constant.AppFlg.FLAG_SYSTEM;
					}

					AppModel model = new AppModel(pkgName, reInfo.icon,
							appName, categoryId, appFlg);
					apps.add(model);
				}
				// 清空app信息
				helper.clearApp();
				helper.insertApp(apps);
			}

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			this.queryAppInfo();
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {

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
