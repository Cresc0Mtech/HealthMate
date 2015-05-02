package com.cresco.HealthMate;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

/**
 * Created by comp1 on 09/04/2015.
 */
@SuppressLint({"NewApi", "ValidFragment"})
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Exit_ConfirmDialog extends DialogFragment implements View.OnTouchListener
{
    NoticeDialogListener mListener;
    Context context;
    Dialog exitDialog;

    String ls_className ="";
    TableRow tr_header;
    String ls_msgToShow = null;

    String TAG= "Exit_ConfirmDialog";
    String ls_for = "";

    CrescoTextView tv_dialogTitle, tv_dialogBody1;
    Button cb_yes, cb_no;

    Typeface typeface;


    public Exit_ConfirmDialog(Context context)
    {
        this.context = context;
    }



    public interface NoticeDialogListener
    {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        typeface  = Typeface.createFromAsset(context.getAssets(), "fonts/DistProTh.otf");

        exitDialog = new Dialog(context,R.style.crescoDialogStyle);
        exitDialog.setContentView(R.layout.delete_dialog);


        exitDialog.setCanceledOnTouchOutside(false);


        tr_header = (TableRow) exitDialog.findViewById(R.id.tr_header);
        tv_dialogTitle = (CrescoTextView) exitDialog.findViewById(R.id.tv_dialogTitle);


        tv_dialogBody1  = (CrescoTextView) exitDialog.findViewById(R.id.tv_dialogBody1);
        cb_no  = (Button) exitDialog.findViewById(R.id.cb_no);
        cb_yes = (Button) exitDialog.findViewById(R.id.cb_yes);

        cb_no.setTypeface(typeface);
        cb_yes.setTypeface(typeface);

        tv_dialogTitle.setText("Delete Confirmation");

        tv_dialogBody1.setText("Do you want to exit?");


        cb_no.setText("Cancel");
        cb_yes.setText("Exit");

        cb_no.setOnClickListener((View.OnClickListener) mListener);
        cb_yes.setOnClickListener((View.OnClickListener) mListener);
        cb_no.setOnTouchListener(this);
        cb_yes.setOnTouchListener(this);

        return null;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        // TODO Auto-generated method stub
        //Toast.makeText(context, "on Dismiss", Toast.LENGTH_SHORT).show();
        if (ls_className.equals("MeasurementList_Profile"))
        {
            //this.getActivity().finish();
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
			case R.id.cb_yes:
			{
                cb_yes.setBackgroundColor(greenColor);
                cb_no.setBackgroundColor(white);
				break;
			}

            case R.id.cb_no: {
                cb_no.setBackgroundColor(greenColor);
                cb_yes.setBackgroundColor(white);
                break;
            }
        }

        return false;
    }




    }
