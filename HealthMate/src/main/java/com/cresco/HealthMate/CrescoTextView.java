package com.cresco.HealthMate;

import java.util.Hashtable;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CrescoTextView extends TextView
{
	public CrescoTextView(Context context, AttributeSet attrs, int defStyle)
	{    
		super(context, attrs, defStyle);
		setListFont();
	}

	public CrescoTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setListFont();
	}

	public CrescoTextView(Context context) 
	{
		super(context);
		setListFont();
	}

	private void setListFont()
	{
		//titillium04, ColabLig.otf ,
		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/DistProTh.otf");
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/GillSans.ttc");
		////Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/MyriadPro-Regular.otf");
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/MyriadPro-Light.otf");
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Sertig.otf");
		
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Ubuntu-L.ttf");
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Vegur-Regular.otf");
		//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Light.ttf");
		setTypeface(typeface);
		//String font
		
	}
	
	

	@Override
	public boolean isInEditMode()
	{
		return super.isInEditMode();
	}
}
