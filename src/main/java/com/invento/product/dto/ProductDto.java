package com.invento.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto{

	private String id;

	private String category;

	private String brand;
	
	private String productName;
	
	private String spec;
	
	private String imagename;
}
