package com.filip.klose.wophillcoinbank.model;

public class TransferBetweenAccountsDto {
    public String from;
    public String to;
    public int amount;

    public TransferBetweenAccountsDto() {
    }

    public TransferBetweenAccountsDto(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
