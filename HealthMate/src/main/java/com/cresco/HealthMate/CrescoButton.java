package com.cresco.HealthMate;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CrescoButton extends Button
{
	public CrescoButton(Context context, AttributeSet attrs, int defStyle)
	{    
		super(context, attrs, defStyle);
		setListFont();
	}

	public CrescoButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setListFont();
	}

	public CrescoButton(Context context) 
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
