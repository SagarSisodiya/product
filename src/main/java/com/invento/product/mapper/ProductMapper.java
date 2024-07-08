package com.invento.product.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;

@Component
public class ProductMapper {

	public Product dtoToProduct(ProductDto dto) {

		return Product.builder()
			.id(dto.getId())
			.category(dto.getCategory())
			.brand(dto.getBrand())
			.spec(dto.getSpec())
			.imagename(dto.getImagename())
			.productName(dto.getProductName())
			.build();
	}
	
	public ProductDto productToDto(Product product) {
		
		return ProductDto.builder()
				.id(product.getId())
				.category(product.getCategory())
				.brand(product.getBrand())
				.spec(product.getSpec())
				.imagename(product.getImagename())
				.productName(product.getProductName())
				.build();
	}
	
	public List<ProductDto> productToDto(List<Product> products) {
		
		return products.stream().map(p -> {
			return ProductDto.builder()
					.id(p.getId())
					.category(p.getCategory())
					.brand(p.getBrand())
					.spec(p.getSpec())
					.imagename(p.getImagename())
					.productName(p.getProductName())
					.build();
		}).collect(Collectors.toList());
	}
}
