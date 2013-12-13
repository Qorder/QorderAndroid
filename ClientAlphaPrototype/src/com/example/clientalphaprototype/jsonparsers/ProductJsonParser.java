package com.example.clientalphaprototype.jsonparsers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.clientalphaprototype.model.Category;
import com.example.clientalphaprototype.model.Product;
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductJsonParser {

	final String jsonArrayName = "productList";
	
	public List<Product> parse(JSONObject json) throws JSONException,
			JsonParseException, JsonMappingException, ClassNotFoundException,
			IOException {

		JSONArray prodArray = json.getJSONArray(jsonArrayName);
		
		List<Product> products = new ArrayList<Product>();
		
		try {
			for(int i=0;i<prodArray.length();i++)
			{
				JSONObject cat = prodArray.getJSONObject(i);
				Product parsingProd = new Product();
				
				parsingProd.setId(cat.getLong("id"));
				parsingProd.setPrice(BigDecimal.valueOf(cat.getLong("price")));
				parsingProd.setName(cat.getString("name"));
				parsingProd.setUri(cat.getString("uri"));
				
				products.add(parsingProd);
			}

			return products;

		} catch (Exception e) {
			Log.e("products parsing failed",e.getMessage());
			// handle exception
		}

		return null;
	}

}
