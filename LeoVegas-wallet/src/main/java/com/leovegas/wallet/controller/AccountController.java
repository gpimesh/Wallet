package com.leovegas.wallet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.wallet.exception.TransactionException;
import com.leovegas.wallet.model.Account;
import com.leovegas.wallet.model.Transactions;
import com.leovegas.wallet.service.AccountService;
import com.leovegas.wallet.vo.TransactionRequestVO;
import com.leovegas.wallet.vo.TransactionResponseVO;


@RestController
@RequestMapping("/wallet")
@Validated
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	
	@Autowired 
	private AccountService accountService;
	
	
	
	@PutMapping("/transactions/withdraw/")
	public ResponseEntity<TransactionResponseVO> withdrawFromWallet(
			@Valid @RequestBody TransactionRequestVO transactionReqVo) throws TransactionException{
		
	
		Account accObj = accountService.withdrawFromAccount(transactionReqVo.getUserid(), transactionReqVo.getTransactionID(), transactionReqVo.getAmount());
		accObj.setTransactions(new ArrayList<Transactions>());
		TransactionResponseVO txnResp = new TransactionResponseVO();
		txnResp.setAccount(accObj);
		return  new ResponseEntity<TransactionResponseVO>(txnResp, HttpStatus.CREATED);
				
	}

	@PutMapping("/transactions/credit")
	public  ResponseEntity<TransactionResponseVO> addMoney(
			@Valid @RequestBody TransactionRequestVO transactionReqVo) throws TransactionException {
		
		Account accObj = accountService.addMoneyToAccount(transactionReqVo.getUserid(), transactionReqVo.getTransactionID(), transactionReqVo.getAmount());
		accObj.setTransactions(new ArrayList<Transactions>());
		TransactionResponseVO txnResp = new TransactionResponseVO();
		txnResp.setAccount(accObj);
		return new ResponseEntity<TransactionResponseVO>(txnResp, HttpStatus.CREATED);
		
	}
			
}
