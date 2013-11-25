package com.example.clientalphaprototype.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)  
public class Business {
 	
		@JsonProperty("id") private long id;
		@JsonProperty("name") private String name;
		@JsonProperty("menu") private ProductMenu menu;

    public Business() {
			super();
			// TODO Auto-generated constructor stub
	}


    public Business(String name) {
            this.name = name;
    }
    
   
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
            this.name=name;
    }

    
   
    public void setMenu(ProductMenu menu) {
            this.menu = menu;
    }
    
 
    public ProductMenu getMenu() {
            return menu;
    }
       
        

}
