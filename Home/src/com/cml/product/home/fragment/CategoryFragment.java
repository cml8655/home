package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cml.product.home.R;
import com.cml.product.home.activity.HomeActivity;
import com.cml.product.home.constant.IntentAction;
import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;
import com.cml.product.home.fragment.helper.AppItemTouchHelper;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.ui.CategoryItemLayout;
import com.cml.product.home.util.DisplayUtil;

public class CategoryFragment extends BaseFragment {

	public static final String ARGUMENT_TITLE = "CategoryFragment.ARGUMENT_TITLE";
	public static final String ARGUMENT_TYPE = "CategoryFragment.ARGUMENT_TYPE";

	private static final Integer DEFAULT_PADDING = 30;

	// 一行显示app的数量
	private static final Integer ROW_COUNT = 4;
	private static final Integer LOADER_ID = 2001;

	private HomeActivity homeActivity;
	private CategoryItemLayout appContainer;
	private LoaderCallbacks<Cursor> loaderCallback;
	private BroadcastReceiver appChangeReceiver;// app 卸载/安装监听
	private PackageManager pm;

	private String title;
	private Integer type;

	private int fragmentWidth;
	private int fragmentHeight;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		homeActivity = (HomeActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getArguments();
		if (null != extras) {
			title = extras.getString(ARGUMENT_TITLE);
			type = extras.getInt(ARGUMENT_TYPE);
		}
		fragmentWidth = DisplayUtil.getWindowWidth(getActivity());
		fragmentHeight = DisplayUtil.getWindowHeight(getActivity())
				- getResources().getDimensionPixelSize(R.dimen.main_title)
				- getResources().getDimensionPixelSize(R.dimen.main_footer)
				- DEFAULT_PADDING;
		// 初始化app变化监听
		appChangeReceiver = new AppChangeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(IntentAction.Broadcast.APP_INSTALLED);
		filter.addAction(IntentAction.Broadcast.APP_UNINSTALLED);
		getActivity().registerReceiver(appChangeReceiver, filter);

		pm = getActivity().getPackageManager();
	}

	@Override
	public void onDestroy() {
		// 取消app变化监听
		if (null != appChangeReceiver) {
			getActivity().unregisterReceiver(appChangeReceiver);
		}

		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_categorys, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		appContainer = (CategoryItemLayout) view
				.findViewById(R.id.category_container);
		
		
		appContainer.setListener(new AppItemTouchHelper(getActivity()));

		if (null != homeActivity) {
			homeActivity.setCategoryTitle(title);
		}
		// 加载app数据
		loaderCallback = new AppLoaderCallback();
		getLoaderManager()
				.initLoader(LOADER_ID, getArguments(), loaderCallback);
	}

	/**
	 * app卸载，安装后更新
	 * 
	 * @author teamlab
	 *
	 */
	private class AppChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			getLoaderManager().restartLoader(LOADER_ID, getArguments(),
					loaderCallback);
		}

	}

	/***
	 * 加载app数据loader
	 * 
	 * @author teamlab
	 *
	 */
	private class AppLoaderCallback implements LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			return new CursorLoader(
					getActivity(),
					AppContract.URI,
					AppContract.QUERY_ALL,
					ColumnDef.App.CATEGORY + "= ?",
					new String[] { String.valueOf(args.getInt(ARGUMENT_TYPE)) },
					null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

			if (null == data) {
				return;
			}

			List<AppModel> appList = new ArrayList<AppModel>();

			// 将数据封装成list对象
			while (data.moveToNext()) {

				String appName = data.getString(data
						.getColumnIndex(ColumnDef.App.NAME));
				String packageName = data.getString(data
						.getColumnIndex(ColumnDef.App.PACKAGE));
				Integer iconRes = data.getInt(data
						.getColumnIndex(ColumnDef.App.ICON));

				Log.e(TAG, "====>" + appName + "," + packageName);

				if (null == pm.getLaunchIntentForPackage(packageName)) {
					// 删除db数据
					getActivity().sendBroadcast(
							new Intent(IntentAction.Broadcast.APP_REMOVED, Uri
									.parse(packageName)));
					continue;
				}

				AppModel appModel = new AppModel(packageName, iconRes, appName);
				appList.add(appModel);
			}

			appContainer.removeAllViews();
			appContainer.addItems(appList);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}

	}

}
