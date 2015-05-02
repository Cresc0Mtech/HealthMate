/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cresco.HealthMate;

import java.util.List;
import com.cresco.HealthMate.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is a custom array adapter used to populate the listview whose items will
 * expand to display extra content in addition to the default display.
 */
public class CustomArrayAdapter extends ArrayAdapter<ExpandableListItem> {

    
    private int mLayoutViewResourceId;
    
    String TAG = this.getClass().getSimpleName();
	private final Activity context;
	private  List<ExpandableListItem> measure_list ;//= new ArrayList<Bundle>();
	String parameter;
	
	
	Typeface typeface;
	MeasurementList_Profile measure;

    public CustomArrayAdapter(Activity context, int layoutViewResourceId,
                              List<ExpandableListItem> measure_list) 
    {
        super(context, layoutViewResourceId, measure_list);
        this.measure_list = measure_list;
        mLayoutViewResourceId = layoutViewResourceId;
        this.context = context;
        
        typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		measure = new MeasurementList_Profile();
    }
   
    
    static class ViewHolder
	{
		protected TextView tv_listDate, tv_listTime,tv_listsystolic, tv_listdiasttolic, tv_listpulse,
		tv_remark, tv_pos, tv_loc;
		
		ExpandingLayout expandingLayout ;
		LinearLayout linearLayout;
		
	}

   
    /**
     * Populates the item in the listview cell with the appropriate data. This method
     * sets the thumbnail image, the title and the extra text. This method also updates
     * the layout parameters of the item's view so that the image and title are centered
     * in the bounds of the collapsed view, and such that the extra text is not displayed
     * in the collapsed state of the cell.
     */
    @SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        final ExpandableListItem object = measure_list.get(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(mLayoutViewResourceId, parent, false);
        }

        LinearLayout linearLayout = (LinearLayout)(convertView.findViewById(
                R.id.item_linear_layout));
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
                (AbsListView.LayoutParams.MATCH_PARENT, object.getCollapsedHeight());
        linearLayout.setLayoutParams(linearLayoutParams);

        ImageView imgView = (ImageView)convertView.findViewById(R.id.image_view);
        TextView titleView = (TextView)convertView.findViewById(R.id.title_view);
        TextView textView = (TextView)convertView.findViewById(R.id.text_view);

        titleView.setText(object.getTitle());
        imgView.setImageBitmap(getCroppedBitmap(BitmapFactory.decodeResource(getContext()
                .getResources(), object.getImgResource(), null)));
        textView.setText(object.getText());

        convertView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT));

        ExpandingLayout expandingLayout = (ExpandingLayout)convertView.findViewById(R.id
                .expanding_layout);
        expandingLayout.setExpandedHeight(object.getExpandedHeight());
        expandingLayout.setSizeChangedListener(object);

        if (!object.isExpanded()) {
            expandingLayout.setVisibility(View.GONE);
        } else {
            expandingLayout.setVisibility(View.VISIBLE);
        }

        return convertView;*/
        
    	 final ExpandableListItem object = measure_list.get(position);
    	 
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
	        //holder.tv_remark        =(TextView)view.findViewById(R.id.tv_premark);
	        //holder.tv_pos        =(TextView)view.findViewById(R.id.tv_ppos);
	        //holder.tv_loc        =(TextView)view.findViewById(R.id.tv_ploc);
	        
	        //holder.expandingLayout = (ExpandingLayout)view.findViewById(R.id
	        //        .expanding_layout);
	        
	        holder.linearLayout = (LinearLayout)(view.findViewById(
	                R.id.ll_listpressure));
	        
	        
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
			((ViewHolder) view.getTag()).tv_remark.setTag(position);
			((ViewHolder) view.getTag()).tv_pos.setTag(position);
			((ViewHolder) view.getTag()).tv_loc.setTag(position);
			((ViewHolder) view.getTag()).expandingLayout.setTag(position);
			((ViewHolder) view.getTag()).linearLayout.setTag(position);
		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

		
		Bundle bundle = measure_list.get(position).getBPlist();
		
			String Date 	= bundle.getString("date").trim();
			String tm  		= bundle.getString("time").trim();
			String High  	= bundle.getString(BloodPressure_Master.PM1_VALUE);
			String Low 		= bundle.getString(BloodPressure_Master.PM2_VALUE);
			String pulse 	= bundle.getString(BloodPressure_Master.PM3_VALUE);
			String pos  	= bundle.getString(BloodPressure_Master.MSR_POSITION);
			String loc 		= bundle.getString(BloodPressure_Master.MSR_LOCATION);
			String remark 	= bundle.getString(BloodPressure_Master.MSR_REMARKS);
		
			//Log.v(TAG,"Date:"+Date);
			String date[] =Date.split("-");
			String d = date[2];
			String m = measure.getMonth(Integer.parseInt(date[1]));
			String y= date[0];
			String date1 = d+" "+m+", "+y;
			
			viewholder.tv_listDate.setText(date1);
			viewholder.tv_listTime.setText(tm);
			viewholder.tv_listsystolic.setText(High);
			viewholder.tv_listdiasttolic.setText(Low);
			viewholder.tv_listpulse.setText(pulse);
			
			viewholder.tv_remark.setText(remark);
			viewholder.tv_pos.setText(pos);
			viewholder.tv_loc.setText(loc);
			
			viewholder.tv_listDate.setTypeface(typeface);
		    viewholder.tv_listTime.setTypeface(typeface);
		    viewholder.tv_listsystolic.setTypeface(typeface);
		    viewholder.tv_listdiasttolic.setTypeface(typeface);
		    viewholder.tv_listpulse.setTypeface(typeface);
		    viewholder.tv_remark.setTypeface(typeface);
		    viewholder.tv_pos.setTypeface(typeface);
		    viewholder.tv_loc.setTypeface(typeface);
		    
		    LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams
	                (AbsListView.LayoutParams.MATCH_PARENT, object.getCollapsedHeight());
		    viewholder.linearLayout.setLayoutParams(linearLayoutParams);
		    
		    //ExpandingLayout expandingLayout = (ExpandingLayout)convertView.findViewById(R.id
	         //       .expanding_layout);
		    viewholder.expandingLayout.setExpandedHeight(object.getExpandedHeight());
		    viewholder.expandingLayout.setSizeChangedListener(object);

	        if (!object.isExpanded()) 
	        {
	        	viewholder.expandingLayout.setVisibility(View.GONE);
	        } 
	        else 
	        {
	        	viewholder.expandingLayout.setVisibility(View.VISIBLE);
	        }

		
		return view;
    }

    /**
     * Crops a circle out of the thumbnail photo.
     */
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        int halfWidth = bitmap.getWidth()/2;
        int halfHeight = bitmap.getHeight()/2;

        canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}