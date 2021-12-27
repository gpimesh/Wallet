package com.leovegas.wallet.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leovegas.wallet.utility.Constants;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	
	
	@ExceptionHandler
	public ResponseEntity<Object> handleException(Exception ex) {

		if(ex instanceof UserException) {
			UserException uEx = (UserException) ex;
			return new ResponseEntity<>(uEx.errorMessage(), uEx.getStatus());
		}
		
		
		if (ex instanceof TransactionException) {
			TransactionException tEx = (TransactionException) ex;
					return new ResponseEntity<>(tEx.errorMessage(), tEx.getStatus());
		}
		
		if(ex instanceof ConstraintViolationException) {
			ConstraintViolationException keyViolationEx = (ConstraintViolationException) ex;
			return new ResponseEntity<>("Ërror in updating DB ", HttpStatus.CONFLICT);
			
		}
		
		
		return new ResponseEntity<>(Constants.UNEXPECTED_ERROR + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	

}
