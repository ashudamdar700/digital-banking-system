package com.ashutosh.digitalbanking.service.impl;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;
import com.ashutosh.digitalbanking.entity.AccountStatus;
import com.ashutosh.digitalbanking.entity.BankAccount;
import com.ashutosh.digitalbanking.entity.User;
import com.ashutosh.digitalbanking.exception.ResourceNotFoundException;
import com.ashutosh.digitalbanking.repository.BankAccountRepository;
import com.ashutosh.digitalbanking.repository.UserRepository;
import com.ashutosh.digitalbanking.service.BankAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

	private final BankAccountRepository bankAccountRepository;
	
	private final UserRepository userRepository;
	
	@Override
    public AccountResponse createBankAccount(CreateBankAccountRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .accountType(request.getAccountType())
                .accountStatus(AccountStatus.ACTIVE)
                .user(user)
                .build();
        
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        
        return AccountResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .balance(savedAccount.getBalance())
                .accountType(savedAccount.getAccountType())
                .accountStatus(savedAccount.getAccountStatus())
                .build();
    }
	
	private String generateAccountNumber() {
		
		while(true) {
			String accountNumber = String.valueOf(
					ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
			
			 if(!bankAccountRepository.existsByAccountNumber(accountNumber)) {

				 return accountNumber;
			 }
		}
	}
}
