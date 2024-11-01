package com.dhruv.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhruv.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query("Select o From Order o Where o.user.id =:userId And (o.orderStatus = 'PLACED' Or o.orderStatus = 'CONFIRMED' Or o.orderStatus = 'SHIPPED' Or o.orderStatus = 'DELIVERED')")
	public List<Order> getUserOrders(@Param("userId") Long userId);

}