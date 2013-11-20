package com.example.clientalphaprototype;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Categories extends Activity {
	ListView categories_listView;
	String[] categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

		categories_listView = (ListView) findViewById(R.id.categories_listview);

		setCategories();

		initializeArrayAdapter();
	}

	void initializeArrayAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				categories);

		// Assign adapter to ListView
		categories_listView.setAdapter(adapter);

		// ListView Item Click Listener
		categories_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String category = (String) categories_listView
						.getItemAtPosition(position);
				Intent i = new Intent(getApplicationContext(), Products.class);
				i.putExtra("category",category);
				startActivity(i);
			}
		});
	}

	// Parse Json to set the categories
	void setCategories() {
		categories = new String[] { "Food", "Drinks" };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}

}
