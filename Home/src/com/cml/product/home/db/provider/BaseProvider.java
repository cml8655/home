package com.cml.product.home.db.provider;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.cml.product.home.db.SQLiteConnectionHelper;
import com.cml.product.home.db.contract.BaseContract;

public abstract class BaseProvider extends ContentProvider {

	private SQLiteConnectionHelper helper;

	@Override
	public boolean onCreate() {
		helper = SQLiteConnectionHelper.getInstance(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = helper.getReadableDatabase();

		return db.query(getTable(), projection, selection, selectionArgs, null,
				null, sortOrder);

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = helper.getWritableDatabase();
		long id = db.insert(getTable(), null, values);

		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = helper.getWritableDatabase();

		String where = selection;
		String[] whereArgs = selectionArgs;

		switch (getMatchCode(uri)) {
		case BaseContract.ITEM:
			where = BaseColumns._ID + "=?";
			whereArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
			break;
		case BaseContract.ITEMS:
			break;
		default:
			return 0;
		}

		return db.delete(getTable(), where, whereArgs);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = helper.getWritableDatabase();

		String where = selection;
		String[] whereArgs = selectionArgs;

		switch (getMatchCode(uri)) {
		case BaseContract.ITEM:
			where = BaseColumns._ID + "=?";
			whereArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
			break;
		case BaseContract.ITEMS:
			break;
		default:
			return 0;
		}

		return db.update(getTable(), values, where, whereArgs);
	}

	@Override
	public String getType(Uri uri) {

		switch (getMatchCode(uri)) {
		case BaseContract.ITEM:
			return "vnd.android.cursor.item/item";

		case BaseContract.ITEMS:
			return "vnd.android.cursor.dir/items";
		}

		return null;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {

		SQLiteDatabase db = helper.getWritableDatabase();
		db.beginTransaction();
		int successCount = 0;

		try {
			for (ContentValues value : values) {
				db.insert(getTable(), null, value);
				successCount++;
			}
			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}

		return successCount;
	}

	abstract int getMatchCode(Uri uri);

	abstract String getTable();
}
