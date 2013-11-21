package com.example.clientalphaprototype;

import java.util.ArrayList;
import java.util.List;

public class OrderHolder {

	static List<String> order = new ArrayList<String>();

	public void add(String item)
	{
		order.add(item);
	}
	
	public void replace(List<String> replaceOrder)
	{
		this.order =replaceOrder;
	}

	public List<String> getOrder() {
		return order;
	}

}
