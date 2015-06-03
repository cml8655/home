package com.cml.product.home.db.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;
import com.cml.product.home.model.AppModel;

public class AppHelper extends BaseHelper {

	private static final Uri URI = AppContract.URI;

	public AppHelper(Context context) {
		super(context);
	}

	/**
	 * 插入APP数据
	 * 
	 * @param name
	 * @param packageName
	 * @param categoryId
	 * @param appFlg
	 * @return
	 */
	public long insertApp(String name, String packageName, String activityName,
			Integer iconRes, Integer categoryId, Integer appFlg) {

		ContentValues value = new ContentValues();
		value.put(ColumnDef.App.ICON, iconRes);
		value.put(ColumnDef.App.NAME, name);
		value.put(ColumnDef.App.ACTIVITY_NAME, activityName);
		value.put(ColumnDef.App.PACKAGE, packageName);
		value.put(ColumnDef.App.CATEGORY, categoryId);
		value.put(ColumnDef.App.APP_FLG, appFlg);
		value.put(ColumnDef.App.START_TIMES, 0);

		Uri result = context.getContentResolver().insert(URI, value);
		return ContentUris.parseId(result);
	}

	/**
	 * 插入APP数据
	 * 
	 * @param name
	 * @param packageName
	 * @param categoryId
	 * @param appFlg
	 * @return
	 */
	public long insertApp(List<AppModel> datas) {

		List<ContentValues> values = new ArrayList<ContentValues>(datas.size());

		for (AppModel data : datas) {
			ContentValues value = new ContentValues();
			value.put(ColumnDef.App.ICON, data.getIconRes());
			value.put(ColumnDef.App.NAME, data.getAppName());
			value.put(ColumnDef.App.ACTIVITY_NAME, data.getActivityName());
			value.put(ColumnDef.App.PACKAGE, data.getPackageName());
			value.put(ColumnDef.App.CATEGORY, data.getCategoryId());
			value.put(ColumnDef.App.APP_FLG, data.getAppFlg());
			value.put(ColumnDef.App.START_TIMES, 0);

			values.add(value);
		}

		return context.getContentResolver().bulkInsert(URI,
				values.toArray(new ContentValues[] {}));
	}
}
