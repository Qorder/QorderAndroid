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

	public DetailedProduct parse(JSONObject json) throws JSONException,
			JsonParseException, JsonMappingException, ClassNotFoundException,
			IOException {
		DetailedProduct product = new DetailedProduct();

		try {
			product.setId(json.getLong("id"));
			product.setName(json.getString("name"));
			product.setPrice(BigDecimal.valueOf(json.getLong("price")));
			product.setDetails(json.getString("details"));

			return product;

		} catch (Exception e) {
			Log.e("detailedProducts parsing failed", e.getMessage());
			// handle exception
		}

		return null;
	}

}
