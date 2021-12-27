package com.leovegas.wallet.exception;

import org.springframework.http.HttpStatus;

public class TransactionException extends Exception {

	 private HttpStatus status;
	 
	 
		public TransactionException(String message) {
			super(message);
		}

		public TransactionException(HttpStatus status, String message) {
	        super(message);
	        this.status = status;
	    }
		
		public TransactionException(HttpStatus status,Throwable cause) {
	        super(cause);
	        this.status = status;
	    }

	    public TransactionException(String message, Throwable cause) {
	        super(message, cause);
	    }
		
		public String errorMessage(){
	        return status.value() + ":".concat(getMessage());
	    }

	    public HttpStatus getStatus() {
	        return status;
	    }
	

}
