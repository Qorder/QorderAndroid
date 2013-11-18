package com.example.clientprototype;

//TODO: add the following packages
//import com.dm.zbar.android.scanner.ZBarConstants;
//import com.dm.zbar.android.scanner.ZBarScannerActivity;
//import net.sourceforge.zbar.Symbol;

import com.example.clientprototype.R;
import com.example.clientprototype.R.id;
import com.example.clientprototype.R.layout;
import com.example.clientprototype.R.menu;
import com.example.clientprototype.menu.TempMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MenuActivity";
	Button buttonQR;
	Button buttonAdvanced;
	int ZBAR_SCANNER_REQUEST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//Manage buttons
		buttonQR = (Button) findViewById(R.id.buttonQR);
		buttonAdvanced = (Button) findViewById(R.id.buttonAdvanced);
		buttonQR.setOnClickListener(this); 
		buttonAdvanced.setOnClickListener(this); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		   switch(v.getId()) {
           case R.id.buttonQR:
        	    showMenu();
       			//Intent intent = new Intent(this, ZBarScannerActivity.class);
       			//TODO: add the net.sourceforge.zbar.Symbol package
       			//intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
       			//startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
       			Log.d(TAG,"buttonQR onClicked");
           break;
           case R.id.buttonAdvanced:
      			Toast.makeText(MenuActivity.this,"buttonAdvanced is clicked",Toast.LENGTH_LONG).show();
      			Log.d(TAG,"buttonAdvanced onClicked");
           break;
		   }
	}

	//Use this for QR parsing
	//@Override
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{    
	    if (resultCode == RESULT_OK) 
	    {
	        // Scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT)
	        // Type of the scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE)
	        // Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
	        // Toast.makeText(this, "Scan Result Type = " + data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE), Toast.LENGTH_SHORT).show();
	        // The value of type indicates one of the symbols listed in Advanced Options below.
	    } else if(resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
	    }
	}*/
   

    private void showMenu() {
        Intent launchMenu = new Intent(this, TempMenu.class);
        startActivity(launchMenu);
    }
		
}


