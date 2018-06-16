package com.filip.klose.wophillcoinbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.Transaction;
import com.filip.klose.wophillcoinbank.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(String userId, int amount) {
        Transaction transaction = new Transaction(userId, amount);
        transactionRepository.save(transaction);
    }

}
