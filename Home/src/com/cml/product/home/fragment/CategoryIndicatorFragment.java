package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cml.product.home.R;
import com.cml.product.home.db.contract.CategoryContract;
import com.cml.product.home.db.def.ColumnDef;
import com.cml.product.home.ui.CategoryIndicatorView;
import com.cml.product.home.ui.CategoryIndicatorView.Indicator;
import com.cml.product.home.ui.CategoryIndicatorView.IndicatorDirection;
import com.cml.product.home.ui.CategoryIndicatorView.OnItemClickListener;
import com.cml.product.home.ui.CategoryIndicatorView.OnItemLongClickListener;
import com.cml.product.home.util.ToastUtil;

/**
 * 菜单
 * 
 * @author teamlab
 *
 */
public class CategoryIndicatorFragment extends BaseFragment {

	private static final Integer LOADER_CATEGORY = 1001;

	private CategoryIndicatorView indicatorView;

	private OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onClick(View v, String title, Integer type) {
			Toast.makeText(getActivity(), "click��" + title + ":" + type,
					Toast.LENGTH_SHORT).show();
			replaceContainer(title, type);

		}
	};

	private OnItemLongClickListener itemLongClick = new OnItemLongClickListener() {

		@Override
		public boolean onLongClick(View v, String title, Integer type) {
			Toast.makeText(getActivity(), "onLongClick��" + title + ":" + type,
					Toast.LENGTH_SHORT).show();
			return true;
		}
	};

	private void replaceContainer(String title, Integer type) {

		FragmentTransaction tr = getFragmentManager().beginTransaction();

		CategoryFragment target = new CategoryFragment();

		Bundle extra = new Bundle();
		extra.putString(CategoryFragment.ARGUMENT_TITLE, title);
		extra.putInt(CategoryFragment.ARGUMENT_TYPE, type);
		target.setArguments(extra);

		tr.replace(R.id.indicator_container, target).commit();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_indicators, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		indicatorView = (CategoryIndicatorView) view
				.findViewById(R.id.category_indicator);
		indicatorView.setItemLongClickListener(itemLongClick);
		indicatorView.setItemClickListener(itemClick);
		indicatorView.setDirection(IndicatorDirection.RIGHT);

		getLoaderManager().initLoader(LOADER_CATEGORY, null,
				new CategoryLoaderCallback());

	}

	private class CategoryLoaderCallback implements LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			if (id == LOADER_CATEGORY) {
				return new CursorLoader(getActivity(), CategoryContract.URI,
						CategoryContract.QUERY_ALL, null, null, null);
			}

			return null;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

			ToastUtil.show(getActivity(), "返回数据啦：" + data);

			if (data == null || data.isClosed()) {
				return;
			}

			List<Indicator> categories = new ArrayList<CategoryIndicatorView.Indicator>();

			while (data.moveToNext()) {

				String name = data.getString(data
						.getColumnIndex(ColumnDef.Category.NAME));
				Integer type = data.getInt(data
						.getColumnIndex(ColumnDef.Category._ID));

				Indicator indicator = new Indicator();
				indicator.title = name;
				indicator.type = type;
				categories.add(indicator);

			}

			indicatorView.setData(categories);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}

	}

}
