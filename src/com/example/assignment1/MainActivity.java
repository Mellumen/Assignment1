package com.example.assignment1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	// http://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
	// http://androidgenuine.com/?tag=pass-string-from-intent-to-another-intent
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Get and set the text if included:
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText(extras.getString("string"));
		}
		// Try getting and setting the image:
		try {
			Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra(
					"BitmapImage");
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(bitmap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// ****Buttons*****
		// Sendt data:
		Button sendData = (Button) findViewById(R.id.button1);
		sendData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// System.out.println("Sender...");
				Intent intent = new Intent(getBaseContext(), DataTask.class);
				startActivity(intent);
			}
		});
		// Image:
		Button dispImage = (Button) findViewById(R.id.button2);
		dispImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// System.out.println("Viser...");
				Intent intent = new Intent(getBaseContext(), ImageViewer.class);
				startActivity(intent);

			}
		});
		// Get text:
		Button dispText = (Button) findViewById(R.id.button3);
		dispText.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// System.out.println("Skriver...");
				Intent intent = new Intent(getBaseContext(), GetText.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// To reload main with the image:
	public void reload(Bitmap image) {
		System.out.println("Reload");
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		intent.putExtra("BitmapImage", image);
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();

		overridePendingTransition(0, 0);
		startActivity(intent);
	}
}
