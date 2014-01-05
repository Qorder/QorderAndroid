package qorder.clientprototype.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import qorder.clientprototype.R;
import qorder.clientprototype.extensions.BasketCustomList;
import qorder.clientprototype.extensions.OrdersCustomList;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.util.AsyncOrderStatusCheck;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OrdersActivity extends Activity {

	OrderHolder orderHolder;
	ListView listView;
	int selectedPos;
	AsyncOrderStatusCheck statusCheck;
	Executor executor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		orderHolder = new OrderHolder();
		listView = (ListView) findViewById(R.id.orders_listview);

		if (OrderHolder.hasOrders()) {
			initializeActivityArrayAdapter();
		}
		initializeActionBar();
		checkForStatusChange(this);

	}

	@Override
	public void onPause() {
		super.onPause();
		((ExecutorService) executor).shutdown()	;
	}

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();
		setActionbarTitle();
		checkForStatusChange(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.orders, menu);
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

	void checkForStatusChange(Activity activity) {
		executor =Executors.newSingleThreadExecutor();
		statusCheck = new AsyncOrderStatusCheck(this);
		List<String> statusCheckURIs = new ArrayList<String>();
		statusCheckURIs = OrderHolder.getOrderStatusURIs();
		statusCheck.executeOnExecutor(executor,statusCheckURIs.toArray(new String[statusCheckURIs
				.size()]));
	}

	void setActionbarTitle() {
		TextView activityTitle = (TextView) findViewById(R.id.title);
		activityTitle.setText(OrderHolder.getBusinessName());
	}

	void setBasketTitle() {
		Button testButton = (Button) findViewById(R.id.basket_button);
		int basketSum = OrderHolder.count();
		if (basketSum != 0) {
			testButton.setText(getResources().getString(
					R.string.text_basket_multiplier_productsactivity)
					+ basketSum);
		} else {
			testButton.setText(getResources().getString(
					R.string.text_basket_productsactivity));
		}

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

	public void initializeActivityArrayAdapter() {

		OrdersCustomList adapter = new OrdersCustomList(this,
				orderHolder.getOrdersID(), orderHolder.getOrdersStatus());

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showOrderDialog(position);
			}
		});
	}

	void initializeDialogArrayAdapter(int pos, ListView view) {
		selectedPos = pos;
		BasketCustomList adapter = new BasketCustomList(this,
				orderHolder.getProductNames(orderHolder.getOrderAt(pos)),
				orderHolder.getProductNotes(orderHolder.getOrderAt(pos)),
				orderHolder.getProductPrices(orderHolder.getOrderAt(pos),
						new DecimalFormat("###.00"), "€"));

		view.setAdapter(adapter);

		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						DetailsActivity.class);
				i.putExtra("product",
						orderHolder.getOrderAt(selectedPos).get(position)
								.getUri());
				startActivity(i);
			}
		});

	}

	void showOrderDialog(final int position) {
		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.orders_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(dialogview);
		initializeDialogArrayAdapter(position,
				(ListView) dialogview.findViewById(R.id.order_listview));

		alertDialogBuilder.setCancelable(true).setPositiveButton("ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}

}
