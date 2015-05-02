package com.cresco.HealthMate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cresco.HealthMate.R;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class TitleBar_Activity extends Activity implements OnClickListener
{
	Context context;
	public static String TAG = TitleBar_Activity.class.getSimpleName();
	
	LinearLayout ll_linBase, ll_profileheader, ll_profilename, ll_adMob, ll_delete;
	protected ImageView iv_appicon, iv_profileimage;
	
	protected CrescoTextView tv_profilename;
    CrescoTextView tv_delete;
	AdView adView;
	
	/*
	@Override
	protected void onStart()
	{
		super.onStart();

		// <GOOGLE_ANALYTICS>
		if(DbHelper.USE_ANALYTICS)
		{
		EasyTracker.getInstance(this).activityStart(this);
		}
		// </GOOGLE_ANALYTICS>
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// <GOOGLE_ANALYTICS>
		if(DbHelper.USE_ANALYTICS)
		{
		EasyTracker.getInstance(this).activityStop(this);
		}
		// <GOOGLE_ANALYTICS>
	}

	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.setContentView(R.layout.titlebar_activity);
		
		
	/*	String dev_id = DeviceUtils.getImei(context);

        if(DbHelper.USE_ADD)
        {
            adView = (AdView) this.findViewById(R.id.adMob1);
            //request TEST ads to avoid being disabled for clicking your own ads
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                    //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
                    .addTestDevice(dev_id) // Nexus 5
                    .build();
            //"356262052582091"
            adView.loadAd(adRequest);
        }
        */
        
        ll_adMob = (LinearLayout) findViewById(R.id.ll_adMob);
		
		ll_linBase = (LinearLayout) findViewById(R.id.ll_linBase);
		
		ll_profileheader = (LinearLayout) findViewById(R.id.ll_profileheader);
		ll_profilename = (LinearLayout) findViewById(R.id.ll_profilename);

        ll_delete =  (LinearLayout) findViewById(R.id.ll_delete);
		
		iv_appicon = (ImageView) findViewById(R.id.iv_appicon);
		iv_profileimage = (ImageView) findViewById(R.id.iv_profileimage);
		tv_profilename = (CrescoTextView) findViewById(R.id.tv_profilename);
		tv_delete =  (CrescoTextView) findViewById(R.id.tv_delete);
		
		iv_appicon.setOnClickListener(this);
		iv_profileimage.setOnClickListener(this);
		ll_profilename.setOnClickListener(this);
        ll_delete.setOnClickListener(this);


        ///////////
       // setHeight_to_layout();

        //////////
		
	}
	
	@Override
	public void setContentView(int id) 
	{
		LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(id, ll_linBase);
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.iv_appicon:
			{
				break;
			}
			
			case R.id.iv_profileimage:
			{
				
				//exportAsPDF();
				//Task_createPDF proAsync = new Task_createPDF();
				//proAsync.execute(1);
				
				break;
			}
			
			
		}
	}




    public void customToastMain()
    {
        final SuperCardToast superCardToast = new SuperCardToast(this, SuperToast.Type.STANDARD);
        superCardToast.setDuration(SuperToast.Duration.EXTRA_LONG);


        superCardToast.setText("Swipe across a Reading to delete it");

        //superCardToast.setButtonIcon(SuperToast.Icon.Dark.EDIT, "EDIT");
        //superCardToast.setOnClickWrapper(onClickWrapper);
        superCardToast.setBackground(R.color.cresco_gray);
        superCardToast.setTextColor(context.getResources().getColor(R.color.Gray));
        //superCardToast.setTextSize(15);
        superCardToast.setTypefaceStyle(0);
        superCardToast.setSwipeToDismiss(true);
        superCardToast.show();
    }



    public void setHeight_to_layout()
    {
        //** get display screen width and height
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //getWindowManager().getD

        float density  = getResources().getDisplayMetrics().density;
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        //Log.v(TAG, " width in pixel: " + width);
        //Log.v(TAG," height in pixel  : "+height);

        float ht = dm.heightPixels/density;
        //Log.v(TAG,"density : "+density);
        //Log.v(TAG,"Hight in DP : "+ht);


        ViewGroup.LayoutParams layoutParams = ll_profileheader.getLayoutParams();
        ViewGroup.LayoutParams layoutParams1 = ll_linBase.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = ll_adMob.getLayoutParams();

        int d;

        if(ht >= 400 && ht<= 720)
        {
            //Log.v(TAG,"ht dp1:"+ht);
            d = (int) (50*1.23*density);
            //height = height- d;
            //Log.v(TAG,"height screen : "+height);
            layoutParams.height = d;
            layoutParams2.height = d;
            ll_profileheader.setLayoutParams(layoutParams);
            ll_adMob.setLayoutParams(layoutParams2);
        }
        else
        {
            //Log.v(TAG,"ht dp2:"+ht);
            d = (int) (90*1.23*density);
           // height = height- d;

            //Log.v(TAG,"height screen : "+height);
            layoutParams.height = d;
            layoutParams2.height = d;
            ll_profileheader.setLayoutParams(layoutParams);
            ll_adMob.setLayoutParams(layoutParams2);
        }
        //Log.v(TAG,"AD height in pixel :"+d);

        int s_h = height- (d*2);

        layoutParams1.height = s_h;
        ll_linBase.setLayoutParams(layoutParams1);
        //getResources().get
    }
	
}
