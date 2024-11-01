package com.dhruv.controller;

import com.dhruv.exception.OrderException;
import com.dhruv.exception.UserException;
import com.dhruv.model.Address;
import com.dhruv.model.Order;
import com.dhruv.model.User;
import com.dhruv.service.OrderService;
import com.dhruv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
//    @Operation(description = "Create a new order")
    public ResponseEntity<Order> createOrder(
            @RequestBody Address shippingAddress,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Order createdOrder = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
//    @Operation(description = "Get order details by order ID")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
    	  User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
//    @Operation(description = "Get all orders for a user")
    public ResponseEntity<List<Order>> getUserOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
    	 User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/place")
//    @Operation(description = "Place an order")
    public ResponseEntity<Order> placeOrder(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.placedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}