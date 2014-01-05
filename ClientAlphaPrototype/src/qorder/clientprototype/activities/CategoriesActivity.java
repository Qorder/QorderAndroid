package qorder.clientprototype.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.R;
import qorder.clientprototype.extensions.CategoriesCustomList;
import qorder.clientprototype.jsonparsers.CategoryJsonParser;
import qorder.clientprototype.model.Category;
import qorder.clientprototype.model.OrderHolder;
import qorder.clientprototype.util.AndroidUtil;
import qorder.clientprototype.util.NetworkUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class CategoriesActivity extends Activity {

	ListView categories_listView;
	List<Category> categories;
	static String categoriesUrl = null;
	final boolean useMocks = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

		categories_listView = (ListView) findViewById(R.id.categories_listview);
		categories = new ArrayList<Category>();
		Bundle extras = getIntent().getExtras();

		try {
			if (extras != null)
			{
				String info = extras
						.getString("initialInfo");
				String[] additionalInfo = info.split("_");
				OrderHolder.setTableNumber(additionalInfo[0]);
				OrderHolder.setWSPostUrI(additionalInfo[1]); 
				CategoriesActivity.categoriesUrl = additionalInfo[2];
			}
			if(useMocks)
				createMockCategories();
			else
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
		case R.id.YourOrders:
			Intent ordersIntent = new Intent(this, OrdersActivity.class);
			this.startActivity(ordersIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	void parseJson(final String url) throws ClientProtocolException,
			IOException, ClassNotFoundException, JSONException {

		if (AndroidUtil.isNetworkAvailable(this) && url!=null) {
			final ProgressDialog progress = new ProgressDialog(this);
			progress.setButton(DialogInterface.BUTTON_NEGATIVE, "nevermind", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			        errorFetchingMenu(null);
			    }
			});
			progress.setTitle(getResources().getString(R.string.title_fetch_menu_dialog));
			progress.setMessage(getResources().getString(R.string.text_fetch_menu_dialog));
			progress.show();
			
			new Thread(new Runnable() {
				@Override
				public void run() {		
					try {
						CategoryJsonParser jsonParser = new CategoryJsonParser();
						JSONObject json = NetworkUtil.requestJsonObject(url);

						categories = jsonParser.parse(json);
						
						OrderHolder.setBusinessName(json
								.getString("businessName"));
						
						
						//Testing the animation
						/*long start = System.currentTimeMillis();
						long end = start + 5*1000;
						while (System.currentTimeMillis() < end)
						{
						  ;
						}*/
						//createMockCategories();

					} catch (Exception e) {
						errorFetchingMenu(getResources().getString(R.string.text_error_fetch_menu_mock));
					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							initializeActionBar();
							initializeArrayAdapter();
							progress.dismiss();
						}
					});
				}
			}).start();
		} else {
			errorFetchingMenu(getResources().getString(R.string.text_error_network));
		}
	}

	void errorFetchingMenu(String message)
	{
		Intent i = new Intent(getApplicationContext(),
				ScanActivity.class);
		i.putExtra("error", message);
		startActivity(i);
	}
	
	// TODO: remove after debugging
	void createMockCategories() {
		OrderHolder.setBusinessName("business");
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
			testButton.setText(getResources().getString(
					R.string.text_basket_multiplier_productsactivity)
					+ basketSum);
		} else {

			testButton.setText(getResources().getString(
					R.string.text_basket_productsactivity));
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
