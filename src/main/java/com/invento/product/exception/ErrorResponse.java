package com.invento.product.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	private String message;
	
	public ErrorResponse(String message) {
		
		super();
		this.message = message;
	}
}
