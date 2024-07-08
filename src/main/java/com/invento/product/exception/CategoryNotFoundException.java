package com.invento.product.exception;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends RuntimeException {

	private String message;
	
	public CategoryNotFoundException(String msg) {
		
		super(msg);
		this.message = msg;
	}
}
