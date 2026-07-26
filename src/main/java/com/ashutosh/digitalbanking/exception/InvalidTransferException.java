package com.ashutosh.digitalbanking.exception;

public class InvalidTransferException extends RuntimeException{
	
	public InvalidTransferException(String message) {
		super(message);
	}
}
