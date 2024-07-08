package com.invento.product.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.invento.product.dto.CategoryDto;
import com.invento.product.model.Category;

@Component
public class CategoryTestUtils {
	
	public Category getCategory() {
		
		return Category.builder()
				.id("668be8c1686b4325821c777a")
				.name("VRAM")
				.deleted(false)
				.build();
	}
	
	public List<Category> getCategories() {
		
		Category category1 = Category.builder()
				.id("668be8c1686b4325821c777a")
				.name("VRAM")
				.deleted(false)
				.build();
		
		Category category2 = Category.builder()
				.id("668be8c1686b4325821c777b")
				.name("SSD")
				.deleted(false)
				.build();
		
		Category category3 = Category.builder()
				.id("668be8c1686b4325821c777c")
				.name("CPU")
				.deleted(false)
				.build();
		
		return Arrays.asList(category1, category2, category3);
	}
	
	public CategoryDto getCategoryDto() {
		
		return CategoryDto.builder()
				.id("668be8c1686b4325821c777a")
				.name("VRAM")
				.build();
	}
	
	public List<CategoryDto> getCategoryDtos() {
		
		CategoryDto category1 = CategoryDto.builder()
				.id("668be8c1686b4325821c777a")
				.name("VRAM")
				.build();
		
		CategoryDto category2 = CategoryDto.builder()
				.id("668be8c1686b4325821c777b")
				.name("SSD")
				.build();
		
		CategoryDto category3 = CategoryDto.builder()
				.id("668be8c1686b4325821c777c")
				.name("CPU")
				.build();
		
		return Arrays.asList(category1, category2, category3);
	}
}
