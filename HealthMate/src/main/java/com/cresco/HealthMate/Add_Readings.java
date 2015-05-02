package com.cresco.HealthMate;

import java.util.ArrayList;
import java.util.Calendar;

import com.cresco.HealthMate.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Add_Readings extends Activity implements OnClickListener
{
	Context context;
	String TAG = this.getClass().getSimpleName();

	CrescoEditText et_date, et_time, et_sys, et_dias,et_pulse, et_pos, et_loc, et_remark, et_sdate, et_stime,et_sremark,
					et_fasting, et_pp;
	Spinner sp_pos, sp_loc, sp_sys, sp_dias, sp_pulse, sp_fasting, sp_pp;
	LinearLayout ll_add_sugar, ll_add_pressure;
	private int year, month, day, hour, min;
	String mth,hr,mn;
	String tabClicked;
	int profile_id;

	ArrayList<String> sp_list;
	
	ArrayAdapter sAdapter;
	
	ArrayAdapter genderAdapter;
	BloodPressure_Master bp_master;
	Sugar_Master sugar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_readings);

		context = this;
		bp_master = new BloodPressure_Master(context);
		 sugar = new Sugar_Master(context);
		 
		ll_add_pressure= (LinearLayout) findViewById(R.id.ll_add_pressure);
		et_date= (CrescoEditText) findViewById(R.id.et_date);
		et_time = (CrescoEditText) findViewById(R.id.et_time);
		//et_sys= (CrescoEditText) findViewById(R.id.et_sys);
		//et_dias= (CrescoEditText) findViewById(R.id.et_dias);
		//et_pulse= (CrescoEditText) findViewById(R.id.et_pulse);
		et_remark= (CrescoEditText) findViewById(R.id.et_remark);
		sp_pos = (Spinner) findViewById(R.id.sp_pos); 
		sp_loc = (Spinner) findViewById(R.id.sp_loc);
		sp_sys = (Spinner) findViewById(R.id.sp_sys); 
		sp_dias = (Spinner) findViewById(R.id.sp_dias);
		sp_pulse = (Spinner) findViewById(R.id.sp_pulse); 
		
		ll_add_sugar  	= (LinearLayout) findViewById(R.id.ll_add_sugar);
		et_sdate      	= (CrescoEditText) findViewById(R.id.et_sdate);
		et_stime      	= (CrescoEditText) findViewById(R.id.et_stime);
		//et_fasting    	= (CrescoEditText) findViewById(R.id.et_fasting);
		//et_pp    		= (CrescoEditText) findViewById(R.id.et_pp);
		et_sremark		= (CrescoEditText) findViewById(R.id.et_sremark);
		sp_fasting= (Spinner) findViewById(R.id.sp_fasting); 
		sp_pp= (Spinner) findViewById(R.id.sp_pp);
		
		et_date.setOnClickListener(this);
		et_time.setOnClickListener(this);
		et_sdate.setOnClickListener(this);
		et_stime.setOnClickListener(this);
		


		sp_list = new ArrayList<String>();
		sp_list.add("Position");	//adding genders in genderlist for gender adapter
		sp_list.add("Upright");
		sp_list.add("horizontal");
		sp_list.add("Seated");
		sp_list.add("Reclined");

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pos.setAdapter(sAdapter);

		//sp_pos.setTextColor(context.getResources().getColor(R.color.White));
		sp_list = new ArrayList<String>();
		sp_list.add("Location");	//adding genders in genderlist for gender adapter
		sp_list.add("Rt Wrist");
		sp_list.add("Lt Wrist");
		sp_list.add("Rt Arm");
		sp_list.add("Lt Arm");
		sp_list.add("Rt Leg");
		sp_list.add("Lt Leg");

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_loc.setAdapter(sAdapter);
		
		sp_list = new ArrayList<String>();
		sp_list.add("");
		for(int i = 100; i<=200; i++)
		{
			sp_list.add(String.valueOf(i));
		}

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_sys.setAdapter(sAdapter);
		
		
		sp_list = new ArrayList<String>();
		sp_list.add("");
		for(int j = 60;j<=120; j++)
		{
			sp_list.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_dias.setAdapter(sAdapter);
		
		sp_list = new ArrayList<String>();
		sp_list.add("");
		for(int k = 65; k<=80; k++)
		{
			sp_list.add(String.valueOf(k));
		}

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pulse.setAdapter(sAdapter);
		
		sp_list = new ArrayList<String>();
		for(int j = 60;j<=120; j++)
		{
			sp_list.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_fasting.setAdapter(sAdapter);
		
		sp_list = new ArrayList<String>();
		for(int j = 60;j<=120; j++)
		{
			sp_list.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pp.setAdapter(sAdapter);

		tabClicked = getIntent().getStringExtra("tabClicked");
		profile_id = getIntent().getIntExtra("Profile_ID", 0);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);


		mth = getMonth(month+1);

		if(hour<10)
		{
			hr = "0"+String.valueOf(hour);
		}
		else
		{
			hr = String.valueOf(hour);
		}

		if(min<10)
		{
			mn = "0"+String.valueOf(min);
		}
		else
		{
			mn = String.valueOf(min);
		}

		Log.v(TAG,"mth : "+mth +", month : "+month);
		
		et_date.setText(day+"\t"+mth+",\t"+year);
		et_time.setText(hr+":"+mn);
		
		et_sdate.setText(day+"\t"+mth+",\t"+year);
		et_stime.setText(hr+":"+mn);
		
		if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
		{
			ll_add_pressure.setVisibility(View.VISIBLE);
			ll_add_sugar.setVisibility(View.GONE);
		}
		else
		{
			ll_add_pressure.setVisibility(View.GONE);
			ll_add_sugar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		
		case R.id.et_date:

			setDate(v);
			break;

		case R.id.et_time:

			setTime(v);
			break;
			
		case R.id.et_sdate:

			setDate(v);
			break;

		case R.id.et_stime:

			setTime(v);
			break;
			
		
		}

	}




	public void setDate(View view) 
	{
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() 
		{

			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) 
			{

				monthOfYear = monthOfYear+1;
				String month1 = getMonth(monthOfYear);
				Log.v(TAG,"monthOfYear : "+monthOfYear +", month1 : "+month1);


				et_date.setText(dayOfMonth+"\t"+month1+",\t"+year);

				//tv_date.setText(new StringBuilder().append(dayOfMonth).append("/")
				//	      .append(monthOfYear+1).append("/").append(year));


			}
		}, year, month, day);

		dpd.show();
	}


	public void setTime(final View v)
	{
		final Calendar c = Calendar.getInstance();
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int  mMinute = c.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() 
		{

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
					int minute)
			{


				String hour1,min1;

				if(hourOfDay<10)
				{
					hour1 = "0"+String.valueOf(hourOfDay);
				}
				else
				{
					hour1 = String.valueOf(hourOfDay);
				}

				if(minute<10)
				{
					min1 = "0"+String.valueOf(minute);
				}
				else
				{
					min1 = String.valueOf(minute);
				}

				et_time.setText(hour1+":"+min1);

			}
		}, mHour, mMinute, DateFormat.is24HourFormat(context));

		tpd.show();
	}


	public String getMonth(int month)
	{
		String ls_month = null;

		if(month == 1)
		{
			ls_month = "Jan";
		}
		else if(month == 2)
		{
			ls_month = "Feb";
		}
		else if(month == 3)
		{
			ls_month = "Mar";
		}
		else if(month ==4)
		{
			ls_month = "Apr";
		}
		else if(month == 5)
		{
			ls_month = "May";
		}
		else if(month == 6)
		{
			ls_month = "Jun";
		}
		else if(month == 7)
		{
			ls_month = "Jul";
		}
		else if(month == 8)
		{
			ls_month = "Aug";
		}
		else if(month == 9)
		{
			ls_month = "Sep";
		}
		else if(month == 10)
		{
			ls_month = "Oct";
		}
		else if(month == 11)
		{
			ls_month = "Nov";
		}
		else if(month == 12)
		{
			ls_month = "Dec";
		}

		return ls_month;

	}


	@Override
	public void onBackPressed() 
	{

		Toast.makeText(context, "Back button has been pressed.", Toast.LENGTH_LONG).show();

		
		ContentValues cv;
		if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
		{
			String ls_date, ls_time, ls_systolic, ls_diastolic, ls_pulse, 
			ls_msr_ts, ls_pos, ls_loc, ls_remark;
			//int li_sequence;
	
			ls_date = et_date.getText().toString();
			ls_time = et_time.getText().toString();
			//ls_systolic = et_sys.getText().toString();
			//ls_diastolic=et_dias.getText().toString(); 
			//ls_pulse = et_pulse.getText().toString();
			ls_remark = et_remark.getText().toString();
			ls_systolic = sp_sys.getItemAtPosition(sp_sys.getSelectedItemPosition()).toString();
			ls_diastolic =sp_dias.getItemAtPosition(sp_dias.getSelectedItemPosition()).toString();
			ls_pulse  = sp_pulse.getItemAtPosition(sp_pulse.getSelectedItemPosition()).toString();
	
			ls_pos = sp_pos.getItemAtPosition(sp_pos.getSelectedItemPosition()).toString();
			ls_loc = sp_loc.getItemAtPosition(sp_loc.getSelectedItemPosition()).toString();
	
	
			ls_msr_ts = ls_date+"\t"+ls_time;
	
			if(ls_remark == null)
			{
				ls_remark = "";
			}
	
			if(!(ls_systolic.equals("")) && !(ls_diastolic.equals("")) && !(ls_pulse.equals("")))
			{
				cv = new ContentValues();
				cv.clear();
	
				Log.v(TAG,"ls_date : "+ls_date +",ls_time : "+ls_time +",ls_pulse : "+ls_pulse +
						",ls_systolic : "+ls_systolic +",ls_diastolic : "+ls_diastolic+",profile_id : "+profile_id);
	
				cv.put(BloodPressure_Master.PROFILE_ID, profile_id);
				cv.put(BloodPressure_Master.PM1_VALUE,  ls_systolic);
				cv.put(BloodPressure_Master.PM2_VALUE,  ls_diastolic);
				cv.put(BloodPressure_Master.PM3_VALUE,  ls_pulse);
				cv.put(BloodPressure_Master.MSR_TS,     ls_msr_ts);
				cv.put(BloodPressure_Master.MSR_LOCATION, ls_loc);
				cv.put(BloodPressure_Master.MSR_POSITION, ls_pos);
				cv.put(BloodPressure_Master.MSR_REMARKS,  "");
	
				bp_master.insert(cv);
	
				///
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				///
				//finish();
	
			}
		}
		else if(tabClicked.equals(MeasurementList_Profile.SUGAR))
		{
			String ls_date, ls_time, ls_value1,ls_value2,ls_remark, ls_msr_ts;
			 
			 ls_date = et_sdate.getText().toString();
			 ls_time = et_stime.getText().toString();
			 //ls_value1 = et_fasting.getText().toString();
			 //ls_value2 = et_pp.getText().toString();
			 ls_msr_ts = ls_date+"\t"+ls_time;
			 ls_remark = et_sremark.getText().toString();
			 ls_value1 = sp_fasting.getItemAtPosition(sp_fasting.getSelectedItemPosition()).toString();
			 ls_value2 = sp_pp.getItemAtPosition(sp_pp.getSelectedItemPosition()).toString();
			 
			 
			 if(ls_value1 == null)
			 {
				 ls_value1 = "";
			 }
			 if(ls_value2 == null)
			 {
				 ls_value2 = "";
			 }
			 if(ls_remark == null)
			 {
				 ls_remark = "";
			 }
			
			if(!(ls_value1.equals("")) || !(ls_value2.equals("")))
			{
				cv = new ContentValues();
					
				cv.clear();
					
				Log.v(TAG,"ls_date : "+ls_date +",ls_time : "+ls_time +",profile_id : "+profile_id
								 );
						
				cv.put(Sugar_Master.PROFILE_ID, profile_id);
				cv.put(Sugar_Master.PM4_VALUE,  ls_value1);
				cv.put(Sugar_Master.PM4_TIME,     ls_msr_ts);
				cv.put(Sugar_Master.PM4_REMARKS,ls_remark);
				cv.put(Sugar_Master.PM5_VALUE,  ls_value2);
				cv.put(Sugar_Master.PM5_TIME,     ls_msr_ts);
				cv.put(Sugar_Master.PM5_REMARKS, ls_remark);
				
				sugar.insert(cv);
				
				Intent i = new Intent();
				setResult(RESULT_OK, i);
			}
			
		}

		finish();
	}



}
