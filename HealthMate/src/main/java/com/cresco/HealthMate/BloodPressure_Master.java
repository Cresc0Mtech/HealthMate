package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class BloodPressure_Master 
{
	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "blpr_msr";

	public static final String ID = "_id";
	static String MSR_ID 	 	  = "msr_id";
	static String PROFILE_ID      = "profile_id";
	static String PM1_VALUE       = "pm1_value";
	static String PM2_VALUE 	  = "pm2_value";
	static String PM3_VALUE 	  = "pm3_value";
	static String MSR_TS 	      = "msr_ts";
	static String MSR_LOCATION 	  = "msr_location";
	static String MSR_POSITION 	  = "msr_position";
	static String MSR_REMARKS 	  = "msr_remarks";
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public BloodPressure_Master(Context context)
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
		//Log.v(TAG, "BloodPressure_Master insert");
		
				
	}
	
	public void edit_pressure(ContentValues cv, int msr_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.update(TABLE_NAME, cv, MSR_ID + "=" + msr_id, null);
		//Log.v(TAG, "BloodPressure_Master update : "+msr_id);
		
				
	}
	
	public void deleteRow( int msr_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.delete(TABLE_NAME, MSR_ID + "=" + msr_id, null);
		//Log.v(TAG, "BloodPressure_Master deleted : "+msr_id);
			
	}
	
	
	public Cursor getAlllist()   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		
		String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}
	
	
	public ArrayList<Bundle> getMeasurementListByProfile(int profile_id)
	{
		Cursor cursor;
		
		ArrayList<Bundle> List = new ArrayList<Bundle>();
				
		//String ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" ;
		
		String ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM1_VALUE +  "," + PM2_VALUE + "," + PM3_VALUE + "," + MSR_LOCATION +
						"," + MSR_POSITION + "," + MSR_REMARKS + "," + 
						"substr(msr_ts ,0,11) date " + ", substr(msr_ts ,11,16)  time " +
						" FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
						" ORDER BY msr_ts DESC";
		//substr(a.event_startTS,12,5) start_time
		cursor = db.rawQuery(ls_sql, null);
		
		//Log.v(TAG,"Ls_sql :"+ls_sql);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(MSR_ID,cursor.getInt(cursor.getColumnIndex(MSR_ID)));
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			b.putString(PM1_VALUE,cursor.getString(cursor.getColumnIndex(PM1_VALUE)));
			b.putString(PM2_VALUE,cursor.getString(cursor.getColumnIndex(PM2_VALUE)));
			b.putString(PM3_VALUE,cursor.getString(cursor.getColumnIndex(PM3_VALUE)));
			//b.putString(MSR_TS,cursor.getString(cursor.getColumnIndex(MSR_TS)));
			///
			b.putString("date",cursor.getString(cursor.getColumnIndex("date")));
			b.putString("time",cursor.getString(cursor.getColumnIndex("time")));
			//
			b.putString(MSR_LOCATION,cursor.getString(cursor.getColumnIndex(MSR_LOCATION)));
			b.putString(MSR_POSITION,cursor.getString(cursor.getColumnIndex(MSR_POSITION)));
			b.putString(MSR_REMARKS,cursor.getString(cursor.getColumnIndex(MSR_REMARKS)));
	
			List.add(b);
		}
	
		cursor.close();
		return List;
	}
	
	public ArrayList<Bundle> getMeasurement(int profile_id , String from, String to)
	{
		Cursor cursor;
		String[] arr ;
		
		ArrayList<Bundle> List = new ArrayList<Bundle>();
				
		String ls_sql;
		
		if(from.equals("") && to.equals(""))
		{
			ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM1_VALUE +  "," + PM2_VALUE + "," + PM3_VALUE + "," + MSR_LOCATION +
					"," + MSR_POSITION + "," + MSR_REMARKS + "," + 
					"substr(msr_ts ,0,11) date " + ", substr(msr_ts ,11,16)  time " +
					" FROM " + TABLE_NAME +
					" WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
					" ORDER BY msr_ts  DESC";
		}
		else
		{
			ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM1_VALUE +  "," + PM2_VALUE + "," + PM3_VALUE + "," + MSR_LOCATION +
				"," + MSR_POSITION + "," + MSR_REMARKS + "," + 
				"substr(msr_ts ,0,11) date " + ", substr(msr_ts ,11,16)  time " +
				" FROM " + TABLE_NAME + 
				//" WHERE  substr(msr_ts ,0,11)  >= '"+from+"' AND  " +
				//		   " substr(msr_ts ,0,11)  <= '"+to +"'" +
				" WHERE  substr(msr_ts ,0,11)  BETWEEN  '"+ from + "' AND '"+ to +"'" +
				" AND " + PROFILE_ID + " = '"+ profile_id+"'" +
				" ORDER BY msr_ts  DESC";
		}
		
		//Log.v(TAG,"sql : "+ls_sql);
				
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
		arr = new String[cursor.getCount()];
		
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			//arr[i] = cursor.getString(cursor.getColumnIndex(PM1_VALUE));
			
			b = new Bundle();
			
			b.putString("date",cursor.getString(cursor.getColumnIndex("date")));
			b.putString("time",cursor.getString(cursor.getColumnIndex("time")));
			b.putString(PM1_VALUE,cursor.getString(cursor.getColumnIndex(PM1_VALUE)));
			b.putString(PM2_VALUE,cursor.getString(cursor.getColumnIndex(PM2_VALUE)));
			b.putString(PM3_VALUE,cursor.getString(cursor.getColumnIndex(PM3_VALUE)));
			b.putString(MSR_POSITION,cursor.getString(cursor.getColumnIndex(MSR_POSITION)));
			b.putString(MSR_LOCATION,cursor.getString(cursor.getColumnIndex(MSR_LOCATION)));
			b.putString(MSR_REMARKS,cursor.getString(cursor.getColumnIndex(MSR_REMARKS)));
			
			List.add(b);
			
		}
	
		cursor.close();
		return List;
	}

	
	public ArrayList<Bundle> getMeasurementList(int profile_id, String from, String to)
	{
		Cursor cursor;
		String ls_sql;
		
		ArrayList<Bundle> List = new ArrayList<Bundle>();
		
		
		if(from.equals("") && to.equals(""))
		{
			ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" ;
		}
		else
		{
			//SELECT * FROM  test_date WHERE  substr(msr_date, 0, 11)   BETWEEN  "2014-12-21"  AND "2014-12-23"
			//ls_sql = " SELECT * FROM " + TABLE_NAME + 
			//		" WHERE  substr(msr_ts ,0,11)  BETWEEN  '"+ from + "' AND '"+ to +"'" ;
			
			ls_sql = " SELECT * FROM " + TABLE_NAME + 
					" WHERE  substr(msr_ts ,0,11)  >= '"+from+"' AND  " +
						   " substr(msr_ts ,0,11)  <= '"+to +"'" +
					"AND " + PROFILE_ID + " = '"+ profile_id+"'" +
					" ORDER BY msr_ts DESC";
			
			//Log.v(TAG,"sql : "+ls_sql);
		}
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(MSR_ID,cursor.getInt(cursor.getColumnIndex(MSR_ID)));
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			b.putString(PM1_VALUE,cursor.getString(cursor.getColumnIndex(PM1_VALUE)));
			b.putString(PM2_VALUE,cursor.getString(cursor.getColumnIndex(PM2_VALUE)));
			b.putString(PM3_VALUE,cursor.getString(cursor.getColumnIndex(PM3_VALUE)));
			b.putString(MSR_TS,cursor.getString(cursor.getColumnIndex(MSR_TS)));
			
			b.putString(MSR_LOCATION,cursor.getString(cursor.getColumnIndex(MSR_LOCATION)));
			b.putString(MSR_POSITION,cursor.getString(cursor.getColumnIndex(MSR_POSITION)));
			b.putString(MSR_REMARKS,cursor.getString(cursor.getColumnIndex(MSR_REMARKS)));
	
			List.add(b);
		}
	
		cursor.close();
		return List;
	}


}
