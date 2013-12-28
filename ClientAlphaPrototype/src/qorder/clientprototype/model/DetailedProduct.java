package qorder.clientprototype.model;

import java.math.BigDecimal;

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
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


}
