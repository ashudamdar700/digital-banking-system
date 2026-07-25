package com.ashutosh.digitalbanking.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(UserDetails userDetails) {
		
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSigningKey())
				.compact();
	}
	
	public long getExpirationTime() {
		return jwtExpiration;
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
	            .verifyWith((javax.crypto.SecretKey) getSigningKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
	
	public String extractUsername(String token) {
		
	    return extractAllClaims(token).getSubject();
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractAllClaims(token)
	            .getExpiration()
	            .before(new Date());
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String username = extractUsername(token);
		
		return username.equals(userDetails.getUsername())
	            && !isTokenExpired(token);
	}
}
