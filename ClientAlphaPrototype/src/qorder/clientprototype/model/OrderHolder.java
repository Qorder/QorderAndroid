package qorder.clientprototype.model;

import java.util.ArrayList;
import java.util.List;

public class OrderHolder {

	static String businessName;
	
	//TODO: set these from QR
	static String tableNumber = "1";
	static String WSPostUrI = "www.something.troll";
	
	final String wsPostUri = "http://10.0.2.2:8080/qorderws/orders/business?id=";

	static List<BasketProduct> order = new ArrayList<BasketProduct>();

	public void add(BasketProduct basketProduct) {
		order.add(basketProduct);
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

	public List<BasketProduct> getOrder() {
		return order;
	}

	static public int count() {
		return order.size();
	}

}