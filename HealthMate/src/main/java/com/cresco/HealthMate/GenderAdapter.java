package com.cresco.HealthMate;

import java.util.List;

import com.cresco.HealthMate.R;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GenderAdapter  extends ArrayAdapter<String>
{
	private  String TAG = this.getClass().getSimpleName();
	public Activity context;
	public int layout;
	private List<String> list;
	//public static int cgray = Color.parseColor("#D6D6D6"); old value
	public static int cgray 	 = Color.parseColor("#C4C4C4");
	public static int blackColor = Color.parseColor("#000000");
	public static int healthmate_green = Color.parseColor("#00CC00");
	
	String ls_gender;
	public GenderAdapter(Activity context, int resource, List<String> objects)
	{
		super(context, resource,  objects);
		this.context = context;
		this.layout = resource;
		list = objects;
	}

	static class ViewHolder
	{
		protected TextView tv_gender;
		TextView text1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = null;
		ViewHolder holder;
		if (convertView == null)
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(layout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.tv_gender        =(TextView)view.findViewById(R.id.tv_gender);

			viewHolder.tv_gender.setTag(list.get(position));
			view.setTag(viewHolder);
			holder = (ViewHolder) view.getTag();

			ls_gender = list.get(position);
			if (position == 0)
			{
				viewHolder.tv_gender.setText(ls_gender);
				viewHolder.tv_gender.setTextColor(cgray);
				//viewHolder.tv_gender.setHint(ls_gender);
				//viewHolder.tv_gender.setHintTextColor(cgray);
			}
			else
			{
				viewHolder.tv_gender.setText(ls_gender);
				viewHolder.tv_gender.setTextColor(blackColor);
			}
		}
		else
		{
			view = convertView;
			((ViewHolder) view.getTag()).tv_gender.setTag(list.get(position));
		}

		return view;
	}

}
