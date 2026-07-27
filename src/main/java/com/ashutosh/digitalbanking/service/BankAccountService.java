package com.ashutosh.digitalbanking.service;

import java.util.List;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;
import com.ashutosh.digitalbanking.dto.TransactionRequest;
import com.ashutosh.digitalbanking.dto.TransactionResponse;
import com.ashutosh.digitalbanking.dto.TransferRequest;

public interface BankAccountService {

	AccountResponse createBankAccount(CreateBankAccountRequest request);
	
	List<AccountResponse> getMyAccounts();
	
	AccountResponse getAccountDetails(String accountNumber);
	
	TransactionResponse deposit(String accountNumber, TransactionRequest request);
	
	TransactionResponse withdraw(String accountNumber, TransactionRequest request);
	
	TransactionResponse transfer(String accountNumber, TransferRequest request);

	List<TransactionResponse> getAccountTransactions(String accountNumber);
}
