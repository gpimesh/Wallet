package com.leovegas.wallet.service;

import java.util.List;

import com.leovegas.wallet.exception.TransactionException;
import com.leovegas.wallet.model.Account;

public interface AccountService {

	  	
		public List<Account> getAccountBalance();
		
		public Account withdrawFromAccount(String userid,long transactionid, float amount) throws TransactionException;
		
		public Account addMoneyToAccount(String userid,long transactionid, float amount) throws TransactionException;
}
