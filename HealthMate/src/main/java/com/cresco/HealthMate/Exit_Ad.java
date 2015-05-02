package com.cresco.HealthMate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by comp1 on 09/04/2015.
 */
public class Exit_Ad extends Activity
{

    Context context;
    private InterstitialAd interstitial;

    String TAG = this.getClass().getSimpleName();

    @Override
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
            interstitial = new InterstitialAd(Exit_Ad.this);
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
                    finish();
                }
            });

        }
    }


    public void displayInterstitial()
    {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded())
        {
            interstitial.show();
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }


}
