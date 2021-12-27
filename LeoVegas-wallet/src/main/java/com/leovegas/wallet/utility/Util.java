package com.leovegas.wallet.utility;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public final class Util {

	/**
	 * To validate the object coming to the rest API's handler method
	 * */
	public String createErrorString(BindingResult result) {
			StringBuilder sb =  new StringBuilder();
			result.getAllErrors().forEach(error -> {
				if(error instanceof FieldError) {
					FieldError err= (FieldError) error;
					sb.append("Field '").append(err.getField()).append("' value error: ").append(err.getDefaultMessage()).append("\n");
				}
			});
			return sb.toString();
		}

}
