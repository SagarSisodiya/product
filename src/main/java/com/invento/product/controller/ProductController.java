package com.invento.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;
import com.invento.product.service.ProductService;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getProductList")
	public ResponseEntity<List<Product>> getProductList(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "category") String field,
      @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {

		List<Product> products = productService.getProductList(pageNumber, pageSize, field, sortDirection);
		return (products.size() > 0) 
				? ResponseEntity.status(HttpStatus.OK).body(products)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
	}

	@GetMapping("/getProductById")
	public ResponseEntity<Product> getProductById(@RequestParam String id) {

		Optional<Product> productOp = productService.getProductById(id);
		return (productOp.isPresent()) 
				? ResponseEntity.status(HttpStatus.OK).body(productOp.get())
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product());
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Document>> searchProduct(
			@RequestParam String keyword, @RequestParam(defaultValue = "5") int limit) {
		
		List<Document> document = productService.searchProduct(keyword, limit);
		return (Collections.isEmpty(document))
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>())
				: ResponseEntity.status(HttpStatus.OK).body(document);
				
	}

	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductDto dto) {

		return productService.addProduct(dto)
				? ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.")
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create product.");
	}
	
	@PutMapping("/updateProduct")
	public ResponseEntity<String> updateProduct(@RequestBody ProductDto dto) {

		return productService.updateProduct(dto)
				? ResponseEntity.status(HttpStatus.OK).body("Product update successfully.")
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to update product.");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteProduct(@RequestParam String id) {

		return productService.deleteProductById(id)
				? ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.")
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to delete product.");
	}
}
