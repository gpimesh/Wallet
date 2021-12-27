package com.leovegas.wallet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.leovegas.wallet.model.User;

public interface UserRepository extends CrudRepository<User, String> {
	
		
	List<User> findByUserIDOrEmail(String userId, String email);
	
	Optional<User> findByUserID(String userID);
}