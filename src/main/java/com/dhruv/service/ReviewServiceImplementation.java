package com.dhruv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Product;
import com.dhruv.model.Review;
import com.dhruv.model.User;
import com.dhruv.repo.ProductRepository;
import com.dhruv.repo.ReviewRepository;
import com.dhruv.request.ReviewRequest;

import java.time.*;

public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	private  ReviewRepository reviewRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreateAt(LocalDateTime.now());
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		return reviewRepository.getAllProductsReview(productId);
	}

}
