package com.example.clientalphaprototype;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.clientalphaprototype.adapters.DetailsImageAdapter;
import com.example.clientalphaprototype.model.BasketProduct;
import com.example.clientalphaprototype.model.DetailedProduct;
import com.example.clientalphaprototype.model.OrderHolder;
import com.example.clientalphaprototype.model.Product;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {

	OrderHolder orderHolder = new OrderHolder();
	DetailedProduct product;
	final String currencySign = "€";
	ImageView currentImage;
	String notes;
	List<Integer> imgIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetails);

		notes = null;
		Bundle extras = getIntent().getExtras();

		imgIds = new ArrayList<Integer>();

		if (extras != null) {
			notes = extras.getString("notes");
			parseJson(extras.getString("product"));
		} else
			parseJson("mockURL");

		initializeActionBar();

		// TODO: uncomment when WS is ready
		initializeView();

		Button button_addToBasket = (Button) findViewById(R.id.button_addToBasket);

		button_addToBasket.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText mEdit = (EditText) findViewById(R.id.editText_notes);
				orderHolder.add(new BasketProduct(product.getId(), product
						.getName(), product.getPrice(), mEdit.getText()
						.toString(), product.getUri()));
				setBasketTitle();
			}
		});
	}

	//TODO: encapsulate this in an image parsing class and share it with the gallery widget
	void initializeImages() {
    	for(int i=0;i<3;i++)
    	{
    		imgIds.add(R.drawable.image1);
			imgIds.add(R.drawable.image2);
			imgIds.add(R.drawable.image3);
    	}
	}

	@SuppressWarnings("deprecation")
	void initializeView() {
		EditText mEdit = (EditText) findViewById(R.id.editText_notes);

		initializeImages();
		
		if (notes != null)
			mEdit.setText(notes);
		else
			mEdit.setText("your notes here");

		TextView title = (TextView) findViewById(R.id.textView_title);
		title.setText(product.getName());

		TextView price = (TextView) findViewById(R.id.price_txt);
		price.setText(product.getPrice().toString() + currencySign);

		TextView description = (TextView) findViewById(R.id.textView_description);
		// TODO: review
		description.setText(product.getAttributes().get(0));
		
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

	@Override
	protected void onResume() {
		super.onResume();
		setBasketTitle();
	}

	void setBasketTitle() {
		Button testButton = (Button) findViewById(R.id.basket_button);
		int basketSum = OrderHolder.count();
		if (basketSum != 0) {
			testButton.setText("Basket x" + basketSum);
		}
	}

	void initializeActionBar() {
		ActionBar actionBar = getActionBar();

		actionBar.setCustomView(R.layout.actionbar_view);
		actionBar.setDisplayShowCustomEnabled(true);
		setBasketTitle();
		actionBar.getCustomView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						BasketActivity.class);
				startActivity(i);
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
	}

	// Helper
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// If there is no network it will return null
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// Otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	void parseJson(String url) {
		if (isNetworkAvailable()) {
			try {
				product = JsonUtil.<DetailedProduct> JsonToPojoParser(url,
						DetailedProduct.class);

			} /*
			 * catch (JsonParseException e) { e.printStackTrace(); } catch
			 * (JsonMappingException e) { e.printStackTrace(); } catch
			 * (ClassNotFoundException e) { e.printStackTrace(); } catch
			 * (IOException e) { e.printStackTrace(); }
			 */
			catch (Exception e) {
				createMockProduct();
			}
		} else {
			// TODO: remove after debugging

			createMockProduct();
			Toast.makeText(this, "Network Connectivity failure",
					Toast.LENGTH_SHORT).show();
		}
	}

	void createMockProduct() {
		List<String> attributes = new ArrayList<String>();
		attributes.add("A product's description goes here \n now \n testing \n if \n scrollView \n works \n..\n..\n..\n..\n..\n..\n ..\n..\nif you are reading this then it works!\n");

		product = new DetailedProduct(1, "Product", BigDecimal.valueOf(1.99),
				"your notes here", attributes, "example uri");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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
