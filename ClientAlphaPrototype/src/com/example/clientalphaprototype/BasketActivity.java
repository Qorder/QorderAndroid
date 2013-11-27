package com.example.clientalphaprototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.clientalphaprototype.swipe.SwipeDismissListViewTouchListener;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.example.clientalphaprototype.customviews.BasketCustomList;
import com.example.clientalphaprototype.model.BasketProduct;
import com.example.clientalphaprototype.model.OrderHolder;

public class BasketActivity extends Activity {

	ListView listView;
	OrderHolder orderHolder = new OrderHolder();
	List<BasketProduct> order;
	BasketCustomList adapter;
	final String currencySign = "€";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basket);
		
		order = orderHolder.getOrder();

		listView = (ListView) findViewById(R.layout.basket_listview);

		initializeArrayAdapter();

	}

	void initializeArrayAdapter() {

		adapter = new BasketCustomList(this, getProductNames(),getProductNotes(), getProductPrices());

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product = (String) listView.getItemAtPosition(position);
				Intent i = new Intent(getApplicationContext(),
						DetailsActivity.class);
				i.putExtra("product", product);
				startActivity(i);
			}
		});

		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listView,
				new SwipeDismissListViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						return true;
					}

					@Override
					public void onDismiss(ListView listView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							adapter.remove(adapter.getItem(position));
						}
						adapter.notifyDataSetChanged();
					}
				});
		listView.setOnTouchListener(touchListener);
		listView.setOnScrollListener(touchListener.makeScrollListener());
	}

	List<String> getProductNames() {

		List<String> productNames = new ArrayList<String>();
		for (BasketProduct product : order) {
			productNames.add(product.getName());
		}

		return productNames;
	}

	List<String> getProductNotes() {

		List<String> productNotes = new ArrayList<String>();
		for (BasketProduct product : order) {
			productNotes.add(product.getNotes());
		}

		return productNotes;
	}

	List<String> getProductPrices() {
		List<String> productValues = new ArrayList<String>();
		for (BasketProduct product : order) {
			productValues.add(product.getPrice().toString() + currencySign);
		}

		return productValues;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basket, menu);
		return true;
	}


}
