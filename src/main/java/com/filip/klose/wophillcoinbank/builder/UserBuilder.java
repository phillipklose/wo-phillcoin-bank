package com.filip.klose.wophillcoinbank.builder;

import com.filip.klose.wophillcoinbank.entity.User;

public class UserBuilder {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int saldo;

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setSaldo(int saldo) {
        this.saldo = saldo;
        return this;
    }

    public User build() {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setSaldo(saldo);
        return user;
    }
}
