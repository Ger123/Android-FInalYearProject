package com.horgan.citconnect;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	static final String tag = "StateOfApp";
	Button buttonUpdate;
	EditText updateStatus;
	Twitter	twitter;
	
	// This is where the activity starts.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the UI elements into java
		buttonUpdate = (Button) findViewById(R.id.button_update);
		updateStatus = (EditText) findViewById(R.id.status_update);

		// Attach listeners to UI elements
		buttonUpdate.setOnClickListener(this);
	}
	// A UI event occurs when button is clicked
	@Override
	public void onClick(View v) {
		final String statusText = updateStatus.getText().toString();
		// Create an Async task
		new PostToTwitter().execute(statusText);

		
		
		Log.i("APP", "Button clicked and posting text of update:" + statusText);
	}
	// Creating a separate thread from the UI to work in the background. 
		class PostToTwitter extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {
				try {
					twitter = new Twitter("horgag", "Hoggy22222");
					twitter.setAPIRootUrl("http://ec2-54-200-104-161.us-west-2.compute.amazonaws.com/statusnet/index.php/api");
					twitter.setStatus(params[0]);
					Log.e(tag, "Posted: " +params[0]);
					return "Posted Upate: " +params[0];
				} catch (TwitterException e) {
					Log.e(tag, "FAILED", e);
					e.printStackTrace();
					return "Update not posted: " +params[0];
				}
				
			}
			// Returning to UI for toast 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
				Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
			}
			
			
			
			
			
		}

}
