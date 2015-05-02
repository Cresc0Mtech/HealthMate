package com.cresco.HealthMate;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetUtils
{
	private final static String TAG = InternetUtils.class.getSimpleName();

	/*
	public static int getConnectivityStatus(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork)
		{
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return M_DownSettings.NETWORK_TYPE_WIFI;

			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return M_DownSettings.NETWORK_TYPE_MOBILE_DATA;
		}

		return M_DownSettings.NETWORK_TYPE_NO_CONNECTION;
	}
	*/

	public static boolean isConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork)
		{
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}

		return false;
	}

	/*
	public static String getConnectivityStatusString(Context context) 
	{
		int conn = getConnectivityStatus(context);

		String status = null;

		if (conn == M_DownSettings.NETWORK_TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == M_DownSettings.NETWORK_TYPE_MOBILE_DATA) {
			status = "Mobile data enabled";
		} else if (conn == M_DownSettings.NETWORK_TYPE_NO_CONNECTION) {
			status = "Not connected to Internet";
		}

		return status;
	}
	*/
}
