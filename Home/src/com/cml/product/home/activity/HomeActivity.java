package com.cml.product.home.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.fragment.CategoryIndicatorFragment;
import com.cml.product.home.fragment.dialog.LoadingFragment;

public class HomeActivity extends BaseActivity {

	private TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO 第一次启动，需要进行app初始化
		
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
