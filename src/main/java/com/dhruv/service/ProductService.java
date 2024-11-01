package com.dhruv.service;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Product;
import com.dhruv.request.CreateProductRequest;
import java.util.*;

import org.springframework.data.domain.Page;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId, Product product) throws ProductException;
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProducyByCategory(String category);
	
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

}
