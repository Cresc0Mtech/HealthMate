package com.cresco.HealthMate;

import java.io.File;
import java.util.ArrayList;

import com.cresco.HealthMate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirstScreen_Activity extends Activity implements OnClickListener
{
	String TAG = this.getClass().getSimpleName();
	Context context;
	LinearLayout ll_main_1to3 , ll_part1_profile1, ll_part2 ,ll_part2_profile1,ll_part2_profile2,
				ll_part3_create,ll_part4 ,ll_part4_profile3,ll_part4_create
				,ll_main_4to5, ll_part5, ll_part5_profile1, ll_part5_profile2
				,ll_part6_profile3, ll_part6_profile4, ll_part7_create, ll_part7_create_more,
				ll_part7_1,ll_part7_2;
	
	//EditText et_pf;
	//Button bt_pf;
	
	TextView tv_part1_profile1,tv_part2_profile1,tv_part2_profile2,tv_part4_profile3,tv_part5_profile1
			 ,tv_part5_profile2,tv_part6_profile3, tv_part6_profile4;
	
	CrescoTextView tv_createNew1, tv_createNew2, tv_createNew3, tv_createNew4;
	ImageView iv_part1_profile1,iv_part2_profile1,iv_part2_profile2,iv_part4_profile3,
			  iv_part5_profile1, iv_part5_profile2, iv_part6_profile3, iv_part6_profile4;
	
	Profile_Master profile_master;
	Parameter_Master param;
	ArrayList<Bundle> profile_list;
	final static int REQ_CODE = 1;
	
	int p_count;
	Animation from_middle, to_middle;
	Typeface typeface;
	
	final static String CREATE_PROFILE = "New Profile";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		context = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_screen);
		
		typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		
		FileUtils.copyDB(context);
		param = new Parameter_Master(context);
		Cursor c1 = param.getAlllist();
		
		if(c1.getCount()>0)
		{
			Log.v(TAG,"Already inserted :"+p_count);
		}
		else
		{
			Log.v(TAG,"Insert parameter :"+p_count);
			insertParameterMaster();
		}
		
		
		profile_master = new Profile_Master(context);
		
		ll_main_1to3 = (LinearLayout) findViewById(R.id.ll_main_1to3); 
		ll_part1_profile1 = (LinearLayout) findViewById(R.id.ll_part1_profile1);
		 
		ll_part2 = (LinearLayout) findViewById(R.id.ll_part2);
		ll_part2_profile1 = (LinearLayout) findViewById(R.id.ll_part2_profile1);
		ll_part2_profile2 = (LinearLayout) findViewById(R.id.ll_part2_profile2);
		ll_part3_create = (LinearLayout) findViewById(R.id.ll_part3_create);
		ll_part4 = (LinearLayout) findViewById(R.id.ll_part4);
		ll_part4_profile3 = (LinearLayout) findViewById(R.id.ll_part4_profile3);
		ll_part4_create = (LinearLayout) findViewById(R.id.ll_part4_create);
		
		ll_main_4to5	= (LinearLayout) findViewById(R.id.ll_main_4to5);
		ll_part5	= (LinearLayout) findViewById(R.id.ll_part5);
		ll_part5_profile1 = (LinearLayout) findViewById(R.id.ll_part5_profile1);
		ll_part5_profile2 = (LinearLayout) findViewById(R.id.ll_part5_profile2);
		ll_part6_profile3 = (LinearLayout) findViewById(R.id.ll_part6_profile3);
		ll_part6_profile4 = (LinearLayout) findViewById(R.id.ll_part6_profile4);
		ll_part7_create = (LinearLayout) findViewById(R.id.ll_part7_create);
		ll_part7_create_more = (LinearLayout) findViewById(R.id.ll_part7_create_more);
		ll_part7_1 = (LinearLayout) findViewById(R.id.ll_part7_1);
		ll_part7_2 = (LinearLayout) findViewById(R.id.ll_part7_2);
		
		tv_part1_profile1 = (TextView) findViewById(R.id.tv_part1_profile1);
		tv_part2_profile1 = (TextView) findViewById(R.id.tv_part2_profile1);
		tv_part2_profile2 = (TextView) findViewById(R.id.tv_part2_profile2);
		tv_part4_profile3 = (TextView) findViewById(R.id.tv_part4_profile3);
		tv_part5_profile1 = (TextView) findViewById(R.id.tv_part5_profile1);
		tv_part5_profile2= (TextView) findViewById(R.id.tv_part5_profile2);
		tv_part6_profile3= (TextView) findViewById(R.id.tv_part6_profile3); 
		tv_part6_profile4= (TextView) findViewById(R.id.tv_part6_profile4);
		
		tv_createNew1= (CrescoTextView) findViewById(R.id.tv_createNew1); 
		tv_createNew2= (CrescoTextView) findViewById(R.id.tv_createNew2); 
		tv_createNew3 = (CrescoTextView) findViewById(R.id.tv_createNew3);
		tv_createNew4 = (CrescoTextView) findViewById(R.id.tv_createNew4);
		
		iv_part1_profile1 = (ImageView) findViewById(R.id.iv_part1_profile1);
		iv_part2_profile1 = (ImageView) findViewById(R.id.iv_part2_profile1);
		iv_part2_profile2= (ImageView) findViewById(R.id.iv_part2_profile2);
		iv_part4_profile3= (ImageView) findViewById(R.id.iv_part4_profile3);
		iv_part5_profile1= (ImageView) findViewById(R.id.iv_part5_profile1);
		iv_part5_profile2= (ImageView) findViewById(R.id.iv_part5_profile2);
		iv_part6_profile3= (ImageView) findViewById(R.id.iv_part6_profile3);
		iv_part6_profile4 = (ImageView) findViewById(R.id.iv_part6_profile4);
		
		
		tv_part1_profile1.setTypeface(typeface);
		tv_part2_profile1.setTypeface(typeface);
		tv_part2_profile2.setTypeface(typeface);
		tv_part4_profile3.setTypeface(typeface);
		tv_part5_profile1.setTypeface(typeface);
		tv_part5_profile2.setTypeface(typeface);
		tv_part6_profile3.setTypeface(typeface);
		tv_part6_profile4.setTypeface(typeface);
		tv_createNew1.setTypeface(typeface);
		tv_createNew2.setTypeface(typeface);
		tv_createNew3.setTypeface(typeface);
		tv_createNew4.setTypeface(typeface);
		
		tv_createNew1.setText(CREATE_PROFILE);
		tv_createNew2.setText(CREATE_PROFILE);
		tv_createNew3.setText(CREATE_PROFILE);
		tv_createNew4.setText(CREATE_PROFILE);

		
		ll_main_1to3.setOnClickListener(this);
		ll_part3_create.setOnClickListener(this);
				
		ll_part4_create.setOnClickListener(this);
	
		ll_part7_create.setOnClickListener(this);
		ll_part7_1.setOnClickListener(this);
		ll_part7_2.setOnClickListener(this);
		
		
		//open 1st profile
		ll_part1_profile1.setOnClickListener(this);
		ll_part2_profile1.setOnClickListener(this);
		ll_part2_profile2.setOnClickListener(this);
		ll_part4_profile3.setOnClickListener(this);
		ll_part5_profile1.setOnClickListener(this);
		ll_part5_profile2.setOnClickListener(this);
		ll_part6_profile3.setOnClickListener(this);
		ll_part6_profile4.setOnClickListener(this);
		
		//iv_part2_profile1.setBackgroundResource(R.drawable.male_profile);
		//Drawable d = new Drawable(context.getResources().getDrawable(R.drawable.male_profile));
		//iv_part2_profile1.setImageDrawable(context.getResources().getDrawable(R.drawable.male_profile));
		//Cursor c = profile_master.getAlllist();
		 profile_list = new ArrayList<Bundle>();
		 profile_list = profile_master.getAllProfileList();
		 
		 p_count = 1;
		 if(profile_list.size()>0)
		 {
			 p_count = profile_list.size();
			 
			 if(p_count > 5)
			 {
				 p_count = 5;
			 }
			 
			 setProfile(p_count);
			 Log.v(TAG,"Profile count :"+p_count);
		 }
		 else
		 {
			 tv_part1_profile1.setText("Profile Name");
			 
			 Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.male_profile);
			 Bitmap b = getRoundedShape(myBitmap, 200, 200);
			 iv_part1_profile1.setImageBitmap(b);
			
		 }
		 
		 Log.v(TAG,"Profile count :"+p_count);
		
	}

	

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		
			case R.id.ll_part3_create:
				
			case R.id.ll_part4_create:
			
			case R.id.ll_part7_create:
				
			case R.id.ll_part7_1 :
				
				createProfile_choice();
				break;
				
			
			case R.id.ll_part1_profile1 : 
				
			case R.id.ll_part2_profile1 : 
				
			case R.id.ll_part2_profile2 :
				
			case R.id.ll_part4_profile3 :
				
			case R.id.ll_part5_profile1 :
				
			case R.id.ll_part5_profile2 :
				
			case R.id.ll_part6_profile3 :
				
			case R.id.ll_part6_profile4 :
				
				OpenProfileMeasures(v);
				
				break;
				
		}
		
	}
	
	public void setProfile(int count)
	{
		String ls_fname,ls_lname;
		Log.v("kkk","count : "+count);
		//if(count.equals("1"))
		if(count == 1)
		{
			ll_main_1to3.setVisibility(View.VISIBLE);
			ll_part1_profile1.setVisibility(View.VISIBLE);
			ll_part2.setVisibility(View.GONE);
			ll_part3_create.setVisibility(View.VISIBLE);
			ll_part4.setVisibility(View.GONE);
			
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part1_profile1.setText(ls_fname+"  \t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 200, 200);
						    iv_part1_profile1.setImageBitmap(b);
						}
						
					}
				}
			 }
			else
			{
				tv_part1_profile1.setText("Demo Profile");
			}
		}
		//else if(count.equals("2"))
		else if(count == 2)
		{
			ll_main_1to3.setVisibility(View.VISIBLE);
			ll_part1_profile1.setVisibility(View.GONE);
			ll_part2.setVisibility(View.VISIBLE);
			ll_main_4to5.setVisibility(View.GONE);
			ll_part3_create.setVisibility(View.VISIBLE);
			ll_part4.setVisibility(View.GONE);
			
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part2_profile1.setText(ls_fname+"  \t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    // 150, 150
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part2_profile1.setImageBitmap(b);
						}
					}
					if(i == 1)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part2_profile2.setText(ls_fname+"  \t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    //150, 150
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part2_profile2.setImageBitmap(b);
						}
					}
				}
			 }
		}
		//else if(count.equals("3"))
		else if(count == 3)
		{
			ll_main_1to3.setVisibility(View.VISIBLE);
			ll_part1_profile1.setVisibility(View.GONE);
			ll_part2.setVisibility(View.VISIBLE);
			ll_part3_create.setVisibility(View.GONE);
			ll_part4.setVisibility(View.VISIBLE);
			ll_main_4to5.setVisibility(View.GONE);
			
			
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part2_profile1.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part2_profile1.setImageBitmap(b);
						}
					}
					if(i == 1)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part2_profile2.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part2_profile2.setImageBitmap(b);
						}
					}
					if(i == 2)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part4_profile3.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part4_profile3.setImageBitmap(b);
						}
					}
				}
			 }
		}
		
//		else if(count.equals("4"))
		else if(count == 4)
		{
			ll_main_4to5.setVisibility(View.VISIBLE);
			ll_main_1to3.setVisibility(View.GONE);
			ll_part7_create.setVisibility(View.VISIBLE);
			ll_part7_create_more.setVisibility(View.GONE);
			
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part5_profile1.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part5_profile1.setImageBitmap(b);
						}
					}
					if(i == 1)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part5_profile2.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part5_profile2.setImageBitmap(b);
						}
					}
					if(i == 2)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part6_profile3.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part6_profile3.setImageBitmap(b);
						}
					}
					if(i == 3)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part6_profile4.setText(ls_fname+"\t"+ls_lname);
						
						File imgFile = new  File(Environment.getExternalStorageDirectory() + "/Graph/Profile_images/Profile"+ls_fname+".jpg");
						if(imgFile.exists())
						{
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
						    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
						    Bitmap b = getRoundedShape(myBitmap, 150, 150);
						    iv_part6_profile4.setImageBitmap(b);
						}
					}
				}
			 }
		}
//		else if(count.equals("5"))
		else if(count == 5)
		{
			ll_main_4to5.setVisibility(View.VISIBLE);
			ll_main_1to3.setVisibility(View.GONE);
			ll_part7_create.setVisibility(View.GONE);
			ll_part7_create_more.setVisibility(View.VISIBLE);
			
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part5_profile1.setText(ls_fname+"\t"+ls_lname);
					}
					if(i == 1)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part5_profile2.setText(ls_fname+"\t"+ls_lname);
					}
					if(i == 2)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part6_profile3.setText(ls_fname+"\t"+ls_lname);
					}
					if(i == 3)
					{
						ls_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
						ls_lname = profile_list.get(i).getString(Profile_Master.LAST_NAME);
						
						tv_part6_profile4.setText(ls_fname+"\t"+ls_lname);
					}
				}
			 }
		}
			
	}
	
	public void createProfile_choice()
	{
		Intent i = new Intent(this, Create_profile_choice.class);
		startActivity(i);
		
		startActivityForResult(i, REQ_CODE);
		
	}
	
	public void createprofile()
	{
		//Intent i = new Intent(this, Add_Profile.class);
		//startActivity(i);
		
		//startActivityForResult(i, REQ_CODE);
		
	}
	
	public void OpenProfileMeasures(View v)
	{
		int li_profileId = 0;
		if(v.getId() == R.id.ll_part1_profile1)
		{
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						li_profileId = profile_list.get(i).getInt(Profile_Master.PROFILE_ID);
					}
				}
			 }
		}
		
		if(v.getId() == R.id.ll_part2_profile1 || v.getId() == R.id.ll_part5_profile1  )
		{
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 0)
					{
						li_profileId = profile_list.get(i).getInt(Profile_Master.PROFILE_ID);
					}
				}
			 }
		 }
		
		if(v.getId() == R.id.ll_part2_profile2 || v.getId() == R.id.ll_part5_profile2)
		{
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 1)
					{
						li_profileId = profile_list.get(i).getInt(Profile_Master.PROFILE_ID);
					}
				}
			 }
		 }
		
		
		if(v.getId() == R.id.ll_part4_profile3 || v.getId() == R.id.ll_part6_profile3)
		{
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 2)
					{
						li_profileId = profile_list.get(i).getInt(Profile_Master.PROFILE_ID);
					}
				}
			 }
		 }
		
		if(v.getId() == R.id.ll_part6_profile4)
		{
			
			if(profile_list.size()>0)
			 {
				for(int i = 0; i < profile_list.size(); i++)
				{
					if(i == 3)
					{
						li_profileId = profile_list.get(i).getInt(Profile_Master.PROFILE_ID);
					}
				}
			 }
		 }
		
		Log.v(TAG,"li_profileId : "+li_profileId);
		
		
		Intent i = new Intent(this, MeasurementList_Profile.class);
		i.putExtra("ProfileId", li_profileId);
		
		startActivity(i);
		//animatedStartActivity();
		
		//overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
		
		/*from_middle = AnimationUtils.loadAnimation(context,R.anim.from_middle);
		ll_add_pressure.startAnimation(from_middle);
		
		from_middle.setAnimationListener(new AnimationListener() 
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
					//SystemClock.sleep(1500);
					//ll_add_pressure.setVisibility(View.GONE);
				
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					Log.d("abcd", " " + e);
					
					
				}
				
			}
		});*/
	}
	
//	private void animatedStartActivity() 
//	{
//		// we only animateOut this activity here.
//		// The new activity will animateIn from its onResume() - be sure to implement it.
//		final Intent intent = new Intent(getApplicationContext(), MeasurementList_Profile.class);
//		// disable default animation for new intent
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		ActivitySwitcher.animationOut(findViewById(R.id.container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//			@Override
//			public void onAnimationFinished() 
//			{
//				startActivity(intent);
//			}
//		});
//	}
	
	public void insertParameterMaster()
	{
		ContentValues cv;
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "BP - Systolic");
		cv.put(Parameter_Master.PARAMETER_UNIT, "mm Hg");
		cv.put(Parameter_Master.MIN_VALUE, "120");
		cv.put(Parameter_Master.MAX_VALUE, "");
		param.insert(cv);
		
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "BP - Diastolic");
		cv.put(Parameter_Master.PARAMETER_UNIT, "mm Hg");
		cv.put(Parameter_Master.MIN_VALUE, "80");
		cv.put(Parameter_Master.MAX_VALUE, "");
		param.insert(cv);
		
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "Pulse");
		cv.put(Parameter_Master.PARAMETER_UNIT, "");
		cv.put(Parameter_Master.MIN_VALUE, "72");
		cv.put(Parameter_Master.MAX_VALUE, "");
		param.insert(cv);
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "Sugar - Fasting");
		cv.put(Parameter_Master.PARAMETER_UNIT, "mg/dL");
		cv.put(Parameter_Master.MIN_VALUE, "80");
		cv.put(Parameter_Master.MAX_VALUE, "110");
		param.insert(cv);
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "Sugar - PP");
		cv.put(Parameter_Master.PARAMETER_UNIT, "mg/dL");
		cv.put(Parameter_Master.MIN_VALUE, "120");
		cv.put(Parameter_Master.MAX_VALUE, "140");
		param.insert(cv);
		
		cv = new ContentValues();
		cv.put(Parameter_Master.PARAMETER_NAME, "Weight");
		cv.put(Parameter_Master.PARAMETER_UNIT, "kg");
		cv.put(Parameter_Master.MIN_VALUE, "");
		cv.put(Parameter_Master.MAX_VALUE, "");
		param.insert(cv);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		// TODO Auto-generated method stub

		if(requestCode == REQ_CODE)
		{

			if (resultCode == RESULT_OK)
			{

				//tvResultCode.setText("RESULT_OK");
				profile_list = profile_master.getAllProfileList();
				 
				 p_count = 1;
				 if(profile_list.size()>0)
				 {
					 p_count = profile_list.size();
					 
					 if(p_count > 5)
					 {
						 p_count = 5;
					 }
					 
					 setProfile(p_count);
					 Log.v(TAG,"Profile count :"+p_count);
				 }
				 
				 Log.v(TAG,"Profile count on back pressed :"+p_count);

			}
			else if(resultCode == RESULT_CANCELED)
			{

				//tvResultCode.setText("RESULT_CANCELED");

			}

		}

	}
	
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width, int height) 
	{
		  
		  //int targetWidth = 200;
		  //int targetHeight = 200;
		
		  int targetWidth = width;
		  int targetHeight = height;
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
