package com.dhruv.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhruv.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}