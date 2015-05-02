package com.cresco.HealthMate;

/**
 * Created by comp1 on 06/04/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class Welcome_Note extends Activity //implements OnTouchListener
{

    private static final int SPLASH_SHOW_TIME = 5000;

    boolean isClicked = false;
    private Thread mSplashThread;
    private Handler handler;

    CrescoTextView tv_note1, tv_note2;
    LinearLayout ll_note;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_note);

        tv_note1 = (CrescoTextView)findViewById(R.id.tv_note1);

        tv_note2 =  (CrescoTextView)findViewById(R.id.tv_note2);

        ll_note  =  (LinearLayout)findViewById(R.id.ll_note);



        /*
        Welcome to HealthMate!
               We have created two HealthMate profiles to provide a basic demo of the app.
        You can take a tour of the app by selecing any of the demo profiles & exploring further or you can directly get started by creating new HealthMate profiles for you & your family members.               Wishing you and your family good health!
                  */

        tv_note1.setText("Welcome to HealthMate!");

        String note = "\nWe have created two HealthMate profiles to provide you a basic demo of the app." +
                      "\n\nYou can take a tour of the app by selecting any of the demo profiles & exploring further\nor\nyou can " +
                      "directly get started by creating new HealthMate profiles for you & your family members.\n\n"+
                      "Wishing you & your family good health!";

        tv_note2.setText(note);
        tv_note2.setLineSpacing(20, 1);

         /*
        handler = new Handler()
        		 {

        		 };

        mSplashThread =  new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(10000);
                	/*if(isClicked == false)
        			{
                		Intent i = new Intent(SplashActivity.this,
    						ChatActivity.class);
                		i.putExtra("splash", "splash ");

                		startActivity(i);

        			}*/  /*
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                }


            }
        };

        mSplashThread.start();*/



        ll_note.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //handler.removeCallbacks(mSplashThread);
                finish();
                return true;
            }
        });

    }







    private class BackgroundSplashTask extends AsyncTask <Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            // I have just given a sleep for this thread
            // if you want to load database, make
            // network calls, load images
            // you can do them here and remove the following
            // sleep

            // do not worry about this Thread.sleep
            // this is an async task, it will not disrupt the UI
            try
            {
                Thread.sleep(SPLASH_SHOW_TIME
                );
            } catch (InterruptedException e)
             {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            //Log.v("kkk","isclicked 1"+isClicked);
            if(isClicked == false)
            {

               // Intent i = new Intent(SplashActivity.this,
                //        ChatActivity.class);

                //startActivity(i);
            }


            finish();

        }



    }
     /*
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub


        switch (v.getId())
        {
            case R.id.bt_splash:
            {
				/*Intent i = new Intent(SplashActivity.this,
						ChatActivity.class);
				i.putExtra("splash", "splash ");
				startActivity(i);
				isClicked = true;*//*

                handler.removeCallbacks(mSplashThread);

                finish();

                break;
            }
        }

    }
                */

}
