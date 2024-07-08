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

import com.invento.product.dto.BrandDto;
import com.invento.product.dto.ResponseDto;
import com.invento.product.service.BrandService;


@RestController
@RequestMapping(path="/brand", produces= {MediaType.APPLICATION_JSON_VALUE})
public class BrandController {

	private BrandService brandService;
	
	public BrandController(BrandService brandService) {
		
		this.brandService = brandService;
	}
	
	/**
	 * {@code GET /getBrandList} : Get list of all brands
	 * 
	 *    
	 * @return ResponseEntity object with status code 200 {OK} and body with list of BrandDto objects,
	 *         or with status code 404 {NOT FOUND} and body with an empty list.
	 */
	@GetMapping("/getBrandList")
	public ResponseEntity<List<BrandDto>> getBrandList() {
		
		List<BrandDto> dtoList = brandService.getBrandList();
		return (!CollectionUtils.isEmpty(dtoList))
			? ResponseEntity.status(HttpStatus.OK).body(dtoList)
			: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	}
	
	/**
	 * {@code POST /addBrand} : Add new brand
	 * 
	 * @param dtos : Request payload containing brand details to be stored
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 201 {Created} and a success message,
	 *         or throws InvalidRequestException with status 400 {Bad Request} and an error message.
	 */
	@PostMapping("/addBrand")
	public ResponseEntity<ResponseDto> addBrand(@RequestBody List<BrandDto> dtos) {
		
		brandService.addBrand(dtos);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Brands added successfully."));
	}
	
	/**
	 * {@code PUT /updateBrand} : Update an existing brand object with matching id
	 * 
	 * @param dto : Request payload containing brand details to be updated.
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 *         if request dto object is null
	 *         or throws BrandNotFoundException with status code 404 {NOT_FOUND} and an error message
	 *         if brand is not found with matching id in request object
	 */
	@PutMapping("/updateBrand")
	public ResponseEntity<ResponseDto> updateBrand(@RequestBody BrandDto dto) {
		
		brandService.updateBrand(dto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Brand updated successfully."));
	}
	
	/**
	 * {@Code DELETE /delete} : Delete a brand object
	 * 
	 * @param id :  unique identifier value for specific brand object
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or throws BrandNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id
	 *         or throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
     *         if id is null/empty
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteBrand(@RequestParam String id) {
		
		brandService.deleteBrand(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Brand deleted successfully."));
	}
}