package com.cresco.HealthMate;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Personal_Details 
{
	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "personal_details";

	public static final String ID     = "_id";
	static String IMEI_ID 	 	      = "imei_id";
	static String SERIAL_NO 	 	  = "serial_no";
	static String DEVICE_EMAIL 	 	  = "device_email";
	static String INSTALL_DATE 	 	  = "install_date";
	static String INSTALL_TYPE 	 	  = "install_type";
	static String OS_ID 	 	  	  = "os_id";
	static String OS_VERSION 	 	  = "os_version";
	static String VERSION_ID 	 	  = "version_id";
	static String VERSION_CODE 	 	  = "version_code";
	static String DEVICE_MODEL 	 	  = "device_model";
	static String ANDROID_ID 	 	  = "android_id";
	static String MAC_ADDRESS 	 	  = "mac_address";
	static String DEVPAYLOAD 	 	  = "devpayload";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public Personal_Details(Context context)
	{
		this.context = context;
		
		dbHelper = DbHelper.getInstance(context);
		db = dbHelper.getWritableDatabase();

	}
	
	
	public long CRUD(ContentValues cv)
	{
		long ll_return = 0 ;
		long ll_id = 0;
		
		/*
		ll_id = cv.getAsLong(Personal_Details.ID);
		String ls_null = null;

		if (ll_id == 0 )
		{
		
			cv.put(Personal_Details.ID, ls_null);
		*/
			
		ll_return = db.insert(Personal_Details.TABLE_NAME,null,cv);
		
		/*
		}
		else
		{
			String ls_whereClause =  "_id =" + ll_id ;
			ll_return = db.update(Personal_Details.TABLE_NAME,cv,ls_whereClause,null);
		}
		 */
		return ll_return;
	}
	
	public void update_InstallDate(String install_date)
	{
        //install_date = install_date.trim() +" 00:00:00";
		String ls_sql = "UPDATE " + TABLE_NAME + " SET " + INSTALL_DATE + " = " + install_date ;
		
		db.execSQL(ls_sql);
		 
		 //Log.v(TAG,"install date updated as : "+install_date);
		
	}

}
