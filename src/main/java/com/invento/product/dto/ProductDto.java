package com.invento.product.dto;

import com.invento.product.enums.Brand;
import com.invento.product.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto{

	private String id;

	private ProductCategory category;

	private Brand brand;
	
	private String productName;
	
	private String spec;
	
	private String imagename;
}
