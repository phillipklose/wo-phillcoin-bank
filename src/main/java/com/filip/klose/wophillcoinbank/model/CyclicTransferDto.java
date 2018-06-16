package com.filip.klose.wophillcoinbank.model;

import java.util.Date;

public class CyclicTransferDto extends TransferBetweenAccountsDto {
    private Date transactionTime;
    private int intervalInMinutes;

    public CyclicTransferDto(TransferBetweenAccountsDto transferBetweenAccountsDto) {
        setAmount(transferBetweenAccountsDto.getAmount());
        setFrom(transferBetweenAccountsDto.getFrom());
        setTo(transferBetweenAccountsDto.getTo());
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public void setIntervalInMinutes(int intervalInMinutes) {
        this.intervalInMinutes = intervalInMinutes;
    }
}
