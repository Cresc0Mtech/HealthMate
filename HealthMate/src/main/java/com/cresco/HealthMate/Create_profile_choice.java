package com.cresco.HealthMate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.cresco.HealthMate.R;
import com.facebook.widget.LoginButton;

public class Create_profile_choice extends Activity implements OnClickListener

{
	Context context;
	String TAG = this.getClass().getSimpleName();
	
	CrescoButton bt_addProfile;
	private LoginButton bt_facebook_login;
	
	final static int REQ_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.createprofile_choice);
		context = this;
		
		
		bt_addProfile = (CrescoButton)findViewById(R.id.bt_addProfile);
		bt_facebook_login = (LoginButton) findViewById(R.id.bt_facebook_login);
		
		bt_addProfile.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) 
	{

		switch(v.getId())
		{
			case R.id.bt_facebook_login : 
				
				//loginToFacebook();
				break;
				
			case R.id.bt_addProfile : 
				
				createprofile();
				break;
		}
	}
	
	
	public void createprofile()
	{
		Intent i = new Intent(this, Add_Profile.class);
		//startActivity(i);
		
		startActivityForResult(i, REQ_CODE);
		
		//finish();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		// TODO Auto-generated method stub

		if(requestCode == REQ_CODE)
		{

			if (resultCode == RESULT_OK)
			{
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				finish();
			}
		}
	}
}
