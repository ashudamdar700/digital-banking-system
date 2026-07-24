package com.ashutosh.digitalbanking.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ashutosh.digitalbanking.dto.LoginRequest;
import com.ashutosh.digitalbanking.dto.LoginResponse;
import com.ashutosh.digitalbanking.security.JwtService;
import com.ashutosh.digitalbanking.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	@Override
	public LoginResponse login(LoginRequest request) {

		Authentication authentication = authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		                request.getEmail(),
		                request.getPassword()
		        )
		);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken = jwtService.generateToken(userDetails);
		String role = authentication.getAuthorities()
		        .stream()
		        .findFirst()
		        .get()
		        .getAuthority()
		        .replace("ROLE_", "");
		
		return LoginResponse.builder()
				.token(jwtToken)
				.type("Bearer")
				.expiresIn(jwtService.getExpirationTime())
				.email(userDetails.getUsername())
				.role(role)
				.build();
	}
}
