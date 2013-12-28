package qorder.clientprototype.uitests;

import qorder.clientprototype.activities.*;
import android.content.Intent;
import android.widget.Button;

public class ScanActivityTester extends
		android.test.ActivityUnitTestCase<ScanActivity> {

	private int buttonId;
	private ScanActivity activity;

	public ScanActivityTester() {
		super(ScanActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				ScanActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	public void testbuttonText() {
		buttonId = com.example.clientalphaprototype.R.id.button_scan;
		assertNotNull(activity.findViewById(buttonId));
		Button view = (Button) activity.findViewById(buttonId);
		assertEquals("Incorrect label of the button", "I am ready to order",
				view.getText());
	}

	public void testIntentTriggerViaOnClick() {
		buttonId = com.example.clientalphaprototype.R.id.button_scan;
		Button view = (Button) activity.findViewById(buttonId);
		assertNotNull("Button not allowed to be null", view);

		view.performClick();

		Intent triggeredIntent = getStartedActivityIntent();
		assertNotNull("Intent was null", triggeredIntent);
		String data = triggeredIntent.getExtras().getString("categoriesUrl");

		assertEquals("Incorrect data passed via the intent",
				"webServiceCategoriesURI", data);
	}

}
