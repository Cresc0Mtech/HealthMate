package com.cresco.HealthMate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


//to retrive application version code
public class VersionUtils {

    public static int getVersionCode( Context context ) throws NameNotFoundException
    {
    	PackageInfo manager= context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    	//Toast.makeText(context, "Version is " + manager.versionCode, Toast.LENGTH_SHORT).show();
        return manager.versionCode;
    }
}