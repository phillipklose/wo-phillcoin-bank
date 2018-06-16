package com.filip.klose.wophillcoinbank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.model.CashOutOfBankDto;

@Service
public class CashOutOfBankMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CashOutOfBankDto convertToDto(CashOutOfBank cashOutOfBank) {
        CashOutOfBankDto cashOutOfBankDto = modelMapper.map(cashOutOfBank, CashOutOfBankDto.class);
        return cashOutOfBankDto;
    }

}
