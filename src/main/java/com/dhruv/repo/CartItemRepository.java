package com.dhruv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhruv.model.Cart;
import com.dhruv.model.CartItem;
import com.dhruv.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> 
{

	@Query("Select ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product, @Param("size") String size, @Param("userId") Long userId);
}
