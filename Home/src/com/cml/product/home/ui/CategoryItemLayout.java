package com.cml.product.home.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.cml.product.home.util.PrefUtil;

/**
 * app 显示布局文件，可控制app显示风格4*4/4*5
 * 
 * @author teamlab
 *
 */
public class CategoryItemLayout extends ViewGroup {

	private static final Integer ROW_COUNT = 5;
	private static final Integer COLUMN_COUNT = 4;

	private int rowCount;
	private int columnCount;

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

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

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
