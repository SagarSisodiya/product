package com.invento.product.exception;

@SuppressWarnings("serial")
public class BrandNotFoundException extends RuntimeException {

	private String message;
	
	public BrandNotFoundException(String msg) {
		
		super(msg);
		this.message = msg;
	}
}
