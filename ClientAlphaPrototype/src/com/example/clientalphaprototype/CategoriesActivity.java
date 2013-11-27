package com.example.clientalphaprototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesActivity extends Activity {
	ListView categories_listView;
	List<Category> categories;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

		categories_listView = (ListView) findViewById(R.id.categories_listview);
		categories = new ArrayList<Category>();
		
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
		parseJson(extras.getString("categoriesUrl"));
		}
		else{
			parseJson("mockURL");		
		}
			
		initializeActionBar();
		initializeArrayAdapter();
	}

	void parseJson(String url)
	{
		if (isNetworkAvailable()) {
			try {
				categories= JsonUtil.<List<Category>>JsonToPojoParser(url,Category.class);

			}/* catch (JsonParseException e) {e.printStackTrace();} 
			catch (JsonMappingException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
			*/
			catch(Exception e)
			{
				
				createMockCategories();
			}
		}
		else
		{
			createMockCategories();
			Toast.makeText(this, "Network Connectivity failure", Toast.LENGTH_SHORT).show();
		}
	}
	
	//TODO: remove after debugging
	void createMockCategories()
	{
		categories.add(new Category(1,"Food"));
		categories.add(new Category(2,"Drinks"));
	}
	
	void initializeActionBar()
	{
		  ActionBar actionBar = getActionBar();

		    actionBar.setCustomView(R.layout.actionbar_view);
		    actionBar.setDisplayShowCustomEnabled(true);
		    actionBar.getCustomView().setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View view) {
					Intent i = new Intent(getApplicationContext(), BasketActivity.class);
					startActivity(i);
		        }
		    });
		    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
	}
	
	void initializeArrayAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1,getCategoryNames());

		categories_listView.setAdapter(adapter);

		categories_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), ProductsActivity.class);
				i.putExtra("category",categories.get(position).getId());
				startActivity(i);
			}
		});
	}

	List<String> getCategoryNames() {
		
		List<String> categoryNames = new ArrayList<String>();
		  for (Category category : categories) {
              categoryNames.add(category.getName());
          }
		  
		  return categoryNames;
	}
	
	//Helper	
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		// If there is no network it will return null
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// Otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}

}
