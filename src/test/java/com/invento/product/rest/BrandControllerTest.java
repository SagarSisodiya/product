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
import com.invento.product.dto.BrandDto;
import com.invento.product.rest.BrandController;
import com.invento.product.service.BrandService;
import com.invento.product.util.BrandTestUtils;

@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BrandService brandService;
	
	@InjectMocks
	private BrandTestUtils testUtils;
	
	@Test
	public void testGetProductList() throws Exception {
		
		List<BrandDto> brands = testUtils.getBrandDtos();
		
		when(brandService.getBrandList())
			.thenReturn(brands);
		
		ResultActions response = mockMvc.perform(get("/brand/getBrandList")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(brands.size())));
		
	}
	
	@Test
	public void testGetBrandList_notFound() throws Exception {
				
		when(brandService.getBrandList())
			.thenReturn(new ArrayList<>());
		
		ResultActions response = mockMvc.perform(get("/brand/getBrandList")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(0)));
	}
	
	@Test
	public void testAddBrand() throws Exception {
		
		List<BrandDto> brandDtos = testUtils.getBrandDtos();
		
		doNothing().when(brandService)
		.addBrand(ArgumentMatchers.anyList());
			
		
		ResultActions response = mockMvc.perform(post("/brand/addBrand")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(brandDtos)));
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())			
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Brands added successfully.")));
	}
	
	@Test
	public void testUpdateBrand() throws Exception {
		
		doNothing().when(brandService).updateBrand(Mockito.any());
		
		ResultActions response = mockMvc.perform(put("/brand/updateBrand")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(testUtils.getBrandDto())));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Brand updated successfully.")));
				
	}
	
	@Test
	public void testDeleteBrand() throws Exception {
		
		doNothing().when(brandService).updateBrand(Mockito.any());
		
		ResultActions response = mockMvc.perform(delete("/brand/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.param("id", "668bd9b802e26f7521c749fe"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Brand deleted successfully.")));
				
	}
}
