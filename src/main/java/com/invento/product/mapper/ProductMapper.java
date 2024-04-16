package com.invento.product.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.invento.product.dto.ProductDto;
import com.invento.product.model.Product;

@Component
public class ProductMapper {

	public Product dtoToProduct(ProductDto dto) {

		Product product = new Product();
		if (Objects.nonNull(dto)) {
			product.setId(dto.getId());
			product.setCategory(dto.getCategory());
			product.setBrand(dto.getBrand());
			product.setSpec(dto.getSpec());
			product.setImagename(dto.getImagename());
			product.setProductName(dto.getProductName());
		}
		return product;
	}
}
