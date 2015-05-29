package com.cml.product.home.db.provider;

import android.content.UriMatcher;
import android.net.Uri;

import com.cml.product.home.db.contract.AppContract;
import com.cml.product.home.db.def.ColumnDef;

public class AppsProvider extends BaseProvider {

	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		matcher.addURI(AppContract.AUTHORITY, AppContract.PATH_ITEM,
				AppContract.ITEM);
		matcher.addURI(AppContract.AUTHORITY, AppContract.PATH,
				AppContract.ITEMS);
	}

	@Override
	int getMatchCode(Uri uri) {
		return matcher.match(uri);
	}

	@Override
	String getTable() {
		return ColumnDef.Tables.APP;
	}


}
