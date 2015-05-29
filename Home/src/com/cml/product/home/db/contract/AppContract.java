package com.cml.product.home.db.contract;

import android.content.ContentResolver;
import android.net.Uri;

public interface AppContract extends BaseContract {

	public static final String AUTHORITY = AUTHORITY_PREFIX + ".apps";

	public static final String PATH = "app";
	public static final String PATH_ITEM = PATH + "/#";

	public static final Uri URI = new Uri.Builder()
			.scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY)
			.path(PATH).build();

}
