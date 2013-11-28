package com.example.clientalphaprototype.activitytests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.test.AndroidTestCase;








import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
		String url = ("http://83.212.118.113/mockCategoryJson.json");
		List<Category > event = JsonUtil.<List<Category>>JsonToPojoParser(url, Category.class);
		Category category = new Category(1, "mockName","mockUri");
		List<Category> expectedEvent = new ArrayList<Category>();
		expectedEvent.add(category);
		
		assertEquals(event, expectedEvent);
		
	}

	public void testPojoToJsonParser() throws JsonProcessingException{
		Category category = new Category(1, "mockName","mockUri");
		List<Category> event = new ArrayList<Category>();
		event.add(category);
		String excpectedEvent;
		excpectedEvent=JsonUtil.PojoToJsonParser(event);

		assertNotNull(excpectedEvent);
	}

}
