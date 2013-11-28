package com.example.clientalphaprototype.activitytests;

import com.example.clientalphaprototype.CategoriesActivity;

import android.test.AndroidTestCase;
import junit.framework.TestCase;

public class CategoriesActivityTest extends AndroidTestCase  {
	
	private CategoriesActivity mCategoriesActivity;

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testisNetworkAvailable() {
		boolean event ;
		event = mCategoriesActivity.isNetworkAvailable();
		assertTrue(event);
	}

}
