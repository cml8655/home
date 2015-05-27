package com.cml.product.home.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.fragment.CategoryIndicatorFragment;

public class HomeActivity extends BaseActivity {

	private TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		titleView = (TextView) findViewById(R.id.home_title);

		getFragmentManager().beginTransaction()
				.replace(R.id.container, new CategoryIndicatorFragment())
				.commit();

	}

	public void setCategoryTitle(String text) {
		titleView.setText(text);
	}

}
