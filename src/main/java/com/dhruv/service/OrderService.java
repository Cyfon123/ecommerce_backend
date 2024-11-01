package com.dhruv.service;

import com.dhruv.exception.OrderException;
import com.dhruv.model.Address;
import com.dhruv.model.Order;
import com.dhruv.model.User;
import java.util.*;

public interface OrderService  {

	public Order createOrder(User user, Address shippingaddress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	public List<Order> usersOrderHistory(Long userId); 
	public Order placedOrder(Long orderId) throws OrderException;
	public Order confirmedOrder(Long orderId) throws OrderException;
	public Order shippedOrder(Long orderId) throws OrderException;
	public Order deliveredOrder(Long orderId) throws OrderException;
	public Order canceledOrder(Long orderId) throws OrderException;
	public List<Order> getAllOrders();
	public void deleteOrder(Long orderId) throws OrderException;
}
