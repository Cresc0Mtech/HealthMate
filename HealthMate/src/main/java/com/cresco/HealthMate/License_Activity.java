package com.cresco.HealthMate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Created by comp1 on 31/03/2015.
 */
public class License_Activity extends TitleBar_Activity implements OnItemClickListener
{
    private final static String TAG = License_Activity.class.getSimpleName();

    private ArrayList<HashMap<String, Object>> list_hashmap;

    private Context context;

    LayoutInflater layoutInflator;
    ListView lv_license;

    private static String TAG_SETTING_NAME = "setting_name";

    private String[] ls_mapping;
    private int[] li_to;
    boolean lb_normalScreen = false;

    MySimpleAdapter adapter;

    int li_density;
    LinearLayout.LayoutParams ll_params;

    // Client Details
    public static final String CLIENT_NAME = "EDGE ACADEMY";
    public static final String CLIENT_WEB_URL = "http://www.edgeacademy.in";
    // End Client Details

    MeasurementList_Profile measure;

    SharedPreferences recordCount_sp, rate_sp;
    SharedPreferences.Editor sp_recordEditor, sp_rateEditor;
    static String appName = "https://play.google.com/store/apps/details?id=com.cresco.HealthMate";
    static String ls_share = "Share mAccounts via";
    static String SHARETEXT = " Hey! I am using mAccounts now. It's a great mobile accounting app for Android."+
            " I am managing my Accounts & Inventory on it and I am sure you too are going to love it. "+
            " Download mAccounts from "+appName;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.license);


        measure = new MeasurementList_Profile();

        super.ll_profileheader.setBackgroundColor(getResources().getColor(R.color.healthmate_green));
        super.iv_appicon.setBackgroundResource(R.drawable.hm_logo_white);
        super.iv_profileimage.setVisibility(View.GONE);
        super.tv_profilename.setText("HealthMate");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        li_density = metrics.densityDpi;


        if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            //Toast.makeText(context, "Large screen + " +li_density,Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Large screen");

        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            lb_normalScreen = true;
            //Toast.makeText(context, "Normal sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Normal sized screen");
        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            //Toast.makeText(context, "Small sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran Small sized screen");
        }
        else if ((context.getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
        {
            //Toast.makeText(context, "X-large sized screen "+li_density , Toast.LENGTH_LONG).show();
            Debugger.debug(TAG, "Imran X-large sized screen");
        }
        else
        {
            Debugger.debug(TAG,"Screen size is neither large, normal or small " +li_density);
            //Toast.makeText(context, "Imran Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
        }

        setSettingsMenu();

        layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ls_mapping = new String[] {TAG_SETTING_NAME};
        li_to = new int[] {R.id.tv_setting};

        adapter = new MySimpleAdapter(context, list_hashmap, R.layout.settings_main_row, ls_mapping, li_to);

        lv_license = (ListView) findViewById(R.id.lv_license);
        lv_license.setAdapter(adapter);

        lv_license.setOnItemClickListener(this);


    }

    private void setSettingsMenu()
    {
        this.list_hashmap = new ArrayList<HashMap<String,Object>>();

        HashMap<String, Object> hashmap;

        hashmap = new HashMap<String, Object>();

        hashmap.put(TAG_SETTING_NAME, "Apache License, Version 2.0");
        this.list_hashmap.add(hashmap);
    }

    private class MySimpleAdapter extends android.widget.SimpleAdapter
    {
        ViewHolder viewHolder;
        Drawable share, rate, about;

        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        {
            super(context, data, resource, from, to);

           // about = getResources().getDrawable( R.drawable.cresco_about);
           // share = getResources().getDrawable( R.drawable.cresco_share);
           // rate = getResources().getDrawable( R.drawable.cresco_rating);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView == null)
            {
                convertView = layoutInflator.inflate(R.layout.settings_main_row, null);

                viewHolder = new ViewHolder();
                viewHolder.tv_setting 	= (CrescoTextView) convertView.findViewById(R.id.tv_setting);
                viewHolder.ll_row 	= (LinearLayout) convertView.findViewById(R.id.ll_row);
            }

            HashMap<String, Object> hashmap = list_hashmap.get(position);
            Debugger.debug(TAG, "___HASHMAP::::" + hashmap.toString());



            viewHolder.tv_setting.setText(hashmap.get("setting_name").toString());
            convertView.setTag(viewHolder);


            return convertView;
        }
    };

    private class ViewHolder
    {
        public CrescoTextView tv_setting;
        LinearLayout ll_row;
    }


    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int id, long position)
    {
        HashMap<String, Object> hashmap = this.list_hashmap.get((int)position);

        Debugger.debug(TAG, "Selected hashmap: " + hashmap.toString());

        String ls_text = hashmap.get(TAG_SETTING_NAME).toString();

        if(ls_text.equalsIgnoreCase("Apache License, Version 2.0"))
        {
            //Intent intent = new Intent(this, Help_Activity.class);
            //startActivity(intent);

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apache.org/licenses/LICENSE-2.0.html")));
        }

        lv_license.invalidateViews();
    }

}
