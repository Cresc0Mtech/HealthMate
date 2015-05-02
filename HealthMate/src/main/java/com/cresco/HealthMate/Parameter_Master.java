package com.cresco.HealthMate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Parameter_Master 
{
	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "parameter_master";

	
	public static final String ID = "_id";
	static String PARAMETER_ID = "parameter_id";
	static String PARAMETER_NAME = "parameter_name";
	static String PARAMETER_UNIT = "parameter_unit";
	static String MIN_VALUE 	= "min_value";
	static String MAX_VALUE 	= "max_value";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public Parameter_Master(Context context)
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
		Log.v("Parameter_Master", "Parameter_Master insert");
		
				
	}
	
	
	public Cursor getAlllist()   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		
		String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}

}
