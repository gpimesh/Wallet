package com.leovegas.wallet.repositories;

import org.springframework.data.repository.CrudRepository;

import com.leovegas.wallet.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{

}