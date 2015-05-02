package com.cresco.HealthMate;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.cresco.HealthMate.R;

import android.os.SystemClock;

public class HealthMate_Landing extends Activity implements OnClickListener

{
	Context context;
	String TAG = this.getClass().getSimpleName();
	
	CrescoTextView tv_healthmate;
	ImageView iv_healthmate;
	Animation fade_in, fade_out;
	
	Profile_Master profile_master;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.healthmate_landing);
		context = this;
		
		
		//tv_healthmate = (CrescoTextView)findViewById(R.id.tv_healthmate);
		iv_healthmate = (ImageView)findViewById(R.id.iv_healthmate);
		
		//inserting default profiles
		profile_master = new Profile_Master(context);
			
		ArrayList<Bundle >profile_list = new ArrayList<Bundle>();
		profile_list = profile_master.getAllProfileList();
			

		//
			
				
		fade_in = AnimationUtils.loadAnimation(context,R.anim.fade_in);
		fade_out = AnimationUtils.loadAnimation(context,R.anim.fade_out);
				
		
		iv_healthmate.startAnimation(fade_in);
		//tv_healthmate.startAnimation(fade_in);
		
		fade_in.setAnimationListener(new AnimationListener() 
		{
			
			@Override
			public void onAnimationStart(Animation animation) 
			{
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) 
			{
				
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) 
			{
				try 
				{
					iv_healthmate.setVisibility(View.VISIBLE);

                    SystemClock.sleep(4000);
					//tv_healthmate.setVisibility(View.VISIBLE);
					iv_healthmate.startAnimation(fade_out);
					//tv_healthmate.startAnimation(fade_out);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					Log.d("abcd", " " + e);
				}
			}
		});
		
		final Intent i = new Intent(this, New_Grid.class);
		
		fade_out.setAnimationListener(new AnimationListener() 
		{
			
			@Override
			public void onAnimationStart(Animation animation) 
			{
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) 
			{
				
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) 
			{
				try 
				{
					
					startActivity(i);
					finish();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					Log.d("abcd", " " + e);
				}
			}
		});
		
		
		
	}
	
	
	
	@Override
	public void onClick(View arg0) 
	{
		
	}
	
	
	public void AddProfiles()
	{
		ContentValues cv;
		
		cv = new ContentValues();
		cv.clear();
		
		cv.put(Profile_Master.FIRST_NAME, "Jigar");
		cv.put(Profile_Master.MIDDLE_NAME, "Mahendra");
		cv.put(Profile_Master.LAST_NAME, "Shah");
		cv.put(Profile_Master.GENDER, "Male");
		cv.put(Profile_Master.RELATION, "Self");
		cv.put(Profile_Master.DOB, "01/01/1985");
		cv.put(Profile_Master.BLOODGROUP, "O+");
		cv.put(Profile_Master.HEIGHT, "5.3");
		cv.put(Profile_Master.MOBILE_NO, "9833256337");
		cv.put(Profile_Master.EMAIL_ID, "jigarshah_5@rediffmail.com");
		cv.put(Profile_Master.CITY, "Thane");
		cv.put(Profile_Master.COUNTRY, "India");
		cv.put(Profile_Master.PINCODE, "4006");
		cv.put(Profile_Master.STATUS, "A");
		cv.put(Profile_Master.SERVER_STATUS, "OK");
		
		profile_master.insert(cv);
		
		
		cv = new ContentValues();
		cv.clear();
		
		cv.put(Profile_Master.FIRST_NAME, "Amit");
		cv.put(Profile_Master.MIDDLE_NAME, "Mahendra");
		cv.put(Profile_Master.LAST_NAME, "Shah");
		cv.put(Profile_Master.GENDER, "Male");
		cv.put(Profile_Master.RELATION, "Brother");
		cv.put(Profile_Master.DOB, "30/06/1978");
		cv.put(Profile_Master.BLOODGROUP, "AB+");
		cv.put(Profile_Master.HEIGHT, "5.2");
		cv.put(Profile_Master.MOBILE_NO, "9833256338");
		cv.put(Profile_Master.EMAIL_ID, "amit_shah@gmail.com");
		cv.put(Profile_Master.CITY, "Thane");
		cv.put(Profile_Master.COUNTRY, "India");
		cv.put(Profile_Master.PINCODE, "4006");
		cv.put(Profile_Master.STATUS, "A");
		cv.put(Profile_Master.SERVER_STATUS, "OK");
		
		profile_master.insert(cv);
		
		
	}
	
	

}
