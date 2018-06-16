package com.filip.klose.wophillcoinbank.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Transaction {

    @Id
    private ObjectId id;
    private String userId;
    private int amount;

    public Transaction(String userId, int amount) {
        this.userId = userId;
        this.amount = amount;
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
}
