package com.cml.product.home.activity;

import android.os.Bundle;

import com.cml.product.home.R;
import com.cml.product.home.fragment.CategoryIndicatorFragment;

public class HomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getFragmentManager().beginTransaction()
				.replace(R.id.container, new CategoryIndicatorFragment())
				.commit();

	}
	
}
