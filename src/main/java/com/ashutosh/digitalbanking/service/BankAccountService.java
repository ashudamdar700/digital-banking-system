package com.ashutosh.digitalbanking.service;

import java.util.List;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;

public interface BankAccountService {

	AccountResponse createBankAccount(CreateBankAccountRequest request);
	
	List<AccountResponse> getMyAccounts();
}
