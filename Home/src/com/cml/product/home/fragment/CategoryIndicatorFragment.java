package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cml.product.home.R;
import com.cml.product.home.ui.CategoryIndicatorView;
import com.cml.product.home.ui.CategoryIndicatorView.Indicator;
import com.cml.product.home.ui.CategoryIndicatorView.IndicatorDirection;
import com.cml.product.home.ui.CategoryIndicatorView.OnItemClickListener;
import com.cml.product.home.ui.CategoryIndicatorView.OnItemLongClickListener;

/**
 * 分组标签fragment
 * 
 * @author teamlab
 *
 */
public class CategoryIndicatorFragment extends BaseFragment {

	private OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onClick(View v, String title, Integer type) {
			Toast.makeText(getActivity(), "click：" + title + ":" + type,
					Toast.LENGTH_SHORT).show();
			replaceContainer(title, type);

		}
	};

	private OnItemLongClickListener itemLongClick = new OnItemLongClickListener() {

		@Override
		public boolean onLongClick(View v, String title, Integer type) {
			Toast.makeText(getActivity(), "onLongClick：" + title + ":" + type,
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

		List<Indicator> data = new ArrayList<CategoryIndicatorView.Indicator>();

		for (int i = 0; i < 12; i++) {
			Indicator indicator = new Indicator();
			indicator.title = "title" + i;
			indicator.type = i;
			data.add(indicator);
		}

		// TODO 添加菜单栏显示，这里需要进行异步操作
		CategoryIndicatorView indicator = (CategoryIndicatorView) view
				.findViewById(R.id.category_indicator);
		indicator.setDirection(IndicatorDirection.RIGHT);
		indicator.setData(data);

		indicator.setItemLongClickListener(itemLongClick);
		indicator.setItemClickListener(itemClick);

		Log.d(TAG, "fragment 初始化完成！");
	}

}
