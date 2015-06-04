package com.cml.product.home.fragment.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.cml.product.home.R;
import com.cml.product.home.db.helper.AppHelper;
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

	private static final String TAG = "AppItemTouchHelper";

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
				Intent intent = context.getPackageManager()
						.getLaunchIntentForPackage(data.getPackageName());
				if (null != intent) {
					try {
						context.startActivity(intent);
					} catch (Exception e) {
						Log.e(TAG, "启动app失败", e);
					}
				}

			}
		});
		v.findViewById(R.id.app_icon).startAnimation(anim);

	}

	@Override
	public boolean onLongClick(View v, AppModel data) {
		ToastUtil.show(context, "hhh onLongClick:" + data.getAppName());
		final String pgName = data.getPackageName();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("确定卸载：" + data.getAppName());
		builder.setPositiveButton("好滴", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
				// intent.setData(Uri.parse("package:" + pgName));
				// context.startActivity(intent);
				long id = new AppHelper(context).delApp(pgName);
				ToastUtil.show(context, "爱喝酸奶：" + pgName + ",," + id);
			}
		});
		builder.create().show();
		return false;
	}

}
