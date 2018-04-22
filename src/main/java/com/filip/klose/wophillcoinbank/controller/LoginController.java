package com.filip.klose.wophillcoinbank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.mapper.UserMapper;
import com.filip.klose.wophillcoinbank.model.LoginCredentialsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.UserUnauthorizedException;
import com.filip.klose.wophillcoinbank.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @PutMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        final User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(mapper.convertToDto(savedUser));
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginCredentialsDto credentials) {
        final Optional<User> userByLoginCredentials = userService.getUserByLoginCredentials(credentials);
        final User loggedInUser = userByLoginCredentials.orElseThrow(() -> new UserUnauthorizedException());
        return ResponseEntity.ok(mapper.convertToDto(loggedInUser));
    }
}
