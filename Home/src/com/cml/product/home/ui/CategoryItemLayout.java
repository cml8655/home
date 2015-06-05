package com.cml.product.home.ui;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cml.product.home.R;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.util.PrefUtil;

/**
 * app 显示布局文件，可控制app显示风格4*4/4*5
 * 
 * @author teamlab
 *
 */
public class CategoryItemLayout extends ViewGroup {
	private static final String TAG = CategoryItemLayout.class.getSimpleName();

	/** 每行显示的个数 */
	private int rowCount;
	/** 每列显示的个数 */
	private int columnCount;

	public CategoryItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		rowCount = PrefUtil.getAppRowCount(context);
		columnCount = PrefUtil.getAppColumnCount(context);
		setBackgroundColor(Color.GRAY);
	}

	/**
	 * 更新显示布局
	 * 
	 * @param columnCount
	 * @param rowCount
	 */
	public void updateLayoutGroup(int columnCount, int rowCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.requestLayout();
	}

	public void addItems(List<AppModel> data) {

		LayoutInflater inflator = LayoutInflater.from(getContext());
		PackageManager pm = getContext().getPackageManager();

		int len = data.size();

		// 为每个app设置名称和图标
		for (int i = 0; i < len; i++) {

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

			appView.setTag(data.get(i));

			this.addView(appView);
		}
		
		this.requestLayout();

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int viewWidth = getMeasuredWidth();
		int viewHeight = 1000;

		Log.e(TAG, "===>onLayout:" + viewWidth + "," + viewHeight + ",,"
				+ getHeight());

		int horizontalPadding = viewWidth / columnCount;// 水平方向的列间距
		int verticalPadding = viewHeight / rowCount;// 垂直方向的列间距

		int x = horizontalPadding;
		int y = verticalPadding;

		int len = getChildCount();

		// 设置view位置
		for (int i = 0; i < len; i++) {

			View child = getChildAt(i);

			child.layout(x, y, x + child.getMeasuredWidth(), y);

			Log.e(TAG,
					"lx:" + x + ",ly:" + y + ",rx:"
							+ (x + child.getMeasuredWidth()) + ",ry:" + y);

			x += child.getMeasuredWidth() + horizontalPadding;

			// 换行
			if (i > 1 && i % columnCount == columnCount - 1) {
				x = horizontalPadding;
				y += verticalPadding + child.getMeasuredHeight();
			}

		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

}
