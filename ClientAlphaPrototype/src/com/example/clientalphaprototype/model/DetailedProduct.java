package com.example.clientalphaprototype.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
public class DetailedProduct extends BasketProduct {

	@JsonProperty("attributes") private List<String> attributes;

	public DetailedProduct(long id, String name, BigDecimal price,
			String notes, List<String> attributes) {
		super(id, name, price, notes);
		this.attributes = attributes;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}
}
