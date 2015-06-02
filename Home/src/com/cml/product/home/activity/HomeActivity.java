package com.cml.product.home.activity;

import java.util.List;

import android.app.DialogFragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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

		@Override
		protected Boolean doInBackground(Void... params) {

			// 获取所有已安装的程序
			PackageManager pm = getPackageManager();
			List<ApplicationInfo> apps = pm.getInstalledApplications(0);

			AppHelper helper = new AppHelper(getApplicationContext());

			for (ApplicationInfo app : apps) {

				String appName = app.loadLabel(pm).toString();
				String packageName = app.packageName;

				Integer categoryId = Constant.AppType.TYPE_ETC;
				Integer appFlg = Constant.AppFlg.FLAG_ETC;

				// 系统app
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
					categoryId = Constant.AppType.TYPE_SYSTEM;
					appFlg = Constant.AppFlg.FLAG_SYSTEM;
				} else if (packageName.contains(PACKAGE_GAME)) {
					categoryId = Constant.AppType.TYPE_GAME;
				}

				long id = helper.insertApp(appName, packageName, app.icon,
						categoryId, appFlg);

				Log.e(TAG, "insert app values :" + id+",flags:"+app.flags+",name:"+appName);
			}

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
