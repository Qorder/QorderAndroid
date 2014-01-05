package qorder.clientprototype.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

	List<BasketProduct> order = new ArrayList<BasketProduct>();
	String orderID;
	String status;

	public Order(List<BasketProduct> order, String orderID, String status) {
		this.order = order;
		this.orderID = orderID;
		this.status = status;
	}

	public String getStatusCheckURI() {
		return OrderHolder.getOrderStatusURI() + orderID;
	}

	public List<BasketProduct> getOrder() {
		return order;
	}

	public void setOrder(List<BasketProduct> order) {
		this.order = order;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
