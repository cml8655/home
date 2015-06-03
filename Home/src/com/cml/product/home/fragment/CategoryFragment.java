package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.cml.product.home.R;
import com.cml.product.home.activity.HomeActivity;
import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;
import com.cml.product.home.fragment.helper.AppItemTouchHelper;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.ui.CategoryItemView;

public class CategoryFragment extends BaseFragment {

	public static final String ARGUMENT_TITLE = "CategoryFragment.ARGUMENT_TITLE";
	public static final String ARGUMENT_TYPE = "CategoryFragment.ARGUMENT_TYPE";

	// 一行显示app的数量
	private static final Integer ROW_COUNT = 4;
	private static final Integer LOADER_ID = 2001;

	private HomeActivity homeActivity;
	private TableLayout appContainer;

	private String title;
	private Integer type;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_categorys, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		appContainer = (TableLayout) view.findViewById(R.id.category_container);

		if (null != homeActivity) {
			homeActivity.setCategoryTitle(title);
		}

		getLoaderManager().initLoader(LOADER_ID, getArguments(),
				new AppLoaderCallback());
	}

	private class AppLoaderCallback implements LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			return new CursorLoader(
					getActivity(),
					AppContract.URI,
					AppContract.QUERY_ALL,
					ColumnDef.App.CATEGORY + "= ? ",
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

				AppModel appModel = new AppModel(packageName, iconRes, appName);
				appList.add(appModel);
			}
			
			int len = appList.size();

			CategoryItemView itemView = new CategoryItemView(getActivity(),
					appList, new AppItemTouchHelper(getActivity()));

			// 4个一组，添加到界面上显示
			for (int i = 0; i < len; i += ROW_COUNT) {

				Log.d(TAG, "添加row到显示上。。。" + i);

				if (i + ROW_COUNT >= len) {
					// 结束
					View appViews = itemView.getTableRow(i, len);
					appContainer.addView(appViews);
					break;
				}

				View appViews = itemView.getTableRow(i, i + ROW_COUNT);
				appContainer.addView(appViews);
			}

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}

	}

}
