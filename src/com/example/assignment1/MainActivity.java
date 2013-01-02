package com.example.assignment1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*startActivity(new Intent(MainActivity.this, ImageViewer.class));
		reload();
		*/

		Button sendData = (Button) findViewById(R.id.button1);
		sendData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("Sender...");
				startActivity(new Intent(MainActivity.this, DataTask.class));
				//reload();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void reload() {
		System.out.println("Reload");
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();

	    overridePendingTransition(0, 0);
	    startActivity(intent);
	}
	
}
