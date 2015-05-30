package com.cml.product.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cml.product.home.db.SQLiteConnectionHelper;

public class HomeApplication extends Application {

	public static SQLiteConnectionHelper dbHelper;
	public static Context context;

	@Override
	public void onCreate() {

		super.onCreate();
		context = this;

		Log.e("aaaaa", "xxxx" + context + "==" + getApplicationContext());

	}

	public static final SQLiteConnectionHelper getDbHelper() {
		Log.e("aaaaa", "xx2222xx" + context + "==" );
		if (null == dbHelper) {
			dbHelper = new SQLiteConnectionHelper(context);
		}
		return dbHelper;
	}
}
