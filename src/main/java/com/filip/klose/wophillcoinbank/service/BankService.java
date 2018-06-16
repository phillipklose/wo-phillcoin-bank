package com.filip.klose.wophillcoinbank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;
import com.filip.klose.wophillcoinbank.runtime.exception.UserNotFoundException;

@Service
public class BankService {

    @Autowired
    private UserService userService;

    @Autowired
    private CashOutOfBankService cashOutOfBankService;

    public void transferBeetwenAccounts(TransferBetweenAccountsDto betweenAccountsDto) throws CashTransferException, GetCashException {
        final Optional<User> userToTransferFrom = userService.getUserByUserId(betweenAccountsDto.from);
        final Optional<User> userToTransferTo = userService.getUserByUserId(betweenAccountsDto.to);
        if (userToTransferFrom.isPresent() && userToTransferTo.isPresent()) {
            handleCashTransfer(betweenAccountsDto, userToTransferFrom.get(), userToTransferTo.get());
        } else {
            throw new CashTransferException();
        }
    }

    public CashOutOfBank getCash(String userId, int amount) throws GetCashException, UserNotFoundException {
        final Optional<User> user = userService.getUserByUserId(userId);
        final User userThatGetsCash = user.orElseThrow(UserNotFoundException::new);
        userThatGetsCash.getCash(amount);
        return cashOutOfBankService.saveOutOfBankCash(amount);
    }

    private void handleCashTransfer(TransferBetweenAccountsDto betweenAccountsDto, User userFrom, User userTo)
            throws CashTransferException, GetCashException {
        validateTransaction(betweenAccountsDto, userFrom);
        transferCashBetweenAccounts(betweenAccountsDto, userFrom, userTo);
        storeUpdatedAccountSaldo(userFrom, userTo);
    }

    private void storeUpdatedAccountSaldo(User userFrom, User userTo) {
        userService.saveUser(userFrom);
        userService.saveUser(userTo);
    }

    private void transferCashBetweenAccounts(TransferBetweenAccountsDto betweenAccountsDto, User userFrom, User userTo) throws GetCashException {
        userFrom.getCash(betweenAccountsDto.amount);
        userTo.addCash(betweenAccountsDto.amount);
    }

    private void validateTransaction(TransferBetweenAccountsDto betweenAccountsDto, User userFrom) throws CashTransferException {
        if (userFrom.getSaldo() < betweenAccountsDto.amount) {
            throw new CashTransferException();
        }
    }

}
