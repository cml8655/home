package com.cml.product.home.ui;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.model.AppModel;

/**
 * 分类App信息，返回TableRow
 * 
 * @author Administrator
 *
 */
public class CategoryItemView {

	private static final String TAG = "CategoryItemView";

	private Context context;
	private List<AppModel> data;
	private PackageManager pm;

	public CategoryItemView(Context context, List<AppModel> data) {
		this.context = context;
		this.data = data;
		pm = context.getPackageManager();
	}

	public TableRow getTableRow(int start, int end) {

		// 一行显示的app
		TableRow row = new TableRow(context);

		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT, 0);
		params.weight = 1;
		row.setLayoutParams(params);

		LayoutInflater inflator = LayoutInflater.from(context);

		// 为每个app设置名称和图标
		for (int i = start; i < end; i++) {

			AppModel app = data.get(i);

			View appView = inflator.inflate(R.layout.view_app, null);

			TextView appNameView = (TextView) appView
					.findViewById(R.id.app_name);
			ImageView appIconView = (ImageView) appView
					.findViewById(R.id.app_icon);

			appNameView.setText(app.getAppName() + ":" + i);

			try {
				Drawable icon = pm.getApplicationIcon(app.getPackageName());
				appIconView.setImageDrawable(icon);
			} catch (NameNotFoundException e) {
				Log.e(TAG, "load app img:" + e.getMessage());
			}

			// TableLayout.LayoutParams
			TableRow.LayoutParams itemParams = new TableRow.LayoutParams(0,
					TableRow.LayoutParams.MATCH_PARENT, 1);

			row.addView(appView, itemParams);

		}

		return row;
	}

}
