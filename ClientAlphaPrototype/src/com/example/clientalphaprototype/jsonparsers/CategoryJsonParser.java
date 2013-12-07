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
import com.example.clientalphaprototype.util.JsonUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryJsonParser implements IJsonParser<Category> {

	// TODO: add categoryInfoList as a String argument
	@Override
	public List<Category> parse(JSONObject json) throws JSONException,
			JsonParseException, JsonMappingException, ClassNotFoundException,
			IOException {

		JSONArray catArray = json.getJSONArray("categoryInfoList");
		Category parsingCat = new Category();
		List<Category> categories = new ArrayList<Category>();
		
		try {
			for(int i=0;i<catArray.length();i++)
			{
				JSONObject cat = catArray.getJSONObject(i);
				parsingCat.setId(cat.getLong("id"));
				parsingCat.setName(cat.getString("name"));
				parsingCat.setUri(cat.getString("uri"));
				
				categories.add(parsingCat);
			}

			return categories;

		} catch (Exception e) {
			Log.e("categories parsing failed",e.getMessage());
			// handle exception
		}

		return null;
	}

}
