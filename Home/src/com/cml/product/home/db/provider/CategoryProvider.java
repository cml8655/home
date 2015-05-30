package com.cml.product.home.db.provider;

import android.content.UriMatcher;
import android.net.Uri;

import com.cml.product.home.db.contract.CategoryContract;
import com.cml.product.home.db.def.ColumnDef;

public class CategoryProvider extends BaseProvider {

	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		matcher.addURI(CategoryContract.AUTHORITY, CategoryContract.PATH_ITEM,
				CategoryContract.ITEM);
		matcher.addURI(CategoryContract.AUTHORITY, CategoryContract.PATH,
				CategoryContract.ITEMS);
	}

	@Override
	int getMatchCode(Uri uri) {
		return matcher.match(uri);
	}

	@Override
	String getTable() {
		return ColumnDef.Tables.CATEGORY;
	}

}
