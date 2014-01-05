package qorder.clientprototype.model;

public class OrderInfo {
	String id;
	String status;
	boolean result;

	public OrderInfo(String id, String status, boolean result) {
		super();
		this.id = id;
		this.status = status;
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}