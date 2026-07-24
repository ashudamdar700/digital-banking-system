package com.ashutosh.digitalbanking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

	@NotBlank(message = "Full name is required")
	private String fullName;
	
	@Email(message = "Invalid email format")
	@NotBlank(message ="Email is required")
	private String email;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$",
			message = "Invalid phone number")
	private String phoneNumber;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
	
}
