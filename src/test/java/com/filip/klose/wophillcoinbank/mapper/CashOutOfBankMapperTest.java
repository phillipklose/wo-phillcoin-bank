package com.filip.klose.wophillcoinbank.mapper;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.model.CashOutOfBankDto;

public class CashOutOfBankMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertToDto() {
        // given
        CashOutOfBank cashOutOfBank = new CashOutOfBank(100);
        cashOutOfBank.setId(new ObjectId("5b2522b53a17aa251c1d4ef6"));

        // when
        final CashOutOfBankDto cashOutOfBankDto = modelMapper.map(cashOutOfBank, CashOutOfBankDto.class);

        // then
        assertNotNull(cashOutOfBankDto);
        assertEquals(cashOutOfBankDto.getAmount(), cashOutOfBank.getAmount());
        assertNotNull(cashOutOfBankDto.getId());
    }
}