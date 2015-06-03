package com.cml.product.home.ui;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.util.DisplayUtil;
import com.cml.product.home.util.PrefUtil;

/**
 * 分类App信息，返回TableRow
 * 
 * @author Administrator
 *
 */
public class CategoryItemView implements OnClickListener, OnLongClickListener {

	private static final String TAG = "CategoryItemView";

	/**
	 * app点击事件与长按事件
	 * 
	 * @author teamlab
	 *
	 */
	public static interface OnItemTouchListener {

		void onClick(View v, AppModel data);

		boolean onLongClick(View v, AppModel data);
	}

	private Context context;
	private List<AppModel> data;
	private PackageManager pm;
	private OnItemTouchListener listener;
	private int appWidth;
	private int tableRowHeight;

	public CategoryItemView(Context context, List<AppModel> data, int width,
			int height, OnItemTouchListener listener) {
		this.context = context;
		this.data = data;
		this.listener = listener;
		pm = context.getPackageManager();

		appWidth = width / PrefUtil.getAppColumnCount(context);
		tableRowHeight = height / PrefUtil.getAppRowCount(context);
	}

	public TableRow getTableRow(int start, int end) {

		// 一行显示的app
		TableRow row = new TableRow(context);

		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT, tableRowHeight);
		row.setLayoutParams(params);
		row.setBackgroundColor(Color.GREEN);

		LayoutInflater inflator = LayoutInflater.from(context);

		// 为每个app设置名称和图标
		for (int i = start; i < end; i++) {

			AppModel app = data.get(i);

			View appView = inflator.inflate(R.layout.view_app, null);

			TextView appNameView = (TextView) appView
					.findViewById(R.id.app_name);
			ImageView appIconView = (ImageView) appView
					.findViewById(R.id.app_icon);

			appNameView.setText(app.getAppName());

			try {
				Drawable icon = pm.getApplicationIcon(app.getPackageName());
				appIconView.setImageDrawable(icon);
			} catch (NameNotFoundException e) {
				Log.e(TAG, "load app img:" + e.getMessage());
			}

			// TableLayout.LayoutParams
			TableRow.LayoutParams itemParams = new TableRow.LayoutParams(
					appWidth, tableRowHeight);
			itemParams.gravity = Gravity.CENTER;

			appView.setTag(data.get(i));
			appView.setOnClickListener(this);
			appView.setOnLongClickListener(this);

			row.addView(appView, itemParams);

		}

		return row;
	}

	@Override
	public boolean onLongClick(View v) {

		if (null == listener) {
			return false;
		}

		return listener.onLongClick(v, (AppModel) v.getTag());
	}

	@Override
	public void onClick(View v) {
		if (null != listener) {
			listener.onClick(v, (AppModel) v.getTag());
		}

	}

}
