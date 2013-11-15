package com.example.clientprototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MenuActivity";
	Button buttonQR;
	Button buttonAdvanced;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//Manage buttons
		buttonQR = (Button) findViewById(R.id.buttonQR);
		buttonAdvanced = (Button) findViewById(R.id.buttonAdvanced);
		buttonQR.setOnClickListener(this); 
		buttonAdvanced.setOnClickListener(this); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		   switch(v.getId()) {
           case R.id.buttonQR:
       			Toast.makeText(MenuActivity.this,"buttonQR is clicked",Toast.LENGTH_LONG).show();
       			Log.d(TAG,"buttonQR onClicked");
           break;
           case R.id.buttonAdvanced:
      			Toast.makeText(MenuActivity.this,"buttonAdvanced is clicked",Toast.LENGTH_LONG).show();
      			Log.d(TAG,"buttonAdvanced onClicked");
           break;
		   }
	}


		
}


