package com.leovegas.wallet.service;





import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.leovegas.wallet.exception.UserException;
import com.leovegas.wallet.model.User;
import com.leovegas.wallet.repositories.UserRepository;
import com.leovegas.wallet.utility.Constants;
import com.leovegas.wallet.utility.NumberGenerator;

@Service("UserService")
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;


	@Autowired 
	private NumberGenerator numberGenerator;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public User createUser(User user) throws UserException{
		
		if (!StringUtils.isEmpty(user.getEmail()) && !StringUtils.isEmpty(user.getUserID())) {

			List<User> existingUserListfromDB = userRepo.findByUserIDOrEmail(user.getUserID(), user.getEmail());
			
			// if no users with the given user name and email, we register the new user.
			if(existingUserListfromDB.isEmpty()) {

				user.setModifiedAt(LocalDateTime.now());
				user.setCreatedAt(LocalDateTime.now());
				user.getAccount().setAccountNo(numberGenerator.getAccountNumber());
				user.getAccount().setCreationUser(user.getUserID());

				return userRepo.save(user);

			}else {
				// checking whether userID or email is existing to give proper error msg to customer.
				existingUserListfromDB.forEach( (userFromDB) -> {
					String errorMsg;
					if(userFromDB.getEmail().trim().equals(user.getEmail())) {
						errorMsg = Constants.EXSISTING_EMAIL_FOUND;
					}else {
						errorMsg = Constants.EXSISTING_USER_ID_FOUND;
					}

					throw new UserException(HttpStatus.UNPROCESSABLE_ENTITY, errorMsg);
				});
			}
		}
		return user;
	}

	@Override
	public Optional<User> getUserByUserID(String userID) throws UserException {

		return  userRepo.findByUserID(userID);
		
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = (List<User>) userRepo.findAll();
		
		return userList ;		
	}


}
