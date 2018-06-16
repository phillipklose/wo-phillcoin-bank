package com.filip.klose.wophillcoinbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.mapper.CashOutOfBankMapper;
import com.filip.klose.wophillcoinbank.model.CashOutOfBankDto;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;
import com.filip.klose.wophillcoinbank.runtime.exception.UserNotFoundException;
import com.filip.klose.wophillcoinbank.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private CashOutOfBankMapper mapper;

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<?> transferBetweenAccounts(@RequestBody TransferBetweenAccountsDto betweenAccountsDto) {
        try {
            bankService.transferBeetwenAccounts(betweenAccountsDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CashTransferException | GetCashException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getCash")
    @ResponseBody
    public ResponseEntity<?> getCash(@RequestParam("userId") String userId, @RequestParam("amount") int amount) {
        try {
            final CashOutOfBank cash = bankService.getCash(userId, amount);
            final CashOutOfBankDto cashOutOfBankDto = mapper.convertToDto(cash);
            return ResponseEntity.ok(cashOutOfBankDto);
        } catch (GetCashException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}