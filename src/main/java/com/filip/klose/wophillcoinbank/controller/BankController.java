package com.filip.klose.wophillcoinbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.mapper.CashOutOfBankMapper;
import com.filip.klose.wophillcoinbank.model.CashOutOfBankDto;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashNotValidException;
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

    @PostMapping("/transfer/cyclic")
    @ResponseBody
    public ResponseEntity<?> transferBetweenAccounts(@RequestParam("cycle") int cycleIntervalInMinutes,
                                                     @RequestBody TransferBetweenAccountsDto betweenAccountsDto) {
        try {
            bankService.transferBeetwenAccountsInCycle(betweenAccountsDto, cycleIntervalInMinutes);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
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

    @PostMapping("/putCash")
    @ResponseBody
    public ResponseEntity<?> putCash(@RequestParam("userId") String userId, @RequestBody CashOutOfBankDto cashOutOfBankDto) {
        try {
            bankService.putCash(userId, cashOutOfBankDto.getId());
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException | CashNotValidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
