package com.dhruv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Product;
import com.dhruv.request.CreateProductRequest;
import com.dhruv.response.ApiResponse;
import com.dhruv.service.ProductService;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	@PostMapping("/product/create")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws ProductException {
		Product newproduct = productService.createProduct(req);
		return new ResponseEntity<Product>(newproduct, HttpStatus.CREATED);
	}

	@PostMapping("/product/creates")
	public ResponseEntity<List<Product>> createProducts(@RequestBody List<CreateProductRequest> requests)
			throws ProductException {

		List<Product> products = new ArrayList<>();
		for (CreateProductRequest req : requests) {
			Product newProduct = productService.createProduct(req); // Assuming createProduct handles a single product
			products.add(newProduct);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.CREATED);
	}

	@DeleteMapping("/product/{product_id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long product_id) throws ProductException {
		productService.deleteProduct(product_id);
		ApiResponse res = new ApiResponse("Product is deleted successfully", true);
		return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);

	}

}
