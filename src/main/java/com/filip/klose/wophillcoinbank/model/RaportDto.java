package com.filip.klose.wophillcoinbank.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class RaportDto {
    private String userId;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private int saldo;

//    @JsonUnwrapped
//    private LoanCashDto[] loans = new LoanCashDto[1000];

    public RaportDto(UserDto userDto) {
        userId = userDto.getId();
        login = userDto.getLogin();
        firstName = userDto.getFirstName();
        lastName = userDto.getLastName();
        email = userDto.getEmail();
        saldo = userDto.getSaldo();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

//    public LoanCashDto[] getLoans() {
//        return loans;
//    }
//
//    public void setLoans(LoanCashDto[] loans) {
//        this.loans = loans;
//    }

}
