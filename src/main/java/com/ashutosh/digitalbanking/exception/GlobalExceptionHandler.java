package com.ashutosh.digitalbanking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import com.ashutosh.digitalbanking.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
			EmailAlreadyExistsException ex){
		
		return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	@ExceptionHandler(PhoneNumberAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handlePhoneNumberAlreadyExistsException(
			PhoneNumberAlreadyExistsException ex){

		return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> validationErrors = new HashMap<>();

	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        validationErrors.put(error.getField(), error.getDefaultMessage());
	    }

	    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", validationErrors);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
			ResourceNotFoundException ex) {

		return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(
			InsufficientBalanceException ex) {

		return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	@ExceptionHandler(InvalidTransferException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTransferException(
			InvalidTransferException ex) {

		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	@ExceptionHandler(AccountNotActiveException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotActive(
	        AccountNotActiveException ex) {

	    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	//API to build ErrorResponse and avoid code repetition
	private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {

		ErrorResponse response = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.build();
		
		return ResponseEntity.status(status).body(response);
	}
	
	private ResponseEntity<ErrorResponse> buildErrorResponse(
			HttpStatus status,
			String message,
			Map<String, String> validationErrors) {

	    ErrorResponse response = ErrorResponse.builder()
	            .timestamp(LocalDateTime.now())
	            .status(status.value())
	            .error(status.getReasonPhrase())
	            .message(message)
	            .validationErrors(validationErrors)
	            .build();

	    return ResponseEntity.status(status).body(response);
	}
}
