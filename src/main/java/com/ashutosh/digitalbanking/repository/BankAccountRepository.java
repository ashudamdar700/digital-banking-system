package com.ashutosh.digitalbanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashutosh.digitalbanking.entity.BankAccount;
import com.ashutosh.digitalbanking.entity.User;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	
	List<BankAccount> findByUser(User user);
	
	Optional<BankAccount> findByAccountNumber(String accountNumber);
	
	boolean existsByAccountNumber(String accountNumber);
}
