package com.filip.klose.wophillcoinbank.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.Configuration;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    Optional<Configuration> findConfigurationByType(String type);
}
