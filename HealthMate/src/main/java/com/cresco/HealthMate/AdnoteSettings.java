package com.cresco.HealthMate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;


public class AdnoteSettings extends TitleBar_Activity implements OnItemClickListener
{
	private final static String TAG = AdnoteSettings.class.getSimpleName();

	private ArrayList<HashMap<String, Object>> list_hashmap;

	private Context context;

	LayoutInflater layoutInflator;
	ListView lv_settings;

	private static String TAG_SETTING_NAME = "setting_name";

	private String[] ls_mapping;
	private int[] li_to;
	boolean lb_normalScreen = false;

	MySimpleAdapter adapter;

	int li_density;
	LayoutParams ll_params;

	// Client Details
	public static final String CLIENT_NAME = "EDGE ACADEMY";
	public static final String CLIENT_WEB_URL = "http://www.edgeacademy.in";
	// End Client Details

	MeasurementList_Profile measure;
	
	SharedPreferences recordCount_sp, rate_sp;
	SharedPreferences.Editor sp_recordEditor, sp_rateEditor;
	static String appName = "https://play.google.com/store/apps/details?id=com.cresco.HealthMate";
	//static String ls_share = "Share mAccounts via";
//	static String SHARETEXT = " Hey! I am using mAccounts now. It's a great mobile accounting app for Android."+
//			" I am managing my Accounts & Inventory on it and I am sure you too are going to love it. "+
//			" Download mAccounts from "+appName;

    //static String SHARETEXT = " Hey! I am maintaining my health records in HealthMate. It's simple, elegant & immensely useful " +
    //        "to maintain blood pressure & blood sugar readings of all my family members in a single app! Download " +
    //        "HealthMate from Play Store now. "+appName;
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;

		setContentView(R.layout.adnote_settings);

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



		measure = new MeasurementList_Profile();
		
		super.ll_profileheader.setBackgroundColor(getResources().getColor(R.color.healthmate_green));
		super.iv_appicon.setBackgroundResource(R.drawable.hm_logo_white);
		super.iv_profileimage.setVisibility(View.GONE);
        super.tv_profilename.setText("HealthMate");

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		li_density = metrics.densityDpi;


		if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
			//Toast.makeText(context, "Large screen + " +li_density,Toast.LENGTH_LONG).show();
			//Log.v(TAG, "Imran Large screen"); // samsung tab

		}
		else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) 
		{  
			lb_normalScreen = true;
			//Toast.makeText(context, "Normal sized screen "+li_density , Toast.LENGTH_LONG).show();
            //Log.v(TAG, "Imran Normal sized screen");// grand2
		} 
		else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
			//Toast.makeText(context, "Small sized screen "+li_density , Toast.LENGTH_LONG).show();
            //Log.v(TAG, "Imran Small sized screen");
		}
		else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) 
		{     
			//Toast.makeText(context, "X-large sized screen "+li_density , Toast.LENGTH_LONG).show();
            //Log.v(TAG, "Imran X-large sized screen");//nexus 10
		}
		else 
		{
            //Log.v(TAG,"Screen size is neither large, normal or small " +li_density);
			//Toast.makeText(context, "Imran Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
		}

		setSettingsMenu();

		layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ls_mapping = new String[] {TAG_SETTING_NAME};
		li_to = new int[] {R.id.tv_setting};

		adapter = new MySimpleAdapter(context, list_hashmap, R.layout.settings_main_row, ls_mapping, li_to);

		lv_settings = (ListView) findViewById(R.id.lv_settings);
		lv_settings.setAdapter(adapter);

		lv_settings.setOnItemClickListener(this);
		
		
	}

	private void setSettingsMenu()
	{
		this.list_hashmap = new ArrayList<HashMap<String,Object>>();

		HashMap<String, Object> hashmap;

		hashmap = new HashMap<String, Object>();

		hashmap.put(TAG_SETTING_NAME, "About");
		this.list_hashmap.add(hashmap);
		
		hashmap = new HashMap<String, Object>();

		hashmap.put(TAG_SETTING_NAME, "Share");
		this.list_hashmap.add(hashmap);

		hashmap = new HashMap<String, Object>();

		hashmap.put(TAG_SETTING_NAME, "Rate");
		this.list_hashmap.add(hashmap);

        hashmap = new HashMap<String, Object>();

        hashmap.put(TAG_SETTING_NAME, "Licenses");
        this.list_hashmap.add(hashmap);
	}

	private class MySimpleAdapter extends SimpleAdapter
	{
		ViewHolder viewHolder;
		Drawable share, rate, about, licences;

		public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		{
			super(context, data, resource, from, to);
			
			about = getResources().getDrawable( R.drawable.about);
			share = getResources().getDrawable( R.drawable.share);
			rate = getResources().getDrawable( R.drawable.rate);
            licences = getResources().getDrawable( R.drawable.licences);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{			
			if(convertView == null)
			{
				convertView = layoutInflator.inflate(R.layout.settings_main_row, null);

				viewHolder = new ViewHolder();
				viewHolder.tv_setting 	= (CrescoTextView) convertView.findViewById(R.id.tv_setting);
				viewHolder.ll_row 	= (LinearLayout) convertView.findViewById(R.id.ll_row);
			}

			HashMap<String, Object> hashmap = list_hashmap.get(position);
			Debugger.debug(TAG, "___HASHMAP::::" + hashmap.toString());
			
			if(hashmap.get("setting_name").toString().equals("About"))
			{
				viewHolder.tv_setting.setCompoundDrawablesWithIntrinsicBounds(about, null, null, null);

			}
			else if(hashmap.get("setting_name").toString().equals("Share"))
			{
				viewHolder.tv_setting.setCompoundDrawablesWithIntrinsicBounds(share, null, null, null);
			}
			else if(hashmap.get("setting_name").toString().equals("Rate"))
			{
				viewHolder.tv_setting.setCompoundDrawablesWithIntrinsicBounds(rate, null, null, null);

			}
            else if(hashmap.get("setting_name").toString().equals("Licenses"))
            {
                viewHolder.tv_setting.setCompoundDrawablesWithIntrinsicBounds(licences, null, null, null);
            }
			
			viewHolder.tv_setting.setText(hashmap.get("setting_name").toString());
            viewHolder.tv_setting.setCompoundDrawablePadding(10);
			convertView.setTag(viewHolder);

			
			return convertView;
		}
	};

	private class ViewHolder
	{
		public CrescoTextView tv_setting;
		LinearLayout ll_row;
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int id, long position)
	{
		HashMap<String, Object> hashmap = this.list_hashmap.get((int)position);

		Debugger.debug(TAG, "Selected hashmap: " + hashmap.toString());

		String ls_text = hashmap.get(TAG_SETTING_NAME).toString();

		if(ls_text.equalsIgnoreCase("About"))
		{
			Intent intent = new Intent(this, Help_Activity.class);
			startActivity(intent);
		}
		else if(ls_text.equalsIgnoreCase("Share"))
		{
			shareApp();
		}
		else if(ls_text.equalsIgnoreCase("Rate"))
		{
			rateApp();
		}
        else if(ls_text.equalsIgnoreCase("Licenses"))
        {
            Intent intent = new Intent(this, License_Activity.class);
            startActivity(intent);
        }


        lv_settings.invalidateViews();
	}
	
	
	public void shareApp()
	{
		rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
		sp_rateEditor = rate_sp.edit();
		
		sp_rateEditor.putBoolean("RATE",true);
		sp_rateEditor.commit();

		Intent i=new Intent(android.content.Intent.ACTION_SEND);
		i.setType("text/plain");

		//i.putExtra(android.content.Intent.EXTRA_SUBJECT,"mAccounts : Mobile Accounting App for Android");
		i.putExtra(android.content.Intent.EXTRA_TEXT, MeasurementList_Profile.SHARETEXT);


		startActivity(Intent.createChooser(i,MeasurementList_Profile.ls_share));
	}
	
	public void rateApp()
	{
		rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
		sp_rateEditor = rate_sp.edit();
		
		sp_rateEditor.putBoolean("RATE",true);
		sp_rateEditor.commit();
		
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
	}

	
/*	@Override
	protected void onResume() 
	{
	
		// TODO Auto-generated method stub
		super.onResume();
		setSettingsMenu();
		Toast.makeText(context,"On resume",Toast.LENGTH_SHORT).show();
		
	}*/
	
	
}
