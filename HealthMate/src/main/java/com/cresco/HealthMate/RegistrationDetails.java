package com.cresco.HealthMate;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RegistrationDetails 
{
	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "registration_details";

	public static final String ID     = "_id";
	static String REG_ID 	 	      = "reg_id";
	static String IS_REGISTERED 	 	  = "is_registered";
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public RegistrationDetails(Context context)
	{
		this.context = context;
		
		dbHelper = DbHelper.getInstance(context);
		db = dbHelper.getWritableDatabase();

	}
	
	public String getRegisteredFlag()
	{
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
	}
	
	
	public void setRegisteredFlag(String registered)
	{
		db = dbHelper.getWritableDatabase();
		String sql = "INSERT INTO "+RegistrationDetails.TABLE_NAME + 
						"("+ IS_REGISTERED +") VALUES ('" + registered + "');";
		Debugger.debug(TAG, "setRegisteredFlag PD sql: "+sql);
		db.execSQL(sql);
		
	}
	
	public void setRegId(String regId)
	{
		db = dbHelper.getWritableDatabase();
		String sql = "UPDATE "+RegistrationDetails.TABLE_NAME + 
						" SET "+ RegistrationDetails.REG_ID +" = '" + regId + "';";
		
		Debugger.debug(TAG, "setRegId PD sql: "+sql);
		db.execSQL(sql);
		//Debugger.debug(TAG, "Reg ID sql: "+getRegId());
	}
		
}
