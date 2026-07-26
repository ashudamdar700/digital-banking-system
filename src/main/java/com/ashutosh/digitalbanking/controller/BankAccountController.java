package com.ashutosh.digitalbanking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;
import com.ashutosh.digitalbanking.dto.TransactionRequest;
import com.ashutosh.digitalbanking.dto.TransactionResponse;
import com.ashutosh.digitalbanking.service.BankAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class BankAccountController {

	private final BankAccountService bankAccountService;
	
	@PostMapping
	public ResponseEntity<AccountResponse> createAccount(
			@Valid @RequestBody CreateBankAccountRequest request) {
		
		AccountResponse response = bankAccountService.createBankAccount(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getMyAccounts() {
		
		List<AccountResponse> response = bankAccountService.getMyAccounts();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountResponse> getAccountDetails(
			@PathVariable String accountNumber) {
		
		AccountResponse response = bankAccountService.getAccountDetails(accountNumber);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{accountNumber}/deposit")
	public ResponseEntity<TransactionResponse> deposit(
			@PathVariable String accountNumber,
			@Valid @RequestBody TransactionRequest request) {
		
		TransactionResponse response = bankAccountService.deposit(accountNumber, request);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{accountNumber}/withdraw")
	public ResponseEntity<TransactionResponse> withdraw(
			@PathVariable String accountNumber,
			@Valid @RequestBody TransactionRequest request) {
		
		TransactionResponse response = bankAccountService.withdraw(accountNumber, request);
		
		return ResponseEntity.ok(response);
	}
}
