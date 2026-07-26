package com.ashutosh.digitalbanking.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ashutosh.digitalbanking.dto.AccountResponse;
import com.ashutosh.digitalbanking.dto.CreateBankAccountRequest;
import com.ashutosh.digitalbanking.dto.TransactionRequest;
import com.ashutosh.digitalbanking.dto.TransactionResponse;
import com.ashutosh.digitalbanking.entity.AccountStatus;
import com.ashutosh.digitalbanking.entity.BankAccount;
import com.ashutosh.digitalbanking.entity.BankTransaction;
import com.ashutosh.digitalbanking.entity.TransactionMode;
import com.ashutosh.digitalbanking.entity.TransactionType;
import com.ashutosh.digitalbanking.entity.User;
import com.ashutosh.digitalbanking.exception.InsufficientBalanceException;
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

		BankAccount bankAccount = BankAccount.builder()
				.accountNumber(generateAccountNumber())
				.balance(BigDecimal.ZERO)
				.accountType(request.getAccountType())
				.accountStatus(AccountStatus.ACTIVE)
				.user(user)
				.build();

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

		BankAccount account = getUserAccount(accountNumber, user);

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
	public TransactionResponse deposit(String accountNumber, TransactionRequest request) {

		User user = getCurrentUser();
		BankAccount account = getUserAccount(accountNumber, user);

		account.setBalance(account.getBalance().add(request.getAmount()));
		bankAccountRepository.save(account);

		BankTransaction transaction = createTransaction(account, request.getAmount(), TransactionType.CREDIT, TransactionMode.CASH_DEPOSIT, request.getRemarks());

		return buildTransactionResponse(transaction, account);
	}

	@Override
	@Transactional
	public TransactionResponse withdraw(String accountNumber, TransactionRequest request) {
		
		User user = getCurrentUser();
		BankAccount account = getUserAccount(accountNumber, user);
		
		if (account.getBalance().compareTo(request.getAmount()) < 0) {
			throw new InsufficientBalanceException("Insufficient balance");
		}
		
		account.setBalance(account.getBalance().subtract(request.getAmount()));
		bankAccountRepository.save(account);
		
		BankTransaction transaction = createTransaction(account,request.getAmount(),TransactionType.DEBIT,TransactionMode.CASH_WITHDRAWAL,request.getRemarks());

		return buildTransactionResponse(transaction, account);
	}
	
	private String generateReferenceNumber() {

		while (true) {

			String referenceNumber = "TXN" + ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);

			 if(!bankTransactionRepository.existsByReferenceNumber(referenceNumber)) {
				return referenceNumber;
			 }
		}
	}
	
	private BankAccount getUserAccount(String accountNumber, User user) {

		return bankAccountRepository.findByAccountNumberAndUser(accountNumber, user)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
	}
	
	private BankTransaction createTransaction(
			BankAccount account,
			BigDecimal amount,
			TransactionType type,
			TransactionMode mode,
			String remarks) {
		
		BankTransaction transaction = BankTransaction.builder()
				.amount(amount)
				.transactionType(type)
				.transactionMode(mode)
				.referenceNumber(generateReferenceNumber())
				.remarks(remarks)
				.bankAccount(account)
				.build();
		
		return bankTransactionRepository.save(transaction);
	}
	
	private TransactionResponse buildTransactionResponse(BankTransaction transaction, BankAccount account) {
		
		return TransactionResponse.builder()
				.referenceNumber(transaction.getReferenceNumber())
				.amount(transaction.getAmount())
				.transactionType(transaction.getTransactionType())
				.transactionMode(transaction.getTransactionMode())
				.balanceAfterTransaction(account.getBalance())
				.transactionTime(transaction.getTransactionTime())
				.remarks(transaction.getRemarks())
				.build();
	}
}
