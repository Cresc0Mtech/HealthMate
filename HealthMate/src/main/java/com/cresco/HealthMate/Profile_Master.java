package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class Profile_Master 
{
	String TAG = this.getClass().getSimpleName();
	
	
	static String TABLE_NAME = "profile_master";


	public static final String ID 	= "_id";
	static String PROFILE_ID 		= "profile_id";
	static String FIRST_NAME	 	= "first_name";
	static String MIDDLE_NAME 		= "middle_name";
	static String LAST_NAME 		= "last_name";
	static String DOB 				= "dob";
	static String GENDER 			= "gender";
	static String RELATION 			= "relation";
	static String BLOODGROUP 		= "bloodgroup";
	static String HEIGHT 			= "height";
	static String MOBILE_NO 		= "mobile_no";
	static String EMAIL_ID 			= "email_id";
	static String CITY 				= "city";
	static String COUNTRY 			= "country";
	static String PINCODE 			= "pincode";
	static String STATUS  			= "status";
	static String SERVER_STATUS  			= "server_status";
	static String SERVER_PROFILE_ID = "server_profile_id";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;

	public Profile_Master(Context context)
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
		Log.v(TAG, "Profile insert");
		
				
	}
	
	public int get_profileId()
	{
		 String query = "SELECT " + PROFILE_ID + " from " + TABLE_NAME + " order by " + PROFILE_ID + " DESC limit 1";
		 
		 int lastId = 0;
		 Cursor c = db.rawQuery (query, null);
		 if (c != null && c.moveToFirst()) 
		 {
		     lastId = c.getInt(0);
		 //The 0 is the column index, we only have 1 column, so the index is 0
		 }
		 Log.v(TAG, "last profile id :"+lastId);
		 return lastId;
	}
	
	
	public void change_status(int server_profile_id, int prof_id)
	{
		String sql = " UPDATE " + TABLE_NAME + 
				" SET " + SERVER_PROFILE_ID + " = '" + server_profile_id + "'" +
				" , " + SERVER_STATUS + " = 'OK'" +
				" WHERE "  + PROFILE_ID + " = '" + prof_id + "'" ;

		Debugger.debug(TAG, "change_status PD sql: "+sql);
		db.execSQL(sql);
	}
	
	
	
	public void change_edit_status(int prof_id)
	{
		String sql = " UPDATE " + TABLE_NAME + 
				" SET " + SERVER_STATUS + " = 'OK'" +
				" WHERE "  + PROFILE_ID + " = '" + prof_id + "'" ;

		Debugger.debug(TAG, "change_status PD sql: "+sql);
		db.execSQL(sql);
	}
	
	public int get_server_id(int profile_id)
	{
		String query = "SELECT " + SERVER_PROFILE_ID + " from " + TABLE_NAME + " WHERE " 
						+ PROFILE_ID + " = '" + profile_id + "'" ;
		 
		 int s_profId = 0;
		 Cursor c = db.rawQuery (query, null);
		 if (c != null && c.moveToFirst()) 
		 {
			 s_profId = c.getInt(0);
		 //The 0 is the column index, we only have 1 column, so the index is 0
		 }
		 Log.v(TAG, "last server_profile id :"+s_profId);
		 return s_profId;
	}
	
	public void edit_profileDetails(ContentValues cv, int profile_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		db.update(TABLE_NAME, cv, PROFILE_ID + "=" + profile_id , null);
		
		Log.v("Profile_Master", "profile updated + "+profile_id);
		
				
	}
	
	public void delete_profile(int profile_id)
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		//db.delete(TABLE_NAME, PROFILE_ID + "=" + profile_id, null);
		
		String sql = " UPDATE " + TABLE_NAME + 
					 " SET " + STATUS + " = 'I'" + "," + SERVER_STATUS + " = 'PENDING'"+
					 " WHERE "  + PROFILE_ID + " = '" + profile_id + "'" ;
		
		db.execSQL(sql);
		
		Log.v("Profile_Master", "profile deleted + "+profile_id);
			
	}


	public Cursor getAlllist()   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		
		
		String ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS + " = 'A'";
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}


	public ArrayList<Bundle> getAllProfileList()   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql = "SELECT * FROM " + TABLE_NAME +
				" WHERE " + STATUS + " =  'A' ";
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			b.putString(FIRST_NAME,cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
			b.putString(MIDDLE_NAME,cursor.getString(cursor.getColumnIndex(MIDDLE_NAME)));
			b.putString(LAST_NAME,cursor.getString(cursor.getColumnIndex(LAST_NAME)));
			b.putString(DOB,cursor.getString(cursor.getColumnIndex(DOB)));
			b.putString(GENDER,cursor.getString(cursor.getColumnIndex(GENDER)));
			b.putString(RELATION,cursor.getString(cursor.getColumnIndex(RELATION)));
			b.putString(BLOODGROUP,cursor.getString(cursor.getColumnIndex(BLOODGROUP)));
			b.putString(HEIGHT,cursor.getString(cursor.getColumnIndex(HEIGHT)));
			b.putString(MOBILE_NO,cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
			b.putString(EMAIL_ID,cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
			b.putString(CITY,cursor.getString(cursor.getColumnIndex(CITY)));
			b.putString(COUNTRY,cursor.getString(cursor.getColumnIndex(COUNTRY)));
			b.putString(PINCODE,cursor.getString(cursor.getColumnIndex(PINCODE)));
			b.putString(STATUS,cursor.getString(cursor.getColumnIndex(STATUS)));
			b.putString(SERVER_STATUS,cursor.getString(cursor.getColumnIndex(SERVER_STATUS)));
			b.putInt(SERVER_PROFILE_ID,cursor.getInt(cursor.getColumnIndex(SERVER_PROFILE_ID)));
	
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
	
	public ArrayList<Bundle> getProfileByID(int profile_id)
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROFILE_ID + " = '"+ profile_id +"'" +
						" AND " + STATUS + " = 'A'";
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			b.putString(FIRST_NAME,cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
			b.putString(MIDDLE_NAME,cursor.getString(cursor.getColumnIndex(MIDDLE_NAME)));
			b.putString(LAST_NAME,cursor.getString(cursor.getColumnIndex(LAST_NAME)));
			b.putString(DOB,cursor.getString(cursor.getColumnIndex(DOB)));
			b.putString(GENDER,cursor.getString(cursor.getColumnIndex(GENDER)));
			b.putString(RELATION,cursor.getString(cursor.getColumnIndex(RELATION)));
			b.putString(BLOODGROUP,cursor.getString(cursor.getColumnIndex(BLOODGROUP)));
			b.putString(HEIGHT,cursor.getString(cursor.getColumnIndex(HEIGHT)));
			b.putString(MOBILE_NO,cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
			b.putString(EMAIL_ID,cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
			b.putString(CITY,cursor.getString(cursor.getColumnIndex(CITY)));
			b.putString(COUNTRY,cursor.getString(cursor.getColumnIndex(COUNTRY)));
			b.putString(PINCODE,cursor.getString(cursor.getColumnIndex(PINCODE)));
			b.putString(STATUS,cursor.getString(cursor.getColumnIndex(STATUS)));
			b.putString(SERVER_STATUS,cursor.getString(cursor.getColumnIndex(SERVER_STATUS)));
			b.putInt(SERVER_PROFILE_ID,cursor.getInt(cursor.getColumnIndex(SERVER_PROFILE_ID)));
	
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
	
	
	
	public ArrayList<Bundle> getAll_pendingList()   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
	{
		Cursor cursor;
		
		ArrayList<Bundle> mCntList = new ArrayList<Bundle>();
				
		String ls_sql = "SELECT * FROM " + TABLE_NAME + 
						" WHERE " + SERVER_STATUS + " = 'PENDING'";
		
		cursor = db.rawQuery(ls_sql, null);
		
		Bundle b;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			b = new Bundle();
			
			b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
			b.putString(FIRST_NAME,cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
			b.putString(MIDDLE_NAME,cursor.getString(cursor.getColumnIndex(MIDDLE_NAME)));
			b.putString(LAST_NAME,cursor.getString(cursor.getColumnIndex(LAST_NAME)));
			b.putString(DOB,cursor.getString(cursor.getColumnIndex(DOB)));
			b.putString(GENDER,cursor.getString(cursor.getColumnIndex(GENDER)));
			b.putString(RELATION,cursor.getString(cursor.getColumnIndex(RELATION)));
			b.putString(BLOODGROUP,cursor.getString(cursor.getColumnIndex(BLOODGROUP)));
			b.putString(HEIGHT,cursor.getString(cursor.getColumnIndex(HEIGHT)));
			b.putString(MOBILE_NO,cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
			b.putString(EMAIL_ID,cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
			b.putString(CITY,cursor.getString(cursor.getColumnIndex(CITY)));
			b.putString(COUNTRY,cursor.getString(cursor.getColumnIndex(COUNTRY)));
			b.putString(PINCODE,cursor.getString(cursor.getColumnIndex(PINCODE)));
			b.putString(STATUS,cursor.getString(cursor.getColumnIndex(STATUS)));
			b.putString(SERVER_STATUS,cursor.getString(cursor.getColumnIndex(SERVER_STATUS)));
			b.putInt(SERVER_PROFILE_ID,cursor.getInt(cursor.getColumnIndex(SERVER_PROFILE_ID)));
	
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
}
