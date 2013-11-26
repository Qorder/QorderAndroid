package com.example.clientalphaprototype;

import java.io.IOException;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.clientalphaprototype.model.ProductMenu;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ScanActivity extends Activity {
	private static final int ZBAR_SCANNER_REQUEST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Disable Strick mode
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		initializeScanButton();
	}

	//Initialize scan button
	void initializeScanButton()
	{
		Button button = (Button) findViewById(R.id.button_scan);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//QrCode Parser
				if (isCameraAvailable()) {
					Intent intent = new Intent(ScanActivity.this,ZBarScannerActivity.class);
					intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});

					startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
				} else {
					Toast.makeText(ScanActivity.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
				}	

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan, menu);
		return true;
	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		// If there is no network it will return null
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// Otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	} 

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {

				// TODO: Uncomment and change link this when WS is finished
				// Make Url request based on QrCode Result
				// String url =("www.ws.gr/menus/business?id=" + data.getStringExtra(ZBarConstants.SCAN_RESULT));

				// Url to make request 
				//TODO:Change url to mockProductMenu to be able to be tested correctly
				String url = ("http://83.212.118.113/mockBussinessJson.json");

				if (isNetworkAvailable()) {
					try {
						//Parse Json to a ProductMenu Object
						ProductMenu productmenu= JsonUtil.<ProductMenu>JsonToPojoParser(url,ProductMenu.class);

						//TODO:Remove this Later - Check Use Only
						//Toast.makeText(ScanActivity.this, a, Toast.LENGTH_SHORT).show();

					} catch (JsonParseException e) {e.printStackTrace();} 
					catch (JsonMappingException e) {e.printStackTrace();}
					catch (ClassNotFoundException e) {e.printStackTrace();}
					catch (IOException e) {e.printStackTrace();}
				}
				else
				{
					Toast.makeText(ScanActivity.this, "Network Connectivity failure", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(this,url, Toast.LENGTH_SHORT).show();

				//TODO:Remove this Later - Check Use Only
				//Toast.makeText(this,"Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();

				//Move to Categories Activity
				Intent categories = new Intent(ScanActivity.this, Categories.class);
				startActivity(categories);

			} else if(resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if(!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}



}
