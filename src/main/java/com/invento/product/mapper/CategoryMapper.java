package com.invento.product.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
	
	public Category updateDtoToCategory(CategoryDto dto, 
		LocalDateTime createdDate, String createdBy) {
		
		Category category = Category.builder()
				.id(dto.getId())
				.name(dto.getName())
				.deleted(false)
				.build();
		category.setCreatedDate(createdDate);
		category.setCreatedBy(createdBy);
		return category;
	}

	public List<Category> dtoToCategory(List<CategoryDto> dtos) {
		
		List<Category> categoryList = new ArrayList<>();
		categoryList = dtos.stream()
			.filter(d -> Strings.isNotEmpty(d.getName()))
			.map(d -> {
				return Category.builder()
						.id(d.getId())
						.name(d.getName())
						.deleted(false)
						.build();
			}).collect(Collectors.toList());
		return categoryList;
	}
	
	public List<CategoryDto> categoryToDto(List<Category> categories) {
		
		List<CategoryDto> dtoList = new ArrayList<>();
		dtoList = categories.stream()
			.map(c -> {
				return CategoryDto.builder()
						.id(c.getId())
						.name(c.getName())
						.createdBy(c.getCreatedBy())
						.createdDate(formatDate(c.getCreatedDate()))
						.updatedBy(c.getUpdatedBy())
						.updatedDate(formatDate(c.getUpdatedDate()))
						.build();
			}).collect(Collectors.toList());
		return dtoList;
	}
	
	private String formatDate(LocalDateTime date) {
		
		String dateString = null;
		if(Objects.nonNull(date)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			dateString = date.format(formatter);
		}
		return dateString;
	}
}
