package com.cresco.HealthMate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cresco.HealthMate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	public final static String TAG = "MainActivity";
	Context context;
	EditText et_date,et_time,et_low,et_high;
	Button bt_cancel ,bt_save ,bt_opengraph;
	ListView lv_list;
	private int year, month, day;
	ContentValues cv;
	ArrayList<Bundle> list;
	AlertDialog alert;
	Dialog dialog;
	 EditText et_from,et_to;
	 DatePickerDialog  datePickerDialog;
	 NewAdapter adapter1;
	 ArrayList<Bundle> alist;
	
	public final static String LOW_LIST = "low_list";
	public final static String HIGH_LIST = "high_list";
	public final static String DATE_LIST = "date_list";
	public final static String SIZE = "size";
	
	
	SimpleCursorAdapter adapter;
	String ls_from[] = {T_BPMeasures.DATE,T_BPMeasures.TIME,T_BPMeasures.LOW_RANGE,T_BPMeasures.HIGH_RANGE};
	int li_to[]		 = {R.id.tv_rdate,R.id.tv_rtime,R.id.tv_rlow,R.id.tv_rhigh};
 
	T_BPMeasures bp;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		
		et_date = (EditText) findViewById(R.id.et_date);
		et_time = (EditText) findViewById(R.id.et_time);
		et_low = (EditText) findViewById(R.id.et_low);
		et_high = (EditText) findViewById(R.id.et_high);
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_save = (Button) findViewById(R.id.bt_save);
		bt_opengraph = (Button) findViewById(R.id.bt_opengraph);
		lv_list =  (ListView) findViewById(R.id.lv_list);
		
		et_date.setOnClickListener(this);
		et_time.setOnClickListener(this);
		et_low.setOnClickListener(this);
		et_high.setOnClickListener(this);
		bt_cancel.setOnClickListener(this);
		bt_save.setOnClickListener(this);
		bt_opengraph.setOnClickListener(this);
		
		final Calendar c = Calendar.getInstance();
         year = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day = c.get(Calendar.DAY_OF_MONTH);
		
         bp = new T_BPMeasures(context);
         //Cursor cursor = bp.getAlllist();
         
         //adapter = new SimpleCursorAdapter(this, R.layout.list_row, cursor, ls_from, li_to);
         
         alist = new ArrayList<Bundle>();
         alist = bp.getAllMeasureList();
         adapter1 = new NewAdapter(this, alist);
         
         lv_list.setAdapter(adapter1);
	}

	@SuppressLint("SimpleDateFormat")
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
				
			case R.id.bt_save:
				
				saveData();
				break;
				
			case R.id.bt_cancel:
				
				et_date.setText("");
				et_time.setText("");
				et_low.setText("");
				et_high.setText("");
				break;
				
				
			case R.id.bt_opengraph:
				
				showDateRangeDialog();
				break;
				
				
			case R.id.et_from:
				
				setDateFrom(v);
				break;
				
			case R.id.et_to:
				
				setDateTo(v);
				break;
				
			case R.id.bt_ok:
				
				String from = et_from.getText().toString();
				String to = et_to.getText().toString();
				
				
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        Date testDate = null,testDate1 = null;
		        try 
		        {
		            testDate = sdf.parse(from);
		            testDate1 = sdf.parse(to);
		        }
		        catch(Exception ex)
		        {
		            ex.printStackTrace();
		        }
		        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		        String ls_from = formatter.format(testDate);
		        String ls_to = formatter.format(testDate1);
		        
		        Log.v(TAG,"ls_from" + ls_from +" ,,,ls_to" +ls_to);
				
		        long m1 = testDate.getTime();
		        long m2 = testDate1.getTime();
		        
		        String d1 = String.valueOf(m1);
		        String d2 = String.valueOf(m2);
				//openGraph(ls_from , ls_to);
		        
		        openGraph(d1 , d2);
				dialog.dismiss();
				
				break;
				
			
			case R.id.bt_can:
	
				dialog.dismiss();
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
			        	  
			        	 
			             
			            	 et_date.setText(new StringBuilder().append(dayOfMonth).append("/")
			              	      .append(monthOfYear+1).append("/").append(year));
			             
			 
			         }
		 }, year, month, day);
			
		 dpd.show();
	 }
	 
	 
	 public void setDateFrom(View view) 
	 {
		 DatePickerDialog dpd = new DatePickerDialog(this,
			        new DatePickerDialog.OnDateSetListener() 
		 {
			 
			         @Override
			         public void onDateSet(DatePicker view, int year,
			                    int monthOfYear, int dayOfMonth) 
			         {
			        	 
			        	 
			             
			            	 et_from.setText(new StringBuilder().append(dayOfMonth).append("/")
			              	      .append(monthOfYear+1).append("/").append(year));
			             
			 
			         }
		 }, year, month, day);
			
		 dpd.show();
	 }
	 
	 public void setDateTo(View view) 
	 {
		 DatePickerDialog dpd = new DatePickerDialog(this,
			        new DatePickerDialog.OnDateSetListener() 
		 {
			 
			         @Override
			         public void onDateSet(DatePicker view, int year,
			                    int monthOfYear, int dayOfMonth) 
			         {
			        	 
			        	 
			             
			            	 et_to.setText(new StringBuilder().append(dayOfMonth).append("/")
			              	      .append(monthOfYear+1).append("/").append(year));
			             
			 
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
				if(v.getId() == R.id.et_time)
				{
							
					et_time.setText(hourOfDay + ":" + minute);
				}
						
			}
          }, mHour, mMinute, DateFormat.is24HourFormat(context));
        
        tpd.show();
	}
	
	@SuppressLint("SimpleDateFormat")
	public void saveData()
	{
		String date = et_date.getText().toString();
		
		String time = et_time.getText().toString();
		
		String low = et_low.getText().toString();
		
		String high = et_high.getText().toString();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        try 
        {
            testDate = sdf.parse(date);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //String ls_date = formatter.format(testDate);
        
        long millies = testDate.getTime();
        String d = String.valueOf(millies);
        
        if(!(d.equals("")) && !(time.equals("")) && !(low.equals("")) && !(high.equals("")))
        {
			cv = new ContentValues();
			cv.clear();
			
			//cv.put(T_BPMeasures.DATE, ls_date);
			cv.put(T_BPMeasures.DATE, d);
			cv.put(T_BPMeasures.TIME, time);
			cv.put(T_BPMeasures.LOW_RANGE, low);
			cv.put(T_BPMeasures.HIGH_RANGE, high);
			
			bp.insert(cv);
			Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT)
		      .show();
			
			et_date.setText("");
			et_time.setText("");
			et_low.setText("");
			et_high.setText("");
        }
        else
        {
        	Toast.makeText(context, "Insert data in blank field.", Toast.LENGTH_SHORT)
  	      .show();
        }
		
		
		
		//adapter.notifyAll();
		
		alist = bp.getAllMeasureList();
        adapter1 = new NewAdapter(this, alist);
        lv_list.setAdapter(adapter1);
	}
	
	
	
	@SuppressLint("SimpleDateFormat")
	public void showDateRangeDialog()
	{
		////////////////////////////
		/*LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		
		alertDialogBuilder.setView(promptsView);


		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() 
			 {
			    public void onClick(DialogInterface dialog,int id) 
			    {
			    	
			    	list = new ArrayList<Bundle>();
					list = bp.getAllMeasureList();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() 
			  {
			    public void onClick(DialogInterface dialog,int id) 
			    {
			    	dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();*/
		
		
        dialog = new Dialog(this);
       
        dialog.setContentView(R.layout.dialog);
        
        dialog.setTitle("Custom Dialog");

         et_from = (EditText) dialog
				.findViewById(R.id.et_from);
		
		et_to = (EditText) dialog
				.findViewById(R.id.et_to);
		
		final Button bt_ok = (Button) dialog
				.findViewById(R.id.bt_ok);
		
		final Button bt_can = (Button) dialog
				.findViewById(R.id.bt_can);
		
		////////////
		int c = alist.size();
		
		if(c>0)
		{
			String l = alist.get(0).getString(T_BPMeasures.DATE);
			String h = alist.get(c-1).getString(T_BPMeasures.DATE);
			
			long d1 = Long.valueOf(l);
			long d2 = Long.valueOf(h);
		     
			 Date currentDate1 = new Date(d1);
			 Date currentDate2 = new Date(d2);
		     
		     
		     SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		     String s1 = df.format(currentDate1);
		     String s2 = df.format(currentDate2);
		     
		     et_from.setText(s1);
		     et_to.setText(s2);
		}
		else
		{
			et_from.setText("9/12/2014");
		     et_to.setText("11/12/2014");
		}
		///////////
		
		et_from.setOnClickListener(this);
		et_to.setOnClickListener(this);
		bt_ok.setOnClickListener(this);
		bt_can.setOnClickListener(this);

        dialog.show();
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("deprecation")
	public void openGraph(String from ,String to)
	{
		list = new ArrayList<Bundle>();
		if(from != null)
		{
			list = bp.getAllMeasureListByDate(from , to);
		}
		Log.v(TAG,"from"+from +",to"+to);
		
		Log.v(TAG,"list bet date :"+list.toString());
		int[] lowlist = new int[list.size()];
		int[] highlist = new int[list.size()];
		String[] dates = new String[list.size()];
		
		for (int i = 0; i<list.size(); i++)
		{
			lowlist[i] = Integer.parseInt(list.get(i).getString(T_BPMeasures.LOW_RANGE));
			
			highlist[i] = Integer.parseInt(list.get(i).getString(T_BPMeasures.HIGH_RANGE));
			
			//dates[i] = list.get(i).getString(T_BPMeasures.DATE);
			
			////////////////////////////////////////
			long currentDateTime = Long.valueOf(list.get(i).getString(T_BPMeasures.DATE));
		     
			
			 Date currentDate = new Date(currentDateTime);
		     
		     SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		     dates[i] = df.format(currentDate);
		}
		
		Log.v(TAG,"lowlist :"+lowlist);
		Log.v(TAG,"dates :"+dates);
		Log.v(TAG,"highlist :"+highlist);
		Intent i = new Intent(this, GraphActivity.class);
		i.putExtra(SIZE, String.valueOf(list.size()));
		i.putExtra(LOW_LIST, lowlist);
		i.putExtra(HIGH_LIST, highlist);
		i.putExtra(DATE_LIST, dates);
		
		startActivity(i);
	}
	
	
	
	public class NewAdapter extends ArrayAdapter<Bundle> 
	{
		private final Activity context;
		private  ArrayList<Bundle> web = new ArrayList<Bundle>();

		public NewAdapter(Activity context,ArrayList<Bundle> web) 
		{
			super(context, R.layout.list_row ,web);
			this.context = context;
			this.web = web;
		}
		
		private class ViewHolder
		{
			
			TextView tv_rdate,tv_rtime,tv_rlow,tv_rhigh;
			
		}


		@SuppressWarnings("deprecation")
		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View view, ViewGroup parent) 
		{
			Log.v("ContactAdapter","In adapter");
			
			ViewHolder holder = null;
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = null;
			
			if(rowView == null)
			{
				
				rowView = inflater.inflate(R.layout.list_row, null);

				holder = new ViewHolder();
				
				holder.tv_rdate = (TextView) rowView.findViewById(R.id.tv_rdate);
				holder.tv_rtime = (TextView) rowView.findViewById(R.id.tv_rtime);
				holder.tv_rlow = (TextView) rowView.findViewById(R.id.tv_rlow);
				holder.tv_rhigh = (TextView) rowView.findViewById(R.id.tv_rhigh);
				
				rowView.setTag(holder);
			}
			

			holder = (ViewHolder) rowView.getTag();
			
			
			String Date = web.get(position).getString(T_BPMeasures.DATE);
			String Time = web.get(position).getString(T_BPMeasures.TIME);
			String Low  = web.get(position).getString(T_BPMeasures.LOW_RANGE);
			String High = web.get(position).getString(T_BPMeasures.HIGH_RANGE);
			
			long currentDateTime = Long.valueOf(Date);
		     
			 Date currentDate = new Date(currentDateTime);
		     
		     
		     SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		     String s = df.format(currentDate);
		     
			//holder.tv_rdate.setText(Date);
		     holder.tv_rdate.setText(s);
			holder.tv_rtime.setText(Time);
			holder.tv_rlow.setText(Low);
			holder.tv_rhigh.setText(High);
			
			/*if(Integer.parseInt(Low) >85 || Integer.parseInt(Low) <75 || 
					Integer.parseInt(High) >125 || Integer.parseInt(High) <115)
			{
				holder.tv_rdate.setTextColor(Color.RED);
				holder.tv_rtime.setTextColor(Color.RED);
				holder.tv_rlow.setTextColor(Color.RED);
				holder.tv_rhigh.setTextColor(Color.RED);
			}*/
			if(Integer.parseInt(Low) >85 || Integer.parseInt(Low) <75)
			{
				holder.tv_rlow.setTextColor(Color.RED);
			}
			
			if(Integer.parseInt(High) >125 || Integer.parseInt(High) <115)
			{
				holder.tv_rhigh.setTextColor(Color.RED);
			}
			
			return rowView;
			
		}
		


	}
}
