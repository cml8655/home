package com.cml.product.home.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.cml.product.home.R;
import com.cml.product.home.constant.Constant;
import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.ui.CategoryItemLayout;
import com.cml.product.home.util.ToastUtil;

public class HomeActivity2 extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	private CategoryItemLayout appContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		appContainer = (CategoryItemLayout) findViewById(R.id.category_container);

		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return new CursorLoader(this, AppContract.URI, AppContract.QUERY_ALL,
				ColumnDef.App.CATEGORY + "= ? limit 50",
				new String[] { String.valueOf(Constant.AppType.TYPE_ETC) },
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

		ToastUtil.show(this,
				"app===》" + appList.size() + ",," + data.getCount());
		appContainer.addItems(appList);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}
