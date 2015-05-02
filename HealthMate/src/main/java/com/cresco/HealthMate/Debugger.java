package com.cresco.HealthMate;

import android.util.Log;

public class Debugger
{
	private final static boolean debug = false;
	
	public static void debug(String TAG, String ls_msg)
	{
		if(debug)
		{
			//Log.v(TAG, ls_msg);
		}
	}
}
