package com.filip.klose.wophillcoinbank.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class CashOutOfBank {

    @Id
    private ObjectId id;
    private int amount;

    public CashOutOfBank(int amount) {
        this.amount = amount;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
