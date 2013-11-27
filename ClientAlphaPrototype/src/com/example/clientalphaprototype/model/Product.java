package com.example.clientalphaprototype.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 

public class Product {
    
	@JsonProperty("id") private long id;
	@JsonProperty("name") private String name;
	@JsonProperty("price") private BigDecimal price;
    
    public Product(long id, String name, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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
    
    
    public BigDecimal getPrice() {
            return price;
    }
    
    
    public void setPrice(BigDecimal price) {
            this.price = price;
    }
    

}
