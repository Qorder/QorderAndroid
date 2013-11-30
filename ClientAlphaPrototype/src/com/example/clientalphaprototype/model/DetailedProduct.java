package com.example.clientalphaprototype.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class DetailedProduct extends BasketProduct {

	@JsonProperty("attributes") private List<String> attributes;

	public DetailedProduct(long id, String name, BigDecimal price,
			String notes, List<String> attributes,String uri) {
		super(id, name, price, notes,uri);
		this.attributes = attributes;
	}

	public DetailedProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailedProduct(long id, String name, BigDecimal price,
			String notes, String uri) {
		super(id, name, price, notes, uri);
		// TODO Auto-generated constructor stub
	}

	public DetailedProduct(long id, String name, BigDecimal price, String uri) {
		super(id, name, price, uri);
		// TODO Auto-generated constructor stub
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}


}
