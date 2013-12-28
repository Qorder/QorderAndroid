package qorder.clientprototype.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.model.Category;
import qorder.clientprototype.util.HttpRequest;
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
	
}
