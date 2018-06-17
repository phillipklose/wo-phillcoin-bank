package com.filip.klose.wophillcoinbank.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class LoanCash {

    @Id
    private ObjectId id;
    public String userId;
    public int amount;
    private Date timeToPayBack;
    private int interest = 10;

    public LoanCash(String userId, int amount) {
        this.userId = userId;
        this.amount = amount;

        // set the time to pay back the loan
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
        Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        this.timeToPayBack = tmfn;

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTimeToPayBack() {
        return timeToPayBack;
    }

    public void setTimeToPayBack(Date timeToPayBack) {
        this.timeToPayBack = timeToPayBack;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public void calculateNewAmount() {
        int newInterest = getAmount() * getInterest() / 100;
        int newAmount = getAmount() + newInterest;
        setAmount(newAmount);

        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
        Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        setTimeToPayBack(tmfn);
    }
}
