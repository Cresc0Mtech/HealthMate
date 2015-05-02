package com.cresco.HealthMate;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Switch_Profile extends Activity implements OnItemClickListener
{
	Context context;
	String TAG = this.getClass().getSimpleName();
	
	ListView lv_switch;
	ArrayList<Bundle> profile;
	Profile_Master profile_master;
	
	Profile_Adapter pAdater;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.switch_profile);
		
		lv_switch = (ListView) findViewById(R.id.lv_switch);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //getWindowManager().getD

        float density  = getResources().getDisplayMetrics().density;
		
		profile_master = new Profile_Master(context);
		profile = new ArrayList<Bundle>();
		 
		profile = new ArrayList<Bundle>();
		profile = profile_master.getAllProfileList();
		
		pAdater = new Profile_Adapter(this, profile, density);
		
		lv_switch.setAdapter(pAdater);
		
		lv_switch.setOnItemClickListener(this);
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
	{
		int li_profileId = 0;
		
		
		li_profileId = profile.get(position).getInt(Profile_Master.PROFILE_ID);
		
		Intent i = new Intent(this, MeasurementList_Profile.class);
		i.putExtra("ProfileId", li_profileId);
		
		startActivity(i);
		
		finish();
	}
}
