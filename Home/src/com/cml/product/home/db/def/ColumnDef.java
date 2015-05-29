package com.cml.product.home.db.def;

import android.provider.BaseColumns;

public interface ColumnDef {
	interface Tables {
		String CATEGORY = "t_category";
		String APP = "t_app";
	}

	interface Category extends BaseColumns {
		String NAME = "category_name";
		String TYPE = "category_type";
		String ORDER = "category_order";
		/** 分组图片，没有的话就是用默认 */
		String ICON = "category_icon";
	}

	interface App extends BaseColumns {
		String NAME = "app_name";
		String PACKAGE = "app_package";
		String CATEGORY = "category_id";
		String ICON = "app_icon";
		String ORDER = "app_order";
		/** 1 系统应用 ,0 普通应用 */
		String APP_FLG = "app_flg";
		String START_TIMES = "start_times";
	}
}
