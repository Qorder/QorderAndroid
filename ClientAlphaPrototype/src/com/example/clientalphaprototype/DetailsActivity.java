package com.example.clientalphaprototype;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.clientalphaprototype.model.BasketProduct;
import com.example.clientalphaprototype.model.DetailedProduct;
import com.example.clientalphaprototype.model.OrderHolder;
import com.example.clientalphaprototype.model.Product;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {

	OrderHolder orderHolder = new OrderHolder();
	DetailedProduct product;
	final String wsURL = "ws/menus/category?id=";
	final String currencySign = "€";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			long id = extras.getLong("product");
			parseJson(wsURL + id);
		} else
			parseJson("mockURL");
		
		initializeActionBar();
		
		Button button_addToBasket = (Button)findViewById(R.id.button_addToBasket);

		button_addToBasket.setOnClickListener(new OnClickListener() {
		    public void onClick(View v)
		    {
		        orderHolder.add(product);
		    } 
		});
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
	
	void parseJson(String url) {
		if (isNetworkAvailable()) {
			try {
				product = JsonUtil.<DetailedProduct> JsonToPojoParser(url,
						DetailedProduct.class);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// TODO: remove after debugging
			List<String> attributes = new ArrayList<String>();
			attributes.add("Some attribute");
			product = new DetailedProduct(1,"Product",BigDecimal.valueOf(1.99),"your notes here",attributes);

			Toast.makeText(this, "Network Connectivity failure",
					Toast.LENGTH_SHORT).show();
		}
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
