package com.dhruv.service;

import com.dhruv.exception.UserException;
import com.dhruv.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
