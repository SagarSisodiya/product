package com.invento.product.exception;

@SuppressWarnings("serial")
public class ProductAlreadyDeletedException extends RuntimeException {

	private String message;
	
	public ProductAlreadyDeletedException(String msg) {
		
		super(msg);
		this.message = msg;
	}
}
