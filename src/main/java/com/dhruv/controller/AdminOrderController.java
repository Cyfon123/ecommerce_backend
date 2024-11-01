package com.dhruv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhruv.exception.OrderException;
import com.dhruv.model.Order;
import com.dhruv.response.ApiResponse;
import com.dhruv.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	 @Autowired
	 private OrderService orderService;

	 @GetMapping
//   @Operation(description = "Get all orders")
   public ResponseEntity<List<Order>> getAllOrders() {
       List<Order> orders = orderService.getAllOrders();
       return new ResponseEntity<>(orders, HttpStatus.OK);
   }
	 
	 @PutMapping("/{orderId}/confirm")
//   @Operation(description = "Confirm an order")
   public ResponseEntity<Order> confirmOrder(@PathVariable Long orderId) throws OrderException {
       Order order = orderService.confirmedOrder(orderId);
       return new ResponseEntity<>(order, HttpStatus.OK);
   }
	 
	 @PutMapping("/{orderId}/ship")
//   @Operation(description = "Ship an order")
   public ResponseEntity<Order> shipOrder(@PathVariable Long orderId) throws OrderException {
       Order order = orderService.shippedOrder(orderId);
       return new ResponseEntity<>(order, HttpStatus.OK);
   }

   @PutMapping("/{orderId}/deliver")
//   @Operation(description = "Deliver an order")
   public ResponseEntity<Order> deliverOrder(@PathVariable Long orderId) throws OrderException {
       Order order = orderService.deliveredOrder(orderId);
       return new ResponseEntity<>(order, HttpStatus.OK);
   }

   @PutMapping("/{orderId}/cancel")
//   @Operation(description = "Cancel an order")
   public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) throws OrderException {
       Order order = orderService.canceledOrder(orderId);
       return new ResponseEntity<>(order, HttpStatus.OK);
   }
   

   @DeleteMapping("/{orderId}")
//   @Operation(description = "Delete an order")
   public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
       orderService.deleteOrder(orderId);
       
       ApiResponse res = new ApiResponse("order deleted successfully", true);
       return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
   }

}
