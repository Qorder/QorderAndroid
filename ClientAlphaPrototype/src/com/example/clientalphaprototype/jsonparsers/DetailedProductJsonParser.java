package com.example.clientalphaprototype.jsonparsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.model.DetailedProduct;
import com.example.clientalphaprototype.model.Product;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetailedProductJsonParser {

	final String jsonArrayName = "detailedProductInfoList";
	
	public List<DetailedProduct> parse(JSONObject json) throws JSONException,
			JsonParseException, JsonMappingException, ClassNotFoundException,
			IOException {

		JSONArray prodArray = json.getJSONArray(jsonArrayName);
		
		List<DetailedProduct> products = new ArrayList<DetailedProduct>();
		
		try {
			for(int i=0;i<prodArray.length();i++)
			{
				JSONObject cat = prodArray.getJSONObject(i);
				DetailedProduct parsingProd = new DetailedProduct();
				
				//TODO: update this to respond to the ws json
				parsingProd.setId(cat.getLong("id"));
				parsingProd.setName(cat.getString("name"));
				parsingProd.setUri(cat.getString("uri"));
				
				products.add(parsingProd);
			}

			return products;

		} catch (Exception e) {
			Log.e("detailedProducts parsing failed",e.getMessage());
			// handle exception
		}

		return null;
	}

}
