package com.filip.klose.wophillcoinbank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.LoanCash;

public interface LoanCashRepository extends MongoRepository<LoanCash, String> {
}
