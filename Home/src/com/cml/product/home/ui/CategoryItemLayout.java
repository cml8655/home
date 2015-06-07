package com.cml.product.home.ui;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
public class CategoryItemLayout extends ViewGroup implements OnClickListener,
		OnLongClickListener {
	private static final String TAG = CategoryItemLayout.class.getSimpleName();

	/** 每行显示的个数 */
	private int rowCount;
	/** 每列显示的个数 */
	private int columnCount;
	/** 行间距 */
	private int verticalMargin;

	private OnItemTouchListener listener;

	public CategoryItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		rowCount = PrefUtil.getAppRowCount(context);
		columnCount = PrefUtil.getAppColumnCount(context);
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

			View appView = inflator.inflate(R.layout.view_app, this, false);

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

			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			appView.setLayoutParams(params);

			// 添加监听事件
			appView.setOnClickListener(this);
			appView.setOnLongClickListener(this);

			this.addView(appView);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int viewWidth = getMeasuredWidth();
		int viewHeight = getMeasuredHeight();

		Log.e(TAG, "===>onLayout:" + viewWidth + "," + viewHeight + ",,"
				+ getHeight() + "===" + getChildCount());

		int len = getChildCount();

		if (len == 0) {
			return;
		}

		// 计算每个item的padding

		View firstChildView = getChildAt(0);

		// 水平方向的列间距
		int horizontalPadding = viewWidth / columnCount
				- firstChildView.getMeasuredWidth();

		Log.e(TAG, "onlayout==>verticalPadding:" + verticalMargin);

		int x = horizontalPadding / 2;
		int y = verticalMargin;

		// 设置view位置
		for (int i = 0; i < len; i++) {

			View child = getChildAt(i);

			child.layout(x, y, x + child.getMeasuredWidth(),
					y + child.getMeasuredHeight());

			x += child.getMeasuredWidth() + horizontalPadding;

			// 换行
			if (i > 1 && i % columnCount == columnCount - 1) {
				x = horizontalPadding / 2;
				y += verticalMargin + child.getMeasuredHeight();
			}

		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int childCount = getChildCount();
		if (childCount == 0) {
			return;
		}

		int childRowCount = (int) Math.ceil(childCount / rowCount);

		View firstChild = getChildAt(0);

		// 垂直方向的列间距
		verticalMargin = getMeasuredHeight() / rowCount
				- firstChild.getMeasuredHeight();

		int childHeight = firstChild.getMeasuredHeight() + verticalMargin;

		Log.e(TAG, "measure width:" + getMeasuredWidth() + ",height:"
				+ getMeasuredHeight() + ",custom set height:" + childHeight
				* childRowCount + ",measure padding:" + verticalMargin);

		setMeasuredDimension(getMeasuredWidth(), childHeight * childRowCount);
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	public void setListener(OnItemTouchListener listener) {
		this.listener = listener;
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

	/**
	 * app点击事件与长按事件
	 * 
	 * @author teamlab
	 *
	 */
	public static interface OnItemTouchListener {

		void onClick(View v, final AppModel data);

		boolean onLongClick(View v, final AppModel data);
	}

}
