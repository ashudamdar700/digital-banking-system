package com.ashutosh.digitalbanking.dto;

import com.ashutosh.digitalbanking.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long id;
	
	private String fullName;
	
	private String email;
	
	private String phoneNumber;
	
	private Role role;
	
}
