package com.example.assignment1;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class DataTask extends MainActivity {
	Intent i = new Intent(this, MainActivity.class);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Try to connect:
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		//If connected, send data:
		if (networkInfo != null && networkInfo.isConnected()) {
			// do stuff
			new SendGet().execute();
		}
		//Else ask user to change settings:
		else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Internet is disabled!");
			alertDialog.setMessage("Open settings?");
			alertDialog.setButton(-3, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(
									Settings.ACTION_WIRELESS_SETTINGS));
							finish();
						}
					});
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.show();
		}
	}

	//Creates a new thread to send the data:
	private class SendGet extends AsyncTask<Void, Void, String> {
		ProgressDialog pd;

		@Override
		protected String doInBackground(Void... params) {
			try {
				HttpClient client = new DefaultHttpClient();
				String getURL = "http://gtl.hig.no/mobile/logging.php?user=Ruud&data=Hello";
				HttpGet get = new HttpGet(getURL);
				HttpResponse res = client.execute(get);
				System.out.println("Sendt");

				if (res != null) {
					Toast.makeText(getApplicationContext(), "Not working",
							Toast.LENGTH_LONG).show();
				}
			} 
			catch (Exception e) {
				System.out.println("Error: " + e);
			}
			finish();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();

		}

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(DataTask.this, "Loading...",
					"Uploading data...");
		}
	}
}
