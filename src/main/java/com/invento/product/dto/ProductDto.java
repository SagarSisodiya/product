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
public class ProductDto extends TimestampLoggingDto {

	private String id;

	private String category;

	private String brand;
	
	private String productName;
	
	private String spec;
	
	private String imagename;

	@Builder
	public ProductDto(String id, String category, String brand, String productName, String spec, String imagename,
			String createdDate, String createdBy, String updatedDate, String updatedBy) {
		super(createdDate, createdBy, updatedDate, updatedBy);
		this.id = id;
		this.category = category;
		this.brand = brand;
		this.productName = productName;
		this.spec = spec;
		this.imagename = imagename;
	}
}
