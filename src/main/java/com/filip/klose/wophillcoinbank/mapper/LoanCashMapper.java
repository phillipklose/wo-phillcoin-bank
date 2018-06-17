package com.filip.klose.wophillcoinbank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.LoanCash;
import com.filip.klose.wophillcoinbank.model.LoanCashDto;

@Service
public class LoanCashMapper {

    @Autowired
    private ModelMapper modelMapper;

    public LoanCashDto convertToDto(LoanCash loanCash) {
        LoanCashDto loanCashDto = modelMapper.map(loanCash, LoanCashDto.class);
        return loanCashDto;
    }

}
