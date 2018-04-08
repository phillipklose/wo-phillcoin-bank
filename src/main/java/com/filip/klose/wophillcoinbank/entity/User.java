package com.filip.klose.wophillcoinbank.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {

    @Id
    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    public User(String login, String firstName, String lastName, String email) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, login='%s', firstName='%s', lastName='%s', email='%s']", id, login, firstName, lastName, email);
    }
}
