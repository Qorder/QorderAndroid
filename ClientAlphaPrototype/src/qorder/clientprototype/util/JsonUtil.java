package qorder.clientprototype.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static String JsonString;

	public static <T> T JsonToPojoParser(String url, Class<?> target)
			throws JsonParseException, JsonMappingException, IOException,
			ClassNotFoundException {
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
				true);
		URL jsonurl = new URL(url);
		try {

			return mapper.readValue(
					jsonurl,
					mapper.getTypeFactory().constructCollectionType(List.class,
							Class.forName(target.getName())));
		} catch (Exception ex) {
			Log.e(ex.getClass().toString(), ex.toString());	
		}

		return null;
	}

		
	public static String PojoToJsonParser(List<?> target)
			throws JsonProcessingException {
		JsonString = mapper.writeValueAsString(target);

		return JsonString;
	}
}