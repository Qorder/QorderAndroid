package com.example.clientalphaprototype.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;

import com.example.clientalphaprototype.jsonparsers.CategoryJsonParser;
import com.example.clientalphaprototype.jsonparsers.IJsonParser;
import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.model.Product;
import com.example.clientalphaprototype.util.HttpRequest;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import android.test.AndroidTestCase;

public class HttpRequestTest extends AndroidTestCase {

	protected Category category;
	protected List<Category> mockList;
	protected String mockJson;

	public HttpRequestTest() {
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();

		// Prepare mock Objects
		Category category = new Category(1, "mockName", "mockUri");
		List<Category> mockList = new ArrayList<Category>();
		mockList.add(category);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// TODO:Not Finished
	/*
	 * public void testSendPost() throws ClientProtocolException, IOException {
	 * mockJson=JsonUtil.PojoToJsonParser(mockList); String url = "http://...";
	 * //? boolean event ; boolean expectedevent=true; event =
	 * HttpRequest.sendPost(mockJson,url); assertEquals(event,expectedevent); }
	 */

	public void testRequestJson() throws JSONException,
			ClientProtocolException, IOException {
		String url = "http://10.0.2.2:8080/qorderws/businesses/menus/business?id=0";
		JSONObject event;
		JSONObject expectedEvent = new JSONObject(
				"{\"businessName\":\"Ta kala paidia\",\"categoryInfoList\":[{\"id\":0,\"name\":\"drinks\",\"uri\":\"null\"}]}");
		event = HttpRequest.requestJsonObject(url);
		
		assertEquals(event, expectedEvent);

		// assertNotNull(event);
	}
	
	

	// RETURNS NULL OBJECT
	public void testRequestAndParseJson() throws ClientProtocolException,
			IOException, ClassNotFoundException {
		List<Category> event = new ArrayList<Category>();
		List<Category> expectedEvent = new ArrayList<Category>();

		String tempUrl = "http://10.0.2.2:8080/qorderws/businesses/menus/business?id=0";
		 HttpRequest.requestJson(tempUrl);

		event = JsonUtil.<List<Category>> JsonToPojoParser(tempUrl,
				Category.class);
		expectedEvent.add(new Category(0, null, null));

		assertEquals(event, expectedEvent);
	}

}
