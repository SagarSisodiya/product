package com.invento.product.controller;

import java.util.List;

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
import com.invento.product.dto.ResponseDto;
import com.invento.product.model.Product;
import com.invento.product.service.ProductService;
import com.invento.product.util.Constants;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getProductList")
	public ResponseEntity<List<Product>> getProductList(
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_VALUE) int pageNumber,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_VALUE) int pageSize,
			@RequestParam(defaultValue = Constants.DEFAULT_CATEGORY) String field,
      @RequestParam(defaultValue = Constants.DESC) Sort.Direction sortDirection) {

		List<Product> products = productService.getProductList(pageNumber, pageSize, field, sortDirection);
		return (products.size() > 0) 
				? ResponseEntity.status(HttpStatus.OK).body(products)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
	}

	@GetMapping("/getProductById")
	public ResponseEntity<Product> getProductById(@RequestParam String id) {

		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Document>> searchProduct(
			@RequestParam String keyword, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_VALUE) int limit) {
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.searchProduct(keyword, limit));
	}

	@PostMapping("/addProduct")
	public ResponseEntity<ResponseDto> addProduct(@RequestBody ProductDto dto) {

		return productService.addProduct(dto)
				? ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Product created successfully."))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed to create product."));
	}
	
	@PutMapping("/updateProduct")
	public ResponseEntity<ResponseDto> updateProduct(@RequestBody ProductDto dto) {

		return productService.updateProduct(dto)
				? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Product updated successfully."))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseDto("Failed to update product."));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteProduct(@RequestParam String id) {

		return productService.deleteProductById(id)
				? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Product deleted successfully."))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseDto("Failed to delete product."));
	}
}
