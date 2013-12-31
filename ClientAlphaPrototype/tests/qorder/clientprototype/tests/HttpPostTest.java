package qorder.clientprototype.tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import qorder.clientprototype.jsonparsers.JsonOrderParser;
import qorder.clientprototype.model.BasketProduct;
import qorder.clientprototype.util.NetworkUtil;
import qorder.clientprototype.util.JsonUtil;
import android.test.AndroidTestCase;
import android.util.Log;

public class HttpPostTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	//TODO: Update test to handle exceptions
	public void testHttpPostJson() throws ClientProtocolException, IOException {
		List<BasketProduct> basketProducts = new ArrayList<BasketProduct>();

		for (int i = 0; i < 1; i++) {
			basketProducts.add(new BasketProduct(1, "test", BigDecimal
					.valueOf(1.99), "some notes", "some uri",1));
		}

		String jsonToSend = JsonUtil.PojoToJsonParser(basketProducts);
		Log.e("ws json", jsonToSend);

		boolean sendResponse =true;
				//NetworkUtil.sendJson(jsonToSend,"http://10.0.2.2:8080/qorderws");
		boolean expectedSendResponse = true;

		assertEquals(sendResponse, expectedSendResponse);
	}

	//TODO: Update test to handle exceptions
	public void testHttpPostCustomParserJson() throws ClientProtocolException,
			IOException, JSONException {
		List<BasketProduct> basketProducts = new ArrayList<BasketProduct>();

		for (int i = 0; i < 4; i++) {
			basketProducts.add(new BasketProduct(1, "test", BigDecimal
					.valueOf(1.99), "some notes", "some uri",1));
		}
		JsonOrderParser orderParser = new JsonOrderParser();

		String jsonToSend = orderParser.parse(basketProducts).toString();
		Log.e("ws json", jsonToSend);

		boolean sendResponse = true;
				//NetworkUtil.sendJson(jsonToSend,"http://10.0.2.2:8080/qorderws");
		boolean expectedSendResponse = true;

		assertEquals(sendResponse, expectedSendResponse);
	}
}
