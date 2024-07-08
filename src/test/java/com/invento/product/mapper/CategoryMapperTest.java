package com.invento.product.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invento.product.dto.CategoryDto;
import com.invento.product.model.Category;
import com.invento.product.util.CategoryTestUtils;

@ExtendWith(MockitoExtension.class)
public class CategoryMapperTest {

	@InjectMocks
	private CategoryMapper categoryMapper;
	
	@InjectMocks
	private CategoryTestUtils testUtils;
	
	@Test
	public void testDtoTocategory() {
		
		CategoryDto dto = testUtils.getCategoryDto();
		Category category = categoryMapper.dtoToCategory(dto);
		
		Assertions.assertThat(category.getId()).isEqualTo(dto.getId());
		Assertions.assertThat(category.getName()).isEqualTo(dto.getName());
	}
	
	@Test
	public void testDtoListToCategoryList() {
		
		List<CategoryDto> dtos = testUtils.getCategoryDtos();
		List<Category> categoryList = categoryMapper.dtoToCategory(dtos);
		
		Assertions.assertThat(categoryList.get(0).getId())
			.isEqualTo(dtos.get(0).getId());
		Assertions.assertThat(categoryList.get(0).getName())
			.isEqualTo(dtos.get(0).getName());
	}
	
	@Test
	public void testCategoryListToDtoList() {
		
		List<Category> categoryList = testUtils.getCategories();
		List<CategoryDto> dtos = categoryMapper.categoryToDto(categoryList);
		
		Assertions.assertThat(dtos.get(0).getId()).isEqualTo(categoryList.get(0).getId());
		Assertions.assertThat(dtos.get(0).getName()).isEqualTo(categoryList.get(0).getName());
		
	}
}
