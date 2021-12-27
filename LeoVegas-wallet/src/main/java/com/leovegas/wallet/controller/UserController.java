package com.leovegas.wallet.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.wallet.exception.UserException;
import com.leovegas.wallet.model.User;
import com.leovegas.wallet.service.UserService;
import com.leovegas.wallet.utility.Constants;
import com.leovegas.wallet.utility.Util;
import com.leovegas.wallet.vo.UserVO;

@RestController
@RequestMapping("/wallet")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private  UserService userServiceImpl;
	
	@Autowired
	private Util utiltyBean;
	
	
	@PostMapping("/user/registeruser")
	public ResponseEntity<String> registerUser(@Validated(User.BasicValidation.class) @RequestBody User user, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			 String errString = utiltyBean.createErrorString(result);
			 //There could be internal status codes can pass instead HTTP status codes. As this is RestAPI status code. Not HTTP. By right, HTTP must be 200. As this is internal error. not HTTP protocol error.
			 throw new UserException(HttpStatus.BAD_REQUEST, Constants.CANNOT_SAVE_ENTRY + errString);
		}
		try {
			 userServiceImpl.createUser(user);
			return  new ResponseEntity<String>(Constants.USER_CREATION_SUCCESSFUL, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new UserException( HttpStatus.UNPROCESSABLE_ENTITY , e.getMessage());
		}
	}

	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserVO> getUser(@PathVariable("userId") String userID){
		
		try {
			if(org.apache.commons.lang3.StringUtils.isEmpty(userID)) {
				 throw new UserException(HttpStatus.NOT_FOUND, String.format(Constants.USER_NOT_FOUND, userID) );
			}
			
			Optional<User> userOpt = userServiceImpl.getUserByUserID(userID);
			if(userOpt.isPresent()) {
				UserVO user = new UserVO();
				user.setUser(userOpt.get());
	            return new ResponseEntity<>(user, HttpStatus.OK);
			}else {
				throw new UserException( HttpStatus.NOT_FOUND , Constants.USER_NOT_FOUND + userID);
			}
						
		} catch (Exception e) {
			throw new UserException( HttpStatus.NOT_FOUND , e.getMessage());
		}
		
	}
	
	
	
	  @GetMapping("/users") 
	  public ResponseEntity<UserVO> findAllUsers(){

		  UserVO userVO = new UserVO();
		  userVO.setUserList(userServiceImpl.getAllUsers());	 
		  return new ResponseEntity<>(userVO, HttpStatus.OK);

	  
	  }
	 

}
