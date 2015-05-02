package com.cresco.HealthMate;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.cresco.HealthMate.ConfirmDialog.NoticeDialogListener;

import android.view.View.OnClickListener;

public class Custom_keyboard extends Activity implements OnClickListener, NoticeDialogListener

{
	Context context;
	private InterstitialAd interstitial;

    String TAG = this.getClass().getSimpleName();

    ConfirmDialog confirmDialog;
	
	@Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_keyboard);
		context = this;
		
		///////////
		// Prepare the Interstitial Ad
        if(DbHelper.USE_ADD)
        {
            interstitial = new InterstitialAd(Custom_keyboard.this);
            // Insert the Ad Unit ID
            interstitial.setAdUnitId("ca-app-pub-5105540963215368/3939747937");

            String dev_id = DeviceUtils.getImei(context);

            //C6FEF39EC34534918DC965D39C223421
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                            // test mode on DEVICE (this example code must be replaced with your device uniquq ID)
                    .addTestDevice(dev_id) // Nexus 5
                    .build();


            interstitial.loadAd(adRequest);




            // Prepare an Interstitial Ad Listener
            interstitial.setAdListener(new AdListener()
            {
                public void onAdLoaded()
                {
                    // Call displayInterstitial() function
                    displayInterstitial();
                }

                @Override
                public void onAdClosed()
                {
                    //requestNewInterstitial();

                   //finish();
                }
            });

        }

        confirmDialog = new ConfirmDialog(context, "EXIT", 0, "delete", "");
        confirmDialog.show(getFragmentManager(), TAG);
	}
	
	
	public void displayInterstitial() 
	{
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) 
		{
		interstitial.show();
		}
	}
	
	private void requestNewInterstitial() 
	{
		String dev_id = DeviceUtils.getImei(context);
		
		//C6FEF39EC34534918DC965D39C223421
		 AdRequest adRequest = new AdRequest.Builder()
         .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
         // test mode on DEVICE (this example code must be replaced with your device uniquq ID)
         .addTestDevice(dev_id) // Nexus 5
         .build();
        interstitial.loadAd(adRequest);
    }



   @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Custom_keyboard.this, Exit_Ad.class);
        startActivity(i);
        finish();
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.cb_yes:
            {
                confirmDialog.dismiss();

                Intent i = new Intent(Custom_keyboard.this, Exit_Ad.class);
                startActivity(i);
                finish();
                break;
            }

            case R.id.cb_no:
            {
                confirmDialog.dismiss();
                Intent i = new Intent(Custom_keyboard.this, New_Grid.class);
                startActivity(i);
                finish();
                break;
            }
        }
    }
}
