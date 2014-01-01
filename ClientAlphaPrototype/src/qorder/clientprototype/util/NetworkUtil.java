package qorder.clientprototype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

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

		JSONObject json = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {

			HttpResponse response = httpclient.execute(httpGet);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String jsonText = readAll(rd);
			
			Log.i("json received:",jsonText);
			
			json = new JSONObject(jsonText);

		} catch (Exception ex) {
			Log.e(ex.getClass().toString(), ex.toString());

		}

		return json;

	}

	// TODO: write a custom exception
	public static boolean sendJson(String jsonString, String url)
			throws Exception {
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPut httpPut = new HttpPut(url);
			StringEntity stringEnt = new StringEntity(jsonString);

			httpPut.setEntity(stringEnt);
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-type", "application/json");

			HttpResponse response = httpclient.execute(httpPut);
			if (response != null) {
				Log.e("the response", GetResponseMessage(response));
				return true;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return false;

	}

	private static String GetResponseMessage(HttpResponse response)
			throws Exception {
		InputStream ips;

		ips = response.getEntity().getContent();

		BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
				"UTF-8"));
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
			throw new Exception(response.getStatusLine().getReasonPhrase());
		}
		
		StringBuilder sb = new StringBuilder();
		String s;
		while (true) {
			s = buf.readLine();
			if (s == null || s.length() == 0)
				break;
			sb.append(s);

		}
		buf.close();
		ips.close();
		return sb.toString();
	}

}
