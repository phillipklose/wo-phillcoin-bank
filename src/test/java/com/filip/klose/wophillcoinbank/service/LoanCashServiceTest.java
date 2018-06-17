package com.filip.klose.wophillcoinbank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.entity.LoanCash;
import com.filip.klose.wophillcoinbank.repository.LoanCashRepository;

@RunWith(SpringRunner.class)
public class LoanCashServiceTest {

    @TestConfiguration
    static class LoanCashServiceTestContextConfiguration {

        @Bean
        public LoanCashService loanCashServiceService() {
            return new LoanCashService();
        }
    }

    @Autowired
    private LoanCashService loanCashService;

    @MockBean
    private LoanCashRepository loanCashRepository;

    @Test
    public void save() {
        // given
        String userId = "someUserId";
        int amount = 100;
        LoanCash loanCashToSave = new LoanCash(userId, amount);
        when(loanCashRepository.save(loanCashToSave)).thenReturn(loanCashToSave);

        // when
        loanCashService.save(loanCashToSave);
    }

    @Test
    public void getAll() {
        // given
        String userId = "someUserId";
        int amount = 100;
        int amount2 = 1000;
        LoanCash loanCash = new LoanCash(userId, amount);
        LoanCash loanCash2 = new LoanCash(userId, amount2);
        when(loanCashRepository.findAll()).thenReturn(Arrays.asList(loanCash, loanCash2));

        // when
        final List<LoanCash> allLoans = loanCashService.getAll();

        // then
        assertNotNull(allLoans);
        assertEquals(allLoans.size(), 2);
        assertEquals(allLoans.get(0).getAmount(), 100);
        assertEquals(allLoans.get(0).getInterest(), 10);
        assertEquals(allLoans.get(0).getUserId(), userId);
    }
}