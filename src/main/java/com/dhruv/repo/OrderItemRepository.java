package com.dhruv.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhruv.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
