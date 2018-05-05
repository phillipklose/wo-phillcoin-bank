package com.filip.klose.wophillcoinbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<?> transferBetweenAccounts(@RequestBody TransferBetweenAccountsDto betweenAccountsDto) {
        try {
            bankService.transferBeetwenAccounts(betweenAccountsDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CashTransferException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
