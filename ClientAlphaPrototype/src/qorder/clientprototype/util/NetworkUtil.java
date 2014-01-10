package qorder.clientprototype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import qorder.clientprototype.model.OrderInfo;
import android.util.Log;

public class NetworkUtil {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject requestJsonObject(String url)
			throws ClientProtocolException, IOException {
		final HttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpGet httpGet = new HttpGet(url);

		try {

			HttpResponse response = httpclient.execute(httpGet);

			return convertToJson(response);
		} catch (Exception ex) {
			Log.e(ex.getClass().toString(), ex.toString());

		}

		return null;

	}

	private static JSONObject convertToJson(HttpResponse response) {
		JSONObject json = null;

		BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			String jsonText = readAll(rd);

			Log.i("json received:", jsonText);

			json = new JSONObject(jsonText);
		} catch (Exception e) {
			Log.e("error while parsing json", e.getMessage());
		}
		return json;
	}

	public static String checkOrderStatus(String url)
			throws ClientProtocolException, IOException {
		JSONObject json = requestJsonObject(url);
		String orderStatus = null;

		try {
			orderStatus = json.getString("status");
		} catch (Exception e) {
			Log.e("error parsing orderStatus json", e.getMessage());
			return null;
		}
		return orderStatus;
	}

	// TODO: write a custom exception
	public static OrderInfo sendJson(String jsonString, String url)
			throws Exception {
		try {
			final HttpParams httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		    
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

			HttpPut httpPut = new HttpPut(url);
			StringEntity stringEnt = new StringEntity(jsonString);
			httpPut.setEntity(stringEnt);
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-type", "application/json");

			HttpResponse response = httpclient.execute(httpPut);
			if (response != null) {
				JSONObject json = GetResponseMessage(response);
				Log.e("the response", json.toString());
				return new OrderInfo(json.getString("id"),json.getString("status"),true);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return new OrderInfo(null,null,false);

	}

	private static JSONObject GetResponseMessage(HttpResponse response)
			throws Exception {

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
			throw new Exception(response.getStatusLine().getReasonPhrase());
		}

		return convertToJson(response);
	}

}
