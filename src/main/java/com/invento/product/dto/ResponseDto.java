package com.invento.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {

	private String message;
	
	public ResponseDto(String msg) {
		
		this.message = msg;
	}
}
