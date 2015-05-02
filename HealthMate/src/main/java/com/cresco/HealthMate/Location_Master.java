package com.cresco.HealthMate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Location_Master 
{
	

	String TAG = this.getClass().getSimpleName();
	
	
	static String TABLE_NAME = "location_master ";


	public static final String ID 	= "_id";
	static String LOCATION_ID 		= "location_id";
	static String TIMESTAMP 		= "timestamp";
	static String ADDRESS_LINE1	 	= "address_line1";
	static String ADDRESS_LINE2 	= "address_line2";
	static String CITY 				= "city";
	static String COUNTRY 			= "country";
	static String PINCODE 			= "pincode";
	static String LATITUDE 			= "latitude";
	static String LONGITUDE 		= "longitude";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;

	public Location_Master(Context context)
	{
		this.context = context;
		
		dbHelper = DbHelper.getInstance(context);
		db = dbHelper.getWritableDatabase();
	
	}

	public void insert(ContentValues cv)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.insert(TABLE_NAME, null, cv);
		Log.v(TAG, "Location insert");
		
				
	}
	
	
	
	

}
