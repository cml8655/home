package com.cml.product.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cml.product.home.ui.CategoryIndicatorView;
import com.cml.product.home.ui.CategoryIndicatorView.Indicator;
import com.cml.product.home.ui.CategoryIndicatorView.IndicatorDirection;

/**
 * 分组标签fragment
 * 
 * @author teamlab
 *
 */
public class CategoryIndicatorFragment extends BaseFragment {

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

		Log.d(TAG, "fragment 初始化完成！");

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	}

}
