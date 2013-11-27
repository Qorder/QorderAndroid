package com.example.clientalphaprototype.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
	
	@JsonProperty("id") private long id;
	@JsonProperty("tableNumber") private String name;
	
	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
