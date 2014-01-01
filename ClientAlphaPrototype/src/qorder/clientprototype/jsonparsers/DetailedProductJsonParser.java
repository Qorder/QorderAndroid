package qorder.clientprototype.jsonparsers;

import java.io.IOException;
import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import qorder.clientprototype.model.BasketProduct;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DetailedProductJsonParser {

	public BasketProduct parse(JSONObject json) throws JSONException,
			JsonParseException, JsonMappingException, ClassNotFoundException,
			IOException {
		BasketProduct product = new BasketProduct();

		try {
			product.setId(json.getLong("id"));
			product.setName(json.getString("name"));
			product.setPrice(BigDecimal.valueOf(json.getDouble("price")));
			product.setAttributes(json.getString("details"));
			product.setQuantity(1);
			return product;

		} catch (Exception e) {
			Log.e("detailedProducts parsing failed", e.getMessage());
			// handle exception
		}

		return null;
	}

}
