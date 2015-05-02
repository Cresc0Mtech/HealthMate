package com.cresco.HealthMate;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.BitmapFactory;

@SuppressLint("SdCardPath")
public class DbHelper  extends SQLiteOpenHelper 
{

	public static final String DATABASE_NAME = "New.db";
	protected static final int DATABASE_VERSION = 1;
	protected static final String TAG = "DbHelper";
	public static final String DB_FILEPATH = "/data/data/com.cresco.HealthMate/databases/New.db";
    private static DbHelper mInstance = null;
    public static Context context;
	
	public static final boolean USE_ANALYTICS = true;
    public static final boolean USE_ADD = true;
	


    private static final String CREATEFILE = "create.sql";
    private static final String UPGRADEFILE_PREFIX = "upgrade-";
    private static final String UPGRADEFILE_SUFFIX = ".sql";
    private static final String SQL_DIR = "sql";

    private SQLiteDatabase db;
    static int dbVersion = 1;

    Add_Profile add_profile;

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) 
	{
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	
	public DbHelper(Context c,int version)
	{
		super(c, DATABASE_NAME, null, version);
		context = c;

	}

    /*
    public static DbHelper getInstance(Context c)
    {
        try
        {
            dbVersion = VersionUtils.getVersionCode(c);
            //context = c;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        context = c;
        if (mInstance == null)
        {
            mInstance = new DbHelper(c, dbVersion);
            context = c;
        }
        return mInstance;
    }
    */

	public static DbHelper getInstance(Context c)
	{
		if(mInstance == null)
		{
			mInstance =  new DbHelper(c,dbVersion);
		}

		return mInstance;
	}


	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub

		/*db.execSQL(" CREATE TABLE IF NOT EXISTS " + T_BPMeasures.TABLE_NAME + " ( " +
				T_BPMeasures.ID		 		 + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				T_BPMeasures.DATE 		 + " TEXT , "  +
				T_BPMeasures.TIME		     + " TEXT , "  +
				T_BPMeasures.LOW_RANGE        + " TEXT , " + 
				T_BPMeasures.HIGH_RANGE 	     + " TEXT  );");*/
		
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + Profile_Master.TABLE_NAME + " ( " +
				Profile_Master.PROFILE_ID 		 + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				Profile_Master.FIRST_NAME		     + " TEXT , "  +
				Profile_Master.MIDDLE_NAME        + " TEXT , " + 
				Profile_Master.LAST_NAME        + " TEXT , " + 
				Profile_Master.GENDER        + " TEXT , " + 
				Profile_Master.RELATION        + " TEXT , " + 
				Profile_Master.DOB        + " DATETIME , " + 
				Profile_Master.BLOODGROUP        + " TEXT , " +
				Profile_Master.HEIGHT        + " TEXT , " +
				Profile_Master.MOBILE_NO        + " TEXT , " +
				Profile_Master.EMAIL_ID        + " TEXT , " +
				Profile_Master.CITY        + " TEXT , " +
				Profile_Master.COUNTRY        + " TEXT , " +
				Profile_Master.PINCODE        + " TEXT , " +
				Profile_Master.STATUS        + " TEXT , " +
				Profile_Master.SERVER_STATUS        + " TEXT , " +
				Profile_Master.SERVER_PROFILE_ID 	     + " INTEGER  );");
		
		
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + Parameter_Master.TABLE_NAME + " ( " +
				Parameter_Master.PARAMETER_ID 		 + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				Parameter_Master.PARAMETER_NAME		     + " TEXT , "  +
				Parameter_Master.PARAMETER_UNIT		     + " TEXT , "  +
				Parameter_Master.MIN_VALUE		     + " TEXT , "  +
				Parameter_Master.MAX_VALUE 	     + " TEXT  );");
		
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + Profile_ParameterDefaults.TABLE_NAME + " ( " +
				Profile_ParameterDefaults.PPD_ID 		 + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				Profile_ParameterDefaults.PROFILE_ID		     + " INT , "  +
				Profile_ParameterDefaults.PARAMETER_ID        + " INT , " + 
				Profile_ParameterDefaults.MIN_VALUE        + " TEXT , " + 
				Profile_ParameterDefaults.MAX_VALUE 	     + " TEXT  );");
		
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + BloodPressure_Master.TABLE_NAME + " ( " +
				BloodPressure_Master.MSR_ID 		  + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				BloodPressure_Master.PROFILE_ID		  + " INT , "  +
				BloodPressure_Master.PM1_VALUE        + " TEXT , " +
				BloodPressure_Master.PM2_VALUE        + " TEXT , " +
				BloodPressure_Master.PM3_VALUE        + " TEXT , " +
				BloodPressure_Master.MSR_TS           + " DATETIME , " +
				BloodPressure_Master.MSR_LOCATION     + " TEXT , " +
				BloodPressure_Master.MSR_POSITION     + " TEXT , " +
				BloodPressure_Master.MSR_REMARKS      + " TEXT  );");
		
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + Sugar_Master.TABLE_NAME + " ( " +
				Sugar_Master.MSR_ID 		      + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				Sugar_Master.PROFILE_ID		      + " INT , "  +
                Sugar_Master.MSR_DATE            + " DATE , " +
                Sugar_Master.PM4_VALUE            + " TEXT , " +
				Sugar_Master.PM4_TIME               + " TEXT , " +
				Sugar_Master.PM4_REMARKS          + " TEXT , " +
				Sugar_Master.PM5_VALUE            + " TEXT , " +
				Sugar_Master.PM5_TIME               + " TEXT , " +
				Sugar_Master.PM5_REMARKS          + " TEXT  );");
		
		/*db.execSQL(" CREATE TABLE IF NOT EXISTS " + Measurement_values.TABLE_NAME + " ( " +
				Measurement_values.MSR_ID 		 + " INTEGER PRIMARY KEY AUTOINCREMENT , "  +
				Measurement_values.PROFILE_ID		     + " INT , "  +
				Measurement_values.PARAMETER_ID        + " INT , " +
				Measurement_values.SEQUENCE_ID        + " INT , " +
				Measurement_values.MSR_TS        + " TEXT , " + 
				Measurement_values.MSR_VALUE        + " TEXT , " + 
				Measurement_values.MSR_LOCATION        + " TEXT , " + 
				Measurement_values.MSR_POSITION        + " TEXT , " + 
				Measurement_values.MSR_REMARKS 	     + " TEXT  );");*/
		
		
		// personal_details
		db.execSQL(" CREATE TABLE IF NOT EXISTS " + Personal_Details.TABLE_NAME
					+ "(" + Personal_Details.ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ Personal_Details.DEVPAYLOAD + " TEXT,"
					+ Personal_Details.IMEI_ID + " TEXT,"
					+ Personal_Details.SERIAL_NO + " TEXT,"
					+ Personal_Details.DEVICE_EMAIL + " TEXT,"
					+ Personal_Details.INSTALL_DATE + " DATE,"
					+ Personal_Details.INSTALL_TYPE + " TEXT,"
					+ Personal_Details.OS_ID + " TEXT,"
					+ Personal_Details.OS_VERSION + " TEXT,"
					+ Personal_Details.VERSION_ID + " TEXT,"
					+ Personal_Details.VERSION_CODE + " TEXT,"
					+ Personal_Details.DEVICE_MODEL + " TEXT,"
					+ Personal_Details.ANDROID_ID + " TEXT,"
					+ Personal_Details.MAC_ADDRESS + " TEXT " + ");");
		
		//registration_details
		db.execSQL("CREATE TABLE IF NOT EXISTS " + RegistrationDetails.TABLE_NAME +
				"(" +
				RegistrationDetails.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				RegistrationDetails.REG_ID + " TEXT," +
				RegistrationDetails.IS_REGISTERED +" TEXT)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + FaceBookDetails.TABLE_NAME +
				"(" +
				FaceBookDetails.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				FaceBookDetails.PROFILE_ID + " INTEGER," +
				FaceBookDetails.FIRST_NAME + " TEXT," +
				FaceBookDetails.MIDDLE_NAME        + " TEXT , " + 
				FaceBookDetails.LAST_NAME        + " TEXT , " + 
				FaceBookDetails.GENDER        + " TEXT , " + 
				FaceBookDetails.RELATION        + " TEXT , " + 
				FaceBookDetails.DOB        + " DATETIME , " + 
				FaceBookDetails.BLOODGROUP        + " TEXT , " +
				FaceBookDetails.HEIGHT        + " TEXT , " +
				FaceBookDetails.MOBILE_NO        + " TEXT , " +
				FaceBookDetails.EMAIL_ID        + " TEXT , " +
				FaceBookDetails.CITY        + " TEXT , " +
				FaceBookDetails.COUNTRY        + " TEXT , " +
				FaceBookDetails.PINCODE 	     + " TEXT  );");
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Location_Master.TABLE_NAME +
				"(" +
				Location_Master.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				Location_Master.LOCATION_ID + " INTEGER," +
				Location_Master.TIMESTAMP + " TEXT," +
				Location_Master.ADDRESS_LINE1 + " TEXT," +
				Location_Master.ADDRESS_LINE2 + " TEXT," +
				Location_Master.CITY + " TEXT," +
				Location_Master.COUNTRY + " TEXT," +
				Location_Master.PINCODE + " TEXT," +
				Location_Master.LATITUDE + " TEXT," +
				Location_Master.LONGITUDE +" TEXT)");

        //inserting by default 2 profiles and its measurement
        insertDefault_Details(db);
	
	}

    public void insertDefault_Details(SQLiteDatabase db)
    {
        add_profile = new Add_Profile();
        String ls_sql;
        //Shane William Jefferson

        ls_sql = " INSERT INTO profile_master (profile_id, first_name,middle_name,last_name,gender,relation,dob,bloodgroup,height,mobile_no,email_id,city,country,pincode,status,server_status) "
                + "  VALUES (1, 'Shane','William','Jefferson','Male','Self','01/01/1985','O+','5.3','9999999999','shane.jefferson@rediffmail.com','Rolla','USA','400613','A','OK')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO profile_master (profile_id, first_name, middle_name, last_name, gender, relation, dob, bloodgroup, height, mobile_no, email_id, city, country, pincode, status, server_status) "
                + "  VALUES (2, 'Audrey', 'Shane', 'Jefferson', 'Female', 'Wife', '01/01/1985', 'A+', '5.1', '9999999999', 'audrey.jefferson@rediffmail.com', 'Rolla', 'USA', '400613', 'A', 'OK')";
        db.execSQL(ls_sql);

        Bitmap default_male = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.default_profile1);
        saveBitmapImage(default_male, 1);

        Bitmap default_female = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.defualt_profile2);
        saveBitmapImage(default_female,2);


        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (1, 1, '120', '80', '72',  '2015-04-02 11:43:00', 'Left Leg', 'Standing', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (2, 1, '121', '81', '73',  '2015-04-03 11:43:00', 'Right Leg', 'Sleeping', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (3, 1, '122', '82', '74',  '2015-04-04 11:43:00', 'Right Arm', 'Seated', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (4, 1, '123', '83', '75',  '2015-04-05 11:43:00', 'Right Wrist', 'Reclined', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (5, 1, '124', '84', '76',  '2015-04-06 11:43:00', 'Left Wrist', 'Reclined', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (6, 2, '120', '80', '72',  '2015-04-02 11:43:00', 'Left Arm', 'Standing', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (7, 2, '121', '82', '73',  '2015-04-03 11:43:00', 'Left Leg', 'Sleeping', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (8, 2, '122', '82', '74',  '2015-04-04 11:43:00', 'Right Wrist', 'Seated', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (9, 2, '123', '83', '75',  '2015-04-05 11:43:00', 'Right Arm', 'Reclined', '')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blpr_msr (msr_id, profile_id, pm1_value, pm2_value, pm3_value, msr_ts, msr_location, msr_position, msr_remarks) "
                + "  VALUES (10, 2, '124', '84', '76',  '2015-04-06 11:43:00', 'Right Leg', 'Reclined', '')";
        db.execSQL(ls_sql);



        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (1, 1, '2015-04-02', '120', '11:43:00', 'Normal', '140',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (2, 1, '2015-04-03', '122', '11:43:00', 'Normal', '142',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (3, 1, '2015-04-04', '124', '11:43:00', 'Normal', '144',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (4, 1, '2015-04-05', '126', '11:43:00', 'Normal', '146',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (5, 1,  '2015-04-06', '128', '11:43:00', 'Normal', '148',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (6, 2,  '2015-04-02', '120', '11:43:00', 'Normal', '140',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (7, 2,  '2015-04-03', '122', '11:43:00', 'Normal', '142',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (8, 2,  '2015-04-04', '124', '11:43:00', 'Normal', '144',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (9, 2,  '2015-04-05', '126', '11:43:00', 'Normal', '146',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);

        ls_sql = " INSERT INTO blsg_msr (msr_id, profile_id, msr_date, pm4_value, pm4_time, pm4_remarks, pm5_value, pm5_time, pm5_remarks) "
                + "  VALUES (10, 2,  '2015-04-06', '128', '11:43:00', 'Normal', '148',  '11:43:00', 'Normal')";
        db.execSQL(ls_sql);


    }

    public boolean saveBitmapImage(Bitmap profile, int id)
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

            File file = new File(sdIconStorageDir, "Profile"+id+".jpg");

            //Log.v(TAG,"filepath : "+filePath);
            ////

            if (file.exists())
                file.delete();

            ///


            FileOutputStream fileOutputStream = new FileOutputStream(file);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            profile.compress(Bitmap.CompressFormat.JPEG, 100, bos);

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




	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
       // db.execSQL("DROP TABLE IF EXISTS " + T_BPMeasures.TABLE_NAME);
 
        // create fresh table
        //this.onCreate(db);

        upgradeDB(db, oldVersion, newVersion);

	}


    public void upgradeDB(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try
        {
            // Log.v(TAG,"upgrade database from {} to {}" + oldVersion +" to " +
            // newVersion);
            for (String sqlFile : AssetUtils.list(SQL_DIR,
                    this.context.getAssets()))
            {
                if (sqlFile.startsWith(UPGRADEFILE_PREFIX))
                {
                    int fileVersion = Integer.parseInt(sqlFile.substring(
                            UPGRADEFILE_PREFIX.length(), sqlFile.length()
                                    - UPGRADEFILE_SUFFIX.length()));
                    if (fileVersion > oldVersion && fileVersion <= newVersion)
                    {
                        execSqlFile(sqlFile, db, context);
                    }
                }
            }
        } catch (IOException exception)
        {
            throw new RuntimeException("Database upgrade failed", exception);
        }
    }


    protected void execSqlFile(String sqlFile, SQLiteDatabase db,
                               Context context) throws SQLException, IOException
    {
        //Log.v(TAG,"  exec sql file: {}"+ sqlFile );
        // Toast.makeText(context,"Upgrading Database File",Toast.LENGTH_SHORT).show();
        for (String sqlInstruction : SqlParser.parseSqlFile(SQL_DIR + "/"
                + sqlFile, this.context.getAssets())) {
            //Log.v(TAG,"    sql: {}"+ sqlInstruction );
            try
            {
                db.execSQL(sqlInstruction);
            }
            catch (Exception e)
            {
               // Log.v(TAG,"Exception in Sql Execution "+sqlInstruction);
            }
        }
    }

}
