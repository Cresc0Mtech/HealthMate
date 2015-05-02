package com.cresco.HealthMate;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class Measurement_values 
{
	String TAG = this.getClass().getSimpleName();
	
	static String TABLE_NAME = "measurement_values";

	
	public static final String ID = "_id";
	static String MSR_ID = "msr_id";
	static String PROFILE_ID = "profile_id";
	static String PARAMETER_ID = "parameter_id";
	static String SEQUENCE_ID = "sequence_id";
	static String MSR_TS = "msr_ts";
	static String MSR_VALUE = "msr_value";
	static String MSR_LOCATION = "msr_location";
	static String MSR_POSITION = "msr_position";
	static String MSR_REMARKS = "msr_remarks";
	
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Context context;
	
	public Measurement_values(Context context)
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
		Log.v("Measurement_values", "Measurement_values for pressure inserted");
		
				
	}
	
	public Cursor getmeasureCount(String type)   //changing in this method also do change in downside getAllpassenger() which gives data in arraylist
	{
		dbHelper     = DbHelper.getInstance(context);
		db           = dbHelper.getWritableDatabase();
		String ls_sql = null;
		if (type.equals("Pressure"))
		{
		  ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ PARAMETER_ID + " BETWEEN  1 AND 3 " ;
		}
		
		if(type.equals("Sugar"))
		{
			  ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ PARAMETER_ID + " BETWEEN  4 AND 4 " ;
		}
		
		Cursor cursor = db.rawQuery(ls_sql, null);
		return cursor;
	}
	
	public ArrayList<Bundle> getMeasurementList(String parameter, int profile_id)   //changing in this method also do change in aboveside getAllpassengerInCursor() which gives data in cursor
	{
		Cursor cursor;
		String ls_sql = null;
		
		ArrayList<Bundle> measureList = new ArrayList<Bundle>();
		////////////////////////
		String ls_view1 = null, ls_view2 = null, ls_view3 = null, ls_view4 = null,ls_view5 = null;
		
		/*ls_view1 = " CREATE VIEW IF NOT EXISTS tb_bp1 AS SELECT a.profile_id as p_id , "+
				   " a.msr_ts as msr_ts_bp1 ,"+
				   " a.msr_value as msr_value_bp1 ,"+
				   " FROM measurement_values a WHERE a.parameter_id = 1 AND a.profile_id = '" + profile_id + "'";
		
		ls_view2 = " CREATE VIEW IF NOT EXISTS tb_bp2 AS SELECT a.profile_id as p_id, " +
					" a.msr_ts as msr_ts_bp2 ,"+
					" a.msr_value as msr_valu1_bp2 ,"+
					" FROM measurement_values a WHERE a.parameter_id = 2 AND a.profile_id = '" + profile_id + "'";*/
		
//		ls_view3 = " CREATE VIEW IF NOT EXISTS tb_pulse AS SELECT a.profile_id as p_id, " +
//				   " a.msr_ts as msr_ts_pulse ,"+
//				   " a.msr_value as msr_value_pulse ,"+
//				   " FROM measurement_values a WHERE a.parameter_id = 3 AND a.profile_id = '" + profile_id + "'";
		
		////db.execSQL(ls_view1);
		//db.execSQL(ls_view2);
		//db.execSQL(ls_view3);
		
		/*ls_sql = " SELECT  tb_bp.p_id as profile_id , tb_bp1.msr_ts_bp  as msr_ts " +
				 " , tb_bp1.msr_value_bp1 as msr_value_bp1 , tb_bp2.msr_value_bp2 as msr_value_bp2 ,"+
				 " tb_pulse.msr_value_pulse as msr_value_pulse , " +
				 " FROM tb_bp1 " +
				 " LEFT JOIN tb_bp2 " +
				 " ON  tb_bp2.msr_ts_bp2 = tb_bp1.msr_ts_bp1 "+
				 " LEFT JOIN tb_pulse " +
				 " ON  tb_pulse.msr_ts_pulse = tb_bp1.msr_ts_bp1 ";*/
		
		///////////////////////////////////
		
		/*if(parameter.equals("blood_pressure"))
		{
			ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ PARAMETER_ID + 
					 " BETWEEN  1  AND  2  AND " + PROFILE_ID + " = '"+ profile_id +"'";
		}
		else if(parameter.equals("sugar"))
		{
			ls_sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+ PARAMETER_ID + " = 3 AND " + PROFILE_ID +
					 " = '"+ profile_id +"'";
		}
		*/
		/*if(parameter.equals("blood_pressure"))
		{
			ls_sql = " SELECT msr_id , profile_id, msr_ts , "+
					" MAX(CASE WHEN parameter_id = 1 THEN msr_value END) value1,"+
					" MAX(CASE WHEN parameter_id = 2 THEN msr_value END) value2,"+
					" MAX(CASE WHEN parameter_id = 3 THEN msr_value END) value3 "+
					" from measurement_values"+
					" WHERE "+ PROFILE_ID +
					 " = '"+ profile_id +"'"+
					" group by sequence_id";
		}
		else if(parameter.equals("sugar"))
		{
			ls_sql = " SELECT msr_id , profile_id, msr_ts , "+
					" MAX(CASE WHEN parameter_id = 4 THEN msr_value END) value4,"+
					" MAX(CASE WHEN parameter_id = 5 THEN msr_value END) value5 "+
					" from measurement_values"+
					" WHERE "+ PROFILE_ID +
					 " = '"+ profile_id +"'"+
					" group by sequence_id";
		}*/
				
		
		if(parameter.equals("blood_pressure"))
		{
			
			
			ls_view1 = " CREATE VIEW IF NOT EXISTS tb_pressure1 AS SELECT a.profile_id as p_id , "+
					   " a.msr_ts as msr_ts ,"+
					   " a.sequence_id as sequence_id ,"+
					   " a.msr_value as msr_value "+
					   " FROM measurement_values a WHERE a.parameter_id = 1 AND a.profile_id = '" + profile_id + "'";
			
			ls_view2 = " CREATE VIEW IF NOT EXISTS tb_pressure2 AS SELECT a.profile_id as p_id, " +
						" a.msr_ts as msr_ts ,"+
						" a.sequence_id as sequence_id ,"+
						" a.msr_value as msr_value "+
						" FROM measurement_values a WHERE a.parameter_id = 2 AND a.profile_id = '" + profile_id + "'";
			
			ls_view3 = " CREATE VIEW IF NOT EXISTS tb_pressure3 AS SELECT a.profile_id as p_id, " +
					   " a.msr_ts as msr_ts ,"+
					   " a.sequence_id as sequence_id ,"+
					   " a.msr_value as msr_value "+
					   " FROM measurement_values a WHERE a.parameter_id = 3 AND a.profile_id = '" + profile_id + "'";
			
			db.execSQL(ls_view1);
			db.execSQL(ls_view2);
			db.execSQL(ls_view3);
			
			ls_sql = " SELECT "+
					" tb_pressure1.p_id  as profile_id , " +
					" tb_pressure1.msr_ts as msr_ts , "+
					" tb_pressure1.msr_value as msr_value1 , " +
					" tb_pressure2.msr_value as msr_value2 , " +
					" tb_pressure3.msr_value as msr_value3 " +
					" FROM tb_pressure1 "+
					" LEFT JOIN tb_pressure2"+
					" ON  tb_pressure1.sequence_id = tb_pressure2.sequence_id "+
					" LEFT JOIN tb_pressure3 "+
					" ON  tb_pressure1.sequence_id = tb_pressure3.sequence_id "+
					" group by tb_pressure1.sequence_id ";
		}
		else if(parameter.equals("sugar"))
		{
			
			ls_view4 = " CREATE VIEW IF NOT EXISTS tb_sugar1 AS SELECT a.profile_id as p_id, " +
						" a.msr_ts as msr_ts ,"+
						" a.sequence_id as sequence_id ,"+
						" a.msr_value as msr_value "+
						" FROM measurement_values a WHERE a.parameter_id = 4 AND a.profile_id = '" + profile_id + "'";
		
			ls_view5 = " CREATE VIEW IF NOT EXISTS tb_sugar2 AS SELECT a.profile_id as p_id, " +
						" a.msr_ts as msr_ts ,"+
						" a.sequence_id as sequence_id ,"+
						" a.msr_value as msr_value "+
						" FROM measurement_values a WHERE a.parameter_id = 5 AND a.profile_id = '" + profile_id + "'";
			
			db.execSQL(ls_view4);
			db.execSQL(ls_view5);
			
			ls_sql = 	" SELECT "+
						" tb_sugar1.p_id  as profile_id , " +
						" tb_sugar1.msr_ts as msr_ts , "+
						" tb_sugar1.msr_value as msr_value4 , " +
						" tb_sugar2.msr_value as msr_value5 " +
						" FROM tb_sugar1 "+
						" LEFT JOIN tb_sugar2"+
						" ON  tb_sugar1.sequence_id = tb_sugar2.sequence_id " +
						" group by tb_sugar1.sequence_id ";
		}
		cursor = db.rawQuery(ls_sql, null);
		
		Log.v(TAG,"measure_list :"+ls_sql +"cursor :"+cursor.getCount());
		Bundle b = null;
	
		for(int i =0; i<cursor.getCount(); i++)
		{
			cursor.moveToPosition(i);
			
			
			if(parameter.equals("blood_pressure"))
			{
				b = new Bundle();
				
				b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
				b.putString(MSR_TS,cursor.getString(cursor.getColumnIndex(MSR_TS)));
			
				b.putString("msr_value1",cursor.getString(cursor.getColumnIndex("msr_value1")));
				b.putString("msr_value2",cursor.getString(cursor.getColumnIndex("msr_value2")));
				b.putString("msr_value3",cursor.getString(cursor.getColumnIndex("msr_value3")));
			}
			else if(parameter.equals("sugar"))
			{
				b = new Bundle();
				
				b.putInt(PROFILE_ID,cursor.getInt(cursor.getColumnIndex(PROFILE_ID)));
				b.putString(MSR_TS,cursor.getString(cursor.getColumnIndex(MSR_TS)));
				
				b.putString("msr_value4",cursor.getString(cursor.getColumnIndex("msr_value4")));
				b.putString("msr_value5",cursor.getString(cursor.getColumnIndex("msr_value5")));
			}
			
	
//			b.putString(MSR_TS,cursor.getString(cursor.getColumnIndex(MSR_TS)));
//			b.putString("msr_value1_bp",cursor.getString(cursor.getColumnIndex("msr_value1_bp")));
//			b.putString("msr_value2_bp",cursor.getString(cursor.getColumnIndex("msr_value2_bp")));
//			b.putString("msr_value1_pulse",cursor.getString(cursor.getColumnIndex("msr_value1_pulse")));
//			b.putString("msr_value2_pulse",cursor.getString(cursor.getColumnIndex("msr_value2_pulse")));
			
			measureList.add(b);
		}
	
		cursor.close();
		
		
		ls_sql = " DROP VIEW IF EXISTS tb_pressure1";
		db.execSQL(ls_sql);

		
		ls_sql = " DROP VIEW IF EXISTS tb_pressure2";
		db.execSQL(ls_sql);
		
		ls_sql = " DROP VIEW IF EXISTS tb_pressure3";
		db.execSQL(ls_sql);
		
		ls_sql = " DROP VIEW IF EXISTS tb_sugar1";
		db.execSQL(ls_sql);
		
		ls_sql = " DROP VIEW IF EXISTS tb_sugar2";
		db.execSQL(ls_sql);
		return measureList;
	}

}
