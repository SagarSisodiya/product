package com.invento.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimestampLoggingDto {

	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
}
