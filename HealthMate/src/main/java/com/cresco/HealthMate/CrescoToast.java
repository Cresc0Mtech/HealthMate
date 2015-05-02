package com.cresco.HealthMate;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



public final class CrescoToast 
{
	
	static Toast toast;
	 
	private static Handler handler = new Handler();
	 
	 public static void showToast(final Activity tActivity,final String toastText,final int duration)
	 {
		   handler.post(new Runnable()
		   {
		      public void run() 
		      {
		    	  LayoutInflater inflater = tActivity.getLayoutInflater();
		    	  View layout = inflater.inflate(R.layout.cresco_toast, (ViewGroup)tActivity.findViewById(R.id.toast_layout_root));
		    	  TextView tv_toast = (TextView) layout.findViewById(R.id.textView1);
		    	  toast = new Toast(tActivity.getApplicationContext());
		    	  toast.setGravity(Gravity.CENTER, 0, 0);
		    	  toast.setDuration(duration);
		    	  toast.setView(layout);
		    	  tv_toast.setText(toastText);
		    	  toast.show();
		      }
		   });
		}
	 

}