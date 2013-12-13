package com.example.clientalphaprototype.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class DetailedProduct extends BasketProduct {

	@JsonProperty("attributes") private String details;

	public DetailedProduct(long id, String name, BigDecimal price,
			String notes, String details,String uri) {
		super(id, name, price, notes,uri);
		this.details = details;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


}
