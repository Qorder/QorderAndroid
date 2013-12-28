package qorder.clientprototype.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class BasketProduct extends Product {
	
	@JsonProperty("notes") private String notes;

	public BasketProduct(long id, String name, BigDecimal price, String notes,String uri) {
		super(id, name, price,uri);
		this.notes = notes;
	}

	public BasketProduct() {
		super();
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
