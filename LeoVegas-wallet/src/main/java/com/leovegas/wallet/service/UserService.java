package com.leovegas.wallet.service;

import java.util.List;
import java.util.Optional;

import com.leovegas.wallet.model.User;

public interface UserService {
	
	public User createUser(User user);
	
	public Optional<User> getUserByUserID(String userID);
	
	public List<User> getAllUsers();
}
