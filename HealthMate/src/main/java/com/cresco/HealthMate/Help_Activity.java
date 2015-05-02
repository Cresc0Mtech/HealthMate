package com.cresco.HealthMate;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Help_Activity extends TitleBar_Activity implements OnClickListener//, OnGroupClickListener
{
		private final static String TAG = Help_Activity.class.getSimpleName();


		LayoutInflater layoutInflator;

		ArrayList<HashMap<String, Object>> list_hashmap;

		LayoutParams ll_params;
		int li_density;
		boolean lb_normalScreen;

		Context context;

		int li_lastSelectedGroupPosition = -1;
		CrescoTextView tv_oldHeader, tv_about;
		View oldView;


		

		//ExpandableListView elv_help;

		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.help_healthmate);

			context = this;

			//super.tv_screenTitle.setText("About");

            //Admob integration
            String dev_id = DeviceUtils.getImei(context);
            if(DbHelper.USE_ADD)
            {
                adView = (AdView) this.findViewById(R.id.adMob);
                //request TEST ads to avoid being disabled for clicking your own ads
                AdRequest adRequest = new AdRequest.Builder()
                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)// This is for emulators
                        //test mode on DEVICE (this example code must be replaced with your device uniquq ID)
                        .addTestDevice(dev_id) // Nexus 5
                        .build();
                //"356262052582091"
                adView.loadAd(adRequest);
            }
            //
			
			super.ll_profileheader.setBackgroundColor(getResources().getColor(R.color.healthmate_green));
			super.iv_appicon.setBackgroundResource(R.drawable.hm_logo_white);
			super.iv_profileimage.setVisibility(View.GONE);
            super.tv_profilename.setText("HealthMate");


			tv_about = (CrescoTextView) findViewById(R.id.tv_about);
			
			initializeAdapter();

            /*
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			li_density = metrics.densityDpi;

			if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
				//Toast.makeText(context, "Large screen",Toast.LENGTH_LONG).show();
				Debugger.debug(TAG, "Large screen");

			}
			else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) 
			{  
				lb_normalScreen = true;
				//Toast.makeText(context, "Normal sized screen" , Toast.LENGTH_LONG).show();
				Debugger.debug(TAG, "Normal sized screen");
			} 
			else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
				//Toast.makeText(context, "Small sized screen" , Toast.LENGTH_LONG).show();
				Debugger.debug(TAG, "Small sized screen");
			}
			else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) 
			{     
				//Toast.makeText(context, "X-large sized screen" , Toast.LENGTH_LONG).show();
				Debugger.debug(TAG, "X-large sized screen");
			}
			else {
				Debugger.debug(TAG,"Screen size is neither large, normal or small");
				//Toast.makeText(context, "Imran Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
			}

			layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			//initializeAdapter();

			//elv_help.setAdapter(MyElvAdapter);



			//elv_help.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
            */
		}

		private void initializeAdapter()
		{

			String about_text1  = "<p align=\"justify\" style=\"line-height:150%;\">HealthMate is a work of <a href=\"http://www.crescomtech.com\">Cresco Mobility Solutions.</a> Cresco is a Mumbai, India based start-up providing off-the-shelf as well as customized mobility solutions to simplify things for individuals, enterprises & communities. Some of our other works include:" +
                    "<br/><br/>• <a href=\"https://play.google.com/store/apps/details?id=com.cresco.notifiCAtion\">notifiCAtion</a>  - a dedicated messaging & reminder app exclusively for Accountants" +
                    "<br/><br/>• <a href=\"https://play.google.com/store/apps/details?id=com.cresco.adnoteOTA\">Ignite Messenger</a> - messenger app (based on our adnote platform) for Sharekhan's Ignite Program" +

                    "<br/><br/>• <b>SalesTroop</b> - a field sales force automation platform for distributors & field sales professionals" +
                    "<br/><br/>• <a href=\"https://play.google.com/store/apps/details?id=com.cresco.Navrang2014\">Navrang 2014</a> - official app of Navrang 2014, TMM's annual sports & cultural event " +
                    //"<br/><br/>• <a href=\"https://play.google.com/store/apps/details?id=com.cresco.mAccountsFT\">mAccounts Mobile Accounting</a> - a complete, stand-alone, offline, Tally-compatible mobile accounting app" +
                    "<br/><br/>Contact: 3, Sonali, Gen. A. K. Vaidya Marg, Near TMC, Opp. Kacharali Lake, Thane (W)  400 602  |  +91 22 25391231  | contact@crescomtech.com  |   www.crescomtech.com</p>";

            tv_about.setText(Html.fromHtml(about_text1));
            tv_about.setLineSpacing(5, 1);
//
//            //tv_about.setClickable(true);
            tv_about.setMovementMethod(LinkMovementMethod.getInstance());

		}

		private ExpandableListAdapter MyElvAdapter = new ExpandableListAdapter()
		{
			GroupViewHolder gvHolder;
			ChildViewHolder cvHolder;
			String ls_expand = "N";

			@Override
			public void unregisterDataSetObserver(DataSetObserver arg0) 
			{

			}

			@Override
			public void registerDataSetObserver(DataSetObserver arg0) {}

			@Override
			public void onGroupExpanded(int groupPosition)
			{

				
			}

			@Override
			public void onGroupCollapsed(int groupPosition)
			{


			}

			@Override
			public boolean isEmpty()
			{
				return false;
			}

			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition)
			{
				return false;
			}

			@Override
			public boolean hasStableIds()
			{
				return false;
			}

			@SuppressLint("InflateParams")
			@Override
			public View getGroupView(int position, boolean arg1, View convertView, ViewGroup parent)
			{
				Debugger.debug(TAG, "in getGroupView()");

				if(convertView == null)
				{
					convertView = layoutInflator.inflate(R.layout.about_adnote_group, null);

					gvHolder = new GroupViewHolder();
					gvHolder.tv_groupName = (TextView) convertView.findViewById(R.id.tv_groupName);
					gvHolder.ll_groupRow = (LinearLayout) convertView.findViewById(R.id.ll_groupRow);
					gvHolder.ll_row = (LinearLayout) convertView.findViewById(R.id.ll_row);
					convertView.setTag(gvHolder);
				}
				else
				{
					((GroupViewHolder) convertView.getTag()).tv_groupName.setTag(position);
				}

				gvHolder = (GroupViewHolder) convertView.getTag();

				HashMap<String, Object> hashmap = Help_Activity.this.list_hashmap.get(position);

				gvHolder.tv_groupName.setText(hashmap.get("group_name").toString());
				ls_expand =  (String) hashmap.get("expand");

				if (ls_expand.equals("Y"))
				{	
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
					gvHolder.ll_groupRow.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
					gvHolder.tv_groupName.setTextColor(context.getResources().getColor(R.color.White));

				}
				else
				{
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.ll_groupRow.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.tv_groupName.setTextColor(context.getResources().getColor(R.color.healthmate_green));

				}
				//ll_params = (LayoutParams) gvHolder.ll_groupRow.getLayoutParams();

				return convertView;
			}

			@Override
			public long getGroupId(int groupPosition)
			{
				return groupPosition;
			}

			@Override
			public int getGroupCount()
			{
				return list_hashmap.size();
			}

			@Override
			public Object getGroup(int groupPosition)
			{
				return list_hashmap.get(groupPosition);
			}

			@Override
			public long getCombinedGroupId(long groupId)
			{
				return groupId;
			}

			@Override
			public long getCombinedChildId(long groupId, long childId)
			{
				return 0;
			}

			@Override
			public int getChildrenCount(int groupPosition)
			{
				return 1;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
			{
				Debugger.debug(TAG, "in getChildView()");

				if(convertView == null)
				{
					convertView = layoutInflator.inflate(R.layout.about_adnote_child, null);

					cvHolder = new ChildViewHolder();
					cvHolder.tv_childText = (TextView) convertView.findViewById(R.id.tv_childText);

					convertView.setTag(cvHolder);
				}
				{
					((ChildViewHolder) convertView.getTag()).tv_childText.setTag(groupPosition);
				}

				cvHolder = (ChildViewHolder) convertView.getTag();


				HashMap<String, Object> hashmap = Help_Activity.this.list_hashmap.get(groupPosition);
				cvHolder.tv_childText.setText(Html.fromHtml(hashmap.get("child_text").toString()));
				cvHolder.tv_childText.setClickable(true);
				cvHolder.tv_childText.setMovementMethod(LinkMovementMethod.getInstance());


				return convertView;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition)
			{
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition)
			{
				return list_hashmap.get(groupPosition);
			}

			@Override
			public boolean areAllItemsEnabled()
			{
				return false;
			}
		};

		@Override
		public void onClick(View view)
		{
			super.onClick(view);
			//Debugger.debug(TAG, ((TextView)view).getText().toString());	
		}

		private class GroupViewHolder
		{
			LinearLayout ll_row,ll_groupRow;
			TextView tv_groupName;
		}

		private class ChildViewHolder
		{
			TextView tv_childText;
		}



}
