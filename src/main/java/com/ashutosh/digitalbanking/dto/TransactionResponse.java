package com.ashutosh.digitalbanking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ashutosh.digitalbanking.entity.TransactionMode;
import com.ashutosh.digitalbanking.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

	private String referenceNumber;

	private BigDecimal amount;

	private TransactionType transactionType;

	private TransactionMode transactionMode;

	private BigDecimal balanceAfterTransaction;

	private LocalDateTime transactionTime;

	private String remarks;
}
