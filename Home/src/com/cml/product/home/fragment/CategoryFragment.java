package com.cml.product.home.fragment;

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
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.activity.HomeActivity;
import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;

public class CategoryFragment extends BaseFragment {

	public static final String ARGUMENT_TITLE = "CategoryFragment.ARGUMENT_TITLE";
	public static final String ARGUMENT_TYPE = "CategoryFragment.ARGUMENT_TYPE";

	private static final Integer LOADER_ID = 2001;

	private HomeActivity homeActivity;

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
		TextView titleView = (TextView) view.findViewById(R.id.title);
		TextView typeView = (TextView) view.findViewById(R.id.type);

		titleView.setText("title:" + title);
		typeView.setText("type:" + type);

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
					ColumnDef.App.CATEGORY + "= ?",
					new String[] { String.valueOf(args.getInt(ARGUMENT_TYPE)) },
					null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

			if (null == data) {
				return;
			}

			while (data.moveToNext()) {
				String appName = data.getString(data
						.getColumnIndex(ColumnDef.App.NAME));
				Log.d(TAG, "appName:" + appName);
			}

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}

	}

}
