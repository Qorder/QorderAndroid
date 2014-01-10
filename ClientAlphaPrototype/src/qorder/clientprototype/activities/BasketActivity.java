package qorder.clientprototype.activities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import qorder.clientprototype.extensions.BasketCustomList;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.swipe.SwipeDismissListViewTouchListener;
import qorder.clientprototype.threads.AsyncPost;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import qorder.clientprototype.R;

public class BasketActivity extends Activity {

	ListView listView;
	OrderHolder orderHolder = new OrderHolder();
	List<BasketProduct> order;
	BasketCustomList adapter;
	final String currencySign = "€";
	final DecimalFormat priceFormat = new DecimalFormat("###.00");

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
		case R.id.YourOrders:
			Intent ordersIntent = new Intent(this, OrdersActivity.class);
			this.startActivity(ordersIntent);
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

	public void initializeBasket() {
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

	void manipulateEditTextParsing(EditText mEdit) {
		mEdit.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				for (int i = s.length(); i > 0; i--) {

					if (s.subSequence(i - 1, i).toString().equals("\n"))
						s.replace(i - 1, i, "");
				}
			}
		});
	}
	
	void showEditDialog(final int position) {
		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.basket_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(dialogview);

		final EditText userInput = (EditText) dialogview
				.findViewById(R.id.editTextDialogNotes);

		userInput.setText(order.get(position).getNotes());
		manipulateEditTextParsing(userInput);
		
		final NumberPicker numberPicker = (NumberPicker) dialogview
				.findViewById(R.id.numberPicker_basketquantity);
		numberPicker.setMaxValue(20);
		numberPicker.setMinValue(1);
		numberPicker.setValue(order.get(position).getQuantity());
		numberPicker.setWrapSelectorWheel(true);
	

		alertDialogBuilder
				.setCancelable(true)
				.setPositiveButton(
						getResources().getString(
								R.string.text_view_basketdialog),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(getApplicationContext(),
										DetailsActivity.class);
								i.putExtra("product", order.get(position)
										.getUri());
								startActivity(i);
							}
						})
				.setNegativeButton(
						getResources().getString(
								R.string.text_done_basketdialog),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								order.get(position).setQuantity(numberPicker.getValue());
								order.get(position).setNotes(
										userInput.getText().toString());
								order.get(position)
										.setQuantity(numberPicker.getValue());
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
								R.string.text_submit_basketactivity)
								+ " " + getTotalPrice() + currencySign);

		builder.setCancelable(true).setNegativeButton(
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
						AsyncPost post = new AsyncPost(BasketActivity.this);
						BasketProduct[] basketProducts = order
								.toArray(new BasketProduct[order.size()]);
						post.execute(basketProducts);
						// OrderHolder.reset();
						// initializeBasket();
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	void initializeArrayAdapter() {

		adapter = new BasketCustomList(this,orderHolder.getProductNames(order),
				orderHolder.getProductNotes(order), orderHolder.getProductPrices(order,priceFormat,currencySign));
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
							adapter.setItems(orderHolder.getProductNames(order),
									orderHolder.getProductNotes(order), orderHolder.getProductPrices(order,priceFormat,currencySign));
						}
						adapter.notifyDataSetChanged();
					}
				});
		listView.setOnTouchListener(touchListener);
		listView.setOnScrollListener(touchListener.makeScrollListener());
	}

	String getTotalPrice() {
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (BasketProduct product : order) {
			BigDecimal price = product.getPrice().multiply(
					BigDecimal.valueOf((double) product.getQuantity()));
			totalPrice = totalPrice.add(price);
		}
		return priceFormat.format(totalPrice);
	}

}
