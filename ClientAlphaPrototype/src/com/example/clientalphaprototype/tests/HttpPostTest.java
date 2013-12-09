package com.example.clientalphaprototype.tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.clientalphaprototype.model.BasketProduct;
import com.example.clientalphaprototype.util.HttpRequest;
import com.example.clientalphaprototype.util.JsonUtil;

public class HttpPostTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHttpPostJson() throws ClientProtocolException, IOException {
		List<BasketProduct> basketProducts = new ArrayList<BasketProduct>();

		for (int i = 0; i < 1; i++) {
			basketProducts.add(new BasketProduct(1, "test", BigDecimal
					.valueOf(1.99), "some notes", "some uri"));
		}
		
		String jsonToSend = JsonUtil.PojoToJsonParser(basketProducts);
		Log.e("ws json",jsonToSend);
		
		boolean sendResponse = HttpRequest.sendJson(jsonToSend, "http://10.0.2.2:8080/qorderws");
		boolean expectedSendResponse = true;
		
		assertEquals(sendResponse,expectedSendResponse);
	}
}
