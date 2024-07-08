package com.invento.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends TimestampLogging {

	@Id
	private String id;

	private String category;

	private String brand;
	
	private String productName;

	private String spec;
	
	@JsonIgnore
	private boolean deleted;
	
	private String imagename;
}
