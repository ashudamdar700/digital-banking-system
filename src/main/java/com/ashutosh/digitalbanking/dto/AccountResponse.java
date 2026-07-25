package com.ashutosh.digitalbanking.dto;

import java.math.BigDecimal;

import com.ashutosh.digitalbanking.entity.AccountStatus;
import com.ashutosh.digitalbanking.entity.AccountType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

	private String accountNumber;

	private BigDecimal balance;

	private AccountType accountType;

	private AccountStatus accountStatus;
}
