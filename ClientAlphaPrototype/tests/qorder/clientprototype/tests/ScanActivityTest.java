package qorder.clientprototype.tests;

import qorder.clientprototype.activities.ScanActivity;
import android.test.ActivityInstrumentationTestCase2;


public class ScanActivityTest extends ActivityInstrumentationTestCase2<ScanActivity> {
	
	private ScanActivity mScanActivity;

	public ScanActivityTest() {
		super(ScanActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mScanActivity = getActivity();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testisCameraAvailable() {	
		boolean event ;
        event = mScanActivity.isCameraAvailable();
        assertTrue(event);
	}

}
