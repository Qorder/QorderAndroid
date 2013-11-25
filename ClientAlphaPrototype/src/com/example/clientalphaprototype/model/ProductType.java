package com.example.clientalphaprototype.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductType {

	@JsonProperty("id") private long id;
	@JsonProperty("name") private String name;
	@JsonProperty("ProdList") private List<Product> ProdList = new ArrayList<Product>();        
    
            
    public long getId() {
            return this.id;
    }


    public void setId(long id) {
            this.id = id;
    }


    public String getName() {
            return this.name;
    }


    public void setName(String name) {
            this.name = name;
    }


    public void addProduct(Product product) {
            this.ProdList.add(product);
    }


    public List<Product> getProductList() {
            return this.ProdList;
    }

}
