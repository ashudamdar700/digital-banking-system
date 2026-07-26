package com.ashutosh.digitalbanking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	private BigDecimal amount;
	
	private String remarks;
}
