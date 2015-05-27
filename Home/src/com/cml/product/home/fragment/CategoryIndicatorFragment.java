package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		List<Indicator> data = new ArrayList<CategoryIndicatorView.Indicator>();

		for (int i = 0; i < 12; i++) {
			Indicator indicator = new Indicator();
			indicator.title = "title" + i;
			data.add(indicator);
		}

		CategoryIndicatorView view = new CategoryIndicatorView(getActivity(),
				data, IndicatorDirection.RIGHT);

		view.setItemLongClickListener(itemLongClick);
		view.setItemClickListener(itemClick);

		Log.d(TAG, "fragment 初始化完成！");

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	}

}
