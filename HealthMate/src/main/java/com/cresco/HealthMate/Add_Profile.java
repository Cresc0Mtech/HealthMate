package com.cresco.HealthMate;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.GraphUser;
import com.facebook.Session;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class Add_Profile extends TitleBar_Activity implements OnClickListener , ConfirmDialog.NoticeDialogListener

{
	Context context;
	String TAG = this.getClass().getSimpleName();
	
	
	EditText et_fname,et_mname,et_lname,et_mob,et_dob,et_height,et_email,et_relation, et_city,
			 et_country, et_pin;
    CrescoTextView tv_hint;
	Spinner sp_blood,sp_gender, sp_relation;
	Button bt_save, bt_can;
	
	String ls_fname,ls_mname,ls_lname,ls_mob,ls_dob,ls_height,ls_email,ls_relation, ls_gender, ls_blood, ls_city, ls_country, ls_pin;
	
	private int year, month, day;
	ArrayAdapter<CharSequence> adapter;
	ContentValues cv;
	
	Profile_Master profile_master;
	ArrayList<String> sp_list1, sp_list2, sp_list3;
	GenderAdapter sAdapter;
	
	ImageView iv_profile;

	private final int SELECT_PHOTO = 2;
	final int CROP_FROM_CAMERA = 3;
	private Uri mImageCaptureUri;
	
	int profile_id;
	String profile_op;
	
	ArrayList<Bundle> profile_list;
	
	FaceBookDetails fbdetails;
	boolean loginViaFB= false;
	
	public final static String URL_INSERT_DEVICE_PROFILE = New_Grid.URL_FOLDER_BASE + "insert_device_profile.php";
	
	public final static String URL_UPDATE_DEVICE_PROFILE = New_Grid.URL_FOLDER_BASE + "edit_device_profile.php";
	public final static String URL_DELETE_DEVICE_PROFILE = New_Grid.URL_FOLDER_BASE + "delete_device_profile.php";
	
	String date_of_birth;
	
	private Facebook facebook;
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;

    private static String APP_ID = "980146335343232";
    private SharedPreferences mPrefs;
    
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;
    private UiLifecycleHelper uiHelper;
	
    New_Grid new_grid;
    ConfirmDialog confirmDialog;

    Bitmap photo = null;
    MeasurementList_Profile  measurement;

    public static String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
 
    
    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_profile);
		context = this;


        measurement = new MeasurementList_Profile();


        //Admob integration
        String dev_id = DeviceUtils.getImei(context);
        if(DbHelper.USE_ADD)
        {
            adView = (AdView) this.findViewById(R.id.adMob);
            //request TEST ads to avoid being disabled for clicking your own ads
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                    //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
                    .addTestDevice(dev_id) // Nexus 5
                    .build();
            //"356262052582091"
            adView.loadAd(adRequest);
        }
        //
		
		
		////facebook wall post
		facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        new_grid = new New_Grid();
        
        //uiHelper = new UiLifecycleHelper(this, statusCallback);
	    //uiHelper.onCreate(savedInstanceState);
        //if (savedInstanceState != null) 
        //{
           // pendingPublishReauthorization = 
           // savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
        //}
        //////
        

		super.ll_profileheader.setBackgroundColor(getResources().getColor(R.color.healthmate_green));
		super.iv_appicon.setBackgroundResource(R.drawable.hm_logo_white);
		super.iv_profileimage.setVisibility(View.VISIBLE);
		super.tv_profilename.setText("New Profile");
		
		final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
        profile_master = new Profile_Master(context);
        fbdetails = new FaceBookDetails(context);
		
		et_fname = (EditText)findViewById(R.id.et_fname);
		et_mname = (EditText)findViewById(R.id.et_mname);
		et_lname = (EditText)findViewById(R.id.et_lname);
		et_mob = (EditText)findViewById(R.id.et_mob);
		et_dob = (EditText)findViewById(R.id.et_dob);
		//et_height = (EditText)findViewById(R.id.et_height);
		et_email = (EditText)findViewById(R.id.et_email);
		//et_relation = (EditText)findViewById(R.id.et_relation);
		sp_relation = (Spinner)findViewById(R.id.sp_relation);
		sp_blood = (Spinner)findViewById(R.id.sp_blood);
		sp_gender = (Spinner)findViewById(R.id.sp_gender);
		bt_save = (Button)findViewById(R.id.bt_save);
		bt_can = (Button)findViewById(R.id.bt_can);
		
		et_city = (EditText)findViewById(R.id.et_city);
		et_country = (EditText)findViewById(R.id.et_country);
		et_pin = (EditText)findViewById(R.id.et_pin);
		
		iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_hint = (CrescoTextView) findViewById(R.id.tv_hint);
		
		
		et_fname.setFocusable(true);
		et_fname.setFocusableInTouchMode(true);
	    
	    sp_list1 = new ArrayList<String>();
		sp_list1.add("Gender");	
		sp_list1.add("Male");
		sp_list1.add("Female");

		sAdapter = new GenderAdapter(this, R.layout.spinner_row, sp_list1);
		sAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
		sp_gender.setAdapter(sAdapter);
		
		sp_list2 = new ArrayList<String>();
		sp_list2.add("Blood Group");
		sp_list2.add("A+");	
		sp_list2.add("B+");
		sp_list2.add("AB+");
		sp_list2.add("O+");	
		sp_list2.add("A-");
		sp_list2.add("B-");
		sp_list2.add("AB-");	
		sp_list2.add("O-");

		sAdapter = new GenderAdapter(this, R.layout.spinner_row, sp_list2);
		sAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
		sp_blood.setAdapter(sAdapter);
		
		
		sp_list3 = new ArrayList<String>();
		sp_list3.add("Relation");	
		sp_list3.add("Self");
        sp_list3.add("Husband");
        sp_list3.add("Wife");
		sp_list3.add("Father");
        sp_list3.add("Mother");
        sp_list3.add("Grandfather");
        sp_list3.add("Grandmother");
        sp_list3.add("Uncle");
        sp_list3.add("Aunt");
		sp_list3.add("Brother");
		sp_list3.add("Sister");
        sp_list3.add("Son");
        sp_list3.add("Daughter");
		sp_list3.add("Cousin");
		sp_list3.add("Nephew");
		sp_list3.add("Niece");
		sp_list3.add("Friend");
		

		sAdapter = new GenderAdapter(this, R.layout.spinner_row, sp_list3);
		sAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
		sp_relation.setAdapter(sAdapter);
		
		et_dob.setOnClickListener(this);
		
		////////////////////////
		et_fname.setText("");
		et_mname.setText("");
		et_lname.setText("");
		et_dob.setText("");
		//et_height.setText("");
		et_mob.setText("");
		et_email.setText("");
		sp_blood.setSelection(0);
		sp_gender.setSelection(0);
		sp_relation.setSelection(0);
		//et_relation.setText("");
		
		
		/////////////////////////
		profile_op = getIntent().getStringExtra(Grid_Adapter.PROFILE_OPERATION);
		profile_id = getIntent().getIntExtra(Grid_Adapter.PROFILE_ID, 0);

		
		if(profile_op.equals("Edit"))
		{
			
			
			super.tv_delete.setVisibility(View.VISIBLE);
			super.iv_profileimage.setBackgroundResource(R.drawable.delete);//(context.getResources().getDrawable(R.drawable.delete));
		
			super.tv_delete.setText("Delete Profile");
			profile_list = new ArrayList<Bundle>();
			profile_list = profile_master.getProfileByID(profile_id);
			
			//Log.v(TAG,"profile list :"+profile_list.toString());
			
			ls_fname = profile_list.get(0).getString(Profile_Master.FIRST_NAME);
			ls_mname= profile_list.get(0).getString(Profile_Master.MIDDLE_NAME);
			ls_lname= profile_list.get(0).getString(Profile_Master.LAST_NAME);
			ls_mob= profile_list.get(0).getString(Profile_Master.MOBILE_NO);
			ls_dob= profile_list.get(0).getString(Profile_Master.DOB);
			//ls_height= profile_list.get(0).getString(Profile_Master.HEIGHT);
			ls_email= profile_list.get(0).getString(Profile_Master.EMAIL_ID);
			ls_relation= profile_list.get(0).getString(Profile_Master.RELATION);
			ls_gender= profile_list.get(0).getString(Profile_Master.GENDER);
			ls_blood= profile_list.get(0).getString(Profile_Master.BLOODGROUP);
			
			ls_city= profile_list.get(0).getString(Profile_Master.CITY);
			ls_country= profile_list.get(0).getString(Profile_Master.COUNTRY);
			ls_pin= profile_list.get(0).getString(Profile_Master.PINCODE);
			
			super.tv_profilename.setText(ls_fname+" "+ls_lname);
			
			if(!(ls_fname == null))
			{
				et_fname.setText(ls_fname);
			}
			else
			{
				et_fname.setText("");
			}
		
			if(!(ls_mname == null))
			{
				et_mname.setText(ls_mname);
			}
			else
			{
				et_mname.setText("");
			}
			
			if(!(ls_lname == null))
			{
				et_lname.setText(ls_lname);
			}
			else
			{
				et_lname.setText("");
			}
			
			if(!(ls_mob == null))
			{
				et_mob.setText(ls_mob);
			}
			else
			{
				et_mob.setText("");
			}
			
			if(!(ls_dob == null))
			{
				et_dob.setText(ls_dob);
			}
			else
			{
				et_dob.setText("");
			}
			
			if(!(ls_email == null))
			{
				et_email.setText(ls_email);
			}
			else
			{
				et_email.setText("");
			}
			
			if(!(ls_city == null))
			{
				et_city.setText(ls_city);
			}
			else
			{
				et_city.setText("");
			}
			if(!(ls_country == null))
			{
				et_country.setText(ls_country);
			}
			else
			{
				et_country.setText("");
			}
			if(!(ls_pin == null))
			{
				et_pin.setText(ls_pin);
			}
			else
			{
				et_pin.setText("");
			}
			/*
			if(!(ls_relation == null))
			{
				et_relation.setText(ls_relation);
			}
			else
			{
				et_relation.setText("");
			}
			*/
			
			
			for (int i=0;i<sp_list1.size();i++)
			{
				   if (sp_gender.getItemAtPosition(i).toString().equalsIgnoreCase(ls_gender))
				   { 
					   sp_gender.setSelection(i);
				   }
			}
			
			for (int i=0;i<sp_list2.size();i++)
			{
				   if (sp_blood.getItemAtPosition(i).toString().equalsIgnoreCase(ls_blood))
				   { 
					   sp_blood.setSelection(i);
				   }
			}
			
			for (int i=0;i<sp_list3.size();i++)
			{
				   if (sp_relation.getItemAtPosition(i).toString().equalsIgnoreCase(ls_relation))
				   { 
					   sp_relation.setSelection(i);
				   }
			}
			
			//File imgFile = new  File(Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images/Profile"+ls_fname+".jpg");
            // public static String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
            File imgFile = new  File(iconsStoragePath+"/Profile"+profile_id+".jpg");
			if(imgFile.exists())
			{


                int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                {
                    iv_profile.setBackgroundDrawable(null);
                }
                else
                {
                    iv_profile.setBackground(null);
                }
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
			    Bitmap b = getRoundedShape(myBitmap);
                tv_hint.setVisibility(View.GONE);
			    iv_profile.setImageBitmap(b);
			}
			
		}
		else if(profile_op.equals("FaceBook_New"))
		{
			//buildUserInfoDisplay(user) ;
			
			loginViaFB = true;
			
			String facebook_id = getIntent().getStringExtra("facebook_id");
			String name = getIntent().getStringExtra("name");
			//String facebook_id = getIntent().getStringExtra("");
			String gender = getIntent().getStringExtra("gender");
			String dob = getIntent().getStringExtra("dob");
			
			//String city = getIntent().getStringExtra("city");
			//String country = getIntent().getStringExtra("country");
			
			String[] full_name = name.split(":");
			
			String[] fullname = full_name[1].split(" ");
			String fname = fullname[1].trim();
			String lname = fullname[2].trim();
			
			String[] gender1 = gender.split(":");
			String g = gender1[1];
			
			String[] dob1 = dob.split(":");
			String dob2 = dob1[1].trim();

			if(dob2.equals("") || dob2 == null || dob2.equals("null"))
			{
				dob2 = "";
			}

			
			//Log.v(TAG,"name :"+name + "..gender :"+gender + "..dob :"+dob+"..fname :"+fname+"..lname :"+lname +"..g : "+g );
			
			et_fname.setText(fname);
			et_lname.setText(lname);
			et_dob.setText(dob2);
			
			//et_city.setText(city);
			//et_country.setText(country);
			
			for (int i=0;i<sp_list1.size();i++)
			{
				//Log.v(TAG, "sp_list1 : " +sp_list1.get(i));
				
				   if (sp_gender.getItemAtPosition(i).toString().equalsIgnoreCase(g.trim()))
				   { 
					   sp_gender.setSelection(i);
					   
					   //Log.v(TAG, "sp_gender setSelection(i)  " );
				   }
			}
			
			Task_Register task = new Task_Register(facebook_id);
		    Bitmap b = null;
			try 
			{
				b = task.execute().get();
				
				//Log.v(TAG,"in get profile :"+b);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 
			catch (ExecutionException e) 
			{
				e.printStackTrace();
			}
			//Drawable d = new BitmapDrawable(getResources(), b);
		    
		    //iv_profile.setImageDrawable(d);
            tv_hint.setVisibility(View.GONE);
            ArrayList<Bundle> list = profile_master.getAllProfileList();

            int size = list.size();
            profile_id = size+1;

			saveBitmapImage(b);
			
			Bitmap bitmap = getRoundedShape(b);
			iv_profile.setImageBitmap(bitmap);


		}
        else
        {
            ArrayList<Bundle> list = profile_master.getAllProfileList();

            int size = list.size();
            profile_id = size+1;
        }
			


		Cresco_NF.hideKB(this);
		
		iv_profile.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{  
				////if(!(et_fname.getText().toString().equals("")))
				//{
					//GOOGLE ANALYTICS Event Tracking  - profile edit image
                    if(DbHelper.USE_ANALYTICS)
                    {
                        call_GoogleAnalytics_events("Profile", "Edit",
                                "Profile_Image");
                    }
											
					//GOOGLE ANALYTICS Event Tracking  - profile edit image
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				//}
				//else
				//{
					////Toast.makeText(context, "First fill the all profile details thgen you can set your profile picture.", Toast.LENGTH_SHORT).show();
				//}
				
				return;
			}
		});
		


        //GOOGLE ANALYTICS
        if(DbHelper.USE_ANALYTICS) {
            call_GoogleAnalytics_screens();

            call_GoogleAnalytics_sessions();
        }
        //GOOGLE ANALYTICS

        show_toast();
	}

    public void show_toast()
    {
        SuperToast superToast = new SuperToast(this);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText("Once you are done, press back to save Profile");
        superToast.setTextColor(context.getResources().getColor(R.color.white));
        superToast.setBackground(R.color.healthmate_green);
        //superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        superToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        superToast.show();
    }
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			
			 case R.id.et_dob:
			
				setDateOfBirth(v);
				break;
			
			 case R.id.ll_delete:
				
				 if(profile_op.equals("Edit"))
				 {
					//GOOGLE ANALYTICS Event Tracking - profile delete
                     if(DbHelper.USE_ANALYTICS)
                     {
                         call_GoogleAnalytics_events("Profile", "Delete",
                                 null);
                     }
					//GOOGLE ANALYTICS Event Tracking - profile delete

                     confirmDialog = new ConfirmDialog(context, "PROFILE", profile_id, "delete", "tabClicked");
                     confirmDialog.show(getFragmentManager(), TAG);

				 }
					
				 break;

            case R.id.cb_yes:

                confirmDialog.dismiss();

                delete_Profile();

                break;

            case R.id.cb_no:

                confirmDialog.dismiss();

                break;
			
		/*case R.id.bt_save:
				
				saveProfile_details();
				///
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				finish();
				///
				break;
				
			case R.id.bt_can:
				
				finish();
				break;*/
		}
	}

    public void delete_Profile()
    {

        int server_id = profile_master.get_server_id(profile_id);
		profile_master.delete_profile(profile_id);

		//////
		delete_fromServer(server_id, profile_id);
		/////

        /////delete profile pic from sd card
        File sdIconStorageDir = new File(iconsStoragePath);

        if(!sdIconStorageDir.exists())
        {
            sdIconStorageDir.mkdirs();
        }

        try {

            File file = new File(sdIconStorageDir, "Profile" + profile_id + ".jpg");

            if (file.exists())
                file.delete();
        }
        catch(Exception e)
        {
            Log.w("TAG", "Error saving image file: " + e.getMessage());

        }
        ////
        Toast.makeText(context, "Profile deleted successfully", Toast.LENGTH_SHORT).show();

		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
    }
	
	
	public void setDateOfBirth(View view) 
	 {
		 DatePickerDialog dpd = new DatePickerDialog(this,
			        new DatePickerDialog.OnDateSetListener() 
		 {
			 
			         @Override
			         public void onDateSet(DatePicker view, int year,
			                    int monthOfYear, int dayOfMonth) 
			         {
			        	  
			        	 
			             
			        	 et_dob.setText(new StringBuilder().append(dayOfMonth).append("/")
			              	      .append(monthOfYear+1).append("/").append(year));
			        	 
			        	 int m = monthOfYear+1;
			        	 date_of_birth = year+"-"+m+"-"+dayOfMonth;
			             
			 
			         }
		 }, year, month, day);
			
		 dpd.show();
	 }
	
	
	public void saveProfile_details()
	{
		String fname,mname,lname,gender,dob,mob,height,blood,email,relation, city, country, pin;
		
		fname 	= et_fname.getText().toString();
		mname 	= et_mname.getText().toString();
		lname 	= et_lname.getText().toString();
		dob   	= et_dob.getText().toString();
		//height   = et_height.getText().toString();
		mob   	= et_mob.getText().toString();
		email 	 = et_email.getText().toString();
		
		gender = sp_gender.getItemAtPosition(sp_gender.getSelectedItemPosition()).toString();
		blood = sp_blood.getItemAtPosition(sp_blood.getSelectedItemPosition()).toString();
		relation = sp_relation.getItemAtPosition(sp_relation.getSelectedItemPosition()).toString();
		
		city = et_city.getText().toString();
		country = et_country.getText().toString();
		pin = et_pin.getText().toString();
		
		if(!(fname.equals("")) && !(lname.equals("")))
		{
			cv = new ContentValues();
			cv.clear();
			
			if(mname.equals("") || mname == null)
			{
				mname = "";
			}
			if(dob.equals("") || dob == null)
			{
				dob = "";
			}
			
			/*if(height.equals("") || height == null)
			{
				height = "";
			}*/
			if(mob.equals("") || mob == null)
			{
				mob = "";
			}
			if(email.equals("") || email == null)
			{
				email = "";
			}
			/*if(relation.equals("") || relation == null)
			{
				relation = "";
			}*/
			
			if(city.equals("") || city == null)
			{
				city = "";
			}
			if(country.equals("") || country == null)
			{
				country = "";
			}
			if(pin.equals("") || pin == null)
			{
				pin = "";
			}
			
			if(gender.equals(sp_gender.getItemAtPosition(0)))
			{
				gender = "";
			}
			if(blood.equals(sp_blood.getItemAtPosition(0)))
			{
				blood = "";
			}
			
			if(relation.equals(sp_relation.getItemAtPosition(0)))
			{
				relation = "";
			}
			
			
			//Log.v(TAG,"fname : "+fname +",mname : "+mname +",lname : "+lname +",dob : "+dob +",mob : "+mob +",email : "+email +
			//		",gender : "+gender +",relation : "+relation +",blood : "+blood );
			

			cv.put(Profile_Master.FIRST_NAME, fname);
			cv.put(Profile_Master.MIDDLE_NAME, mname);
			cv.put(Profile_Master.LAST_NAME, lname);
			cv.put(Profile_Master.GENDER, gender);
			cv.put(Profile_Master.RELATION, relation);
			cv.put(Profile_Master.DOB, dob);
			cv.put(Profile_Master.BLOODGROUP, blood);
			cv.put(Profile_Master.HEIGHT, "");
			cv.put(Profile_Master.MOBILE_NO, mob);
			cv.put(Profile_Master.EMAIL_ID, email);
			
			cv.put(Profile_Master.CITY, city);
			cv.put(Profile_Master.COUNTRY, country);
			cv.put(Profile_Master.PINCODE, pin);
			
			//by default add status
			cv.put(Profile_Master.STATUS, "A");
			cv.put(Profile_Master.SERVER_STATUS, "PENDING");
			
			profile_master.insert(cv);

            Toast.makeText(context,"Profile saved",Toast.LENGTH_SHORT).show();
			
			//**calling json for sending profile details over server**//
			
			int prof_id = profile_master.get_profileId();
			
			//Log.v(TAG,"prof_id : "+prof_id);
			
			if(InternetUtils.isConnected(context))
			{
			
				Task_insert_device_profile task = new Task_insert_device_profile(fname, mname, lname, gender, 
													relation, date_of_birth, blood, mob, email,
													city, country, pin, prof_id);
				task.execute();
			}
			//else
			//{
				
			//}
			//
			
			/////insert facebook details
			
			//Log.v(TAG,"loginViaFB :"+loginViaFB);
			if(loginViaFB == true)
			{
				cv = new ContentValues();
				cv.clear();
				
				cv.put(FaceBookDetails.FIRST_NAME, fname);
				cv.put(FaceBookDetails.MIDDLE_NAME, mname);
				cv.put(FaceBookDetails.LAST_NAME, lname);
				cv.put(FaceBookDetails.GENDER, gender);
				cv.put(FaceBookDetails.RELATION, relation);
				cv.put(FaceBookDetails.DOB, dob);
				cv.put(FaceBookDetails.BLOODGROUP, blood);
				cv.put(FaceBookDetails.HEIGHT, "");
				cv.put(FaceBookDetails.MOBILE_NO, mob);
				cv.put(FaceBookDetails.EMAIL_ID, email);
				
				cv.put(FaceBookDetails.CITY, city);
				cv.put(FaceBookDetails.COUNTRY, country);
				cv.put(FaceBookDetails.PINCODE, pin);
				
				fbdetails.insert(cv);
			}
			else
			{
				//publishStory();
				
				///loginToFacebook();
				//postToWall();

			}
			

			
			//Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT)
		    //  .show();
			
			//*****facebook wall post 
			//New_Grid new_grid = new New_Grid();
			//postOnWall("Test Message");
			//new_grid.publishStory();
			//postToWall();
			//publishStory();
			//loginToFacebook();

            //saving profile image
            if(photo != null) {
                saveBitmapImage(photo);
            }
			
			Intent i = new Intent();
			setResult(RESULT_OK, i);
			finish();
		}
		else
		{
			if(fname.equals(""))
			{
				Toast.makeText(context, "Enter First Name.", Toast.LENGTH_SHORT)
			      .show();
			}
			else if(lname.equals(""))
			{
				Toast.makeText(context, "Enter Last Name.", Toast.LENGTH_SHORT)
			      .show();
			}
		}
	}
	
	
	public void editProfile_details()
	{
		String fname,mname,lname,gender,dob,mob,height,blood,email,relation, city, country, pin;
		
		fname 	= et_fname.getText().toString();
		mname 	= et_mname.getText().toString();
		lname 	= et_lname.getText().toString();
		dob   	= et_dob.getText().toString();
		//height   = et_height.getText().toString();
		mob   	= et_mob.getText().toString();
		email 	 = et_email.getText().toString();

		
		relation = sp_relation.getItemAtPosition(sp_relation.getSelectedItemPosition()).toString();
		gender = sp_gender.getItemAtPosition(sp_gender.getSelectedItemPosition()).toString();
		blood = sp_blood.getItemAtPosition(sp_blood.getSelectedItemPosition()).toString();
		
		city = et_city.getText().toString();
		country = et_country.getText().toString();
		pin = et_pin.getText().toString();
		
		if(!(fname.equals("")) && !(lname.equals("")))
		{
			cv = new ContentValues();
			cv.clear();
			
			if(mname.equals("") || mname == null)
			{
				mname = "";
			}
			if(dob.equals("") || dob == null || dob.equals("null"))
			{
				dob = "";
				date_of_birth = "";
			}
			else
			{
				String dob1[] = dob.split("/");
				date_of_birth = dob1[2]+"-"+dob1[1]+"-"+dob1[0];
				//date_of_birth = dob;
				
			}
            Debugger.debug(TAG," date_of_birth : "+date_of_birth);
			
			/*if(height.equals("") || height == null)
			{
				height = "";
			}*/
			if(mob.equals("") || mob == null)
			{
				mob = "";
			}
			if(email.equals("") || email == null)
			{
				email = "";
			}
			if(city.equals("") || city == null)
			{
				city = "";
			}
			if(country.equals("") || country == null)
			{
				country = "";
			}
			if(pin.equals("") || pin == null)
			{
				pin = "";
			}
			
			if(gender.equals(sp_gender.getItemAtPosition(0)))
			{
				gender = "";
			}
			if(blood.equals(sp_blood.getItemAtPosition(0)))
			{
				blood = "";
			}
			
			if(relation.equals(sp_relation.getItemAtPosition(0)))
			{
				relation = "";
			}


            Debugger.debug(TAG,"fname : "+fname +",mname : "+mname +",lname : "+lname +",dob : "+dob +",mob : "+mob +",email : "+email +
					",gender : "+gender +",relation : "+relation +",blood : "+blood );
			
			
			cv.put(Profile_Master.FIRST_NAME, fname);
			cv.put(Profile_Master.MIDDLE_NAME, mname);
			cv.put(Profile_Master.LAST_NAME, lname);
			cv.put(Profile_Master.GENDER, gender);
			cv.put(Profile_Master.RELATION, relation);
			cv.put(Profile_Master.DOB, dob);
			cv.put(Profile_Master.BLOODGROUP, blood);
			cv.put(Profile_Master.HEIGHT, "");
			cv.put(Profile_Master.MOBILE_NO, mob);
			cv.put(Profile_Master.EMAIL_ID, email);
			cv.put(Profile_Master.CITY, city);
			cv.put(Profile_Master.COUNTRY, country);
			cv.put(Profile_Master.PINCODE, pin);
			
			//by default add status
			cv.put(Profile_Master.STATUS, "A");
			cv.put(Profile_Master.SERVER_STATUS, "PENDING");
			
			profile_master.edit_profileDetails(cv, profile_id );
			
			//get server profile id
			int server_id = profile_master.get_server_id(profile_id);
			
			//**calling json for sending profile details over server**//
			
			if(InternetUtils.isConnected(context))
			{
			
				Task_edit_device_profile task = new Task_edit_device_profile( fname, mname, lname, gender, 
													relation, date_of_birth, blood, mob, email,
													city, country, pin, server_id, profile_id);
				task.execute();
			}

            if(photo != null) {
                saveBitmapImage(photo);
            }
	
			
			Intent i = new Intent();
			setResult(RESULT_OK, i);
			finish();
		}
		else
		{
			if(fname.equals(""))
			{
				Toast.makeText(context, "Enter First Name.", Toast.LENGTH_SHORT)
			      .show();
			}
			else if(lname.equals(""))
			{
				Toast.makeText(context, "Enter Last Name.", Toast.LENGTH_SHORT)
			      .show();
			}
		}
	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
	    //uiHelper.onActivityResult(requestCode, resultCode, data);

		if(requestCode == SELECT_PHOTO)
		{
			if(resultCode == RESULT_OK)
			{
				try 
				{
					//final Uri imageUri = data.getData();
					mImageCaptureUri = data.getData();
					//final InputStream imageStream = getContentResolver().openInputStream(imageUri);
					//final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
					//iv_profile.setImageBitmap(selectedImage);
					//performCrop();
					doCrop();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
	
	        }
		}
		
		if(requestCode == CROP_FROM_CAMERA)
		{
			if(resultCode == RESULT_OK)
			{
				try 
				{
//					final Uri imageUri = data.getData();
//					final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//					final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//					iv_profile.setImageBitmap(selectedImage);
					
					Bundle extras = data.getExtras();
					
					if (extras != null)
					{
						//Bitmap photo = extras.getParcelable("data");
                         photo = extras.getParcelable("data");
						//Drawable d = new BitmapDrawable(getResources(),photo);
						//iv_profile.setImageBitmap(photo);
						//iv_profile.setImageDrawable(d);
						
						Bitmap b = getRoundedShape(photo);
                        tv_hint.setVisibility(View.GONE);
						iv_profile.setImageBitmap(b);
						//Drawable d = new BitmapDrawable(getResources(),b);
						//iv_profile.setImageDrawable(d);
						
						//saveBitmapImage(photo);
					}

					File f = new File(mImageCaptureUri.getPath());
					
					if (f.exists())
						f.delete();

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}

	}
	

	private void doCrop() 
	{
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		
		if (size == 0) 
		{

			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} 
		else 
		{
			
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			//intent.putExtra("circleCrop", new String(""));
			//startActivityForResult(intent, CROP_FROM_CAMERA);
			

			if (size == 1) 
			{
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));
				//i.putExtra("circleCrop", new String(""));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} 
			else 
			{
				
				for (ResolveInfo res : list) 
				{
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() 
				{
							public void onClick(DialogInterface dialog, int item) 
							{
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() 
				{
					@Override
					public void onCancel(DialogInterface dialog) 
					{

						if (mImageCaptureUri != null) 
						{
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}


	public class CropOptionAdapter extends ArrayAdapter<CropOption> 
	{
		private ArrayList<CropOption> mOptions;
		private LayoutInflater mInflater;

		public CropOptionAdapter(Context context, ArrayList<CropOption> options) 
		{
			super(context, R.layout.crop_selector, options);

			mOptions = options;

			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup group) 
		{
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.crop_selector, null);

			CropOption item = mOptions.get(position);

			if (item != null)
			{
				((ImageView) convertView.findViewById(R.id.iv_icon))
						.setImageDrawable(item.icon);
				((TextView) convertView.findViewById(R.id.tv_name))
						.setText(item.title);

				return convertView;
			}

			return null;
		}
	}

	public class CropOption 
	{
		public CharSequence title;
		public Drawable icon;
		public Intent appIntent;
	}
	
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) 
	{
		  // TODO Auto-generated method stub
		  int targetWidth = 0;
		  int targetHeight = 0;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        float density =  getResources().getDisplayMetrics().density;


        String screen = new_grid.getScreenResolution(context);

        Debugger.debug(TAG, "screen :" +screen);

        if(screen.equals("X-LARGE"))
        {
            targetWidth = (int) (250 * density);
            targetHeight = (int) (250 * density);

        }
        else if(screen.equals("LARGE"))
        {
            targetWidth = (int) (250 * density);
            targetHeight = (int) (250 * density);
        }
        else if(screen.equals("NORMAL"))
        {
            targetWidth = (int) (150 * density);
            targetHeight = (int) (150 * density);
        }
        else if(screen.equals("SMALL"))
        {
            targetWidth = 120;
            targetHeight = 120;
        }
        else
        {
            targetWidth = 120;
            targetHeight = 120;
        }

        //Log.v(TAG, "targetWidth : "+ targetWidth+"targetHeight : "+ targetHeight);

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

	
	public boolean saveBitmapImage(Bitmap profile)
	{
		
		//String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
		
		File sdIconStorageDir = new File(iconsStoragePath);

				//create storage directories, if they don't exist
		
		if(!sdIconStorageDir.exists())
		{
			sdIconStorageDir.mkdirs();
		}
				
		try 	
		{
			String fname 	= et_fname.getText().toString();
			//String filePath = sdIconStorageDir.toString() + "Profile"+profile_id+".jpg";
					
			//File file = new File(sdIconStorageDir, "Profile"+fname+".jpg");

            File file = new File(sdIconStorageDir, "Profile"+profile_id+".jpg");
					
			//Log.v(TAG,"PROFILEID : "+profile_id);
			////
					
			if (file.exists())
				file.delete();
					
			///
					
					
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			//choose another format if PNG doesn't suit you
			profile.compress(CompressFormat.JPEG, 100, bos);

			bos.flush();
			bos.close();

		} 
		catch (FileNotFoundException e)
		{
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		} 
		catch (IOException e) 
		{
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		}

		return true;
	}
	
	@Override
	public void onBackPressed() 
	{
		if(!(et_fname.getText().toString().equals("")) || !(et_lname.getText().toString().equals("")))
		{
			
			if(profile_op.equals("New") || profile_op.equals("FaceBook_New"))
			{
				saveProfile_details();
			}
			else
			{
				editProfile_details();
			}
		}
		else
		{
			finish();
		}
	}
	
	
	
	
private final class Task_Register extends AsyncTask<Bitmap, Bitmap, Bitmap>
	
	{
		String f_id;
		Bitmap mIcon1 = null;
		
		public Task_Register( String id)
		{			
			// POST parameters to sends
			
			f_id = id;
		}

		@Override
		protected Bitmap doInBackground(Bitmap... params) 
		{
			//Log.v(TAG,"In doin background"+f_id);
			URL img_value = null;
		    
				//img_value = new URL("http://graph.facebook.com/"+f_id+"/picture?type=small");
		    	
		    	//HttpURLConnection urlConnection = (HttpURLConnection) img_value.openConnection();
		    		try 
		    		{
		    			
		    			HttpGet httpRequest = new HttpGet(URI.create("http://graph.facebook.com/"+f_id+"/picture?type=large") );
		                HttpClient httpclient = new DefaultHttpClient();
		                HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
		                HttpEntity entity = response.getEntity();
		                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		                mIcon1 = BitmapFactory.decodeStream(bufHttpEntity.getContent());
		                httpRequest.abort();
		    		   //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		    		  // mIcon1 = BitmapFactory.decodeStream(in);
		    			
		    		}
		    		catch (IOException e) 
		    		{
						e.printStackTrace();
					}
		    		finally 
		    		{
		    		      //urlConnection.disconnect();
		    		}
			 
		    
		   
			//Log.v(TAG,"mIcon1 :"+mIcon1);
		   
			return mIcon1;
		}
		
		
		
		@Override
		protected void onPostExecute(Bitmap mIcon1)
		{
			super.onPostExecute(mIcon1);
		}
	}






private final class Task_insert_device_profile extends AsyncTask<String, String, String>
{
	JSONParser jsonParser;
	JSONObject jo_resp;
	
	boolean inserted = false;
	
	
	int prof_id;
	
	ArrayList<NameValuePair> list_params;

	public Task_insert_device_profile(String fname, String mname, String lname, String gender, 
			String relation, String dob, String blood, String mob, String email,
			String city, String country, String pin, int prof_id)
	{			
		list_params = new ArrayList<NameValuePair>();

		list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, DeviceUtils.getImei(context)));
		list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, DeviceUtils.getSerialId()));
		
		list_params.add(new BasicNameValuePair(Profile_Master.FIRST_NAME, fname));
		list_params.add(new BasicNameValuePair(Profile_Master.MIDDLE_NAME, mname));
		list_params.add(new BasicNameValuePair(Profile_Master.LAST_NAME, lname));
		list_params.add(new BasicNameValuePair(Profile_Master.GENDER, gender));
		list_params.add(new BasicNameValuePair(Profile_Master.RELATION, relation));
		list_params.add(new BasicNameValuePair(Profile_Master.DOB, dob));
		list_params.add(new BasicNameValuePair(Profile_Master.BLOODGROUP, blood));
		list_params.add(new BasicNameValuePair(Profile_Master.MOBILE_NO, mob));
		list_params.add(new BasicNameValuePair(Profile_Master.EMAIL_ID, email));
		list_params.add(new BasicNameValuePair(Profile_Master.CITY, city));
		list_params.add(new BasicNameValuePair(Profile_Master.COUNTRY, country));
		list_params.add(new BasicNameValuePair(Profile_Master.PINCODE, pin));
		
		list_params.add(new BasicNameValuePair(Profile_Master.PROFILE_ID, String.valueOf(prof_id)));
	
		//Log.v(TAG,"list_params :  : " + list_params.toString());
		
		this.prof_id = prof_id;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		jsonParser = new JSONParser();

		// 1st step or second one, because the PHPs are different
		String ls_url = URL_INSERT_DEVICE_PROFILE; 

		jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
		//Log.v(TAG,"Response from server...... : " + jo_resp.toString());

		//Debugger.debug(TAG, list_params.toString());

		if(jo_resp != null)
		{
			try
			{
				int li_success = jo_resp.getInt(New_Grid.TAG_SUCCESS);

				if(li_success == 1)
				{
					int li_connSuccess = jo_resp.getInt(New_Grid.TAG_CONN_SUCCESS);

					if(li_connSuccess == 1)
					{
						////////
						//int profile_inserted =  jo_resp.getInt("profile_inserted");
						
						//if(profile_inserted == 1)
						//{
						boolean profile_inserted = jo_resp.getBoolean("profile_inserted");
						
						if(profile_inserted == true)
						{
							
							int server_profile_id = jo_resp.getInt("server_profile_id");
							
							profile_master.change_status(server_profile_id, prof_id);
                            Debugger.debug(TAG,"prof_id : "+prof_id);
						}
						////////
					}
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
				
			}
		}
		return null;
	}
			
}



private final class Task_edit_device_profile extends AsyncTask<String, String, String>
{
	JSONParser jsonParser;
	JSONObject jo_resp;
	
	
	int server_id;
	int profile_id;
	
	ArrayList<NameValuePair> list_params;

	public Task_edit_device_profile(String fname, String mname, String lname, String gender, 
			String relation, String dob, String blood, String mob,  String email,
			String city, String country, String pin, int server_id, int profile_id)
	{			
		list_params = new ArrayList<NameValuePair>();

		list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, DeviceUtils.getImei(context)));
		list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, DeviceUtils.getSerialId()));
		
		list_params.add(new BasicNameValuePair(Profile_Master.FIRST_NAME, fname));
		list_params.add(new BasicNameValuePair(Profile_Master.MIDDLE_NAME, mname));
		list_params.add(new BasicNameValuePair(Profile_Master.LAST_NAME, lname));
		list_params.add(new BasicNameValuePair(Profile_Master.GENDER, gender));
		list_params.add(new BasicNameValuePair(Profile_Master.RELATION, relation));
		list_params.add(new BasicNameValuePair(Profile_Master.DOB, dob));
		list_params.add(new BasicNameValuePair(Profile_Master.BLOODGROUP, blood));
		list_params.add(new BasicNameValuePair(Profile_Master.MOBILE_NO, mob));
		list_params.add(new BasicNameValuePair(Profile_Master.EMAIL_ID, email));
		list_params.add(new BasicNameValuePair(Profile_Master.CITY, city));
		list_params.add(new BasicNameValuePair(Profile_Master.COUNTRY, country));
		list_params.add(new BasicNameValuePair(Profile_Master.PINCODE, pin));
		
		
		list_params.add(new BasicNameValuePair(Profile_Master.SERVER_PROFILE_ID, String.valueOf(server_id)));
	
		//Log.v(TAG,"list_params :  : " + list_params.toString());
		
		this.server_id = server_id;
		this.profile_id = profile_id;
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		jsonParser = new JSONParser();

		// 1st step or second one, because the PHPs are different
		String ls_url = URL_UPDATE_DEVICE_PROFILE; 

		jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
		//Log.v(TAG,"Response from server...... : " + jo_resp.toString());

		//Debugger.debug(TAG, list_params.toString());

		if(jo_resp != null)
		{
			try
			{
				int li_success = jo_resp.getInt(New_Grid.TAG_SUCCESS);

				if(li_success == 1)
				{
					int li_connSuccess = jo_resp.getInt(New_Grid.TAG_CONN_SUCCESS);

					if(li_connSuccess == 1)
					{
						
						boolean profile_updated = jo_resp.getBoolean("profile_updated");
						
						if(profile_updated == true)
						{
						
							//profile_master.change_status(server_profile_id, profile_id);
							profile_master.change_edit_status( profile_id);
						}
					}
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
				
			}
		}
		return null;
	}
			
}


	

public void delete_fromServer(int server_id, int profile_id)
{
	//int server_id = profile_master.get_server_id(profile_id);
	
	if(InternetUtils.isConnected(context))
	{
		Task_delete_device_profile task = new Task_delete_device_profile(server_id, profile_id);
		task.execute();
	}
	
}


private final class Task_delete_device_profile extends AsyncTask<String, String, String>
{
	JSONParser jsonParser;
	JSONObject jo_resp;
	
	
	int server_id;
	int profile_id;
	
	// POST parameters to send
	ArrayList<NameValuePair> list_params;

	public Task_delete_device_profile( int server_id, int profile_id)
	{			
		// POST parameters to sends
		list_params = new ArrayList<NameValuePair>();

		list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, DeviceUtils.getImei(context)));
		list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, DeviceUtils.getSerialId()));
		
		list_params.add(new BasicNameValuePair(Profile_Master.SERVER_PROFILE_ID, String.valueOf(server_id)));
		
		//Log.v(TAG,"list_params :  : " + list_params.toString());
		
		this.server_id = server_id;
		this.profile_id = profile_id;
		
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		jsonParser = new JSONParser();

		// 1st step or second one, because the PHPs are different
		String ls_url = URL_DELETE_DEVICE_PROFILE; 

		jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
		//Log.v(TAG,"Response from server...... : " + jo_resp.toString());

		//Debugger.debug(TAG, list_params.toString());

		if(jo_resp != null)
		{
			try
			{
				int li_success = jo_resp.getInt(New_Grid.TAG_SUCCESS);

				if(li_success == 1)
				{
					int li_connSuccess = jo_resp.getInt(New_Grid.TAG_CONN_SUCCESS);

					if(li_connSuccess == 1)
					{
						boolean profile_deleted = jo_resp.getBoolean("profile_deleted");
						
						if(profile_deleted == true)
						{
							//profile_master.change_status(server_profile_id, profile_id);
							profile_master.change_edit_status( profile_id);
						}
						
					}
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
				
			}
		}
		return null;
	}
			
}


@SuppressWarnings("deprecation")
public void postOnWall(String msg) 
{
    //Log.v("Tests", "Testing graph API wall post");
//     try 
//     {
//            String response = facebook.request("me");
//            Bundle parameters = new Bundle();
//            parameters.putString("message", msg);
//            parameters.putString("description", "Testing wall pos");
//            response = facebook.request("me/feed", parameters, 
//                    "POST");
//            
//           
//            
//            Log.v("Tests", "got response: " + response);
//            
//            if (response == null || response.equals("") || 
//                    response.equals("false")) 
//            {
//               Log.v("Error", "Blank response");
//            }
//     } 
//     catch(Exception e) 
//     {
//         e.printStackTrace();
//     }
    
   // Task_post t = new Task_post();
   // t.execute();
}



@SuppressWarnings("deprecation")
public void loginToFacebook() 
{
    mPrefs = getPreferences(MODE_PRIVATE);
    String access_token = mPrefs.getString("access_token", null);
    long expires = mPrefs.getLong("access_expires", 0);

    Debugger.debug(TAG, "access_token : "+access_token);
    Debugger.debug( TAG,"expires : "+expires);
 
    if (access_token != null) 
    {
        facebook.setAccessToken(access_token);
    }
 
    if (expires != 0) 
    {
        facebook.setAccessExpires(expires);
    }
 
    if (!facebook.isSessionValid()) 
    {
    	//Task_post t = new Task_post();
        //t.execute();
    	
        facebook.authorize(this,
                new String[] {"publish_actions"},
                new DialogListener()
        		{
 
                    @Override
                    public void onCancel() 
                    {
                        // Function to handle cancel event
                    }
 
                    @Override
                    public void onComplete(Bundle values) 
                    {
                        // Function to handle complete event
                        // Edit Preferences and update facebook acess_token
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putString("access_token",
                                facebook.getAccessToken());
                        editor.putLong("access_expires",
                                facebook.getAccessExpires());
                        editor.commit();
                        Debugger.debug(TAG,"onComplete");
                        //Task_post t = new Task_post();
                        //t.execute();
                        //postToWall();
                        
                        try 
               	     {
               	            String response = facebook.request("me");
               	            Bundle parameters = new Bundle();
               	            parameters.putString("message", "Test Message");
               	            parameters.putString("description", "Testing wall pos");
               	            response = facebook.request("feed", parameters, 
               	                    "POST");



                         Debugger.debug("Tests", "got response: " + response);
               	            
               	            if (response == null || response.equals("") || 
               	                    response.equals("false")) 
               	            {
                                Debugger.debug("Error", "Blank response");
               	            }
               	     } 
               	     catch(Exception e) 
               	     {
               	         e.printStackTrace();
               	     }
                    }

					@Override
					public void onFacebookError(FacebookError e) 
					{
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onError(DialogError e) 
					{
						// TODO Auto-generated method stub
						
					}
              });
    }
}

private final class Task_post extends AsyncTask<String, String, String>
{

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(String... arg0) 
	{
		 try 
	     {
	            String response = facebook.request("me");
	            Bundle parameters = new Bundle();
	            parameters.putString("message", "Test Message");
	            parameters.putString("description", "Testing wall pos");
	            response = facebook.request("feed", parameters, 
	                    "POST");
	            
	           
	            
	            //Log.v("Tests", "got response: " + response);
	            
	            if (response == null || response.equals("") || 
	                    response.equals("false")) 
	            {
	               //Log.v("Error", "Blank response");
	            }
	     } 
	     catch(Exception e) 
	     {
	         e.printStackTrace();
	     }
		return null;
	}
}


/*
private void publishStory() 
{
    Session session = Session.getActiveSession();
    
    Log.v(TAG,"In publishStory");

    if (session != null)
    {
    	Log.v(TAG,"session != null");
        // Check for publish permissions    
        List<String> permissions = session.getPermissions();
        if (!isSubsetOf(PERMISSIONS, permissions)) 
        {
            pendingPublishReauthorization = true;
            Session.NewPermissionsRequest newPermissionsRequest = new Session
                    .NewPermissionsRequest(this, PERMISSIONS);
        session.requestNewPublishPermissions(newPermissionsRequest);
            return;
        }

        Bundle postParams = new Bundle();
        postParams.putString("name", "Facebook SDK for Android");
        postParams.putString("caption", "Build great social apps and get more installs.");
        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
       // postParams.putString("link", "https://developers.facebook.com/android");
       // postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

        Request.Callback callback= new Request.Callback() 
        {
            public void onCompleted(Response response) 
            {
                JSONObject graphResponse = response
                                           .getGraphObject()
                                           .getInnerJSONObject();
                String postId = null;
                try 
                {
                    postId = graphResponse.getString("id");
                } 
                catch (JSONException e) 
                {
                    Log.i(TAG,
                        "JSON error "+ e.getMessage());
                }
                FacebookRequestError error = response.getError();
                if (error != null) 
                {
                    //Toast.makeText(getActivity().getApplicationContext(),error.getErrorMessage(),
                         //Toast.LENGTH_SHORT).show();
                 } else 
                 {
                        //Toast.makeText(getActivity().getApplicationContext(),  postId,
                        //     Toast.LENGTH_LONG).show();
                }
            }
        };

        Request request = new Request(session, "me/feed", postParams, 
                              HttpMethod.POST, callback);

        RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
    }

}

private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) 
{
    for (String string : subset) 
    {
        if (!superset.contains(string)) 
        {
            return false;
        }
    }
    return true;
}

@Override
public void onSaveInstanceState(Bundle outState) 
{
   super.onSaveInstanceState(outState);
   outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
   uiHelper.onSaveInstanceState(outState);
}	

@Override
public void onResume() 
{
    super.onResume();
    uiHelper.onResume();
}


//@Override
//public void onActivityResult(int requestCode, int resultCode, Intent data) 
//{
//    super.onActivityResult(requestCode, resultCode, data);
//    uiHelper.onActivityResult(requestCode, resultCode, data);
//}



@Override
public void onPause() 
{
    super.onPause();
    uiHelper.onPause();
    
   
}

@Override
public void onDestroy() 
{
    super.onDestroy();
    uiHelper.onDestroy();
}


private Session.StatusCallback statusCallback = new Session.StatusCallback() 
{
	
	        @SuppressWarnings("deprecation")
			@Override
		        public void call(final Session session, SessionState state,
	                Exception exception) 
	        	{
	        	
	        	///
	        	mPrefs = getPreferences(MODE_PRIVATE);
	    	    String access_token = mPrefs.getString("access_token", null);
	    	    long expires = mPrefs.getLong("access_expires", 0);
	    	    
	    	    Log.v(TAG, "access_token : "+access_token);
	    	    Log.v( TAG,"expires : "+expires);
	    	 
	    	    if (access_token != null) 
	    	    {
	    	        facebook.setAccessToken(access_token);
	    	    }
	    	 
	    	    if (expires != 0) 
	    	    {
	    	        facebook.setAccessExpires(expires);
	    	    }
	    	    /////
		            if (state.isOpened()) 
		            {
		                Log.v( TAG,"Facebook session opened1.");
		                
		             // Request user data and show the results
		                Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
		                {

		                    @Override
		                    public void onCompleted(GraphUser user, Response response) 
		                    {
		                        if (user != null) 
		                        {
		                        	
		                            ///////
		                            SharedPreferences.Editor editor = mPrefs.edit();
			                        editor.putString("access_token",
			                                facebook.getAccessToken());
			                        editor.putLong("access_expires",
			                                facebook.getAccessExpires());
			                        editor.commit();
		                            
		                            ///////
			                        ////////
			                        
			                        Log.v(TAG,"In Log In completed ");
		                        	
		                        }
		                    }
		                });
		            } 
		            else if (state.isClosed()) 
		            {
		                Log.v(TAG, "Facebook session closed1.");
		            }
		        }
};

*/

@SuppressWarnings("deprecation")
public void postToWall() 
{
    // post on user's wall.
    facebook.dialog(this, "feed", new DialogListener() {
 
        @Override
        public void onFacebookError(FacebookError e) {
        }
 
        @Override
        public void onError(DialogError e) {
        }
 
        @Override
        public void onComplete(Bundle values) {
        }
 
        @Override
        public void onCancel() {
        }
    });
 
}


public void call_GoogleAnalytics_screens()
{
    try {


        //GOOGLE aNALYTICS
        Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);

        t.enableAdvertisingIdCollection(true);

        t.setScreenName("Add_Profile");
        t.send(new HitBuilders.ScreenViewBuilder().build());
        //GOOGLE aNALYTICS
    }
    catch(Exception e)
    {
        //Log.v(TAG,"Exception in analytics :"+e);
    }
}

public void call_GoogleAnalytics_sessions()
{
	//GOOGLE aNALYTICS
	Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
	
	t.enableAdvertisingIdCollection(true);
	
	t.setScreenName("Add_Profile");
	t.send(new HitBuilders.ScreenViewBuilder().build());
	
	// Start a new session with the hit.
	t.send(new HitBuilders.AppViewBuilder().setNewSession().build());
	//GOOGLE aNALYTICS
}


public void call_GoogleAnalytics_events(String category, String action,
		String label)
{
	Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
	
	t.enableAdvertisingIdCollection(true);
		// Build and send an Event.
		t.send(new HitBuilders.EventBuilder()
		    .setCategory(category)
		    .setAction(action)
		    .setLabel(label)
		    .build());
}
	
}
