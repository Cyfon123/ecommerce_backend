package com.dhruv.service;

import com.dhruv.exception.CartItemException;
import com.dhruv.exception.UserException;
import com.dhruv.model.Cart;
import com.dhruv.model.CartItem;
import com.dhruv.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long cartItemId, int quantity) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
