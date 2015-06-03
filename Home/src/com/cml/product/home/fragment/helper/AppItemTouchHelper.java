package com.cml.product.home.fragment.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cml.product.home.model.AppModel;
import com.cml.product.home.ui.CategoryItemView;
import com.cml.product.home.util.ToastUtil;

/**
 * app 触摸helper，包含点击，长按等事件
 * 
 * @author teamlab
 *
 */
public class AppItemTouchHelper implements CategoryItemView.OnItemTouchListener {

	private Context context;

	public AppItemTouchHelper(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onClick(View v, AppModel data) {
		ToastUtil.show(context, "hhh onClick :" + data.getAppName());
		Intent intent = new Intent();
		ComponentName cm = new ComponentName(data.getPackageName(),
				data.getActivityName());
		intent.setComponent(cm);
		context.startActivity(intent);
	}

	@Override
	public boolean onLongClick(View v, AppModel data) {
		ToastUtil.show(context, "hhh onLongClick:" + data.getAppName());
		return false;
	}

}
