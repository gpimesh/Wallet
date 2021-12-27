package com.leovegas.wallet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Transactions extends AbstractEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    
	@Column(name="transaction_id", unique = true)
	@NotNull
	private long transactionID;
	
	@NotNull
	private float amount;
	
	@Column(name="user_id")
	@NotNull
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	private String creationuser;
	
	@Column(name="transaction_type")
	@NotNull
	private String transactionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_number", referencedColumnName="cust_account_number")
	@JsonIgnore  // ignore recursion transactions in the response.
	private Account userAccount;
	
	public Transactions(@NotNull long transactionID, @NotNull float amount, @NotNull String creationuser,
			@NotNull String transactionType) {
		super();
		this.transactionID = transactionID;
		this.amount = amount;
		this.creationuser = creationuser;
		this.transactionType = transactionType;
	}

	public Transactions() {
		
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Account getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
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

	public String getCreationuser() {
		return creationuser;
	}

	public void setCreationuser(String creationuser) {
		this.creationuser = creationuser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (transactionID ^ (transactionID >>> 32));
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transactions other = (Transactions) obj;		
		if (transactionID != other.transactionID)
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
