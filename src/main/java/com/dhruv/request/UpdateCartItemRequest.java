package com.dhruv.request;

public class UpdateCartItemRequest {
	 private int quantity;

	public UpdateCartItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateCartItemRequest(int quantity) {
		super();
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	 
	

}
