package com.example.clientalphaprototype.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.clientalphaprototype.jsonparsers.CategoryJsonParser;
import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.util.HttpRequest;

public class HttpRequestAndParsingTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRequestJsonBusinessID() throws ClientProtocolException,
			IOException, JSONException {
		// LocalHost url
		// String url = "http://10.0.2.2:8080/qorderws/businesses/menus/business?id=0";

		// Ocean url
		String url = "http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/businesses/menus/business?id=0";

		JSONObject json;
		json = HttpRequest.requestJsonObject(url);
		String businessID = json.getString("businessName");
		String expectedBusinessID = "Dr. Guros";

		assertEquals(businessID, expectedBusinessID);
	}

	public void testRequestAndJsonCategoryParser()
			throws ClientProtocolException, IOException,
			ClassNotFoundException, JSONException {
		// LocalHost url
		// String url = "http://10.0.2.2:8080/qorderws/businesses/menus/business?id=0";

		// Ocean url
		String url = "http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/businesses/menus/business?id=0";
		JSONObject json;
		List<Category> category = new ArrayList<Category>();
		CategoryJsonParser jsonParser = new CategoryJsonParser();
		List<Category> expectedCategory = new ArrayList<Category>();

		json = HttpRequest.requestJsonObject(url);

		category = jsonParser.parse(json);
		expectedCategory.add(new Category(0, "food", "null"));
		expectedCategory.add(new Category(1, "drinks", "null"));

		assertEquals(category, expectedCategory);
	}

}