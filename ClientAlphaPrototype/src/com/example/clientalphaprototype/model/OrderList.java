package com.example.clientalphaprototype.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)  
public class OrderList {
    
	@JsonProperty("id") private long id;
	@JsonProperty("tableNumber") private String tableNumber;
	@JsonProperty("dateTime") private Date dateTime;
	@JsonProperty("productOrderList") private List<ProductOrder> productOrderList = new ArrayList<ProductOrder>();
    
    
    public long getId() {
            return id;
    }

    public void setId(long id) {
            this.id = id;
    }


    public String getTableNumber() {
            return tableNumber;
    }


    public void setTableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
    }

    public Date getDateTime() {
            return dateTime;
    }
    
    public void setDateTime() {
            this.dateTime = new Date();
    }


    public List<ProductOrder> getOrderProductList() {
            return this.productOrderList;
    }

    public void addProductOrder(ProductOrder orderProd) {
            this.productOrderList.add(orderProd);
    }
        

}
