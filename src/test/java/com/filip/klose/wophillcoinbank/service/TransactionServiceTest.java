package com.filip.klose.wophillcoinbank.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.entity.Transaction;
import com.filip.klose.wophillcoinbank.repository.TransactionRepository;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @TestConfiguration
    static class TransactionServiceTestContextConfiguration {

        @Bean
        public TransactionService transactionServiceService() {
            return new TransactionService();
        }
    }

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void saveTransaction() {
        // given
        String userId = "id";
        int amount = 100;
        Transaction transaction = new Transaction(userId, amount);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // when
        transactionService.saveTransaction(userId, amount);
    }
}