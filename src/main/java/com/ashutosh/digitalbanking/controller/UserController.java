package com.ashutosh.digitalbanking.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashutosh.digitalbanking.dto.RegisterUserRequest;
import com.ashutosh.digitalbanking.dto.UserResponse;
import com.ashutosh.digitalbanking.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/register")
	public UserResponse resgisterUser(
			@Valid @RequestBody RegisterUserRequest request) {
		
		return userService.registerUser(request);
	}
	
}
