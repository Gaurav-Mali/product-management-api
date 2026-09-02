package com.zestindia.productmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zestindia.productmanagement.entity.RefreshToken;
import com.zestindia.productmanagement.entity.User;

public interface RefreshTokenRepository extends 
JpaRepository<RefreshToken, Integer>{
	Optional<RefreshToken> findByToken(String token);
	void deleteByUser(User user);
}
