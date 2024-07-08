package com.invento.product.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.invento.product.dto.CategoryDto;
import com.invento.product.exception.InvalidRequestException;
import com.invento.product.mapper.CategoryMapper;
import com.invento.product.model.Category;
import com.invento.product.repository.CategoryRepo;
import com.invento.product.service.impl.CategoryServiceImpl;
import com.invento.product.util.CategoryTestUtils;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepo categoryRepo;
	
	@Mock
	private MongoCollection<Document> collection;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private CategoryMapper categoryMapper;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	@InjectMocks
	private CategoryTestUtils testUtils;
	
	@Test
	public void testGetCategoryList() {
		
		when(categoryRepo.findAllByDeleted(Mockito.anyBoolean()))
			.thenReturn(testUtils.getCategories());
		when(categoryMapper.categoryToDto(Mockito.anyList()))
			.thenReturn(testUtils.getCategoryDtos());
		
		List<CategoryDto> dtoList = categoryService.getCategoryList();
		
		Assertions.assertThat(dtoList.size()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCategoryList_notFound() {
		
		when(categoryRepo.findAllByDeleted(Mockito.anyBoolean()))
			.thenReturn(new ArrayList<>());
		
		List<CategoryDto> dtoList = categoryService.getCategoryList();
		
		Assertions.assertThat(dtoList.size()).isEqualTo(0);
	}
	
	@Test
	public void testAddCategory() {
		
		when(categoryMapper.dtoToCategory(Mockito.anyList()))
			.thenReturn(testUtils.getCategories());
		when(categoryRepo.saveAll(Mockito.anyList()))
			.thenReturn(testUtils.getCategories());
		
		categoryService.addCategory(testUtils.getCategoryDtos());
		
		verify(categoryMapper, times(1)).dtoToCategory(Mockito.anyList());
		verify(categoryRepo, times(1)).saveAll(Mockito.anyList());
	}
	
	@Test
	public void testAddCategory_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class, 
				() -> categoryService.addCategory(new ArrayList<>()));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("Dto list is null/empty.");
	}
	
	@Test
	public void testUpdateCategory() {
		
		Category category = testUtils.getCategory();
		
		when(categoryRepo.findByIdAndDeleted(Mockito.any(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(category));
		when(categoryMapper.updateDtoToCategory(Mockito.any(CategoryDto.class), 
			Mockito.any(), Mockito.any())).thenReturn(category);
		when(categoryRepo.save(Mockito.any())).thenReturn(category);
		
		categoryService.updateCategory(testUtils.getCategoryDto());
		
		verify(categoryRepo, times(1)).findByIdAndDeleted(
			Mockito.anyString(), Mockito.anyBoolean());
		verify(categoryMapper, times(1)).updateDtoToCategory(Mockito.any(CategoryDto.class), 
				Mockito.any(), Mockito.any());
		verify(categoryRepo, times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUpdateCategory_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class,
				() -> categoryService.updateCategory(null));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("Dto is null.");
	}
	
	@Test
	public void testDeleteCategory() {
		
		Category category = testUtils.getCategory();
		
		when(categoryRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(category));
		when(categoryRepo.save(Mockito.any())).thenReturn(category);
		
		categoryService.deleteCategory("668bd9b802e26f7521c749fe");
		
		verify(categoryRepo, times(1)).findByIdAndDeleted(
			Mockito.anyString(), Mockito.anyBoolean());
		verify(categoryRepo, times(1)).save(Mockito.any());
	}
	
	@Test
	public void testDeleteCategory_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class,
				() -> categoryService.deleteCategory(null));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("id is null/empty.");
	}
}
