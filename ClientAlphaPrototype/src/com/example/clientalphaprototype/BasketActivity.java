package com.example.clientalphaprototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.clientalphaprototype.swipe.SwipeDismissListViewTouchListener;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.example.clientalphaprototype.adapters.BasketCustomList;
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

		initializeBasket();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initializeBasket();
	}

	void initializeBasket() {
		order = orderHolder.getOrder();

		listView = (ListView) findViewById(R.id.basket_list);

		initializeActionBar();
		initializeArrayAdapter();
	}

	void initializeActionBar() {
		ActionBar actionBar = getActionBar();

		actionBar.setCustomView(R.layout.actionbar_submit_view);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.getCustomView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				;
				// TODO: send json
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);

	}

	void initializeArrayAdapter() {

		adapter = new BasketCustomList(this, getProductNames(),
				getProductNotes(), getProductPrices());

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						DetailsActivity.class);
				i.putExtra("product", order.get(position).getUri());
				i.putExtra("notes", order.get(position).getNotes());
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
							order.remove(position);
							adapter.setItems(getProductNames(), getProductNotes(), getProductPrices());
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId()) {
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
