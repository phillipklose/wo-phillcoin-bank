package com.filip.klose.wophillcoinbank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.repository.CashOutOfBankRepository;

@Service
public class CashOutOfBankService {

    @Autowired
    private CashOutOfBankRepository cashOutOfBankRepository;

    public boolean isCashValid(String cashId) {
        return cashOutOfBankRepository.findById(cashId).isPresent();
    }

    public CashOutOfBank saveOutOfBankCash(int amount) {
        CashOutOfBank cash = new CashOutOfBank(amount);
        return cashOutOfBankRepository.save(cash);
    }

    public Optional<CashOutOfBank> getCashOutOfBank(String cashId) {
        return cashOutOfBankRepository.findById(cashId);
    }

    public void removeCashOutOfBankEntry(String cashId) {
        cashOutOfBankRepository.deleteById(cashId);
    }

}
