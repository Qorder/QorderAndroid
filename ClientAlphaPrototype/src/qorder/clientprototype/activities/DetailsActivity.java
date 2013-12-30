package qorder.clientprototype.activities;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.extensions.DetailsCustomList;
import qorder.clientprototype.extensions.DetailsImageAdapter;
import qorder.clientprototype.jsonparsers.DetailedProductJsonParser;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.model.DetailedProduct;
import qorder.clientprototype.model.DetailsHolder;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.util.AndroidUtil;
import qorder.clientprototype.util.NetworkUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import qorder.clientprototype.R;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SuppressWarnings("deprecation")
public class DetailsActivity extends Activity {

	OrderHolder orderHolder = new OrderHolder();
	DetailedProduct product;
	final String currencySign = "€";
	ImageView currentImage;
	String notes;
	List<Integer> imgIds;
	ListView details_listview;
	DetailsCustomList adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetails);

		details_listview = (ListView) findViewById(R.id.listview_details);
		product = new DetailedProduct();
		notes = null;
		Bundle extras = getIntent().getExtras();

		imgIds = new ArrayList<Integer>();

		try {
			notes = extras.getString("notes");
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

		Button button_addToBasket = (Button) findViewById(R.id.button_addToBasket);

		button_addToBasket.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText mEdit = (EditText) findViewById(R.id.editText_notes);
				orderHolder.add(new BasketProduct(product.getId(), product
						.getName(), product.getPrice(), getSelectedDetails() + mEdit.getText().toString(), product.getUri()));
				setBasketTitle();

			}
		});
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
				responseText.append(holder.getName()+ "  ");
			}
		}
		return responseText.toString();
	}

	void initializeDetailsArrayAdapter(List<DetailsHolder> details) {
		adapter = new DetailsCustomList(this, details);

		details_listview.setAdapter(adapter);

	}

	// TODO: encapsulate this in an image parsing and loading async class and
	// share it with the
	// gallery widget
	void initializeImages() {
		for (int i = 0; i < 3; i++) {
			imgIds.add(R.drawable.image1);
			imgIds.add(R.drawable.image2);
			imgIds.add(R.drawable.image3);
		}
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
		EditText mEdit = (EditText) findViewById(R.id.editText_notes);
		manipulateEditTextParsing(mEdit);

		initializeImages();

		// if (notes != null)
		// mEdit.setText(notes);
		// else
		// mEdit.setText(getResources().getString(R.string.title_notes_activity_productdetails));

		TextView title = (TextView) findViewById(R.id.textView_title);
		title.setText(product.getName());

		TextView price = (TextView) findViewById(R.id.price_txt);
		price.setText(product.getPrice().toString() + currencySign);

		// TextView description = (TextView)
		// findViewById(R.id.textView_description);
		// TODO: review
		// description.setText(product.getDetails().replace("-", "\n"));

		String[] details = product.getDetails().split("-");
		List<DetailsHolder> detailsList = new ArrayList<DetailsHolder>();
		for (String string : details) {
			detailsList.add(new DetailsHolder(string));
		}

		initializeDetailsArrayAdapter(detailsList);

		Gallery gallery = (Gallery) findViewById(R.id.products_gallery);
		gallery.setSpacing(1);
		gallery.setAdapter(new DetailsImageAdapter(this));
		currentImage = (ImageView) findViewById(R.id.products_imageview);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				currentImage.setImageResource(imgIds.get(position));
			}
		});

		// TODO: review
		// ImageView img = (ImageView) findViewById(R.id.product_Img);
		// img.setImageResource("uri");

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

				product = jsonParser.parse(json);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
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

	void createMockProduct() {
		product = new DetailedProduct(
				1,
				"Product",
				BigDecimal.valueOf(1.99),
				"your notes here",
				"a description goes here \nnow \ntesting  \nscrollview \n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\nif you are reading this then it works",
				"example uri");
	}

}
