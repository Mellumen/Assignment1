package com.example.assignment1;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
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
import android.widget.TextView;

public class GetText extends Activity {
	TextView textview;
	String url = "https://dl.dropbox.com/u/10542464/Dummy.txt";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textview = new TextView(this);

		// Checks if the user is connected to the internet
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		// If connected:
		if (networkInfo != null && networkInfo.isConnected()) {
			textview = (TextView) findViewById(R.id.textView1);
			new DownloadText().execute();
		}
		// Else ask user to change settings:
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

	// Creates a new thread to start downloading the text
	private class DownloadText extends AsyncTask<Void, Void, String> {
		ProgressDialog pd;

		protected String doInBackground(Void... params) {
			String response = null;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse res = client.execute(get);
				HttpEntity ent = res.getEntity();

				if (ent != null) {
					response = EntityUtils.toString(ent);
					return response;
				} else {
					response = "Failed!";
					return response;
				}

			} catch (Exception e) {
				System.out.println("Error: " + e);
				return response;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			// System.out.println("Ferdig med tekst");
			reload(result);
			finish();
		}

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(GetText.this, "Loading...",
					"Downloading text...");

		}

		public void reload(String text) {
			System.out.println("Reload");
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.putExtra("string", text);
			overridePendingTransition(0, 0);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			finish();

			overridePendingTransition(0, 0);
			startActivity(intent);
		}

	}
}