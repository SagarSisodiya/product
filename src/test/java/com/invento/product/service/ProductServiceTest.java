package com.invento.product.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.invento.product.dto.ProductDto;
import com.invento.product.exception.ProductNotFoundException;
import com.invento.product.mapper.ProductMapper;
import com.invento.product.model.Product;
import com.invento.product.repository.ProductRepo;
import com.invento.product.service.impl.ProductServiceImpl;
import com.invento.product.util.TestConstants;
import com.invento.product.util.ProductTestUtils;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	private ProductRepo productRepo;
	
	@Mock
	private MongoCollection<Document> collection;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
	private ProductMapper productMapper;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@InjectMocks
	private ProductTestUtils testUtils;

	@Test
	public void testGetProductList() {
		
		Page<Product> productsPage = new PageImpl<>(testUtils.getProducts());
		
		when(productRepo.findAllByDeleted(Mockito.anyBoolean(), Mockito.any(PageRequest.class)))
			.thenReturn(productsPage);
		
		when(productMapper.productToDto(Mockito.anyList())).thenReturn(testUtils.getProductDtos());
		
		List<ProductDto> products = 
				productService.getProductList(0, 5, "category", Sort.Direction.DESC);
		
		Assertions.assertThat(products).isNotEmpty();
	}
		
	@Test
	public void testGetProductByIdAndDeltedFalse() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(product));
		
		when(productMapper.productToDto(Mockito.any(Product.class))).thenReturn(testUtils.getProductDto());
					
		ProductDto productRes = 
			productService.getProductById(TestConstants.PRODUCT_ID);
		
		Assertions.assertThat(productRes).isNotNull();
	}
	
	@Test
	public void testGetProductByIdAndDeltedTrue() {
		
		doThrow(new ProductNotFoundException("No product found with id: " + TestConstants.PRODUCT_ID))
			.when(productRepo).findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean());
		
		Exception ex = assertThrows(ProductNotFoundException.class, 
				() -> productService.getProductById(TestConstants.PRODUCT_ID));
		
		Assertions.assertThat(ex.getMessage())
			.isEqualTo("No product found with id: " + TestConstants.PRODUCT_ID);
	}
	
	@Test
	public void testAddProduct() {
		
		Product product = testUtils.getProduct();
		
		when(productMapper.dtoToProduct(Mockito.any(ProductDto.class)))
			.thenReturn(product);
		when(productRepo.save(product)).thenReturn(product);
		
		Boolean created = productService.addProduct(testUtils.getProductDto());
		
		Assertions.assertThat(created).isTrue();
	}
	
	@Test
	public void testUpdateProductAndDeletedFalse() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(product));
		when(productMapper.updateDtoToProduct(Mockito.any(ProductDto.class), 
			Mockito.any(), Mockito.any())).thenReturn(product);
				
		productService.updateProduct(testUtils.getProductDto());
		
		verify(productRepo, times(1)).findByIdAndDeleted(
				Mockito.anyString(), Mockito.anyBoolean());
		verify(productMapper, times(1)).updateDtoToProduct(Mockito.any(ProductDto.class), 
				Mockito.any(), Mockito.any());
		verify(productRepo, times(1)).save(Mockito.any(Product.class));
	}
	
	@Test
	public void testDeleteProductByIdAndDeletedFalse() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.eq(false)))
		.thenReturn(Optional.ofNullable(product));
		
		productService.deleteProductById(TestConstants.PRODUCT_ID);
		
		verify(productRepo,times(1)).findByIdAndDeleted(Mockito.anyString(), Mockito.eq(false));
		verify(productRepo, times(1)).save(Mockito.any(Product.class));
	}
}
