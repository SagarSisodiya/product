package com.invento.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
@Builder
public class Category {

	@Id
	private String id;
	
	private String name;
	
	private boolean deleted;
}
