package com.filip.klose.wophillcoinbank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.entity.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
