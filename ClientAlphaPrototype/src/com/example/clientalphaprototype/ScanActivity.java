package com.example.clientalphaprototype;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		
		initializeScanButton();
	}

	//Initialize scan button
	void initializeScanButton()
	{
		Button button = (Button) findViewById(R.id.button_scan);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent categories = new Intent(ScanActivity.this, Categories.class);
				startActivity(categories);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan, menu);
		return true;
	}

}
