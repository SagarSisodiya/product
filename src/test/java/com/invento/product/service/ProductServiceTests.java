package com.invento.product.service;

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

import com.invento.product.commons.TestUtils;
import com.invento.product.dto.ProductDto;
import com.invento.product.mapper.ProductMapper;
import com.invento.product.model.Product;
import com.invento.product.repository.ProductRepo;
import com.invento.product.service.impl.ProductServiceImpl;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

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
	private TestUtils testUtils;

	@Test
	public void testGetProductList() {
		
		Page<Product> productsPage = new PageImpl<>(testUtils.getProducts());
		
		when(productRepo.findAllByDeleted(Mockito.anyBoolean(), Mockito.any(PageRequest.class)))
			.thenReturn(productsPage);
		
		List<Product> products = 
			productService.getProductList(0, 5, "category", Sort.Direction.DESC);
		
		Assertions.assertThat(products).isNotEmpty();
	}
		
	@Test
	public void testGetProductByIdAndDeltedTrue() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(product));
					
		Product productRes = 
			productService.getProductById("6639dd31e5b7490f597ee80f");
		
		Assertions.assertThat(productRes).isNotNull();
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
	public void testUpdateProduct() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.anyBoolean()))
			.thenReturn(Optional.ofNullable(product));
		when(productMapper.dtoToProduct(Mockito.any(ProductDto.class)))
			.thenReturn(product);
				
		Boolean updated = productService.updateProduct(testUtils.getProductDto());
		
		Assertions.assertThat(updated).isTrue();
	}
	
	@Test
	public void testDeleteProductById() {
		
		Product product = testUtils.getProduct();
		
		when(productRepo.findByIdAndDeleted(Mockito.anyString(), Mockito.eq(false)))
		.thenReturn(Optional.ofNullable(product));
		
		Boolean deleted = productService.deleteProductById("6639dd31e5b7490f597ee80f");
		
		Assertions.assertThat(deleted).isTrue();
	}
}
