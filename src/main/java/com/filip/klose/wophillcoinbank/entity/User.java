package com.filip.klose.wophillcoinbank.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;

public class User {

    @Id
    private ObjectId id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int saldo;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public void applySalary(int salary) {
        saldo = saldo + salary;
    }

    public void applyFee(int fee) {
        saldo = saldo - fee;
    }

    public void getCash(int cash) throws GetCashException {
        if (isEnoughCashToGet(cash)) {
            saldo = saldo - cash;
        } else {
            throw new GetCashException();
        }
    }

    private boolean isEnoughCashToGet(int cash) {
        return saldo - cash >= 0;
    }

    public void addCash(int cash) {
        saldo = saldo + cash;
    }

}
