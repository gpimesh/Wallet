package com.leovegas.wallet.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leovegas.wallet.exception.TransactionException;
import com.leovegas.wallet.exception.UserException;
import com.leovegas.wallet.model.Account;
import com.leovegas.wallet.model.Transactions;
import com.leovegas.wallet.model.User;
import com.leovegas.wallet.repositories.AccountRepository;
import com.leovegas.wallet.repositories.TransactionsRepository;
import com.leovegas.wallet.utility.Constants;
import com.leovegas.wallet.utility.TransactionTypes;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {
	
	Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransactionsRepository transactionRepo;
	
	@Autowired
	private UserService userService;
	

    @Override
	public List<Account> getAccountBalance() {
				
		return (List<Account>) accountRepo.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public Account withdrawFromAccount(String userid, long transactionid, float amount) throws TransactionException {
		
		Account accObj = getAccount(userid);
		float accountBal = accObj.getBalance();
		accountBal = accountBal - amount;

		if (accountBal < 0) {
			throw new TransactionException(HttpStatus.NOT_FOUND, Constants.FUNDS_NOT_ENOUGH);
		}else {
			if (isDuplicateTransactionID(transactionid)) {
				throw new TransactionException(HttpStatus.CONFLICT, Constants.TRANSACTION_ID_FOUND + transactionid);
			}else {
				accObj.setBalance(accountBal);
				accObj.addTransactions(new Transactions(transactionid,amount, userid, TransactionTypes.DEBIT.name()));
				Account ac = accountRepo.save(accObj);
				return ac;
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public Account addMoneyToAccount(String userid, long transactionid, float amount) throws UserException, TransactionException {
		
		Account accObj = getAccount(userid);
		if (isDuplicateTransactionID(transactionid)) {
			throw new TransactionException(HttpStatus.CONFLICT, Constants.TRANSACTION_ID_FOUND + transactionid);
		}else {
			float accountBal = accObj.getBalance();
			accountBal = accountBal + amount;
			accObj.setBalance(accountBal);
			accObj.addTransactions(new Transactions(transactionid,amount, userid, TransactionTypes.CREDIT.name()));
						
			accountRepo.save(accObj);
		
			return accObj;
		}
	}

	
	private Account getAccount(String userid) throws TransactionException{
		
		Optional<User> userOpt = userService.getUserByUserID(userid);
		if (userOpt.isPresent()) {
			return userOpt.get().getAccount();
		} else {
			throw new UserException( HttpStatus.NOT_FOUND , Constants.USER_NOT_FOUND + userid );
		}
	}
	
	private boolean isDuplicateTransactionID(long transactionID) {
		
		Optional<Transactions> existingTr = transactionRepo.findByTransactionID(transactionID);
		return existingTr.isPresent();
	}
	

}
