package com.cml.product.home.constant;

public interface Constant {
	interface AppType {
		Integer TYPE_SYSTEM = 1;
		Integer TYPE_GAME = 2;
		Integer TYPE_ETC = 3;
	}

	interface AppFlg {
		/** 系统软件 */
		Integer FLAG_SYSTEM = 1;
		Integer FLAG_ETC = 2;

		/** app在界面显示 */
		Integer FLAG_VIVIBLE = 1;
		Integer FLAG_HIDDEN = 1;
	}

	interface Common {
		/** APP行数 */
		Integer COUNT_APP_ROW = 5;
		/** APP列数 */
		Integer COUNT_APP_COLUMN = 4;
	}
}
