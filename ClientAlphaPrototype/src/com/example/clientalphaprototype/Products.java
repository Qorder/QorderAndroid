package com.example.clientalphaprototype;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Products extends Activity {

	ListView products_listview;
	List<String> products;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		products_listview = (ListView) findViewById(R.id.products_listview);
		
		products = new ArrayList<String>();
		
		String category = "Food";

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			category = extras.getString("category");
		}

		setProducts(category);
		initializeActionBar();
		initializeArrayAdapter();
	}
	
	void initializeActionBar()
	{
		  ActionBar actionBar = getActionBar();

		    actionBar.setCustomView(R.layout.actionbar_view);
		    actionBar.setDisplayShowCustomEnabled(true);
		    actionBar.getCustomView().setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View view) {
					Intent i = new Intent(getApplicationContext(), Basket.class);
					startActivity(i);
		        }
		    });
		    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
	}

	void initializeArrayAdapter() {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				products);

		products_listview.setAdapter(adapter);

		products_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product = (String) products_listview
						.getItemAtPosition(position);
				Intent i = new Intent(getApplicationContext(), Details.class);
				i.putExtra("product", product);
				startActivity(i);
			}
		});
	}

	// Parse Json to set the categories
	void setProducts(String category) {
		
		if (category.contentEquals("Food")) {
			for (int i = 1; i < 10; i++)
				products.add("Food" + i);
		} else if (category.contentEquals("Drinks")) {
			for (int i = 1; i < 10; i++)
				products.add("Drink" + i);
		}else
		{
			products.add("error parsing json " + category);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.products, menu);
		return true;
	}

}
