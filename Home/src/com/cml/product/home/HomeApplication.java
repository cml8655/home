package com.cml.product.home;

import com.cml.product.home.db.SQLiteConnectionHelper;

import android.app.Application;

public class HomeApplication extends Application {

	public static SQLiteConnectionHelper dbHelper;

	@Override
	public void onCreate() {

		super.onCreate();
		dbHelper = new SQLiteConnectionHelper(this);
	}
}
