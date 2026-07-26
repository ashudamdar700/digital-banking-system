package com.ashutosh.digitalbanking.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionMode transactionMode;
	
	@Column(nullable = false, unique = true)
	private String referenceNumber;
	
	@Column(length = 255)
	private String remarks;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime transactionTime;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private BankAccount bankAccount;
}
