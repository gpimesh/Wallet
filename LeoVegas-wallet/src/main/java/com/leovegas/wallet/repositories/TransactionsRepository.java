package com.leovegas.wallet.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.leovegas.wallet.model.Transactions;

public interface TransactionsRepository extends CrudRepository<Transactions, Long> {

	Optional<Transactions> findByTransactionID(long txnID );
}