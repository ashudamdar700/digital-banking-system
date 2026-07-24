package com.ashutosh.digitalbanking.service;

import com.ashutosh.digitalbanking.dto.RegisterUserRequest;
import com.ashutosh.digitalbanking.dto.UserResponse;
import com.ashutosh.digitalbanking.entity.User;

public interface UserService {

	UserResponse registerUser(RegisterUserRequest request);
	
	User getUserByEmail(String email);
}
