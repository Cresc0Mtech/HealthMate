package com.cresco.HealthMate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Profile_ParameterDefaults 
{
static String TABLE_NAME = "profile_parameterDefaults";
	
	public static final String ID = "_id";
	static String PPD_ID = "ppd_id";
	static String PROFILE_ID = "profile_id";
	static String PARAMETER_ID = "parameter_id";
	static String MIN_VALUE = "min_value";
	static String MAX_VALUE = "max_value";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public Profile_ParameterDefaults(Context context)
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
		Log.v("Profile_ParameterDefaults", "Alarms insert");
		
				
	}
	
	

}
