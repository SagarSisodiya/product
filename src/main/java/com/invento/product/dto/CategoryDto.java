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
public class CategoryDto extends TimestampLoggingDto {

	private String id;
	
	private String name;

	@Builder
	public CategoryDto(String id, String name, String createdDate, 
		String createdBy, String updatedDate, String updatedBy) {
		super(createdDate, createdBy, updatedDate, updatedBy);
		this.id = id;
		this.name = name;
	}
}
