package com.example.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.splash); 
		//Makes the splash screen sleep for 5 sec:
		Thread timer = new Thread() {
			public void run() {
				try{
					sleep(5000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally{
					Intent startMain = new Intent("com.example.assignment1.MAINACTIVITY");
					startActivity(startMain);
				}
			}
		};
		timer.start();
	}
}
