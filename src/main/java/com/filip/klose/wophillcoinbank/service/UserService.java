package com.filip.klose.wophillcoinbank.service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
