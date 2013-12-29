package qorder.clientprototype.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.jsonparsers.CategoryJsonParser;
import qorder.clientprototype.model.Category;
import qorder.clientprototype.util.NetworkUtil;
import android.test.AndroidTestCase;

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
		String url = "http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/menus/business?id=0";

		JSONObject json;
		json = NetworkUtil.requestJsonObject(url);
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
		String url = "http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/menus/business?id=0";
		JSONObject json;
		List<Category> category = new ArrayList<Category>();
		CategoryJsonParser jsonParser = new CategoryJsonParser();
		List<Category> expectedCategory = new ArrayList<Category>();

		json = NetworkUtil.requestJsonObject(url);

		category = jsonParser.parse(json);
		expectedCategory.add(new Category(0, "food", "null"));
		expectedCategory.add(new Category(1, "drinks", "null"));

		assertEquals(category, expectedCategory);
	}

}