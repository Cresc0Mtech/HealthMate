package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class Sugar_Master 
{
	String TAG = this.getClass().getSimpleName();

	static String TABLE_NAME = "blsg_msr";

	
	public static final String ID 	= "_id";
	static String MSR_ID 			= "msr_id";
	static String PROFILE_ID 		= "profile_id";
    static String MSR_DATE 		    = "msr_date";
	static String PM4_VALUE 		= "pm4_value";
	static String PM4_TIME 	 		= "pm4_time";
	static String PM4_REMARKS	 	= "pm4_remarks";
	static String PM5_VALUE 	 	= "pm5_value";
	static String PM5_TIME 	 		= "pm5_time";
	static String PM5_REMARKS 	 	= "pm5_remarks";


	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;

	public Sugar_Master(Context context)
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
		//Log.v(TAG, "Sugar_Master insert");
	}

	
	
	public void edit_sugar(ContentValues cv, int msr_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.update(TABLE_NAME, cv, MSR_ID + "=" + msr_id, null);
		//Log.v(TAG, "Sugar_Master updated : "+msr_id);
	}
	public Cursor getAlllist()   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		
		String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}
	
	public void deleteRow( int msr_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.delete(TABLE_NAME, MSR_ID + "=" + msr_id, null);
		//Log.v(TAG, "Sugar_Master deleted : "+msr_id);
			
	}
	
	
	public ArrayList<Bundle> getMeasurementListByProfile(int profile_id)
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		//String ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" ;
		
		/*String ls_sql = "SELECT " + MSR_ID + " ," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE +
				"," + PM5_REMARKS + "," + PM4_REMARKS + "," + 
				" substr(pm4_ts ,0,11) date1 " + ", substr(pm4_ts ,11,15)  time1 ," +
                " substr(pm5_ts ,0,11) date2 " + ", substr(pm5_ts ,11,15)  time2 " +
				" FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
				" ORDER BY date1  DESC";*/

      /*  String ls_sql = "SELECT " + MSR_ID + " ," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE +
                "," + PM5_REMARKS + "," + PM4_REMARKS + "," +
                " substr(pm4_ts ,0,11) date1 " + ", substr(pm4_ts ,11,15)  time1 ," +
                " substr(pm5_ts ,0,11) date2 " + ", substr(pm5_ts ,11,15)  time2 " +
                " FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
                " ORDER BY date1  DESC";*/

        String ls_sql = "SELECT " + MSR_ID + " ," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE +
                "," + PM4_REMARKS + "," + PM5_REMARKS + ","
                 + MSR_DATE + ", " +  PM4_TIME + "," + PM5_TIME +
                " FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
                " ORDER BY msr_date  DESC";

        //Log.v(TAG,"sql12 : "+ls_sql);
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(MSR_ID,cursor.getInt(cursor.getColumnIndex(MSR_ID)));
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
            b.putString(MSR_DATE,cursor.getString(cursor.getColumnIndex(MSR_DATE)));
			b.putString(PM4_VALUE,cursor.getString(cursor.getColumnIndex(PM4_VALUE)));
			b.putString(PM4_TIME,cursor.getString(cursor.getColumnIndex(PM4_TIME)));
			b.putString(PM4_REMARKS,cursor.getString(cursor.getColumnIndex(PM4_REMARKS)));
			b.putString(PM5_VALUE,cursor.getString(cursor.getColumnIndex(PM5_VALUE)));
			b.putString(PM5_TIME,cursor.getString(cursor.getColumnIndex(PM5_TIME)));
			b.putString(PM5_REMARKS,cursor.getString(cursor.getColumnIndex(PM5_REMARKS)));
			///
			//b.putString("date1",cursor.getString(cursor.getColumnIndex("date1")));
			//b.putString("time1",cursor.getString(cursor.getColumnIndex("time1")));
           // b.putString("date2",cursor.getString(cursor.getColumnIndex("date2")));
            //b.putString("time2",cursor.getString(cursor.getColumnIndex("time2")));
			//
			
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}


	public ArrayList<Bundle> getMeasurementList(int profile_id)
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" ;
		
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
            b.putString(MSR_DATE,cursor.getString(cursor.getColumnIndex(MSR_DATE)));
			b.putString(PM4_VALUE,cursor.getString(cursor.getColumnIndex(PM4_VALUE)));
			b.putString(PM4_TIME,cursor.getString(cursor.getColumnIndex(PM4_TIME)));
			b.putString(PM4_REMARKS,cursor.getString(cursor.getColumnIndex(PM4_REMARKS)));
			b.putString(PM5_VALUE,cursor.getString(cursor.getColumnIndex(PM5_VALUE)));

			b.putString(PM5_TIME,cursor.getString(cursor.getColumnIndex(PM5_TIME)));
			b.putString(PM5_REMARKS,cursor.getString(cursor.getColumnIndex(PM5_REMARKS)));
			
			
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}

	public ArrayList<Bundle> getMeasurementList(int profile_id, String from, String to)
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql;// = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" ;
		
		if(from.equals("") && to.equals(""))
		{
			ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
                    " ORDER BY msr_date DESC";
		}
		else
		{
			//SELECT * FROM  test_date WHERE  substr(msr_date, 0, 11)   BETWEEN  "2014-12-21"  AND "2014-12-23"
			/*
            ls_sql = " SELECT * FROM " + TABLE_NAME +
					" WHERE  substr(pm4_ts ,0,11)  BETWEEN  '"+ from + "' AND '"+ to +"'" +
					" ORDER BY pm4_ts DESC";*/

            ls_sql = " SELECT * FROM " + TABLE_NAME +
                    " WHERE  msr_date  BETWEEN  '"+ from + "' AND '"+ to +"'" +
                    " AND "+ PROFILE_ID + " = '"+ profile_id+"'" +
                    " ORDER BY msr_date DESC";
			

		}
        //Log.v(TAG,"sql : "+ls_sql);
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
            b.putString(MSR_DATE,cursor.getString(cursor.getColumnIndex(MSR_DATE)));
			b.putString(PM4_VALUE,cursor.getString(cursor.getColumnIndex(PM4_VALUE)));
			b.putString(PM4_TIME,cursor.getString(cursor.getColumnIndex(PM4_TIME)));
			b.putString(PM4_REMARKS,cursor.getString(cursor.getColumnIndex(PM4_REMARKS)));
			b.putString(PM5_VALUE,cursor.getString(cursor.getColumnIndex(PM5_VALUE)));
			b.putString(PM5_TIME,cursor.getString(cursor.getColumnIndex(PM5_TIME)));
			b.putString(PM5_REMARKS,cursor.getString(cursor.getColumnIndex(PM5_REMARKS)));


			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
	
	
	public ArrayList<Bundle> getMeasurement(int profile_id , String from, String to)
	{
		Cursor cursor;
		String[] arr ;
		
		ArrayList<Bundle> List = new ArrayList<Bundle>();
				
		String ls_sql;
		
		if(from.equals("") && to.equals(""))
		{
			/*ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE +  "," + PM4_REMARKS + "," +
					"substr(pm4_ts ,0,11) date " + ", substr(pm4_ts ,11,16)  time " +
					" FROM " + TABLE_NAME +
					" WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
					" ORDER BY pm4_ts  DESC";*/

            ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE +  "," + PM4_REMARKS + ","
                      + PM5_REMARKS + "," + PM4_TIME +  "," + PM5_TIME +  "," + MSR_DATE +
                    " FROM " + TABLE_NAME +
                    " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
                    " ORDER BY msr_date  DESC";
		}
		else
		{
			/*ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE + "," + PM4_REMARKS + "," +
				"substr(pm4_ts ,0,11) date " + ", substr(pm4_ts ,11,16)  time " +
				" FROM " + TABLE_NAME + 
				" WHERE  substr(pm4_ts ,0,11)  BETWEEN  '"+ from + "' AND '"+ to +"'" +
				" AND " + PROFILE_ID + " = '"+ profile_id+"'" +
				" ORDER BY pm4_ts  DESC";*/

				ls_sql = "SELECT " + MSR_ID +"," + PROFILE_ID + "," + PM4_VALUE +  "," + PM5_VALUE + "," + PM4_REMARKS + ","
                        + PM5_REMARKS + "," + PM4_TIME +  "," + PM5_TIME +  "," + MSR_DATE +
				        " FROM " + TABLE_NAME +
				        " WHERE  msr_date  BETWEEN  '"+ from + "' AND '"+ to +"'" +
				        " AND " + PROFILE_ID + " = '"+ profile_id+"'" +
				        " ORDER BY msr_date  DESC";
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
			
			//b.putString("date",cursor.getString(cursor.getColumnIndex("date")));
			//b.putString("time",cursor.getString(cursor.getColumnIndex("time")));
            b.putString(MSR_DATE,cursor.getString(cursor.getColumnIndex(MSR_DATE)));
            b.putString(PM4_VALUE,cursor.getString(cursor.getColumnIndex(PM4_VALUE)));
            b.putString(PM4_TIME,cursor.getString(cursor.getColumnIndex(PM4_TIME)));
            b.putString(PM4_REMARKS,cursor.getString(cursor.getColumnIndex(PM4_REMARKS)));
            b.putString(PM5_VALUE,cursor.getString(cursor.getColumnIndex(PM5_VALUE)));
            b.putString(PM5_TIME,cursor.getString(cursor.getColumnIndex(PM5_TIME)));
            b.putString(PM5_REMARKS,cursor.getString(cursor.getColumnIndex(PM5_REMARKS)));
			
			List.add(b);
			
		}
	
		cursor.close();
		return List;
	}

	
	public ArrayList<Bundle> getlistByType(String ls_type , int profile_id)
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
		String ls_sql,ls_alue = null;
		String	ls_null = "";
		
		if(ls_type.equals("Fasting"))
		{
			ls_alue = "pm4_value";
		}
		else if(ls_type.equals("PP"))
		{
			ls_alue = "pm5_value";
		}
		 
		ls_sql = "SELECT " + ls_alue + " as value , pm4_ts FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id+"'" +
						" AND " + ls_alue + " != " + "''";
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			//b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			//b.putString(PM4_VALUE,cursor.getString(cursor.getColumnIndex(PM4_VALUE)));
			b.putString(PM4_TIME,cursor.getString(cursor.getColumnIndex(PM4_TIME)));
			//b.putString(PM4_REMARKS,cursor.getString(cursor.getColumnIndex(PM4_REMARKS)));
			//b.putString(PM5_VALUE,cursor.getString(cursor.getColumnIndex(PM5_VALUE)));
			//b.putString(PM5_TS,cursor.getString(cursor.getColumnIndex(PM5_TS)));
			//b.putString(PM5_REMARKS,cursor.getString(cursor.getColumnIndex(PM5_REMARKS)));
			b.putString("value",cursor.getString(cursor.getColumnIndex("value")));
			
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
}
