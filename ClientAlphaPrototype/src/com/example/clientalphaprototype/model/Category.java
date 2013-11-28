package com.example.clientalphaprototype.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
	
	@JsonProperty("id") private long id;
	@JsonProperty("name") private String name;
	@JsonProperty("uri") private String uri;
	
	public Category(long id, String name,String uri) {
		super();
		this.id = id;
		this.name = name;
		this.uri = uri;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
