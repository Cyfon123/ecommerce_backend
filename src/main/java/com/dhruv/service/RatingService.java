package com.dhruv.service;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Rating;
import com.dhruv.model.User;
import com.dhruv.request.RatingRequest;

import java.util.*;

public interface RatingService {
	
	public Rating creatRating(RatingRequest req, User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}

