package com.example.clientalphaprototype.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductOrder {
    
	@JsonProperty("comments") private String comments;
	@JsonProperty("product")private Product product;
    
    
    public ProductOrder(Product product) {
            this.product = product;
    }

  
    public String getComments() {
            return comments;
    }

    
    public void setComments(String comments) {
            this.comments = comments;
    }

   
    public Product getProduct() {
            return product;
    }

    
    public void setProduct(Product product) {
            this.product = product;
    }
    

}
