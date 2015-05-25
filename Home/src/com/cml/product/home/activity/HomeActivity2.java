package com.cml.product.home.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cml.product.home.R;

public class HomeActivity2 extends BaseActivity {

	private LinearLayout header;
	private LinearLayout item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		header = (LinearLayout) findViewById(R.id.layout_header);
		item = (LinearLayout) findViewById(R.id.category_item);

		OnItemClickListener listener = new OnItemClickListener();

		item.setOnClickListener(listener);
		header.setOnClickListener(listener);
	}

	private class OnItemClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			Toast.makeText(
					getApplicationContext(),
					(v.getId() == R.id.layout_header) + ",,"
							+ (v.getId() == R.id.category_item),
					Toast.LENGTH_LONG).show();

			Log.d("dddd", (v.getId() == R.id.layout_header) + ",,"
					+ (v.getId() == R.id.category_item));

			// titleµã»÷
			if (v.getId() == R.id.layout_header) {
				header.setVisibility(View.GONE);
				item.setVisibility(View.VISIBLE);
			} else {
				header.setVisibility(View.VISIBLE);
				item.setVisibility(View.GONE);
			}
		}
	}
}
