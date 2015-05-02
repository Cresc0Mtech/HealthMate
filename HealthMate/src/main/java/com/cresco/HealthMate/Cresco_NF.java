package com.cresco.HealthMate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/* This file is used to format the numeric columns
 * Parameters required : String (Input Value)
 * Format Type		   : To Which format the input needs to be formatted
 */

public final class Cresco_NF {

	
	public static Context context;
	private static final String TAG = "Cresco_NF";

	public Cresco_NF(Context context) {
		Cresco_NF.context = context;
	}

	public static void showKB(Activity mActivity) {
		mActivity.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	public static void hideKB(Activity mActivity) {
		mActivity.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	public static void showKB(Dialog mDialog) {
		mDialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	public static void hideKB(Dialog mDialog) {
		mDialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	public static void showKB(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, 0);

	}

	public static BigDecimal getAmount(String ls_amount) 
	{
		if (ls_amount == null || ls_amount.length() <= 0)
		{
			ls_amount = "0.00";
		}

		boolean isMinus = false;

		if (ls_amount.indexOf("-") >= 0) 
		{
			isMinus = true;
			//Log.v(TAG, ls_amount);
		}
		
		if (ls_amount.indexOf("(") >= 0)
		{
			isMinus = true;
			ls_amount = ls_amount.replace(")", "").trim();
			ls_amount = ls_amount.replace("(", "").trim();
			
		}

		if (ls_amount.indexOf(" ") > 0) 
		{
			ls_amount = ls_amount.substring(ls_amount.indexOf(" "),ls_amount.length());
		}
		ls_amount = ls_amount.replace(",", "").trim();
		BigDecimal bd_amount ;
		try
		{
			bd_amount = new BigDecimal(ls_amount);
		}
		catch (NumberFormatException nfe)
		{
			//Debugger.debug(TAG,nfe.toString());
			bd_amount = new BigDecimal("0.00");
		}

		if (isMinus) 
		{
			if (bd_amount.floatValue() > 0) 
			{
				bd_amount = bd_amount.abs();
			}
			
		}
		// bd_amount = bd_amount.
		//Debugger.debug(TAG, "Amount " + bd_amount);
		return bd_amount;
	}

	public static Double getOpBalance(String ls_amount) {
		Double ld_amount = 0.0;
		boolean isMinus = false;

		if (ls_amount.indexOf("-") >= 0) {
			isMinus = true;
			// Debugger.debug(TAG,ls_amount);
		}

		if (ls_amount.indexOf(" ") > 0) {
			ls_amount = ls_amount.substring(ls_amount.indexOf(" "),
					ls_amount.length());
		}
		ls_amount = ls_amount.replace(",", "").trim();

		try {
			ld_amount = Double.parseDouble(ls_amount);
		} catch (NumberFormatException nfe) {
			ld_amount = 0.00;
		}
		if (isMinus) {
			if (ld_amount > 0) {
				ld_amount = ld_amount * -1;
			}
			// ld_amount = ld_amount * -1;
			// Debugger.debug("Minus Amount","Neg Amt " +ld_amount);
		}
		// int li_amount = (int)(ld_amount * 10000);
		// ld_amount = (double) (li_amount / 10000);

		double roundedValue = BigDecimal.valueOf(ld_amount).doubleValue();
		// Debugger.debug(TAG,"Amount is "+ld_amount + " AND Round is "+roundedValue);
		return roundedValue;
	}

}
