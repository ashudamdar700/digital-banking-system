package com.ashutosh.digitalbanking.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_accounts")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus accountStatus;
	
	@OneToMany(mappedBy = "bankAccount")
	private List<BankTransaction> transactions;
}