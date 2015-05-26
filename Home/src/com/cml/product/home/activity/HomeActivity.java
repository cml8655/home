package com.cml.product.home.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cml.product.home.R;

public class HomeActivity extends BaseActivity {

	private ListView verticalMenuList;

	private final class MenuClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			view.clearAnimation();

			ObjectAnimator animator = new ObjectAnimator();

			animator.setDuration(500);
			animator.setTarget(view);
			animator.setPropertyName("x");
			float start = view.getX();

			if ("moved".equals(view.getTag())) {
				view.setTag(null);
				animator.setFloatValues(start, start - 200);
			} else {
				animator.setFloatValues(start, start + 200);
				view.setTag("moved");
			}

			animator.start();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		verticalMenuList = (ListView) findViewById(R.id.vertical_menu_list);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (int i = 0; i < 10; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("v", "°∂°∂ ∑÷¿‡XXX" + i);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "v" },
				new int[] { android.R.id.text1 });
		verticalMenuList.setAdapter(adapter);
		verticalMenuList.setOnItemClickListener(new MenuClickListener());

	}
}
