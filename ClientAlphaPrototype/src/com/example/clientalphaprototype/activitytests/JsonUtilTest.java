package com.example.clientalphaprototype.activitytests;

import java.io.IOException;

import android.test.AndroidTestCase;

import com.example.clientalphaprototype.model.Business;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonUtilTest extends AndroidTestCase  {

	public JsonUtilTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testJsonToPojoParser() throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {
		String url = ("http://83.212.118.113/mockBussinessJson.json");
	    Business event = JsonUtil.<Business>JsonToPojoParser(url, Business.class);
	    Business expectedEvent = new Business();
	    expectedEvent.setName("mockName");
	    assertEquals(expectedEvent.getName(), event.getName());
	}

}
