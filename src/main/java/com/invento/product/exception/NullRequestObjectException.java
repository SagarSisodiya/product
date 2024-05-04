package com.invento.product.exception;

@SuppressWarnings("serial")
public class NullRequestObjectException extends RuntimeException{

	private String message;
	
	public NullRequestObjectException(String msg) {
		
		super(msg);
		this.message = msg;
	}
}
