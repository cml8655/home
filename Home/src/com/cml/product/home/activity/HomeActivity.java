package com.cml.product.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.fragment.CategoryIndicatorFragment;
import com.cml.product.home.fragment.dialog.LoadingFragment;
import com.cml.product.home.service.AppInitIntentService;
import com.cml.product.home.util.PrefUtil;

public class HomeActivity extends BaseActivity {

	private TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startService(new Intent(this, AppInitIntentService.class));

		boolean isFistLaunch = PrefUtil.isFirstLaunch(this);

		if (isFistLaunch) {
			// TODO 获取所有app
		}

		new LoadingFragment().show(getFragmentManager(), "ss");

		titleView = (TextView) findViewById(R.id.home_title);

		getFragmentManager().beginTransaction()
				.replace(R.id.container, new CategoryIndicatorFragment())
				.commit();

	}

	public void setCategoryTitle(String text) {
		titleView.setText(text);
	}

}
