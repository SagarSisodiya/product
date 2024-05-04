package com.invento.product.exception;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException {

	private String message;
	
	public ProductNotFoundException(String msg) {
		
		super(msg);
		this.message = msg;
	}
}
