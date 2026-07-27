package com.ashutosh.digitalbanking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashutosh.digitalbanking.entity.BankAccount;
import com.ashutosh.digitalbanking.entity.BankTransaction;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
	
	List<BankTransaction> findByBankAccountOrderByTransactionTimeDesc(BankAccount bankAccount);
	
	boolean existsByReferenceNumber(String referenceNumber);
	
}