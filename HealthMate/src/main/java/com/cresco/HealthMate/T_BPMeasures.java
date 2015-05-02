package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*imports for database encryption*/

import android.os.Bundle;
import android.util.Log;

public class T_BPMeasures 
{
	//"select * from category where date(createdon) between date('2012-03-11') AND date('2013-01-01');"
	static String TABLE_NAME = "BPMeasures ";
	
	public static final String ID = "_id";
	static String DATE = "date";
	static String TIME = "time";
	static String LOW_RANGE = "low_range";
	static String HIGH_RANGE = "high_range";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public T_BPMeasures(Context context)
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
		Log.v("T_BPMeasures", "Alarms insert");
		
				
	}
	
	public void deleteRecords(int sr_no)
	{
		Cursor c = getAlllist();
		
		String ls_where = "_id = '" + sr_no +"'" ;
		if(c.getCount()>0)
		{
			db.delete(TABLE_NAME, ls_where, null);
			Log.v("T_BPMeasures", "Record deleted : "+ls_where );
		}
		
		
		
	}
	
	public Cursor getAlllist()   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		
		String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}
	
	public ArrayList<Bundle> getAllMeasureList()   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;

		 for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putString(DATE,cursor.getString(cursor.getColumnIndex(DATE)));
			b.putString(TIME,cursor.getString(cursor.getColumnIndex(TIME)));
			b.putString(LOW_RANGE,cursor.getString(cursor.getColumnIndex(LOW_RANGE)));
			b.putString(HIGH_RANGE,cursor.getString(cursor.getColumnIndex(HIGH_RANGE)));

			mCntList.add(b);
			
		}

		cursor.close();
		return mCntList;
	}
	
	
	public ArrayList<Bundle> getAllMeasureListByDate(String from, String to)   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		//String ls_sql = "SELECT * FROM " + TABLE_NAME ;
		String ls_sql = " SELECT * FROM " + TABLE_NAME + 
						" WHERE  " + DATE + " BETWEEN  '"+ from + "' AND '"+ to +"'" ;
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
		Log.v("jjj","ls_sql :"+ls_sql);
		 for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putString(DATE,cursor.getString(cursor.getColumnIndex(DATE)));
			b.putString(TIME,cursor.getString(cursor.getColumnIndex(TIME)));
			b.putString(LOW_RANGE,cursor.getString(cursor.getColumnIndex(LOW_RANGE)));
			b.putString(HIGH_RANGE,cursor.getString(cursor.getColumnIndex(HIGH_RANGE)));

			mCntList.add(b);
			
		}

		cursor.close();
		return mCntList;
	}
	
	
	

}
