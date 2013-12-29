package qorder.clientprototype.activities;

import java.util.ArrayList;
import java.util.List;

import qorder.clientprototype.extensions.BasketCustomList;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.swipe.SwipeDismissListViewTouchListener;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.clientalphaprototype.R;

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
		setActionbarTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basket, menu);
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

	void setActionbarTitle() {
		TextView activityTitle = (TextView) findViewById(R.id.title);
		activityTitle.setText(OrderHolder.getBusinessName());
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

		Button testButton = (Button) findViewById(R.id.submit_button);
		testButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (order.size() > 0)
					showSubmitDialog();
			}
		});
		setActionbarTitle();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

	}

	void showEditDialog(final int position) {
		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.basket_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(dialogview);

		final EditText userInput = (EditText) dialogview
				.findViewById(R.id.editTextDialogNotes);

		userInput.setText(order.get(position).getNotes());

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton(
						getResources().getString(
								R.string.text_view_basketdialog),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(getApplicationContext(),
										DetailsActivity.class);
								i.putExtra("product", order.get(position)
										.getUri());
								i.putExtra("notes", order.get(position)
										.getNotes());
								startActivity(i);
							}
						})
				.setNegativeButton(
						getResources().getString(
								R.string.text_done_basketdialog),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								order.get(position).setNotes(
										userInput.getText().toString());
								initializeArrayAdapter();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}

	void showSubmitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(
				getResources().getString(R.string.text_ready_basketactivity))
				.setTitle(
						getResources().getString(
								R.string.text_submit_basketactivity));

		builder.setNegativeButton(
				getResources().getString(R.string.text_no_basketactivity),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.setPositiveButton(
				getResources().getString(R.string.text_yes_basketactivity),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// send order async here
						orderHolder.reset();
						initializeBasket();
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	void initializeArrayAdapter() {

		adapter = new BasketCustomList(this, getProductNames(),
				getProductNotes(), getProductPrices());

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showEditDialog(position);
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
							adapter.setItems(getProductNames(),
									getProductNotes(), getProductPrices());
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
}
