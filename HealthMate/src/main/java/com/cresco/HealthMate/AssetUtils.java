package com.cresco.HealthMate;

import java.io.IOException;
import java.util.Arrays;


import android.content.res.AssetManager;


//to listing in assets
public class AssetUtils
{
//	 public static boolean exists( String fileName, String path, AssetManager assetManager ) throws IOException
//	 {
//		    fileName = M_Item_Master.TABLE_NAME;
//
//	        for( String currentFileName : assetManager.list(path))
//	        {
//	            if ( currentFileName.equals(fileName))
//	            {
//	                return true ;
//	            }
//	        }
//	        return false ;
//	    }
	   
	    public static String[] list( String path, AssetManager assetManager ) throws IOException 
	    {
	        String[] files = assetManager.list(path);
	        Arrays.sort( files );
	        return files ;
	    }
	

}
