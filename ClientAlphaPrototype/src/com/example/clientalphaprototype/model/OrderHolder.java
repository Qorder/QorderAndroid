package com.example.clientalphaprototype.model;

import java.util.ArrayList;
import java.util.List;

public class OrderHolder {

	String businessName;
	final String wsPostUri = "http://10.0.2.2:8080/qorderws/orders/business?id=";

	static List<BasketProduct> order = new ArrayList<BasketProduct>();

	public void add(BasketProduct basketProduct) {
		order.add(basketProduct);
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void replace(List<BasketProduct> order) {
		OrderHolder.order = order;
	}

	public void reset() {
		order.clear();
	}

	public List<BasketProduct> getOrder() {
		return order;
	}

	static public int count() {
		return order.size();
	}

}