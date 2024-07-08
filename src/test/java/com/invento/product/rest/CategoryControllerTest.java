package com.invento.product.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invento.product.dto.CategoryDto;
import com.invento.product.rest.CategoryController;
import com.invento.product.service.CategoryService;
import com.invento.product.util.CategoryTestUtils;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryTestUtils testUtils;
	
	@Test
	public void testGetProductList() throws Exception {
		
		List<CategoryDto> categorys = testUtils.getCategoryDtos();
		
		when(categoryService.getCategoryList())
			.thenReturn(categorys);
		
		ResultActions response = mockMvc.perform(get("/category/getCategoryList")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(categorys.size())));
		
	}
	
	@Test
	public void testGetCategoryList_notFound() throws Exception {
				
		when(categoryService.getCategoryList())
			.thenReturn(new ArrayList<>());
		
		ResultActions response = mockMvc.perform(get("/category/getCategoryList")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(0)));
	}
	
	@Test
	public void testAddCategory() throws Exception {
		
		List<CategoryDto> categoryDtos = testUtils.getCategoryDtos();
		
		doNothing().when(categoryService)
		.addCategory(ArgumentMatchers.anyList());
			
		
		ResultActions response = mockMvc.perform(post("/category/addCategory")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(categoryDtos)));
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())			
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Categories added successfully.")));
	}
	
	@Test
	public void testUpdateCategory() throws Exception {
		
		doNothing().when(categoryService).updateCategory(Mockito.any());
		
		ResultActions response = mockMvc.perform(put("/category/updateCategory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(testUtils.getCategoryDto())));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Category updated successfully.")));
				
	}
	
	@Test
	public void testDeleteCategory() throws Exception {
		
		doNothing().when(categoryService).updateCategory(Mockito.any());
		
		ResultActions response = mockMvc.perform(delete("/category/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.param("id", "668bd9b802e26f7521c749fe"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Category deleted successfully.")));
				
	}
}
