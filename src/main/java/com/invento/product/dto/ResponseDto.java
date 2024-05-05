package com.invento.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {

	private String message;
	
	public ResponseDto(String msg) {
		
		this.message = msg;
	}
}
