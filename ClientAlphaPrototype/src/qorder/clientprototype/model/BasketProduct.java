package qorder.clientprototype.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class BasketProduct extends Product {
	
	@JsonProperty("notes") private String notes;
	private int quantity;
	public BasketProduct(long id, String name, BigDecimal price, String notes,String uri,int quantity) {
		super(id, name, price,uri);
		this.notes = notes;
		this.setQuantity(quantity);
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductTitle()
	{
		if(quantity>1)
			return (this.getName()+"   x" +quantity);
		else 
			return(this.getName());
	}
	
}
