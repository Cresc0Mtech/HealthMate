package com.cresco.HealthMate;

import java.util.ArrayList;

import com.cresco.HealthMate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

class SimpleAdapter extends ArrayAdapter<Bundle>  
{

	String TAG = this.getClass().getSimpleName();
    static int mCount = 12;
    private final LayoutInflater mLayoutInflater;
    private final String mPositionString;
   // private final int mTextViewResourceId;
    private  ArrayList<Bundle> measure_list = new ArrayList<Bundle>();
    private Activity ctx;
    Typeface typeface;
    String parameter;

   SimpleAdapter(Activity ctx,ArrayList<Bundle> measure_list)
   {
    	super(ctx, R.layout.measurement_row ,measure_list);
        mLayoutInflater = LayoutInflater.from(ctx);
        mPositionString = "Position ";
        //mTextViewResourceId = textViewResourceId;
        this.measure_list = measure_list;
        this.ctx = ctx;
        //this.parameter = parameter;
        
        typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
    }

    public void addMoreItems(int count) {
        mCount += count;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Bundle getItem(int position) {
        //return mPositionString + position;
    	return measure_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder
	{
		protected CrescoTextView tv_listDate, tv_listTime,tv_listsystolic, tv_listdiasttolic, tv_listpulse;
		protected CrescoTextView tv_sdate, tv_stime,tv_fasting, tv_pp;
	}
    @SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        //final TextView tv;
    	//CrescoTextView tv_listTime, tv_listsystolic, tv_listdiasttolic, tv_listpulse, tv_listDate; 
        //if (convertView == null) 
        //{
            //tv = (TextView) mLayoutInflater.inflate (mTextViewResourceId, null);
        	 //tv.setText(itemList.get(position));

       // } 
        //else 
       // {
            //tv = (TextView) convertView;
       // }
        //tv.setText(getItem(position));
    	
    	View view = null;
		ViewHolder holder = null;
		
		if (convertView == null) 
		{
	        holder = new ViewHolder();
	        
	        LayoutInflater inflater = ctx.getLayoutInflater();
	        
//	        if(parameter.equals(MeasurementList_Profile.PRESSURE))
//			{
	        	view = inflater.inflate(R.layout.measurement_row, null);
//			}
//	        else
//	        {
//	        	view = inflater.inflate(R.layout.sugar_list_row, null);
//	        }
		
		
	       // holder.tv_listTime        =(CrescoTextView)view.findViewById(R.id.tv_listTime);
	        //holder.tv_listsystolic        =(CrescoTextView)view.findViewById(R.id.tv_listsystolic);
	        //holder.tv_listdiasttolic        =(CrescoTextView)view.findViewById(R.id.tv_listdiasttolic);
	        //holder.tv_listpulse        =(CrescoTextView)view.findViewById(R.id.tv_listpulse);
	       // holder.tv_listDate        =(CrescoTextView)view.findViewById(R.id.tv_listDate);
		
//	        holder.tv_sdate        =(CrescoTextView)view.findViewById(R.id.tv_sdate);
//	        holder.tv_stime        =(CrescoTextView)view.findViewById(R.id.tv_stime);
//	        holder.tv_fasting        =(CrescoTextView)view.findViewById(R.id.tv_fasting);
//	        holder.tv_pp        =(CrescoTextView)view.findViewById(R.id.tv_pp);
		
	       
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
			
//			((ViewHolder) view.getTag()).tv_sdate.setTag(position);
//			((ViewHolder) view.getTag()).tv_stime.setTag(position);
//			((ViewHolder) view.getTag()).tv_fasting.setTag(position);
//			((ViewHolder) view.getTag()).tv_pp.setTag(position);
		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

//		if(parameter.equals(MeasurementList_Profile.PRESSURE))
//		{
        
	        String Date = measure_list.get(position).getString("date").trim();
	    	String tm  = measure_list.get(position).getString("time").trim();
	    	String High  = measure_list.get(position).getString(BloodPressure_Master.PM1_VALUE);
	    	String Low = measure_list.get(position).getString(BloodPressure_Master.PM2_VALUE);
	    	String pulse = measure_list.get(position).getString(BloodPressure_Master.PM3_VALUE);
	
	    	viewholder.tv_listDate.setText(Date);
	    	viewholder.tv_listTime.setText(tm);
	    	viewholder.tv_listsystolic.setText(High);
	    	viewholder.tv_listdiasttolic.setText(Low);
	    	viewholder.tv_listpulse.setText(pulse);
	       // return tv;
	    	
	    	viewholder.tv_listDate.setTypeface(typeface);
	    	viewholder.tv_listTime.setTypeface(typeface);
	    	viewholder.tv_listsystolic.setTypeface(typeface);
	    	viewholder.tv_listdiasttolic.setTypeface(typeface);
	    	viewholder.tv_listpulse.setTypeface(typeface);
//		}
//		else
//		{
//			String Date1 = measure_list.get(position).getString("date").trim();
//			String time1 = measure_list.get(position).getString("time").trim();
//			String fast  = measure_list.get(position).getString(Sugar_Master.PM4_VALUE);
//			String pp = measure_list.get(position).getString(Sugar_Master.PM5_VALUE);
//				
//			viewholder.tv_sdate.setText(Date1);
//			viewholder.tv_stime.setText(time1);
//				
//				
//			viewholder.tv_fasting.setText(fast);
//			Log.v(TAG,"Fasting is not null  : "+fast);
//				
//			viewholder.tv_pp.setText(pp);
//			Log.v(TAG,"PP is not null  : "+pp);
//			
//			viewholder.tv_sdate.setTypeface(typeface);
//			viewholder.tv_stime.setTypeface(typeface);
//			viewholder.tv_fasting.setTypeface(typeface);
//			viewholder.tv_pp.setTypeface(typeface);
//		}
    	
    	view.postInvalidate();
		return view;
    }
}
