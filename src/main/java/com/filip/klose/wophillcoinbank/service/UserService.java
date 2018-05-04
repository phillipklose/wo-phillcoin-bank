package com.filip.klose.wophillcoinbank.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.LoginCredentialsDto;
import com.filip.klose.wophillcoinbank.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByLoginCredentials(LoginCredentialsDto credentials) {
        return userRepository.findByLoginAndPasswordOrEmailAndPassword(credentials.getLogin(), credentials.getPassword(), credentials
                .getLogin(), credentials.getPassword());
    }

    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
