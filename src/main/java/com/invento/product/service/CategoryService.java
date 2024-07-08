package com.invento.product.service;

import java.util.List;

import com.invento.product.dto.CategoryDto;

public interface CategoryService {
	
	public List<CategoryDto> getCategoryList();

	public void addCategory(List<CategoryDto> dto);
	
	public void updateCategory(CategoryDto dto);
	
	public void deleteCategory(String id);
}
