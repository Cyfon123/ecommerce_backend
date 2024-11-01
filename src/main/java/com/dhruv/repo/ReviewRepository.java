package com.dhruv.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhruv.model.Rating;
import com.dhruv.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("Select r From Review r Where r.product.id=:productId")
	public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
