package com.ashutosh.digitalbanking.service;

import com.ashutosh.digitalbanking.dto.LoginRequest;
import com.ashutosh.digitalbanking.dto.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);
}
