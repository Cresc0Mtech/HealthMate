package com.cresco.HealthMate;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.cresco.HealthMate.ConfirmDialog.NoticeDialogListener;
//import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.location.LocationRequest;

public class New_Grid extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener
												, LocationListener, NoticeDialogListener
{
	Context context;
	String TAG = this.getClass().getSimpleName();
	
	LocationManager locationManager;
	Location location;
	double latitude;
	double longitude;
	GetAddressTask gtask; 
	
	Location_Master loc_master;
	 
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
		 
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	
	Profile_Master profile_master;
	Parameter_Master param;
	ArrayList<Bundle> profile_list;
	final static int REQ_CODE = 1;
	
	int p_count;
	Animation from_middle, to_middle;
	Typeface typeface;
	
	Grid_Adapter gridAdapter;
	
	GridView profile_grid;
	CrescoTextView tv_create_new, tv_edit_profile, tv_delete_profile;
	LinearLayout ll_app_icon, ll_create_new, ll_add_profile, ll_fb_login, ll_sub1;
	
	private Facebook facebook;
    @SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
	
    private UiLifecycleHelper uiHelper;
    private static String APP_ID = "980146335343232";
    
    int height_grids, height_header, width_set, columns;
    
    LinearLayout ll_create_new1, ll_create_new2, ll_adMob;
    FlipVertical_animation flipAnimation;
    
    private LoginButton tv_fb_login;

    //int DENSITY;
    float density;
    
    
    private static final List<String> PERMISSIONS = new ArrayList<String>() 
    {
        {
            add("user_location");
            add("user_birthday");
            add("user_likes");
        }
    };
	
    ListViewSwipeDetector lvSwipeDetector;
	public static final int MSG_ANIMATION_REMOVE 	= 10;
    
  
  //KL start /* GCM registration through new google cloud api */
  	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
  	GoogleCloudMessaging gcm;
  	public static final String PROPERTY_REG_ID = "registration_id";
  	private static final String PROPERTY_APP_VERSION = "appVersion";
  	String regId ;
  	/////////////
  	
  	String ls_isRegisterd;
  	RegistrationDetails rd;
  	Personal_Details pd;
  	ContentValues cv;
  	
  	// The folder in which healthmate php files are kept.
 	 final static String URL_FOLDER_BASE = "http://www.crescomtech.com/healthmate/";

  	
  	// Called when user enter in app
 	public final static String URL_INSERT_AND_VERIFY_DEVICE_DETAILS = URL_FOLDER_BASE + "verify_device.php";
 	
 	// Called when user enter in app get location and insert on server tables
 	public final static String URL_INSERT_LOCATION_DETAILS = URL_FOLDER_BASE + "insert_location_details.php";
 	public final static String TAG_SUCCESS		= "success";
	public final static String TAG_MESSAGE		= "message";
	public final static String TAG_CONN_SUCCESS	= "conn_success";
	public final static String TAG_CAN_REGISTER	= "can_register";
	
	public final static String URL_SERVER_VERIFICATION = URL_FOLDER_BASE + "server_profile_verify.php";
	
	
	
	private GestureDetector gd;
	SuperCardToast superCardToast;
	ArrayList<Bundle> fblist ;
	FaceBookDetails fbdetails;
	
	private InterstitialAd interstitial;
	
	MeasurementList_Profile measure;
	SharedPreferences toast_sp1, sp;
	SharedPreferences.Editor sp_toastEditor1, sp_editor;

    SharedPreferences  rate_sp, screenCount_sp;
    SharedPreferences.Editor  sp_rateEditor,  sp_screenEditor;

    static String SHARETEXT = " Hey! I am maintaining my health records in HealthMate. It's simple, elegant & immensely useful " +
            "to maintain blood pressure & blood sugar readings of all my family members in a single app! Download " +
            "HealthMate from Play Store now. "+MeasurementList_Profile.appName;

	public static final int MAX_SCREENCOUNT= 10;
    ConfirmDialog confirmDialog;

    LinearLayout ll_new_grid;
	
	
	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_grid);
		context = this;

        ///
        ll_new_grid = (LinearLayout) findViewById(R.id.ll_new_grid);
        int mheight = ll_new_grid.getHeight();

        Debugger.debug(TAG,"My View height :"+mheight);
        //
		
		///////////
		// Prepare the Interstitial Ad
		interstitial = new InterstitialAd(New_Grid.this);
		// Insert the Ad Unit ID
		interstitial.setAdUnitId("ca-app-pub-5304110283081319/7796423587");
		String dev_id = DeviceUtils.getImei(context);


		facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
		
		profile_grid = (GridView) findViewById(R.id.profile_grid);
		ll_app_icon = (LinearLayout) findViewById(R.id.ll_app_icon);
		ll_create_new = (LinearLayout) findViewById(R.id.ll_create_new);
		ll_add_profile = (LinearLayout) findViewById(R.id.ll_add_profile);
		ll_fb_login = (LinearLayout) findViewById(R.id.ll_fb_login);
		
		ll_create_new1 = (LinearLayout) findViewById(R.id.ll_create_new1);
		ll_create_new2 = (LinearLayout) findViewById(R.id.ll_create_new2);
		
		ll_sub1 = (LinearLayout) findViewById(R.id.ll_sub1);
		
		tv_fb_login = (LoginButton) findViewById(R.id.tv_fb_login);
		
		ll_adMob = (LinearLayout) findViewById(R.id.ll_adMob);
		
		///
		//tv_fb_login.setReadPermissions(Arrays.asList("public_profile","user_birthday", "email", "user_location"));
		
		tv_fb_login.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes"));
		///
		
		ll_create_new.setOnClickListener(this);
		ll_add_profile.setOnClickListener(this);
		ll_fb_login.setOnClickListener(this);
		profile_grid.setOnItemClickListener(this);
		profile_grid.setOnItemLongClickListener(this);
		ll_create_new2.setOnClickListener(this);
		ll_app_icon.setOnClickListener(this);
		 
		typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		
		tv_fb_login.setTypeface(typeface);
		
		//FileUtils.copyDB(context);
		param = new Parameter_Master(context);
		fbdetails = new FaceBookDetails(context);
		loc_master = new Location_Master(context);
		
		measure = new MeasurementList_Profile();
		
		Cursor c1 = param.getAlllist();
		
		if(c1.getCount()>0)
		{
            Debugger.debug(TAG,"Already inserted :"+p_count);
		}
		else
		{
            Debugger.debug(TAG,"Insert parameter :"+p_count);
			insertParameterMaster();
		}
		
		profile_master = new Profile_Master(context);
		
		//setting grid adapter
		set_Profile_grid();
		//

				
		  uiHelper = new UiLifecycleHelper(this, statusCallback);
	      uiHelper.onCreate(savedInstanceState);
	      
	      ///////		
	      try 
	      {
	    	  PackageInfo info = getPackageManager().getPackageInfo("com.cresco.HealthMate", PackageManager.GET_SIGNATURES);
	    	  for (Signature signature : info.signatures) 
	    	  {
	    	  MessageDigest md = MessageDigest.getInstance("SHA");
	    	  md.update(signature.toByteArray());
	    	  String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                  Debugger.debug("MY KEY HASH:", sign);
	    	  //Toast.makeText(getApplicationContext(),sign, Toast.LENGTH_LONG).show();
	    	  }
	    	  } catch (NameNotFoundException e) {
	    	  } catch (NoSuchAlgorithmException e) {
	    	  }
	      //////
	      
	      
	      //sending device details to server to verify device
			/***** gcm registration process*****/
			
			
			register_gcm();
			
			/*****************************/
			
			
			
			////***** find current location
			
			if(InternetUtils.isConnected(context))
			{
				get_location(context);
			}
			
			
			if(InternetUtils.isConnected(context))
			{
				server_verification();
			}
			
			////
			


			
			// custom toast
			boolean  lb_showtoast;
			toast_sp1 = context.getSharedPreferences("TOAST1",Context.MODE_PRIVATE);
			
			lb_showtoast = toast_sp1.getBoolean("SHOW_TOAST_GRID", true);

        Debugger.debug(TAG,"lb_showtoast : "+lb_showtoast);
			
			if(lb_showtoast == true)
			{
                //measure.show_share_RateDailog();

				final int interval = 2000; // 5 Second
				Handler handler = new Handler();
				Runnable runnable = new Runnable()
				{
				    public void run() 
				    {
				        //Toast.makeText(MyActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
				    	customToast();
				    	
				    	set_toastCount();
				    }
				};
				
				handler.postAtTime(runnable, System.currentTimeMillis()+interval);
				handler.postDelayed(runnable, interval);
			}
			//

        //Kalyani start

        sp = context.getSharedPreferences("WELCOME_NOTE",Context.MODE_PRIVATE);
        sp_editor = sp.edit();

        boolean iscalled = sp.getBoolean("IS_CALLED", false);

        Debugger.debug(TAG, "iscalled : "+iscalled);
        if(iscalled == false)
        {
            sp_editor.putBoolean("IS_CALLED",true);
            Intent intent = new Intent(this, Welcome_Note.class);
            startActivity(intent);
            sp_editor.commit();
        }
        //kalyani end





        if(DbHelper.USE_ADD)
        {
            AdView adView = (AdView) this.findViewById(R.id.adMob);
            //request TEST ads to avoid being disabled for clicking your own ads
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                    //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
                    .addTestDevice(dev_id) // Nexus 5
                    .build();
            //"356262052582091"
            adView.loadAd(adRequest);
        }



        //GOOGLE ANALYTICS
        if(DbHelper.USE_ANALYTICS)
        {
            call_GoogleAnalytics_screens();

            call_GoogleAnalytics_sessions();
        }
        //GOOGLE ANALYTICS

        getscreenCount();

    }
	
	
	
	public void customToast()
	{
		AssetManager mgr = context.getAssets();
		superCardToast = new SuperCardToast(this, SuperToast.Type.STANDARD);
		superCardToast.setDuration(SuperToast.Duration.LONG);
		superCardToast.setText("Long press on a Profile to edit/delete it");
		//superCardToast.setButtonIcon(SuperToast.Icon.Dark.EDIT, "EDIT");
		//superCardToast.setOnClickWrapper(onClickWrapper);
		superCardToast.setBackground(R.color.cresco_gray);
		superCardToast.setTextColor(context.getResources().getColor(R.color.Gray));
		//superCardToast.setTextSize(15);
		superCardToast.setTouchToDismiss(true);
		//createFromAsset(context.getAssets(),"fonts/DistProTh.otf")
		//superCardToast.setTypefaceStyle(Typeface.createFromAsset(mgr,"fonts/DistProTh.otf"));
				//superCardToast.setButtonTypefaceStyle(typefaceStyle);
		superCardToast.show();
		//superCardToast.
	}
	

	public void set_Profile_grid()
	{
		profile_list = new ArrayList<Bundle>();
		profile_list = profile_master.getAllProfileList();
		
		//** get display screen width and height
		DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //getWindowManager().getD
		
	     density  = getResources().getDisplayMetrics().density;
		int width=dm.widthPixels;
		int height=dm.heightPixels;

        Debugger.debug(TAG," width in pixel: "+width);
        Debugger.debug(TAG," height in pixel  : "+height);
		
		float ht = dm.heightPixels/density;
        Debugger.debug(TAG,"density : "+density);
        Debugger.debug(TAG,"Hight in DP : "+ht);
		
		
		//Toast.makeText(getApplicationContext(),"height : "+ height, Toast.LENGTH_LONG).show();
				
		//Log.v(TAG,"Display height in pixel  : "+height); //nexus 2464
		//Log.v(TAG,"Display width in pixel: "+width);
				
				
		int profile_count = profile_list.size();

        Debugger.debug(TAG," profile_count : "+profile_count);
				
		ViewGroup.LayoutParams layoutParams = ll_sub1.getLayoutParams(); 
		ViewGroup.LayoutParams layoutParams1 = ll_adMob.getLayoutParams();
		
		int d;
		
		if(ht >= 400 && ht<= 720)
		{
            Debugger.debug(TAG,"ht dp1:"+ht);
			d = (int) (50*1.23*density);
			height = height- d;
            Debugger.debug(TAG,"height screen : "+height);
			layoutParams1.height = d;
			ll_adMob.setLayoutParams(layoutParams1);
		}
		else
		{
            Debugger.debug(TAG,"ht dp2:"+ht);
			d = (int) (90*1.23*density);
			height = height- d;

            Debugger.debug(TAG,"height screen : "+height);
			layoutParams1.height = d;
			ll_adMob.setLayoutParams(layoutParams1);
		}
        Debugger.debug(TAG,"AD height in pixel :"+d);
		
		
					
		if(profile_count == 0)
		{
			int height1 =  height/3;
					
			height_grids = height - height1;
					
			height_header = height1;
					
			columns = 1;
					
		}
				
		if(profile_count == 1)
		{
			int height1 =  height/3;
						
			height_grids = height - height1;
			//height_grids = height1;
						
			height_header = height1;
						
			columns =1;
						
		}
		else if(profile_count == 2)
		{
			height_grids =  height/3;
						
			height_header = height_grids;
						
			columns =1;
		}
					
		else if(profile_count == 3)
		{
			height_grids =  height/3;
						
			height_header = height_grids;
						
			columns = 2;
		}
		else if(profile_count == 4)
		{
			height_grids =  height/3;
						
			height_header = height_grids;
					
			columns = 2;
		}
		else if(profile_count > 4)
		{
			//height_grids =  width/2;
						
			//height_header = width/2;

            height_grids =  height/3;

            height_header = height_grids;
						
			columns = 2;
		}
					
		width_set = width;

        Debugger.debug(TAG,"height_set : "+height_grids);
        Debugger.debug(TAG,"columns : "+columns);
					
		layoutParams.height = height_header;
		ll_sub1.setLayoutParams(layoutParams);
		
		//layoutParams1.height = 100;
		//ll_adMob.setLayoutParams(layoutParams1);
		
					
		profile_grid.setNumColumns(columns);
		
		if(ht >= 400 && ht<= 720)
		{
            Debugger.debug(TAG,"ht  :"+ht);
			d = (int) (50*1.23*density);
			height = height+ d;
			
		}
		else
		{
            Debugger.debug(TAG,"ht :"+ht);
			d = (int) (90*1.23*density);
			height = height + d;
			
		}
					
		gridAdapter = new Grid_Adapter(this, profile_list, height_grids, width_set, height, density);
				
		profile_grid.setAdapter(gridAdapter);
				
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		
			case R.id.ll_add_profile:
				
				createProfile_choice();
				
				break;
				
			case R.id.ll_fb_login:

				break;
				
			case R.id.ll_create_new2 :
				
				///////
				Create_Profile();
				
				break;
				
				
				
			case R.id.ll_app_icon :

				Intent i1 = new Intent(this, AdnoteSettings.class);
				startActivity(i1);
				
				break;

            case R.id.cb_shareApp:

                confirmDialog.dismiss();

                shareApp(context);

                break;

            case R.id.cb_rate:

                confirmDialog.dismiss();

                rateApp(context);

                break;
		}
	}
	
	public void Create_Profile()
	{
		boolean b = checkForFaceBookIsInstalled();
		
		//Log.v(TAG,"checkForFaceBookIsInstalled : "+b);
		
		fblist = new ArrayList<Bundle>();
		fblist = fbdetails.getAllProfileList();
		
		int size = fblist.size();
		
		//Log.v(TAG,"Size of fb list :"+fblist.size());
		
		//////
		if(b == true)
		{
			if(size > 0)
			{
				createProfile_choice();
			}
			else
			{
				flipAnimation = new FlipVertical_animation(ll_create_new2, ll_create_new1, 0);
				
				if (ll_create_new1.getVisibility() == View.GONE)
			    {
			        //flipAnimation.reverse();
			    }
				
				ll_create_new.startAnimation(flipAnimation);
			}
		}
		else
		{
			flipAnimation = new FlipVertical_animation(ll_create_new2, ll_create_new1, 0);
			
			if (ll_create_new1.getVisibility() == View.GONE)
		    {
		        //flipAnimation.reverse();
		    }
			
			ll_create_new.startAnimation(flipAnimation);
		}
		 
		
		
	}
	
	public boolean checkForFaceBookIsInstalled()
	{
		try
		{
		    ApplicationInfo info = getPackageManager().
		            getApplicationInfo("com.facebook.katana", 0 );
		    return true;
		} 
		catch( PackageManager.NameNotFoundException e )
		{
		    return false;
		}
	}
	
	
	public void createProfile_choice()
	{
		//GOOGLE ANALYTICS Event Tracking  -profile add
        if(DbHelper.USE_ANALYTICS)
        {
            call_GoogleAnalytics_events("Profile", "Add",
                    "Add_Profile_Button");
        }
				
		//GOOGLE ANALYTICS Event Tracking  -profile add
				

		Intent i = new Intent(this, Add_Profile.class);
		i.putExtra(Grid_Adapter.PROFILE_OPERATION, "New");
		
		startActivityForResult(i, REQ_CODE);
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		//////
		super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
		//////

		if(requestCode == REQ_CODE)
		{

			if (resultCode == RESULT_OK)
			{
				
				set_Profile_grid();

				
				///////
				
				if (Session.getActiveSession() != null) 
				{
					Session.getActiveSession().closeAndClearTokenInformation();
						// Session.getActiveSession().close();
					Log.i("fb", "in");
					Session.setActiveSession(null);
					
					//Toast.makeText(context, "In log out", Toast.LENGTH_LONG).show();
				} 
			 }
			 else if(resultCode == RESULT_CANCELED)
			 {
				 
				 if (Session.getActiveSession() != null) 
					{
						Session.getActiveSession().closeAndClearTokenInformation();
							// Session.getActiveSession().close();
						Log.i("fb", "in");
						Session.setActiveSession(null);
						
						//Toast.makeText(context, "In log out", Toast.LENGTH_LONG).show();
					} 
					
			 }
			
			ll_create_new2.setVisibility(View.VISIBLE);
			ll_create_new1.setVisibility(View.GONE);
		}
	}
	
	
	
	
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
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
	{
		int li_profileId = 0;
		/*
		if (lvSwipeDetector.swipeDetected())
		{
			Message msg = new Message();
			//msg.arg1 = position - 1;
			msg.arg1 = position;

			if (lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.LR || 
					lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.RL)
			{
				msg.what = MSG_ANIMATION_REMOVE;
				msg.arg2 = lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.LR ? 1 : 0;
				msg.obj  = v;
				handler.sendMessage(msg);
				Log.v(TAG, "IN swipe Detection");
			}


		}
		else
		{*/
		//superCardToast.dismiss();
		
		profile_list = new ArrayList<Bundle>();
		profile_list = profile_master.getAllProfileList();
		
		li_profileId = profile_list.get(position).getInt(Profile_Master.PROFILE_ID);
		
		Intent i = new Intent(this, MeasurementList_Profile.class);
		i.putExtra("ProfileId", li_profileId);
		
		//startActivity(i);
        //KKK
        startActivityForResult(i, REQ_CODE);

				
	}
	
	
	@SuppressWarnings("deprecation")
	
	public void loginToFacebook() 
	{
		/*
		if (Session.getActiveSession() != null) 
		{
			Session.getActiveSession().closeAndClearTokenInformation();
			// Session.getActiveSession().close();
			Log.i("fb", "in");
			Session.setActiveSession(null);
			
			Toast.makeText(context, "In log in", Toast.LENGTH_LONG).show();
		} 
		else 
		{
			Toast.makeText(context, "In log out", Toast.LENGTH_LONG).show();

			Log.i("fb", "out");
			Session.openActiveSession(this, true, new Session.StatusCallback() 
			{
				@Override
				public void call(Session session, SessionState state,
						Exception exception) 
				{
					// TODO Auto-generated method stub

					if (session.isOpened()) 
					{
						Request.executeMeRequestAsync(session,
								new Request.GraphUserCallback() 
						{
							@Override
							public void onCompleted(GraphUser user,
									Response response) 
							{
								// TODO Auto-generated method stub
								if (user != null) 
								{
									// write ur code here..
		                        	//Toast.makeText(context, "login completed", Toast.LENGTH_LONG).show();
									send_facebookData(user);
								}
							}
						});
					}
					else
					{
					
					}
					
				}
			});
		}

		
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
	 
	    if (!facebook.isSessionValid()) 
	    {
	        facebook.authorize(this,
	                new String[] { "email", "publish_stream" },
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
	                    	Intent i = new Intent(New_Grid.this, Add_Profile.class);
	                        startActivity(i);
	                        
	                        //Toast.makeText(context, "login completed", Toast.LENGTH_LONG).show();
	                        // Function to handle complete event
	                        // Edit Preferences and update facebook acess_token
	                        SharedPreferences.Editor editor = mPrefs.edit();
	                        editor.putString("access_token",
	                                facebook.getAccessToken());
	                        editor.putLong("access_expires",
	                                facebook.getAccessExpires());
	                        editor.commit();
	                        
	                        
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
	    */
	}
	
	/*
	 if (Session.getActiveSession() == null || !Session.getActiveSession().isOpened()) 
	 {
            Session.openActiveSession(
                    this, 
                    true, 
                    PERMISSIONS,
                    new Session.StatusCallback()
	 */
//	private Session.StatusCallback statusCallback = (StatusCallback) Session.openActiveSession(
//            this, 
//            true, 
//            PERMISSIONS,
//            new Session.StatusCallback()
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
		    	    
		    	    //Log.v(TAG, "access_token : "+access_token);
		    	    //Log.v( TAG,"expires : "+expires);
		    	 
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
			                //Log.v( TAG,"Facebook session opened1.");
			                
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
				                        
				                        //Log.v(TAG,"In Log In completed ");
			                        	//Toast.makeText(context, "login completed", Toast.LENGTH_LONG).show();
			                        	
				                        ///////*****
				                       /* Session.OpenRequest openRequest = null;
				                        openRequest = new Session.OpenRequest(New_Grid.this);

				                        if (openRequest != null) {
				                            openRequest.setDefaultAudience(SessionDefaultAudience.FRIENDS);
				                            openRequest.setPermissions(Arrays.asList("user_birthday", "email", "user_location", "friends_birthday","read_friendlists" ));
				                            openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
				                            //session.openForRead(openRequest);
				                        }*/
				                        
				                        //////******
				                        
				                        requestPermissions();
				                        
			                        	send_facebookData(user);
			                    		
				                        /////////
			                        }
			                    }
			                });
			            } 
			            else if (state.isClosed()) 
			            {
			                //Log.v(TAG, "Facebook session closed1.");
			            }
			        }
	};
	//});

	
	public void send_facebookData(GraphUser user)
	{
		//GOOGLE ANALYTICS Event Tracking  -profile add
        if(DbHelper.USE_ANALYTICS)
        {
            call_GoogleAnalytics_events("Profile", "Add",
                    "FaceBook_Button");
        }
						
		//GOOGLE ANALYTICS Event Tracking - profile add
		
		String name = String.format("Name: %s\n\n", 
		        user.getName());
		
		String gender = String.format("Gender: %s\n\n", 
		   	    user.getProperty("gender"));
		
		String dob = String.format("Birthday: %s\n\n", 
		        user.getBirthday());
		
		///////
		//String user_name = String.format("username: %s\n\n", 
		//		   	    user.getUsername());
		
		//Log.v(TAG,"USER NAME ==="+user_name);
		/*
		String LOCATION = String.format("Location: %s\n\n", 
		        user.getLocation().getProperty("name"));
		
		Log.v(TAG,"LOCATION ==="+LOCATION);
		
		String loc[] = LOCATION.split(":");
		String loc1 = loc[1].trim();
		String loc2[] = loc1.split(",");
		String city = loc2[0].trim();
		String country = loc2[1].trim();
		
		
		Log.v(TAG,"city :"+city + ".....country : "+country);
		*/
		
		String hm = String.format("HomeTown: %s\n\n", 
		        user.getProperty("location"));
		
		//Log.v(TAG,"HOMETOWN ==="+hm);
		
		//String hString =
		//////////////
		
		
		String face_id = user.getId();
	    
		
		Intent i = new Intent(New_Grid.this, Add_Profile.class);
       // Intent i = new Intent(this, test.class);
		i.putExtra(Grid_Adapter.PROFILE_OPERATION, "FaceBook_New");
		
		i.putExtra("facebook_id", face_id);
		i.putExtra("name", name);
		//i.putExtra(Grid_Adapter.PROFILE_OPERATION, "FaceBook_New");
		i.putExtra("gender", gender);
		i.putExtra("dob", dob);
		
		//i.putExtra("city", city);
		//i.putExtra("country", country);
		
		startActivityForResult(i, REQ_CODE);
	}
	
	public void requestPermissions() 
	{
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
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
	    
	   //if (Session.getActiveSession() != null) 
		//{
		//	Session.getActiveSession().closeAndClearTokenInformation();
		//	// Session.getActiveSession().close();
		//	Log.v("fb", "in");
		//	Session.setActiveSession(null);
		//} 
	}

	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	///////////////////////////////
	/********GCM Integration*******/
	
	
	public void get_deviceDetails(String reg_Id)
	{
		
		//Log.v(TAG,"In get_deviceDetails  : " + regId);
		
		String deviceId, macAddress, androidId, serialId, versionId, device_emailId,
				osId, osVersion, deviceModel, devPayload;
		

		// get the device ID
		deviceId = DeviceUtils.getImei(context);
		
		macAddress = DeviceUtils.getMacAddress(context);

		// getting the android ID
		androidId = DeviceUtils.getAndroidId(context);

		// getting serial ID	
		serialId = DeviceUtils.getSerialId();
		
		//getting email
		device_emailId = DeviceUtils.getEmailAddress(context);
		
		osId 		= "Android";
		osVersion 	= android.os.Build.VERSION.RELEASE;
		deviceModel = android.os.Build.MODEL;

		// getting the version of MAccounts
		PackageInfo packageInfo = null;

		try 
		{
			packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} 
		catch (NameNotFoundException e) 
		{
			e.printStackTrace();
		}

		versionId = packageInfo.versionName;
		
		devPayload = deviceId + serialId;
		
		//inserting device details in lacal table
		insert_deviceDetails(devPayload,deviceId, macAddress, androidId, serialId, versionId, device_emailId,
				osId, osVersion, deviceModel);
		
		
		Task_verify_deviceDetails task = new Task_verify_deviceDetails(reg_Id, devPayload, deviceId, macAddress, 
											 androidId, serialId, versionId, device_emailId,
											 osId, osVersion, deviceModel);
		task.execute();
	}
	
	
	
	public void insert_deviceDetails(String devPayload,String deviceId, String macAddress, String androidId, String serialId, String versionId, String device_emailId,
									 String osId, String osVersion, String deviceModel)
	{
		if(deviceId == null || deviceId.equals(""))
		{
			deviceId = "";
		}
		
		if(serialId == null || serialId.equals(""))
		{
			serialId = "";
		}
		
		if(device_emailId == null || device_emailId.equals(""))
		{
			device_emailId = "";
		}
		
		if(osId == null || osId.equals(""))
		{
			osId = "";
		}
		
		if(osVersion == null || osVersion.equals(""))
		{
			osVersion = "";
		}
		
		if(versionId == null || versionId.equals(""))
		{
			versionId = "";
		}
		
		if(deviceModel == null || deviceModel.equals(""))
		{
			deviceModel = "";
		}
		
		if(androidId == null || androidId.equals(""))
		{
			androidId = "";
		}
		
		if(macAddress == null || macAddress.equals(""))
		{
			macAddress = "";
		}
		
		cv = new ContentValues();
		
		cv.put(Personal_Details.ID, 0);
		cv.put(Personal_Details.DEVPAYLOAD, devPayload);
		cv.put(Personal_Details.IMEI_ID, deviceId);
		cv.put(Personal_Details.SERIAL_NO, serialId);
		cv.put(Personal_Details.DEVICE_EMAIL, device_emailId);
		cv.put(Personal_Details.INSTALL_DATE, "");
		cv.put(Personal_Details.INSTALL_TYPE, "");
		cv.put(Personal_Details.OS_ID, osId);
		cv.put(Personal_Details.OS_VERSION, osVersion);
		cv.put(Personal_Details.VERSION_ID, versionId);
		cv.put(Personal_Details.VERSION_CODE, "");
		cv.put(Personal_Details.DEVICE_MODEL, deviceModel);
		cv.put(Personal_Details.ANDROID_ID, androidId);
		cv.put(Personal_Details.MAC_ADDRESS, macAddress);
		
		//Log.v(TAG,"CV :  : " + cv.toString());
		
		pd = new Personal_Details(context);
		pd.CRUD(cv);
	}
	
	private final class Task_verify_deviceDetails extends AsyncTask<String, String, String>
	{
		JSONParser jsonParser;
		JSONObject jo_resp;
		
		boolean lb_canRegisterWithGcm = false;
		String msg ;
		String install_date = null;
		String i_Date;
		Date date;
		
		// POST parameters to send
		ArrayList<NameValuePair> list_params;

		public Task_verify_deviceDetails(String reg_Id , String devPayload, String deviceId, String macAddress, String androidId, String serialId, String versionId, String device_emailId,
				 						 String osId, String osVersion, String deviceModel)
		{			
			// POST parameters to sends
			list_params = new ArrayList<NameValuePair>();

			list_params.add(new BasicNameValuePair(RegistrationDetails.REG_ID, reg_Id));
			list_params.add(new BasicNameValuePair(Personal_Details.DEVPAYLOAD, devPayload));
			list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, deviceId));
			list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, serialId));
			list_params.add(new BasicNameValuePair(Personal_Details.DEVICE_EMAIL, device_emailId));
			//list_params.add(new BasicNameValuePair(Personal_Details.INSTALL_DATE, ""));
			list_params.add(new BasicNameValuePair(Personal_Details.INSTALL_TYPE, ""));
			list_params.add(new BasicNameValuePair(Personal_Details.OS_ID, osId));
			list_params.add(new BasicNameValuePair(Personal_Details.OS_VERSION, osVersion));
			list_params.add(new BasicNameValuePair(Personal_Details.VERSION_ID, versionId));
			list_params.add(new BasicNameValuePair(Personal_Details.VERSION_CODE, ""));
			list_params.add(new BasicNameValuePair(Personal_Details.DEVICE_MODEL, deviceModel));
			list_params.add(new BasicNameValuePair(Personal_Details.ANDROID_ID, androidId));
			list_params.add(new BasicNameValuePair(Personal_Details.MAC_ADDRESS, macAddress));
		
			//Log.v(TAG,"list_params :  : " + list_params.toString());
		}
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}
				
				
		
		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(String... arg0) 
		{
			jsonParser = new JSONParser();

			// 1st step or second one, because the PHPs are different
			String ls_url = URL_INSERT_AND_VERIFY_DEVICE_DETAILS; 

			jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
            Debugger.debug(TAG,"Response from server...... : " + String.valueOf(jo_resp));

			//Debugger.debug(TAG, list_params.toString());

			if(jo_resp != null)
			{
				// file was called successfully
				try
				{
					// checking if the mandatory params were passed
					int li_success = jo_resp.getInt(TAG_SUCCESS);

					if(li_success == 1)
					{
						int li_connSuccess = jo_resp.getInt(TAG_CONN_SUCCESS);

						if(li_connSuccess == 1)
						{
							// successfully connected to the database on the server

							//boolean lb_canRegisterWithGcm = jo_resp.getBoolean(TAG_CAN_REGISTER);

							//if(lb_canRegisterWithGcm)
							//{
									
							//}
							
							if(jo_resp.has("install_date"))
							{
								install_date = jo_resp.getString("install_date");
								
								
								//String dateStr = obj.getString("birthdate");
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								try {
									date = sdf.parse(install_date);
									 
									i_Date = sdf.format(date);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 
								
								//Log.v(TAG,"jo_resp has install date ");
							}
							
							msg = jo_resp.getString("message");
							
							//Log.v(TAG,"install_date :  " + install_date);
							
							
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
		
		
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			
			//if(lb_canRegisterWithGcm)
			//{
				//register_gcm();
			//}
			
			if(!(install_date ==  null))
			{
				pd = new Personal_Details(context);
				
				//Log.v(TAG,"install_date :  " + install_date);
				
				pd.update_InstallDate(i_Date);
				
				
			}
		}
	}
	
	
	
	public void register_gcm()
	{
		/*****GCm registration process******/
		
		//Log.v(TAG,"In register_gcm");
		
		rd = new RegistrationDetails(this);
		ls_isRegisterd = rd.getRegisteredFlag();

		if(ls_isRegisterd == null || ls_isRegisterd == "") ls_isRegisterd = "N";
		
		
		//Log.v(TAG,"is registered : "+ls_isRegisterd);
		
		/////////////////////////////////
		///KL start /* GCM registration through new google cloud api */
		if (checkPlayServices(context)) 
		{
			if(ls_isRegisterd.equals("N"))
			{
				//registering device with gcm
				if(InternetUtils.isConnected(context))
				{
					///////
					registeringWithGCM();
					////////
				}
				else
				{
					//Toast.makeText(this, "Please connect to working Internet connection!", Toast.LENGTH_SHORT).show();
				}
			}
					       
		}

		else
		{
			Log.i(TAG, "No valid Google Play Services APK found.");
				            
			Toast.makeText(context, "Google Play Services not found. Cannot proceed with Registration.", Toast.LENGTH_LONG).show();
		}
		//KL end
	}
	
	
	public void registeringWithGCM()
	{
		//Log.v(TAG,"In registeringWithGCM");
		//KL start /* GCM registration through new google cloud api */
		if (checkPlayServices(context)) 
		{
            gcm = GoogleCloudMessaging.getInstance(context);

            if(InternetUtils.isConnected(context)) {
                Task_GCMregister gcm_reg = new Task_GCMregister();
                gcm_reg.execute();
            }
            
        } 
		else 
        {
            Log.i(TAG, "No valid Google Play Services APK found.");
            
            Toast.makeText(context, "Google Play Services not found. Cannot proceed with Registration.", Toast.LENGTH_LONG).show();
        }
		//KL end
	}
	
	
	//KL start /* GCM registration through new google cloud api */
	private boolean checkPlayServices(Context context) 
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if (resultCode != ConnectionResult.SUCCESS) 
		{
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) 
			{
				   GooglePlayServicesUtil.getErrorDialog(resultCode, this,
				   PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} 
			else

			{
				  Log.i(TAG, "This device is not supported.");
				  finish();
			}
			return false;
		}
			    
		return true;
	}
	
	
	private final  class Task_GCMregister extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) 
		{
			
			//Log.v(TAG,"In Task_GCMregister doin background");
			
			String msg = "";
            try 
            {
                if (gcm == null) 
                {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regId = gcm.register(CommonUtilities.SENDER_ID);
                
                
                msg = "Device registered, registration ID=" + regId;
                
                //Log.v(TAG,"In gcm register asynctask :"+regId);
                
            } 
            catch (IOException ex) 
            {
                msg = "Error :" + ex.getMessage();
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            return msg;

		}
		
		
		@Override
		protected void onPostExecute(String result)
		{
			//super.onPostExecute(result);

            try {
                if (!(regId.equals("")) || !(regId == null)) {
                    //register_gcm();

                    //Log.v(TAG, "In Task_GCMregister onPostExecute  : " + regId);

                    get_deviceDetails(regId);
                } else {
                    //Log.v(TAG, "Null onPostExecute  : " + regId);
                }
            }
            catch(Exception e)
            {
                //Log.v(TAG,"Exception15 :"+e);
            }
		}
		
	}
	
	
	
	public class GestureListener extends GestureDetector.SimpleOnGestureListener 
	{
		int pos;
		/*
	    public boolean onDown(MotionEvent e) 
	    {
	        return true;
	    }

	    public boolean onDoubleTap(MotionEvent e) 
	    {
	        Log.d("Double_Tap", "Yes, Clicked");
	        
	        int position = profile_grid.pointToPosition((int) e.getX(), (int) e.getY());
	        //int position = profile_grid.pointToPosition(e.getX(), e.getY());
	        
	        Toast.makeText(context, "You clicked on grid no. "+position, Toast.LENGTH_LONG).show();
	        return true;
	    }
	    */
		
		public GestureListener(View v) 
		{
            pos = (Integer) v.getTag();
        }
        
        @Override
        public boolean onDown(MotionEvent me) 
        {
            //this does get called but none of these methods below
            return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent me) 
        {/*
            new DeleteConfirmationPrompt(c, "board") {
                @Override
                protected boolean onDeleteConfirmed() {
                    // delete the visionboard
                    return deleteBoard(pos);
                }
            }; // Constructor shows dialog
            */
        	Toast.makeText(context, "You clicked on grid no. "+pos, Toast.LENGTH_LONG).show();
            return false;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) 
        {/*
            MainBoardGridAdapter.super.flagForUpdate(pos);
            if (listener != null) {
                listener.onBoardClick(pos, getName(pos));
            } else {
                Intent intent = new Intent(c, VisionBoardActivity.class);
                intent.putExtra(VisionBoardActivity.EXTRA_VISION_BOARD_NAME, getName(pos));
                frag.startActivityForResult(intent, MyBoardsFragment.REQUEST_EDIT);
            }
            */
            return false;
        }
	}
	
	
	private Handler handler = new Handler() 
	{
		public void handleMessage(Message msg) 
		{

			switch (msg.what)
			{
			/*
			case MSG_UPDATE_ADAPTER: // ListView updating

				//listview.invalidateViews();

				//Log.v(TAG, "In handler");

				break;
			case MSG_CHANGE_ITEM: // Do / not do case
				/*
				ToDoItem item = list.get(msg.arg1);
				item.setCheck(!item.isCheck());
				Utils.sorting(list, 0);
				saveList();
				adapter.notifyDataSetChanged();
				setCountPurchaseProduct();
				 ///
				break;	
				*/
			case MSG_ANIMATION_REMOVE: // Start animation removing
				View view = (View)msg.obj;
				view.startAnimation(getDeleteAnimation(0, (msg.arg2 == 0) ? -view.getWidth() : 2 * view.getWidth(), msg.arg1));
				break;

			}
		}
	};

	private Animation getDeleteAnimation(float fromX, float toX, int position)
	{
		Animation animation = new TranslateAnimation(fromX, toX, 0, 0);
		animation.setStartOffset(100);
		animation.setDuration(800);
		animation.setAnimationListener(new DeleteAnimationListenter(position));
		animation.setInterpolator(AnimationUtils.loadInterpolator(context,
				android.R.anim.anticipate_overshoot_interpolator));
		return animation;
	}

	/**
	 * Listenter used to remove an item after the animation has finished remove
	 */
	public class DeleteAnimationListenter implements Animation.AnimationListener
	{

		public DeleteAnimationListenter(int position)
		{
			//Log.v(TAG,"Animation position is "+position);

		}
		public void onAnimationEnd(Animation arg0) 
		{	

			//confirmDelete();
			/*//final int li_return = DeleteMaster(); only current line previous commented 
			//Log.v(TAG,"In Animation end "+ls_docType + "  Doc No "+li_docno);
			txnDetails.DeleteTxn(ls_docType, li_docno, true);
			handler.post(new Runnable() 
			{
				public void run() 
				{

					getData();

				}
			});
			 */

		}

		public void onAnimationRepeat(Animation animation) 
		{


		}

		public void onAnimationStart(Animation animation)
		{

		}	
	}



	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,
			long id) 
	{
		//GOOGLE ANALYTICS Event Tracking  - profile edit
        if(DbHelper.USE_ANALYTICS)
        {
            call_GoogleAnalytics_events("Profile", "Edit",
                    null);
        }
								
		//GOOGLE ANALYTICS Event Tracking  - profile edit
		

		int li_profileid = profile_list.get(position).getInt(Profile_Master.PROFILE_ID);
		
		Intent i = new Intent(this, Add_Profile.class);
        //Intent i = new Intent(this, test.class);
		i.putExtra(Grid_Adapter.PROFILE_OPERATION, "Edit");
		i.putExtra(Grid_Adapter.PROFILE_ID, li_profileid);
		startActivityForResult(i, REQ_CODE);
		
		//Log.v(TAG,"onLongclick has called");
		return true;
	}
	
	
	public void get_location(Context context)
	{
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		   
		  // boolean isGPSEnabled = locationManager
           //     .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
		   boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		 //  Log.v(TAG,"net :"+isNetworkEnabled+","+isGPSEnabled);
		   
		   
		  //Criteria criteria = new Criteria();
		   //String provider = locationManager.getBestProvider(criteria, true);
		   
		   //if(isNetworkEnabled == true)
		   //{
			  // locationManager.requestLocationUpdates(provider,
	           //        MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			   
			   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                       MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			   
			   if (locationManager != null)
	           {
				   //location = locationManager.getLastKnownLocation(provider);
				   
				   location = locationManager
                           .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				   
	               if (location != null) 
	               {
	                   latitude = location.getLatitude();
	                   longitude = location.getLongitude();
	                   gtask = new GetAddressTask(context);
	    			   gtask.execute(location);
	                   
	                   
	                   //Log.v(TAG,"Location : "+latitude + ","+longitude);
	               }
	           } 
		   //}
		   //else
		   //{
			   
				//Toast.makeText(this, "Please connect to working Internet connection!", Toast.LENGTH_SHORT).show();
				
		  // }
	           
		
	}
	

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


	private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null ||
                !Session.getActiveSession().isOpened()) {
            Session.openActiveSession(
                    this, 
                    true, 
                    PERMISSIONS,
                    new Session.StatusCallback() {
                        @SuppressWarnings("deprecation")
						@Override
                        public void call(Session session, SessionState state, Exception exception) {
                            //onSessionStateChanged(session, state, exception);
                        	
                        	
                        	if (state.isOpened()) 
    			            {
    			                //Log.v( TAG,"Facebook session opened2.");
    			                
    			             // Request user data and show the results
    			                Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
    			                {

    			                    @Override
    			                    public void onCompleted(GraphUser user, Response response) 
    			                    {
    			                        if (user != null) 
    			                        {
    			                            // Display the parsed user info
    			                            //tv_profile.setText(buildUserInfoDisplay(user));
    			                            
    			                            ///////
    			                            SharedPreferences.Editor editor = mPrefs.edit();
    				                        editor.putString("access_token",
    				                                facebook.getAccessToken());
    				                        editor.putLong("access_expires",
    				                                facebook.getAccessExpires());
    				                        editor.commit();
    			                            
    			                            ///////
    				                        
    				                        send_facebookData(user);
    			                        }
    			                    }
    			                });
    			            } 
    			            else if (state.isClosed()) 
    			            {
    			                //Log.v(TAG, "Facebook session closed2.");
    			            }
                        }
                    });
            return false;
        }
        return true;
    }
	
	
	
	
	
	private class GetAddressTask extends AsyncTask<Location, Void, String>
    {
        Context mContext;
        public GetAddressTask(Context context) 
        {
           super();
            //Log.v(TAG,"Context in getAdd :"+context);
           this.mContext = context;
        }

		
   
        protected void onPostExecute(String address) 
        {
           //Toast.makeText(mContext,"Address : "+address ,Toast.LENGTH_SHORT).show();
           //Log.v(TAG,"address is = "+address);
		   
        }
      
        protected String doInBackground(Location... params) 
        {

                Debugger.debug(TAG, "Context in getAdd :" + context);
                Geocoder geocoder =
                        new Geocoder(mContext, Locale.getDefault());

           
            Location loc = params[0];
            Double lati = loc.getLatitude();
            Double longi = loc.getLongitude();
            Debugger.debug(TAG,"Location for address :"+loc.getLatitude()+","+loc.getLongitude());
           
            List<Address> addresses = null;
           
           try
           {
              addresses = geocoder.getFromLocation(loc.getLatitude(),
              loc.getLongitude(), 1);
              
              
               	Calendar c = Calendar.getInstance();
      			int year = c.get(Calendar.YEAR);
      			int month = c.get(Calendar.MONTH);
      			int day = c.get(Calendar.DAY_OF_MONTH);
      			int hour = c.get(Calendar.HOUR_OF_DAY);
      			int min = c.get(Calendar.MINUTE);
      			
      			int m = month+1;
      			String timestamp = year+"-"+m+"-"+day+" "+hour+":"+min+":00";
      		
      		
           
	           if (addresses != null && addresses.size() > 0) 
	           {
                    try {
                        Address address = addresses.get(0);
                        Debugger.debug(TAG, "Address : " + address.toString());

                        Debugger.debug(TAG, "city : " + address.getLocality());
                        Debugger.debug(TAG, "country : " + address.getCountryName());
                        Debugger.debug(TAG, "postal  : " + address.getPostalCode());

                        String lat = Double.toString(address.getLatitude());
                        String lon = Double.toString(address.getLongitude());
                        String locale = (address.getLocale().toString());
                        String city = (address.getLocality());
                        String country = (address.getCountryName());
                        String postal = (address.getPostalCode());

                        String add1 = (address.getAddressLine(0));
                        String add2 = (address.getAddressLine(1));

                        if(locale == null || locale.equals(""))
                        {
                            locale = "";
                        }
                        if(city == null || city.equals(""))
                        {
                            city = "";
                        }
                        if(country == null || country.equals(""))
                        {
                            country = "";
                        }
                        if(postal == null || postal.equals(""))
                        {
                            postal = "";
                        }
                        if(add1 == null || add1.equals(""))
                        {
                            add1 = "";
                        }
                        if(add2 == null || add2.equals(""))
                        {
                            add2 = "";
                        }


                        ContentValues cv = new ContentValues();
                        cv.clear();

                        cv.put(Location_Master.TIMESTAMP, timestamp);
                        cv.put(Location_Master.ADDRESS_LINE1, add1);
                        cv.put(Location_Master.ADDRESS_LINE2, add2);
                        cv.put(Location_Master.CITY, city);
                        cv.put(Location_Master.COUNTRY, country);
                        cv.put(Location_Master.PINCODE, postal);
                        cv.put(Location_Master.LATITUDE, lat);
                        cv.put(Location_Master.LONGITUDE, lon);

                        ////
                        loc_master.insert(cv);
                        ///

                        Task_insert_location_details task = new Task_insert_location_details(timestamp, add1, add2, city, country,
                                postal, lat, lon);
                        task.execute();
                        //
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
	              return "";
	           } 
           }
           catch(IOException e1)
           {
        	   
           }
		return FILENAME;
        }
    }
	
	
	
	
	
	private final class Task_insert_location_details extends AsyncTask<String, String, String>
	{
		JSONParser jsonParser;
		JSONObject jo_resp;
		
		
		ArrayList<NameValuePair> list_params;

		public Task_insert_location_details(String timestamp, String add1, String add2, String city, String country,
				String postal, String lat, String lon)
		{			
			list_params = new ArrayList<NameValuePair>();

			list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, DeviceUtils.getImei(context)));
			list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, DeviceUtils.getSerialId()));
			
			list_params.add(new BasicNameValuePair(Location_Master.TIMESTAMP, timestamp));
			list_params.add(new BasicNameValuePair(Location_Master.ADDRESS_LINE1, add1));
			list_params.add(new BasicNameValuePair(Location_Master.ADDRESS_LINE2, add2));
			list_params.add(new BasicNameValuePair(Location_Master.CITY, city));
			list_params.add(new BasicNameValuePair(Location_Master.COUNTRY, country));
			list_params.add(new BasicNameValuePair(Location_Master.PINCODE, postal));
			list_params.add(new BasicNameValuePair(Location_Master.LATITUDE, lat));
			list_params.add(new BasicNameValuePair(Location_Master.LONGITUDE, lon));
			
		
			//Log.v(TAG,"list_params :  : " + list_params.toString());
			
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
			String ls_url = URL_INSERT_LOCATION_DETAILS; 

			jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
            Debugger.debug(TAG,"Response from server...... : " + String.valueOf(jo_resp));

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
	
	
	
	
	
	public void server_verification()
	{
		if(InternetUtils.isConnected(context))
		{
			Task_server_verification task = new Task_server_verification();
			task.execute();
		}
	}
	
	
	
	
	private final class Task_server_verification extends AsyncTask<String, String, String>
	{
		JSONParser jsonParser;
		JSONObject jo_resp;
		
		JSONArray ja_allProds = null;
		
		
		
		ArrayList<NameValuePair> list_params;

		public Task_server_verification()
		{			
			list_params = new ArrayList<NameValuePair>();

			list_params.add(new BasicNameValuePair(Personal_Details.IMEI_ID, DeviceUtils.getImei(context)));
			list_params.add(new BasicNameValuePair(Personal_Details.SERIAL_NO, DeviceUtils.getSerialId()));
			
			
			try 
			{
				ja_allProds = getPendingProfileDetails();
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
			list_params.add(new BasicNameValuePair("pending_profile_details", ja_allProds.toString()));


            Debugger.debug(TAG,"list_params :  : " + list_params.toString());
			
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
			String ls_url = URL_SERVER_VERIFICATION; 

			jo_resp = jsonParser.makeHttpRequest(ls_url, "POST", list_params);
            Debugger.debug(TAG,"Response from server...... : " + String.valueOf(jo_resp));

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
							if(jo_resp.has("verify_result"))
							{
								JSONArray jSon = jo_resp.getJSONArray("verify_result");
								
								for (int i =0;i < jSon.length();i++)
								{
									boolean is_verify = jSon.getJSONObject(i).getBoolean("is_verify");
									
									if(is_verify == true)
									{
										int server_id  = jSon.getJSONObject(i).getInt("server_profile_id");
										int profile_id  = jSon.getJSONObject(i).getInt("profile_id");
										String server_status = jSon.getJSONObject(i).getString("server_status");
										
										profile_master.change_status(server_id, profile_id);
									}
									
								}
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
	
	
	
	public JSONArray getPendingProfileDetails() throws JSONException
	{
		ArrayList<Bundle> list_bundle = profile_master.getAll_pendingList();

		Bundle bundle = null;

		JSONArray ja_allProds = new JSONArray();

		
		JSONObject jsonObject;
		
		for(int i=0 ; i<list_bundle.size() ; i++)
		{
			jsonObject = new JSONObject();

			bundle = new Bundle(list_bundle.get(i));
			
			jsonObject.put(Profile_Master.PROFILE_ID, bundle.getInt(Profile_Master.PROFILE_ID));
			jsonObject.put(Profile_Master.FIRST_NAME, bundle.getString(Profile_Master.FIRST_NAME));
			jsonObject.put(Profile_Master.MIDDLE_NAME, bundle.getString(Profile_Master.MIDDLE_NAME));
			jsonObject.put(Profile_Master.LAST_NAME, bundle.getString(Profile_Master.LAST_NAME));
			jsonObject.put(Profile_Master.DOB, bundle.getString(Profile_Master.DOB));
			jsonObject.put(Profile_Master.GENDER, bundle.getString(Profile_Master.GENDER));
			jsonObject.put(Profile_Master.RELATION, bundle.getString(Profile_Master.RELATION));
			jsonObject.put(Profile_Master.BLOODGROUP, bundle.getString(Profile_Master.BLOODGROUP));
			jsonObject.put(Profile_Master.HEIGHT, bundle.getString(Profile_Master.HEIGHT));
			jsonObject.put(Profile_Master.MOBILE_NO, bundle.getString(Profile_Master.MOBILE_NO));
			jsonObject.put(Profile_Master.EMAIL_ID, bundle.getString(Profile_Master.EMAIL_ID));
			jsonObject.put(Profile_Master.CITY, bundle.getString(Profile_Master.CITY));
			jsonObject.put(Profile_Master.COUNTRY, bundle.getString(Profile_Master.COUNTRY));
			jsonObject.put(Profile_Master.PINCODE, bundle.getString(Profile_Master.PINCODE));
			jsonObject.put(Profile_Master.STATUS, bundle.getString(Profile_Master.STATUS));
			jsonObject.put(Profile_Master.SERVER_STATUS, bundle.getString(Profile_Master.SERVER_STATUS));
			jsonObject.put(Profile_Master.SERVER_PROFILE_ID, bundle.getInt(Profile_Master.SERVER_PROFILE_ID));
			
			ja_allProds.put(jsonObject);
		}

		return ja_allProds;
	}
	
	/*
	@SuppressWarnings("deprecation")
	public void postOnWall(String msg) 
	{
        Log.v("Tests", "Testing graph API wall post");
         try 
         {
                String response = facebook.request("me");
                Bundle parameters = new Bundle();
                parameters.putString("message", msg);
                parameters.putString("description", "Testing wall pos");
                response = facebook.request("me/feed", parameters, 
                        "POST");
                Log.d("Tests", "got response: " + response);
                if (response == null || response.equals("") || 
                        response.equals("false")) 
                {
                   Log.v("Error", "Blank response");
                }
         } 
         catch(Exception e) 
         {
             e.printStackTrace();
         }
	}
	
	
	
*/
    
	
	public void call_GoogleAnalytics_screens()
	{
		//GOOGLE aNALYTICS
		Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
		
		t.enableAdvertisingIdCollection(true);
		
		t.setScreenName("New_Grid");
		t.send(new HitBuilders.ScreenViewBuilder().build());
		//GOOGLE aNALYTICS
	}
	
	public void call_GoogleAnalytics_sessions()
	{
		//GOOGLE ANALYTICS
		Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
		
		t.enableAdvertisingIdCollection(true);
		
		t.setScreenName("New_Grid");
		t.send(new HitBuilders.ScreenViewBuilder().build());
		
		// Start a new session with the hit.
		t.send(new HitBuilders.AppViewBuilder().setNewSession().build());
		//GOOGLE ANALYTICS
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
	
	public void displayInterstitial() 
	{
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) 
		{
		interstitial.show();
		}
	}
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
	public void onBackPressed() 
	{
        Intent i = new Intent(New_Grid.this, Custom_keyboard.class);
        startActivity(i);

        finish();
	}
	
	
	public void set_toastCount()
	{
		boolean lb_showToast = false;
		toast_sp1 = context.getSharedPreferences("TOAST1",Context.MODE_PRIVATE);
		sp_toastEditor1 = toast_sp1.edit();

		int  li_showNoTimes = 0;
		
		
		li_showNoTimes = toast_sp1.getInt("TOAST_COUNT_GRID",0);
		li_showNoTimes = li_showNoTimes + 1;
			
		sp_toastEditor1.putInt("TOAST_COUNT_GRID",li_showNoTimes);


        Debugger.debug(TAG,"li_showNoTimes list " + li_showNoTimes);
				

		if(li_showNoTimes >= MAX_SCREENCOUNT)
		{
			lb_showToast = false;
		}
		else
		{
			lb_showToast = true;

		}
		Debugger.debug(TAG,"No.of times clicked  " + li_showNoTimes);
        Debugger.debug(TAG,"lb_showToast is " + lb_showToast);

		sp_toastEditor1.putBoolean("SHOW_TOAST_GRID",lb_showToast);

		sp_toastEditor1.commit();
	}




    public String getScreenResolution(Context context)
    {
        String Screen_size;
       // DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //int li_density = metrics.densityDpi;


        if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            //Toast.makeText(context, "Large screen + " +li_density,Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Large screen"+density); // samsung tab
            Screen_size = "LARGE";

        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
           // lb_normalScreen = true;
            //Toast.makeText(context, "Normal sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Normal sized screen"+density);// grand2, onePlus,
            Screen_size = "NORMAL";
        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            //Toast.makeText(context, "Small sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Small sized screen"+density);
            Screen_size = "SMALL";
        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            //Toast.makeText(context, "X-large sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran X-large sized screen"+density);//nexus 10
            Screen_size = "X-LARGE";
        }
        else
        {
            Debugger.debug(TAG,"Screen size is neither large, normal or small " +density);
            //Toast.makeText(context, "Imran Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
            Screen_size = "NO";
        }
        return Screen_size;
    }


    public void getscreenCount()
    {
        boolean lb_showDialog = false;
        screenCount_sp = context.getSharedPreferences("SCREEN_COUNT",Context.MODE_PRIVATE);
        sp_screenEditor = screenCount_sp.edit();

        int  li_showNoTimes = 0;

        li_showNoTimes = screenCount_sp.getInt("SCREEN",0);
        li_showNoTimes = li_showNoTimes + 1;
        sp_screenEditor.putInt("SCREEN",li_showNoTimes);


        Debugger.debug(TAG,"Screen name is " + li_showNoTimes);



         if(li_showNoTimes > MAX_SCREENCOUNT)
         {
             lb_showDialog = true;
             //li_showNoTimes = 0;
             li_showNoTimes = 1;
             sp_screenEditor.putInt("SCREEN",li_showNoTimes);

             Debugger.debug(TAG,"showDialog1 is " + lb_showDialog);


         }
         else
         {
             lb_showDialog = false;

         }
        Debugger.debug(TAG,"No.of times clicked123  " + li_showNoTimes);
        Debugger.debug(TAG,"showDialog3 is " + lb_showDialog);

        sp_screenEditor.putBoolean("SHOW_DIALOG",lb_showDialog);

        sp_screenEditor.commit();

        show_share_RateDailog();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void show_share_RateDailog()
    {
        boolean is_share, lb_showDialog;
        int screen_count = 0;
        rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
        //sp_rateEditor = rate_sp.edit();

        is_share =  rate_sp.getBoolean("RATE",false);
        Debugger.debug(TAG,"is_share is " + is_share);

        screenCount_sp = context.getSharedPreferences("SCREEN_COUNT",Context.MODE_PRIVATE);


        //lb_showDialog = screenCount_sp.getBoolean("SHOW_DIALOG", false);


        screen_count = screenCount_sp.getInt("SCREEN",0);
        Debugger.debug(TAG,"screen_count is " + screen_count);

        //if(lb_showDialog)
        if(screen_count == MAX_SCREENCOUNT)
        {
            Debugger.debug(TAG,"in if screen_count is " + screen_count);
            if(!(is_share))
            {
                Debugger.debug(TAG,"is_share is " + is_share);
                confirmDialog = new ConfirmDialog(context, "", 0, "rate", "tabClicked");
                confirmDialog.show(getFragmentManager(), TAG);
            }
        }
    }


    public void shareApp(Context context)
    {
        rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
        sp_rateEditor = rate_sp.edit();

        sp_rateEditor.putBoolean("RATE",true);
        sp_rateEditor.commit();

        Intent i=new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");

        //i.putExtra(android.content.Intent.EXTRA_SUBJECT,"mAccounts : Mobile Accounting App for Android");
        i.putExtra(android.content.Intent.EXTRA_TEXT, SHARETEXT);


        startActivity(Intent.createChooser(i,MeasurementList_Profile.ls_share));
    }

    public void rateApp(Context context)
    {
        rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
        sp_rateEditor = rate_sp.edit();

        sp_rateEditor.putBoolean("RATE",true);
        sp_rateEditor.commit();

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.cresco.HealthMate")));
    }

}
