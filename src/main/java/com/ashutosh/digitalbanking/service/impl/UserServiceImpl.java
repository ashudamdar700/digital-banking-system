package com.ashutosh.digitalbanking.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashutosh.digitalbanking.dto.RegisterUserRequest;
import com.ashutosh.digitalbanking.dto.UserResponse;
import com.ashutosh.digitalbanking.entity.AccountStatus;
import com.ashutosh.digitalbanking.entity.Role;
import com.ashutosh.digitalbanking.entity.User;
import com.ashutosh.digitalbanking.exception.EmailAlreadyExistsException;
import com.ashutosh.digitalbanking.exception.PhoneNumberAlreadyExistsException;
import com.ashutosh.digitalbanking.repository.UserRepository;
import com.ashutosh.digitalbanking.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository,
							PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserResponse registerUser(RegisterUserRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		
		if(userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
			throw new PhoneNumberAlreadyExistsException("Phone Number already exists");
		}
		
		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		
		//Encrypt this later using Spring Security
		//user.setPassword(request.getPassword());
		
		//Password encoded using BCrypt
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		//New user starts as CUSTOMER and accountStatus as ACTIVE
		user.setRole(Role.CUSTOMER);
		user.setStatus(AccountStatus.ACTIVE);
		
		//Save to database
		User savedUser = userRepository.save(user);
		
		return UserResponse.builder()
				.id(savedUser.getId())
				.fullName(savedUser.getFullName())
				.email(savedUser.getEmail())
				.phoneNumber(savedUser.getPhoneNumber())
				.role(savedUser.getRole())
				.build();
	}

	@Override
	public User getUserByEmail(String email) {
		return null;
	}

}
