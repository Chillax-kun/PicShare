package com.pictureshare.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class IsNetWork {
	
	/**
	 * 判断是否有网络
	 * @param context
	 * @return true与false
	 */
	public static boolean isNetWorking(Context c) {
		boolean status = false;
		Context context = c.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			status = false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						status = true;
						break;
					}
				}
			}
		}
		return status;
	}
}
