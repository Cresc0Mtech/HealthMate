package com.cresco.HealthMate;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class EndlessAdapter extends ArrayAdapter<String> {
private List<String> itemList;
private Context ctx;
private int layoutId;
private  ArrayList<Bundle> measure_list = new ArrayList<Bundle>();

public EndlessAdapter(Context ctx, List<String> itemList,ArrayList<Bundle> measure_list, int layoutId) 
{
	super(ctx, layoutId, itemList);
	this.itemList = itemList;
	this.ctx = ctx;
	this.layoutId = layoutId;
	this.measure_list = measure_list;
}

@Override
public int getCount() 
{
	return itemList.size() ;
}

@Override
public String getItem(int position) 
{
	return itemList.get(position);
}

@Override
public long getItemId(int position) 
{
	return itemList.get(position).hashCode();
}

@Override
public View getView(int position, View convertView, ViewGroup parent) 
{
	View result = convertView;
	if (result == null) 
	{
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		result = inflater.inflate(layoutId, parent, false);
	}

	// We should use class holder pattern
	//TextView tv = (TextView) result.findViewById(R.id.txt1);
/*
	CrescoTextView tv_listTime        =(CrescoTextView) result.findViewById(R.id.tv_listTime);
	CrescoTextView tv_listsystolic        =(CrescoTextView)result.findViewById(R.id.tv_listsystolic);
	CrescoTextView tv_listdiasttolic        =(CrescoTextView)result.findViewById(R.id.tv_listdiasttolic);
	CrescoTextView tv_listpulse        =(CrescoTextView)result.findViewById(R.id.tv_listpulse);
	CrescoTextView tv_listDate        =(CrescoTextView)result.findViewById(R.id.tv_listDate);
	//tv.setText(itemList.get(position));

	
	String Date = measure_list.get(position).getString("date").trim();
	String tm  = measure_list.get(position).getString("time").trim();
	String High  = measure_list.get(position).getString(BloodPressure_Master.PM1_VALUE);
	String Low = measure_list.get(position).getString(BloodPressure_Master.PM2_VALUE);
	String pulse = measure_list.get(position).getString(BloodPressure_Master.PM3_VALUE);

	tv_listDate.setText(Date);
	tv_listTime.setText(tm);
	tv_listsystolic.setText(High);
	tv_listdiasttolic.setText(Low);
	tv_listpulse.setText(pulse);*/
	return result;
}
}