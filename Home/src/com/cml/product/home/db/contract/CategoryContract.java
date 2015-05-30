package com.cml.product.home.db.contract;

import android.content.ContentResolver;
import android.net.Uri;

public interface CategoryContract extends BaseContract {

	public static final String AUTHORITY = AUTHORITY_PREFIX + ".categories";

	public static final String PATH = "category";
	public static final String PATH_ITEM = PATH + "/#";

	public static final Uri URI = new Uri.Builder()
			.scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY)
			.path(PATH).build();

}
