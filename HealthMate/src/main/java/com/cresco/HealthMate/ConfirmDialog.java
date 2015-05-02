package com.cresco.HealthMate;

import java.util.ArrayList;
import com.cresco.HealthMate.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TableRow;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "ValidFragment", "NewApi" })
public class ConfirmDialog extends DialogFragment implements OnTouchListener
{
	NoticeDialogListener mListener;
	Context  context;
	Dialog deleteDialog;
	Button cb_pdf, cb_csv, cb_open, cb_share, cb_no, cb_yes, cb_rate, cb_shareApp;
	String ls_className ="";
	TableRow tr_header;
	String ls_msgToShow = null;

	String TAG= "ConfirmDialog";	
	String ls_for = "";
	String path;
	MeasurementList_Profile measure;
	BloodPressure_Master bp_master;
	Sugar_Master sugar_master;
	

	CrescoTextView tv_dialogTitle,tv_dialogBody, tv_dialogBody1, tv_dialogBody2;
	CrescoEditText et_from, et_to;

	Typeface typeface;
	int profile_id;
	String param;
	String tabClicked;
	

	public ConfirmDialog(Context context) 
	{
		this.context = context;
	}

	public ConfirmDialog(Context context, String path, int profile_id, String param, String tabClicked) 
	{
		this.context = context;
		
		this.path = path;
		this.profile_id = profile_id;
		this.param = param;
		this.tabClicked = tabClicked;
	}

	public interface NoticeDialogListener
	{}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		
		typeface  = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		
		measure = new MeasurementList_Profile();
		bp_master = new BloodPressure_Master(context);
		sugar_master = new Sugar_Master(context);

		if(param.equals("share"))
		{
			deleteDialog = new Dialog(context,R.style.crescoDialogStyle);
			deleteDialog.setContentView(R.layout.share_dialog);
		}
		else if(param.equals("export"))
		{
			deleteDialog = new Dialog(context,R.style.crescoDialogStyle);
			deleteDialog.setContentView(R.layout.export_dialog);
		}
		else if(param.equals("delete"))
		{
			deleteDialog = new Dialog(context,R.style.crescoDialogStyle);
			deleteDialog.setContentView(R.layout.delete_dialog);
		}
		else if(param.equals("rate"))
		{
			deleteDialog = new Dialog(context,R.style.crescoDialogStyle);
			deleteDialog.setContentView(R.layout.rate_dialog);
		}
		

		deleteDialog.setCanceledOnTouchOutside(false);

		
		tr_header = (TableRow) deleteDialog.findViewById(R.id.tr_header);
		tv_dialogTitle = (CrescoTextView) deleteDialog.findViewById(R.id.tv_dialogTitle);
		
		if(param.equals("share"))
		{
			tv_dialogBody  = (CrescoTextView) deleteDialog.findViewById(R.id.tv_dialogBody);			
			cb_share  = (Button) deleteDialog.findViewById(R.id.cb_share);
			cb_open = (Button) deleteDialog.findViewById(R.id.cb_open);
			
			cb_share.setTypeface(typeface);
			cb_open.setTypeface(typeface);
			
			tv_dialogTitle.setText("Readings Exported Successfully");
			tv_dialogBody.setText("File saved as "+path);
			cb_share.setText("Share");
			cb_open.setText("Open");
			
			cb_share.setOnClickListener((OnClickListener) mListener);
			cb_open.setOnClickListener((OnClickListener) mListener);
			cb_share.setOnTouchListener(this);
			cb_open.setOnTouchListener(this);
		}
		else if(param.equals("export"))
		{
			cb_pdf  = (Button) deleteDialog.findViewById(R.id.cb_pdf);
			cb_csv = (Button) deleteDialog.findViewById(R.id.cb_csv);
			et_from = (CrescoEditText) deleteDialog.findViewById(R.id.et_from);
			et_to = (CrescoEditText) deleteDialog.findViewById(R.id.et_to);
			
			cb_pdf.setTypeface(typeface);
			cb_csv.setTypeface(typeface);
			
			tv_dialogTitle.setText("Set Period to Export Readings");
			cb_pdf.setText("Export PDF");
			cb_csv.setText("Export CSV");
			
		    
		    ArrayList<Bundle> mlist = new ArrayList<Bundle>();
		    
		    if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
		    {
		    	mlist = bp_master.getMeasurementListByProfile(profile_id);
		    }
		    else
		    {
		    	mlist = sugar_master.getMeasurementListByProfile(profile_id);
		    }
		    
		    //Log.v(TAG,"measure : " + mlist.toString());
		    
		    if(mlist.size() > 0 )
		    {
                String default_from, default_to;
                if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
                {
                     default_from = mlist.get(0).getString("date");
                    default_to = mlist.get(mlist.size() - 1).getString("date");

                }
                else
                {
                    default_from = mlist.get(0).getString(Sugar_Master.MSR_DATE);
                    default_to = mlist.get(mlist.size() - 1).getString(Sugar_Master.MSR_DATE);

                }
			    
			    String date[] =default_from.split("-");
				String d = date[2];
				String m = measure.getMonth(Integer.parseInt(date[1]));
				String y= date[0];
				String date1 = d+" "+m+", "+y;
				
				//String default_to = mlist.get(mlist.size()-1).getString("date");
				
				String date2[] =default_to.split("-");
				String d1 = date2[2];
				String m1 = measure.getMonth(Integer.parseInt(date2[1]));
				String y1= date[0];
				String date3 = d1+" "+m1+", "+y1;
				
				//Log.v(TAG,"default_from :"+default_from);
				//Log.v(TAG,"default_to :"+default_to);
				//Log.v(TAG,"d , m , y :"+d+" "+m+", "+y);
				//Log.v(TAG,"d1 , m1, y1 :"+d1+" "+m1+", "+y1);
				
				
				et_from.setText(date3);
				et_to.setText(date1);
				
		    }
		   			
			cb_pdf.setOnClickListener((OnClickListener) mListener);
			cb_csv.setOnClickListener((OnClickListener) mListener);
			et_from.setOnClickListener((OnClickListener) mListener);
			et_to.setOnClickListener((OnClickListener) mListener);

			cb_pdf.setOnTouchListener(this);
			cb_csv.setOnTouchListener(this);
			et_from.setOnTouchListener(this);
			et_to.setOnTouchListener(this);
		}
		
		else if(param.equals("delete"))
		{
			tv_dialogBody1  = (CrescoTextView) deleteDialog.findViewById(R.id.tv_dialogBody1);			
			cb_no  = (Button) deleteDialog.findViewById(R.id.cb_no);
			cb_yes = (Button) deleteDialog.findViewById(R.id.cb_yes);
			
			cb_no.setTypeface(typeface);
			cb_yes.setTypeface(typeface);
			
//			tv_dialogTitle.setText("Delete Confirmation");

            if (path.equals("PROFILE"))
            {
                tv_dialogTitle.setText("Delete Confirmation");

                tv_dialogBody1.setText("Are you sure you want to delete this Profile permanently?");

                cb_no.setText("No");
                cb_yes.setText("Yes");
            }
            else if (path.equals("EXIT"))
            {
                tv_dialogTitle.setText("Exit HealthMate");

                tv_dialogBody1.setText("Do you want to exit HealthMate?");

                cb_no.setText("No");
                cb_yes.setText("Yes");
            }
            else
            {
                tv_dialogTitle.setText("Delete Confirmation");

                tv_dialogBody1.setText("Are you sure you want to delete record?");

                cb_no.setText("No");
                cb_yes.setText("Yes");
            }

			cb_no.setOnClickListener((OnClickListener) mListener);
			cb_yes.setOnClickListener((OnClickListener) mListener);
			cb_no.setOnTouchListener(this);
			cb_yes.setOnTouchListener(this);
		}
		else if(param.equals("rate"))
		{
			tv_dialogBody2  = (CrescoTextView) deleteDialog.findViewById(R.id.tv_dialogBody2);			
			cb_shareApp  = (Button) deleteDialog.findViewById(R.id.cb_shareApp);
			cb_rate = (Button) deleteDialog.findViewById(R.id.cb_rate);
			
			cb_shareApp.setTypeface(typeface);
			cb_rate.setTypeface(typeface);
			
			tv_dialogTitle.setText("Like HealthMate");
			tv_dialogBody2.setText("Please spare a moment to spread the word about HealthMate ");
			cb_shareApp.setText("Share");
			cb_rate.setText("Rate");
			
			cb_shareApp.setOnClickListener((OnClickListener) mListener);
			cb_rate.setOnClickListener((OnClickListener) mListener);
			cb_shareApp.setOnTouchListener(this);
			cb_rate.setOnTouchListener(this);
		}

		deleteDialog.show();


		if (ls_for == null  ) ls_for =  "";
		
		//if(ls_className.equalsIgnoreCase("RegisterActivity"))
		//{
			//tv_dialogTitle.setText("Export Files ");
			//tv_dialogBody.setText("Looks like either your Accountant has not signed up for adnote or has not added you to adnote.\n");
			//cb_pdf.setText("Export PDF");
			//cb_csv.setText("Export CSV");
		//}

		//kalyani end
		return deleteDialog;

	}



	@Override
	public void onDismiss(DialogInterface dialog)
	{
		// TODO Auto-generated method stub
		//Toast.makeText(context, "on Dismiss", Toast.LENGTH_SHORT).show();
		if (ls_className.equals("MeasurementList_Profile") || ls_className.equals("New_Grid") || ls_className.equals("Custom_keyboard"))
		{
			//this.getActivity().finish();
            dismiss();
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {


			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (NoticeDialogListener) activity;

			//Toast.makeText(context, "In attach"+activity.getLocalClassName(), Toast.LENGTH_SHORT).show();

			ls_className = activity.getLocalClassName();
			//Toast.makeText(context, "class is "+ls_className, Toast.LENGTH_SHORT).show();

		}
		catch (ClassCastException e)
		{
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}


    @SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		int greenColor = getResources().getColor(R.color.transparent_healthmate_green);
		int white = getResources().getColor(R.color.White);
		switch (v.getId())
		{
			case R.id.cb_pdf:
			{
				cb_pdf.setBackgroundColor(greenColor);
				cb_csv.setBackgroundColor(white);
				break;
			}
	
			case R.id.cb_csv:
			{
				cb_csv.setBackgroundColor(greenColor);
				cb_pdf.setBackgroundColor(white);
				break;
			}
		
			case R.id.cb_share:
			{
	
				cb_share.setBackgroundColor(greenColor);
				break;
			}
			
			case R.id.cb_open:
			{
	
				cb_open.setBackgroundColor(greenColor);
				break;
			}
			
			case R.id.cb_no:
			{
	
				cb_no.setBackgroundColor(greenColor);
				break;
			}
			
			case R.id.cb_yes:
			{
	
				cb_yes.setBackgroundColor(greenColor);
				break;
			}
			
			case R.id.cb_shareApp:
			{
	
				cb_shareApp.setBackgroundColor(greenColor);
				break;
			}
			
			case R.id.cb_rate:
			{
	
				cb_rate.setBackgroundColor(greenColor);
				break;
			}
		}
		return false;


	}

}
