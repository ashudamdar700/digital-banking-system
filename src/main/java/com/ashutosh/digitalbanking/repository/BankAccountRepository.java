package com.ashutosh.digitalbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashutosh.digitalbanking.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	
	Optional<BankAccount> findByAccountNumber(String accountNumber);
}
