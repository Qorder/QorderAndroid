package com.example.clientalphaprototype.util;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static String JsonString;

	public static <T> T JsonToPojoParser(String url, Class<?> target) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		URL jsonurl = new URL(url);
		
		return mapper.readValue(jsonurl, mapper.getTypeFactory().constructCollectionType(List.class, Class.forName(target.getName())));
	}

	public static String PojoToJsonParser(List<?> target) throws JsonProcessingException{
		JsonString = mapper.writeValueAsString(target); 
		
		return JsonString;
	}
}