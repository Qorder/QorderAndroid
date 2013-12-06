package com.example.clientalphaprototype.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.mockito.Mockito;

import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.util.HttpRequest;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import android.test.AndroidTestCase;

public class HttpRequestTest extends AndroidTestCase  {

	protected Category category;
	protected List<Category> mockList;
	protected String mockJson;

	public HttpRequestTest() {
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();

		//Prepare mock Objects
		Category category = new Category(1, "mockName","mockUri");
		List<Category> mockList = new ArrayList<Category>();
		mockList.add(category);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//TODO:Not Finished
	public void testSendPost() throws ClientProtocolException, IOException {
		mockJson=JsonUtil.PojoToJsonParser(mockList);
		String url = "http://..."; //?
		boolean event ;
		boolean expectedevent=true;
		event = HttpRequest.sendPost(mockJson,url);
		assertEquals(event,expectedevent);
	}
}
