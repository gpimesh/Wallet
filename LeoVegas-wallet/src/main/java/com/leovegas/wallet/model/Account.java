package com.leovegas.wallet.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Account extends AbstractEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	 
		
	@Column(name="cust_account_number", unique = true)
	@NotNull
	private long accountNo;
	
	@NotNull
	private float balance;
	
	@Column(name="creation_user")
	@NotNull	
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	private String creationUser;
	
	
	@OneToOne (mappedBy = "account" )
	private User user;
	
	
	@OneToMany(mappedBy = "userAccount",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY) 
    private List<Transactions> transactions;
	
	
	public Account() {
		
	}
	
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public void setCreationUser(String creationUser) {
		
		this.creationUser = creationUser;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	public long getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	// to avoid synchronization issues. in 1-M Bi-direactional
	public void addTransactions(Transactions transaction) {
		transactions.add(transaction);
		transaction.setUserAccount(this);
	}

	// to avoid synchronization issues. in 1-M Bi-direactional
	public void removeTransactions(Transactions transaction) {
		transactions.remove(transaction);
		transaction.setUserAccount(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
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
		Account other = (Account) obj;
		if (accountNo != other.accountNo)
			return false;
		return true;
	}

	
	
}
