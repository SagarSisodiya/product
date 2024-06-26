package com.invento.product.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

/**
 * Rest controller for managing {@link com.invento.product.model.Product}.
 */

@RestController
@RequestMapping(path="/product", produces= {MediaType.APPLICATION_JSON_VALUE}	)
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * {@code GET /getProductList} : Get list of all product that are not deleted
	 * 
	 * @param pageNumber : Specific page number in paginated response of all products
	 * @param pageSize : Number of product objects in each page
	 * @param field : Field used for sorting in product object
	 * @param sortDirection {@value ASC/DESC} : Sorting order - {ASC} for ascending and {DESC} for descending
	 *    
	 * @return ResponseEntity object with status code 200 {OK} and body with list of Product objects,
	 *         or with status code 404 {NOT FOUND} and body with and empty list.
	 */
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
	
	/**
	 * {@code GET /getProductById} : Get product details with the matching id
	 * 
	 * @param id : unique identifier value for specific product object
	 * 
	 * @return ResponseEntity object with status code 200 {OK} and body with product object,
	 *         or throws ProductNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id.
	 */
	@GetMapping("/getProductById")
	public ResponseEntity<Product> getProductById(@RequestParam String id) {

		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
	}
	
	/**
	 * {@code GET /search} : Search products in mongodb database
	 *  
	 * @param keyword : String values to be search over the indexed fields in mongodb
	 * @param limit : Number of objects to be fetched
	 * 
	 * @return ResponseEntity object with status 200 {OK} and body with list of Product objects,
	 *         or throws ProductNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided keyword/s.
	 */         
	@GetMapping("/search")
	public ResponseEntity<List<Document>> searchProduct(
			@RequestParam String keyword, @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_VALUE) int limit) {
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.searchProduct(keyword, limit));
	}

	/**
	 * {@code POST /addProduct} : Add new product
	 * 
	 * @param dto : Request payload containing product details to be stored
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 201 {Created} and a success message,
	 *         or with status 400 {Bad Request} and a failure message.
	 */
	@PostMapping("/addProduct")
	public ResponseEntity<ResponseDto> addProduct(@RequestBody ProductDto dto) {

		return productService.addProduct(dto)
				? ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Product created successfully."))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Failed to create product."));
	}
	
	/**
	 * {@code PUT /updateProduct}
	 * 
	 * @param dto : Request payload containing product details to be updated
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or with status 304 {NOT MODIFIED} and a failure message,
	 *         or throws ProductNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id in dto.
	 */
	@PutMapping("/updateProduct")
	public ResponseEntity<ResponseDto> updateProduct(@RequestBody ProductDto dto) {

		return productService.updateProduct(dto)
				? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Product updated successfully."))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseDto("Failed to update product."));
	}
	
	/**
	 * {@Code DELETE /delete} : Soft delete a product in mongodb by setting value of deleted field to 'true'.
	 * 
	 * @param id :  unique identifier value for specific product object
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 201 {Created} and a success message,
	 *         or with status 304 {NOT MODIFIED} and a failure message,
	 *         or throws ProductNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteProduct(@RequestParam String id) {

		return productService.deleteProductById(id)
				? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Product deleted successfully."))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseDto("Failed to delete product."));
	}
}