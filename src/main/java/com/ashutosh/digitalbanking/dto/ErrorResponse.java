package com.ashutosh.digitalbanking.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL) //exclude fields with null values when converting a Java object into JSON
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    
    private Map<String, String> validationErrors;
}
