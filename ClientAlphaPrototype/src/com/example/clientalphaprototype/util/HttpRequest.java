package com.example.clientalphaprototype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class HttpRequest {

	public HttpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	 
	public static JSONObject requestJsonObject(String url)
			throws ClientProtocolException, IOException{
		
		JSONObject json = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try {

			HttpResponse response = httpclient.execute(httpGet);
			
			BufferedReader rd = new BufferedReader
					  (new InputStreamReader(response.getEntity().getContent()));
			
			String jsonText = readAll(rd);
			 
			json = new JSONObject(jsonText);
			
		} catch (Exception ex) {
			Log.e(ex.getClass().toString(), ex.toString());
			
		}

		return json;

	}
	
	public static boolean requestJson(String url)
			throws ClientProtocolException, IOException{
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try {

			HttpResponse response = httpclient.execute(httpGet);
			
					
		} catch (Exception ex) {
			Log.e(ex.getClass().toString(), ex.toString());
			return false;
		}

		return true;

	}

	// TODO: Not finished
	public static boolean sendPost(String jsonString, String url)
			throws ClientProtocolException, IOException {
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEnt = new StringEntity(jsonString);

			httpPost.setEntity(stringEnt);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			httpclient.execute(httpPost);

		} catch (Exception ex) {
			// handle exception here
			return false;
		}

		return true;
	}
}
