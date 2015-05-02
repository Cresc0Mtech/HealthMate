package com.cresco.HealthMate;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CrescoEditText extends EditText 
{
	public CrescoEditText(Context context, AttributeSet attrs, int defStyle)
	{    
		super(context, attrs, defStyle);
		setListFont();
	}

	public CrescoEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setListFont();
	}

	public CrescoEditText(Context context) 
	{
		super(context);
		setListFont();
	}

	private void setListFont()
	{
		//titillium04, ColabLig.otf ,
		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
		setTypeface(typeface);
	}



}
