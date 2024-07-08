package com.invento.product.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.invento.product.dto.CategoryDto;
import com.invento.product.model.Category;

@Component
public class CategoryMapper {
	
	public Category dtoToCategory(CategoryDto dto) {
		
		return Category.builder()
				.id(dto.getId())
				.name(dto.getName())
				.deleted(false)				
				.build();
	}

	public List<Category> dtoTocategory(List<CategoryDto> dtos) {
		
		List<Category> categoryList = new ArrayList<>();
		categoryList = dtos.stream()
			.filter(d -> Strings.isNotEmpty(d.getName()))
			.map(d -> {
				return Category.builder()
						.name(d.getName())
						.deleted(false)
						.build();})
			.collect(Collectors.toList());
		return categoryList;
	}
	
	public List<CategoryDto> categoryToDto(List<Category> categories) {
		
		List<CategoryDto> dtoList = new ArrayList<>();
		dtoList = categories.stream()
			.map(c -> {
				return CategoryDto.builder()
						.id(c.getId())
						.name(c.getName())
						.build();})
			.collect(Collectors.toList());
		return dtoList;
	}
}
