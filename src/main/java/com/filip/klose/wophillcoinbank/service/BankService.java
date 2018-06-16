package com.filip.klose.wophillcoinbank.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.CashOutOfBank;
import com.filip.klose.wophillcoinbank.entity.CyclicTransfer;
import com.filip.klose.wophillcoinbank.entity.User;
import com.filip.klose.wophillcoinbank.model.TransferBetweenAccountsDto;
import com.filip.klose.wophillcoinbank.runtime.exception.CashNotValidException;
import com.filip.klose.wophillcoinbank.runtime.exception.CashTransferException;
import com.filip.klose.wophillcoinbank.runtime.exception.GetCashException;
import com.filip.klose.wophillcoinbank.runtime.exception.UserNotFoundException;

@Service
public class BankService {

    @Autowired
    private UserService userService;

    @Autowired
    private CashOutOfBankService cashOutOfBankService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CyclicTransferService cyclicTransferService;

    public void transferBeetwenAccounts(TransferBetweenAccountsDto betweenAccountsDto) throws CashTransferException, GetCashException {
        final Optional<User> userToTransferFrom = userService.getUserByUserId(betweenAccountsDto.from);
        final Optional<User> userToTransferTo = userService.getUserByUserId(betweenAccountsDto.to);
        if (userToTransferFrom.isPresent() && userToTransferTo.isPresent()) {
            handleCashTransfer(betweenAccountsDto, userToTransferFrom.get(), userToTransferTo.get());
        } else {
            throw new CashTransferException();
        }
    }

    public void transferBeetwenAccountsInCycle(TransferBetweenAccountsDto betweenAccountsDto, int cycleInMinutes) throws
            UserNotFoundException {
        final Optional<User> userToTransferFrom = userService.getUserByUserId(betweenAccountsDto.from);
        final Optional<User> userToTransferTo = userService.getUserByUserId(betweenAccountsDto.to);
        if (userToTransferFrom.isPresent() && userToTransferTo.isPresent()) {
            CyclicTransfer cyclicTransfer = new CyclicTransfer();
            cyclicTransfer.setAmount(betweenAccountsDto.getAmount());
            cyclicTransfer.setFrom(betweenAccountsDto.getFrom());
            cyclicTransfer.setTo(betweenAccountsDto.getTo());
            cyclicTransfer.setIntervalInMinutes(cycleInMinutes);
            LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(cycleInMinutes, ChronoUnit.MINUTES));
            Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            cyclicTransfer.setTransactionTime(tmfn);
            cyclicTransferService.saveCyclicTransfer(cyclicTransfer);
        } else {
            throw new UserNotFoundException();
        }
    }

    public CashOutOfBank getCash(String userId, int amount) throws GetCashException, UserNotFoundException {
        final User user = userService.getUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        user.getCash(amount);
        userService.saveUser(user);
        transactionService.saveTransaction(userId, -amount);
        return cashOutOfBankService.saveOutOfBankCash(amount);
    }

    public void putCash(String userId, String cashId) throws UserNotFoundException, CashNotValidException {
        final User user = userService.getUserByUserId(userId).orElseThrow(UserNotFoundException::new);
        final CashOutOfBank cashOutOfBank = cashOutOfBankService.getCashOutOfBank(cashId).orElseThrow(CashNotValidException::new);
        user.addCash(cashOutOfBank.getAmount());
        userService.saveUser(user);
        transactionService.saveTransaction(userId, cashOutOfBank.getAmount());
        cashOutOfBankService.removeCashOutOfBankEntry(cashId);
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

    private void transferCashBetweenAccounts(TransferBetweenAccountsDto betweenAccountsDto, User userFrom, User userTo) throws
            GetCashException {
        userFrom.getCash(betweenAccountsDto.amount);
        userTo.addCash(betweenAccountsDto.amount);
    }

    private void validateTransaction(TransferBetweenAccountsDto betweenAccountsDto, User userFrom) throws CashTransferException {
        if (userFrom.getSaldo() < betweenAccountsDto.amount) {
            throw new CashTransferException();
        }
    }

}
