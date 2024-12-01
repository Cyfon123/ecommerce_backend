package com.dhruv.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhruv.exception.AddressException;
import com.dhruv.exception.OrderException;
import com.dhruv.model.Address;
import com.dhruv.model.Cart;
import com.dhruv.model.CartItem;
import com.dhruv.model.Order;
import com.dhruv.model.OrderItem;
import com.dhruv.model.User;
import com.dhruv.repo.AddressRepository;
import com.dhruv.repo.CartRepository;
import com.dhruv.repo.OrderItemRepository;
import com.dhruv.repo.OrderRepository;
import com.dhruv.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public Order createOrder(User user, Address shippingaddress) {
		shippingaddress.setUser(user);
		
		Address address = addressRepository.save(shippingaddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item : cart.getCartItems())
		{
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
		
			orderItems.add(createdOrderItem);
		}
		
		
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepository.save(createdOrder);
		
		for(OrderItem item : orderItems)
		{
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}
	
	@Override
	@Transactional
	public Order createOrderWithAddressId(User user, Long addressId) throws AddressException {
		Address address = addressService.findAddressById(addressId);
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item : cart.getCartItems())
		{
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			System.out.println("createdOrderItem" + createdOrderItem.getId());
		
			orderItems.add(createdOrderItem);
		}
			
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		System.out.println("createdOrder" + createdOrder.getOrderId());
		
		Order savedOrder = orderRepository.save(createdOrder);
		
		for(OrderItem item : orderItems)
		{
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		System.out.println("savedOrder" + savedOrder.getOrderId());
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt = orderRepository.findById(orderId);
		
		if(opt.isPresent())
			return opt.get();
		throw new OrderException("Order not exist with id :- " + orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUserOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		
		orderRepository.deleteById(orderId);
		
	}

	

}
