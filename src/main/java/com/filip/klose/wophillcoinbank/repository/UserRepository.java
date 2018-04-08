package com.filip.klose.wophillcoinbank.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filip.klose.wophillcoinbank.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLoginOrEmail(String login, String email);
}
