package com.filip.klose.wophillcoinbank.entity;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LoanCashTest {

    @Test
    public void calculateNewAmount() throws InterruptedException {
        // given
        String userId = "someUserId";
        int amount = 100;
        LoanCash loanCash = new LoanCash(userId, amount);
        Long initTimeToPayBack = loanCash.getTimeToPayBack().toInstant().toEpochMilli();

        // when
        // sleep to be sure that time after change is different then init one
        Thread.sleep(100);
        loanCash.calculateNewAmount();

        // then
        assertEquals(loanCash.getUserId(), userId);
        assertEquals(loanCash.getInterest(), 10);
        assertEquals(loanCash.getAmount(), 110);
        assertNotNull(loanCash.getTimeToPayBack());
        assertNotEquals(Long.valueOf(loanCash.getTimeToPayBack().toInstant().toEpochMilli()), initTimeToPayBack);
    }
}