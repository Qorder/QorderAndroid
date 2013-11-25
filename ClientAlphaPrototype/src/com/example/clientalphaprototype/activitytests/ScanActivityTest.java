package com.example.clientalphaprototype.activitytests;

import java.net.MalformedURLException;
import java.net.URL;

import com.example.clientalphaprototype.ScanActivity;
import com.example.clientalphaprototype.model.Business;

import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;

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

	public void testJsonParser() throws MalformedURLException {
		String url = ("http://83.212.118.113/mockBussinessJson.json");
        boolean event ;
	    event = mScanActivity.JsonParser(url);
	    assertTrue(event);
	    
	}
	
	public void testisCameraAvailable() {	
		boolean event ;
        event = mScanActivity.isCameraAvailable();
        assertTrue(event);
	}
	
	public void testisNetworkAvailable() {
        boolean event ;
        event = mScanActivity.isNetworkAvailable();
        assertTrue(event);
	}

}
