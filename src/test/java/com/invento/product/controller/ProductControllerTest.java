package com.invento.product.controller;

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
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invento.product.commons.TestUtils;
import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;
import com.invento.product.service.ProductService;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService productService;
	
	@InjectMocks
	private TestUtils testUtils;

	@Test
	public void testGetProductList() throws Exception {
		
		List<Product> products = testUtils.getProducts();
		
		when(productService.getProductList(Mockito.anyInt(), Mockito.anyInt(), 
				Mockito.anyString(), Mockito.any(Sort.Direction.class)))
			.thenReturn(products);
		
		ResultActions response = mockMvc.perform(get("/product/getProductList")
			.contentType(MediaType.APPLICATION_JSON)
			.param("pageNumber", "0")
			.param("pageSize", "5")
			.param("field", "category")
			.param("sortDirection", "DESC"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(products.size())));
		
	}
	
	@Test
	public void testGetProductList_notFound() throws Exception {
				
		when(productService.getProductList(Mockito.anyInt(), Mockito.anyInt(), 
				Mockito.anyString(), Mockito.any(Sort.Direction.class)))
			.thenReturn(new ArrayList<>());
		
		ResultActions response = mockMvc.perform(get("/product/getProductList")
			.contentType(MediaType.APPLICATION_JSON)
			.param("pageNumber", "0")
			.param("pageSize", "5")
			.param("field", "category")
			.param("sortDirection", "DESC"));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.size()", CoreMatchers.is(0)));
		
	}
	
	@Test
	public void testGetProductById() throws Exception {
		
		Product product = testUtils.getProduct();
		
		when(productService.getProductById(Mockito.anyString())).thenReturn(product);
		
		ResultActions response = mockMvc.perform(get("/product/getProductById")
			.contentType(MediaType.APPLICATION_JSON)
			.param("id", "6639dd31e5b7490f597ee80f"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.productName", CoreMatchers.is(product.getProductName())));
		
	}
	
	@Test
	public void testAddProduct() throws Exception {
		
		ProductDto productDto = testUtils.getProductDto();
		
		when(productService.addProduct(ArgumentMatchers.any()))
			.thenReturn(true);
		
		ResultActions response = mockMvc.perform(post("/product/addProduct")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(productDto)));
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())			
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Product created successfully.")));
	}
	
	@Test
	public void testAddProduct_failed() throws Exception {
		
		when(productService.addProduct(ArgumentMatchers.any()))
			.thenReturn(false);
		
		ResultActions response = mockMvc.perform(post("/product/addProduct")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(testUtils.getProductDto())));
		
		response.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Failed to create product.")));
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
		
		ProductDto productDto = testUtils.getProductDto();
				
		when(productService.updateProduct(Mockito.any())).thenReturn(true);
		
		ResultActions response = mockMvc.perform(put("/product/updateProduct")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(productDto)));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Product updated successfully.")));
	}
	
	@Test
	public void testUpdateProduct_failed() throws Exception {
		
		ProductDto productDto = testUtils.getProductDto();
				
		when(productService.updateProduct(Mockito.any())).thenReturn(false);
		
		ResultActions response = mockMvc.perform(put("/product/updateProduct")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(productDto)));
		
		response.andExpect(MockMvcResultMatchers.status().isNotModified())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Failed to update product.")));
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
						
		when(productService.deleteProductById(Mockito.anyString())).thenReturn(true);
		
		ResultActions response = mockMvc.perform(delete("/product/delete")
			.contentType(MediaType.APPLICATION_JSON)
			.param("id", "6639dd31e5b7490f597ee80f"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Product deleted successfully.")));
	}
	
	@Test
	public void testDeleteProduct_failed() throws Exception {
						
		when(productService.deleteProductById(Mockito.anyString())).thenReturn(false);
		
		ResultActions response = mockMvc.perform(delete("/product/delete")
			.contentType(MediaType.APPLICATION_JSON)
			.param("id", "6639dd31e5b7490f597ee80f"));
		
		response.andExpect(MockMvcResultMatchers.status().isNotModified())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Failed to delete product.")));
	}
}
