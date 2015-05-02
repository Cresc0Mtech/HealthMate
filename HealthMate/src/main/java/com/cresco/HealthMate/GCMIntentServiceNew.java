package com.cresco.HealthMate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import static com.google.android.gcm.GCMConstants.ERROR_SERVICE_NOT_AVAILABLE;
import static com.google.android.gcm.GCMConstants.EXTRA_ERROR;
import static com.google.android.gcm.GCMConstants.EXTRA_REGISTRATION_ID;
import static com.google.android.gcm.GCMConstants.EXTRA_SPECIAL_MESSAGE;
import static com.google.android.gcm.GCMConstants.EXTRA_TOTAL_DELETED;
import static com.google.android.gcm.GCMConstants.EXTRA_UNREGISTERED;
import static com.google.android.gcm.GCMConstants.INTENT_FROM_GCM_LIBRARY_RETRY;
import static com.google.android.gcm.GCMConstants.INTENT_FROM_GCM_MESSAGE;
import static com.google.android.gcm.GCMConstants.INTENT_FROM_GCM_REGISTRATION_CALLBACK;
import static com.google.android.gcm.GCMConstants.VALUE_DELETED_MESSAGES;

public  class GCMIntentServiceNew extends IntentService
{
	private static final String TAG = "GCMBaseIntentService";

	static String regIdFrom;
	long ll_msgId;
	SQLiteDatabase db;
	Context context;

	private final static int MSG_TYPE_DESKTOP_OP = 1;
	private final static int MSG_TYPE_MESSAGE = 2;
	
	private static final Object LOCK = GCMIntentServiceNew.class;
	private static PowerManager.WakeLock sWakeLock;

	 public GCMIntentServiceNew() 
	 {
	        super("GCMIntentServiceNew");
	  }

	@Override
	protected void onHandleIntent(Intent intent) 
	{
		try 
		{
		Context context = getApplicationContext();
		String action = intent.getAction();
		if (action.equals(INTENT_FROM_GCM_REGISTRATION_CALLBACK)) 
		{
			handleRegistration(context, intent);
		}
		else if (action.equals(INTENT_FROM_GCM_MESSAGE)) 
		{
			// checks for special messages
			String messageType = intent.getStringExtra(EXTRA_SPECIAL_MESSAGE);
			if (messageType != null) 
			{
				if (messageType.equals(VALUE_DELETED_MESSAGES)) 
				{
					 String sTotal = intent.getStringExtra(EXTRA_TOTAL_DELETED);
					 if (sTotal != null) 
					 {
						 try 
						 {
							 int total = Integer.parseInt(sTotal);
							 //Log.v(TAG, "Received deleted messages " +"notification: " + total);
							 onDeletedMessages(context, total);
						 } 
						 catch (NumberFormatException e) 
						 {
							 Log.e(TAG, "GCM returned invalid number of " +"deleted messages: " + sTotal);
						 }
					 }
				 } 
					else 
				 {
					 // application is not using the latest GCM library
					 Log.e(TAG, "Received unknown special message: " + messageType);
				 }
			 } 
			 else 
			 {
				 onMessage(context, intent);
			 }
		} 
		}
		finally 
		{
			 // Release the power lock, so phone can get back to sleep.
			 // The lock is reference-counted by default, so multiple
			 // messages are ok.
			 // If onMessage() needs to spawn a thread or do something else,
			 // it should use its own lock.
			 /*synchronized (LOCK) 
			 {
				 // sanity check for null as this is a public method
				 if (sWakeLock != null) 
				 {
					 Log.v(TAG, "Releasing wakelock");
					 sWakeLock.release();
				 } 
				 else 
				 {
					 // should never happen during normal workflow
					 Log.e(TAG, "Wakelock reference is null");
				 }
			 }*/
			GcmBroadcastReceiver.completeWakefulIntent(intent);
		 }
		
	}
	
	private void handleRegistration(final Context context, Intent intent) 
	{
		
		String registrationId = intent.getStringExtra(EXTRA_REGISTRATION_ID);
		String error = intent.getStringExtra(EXTRA_ERROR);
		String unregistered = intent.getStringExtra(EXTRA_UNREGISTERED);
		Log.d(TAG, "handleRegistration: registrationId = " + registrationId +
		", error = " + error + ", unregistered = " + unregistered);
			// registration succeeded
		
			if (registrationId != null) 
			{
				onRegistered(context, registrationId);
				
				//Log.v("GCMBaseIntentService", "In onRegistered()");
				RegistrationDetails rd = new RegistrationDetails(context);
				rd.setRegId(registrationId);
				
				return;
			}
		
			// unregistration succeeded
			//if (unregistered != null) 
			//{
			//	onUnregistered(context, registrationId);
			//	return;
			//}
		// last operation (registration or unregistration) returned an error;
		Log.d(TAG, "Registration error: " + error);
		// Registration failed
		if (ERROR_SERVICE_NOT_AVAILABLE.equals(error)) 
		{
			boolean retry = onRecoverableError(context, error);
			/*if (retry) 
			{
				int backoffTimeMs = GCMRegistrar.getBackoff(context);
				int nextAttempt = backoffTimeMs / 2 +
						sRandom.nextInt(backoffTimeMs);
				Log.d(TAG, "Scheduling registration retry, backoff = " +
						nextAttempt + " (" + backoffTimeMs + ")");
				Intent retryIntent =
						new Intent(INTENT_FROM_GCM_LIBRARY_RETRY);
				retryIntent.putExtra(EXTRA_TOKEN, TOKEN);
				PendingIntent retryPendingIntent = PendingIntent
						.getBroadcast(context, 0, retryIntent, 0);
				AlarmManager am = (AlarmManager)
						context.getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime() + nextAttempt,
				retryPendingIntent);
				// Next retry should wait longer.
				if (backoffTimeMs < MAX_BACKOFF_MS) 
				{
					GCMRegistrar.setBackoff(context, backoffTimeMs * 2);
				}
			} 
			else 	
			{
				Log.d(TAG, "Not retrying failed operation");
			}*/
		}
	
		else 
		{
			// Unrecoverable error, notify app
			onError(context, error);
		}
		
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//old code as it is
	
	protected void onRegistered(Context context, String registrationId)
	{
		
		//Log.v("GCMBaseIntentService", "In onRegistered()");
		RegistrationDetails rd = new RegistrationDetails(context);
		rd.setRegisteredFlag("Y");
		
		/*
		Log.i("GCMBaseIntentService", "In onRegistered()");
		PersonalDetails pd = new PersonalDetails(context);
		pd.setRegisteredFlag("Y");
		
		//This commented code is added for re registration on app upgrade 
		String ls_registered = "Y";
        final SharedPreferences prefs = getSharedPreferences(GCMIntentServiceNew.class.getSimpleName(),
	            Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Isregistered", ls_registered);
        editor.commit();
        Log.v(TAG,"Register Id in Intentservice : "+registrationId);
       
        
        
		Intent intent = new Intent(RegisterActivity.INTENT_REGISTERED_WITH_GCM);
		intent.putExtra(PhpFiles.TAG_REG_ID, registrationId);
		context.sendBroadcast(intent);
		 */
	}

	/**
	 * Method called when the device is unregistered
	 * */
	
	protected void onUnregistered(Context context, String registrationId)
	{
		/*
		Log.i("GCMBaseIntentService", "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		ServerUtilities.unregister(context, registrationId);
		*/
	}
	
	
	
	/**
	 * Method called on Receiving a new message
	 * */
	
	protected void onMessage(Context context, Intent intent) 
	{
		/*
		Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("msg");
		String li_serverMsgId = intent.getExtras().getString("msg_id");
		String ls_serverMsgTime = intent.getExtras().getString("server_msg_time");
		String ls_orderStatus   = intent.getExtras().getString("order_status");


		//Debugger.debug(TAG, "Message: "+ message + " AND ID "+li_serverMsgId + " Message Time "+ls_serverMsgTime);
		 

		JSONObject jo_message = new JSONObject();

		try
		{
			jo_message = new JSONObject(message);
			regIdFrom = jo_message.getString("reg_id_from");

			receiveMsg(jo_message, regIdFrom, context,li_serverMsgId,ls_serverMsgTime,ls_orderStatus);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		//Debugger.debug(TAG, "Message after removing reg id: " + message);

		//displayMessage(context, message);
		 
		 */
	}





	
	public void receiveMsg(JSONObject jo_message, String regIdFrom, Context context,String li_serverMsgId,String ls_serverMsgTime,String ls_orderStatus) throws JSONException
	{
		/*
		boolean lb_silent = false;
		lb_silent = jo_message.getBoolean("is_silent");
		Debugger.debug(TAG, "is_silent: " + lb_silent);

		ArrayList<String> list_fileAbsPaths = new ArrayList<String>();
		String[] ls_localDlPaths;

		// file location - set when a file is sent
		String ls_fileLocation = "";

		int li_messageType = jo_message.getInt("msg_type");

		if(li_messageType == MSG_TYPE_DESKTOP_OP)
		{
			int li_custId = jo_message.getInt("cust_id");
			String ls_operation = jo_message.getString("operation");

			GCMUsers gcmUsers = new GCMUsers(context);

			if(ls_operation.equals("I"))
			{
				JSONArray ja_appCusts = jo_message.getJSONArray("app_cust_details");
				ContentValues[] cvs = RegisterActivity.getAppCustsCVs(ja_appCusts);

				for(int i=0 ; i<cvs.length ; i++)
				{
					Debugger.debug(TAG, "CV@" + i + ":" + cvs[i].toString());

					li_custId = cvs[i].getAsInteger("cust_id");

					if(gcmUsers.checkCustExists(li_custId))
					{
						String ls_where = GCMUsers.CUST_ID + " = " + li_custId;

						// making customer inactive
						gcmUsers.update(cvs[i], ls_where);
					}
					else
					{
						gcmUsers.CRUD(cvs[i]);
					}
				}
			}
			else
			{
				// making customer inactive
				gcmUsers.updateStatus(li_custId, "I");
			}


		}
		else
		{
			JSONObject jo_headers = jo_message.getJSONObject("headers");
			String ls_message = jo_headers.getString("header_message");

			if(lb_silent)
			{			
				FileDownloader fDownloader = new FileDownloader(context, jo_message);

				try
				{
					list_fileAbsPaths = fDownloader.downloadFiles();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				// the message was silent.
			// we have saved the files.
			// we have the absolute paths where the downloaded files are saved.
			// now, storing this along with the message in the database
			// <HARDCODED>
			// only taken the first file as Jigar Sir told:
			// we wont be allowing multiple file uploads
			if(list_fileAbsPaths.size() > 0)
			{
				message = fDownloader.getMessageForDownloadedFile(list_fileAbsPaths.get(0));
			}
			// </HARDCODED>

				//ls_localDlPaths = fDownloader.getAllFilesAbsPaths_Local();

				// getting formatted message to be stored in the database.
				// the formatted message will let us identify whether the-
				// message is a normal one or a file or something else-
				// was donwloaded
				String[] ls_storeMsgs = fDownloader.getMessageForDownloadedFiles(); 
				Log.v(TAG,"store msgs after download :"+ls_storeMsgs);

				// <HARDCODED>
				ls_fileLocation = ls_storeMsgs[0];
				// </HARDCODED>

				ls_message = fDownloader.getMessageHeader();
			}

			String date;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.format(new Date());

			Calendar calendar = Calendar.getInstance();
			int hour 	= calendar.get(Calendar.HOUR_OF_DAY);
			int minute 	= calendar.get(Calendar.MINUTE);

			String ls_minute =  String.valueOf(calendar.get(Calendar.MINUTE));
			String ls_date = String.valueOf(calendar.get(Calendar.DATE));
			if(minute >= 0 && minute <=9)
			{
				ls_minute = "0" + minute;
			}
			
			
			// inserting row into chat_history
			ContentValues cv = new ContentValues();

			cv.put(ChatHistory.MSG_TYPE, "I");
			cv.put(ChatHistory.REG_ID, regIdFrom);

			cv.put(ChatHistory.MESSAGE, ls_message);
			cv.put(ChatHistory.FILE_LOCATION, ls_fileLocation);
			cv.put(ChatHistory.DATE, date);
			cv.put(ChatHistory.TIME, hour+ ":"+ls_minute);
			cv.put(ChatHistory.CHAT_SERVER_MSG_ID, li_serverMsgId);
			cv.put(ChatHistory.CHAT_SERVER_MSG_TIME,ls_serverMsgTime);
			cv.put(ChatHistory.CHAT_ORDER_STATUS,ls_orderStatus);

			ChatHistory chatHistory = new ChatHistory(context);
			Debugger.debug(TAG,"Message Time" + ls_serverMsgTime);
			Debugger.debug(TAG,"Message id" + li_serverMsgId);
			ll_msgId = chatHistory.CRUD(cv);

			// check if device is registered
			// if not registered, dont show notification.
			// And the msg has already been saved so that
			// when the user registers, he will be shown
			// all the messages

			PersonalDetails pd = new PersonalDetails(context);
			String ls_registered;

			ls_registered = pd.getRegisteredFlag();
			if(ls_registered == null) ls_registered = "N";

			if(ls_registered.equals("Y"))
			{
				if(DisplayUsers.activityRunning)
				{
					Intent intent = new Intent(GetGCMContacts.INTENT_REFRESH_LIST);
					context.sendBroadcast(intent);
				}

				if(ChatActivity.activityRunning)
				{
					Intent intent = new Intent("com.cresco.refresh_list");
					context.sendBroadcast(intent);
				}
				else
				{
					ls_message = getApprMsg(context, ls_message, regIdFrom);
					generateNotification(context, ls_message);
				}
			}
		}*/
	}

	/*
	public boolean isSilentMsg(String ls_msg)	
	{
		boolean lb_silent = false;

		Debugger.debug(TAG, "Message: " + ls_msg);

		try
		{
			JSONObject jo_message = new JSONObject(ls_msg);
 
			lb_silent = jo_message.getBoolean("is_silent");
			Debugger.debug(TAG, "Silent: " + lb_silent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if(ls_msg.startsWith(FileDownloader.SILENT_MSG_NOTIFIER))
		{
			lb_isSilent = true;
		}

		return lb_silent;
	}

	private String getApprMsg(Context context, String ls_msg, String ls_regIdFrom)
	{
		String ls_newMsg = ls_msg;

		ChatHistory chatHistory = new ChatHistory(context);

		int li_unreadMsgs = chatHistory.getUnreadMsgsCount(ls_regIdFrom);

		if(li_unreadMsgs > 1)
		{
			ls_newMsg = li_unreadMsgs + " New Messages";
		}

		return ls_newMsg;
	}

	*/
	/*
	 * Method called on receiving a deleted message
	 */
	
	protected void onDeletedMessages(Context context, int total)
	{
		//Log.i(TAG, "Received deleted messages notification");
		//String message = getString(R.string.gcm_deleted, total);
		//displayMessage(context, message);
		// notifies user
		//generateNotification(context, message);
	}

	
	 /* Method called on Error
	 */
	
	public void onError(Context context, String errorId) 
	{
		//Log.i(TAG, "Received error: " + errorId);
		//displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	
	protected boolean onRecoverableError(Context context, String errorId) 
	{
		// log message
		//Log.i(TAG, "Received recoverable error: " + errorId);
		//displayMessage(context, getString(R.string.gcm_recoverable_error,
			//	errorId));
		//return super.onRecoverableError(context, errorId);
		return false;
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	/*
	private void generateNotification(Context context, String message)
	{
		int icon = R.drawable.navrang_titlebar;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		String title = context.getString(R.string.app_name);

		GCMUsers gcmUsers	= new GCMUsers(context);
		PersonalDetails	pd	= new PersonalDetails(context);

		Cursor cursor = gcmUsers.getUserByRegId(regIdFrom);
		Debugger.debug(TAG, "reg id from: "+ regIdFrom);
		Debugger.debug(TAG, "Message: " + message);

		if(cursor.moveToFirst() && cursor.getCount() > 0)
		{
			Notification notification = new Notification(icon, message, when);

			Debugger.debug(TAG,"Cursor count is "+cursor.getCount() + " AND Reg id is "+regIdFrom);
			String regIdTo 		= pd.getRegId();
			String mobile 		= cursor.getString(cursor.getColumnIndex(GCMUsers.MOBILE_NUM));
			String ls_name 		= cursor.getString(cursor.getColumnIndex(GCMUsers.DISPLAY_NAME));

			Intent notificationIntent = new Intent(this, ChatActivity.class);
			notificationIntent.putExtra(GCMUsers.MOBILE_NUM, mobile);
			notificationIntent.putExtra(GCMUsers.DISPLAY_NAME,ls_name);
			notificationIntent.putExtra(ChatActivity.TAG_REG_ID_FROM, regIdTo);
			notificationIntent.putExtra(ChatActivity.TAG_REG_ID_TO, regIdFrom);

			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
					Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent =
					PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, title, message, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			//notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}
		else
		{
			Debugger.debug(TAG, "I will throw a toast!");
			Toast.makeText(context, "From desktop\nMessage: " + message, Toast.LENGTH_SHORT).show();
		}
	}
	
	*/
	


	

}
