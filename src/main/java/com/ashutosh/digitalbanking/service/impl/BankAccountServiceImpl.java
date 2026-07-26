package com.ashutosh.digitalbanking.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;
import com.ashutosh.digitalbanking.dto.DepositRequest;
import com.ashutosh.digitalbanking.dto.TransactionResponse;
import com.ashutosh.digitalbanking.entity.AccountStatus;
import com.ashutosh.digitalbanking.entity.BankAccount;
import com.ashutosh.digitalbanking.entity.BankTransaction;
import com.ashutosh.digitalbanking.entity.TransactionMode;
import com.ashutosh.digitalbanking.entity.TransactionType;
import com.ashutosh.digitalbanking.entity.User;
import com.ashutosh.digitalbanking.exception.ResourceNotFoundException;
import com.ashutosh.digitalbanking.repository.BankAccountRepository;
import com.ashutosh.digitalbanking.repository.BankTransactionRepository;
import com.ashutosh.digitalbanking.repository.UserRepository;
import com.ashutosh.digitalbanking.service.BankAccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

	private final BankAccountRepository bankAccountRepository;

	private final UserRepository userRepository;

	private final BankTransactionRepository bankTransactionRepository;

	@Override
	public AccountResponse createBankAccount(CreateBankAccountRequest request) {

		User user = getCurrentUser();

		BankAccount bankAccount = BankAccount.builder().accountNumber(generateAccountNumber()).balance(BigDecimal.ZERO)
				.accountType(request.getAccountType()).accountStatus(AccountStatus.ACTIVE).user(user).build();

		BankAccount savedAccount = bankAccountRepository.save(bankAccount);

		return AccountResponse.builder().accountNumber(savedAccount.getAccountNumber())
				.balance(savedAccount.getBalance()).accountType(savedAccount.getAccountType())
				.accountStatus(savedAccount.getAccountStatus()).build();
	}

	private String generateAccountNumber() {

		while (true) {
			String accountNumber = String
					.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));

			if (!bankAccountRepository.existsByAccountNumber(accountNumber)) {

				return accountNumber;
			}
		}
	}

	@Override
	public List<AccountResponse> getMyAccounts() {

		User user = getCurrentUser();

		List<BankAccount> accounts = bankAccountRepository.findByUser(user);

		return accounts.stream()
				.map(account -> AccountResponse.builder().accountNumber(account.getAccountNumber())
						.balance(account.getBalance()).accountType(account.getAccountType())
						.accountStatus(account.getAccountStatus()).build())
				.toList();
	}

	@Override
	public AccountResponse getAccountDetails(String accountNumber) {

		User user = getCurrentUser();

		BankAccount account = bankAccountRepository.findByAccountNumberAndUser(accountNumber, user)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		return AccountResponse.builder().accountNumber(account.getAccountNumber()).balance(account.getBalance())
				.accountType(account.getAccountType()).accountStatus(account.getAccountStatus()).build();
	}

	private User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	@Transactional
	public TransactionResponse deposit(String accountNumber, DepositRequest request) {

		User user = getCurrentUser();
		BankAccount account = bankAccountRepository.findByAccountNumberAndUser(accountNumber, user)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		account.setBalance(account.getBalance().add(request.getAmount()));

		BankTransaction transaction = BankTransaction.builder().amount(request.getAmount())
				.transactionType(TransactionType.CREDIT).transactionMode(TransactionMode.CASH_DEPOSIT)
				.referenceNumber(generateReferenceNumber()).remarks(request.getRemarks()).bankAccount(account).build();

		bankAccountRepository.save(account);
		bankTransactionRepository.save(transaction);

		return TransactionResponse.builder().referenceNumber(transaction.getReferenceNumber())
				.amount(transaction.getAmount()).transactionType(transaction.getTransactionType())
				.transactionMode(transaction.getTransactionMode()).balanceAfterTransaction(account.getBalance())
				.transactionTime(transaction.getTransactionTime()).remarks(transaction.getRemarks()).build();
	}

	private String generateReferenceNumber() {

		boolean referenceExists = true;
		String referenceNumber = null;

		while (referenceExists) {

			referenceNumber = "TXN" + ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);

			referenceExists = bankTransactionRepository.existsByReferenceNumber(referenceNumber);
		}

		return referenceNumber;
	}
}
