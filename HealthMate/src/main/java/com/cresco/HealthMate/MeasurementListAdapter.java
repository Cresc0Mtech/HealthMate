package com.cresco.HealthMate;

import java.util.ArrayList;

import com.cresco.HealthMate.R;
import com.google.android.gms.internal.ll;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MeasurementListAdapter extends ArrayAdapter<Bundle>  //implements OnTouchListener
{
	String TAG = this.getClass().getSimpleName();
	private final Activity context;
	private  ArrayList<Bundle> measure_list = new ArrayList<Bundle>();
	String parameter;
	Typeface typeface;
	MeasurementList_Profile measure;
	
	float historicX = Float.NaN, historicY = Float.NaN;
	final int DELTA = 50;
	enum Direction {LEFT, RIGHT;}
	
	 ListViewSwipeDetector lvSwipeDetector;

	public MeasurementListAdapter(Activity context,ArrayList<Bundle> measure_list, String parameter) 
	{
		super(context, R.layout.measurement_row ,measure_list);
		this.context = context;
		this.measure_list = measure_list;
		this.parameter = parameter;
		
		typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		measure = new MeasurementList_Profile();
	}

	
	static class ViewHolder
	{
		protected TextView tv_listDate, tv_listTime,tv_listsystolic, tv_listdiasttolic, tv_listpulse;
		LinearLayout ll_listmain,  ll_listpressure ;
		GestureDetectorCompat mDetector;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Log.v("ContactAdapter","In adapter");
		View view = null;
		ViewHolder holder = null;
		
		if (convertView == null) 
		{
	        holder = new ViewHolder();
	        
	        LayoutInflater inflater = context.getLayoutInflater();
	        
	        view = inflater.inflate(R.layout.measurement_row, null);
		
		
	        holder.tv_listTime        =(TextView)view.findViewById(R.id.tv_listTime);
	        holder.tv_listsystolic        =(TextView)view.findViewById(R.id.tv_listsystolic);
	        holder.tv_listdiasttolic        =(TextView)view.findViewById(R.id.tv_listdiasttolic);
	        holder.tv_listpulse        =(TextView)view.findViewById(R.id.tv_listpulse);
	        holder.tv_listDate        =(TextView)view.findViewById(R.id.tv_listDate);
	        
	        holder.ll_listmain = (LinearLayout)view.findViewById(R.id.ll_listmain);
	       // holder.ll_expanding = (LinearLayout)view.findViewById(R.id.ll_expanding);
	        //holder.ll_listpressure = (LinearLayout)view.findViewById(R.id.ll_listpressure);
	       
	        
	        view.setTag(holder);
		}
		else
		{
			view = convertView;

			((ViewHolder) view.getTag()).tv_listDate.setTag(position);
			((ViewHolder) view.getTag()).tv_listTime.setTag(position);
			((ViewHolder) view.getTag()).tv_listsystolic.setTag(position);
			((ViewHolder) view.getTag()).tv_listpulse.setTag(position);
			((ViewHolder) view.getTag()).tv_listdiasttolic.setTag(position);
			
			//((ViewHolder) view.getTag()).ll_listmain.setTag(position);
			//((ViewHolder) view.getTag()).ll_expanding.setTag(position);
		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

			String Date = measure_list.get(position).getString("date").trim();
			String tm  = measure_list.get(position).getString("time").trim();
			String High  = measure_list.get(position).getString(BloodPressure_Master.PM1_VALUE);
			String Low = measure_list.get(position).getString(BloodPressure_Master.PM2_VALUE);
			String pulse = measure_list.get(position).getString(BloodPressure_Master.PM3_VALUE);
		
			//Log.v(TAG,"Date:"+Date);
			String date[] =Date.split("-");
			String d = date[2];
			String m = measure.getMonth(Integer.parseInt(date[1]));
			String y= date[0];
			String date1 = d+" "+m+", "+y;
			
			String time[] =tm.split(":");
			String h =  time[0];
			String mn = time[1];
			//String s= date[2];
			String tm1 = h+":"+mn;
			
			
			viewholder.tv_listDate.setText(date1);
			viewholder.tv_listTime.setText(tm1);
			viewholder.tv_listsystolic.setText(High);
			viewholder.tv_listdiasttolic.setText(Low);
			viewholder.tv_listpulse.setText(pulse);
			
			viewholder.tv_listDate.setTypeface(typeface);
		    viewholder.tv_listTime.setTypeface(typeface);
		    viewholder.tv_listsystolic.setTypeface(typeface);
		    viewholder.tv_listdiasttolic.setTypeface(typeface);
		    viewholder.tv_listpulse.setTypeface(typeface);
		    
		    
		    //////////////////
		   /*
		    final View toolbar = view.findViewById(R.id.ll_expanding);
		    final View toolbar1 = view.findViewById(R.id.ll_listpressure);
		    final View toolbar2 = view.findViewById(R.id.ll_listmain);
		    
		    toolbar2.clearAnimation();
		    
		    toolbar1.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View arg0) 
				{
					FlipAnimation flipAnimation = new FlipAnimation(toolbar1,toolbar, 0);
					
					if (toolbar1.getVisibility() == View.GONE)
				    {
				        flipAnimation.reverse();
				    }
					
					toolbar2.startAnimation(flipAnimation);
				}
			});
		    */
		    
//		    View toolbar = view.findViewById(R.id.ll_expanding);
//	        ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -100;
//	        toolbar.setVisibility(View.GONE);
		    /////////////////////////

		
		return view;
	}
	
}

