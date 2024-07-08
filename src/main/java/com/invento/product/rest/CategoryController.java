package com.invento.product.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invento.product.dto.CategoryDto;
import com.invento.product.dto.ResponseDto;
import com.invento.product.service.CategoryService;

@RestController
@RequestMapping(path="/category", produces= {MediaType.APPLICATION_JSON_VALUE})
public class CategoryController {

	private CategoryService categoryService;
	
	CategoryController(CategoryService categoryService) {
		
		this.categoryService = categoryService;
	}
	
	/**
	 * {@code GET /getCategoryList} : Get list of all categories
	 * 
	 *    
	 * @return ResponseEntity object with status code 200 {OK} and body with list of CategoryDto objects,
	 *         or with status code 404 {NOT FOUND} and body with an empty list.
	 */
	@GetMapping("/getCategoryList")
	public ResponseEntity<List<CategoryDto>> getCategoryList() {
		
		List<CategoryDto> dtoList = categoryService.getCategoryList();
		return (!CollectionUtils.isEmpty(dtoList))
			? ResponseEntity.status(HttpStatus.OK).body(dtoList)
			: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	}
	
	/**
	 * {@code POST /addCategory} : Add new category
	 * 
	 * @param dtos : Request payload containing category details to be stored
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 201 {Created} and a success message,
	 *         or throws InvalidRequestException with status 400 {Bad Request} and an error message.
	 */
	@PostMapping("/addCategory")
	public ResponseEntity<ResponseDto> addCategory(@RequestBody List<CategoryDto> dtos) {
		
		categoryService.addCategory(dtos);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Categories added successfully."));
	}
	
	/**
	 * {@code PUT /updateCategory} : Update an existing category object with matching id
	 * 
	 * @param dto : Request payload containing category details to be updated.
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 *         if request dto object is null
	 *         or throws CategoryNotFoundException with status code 404 {NOT_FOUND} and an error message
	 *         if category is not found with matching id in request object
	 */
	@PutMapping("/updateCategory")
	public ResponseEntity<ResponseDto> updateCategory(@RequestBody CategoryDto dto) {
		
		categoryService.updateCategory(dto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Category updated successfully."));
	}
	
	/**
	 * {@Code DELETE /delete} : Delete a category object
	 * 
	 * @param id :  unique identifier value for specific brand object
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or throws CategoryNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id
	 *         or throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
     *         if id is null/empty
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteCategory(@RequestParam String id) {
		
		categoryService.deleteCategory(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Category deleted successfully."));
	}
}