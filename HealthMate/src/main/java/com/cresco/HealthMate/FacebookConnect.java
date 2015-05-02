package com.cresco.HealthMate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.cresco.HealthMate.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

 

public class FacebookConnect extends Activity implements OnClickListener
{
	
	Context context;
	String TAG = this.getClass().getSimpleName();
	Button  bt_healthmate, bt_post, bt_getprofile;
	
	private static String APP_ID = "980146335343232"; // Replace your App ID here
	 
    // Instance of Facebook Class
    private Facebook facebook;
    @SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    
    private LoginButton bt_facebook_login;
	
    private UiLifecycleHelper uiHelper;
    
    ImageView iv_profile;
    TextView tv_profile;
    
    private static final List<String> PERMISSIONS = new ArrayList<String>() {
        {
            add("user_location");
            add("user_birthday");
            add("user_likes");
        }
    };
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.facebook);
		
		context = this;
		
		facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
		
		bt_facebook_login = (LoginButton) findViewById(R.id.bt_facebook_login);
		bt_post	= (Button) findViewById(R.id.bt_post);
		bt_getprofile	= (Button) findViewById(R.id.bt_getprofile);
		bt_healthmate	= (Button) findViewById(R.id.bt_healthmate);
		iv_profile = (ImageView) findViewById(R.id.iv_profile);
		tv_profile = (TextView) findViewById(R.id.tv_profile);
		
		bt_facebook_login.setOnClickListener(this);
		bt_healthmate.setOnClickListener(this);
		bt_getprofile.setOnClickListener(this);
		bt_post.setOnClickListener(this);
		
		//bt_facebook_login.setReadPermissions(Arrays.asList("email"));
		
		bt_facebook_login.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes"));
		
		
		
		uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        
        //ensureOpenSession();
	}
	
	
	private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null ||
                !Session.getActiveSession().isOpened()) {
            Session.openActiveSession(
                    this, 
                    true, 
                    PERMISSIONS,
                    new Session.StatusCallback() {
                        @SuppressWarnings("deprecation")
						@Override
                        public void call(Session session, SessionState state, Exception exception) {
                            //onSessionStateChanged(session, state, exception);
                        	
                        	
                        	if (state.isOpened()) 
    			            {
    			                Log.v( TAG,"Facebook session opened2.");
    			                
    			             // Request user data and show the results
    			                Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
    			                {

    			                    @Override
    			                    public void onCompleted(GraphUser user, Response response) 
    			                    {
    			                        if (user != null) 
    			                        {
    			                            // Display the parsed user info
    			                            tv_profile.setText(buildUserInfoDisplay(user));
    			                            
    			                            ///////
    			                            SharedPreferences.Editor editor = mPrefs.edit();
    				                        editor.putString("access_token",
    				                                facebook.getAccessToken());
    				                        editor.putLong("access_expires",
    				                                facebook.getAccessExpires());
    				                        editor.commit();
    			                            
    			                            ///////
    			                        }
    			                    }
    			                });
    			            } 
    			            else if (state.isClosed()) 
    			            {
    			                Log.v(TAG, "Facebook session closed2.");
    			            }
                        }
                    });
            return false;
        }
        return true;
    }
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.v(TAG, "Logged in...");
	    } else if (state.isClosed()) {
	        Log.v(TAG, "Logged out...");
	    }
	}
	private Session.StatusCallback statusCallback = new Session.StatusCallback() 
	{
		
		String facebookID = null, facebookEmail = null, faceBooklastName = null , faceBookFirstName = null;
		        @SuppressWarnings("deprecation")
				@Override
			        public void call(Session session, SessionState state,
		                Exception exception) 
		        	{
		        	
		        	///
		        	mPrefs = getPreferences(MODE_PRIVATE);
		    	    String access_token = mPrefs.getString("access_token", null);
		    	    long expires = mPrefs.getLong("access_expires", 0);
		    	    
		    	    Log.v(TAG, "access_token : "+access_token);
		    	    Log.v( TAG,"expires : "+expires);
		    	 
		    	    if (access_token != null) 
		    	    {
		    	        facebook.setAccessToken(access_token);
		    	    }
		    	 
		    	    if (expires != 0) 
		    	    {
		    	        facebook.setAccessExpires(expires);
		    	    }
		    	    /////
			            if (state.isOpened()) 
			            {
			                Log.v( TAG,"Facebook session opened1.");
			                
			             // Request user data and show the results
			                Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
			                {

			                    @Override
			                    public void onCompleted(GraphUser user, Response response) 
			                    {
			                        if (user != null) 
			                        {
			                            // Display the parsed user info
			                            tv_profile.setText(buildUserInfoDisplay(user));
			                            
			                            ///////
			                            SharedPreferences.Editor editor = mPrefs.edit();
				                        editor.putString("access_token",
				                                facebook.getAccessToken());
				                        editor.putLong("access_expires",
				                                facebook.getAccessExpires());
				                        editor.commit();
			                            
			                            ///////
			                        }
			                    }
			                });
			            } 
			            else if (state.isClosed()) 
			            {
			                Log.v(TAG, "Facebook session closed1.");
			            }
			        }
	};
	
	
	private String buildUserInfoDisplay(GraphUser user) 
	{
	    StringBuilder userInfo = new StringBuilder("");

	    // Example: typed access (name)
	    // - no special permissions required
	    userInfo.append(String.format("Name: %s\n\n", 
	        user.getName()));

	    // Example: typed access (birthday)
	    // - requires user_birthday permission
	    userInfo.append(String.format("Birthday: %s\n\n", 
	        user.getBirthday()));
	    
	 userInfo.append(String.format("Locale: %s\n\n", 
	        user.getProperty("locale")));
	    
	    userInfo.append(String.format("Gender: %s\n\n", 
	   	    user.getProperty("gender")));
	    
	    userInfo.append(String.format("timezone: %s\n\n", 
		   	    user.getProperty("timezone")));
	    
	   // userInfo.append(String.format("email: %s\n\n", 
		//   	    user.getProperty("email")));
	    
	   // userInfo.append(String.format("username: %s\n\n", 
		//   	    user.getProperty("username")));
	    
	    /////////
	    
	   
	    String face_id = user.getId();
	    Task_Register task = new Task_Register(face_id);
	    Bitmap b = null;
		try {
			b = task.execute().get();
			
			Log.v(TAG,"in get profile :"+b);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Drawable d = new BitmapDrawable(getResources(), b);
	    
	    iv_profile.setImageDrawable(d);
	    
	    
	   //////////

	    // Example: partially typed access, to location field,
	    // name key (location)
	    // - requires user_location permission
	    
	    userInfo.append(String.format("Location: %s\n\n", 
	        user.getLocation().getProperty("name")));

	    

	    // Example: access via key for array (languages) 
	    // - requires user_likes permission
	   
	     JSONArray languages = (JSONArray)user.getProperty("languages");
	    if (languages.length() > 0) 
	    {
	        ArrayList<String> languageNames = new ArrayList<String> ();
	        for (int i=0; i < languages.length(); i++) 
	        {
	            JSONObject language = languages.optJSONObject(i);
	            // Add the language name to a list. Use JSON
	            // methods to get access to the name field. 
	            languageNames.add(language.optString("name"));
	        }           
	        userInfo.append(String.format("Languages: %s\n\n", 
	        languageNames.toString()));
	    }

	    return userInfo.toString();
	}
	
	private final class Task_Register extends AsyncTask<Bitmap, Bitmap, Bitmap>
	
	{
		String f_id;
		Bitmap mIcon1 = null;
		
		public Task_Register( String id)
		{			
			// POST parameters to sends
			
			f_id = id;
		}

		@Override
		protected Bitmap doInBackground(Bitmap... params) 
		{
			Log.v(TAG,"In doin background"+f_id);
			URL img_value = null;
		    
				//img_value = new URL("http://graph.facebook.com/"+f_id+"/picture?type=small");
		    	
		    	//HttpURLConnection urlConnection = (HttpURLConnection) img_value.openConnection();
		    		try 
		    		{
		    			
		    			HttpGet httpRequest = new HttpGet(URI.create("http://graph.facebook.com/"+f_id+"/picture?type=large") );
		                HttpClient httpclient = new DefaultHttpClient();
		                HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
		                HttpEntity entity = response.getEntity();
		                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		                mIcon1 = BitmapFactory.decodeStream(bufHttpEntity.getContent());
		                httpRequest.abort();
		    		   //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		    		  // mIcon1 = BitmapFactory.decodeStream(in);
		    			
		    		}
		    		catch (IOException e) 
		    		{
						e.printStackTrace();
					}
		    		finally 
		    		{
		    		      //urlConnection.disconnect();
		    		}
			 
		    
		   
			Log.v(TAG,"mIcon1 :"+mIcon1);
		   
			return mIcon1;
		}
		
		
		
		@Override
		protected void onPostExecute(Bitmap mIcon1)
		{
			super.onPostExecute(mIcon1);
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.bt_facebook_login : 
				
				//loginToFacebook();
				break;
				
			case R.id.bt_post : 
				
				//postToWall();
				break;
				
			case R.id.bt_getprofile : 
				
				//getProfileInformation();
				break;
				
				
			case R.id.bt_healthmate : 
				
				Intent i = new Intent(this, FirstScreen_Activity.class);
				startActivity(i);
				break;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	
	public void loginToFacebook() 
	{
	    mPrefs = getPreferences(MODE_PRIVATE);
	    String access_token = mPrefs.getString("access_token", null);
	    long expires = mPrefs.getLong("access_expires", 0);
	    
	    Log.v(TAG, "access_token : "+access_token);
	    Log.v( TAG,"expires : "+expires);
	 
	    if (access_token != null) 
	    {
	        facebook.setAccessToken(access_token);
	    }
	 
	    if (expires != 0) 
	    {
	        facebook.setAccessExpires(expires);
	    }
	 
	    if (!facebook.isSessionValid()) 
	    {
	        facebook.authorize(this,
	                new String[] { "email", "publish_stream" },
	                new DialogListener()
	        		{
	 
	                    @Override
	                    public void onCancel() 
	                    {
	                        // Function to handle cancel event
	                    }
	 
	                    @Override
	                    public void onComplete(Bundle values) 
	                    {
	                        // Function to handle complete event
	                        // Edit Preferences and update facebook acess_token
	                        SharedPreferences.Editor editor = mPrefs.edit();
	                        editor.putString("access_token",
	                                facebook.getAccessToken());
	                        editor.putLong("access_expires",
	                                facebook.getAccessExpires());
	                        editor.commit();
	                    }

						@Override
						public void onFacebookError(FacebookError e) 
						{
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onError(DialogError e) 
						{
							// TODO Auto-generated method stub
							
						}
	              });
	    }
	}
	
	
	@SuppressWarnings("deprecation")
	public void postToWall() 
	{
	    // post on user's wall.
	    facebook.dialog(this, "feed", new DialogListener() {
	 
	        @Override
	        public void onFacebookError(FacebookError e) {
	        }
	 
	        @Override
	        public void onError(DialogError e) {
	        }
	 
	        @Override
	        public void onComplete(Bundle values) {
	        }
	 
	        @Override
	        public void onCancel() {
	        }
	    });
	 
	}
	
	@Override
	public void onResume() 
	{
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() 
	{
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	}
