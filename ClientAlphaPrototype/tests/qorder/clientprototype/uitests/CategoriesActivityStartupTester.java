package qorder.clientprototype.uitests;

import qorder.clientprototype.activities.CategoriesActivity;
import qorder.clientprototype.activities.ScanActivity;
import qorder.clientprototype.model.Category;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;

public class CategoriesActivityStartupTester extends
		ActivityInstrumentationTestCase2<ScanActivity> {

	private ScanActivity activity;

	public CategoriesActivityStartupTester() {
		super(ScanActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
	}

	public void testStartCategoriesActivity() throws Exception {

		ActivityMonitor monitor = getInstrumentation().addMonitor(
				CategoriesActivity.class.getName(), null, false);

		Button view = (Button) activity
				.findViewById(com.example.clientalphaprototype.R.id.button_scan);

		TouchUtils.clickView(this, view);

		CategoriesActivity categoriesActivity = (CategoriesActivity) monitor
				.waitForActivityWithTimeout(2000);
		assertNotNull(categoriesActivity);

		ListView categories_listView = (ListView) categoriesActivity
				.findViewById(com.example.clientalphaprototype.R.id.categories_listview);

		ViewAsserts.assertOnScreen(categoriesActivity.getWindow()
				.getDecorView(), categories_listView);

		String tempCategory =  (String)categories_listView.getItemAtPosition(0);

		assertEquals("Text incorrect", "Food", tempCategory);

		this.sendKeys(KeyEvent.KEYCODE_BACK);

		TouchUtils.clickView(this, view);
	}
}
