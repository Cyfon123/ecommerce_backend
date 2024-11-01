package com.dhruv.service;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Cart;
import com.dhruv.model.User;
import com.dhruv.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
