package qorder.clientprototype.model;

import java.math.BigDecimal;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderHolder {

	static String businessName;
	
	//TODO: set these from QR
	static String tableNumber = "1";
	static String WSPostUrI = "www.something.troll";
	static String orderStatusURI = "http://snf-185147.vm.okeanos.grnet.gr:8080/qorderws/orders/order?id=";
	static List<BasketProduct> order = new ArrayList<BasketProduct>();
	static List<Order> submittedOrders = new ArrayList<Order>();

	public void add(BasketProduct basketProduct) {
		order.add(basketProduct);
	}
	

	public static String getOrderStatusURI() {
		return orderStatusURI;
	}

	public static void setOrderStatusURI(String orderStatusURI) {
		OrderHolder.orderStatusURI = orderStatusURI;
	}

	public static List<String> getOrderStatusURIs()
	{
		List<String> statusURI = new ArrayList<String>();
		for(Order orderItem : submittedOrders)
		{
			statusURI.add(orderItem.getStatusCheckURI());
		}
		return statusURI;
	}

	public static String getBusinessName() {
		return businessName;
	}

	public static void setBusinessName(String businessname) {
		OrderHolder.businessName = businessname;
	}
	
	public static String getTableNumber() {
		return tableNumber;
	}
	
	public static String getWSPostUrI()
	{
		return WSPostUrI;
	}
	
	public static void setWSPostUrI(String WSPostUrI)
	{
		OrderHolder.WSPostUrI = WSPostUrI;
	}

	public static void setTableNumber(String tableNumber) {
		OrderHolder.tableNumber = tableNumber;
	}

	public void replace(List<BasketProduct> order) {
		OrderHolder.order = order;
	}

	public static void reset() {
		order.clear();
	}
	
	public static void sendOrder(String orderID, String status) {
		List<BasketProduct> orderList = new ArrayList<BasketProduct>();
		for(BasketProduct orderItem : order)
		{
			orderList.add(orderItem);
		}
		Collections.copy(orderList, order);
		submittedOrders.add(new Order(orderList,orderID,status));	
		reset();
	}

	public List<BasketProduct> getOrder() {
		return order;
	}
		
	public List<BasketProduct> getOrderAt(int pos) {
		return submittedOrders.get(pos).getOrder();
	}

	public static String getOrderStatusAt(int pos) {
		return submittedOrders.get(pos).getStatus();
	}
	
	public static void setOrderStatusAt(int pos,String newStatus) {
		submittedOrders.get(pos).setStatus(newStatus);
	}
	
	public static int count() {
		return order.size();
	}
	
	public static boolean hasOrders()
	{
		return submittedOrders.size()>0;
	}
	
	public List<String> getOrdersID()
	{
		List<String>ordersID = new ArrayList<String>();
		for(Order orderItem : submittedOrders)
		{
			ordersID.add("Order #" + orderItem.getOrderID());
		}
		return ordersID;
	}
	
	public List<String> getOrdersStatus()
	{
		List<String>ordersStatus = new ArrayList<String>();
		for(Order orderItem : submittedOrders)
		{
			ordersStatus.add(orderItem.getStatus());
		}
		return ordersStatus;
	}
	
	public List<String> getProductNames(List<BasketProduct> products) {

		List<String> productNames = new ArrayList<String>();
		for (BasketProduct product : products) {
			productNames.add(product.getProductTitle());
		}

		return productNames;
	}

	public List<String> getProductNotes(List<BasketProduct> products) {

		List<String> productNotes = new ArrayList<String>();
		for (BasketProduct product : products) {
			productNotes.add(product.getNotes());
		}

		return productNotes;
	}

	//TODO: get currencySign from strings.xml
	public List<String> getProductPrices(List<BasketProduct> products,Format priceFormat,String currencySign) {
		List<String> productValues = new ArrayList<String>();
		for (BasketProduct product : products) {
			BigDecimal totalPrice = product.getPrice().multiply(
					BigDecimal.valueOf((double) product.getQuantity()));
			productValues.add(priceFormat.format(totalPrice) + currencySign);
		}

		return productValues;
	}
	

}

