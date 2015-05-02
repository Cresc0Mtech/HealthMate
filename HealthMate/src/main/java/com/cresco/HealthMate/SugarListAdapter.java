package com.cresco.HealthMate;


import java.util.ArrayList;

import com.cresco.HealthMate.SimpleAdapter.ViewHolder;
import com.cresco.HealthMate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SugarListAdapter extends ArrayAdapter<Bundle>  
{

	String TAG = this.getClass().getSimpleName();
    static int mCount = 8;
    private final LayoutInflater mLayoutInflater;
    private final String mPositionString;
   // private final int mTextViewResourceId;
    private  ArrayList<Bundle> measure_list = new ArrayList<Bundle>();
    private Activity ctx;
    Typeface typeface;
    String parameter;
    MeasurementList_Profile measure;

  SugarListAdapter(Activity ctx,ArrayList<Bundle> measure_list)
  {
    	super(ctx, R.layout.measurement_row ,measure_list);
        mLayoutInflater = LayoutInflater.from(ctx);
        mPositionString = "Position ";
        //mTextViewResourceId = textViewResourceId;
        this.measure_list = measure_list;
        this.ctx = ctx;
        //this.parameter = parameter;
        
        typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
        
        measure = new MeasurementList_Profile();
    }

   /*
      public void addMoreItems(int count) 
    {
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
    */
    static class ViewHolder
	{
		protected TextView tv_sdate1, tv_stime1,tv_sdate2, tv_stime2, tv_fasting, tv_pp;
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
       
    	View view = null;
		ViewHolder holder = null;
		
		if (convertView == null) 
		{
	        holder = new ViewHolder();
	        
	        LayoutInflater inflater = ctx.getLayoutInflater();
	        
	        view = inflater.inflate(R.layout.sugar_list_row, null);
	        
	        
	        holder.tv_sdate1        =(TextView)view.findViewById(R.id.tv_sdate1);
	        holder.tv_stime1        =(TextView)view.findViewById(R.id.tv_stime1);
            //holder.tv_sdate2        =(TextView)view.findViewById(R.id.tv_sdate2);
            holder.tv_stime2        =(TextView)view.findViewById(R.id.tv_stime2);
	        holder.tv_fasting        =(TextView)view.findViewById(R.id.tv_fasting);
	        holder.tv_pp        =(TextView)view.findViewById(R.id.tv_pp);

	        view.setTag(holder);
		}
		else
		{
			 view = convertView;

			
			((ViewHolder) view.getTag()).tv_sdate1.setTag(position);
			((ViewHolder) view.getTag()).tv_stime1.setTag(position);
           // ((ViewHolder) view.getTag()).tv_sdate2.setTag(position);
            ((ViewHolder) view.getTag()).tv_stime2.setTag(position);
			((ViewHolder) view.getTag()).tv_fasting.setTag(position);
			((ViewHolder) view.getTag()).tv_pp.setTag(position);
		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

		
		
			//String Date1 = measure_list.get(position).getString("date1").trim();
			//String time1 = measure_list.get(position).getString("time1").trim();
            //String Date2 = measure_list.get(position).getString("date2").trim();
            //String time2 = measure_list.get(position).getString("time2").trim();
            String Date1 = measure_list.get(position).getString(Sugar_Master.MSR_DATE).trim();
            String time1 = measure_list.get(position).getString(Sugar_Master.PM4_TIME).trim();
            String time2 = measure_list.get(position).getString(Sugar_Master.PM5_TIME).trim();
			String fast  = measure_list.get(position).getString(Sugar_Master.PM4_VALUE);
			String pp    = measure_list.get(position).getString(Sugar_Master.PM5_VALUE);

        //Log.v(TAG, "DATE :" +Date1);

        String date1 = "" , tm1 = "", tm3 = "";

                String date[] = Date1.split("-");
                String d = date[2];
                String m = measure.getMonth(Integer.parseInt(date[1]));
                String y = date[0];
                date1 = d + " " + m + ", " + y;


        if(!(time1.equals(""))) {
            String time[] = time1.split(":");
            String h = time[0];
            String mn = time[1];
            tm1 = h + ":" + mn;
        }

//            String date2[] =Date2.split("-");
//            String d2 = date2[2];
//            String m2 = measure.getMonth(Integer.parseInt(date2[1]));
//            String y2= date2[0];
//            String date3 = d2+" "+m2+", "+y2;

        if(!(time2.equals(""))) {
            String time3[] = time2.split(":");
            String h2 = time3[0];
            String mn2 = time3[1];
            tm3 = h2 + ":" + mn2;
        }
			
			viewholder.tv_sdate1.setText(date1);
			viewholder.tv_stime1.setText(tm1);
            //viewholder.tv_sdate2.setText(date3);
            viewholder.tv_stime2.setText(tm3);
			viewholder.tv_fasting.setText(fast);
			//Log.v(TAG,"Fasting is not null  : "+fast);
				
			viewholder.tv_pp.setText(pp);
			//Log.v(TAG,"PP is not null  : "+pp);
			
			viewholder.tv_sdate1.setTypeface(typeface);
			viewholder.tv_stime1.setTypeface(typeface);
           // viewholder.tv_sdate2.setTypeface(typeface);
            viewholder.tv_stime2.setTypeface(typeface);
			viewholder.tv_fasting.setTypeface(typeface);
			viewholder.tv_pp.setTypeface(typeface);
		
    	
    	view.postInvalidate();
		return view;
    }
}
