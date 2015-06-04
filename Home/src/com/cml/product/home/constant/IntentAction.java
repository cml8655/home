package com.cml.product.home.constant;

public interface IntentAction {
	interface Broadcast {
		/** app安装db更新完成 */
		String APP_INSTALLED = "com.cml.product.home.constant.intentaction.broadcast.app_installed";
		/** app卸载db更新完成 */
		String APP_UNINSTALLED = "com.cml.product.home.constant.intentaction.broadcast.app_uninstalled";
	}
}
