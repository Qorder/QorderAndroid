package qorder.clientprototype.activities;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.extensions.ProductCustomList;
import qorder.clientprototype.jsonparsers.ProductJsonParser;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.model.Product;
import qorder.clientprototype.util.AndroidUtil;
import qorder.clientprototype.util.HttpRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientalphaprototype.R;

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

		try {
			parseJson(extras.getString("category"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		initializeArrayAdapter();
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

	void setActionbarTitle() {
		TextView activityTitle = (TextView) findViewById(R.id.title);
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

	void parseJson(String url) throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		if (AndroidUtil.isNetworkAvailable(this)) {
			try {
				ProductJsonParser jsonParser = new ProductJsonParser();
				JSONObject json = HttpRequest.requestJsonObject(url);

				products = jsonParser.parse(json);

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

}
