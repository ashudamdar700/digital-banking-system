package com.ashutosh.digitalbanking.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	
	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}
