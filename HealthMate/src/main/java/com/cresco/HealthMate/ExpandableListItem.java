/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cresco.HealthMate;



import android.os.Bundle;

/**
 * This custom object is used to populate the list adapter. It contains a reference
 * to an image, title, and the extra text to be displayed. Furthermore, it keeps track
 * of the current state (collapsed/expanded) of the corresponding item in the list,
 * as well as store the height of the cell in its collapsed state.
 */
public class ExpandableListItem implements OnSizeChangedListener {

    private String mTitle;
    private String mText;
    private boolean mIsExpanded;
    private int mImgResource;
    private int mCollapsedHeight;
    private int mExpandedHeight;
    
    String TAG = this.getClass().getSimpleName(); 
	String ls_p_date, ls_p_time , ls_sys, ls_dias, ls_pulse, ls_pos, ls_loc, ls_remark; 
    
    

    /*public ExpandableListItem(String title, int imgResource, int collapsedHeight, String text) {
        mTitle = title;
        mImgResource = imgResource;
        mCollapsedHeight = collapsedHeight;
        mIsExpanded = false;
        mText = text;
        mExpandedHeight = -1;
    }*/
    
    public ExpandableListItem(Bundle bundle)
    {
    	ls_p_date  				= bundle.getString("date");
    	ls_p_time 			= bundle.getString("time");
    	ls_sys 			= bundle.getString(BloodPressure_Master.PM2_VALUE);
		ls_dias			= bundle.getString(BloodPressure_Master.PM1_VALUE);
		ls_pulse			= bundle.getString(BloodPressure_Master.PM3_VALUE);
		ls_pos			= bundle.getString(BloodPressure_Master.MSR_POSITION);
		ls_loc			= bundle.getString(BloodPressure_Master.MSR_LOCATION);
		ls_remark			= bundle.getString(BloodPressure_Master.MSR_REMARKS);
    }

    public Bundle getBPlist()
	{
		Bundle bundle = new Bundle();

		bundle.putString("date",ls_p_date);
		bundle.putString("time", ls_p_time);
		bundle.putString(BloodPressure_Master.PM1_VALUE, ls_dias);
		bundle.putString(BloodPressure_Master.PM2_VALUE, ls_sys);
		bundle.putString(BloodPressure_Master.PM3_VALUE, ls_pulse);
		bundle.putString(BloodPressure_Master.MSR_POSITION, ls_pos);
		bundle.putString(BloodPressure_Master.MSR_LOCATION, ls_loc);
		bundle.putString(BloodPressure_Master.MSR_REMARKS, ls_remark);
		
		return bundle;
	}
    
    public boolean isExpanded() 
    {
        return mIsExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        mIsExpanded = isExpanded;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImgResource() {
        return mImgResource;
    }

    public int getCollapsedHeight() {
        return mCollapsedHeight;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        //mCollapsedHeight = collapsedHeight;
        
        mCollapsedHeight = 100;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getExpandedHeight() {
        return -1;
    }

    public void setExpandedHeight(int expandedHeight) {
        //mExpandedHeight = expandedHeight;
        
        mExpandedHeight = expandedHeight;
    }

    @Override
    public void onSizeChanged(int newHeight) {
        setExpandedHeight(newHeight);
    }
}
