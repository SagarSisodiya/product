package com.invento.product.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
	
	public Product updateDtoToProduct(ProductDto dto, 
		LocalDateTime createdDate, String createdBy) {

		Product product = Product.builder()
				.id(dto.getId())
				.category(dto.getCategory())
				.brand(dto.getBrand())
				.spec(dto.getSpec())
				.imagename(dto.getImagename())
				.productName(dto.getProductName())
				.build();
		product.setCreatedDate(createdDate);
		product.setCreatedBy(createdBy);
		return product;
	}
	
	public ProductDto productToDto(Product product) {
		
		return ProductDto.builder()
				.id(product.getId())
				.createdBy(product.getCreatedBy())
				.createdDate(formatDate(product.getCreatedDate()))
				.updatedBy(product.getUpdatedBy())
				.updatedDate(formatDate(product.getUpdatedDate()))
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
					.createdBy(p.getCreatedBy())
					.createdDate(formatDate(p.getCreatedDate()))
					.updatedBy(p.getUpdatedBy())
					.updatedDate(formatDate(p.getUpdatedDate()))
					.category(p.getCategory())
					.brand(p.getBrand())
					.spec(p.getSpec())
					.imagename(p.getImagename())
					.productName(p.getProductName())
					.build();
		}).collect(Collectors.toList());
	}
	
	private String formatDate(LocalDateTime date) {
		
		String dateString = null;
		if(Objects.nonNull(date)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			dateString = date.format(formatter);
		}
		return dateString;
	}
}
