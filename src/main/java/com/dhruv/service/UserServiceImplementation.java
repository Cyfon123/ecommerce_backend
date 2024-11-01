package com.dhruv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhruv.config.JwtProvider;
import com.dhruv.exception.UserException;
import com.dhruv.model.User;
import com.dhruv.repo.UserRepository;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent())
			return user.get();
		throw new UserException("User not found with id :- " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if(user == null)
			throw new UserException("User not found with email :- " + email);
		return user;
	}
	
	

}
