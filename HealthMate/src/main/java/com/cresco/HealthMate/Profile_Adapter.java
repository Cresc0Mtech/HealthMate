package com.cresco.HealthMate;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cresco.HealthMate.SugarListAdapter.ViewHolder;

public class Profile_Adapter extends ArrayAdapter<Bundle>  
{

	String TAG = this.getClass().getSimpleName();
    private  ArrayList<Bundle> profile_list = new ArrayList<Bundle>();
    private Activity ctx;
    Typeface typeface;
    MeasurementList_Profile measure;

    New_Grid newGrid;
    float density;

    Profile_Adapter(Activity ctx,ArrayList<Bundle> profile_list, float density)
  {
    	super(ctx, R.layout.measurement_row ,profile_list);
       
        this.profile_list = profile_list;
        this.ctx = ctx;
        this.density = density;
        
        typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
        
        measure = new MeasurementList_Profile();

        newGrid = new New_Grid();


    }

  
    static class ViewHolder
	{
    	ImageView iv_p_image;
		protected CrescoTextView tv_p_name;
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
	        
	        view = inflater.inflate(R.layout.profile_row, null);
	        
	        
	        holder.tv_p_name        =(CrescoTextView)view.findViewById(R.id.tv_p_name);
	        holder.iv_p_image        =(ImageView)view.findViewById(R.id.iv_p_image);

	        view.setTag(holder);
		}
		else
		{
			 view = convertView;

			
			((ViewHolder) view.getTag()).iv_p_image.setTag(position);
			((ViewHolder) view.getTag()).tv_p_name.setTag(position);
		}

		final ViewHolder viewholder = (ViewHolder) view.getTag();

		String fname = profile_list.get(position).getString(Profile_Master.FIRST_NAME);
		String lname = profile_list.get(position).getString(Profile_Master.LAST_NAME);

        int profile_id =  profile_list.get(position).getInt(Profile_Master.PROFILE_ID);
				
		String full_name = fname+" "+lname;
		
		Log.v(TAG,"profile name : "+full_name);
			
		viewholder.tv_p_name.setText(full_name);
			
		viewholder.tv_p_name.setTypeface(typeface);

        //File imgFile = new  File(Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images/Profile"+ls_fname+".jpg");
        //public static String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";

        File imgFile = new  File(Add_Profile.iconsStoragePath+"/Profile"+profile_id+".jpg");
		if(imgFile.exists())
		{
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
		    Bitmap b = getRoundedShape(myBitmap);
		    viewholder.iv_p_image.setImageBitmap(b);
		}
		
    	
    	view.postInvalidate();
		return view;
    }
    
    
    
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) 
	{
		  // TODO Auto-generated method stub
    	int targetWidth = 0;
		int targetHeight = 0;

       // DisplayMetrics dm = new DisplayMetrics();
       // getContext().getWindowManager().getDefaultDisplay().getMetrics(dm);
       // int width=dm.widthPixels;
       // int height=dm.heightPixels;

       // float density =  getResources().getDisplayMetrics().density;

        String screen = newGrid.getScreenResolution(getContext());

        Log.v(TAG, "screen :" +screen);

        if(screen.equals("X-LARGE"))
        {
            targetWidth = (int)(70 * density);
            targetHeight = (int)(70* density);

        }
        else if(screen.equals("LARGE"))
        {
            targetWidth = (int)(90 * density);
            targetHeight = (int)(90* density);
        }
        else if(screen.equals("NORMAL"))
        {
            targetWidth = (int)(50 * density);
            targetHeight = (int)(50* density);
        }
        else if(screen.equals("SMALL"))
        {
            targetWidth = 70;
            targetHeight = 70;
        }
        else
        {
            targetWidth = 70;
            targetHeight = 70;
        }

        Log.v(TAG, "targetWidth : "+ targetWidth+"targetHeight : "+ targetHeight);

		/*	
		 if(height == 2464)
    	{
    		targetWidth = 500;
    		targetHeight = 500;
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

}
