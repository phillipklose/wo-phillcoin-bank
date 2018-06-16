package com.filip.klose.wophillcoinbank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.repository.CashOutOfBankRepository;

@RunWith(SpringRunner.class)
public class CashOutOfBankServiceTest {

    @TestConfiguration
    static class CashOutOfBankServiceTestContextConfiguration {

        @Bean
        public CashOutOfBankService cashOutOfBankService() {
            return new CashOutOfBankService();
        }
    }

    @MockBean
    private CashOutOfBankRepository cashOutOfBankRepository;

    @Autowired
    private CashOutOfBankService cashOutOfBankService;

    @Test
    public void isCashValidForNotExistingCashIdShouldReturnFalse() {
        // given
        String notExistingCashId = "notExistingCashId";
        when(cashOutOfBankRepository.findById(notExistingCashId)).thenReturn(Optional.empty());

        // when
        final boolean cashValid = cashOutOfBankService.isCashValid(notExistingCashId);

        // then
        assertNotNull(cashValid);
        assertEquals(cashValid, false);
    }

    @Test
    public void isCashValidForExistingCashIdShouldReturnTrue() {
        // given
        String existingCashId = "existingCashId";
        CashOutOfBank cashOutOfBank = mock(CashOutOfBank.class);
        when(cashOutOfBankRepository.findById(existingCashId)).thenReturn(Optional.of(cashOutOfBank));

        // when
        final boolean cashValid = cashOutOfBankService.isCashValid(existingCashId);

        // then
        assertNotNull(cashValid);
        assertEquals(cashValid, true);
    }

    @Test
    public void saveOutOfBankCash() {
        // given
        int amount = 100;
        CashOutOfBank savedCash = new CashOutOfBank(amount);
        savedCash.setId(new ObjectId("5b2522993a17aa3a30630555"));
        when(cashOutOfBankRepository.save(any())).thenReturn(savedCash);


        // when
        final CashOutOfBank cashOutOfBank = cashOutOfBankService.saveOutOfBankCash(amount);

        // then
        assertNotNull(cashOutOfBank);
        assertEquals(cashOutOfBank.getAmount(), amount);
        assertNotNull(cashOutOfBank.getId());
    }
}