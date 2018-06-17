package com.filip.klose.wophillcoinbank.model;

public class LoanCashDto {
    private String id;
    private String userId;
    private int amount;
    private long timeToPayBack;
    private int interest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public long getTimeToPayBack() {
        return timeToPayBack;
    }

    public void setTimeToPayBack(long timeToPayBack) {
        this.timeToPayBack = timeToPayBack;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "LoanCashDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", timeToPayBack=" + timeToPayBack +
                ", interest=" + interest +
                '}';
    }
}
