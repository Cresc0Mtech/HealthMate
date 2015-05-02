package com.cresco.HealthMate;

import java.util.Locale;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;

public class DeviceUtils 
{
	public static final String TAG_DEVICE_ID	= "device_id";
	public static final String TAG_MAC_ADDRESS	= "mac_address";
	public static final String TAG_ANDROID_ID	= "android_id";
	public static final String TAG_SERIAL_NUM	= "serial_number";
	
	public static final String TAG_DEV_PAYLOAD	= "dev_payload";
	
	public static final String TAG_VERSION_ID	= "version_id";
	public static final String TAG_OS_NAME		= "os_id";
	public static final String TAG_OS_VERSION	= "os_version";
	public static final String TAG_DEVICE_MODEL	= "device_model";
	
	public static final String TAG_EMAIL_ID	= "email_id";
	
	/*
	 * _VERSION, versionId);
		bundle.putString(Personal_Details.TAG_OS_ID, osId);
		bundle.putString(Personal_Details.ANDROID_ID, osVersion);
		bundle.putString(Personal_Details.SERIAL_NUMBER, deviceModel);*/
	
	public static String getImei(Context context)
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		
		return deviceId;
	}
	
	public static String getMacAddress(Context context)
	{
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		
		String macAddress = info.getMacAddress();
		
		return macAddress;
	}
	
	public static String getAndroidId(Context context)
	{
		String androidId = Secure.getString(context.getContentResolver() , Secure.ANDROID_ID);
		
		return androidId;
	}
	
	@SuppressLint("NewApi")
	public static String getSerialId()
	{
		String serialId = null;
		serialId =  android.os.Build.SERIAL;
		
		if(serialId.contains("unknown"))
		{
			serialId = "";
		}
		
		return serialId;
	}
	
	public static String getEmailAddress(Context context) 
	{
		String emailAddress = null;

		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(context).getAccounts();

		for (Account account : accounts) 
		{
			if (emailPattern.matcher(account.name).matches())
			{
				emailAddress = account.name;
				break;
			}
		}

		return emailAddress;
	}
	
	public static Bundle getAllParams(Context context)
	{
		Bundle bundle = new Bundle();

		String deviceId, macAddress, androidId, serialId = null;
		String devPayload, versionId = "", osId, osVersion, deviceModel;

		// getting IMEI - device ID
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		if(deviceId == null) deviceId = "";

		// getting MAC Address
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		macAddress = info.getMacAddress();
		if(macAddress == null) macAddress = "";
		
		// <WOMAC>
		//macAddress = "";
		// </WOMAC>

		// getting the android ID
		androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if(androidId == null) androidId = "";

		// getting serial ID	
		/*serialId = Build.SERIAL;
		if(serialId != null)
		{
			if(serialId.contains("unknown"))
			{
				serialId = "";
			}
		}*/
		
		if(serialId == null) serialId = "";
		
		PackageInfo packageInfo = null;
		
		try
		{
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionId = packageInfo.versionName;
		} 
		catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		osId 		= "Android";
		osVersion 	= android.os.Build.VERSION.RELEASE;
		deviceModel = android.os.Build.MODEL;
		
		bundle.putString(TAG_DEVICE_ID, deviceId);
		bundle.putString(TAG_MAC_ADDRESS, macAddress);
		bundle.putString(TAG_ANDROID_ID, androidId);
		bundle.putString(TAG_SERIAL_NUM, serialId);
		
		bundle.putString(TAG_VERSION_ID, versionId);
		bundle.putString(TAG_OS_NAME, osId);
		bundle.putString(TAG_OS_VERSION, osVersion);
		bundle.putString(TAG_DEVICE_MODEL, deviceModel);
		
		devPayload = deviceId + serialId;
		bundle.putString(TAG_DEV_PAYLOAD, devPayload);
		bundle.putString(TAG_EMAIL_ID, getEmailAddress(context));
		
		return bundle;
	}
	
	@SuppressLint("NewApi")
	public static String getDeveloperPayload(Context context)
	{
		String deviceId, macAddress, androidId, serialId;
		String devPayload = "";

		// getting IMEI - device ID
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();	
		if(deviceId == null) deviceId = "";

		// getting MAC Address
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		macAddress = info.getMacAddress();
		if(macAddress == null) macAddress = "";

		// getting the android ID
		androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if(androidId == null) androidId = "";

		// getting serial ID	
		serialId = Build.SERIAL;
		if(serialId != null)
		{
			if(serialId.contains("unknown"))
			{
				serialId = "";
			}
		}

		devPayload = deviceId + serialId;

		//TGNGS Debugger.debug((TAG, "developer payload created: "+ devPayload);

		return devPayload;
	}
	
	public static String getNtwrkCountryIso(Context context)
	{
		String ls_ntwrkCountryIso;
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		ls_ntwrkCountryIso 	= tm.getNetworkCountryIso();
		
		return ls_ntwrkCountryIso;
	}
	
	public static String getCountry(Context context)
	{
		String ls_country;
		
		Locale locale = context.getResources().getConfiguration().locale;
		ls_country = locale.getCountry();
		
		return ls_country;
	}
	
	public static String getIsoCountry(Context context)
	{
		String ls_iso3Code;
		
		Locale locale = context.getResources().getConfiguration().locale;		
		ls_iso3Code = locale.getISO3Country();
		
		Debugger.debug("DeviceUtils", "ISO3 Country: " + ls_iso3Code);
		
		return ls_iso3Code;
	}
	
	public static Bundle getAllTelephonyDetails(Context context)
	{
		Bundle bundle = new Bundle();
		
		String ls_simCountryIso, ls_simOperator, ls_ntwrkCountryIso, ls_ntwrkOperator;
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		ls_simCountryIso 	= tm.getSimCountryIso();
		ls_simOperator 		= tm.getSimOperator();
		ls_ntwrkCountryIso 	= tm.getNetworkCountryIso();
		ls_ntwrkOperator	= tm.getNetworkOperator();
		
		bundle.putString("sim_country_iso", ls_simCountryIso);
		bundle.putString("sim_operator", ls_simOperator);
		bundle.putString("ntwrk_country_iso", ls_ntwrkCountryIso);
		bundle.putString("ntwrk_operator", ls_ntwrkOperator);
		
		Debugger.debug("DeviceUtils", "TelephonyManager details: " + bundle.toString());
		
		return bundle;
	}
	
	public static Bundle getLocaleDetails(Context context)
	{
		String ls_localeDetails = "";
		
		Locale locale = context.getResources().getConfiguration().locale;
		
		Bundle bundle = new Bundle();
		
		bundle.putString("country", locale.getCountry());
		bundle.putString("display_country", locale.getDisplayCountry());
		bundle.putString("display_language", locale.getDisplayLanguage());
		bundle.putString("display_name", locale.getDisplayName());
		bundle.putString("iso_country", locale.getISO3Country());
		bundle.putString("iso_language", locale.getISO3Language());
		bundle.putString("display_name", locale.getDisplayName());
		
		String[] ls_countries = Locale.getISOCountries();
		
		String ls_countriesStr = "";
		for(int i=0 ; i<ls_countries.length ; i++)
		{
			ls_countriesStr += " | " + ls_countries[i] + " | "; 
		}
		
		bundle.putString("iso_countries", ls_countriesStr);
		
		String[] ls_langs = Locale.getISOLanguages();
		String ls_langsStr = "";
		for(int i=0 ; i<ls_langs.length ; i++)
		{
			ls_langsStr += " | " + ls_langs[i] + " | "; 
		}
		
		//bundle.putString("iso_langs", ls_langsStr);
		
		Debugger.debug("DeviceUtils", "Locale details: " + bundle.toString());
		
		return bundle;
	}
	
	
	 public static String getPhoneNumber(Context context)
	  {
		    TelephonyManager mTelephonyMgr;
		    mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 

		    
		    String ls_number =mTelephonyMgr.getLine1Number(); 
		    if(ls_number == null)
		    {
		    	ls_number = "";
		    }
		    
		    if(ls_number.length() <= 0)
		    {
		    	ls_number = mTelephonyMgr.getSimSerialNumber();
		    	if(ls_number == null)
		    	{
		    		ls_number = "";
		    	}
		    }
		    
		    return ls_number;
		}
}
