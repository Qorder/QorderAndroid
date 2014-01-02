package qorder.clientprototype.activities;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.extensions.DetailsCustomList;
import qorder.clientprototype.images.ImageLoader;
import qorder.clientprototype.jsonparsers.DetailedProductJsonParser;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.DetailsHolder;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.util.AndroidUtil;
import qorder.clientprototype.util.NetworkUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import qorder.clientprototype.R;

public class DetailsActivity extends Activity {

	OrderHolder orderHolder = new OrderHolder();
	BasketProduct product;
	final String currencySign = "€";
	String notes;
	ListView details_listview;
	DetailsCustomList adapter;
	ImageLoader imageLoader;
	final DecimalFormat priceFormat = new DecimalFormat("###.00");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetails);

		imageLoader = new ImageLoader(this.getApplicationContext());
		details_listview = (ListView) findViewById(R.id.listview_details);
		product = new BasketProduct();
		notes = " ";
		Bundle extras = getIntent().getExtras();

		try {
			parseJson(extras.getString("product"));
			product.setUri(extras.getString("product"));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		initializeActionBar();

		initializeView();
		initializeButtons();

	}

	private void initializeButtons() {
		Button button_addToBasket = (Button) findViewById(R.id.button_addToBasket);

		button_addToBasket.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// EditText mEdit = (EditText)
				// findViewById(R.id.editText_notes);
				orderHolder.add(new BasketProduct(product.getId(), product
						.getName(), product.getPrice(), getSelectedDetails(),
						notes, product.getUri(), product.getQuantity()));
				setBasketTitle();
				/*
				 * Toast.makeText( DetailsActivity.this, "added to cart",
				 * Toast.LENGTH_SHORT).show();
				 */
			}
		});

		Button button_edit_notes = (Button) findViewById(R.id.button_edit_notes);

		button_edit_notes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showEditDialog();
			}
		});

		Button button_quantity = (Button) findViewById(R.id.button_quantity);

		button_quantity.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showQuantityDialog();
			}
		});
		setQuantityButtonTitle();
	}

	void setQuantityButtonTitle() {
		Button button_quantity = (Button) findViewById(R.id.button_quantity);
		button_quantity.setText("x " + product.getQuantity());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details, menu);
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

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();
		setActionbarTitle();
	}

	String getSelectedDetails() {
		StringBuffer responseText = new StringBuffer();

		List<DetailsHolder> details = adapter.list;
		for (int i = 0; i < details.size(); i++) {
			DetailsHolder holder = details.get(i);
			if (holder.isSelected()) {
				responseText.append(holder.getName() + "  ");
			}
		}
		return responseText.toString();
	}

	void initializeDetailsArrayAdapter(List<DetailsHolder> details) {
		adapter = new DetailsCustomList(this, details);
		details_listview.setAdapter(adapter);
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

	void initializeView() {
		// EditText mEdit = (EditText) findViewById(R.id.editText_notes);

		// if (notes != null)
		// mEdit.setText(notes);
		// else
		// mEdit.setText(getResources().getString(R.string.title_notes_activity_productdetails));

		TextView title = (TextView) findViewById(R.id.textView_title);
		title.setText(product.getName());

		TextView price = (TextView) findViewById(R.id.price_txt);
		price.setText(priceFormat.format(product.getPrice()) + currencySign);

		List<DetailsHolder> detailsList = new ArrayList<DetailsHolder>();
		if (product.getAttributes() != null) {
			String[] details = product.getAttributes().split("-");

			for (String string : details) {
				detailsList.add(new DetailsHolder(string));
			}

		}
		initializeDetailsArrayAdapter(detailsList);

	}

	void loadImageFrom(String url) {
		imageLoader.clearCache();
		ImageView image = (ImageView) findViewById(R.id.products_imageview);
		imageLoader.DisplayImage(url, image);
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

	void parseJson(String url) throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		if (AndroidUtil.isNetworkAvailable(this)) {
			try {
				DetailedProductJsonParser jsonParser = new DetailedProductJsonParser();
				JSONObject json = NetworkUtil.requestJsonObject(url);
				try {
					//Large image
					// loadImageFrom("http://www.pleiade.org/images/hubble-m45_large.jpg");
					loadImageFrom(json.getString("imageRequestURI"));
				} catch (Exception e) {
					Log.e("Error parsing image in details activity:",
							e.getMessage());
				}

				product = jsonParser.parse(json);
			} catch (Exception e) {
				Log.e("Error parsing json in details activity:", e.getMessage());
				Toast.makeText(
						this,
						getResources().getString(
								R.string.text_error_fetch_menu_mock),
						Toast.LENGTH_SHORT).show();
				createMockProduct();
			}

		} else {
			Toast.makeText(this,
					getResources().getString(R.string.text_error_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	void showEditDialog() {

		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.editnotes_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(dialogview);

		final EditText userInput = (EditText) dialogview
				.findViewById(R.id.editTextDialogNotes);
		manipulateEditTextParsing(userInput);
		userInput.setText(notes);

		alertDialogBuilder.setCancelable(false).setPositiveButton(
				getResources().getString(R.string.text_done_basketdialog),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						notes = userInput.getText().toString();
						TextView description = (TextView) findViewById(R.id.textview_notes);
						description.setText(notes);
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}

	void showQuantityDialog() {

		LayoutInflater li = LayoutInflater.from(this);
		View dialogview = li.inflate(R.layout.numberpicker_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(dialogview);

		final NumberPicker numberPicker = (NumberPicker) dialogview
				.findViewById(R.id.numberPicker_quantity);
		numberPicker.setMaxValue(20);
		numberPicker.setMinValue(1);
		numberPicker.setValue(product.getQuantity());
		numberPicker.setWrapSelectorWheel(true);
		numberPicker
				.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						product.setQuantity(newVal);
					}
				});

		alertDialogBuilder.setCancelable(false).setPositiveButton(
				getResources().getString(R.string.text_done_basketdialog),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						setQuantityButtonTitle();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}

	void createMockProduct() {
		product = new BasketProduct(1, "Product", BigDecimal.valueOf(1.99),
				"attribute", "note", "example uri", 1);
	}

}
