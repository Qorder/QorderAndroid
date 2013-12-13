package com.example.clientalphaprototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.clientalphaprototype.adapters.CategoriesCustomList;
import com.example.clientalphaprototype.jsonparsers.CategoryJsonParser;
import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.model.OrderHolder;
import com.example.clientalphaprototype.util.HttpRequest;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesActivity extends Activity {
	ListView categories_listView;
	List<Category> categories;
	static String categoriesUrl = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

		categories_listView = (ListView) findViewById(R.id.categories_listview);
		categories = new ArrayList<Category>();

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			try {
				this.categoriesUrl=extras.getString("categoriesUrl");
				parseJson(categoriesUrl);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				parseJson(categoriesUrl);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		initializeActionBar();
		initializeArrayAdapter();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();

	}

	void parseJson(String url) throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		if (isNetworkAvailable()) {
			try {
				CategoryJsonParser jsonParser = new CategoryJsonParser();
				JSONObject json = HttpRequest.requestJsonObject(url);

				categories = jsonParser.parse(json);

				OrderHolder order = new OrderHolder();
				order.setBusinessName(json.getString("businessName"));

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e)
			{
				Toast.makeText(this, "Failed to find menu. Creating mock categories",
						Toast.LENGTH_SHORT).show();
				createMockCategories();
			}
		} else {
			//createMockCategories();
			Toast.makeText(this, "Network Connectivity failure",
					Toast.LENGTH_SHORT).show();
		}
	}

	// TODO: remove after debugging
	void createMockCategories() {
		categories.add(new Category(1, "Food", "example uri"));
		categories.add(new Category(2, "Drinks", "example uri"));
	}

	void initializeActionBar() {
		ActionBar actionBar = getActionBar();

		actionBar.setCustomView(R.layout.actionbar_view);
		actionBar.setDisplayShowCustomEnabled(true);

		setBasketTitle();

		actionBar.getCustomView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						BasketActivity.class);
				startActivity(i);
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
	}

	void setBasketTitle() {
		Button testButton = (Button) findViewById(R.id.basket_button);
		int basketSum = OrderHolder.count();
		if (basketSum != 0) {
			testButton.setText("Basket x" + basketSum);
		} else {

			testButton.setText("Basket");
		}

	}

	void initializeArrayAdapter() {
		ArrayAdapter<String> adapter = new CategoriesCustomList(this,getCategoryNames());

		categories_listView.setAdapter(adapter);

		categories_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						ProductsActivity.class);
				i.putExtra("category", categories.get(position).getUri());
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

	// Helper
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.categories, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.ScanAgain:
			Intent scanIntent = new Intent(this, ScanActivity.class);
			this.startActivity(scanIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

}
