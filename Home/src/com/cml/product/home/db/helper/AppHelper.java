package com.cml.product.home.db.helper;

import com.cml.product.home.db.def.ColumnDef;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class AppHelper extends BaseHelper {

	private static final Uri URI = null;

	public AppHelper(Context context) {
		super(context);
	}

	/**
	 * ²åÈëapp¼ÇÂ¼
	 * 
	 * @param name
	 * @param packageName
	 * @param categoryId
	 * @param appFlg
	 * @return
	 */
	public long insertApp(String name, String packageName, Integer categoryId,
			Integer appFlg) {

		ContentValues value = new ContentValues();
		value.put(ColumnDef.App.NAME, name);
		value.put(ColumnDef.App.PACKAGE, packageName);
		value.put(ColumnDef.App.CATEGORY, categoryId);
		value.put(ColumnDef.App.APP_FLG, appFlg);
		value.put(ColumnDef.App.START_TIMES, 0);

		Uri result = context.getContentResolver().insert(URI, value);
		return ContentUris.parseId(result);
	}
}
