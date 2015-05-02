package com.cresco.HealthMate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.cresco.HealthMate.R;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     * 
     * @param fromFile
     *            - FileInputStream for the file to copy from.
     * @param toFile
     *            - FileInputStream for the file to copy to.
     */
	private static final String TAG = "FileUtils";
	static Context context;
	static String ls_appName; 
	static String ls_exportDir;
	
	public FileUtils(Context context)
	{
		FileUtils.context = context;
		 
	}
    
	public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            long ll_bytes = fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

	public static void copyDB(Context context)
	{
		
		FileUtils.context = context;
		ls_appName = context.getResources().getString(R.string.app_name);
		
		ls_exportDir = Environment.getExternalStorageDirectory()
				.toString() + "/"+ls_appName;
		//Log.v(TAG,"copyDB file :"+ls_appName);
		
		//String ls_exportDir = Environment.getExternalStorageDirectory().toString() + "/Navarang2014";
		
		File f = new File(ls_exportDir);
		//if (!f.exists())
		//{
			f.mkdir();
		//}
		File srcFile = new File(DbHelper.DB_FILEPATH);
		File destFile = new File(ls_exportDir+"/"+DbHelper.DATABASE_NAME);

		try {
			FileUtils.copyFile(new FileInputStream(srcFile),
					new FileOutputStream(destFile));
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//kalyani start
	public static void deleteDB(Context context) 
	{
		FileUtils.context = context;
			
		ls_appName = context.getResources().getString(R.string.app_name);
			
		File extStore = Environment.getExternalStorageDirectory();
		//File path1 = new File(extStore.getAbsolutePath() + "/adnoteOTA/adnote.db");
		File path2 = new File(extStore.getAbsolutePath() + "/" +ls_appName +"/adnote.db");
			
				/*if(path1.exists())
				{
					try
					{
						path1.delete();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}*/
		if(path2.exists())
		{
			try
			{
				path2.delete();
				//Log.v(TAG,"Deleting db from "+ls_appName);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
		//kalyani end
}