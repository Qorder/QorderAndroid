package com.example.clientalphaprototype;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Details extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		String title = "title";

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			title = extras.getString("product");
		}
		
		ParseJson(title);		
		
	}

	void ParseJson(String product)
	{
		TextView tv_title = (TextView)findViewById(R.id.textView_title);
		tv_title.setText(product);
		
		TextView tv_description = (TextView)findViewById(R.id.textView_description);
		tv_description.setText("A description for " + product + " goes here" );
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
