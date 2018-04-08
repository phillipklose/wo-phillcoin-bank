package com.filip.klose.wophillcoinbank.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.LoginCredentials;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByLoginCredentials(LoginCredentials credentials) {
        return userRepository.findByLoginOrEmailAndPassword(credentials.getLogin(), credentials.getLogin(), credentials.getPassword());
    }

    public Optional<User> getUserById(ObjectId objectId) {
        return userRepository.findById(objectId.toString());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
