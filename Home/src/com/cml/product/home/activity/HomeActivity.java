package com.cml.product.home.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.cml.product.home.R;
import com.cml.product.home.ui.PushShowView;

public class HomeActivity extends BaseActivity {
	private LinearLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		container = (LinearLayout) findViewById(R.id.container);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
				LayoutParams.WRAP_CONTENT);
		params.weight = 1;

		container.addView(new PushShowView(this).getView(), params);
		container.addView(new PushShowView(this).getView(), params);
		container.addView(new PushShowView(this).getView(), params);
		container.addView(new PushShowView(this).getView(), params);
		container.addView(new PushShowView(this).getView(), params);

	}
}
