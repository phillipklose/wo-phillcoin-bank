package com.filip.klose.wophillcoinbank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.CyclicTransfer;

public interface CyclicTransferRepository extends MongoRepository<CyclicTransfer, String> {
}
