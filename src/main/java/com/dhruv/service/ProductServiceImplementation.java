package com.dhruv.service;

import java.util.*;
import java.time.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dhruv.exception.ProductException;
import com.dhruv.model.Category;
import com.dhruv.model.Product;
import com.dhruv.repo.CategoryRepository;
import com.dhruv.repo.ProductRepository;
import com.dhruv.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

		if (topLevel == null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			topLevel = categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		if (secondLevel == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setLevel(2);
			secondLevel = categoryRepository.save(secondLevelCategory);
		}
		
		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		if (thirdLevel == null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setLevel(3);
			thirdLevel = categoryRepository.save(thirdLevelCategory);
		}
		
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountPercent(req.getDiscountPercent());
		product.setDiscountPrice(req.getDiscountPrice());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSizes());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product = findProductById(productId);
		
		if(req.getQuantity() != 0)
			product.setQuantity(req.getQuantity());
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt = productRepository.findById(id);
		if(opt.isPresent())
			return opt.get();
		throw new ProductException("Product Not found with id :- " + id);
	}

	@Override
	public List<Product> findProducyByCategory(String category) {
		// TODO Auto-generated method stub
//		List<Product> products = productRepository.findProducyByCategory(String category);
		
		
		
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		 if (!colors.isEmpty()) {
	            products = products.stream()
	                .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
	                .collect(Collectors.toList());
	        }
		 
		 if(stock != null)
		 {
			 if(stock.equalsIgnoreCase("in_stock"))
				 products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
			 else if(stock.equalsIgnoreCase("out_of_stock"))
				 products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
		 }
		 
		 int startIndex = (int) pageable.getOffset();
		 int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
		 
		 List<Product> pageContent = products.subList(startIndex, endIndex);
		 
		 Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		return filteredProducts;
	}
	
	

}
