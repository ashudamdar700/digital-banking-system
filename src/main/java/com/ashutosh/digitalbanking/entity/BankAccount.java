package com.ashutosh.digitalbanking.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String accountNumber;
	
	@Column(nullable = false)
	private BigDecimal balance;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "bankAccount")
	private List<BankTransaction> transactions;
}