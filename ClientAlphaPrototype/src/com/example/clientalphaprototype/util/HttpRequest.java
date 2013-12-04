package com.example.clientalphaprototype.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpRequest {

	public HttpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	//TODO: Not finished
	public static boolean sendPost (String jsonString,String url) throws ClientProtocolException, IOException 
	{	
		try{

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEnt = new StringEntity(jsonString);

			httpPost.setEntity(stringEnt);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");


			HttpResponse httpResponse = httpclient.execute(httpPost);
			//HttpEntity httpEntity = httpResponse.getEntity();
			//InputStream inputStream = httpEntity.getContent();
			//String response = inputStream.toString();

		}
		catch(Exception ex)
		{
			//handle exception here
			return false;
		}

		return true;
	}
}
