package com.cresco.HealthMate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.test.PerformanceTestCase;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

import com.cresco.HealthMate.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.cresco.HealthMate.ConfirmDialog.NoticeDialogListener;
import com.cresco.HealthMate.R;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
//import com.google.android.gms.internal.me;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeasurementList_Profile extends TitleBar_Activity implements OnClickListener,OnGroupClickListener, OnItemClickListener
																		, NoticeDialogListener//, OnTouchListener
{
	Context context;
	 String TAG = this.getClass().getSimpleName();
	
	LinearLayout ll_profileheader,ll_blood_pressure,ll_sugar, ll_bt_add, ll_bt_export,ll_bt_showgraph, ll_pressure_header,ll_sugar_header;
	ImageView iv_profileimage;
	CrescoTextView tv_profilename, tv_blood_pressure, tv_sugar, bt_showgraph, bt_add, bt_export;
	ListView lv_measures;
	//ExpandableListView elv_measures;
	 Typeface typeface;
	 
	 int bd_id, bs_id;
	 
	 Dialog dialog;
	 private ConfirmDialog confirmDialog;
	 Task_createPDF proAsync;
	 File exportDir;
	 String ls_filename, ls_docType;
	 Dialog progressDialog;
	
	AnimatedExpandableListView elv_measures;
	int mult = 1;
	 private final static int ITEM_PER_REQUEST = 7;
	
	EditText et_from, et_to;
	ArrayList<Bundle> alist;
	Spinner sp_type;
	
	int group_id;
	
	String tabClicked;
	 
	public static String PRESSURE = "Blood Pressure";
	public static String SUGAR = "Blood Sugar";
	ContentValues cv;	
	ArrayAdapter<CharSequence> adapter1;
	
	ArrayList<HashMap<String, Object>> list_hashmap;
	
	static int profile_id;
	String profile_fname,profile_lname;
	Profile_Master profile_master;
	Measurement_values measurement_values;
	static BloodPressure_Master bp_master;
	Sugar_Master sugar_master;
	static ArrayList<Bundle> profile_list;
	ArrayList<Bundle> measure_list;
	ArrayList<ExpandableListItem>newlist;
	MeasurementListAdapter adapter;
	SugarListAdapter sugar_adapter;
	final static int REQ_CODE = 1;
	
	public final static String DIASTOLIC_LIST = "diastolic_list";
	public final static String SYSTOLIC_LIST = "systolic_list";
	public final static String PULSE_LIST = "pulse_list";
	public final static String DATE_LIST = "date_list";
	public final static String FASTING_LIST = "fasting_list";
	public final static String PP_LIST = "pp_list";
	public final static String TAB_CLICKED = "tab_clicked";
	public final static String SIZE = "size";
	
	public final static String ADD_READING = "Add Reading";
	public final static String VIEW_LIST = "View List";
	public final static String VIEW_GRAPH = "View Graph";
	
	private final int SELECT_PHOTO = 2;
	final int CROP_FROM_CAMERA = 3;
	private Uri mImageCaptureUri;
	LayoutInflater layoutInflator;
	int li_lastSelectedGroupPosition = -1;
	
	private int year, month, day, hour, min;
	String mth,hr,mn;

	ArrayList<String> sp_list1, sp_list2, sp_list3, sp_list4, sp_list5, sp_list6, sp_list7;
	SpinnerAdapter sAdapter;
	
	LinearLayout ll_add_sugar, ll_add_pressure,ll_elvlist, ll_main_graph, ll_graph, ll_custom_keyboard;
	GroupViewHolder g;
	CrescoEditText et_pressuredate, et_pressuretime ,et_pressureremark ,
				    et_sugardate, et_sugartime,et_sugardate2, et_sugartime2, et_sugarremark1,et_sugarremark2, et_from_date, et_to_date;
	//Spinner sp_pressuresys, sp_pressuredias, sp_pressurepulse,
	Spinner sp_pressurepos,  sp_pressureloc ;//, sp_sugarfasting, sp_sugarpp;
	
	CrescoEditText et_pressuresys, et_pressuredias, et_pressurepulse, et_sugarpp, et_sugarfasting;
	public static String TRUE = "true";
	String is_addMeasureclicked;
	Animation slide_top, slide_down, push_right_out, push_right_in;
	
	int[] diastoliclist, systoliclist ,pulselist, fastinglist ,pplist;
	String[] dates ; 
	int size;
	View rootLayout;
	Drawable graph, list, plus;

    New_Grid newgrid;
	
	@SuppressWarnings("unused")
	private final int AUTOLOAD_THRESHOLD = 4;
    private final int MAXIMUM_ITEMS = 22;
    private SimpleAdapter mAdapter;
    private View mFooterView;
    private Handler mHandler;
    private boolean mIsLoading = false;
    private boolean sIsLoading = false;
    private boolean mMoreDataAvailable = true;
    private boolean mWasLoading = false;
    private boolean sWasLoading = false;
    
	LinearLayout ll_graph_date, ll_cb_pressure, ll_cb_sugar;
	String Date, Time;
	static String fromDate = "";
	static String toDate = "";

    String toDate_export = "";
    String fromDate_export = "";

    String FROMDATE = "", TODATE = "";
	CheckBox cb_sys, cb_dias, cb_pulse, cb_fasting, cb_pp;
	
	int ischecked_sys = 0 , ischecked_dias = 0, ischecked_pulse = 0, ischecked_fast = 0, ischecked_pp = 0;
	
	ExpandableListItem exp;
	private ExpandingListView mListView;
	
	boolean is_data_edit = false;
	
	
	private static Font catFont = new Font(Font.UNDEFINED, 18,
		      Font.BOLD);
	private static Font redFont = new Font(Font.UNDEFINED, 12,
		      Font.NORMAL);
	private static Font subFont = new Font(Font.UNDEFINED, 16,
		      Font.BOLD);
	private static Font smallBold = new Font(Font.UNDEFINED, 12,
		      Font.NORMAL);
	private static Font smallNormal = new Font(Font.UNDEFINED, 14,
			      Font.NORMAL);
		  
	float historicX = Float.NaN, historicY = Float.NaN;
	final int DELTA = 50;
	enum Direction {LEFT, RIGHT;}
			
	ListViewSwipeDetector lvSwipeDetector;
	public static final int MSG_ANIMATION_REMOVE 	= 2;
	int RowPosition;
			
			
	CrescoTextView tv_one, tv_two, tv_three, tv_four, tv_five, tv_six, tv_seven, tv_eight,
						   tv_nine, tv_zero, tv_plus, tv_minus, tv_clear, tv_cancel, tv_calci_header;
			
	View edit_view;
			
	String editText_value;
			
	SharedPreferences recordCount_sp, rate_sp, toast_sp, screenCount_sp;
	SharedPreferences.Editor sp_recordEditor, sp_rateEditor, sp_toastEditor, sp_screenEditor;
	
	public static final int MAX_SCREENCOUNT= 3;
    public static final int MAX_TOASTCOUNT= 10;
	static String appName = "https://play.google.com/store/apps/details?id=com.cresco.HealthMate";
	static String ls_share = "Share HealthMate via";


    //Hey! I am maintaining my health records in HealthMate. It's simple, elegant & immensely useful to maintain blood pressure & blood sugar readings of all my family members in a single app! Download HealthMate from Play Store now.
    //https://play.google.com/store/apps/details?id=com.cresco.HealthMate

    static String SHARETEXT = " Hey! I am maintaining my health records in HealthMate. It's simple, elegant & immensely useful " +
                               "to maintain blood pressure & blood sugar readings of all my family members in a single app! Download " +
                                "HealthMate from Play Store now. "+appName;
	
	LinearLayout ll_adMob;
	AdRequest adRequest;
    Animation left_in, left_out;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_measures);
		
		context = this;
		
		rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
		sp_rateEditor = rate_sp.edit();
		
		//GOOGLE ANALYTICS
        if(DbHelper.USE_ANALYTICS)
        {
            call_GoogleAnalytics_screens();

            call_GoogleAnalytics_sessions();
        }
		//GOOGLE ANALYTICS
		
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
		
		dialog = new Dialog(this);
        newgrid = new New_Grid();
		
		list = getResources().getDrawable( R.drawable.list);
		plus = getResources().getDrawable( R.drawable.plus);
		graph = getResources().getDrawable( R.drawable.graph);
		
		typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
		
		super.ll_profileheader.setBackgroundColor(getResources().getColor(R.color.White));
		//super.iv_appicon.setBackground(getResources().getDrawable(R.drawable.hm_logo_green));
        super.iv_appicon.setBackgroundResource(R.drawable.hm_logo_green);
		super.tv_profilename.setTextColor(getResources().getColor(R.color.healthmate_green));
		super.iv_profileimage.setVisibility(View.VISIBLE);
		super.iv_profileimage.setBackgroundResource(R.drawable.frame);//(getResources().getDrawable(R.drawable.frame));
		
		super.tv_profilename.setTypeface(typeface);
		rootLayout = (View) findViewById(R.id.main_activity_root);
		layoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ll_blood_pressure   = (LinearLayout)findViewById(R.id.ll_blood_pressure);
		ll_sugar			= (LinearLayout)findViewById(R.id.ll_sugar);
		ll_custom_keyboard = (LinearLayout)findViewById(R.id.ll_custom_keyboard);
		
		tv_blood_pressure	= (CrescoTextView)findViewById(R.id.tv_blood_pressure); 
		tv_sugar 			= (CrescoTextView)findViewById(R.id.tv_sugar);
		
		lv_measures 		= (ListView)findViewById(R.id.lv_measures);
		//elv_measures 		= (ExpandableListView)findViewById(R.id.elv_measures);
		//elv_measures 		= (AnimatedExpandableListView)findViewById(R.id.elv_measures);
		
		bt_showgraph = (CrescoTextView)findViewById(R.id.bt_showgraph);
		bt_add = (CrescoTextView)findViewById(R.id.bt_add);
		bt_export = (CrescoTextView)findViewById(R.id.bt_export);
		ll_add_pressure= (LinearLayout) findViewById(R.id.ll_add_pressure);
		ll_add_sugar = (LinearLayout) findViewById(R.id.ll_add_sugar);
		ll_elvlist = (LinearLayout) findViewById(R.id.ll_elvlist);

		
		ll_bt_add= (LinearLayout) findViewById(R.id.ll_bt_add);
		ll_bt_export = (LinearLayout) findViewById(R.id.ll_bt_export);
        ll_bt_showgraph = (LinearLayout) findViewById(R.id.ll_bt_showgraph);

        ll_pressure_header = (LinearLayout) findViewById(R.id.ll_pressure_header);
        ll_sugar_header = (LinearLayout) findViewById(R.id.ll_sugar_header);


        et_pressuredate = (CrescoEditText)findViewById(R.id.et_pressuredate);
		et_pressuretime = (CrescoEditText)findViewById(R.id.et_pressuretime);
		//sp_pressuresys = (Spinner)findViewById(R.id.sp_pressuresys);
		//sp_pressuredias = (Spinner)findViewById(R.id.sp_pressuredias);
		//sp_pressurepulse = (Spinner)findViewById(R.id.sp_pressurepulse);
		et_pressuresys = (CrescoEditText)findViewById(R.id.et_pressuresys);
		et_pressuredias = (CrescoEditText)findViewById(R.id.et_pressuredias);
		et_pressurepulse = (CrescoEditText)findViewById(R.id.et_pressurepulse);
		sp_pressurepos = (Spinner)findViewById(R.id.sp_pressurepos);
		sp_pressureloc = (Spinner)findViewById(R.id.sp_pressureloc);
		et_pressureremark= (CrescoEditText)findViewById(R.id.et_pressureremark);
		et_sugardate = (CrescoEditText)findViewById(R.id.et_sugardate); 
		et_sugartime = (CrescoEditText)findViewById(R.id.et_sugartime);
        //et_sugardate2 = (CrescoEditText)findViewById(R.id.et_sugardate2);
        et_sugartime2 = (CrescoEditText)findViewById(R.id.et_sugartime2);
		//sp_sugarfasting = (Spinner)findViewById(R.id.sp_sugarfasting);
		//sp_sugarpp = (Spinner)findViewById(R.id.sp_sugarpp);
		
		et_sugarfasting = (CrescoEditText)findViewById(R.id.et_sugarfasting);
		et_sugarpp = (CrescoEditText)findViewById(R.id.et_sugarpp);
		et_sugarremark1 = (CrescoEditText)findViewById(R.id.et_sugarremark1);
        et_sugarremark2 = (CrescoEditText)findViewById(R.id.et_sugarremark2);
		
		ll_main_graph = (LinearLayout)findViewById(R.id.ll_main_graph); 
		ll_graph = (LinearLayout)findViewById(R.id.ll_graph);
		ll_graph_date = (LinearLayout)findViewById(R.id.ll_graph_date);
		ll_cb_pressure = (LinearLayout)findViewById(R.id.ll_cb_pressure);
		ll_cb_sugar = (LinearLayout)findViewById(R.id.ll_cb_sugar);
		
		et_from_date = (CrescoEditText)findViewById(R.id.et_from_date);
		et_to_date = (CrescoEditText)findViewById(R.id.et_to_date);
		
		cb_sys 			= (CheckBox) findViewById(R.id.cb_sys);
		cb_dias 		= (CheckBox) findViewById(R.id.cb_dias);
		cb_pulse		= (CheckBox) findViewById(R.id.cb_pulse);
		cb_fasting 		= (CheckBox) findViewById(R.id.cb_fasting);
		cb_pp 			= (CheckBox) findViewById(R.id.cb_pp);
		
		tv_one = (CrescoTextView)findViewById(R.id.tv_one);
		tv_two = (CrescoTextView)findViewById(R.id.tv_two);
		tv_three = (CrescoTextView)findViewById(R.id.tv_three);
		tv_four = (CrescoTextView)findViewById(R.id.tv_four);
		tv_five = (CrescoTextView)findViewById(R.id.tv_five);
		tv_six = (CrescoTextView)findViewById(R.id.tv_six);
		tv_seven = (CrescoTextView)findViewById(R.id.tv_seven);
		tv_eight = (CrescoTextView)findViewById(R.id.tv_eight);
		tv_nine = (CrescoTextView)findViewById(R.id.tv_nine);
		tv_zero = (CrescoTextView)findViewById(R.id.tv_zero);
		tv_plus= (CrescoTextView)findViewById(R.id.tv_plus);
		tv_minus = (CrescoTextView)findViewById(R.id.tv_minus);
		tv_clear = (CrescoTextView)findViewById(R.id.tv_clear);
		tv_cancel = (CrescoTextView)findViewById(R.id.tv_cancel);
		tv_calci_header = (CrescoTextView)findViewById(R.id.tv_calci_header);
		
		//ll_adMob = (LinearLayout) findViewById(R.id.ll_adMob);
		
		//mListView = (ExpandingListView)findViewById(R.id.lv_measures);
		
		tv_one.setOnClickListener(this);
		tv_two.setOnClickListener(this); 
		tv_three.setOnClickListener(this);
		tv_four.setOnClickListener(this);
		tv_five.setOnClickListener(this);
		tv_six.setOnClickListener(this);
		tv_seven.setOnClickListener(this);
		tv_eight.setOnClickListener(this);
		tv_nine.setOnClickListener(this);
		tv_zero.setOnClickListener(this);
		tv_plus.setOnClickListener(this);
		tv_minus.setOnClickListener(this);
		tv_clear.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		 
		cb_sys.setOnClickListener(this);
		cb_dias.setOnClickListener(this);
		cb_pulse.setOnClickListener(this);
		cb_fasting.setOnClickListener(this);
		cb_pp.setOnClickListener(this);
		
		ll_blood_pressure.setOnClickListener(this);
		ll_sugar.setOnClickListener(this);
		
		lv_measures.setOnItemClickListener(this);
		bt_showgraph.setOnClickListener(this);
		bt_add.setOnClickListener(this);
		bt_export.setOnClickListener(this);

        ll_bt_add.setOnClickListener(this);
        ll_bt_export.setOnClickListener(this);
        ll_bt_showgraph.setOnClickListener(this);
		
		et_pressuredate.setOnClickListener(this);
		et_pressuretime.setOnClickListener(this);
		et_sugardate.setOnClickListener(this);
		et_sugartime.setOnClickListener(this);
       // et_sugardate2.setOnClickListener(this);
        et_sugartime2.setOnClickListener(this);
		et_from_date.setOnClickListener(this);
		et_to_date.setOnClickListener(this);
		
		et_pressuresys.setOnClickListener(this);
		et_pressuredias.setOnClickListener(this);
		et_pressurepulse.setOnClickListener(this);
		et_sugarfasting.setOnClickListener(this);
		et_sugarpp.setOnClickListener(this);
		
		et_pressureremark.setOnClickListener(this);
		et_sugarremark1.setOnClickListener(this);
        et_sugarremark2.setOnClickListener(this);
		
		//elv_measures.setOnGroupClickListener(this);
		profile_master= new Profile_Master(context);
		measurement_values = new Measurement_values(context);
		bp_master = new BloodPressure_Master(context);
		sugar_master = new Sugar_Master(context);
		measure_list = new ArrayList<Bundle>();
		
		profile_id = getIntent().getIntExtra("ProfileId", 0);
		
		profile_list = new ArrayList<Bundle>();
		profile_list = profile_master.getProfileByID(profile_id);
		
		
		for(int i = 0; i < profile_list.size(); i++)
		{
			profile_fname = profile_list.get(i).getString(Profile_Master.FIRST_NAME);
			profile_lname= profile_list.get(i).getString(Profile_Master.LAST_NAME);
		}
		super.tv_profilename.setText(profile_fname+" "+profile_lname);
		
		
		ll_blood_pressure.performClick();
		
		tv_blood_pressure.setTypeface(typeface);
		tv_sugar.setTypeface(typeface);
		bt_add.setTypeface(typeface);
		bt_export.setTypeface(typeface);
		bt_showgraph.setTypeface(typeface);
		cb_dias.setTypeface(typeface);
		cb_sys.setTypeface(typeface);
		cb_pulse.setTypeface(typeface);
		cb_fasting.setTypeface(typeface);
		cb_pp.setTypeface(typeface);
		
		/////////***
		//final Uri imageUri = data.getData();
//		final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//		final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//		iv_profileimage.setImageBitmap(selectedImage);
		//android.os.Environment.getExternalStorageDirectory().toString();
		
		//File imgFile = new  File(Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images/Profile"+profile_fname+".jpg");
        //public static String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
        File imgFile = new  File(Add_Profile.iconsStoragePath+"/Profile"+profile_id+".jpg");
		if(imgFile.exists())
		{

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
            {
                super.iv_profileimage.setBackgroundDrawable(null);
            }
            else
            {
                super.iv_profileimage.setBackground(null);
            }
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
		    Bitmap b = getRoundedShape(myBitmap);
		    super.iv_profileimage.setImageBitmap(b);
		}
		
		
		sp_list1 = new ArrayList<String>();
		sp_list1.add("Position");	
		sp_list1.add("Standing");
		sp_list1.add("Sleeping");
		sp_list1.add("Seated");
		sp_list1.add("Reclined");

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list1);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pressurepos.setAdapter(sAdapter);
		
		sp_list2 = new ArrayList<String>();
		sp_list2.add("Location");	
		sp_list2.add("Right Wrist");
		sp_list2.add("Left Wrist");
		sp_list2.add("Right Arm");
		sp_list2.add("Left Arm");
		sp_list2.add("Right Leg");
		sp_list2.add("Left Leg");

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list2);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pressureloc.setAdapter(sAdapter);
		
		/*
		sp_list3 = new ArrayList<String>();
		sp_list3.add("Systolic");
		for(int i = 100; i<=150; i++)
		{
			sp_list3.add(String.valueOf(i));
		}

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list3);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pressuresys.setAdapter(sAdapter);
		
		
		sp_list4 = new ArrayList<String>();
		sp_list4.add("Diastolic");
		for(int j = 60;j<=100; j++)
		{
			sp_list4.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list4);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pressuredias.setAdapter(sAdapter);
		
		sp_list5 = new ArrayList<String>();
		sp_list5.add("Pulse");
		for(int k = 65; k<=100; k++)
		{
			sp_list5.add(String.valueOf(k));
		}

		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list5);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_pressurepulse.setAdapter(sAdapter);
		
		
		sp_list6 = new ArrayList<String>();
		sp_list6.add("Fasting");
		for(int j = 60;j<=100; j++)
		{
			sp_list6.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list6);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_sugarfasting.setAdapter(sAdapter);
		
		sp_list7 = new ArrayList<String>();
		sp_list7.add("PP");
		for(int j = 100;j<=300; j++)
		{
			sp_list7.add(String.valueOf(j));
		}
		sAdapter = new SpinnerAdapter(this, R.layout.gender_row, sp_list7);
		sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
		sp_sugarpp.setAdapter(sAdapter);
		*/
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		
		mth = getMonth(month+1);
		
		String  d = null;
		
		if(day<10)
		{
			d = "0"+String.valueOf(day);
		}
		else
		{
			d= String.valueOf(day);
		}

		if(hour<10)
		{
			hr = "0"+String.valueOf(hour);
		}
		else
		{
			hr = String.valueOf(hour);
		}

		if(min<10)
		{
			mn = "0"+String.valueOf(min);
		}
		else
		{
			mn = String.valueOf(min);
		}

		//Log.v(TAG,"mth : "+mth +", month : "+month);
		
		et_pressuredate.setText(d+" "+mth+", "+year);
		et_pressuretime.setText(hr+":"+mn);
		
		et_sugardate.setText(d+" "+mth+", "+year);
		et_sugartime.setText(hr+":"+mn);


        //et_sugardate2.setText(d+" "+mth+", "+year);

        et_sugartime2.setText(hr+":"+mn);
		
		int m = month+1;
		String m1;
		
		if(m<10)
		{
			m1 = "0"+String.valueOf(m);
		}
		else
		{
			m1 = String.valueOf(m);
		}
		
		Date = year+"-"+m1+"-"+d;
		Time = hr+":"+mn+":00";
		
		//Log.v(TAG,"Date :  "+Date);
		//Log.v(TAG,"Time :  "+Time);
		
		/*super.iv_profileimage.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) 
			{
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				return false;
			}
		});*/
		
		is_addMeasureclicked = "false";
		
		

		
		lvSwipeDetector = new ListViewSwipeDetector();
		lv_measures.setOnTouchListener(lvSwipeDetector);
		

        ////////////////////
		measure_list = new ArrayList<Bundle>();
		measure_list = bp_master.getMeasurementListByProfile(profile_id);
		
		boolean  lb_showtoast;
		toast_sp = context.getSharedPreferences("TOAST",Context.MODE_PRIVATE);
		
		lb_showtoast = toast_sp.getBoolean("SHOW_TOAST_LIST", true);
		
		if(lb_showtoast == true)
		{
		
			final int interval = 2000; // 5 Second
			Handler handler = new Handler();
			Runnable runnable = new Runnable()
			{
			    public void run() 
			    {
			        //Toast.makeText(MyActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
			    	//if(adapter)
			    	if(measure_list.size() >0)
					{
			    		customToast("From_onCreate");
			    		
			    		set_toastCount("LIST_VIEW");
					}
			    }
			};
			
			handler.postAtTime(runnable, System.currentTimeMillis()+interval);
			handler.postDelayed(runnable, interval);
		}
	}
	
	
	public void customToast(String call)
{
    final SuperCardToast superCardToast = new SuperCardToast(this, SuperToast.Type.STANDARD);
    superCardToast.setDuration(SuperToast.Duration.EXTRA_LONG);

    if(call.equals("From_graph"))
    {
        superCardToast.setText("Tap a Parameter at the bottom to add/remove it from the graph");
    }
    else
    {
        superCardToast.setText("Tap a Reading to view/edit its details\nSwipe across a Reading to delete it");
    }
    //superCardToast.setButtonIcon(SuperToast.Icon.Dark.EDIT, "EDIT");
    //superCardToast.setOnClickWrapper(onClickWrapper);
    superCardToast.setBackground(R.color.cresco_gray);
    superCardToast.setTextColor(context.getResources().getColor(R.color.Gray));

    //superCardToast.setTextSize(15);
    superCardToast.setTypefaceStyle(0);
    superCardToast.setTouchToDismiss(true);
    superCardToast.show();
}

	@Override
	public void onClick(View v) 
	{
		//boolean checked = ((CheckBox) v).isChecked();
		super.onClick(v);
		
		switch (v.getId()) 
		{
			
			case R.id.ll_blood_pressure:
				
				//GOOGLE ANALYTICS Event Tracking  - Measurement view list
                if(DbHelper.USE_ANALYTICS)
                {
                    call_GoogleAnalytics_events("MeasureMent", "View_List",
                            "BloodPressure", 0);

                    call_GoogleAnalytics_userTimings("Load_Time", 5,
                            "List", "BloodPressure");
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement view list
				
				tabClicked = PRESSURE;
				
				tv_sugar.setTextColor(getResources().getColor(R.color.disabled_healthmate_green));
				tv_blood_pressure.setTextColor(getResources().getColor(R.color.White));
				
				FlipAnimation flipAnimation;
				
				rootLayout.clearAnimation();

                ll_sugar_header.setVisibility(View.GONE);
                ll_pressure_header.setVisibility(View.VISIBLE);
				
				if(ll_add_sugar.getVisibility() == View.VISIBLE)
				{
					flipAnimation = new FlipAnimation(ll_elvlist, ll_add_sugar, 0);
					
					ll_custom_keyboard.setVisibility(View.GONE);
					
					bt_showgraph.setVisibility(View.VISIBLE);
					bt_export.setVisibility(View.VISIBLE);
					ll_bt_export.setVisibility(View.VISIBLE);


					bt_add.setText(ADD_READING);
					bt_showgraph.setText(VIEW_GRAPH);
					
					bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
					bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
					//bt_add.setCompoundDrawablePadding(10);
					//bt_showgraph.setCompoundDrawablePadding(10);
					
					
					if (ll_elvlist.getVisibility() == View.GONE)
				    {
				        flipAnimation.reverse();
				    }
					
					rootLayout.startAnimation(flipAnimation);
				}
				
				
				
				if(ll_main_graph.getVisibility() == View.VISIBLE)
				{
					flipAnimation = new FlipAnimation(ll_elvlist, ll_main_graph, 1);
					
					bt_add.setVisibility(View.VISIBLE);
					bt_export.setVisibility(View.VISIBLE);
					ll_bt_add.setVisibility(View.VISIBLE);
					ll_bt_export.setVisibility(View.VISIBLE);
					bt_showgraph.setText(VIEW_GRAPH);
					bt_add.setText(ADD_READING);
					ll_graph_date.setVisibility(View.GONE);
					
					bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
					bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
					//bt_add.setCompoundDrawablePadding(10);
					//bt_showgraph.setCompoundDrawablePadding(10);
					//bt_showgraph.setPadding(70, 0, 0, 0);
					
					if (ll_elvlist.getVisibility() == View.GONE)
				    {
				        flipAnimation.reverse();
				    }
					
					rootLayout.startAnimation(flipAnimation);
					
				}

				
				ShowList_Measures(tabClicked);
				break;
				
			case R.id.ll_sugar:
				
				//GOOGLE ANALYTICS Event Tracking  - Measurement view list
                if(DbHelper.USE_ANALYTICS)
                {
                    call_GoogleAnalytics_events("MeasureMent", "View_List",
                            "BloodSugar", 0);

                    call_GoogleAnalytics_userTimings("Load_Time", 5,
                            "List", "BloodSugar");
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement view list
				
				tabClicked = SUGAR;
				
				tv_blood_pressure.setTextColor(getResources().getColor(R.color.disabled_healthmate_green));
				tv_sugar.setTextColor(getResources().getColor(R.color.White));

				rootLayout.clearAnimation();

                ll_sugar_header.setVisibility(View.VISIBLE);
                ll_pressure_header.setVisibility(View.GONE);
				
				if(ll_add_pressure.getVisibility() == View.VISIBLE)
				{
					flipAnimation = new FlipAnimation(ll_elvlist, ll_add_pressure, 0);
					
					ll_custom_keyboard.setVisibility(View.GONE);
					
					bt_showgraph.setVisibility(View.VISIBLE);
					bt_export.setVisibility(View.VISIBLE);

					ll_bt_export.setVisibility(View.VISIBLE);
					bt_add.setText(ADD_READING);
					bt_showgraph.setText(VIEW_GRAPH);
					
					bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
					bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
					//bt_add.setCompoundDrawablePadding(10);
					//bt_showgraph.setCompoundDrawablePadding(10);
					//bt_showgraph.setPadding(70, 0, 0, 0);
					
					if (ll_elvlist.getVisibility() == View.GONE)
				    {
				        flipAnimation.reverse();
				    }
					
					rootLayout.startAnimation(flipAnimation);
					
				}
				if(ll_main_graph.getVisibility() == View.VISIBLE)
				{
					flipAnimation = new FlipAnimation(ll_elvlist, ll_main_graph, 1);
					
					bt_add.setVisibility(View.VISIBLE);
					bt_export.setVisibility(View.VISIBLE);

					ll_bt_add.setVisibility(View.VISIBLE);
					ll_bt_export.setVisibility(View.VISIBLE);
					bt_showgraph.setText(VIEW_GRAPH);
					bt_add.setText(ADD_READING);
					ll_graph_date.setVisibility(View.GONE);
					
					bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
					bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null,  null);
					//bt_add.setCompoundDrawablePadding(10);
					//bt_showgraph.setCompoundDrawablePadding(10);
					//bt_showgraph.setPadding(70, 0, 0, 0);
					
					if (ll_elvlist.getVisibility() == View.GONE)
				    {
				        flipAnimation.reverse();
				    }
					
					rootLayout.startAnimation(flipAnimation);
					
				}
				
				ShowList_Measures(tabClicked);
				break;
				
			case R.id.bt_add:

				
				ll_custom_keyboard.setVisibility(View.GONE);
				super.adView.setVisibility(View.GONE);
				
				bt_showgraph.setVisibility(View.GONE);
				bt_export.setVisibility(View.GONE);

				ll_bt_export.setVisibility(View.GONE);
				rootLayout.clearAnimation();
				
				if(ll_main_graph.getVisibility() == View.VISIBLE)
				{
					ll_main_graph.setVisibility(View.GONE);
					//Log.v(TAG,"visibility1 gone sugar");
				}
				
				
				OpenAddMeasures();
				break;
				
			case R.id.bt_showgraph:


				bt_add.setVisibility(View.GONE);
				bt_export.setVisibility(View.GONE);
				
				ll_bt_add.setVisibility(View.GONE);
				ll_bt_export.setVisibility(View.GONE);
                ll_sugar_header.setVisibility(View.GONE);
                ll_pressure_header.setVisibility(View.GONE);

				rootLayout.clearAnimation();
				
				if(ll_add_pressure.getVisibility() == View.VISIBLE)
				{
					ll_add_pressure.clearAnimation();
					ll_add_pressure.setVisibility(View.GONE);
					//Log.v(TAG,"visibility2 gones pressure");
				}
				if(ll_add_sugar.getVisibility() == View.VISIBLE)
				{
					ll_add_sugar.clearAnimation();
					ll_add_sugar.setVisibility(View.GONE);
					//Log.v(TAG,"visibility2 gone sugar");
				}
				et_from_date.setText("");
				et_to_date.setText("");
				
				openGraph("", "");
				break;
				
			case R.id.et_pressuredate :
				
				setDate(v);
				break;
				
			case R.id.et_pressuretime :
				
				setTime(v);
				break;
				
			case R.id.et_sugardate :
	
				setDate(v);
				break;
	
			case R.id.et_sugartime :
	
				setTime(v);
				break;

//            case R.id.et_sugardate2 :
//
//                setDate2(v);
//                break;

            case R.id.et_sugartime2 :

                setTime2(v);
                break;
				
			case R.id.et_from_date :
				
				setDateFrom(v);
				break;
				
			case R.id.et_to_date :
				
				setDateTo(v);
				break;
				
			    
			 case R.id.cb_dias:
			      if (cb_dias.isChecked())
			      {
			    	  ischecked_dias = 1;
			    	  dateRange();
			      }        
			      else
			      {
			    	  ischecked_dias = 0;
			    	  dateRange();
			      }
			                
			      break;
			  
			 case R.id.cb_sys:
			            
				  if (cb_sys.isChecked())
				  {
					  ischecked_sys = 1; 
					  dateRange();
				  }
				  else
				  {
					  ischecked_sys = 0;
					  dateRange();
				  }
			                
			      break;
			      
			 case R.id.cb_pulse:
		            
				  if (cb_pulse.isChecked())
				  {
					  ischecked_pulse = 1;
					  dateRange();
				  }
				  else
				  {
					  ischecked_pulse = 0;
					  dateRange();
				  }
			             
			      break;
			      
			 case R.id.cb_fasting:
		            
				  if (cb_fasting.isChecked())
				  {
					  ischecked_fast = 1;
					  dateRange();
				  }
				  else
				  {
					  ischecked_fast = 0;
					  dateRange();
				  }
			             
			      break;
			      
			 case R.id.cb_pp:
		            
				  if (cb_pp.isChecked())
				  {
					  ischecked_pp = 1;
					  dateRange();
				  }
				  else
				  {
					  ischecked_pp = 0;
					  dateRange();
				  }
			             
			      break;
			      
			      //////
			 case R.id.iv_appicon :
				 
				 Intent i1 = new Intent(this, AdnoteSettings.class);
				 startActivity(i1);
				 
				 break;
			 case R.id.ll_profilename :
				 
				 Intent i = new Intent(this, Switch_Profile.class);
				 startActivity(i);

				 
				 break;

            case R.id.iv_profileimage :

                Intent intent = new Intent(this, Add_Profile.class);
                intent.putExtra(Grid_Adapter.PROFILE_OPERATION, "Edit");
                intent.putExtra(Grid_Adapter.PROFILE_ID, profile_id);
                startActivityForResult(intent, REQ_CODE);
                finish();


                break;
				 
			 case R.id.bt_export:
					
					showDateRangeDialog();
					
					break;
				 
			 case R.id.et_from:
					
					setDateFrom1(v);
					break;
					
			case R.id.et_to:
					
					setDateTo1(v);
					break;
					
			case R.id.cb_csv:
				
				//GOOGLE ANALYTICS Event Tracking  - Measurement delete
                if(DbHelper.USE_ANALYTICS)
                {
                    if (tabClicked.equals(PRESSURE)) {
                        call_GoogleAnalytics_events("MeasureMent", "Export",
                                "BloodPressure-CSV", 0);
                    } else {
                        call_GoogleAnalytics_events("MeasureMent", "Export",
                                "BloodSugar-CSV", 0);
                    }
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement delete
					
					if(!(confirmDialog.et_from.getText().toString().equals("")) && !(confirmDialog.et_to.getText().toString().equals("")))
					{
						confirmDialog.dismiss();
						
						ls_docType = "CSV";
						proAsync = new Task_createPDF("csv");
						proAsync.execute();
					}
					else
					{
						Toast.makeText(this, "Enter Date range.",
								Toast.LENGTH_SHORT).show();
					}
					
					break;

			case R.id.cb_pdf:
				
				//GOOGLE ANALYTICS Event Tracking  - Measurement delete
                if(DbHelper.USE_ANALYTICS)
                {
                    if (tabClicked.equals(PRESSURE)) {
                        call_GoogleAnalytics_events("MeasureMent", "Export",
                                "BloodPressure-PDF", 0);
                    } else {
                        call_GoogleAnalytics_events("MeasureMent", "Export",
                                "BloodSugar-PDF", 0);
                    }
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement delete
					
					if(!(confirmDialog.et_from.getText().toString().equals("")) && !(confirmDialog.et_to.getText().toString().equals("")))
					{
						confirmDialog.dismiss();
						
						ls_docType = "PDF";
						
						proAsync = new Task_createPDF("pdf");
						proAsync.execute();
						
					}
					else
					{
						Toast.makeText(this, "Enter Date range.",
								Toast.LENGTH_SHORT).show();
					}
					
					break;

			case R.id.cb_share:

                if(DbHelper.USE_ANALYTICS)
                {

                    //GOOGLE ANALYTICS Event Tracking  - Measurement exported file share
                    if (tabClicked.equals(PRESSURE)) {
                        if (ls_docType.equals("PDF")) {
                            call_GoogleAnalytics_events("MeasureMent", "Share",
                                    "BloodPressure-PDF", 0);
                        } else {
                            call_GoogleAnalytics_events("MeasureMent", "Share",
                                    "BloodPressure-CSV", 0);
                        }
                    } else {

                        if (ls_docType.equals("PDF")) {
                            call_GoogleAnalytics_events("MeasureMent", "Share",
                                    "BloodSugar-PDF", 0);
                        } else {
                            call_GoogleAnalytics_events("MeasureMent", "Share",
                                    "BloodSugar-CSV", 0);
                        }

                    }
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement exported file share
				
				confirmDialog.dismiss();
				
				shareExported_file();
				
				break;
				
			case R.id.cb_open:
				
				//GOOGLE ANALYTICS Event Tracking  - Measurement exported file open
                if(DbHelper.USE_ANALYTICS)
                {
                    if (tabClicked.equals(PRESSURE)) {
                        if (ls_docType.equals("PDF")) {
                            call_GoogleAnalytics_events("MeasureMent", "Open",
                                    "BloodPressure-PDF", 0);
                        } else {
                            call_GoogleAnalytics_events("MeasureMent", "Open",
                                    "BloodPressure-CSV", 0);
                        }
                    } else {

                        if (ls_docType.equals("PDF")) {
                            call_GoogleAnalytics_events("MeasureMent", "Open",
                                    "BloodSugar-PDF", 0);
                        } else {
                            call_GoogleAnalytics_events("MeasureMent", "Open",
                                    "BloodSugar-CSV", 0);
                        }

                    }
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement exported file open
	
				confirmDialog.dismiss();
				
				openExported_file();
	
				break;
				
			case R.id.cb_yes:
				
				confirmDialog.dismiss();
				
				deleteRecord();
				
				break;
				
			case R.id.cb_no:
	
				confirmDialog.dismiss();
	
				break;
				
			case R.id.et_pressuresys:
				
				
				ll_custom_keyboard.setVisibility(View.VISIBLE);
				edit_view = et_pressuresys;
				editText_value = et_pressuresys.getText().toString();
				
				
				tv_calci_header.setText("Systolic (Upper)");
	
				break;
				
			case R.id.et_pressuredias:
							
				ll_custom_keyboard.setVisibility(View.VISIBLE);
				edit_view = et_pressuredias;
				editText_value = et_pressuredias.getText().toString();
				
				tv_calci_header.setText("Diastolic (Lower)");
				
				break;
							
			case R.id.et_pressurepulse:
				
				ll_custom_keyboard.setVisibility(View.VISIBLE);
				edit_view = et_pressurepulse;
				editText_value = et_pressurepulse.getText().toString();
				
				tv_calci_header.setText("Pulse");
			
				break;
				
			case R.id.et_pressureremark:
				
				//et_pressureremark.setFocusableInTouchMode(true);
				
				//ll_adMob.setVisibility(View.GONE);
				ll_custom_keyboard.setVisibility(View.GONE);
				
				break;
				
				
			case R.id.et_sugarfasting:
				
				ll_custom_keyboard.setVisibility(View.VISIBLE);
				edit_view = et_sugarfasting;
				editText_value = et_sugarfasting.getText().toString();
				
				tv_calci_header.setText("Fasting");
			
				break;
				
			case R.id.et_sugarpp:
				
				ll_custom_keyboard.setVisibility(View.VISIBLE);
				edit_view = et_sugarpp;
				editText_value = et_sugarpp.getText().toString();
				
				tv_calci_header.setText("PP");
			
				break;
				
			case R.id.et_sugarremark1:
				
				ll_custom_keyboard.setVisibility(View.GONE);
				
				break;

            case R.id.et_sugarremark2:

                ll_custom_keyboard.setVisibility(View.GONE);

                break;
					
				 ////////
				
				
			case R.id.tv_one:
				
				set_editText(1);
	
				break;
			
			case R.id.tv_two:
				
				set_editText(2);
	
				break;
				
			case R.id.tv_three:
				
				set_editText(3);
	
				break;
				
			case R.id.tv_four:
				
				set_editText(4);
			
				break;
				
			case R.id.tv_five:
				
				set_editText(5);
			
				break;
				
			case R.id.tv_six:
				
				set_editText(6);
			
				break;
				
			case R.id.tv_seven:
	
				set_editText(7);

				break;
	
			case R.id.tv_eight:
	
				set_editText(8);

				break;
				
			case R.id.tv_nine:
				
				set_editText(9);

				break;
				
			case R.id.tv_zero:
				
				set_editText(0);

				break;
				
			case R.id.tv_plus:
				
				set_editTextadd();

				break;
			
			case R.id.tv_minus:
				
				set_editTextminus();
				 break;
				
			case R.id.tv_clear:
				
				set_editTextclear();

				break;
				
			case R.id.tv_cancel:
				
				set_editTextcancel();

				break;
				
			case R.id.cb_shareApp:
				
				confirmDialog.dismiss();
				
				shareApp(context);
				
				break;
				
			case R.id.cb_rate:
				
				confirmDialog.dismiss();
				
				rateApp(context);
				
				break;
		}
		
	}
	
	public void OpenAddMeasures()
	{
		
		//Log.v(TAG,"group_id :"+group_id);
		
		//Intent i = new Intent(this, Add_Readings.class);
		//i.putExtra("Profile_ID", profile_id);
		//i.putExtra("tabClicked", tabClicked);
		//startActivityForResult(i, REQ_CODE);
		
		
		
		/*if (elv_measures.isGroupExpanded(group_id))
		{
			list_hashmap.get(group_id).put("expand", "N");
			//elv_measures.collapseGroup(group_id);
			
			elv_measures.collapseGroupWithAnimation(group_id);
		}*/
		
		
		/*//ExpandAnimation expandAni = new ExpandAnimation(ll_add_pressure, 500);

            // Start the animation on the toolbar
            //ll_add_pressure.startAnimation(expandAni);
			
			//expand2(ll_add_pressure);
			//ll_add_pressure.startAnimation(slide_down);
			//ll_add_pressure.setVisibility(View.VISIBLE);
			//ll_add_sugar.setVisibility(View.GONE);
			
		}
		*/
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		
		mth = getMonth(month+1);
		
		String  d = null;
		
		if(day<10)
		{
			d = "0"+String.valueOf(day);
		}
		else
		{
			d= String.valueOf(day);
		}

		if(hour<10)
		{
			hr = "0"+String.valueOf(hour);
		}
		else
		{
			hr = String.valueOf(hour);
		}

		if(min<10)
		{
			mn = "0"+String.valueOf(min);
		}
		else
		{
			mn = String.valueOf(min);
		}
		
		
		//Log.v(TAG, "date in add readings :" + day+" "+mth+", "+year);
		//Log.v(TAG, "time in add readings :" + hr+":"+mn);
		
		int m = month+1;
		
		String m1;
		
		if(m<10)
		{
			m1 = "0"+String.valueOf(m);
		}
		else
		{
			m1 = String.valueOf(m);
		}
		
		
		Date = year+"-"+m1+"-"+d;
		Time = hr+":"+mn+":00";
		//if(ll_add_pressure.getVisibility() == View.GONE && ll_add_sugar.getVisibility() == View.GONE)
		//{
		is_data_edit = false;
		
		et_pressuredate.setText(d+" "+mth+", "+year);
		et_pressuretime.setText(hr+":"+mn);
		
		et_pressureremark.setText("");
		
		et_pressuresys.setText("");
		et_pressuredias.setText("");
		et_pressurepulse.setText("");
		
		//et_pressuresys.setHint("Systolic");
		//et_pressuredias.setHint("Diastolic");
		//et_pressurepulse.setHint("Pulse");
		
		
		//sp_pressuresys.setSelection(0);
		//sp_pressuredias.setSelection(0);
		//sp_pressurepulse.setSelection(0);
		sp_pressurepos.setSelection(0);
		sp_pressureloc.setSelection(0);
		
		
		
		et_sugarremark1.setText("");
        et_sugarremark2.setText("");
		
		et_sugarfasting.setText("");
		et_sugarpp.setText("");
		//sp_sugarfasting.setSelection(0);
		//sp_sugarpp.setSelection(0);
		
		et_sugardate.setText(d+" "+mth+", "+year);
		et_sugartime.setText(hr+":"+mn);

       // et_sugardate2.setText(d+"\t"+mth+",\t"+year);
        et_sugartime2.setText(hr+":"+mn);
		
			final View v;
			
		
			if(tabClicked.equals(PRESSURE))
			{
				v = ll_add_pressure;
			}
			else
			{
				v = ll_add_sugar;
			}
			
			
			FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, v, 0);
			
			if (ll_elvlist.getVisibility() == View.GONE)
		    {
		        flipAnimation.reverse();
		    }
			
			rootLayout.startAnimation(flipAnimation);
			
			if(v.getVisibility() == View.VISIBLE)
			{
				bt_add.setText(ADD_READING);
				bt_showgraph.setVisibility(View.VISIBLE);
				bt_export.setVisibility(View.VISIBLE);
				ll_bt_export.setVisibility(View.VISIBLE);
				
				super.adView.setVisibility(View.VISIBLE);
				
				bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
				bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
				//bt_add.setCompoundDrawablePadding(10);
				//bt_showgraph.setCompoundDrawablePadding(10);
				//bt_showgraph.setPadding(70, 0, 0, 0);
				
			}
			else
			{
				 bt_add.setText(VIEW_LIST);
				 bt_add.setCompoundDrawablesWithIntrinsicBounds(list, null, null, null);
				 //bt_add.setCompoundDrawablePadding(10);
				 
				//GOOGLE ANALYTICS Event Tracking  - Measurement Add
                if(DbHelper.USE_ANALYTICS)
                {
                    if (tabClicked.equals(PRESSURE)) {
                        call_GoogleAnalytics_events("MeasureMent", "Add",
                                "BloodPressure", 0);
                    } else {
                        call_GoogleAnalytics_events("MeasureMent", "Add",
                                "BloodSugar", 0);
                    }
                }
				//GOOGLE ANALYTICS Event Tracking  - Measurement Add

                show_toast();
			}
			
		
	}

    public void show_toast()
    {
        SuperToast superToast = new SuperToast(MeasurementList_Profile.this);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setText("Once you are done, press back to save Reading");
        superToast.setTextColor(context.getResources().getColor(R.color.white));
        superToast.setBackground(R.color.healthmate_green);
        //superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        superToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        superToast.show();
    }

	
	public void ShowList_Measures(final String tabClicked)
	{
        setAdapter();
        /*left_in = AnimationUtils.loadAnimation(context,R.anim.push_left_in);
        left_out = AnimationUtils.loadAnimation(context,R.anim.push_right_out);

        //lv_measures.setVisibility(View.INVISIBLE);
        left_out.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {}

            @Override
            public void onAnimationRepeat(Animation animation)
            {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                try
                {
                    lv_measures.setVisibility(View.GONE);
                    lv_measures.startAnimation(left_in);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.d("abcd", " " + e);
                }
            }
        });

        left_in.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {}

            @Override
            public void onAnimationRepeat(Animation animation)
            {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                try
                {
                    lv_measures.setVisibility(View.VISIBLE);
                    //setAdapter();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.d("abcd", " " + e);
                }
            }
        });*/



        //setAdapter();
		
		//EndlessAdapter adp = new EndlessAdapter(this, createItems(mult), measure_list, R.layout.measurement_row);
		//lv_measures.setLoadingView(R.layout.loading_layout);
		//lv_measures.setAdapter(adp);
		//lv_measures.setListener(MeasurementList_Profile.this);
		
//			mHandler = new Handler();
//	        //mAdapter = new SimpleAdapter(context, android.R.layout.simple_list_item_1);
//			
//			mAdapter = new SimpleAdapter(MeasurementList_Profile.this, measure_list);
//	        mFooterView = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
//	        lv_measures.addFooterView(mFooterView, null, false);
//	        lv_measures.setAdapter(mAdapter);
//	        lv_measures.setOnScrollListener(MeasurementList_Profile.this);
		

		 
		 //Log.v(TAG,"measure_list :"+measure_list.toString());
		
		// adapter = new MeasurementListAdapter(this, measure_list, tabClicked);
		 
		 //Task t = new Ta
		 //lv_measures.setAdapter(adapter);
		 
		//expandableListView
		//initializeAdapter(measure_list);
		
		//elv_measures.setAdapter(MyElvAdapter);
	}
	
	public void setAdapter()
	{

		
		 if(tabClicked.equals(PRESSURE))
		 {
			 
			 measure_list = new ArrayList<Bundle>();
			 measure_list = bp_master.getMeasurementListByProfile(profile_id);
			 
			 //Log.v(TAG,"measure_list :"+measure_list.toString());
			 
			 adapter = new MeasurementListAdapter(MeasurementList_Profile.this, measure_list, PRESSURE);
				    	
			 lv_measures.setAdapter(adapter);
			 
			 
			 
			 /*
			  * // loading more data in list
			 mHandler = new Handler();
			 mAdapter = new SimpleAdapter(MeasurementList_Profile.this, measure_list);
			 mFooterView = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
			 lv_measures.addFooterView(mFooterView, null, false);
			 lv_measures.setAdapter(mAdapter);
			 lv_measures.setOnScrollListener(MeasurementList_Profile.this);
			 */
			 
			 ///////
			 
			/* measure_list = new ArrayList<Bundle>();
			 measure_list = bp_master.getMeasurementListByProfile(profile_id);
			 Bundle b;
			 newlist = new ArrayList<ExpandableListItem>();
			 
			 for (int i =0; i< measure_list.size(); i++)
			 {
				 b = new Bundle();
				 
				 b = measure_list.get(i);
				 
				exp = new ExpandableListItem(b);
				exp.setCollapsedHeight(100);
				newlist.add(exp);
				//exp.setCollapsedHeight(200);
			 }
			 
			 CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.measurement_row, newlist);

		    // mListView = (ExpandingListView)findViewById(R.id.lv_measures);
		     mListView.setAdapter(adapter);
		     mListView.setDivider(null);
		     */
		     
				  /////////
		 }
		 else if(tabClicked.equals(SUGAR))
		 {
			  ///normal lisview///
			 measure_list = new ArrayList<Bundle>();
			 measure_list = sugar_master.getMeasurementListByProfile(profile_id);
			 
			 sugar_adapter = new SugarListAdapter(this, measure_list);
			 
			 lv_measures.setAdapter(sugar_adapter);
			 
			 
			 
			 /*// loading more data in list///
			 mHandler = new Handler();
			 sugar_adapter = new SugarListAdapter(MeasurementList_Profile.this, measure_list);
			 mFooterView = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
			 lv_measures.addFooterView(mFooterView, null, false);
			 lv_measures.setAdapter(sugar_adapter);
			 lv_measures.setOnScrollListener(MeasurementList_Profile.this);*/
		 } 
		 //lv_measures.setOnScrollListener(MeasurementList_Profile.this);
		 
		 //SHOW SHARE/rATE DIALOG
		 
		 //show_share_RateDailog();
	}
	
	public void openGraph(String from, String to)
	{

		FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, ll_main_graph, 1);
		
		
		if (ll_elvlist.getVisibility() == View.GONE)
	    {
	        flipAnimation.reverse();
	    }
		
			rootLayout.startAnimation(flipAnimation);
		
		if(ll_main_graph.getVisibility() == View.VISIBLE)
		{
			bt_showgraph.setText(VIEW_GRAPH);
			
			bt_add.setVisibility(View.VISIBLE);
			bt_export.setVisibility(View.VISIBLE);
			ll_bt_add.setVisibility(View.VISIBLE);
			ll_bt_export.setVisibility(View.VISIBLE);
			ll_graph_date.setVisibility(View.GONE);
			
			bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
			bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
			//bt_add.setCompoundDrawablePadding(10);
			//bt_showgraph.setCompoundDrawablePadding(10);

            if(tabClicked.equals(PRESSURE)) {
                ll_sugar_header.setVisibility(View.GONE);
                ll_pressure_header.setVisibility(View.VISIBLE);
            }
            else
            {
                ll_sugar_header.setVisibility(View.VISIBLE);
                ll_pressure_header.setVisibility(View.GONE);
            }
			
		}
		
		else
		{
			//GOOGLE ANALYTICS Event Tracking  - Measurement view graph
            if(DbHelper.USE_ANALYTICS)
            {
                if (tabClicked.equals(PRESSURE)) {
                    call_GoogleAnalytics_events("MeasureMent", "View_Graph",
                            "BloodPressure", 0);

                    call_GoogleAnalytics_userTimings("Load_Time", 5,
                            "Graph", "BloodPressure");
                } else {
                    call_GoogleAnalytics_events("MeasureMent", "View_Graph",
                            "BloodSugar", 0);

                    call_GoogleAnalytics_userTimings("Load_Time", 5,
                            "Graph", "BloodSugar");
                }
            }
			//GOOGLE ANALYTICS Event Tracking  - Measurement Add
			 
			 bt_showgraph.setText("View List");
			 
			 ll_graph_date.setVisibility(View.VISIBLE);
			 ll_graph.removeAllViews();
			 
			bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(list, null, null, null);
				
			//bt_showgraph.setPadding(70, 0, 0, 0);
			
			ischecked_sys = 1; 
			ischecked_dias = 1; 
			ischecked_pulse =1;
			ischecked_fast =  1; 
			ischecked_pp = 1;
			
			cb_dias.setChecked(true);
			cb_sys.setChecked(true);
			cb_pulse.setChecked(true);
			cb_fasting.setChecked(true);
			cb_pp.setChecked(true);
			
			
			measure_list = new ArrayList<Bundle>();
			
			if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
		    {
				measure_list = bp_master.getMeasurementListByProfile(profile_id);
		    }
		    else
		    {
		    	measure_list = sugar_master.getMeasurementListByProfile(profile_id);
		    }
		    
		    
			
			if(measure_list.size() > 0 )
		    {
				
				/////////////////////////
				boolean  lb_showtoast;
				toast_sp = context.getSharedPreferences("TOAST",Context.MODE_PRIVATE);
				
				lb_showtoast = toast_sp.getBoolean("SHOW_TOAST_GRAPH", true);
				
				if(lb_showtoast == true)
				{
					final int interval = 2000; // 5 Second
					Handler handler = new Handler();
					Runnable runnable = new Runnable()
					{
					    public void run() 
					    {
					        //Toast.makeText(MyActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
					    	customToast("From_graph");
					    	
					    	set_toastCount("GRAPH");
					    }
					};
					
					handler.postAtTime(runnable, System.currentTimeMillis()+interval);
					handler.postDelayed(runnable, interval);
				}
				////////////////////////
		    
			    String default_from, default_to;
                if(tabClicked.equals(PRESSURE)) {
                    default_from = measure_list.get(0).getString("date");
                }
                else
                {
                    default_from = measure_list.get(0).getString(Sugar_Master.MSR_DATE);
                }

			    
			    String date[] =default_from.split("-");
				String d = date[2];
				String m = getMonth(Integer.parseInt(date[1]));
				String y= date[0];
				String date1 = d+" "+m+", "+y;

                if(tabClicked.equals(PRESSURE)) {

                    default_to = measure_list.get(measure_list.size() - 1).getString("date");
                }
                else
                {
                    default_to = measure_list.get(measure_list.size() - 1).getString(Sugar_Master.MSR_DATE);
                }

                fromDate = default_to;
                toDate = default_from;

                //Log.v(TAG,"default_to"+toDate);
				
				String date2[] =default_to.split("-");
				String d1 = date2[2];
				String m1 = getMonth(Integer.parseInt(date2[1]));
				String y1= date[0];
				String date3 = d1+" "+m1+", "+y1;
				
				et_from_date.setText(date3);
				et_to_date.setText(date1);
				
		    }
			
			showGraphByDate(from, to);
		
			/*if(tabClicked.equals(PRESSURE))
			{
				alist = new ArrayList<Bundle>();
				alist = bp_master.getMeasurementList(profile_id, from, to);
				
				//Log.v(TAG,"List :"+alist.toString());
				
				diastoliclist = new int[alist.size()];
				systoliclist = new int[alist.size()];
				pulselist = new int[alist.size()];
				dates = new String[alist.size()];
					
				size = alist.size();
				for (int i = 0; i<alist.size(); i++)
				{
					diastoliclist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM1_VALUE));
						
					systoliclist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM2_VALUE));
					pulselist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM3_VALUE));
						
					dates[i] = alist.get(i).getString(BloodPressure_Master.MSR_TS).trim();
				}
					
					
				intent.putExtra(SIZE, String.valueOf(alist.size()));
				//intent.putExtra(DIASTOLIC_LIST, diastoliclist);
				//intent.putExtra(SYSTOLIC_LIST, systoliclist);
				//intent.putExtra(PULSE_LIST, pulselist);
				//intent.putExtra(DATE_LIST, dates);
				//startActivity(intent);
				
				
				openChartPressure(diastoliclist, systoliclist, pulselist, dates, size);
			}
			else if(tabClicked.equals(SUGAR))
			{
			 
			
			 	 alist = new ArrayList<Bundle>();
				 alist = sugar_master.getMeasurementList(profile_id);
				 
				 Log.v(TAG,"list sugar : "+alist.toString());
			 
				 fastinglist = new int[alist.size()];
				 pplist = new int[alist.size()];
				 dates = new String[alist.size()];
				 String fast, pp;
				 
				 size = alist.size();
				 	
				 for (int i = 0; i<alist.size(); i++)
				 {
					 fast = alist.get(i).getString(Sugar_Master.PM4_VALUE);
					 pp = alist.get(i).getString(Sugar_Master.PM5_VALUE);
					 
					 if(fast == null || fast.equals(""))
					 {
						 fast = "0";
					 }
					 fastinglist[i] = Integer.parseInt(fast);
					 
					 if(pp == null || pp.equals(""))
					 {
						 pp = "0";
					 }
					 pplist[i] = Integer.parseInt(pp);
					//fastinglist[i] = Integer.parseInt(alist.get(i).getString(Sugar_Master.PM4_VALUE));
							
					//pplist[i] = Integer.parseInt(alist.get(i).getString(Sugar_Master.PM5_VALUE));
						
					dates[i] = alist.get(i).getString(Sugar_Master.PM4_TS).trim();
					Log.v(TAG,"date -"+i+":"+dates[i]);
				 }
					
					
					//intent.putExtra(SIZE, String.valueOf(alist.size()));
					//intent.putExtra(FASTING_LIST, fastinglist);
					//intent.putExtra(PP_LIST, pplist);
					//intent.putExtra(DATE_LIST, dates);
					//startActivity(intent);
					//openChartSugar(fastinglist, pplist, dates, size);
			} */
		}
	}
	
	
	private void openChartPressure(int[] diastoliclist, int[] systoliclist, int[] pulselist, String[] dates, int size)
	{
		XYMultipleSeriesDataset dataset1 = new XYMultipleSeriesDataset();
		
		XYSeries diasSeries = new XYSeries("Diastolic");
	    XYSeries sysSeries = new XYSeries("Systolic");
	    XYSeries pulseSeries = new XYSeries("Pulse Rate");
	    XYSeries s = new XYSeries(" ");
	    dataset1.clear();
	    
		    for(int i=0;i<size;i++)
		    {
		    	diasSeries.add(i,diastoliclist[i]);
		    	sysSeries.add(i,systoliclist[i]);
		    	pulseSeries.add(i,pulselist[i]);
		    }
		    
		    ///
		    if(ischecked_dias ==1)
		    {
		    	dataset1.addSeries(diasSeries);
		    }
		    if(ischecked_sys ==1)
		    {
		    	dataset1.addSeries(sysSeries);
		    }
		    if(ischecked_pulse == 1)
		    {
		    	dataset1.addSeries(pulseSeries);
		    }
		    
		    if(ischecked_dias == 0 && ischecked_sys == 0 && ischecked_pulse == 0)
		    {
		    	dataset1.addSeries(s);
		    }
		    
		    //
		    //dataset1.addSeries(diasSeries);
		    //dataset1.addSeries(sysSeries);
		    //dataset1.addSeries(pulseSeries);
	    
	    
	    XYSeriesRenderer diasRenderer = new XYSeriesRenderer();
	    
	    diasRenderer.setColor(getResources().getColor(R.color.diastolic));
	    diasRenderer.setFillPoints(true);
	    diasRenderer.setDisplayChartValues(true);
	    diasRenderer.setChartValuesTextSize(30);
	    diasRenderer.setChartValuesTextAlign(Align.RIGHT);
	    
	    XYSeriesRenderer sysRenderer = new XYSeriesRenderer();
	    
	    sysRenderer.setColor(getResources().getColor(R.color.systolic));
	    sysRenderer.setFillPoints(true);
	    sysRenderer.setDisplayChartValues(true);
	    sysRenderer.setChartValuesTextSize(30);
	    sysRenderer.setChartValuesTextAlign(Align.RIGHT);
	    
	    XYSeriesRenderer pulseRenderer = new XYSeriesRenderer();
	    
	    pulseRenderer.setColor(getResources().getColor(R.color.pulse));
	    pulseRenderer.setFillPoints(true);
	    pulseRenderer.setDisplayChartValues(true);
	    pulseRenderer.setChartValuesTextSize(30);
	    pulseRenderer.setChartValuesTextAlign(Align.RIGHT);
	    
	    XYSeriesRenderer r = new XYSeriesRenderer();
	    
	    r.setColor(Color.GREEN);
	    r.setFillPoints(true);
	    r.setDisplayChartValues(true);
	    r.setChartValuesTextSize(30);
	    r.setChartValuesTextAlign(Align.RIGHT);
	    
	   
	    XYMultipleSeriesRenderer multiRenderer1 = new XYMultipleSeriesRenderer();
	    multiRenderer1.removeAllRenderers();
	    
	    int[] mar = new int[] {40,40,40,40};
	    multiRenderer1.setChartTitle("Blood Pressure Measurement");
	    
	    multiRenderer1.setAxesColor(getResources().getColor(R.color.healthmate_green));
	    multiRenderer1.setApplyBackgroundColor(true);
	    multiRenderer1.setBackgroundColor(Color.WHITE);
	    multiRenderer1.setMargins(mar);
	    multiRenderer1.setMarginsColor(Color.WHITE);
	    multiRenderer1.setAxisTitleTextSize(30);
	    multiRenderer1.setChartTitleTextSize(30);
	    multiRenderer1.setLabelsTextSize(23);
	    multiRenderer1.setLegendTextSize(0);
	    
	    multiRenderer1.setXLabelsPadding(10);
	    multiRenderer1.setLegendHeight(0);
	    multiRenderer1.setBarSpacing(4); 
	    multiRenderer1.setShowGrid(true);
	    multiRenderer1.setBarWidth(20);
	    multiRenderer1.setXAxisMin(-0.1);
	    multiRenderer1.setYAxisMin(-1);
	    multiRenderer1.setXAxisMax(3);
	    multiRenderer1.setFitLegend(true);
	    multiRenderer1.setXLabels(0);
	    
	    
	    multiRenderer1.setXLabelsColor(getResources().getColor(R.color.healthmate_green));
	    multiRenderer1.setYLabelsColor(0, getResources().getColor(R.color.healthmate_green));
	    multiRenderer1.setYLabelsAlign(Align.RIGHT);
	    multiRenderer1.setYLabelsPadding(5.0f);
	    
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
	    multiRenderer1.setTextTypeface(typeface);
	    
	    
	    for(int i=0; i< size;i++)
	    {
	    	multiRenderer1.addXTextLabel(i , dates[i]);
	    }
	    
	    /////
	    if(ischecked_dias == 1)
	    {
	    	multiRenderer1.addSeriesRenderer(diasRenderer);
	    }
	    if(ischecked_sys == 1)
	    {
	    	multiRenderer1.addSeriesRenderer(sysRenderer);
	    }
	    if(ischecked_pulse == 1)
	    {
	    	multiRenderer1.addSeriesRenderer(pulseRenderer);
	    }
	    
	    if(ischecked_dias == 0 && ischecked_sys == 0 && ischecked_pulse == 0)
	    {
	    	multiRenderer1.addSeriesRenderer(r);
	    }
	    
	    
	    /////
	    //multiRenderer1.addSeriesRenderer(diasRenderer);
	   // multiRenderer1.addSeriesRenderer(sysRenderer);
	    //multiRenderer1.addSeriesRenderer(pulseRenderer);
	    
	    View view = ChartFactory.getBarChartView(this, dataset1, multiRenderer1, Type.DEFAULT);
    
    	//setContentView(view);
    	
    	ll_graph.addView(view);
	}
	
	
	private void openChartSugar(int[] fastinglist, int[] pplist, String[] dates, int size)
	{
		XYMultipleSeriesDataset dataset2 = new XYMultipleSeriesDataset();
		
	    XYSeries fastingSeries = new XYSeries("Sugar - Fasting");
	    XYSeries ppSeries = new XYSeries("Sugar - PP");
	    XYSeries s = new XYSeries(" ");
	    
	    dataset2.clear();
	    
	    for(int i=0;i<size;i++)
		{
	    		fastingSeries.add(i,fastinglist[i]);
	    		ppSeries.add(i,pplist[i]);
		 }
	    	
		//dataset2.addSeries(fastingSeries);
		//dataset2.addSeries(ppSeries);
	    if(ischecked_fast == 1)
	    {
	    	dataset2.addSeries(fastingSeries);
	    }
	    if(ischecked_pp == 1)
	    {
	    	dataset2.addSeries(ppSeries);
	    }
	    
	    if(ischecked_fast == 0 && ischecked_pp == 0)
	    {
	    	dataset2.addSeries(s);
	    }
	    
	    XYSeriesRenderer fastingRenderer = new XYSeriesRenderer();
	    
	    fastingRenderer.setColor(getResources().getColor(R.color.fasting));
	    fastingRenderer.setFillPoints(true);
	    fastingRenderer.setDisplayChartValues(true);
	    fastingRenderer.setChartValuesTextSize(30);
	    fastingRenderer.setChartValuesTextAlign(Align.RIGHT);
	    
	    XYSeriesRenderer ppRenderer = new XYSeriesRenderer();
	    
	    ppRenderer.setColor(getResources().getColor(R.color.pp));
	    ppRenderer.setFillPoints(true);
	    ppRenderer.setDisplayChartValues(true);
	    ppRenderer.setChartValuesTextSize(30);
	    ppRenderer.setChartValuesTextAlign(Align.RIGHT);
	    
	    XYSeriesRenderer r = new XYSeriesRenderer();
	    
	    r.setColor(getResources().getColor(R.color.pp));
	    r.setFillPoints(true);
	    r.setDisplayChartValues(true);
	    r.setChartValuesTextSize(30);
	    r.setChartValuesTextAlign(Align.RIGHT);
	    
	    
	    XYMultipleSeriesRenderer multiRenderer2 = new XYMultipleSeriesRenderer();
	    int[] mar = new int[] {40,40,40,40};
	    multiRenderer2.removeAllRenderers();
	    
	    multiRenderer2.setChartTitle("Blood Sugar Measurement");
	   
	    multiRenderer2.setAxesColor(getResources().getColor(R.color.healthmate_green));
	    multiRenderer2.setApplyBackgroundColor(true);
	    multiRenderer2.setBackgroundColor(Color.WHITE);
	    multiRenderer2.setMarginsColor(Color.WHITE);
	    multiRenderer2.setMargins(mar);
	    multiRenderer2.setAxisTitleTextSize(30);
	    multiRenderer2.setChartTitleTextSize(30);
	    multiRenderer2.setLabelsTextSize(23);
	    multiRenderer2.setLegendTextSize(0);
	    multiRenderer2.setLegendHeight(0);
	    multiRenderer2.setShowGrid(true);
	    multiRenderer2.setBarWidth(20);
	    
	    multiRenderer2.setXLabelsPadding(10);
	    multiRenderer2.setXAxisMin(-0.1);
	    multiRenderer2.setYAxisMin(-1);
	    multiRenderer2.setXAxisMax(3);
	    multiRenderer2.setFitLegend(true);
	    multiRenderer2.setXLabels(0);
	    
	    
	    multiRenderer2.setXLabelsColor(getResources().getColor(R.color.healthmate_green));
	    multiRenderer2.setYLabelsColor(0, getResources().getColor(R.color.healthmate_green));
	    multiRenderer2.setYLabelsAlign(Align.RIGHT);
	    multiRenderer2.setYLabelsPadding(5.0f);
	    
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
	    multiRenderer2.setTextTypeface(typeface);
	    
	    
	    for(int i=0; i< size;i++)
	    {
	    	multiRenderer2.addXTextLabel(i , dates[i]);
	    }
	    
	    if(ischecked_fast == 1)
	    {
	    	multiRenderer2.addSeriesRenderer(fastingRenderer);
	    }
	    if(ischecked_pp == 1)
	    {
	    	multiRenderer2.addSeriesRenderer(ppRenderer);
	    }
	    
	    if(ischecked_fast == 0 && ischecked_pp == 0)
	    {
	    	multiRenderer2.addSeriesRenderer(r);
	    }
	    	//multiRenderer2.addSeriesRenderer(fastingRenderer);
	    	//multiRenderer2.addSeriesRenderer(ppRenderer);
	    
	      View view = ChartFactory.getBarChartView(this, dataset2, multiRenderer2, Type.DEFAULT);
    
    	//setContentView(view);
    	
    	ll_graph.addView(view);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

		// TODO Auto-generated method stub

		if(requestCode == REQ_CODE)
		{

			if (resultCode == RESULT_OK)
			{
				 /*if(tabClicked.equals(PRESSURE))
				 {
					 measure_list = new ArrayList<Bundle>();
					 measure_list = bp_master.getMeasurementListByProfile(profile_id);
				 }
				 else if(tabClicked.equals(SUGAR))
				 {
					 measure_list = new ArrayList<Bundle>();
					 measure_list = sugar_master.getMeasurementListByProfile(profile_id);
				 }  
				 
				 Log.v(TAG,"measure_list :"+measure_list.toString());
				
				 adapter = new MeasurementListAdapter(this, measure_list, tabClicked);
				 
				 lv_measures.setAdapter(adapter);*/
				
				setAdapter();
				
				 //expandableListView
				//initializeAdapter(measure_list);
					
				//elv_measures.setAdapter(MyElvAdapter);

			}
			else if(resultCode == RESULT_CANCELED)
			{

			}
		}
		
		if(requestCode == SELECT_PHOTO)
		{
			if(resultCode == RESULT_OK)
			{
				try 
				{
					//final Uri imageUri = data.getData();
					mImageCaptureUri = data.getData();
					//final InputStream imageStream = getContentResolver().openInputStream(imageUri);
					//final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
					//iv_profileimage.setImageBitmap(selectedImage);
					//performCrop();
					doCrop();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
	
	        }
		}
		
		if(requestCode == CROP_FROM_CAMERA)
		{
			if(resultCode == RESULT_OK)
			{
				try 
				{
//					final Uri imageUri = data.getData();
//					final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//					final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//					iv_profileimage.setImageBitmap(selectedImage);
					
					Bundle extras = data.getExtras();
					
					if (extras != null)
					{
						Bitmap photo = extras.getParcelable("data");
						//Drawable d = new BitmapDrawable(getResources(),photo);
						//iv_profileimage.setImageBitmap(photo);
						//iv_profileimage.setImageDrawable(d);
						
						Bitmap b = getRoundedShape(photo);
						super.iv_profileimage.setImageBitmap(b);
						//Drawable d = new BitmapDrawable(getResources(),b);
						//iv_profileimage.setImageDrawable(d);
						
						saveBitmapImage(photo);
					}

					File f = new File(mImageCaptureUri.getPath());
					
					if (f.exists())
						f.delete();

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}

	}
	

	private void doCrop() 
	{
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		
		if (size == 0) 
		{

			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} 
		else 
		{
			
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			//intent.putExtra("circleCrop", new String(""));
			//startActivityForResult(intent, CROP_FROM_CAMERA);
			

			if (size == 1) 
			{
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));
				//i.putExtra("circleCrop", new String(""));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} 
			else 
			{
				
				for (ResolveInfo res : list) 
				{
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() 
				{
							public void onClick(DialogInterface dialog, int item) 
							{
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() 
				{
					@Override
					public void onCancel(DialogInterface dialog) 
					{

						if (mImageCaptureUri != null) 
						{
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}


	public class CropOptionAdapter extends ArrayAdapter<CropOption> 
	{
		private ArrayList<CropOption> mOptions;
		private LayoutInflater mInflater;

		public CropOptionAdapter(Context context, ArrayList<CropOption> options) 
		{
			super(context, R.layout.crop_selector, options);

			mOptions = options;

			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup group) 
		{
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.crop_selector, null);

			CropOption item = mOptions.get(position);

			if (item != null)
			{
				((ImageView) convertView.findViewById(R.id.iv_icon))
						.setImageDrawable(item.icon);
				((TextView) convertView.findViewById(R.id.tv_name))
						.setText(item.title);

				return convertView;
			}

			return null;
		}
	}

	public class CropOption 
	{
		public CharSequence title;
		public Drawable icon;
		public Intent appIntent;
	}
	
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) 
	{
		  // TODO Auto-generated method stub
		//120, 120
		//** get display screen width and height
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;

        float density =  getResources().getDisplayMetrics().density;
		  
		int targetWidth;
		int targetHeight;

        String screen = newgrid.getScreenResolution(context);

        //Log.v(TAG, "screen :" +screen);

        if(screen.equals("X-LARGE"))
        {
            targetWidth = (int) (90 * density);
            targetHeight = (int) (90 * density);

        }
        else if(screen.equals("LARGE"))
        {
            targetWidth = (int) (90 * density);
            targetHeight = (int) (90 * density);
        }
        else if(screen.equals("NORMAL"))
        {
            targetWidth = (int) (44 * density);
            targetHeight = (int) (44 * density);
        }
        else if(screen.equals("SMALL"))
        {
            targetWidth = 70;
            targetHeight = 70;
        }
        else
        {
            targetWidth = 70;
            targetHeight = 70;
        }

        //Log.v(TAG, "targetWidth : "+ targetWidth+"targetHeight : "+ targetHeight);


        /*
		if(height == 2464)
		{
			targetWidth = 200;
			targetHeight = 200;
		}
		else if(height == 1280)
		{
			targetWidth = 90;
			targetHeight = 90;
		}
		else if(height == 976)
		{
			targetWidth = 90;
			targetHeight = 90;
		}
        else if(height == 1184)
        {
            targetWidth = 90;
            targetHeight = 90;
        }
		else
		{
			targetWidth = 70;
			targetHeight = 70;
		}
		*/
		  Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
		                            targetHeight,Bitmap.Config.ARGB_8888);

		                Canvas canvas = new Canvas(targetBitmap);
		  Path path = new Path();
		  path.addCircle(((float) targetWidth - 1) / 2,
		  ((float) targetHeight - 1) / 2,
		  (Math.min(((float) targetWidth), 
		                ((float) targetHeight)) / 2),
		          Path.Direction.CCW);

		                canvas.clipPath(path);
		  Bitmap sourceBitmap = scaleBitmapImage;
		  canvas.drawBitmap(sourceBitmap, 
		                                new Rect(0, 0, sourceBitmap.getWidth(),
		    sourceBitmap.getHeight()), 
		                                new Rect(0, 0, targetWidth,
		    targetHeight), null);
		  return targetBitmap;
	}

	
	public boolean saveBitmapImage(Bitmap profile)
	{
		
		String iconsStoragePath = Environment.getExternalStorageDirectory() + "/HealthMate/Profile_Images";
		
		File sdIconStorageDir = new File(iconsStoragePath);

				//create storage directories, if they don't exist
		
		if(!sdIconStorageDir.exists())
		{
			sdIconStorageDir.mkdirs();
		}
		

				try 
				{
					//String filePath = sdIconStorageDir.toString() + "Profile"+profile_id+".jpg";
					
					File file = new File(sdIconStorageDir, "Profile"+profile_id+".jpg");
					
					//Log.v(TAG,"filepath : "+filePath);
					////
					
					if (file.exists())
						file.delete();
					
					///
					
					
					FileOutputStream fileOutputStream = new FileOutputStream(file);

					BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

					//choose another format if PNG doesn't suit you
					profile.compress(CompressFormat.JPEG, 100, bos);

					bos.flush();
					bos.close();

				} 
				catch (FileNotFoundException e)
				{
					Log.w("TAG", "Error saving image file: " + e.getMessage());
					return false;
				} 
				catch (IOException e) 
				{
					Log.w("TAG", "Error saving image file: " + e.getMessage());
					return false;
				}

				return true;
	}
	
	private void initializeAdapter(ArrayList<Bundle> measure_list)
	{
		list_hashmap = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> hashmap;
		
		for (int i =0; i<measure_list.size(); i++)
		{
								
			hashmap = new HashMap<String, Object>();
			hashmap.put("group_id",i);
			hashmap.put("expand","N");
			list_hashmap.add(hashmap);
		}
		//Log.v(TAG,"initializeAdapter--"+list_hashmap.toString());
		
	}
	
	private class GroupViewHolder
	{
		LinearLayout ll_row,ll_groupRow1, ll_groupRow2, ll_groupRow3, ll_groupRow4;
		CrescoTextView tv_date, tv_time ,tv_sys,tv_dias, tv_pulse,tv_sdate, tv_stime, tv_pp, tv_fasting ;
		
		CrescoEditText et_date, et_time,et_sys, et_dias, et_pulse, et_sdate, et_stime, et_pp, et_fasting;
		Spinner sp_sys, sp_dias, sp_pulse , sp_pp, sp_fasting;
	}

	private class ChildViewHolder
	{
		LinearLayout ll_c_row, ll_childRow1, ll_childRow2, ll_childRow3, ll_childRow4;
		CrescoTextView tv_remark, tv_pos, tv_loc, tv_sremark;
		CrescoEditText et_remark, et_pos, et_loc, et_sremark;
		Spinner sp_pos, sp_loc;
	}
	
	//private ExpandableListAdapter MyElvAdapter = new ExpandableListAdapter()
	private AnimatedExpandableListAdapter MyElvAdapter = new AnimatedExpandableListAdapter()
	{
		
		GroupViewHolder gvHolder;
		ChildViewHolder cvHolder;
		String ls_expand = "N";
		
		@Override
		public Object getChild(int groupPosition, int childPosition) 
		{
			return list_hashmap.get(groupPosition);
		}
		@Override
		public long getChildId(int groupPosition, int childPosition) 
		{
			return childPosition;
		}
		@Override
		public Object getGroup(int groupPosition) 
		{
			return list_hashmap.get(groupPosition);
		}
		@Override
		public int getGroupCount() 
		{
			return list_hashmap.size();
		}
		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) 
		{
			//Log.v(TAG, "in getGroupView()");
			ArrayList<String> sp_list1, sp_list2, sp_list3, sp_list4, sp_list5 , sp_list6, sp_list7;
			
			ArrayAdapter sAdapter;
			if(convertView == null)
			{
				convertView = layoutInflator.inflate(R.layout.group, null);

				gvHolder = new GroupViewHolder();
				
				gvHolder.ll_row = (LinearLayout) convertView.findViewById(R.id.ll_row);
				gvHolder.ll_groupRow1 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow1);
				gvHolder.tv_date = (CrescoTextView) convertView.findViewById(R.id.tv_date);
				gvHolder.tv_time = (CrescoTextView) convertView.findViewById(R.id.tv_time);
				gvHolder.tv_sys = (CrescoTextView) convertView.findViewById(R.id.tv_sys);
				gvHolder.tv_dias = (CrescoTextView) convertView.findViewById(R.id.tv_dias);
				gvHolder.tv_pulse = (CrescoTextView) convertView.findViewById(R.id.tv_pulse);
				
				gvHolder.ll_groupRow2 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow2);
				gvHolder.et_date = (CrescoEditText) convertView.findViewById(R.id.et_date);
				gvHolder.et_time = (CrescoEditText) convertView.findViewById(R.id.et_time);
				gvHolder.sp_sys = (Spinner) convertView.findViewById(R.id.sp_sys);
				gvHolder.sp_dias = (Spinner) convertView.findViewById(R.id.sp_dias);
				gvHolder.sp_pulse = (Spinner) convertView.findViewById(R.id.sp_pulse);
				
				gvHolder.ll_groupRow3 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow3);
				gvHolder.tv_sdate = (CrescoTextView) convertView.findViewById(R.id.tv_sdate);
				gvHolder.tv_stime = (CrescoTextView) convertView.findViewById(R.id.tv_stime);
				gvHolder.tv_fasting = (CrescoTextView) convertView.findViewById(R.id.tv_fasting);
				gvHolder.tv_pp = (CrescoTextView) convertView.findViewById(R.id.tv_pp);
				
				gvHolder.ll_groupRow4 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow4);
				gvHolder.et_sdate = (CrescoEditText) convertView.findViewById(R.id.et_sdate);
				gvHolder.et_stime = (CrescoEditText) convertView.findViewById(R.id.et_stime);
				gvHolder.sp_fasting = (Spinner) convertView.findViewById(R.id.sp_fasting);
				gvHolder.sp_pp = (Spinner) convertView.findViewById(R.id.sp_pp);
				
				convertView.setTag(gvHolder);
			}
			
			gvHolder = (GroupViewHolder) convertView.getTag();

			sp_list1 = new ArrayList<String>();
			sp_list1.add("");
			for(int i = 100; i<=200; i++)
			{
				sp_list1.add(String.valueOf(i));
			}
			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list1);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			gvHolder.sp_sys.setAdapter(sAdapter);
			
			
			sp_list2 = new ArrayList<String>();
			sp_list2.add("");
			for(int j = 60;j<=120; j++)
			{
				sp_list2.add(String.valueOf(j));
			}
			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list2);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			gvHolder.sp_dias.setAdapter(sAdapter);
			
			sp_list3 = new ArrayList<String>();
			sp_list3.add("");
			for(int k = 65; k<=80; k++)
			{
				sp_list3.add(String.valueOf(k));
			}

			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list3);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			gvHolder.sp_pulse.setAdapter(sAdapter);
			
			sp_list4 = new ArrayList<String>();
			sp_list4.add("");
			for(int j = 60;j<=120; j++)
			{
				sp_list4.add(String.valueOf(j));
			}
			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list4);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			gvHolder.sp_fasting.setAdapter(sAdapter);
			
			sp_list5 = new ArrayList<String>();
			sp_list5.add("");
			for(int j = 60;j<=120; j++)
			{
				sp_list5.add(String.valueOf(j));
			}
			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list5);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			gvHolder.sp_pp.setAdapter(sAdapter);
			
			HashMap<String, Object> hashmap = MeasurementList_Profile.this.list_hashmap.get(groupPosition);
			
			String Date = null, High = null, Low= null, pulse= null, Date1= null, pp= null, fast= null;
			String	tm = null, time1 = null;
			
			
			
			if(tabClicked.equals(PRESSURE))
			{
				gvHolder.ll_groupRow1.setVisibility(View.VISIBLE);
				gvHolder.ll_groupRow2.setVisibility(View.GONE);
				gvHolder.ll_groupRow3.setVisibility(View.GONE);
				gvHolder.ll_groupRow4.setVisibility(View.GONE);
				
				//Date = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_TS);
				Date = measure_list.get(groupPosition).getString("date");
				tm  = measure_list.get(groupPosition).getString("time");
				High  = measure_list.get(groupPosition).getString(BloodPressure_Master.PM1_VALUE);
				Low = measure_list.get(groupPosition).getString(BloodPressure_Master.PM2_VALUE);
				pulse = measure_list.get(groupPosition).getString(BloodPressure_Master.PM3_VALUE);
			
				gvHolder.tv_date.setText(Date);
				gvHolder.tv_time.setText(tm);
				gvHolder.tv_sys.setText(High);
				gvHolder.tv_dias.setText(Low);
				gvHolder.tv_pulse.setText(pulse);
			}
			else if(tabClicked.equals(SUGAR))
			{
				
				gvHolder.ll_groupRow1.setVisibility(View.GONE);
				gvHolder.ll_groupRow2.setVisibility(View.GONE);
				gvHolder.ll_groupRow3.setVisibility(View.VISIBLE);
				gvHolder.ll_groupRow4.setVisibility(View.GONE);
				 
				//Date1 = measure_list.get(groupPosition).getString(Sugar_Master.PM4_TS);
				Date1 = measure_list.get(groupPosition).getString("date");
				time1 = measure_list.get(groupPosition).getString("time");
				
				fast  = measure_list.get(groupPosition).getString(Sugar_Master.PM4_VALUE);
				pp = measure_list.get(groupPosition).getString(Sugar_Master.PM5_VALUE);
				
				gvHolder.tv_sdate.setText(Date1);
				gvHolder.tv_stime.setText(time1);
				gvHolder.tv_fasting.setText(fast);
				gvHolder.tv_pp.setText(pp);
			}
			
			
			
			ls_expand =  (String) hashmap.get("expand");
			
			
			//Log.v(TAG,"group_id in view "+group_id);
			
				
			//Log.v(TAG,"ll_add_pressure not VISIBLE"+ll_add_pressure.getVisibility());
			
			if (ls_expand.equals("Y"))
			{	
				gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));

				if(tabClicked.equals(PRESSURE))
				{
				
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow2.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);

					gvHolder.et_date.setText(Date);
					gvHolder.et_time.setText(tm);
					//gvHolder.et_sys.setText(High);
					//gvHolder.et_dias.setText(Low);
					//gvHolder.et_pulse.setText(pulse);
					
					for (int i=0;i<sp_list1.size();i++)
					{
						   if (gvHolder.sp_sys.getItemAtPosition(i).toString().equalsIgnoreCase(High))
						   { 
							   gvHolder.sp_sys.setSelection(i);
						   }
					}
					
					for (int i=0;i<sp_list2.size();i++)
					{
						   if (gvHolder.sp_dias.getItemAtPosition(i).toString().equalsIgnoreCase(Low))
						   {
							   gvHolder.sp_dias.setSelection(i);
						   }
					}
					for (int i=0;i<sp_list3.size();i++)
					{
						   if (gvHolder.sp_pulse.getItemAtPosition(i).toString().equalsIgnoreCase(pulse))
						   {
							   gvHolder.sp_pulse.setSelection(i);
						   }
					}
				
				}
				else
				{
										
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow2.setVisibility(View.GONE);

					gvHolder.et_sdate.setText(Date1);
					gvHolder.et_stime.setText(time1);
					//gvHolder.et_fasting.setText(fast);
					//gvHolder.et_pp.setText(pp);
					
					for (int i=0;i<sp_list4.size();i++)
					{
						   if (gvHolder.sp_fasting.getItemAtPosition(i).toString().equalsIgnoreCase(fast))
						   {
							   gvHolder.sp_fasting.setSelection(i);
						   }
					}
					for (int i=0;i<sp_list5.size();i++)
					{
						   if (gvHolder.sp_pp.getItemAtPosition(i).toString().equalsIgnoreCase(pp))
						   {
							   gvHolder.sp_pp.setSelection(i);
						   }
					}
				}
			}
			else
			{
				/*if(is_addMeasureclicked.equals(TRUE))
				{
					Log.v(TAG,"if is_addMeasureclicked : "+is_addMeasureclicked);
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.transparent_back));
				}
				else
				{
					Log.v(TAG,"else is_addMeasureclicked : "+is_addMeasureclicked);
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.White));
				}*/
				gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.White));
				if(tabClicked.equals(PRESSURE))
				{
					gvHolder.ll_groupRow2.setVisibility(View.GONE);
					gvHolder.ll_groupRow1.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);
				}
				else
				{
					gvHolder.ll_groupRow2.setVisibility(View.GONE);
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow3.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);
				}
			}
			//}
			return convertView;

		}
		
		
		@Override
		public View getRealChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) 
		{
			//Log.v(TAG, "in getChildView()");
			ArrayList<String> sp_list6, sp_list7;
			if(convertView == null)
			{
				convertView = layoutInflator.inflate(R.layout.child, null);

				cvHolder = new ChildViewHolder();
				cvHolder.ll_c_row = (LinearLayout) convertView.findViewById(R.id.ll_c_row);
				cvHolder.ll_childRow1 = (LinearLayout) convertView.findViewById(R.id.ll_childRow1);
				cvHolder.tv_remark = (CrescoTextView) convertView.findViewById(R.id.tv_remark);
				cvHolder.tv_pos = (CrescoTextView) convertView.findViewById(R.id.tv_pos);
				cvHolder.tv_loc = (CrescoTextView) convertView.findViewById(R.id.tv_loc);
				
				cvHolder.ll_childRow2 = (LinearLayout) convertView.findViewById(R.id.ll_childRow2);
				cvHolder.et_remark = (CrescoEditText) convertView.findViewById(R.id.et_remark);
				cvHolder.sp_pos = (Spinner) convertView.findViewById(R.id.sp_pos);
				cvHolder.sp_loc = (Spinner) convertView.findViewById(R.id.sp_loc);
				
				cvHolder.ll_childRow3 = (LinearLayout) convertView.findViewById(R.id.ll_childRow3);
				cvHolder.tv_sremark = (CrescoTextView) convertView.findViewById(R.id.tv_sremark);
				cvHolder.ll_childRow4 = (LinearLayout) convertView.findViewById(R.id.ll_childRow4);
				cvHolder.et_sremark = (CrescoEditText) convertView.findViewById(R.id.et_sremark);
				
				convertView.setTag(cvHolder);
			}
			

			cvHolder = (ChildViewHolder) convertView.getTag();

			sp_list6 = new ArrayList<String>();
			sp_list6.add("Position");	
			sp_list6.add("Upright");
			sp_list6.add("horizontal");
			sp_list6.add("Seated");
			sp_list6.add("Reclined");

			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list6);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			cvHolder.sp_pos.setAdapter(sAdapter);

			
			sp_list7 = new ArrayList<String>();
			sp_list7.add("Location");	
			sp_list7.add("Rt Wrist");
			sp_list7.add("Lt Wrist");
			sp_list7.add("Rt Arm");
			sp_list7.add("Lt Arm");
			sp_list7.add("Rt Leg");
			sp_list7.add("Lt Leg");

			sAdapter = new SpinnerAdapter(MeasurementList_Profile.this, R.layout.gender_row, sp_list7);
			sAdapter.setDropDownViewResource(R.layout.gender_dropdown);
			cvHolder.sp_loc.setAdapter(sAdapter);
			
			HashMap<String, Object> hashmap = MeasurementList_Profile.this.list_hashmap.get(groupPosition);
			
			String pos = null, loc = null, remark = null, sremark= null;
			
			if(tabClicked.equals(PRESSURE))
			{
				pos = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_POSITION);
				loc = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_LOCATION);
				remark = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_REMARKS);
			
				//cvHolder.tv_remark.setText(remark);
				//cvHolder.tv_pos.setText(pos);
				//cvHolder.tv_loc.setText(loc);
				
				
			}
			else
			{
				sremark = measure_list.get(groupPosition).getString(Sugar_Master.PM4_REMARKS);
				
				//cvHolder.tv_sremark.setText(sremark);
			}
			
			ls_expand =  (String) hashmap.get("expand");

			if (ls_expand.equals("Y"))
			{
				cvHolder.ll_c_row.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));

				if(tabClicked.equals(PRESSURE))
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
					
					cvHolder.et_remark.setText(remark);
					//cvHolder.et_pos.setText(pos);
					//cvHolder.et_loc.setText(loc);
					for (int i=0;i<sp_list6.size();i++)
					{
						   if (cvHolder.sp_pos.getItemAtPosition(i).toString().equalsIgnoreCase(pos))
						   {
							   cvHolder.sp_pos.setSelection(i);
						   }
					}
					
					for (int i=0;i<sp_list7.size();i++)
					{
						   if (cvHolder.sp_loc.getItemAtPosition(i).toString().equalsIgnoreCase(loc))
						   {
							  		cvHolder.sp_loc.setSelection(i);
						   }
					}
					
				}
				else
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.VISIBLE);
					
					cvHolder.et_sremark.setText(sremark);
				}
				
			}
			else
			{
				cvHolder.ll_c_row.setBackgroundColor(context.getResources().getColor(R.color.White));

				if(tabClicked.equals(PRESSURE))
				{
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow1.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
				}
				else
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow3.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
				}
				
			}


			return convertView;
		}
		@Override
		public int getRealChildrenCount(int groupPosition) 
		{
			return 1;
		}
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
		/*@Override
		public boolean areAllItemsEnabled() 
		{
			return false;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) 
		{
			return list_hashmap.get(groupPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) 
		{
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) 
		{
			
			//Log.v(TAG, "in getChildView()");
			if(convertView == null)
			{
				convertView = layoutInflator.inflate(R.layout.child, null);

				cvHolder = new ChildViewHolder();
				
				cvHolder.ll_childRow1 = (LinearLayout) convertView.findViewById(R.id.ll_childRow1);
				cvHolder.tv_remark = (CrescoTextView) convertView.findViewById(R.id.tv_remark);
				cvHolder.tv_pos = (CrescoTextView) convertView.findViewById(R.id.tv_pos);
				cvHolder.tv_loc = (CrescoTextView) convertView.findViewById(R.id.tv_loc);
				
				cvHolder.ll_childRow2 = (LinearLayout) convertView.findViewById(R.id.ll_childRow2);
				cvHolder.et_remark = (CrescoEditText) convertView.findViewById(R.id.et_remark);
				cvHolder.et_pos = (CrescoEditText) convertView.findViewById(R.id.et_pos);
				cvHolder.et_loc = (CrescoEditText) convertView.findViewById(R.id.et_loc);
				
				cvHolder.ll_childRow3 = (LinearLayout) convertView.findViewById(R.id.ll_childRow3);
				cvHolder.tv_sremark = (CrescoTextView) convertView.findViewById(R.id.tv_sremark);
				cvHolder.ll_childRow4 = (LinearLayout) convertView.findViewById(R.id.ll_childRow4);
				cvHolder.et_sremark = (CrescoEditText) convertView.findViewById(R.id.et_sremark);

				convertView.setTag(cvHolder);
			}
			else
			{
				((ChildViewHolder) convertView.getTag()).tv_remark.setTag(groupPosition);
				((ChildViewHolder) convertView.getTag()).tv_pos.setTag(groupPosition);
				((ChildViewHolder) convertView.getTag()).tv_loc.setTag(groupPosition);
				
				((ChildViewHolder) convertView.getTag()).et_remark.setTag(groupPosition);
				((ChildViewHolder) convertView.getTag()).et_pos.setTag(groupPosition);
				((ChildViewHolder) convertView.getTag()).et_loc.setTag(groupPosition);
			}

			cvHolder = (ChildViewHolder) convertView.getTag();


			HashMap<String, Object> hashmap = MeasurementList_Profile.this.list_hashmap.get(groupPosition);
			
			String pos = null, loc = null, remark = null, sremark= null;
			
			if(tabClicked.equals(PRESSURE))
			{
				pos = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_POSITION);
				loc = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_LOCATION);
				remark = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_REMARKS);
			
				cvHolder.tv_remark.setText(remark);
				cvHolder.tv_pos.setText(pos);
				cvHolder.tv_loc.setText(loc);
			}
			else
			{
				sremark = measure_list.get(groupPosition).getString(Sugar_Master.PM4_REMARKS);
				
				cvHolder.tv_sremark.setText(sremark);
			}
			
			ls_expand =  (String) hashmap.get("expand");

			if (ls_expand.equals("Y"))
			{

				if(tabClicked.equals(PRESSURE))
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
				
					cvHolder.et_remark.setTextColor(context.getResources().getColor(R.color.White));
					cvHolder.et_pos.setTextColor(context.getResources().getColor(R.color.White));
					cvHolder.et_loc.setTextColor(context.getResources().getColor(R.color.White));
				
					cvHolder.et_remark.setText(remark);
					cvHolder.et_pos.setText(pos);
					cvHolder.et_loc.setText(loc);
				}
				else
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.VISIBLE);
					
					cvHolder.ll_childRow3.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
					
					cvHolder.et_sremark.setTextColor(context.getResources().getColor(R.color.White));
					
					cvHolder.et_sremark.setText(sremark);
				}
				
			}
			else
			{
				if(tabClicked.equals(PRESSURE))
				{
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow1.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow3.setVisibility(View.GONE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
				
					cvHolder.ll_childRow1.setBackgroundColor(context.getResources().getColor(R.color.White));
					cvHolder.tv_remark.setTextColor(context.getResources().getColor(R.color.text_color));
					cvHolder.tv_pos.setTextColor(context.getResources().getColor(R.color.text_color));
					cvHolder.tv_loc.setTextColor(context.getResources().getColor(R.color.text_color));
				}
				else
				{
					cvHolder.ll_childRow1.setVisibility(View.GONE);
					cvHolder.ll_childRow2.setVisibility(View.GONE);
					cvHolder.ll_childRow3.setVisibility(View.VISIBLE);
					cvHolder.ll_childRow4.setVisibility(View.GONE);
					
					cvHolder.ll_childRow3.setBackgroundColor(context.getResources().getColor(R.color.White));
					cvHolder.tv_sremark.setTextColor(context.getResources().getColor(R.color.text_color));
				}
				
			}


			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) 
		{
			return 1;
		}

		@Override
		public long getCombinedChildId(long groupId, long childId) 
		{
			return 0;
		}

		@Override
		public long getCombinedGroupId(long groupId) 
		{
			return groupId;
		}

		@Override
		public Object getGroup(int groupPosition) 
		{
			return list_hashmap.get(groupPosition);
		}

		@Override
		public int getGroupCount() 
		{
			return list_hashmap.size();
		}

		@Override
		public long getGroupId(int groupPosition) 
		{
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean arg1, View convertView, ViewGroup parent) 
		{
			Log.v(TAG, "in getGroupView()");

			if(convertView == null)
			{
				convertView = layoutInflator.inflate(R.layout.group, null);

				gvHolder = new GroupViewHolder();
				
				gvHolder.ll_row = (LinearLayout) convertView.findViewById(R.id.ll_row);
				gvHolder.ll_groupRow1 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow1);
				gvHolder.tv_ts = (CrescoTextView) convertView.findViewById(R.id.tv_ts);
				gvHolder.tv_sys = (CrescoTextView) convertView.findViewById(R.id.tv_sys);
				gvHolder.tv_dias = (CrescoTextView) convertView.findViewById(R.id.tv_dias);
				gvHolder.tv_pulse = (CrescoTextView) convertView.findViewById(R.id.tv_pulse);
				
				gvHolder.ll_groupRow2 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow2);
				gvHolder.et_ts = (CrescoEditText) convertView.findViewById(R.id.et_ts);
				gvHolder.et_sys = (CrescoEditText) convertView.findViewById(R.id.et_sys);
				gvHolder.et_dias = (CrescoEditText) convertView.findViewById(R.id.et_dias);
				gvHolder.et_pulse = (CrescoEditText) convertView.findViewById(R.id.et_pulse);
				
				gvHolder.ll_groupRow3 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow3);
				gvHolder.tv_sts = (CrescoTextView) convertView.findViewById(R.id.tv_sts);
				gvHolder.tv_fasting = (CrescoTextView) convertView.findViewById(R.id.tv_fasting);
				gvHolder.tv_pp = (CrescoTextView) convertView.findViewById(R.id.tv_pp);
				
				gvHolder.ll_groupRow4 = (LinearLayout) convertView.findViewById(R.id.ll_groupRow4);
				gvHolder.et_sts = (CrescoEditText) convertView.findViewById(R.id.et_sts);
				gvHolder.et_fasting = (CrescoEditText) convertView.findViewById(R.id.et_fasting);
				gvHolder.et_pp = (CrescoEditText) convertView.findViewById(R.id.et_pp);
				
				convertView.setTag(gvHolder);
			}
			else
			{
				((GroupViewHolder) convertView.getTag()).tv_ts.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).tv_sys.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).tv_dias.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).tv_pulse.setTag(groupPosition);
				
				((GroupViewHolder) convertView.getTag()).et_ts.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).et_sys.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).et_dias.setTag(groupPosition);
				((GroupViewHolder) convertView.getTag()).et_pulse.setTag(groupPosition);
			}

			gvHolder = (GroupViewHolder) convertView.getTag();

			HashMap<String, Object> hashmap = MeasurementList_Profile.this.list_hashmap.get(groupPosition);
			
			String Date = null, High = null, Low= null, pulse= null, Date1= null, pp= null, fast= null;
			
			if(tabClicked.equals(PRESSURE))
			{
				gvHolder.ll_groupRow1.setVisibility(View.VISIBLE);
				gvHolder.ll_groupRow2.setVisibility(View.GONE);
				gvHolder.ll_groupRow3.setVisibility(View.GONE);
				gvHolder.ll_groupRow4.setVisibility(View.GONE);
				
				Date = measure_list.get(groupPosition).getString(BloodPressure_Master.MSR_TS);
				
				High  = measure_list.get(groupPosition).getString(BloodPressure_Master.PM1_VALUE);
				Low = measure_list.get(groupPosition).getString(BloodPressure_Master.PM2_VALUE);
				pulse = measure_list.get(groupPosition).getString(BloodPressure_Master.PM3_VALUE);
			
				gvHolder.tv_ts.setText(Date);
				gvHolder.tv_sys.setText(High);
				gvHolder.tv_dias.setText(Low);
				gvHolder.tv_pulse.setText(pulse);
			}
			else if(tabClicked.equals(SUGAR))
			{
				
				gvHolder.ll_groupRow1.setVisibility(View.GONE);
				gvHolder.ll_groupRow2.setVisibility(View.GONE);
				gvHolder.ll_groupRow3.setVisibility(View.VISIBLE);
				gvHolder.ll_groupRow4.setVisibility(View.GONE);
				 
				Date1 = measure_list.get(groupPosition).getString(Sugar_Master.PM4_TS);
				
				fast  = measure_list.get(groupPosition).getString(Sugar_Master.PM4_VALUE);
				pp = measure_list.get(groupPosition).getString(Sugar_Master.PM5_VALUE);
				
				gvHolder.tv_sts.setText(Date1);
				gvHolder.tv_fasting.setText(fast);
				gvHolder.tv_pp.setText(pp);
			}
			
			ls_expand =  (String) hashmap.get("expand");

			
			if (ls_expand.equals("Y"))
			{	
				if(tabClicked.equals(PRESSURE))
				{
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
				
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow2.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);
				
					gvHolder.ll_groupRow2.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));

					gvHolder.et_ts.setText(Date);
					gvHolder.et_sys.setText(High);
					gvHolder.et_dias.setText(Low);
					gvHolder.et_pulse.setText(pulse);
				
					gvHolder.et_ts.setTextColor(context.getResources().getColor(R.color.White));
					gvHolder.et_sys.setTextColor(context.getResources().getColor(R.color.White));
					gvHolder.et_dias.setTextColor(context.getResources().getColor(R.color.White));
					gvHolder.et_pulse.setTextColor(context.getResources().getColor(R.color.White));
				}
				else
				{
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));
					
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow2.setVisibility(View.GONE);
					
					gvHolder.ll_groupRow4.setBackgroundColor(context.getResources().getColor(R.color.healthmate_green));

					gvHolder.et_sts.setText(Date1);
					gvHolder.et_fasting.setText(fast);
					gvHolder.et_pp.setText(pp);
					
					gvHolder.et_sts.setTextColor(context.getResources().getColor(R.color.White));
					gvHolder.et_fasting.setTextColor(context.getResources().getColor(R.color.White));
					gvHolder.et_pp.setTextColor(context.getResources().getColor(R.color.White));
				}
			}
			else
			{
				if(tabClicked.equals(PRESSURE))
				{
					gvHolder.ll_groupRow2.setVisibility(View.GONE);
					gvHolder.ll_groupRow1.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow3.setVisibility(View.GONE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);
				
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.ll_groupRow1.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.tv_ts.setTextColor(context.getResources().getColor(R.color.text_color));
					gvHolder.tv_sys.setTextColor(context.getResources().getColor(R.color.text_color));
					gvHolder.tv_dias.setTextColor(context.getResources().getColor(R.color.text_color));
					gvHolder.tv_pulse.setTextColor(context.getResources().getColor(R.color.text_color));
				}
				else
				{
					gvHolder.ll_groupRow2.setVisibility(View.GONE);
					gvHolder.ll_groupRow1.setVisibility(View.GONE);
					gvHolder.ll_groupRow3.setVisibility(View.VISIBLE);
					gvHolder.ll_groupRow4.setVisibility(View.GONE);
				
					gvHolder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.ll_groupRow3.setBackgroundColor(context.getResources().getColor(R.color.White));
					gvHolder.tv_sts.setTextColor(context.getResources().getColor(R.color.text_color));
					gvHolder.tv_fasting.setTextColor(context.getResources().getColor(R.color.text_color));
					gvHolder.tv_pp.setTextColor(context.getResources().getColor(R.color.text_color));
				}
			}
			return convertView;
		}

		@Override
		public boolean hasStableIds() 
		{ 	
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) 
		{ 
			return false;
		}

		@Override
		public boolean isEmpty()
		{ 	
			return false;
		}

		@Override
		public void onGroupCollapsed(int groupPosition) 
		{ }

		@Override
		public void onGroupExpanded(int groupPosition) 
		{ }

		@Override
		public void registerDataSetObserver(DataSetObserver observer) 
		{ }

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) 
		{ }*/

	};
	
	
	@SuppressLint("NewApi")
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
	{
		if (li_lastSelectedGroupPosition != -1 && elv_measures.isGroupExpanded(li_lastSelectedGroupPosition) && li_lastSelectedGroupPosition != groupPosition)
		{
			elv_measures.collapseGroup(li_lastSelectedGroupPosition);
			list_hashmap.get(li_lastSelectedGroupPosition).put("expand", "N");
			group_id = li_lastSelectedGroupPosition;
			////
			//setupLayoutAnimationClose(li_lastSelectedGroupPosition);
			//elv_measures.collapseGroupWithAnimation(li_lastSelectedGroupPosition);
		}

		if (elv_measures.isGroupExpanded(groupPosition))
		{
			
			group_id = groupPosition;
			//elv_measures.collapseGroup(groupPosition);
			
			//Log.v(TAG,"groupPosition "+groupPosition);
			////
			//setupLayoutAnimationClose(groupPosition);
			elv_measures.collapseGroupWithAnimation(groupPosition);
			list_hashmap.get(groupPosition).put("expand", "N");
			
		}
		else
		{
			list_hashmap.get(groupPosition).put("expand", "Y");
			group_id = groupPosition;
			//elv_measures.expandGroup(groupPosition);
			//Log.v(TAG,"groupPosition "+groupPosition);
			///
			//setupLayoutAnimation(groupPosition);
			elv_measures.expandGroupWithAnimation(groupPosition);
		}
		li_lastSelectedGroupPosition = groupPosition;
		elv_measures.invalidateViews();
		
		return true;
	}
	

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() 
	{


		if(!(ll_add_pressure.getVisibility() == View.VISIBLE 
				|| ll_add_sugar.getVisibility() == View.VISIBLE) &&!(ll_main_graph.getVisibility() == View.VISIBLE))
		{
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
		}
		
		if (ll_main_graph.getVisibility() == View.VISIBLE)
		{
			rootLayout.clearAnimation();
			
			FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, ll_main_graph, 1);
			if (ll_elvlist.getVisibility() == View.GONE)
		    {
		        flipAnimation.reverse();
		    }
			
			rootLayout.startAnimation(flipAnimation);
			
			bt_add.setVisibility(View.VISIBLE);
			bt_export.setVisibility(View.VISIBLE);
			ll_bt_add.setVisibility(View.VISIBLE);
			ll_bt_export.setVisibility(View.VISIBLE);
			bt_add.setText(ADD_READING);
			bt_showgraph.setText(VIEW_GRAPH);
			ll_graph_date.setVisibility(View.GONE);
			
			bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
			bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);

            if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
            {
                ll_pressure_header.setVisibility(View.VISIBLE);
                ll_sugar_header.setVisibility(View.GONE);
            }
            else
            {
                ll_sugar_header.setVisibility(View.VISIBLE);
                ll_pressure_header.setVisibility(View.GONE);
            }
			
			//bt_showgraph.setPadding(70, 0, 0, 0);
			//bt_add.setCompoundDrawablePadding(10);
			//bt_showgraph.setCompoundDrawablePadding(10);
			
		}
		
		
		
		View v = null;
		
		if(ll_add_pressure.getVisibility() == View.VISIBLE || ll_add_sugar.getVisibility() == View.VISIBLE )
		{
			ContentValues cv;

            if(ll_custom_keyboard.getVisibility() == View.VISIBLE)
			{
				ll_custom_keyboard.setVisibility(View.GONE);
			}
			else
			{
			
				super.adView.setVisibility(View.VISIBLE);

			if(tabClicked.equals(MeasurementList_Profile.PRESSURE))
			{
				
				String ls_date, ls_time, ls_systolic, ls_diastolic, ls_pulse, 
				ls_msr_ts, ls_pos, ls_loc, ls_remark;
				//int li_sequence;
		
				ls_date = et_pressuredate.getText().toString();
				ls_time = et_pressuretime.getText().toString();
				ls_remark = et_pressureremark.getText().toString();
				//ls_systolic = sp_pressuresys.getItemAtPosition(sp_pressuresys.getSelectedItemPosition()).toString();
				//ls_diastolic =sp_pressuredias.getItemAtPosition(sp_pressuredias.getSelectedItemPosition()).toString();
				//ls_pulse  = sp_pressurepulse.getItemAtPosition(sp_pressurepulse.getSelectedItemPosition()).toString();
		
				ls_systolic = et_pressuresys.getText().toString();
				ls_diastolic = et_pressuredias.getText().toString();
				ls_pulse = et_pressurepulse.getText().toString();
				
				ls_pos = sp_pressurepos.getItemAtPosition(sp_pressurepos.getSelectedItemPosition()).toString();
				ls_loc = sp_pressureloc.getItemAtPosition(sp_pressureloc.getSelectedItemPosition()).toString();
		
		
				//ls_msr_ts = ls_date+"\t"+ls_time;
				//ls_msr_ts = Date+" "+Time;

                String date[] =ls_date.split(" ");
                String d = date[0];
                String m = getdatebyMonth(date[1]);
                String y= date[2];
                String date1 = y+"-"+m+"-"+d+" "+ls_time+":00";

                //Log.v(TAG, "date to save : "+d);
                //Log.v(TAG, "month to save : "+date[1]);
                //Log.v(TAG, "year to save : "+y);
                ls_msr_ts = date1;
				//Log.v(TAG, "date and time to save : "+ls_msr_ts);


				if(ls_remark == null)
				{
					ls_remark = "";
				}
		
				if(!(ls_systolic.equals("")) && !(ls_systolic.equals("Systolic"))  && !(ls_diastolic.equals("")) &&  !(ls_diastolic.equals("Diastolic")) && !(ls_pulse.equals("")) &&  !(ls_pulse.equals("Pulse")))
				{
					cv = new ContentValues();
					cv.clear();
		
					//Log.v(TAG,"ls_date : "+ls_date +",ls_time : "+ls_time +",ls_pulse : "+ls_pulse +
					//		",ls_systolic : "+ls_systolic +",ls_diastolic : "+ls_diastolic+",profile_id : "+profile_id);
		
					cv.put(BloodPressure_Master.PROFILE_ID, profile_id);
					cv.put(BloodPressure_Master.PM1_VALUE,  ls_systolic);
					cv.put(BloodPressure_Master.PM2_VALUE,  ls_diastolic);
					cv.put(BloodPressure_Master.PM3_VALUE,  ls_pulse);
					cv.put(BloodPressure_Master.MSR_TS,     ls_msr_ts);
					cv.put(BloodPressure_Master.MSR_LOCATION, ls_loc);
					cv.put(BloodPressure_Master.MSR_POSITION, ls_pos);
					cv.put(BloodPressure_Master.MSR_REMARKS, ls_remark);
					
					//Log.v(TAG,"CV : "+cv.toString());
		
					if(is_data_edit == true)
					{
						bp_master.edit_pressure(cv, bd_id);
					}
					else
					{
						bp_master.insert(cv);

						// adding row count for show share/rate dialog
						
						getRecordCount("ADDED");
						//
					}

                    Toast.makeText(context, "Reading saved", Toast.LENGTH_SHORT).show();
					
					Intent i = new Intent();
					setResult(RESULT_OK, i);
		
				}
				v = ll_add_pressure;
			}
			else if(tabClicked.equals(MeasurementList_Profile.SUGAR))
			{
				String ls_date1,ls_date2,ls_time1,ls_time2,ls_value1,ls_value2,ls_remark1,ls_remark2,  ls_msr_ts, ls_msr_ts2;
				 
				 ls_date1 = et_sugardate.getText().toString();
				 ls_time1 = et_sugartime.getText().toString();

                //ls_date2 = et_sugardate2.getText().toString();
                ls_time2 = et_sugartime2.getText().toString();
				 //ls_msr_ts = ls_date+"\t"+ls_time;
				 ls_remark1 = et_sugarremark1.getText().toString();
                ls_remark2 = et_sugarremark2.getText().toString();
				 //ls_value1 = sp_sugarfasting.getItemAtPosition(sp_sugarfasting.getSelectedItemPosition()).toString();
				 //ls_value2 = sp_sugarpp.getItemAtPosition(sp_sugarpp.getSelectedItemPosition()).toString();
				 
				 ls_value1 = et_sugarfasting.getText().toString();
				 ls_value2 = et_sugarpp.getText().toString();
                //Log.v(TAG, "ls_date1 : "+ls_date1);
				 //ls_msr_ts = Date+" "+Time;
                String date[] =ls_date1.split(" ");
                String d = date[0];
                String m = getdatebyMonth(date[1]);
                String y= date[2];
               // String date1 = y+"-"+m+"-"+d+" "+ls_time1+":00";
                String date1 = y+"-"+m+"-"+d;

                String time1 = ls_time1+":"+"00";
                String time2 = ls_time2+":"+"00";

//                Log.v(TAG, "date to save : "+d);
//                Log.v(TAG, "month to save : "+date[1]);
//                Log.v(TAG, "year to save : "+y);
//                ls_msr_ts = date1;
//                Log.v(TAG, "date and time to save : "+ls_msr_ts);

//                String date2[] =ls_date2.split(" ");
//                String d2 = date2[0];
//                String m2 = String.valueOf(getdatebyMonth(date2[1]));
//                String y2= date2[2];
                //String date3 = y+"-"+m+"-"+d+" "+ls_time2+":00";

                //ls_msr_ts2 = date3;

				 if(ls_value1 == null)
				 {
					 ls_value1 = "";

				 }
				 if(ls_value2 == null)
				 {
					 ls_value2 = "";


				 }
				 if(ls_remark1 == null)
				 {
					 ls_remark1 = "";
				 }
                if(ls_remark2 == null)
                {
                    ls_remark2 = "";
                }


                if(is_data_edit == true)
                {

                }
                else
                {
                    if(ls_value1.equals(""))
                    {
                        time1 ="";
                    }
                    else  if(ls_value2.equals(""))
                    {
                        time2 = "";
                    }
                }

				
				if((!(ls_value1.equals("")) && !(ls_value1.equals("Fasting"))) || (!(ls_value2.equals("")) && !(ls_value1.equals("PP"))))
				{


					cv = new ContentValues();
						
					cv.clear();
							
					cv.put(Sugar_Master.PROFILE_ID, profile_id);
                    cv.put(Sugar_Master.MSR_DATE,     date1);
					cv.put(Sugar_Master.PM4_VALUE,  ls_value1);
					cv.put(Sugar_Master.PM4_TIME,     time1);
					cv.put(Sugar_Master.PM4_REMARKS,ls_remark1);
					cv.put(Sugar_Master.PM5_VALUE,  ls_value2);
					cv.put(Sugar_Master.PM5_TIME,     time2);
					cv.put(Sugar_Master.PM5_REMARKS, ls_remark2);
					
					
					if(is_data_edit == true)
					{
						sugar_master.edit_sugar(cv, bs_id);
					}
					else
					{
						sugar_master.insert(cv);
						// adding row count for show share/rate dialog
						
						getRecordCount("ADDED");
						//
					}
                    Toast.makeText(context, "Reading saved", Toast.LENGTH_SHORT).show();

					
					Intent i = new Intent();
					setResult(RESULT_OK, i);
				}
				
				v = ll_add_sugar;
				
			}
			
			
			
				rootLayout.clearAnimation();
				
				FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, v, 0);
				
				if (ll_elvlist.getVisibility() == View.GONE)
			    {
			        flipAnimation.reverse();
			    }
				rootLayout.startAnimation(flipAnimation);
			
			
			bt_showgraph.setVisibility(View.VISIBLE);
			bt_export.setVisibility(View.VISIBLE);
			ll_bt_export.setVisibility(View.VISIBLE);
			bt_add.setText(ADD_READING);
			bt_showgraph.setText(VIEW_GRAPH);
			
			bt_add.setCompoundDrawablesWithIntrinsicBounds(plus, null, null, null);
			bt_showgraph.setCompoundDrawablesWithIntrinsicBounds(graph, null, null, null);
			
			//bt_showgraph.setPadding(70, 0, 0, 0);
			//bt_add.setCompoundDrawablePadding();
			//bt_showgraph.setCompoundDrawablePadding(10);
			
			setAdapter();
			}
			
		}
	}
	
	
	public void setDate(View view) 
	{
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() 
		{

			@SuppressLint("NewApi")
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) 
			{

				monthOfYear = monthOfYear+1;
				String month1 = getMonth(monthOfYear);
				
				String mn, d = null;
				
				if(monthOfYear<10)
				{
	
					mn = "0"+String.valueOf(monthOfYear);
				}
				else
				{
					mn= String.valueOf(monthOfYear);
				}
				
				if(dayOfMonth<10)
				{
					d = "0"+String.valueOf(dayOfMonth);
				}
				else
				{
					d= String.valueOf(dayOfMonth);
				}
				
				//Log.v(TAG,"monthOfYear : "+monthOfYear +", dayOfMonth : "+dayOfMonth);

				if(tabClicked.equals(PRESSURE))
				{
					et_pressuredate.setText(d+" "+month1+", "+year);
					
					//2014-12-21 12:00:12
					Date = year+"-"+mn+"-"+d;
				}
				else
				{

                        et_sugardate.setText(d + " " + month1 + ", " + year);

					
					Date = year+"-"+mn+"-"+d;
				}

				//Log.v(TAG,"Date12>>>"+Date);
				
				//tv_date.setText(new StringBuilder().append(dayOfMonth).append("/")
				//	      .append(monthOfYear+1).append("/").append(year));


			}
		}, year, month, day);

		dpd.show();
	}


	public void setTime(final View v)
	{
		final Calendar c = Calendar.getInstance();
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int  mMinute = c.get(Calendar.MINUTE);

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() 
		{

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
					int minute)
			{


				String hour1,min1;

				if(hourOfDay<10)
				{
					hour1 = "0"+String.valueOf(hourOfDay);
				}
				else
				{
					hour1 = String.valueOf(hourOfDay);
				}

				if(minute<10)
				{
					min1 = "0"+String.valueOf(minute);
				}
				else
				{
					min1 = String.valueOf(minute);
				}

				
				
				if(tabClicked.equals(PRESSURE))
				{
					et_pressuretime.setText(hour1+":"+min1);
					
					//2014-12-21 12:00:12
					Time = hour1+":"+min1+":00";
				}
				else
				{
					et_sugartime.setText(hour1+":"+min1);
					
					Time = hour1+":"+min1+":00";
				}

			}
		}, mHour, mMinute, DateFormat.is24HourFormat(context));

		tpd.show();
	}



    public void setDate2(View view)
    {
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {

                    @SuppressLint("NewApi")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {

                        monthOfYear = monthOfYear+1;
                        String month1 = getMonth(monthOfYear);

                        String mn, d = null;

                        if(monthOfYear<10)
                        {

                            mn = "0"+String.valueOf(monthOfYear);
                        }
                        else
                        {
                            mn= String.valueOf(monthOfYear);
                        }

                        if(dayOfMonth<10)
                        {
                            d = "0"+String.valueOf(dayOfMonth);
                        }
                        else
                        {
                            d= String.valueOf(dayOfMonth);
                        }

                        //Log.v(TAG,"monthOfYear : "+monthOfYear +", dayOfMonth : "+dayOfMonth);


                            et_sugardate2.setText(d + " " + month1 + ", " + year);


                            Date = year+"-"+mn+"-"+d;


                        //Log.v(TAG,"Date12>>>"+Date);

                        //tv_date.setText(new StringBuilder().append(dayOfMonth).append("/")
                        //	      .append(monthOfYear+1).append("/").append(year));


                    }
                }, year, month, day);

        dpd.show();
    }


    public void setTime2(final View v)
    {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int  mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
        {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute)
            {


                String hour1,min1;

                if(hourOfDay<10)
                {
                    hour1 = "0"+String.valueOf(hourOfDay);
                }
                else
                {
                    hour1 = String.valueOf(hourOfDay);
                }

                if(minute<10)
                {
                    min1 = "0"+String.valueOf(minute);
                }
                else
                {
                    min1 = String.valueOf(minute);
                }




                    et_sugartime2.setText(hour1+":"+min1);

                    Time = hour1+":"+min1+":00";


            }
        }, mHour, mMinute, DateFormat.is24HourFormat(context));

        tpd.show();
    }


    public void setDateTo(View view)
	{
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() 
		{

			@SuppressLint("NewApi")
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) 
			{

				monthOfYear = monthOfYear+1;
				String month1 = getMonth(monthOfYear);
				Log.v(TAG,"monthOfYear : "+monthOfYear +", month1 : "+month1);

				
				
				String mn, d = null;
				
				if(monthOfYear<10)
				{
	
					mn = "0"+String.valueOf(monthOfYear);
				}
				else
				{
					mn= String.valueOf(monthOfYear);
				}
				
				if(dayOfMonth<10)
				{
					d = "0"+String.valueOf(dayOfMonth);
				}
				else
				{
					d= String.valueOf(dayOfMonth);
				}
					
				et_to_date.setText(d+" "+month1+", "+year);
				//2014-12-21 12:00:12
				toDate = year+"-"+mn+"-"+d;
					
				Log.v(TAG, "toDate date :"+toDate);
				dateRange();


			}
		}, year, month, day);

		dpd.show();
	}
	
	public void setDateFrom(View view) 
	{
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() 
		{

			@SuppressLint("NewApi")
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) 
			{

				monthOfYear = monthOfYear+1;
				String month1 = getMonth(monthOfYear);
				Log.v(TAG,"monthOfYear : "+monthOfYear +", month1 : "+month1);
				
				

				String mn, d = null;
				
				if(monthOfYear<10)
				{
	
					mn = "0"+String.valueOf(monthOfYear);
				}
				else
				{
					mn= String.valueOf(monthOfYear);
				}
				
				if(dayOfMonth<10)
				{
					d = "0"+String.valueOf(dayOfMonth);
				}
				else
				{
					d= String.valueOf(dayOfMonth);
				}
				
				et_from_date.setText(d+" "+month1+", "+year);
				
				//2014-12-21 12:00:12
				fromDate = year+"-"+mn+"-"+d;
				
				Log.v(TAG, "from date :"+fromDate);
				
				//et_from_date.setText(new StringBuilder().append(dayOfMonth).append("/")
				//	      .append(monthOfYear).append("/").append(year));

                dateRange();
			}
		}, year, month, day);

		dpd.show();
	}

	public String getMonth(int month)
	{
		String ls_month = null;

		if(month == 1)
		{
			ls_month = "Jan";
		}
		else if(month == 2)
		{
			ls_month = "Feb";
		}
		else if(month == 3)
		{
			ls_month = "Mar";
		}
		else if(month ==4)
		{
			ls_month = "Apr";
		}
		else if(month == 5)
		{
			ls_month = "May";
		}
		else if(month == 6)
		{
			ls_month = "Jun";
		}
		else if(month == 7)
		{
			ls_month = "Jul";
		}
		else if(month == 8)
		{
			ls_month = "Aug";
		}
		else if(month == 9)
		{
			ls_month = "Sep";
		}
		else if(month == 10)
		{
			ls_month = "Oct";
		}
		else if(month == 11)
		{
			ls_month = "Nov";
		}
		else if(month == 12)
		{
			ls_month = "Dec";
		}

		return ls_month;
	}


    public String getdatebyMonth(String month)
    {
        String li_month = null;

        if(month.equals("Jan,"))
        {
            li_month = "01";
        }
        else if(month.equals("Feb,"))
        {
            li_month = "02";
        }
        else if(month.equals("Mar,"))
        {
            li_month = "03";
        }
        else if(month.equals("Apr,"))
        {
            li_month = "04";
        }
        else if(month.equals("May,"))
        {
            li_month = "05";
        }
        else if(month.equals("Jun,"))
        {
            li_month = "06";
        }
        else if(month.equals("Jul,"))
        {
            li_month = "07";
        }
        else if(month.equals("Aug,"))
        {
            li_month = "08";
        }
        else if(month.equals("Sep,"))
        {
            li_month = "09";
        }
        else if(month.equals("Oct,"))
        {
            li_month = "10";
        }
        else if(month.equals("Nov,"))
        {
            li_month = "11";
        }
        else if(month.equals("Dec,"))
        {
            li_month = "12";
        }

        return li_month;
    }

	
	public void dateRange()
	{
		String from = et_from_date.getText().toString();
		String to = et_to_date.getText().toString();

        Log.v(TAG,"FROM DATE : "+fromDate);
        Log.v(TAG,"TO DATE : "+toDate);
		showGraphByDate(fromDate, toDate);
		//et_from_date.setText("");
		//et_to_date.setText("");
	}
	
	@SuppressLint("SimpleDateFormat")
	
	public void showGraphByDate(String from, String to)
	{
		
		ll_graph.removeAllViews();
		
		if(tabClicked.equals(PRESSURE))
		{
			ll_cb_pressure.setVisibility(View.VISIBLE);
			ll_cb_sugar.setVisibility(View.GONE);
			
			alist = new ArrayList<Bundle>();
			alist = bp_master.getMeasurementList(profile_id, from, to);
			
			Log.v(TAG,"List :"+alist.toString());
			
			diastoliclist = new int[alist.size()];
			systoliclist = new int[alist.size()];
			pulselist = new int[alist.size()];
			dates = new String[alist.size()];
				
			size = alist.size();
			for (int i = 0; i<alist.size(); i++)
			{
				diastoliclist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM1_VALUE));
					
				systoliclist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM2_VALUE));
				pulselist[i] = Integer.parseInt(alist.get(i).getString(BloodPressure_Master.PM3_VALUE));
					
				dates[i] = alist.get(i).getString(BloodPressure_Master.MSR_TS).trim();
			}
			
			openChartPressure(diastoliclist, systoliclist, pulselist, dates, size);
		}
		else if(tabClicked.equals(SUGAR))
		{
		 
			ll_cb_pressure.setVisibility(View.GONE);
			ll_cb_sugar.setVisibility(View.VISIBLE);
			
		 	 alist = new ArrayList<Bundle>();
			 alist = sugar_master.getMeasurementList(profile_id,from, to);
			 
			 Log.v(TAG,"list sugar : "+alist.toString());
		 
			 fastinglist = new int[alist.size()];
			 pplist = new int[alist.size()];
			 dates = new String[alist.size()];
			 String fast, pp;
			 
			 size = alist.size();
			 	
			 for (int i = 0; i<alist.size(); i++)
			 {
				 fast = alist.get(i).getString(Sugar_Master.PM4_VALUE);
				 pp = alist.get(i).getString(Sugar_Master.PM5_VALUE);
				 
				 if(fast == null || fast.equals(""))
				 {
					 fast = "0";
				 }
				 
				 //error at this line
				 fastinglist[i] = Integer.parseInt(fast);
				 
				 if(pp == null || pp.equals(""))
				 {
					 pp = "0";
				 }
				 pplist[i] = Integer.parseInt(pp);
						
					
				dates[i] = alist.get(i).getString(Sugar_Master.MSR_DATE).trim();
				Log.v(TAG,"date -"+i+":"+dates[i]);
			 }
				
			 openChartSugar(fastinglist, pplist, dates, size);
		} 
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View rowView, int position, long id) 
	{
		
		RowPosition = position;
//		LinearLayout ll_listmain = (LinearLayout)findViewById(R.id.ll_listmain);
//		LinearLayout ll_expanding = (LinearLayout)findViewById(R.id.ll_expanding);
		
//		FlipAnimation flipAnimation = new FlipAnimation(rowView, ll_expanding, 0);
//		
//		if (rowView.getVisibility() == View.GONE)
//	    {
//	        flipAnimation.reverse();
//	    }
//		
//		ll_listmain.startAnimation(flipAnimation);
		////////
//		View toolbar = rowView.findViewById(R.id.ll_expanding);
//		toolbar.clearAnimation();
//
//		// Creating the expand animation for the item
//		ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
//
//		// Start the animation on the toolbar
//		toolbar.startAnimation(expandAni);
		
		if (lvSwipeDetector.swipeDetected())
		{
			//GOOGLE ANALYTICS Event Tracking  - Measurement delete
            if(DbHelper.USE_ANALYTICS)
            {
                if (tabClicked.equals(PRESSURE)) {
                    call_GoogleAnalytics_events("MeasureMent", "Delete",
                            "BloodPressure", 0);
                } else {
                    call_GoogleAnalytics_events("MeasureMent", "Delete",
                            "BloodSugar", 0);
                }
            }
			//GOOGLE ANALYTICS Event Tracking  - Measurement delete
			 
			Message msg = new Message();
			//msg.arg1 = position - 1;
			msg.arg1 = position;

			if (lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.LR || 
					lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.RL)
			{
				msg.what = MSG_ANIMATION_REMOVE;
				msg.arg2 = lvSwipeDetector.getAction() == ListViewSwipeDetector.Action.LR ? 1 : 0;
				msg.obj  = rowView;
				handler.sendMessage(msg);
				Log.v(TAG, "IN swipe Detection");
			}


		}
		else
		{

            show_toast();
			
			//GOOGLE ANALYTICS Event Tracking  - Measurement Edit
            if(DbHelper.USE_ANALYTICS)
            {
                if (tabClicked.equals(PRESSURE)) {
                    call_GoogleAnalytics_events("MeasureMent", "Edit",
                            "BloodPressure", 0);
                } else {
                    call_GoogleAnalytics_events("MeasureMent", "Edit",
                            "BloodSugar", 0);
                }
            }
			//GOOGLE ANALYTICS Event Tracking  - Measurement Edit
			 
			ll_custom_keyboard.setVisibility(View.GONE);
			super.adView.setVisibility(View.GONE);
				
			bt_showgraph.setVisibility(View.GONE);
			bt_export.setVisibility(View.GONE);

			ll_bt_export.setVisibility(View.GONE);

            bt_add.setText(VIEW_LIST);
		
		is_data_edit = true;
		if(tabClicked.equals(PRESSURE))
		{
		
			bd_id =  measure_list.get(position).getInt(BloodPressure_Master.MSR_ID);

            String Date = measure_list.get(position).getString("date").trim();
            String tm  = measure_list.get(position).getString("time").trim();
			String High  = measure_list.get(position).getString(BloodPressure_Master.PM1_VALUE);
			String Low = measure_list.get(position).getString(BloodPressure_Master.PM2_VALUE);
			String pulse = measure_list.get(position).getString(BloodPressure_Master.PM3_VALUE);
			String pos = measure_list.get(position).getString(BloodPressure_Master.MSR_POSITION);
			String loc = measure_list.get(position).getString(BloodPressure_Master.MSR_LOCATION);
			String remark = measure_list.get(position).getString(BloodPressure_Master.MSR_REMARKS);
		
			Log.v(TAG,"bd_id :"+bd_id);
			Log.v(TAG,"Date:"+Date);
			String date[] =Date.split("-");
			String d = date[2];
			String m = getMonth(Integer.parseInt(date[1]));
			String y= date[0];
			String date1 = d+" "+m+", "+y;
			
			String time[] =tm.split(":");
			String h =  time[0];
			String mn = time[1];
			//String s= date[2];
			String tm1 = h+":"+mn;
			
			/*
			for (int i=0;i<sp_list4.size();i++)
			{
				   if (sp_pressuredias.getItemAtPosition(i).toString().equalsIgnoreCase(Low))
				   { 
					   sp_pressuredias.setSelection(i);
				   }
			}
			
			for (int i=0;i<sp_list3.size();i++)
			{
				   if (sp_pressuresys.getItemAtPosition(i).toString().equalsIgnoreCase(High))
				   { 
					   sp_pressuresys.setSelection(i);
				   }
			}
			for (int i=0;i<sp_list5.size();i++)
			{
				   if (sp_pressurepulse.getItemAtPosition(i).toString().equalsIgnoreCase(pulse))
				   { 
					   sp_pressurepulse.setSelection(i);
				   }
			}
			*/
			
			for (int i=0;i<sp_list1.size();i++)
			{
				   if (sp_pressurepos.getItemAtPosition(i).toString().equalsIgnoreCase(pos))
				   { 
					   sp_pressurepos.setSelection(i);
				   }
			}
			
			for (int i=0;i<sp_list2.size();i++)
			{
				   if (sp_pressureloc.getItemAtPosition(i).toString().equalsIgnoreCase(loc))
				   { 
					   sp_pressureloc.setSelection(i);
				   }
			}
			
			
			et_pressuredate.setText(date1);
			et_pressuretime.setText(tm1);
			et_pressureremark.setText(remark);
			
			et_pressuresys.setText(High);
			et_pressuredias.setText(Low);
			et_pressurepulse.setText(pulse);
			//////
			FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, ll_add_pressure, 0);
			
			if (ll_elvlist.getVisibility() == View.GONE)
		    {
		        flipAnimation.reverse();
		    }
			
			rootLayout.startAnimation(flipAnimation);
			bt_add.setText(VIEW_LIST);
			bt_add.setCompoundDrawablesWithIntrinsicBounds(list, null, null, null);
			
		}
		else
		{
			bs_id =  measure_list.get(position).getInt(Sugar_Master.MSR_ID);
			//String Date1 = measure_list.get(position).getString("date1").trim();
			//String time1 = measure_list.get(position).getString("time1").trim();
            //String Date2 = measure_list.get(position).getString("date2").trim();
            //String time2 = measure_list.get(position).getString("time2").trim();
            String Date1 = measure_list.get(position).getString(Sugar_Master.MSR_DATE).trim();
            String time1 = measure_list.get(position).getString(Sugar_Master.PM4_TIME).trim();
            String time2 = measure_list.get(position).getString(Sugar_Master.PM5_TIME).trim();
			String fast  = measure_list.get(position).getString(Sugar_Master.PM4_VALUE);
			String pp = measure_list.get(position).getString(Sugar_Master.PM5_VALUE);
			String remark1 = measure_list.get(position).getString(Sugar_Master.PM4_REMARKS);
            String remark2 = measure_list.get(position).getString(Sugar_Master.PM5_REMARKS);
		
			Log.v(TAG,"bs_id :"+bs_id);
			Log.v(TAG,"Date:"+Date1);
			String date[] =Date1.split("-");
			String d = date[2];
			String m = getMonth(Integer.parseInt(date[1]));
			String y= date[0];
			String date1 = d+" "+m+", "+y;

            String tm1;
            if(!(time1.equals("")))
            {
                String time[] = time1.split(":");
                String h = time[0];
                String mn = time[1];
                //String s= date[2];
                tm1 = h + ":" + mn;
            }
            else
            {
                 tm1 = "";
            }

//            String date2[] =Date2.split("-");
//            String d2 = date2[2];
//            String m2 = getMonth(Integer.parseInt(date2[1]));
//            String y2= date2[0];
//            String date3 = d2+" "+m2+", "+y2;
            String tm2;
            if(!(time2.equals("")))
            {
                String time3[] = time2.split(":");
                String h2 = time3[0];
                String mn2 = time3[1];
                //String s= date[2];
                tm2 = h2 + ":" + mn2;
            }
            else
            {
                tm2 = "";
            }
			
			/*
			for (int i=0;i<sp_list6.size();i++)
			{
				   if (sp_sugarfasting.getItemAtPosition(i).toString().equalsIgnoreCase(fast))
				   { 
					   sp_sugarfasting.setSelection(i);
				   }
			}
			
			for (int i=0;i<sp_list7.size();i++)
			{
				   if (sp_sugarpp.getItemAtPosition(i).toString().equalsIgnoreCase(pp))
				   { 
					   sp_sugarpp.setSelection(i);
				   }
			}
			*/
			
			
			et_sugardate.setText(date1);
			et_sugartime.setText(tm1);
            //et_sugardate2.setText(date3);
            et_sugartime2.setText(tm2);
			et_sugarremark1.setText(remark1);
            et_sugarremark2.setText(remark2);
			
			et_sugarfasting.setText(fast);
			et_sugarpp.setText(pp);
			//////
			FlipAnimation flipAnimation = new FlipAnimation(ll_elvlist, ll_add_sugar, 0);
			
			if (ll_elvlist.getVisibility() == View.GONE)
		    {
		        flipAnimation.reverse();
		    }
			
			rootLayout.startAnimation(flipAnimation);
		}
		}
		
	}

	
	
/*	private class FakeNetLoader extends AsyncTask<String, Void, List<String>> 
	{
		@Override
		protected List<String> doInBackground(String... params) 
		{
			try 
			{
				Thread.sleep(4000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return createItems(mult);
		}
		
		@Override
		protected void onPostExecute(List<String> result) 
		{
			super.onPostExecute(result);
			lv_measures.addNewData(result);
		}
	}
		
	private List<String> createItems(int mult) 
	{
		ArrayList<String> result = new ArrayList<String>();
		for (int i=1; i < ITEM_PER_REQUEST; i++) 
		{
			result.add("Item " + (i * mult));
		}
		return result;
	}
		
	@Override
	public void loadData() 
	{
		System.out.println("Load data");
		mult += 1;
		// We load more data here
		FakeNetLoader fl = new FakeNetLoader();
		fl.execute(new String[]{});
	}
*/
	/*
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) 
	{
		int MAXIMUM_ITEMS =0;
		if(tabClicked.equals(PRESSURE))
    	{
    		measure_list = new ArrayList<Bundle>();
    		measure_list = bp_master.getMeasurementList(profile_id);
    		
    		 MAXIMUM_ITEMS = measure_list.size();
    		if (!mIsLoading && mMoreDataAvailable) 
    		{
                if (totalItemCount >= MAXIMUM_ITEMS) 
                {
                    mMoreDataAvailable = false;
                   lv_measures.removeFooterView(mFooterView);
                } 
                else if (totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount) 
                {
                    mIsLoading = true;
                    mHandler.postDelayed(mAddItemsRunnable, 1000);
                }
            }
    	}
    	else
    	{
    		measure_list = new ArrayList<Bundle>();
        	measure_list = sugar_master.getMeasurementList(profile_id);
        	
        	 MAXIMUM_ITEMS = measure_list.size();
        	if (!mIsLoading && mMoreDataAvailable) 
    		{
                if (totalItemCount >= MAXIMUM_ITEMS) 
                {
                    mMoreDataAvailable = false;
                   lv_measures.removeFooterView(mFooterView);
                } 
                else if (totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount) 
                {
                    sIsLoading = true;
                    mHandler.postDelayed(mAddItemsRunnable, 1000);
                }
            }
    	}
		
		
		Log.v(TAG,"Max size :"+MAXIMUM_ITEMS);
		
//		if (!mIsLoading && mMoreDataAvailable) 
//		{
//            if (totalItemCount >= MAXIMUM_ITEMS) 
//            {
//                mMoreDataAvailable = false;
//               lv_measures.removeFooterView(mFooterView);
//            } 
//            else if (totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount) 
//            {
//                mIsLoading = true;
//                mHandler.postDelayed(mAddItemsRunnable, 1000);
//            }
//        }
		
	}
	
	
	private Runnable mAddItemsRunnable = new Runnable() {
        @Override
        public void run() 
        {
        	int MAXIMUM_ITEMS = 0,  more;
        	
        	if(tabClicked.equals(PRESSURE))
        	{
        		measure_list = new ArrayList<Bundle>();
        		measure_list = bp_master.getMeasurementList(profile_id);
        		
        		 MAXIMUM_ITEMS = measure_list.size();
            	
        		Log.v(TAG,"Max size :"+MAXIMUM_ITEMS);
        		more = MAXIMUM_ITEMS - SimpleAdapter.mCount;    	
        		Log.v(TAG,"more :"+more);
        		
                mAdapter.addMoreItems(more);
                mIsLoading = false;
        	}
        	else
        	{
        		measure_list = new ArrayList<Bundle>();
            	measure_list = sugar_master.getMeasurementList(profile_id);
            	
            	MAXIMUM_ITEMS = measure_list.size();
            	
        		Log.v(TAG,"Max size :"+MAXIMUM_ITEMS);
        		more = MAXIMUM_ITEMS - SugarListAdapter.mCount;    	
        		Log.v(TAG,"more :"+more);
        		
                sugar_adapter.addMoreItems(more);
                sIsLoading = false;
        	}
//        	int MAXIMUM_ITEMS = measure_list.size();
//        	
//    		Log.v(TAG,"Max size :"+MAXIMUM_ITEMS);
//    		int more = MAXIMUM_ITEMS - SimpleAdapter.mCount;    	
//    		Log.v(TAG,"more :"+more);
//    		
//            mAdapter.addMoreItems(more);
//            mIsLoading = false;
        }
    };
    
    @Override
    public void onStart() 
    {
        super.onStart();
        if (mWasLoading) 
        {
            mWasLoading = false;
            mIsLoading = true;
            //mHandler.postDelayed(mAddItemsRunnable, 1000);
            
            sWasLoading = false;
            sIsLoading = true;
            mHandler.postDelayed(mAddItemsRunnable, 1000);
        }
    }

    @Override
    public void onStop() 
    {
        super.onStop();
        mHandler.removeCallbacks(mAddItemsRunnable);
        mWasLoading = mIsLoading;
        mIsLoading = false;
        
        sWasLoading = mIsLoading;
        sIsLoading = false;
    }

@Override
public void onScrollStateChanged(AbsListView view, int scrollState) {
	// TODO Auto-generated method stub
	
}
*/
	
	
	
	public class Task_createPDF extends AsyncTask<Integer, Integer, Void> 
	{
		String value ;

		Task_createPDF(String value)
		{
			this.value = value;
		}
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			progressDialog = new Dialog(context,R.style.crescoDialogStyle);
			progressDialog.setContentView(R.layout.progressbar_round);
			
			CrescoTextView tv_loading = (CrescoTextView) progressDialog.findViewById(R.id.loading);

            if(value.equalsIgnoreCase("pdf"))
            {
                tv_loading.setText("Exporting PDF...");
            }
            else
            {
                tv_loading.setText("Exporting CSV...");
            }

			//tv_loading.setVisibility(View.GONE);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Integer... params)
		{ 
			if(value.equalsIgnoreCase("pdf"))
			{
				//exportAsPDF();

                exportAsPDF_New();
			}
			else
			{
				exportAsCSV();
			}
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			
		}
		
		
	}
	/*
	private boolean exportAsPDF()
{
    final Calendar c = Calendar.getInstance();
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH);
    day = c.get(Calendar.DAY_OF_MONTH);
    hour = c.get(Calendar.HOUR_OF_DAY);
    min = c.get(Calendar.MINUTE);

    mth = getMonth(month+1);

    String  d = null;

    if(day<10)
    {
        d = "0"+String.valueOf(day);
    }
    else
    {
        d= String.valueOf(day);
    }

    if(hour<10)
    {
        hr = "0"+String.valueOf(hour);
    }
    else
    {
        hr = String.valueOf(hour);
    }

    if(min<10)
    {
        mn = "0"+String.valueOf(min);
    }
    else
    {
        mn = String.valueOf(min);
    }

    Log.v(TAG,"mth : "+mth +", month : "+month);


    int m = month+1;
    String m1;

    if(m<10)
    {
        m1 = "0"+String.valueOf(m);
    }
    else
    {
        m1 = String.valueOf(m);
    }

    String TS = year+"-"+m1+"-"+d+" "+hr+":"+mn;

    //File exportDir;

    if(tabClicked.equals(PRESSURE))
    {
        exportDir = new File(android.os.Environment.getExternalStorageDirectory()+"/HealthMate/"+profile_fname+" "+profile_lname+"/"+PRESSURE, "");
    }
    else
    {
        exportDir = new File(android.os.Environment.getExternalStorageDirectory()+"/HealthMate/"+profile_fname+" "+profile_lname+"/"+SUGAR, "");
    }

    if (!exportDir.exists())
    {
        exportDir.mkdirs();
    }

    ls_filename = TS+".pdf";

    File file = new File(exportDir, ls_filename);
    try
    {

        file.createNewFile();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        //addMetaData(document);
        //addTitlePage(document);
        //addContent(document);
        addTable(document);
        document.close();


        progressDialog.dismiss();

        String path = exportDir.getAbsolutePath().toString()+"/"+ls_filename;

        confirmDialog = new ConfirmDialog(context, path, profile_id, "share", tabClicked);
        confirmDialog.show(getFragmentManager(), TAG);

        return true;
    }
    catch(Exception sqlEx)
    {
        Log.v(TAG, sqlEx.getMessage(), sqlEx);
        return false;
    }
}
    */
	
	private boolean exportAsCSV()
	{
		
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		
		mth = getMonth(month+1);
		
		String  d = null;
		
		if(day<10)
		{
			d = "0"+String.valueOf(day);
		}
		else
		{
			d= String.valueOf(day);
		}

		if(hour<10)
		{
			hr = "0"+String.valueOf(hour);
		}
		else
		{
			hr = String.valueOf(hour);
		}

		if(min<10)
		{
			mn = "0"+String.valueOf(min);
		}
		else
		{
			mn = String.valueOf(min);
		}

		Log.v(TAG,"mth : "+mth +", month : "+month);
		
		
		int m = month+1;
		String m1;
		
		if(m<10)
		{
			m1 = "0"+String.valueOf(m);
		}
		else
		{
			m1 = String.valueOf(m);
		}
		
		String TS = year+"-"+m1+"-"+d+" "+hr+":"+mn;
		
		//android.os.Environment.getExternalStorageDirectory().toString();
		//File exportDir;
		
		if(tabClicked.equals(PRESSURE))
		{
			exportDir = new File(Environment.getExternalStorageDirectory() + "/HealthMate/"+profile_fname+" "+profile_lname+"/"+PRESSURE, "");
		}
		else
		{
			exportDir = new File(Environment.getExternalStorageDirectory() + "/HealthMate/"+profile_fname+" "+profile_lname+"/"+SUGAR, "");
		}
		
		if (!exportDir.exists())
		{
			exportDir.mkdirs();
		}
		
		 ls_filename  = TS+".csv";

		File file = new File(exportDir, ls_filename);
		try
		{
			
			file.createNewFile();
			JSONObject dataObject = null;
			JSONObject jsonObject = null;
			JSONObject configObj  = null;
			JSONArray rowArray 	  = new JSONArray();
			
			CSVWriter csvWrite 	  = null;


			csvWrite = new CSVWriter(new FileWriter(file));

			String headStr[] = new String[4];
			headStr[0] = "";
			headStr[3] = "HealthMate Report";
			csvWrite.writeNext(headStr);
			
			
			String headStr1[] = new String[3];
			headStr1[0] = "";
			headStr1[2] = "This report is generated from HealthMate Mobile App";
			csvWrite.writeNext(headStr1);
			
			String headStr2[] = new String[3];
			headStr2[0] = "";
			csvWrite.writeNext(headStr2);
			
			

			String ls_fname = profile_list.get(0).getString(Profile_Master.FIRST_NAME);
			String ls_lname = profile_list.get(0).getString(Profile_Master.LAST_NAME);
			String gender = profile_list.get(0).getString(Profile_Master.GENDER);
			String dob = profile_list.get(0).getString(Profile_Master.DOB);
			
			String dob1[] = dob.split("/");
			String dob2 = dob1[2];
			
			Log.v(TAG, "dob :"+dob +".. dob2 : "+dob1[2]+ "... current year :" +year);
			
			int age = year -  Integer.parseInt(dob2);
			
			
			String headStr3[] = new String[8];
			headStr3[0] = "Name : ";
			headStr3[2] = ls_fname+ " "+ls_lname;
			headStr3[4] = "Age : ";
			headStr3[5] = String.valueOf(age);
			headStr3[6] = "Gender : ";
			headStr3[7] = gender;
			csvWrite.writeNext(headStr3);
			
			String headStr4[] = new String[7];
			headStr4[0] = "Report Date : ";
			headStr4[2] = day+" "+mth+" "+year;
			headStr4[4] = "Report Time :";
			headStr4[6] = hr+":"+mn;
			csvWrite.writeNext(headStr4);
			
			String headStr5[] = new String[3];
			headStr5[0] = "";
			csvWrite.writeNext(headStr5);
			

			String frdate = confirmDialog.et_from.getText().toString();
		    
		    Log.v(TAG,"fromDate_export : "+fromDate_export);
		    Log.v(TAG,"frdate : "+frdate);
		    

		    
		    String trdate = confirmDialog.et_to.getText().toString();
		    
		    Log.v(TAG,"toDate_export : "+toDate_export);
		    Log.v(TAG,"trdate : "+trdate);
			
			String headStr6[] = new String[8];
			
			if(tabClicked.equals(PRESSURE))
			{
				headStr6[0] = "BloodPressure Readings";
			}
			else
			{
				headStr6[0] = "Blood Sugar Readings";
			}
			//headStr6[4] = "From "+" " + frdate +" To "+trdate;
			csvWrite.writeNext(headStr6);

			String[] printHeader = new String[9];
			printHeader[0] = "Date";

			
			if(tabClicked.equals(PRESSURE))
			{
                printHeader[1] = "Time";
				printHeader[2] = "Diastolic";
				printHeader[3] = "Systolic";
				printHeader[4] = "pulse";
				printHeader[5] = "Position";
				printHeader[6] = "Location";
				printHeader[7] = "Remark";
			}
			else
			{
                printHeader[1] = "Fasting-Time";
				printHeader[2] = "Fasting";
                printHeader[3] = "Fasting-Remark";
                printHeader[4] = "PP-Time";
				printHeader[5] = "PP";
				printHeader[6] = "PP-Remark";
			}

				
			//jsonObject = new JSONObject();	
			//jsonObject = getJSONRow("header", printHeader);
			//dataObject.put("table_header", jsonObject);
			
			csvWrite.writeNext(printHeader);
			
			String[] printFooter = new String[8];
			printFooter[0] = "----------------";
			printFooter[1] = "----------------";
			printFooter[2] = "----------------";
            printFooter[3] = "----------------";
            printFooter[4] = "----------------";
            printFooter[5] = "----------------";
            printFooter[6] = "----------------";
			if(tabClicked.equals(PRESSURE))
			{

                printFooter[7] = "----------------";
			}


			
			csvWrite.writeNext(printFooter);
			
			measure_list = new ArrayList<Bundle>();
			
			if(tabClicked.equals(PRESSURE))
			{
				measure_list = bp_master.getMeasurement(profile_id, fromDate_export, toDate_export);
			}
			else
			{
				measure_list = sugar_master.getMeasurement(profile_id, fromDate_export, toDate_export);
			}


            Log.v(TAG,"Measurement list :"+measure_list.toString());

			String[] list;
			for(int i =0; i<measure_list.size(); i++)
			{
				//Bundle b = new Bundle();
				//b = measure_list.get(i);
				list = new String[8];

                if(tabClicked.equals(PRESSURE))
                {
				    String d1 = measure_list.get(i).getString("date");
				    String tdate1[] = d1.split("-");
			        String trdate1 = tdate1[2]+" "+ getMonth(Integer.parseInt(tdate1[1]))+" "+tdate1[0];
			    
				    list[0]=trdate1;
				    String t = measure_list.get(i).getString("time");
				    String t1[] = t.split(":");
				    String t2 =t1[0];
				    String t3 =t1[1];
				    list[1] =t2+":"+t3;
				

					String dis = measure_list.get(i).getString(BloodPressure_Master.PM1_VALUE);
					list[2]=dis;
					String sys = measure_list.get(i).getString(BloodPressure_Master.PM2_VALUE);
					list[3]=sys;
					String pul = measure_list.get(i).getString(BloodPressure_Master.PM3_VALUE);
					list[4]=pul;
					String pos = measure_list.get(i).getString(BloodPressure_Master.MSR_POSITION);
					list[5]=pos;
					String loc = measure_list.get(i).getString(BloodPressure_Master.MSR_LOCATION);
					list[6]=loc;
					String rem = measure_list.get(i).getString(BloodPressure_Master.MSR_REMARKS);
					list[7]=rem;
					Log.v(TAG,"list : "+list.toString());
				}
				else
				{
                    String d1 = measure_list.get(i).getString(Sugar_Master.MSR_DATE);
                    String tdate1[] = d1.split("-");
                    String trdate1 = tdate1[2]+" "+ getMonth(Integer.parseInt(tdate1[1]))+" "+tdate1[0];

                    list[0]=trdate1;


                    String t = measure_list.get(i).getString(Sugar_Master.PM4_TIME);

                    if(!(t.equals("")))
                    {
                        String t1[] = t.split(":");
                        String t2 = t1[0];
                        String t3 = t1[1];
                        list[1] = t2 + ":" + t3;
                    }
                    else
                    {
                        list[1] = "";
                    }

					String fast = measure_list.get(i).getString(Sugar_Master.PM4_VALUE);


                    if(fast.equals(""))
                    {
                        fast = "";
                    }

                    list[2] = fast;



                    String rem = measure_list.get(i).getString(Sugar_Master.PM4_REMARKS);
                    list[3] = rem;

                    String t7 = measure_list.get(i).getString(Sugar_Master.PM5_TIME);

                    if(!(t7.equals("")))
                    {
                        String t4[] = t7.split(":");
                        String t5 = t4[0];
                        String t6 = t4[1];
                        list[4] = t5 + ":" + t6;
                    }
                    else
                    {
                        list[4] = "";
                    }

					String pp = measure_list.get(i).getString(Sugar_Master.PM5_VALUE);
                    if(pp.equals(""))
                    {
                        pp = "";
                    }
					list[5] = pp;

					String rem1 = measure_list.get(i).getString(Sugar_Master.PM5_REMARKS);
					list[6] = rem1;
				}
				
				csvWrite.writeNext(list);
			}
			
			//String[] printFooter = new String[8];
			//printFooter[0] = "----------------";
			//printFooter[1] = "----------------";
			//printFooter[2] = "----------------";
			//printFooter[3] = "----------------";
			//printFooter[4] = "----------------";
			//printFooter[5] = "----------------";
			//printFooter[6] = "----------------";
			//printFooter[7] = "----------------";

				
			//jsonObject = new JSONObject();
			//jsonObject = getJSONRow("footer", printFooter);
			
			//csvWrite.writeNext(printFooter);
			
			//configObj = new JSONObject();
			//configObj.put("config_border", Rectangle.TOP | Rectangle.BOTTOM);
			//configObj.put("fixed_height", 16.0f);
			//configObj.put("config_font", 10.0f);
			//configObj.put("config_weight", Font.BOLD);
			//configObj.put("v_align", Element.ALIGN_MIDDLE);
			//jsonObject.put("cell_config", configObj);

			//dataObject.put("table_footer", jsonObject);


			//LS_PDF pdf = new LS_PDF(context, LS_PDF.TYPE_LED_BILL);	
			//pdf.printData(dataObject, ls_filename);
			

			csvWrite.close();
			
			 progressDialog.dismiss();
			
			String path = exportDir.getAbsolutePath().toString()+"/"+ls_filename;
			
			confirmDialog = new ConfirmDialog(context, path, profile_id, "share", tabClicked);
			confirmDialog.show(getFragmentManager(), TAG);
			
			return true;
		}
		catch(Exception sqlEx)
		{
			Log.v(TAG, sqlEx.getMessage(), sqlEx);			
			return false;
		}

	}

	private  void addTable(Document document) throws DocumentException
	{

		Paragraph preface = new Paragraph();
		
		PdfPTable t1 =  new PdfPTable(8);
		
		t1.setWidthPercentage(100);
		t1.setHorizontalAlignment(Element.ALIGN_LEFT);
		float[] columnWidths = {20, 20, 18, 15, 10, 15, 2, 0 };

	    try 
	    {
			t1.setWidths(columnWidths);
			
		} 
	    catch (DocumentException e) 
		{
			e.printStackTrace();
		}
				
		String[] printHeader = new String[8];
		
		PdfPCell c1;
		
	    for(int i =0; i<printHeader.length; i++ )
	    {
	    	c1 = new PdfPCell(new Phrase(" "));
		    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(1);
		    t1.addCell(c1);
	    }
	    
	    String[] print1 = new String[1];
		print1[0] = "HealthMate Report";
		
		Phrase p = new Phrase(print1[0], catFont);
		
	    
	    for(int i =0; i<print1.length; i++)
	    {
	    	//c1 = new PdfPCell(new Phrase(print1[i]));
	    	c1 = new PdfPCell(p);
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(8);
		    c1.setPadding(5);
		    t1.addCell(c1);
		    
	    }
	    
	    String[] print2 = new String[1];
		print2[0] = "This report is generated from HealthMate Mobile App";
		
	    
	    for(int i =0; i<print2.length; i++)
	    {
	    	c1 = new PdfPCell(new Phrase(print2[i]));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(8);
		    c1.setPadding(5);
		    t1.addCell(c1);
	    }
	    
	    for(int i =0; i<printHeader.length; i++)
	    {
	    	c1 = new PdfPCell(new Phrase(" "));
		    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(1);
		    c1.setPadding(5);
		    t1.addCell(c1);
	    }
	    
	    String ls_fname = profile_list.get(0).getString(Profile_Master.FIRST_NAME);
		String ls_lname = profile_list.get(0).getString(Profile_Master.LAST_NAME);
		String gender = profile_list.get(0).getString(Profile_Master.GENDER);
		String dob = profile_list.get(0).getString(Profile_Master.DOB);
		
		String dob1[] = dob.split("/");
		String dob2 = dob1[2];
		
		Log.v(TAG, "dob :"+dob +".. dob2 : "+dob1[1]+ "... current year :" +year);
		
		int age = year -  Integer.parseInt(dob2);
		
		//{15, 10, 10, 10, 15, 15, 10, 15 };
		
	    String[] print3 = new String[6];
		print3[0] = "Name:";
		
		c1 = new PdfPCell(new Phrase(print3[0]));
	    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print3[1] = ls_fname+" "+ls_lname;
		c1 = new PdfPCell(new Phrase(print3[1]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		
		print3[2] = "Gender:";
		c1 = new PdfPCell(new Phrase(print3[2]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print3[3] = gender;
		c1 = new PdfPCell(new Phrase(print3[3]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
		
	    print3[4] = "Age: ";
	    c1 = new PdfPCell(new Phrase(print3[4]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print3[5] = String.valueOf(age);
		c1 = new PdfPCell(new Phrase(print3[5]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(3);
	    t1.addCell(c1);

	  
	    String[] print4 = new String[4];
		print4[0] = "Report Date:";
		c1 = new PdfPCell(new Phrase(print4[0]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print4[1] = day+" "+mth+","+year;
		c1 = new PdfPCell(new Phrase(print4[1]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print4[2] = "Report Time:";
		c1 = new PdfPCell(new Phrase(print4[2]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print4[3] = hr+":"+mn;
		c1 = new PdfPCell(new Phrase(print4[3]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(5);
	    t1.addCell(c1);
	    
	    for(int i =0; i<printHeader.length; i++)
	    {
	    	c1 = new PdfPCell(new Phrase(" "));
		    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(1);
		    c1.setPadding(5);
		    t1.addCell(c1);
	    }
	    
	    String[] print5 = new String[5];
	    if(tabClicked.equals(PRESSURE))
	    {
	    	print5[0] = "Blood Pressure Readings";
	    }
	    else
	    {
	    	print5[0] = "Blood Sugar Readings";
	    }
		c1 = new PdfPCell(new Phrase(print5[0]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(2);
	    t1.addCell(c1);
		
		print5[1] = "Period";
		c1 = new PdfPCell(new Phrase(print5[1]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    

	    
	    String frdate = confirmDialog.et_from.getText().toString();
	    
		print5[2] = frdate;
		c1 = new PdfPCell(new Phrase(print5[2]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    
		print5[3] = "-";
		c1 = new PdfPCell(new Phrase(print5[3]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(1);
	    t1.addCell(c1);
	    

	    String trdate = confirmDialog.et_to.getText().toString();
		
	    print5[4] = trdate;
		c1 = new PdfPCell(new Phrase(print5[4]));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    c1.setBorder(PdfPCell.NO_BORDER);
	    c1.setColspan(3);
	    t1.addCell(c1);
	    
	   
	    for(int i =0; i<printHeader.length; i++)
	    {
	    	c1 = new PdfPCell(new Phrase(" "));
		    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    c1.setBorder(PdfPCell.NO_BORDER);
		    c1.setColspan(1);
		    c1.setPadding(5);
		    t1.addCell(c1);
	    }
	    
	    preface.add(t1);
	    
	    createTable(preface);
	    
	    document.add(preface);
	    
	}
	/*
	public JSONObject getJSONRow(String type, String[] arrStr) throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		int j = -1;

		for (int i = 0; i < arrStr.length; i++ )
		{
			j++;
			jsonObject.put(type + j, arrStr[i]+"");

			if ( i != (arrStr.length-1))
			{	
				j++;
				jsonObject.put(type + (j), "");
			}

		}
		return jsonObject;
	}
	
	public JSONObject getJSONArray(String type, ArrayList<Bundle> arrStr) throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		int j = -1;

		for (int i = 0; i < arrStr.size(); i++ )
		{
			j++;
			jsonObject.put(type + j, arrStr.get(i)+"");

			if ( i != (arrStr.size()-1))
			{	
				j++;
				jsonObject.put(type + (j), "");
			}

		}
		return jsonObject;
	}

	private static void addMetaData(Document document) 
	{
	    document.addTitle("My first PDF");
	    document.addSubject("Using iText");
	    document.addKeywords("Java, PDF, iText");
	    document.addAuthor("Lars Vogel");
	    document.addCreator("Lars Vogel");
	  }
	
	  private static void addTitlePage(Document document)
	      throws DocumentException 
	      {
	    Paragraph preface = new Paragraph();
	    // We add one empty line
	    addEmptyLine(preface, 1);
	    // Lets write a big header
	    preface.add(new Paragraph("Title of the document", catFont));

	    addEmptyLine(preface, 1);
	    // Will create: Report generated by: _name, _date
	    preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new java.util.Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        smallBold));
	    	
	    addEmptyLine(preface, 3);
	    preface.add(new Paragraph("This document describes something which is very important ",
	        smallBold));

	    addEmptyLine(preface, 8);

	    preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
	    	    redFont));
	    

	    document.add(preface);
	    // Start a new page
	    //document.newPage();
	  }

	  private static void addContent(Document document) throws DocumentException 
	  {
	    Anchor anchor = new Anchor("First Chapter", catFont);
	    anchor.setName("First Chapter");

	    // Second parameter is the number of the chapter
	    Chapter catPart = new Chapter(new Paragraph(anchor), 1);

	    Paragraph subPara = new Paragraph("Subcategory 1", subFont);
	    Section subCatPart = catPart.addSection(subPara);
	    //subCatPart.add(new Paragraph("Hello"));

	    //subPara = new Paragraph("Subcategory 2", subFont);
	    //subCatPart = catPart.addSection(subPara);
	    //subCatPart.add(new Paragraph("Paragraph 1"));
	    //subCatPart.add(new Paragraph("Paragraph 2"));
	    //subCatPart.add(new Paragraph("Paragraph 3"));

	    // add a list
	    //createList(subCatPart);
	    Paragraph paragraph = new Paragraph();
	    addEmptyLine(paragraph, 5);
	    subCatPart.add(paragraph);

	    // add a table
	    //createTable(subCatPart);

	    // now add all this to the document
	    document.add(catPart);

	    // Next section
	    //anchor = new Anchor("Second Chapter", catFont);
	   // anchor.setName("Second Chapter");

	    // Second parameter is the number of the chapter
	    //catPart = new Chapter(new Paragraph(anchor), 1);

	    //subPara = new Paragraph("Subcategory", subFont);
	    //subCatPart = catPart.addSection(subPara);
	    //subCatPart.add(new Paragraph("This is a very important message"));

	    // now add all this to the document
	   // document.add(catPart);

	  }
    */

	 public  void createTable(Paragraph subCatPart)
	      throws BadElementException
	 {
		PdfPTable table;
		
		if(tabClicked.equals(PRESSURE))
	    {
			table = new PdfPTable(8);
	    }
		else
		{
			table = new PdfPTable(7);
		}

	    
	    table.setWidthPercentage(100);
	    table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    
	    float[] columnWidths = {100, 70, 70, 70, 70, 90,90, 100 };

	    try 
	    {
			table.setWidths(columnWidths);
			
		} 
	    catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	    
	    ArrayList<Bundle> mlist = new ArrayList<Bundle>();
	    String[] printHeader ;
	    
	    if(tabClicked.equals(PRESSURE))
	    {
	    	mlist = bp_master.getMeasurement(profile_id, fromDate_export, toDate_export );
	    	printHeader = new String[8];
	    }
	    else
	    {
	    	mlist = sugar_master.getMeasurement(profile_id, fromDate_export, toDate_export );
	    	printHeader = new String[7];
	    }
	    
	    Log.v(TAG,"m list : "+mlist.toString());
	    
	    PdfPCell c1 ;
	    
	    //String[] printHeader = new String[8];
		printHeader[0] = "Date";

		if(tabClicked.equals(PRESSURE))
	    {

            printHeader[1] = "Time";
		    printHeader[2] = "Diastolic";
		    printHeader[3] = "Systolic";
		    printHeader[4] = "Pulse";
		    printHeader[5] = "Position";
		    printHeader[6] = "Location";
		    printHeader[7] = "Remark";
	    }
		else
		{
            printHeader[1] = "Fasting-Time";
            printHeader[2] = "Fasting";
            printHeader[3] = "Fasting-Remark";
            printHeader[4] = "PP-Time";
            printHeader[5] = "PP";
            printHeader[6] = "PP-Remark";
		}
	    
	    for(int i =0; i<printHeader.length; i++)
	    {
	    	c1 = new PdfPCell(new Phrase(printHeader[i]));
		    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    c1.setPadding(5);
		    table.addCell(c1);
	    }
	    //table.setHeaderRows(1);
	    
	    String[] list;
		for(int i =0; i<mlist.size(); i++)
		{
			//list = new String[8];
            if(tabClicked.equals(PRESSURE))
            {
			String d = mlist.get(i).getString("date");
			//list[0]=d;
			String date[] = d.split("-");
		    String trdate = date[2]+" "+ getMonth(Integer.parseInt(date[1]))+","+date[0];
			
			c1 = new PdfPCell(new Phrase(trdate));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
			
		    String t = mlist.get(i).getString("time");



                 String t1[] = t.split(":");
                 String t2 = t1[0];
                 String t3 = t1[1];
                 //list[1]=t;

                 c1 = new PdfPCell(new Phrase(t2 + ":" + t3));
                 c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                 c1.setPadding(5);
                 table.addCell(c1);

		    

			String dis = mlist.get(i).getString(BloodPressure_Master.PM1_VALUE);
			//list[2]=dis;
			
			c1 = new PdfPCell(new Phrase(dis));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
			
			String sys = mlist.get(i).getString(BloodPressure_Master.PM2_VALUE);
			//list[3]=sys;
			
			c1 = new PdfPCell(new Phrase(sys));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
		    
			String pul = mlist.get(i).getString(BloodPressure_Master.PM3_VALUE);
			//list[4]=pul;
			
			c1 = new PdfPCell(new Phrase(pul));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
			
			String pos = mlist.get(i).getString(BloodPressure_Master.MSR_POSITION);
			//list[5]=pos;
			
			c1 = new PdfPCell(new Phrase(pos));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
		    
			String loc = mlist.get(i).getString(BloodPressure_Master.MSR_LOCATION);
			//list[6]=loc;
			
			c1 = new PdfPCell(new Phrase(loc));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
		    
			String rem = mlist.get(i).getString(BloodPressure_Master.MSR_REMARKS);
			//list[7]=rem;
			
			c1 = new PdfPCell(new Phrase(rem));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5);
		    table.addCell(c1);
		    }
		    else
		    {

                String d = mlist.get(i).getString(Sugar_Master.MSR_DATE);
                //list[0]=d;
                String date[] = d.split("-");
                String trdate = date[2]+" "+ getMonth(Integer.parseInt(date[1]))+","+date[0];

                c1 = new PdfPCell(new Phrase(trdate));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setPadding(5);
                table.addCell(c1);

                String t = mlist.get(i).getString(Sugar_Master.PM4_TIME);

                if(!(t.equals(""))) {
                    String t1[] = t.split(":");
                    String t2 = t1[0];
                    String t3 = t1[1];


                    c1 = new PdfPCell(new Phrase(t2 + ":" + t3));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setPadding(5);
                    table.addCell(c1);
                }
                else
                {
                    c1 = new PdfPCell(new Phrase(""));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setPadding(5);
                    table.addCell(c1);
                }

		    	String fast = mlist.get(i).getString(Sugar_Master.PM4_VALUE);

				Log.v(TAG, "fast : "+fast);
				c1 = new PdfPCell(new Phrase(fast));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setPadding(5);
			    table.addCell(c1);

                String rem1 = mlist.get(i).getString(Sugar_Master.PM4_REMARKS);

                Log.v(TAG, "fast rem : "+rem1);

                c1 = new PdfPCell(new Phrase(rem1));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setPadding(5);
                table.addCell(c1);

                String tr = mlist.get(i).getString(Sugar_Master.PM5_TIME);

                if(!(tr.equals(""))) {
                    String tr1[] = tr.split(":");
                    String tr2 = tr1[0];
                    String tr3 = tr1[1];


                    c1 = new PdfPCell(new Phrase(tr2 + ":" + tr3));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setPadding(5);
                    table.addCell(c1);
                }
                else
                {
                    c1 = new PdfPCell(new Phrase(""));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setPadding(5);
                    table.addCell(c1);
                }
				
				String pp = mlist.get(i).getString(Sugar_Master.PM5_VALUE);

				Log.v(TAG, "pp : "+pp);
				
				c1 = new PdfPCell(new Phrase(pp));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setPadding(5);
			    table.addCell(c1);
			    
				String rem2 = mlist.get(i).getString(Sugar_Master.PM5_REMARKS);

				Log.v(TAG, "pp rem : "+rem2);
				
				c1 = new PdfPCell(new Phrase(rem2));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setPadding(5);
			    table.addCell(c1);
				
		    }
		}
		subCatPart.add(table);
	  }
/*
	  private static void createList(Section subCatPart) 
	  {
		//List list = new List(true, false, 10);
	    //list.add(new ListItem("First point"));
	    //list.add(new ListItem("Second point"));
	    //list.add(new ListItem("Third point"));
	   // subCatPart.add(list);
	  }

	  private static void addEmptyLine(Paragraph paragraph, int number) 
	  {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  }
*/


    public boolean exportAsPDF_New()
    {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        mth = getMonth(month+1);

        String  d = null;

        if(day<10)
        {
            d = "0"+String.valueOf(day);
        }
        else
        {
            d= String.valueOf(day);
        }

        if(hour<10)
        {
            hr = "0"+String.valueOf(hour);
        }
        else
        {
            hr = String.valueOf(hour);
        }

        if(min<10)
        {
            mn = "0"+String.valueOf(min);
        }
        else
        {
            mn = String.valueOf(min);
        }

        Log.v(TAG,"mth : "+mth +", month : "+month);


        int m = month+1;
        String m1;

        if(m<10)
        {
            m1 = "0"+String.valueOf(m);
        }
        else
        {
            m1 = String.valueOf(m);
        }

        String TS = year+"-"+m1+"-"+d+" "+hr+":"+mn;



        if(tabClicked.equals(PRESSURE))
        {
            exportDir = new File(android.os.Environment.getExternalStorageDirectory()+"/HealthMate/"+profile_fname+" "+profile_lname+"/"+PRESSURE, "");
        }
        else
        {
            exportDir = new File(android.os.Environment.getExternalStorageDirectory()+"/HealthMate/"+profile_fname+" "+profile_lname+"/"+SUGAR, "");
        }

        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        ls_filename = TS+".pdf";


        File file = new File(exportDir, ls_filename);

        try
        {
            file.createNewFile();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addTable(document);
            document.close();

            /*JSONObject dataObject = new JSONObject();


            // putting page headers
            dataObject.put(Txn_PDF.TAG_COMP_NAME, 	"HealthMate Report");
            dataObject.put(Txn_PDF.TAG_FROM_TO, 	"This Report is generated from HealthMate Mobile App");



            String ls_fname = profile_list.get(0).getString(Profile_Master.FIRST_NAME);
            String ls_lname = profile_list.get(0).getString(Profile_Master.LAST_NAME);
            String gender = profile_list.get(0).getString(Profile_Master.GENDER);
            String dob = profile_list.get(0).getString(Profile_Master.DOB);

            String dob1[] = dob.split("/");
            String dob2 = dob1[2];

            Log.v(TAG, "dob :"+dob +".. dob2 : "+dob1[1]+ "... current year :" +year);

            int age = year -  Integer.parseInt(dob2);

            String details = "Name: "+ ls_fname +" "+ls_lname +" Gender: "+gender + " Age: "+age;

            String report_ts = "Report Date: "+day+" "+mth+","+year+"  Report time: "+hr+":"+mn;

            dataObject.put(Txn_PDF.DETAILS, details);
            dataObject.put(Txn_PDF.REPORT_TS, report_ts);

            String heading;
            if(tabClicked.equals(PRESSURE))
            {
                heading = "Blood Pressure Readings";
            }
            else
            {
                heading = "Blood Sugar Readings";
            }
            dataObject.put(Txn_PDF.HEADING, heading);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("header0", "Date");
            jsonObject.put("header1", "");
            jsonObject.put("header2", "Time");
            jsonObject.put("header3", "");
            jsonObject.put("header4", "Diastolic");
            jsonObject.put("header5", "");
            jsonObject.put("header6", "Systolic");
            jsonObject.put("header7", "");
            jsonObject.put("header8", "Pulse");
            jsonObject.put("header9", "");
            jsonObject.put("header10", "Location");
            jsonObject.put("header11", "");
            jsonObject.put("header12", "Position");
            jsonObject.put("header13", "");
            jsonObject.put("header14", "Remark");


            // putting table header
            dataObject.put(Txn_PDF.TAG_TABLE_HEADER, jsonObject);

            ArrayList<Bundle> mlist = new ArrayList<Bundle>();
            String[] printHeader ;

            if(tabClicked.equals(PRESSURE))
            {
                mlist = bp_master.getMeasurement(profile_id, fromDate_export, toDate_export );
                printHeader = new String[8];
            }
            else
            {
                mlist = sugar_master.getMeasurement(profile_id, fromDate_export, toDate_export );
                printHeader = new String[5];
            }

            // creating JSON array that will store all the table rows
            JSONArray rowArray = new JSONArray();

            for(int i =0; i<mlist.size(); i++)
            {

                if(tabClicked.equals(PRESSURE))
                {

                    String arrStr[] = new String[8];

                    String DATE = mlist.get(i).getString("date");

                    String date[] = DATE.split("-");
                    String trdate = date[2] + " " + getMonth(Integer.parseInt(date[1])) + "," + date[0];
                    arrStr[0] = trdate;

                    String t = mlist.get(i).getString("time");

                    String t1[] = t.split(":");
                    String t2 = t1[0];
                    String t3 = t1[1];

                    arrStr[1] = t2+":"+t3;


                    String dis = mlist.get(i).getString(BloodPressure_Master.PM1_VALUE);
                    arrStr[2] = dis;

                    String sys = mlist.get(i).getString(BloodPressure_Master.PM2_VALUE);
                    arrStr[3] = sys;

                    String pul = mlist.get(i).getString(BloodPressure_Master.PM3_VALUE);
                    arrStr[4] = pul;


                    String pos = mlist.get(i).getString(BloodPressure_Master.MSR_POSITION);
                    arrStr[5] = pos;

                    String loc = mlist.get(i).getString(BloodPressure_Master.MSR_LOCATION);
                    arrStr[6] = loc;


                    String rem = mlist.get(i).getString(BloodPressure_Master.MSR_REMARKS);
                    arrStr[7] = rem;


                    jsonObject = new JSONObject();

                    jsonObject.put("col0", arrStr[0] + "");
                    jsonObject.put("col1", "");
                    jsonObject.put("col2", arrStr[1] + "");
                    jsonObject.put("col3", "");
                    jsonObject.put("col4", arrStr[2] + "");
                    jsonObject.put("col5", "");
                    jsonObject.put("col6", arrStr[3] + "");
                    jsonObject.put("col7", "");
                    jsonObject.put("col8", arrStr[4] + "");
                    jsonObject.put("col9", "");
                    jsonObject.put("col10", arrStr[5] + "");
                    jsonObject.put("col11", "");
                    jsonObject.put("col12", arrStr[6] + "");
                    jsonObject.put("col13", "");
                    jsonObject.put("col14", arrStr[7] + "");

                    rowArray.put(jsonObject);
                }
            }


            dataObject.put(Txn_PDF.TAG_TABLE_ROWS, rowArray);

            // now writing the data to PDF file
            Txn_PDF pdf = new Txn_PDF(context);
            pdf.printData(dataObject, ls_filename);*/

            progressDialog.dismiss();

            String path = exportDir.getAbsolutePath().toString()+"/"+ls_filename;

            confirmDialog = new ConfirmDialog(context, path, profile_id, "share", tabClicked);
            confirmDialog.show(getFragmentManager(), TAG);

            return true;
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            return false;
        }
    }


	  


	    @SuppressLint("NewApi")
	public void showDateRangeDialog()
		{
			
		  	//confirmDialog  = new ConfirmDialog(context, " " , profile_id, "export", tabClicked);
			//confirmDialog.show(getFragmentManager(), TAG);



			ArrayList<Bundle> mlist = new ArrayList<Bundle>();
		   // mlist = bp_master.getMeasurementListByProfile(profile_id);

            if(tabClicked.equals(PRESSURE))
            {
                mlist = bp_master.getMeasurementListByProfile(profile_id);
            }
            else
            {
                mlist = sugar_master.getMeasurementListByProfile(profile_id);
            }
		    
		    if(mlist.size() > 0 )
		    {
		    
			    //String default_from = mlist.get(0).getString("date");

                String default_from, default_to;
                if(tabClicked.equals(PRESSURE)) {
                    default_from = mlist.get(0).getString("date");
                }
                else
                {
                    default_from = mlist.get(0).getString(Sugar_Master.MSR_DATE);
                }
			    
			    String date[] =default_from.split("-");
				String d = date[2];
				String m = getMonth(Integer.parseInt(date[1]));
				String y= date[0];
				String date1 = d+" "+m+", "+y;

                if(tabClicked.equals(PRESSURE)) {
                    default_to = mlist.get(mlist.size() - 1).getString("date");
                }
                else
                {
                    default_to = mlist.get(mlist.size() - 1).getString(Sugar_Master.MSR_DATE);
                }
				
				String date2[] =default_to.split("-");
				String d1 = date[2];
				String m1 = getMonth(Integer.parseInt(date2[1]));
				String y1= date[0];
				String date3 = d1+" "+m1+", "+y1;

                fromDate_export = default_to;
                toDate_export = default_from;
				
				
				//confirmDialog.et_from.setText(date1);
				//confirmDialog.et_to.setText(date3);
		    }

            confirmDialog  = new ConfirmDialog(context, " " , profile_id, "export", tabClicked);
            confirmDialog.show(getFragmentManager(), TAG);

		}
	  
	  
	  public void setDateFrom1(View view) 
		 {
			 DatePickerDialog dpd = new DatePickerDialog(this,
				        new DatePickerDialog.OnDateSetListener() 
			 {
				 
				         @Override
				         public void onDateSet(DatePicker view, int year,
				                    int monthOfYear, int dayOfMonth) 
				         {
				        	 
				        	 

				        	 
				        	 monthOfYear = monthOfYear+1;
								String month1 = getMonth(monthOfYear);
								Log.v(TAG,"monthOfYear : "+monthOfYear +", month1 : "+month1);
								
								

								String mn, d = null;
								
								if(monthOfYear<10)
								{
					
									mn = "0"+String.valueOf(monthOfYear);
								}
								else
								{
									mn= String.valueOf(monthOfYear);
								}
								
								if(dayOfMonth<10)
								{
									d = "0"+String.valueOf(dayOfMonth);
								}
								else
								{
									d= String.valueOf(dayOfMonth);
								}
								
								confirmDialog.et_from.setText(d+" "+month1+", "+year);
								
								//2014-12-21 12:00:12
								fromDate_export = year+"-"+mn+"-"+d;
								
								Log.v(TAG, "from date :"+fromDate_export);
								


				             
				 
				         }
			 }, year, month, day);
				
			 dpd.show();
		 }
		 
		 public void setDateTo1(View view) 
		 {
			 DatePickerDialog dpd = new DatePickerDialog(this,
				        new DatePickerDialog.OnDateSetListener() 
			 {
				 
				         @SuppressLint("SimpleDateFormat")
						@Override
				         public void onDateSet(DatePicker view, int year,
				                    int monthOfYear, int dayOfMonth) 
				         {
				        	 
				        	 
				             
				            	// et_to.setText(new StringBuilder().append(dayOfMonth).append("/")
				              	 //     .append(monthOfYear+1).append("/").append(year));
				             
				        	 
				        	    monthOfYear = monthOfYear+1;
								String month1 = getMonth(monthOfYear);
								Log.v(TAG,"monthOfYear : "+monthOfYear +", month1 : "+month1);
								
								

								String mn, d = null;
								
								if(monthOfYear<10)
								{
					
									mn = "0"+String.valueOf(monthOfYear);
								}
								else
								{
									mn= String.valueOf(monthOfYear);
								}
								
								if(dayOfMonth<10)
								{
									d = "0"+String.valueOf(dayOfMonth);
								}
								else
								{
									d= String.valueOf(dayOfMonth);
								}
								
								toDate_export = year+"-"+mn+"-"+d;
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						        Date testDate = null,testDate1 = null;
						        try 
						        {
						            testDate = sdf.parse(fromDate_export);
						            testDate1 = sdf.parse(toDate_export);
						        }
						        catch(Exception ex)
						        {
						            ex.printStackTrace();
						        }
								
						        if(testDate1.compareTo(testDate)>0 || testDate1.compareTo(testDate)==0)
								{
						        	confirmDialog.et_to.setText(d+" "+month1+", "+year);
								}
						        else
						        {
						        	Toast.makeText(context, "Date should be greater than From Date.", Toast.LENGTH_SHORT).show();
						        }
									

								
								Log.v(TAG, "to date :"+toDate_export);
								
								//et_from_date.setText(new StringBuilder().append(dayOfMonth).append("/")
								//	      .append(monthOfYear).append("/").append(year));

				 
				         }
			 }, year, month, day);
				
			 dpd.show();
		 }
	  
		 
		 public void shareExported_file()
		 {
			 File externalStorage = Environment.getExternalStorageDirectory();

			 //Uri uri = Uri.fromFile(new File(externalStorage.getAbsolutePath() + "/" + "download.pdf"));
			 Log.v(TAG,"ExportDir : "+exportDir.getAbsolutePath()+"File name : "+ls_filename);
			 
			 Uri uri = Uri.fromFile(new File(exportDir.getAbsolutePath() + "/" + ls_filename));

			 Intent emailIntent = new Intent(Intent.ACTION_SEND);
			 emailIntent.putExtra(Intent.EXTRA_EMAIL, "kalyani.maid@rediffmail.com");
			 emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
			 emailIntent.putExtra(Intent.EXTRA_TEXT, "Text");
			 emailIntent.setType("application/pdf");
			 emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

			 startActivity(Intent.createChooser(emailIntent, "Send email using:"));
		 }
		 
		 public void openExported_file()
		 {
			 
			 Log.v(TAG,"ExportDir : "+exportDir.getAbsolutePath()+"File name : "+ls_filename);
			 String ls_file = "";
				
			
			ls_file = exportDir+"/"+ ls_filename;	
			

			File fileToOpen = new File(ls_file);

			Log.v(TAG, "Request to open file with path: "+ fileToOpen.getAbsolutePath());

			Intent i = new Intent(Intent.ACTION_VIEW);
			String webkitMimeType = null;

			if(ls_docType.equals("PDF"))
			{
				webkitMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
			}
			else if(ls_docType.equals("CSV"))
			{
				webkitMimeType  = MimeTypeMap.getSingleton().getMimeTypeFromExtension("csv");
			}

			if (fileToOpen.exists())
			{	
				if (webkitMimeType == null)
				{
					Toast.makeText(this, "File Saved. No Document Viewer found to open file.", Toast.LENGTH_LONG).show();
					return;
				}
				i.setDataAndType(Uri.fromFile(fileToOpen),webkitMimeType);
				try
				{
					Log.v(TAG, "csv mimitype "+webkitMimeType);

					startActivity(i);
				}
				catch(Exception e)
				{
						Toast.makeText(this, "File Saved. No Document Viewer found to open file.", Toast.LENGTH_LONG).show();				
				}
			}
			else
			{
					Toast.makeText(context, "File not found", Toast.LENGTH_LONG).show();
			}
		 }

		
		/*
		@Override
		public boolean onTouch(View v, MotionEvent event) 
		{
			switch (event.getAction()) 
	        {
	            case MotionEvent.ACTION_DOWN:
	            	
	            	historicX = event.getX();
	            	historicY = event.getY();
	            	break;

	            case MotionEvent.ACTION_UP:
	            
	            	if (event.getX() - historicX < -DELTA) 
	            	{
	            		int id = lv_measures.pointToPosition((int) event.getX(), (int) event.getY());
	            		Log.v(TAG, "id : " + id);
	            	
	            		onSwipeLeft(id);
	            		return true;
	            	}
	            	else if (event.getX() - historicX > DELTA)  
	            	{
	            		int id = lv_measures.pointToPosition((int) event.getX(), (int) event.getY());
	            	
	            		//onSwipeRight(id);
	            		Animation outToRight = AnimationUtils.loadAnimation(context, R.anim.out_to_right);
	            		
	            		v.startAnimation(outToRight);
	            		
	            		return true;
	            	} 
	            	break;
	            
	            default: return false;
	        }

			return false;
		}
		
		
		public void onSwipeLeft(final int posId) 
		{
			new AlertDialog.Builder(this)
		    .setTitle("Delete entry")
		    .setMessage("Are you sure you want to delete this entry?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) 
		        { 
		            // continue with delete
		    		//arrList_mem = new ArrayList<Bundle>();
		    		//arrList_mem = members.memDetails(mobNums);
		    		
		    		//String mobNum = arrList_mem.get(posId).getString(Members.MOB_NUM);
		    		//String memFirstName = arrList_mem.get(posId).getString(Members.FIRST_NAME);
		    		
		    		//Log.v(TAG,"mobNum : " + mobNum);
		    		//Log.v(TAG,"memFirstName : " + memFirstName);
		    		
		    		/////Delete user from group
		    		//deleteFamilyMember(GroupId, mobNum, memFirstName);	
		    		
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) 
		        { 
		            //do nothing
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
	    }

	    public void onSwipeRight(final int posId) 
	    {
	    	new AlertDialog.Builder(this)
	        .setTitle("Delete entry")
	        .setMessage("Are you sure you want to delete this entry?")
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int which)
	            { 
	                // continue with delete
	            	Log.v(TAG, "which : " + which);
	            	
	            	//arrList_mem = new ArrayList<Bundle>();
		    		//arrList_mem = members.memDetails(mobNums);
		    		
		    		//String mobNum = arrList_mem.get(posId).getString(Members.MOB_NUM);
		    		//String memFirstName = arrList_mem.get(posId).getString(Members.FIRST_NAME);
		    		
		    		//Log.v(TAG,"mobNum : " + mobNum);
		    		//Log.v(TAG,"memFirstName : " + memFirstName);
		    		
		    		//deleteFamilyMember(GroupId, mobNum, memFirstName);
	            }
	         })
	        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int which) 
	            { 
	                // do nothing
	            }
	         })
	        .setIcon(android.R.drawable.ic_dialog_alert)
	         .show();
	    }
	    
	    */
	    
	    private Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) 
			{

				switch (msg.what)
				{
				/*
				case MSG_UPDATE_ADAPTER: // ListView updating

					//listview.invalidateViews();

					Log.v(TAG, "In handler");

					break;
				case MSG_CHANGE_ITEM: // Do / not do case
					/*
					ToDoItem item = list.get(msg.arg1);
					item.setCheck(!item.isCheck());
					Utils.sorting(list, 0);
					saveList();
					adapter.notifyDataSetChanged();
					setCountPurchaseProduct();
					 ///
					break;	
					*/
				case MSG_ANIMATION_REMOVE: // Start animation removing
					View view = (View)msg.obj;
					view.startAnimation(getDeleteAnimation(0, (msg.arg2 == 0) ? -view.getWidth() : 2 * view.getWidth(), msg.arg1));
					break;

				}
			}
		};

		private Animation getDeleteAnimation(float fromX, float toX, int position)
		{
			Animation animation = new TranslateAnimation(fromX, toX, 0, 0);
			animation.setStartOffset(100);
			animation.setDuration(800);
			animation.setAnimationListener(new DeleteAnimationListenter(position));
			animation.setInterpolator(AnimationUtils.loadInterpolator(context,
					android.R.anim.anticipate_overshoot_interpolator));
			return animation;
		}

		/**
		 * Listenter used to remove an item after the animation has finished remove
		 */
		public class DeleteAnimationListenter implements Animation.AnimationListener
		{

			public DeleteAnimationListenter(int position)
			{
				//Log.v(TAG,"Animation position is "+position);

			}
			public void onAnimationEnd(Animation arg0) 
			{	

				confirmDelete();
				/*//final int li_return = DeleteMaster(); only current line previous commented 
				Log.v(TAG,"In Animation end "+ls_docType + "  Doc No "+li_docno);
				txnDetails.DeleteTxn(ls_docType, li_docno, true);
				handler.post(new Runnable() 
				{
					public void run() 
					{

						getData();

					}
				});
				 */

			}

			public void onAnimationRepeat(Animation animation) 
			{


			}

			public void onAnimationStart(Animation animation)
			{

			}	
		}
		
		
		public void confirmDelete()
		{
			confirmDialog = new ConfirmDialog(context, tabClicked, profile_id, "delete", tabClicked);
		    confirmDialog.show(getFragmentManager(), TAG);
		}
		
		public void deleteRecord()
		{
			
			//Log.v(TAG,"In delete record");
			if(tabClicked.equals(PRESSURE))
			{
				//Log.v(TAG,"In delete record IF "+RowPosition);
				
				measure_list = new ArrayList<Bundle>();
				measure_list = bp_master.getMeasurementListByProfile(profile_id);
			
				int measure_id = measure_list.get(RowPosition).getInt(BloodPressure_Master.MSR_ID);
			
				bp_master.deleteRow(measure_id);
			
				setAdapter();
			}
			else
			{
				//Log.v(TAG,"In delete record ELSE"+RowPosition);
				measure_list = new ArrayList<Bundle>();
				measure_list = sugar_master.getMeasurementListByProfile(profile_id);
				
				int measure_id = measure_list.get(RowPosition).getInt(Sugar_Master.MSR_ID);
			
				sugar_master.deleteRow(measure_id);
			
				setAdapter();
			}
			
		}
		
		
		public void set_editText(int no)
		{
			String number = String.valueOf(no);
			
			
			if(edit_view == et_pressuresys)
			{
				if(et_pressuresys.getText().toString().length() ==3)
				{
					number = "";
				}
				et_pressuresys.setText(et_pressuresys.getText()+number);
			}
			
			if(edit_view == et_pressuredias)
			{
				if(et_pressuredias.getText().toString().length() ==3)
				{
					number = "";
				}
				et_pressuredias.setText(et_pressuredias.getText()+number);
			}
			
			if(edit_view == et_pressurepulse)
			{
				if(et_pressurepulse.getText().toString().length() ==3)
				{
					number = "";
				}
				et_pressurepulse.setText(et_pressurepulse.getText()+number);
			}
			
			if(edit_view == et_sugarfasting)
			{
				if(et_sugarfasting.getText().toString().length() ==3)
				{
					number = "";
				}
				et_sugarfasting.setText(et_sugarfasting.getText()+number);
			}
			
			if(edit_view == et_sugarpp)
			{
				if(et_sugarpp.getText().toString().length() ==3)
				{
					number = "";
				}
				et_sugarpp.setText(et_sugarpp.getText()+number);
			}
		}
		
		public void  set_editTextadd()
		{
			
			int no = 0, addition = 0;
			String number ;
			
			if(edit_view == et_pressuresys)
			{
				if(et_pressuresys.getText().toString().equals(""))
				{
					no = 0;
				}
				else
				{
					no = Integer.parseInt(et_pressuresys.getText().toString());
				}
				
				addition = no + 1;
				
				number = String.valueOf(addition);
				//Log.v(TAG,"no = "+no + ", add = "+addition);
				et_pressuresys.setText(number);
			}
			
			if(edit_view == et_pressuredias)
			{
				if(et_pressuredias.getText().toString().equals(""))
				{
					no = 0;
				}
				else
				{
					no = Integer.parseInt(et_pressuredias.getText().toString());
				}
				
				addition = no + 1;
				
				number = String.valueOf(addition);
				//Log.v(TAG,"no = "+no + ", add = "+addition);
				et_pressuredias.setText(number);
			}
			
			if(edit_view == et_pressurepulse)
			{
				if(et_pressurepulse.getText().toString().equals(""))
				{
					no = 0;
				}
				else
				{
					no = Integer.parseInt(et_pressurepulse.getText().toString());
				}
				
				addition = no + 1;
				
				number = String.valueOf(addition);
				//Log.v(TAG,"no = "+no + ", add = "+addition);
				et_pressurepulse.setText(number);
			}
			
			if(edit_view == et_sugarfasting)
			{
				if(et_sugarfasting.getText().toString().equals(""))
				{
					no = 0;
				}
				else
				{
					no = Integer.parseInt(et_sugarfasting.getText().toString());
				}
				
				addition = no + 1;
				
				number = String.valueOf(addition);
				//Log.v(TAG,"no = "+no + ", add = "+addition);
				et_sugarfasting.setText(number);
			}
			
			if(edit_view == et_sugarpp)
			{
				if(et_sugarpp.getText().toString().equals(""))
				{
					no = 0;
				}
				else
				{
					no = Integer.parseInt(et_sugarpp.getText().toString());
				}
				
				addition = no + 1;
				
				number = String.valueOf(addition);
				//Log.v(TAG,"no = "+no + ", add = "+addition);
				et_sugarpp.setText(number);
			}
			
			
		}
		
		
		public void  set_editTextminus()
		{
			int no = 0, sub = 0;
			String number;
			
			if(edit_view == et_pressuresys)
			{
				
				if(et_pressuresys.getText().toString().equals(""))
				{
					
					et_pressuresys.setText("");
				}
				else
				{
					no = Integer.parseInt(et_pressuresys.getText().toString());
					
					if(no == 0)
					{
						no = 0; 
						sub = 0;
					}
					else
					{
						sub = no - 1;
						
					}
					number = String.valueOf(sub);
					et_pressuresys.setText(number);
				}
			}
			
			if(edit_view == et_pressuredias)
			{
				
				
				if(et_pressuredias.getText().toString().equals(""))
				{
					
					et_pressuredias.setText("");
				}
				else
				{
					no = Integer.parseInt(et_pressuredias.getText().toString());
					
					if(no == 0)
					{
						no = 0; 
						sub = 0;
					}
					else
					{
						sub = no - 1;
						
					}
					number = String.valueOf(sub);
					et_pressuredias.setText(number);
				}
				
			}
			
			if(edit_view == et_pressurepulse)
			{
				
				
				if(et_pressurepulse.getText().toString().equals(""))
				{
					
					et_pressurepulse.setText("");
				}
				else
				{
					no = Integer.parseInt(et_pressurepulse.getText().toString());
					
					if(no == 0)
					{
						no = 0; 
						sub = 0;
					}
					else
					{
						sub = no - 1;
						
					}
					number = String.valueOf(sub);
					et_pressurepulse.setText(number);
				}
			}
			
			if(edit_view == et_sugarfasting)
			{
				
				
				if(et_sugarfasting.getText().toString().equals(""))
				{
					
					et_sugarfasting.setText("");
				}
				else
				{
					no = Integer.parseInt(et_sugarfasting.getText().toString());
					
					if(no == 0)
					{
						no = 0; 
						sub = 0;
					}
					else
					{
						sub = no - 1;
						
					}
					number = String.valueOf(sub);
					et_sugarfasting.setText(number);
				}
			}
			
			if(edit_view == et_sugarpp)
			{
				
				if(et_sugarpp.getText().toString().equals(""))
				{
					
					et_sugarpp.setText("");
				}
				else
				{
					no = Integer.parseInt(et_sugarpp.getText().toString()); 
					
					if(no == 0)
					{
						no = 0; 
						sub = 0;
					}
					else
					{
						sub = no - 1;
						
					}
					number = String.valueOf(sub);
					et_sugarpp.setText(number);
				}
			}
		}
		
		public void set_editTextclear()
		{
			
			if(edit_view == et_pressuresys)
			{
				et_pressuresys.setText("");
			}
			if(edit_view == et_pressuredias)
			{
				et_pressuredias.setText("");
			}
			if(edit_view == et_pressurepulse)
			{
				et_pressurepulse.setText("");
			}
			
			if(edit_view == et_sugarfasting)
			{
				et_sugarfasting.setText("");
			}
			if(edit_view == et_sugarpp)
			{
				et_sugarpp.setText("");
			}
		}
		
		public void set_editTextcancel()
		{
			/*
			ll_custom_keyboard.setVisibility(View.GONE);
			
			
			if(edit_view == et_pressuresys)
			{
				et_pressuresys.setText(editText_value);
			}
			if(edit_view == et_pressuredias)
			{
				et_pressuredias.setText(editText_value);
			}
			if(edit_view == et_pressurepulse)
			{
				et_pressurepulse.setText(editText_value);
			}
			
			if(edit_view == et_sugarfasting)
			{
				et_sugarfasting.setText(editText_value);
			}
			if(edit_view == et_sugarpp)
			{
				et_sugarpp.setText(editText_value);
			}
			*/
			
			if(edit_view == et_pressuresys)
			{
				et_pressuredias.performClick();
				
				//Log.v(TAG,"..."+et_pressuredias.performClick());
			}
			else if(edit_view == et_pressuredias)
			{
				et_pressurepulse.performClick();
			}
			else if(edit_view == et_pressurepulse)
			{
				et_pressureremark.performClick();
				
				//Log.v(TAG,"et_pressureremark.performClick()");

				//ll_custom_keyboard.setVisibility(View.GONE);
			}
			
			
			if(edit_view == et_sugarfasting)
			{
				et_sugarpp.performClick();
			}
			else if(edit_view == et_sugarpp)
			{
				et_sugarremark1.performClick();
				
				//ll_custom_keyboard.setVisibility(View.GONE);
				
			}
			
		}

		
		
		public void call_GoogleAnalytics_screens()
		{
			//GOOGLE aNALYTICS
			Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
			
			t.enableAdvertisingIdCollection(true);
			
			t.setScreenName("MeasureMent_Activity");
			t.send(new HitBuilders.ScreenViewBuilder().build());
			//GOOGLE aNALYTICS
		}
		
		public void call_GoogleAnalytics_sessions()
		{
			//GOOGLE aNALYTICS
			Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
			
			t.enableAdvertisingIdCollection(true);
			
			t.setScreenName("MeasureMent_Activity");
			t.send(new HitBuilders.ScreenViewBuilder().build());
			
			// Start a new session with the hit.
			t.send(new HitBuilders.AppViewBuilder().setNewSession().build());
			//GOOGLE aNALYTICS
		}
		
//		public void call_GoogleAnalytics_events(final int categoryId, final int actionId,
//			      final int labelId)
//		{
//			Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
//			
//				// Build and send an Event.
//				t.send(new HitBuilders.EventBuilder()
//				    .setCategory(getString(categoryId))
//				    .setAction(getString(actionId))
//				    .setLabel(getString(labelId))
//				    .build());
//		}
		
		
		public void call_GoogleAnalytics_events(String category, String action,
				String label, long value)
		{
			Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
			
			t.enableAdvertisingIdCollection(true);
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
				    .setCategory(category)
				    .setAction(action)
				    .setLabel(label)
				    .build());
		}
		
		public void call_GoogleAnalytics_userTimings(String category, long interval,
				String name, String label)
		{
			Tracker t = ((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);
			
			t.enableAdvertisingIdCollection(true);
			
				// Build and send an Event.
			 t.send(new HitBuilders.TimingBuilder()
	            .setCategory(category)
	            .setValue(interval)
	            .setVariable(name)
	            .setLabel(label)
	            .build());
			 
		}
		
		
		public void getRecordCount(String ls_recordAdded)
		{
			boolean lb_showDialog = false;
			recordCount_sp = context.getSharedPreferences("RECORD_COUNT",Context.MODE_PRIVATE);
			sp_recordEditor = recordCount_sp.edit();

			int  li_showNoTimes = 0;
			if(ls_recordAdded != null)
			{
				li_showNoTimes = recordCount_sp.getInt(ls_recordAdded,0);
				li_showNoTimes = li_showNoTimes + 1;
				sp_recordEditor.putInt(ls_recordAdded,li_showNoTimes);


				//Log.v(TAG,"Screen name is " + ls_recordAdded);


				if(li_showNoTimes > MAX_SCREENCOUNT)
				{
					lb_showDialog = true;
					//li_showNoTimes = 0;
                    li_showNoTimes = 1;
					sp_recordEditor.putInt(ls_recordAdded,li_showNoTimes);
				}
				else
				{
					lb_showDialog = false;

				}
				//Log.v(TAG,"No.of times clicked  " + li_showNoTimes);
				//Log.v(TAG,"lb_showDialog is " + lb_showDialog);

				sp_recordEditor.putBoolean("SHOW_DIALOG",lb_showDialog);

				sp_recordEditor.commit();


                show_share_RateDailog();
			}

		}
		
		
		public void show_share_RateDailog()
		{
			boolean is_share, lb_showDialog;
            int rec_count = 0;
			rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
			
			is_share =  rate_sp.getBoolean("RATE",false);
			//Log.v(TAG,"is_share is " + is_share);
			
			
			recordCount_sp = context.getSharedPreferences("RECORD_COUNT",Context.MODE_PRIVATE);
			//sp_recordEditor = recordCount_sp.edit();

			//lb_showDialog  = recordCount_sp.getBoolean("SHOW_DIALOG", false);
			//Log.v(TAG,"lb_showDialog_Records is " + lb_showDialog);

            rec_count  = recordCount_sp.getInt("ADDED", 0);
            //Log.v(TAG,"rec_count is " + rec_count);

			
			//if(lb_showDialog)
            if(rec_count == MAX_SCREENCOUNT)
			{
				if(!(is_share))
				{
                    //Log.v(TAG,"is_share is " + is_share);
					 confirmDialog = new ConfirmDialog(context, "", profile_id, "rate", tabClicked);
					 confirmDialog.show(getFragmentManager(), TAG);
				}
			}
		}
		
		
		public void shareApp(Context context)
		{
			rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
			sp_rateEditor = rate_sp.edit();
			
			sp_rateEditor.putBoolean("RATE",true);
			sp_rateEditor.commit();

			Intent i=new Intent(android.content.Intent.ACTION_SEND);
			i.setType("text/plain");

			//i.putExtra(android.content.Intent.EXTRA_SUBJECT,"mAccounts : Mobile Accounting App for Android");
			i.putExtra(android.content.Intent.EXTRA_TEXT, SHARETEXT);


			startActivity(Intent.createChooser(i,MeasurementList_Profile.ls_share));
		}
		
		public void rateApp(Context context)
		{
			rate_sp = context.getSharedPreferences("CHECK_RATE",Context.MODE_PRIVATE);
			sp_rateEditor = rate_sp.edit();
			
			sp_rateEditor.putBoolean("RATE",true);
			sp_rateEditor.commit();
			
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.cresco.HealthMate")));
		}
		
		
		public void set_toastCount(String type)
		{
			boolean lb_showToast = false;
			toast_sp = context.getSharedPreferences("TOAST",Context.MODE_PRIVATE);
			sp_toastEditor = toast_sp.edit();

			int  li_showNoTimes = 0;
			
			if(type.equals("LIST_VIEW"))
			{
				li_showNoTimes = toast_sp.getInt("LIST_TOAST_COUNT",0);
				li_showNoTimes = li_showNoTimes + 1;
				
				sp_toastEditor.putInt("LIST_TOAST_COUNT",li_showNoTimes);
	
	
				//Log.v(TAG,"li_showNoTimes list " + li_showNoTimes);
					
	
				if(li_showNoTimes >= MAX_TOASTCOUNT)
				{
					lb_showToast = false;
				}
				else
				{
					lb_showToast = true;
	
				}
				//Log.v(TAG,"No.of times clicked  " + li_showNoTimes);
				//Log.v(TAG,"lb_showDialog is " + lb_showToast);
	
				sp_toastEditor.putBoolean("SHOW_TOAST_LIST",lb_showToast);
	
				sp_toastEditor.commit();
			}
			else
			{
				li_showNoTimes = toast_sp.getInt("GRAPH_TOAST_COUNT",0);
				li_showNoTimes = li_showNoTimes + 1;
				sp_toastEditor.putInt("GRAPH_TOAST_COUNT",li_showNoTimes);
	
	
				//Log.v(TAG,"li_showNoTimes graph " + li_showNoTimes);
					
	
	
				if(li_showNoTimes >= MAX_TOASTCOUNT)
				{
					lb_showToast = false;
				}
				else
				{
					lb_showToast = true;
	
				}
				//Log.v(TAG,"No.of times clicked  " + li_showNoTimes);
				//Log.v(TAG,"lb_showDialog is " + lb_showToast);
	
				sp_toastEditor.putBoolean("SHOW_TOAST_GRAPH",lb_showToast);
	
				sp_toastEditor.commit();
			}

		}



	    
}