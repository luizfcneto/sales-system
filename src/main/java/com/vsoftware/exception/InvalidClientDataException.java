package com.vsoftware.exception;

public class InvalidClientDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidClientDataException(String message) {
		super(message);	
	}
	
}
