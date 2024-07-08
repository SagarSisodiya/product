package com.invento.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.invento.product.dto.CategoryDto;
import com.invento.product.exception.CategoryNotFoundException;
import com.invento.product.exception.InvalidRequestException;
import com.invento.product.mapper.CategoryMapper;
import com.invento.product.model.Category;
import com.invento.product.repository.CategoryRepo;
import com.invento.product.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepo categoryRepo;
	
	private CategoryMapper categoryMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	CategoryServiceImpl(CategoryRepo categoryRepo, CategoryMapper categoryMapper) {
		
		this.categoryRepo = categoryRepo;
		this.categoryMapper = categoryMapper;
	}
	
	/**
	 * Get list of Category details objects where value of deleted property is false
	 * 
	 * @return List of CategoryDto
	 * 
	 */
	@Override
	public List<CategoryDto> getCategoryList() {
		
		List<CategoryDto> dtoList = new ArrayList<>();
		List<Category> categoryList = categoryRepo.findAllByDeleted(false);
		if(!CollectionUtils.isEmpty(categoryList)) {
			dtoList = categoryMapper.categoryToDto(categoryList);
		}
		return dtoList;
	}
	
	/**
	 * Add new category objects
	 * 
	 * @param dtos: Category details to be stored
	 * 
	 * throws InvalidRequestException if dtos is null/empty
	 * 
	 */
	@Override
	public void addCategory(List<CategoryDto> dtos) {
		
		if(CollectionUtils.isEmpty(dtos)) {
			throw new InvalidRequestException("Dto list is empty/null");
		}
		List<Category> categoryList = categoryMapper.dtoTocategory(dtos);
		categoryRepo.saveAll(categoryList);
		log.info("Categories saved successfully.");
	}

	/**
	 * Update existing category object where id matches the provided id in request object
	 * 
	 * @param dto : Request payload containing category details to be updated
	 * 
	 * throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 * if request dto object is null
	 * 
	 * throws CategoryNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 * 
	 */
	@Override
	public void updateCategory(CategoryDto dto) {
		
		if(Objects.isNull(dto)) {
			throw new InvalidRequestException("Dto is null.");
		}
		Category category = categoryRepo.findByIdAndDeleted(dto.getId(), false).orElseThrow(
			() -> new CategoryNotFoundException("Category not found with id: " + dto.getId()));
		category = categoryMapper.dtoToCategory(dto);
		category = categoryRepo.save(category);
		log.info("Category updated successfully. Category: {}", category);
	}

	/**
	 * Delete a category object
	 * 
	 * @param id :  unique identifier value for specific category object
	 * 
	 * throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 * if id is null/empty
	 * 
	 * throws CategoryNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 * 
	 */
	@Override
	public void deleteCategory(String id) {
		
		if(Strings.isEmpty(id)) {
			throw new InvalidRequestException("id is null/empty");
		}
		Category category = categoryRepo.findByIdAndDeleted(id, false).orElseThrow(
			() -> new CategoryNotFoundException("Category not found with id: " + id));
		category.setDeleted(true);
		categoryRepo.save(category);
		log.info("Category deleted with id: {}", id);
	}

}
