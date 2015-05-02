package com.cresco.HealthMate;


import java.io.File;
import java.util.ArrayList;

import com.cresco.HealthMate.SimpleAdapter.ViewHolder;
import com.cresco.HealthMate.R;
import com.google.android.gms.internal.ll;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.sax.StartElementListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Grid_Adapter extends ArrayAdapter<Bundle>  
{

	String TAG = this.getClass().getSimpleName();
    
    private  ArrayList<Bundle> profile_list = new ArrayList<Bundle>();
    private Activity ctx;
    Typeface typeface;
    int height_set, width_set, height;
    float density;
     public static String PROFILE_OPERATION = "profile_operation";
     public static String PROFILE_ID = "profile_id";
   
    MeasurementList_Profile measure;
    Profile_Master p_master;
    Context context;
    New_Grid newGrid;

    Grid_Adapter(Activity ctx,ArrayList<Bundle> profile_list, int height_set, int width_set, int height, float density)
    {
    	super(ctx, R.layout.measurement_row ,profile_list);
       
        this.profile_list = profile_list;
        this.ctx = ctx;
        this.height_set = height_set;
        this.width_set = width_set;
        
        this.height = height;

        this.density = density;
        
        typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
        
        measure = new MeasurementList_Profile();
        p_master = new Profile_Master(context);
        newGrid = new New_Grid();
    }

    static class ViewHolder
	{
		ImageView iv_profile_image;
		LinearLayout ll_grid_item, ll_item_view, ll_grid_image, ll_grid_name;
		
		TextView tv_profile_name, tv_edit_profile, tv_delete_profile;
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
	        
	        view = inflater.inflate(R.layout.grid_item, null);
	        
	        holder.iv_profile_image = (ImageView)view.findViewById(R.id.iv_profile_image);
	        holder.tv_profile_name = (TextView)view.findViewById(R.id.tv_profile_name);
	        holder.tv_edit_profile = (TextView)view.findViewById(R.id.tv_edit_profile);
	        holder.tv_delete_profile = (TextView)view.findViewById(R.id.tv_delete_profile);
	        holder.ll_grid_item = (LinearLayout)view.findViewById(R.id.ll_grid_item);
	        holder.ll_item_view = (LinearLayout)view.findViewById(R.id.ll_item_view);
	        holder.ll_grid_image = (LinearLayout)view.findViewById(R.id.ll_grid_image);
	        holder.ll_grid_name = (LinearLayout)view.findViewById(R.id.ll_grid_name);

	        holder.ll_grid_image.setTag(position);
	        view.setTag(holder);
		}
		else
		{
			 view = convertView;
			
			((ViewHolder) view.getTag()).iv_profile_image.setTag(position);
			((ViewHolder) view.getTag()).tv_profile_name.setTag(position);
			((ViewHolder) view.getTag()).tv_edit_profile.setTag(position);
			((ViewHolder) view.getTag()).tv_delete_profile.setTag(position);
			((ViewHolder) view.getTag()).ll_grid_image.setTag(position);

		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

			final int li_profileid = profile_list.get(position).getInt(Profile_Master.PROFILE_ID);
		
			String ls_fname = profile_list.get(position).getString(Profile_Master.FIRST_NAME);
			String ls_lname = profile_list.get(position).getString(Profile_Master.LAST_NAME);
			
			ViewGroup.LayoutParams layoutParams = viewholder.ll_grid_item.getLayoutParams();

       // Log.v(TAG,"Profile ID in Grid_Adapter :"+li_profileid);
				
			layoutParams.height = height_set;
			viewholder.ll_grid_item.setLayoutParams(layoutParams);
			
			viewholder.tv_profile_name.setText(ls_fname+" "+ls_lname);

        //public static String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
			//File imgFile = new  File(Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images/Profile"+ls_fname+".jpg");
        File imgFile = new  File(Add_Profile.iconsStoragePath+"/Profile"+li_profileid+".jpg");

        //Log.v(TAG,"image file :"+imgFile.getAbsolutePath().toString());

        //viewholder.iv_profile_image.setBackgroundDrawable(null);
        viewholder.iv_profile_image.setImageBitmap(null);

			if(imgFile.exists())
			{
                //Log.v(TAG,"In set image -"+li_profileid);
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
			    Bitmap b = getRoundedShape(myBitmap);
			    viewholder.iv_profile_image.setImageBitmap(b);
			}
            else
            {
                viewholder.iv_profile_image.setImageResource(R.drawable.p_frame);
            }
			
			viewholder.tv_profile_name.setTypeface(typeface);
			viewholder.tv_delete_profile.setTypeface(typeface);
			viewholder.tv_edit_profile.setTypeface(typeface);
			
			//viewholder.ll_grid_image.setOnTouchListener(new onDoubleClick());
			/*
			viewholder.ll_grid_image.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					//Intent i = new Intent(ctx, Add_Profile.class);
					///i.putExtra(PROFILE_OPERATION, "Edit");
					//i.putExtra(PROFILE_ID, li_profileid);
					//ctx.startActivity(i);
					
					Intent i = new Intent(ctx, MeasurementList_Profile.class);
					i.putExtra("ProfileId", li_profileid);
					
					ctx.startActivity(i);
					
					Log.v(TAG,"onclick has called");
					
				}
			});
			
			viewholder.ll_grid_image.setOnLongClickListener(new View.OnLongClickListener() 
			{
				@Override
				public boolean onLongClick(View v) 
				{
					Intent i = new Intent(ctx, Add_Profile.class);
					i.putExtra(PROFILE_OPERATION, "Edit");
					i.putExtra(PROFILE_ID, li_profileid);
					ctx.startActivity(i);
					
					Log.v(TAG,"onLongclick has called");
					return false;
					
				}
			});
			*/
			
			/*
			viewholder.tv_edit_profile.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					Intent i = new Intent(ctx, Add_Profile.class);
					i.putExtra(PROFILE_OPERATION, "Edit");
					i.putExtra(PROFILE_ID, li_profileid);
					ctx.startActivity(i);
					
				}
			});
			
			viewholder.tv_delete_profile.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					p_master.delete_profile(li_profileid);
					
					//notifyDataSetChanged();
					//newGrid.set_Profile_grid();
					
				}
			});
		
			
			final GestureDetector gestureDectector = new GestureDetector(context, new GestureListener());        
			viewholder.iv_profile_image.setOnTouchListener(new OnTouchListener() 
			{

			            @Override
			            public boolean onTouch(View v, MotionEvent event) 
			            {
			                gestureDectector.onTouchEvent(event);
			                return true;
			            }

						
			});
			
			
			
			viewholder.ll_grid_image.setOnLongClickListener(new View.OnLongClickListener() 
			{
				@Override
				public boolean onLongClick(View v) 
				{
					viewholder.ll_item_view.setVisibility(View.VISIBLE);
					return false;
				}
			});
			
			viewholder.ll_grid_name.setOnLongClickListener(new View.OnLongClickListener() 
			{
				@Override
				public boolean onLongClick(View v) 
				{
					viewholder.ll_item_view.setVisibility(View.VISIBLE);
					return false;
				}
			});
    	*/
    	view.postInvalidate();
		return view;
    }
    
    
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) 
	{

    	int targetWidth = 0;
        int targetHeight = 0;
		
		//(TAG,"targetHeight : "+height);

       String screen = newGrid.getScreenResolution(getContext());

        //Log.v(TAG, "screen :" +screen);

        if(screen.equals("X-LARGE"))
        {
            targetWidth = (int) (250 * density);
            targetHeight = (int) (250 * density);

        }
        else if(screen.equals("LARGE"))
        {
            targetWidth = (int) (200 * density);
            targetHeight = (int) (200 * density);
        }
        else if(screen.equals("NORMAL"))
        {
            targetWidth = (int) (100 * density);
            targetHeight = (int) (100 * density);
        }
        else if(screen.equals("SMALL"))
        {
            targetWidth = 150;
            targetHeight = 150;
        }
        else
        {
            targetWidth = 150;
            targetHeight = 150;
        }

        //Log.v(TAG, "targetWidth : "+ targetWidth+"targetHeight : "+ targetHeight);

    	/*
		if(height == 2464)
    	{
    		targetWidth = 500;
    		targetHeight = 500;
    	}
		else if(height == 1280)
		{
		   targetWidth =300;
		   targetHeight =300;
		}
		else if(height == 976)
		{
			targetWidth = 150;
			targetHeight = 150;
		}
		else if(height == 1184)
		{
			targetWidth = 200;
			targetHeight = 200;
		}
        else
        {
            targetWidth = 150;
            targetHeight = 150;
        }
        */
		  Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
		                            targetHeight,Bitmap.Config.ARGB_8888);

		                Canvas canvas = new Canvas(targetBitmap);
		  Path path = new Path();
		  path.addCircle(((float) targetWidth - 1) / 2,
		  ((float) targetHeight - 1) / 2,
		  (Math.min(((float) targetWidth), 
		                ((float) targetHeight)) / 2),
		          Path.Direction.CCW);

		                canvas.clipPath(path);
		  Bitmap sourceBitmap = scaleBitmapImage;
		  canvas.drawBitmap(sourceBitmap, 
		                                new Rect(0, 0, sourceBitmap.getWidth(),
		    sourceBitmap.getHeight()), 
		                                new Rect(0, 0, targetWidth,
		    targetHeight), null);
		  return targetBitmap;
		}
    
    
    public class GestureListener extends GestureDetector.SimpleOnGestureListener 
	{
		int pos;
		
	    public boolean onDown(MotionEvent e) 
	    {
	        return true;
	    }

	    public boolean onDoubleTap(MotionEvent e) 
	    {
	        Log.d("Double_Tap", "Yes, Clicked");
	        
	        //int position = profile_grid.pointToPosition((int) e.getX(), (int) e.getY());
	        //int position = profile_grid.pointToPosition(e.getX(), e.getY());
	        
	        Toast.makeText(context, "You clicked on grid no. ", Toast.LENGTH_LONG).show();
	        return true;
	    }
	}
    
    
    
    class onDoubleClick implements View.OnTouchListener
	{     
		int count = 0;   
		int firClick = 0;   
		int secClick = 0;   
		@Override    
		public boolean onTouch(View v, MotionEvent event) 
		{     
			if(MotionEvent.ACTION_DOWN == event.getAction())
			{     
				count++;    

				if(count == 1)
				{     
					firClick = (int) System.currentTimeMillis();  
					//Log.v(TAG,"first click : "+firClick);
					

				}
				else if (count == 2)
				{     
					secClick = (int) System.currentTimeMillis();     
					if(secClick - firClick < 1000)
					{     
						//  
						int li_pos = (Integer) v.getTag();
						
						//Intent intent = new Intent(ChatActivity.INTENT_IMAGE_CLICKED);
						//intent.putExtra(TAG_CLICKED_POSITION, li_pos);
						//intent.putExtra(TAG_CLICKED_FILE_LOCATION, "");
						//intent.putExtra(TAG_DOUBLE_CLICKED, true);
						
						//context.sendBroadcast(intent);
						//Log.v(TAG,"secClick : "+secClick);
						//Log.v(TAG,"pos = "+li_pos);

						Toast.makeText(ctx, "on double click pos "+li_pos, Toast.LENGTH_SHORT).show();
					}     
					count = 0;     
					firClick = 0;     
					secClick = 0;     
				}     
			}     
			return true;     
		}     
	}
    

}
