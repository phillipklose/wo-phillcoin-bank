package com.filip.klose.wophillcoinbank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;

public interface CashOutOfBankRepository extends MongoRepository<CashOutOfBank, String> {
}
