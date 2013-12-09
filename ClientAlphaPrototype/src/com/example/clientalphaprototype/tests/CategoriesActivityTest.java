package com.example.clientalphaprototype.tests;

import com.example.clientalphaprototype.CategoriesActivity;
import android.test.ActivityInstrumentationTestCase2;


public class CategoriesActivityTest extends ActivityInstrumentationTestCase2<CategoriesActivity> {
	
	private CategoriesActivity mCategoriesActivity;
	
	public CategoriesActivityTest() {
		super(CategoriesActivity.class);
		// TODO Auto-generated constructor stub
	}


	protected void setUp() throws Exception {
		super.setUp();
		mCategoriesActivity = getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testisNetworkAvailable() {
		boolean event;
		event = mCategoriesActivity.isNetworkAvailable();
		assertTrue(event);
	}

}
