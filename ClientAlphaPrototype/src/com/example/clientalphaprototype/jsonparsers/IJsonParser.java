package com.example.clientalphaprototype.jsonparsers;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IJsonParser<T> {

	List<T> parse(JSONObject json) throws JSONException, JsonParseException, JsonMappingException, ClassNotFoundException, IOException;
}
