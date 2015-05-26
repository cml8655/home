package com.cml.product.home.ui;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cml.product.home.R;

public class CategoryIndicatorView extends ViewGroup {

	private static final String TAG = CategoryIndicatorView.class
			.getSimpleName();
	private static final int ITEM_PADDING = 10;

	private List<Indicator> data;
	private IndicatorDirection direction;

	public CategoryIndicatorView(Context context, List<Indicator> data,
			IndicatorDirection direction) {
		super(context);
		this.data = data;
		this.direction = direction;
		initItems(context);
	}

	private void initItems(Context context) {

		for (Indicator indicator : data) {

			View item = inflate(context, R.layout.view_category_indicator_item,
					this);

			TextView txtView = (TextView) item.findViewById(R.id.indicator_txt);

			txtView.setText(indicator.title);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		Log.i(TAG, "onlayout:" + changed + ",l:" + l + ",t:" + t + ",r:" + r
				+ ",b:" + b);

		int childCount = getChildCount();

		if (childCount == 0) {
			return;
		}

		View firstChild = getChildAt(0);

		int height = firstChild.getMeasuredHeight();
		int width = firstChild.getMeasuredWidth();

		// 计算容器的高度，和最大能容数量
		int maxCount = b / (height + ITEM_PADDING);

		int len = Math.min(maxCount, childCount);

		int y = ITEM_PADDING;

		for (int i = 0; i < len; i++) {

			View child = getChildAt(i);

			child.layout(0, y, width, y + height);

			y += ITEM_PADDING + height;

		}

		// 多出的部分切换到对面上
		int startX = getMeasuredWidth() - width;

		Toast.makeText(getContext(), "dd" + len + "," + childCount,
				Toast.LENGTH_SHORT).show();

		if (childCount > len) {

			y -= ITEM_PADDING + height;

			for (int i = len; i < childCount; i++) {

				getChildAt(i).layout(startX, y, getMeasuredWidth(), y + height);

				y -= (height + ITEM_PADDING);
			}
		}

	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}

	}

	public static enum IndicatorDirection {
		LEFT, RIGHT
	}

	public static class Indicator {
		public String title;
		public Integer type;// 分类
	}

}
