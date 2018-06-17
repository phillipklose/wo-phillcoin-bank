package com.filip.klose.wophillcoinbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.LoanCash;
import com.filip.klose.wophillcoinbank.repository.LoanCashRepository;

@Service
public class LoanCashService {

    @Autowired
    private LoanCashRepository loanCashRepository;

    public void save(LoanCash loanCash) {
        loanCashRepository.save(loanCash);
    }

    public List<LoanCash> getAll() {
        return loanCashRepository.findAll();
    }

}
