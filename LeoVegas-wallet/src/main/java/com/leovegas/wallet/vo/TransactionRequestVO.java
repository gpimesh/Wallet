package com.leovegas.wallet.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionRequestVO {
	
	
	

	@NotBlank(message = "stringValue has to be present") @Size( min=5, max=25, message = "User ID must be between {min} and {max} characters long" ) 
	private String userid;

	@NotNull
	private long transactionID;
	
	@NotNull
	private float amount;

	
	
	
	
	public TransactionRequestVO(
			@NotBlank(message = "stringValue has to be present") @Size(min = 5, max = 25, message = "User ID must be between {min} and {max} characters long") String userid,
			@NotNull long transactionID, @NotNull float amount) {
		super();
		this.userid = userid;
		this.transactionID = transactionID;
		this.amount = amount;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	

}
