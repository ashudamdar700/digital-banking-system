package com.ashutosh.digitalbanking.service;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;

public interface BankAccountService {

	AccountResponse createBankAccount(CreateBankAccountRequest request);
}
