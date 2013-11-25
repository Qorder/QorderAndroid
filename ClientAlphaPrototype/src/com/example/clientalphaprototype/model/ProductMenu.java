package com.example.clientalphaprototype.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)  
public class ProductMenu {
    
	@JsonProperty("id") private long id;
	@JsonProperty("prodTypeList") private List<ProductType> prodTypeList = new ArrayList<ProductType>();


    public long getId() {
            return this.id;
    }
    

    public void setId(long id)
    {
            this.id = id;
    }


    public void addProductType(ProductType prodType) {
            this.prodTypeList.add(prodType);
    }

    public List<ProductType> getProductTypes() {
            return this.prodTypeList;
    }
}
