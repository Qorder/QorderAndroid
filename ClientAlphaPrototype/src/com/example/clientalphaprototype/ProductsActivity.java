package com.example.clientalphaprototype;

import com.example.clientalphaprototype.jsonparsers.ProductJsonParser;
import com.example.clientalphaprototype.model.OrderHolder;
import com.example.clientalphaprototype.model.Product;
import com.example.clientalphaprototype.util.HttpRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.clientalphaprototype.adapters.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductsActivity extends Activity {

	final String currencySign = "€";
	ListView products_listview;
	List<Product> products;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		products_listview = (ListView) findViewById(R.id.products_listview);

		products = new ArrayList<Product>();
		initializeActionBar();
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			try {
				parseJson(extras.getString("category"));
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
		} else
			try {
				parseJson("mockURL");
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

		initializeArrayAdapter();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();
		setActionbarTitle();
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
	
	void setActionbarTitle()
	{
		TextView activityTitle = (TextView)findViewById(R.id.title);
		activityTitle.setText(OrderHolder.getBusinessName());
	}

	void initializeActionBar() {
		ActionBar actionBar = getActionBar();

		actionBar.setCustomView(R.layout.actionbar_view);
		actionBar.setDisplayShowCustomEnabled(true);
		setBasketTitle();
		
		Button testButton = (Button) findViewById(R.id.basket_button);
		testButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						BasketActivity.class);
				startActivity(i);
			}
		});
		setActionbarTitle();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	void initializeArrayAdapter() {

		ArrayAdapter<String> adapter = new ProductCustomList(this,
				getProductNames(), getProductValues());

		products_listview.setAdapter(adapter);

		products_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						DetailsActivity.class);
				i.putExtra("product", products.get(position).getUri());
				startActivity(i);
			}
		});
	}

	List<String> getProductNames() {

		List<String> productNames = new ArrayList<String>();
		for (Product product : products) {
			productNames.add(product.getName());
		}

		return productNames;
	}

	List<String> getProductValues() {
		List<String> productValues = new ArrayList<String>();
		for (Product product : products) {
			productValues.add(product.getPrice().toString() + currencySign);
		}

		return productValues;
	}

	// Helper
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// If there is no network it will return null
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// Otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	void parseJson(String url) throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		if (isNetworkAvailable()) {
			try {
				ProductJsonParser jsonParser = new ProductJsonParser();
				JSONObject json = HttpRequest.requestJsonObject(url);

				products = jsonParser.parse(json);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Toast.makeText(this,
						"Failed to find products. Creating mock products",
						Toast.LENGTH_SHORT).show();
				createMockProducts();
			}
		} else {
			// createMockProducts();
			Toast.makeText(this, "Network Connectivity failure",
					Toast.LENGTH_SHORT).show();
		}
	}

	// TODO: remove after debugging
	void createMockProducts() {

		products.add(new Product(1, "Food1", BigDecimal.valueOf(1.99),
				"example uri"));
		products.add(new Product(2, "Food2", BigDecimal.valueOf(2.99),
				"example uri"));
		products.add(new Product(3, "Food3", BigDecimal.valueOf(5.99),
				"example uri"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.products, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.Menu:
			Intent menuIntent = new Intent(this, CategoriesActivity.class);
			this.startActivity(menuIntent);
			break;
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
