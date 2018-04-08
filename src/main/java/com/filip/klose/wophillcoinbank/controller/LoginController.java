package com.filip.klose.wophillcoinbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.service.TokenService;
import com.filip.klose.wophillcoinbank.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        final User savedUser = userService.saveUser(user);
        return tokenService.createToken(savedUser.getId());
    }
}
