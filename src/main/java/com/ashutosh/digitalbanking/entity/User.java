package com.ashutosh.digitalbanking.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Full name is required")
	@Column(nullable = false)
	private String fullName;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;

	@NotBlank(message = "Phone number is required")
	@Column(nullable = false, unique = true)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus status;
	
	@OneToMany(mappedBy = "user")
	private List<BankAccount> bankAccounts;
}
