package com.example.clientalphaprototype;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import net.sourceforge.zbar.Symbol;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.clientalphaprototype.model.Business;
import com.example.clientalphaprototype.model.ProductMenu;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
				//TODO:Create request from qr id e.gwww.ws.gr/menus/business?id=3

					//JsonParser(url);

				//QR Parser
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
	    // if there is no network it will return null
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 

	public boolean JsonParser(String url) {
		ObjectMapper mapper = new ObjectMapper(); 
		
		if (isNetworkAvailable()) {
			try {	
					URL jsonurl = new URL(url);
					//Business pmenu = new Business();
					Business pmenu = mapper.readValue(jsonurl, Business.class);
					
					
					/*//TEST-Get name into a string
					String a=pmenu.getName();
					//TEST- check if it's correctly set
					Toast.makeText(ScanActivity.this,a , Toast.LENGTH_SHORT).show();*/
					return true;
				} 
			catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		else
		{
			Toast.makeText(ScanActivity.this, "Network Connectivity failure", Toast.LENGTH_SHORT).show();
			return false;
		}
		return false;
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {

                	//String url =("www.ws.gr/menus/business?id=" + data.getStringExtra(ZBarConstants.SCAN_RESULT));
                	// url to make request
                	String url = ("http://83.212.118.113/mockBussinessJson.json");
					JsonParser(url);
					Toast.makeText(this,url, Toast.LENGTH_SHORT).show();
                	//TODO:Remove this Later - Check Use Only
                	//Toast.makeText(this,"Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();

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
