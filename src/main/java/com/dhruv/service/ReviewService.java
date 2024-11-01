package com.dhruv.service;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Review;
import com.dhruv.model.User;
import com.dhruv.request.ReviewRequest;

import java.util.*;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);

}
