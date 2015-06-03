package com.cml.product.home.fragment.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.cml.product.home.R;
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
		this.context = context;
	}

	@Override
	public void onClick(View v, final AppModel data) {

		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.anim_app_click);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent();
				ComponentName cm = new ComponentName(data.getPackageName(),
						data.getActivityName());
				intent.setComponent(cm);
				context.startActivity(intent);
			}
		});
		v.findViewById(R.id.app_icon).startAnimation(anim);

	}

	@Override
	public boolean onLongClick(View v, AppModel data) {
		ToastUtil.show(context, "hhh onLongClick:" + data.getAppName());
		return false;
	}

}
