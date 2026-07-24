package com.ashutosh.digitalbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashutosh.digitalbanking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	boolean existsByPhoneNumber(String phoneNumber);
	
}
