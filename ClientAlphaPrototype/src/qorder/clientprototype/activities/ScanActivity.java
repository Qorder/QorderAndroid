package qorder.clientprototype.activities;

import java.util.List;

import qorder.clientprototype.extensions.ExpandableTextView;
import qorder.clientprototype.model.Category;
import qorder.clientprototype.model.OrderHolder;
import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.clientalphaprototype.R;

public class ScanActivity extends Activity {

	private static final int ZBAR_SCANNER_REQUEST = 0;
	//String text = "press the scan button and place your phones camera on top of the bar code to proceed to the catalogue";//getResources().getString(R.string.text_guide_scan_activity);
	List<Category> categories;

	// Set to true if you want to scan a qr code
	final boolean scanQR = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Disable Strick mode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		ExpandableTextView expandableTextView = (ExpandableTextView) findViewById(R.id.expandable_scaninfo);
		expandableTextView.setText(getResources().getString(R.string.text_guide_scan_activity));

		OrderHolder orderHolder = new OrderHolder();
		orderHolder.reset();
		initializeScanButton();
	}

	// Initialize scan button
	void initializeScanButton() {
		Button button = (Button) findViewById(R.id.button_scan);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (scanQR) {
					if (isCameraAvailable()) {
						Intent intent = new Intent(ScanActivity.this,
								ZBarScannerActivity.class);
						intent.putExtra(ZBarConstants.SCAN_MODES,
								new int[] { Symbol.QRCODE });

						startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
					} else {
						Toast.makeText(ScanActivity.this,
								getResources().getString(R.string.text_error_camera),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					// QrCode Parser
					Intent categories = new Intent(ScanActivity.this,
							CategoriesActivity.class);
					// Localhost ws
					// categories.putExtra("categoriesUrl","http://10.0.2.2:8080/qorderws/businesses/menus/business?id=0");
					categories
							.putExtra("categoriesUrl",
									"http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/menus/business?id=0");
					startActivity(categories);
				}

			}
		});
	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {

				String url = (data.getStringExtra(ZBarConstants.SCAN_RESULT));

				Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

				Intent categories = new Intent(ScanActivity.this,
						CategoriesActivity.class);
				categories.putExtra("categoriesUrl", url);
				startActivity(categories);

			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if (!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

}
