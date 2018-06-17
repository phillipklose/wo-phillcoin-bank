package com.filip.klose.wophillcoinbank.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.LoanCash;

public interface LoanCashRepository extends MongoRepository<LoanCash, String> {
    List<LoanCash> findAllByUserId(String userId);
}
