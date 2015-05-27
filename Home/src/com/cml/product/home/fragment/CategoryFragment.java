package com.cml.product.home.fragment;

import com.cml.product.home.R;
import com.cml.product.home.activity.HomeActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CategoryFragment extends BaseFragment {

	public static final String ARGUMENT_TITLE = "CategoryFragment.ARGUMENT_TITLE";
	public static final String ARGUMENT_TYPE = "CategoryFragment.ARGUMENT_TYPE";

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
	}

}
