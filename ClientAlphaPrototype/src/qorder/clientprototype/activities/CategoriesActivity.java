package qorder.clientprototype.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.extensions.CategoriesCustomList;
import qorder.clientprototype.jsonparsers.CategoryJsonParser;
import qorder.clientprototype.model.Category;
import qorder.clientprototype.model.OrderHolder;
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

public class CategoriesActivity extends Activity {

	ListView categories_listView;
	List<Category> categories;
	static String categoriesUrl = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

		categories_listView = (ListView) findViewById(R.id.categories_listview);
		categories = new ArrayList<Category>();
		Bundle extras = getIntent().getExtras();

		try {
			CategoriesActivity.categoriesUrl = extras
					.getString("categoriesUrl");
			parseJson(categoriesUrl);
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
		initializeArrayAdapter();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();
		setActionbarTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.categories, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.ScanAgain:
			Intent scanIntent = new Intent(this, ScanActivity.class);
			this.startActivity(scanIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	void parseJson(String url) throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		if (AndroidUtil.isNetworkAvailable(this)) {
			try {
				CategoryJsonParser jsonParser = new CategoryJsonParser();
				JSONObject json = HttpRequest.requestJsonObject(url);

				categories = jsonParser.parse(json);

				OrderHolder.setBusinessName(json.getString("businessName"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Toast.makeText(this,
						"Failed to find menu. Creating mock categories",
						Toast.LENGTH_SHORT).show();
				createMockCategories();
			}
		} else {
			Toast.makeText(this, "Network Connectivity failure",
					Toast.LENGTH_SHORT).show();
		}
	}

	// TODO: remove after debugging
	void createMockCategories() {
		categories.add(new Category(1, "Food", "example uri"));
		categories.add(new Category(2, "Drinks", "example uri"));
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

	void setActionbarTitle() {
		TextView activityTitle = (TextView) findViewById(R.id.title);
		activityTitle.setText(OrderHolder.getBusinessName());
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

	void initializeArrayAdapter() {
		ArrayAdapter<String> adapter = new CategoriesCustomList(this,
				getCategoryNames());

		categories_listView.setAdapter(adapter);

		categories_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						ProductsActivity.class);
				i.putExtra("category", categories.get(position).getUri());
				startActivity(i);
			}
		});
	}

	List<String> getCategoryNames() {

		List<String> categoryNames = new ArrayList<String>();
		for (Category category : categories) {
			categoryNames.add(category.getName());
		}

		return categoryNames;
	}

}
