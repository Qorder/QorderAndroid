package qorder.clientprototype.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class BasketProduct extends Product {
	
	@JsonProperty("notes") private String notes;
	private int quantity;
	private String attributes;
	
	public BasketProduct(long id, String name, BigDecimal price,String attributes, String notes,String uri,int quantity) {
		super(id, name, price,uri);
		this.notes = notes;
		this.attributes = attributes;
		this.setQuantity(quantity);
	}
	
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
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
			return (this.getName()+ " " + this.getAttributes() + " x" +quantity);
		else 
			return(this.getName()+ " " + this.getAttributes());
	}
	
}
