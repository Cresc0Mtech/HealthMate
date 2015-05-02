package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class FaceBookDetails 
{

	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "facebook_details";

	public static final String ID     = "_id";
	static String PROFILE_ID = "profile_id";
	static String FIRST_NAME = "first_name";
	static String MIDDLE_NAME = "middle_name";
	static String LAST_NAME = "last_name";
	static String DOB = "dob";
	static String GENDER = "gender";
	static String RELATION = "relation";
	static String BLOODGROUP = "bloodgroup";
	static String HEIGHT = "height";
	static String MOBILE_NO = "mobile_no";
	static String EMAIL_ID = "email_id";
	static String CITY = "city";
	static String COUNTRY = "country";
	static String PINCODE = "pincode";
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public FaceBookDetails(Context context)
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
		Log.v(TAG, "FaceBookDetails insert");
			
	}
	
	
	public ArrayList<Bundle> getAllProfileList()   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
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
	
			mCntList.add(b);
		}
	
		cursor.close();
		return mCntList;
	}
	
	public String getRegisteredFlag()
	{
		return TAG;
		/*
		db = dbHelper.getWritableDatabase();
		String sql = "SELECT "+ IS_REGISTERED + " FROM " +RegistrationDetails.TABLE_NAME;
		String flag = "";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst())
		{
			flag = cursor.getString(cursor.getColumnIndex(RegistrationDetails.IS_REGISTERED));
			Debugger.debug(TAG, "Flag is:" + flag);
		}
		if(flag == null)
		{
			flag = "N";
		}
		cursor.close();
		return flag;
		*/
	}


}
