package com.invento.product.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;
import com.invento.product.util.ProductTestUtils;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

	@InjectMocks
	private ProductMapper productMapper;
	
	@InjectMocks
	private ProductTestUtils testUtils;
	
	@Test
	public void testDtoToProduct() {
		
		ProductDto dto = testUtils.getProductDto();
		Product product = productMapper.dtoToProduct(dto);
		
		Assertions.assertThat(product.getId()).isEqualTo(dto.getId());
		Assertions.assertThat(product.getProductName()).isEqualTo(dto.getProductName());
	}
	
	@Test
	public void testProductToDto() {
		
		Product product = testUtils.getProduct();
		ProductDto dto = productMapper.productToDto(product);
		
		Assertions.assertThat(dto.getId()).isEqualTo(product.getId());
		Assertions.assertThat(dto.getProductName()).isEqualTo(product.getProductName());
	}
	
	@Test
	public void testProductListToDtoList() {
		
		List<Product> products = testUtils.getProducts();
		List<ProductDto> productDtos = productMapper.productToDto(products);
		
		Assertions.assertThat(productDtos.get(0).getId())
			.isEqualTo(products.get(0).getId());
		Assertions.assertThat(productDtos.get(0).getProductName())
			.isEqualTo(products.get(0).getProductName());
	}
}
