package com.dhruv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Product;
import com.dhruv.service.ProductService;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
//	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
//			@RequestParam List<String> colors, @RequestParam List<String> size,
//			@RequestParam Integer minPrice, @RequestParam Integer maxPrice,
//			@RequestParam Integer minDiscount, @RequestParam String sort,
//			@RequestParam String stock, @RequestParam Integer pageNumber,
//			@RequestParam Integer pageSize)
//	{
//		Page<Product> res = productService.getAllProduct(category, colors, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
//		System.out.println("Complete Products");
//		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//	}
	public ResponseEntity<Page<Product>> getAllProduct(
	        @RequestParam(required = false) String category,
	        @RequestParam(required = false) List<String> colors,
	        @RequestParam(required = false) List<String> sizes,
	        @RequestParam(required = false) Integer minPrice,
	        @RequestParam(required = false) Integer maxPrice,
	        @RequestParam(required = false) Integer minDiscount,
	        @RequestParam(required = false) String sort,
	        @RequestParam(required = false) String stock,
	        @RequestParam(defaultValue = "0") Integer pageNumber,
	        @RequestParam(defaultValue = "10") Integer pageSize) throws ProductException {

		Page<Product> newproduct = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
	    
	    if (newproduct.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // or handle accordingly
	    }
	    
	    return new ResponseEntity<>(newproduct, HttpStatus.OK);
	}
	
	@GetMapping("/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException
	{
		Product product = productService.findProductById(productId);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	

//	@GetMapping("/search")
//	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q)
//	{
//		List<Product> products = productService.searchProduct(q);
//		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//	}
	
	

}
 