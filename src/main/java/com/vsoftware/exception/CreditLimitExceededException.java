package com.vsoftware.exception;

public class CreditLimitExceededException extends ValidationException {
	
	private static final long serialVersionUID = 1L;

	public CreditLimitExceededException(String message) {
		super(message);	
	}
	
}
