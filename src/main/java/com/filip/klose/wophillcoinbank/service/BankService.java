package com.filip.klose.wophillcoinbank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;

@Service
public class BankService {

    @Autowired
    private UserService userService;

    public void transferBeetwenAccounts(TransferBetweenAccountsDto betweenAccountsDto) throws CashTransferException {
        final Optional<User> userToTransferFrom = userService.getUserByUserId(betweenAccountsDto.from);
        final Optional<User> userToTransferTo = userService.getUserByUserId(betweenAccountsDto.to);
        if (userToTransferFrom.isPresent() && userToTransferTo.isPresent()) {
            handleCashTransfer(betweenAccountsDto, userToTransferFrom.get(), userToTransferTo.get());
        } else {
            throw new CashTransferException();
        }
    }

    private void handleCashTransfer(TransferBetweenAccountsDto betweenAccountsDto, User userFrom, User userTo)
            throws CashTransferException {
        validateTransaction(betweenAccountsDto, userFrom);
        transferCashBetweenAccounts(betweenAccountsDto, userFrom, userTo);
        storeUpdatedAccountSaldo(userFrom, userTo);
    }

    private void storeUpdatedAccountSaldo(User userFrom, User userTo) {
        userService.saveUser(userFrom);
        userService.saveUser(userTo);
    }

    private void transferCashBetweenAccounts(TransferBetweenAccountsDto betweenAccountsDto, User userFrom, User userTo) {
        userFrom.getCash(betweenAccountsDto.amount);
        userTo.addCash(betweenAccountsDto.amount);
    }

    private void validateTransaction(TransferBetweenAccountsDto betweenAccountsDto, User userFrom) throws CashTransferException {
        if (userFrom.getSaldo() < betweenAccountsDto.amount) {
            throw new CashTransferException();
        }
    }

}
